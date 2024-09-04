package com.xht.cloud.framework.security.resource.captcha;

import com.xht.cloud.framework.domain.response.AbstractResponse;
import lombok.Data;

/**
 * 描述 ：验证码返回
 *
 * @author 小糊涂
 **/
@Data
public class CaptchaResult extends AbstractResponse {

    private String uuid;

    private String img;

    long timeout;

}
