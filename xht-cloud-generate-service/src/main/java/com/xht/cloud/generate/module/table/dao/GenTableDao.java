package com.xht.cloud.generate.module.table.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.generate.module.table.domain.dataobject.GenTableDO;
import com.xht.cloud.generate.module.table.domain.request.GenTableQueryRequest;
import com.xht.cloud.generate.module.table.domain.request.GenTableUpdateRequest;
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
        // @formatter:off
        LambdaQueryWrapper<GenTableDO> wrapper = new LambdaQueryWrapper<>();
         wrapper
                .eq(Objects.nonNull(queryRequest.getConfigId()), GenTableDO::getConfigId, queryRequest.getConfigId())
                .like(StringUtils.hasText(queryRequest.getTableName()), GenTableDO::getTableName, queryRequest.getTableName());
        // @formatter:on
        return page(PageTool.getPage(queryRequest), wrapper);
    }

    /**
     * 扩展的修改的接口
     *
     * @param updateRequest 修改参数
     * @return 修改成功true
     */
    public boolean updateRequest(GenTableUpdateRequest updateRequest) {
        LambdaUpdateWrapper<GenTableDO> wrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        wrapper
                .set(GenTableDO::getConfigId, updateRequest.getConfigId())
                .set(GenTableDO::getGenDbId, updateRequest.getGenDbId())
                .set(GenTableDO::getTableSchema, updateRequest.getTableSchema())
                .set(GenTableDO::getTableEngine, updateRequest.getTableEngine())
                .set(GenTableDO::getTableName, updateRequest.getTableName())
                .set(GenTableDO::getModuleName, updateRequest.getModuleName())
                .set(GenTableDO::getServiceName, updateRequest.getServiceName())
                .set(GenTableDO::getServiceDesc, updateRequest.getServiceDesc())
                .set(GenTableDO::getAuthorizationPrefix, updateRequest.getAuthorizationPrefix())
                .set(GenTableDO::getPathUrl, updateRequest.getPathUrl())
                .set(GenTableDO::getCodeName, updateRequest.getCodeName())
                .set(GenTableDO::getTableCreateTime, updateRequest.getTableCreateTime())
                .set(GenTableDO::getTableUpdateTime, updateRequest.getTableUpdateTime())
                .eq(GenTableDO::getId, updateRequest.getId());
        // @formatter:on
        return update(wrapper);
    }
}
