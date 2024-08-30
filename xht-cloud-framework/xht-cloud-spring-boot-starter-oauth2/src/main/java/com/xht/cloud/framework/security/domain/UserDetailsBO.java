package com.xht.cloud.framework.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xht.cloud.admin.api.user.enums.DeptUserDataScopeEnum;
import com.xht.cloud.admin.api.user.enums.SuperAdminUserEnums;
import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.framework.core.domain.Identifier;
import com.xht.cloud.framework.security.constant.SecurityConstant;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * 描述 ： 自定义 用户详细信息
 *
 * @author 小糊涂
 **/
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public class UserDetailsBO extends Identifier<String> implements UserDetails, OAuth2AuthenticatedPrincipal, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    private String nickName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户类型
     */
    private UserTypeEnums userType;


    /**
     * 密码.
     * 1、变量被transient修饰，变量将不会被序列化
     * 2、transient关键字只能修饰变量，而不能修饰方法和类。
     * 3、被static关键字修饰的变量不参与序列化，一个静态static变量不管是否被transient修饰，均不能被序列化。
     * 4、final变量值参与序列化，final transient同时修饰变量，final不会影响transient，一样不会参与序列化
     */
    private transient String password;

    /**
     * 超级管理员标识 0否 1是.
     */
    private SuperAdminUserEnums superAdmin;

    /**
     * 数据权限级别
     */
    private DeptUserDataScopeEnum dataScope;

    /**
     * 用户状态 0启用 1禁用.
     */
    private UserStatusEnums userStatus;

    /**
     * 手机号.
     */
    private String mobile;

    /**
     * 部门ID.
     */
    private String deptId;

    /**
     * 菜单权限标识集合.
     */
    private Set<String> menuCode;

    /**
     * 角色Code
     */
    private Set<String> roleCode;

    /**
     * 数据源名称.
     */
    private String sourceName;


    /**
     * 获取OAuth 2.0令牌属性
     *
     * @return OAuth 2.0令牌属性
     */
    @Override
    @JsonIgnore
    public Map<String, Object> getAttributes() {
        return Collections.emptyMap();
    }

    /**
     * 返回授予用户的权限。不能返回<code>null</code>。
     *
     * @return 权限，按自然键排序
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> result = new ArrayList<>();
        // 获取资源
        if (!CollectionUtils.isEmpty(this.menuCode)) {
            for (String item : this.menuCode) {
                result.add(new SimpleGrantedAuthority(item));
            }
        }
        // 获取角色
        if (!CollectionUtils.isEmpty(this.roleCode)) {
            for (String item : this.roleCode) {
                result.add(new SimpleGrantedAuthority(String.format("%s%s", SecurityConstant.ROLE_PREFIX, item)));
            }
        }
        return result;
    }


    /**
     * 用户帐号是否过期。过期的帐户不能使用身份验证。
     *
     * @return <code>true</code>如果用户的帐户是有效的(即未过期)，
     * <code>false</code>如果不再有效(即过期)
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户处于锁定状态或未锁定状态。被锁定的用户不能进行操作身份验证。
     *
     * @return <code>true</code>如果用户未被锁定，<code>false</code>否则
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示用户的凭据(密码)是否已过期。过期的凭据(密码)阻止身份验证。
     *
     * @return <code>true</code>如果用户的凭证是有效的(即未过期)，
     * <code>false</code>如果不再有效(即过期)
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 显示用户是否启用或禁用。已禁用的用户不能身份验证。
     *
     * @return <code>true</code> 用户已启用, <code>false</code> 用户已禁用
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    /**
     * 判断是否是管理员
     *
     * @return true 是
     */
    @JsonIgnore
    public boolean isAdmin() {
        return Objects.equals(SuperAdminUserEnums.YES, this.superAdmin);
    }

    /**
     * @return 已验证<code>主体</code>的名称
     */
    @Override
    @JsonIgnore
    public String getName() {
        return this.username;
    }
}
