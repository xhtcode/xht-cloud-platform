package com.xht.cloud.generate.module.column.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnQueryRequest;
import com.xht.cloud.generate.module.column.mapper.GenTableColumnMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class GenTableColumnDao extends BaseDaoImpl<GenTableColumnMapper, GenTableColumnDO> {

    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public IPage<GenTableColumnDO> pageQueryRequest(GenTableColumnQueryRequest queryRequest) {
        LambdaQueryWrapper<GenTableColumnDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(Objects.nonNull(queryRequest.getTableId()), GenTableColumnDO::getTableId, queryRequest.getTableId())
                .eq(StringUtils.hasText(queryRequest.getColumnName()), GenTableColumnDO::getColumnName, queryRequest.getColumnName())
                .eq(!ObjectUtils.isEmpty(queryRequest.getColumnLength()), GenTableColumnDO::getColumnLength, queryRequest.getColumnLength())
                .eq(StringUtils.hasText(queryRequest.getColumnCodeName()), GenTableColumnDO::getColumnCodeName, queryRequest.getColumnCodeName())
                .eq(StringUtils.hasText(queryRequest.getColumnComment()), GenTableColumnDO::getColumnComment, queryRequest.getColumnComment())
                .eq(StringUtils.hasText(queryRequest.getColumnDbType()), GenTableColumnDO::getColumnDbType, queryRequest.getColumnDbType())
                .eq(StringUtils.hasText(queryRequest.getColumnPk()), GenTableColumnDO::getColumnPk, queryRequest.getColumnPk())
                .eq(StringUtils.hasText(queryRequest.getColumnList()), GenTableColumnDO::getColumnList, queryRequest.getColumnList())
                .eq(StringUtils.hasText(queryRequest.getColumnOperation()), GenTableColumnDO::getColumnOperation, queryRequest.getColumnOperation())
                .eq(StringUtils.hasText(queryRequest.getColumnQuery()), GenTableColumnDO::getColumnQuery, queryRequest.getColumnQuery())
                .eq(StringUtils.hasText(queryRequest.getColumnRequired()), GenTableColumnDO::getColumnRequired, queryRequest.getColumnRequired())
                .eq(!ObjectUtils.isEmpty(queryRequest.getColumnSort()), GenTableColumnDO::getColumnSort, queryRequest.getColumnSort())
        ;
        return page(PageTool.getPage(queryRequest),wrapper);
    }
}
