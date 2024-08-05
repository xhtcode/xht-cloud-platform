package com.xht.cloud.generate.module.table.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.constant.TreeConstant;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.generate.constant.GenerateConstant;
import com.xht.cloud.generate.constant.enums.GenerateType;
import com.xht.cloud.generate.constant.enums.HtmlTypeEnum;
import com.xht.cloud.generate.constant.enums.QueryTypeEnum;
import com.xht.cloud.generate.module.column.convert.GenTableColumnConvert;
import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnUpdateRequest;
import com.xht.cloud.generate.module.column.mapper.GenTableColumnMapper;
import com.xht.cloud.generate.module.config.domain.dataobject.GenCodeConfigDO;
import com.xht.cloud.generate.module.config.mapper.GenCodeConfigMapper;
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
import com.xht.cloud.generate.module.type.domain.dataobject.GenColumnTypeDO;
import com.xht.cloud.generate.module.type.mapper.GenColumnTypeMapper;
import com.xht.cloud.generate.support.DataBaseQueryFactory;
import com.xht.cloud.generate.support.IDataBaseQuery;
import com.xht.cloud.generate.utils.GenerateTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 描述 ：代码生成器-数据库信息
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenTableServiceImpl implements IGenTableService, InitializingBean {

    private final GenCodeConfigMapper genCodeConfigMapper;
    private final GenTableMapper genTableMapper;

    private final GenDatabaseMapper genDatabaseMapper;

    private final GenTableConvert genTableConvert;

    private final DataBaseQueryFactory dataBaseQueryFactory;

    private final GenTableColumnMapper genTableColumnMapper;
    private final GenTableColumnConvert genTableColumnConvert;

    private final GenColumnTypeMapper genColumnTypeMapper;

    private static final Map<String, String> JAVA_TYPE = new HashMap<>();
    private static final Map<String, String> TS_TYPE = new HashMap<>();
    private static final GenTableWrapper GEN_TABLE_WRAPPER = new GenTableWrapper();


    /**
     * 创建
     *
     * @param createRequest {@link GenTableCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(GenTableCreateRequest createRequest) {
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
     * @return {@link PageResponse<GenTableResponse>} 分页详情
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
        String dbId = importRequest.getDbId();
        GenDatabaseDO genDatabaseDO = genDatabaseMapper.findById(dbId).orElse(null);
        if (Objects.isNull(genDatabaseDO)) {
            return Boolean.FALSE;
        }
        List<String> tableNames = importRequest.getTableNames();
        IDataBaseQuery dataBaseQuery = dataBaseQueryFactory.getStrategy(genDatabaseDO.getDbType());
        for (String tableName : tableNames) {
            GenTableDO genTableDO = dataBaseQuery.selectTableByTableName(genDatabaseDO, tableName);
            List<GenTableColumnDO> genTableColumnDOS = dataBaseQuery.selectTableColumnsByTableName(genDatabaseDO, tableName);
            saveInfo(genDatabaseDO, genTableDO, genTableColumnDOS);
        }
        return Boolean.TRUE;
    }

    public void saveInfo(GenDatabaseDO genDatabaseDO, GenTableDO genTableDO, List<GenTableColumnDO> genTableColumnDOS) {
        genTableDO.setGenDbId(genDatabaseDO.getId());
        genTableDO.setModuleLabel(GenerateTool.getModuleLabel(genTableDO.getTableName()));
        genTableDO.setModuleName(GenerateTool.getModuleName(genTableDO.getTableName()));
        genTableDO.setModuleDesc(genTableDO.getModuleValue());
        genTableDO.setAuthorizationPrefix(GenerateTool.getAuthorizationPrefix(genTableDO.getTableName()));
        genTableDO.setPathUrl(GenerateTool.getPathUrl(genTableDO.getTableName()));
        genTableDO.setCodeName(GenerateTool.getClassName(genTableDO.getTableName()));
        genTableDO.setGenType(GenerateType.ZIP.getValue());
        genTableDO.setMenuId(TreeConstant.TREE_PARENT_DEFAULT);
        genTableDO.setLombok(GenerateConstant.SUCCESS_STATUS);
        genTableDO.setJsr303(GenerateConstant.SUCCESS_STATUS);
        genTableDO.setSwagger(GenerateConstant.SUCCESS_STATUS);
        genTableDO.setAuthorization(GenerateConstant.SUCCESS_STATUS);
        genTableDO.setTemplateType("0");
        genTableDO.setConfigId(genCodeConfigMapper.selectOne(GenCodeConfigDO::getConfigDefault, GenerateConstant.SUCCESS_STATUS).orElse(new GenCodeConfigDO()).getId());
        genTableMapper.insert(genTableDO);
        if (!CollectionUtils.isEmpty(genTableColumnDOS)) {
            for (GenTableColumnDO genTableColumn : genTableColumnDOS) {
                genTableColumn.setTableId(genTableDO.getId());
                genTableColumn.setTableSchema(genTableColumn.getTableSchema());
                genTableColumn.setTableName(genTableDO.getTableName());
                genTableColumn.setColumnCodeName(GenerateTool.getColumnName(genTableColumn.getColumnName()));
                genTableColumn.setColumnJavaType(getJavaType(genTableColumn.getColumnDbType()));
                genTableColumn.setColumnTsType(getTsType(genTableColumn.getColumnDbType()));
                genTableColumn.setColumnExample(null);
                genTableColumn.setColumnList(GenerateTool.findColumnExits(genTableColumn.getColumnName(), GenerateConstant.BASE_COLUMN_BASE_DELETE_NAME_));
                genTableColumn.setColumnSave(GenerateTool.findColumnExits(genTableColumn.getColumnName(), GenerateConstant.BASE_COLUMN_BASE_DELETE_NAME_));
                genTableColumn.setColumnUpdate(GenerateTool.findColumnExits(genTableColumn.getColumnName(), GenerateConstant.BASE_COLUMN_BASE_DELETE_NAME));
                genTableColumn.setColumnQuery(GenerateTool.findColumnExits(genTableColumn.getColumnName(), GenerateConstant.BASE_COLUMN_BASE_DELETE_NAME_));
                genTableColumn.setColumnDict(null);
                genTableColumn.setColumnHtml(HtmlTypeEnum.INPUT.getValue());
                genTableColumn.setColumnQueryType(QueryTypeEnum.eq.getValue());
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
        //现在配置
        return true;
    }

    /**
     * 获取未进行同步的表
     *
     * @param importRequest 表名
     * @return R
     */
    @Override
    public List<GenTableResponse> syncList(ImportRequest importRequest) {
        String dbId = importRequest.getDbId();
        String tableName = importRequest.getTableName();
        GenDatabaseDO genDatabaseDO = genDatabaseMapper.findById(dbId).orElse(null);
        if (Objects.isNull(genDatabaseDO)) {
            return Collections.emptyList();
        }
        IDataBaseQuery dataBaseQuery = dataBaseQueryFactory.getStrategy(genDatabaseDO.getDbType());
        List<GenTableDO> genTableDOS = dataBaseQuery.selectListTableByLikeTableName(genDatabaseDO, tableName);
        return genTableConvert.toResponse(genTableDOS);
    }

    @Override
    public void afterPropertiesSet() {
        List<GenColumnTypeDO> tsTypes = genColumnTypeMapper.selectList(GenColumnTypeDO::getLabel, "TS");
        List<GenColumnTypeDO> javaTypes = genColumnTypeMapper.selectList(GenColumnTypeDO::getLabel, "Java");
        for (GenColumnTypeDO typeDO : tsTypes) {
            TS_TYPE.put(typeDO.getDbValue().toLowerCase(), typeDO.getValue());
        }
        for (GenColumnTypeDO typeDO : javaTypes) {
            JAVA_TYPE.put(typeDO.getDbValue().toLowerCase(), typeDO.getValue());
        }
    }

    public String getJavaType(String name) {
        if (!StringUtils.hasText(name)) return "String";
        String item = JAVA_TYPE.get(name.toLowerCase());
        return StringUtils.hasText(item) ? item : "String";
    }

    public String getTsType(String name) {
        if (!StringUtils.hasText(name)) return "any";
        String item = TS_TYPE.get(name.toLowerCase());
        return StringUtils.hasText(item) ? item : "any";
    }

}
