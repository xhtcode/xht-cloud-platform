package com.xht.cloud.framework.mybatis.handler;

import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@SuppressWarnings("all")
@RestControllerAdvice
public class SqlExceptionHandler {

    /**
     * 捕获 {@link SQLException} 异常
     */
    @ExceptionHandler(value = SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> handle(SQLException e) {
        log.error("sql 异常: {}", e.getMessage(), e);
        return R.<String>failed(GlobalErrorStatusCode.INTERNAL_SERVER_ERROR);
    }
}
