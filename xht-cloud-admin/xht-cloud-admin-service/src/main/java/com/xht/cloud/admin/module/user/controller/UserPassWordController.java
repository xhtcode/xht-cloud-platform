package com.xht.cloud.admin.module.user.controller;

import com.xht.cloud.admin.module.user.domain.PassWordBO;
import com.xht.cloud.framework.core.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.xht.cloud.framework.core.R.ok;

/**
 * 描述 ：用户密码
 *
 * @author : 小糊涂
 **/
@Slf4j
@Tag(name = "用户密码")
@RestController
@RequestMapping("/sys/user/pwd")
@RequiredArgsConstructor
public class UserPassWordController {

    /**
     * 用户修改密码
     *
     * @param updatePassWordBO 密码请求
     * @return true 修改成功
     */
    @Operation(summary = "用户修改密码")
    @PutMapping
    public R<Boolean> updatePassword(@Validated @RequestBody PassWordBO updatePassWordBO) {
        return ok();
    }


}
