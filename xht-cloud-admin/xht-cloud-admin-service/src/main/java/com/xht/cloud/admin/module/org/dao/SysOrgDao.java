package com.xht.cloud.admin.module.org.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.org.domain.dataobject.SysOrgDO;
import com.xht.cloud.admin.module.org.domain.request.SysOrgUpdateRequest;
import com.xht.cloud.admin.module.org.mapper.SysOrgMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.SqlHelper;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 组织机构 DAO
 *
 * @author 小糊涂
 */
@Slf4j
@Repository
public class SysOrgDao extends BaseDaoImpl<SysOrgMapper, SysOrgDO> {

    /**
     * 判断上级部门存在不存在
     *
     * @param parentId 上级部门id
     * @return 存在true
     */
    public boolean existsOrgParentId(Long parentId) {
        if (Objects.equals(-1L, parentId)) return true;
        return SqlHelper.exist(count(lambdaQuery().eq(SysOrgDO::getId, parentId).eq(SysOrgDO::getOrgStatus, "1")));
    }

    /**
     * 判断上部门code存在不存在
     *
     * @param orgCode 部门code
     * @return 存在true
     */
    public boolean existsOrgCod(String orgCode) {
        if (StringUtils.isEmpty(orgCode)) return true;
        return SqlHelper.exist(selectCount(SysOrgDO::getOrgCode, orgCode));
    }

    /**
     * 扩展的修改的接口
     *
     * @param updateRequest 修改参数
     * @return 修改成功true
     */
    public boolean updateRequest(SysOrgUpdateRequest updateRequest) {
        updateRequest.checkId();
        LambdaUpdateWrapper<SysOrgDO> wrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        wrapper
                .set(Objects.nonNull(updateRequest.getDirectorId()), SysOrgDO::getDirectorId, updateRequest.getDirectorId())
                .set(Objects.nonNull(updateRequest.getParentId()), SysOrgDO::getParentId, updateRequest.getParentId())
                .set(Objects.nonNull(updateRequest.getParentName()), SysOrgDO::getParentName, updateRequest.getParentName())
                .set(Objects.nonNull(updateRequest.getOrgName()), SysOrgDO::getOrgName, updateRequest.getOrgName())
                .set(Objects.nonNull(updateRequest.getOrgCode()), SysOrgDO::getOrgCode, updateRequest.getOrgCode())
                .set(Objects.nonNull(updateRequest.getOrgType()), SysOrgDO::getOrgType, updateRequest.getOrgType())
                .set(Objects.nonNull(updateRequest.getOrgStatus()), SysOrgDO::getOrgStatus, updateRequest.getOrgStatus())
                .set(Objects.nonNull(updateRequest.getOrgSort()), SysOrgDO::getOrgSort, updateRequest.getOrgSort())
                .set(Objects.nonNull(updateRequest.getOrgPath()), SysOrgDO::getOrgPath, updateRequest.getOrgPath())
                .set(Objects.nonNull(updateRequest.getOrgDesc()), SysOrgDO::getOrgDesc, updateRequest.getOrgDesc())
                .set(Objects.nonNull(updateRequest.getOrgPhone()), SysOrgDO::getOrgPhone, updateRequest.getOrgPhone())
                .set(Objects.nonNull(updateRequest.getOrgEmail()), SysOrgDO::getOrgEmail, updateRequest.getOrgEmail())
                .eq(SysOrgDO::getId, updateRequest.getId());
        // @formatter:on
        return update(wrapper);
    }
}
