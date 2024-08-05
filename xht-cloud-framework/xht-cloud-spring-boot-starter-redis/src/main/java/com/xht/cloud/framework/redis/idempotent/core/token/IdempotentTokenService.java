package com.xht.cloud.framework.redis.idempotent.core.token;

import com.xht.cloud.framework.redis.idempotent.core.IdempotentExecuteHandler;

/**
 * 描述 ：Token 实现幂等接口
 *
 * @author 小糊涂
 **/
public interface IdempotentTokenService extends IdempotentExecuteHandler {

    /**
     * 创建幂等验证Token
     */
    String createToken();
}
