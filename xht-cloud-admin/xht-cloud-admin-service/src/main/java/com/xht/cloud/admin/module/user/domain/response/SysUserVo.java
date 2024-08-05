package com.xht.cloud.admin.module.user.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.cloud.admin.api.user.dto.SysUserResDTO;
import com.xht.cloud.admin.module.dept.domain.response.SysDeptResponse;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
public class SysUserVo {

    private SysUserResponse sysUser;

    private SysUserProfileResponse profile;

    /**
     * 角色编码
     */
    private Set<String> roleCode;

    /**
     * 数据权限 {@link com.xht.cloud.framework.mybatis.enums.DeptUserDataScopeEnum}
     */
    private Integer dataScope;

    /**
     * 菜单权限或者其他权限
     */
    private Set<String> authorities;

    /**
     * 部门信息
     */
    private SysDeptResponse dept;

    @JsonIgnore
    public SysUserResDTO toDTO() {
        if (Objects.isNull(sysUser)) {
            return null;
        }
        SysUserResDTO result = new SysUserResDTO();
        result.setUserId(sysUser.getId());
        result.setUserAccount(sysUser.getUserAccount());
        result.setUserName(sysUser.getUserName());
        result.setPassWord(sysUser.getPassWord());
        result.setSuperAdmin(sysUser.getAdminStatus());
        result.setDataScope(this.dataScope);
        result.setUserStatus(sysUser.getUserStatus());
        if (Objects.nonNull(profile)){
            result.setMobile(profile.getPhoneNumber());
        }
        if (Objects.nonNull(dept)){
            result.setDeptId(dept.getId());
        }
        result.setMenuCode(authorities);
        result.setRoleCode(roleCode);
        result.setSourceName("");
        return result;
    }

}
