package com.xht.cloud.admin.module.dept.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysPositionDO;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionQueryRequest;
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
        wrapper
                .eq(StringUtils.hasText(queryRequest.getParentId()), SysPositionDO::getParentId, queryRequest.getParentId())
                .eq(StringUtils.hasText(queryRequest.getDeptId()), SysPositionDO::getDeptId, queryRequest.getDeptId())
                .eq(StringUtils.hasText(queryRequest.getPositionCode()), SysPositionDO::getPositionCode, queryRequest.getPositionCode())
                .eq(StringUtils.hasText(queryRequest.getPositionName()), SysPositionDO::getPositionName, queryRequest.getPositionName())
                .eq(!ObjectUtils.isEmpty(queryRequest.getStatus()), SysPositionDO::getStatus, queryRequest.getStatus());
        return page(PageTool.getPage(queryRequest), wrapper);

    }
}
