package com.xht.cloud.generate.module.table.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.generate.module.table.domain.dataobject.GenTableDO;
import com.xht.cloud.generate.module.table.domain.request.GenTableQueryRequest;
import com.xht.cloud.generate.module.table.mapper.GenTableMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class GenTableDao extends BaseDaoImpl<GenTableMapper, GenTableDO> {
    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public IPage<GenTableDO> pageQueryRequest(GenTableQueryRequest queryRequest) {
        LambdaQueryWrapper<GenTableDO> wrapper = new LambdaQueryWrapper<>();
         wrapper
                .eq(Objects.nonNull(queryRequest.getConfigId()), GenTableDO::getConfigId, queryRequest.getConfigId())
                .like(StringUtils.hasText(queryRequest.getTableName()), GenTableDO::getTableName, queryRequest.getTableName());
        return page(PageTool.getPage(queryRequest), wrapper);
    }
}
