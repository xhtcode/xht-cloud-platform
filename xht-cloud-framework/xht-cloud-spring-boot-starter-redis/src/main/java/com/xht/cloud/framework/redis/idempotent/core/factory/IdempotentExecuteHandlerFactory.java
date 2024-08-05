package com.xht.cloud.framework.redis.idempotent.core.factory;

import com.xht.cloud.framework.redis.idempotent.core.IdempotentExecuteHandler;
import com.xht.cloud.framework.redis.idempotent.core.param.IdempotentParamService;
import com.xht.cloud.framework.redis.idempotent.core.spel.IdempotentSpELService;
import com.xht.cloud.framework.redis.idempotent.core.token.IdempotentTokenService;
import com.xht.cloud.framework.redis.idempotent.enums.IdempotentTypeEnum;
import com.xht.cloud.framework.utils.spring.SpringContextUtil;

/**
 * 描述 ：幂等执行处理器工厂
 *
 * @author 小糊涂
 **/
public final class IdempotentExecuteHandlerFactory {

    /**
     * 获取幂等执行处理器
     *
     * @param type 指定幂等处理类型
     * @return 幂等执行处理器
     */
    public static IdempotentExecuteHandler getInstance(IdempotentTypeEnum type) {
        IdempotentExecuteHandler result = null;
        switch (type) {
            case PARAM:
                result = SpringContextUtil.getBean(IdempotentParamService.class);
                break;
            case TOKEN:
                result = SpringContextUtil.getBean(IdempotentTokenService.class);
                break;
            case SPEL:
                result = SpringContextUtil.getBean(IdempotentSpELService.class);
                break;
            default:
                break;
        }
        return result;
    }
}
