package com.xht.cloud.framework.redis.idempotent.core.token;

import com.xht.cloud.framework.core.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 描述 ：基于 Token 验证请求幂等性控制器
 *
 * @author 小糊涂
 **/
@Tag(name = "幂等性控制器")
@RestController
@RequiredArgsConstructor
public class IdempotentTokenController {

    private final IdempotentTokenService idempotentTokenService;

    /**
     * 请求申请Token
     */
    @Operation(summary = "请求申请Token")
    @GetMapping("/idempotent/token")
    public R<String> createToken() {
        return R.ok(idempotentTokenService.createToken());
    }
}
