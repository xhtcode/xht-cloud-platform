package com.xht.cloud.admin.module.dept.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysDeptDO;
import com.xht.cloud.admin.module.dept.domain.request.SysDeptUpdateRequest;
import com.xht.cloud.admin.module.dept.mapper.SysDeptMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述 ：部门
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysDeptDao extends BaseDaoImpl<SysDeptMapper, SysDeptDO> {

    /**
     * 判断是否有下级部门
     *
     * @param deptIds 部门id
     */
    public boolean existsChild(List<String> deptIds) {
        return exists(lambdaQuery().in(SysDeptDO::getParentId, deptIds));
    }

    /**
     * 扩展的修改的接口
     *
     * @param updateRequest 修改参数
     * @return 修改成功true
     */
    public boolean updateRequest(SysDeptUpdateRequest updateRequest) {
        updateRequest.checkId();
        LambdaUpdateWrapper<SysDeptDO> wrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        wrapper
                .set(SysDeptDO::getParentId, updateRequest.getParentId())
                .set(SysDeptDO::getDirectorId, updateRequest.getDirectorId())
                .set(SysDeptDO::getDeptName, updateRequest.getDeptName())
                .set(SysDeptDO::getDeptCode, updateRequest.getDeptCode())
                .set(SysDeptDO::getDeptLeader, updateRequest.getDeptLeader())
                .set(SysDeptDO::getDeptTel, updateRequest.getDeptTel())
                .set(SysDeptDO::getDeptSort, updateRequest.getDeptSort())
                .set(SysDeptDO::getDeptStatus, updateRequest.getDeptStatus())
                .set(SysDeptDO::getDescription, updateRequest.getDescription())
                .eq(SysDeptDO::getId, updateRequest.getId());
        // @formatter:on
        return update(wrapper);
    }
}
