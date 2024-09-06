package com.xht.cloud.generate.module.type.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.generate.module.type.domain.dataobject.GenColumnTypeDO;
import com.xht.cloud.generate.module.type.domain.request.GenColumnTypeQueryRequest;
import com.xht.cloud.generate.module.type.domain.request.GenColumnTypeUpdateRequest;
import com.xht.cloud.generate.module.type.mapper.GenColumnTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class GenColumnTypeDao extends BaseDaoImpl<GenColumnTypeMapper, GenColumnTypeDO> {

    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public IPage<GenColumnTypeDO> pageQueryRequest(GenColumnTypeQueryRequest queryRequest) {
        LambdaQueryWrapper<GenColumnTypeDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        lambdaQueryWrapper
                .eq(StringUtils.hasText(queryRequest.getDbLabel()), GenColumnTypeDO::getDbLabel, queryRequest.getDbLabel())
                .eq(StringUtils.hasText(queryRequest.getDbValue()), GenColumnTypeDO::getDbValue, queryRequest.getDbValue());
        // @formatter:on
        return page(PageTool.getPage(queryRequest), lambdaQueryWrapper);
    }

    /**
     * 扩展的修改的接口
     *
     * @param updateRequest 修改参数
     * @return 修改成功true
     */
    public boolean updateRequest(GenColumnTypeUpdateRequest updateRequest) {
        updateRequest.checkId();
        LambdaUpdateWrapper<GenColumnTypeDO> wrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        wrapper
                .set(GenColumnTypeDO::getDbLabel, updateRequest.getDbLabel())
                .set(GenColumnTypeDO::getDbValue, updateRequest.getDbValue())
                .set(GenColumnTypeDO::getLabel, updateRequest.getLabel())
                .set(GenColumnTypeDO::getValue, updateRequest.getValue())
                .eq(GenColumnTypeDO::getId, updateRequest.getId());
        // @formatter:on
        return update(wrapper);
    }
}
