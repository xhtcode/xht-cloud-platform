package com.xht.cloud.framework.file;

import com.xht.cloud.framework.file.exception.FileException;
import com.xht.cloud.framework.file.exception.OssException;
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
 * @see OssException
 **/
@Slf4j
@RestControllerAdvice
public class FileExceptionHandler implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 捕获 {@code FileException} 异常
     */
    @ExceptionHandler(value = FileException.class)
    public R<?> handle(FileException e) {
        log.error("文件异常: code=`{}` message=`{}`", e.getCode(), e.getLocalizedMessage(), e);
        return R.failed(e.getMessage()).setCode(e.getCode());
    }

    /**
     * 捕获 {@code OssException} 异常
     */
    @ExceptionHandler(value = OssException.class)
    public R<?> handle(OssException e) {
        log.error("OSS异常: code=`{}` message=`{}`", e.getCode(), e.getLocalizedMessage(), e);
        return R.failed(e.getMessage()).setCode(e.getCode());
    }
}
