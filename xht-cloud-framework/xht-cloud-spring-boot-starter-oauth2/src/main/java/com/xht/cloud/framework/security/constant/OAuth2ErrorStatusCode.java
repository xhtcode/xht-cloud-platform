package com.xht.cloud.framework.security.constant;

import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;

/**
 * 描述 ：oauth2 异常常量
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum OAuth2ErrorStatusCode implements IEnum<String> {


    /**
     * 用户构建失败
     */
    USER_BUILDER(OAuth2ErrorCodes.INVALID_REQUEST, "用户构建失败"),


    ;

    private final String code;

    private final String message;

    /**
     * @return 字典值
     */
    @Override
    public String getValue() {
        return this.code;
    }
}
