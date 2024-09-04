package com.xht.cloud.auth.controller;

import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.security.resource.captcha.CaptchaResult;
import com.xht.cloud.framework.security.resource.captcha.ICaptchaService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 ：验证码 获取
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@Slf4j
@Tag(name = "验证码")
@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private final ICaptchaService validateCodeService;

    @Schema(description = "获取验证码")
    @GetMapping("/code")
    public R<CaptchaResult> captchaController() throws Exception {
        return R.ok(validateCodeService.createCaptcha());
    }

}
