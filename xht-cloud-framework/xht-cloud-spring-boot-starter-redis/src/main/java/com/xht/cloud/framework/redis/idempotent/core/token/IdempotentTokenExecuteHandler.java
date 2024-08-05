package com.xht.cloud.framework.redis.idempotent.core.token;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.cloud.framework.redis.exception.IdempotentException;
import com.xht.cloud.framework.redis.idempotent.IdempotentProperties;
import com.xht.cloud.framework.redis.idempotent.core.AbstractIdempotentTemplate;
import com.xht.cloud.framework.redis.idempotent.core.domain.IdempotentParamWrapper;
import com.xht.cloud.framework.redis.key.RedisKeyTool;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.TimeUnit;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public class IdempotentTokenExecuteHandler extends AbstractIdempotentTemplate implements IdempotentTokenService {

    private static final String TOKEN_KEY = "idempotent-token";
    private final RedisTemplate<String, Object> redisTemplate;

    public IdempotentTokenExecuteHandler(IdempotentProperties idempotentProperties, RedisTemplate<String, Object> redisTemplate) {
        super(idempotentProperties);
        this.redisTemplate = redisTemplate;
    }

    /**
     * 幂等处理逻辑
     *
     * @param wrapper 幂等参数包装器
     */
    @Override
    public void handler(IdempotentParamWrapper wrapper) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String token = request.getHeader(TOKEN_KEY);
        if (StrUtil.isBlank(token)) {
            token = request.getParameter(TOKEN_KEY);
            if (StrUtil.isBlank(token)) {
                throw new IdempotentException("幂等Token为空");
            }
        }
        Boolean tokenDelFlag = redisTemplate.delete(token);
        if (Boolean.FALSE.equals(tokenDelFlag)) {
            String errMsg = StrUtil.isNotBlank(wrapper.getIdempotent().message())
                    ? wrapper.getIdempotent().message()
                    : "幂等Token已被使用或失效";
            throw new IdempotentException(errMsg);
        }
    }

    /**
     * 创建幂等验证Token
     */
    @Override
    public String createToken() {
        String token = RedisKeyTool.createKey(idempotentProperties.getPrefix(), IdUtil.simpleUUID());
        redisTemplate.opsForValue().set(token, "", idempotentProperties.getTimeout(), TimeUnit.MILLISECONDS);
        return token;
    }
}
