package com.xht.cloud.framework.security.domain;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.security.constant.SecurityConstant;
import com.xht.cloud.framework.security.resource.captcha.ICaptchaService;
import com.xht.cloud.framework.utils.spring.SpringContextUtil;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 描述 ：用户信息
 *
 * @author 小糊涂
 **/
@Slf4j
@Getter
@SuppressWarnings("all")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUserBO {
    /**
     * 用户名
     */
    private final String username;

    /**
     * 密码
     */
    private final String password;

    /**
     * 请求的`验证码 uuid`name值
     */
    private final String uuid;

    /**
     * 请求的`验证码 信息`name值
     */
    private final String captcha;

    /**
     * 登录类型
     */
    private final UserTypeEnums userType;

    /**
     * 请求范围
     */
    private final Set<String> scopes;

    /**
     * 根据请求信息构建 用户
     *
     * @param additionalParameters 扩展信息 数据来源{@code  com.xht.cloud.framework.security.authorization.granttype.AbstractAuthenticationConverter}
     * @return {@link RequestUserBO}
     */
    public static RequestUserBO builderUser(Map<String, Object> additionalParameters) throws AuthenticationException {
        String uuid = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_CAPTCHA_UUID);
        String captcha = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_CAPTCHA_CODE);
        String username = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_USERNAME);
        String password = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_PASSWORD);
        Integer userType = MapUtil.getInt(additionalParameters, SecurityConstant.REQUEST_USER_TYPE);
        String scopeStr = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_SCOPE);
        Set<String> scopes = new HashSet<>();
        if (StringUtils.hasText(scopeStr)) {
            scopes.addAll(StrUtil.split(scopeStr, SecurityConstant.REQUEST_SCOPE_SPLIT));
        }
        UserTypeEnums userTypeEnums = UserTypeEnums.of(userType);
        log.info("账号：{},密码：{},UUID：{},验证码：{},用户类型：{},授权范围：{}", username, password, uuid, captcha, userTypeEnums, scopes);
        return new RequestUserBO(username, password, uuid, captcha, userTypeEnums, scopes);
    }

    /**
     * 校验授权范围
     *
     * @param clientScopes 客户端的授权范围
     */
    public void checkScopes(Set<String> clientScopes) {
        if (CollectionUtils.isEmpty(clientScopes)) {
            throw new BizException("registered client scope empty!");
        }
        Collection<String> intersection = CollectionUtil.intersection(clientScopes, this.scopes);
        if (CollectionUtils.isEmpty(intersection)) {
            throw new BizException("scope not exits");
        }
    }

    /***
     * 判断是否存在某个授权范围
     * @param scope 授权范围
     * @return 存在 true 反之false
     */
    public boolean hashScope(String scope) {
        Assert.hasText(scope, "scope is empty!");
        return CollectionUtil.contains(scopes, scope);
    }

    /**
     * 校验账号
     */
    public RequestUserBO checkUserName() {
        Assert.hasText(username, "【账号】不能为空");
        return this;
    }

    /**
     * 校验密码
     */
    public RequestUserBO checkPassword() {
        Assert.hasText(password, "【密码】不能为空");
        return this;
    }

    /**
     * 校验uuid
     */
    public RequestUserBO checkUuid() {
        Assert.hasText(uuid, "【uuid】不能为空");
        return this;
    }

    /**
     * 校验用户类型
     */
    public RequestUserBO checkUserType() {
        Assert.notNull(userType, "【userType】不能为空");
        return this;
    }

    /**
     * 校验验证码
     */
    public void checkCaptcha() {
        Assert.hasText(captcha, "【验证码】不能为空");
        ICaptchaService captchaService = SpringContextUtil.getBean(ICaptchaService.class);
        if (Objects.isNull(captchaService)) {
            throw new RuntimeException("未配置验证码服务！");
        } else {
            captchaService.checkCaptcha(this.uuid, this.captcha);
        }
    }

    /**
     * 清理验证码
     */
    public void clearCaptcha() {
        ICaptchaService captchaService = SpringContextUtil.getBean(ICaptchaService.class);
        if (Objects.isNull(captchaService)) {
            throw new RuntimeException("未配置验证码服务！");
        } else {
            captchaService.clearCaptcha(this.uuid);
        }
    }
}
