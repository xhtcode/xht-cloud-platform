package com.xht.cloud.framework.redis;

import com.xht.cloud.framework.redis.exception.IdempotentException;
import com.xht.cloud.framework.redis.exception.RequestLimitException;
import com.xht.cloud.framework.core.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述 ：oss异常处理,如果要使用则继承它，注入到spring容器中。
 *
 * @author 小糊涂
 * @see IdempotentException
 * @see RequestLimitException
 **/
@Slf4j
@RestControllerAdvice
public class RedisExceptionHandler implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 捕获 {@code FileException} 异常
     */
    @ExceptionHandler(value = IdempotentException.class)
    public R<?> handle(IdempotentException e) {
        log.error("幂等异常: code=`{}` message=`{}`", e.getCode(), e.getLocalizedMessage(), e);
        return R.failed(e.getMessage()).setCode(e.getCode());
    }

    /**
     * 捕获 {@code OssException} 异常
     */
    @ExceptionHandler(value = RequestLimitException.class)
    public R<?> handle(RequestLimitException e) {
        log.error("接口限流异常: code=`{}` message=`{}`", e.getCode(), e.getLocalizedMessage(), e);
        return R.failed(e.getMessage()).setCode(e.getCode());
    }
}
