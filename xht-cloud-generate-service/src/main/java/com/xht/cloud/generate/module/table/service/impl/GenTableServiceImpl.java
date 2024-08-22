package com.xht.cloud.generate.module.table.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.generate.constant.GenerateConstant;
import com.xht.cloud.generate.exception.GenerateException;
import com.xht.cloud.generate.module.column.convert.GenTableColumnConvert;
import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnUpdateRequest;
import com.xht.cloud.generate.module.column.mapper.GenTableColumnMapper;
import com.xht.cloud.generate.module.database.domain.dataobject.GenDatabaseDO;
import com.xht.cloud.generate.module.database.mapper.GenDatabaseMapper;
import com.xht.cloud.generate.module.table.convert.GenTableConvert;
import com.xht.cloud.generate.module.table.domain.dataobject.GenTableDO;
import com.xht.cloud.generate.module.table.domain.request.GenTableCreateRequest;
import com.xht.cloud.generate.module.table.domain.request.GenTableQueryRequest;
import com.xht.cloud.generate.module.table.domain.request.GenTableUpdateRequest;
import com.xht.cloud.generate.module.table.domain.request.ImportRequest;
import com.xht.cloud.generate.module.table.domain.response.GenTableResponse;
import com.xht.cloud.generate.module.table.domain.response.GenerateVo;
import com.xht.cloud.generate.module.table.domain.wrapper.GenTableWrapper;
import com.xht.cloud.generate.module.table.mapper.GenTableMapper;
import com.xht.cloud.generate.module.table.service.IGenTableService;
import com.xht.cloud.generate.support.DataBaseQueryFactory;
import com.xht.cloud.generate.support.IDataBaseQuery;
import com.xht.cloud.generate.utils.GenerateTool;
import com.xht.cloud.generate.utils.JDBCUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 描述 ：代码生成器-数据库信息
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenTableServiceImpl implements IGenTableService {

    private final GenTableMapper genTableMapper;

    private final GenDatabaseMapper genDatabaseMapper;

    private final GenTableConvert genTableConvert;

    private final DataBaseQueryFactory dataBaseQueryFactory;

    private final GenTableColumnMapper genTableColumnMapper;

    private final GenTableColumnConvert genTableColumnConvert;
    private static final GenTableWrapper GEN_TABLE_WRAPPER = new GenTableWrapper();


    /**
     * 创建
     *
     * @param createRequest {@link GenTableCreateRequest}
     * @return {@link Long} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(GenTableCreateRequest createRequest) {
        GenTableDO entity = genTableConvert.toDO(createRequest);
        genTableMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 根据id修改
     *
     * @param updateRequest GenTableUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GenTableUpdateRequest updateRequest) {
        updateRequest.setPathUrl(StrUtil.addPrefixIfNot(updateRequest.getPathUrl(), "/"));
        List<GenTableColumnUpdateRequest> columns = updateRequest.getColumns();
        Assert.notEmpty(columns, "字段信息查询不到!");
        genTableMapper.updateById(genTableConvert.toDO(updateRequest));
        columns.forEach(item -> genTableColumnMapper.updateById(genTableColumnConvert.toDO(item)));
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        genTableMapper.deleteBatchIds(ids);
        genTableColumnMapper.delete(new LambdaQueryWrapper<GenTableColumnDO>().in(GenTableColumnDO::getTableId, ids));
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenerateVo}
     */
    @Override
    public GenerateVo findById(String id) {
        GenerateVo generateVo = new GenerateVo();
        GenTableResponse tableResponse = genTableConvert.toResponse(genTableMapper.findById(id).orElse(null));
        generateVo.setTable(tableResponse);
        if (Objects.nonNull(tableResponse)) {
            List<GenTableColumnDO> genTableColumnDOS = genTableColumnMapper.selectList(GenTableColumnDO::getTableId, tableResponse.getId());
            generateVo.setColumns(genTableColumnConvert.toResponse(genTableColumnDOS));
        }
        return generateVo;
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenTableQueryRequest}
     * @return {@link GenTableResponse} 分页详情
     */
    @Override
    public PageResponse<GenTableResponse> findPage(GenTableQueryRequest queryRequest) {
        IPage<GenTableDO> genTableIPage = genTableMapper.selectPage(PageTool.getPage(queryRequest), GEN_TABLE_WRAPPER.lambdaQuery(genTableConvert.toDO(queryRequest)));
        return genTableConvert.toPageResponse(genTableIPage);
    }


    /**
     * 从数据库把表信息同步到gen_table表信息中
     *
     * @param importRequest 请求信息
     * @return Boolean true/false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importTable(final ImportRequest importRequest) {
        Long dbId = importRequest.getDbId();
        GenDatabaseDO genDatabaseDO = genDatabaseMapper.findById(dbId).orElseThrow(() -> new GenerateException("查询不到数据源信息！"));
        List<String> tableNames = importRequest.getTableNames();
        IDataBaseQuery dataBaseQuery = dataBaseQueryFactory.getStrategy(genDatabaseDO.getDbType());
        JDBCUtils jdbcUtils = JDBCUtils.jdbcTemplate(genDatabaseDO);
        try {
            JdbcTemplate jdbcTemplate = jdbcUtils.getJdbcTemplate();
            for (String tableName : tableNames) {
                GenTableDO genTableDO = dataBaseQuery.selectTableByTableName(jdbcTemplate, genDatabaseDO, tableName);
                List<GenTableColumnDO> columnDOList = dataBaseQuery.selectTableColumnsByTableName(jdbcTemplate, genDatabaseDO, tableName);
                saveInfo(importRequest, genDatabaseDO, genTableDO, columnDOList);
            }
        } catch (Exception e) {
            throw new GenerateException("导入库表失败", e);
        } finally {
            jdbcUtils.close();
        }
        return Boolean.TRUE;
    }

    public void saveInfo(ImportRequest importRequest, GenDatabaseDO genDatabaseDO, GenTableDO genTableDO, List<GenTableColumnDO> genTableColumnDOS) {
        genTableDO.setGenDbId(genDatabaseDO.getId());
        genTableDO.setModuleName(importRequest.getModuleName());
        genTableDO.setConfigId(importRequest.getConfigId());
        genTableDO.setServiceName(GenerateTool.getServiceName(genTableDO.getTableName()));
        genTableDO.setServiceDesc(genTableDO.getServiceDesc());
        genTableDO.setAuthorizationPrefix(GenerateTool.getAuthorizationPrefix(genTableDO.getTableName()));
        genTableDO.setPathUrl(GenerateTool.getPathUrl(genTableDO.getTableName()));
        genTableDO.setCodeName(GenerateTool.getClassName(genTableDO.getTableName()));
        genTableMapper.insert(genTableDO);
        if (!CollectionUtils.isEmpty(genTableColumnDOS)) {
            for (GenTableColumnDO genTableColumn : genTableColumnDOS) {
                genTableColumn.setTableId(genTableDO.getId());
                genTableColumn.setColumnCodeName(GenerateTool.getColumnName(genTableColumn.getColumnName()));
                genTableColumn.setColumnList(GenerateTool.findColumnExits(genTableColumn.getColumnName(), GenerateConstant.BASE_COLUMN_BASE_DELETE_NAME_));
                genTableColumn.setColumnOperation(GenerateTool.findColumnExits(genTableColumn.getColumnName(), GenerateConstant.BASE_COLUMN_BASE_DELETE_NAME_));
                genTableColumn.setColumnQuery(GenerateTool.findColumnExits(genTableColumn.getColumnName(), GenerateConstant.BASE_COLUMN_BASE_DELETE_NAME_));
            }
            genTableColumnMapper.insertBatch(genTableColumnDOS);
        }
    }


    /**
     * 从数据库把表信息同步到gen_table表信息中
     *
     * @param tableId 表Id
     * @return Boolean true/false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean synchronous(final String tableId) {
        GenTableDO genTableDO = genTableMapper.findById(tableId).orElseThrow(() -> new GenerateException("表不存在，请先导入表!"));
        genTableMapper.deleteById(tableId);
        genTableColumnMapper.delete(new LambdaQueryWrapper<GenTableColumnDO>().eq(GenTableColumnDO::getTableId, tableId));
        ImportRequest importRequest = new ImportRequest();
        importRequest.setTableNames(Collections.singletonList(genTableDO.getTableName()));
        importRequest.setTableName(genTableDO.getTableName());
        importRequest.setDbId(genTableDO.getGenDbId());
        importRequest.setModuleName(genTableDO.getModuleName());
        importRequest.setConfigId(genTableDO.getConfigId());
        return importTable(importRequest);
    }

    /**
     * 获取未进行同步的表
     *
     * @param importRequest 表名
     * @return R
     */
    @Override
    public List<GenTableResponse> syncList(ImportRequest importRequest) {
        Long dbId = importRequest.getDbId();
        String tableName = importRequest.getTableName();
        GenDatabaseDO genDatabaseDO = genDatabaseMapper.findById(dbId).orElse(null);
        if (Objects.isNull(genDatabaseDO)) {
            return Collections.emptyList();
        }
        IDataBaseQuery dataBaseQuery = dataBaseQueryFactory.getStrategy(genDatabaseDO.getDbType());
        List<GenTableDO> genTableDOS = dataBaseQuery.selectListTableByLikeTableName(genDatabaseDO, tableName);
        return genTableConvert.toResponse(genTableDOS);
    }

}
