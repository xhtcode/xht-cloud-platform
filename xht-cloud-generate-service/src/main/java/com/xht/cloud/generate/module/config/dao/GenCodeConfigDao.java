package com.xht.cloud.generate.module.config.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.generate.module.config.domain.dataobject.GenCodeConfigDO;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigQueryRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigUpdateRequest;
import com.xht.cloud.generate.module.config.mapper.GenCodeConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class GenCodeConfigDao extends BaseDaoImpl<GenCodeConfigMapper, GenCodeConfigDO> {

    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public IPage<GenCodeConfigDO> pageQueryRequest(GenCodeConfigQueryRequest queryRequest) {
        LambdaQueryWrapper<GenCodeConfigDO> wrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        wrapper
                .eq(StringUtils.hasText(queryRequest.getConfigName()), GenCodeConfigDO::getConfigName, queryRequest.getConfigName())
                .eq(StringUtils.hasText(queryRequest.getConfigDesc()), GenCodeConfigDO::getConfigDesc, queryRequest.getConfigDesc())
                .eq(!ObjectUtils.isEmpty(queryRequest.getConfigSort()), GenCodeConfigDO::getConfigSort, queryRequest.getConfigSort())
        ;
        // @formatter:on
        return page(PageTool.getPage(queryRequest),wrapper);
    }

    /**
     * 扩展的修改的接口
     *
     * @param updateRequest 修改参数
     * @return 修改成功true
     */
    public boolean updateRequest(GenCodeConfigUpdateRequest updateRequest) {
        updateRequest.checkId();
        LambdaUpdateWrapper<GenCodeConfigDO> wrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        wrapper
                .set(GenCodeConfigDO::getConfigName, updateRequest.getConfigName())
                .set(GenCodeConfigDO::getConfigDesc, updateRequest.getConfigDesc())
                .set(GenCodeConfigDO::getConfigSort, updateRequest.getConfigSort())
                .eq(GenCodeConfigDO::getId, updateRequest.getId());
        // @formatter:on
        return update(wrapper);
    }
}
