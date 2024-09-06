package com.xht.cloud.admin.module.dept.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysPositionDO;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionQueryRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionUpdateRequest;
import com.xht.cloud.admin.module.dept.mapper.SysPositionMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
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
public class SysPositionDao extends BaseDaoImpl<SysPositionMapper, SysPositionDO> {

    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public IPage<SysPositionDO> pageQueryRequest(SysPositionQueryRequest queryRequest) {
        LambdaQueryWrapper<SysPositionDO> wrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        wrapper
                .eq(StringUtils.hasText(queryRequest.getParentId()), SysPositionDO::getParentId, queryRequest.getParentId())
                .eq(StringUtils.hasText(queryRequest.getDeptId()), SysPositionDO::getDeptId, queryRequest.getDeptId())
                .eq(StringUtils.hasText(queryRequest.getPositionCode()), SysPositionDO::getPositionCode, queryRequest.getPositionCode())
                .eq(StringUtils.hasText(queryRequest.getPositionName()), SysPositionDO::getPositionName, queryRequest.getPositionName())
                .eq(!ObjectUtils.isEmpty(queryRequest.getStatus()), SysPositionDO::getStatus, queryRequest.getStatus());
        // @formatter:on
        return page(PageTool.getPage(queryRequest), wrapper);
    }

    /**
     * 扩展的修改的接口
     *
     * @param updateRequest 修改参数
     * @return 修改成功true
     */
    public boolean updateRequest(SysPositionUpdateRequest updateRequest) {
        updateRequest.checkId();
        LambdaUpdateWrapper<SysPositionDO> wrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        wrapper
                .set(SysPositionDO::getParentId, updateRequest.getParentId())
                .set(SysPositionDO::getDeptId, updateRequest.getDeptId())
                .set(SysPositionDO::getPositionCode, updateRequest.getPositionCode())
                .set(SysPositionDO::getPositionName, updateRequest.getPositionName())
                .set(SysPositionDO::getSort, updateRequest.getSort())
                .set(SysPositionDO::getStatus, updateRequest.getStatus())
                .set(SysPositionDO::getDescription, updateRequest.getDescription())
                .eq(SysPositionDO::getId, updateRequest.getId());
        // @formatter:on
        return update(wrapper);
    }
}
