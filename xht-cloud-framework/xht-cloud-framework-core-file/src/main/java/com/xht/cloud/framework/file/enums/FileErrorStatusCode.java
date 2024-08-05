package com.xht.cloud.framework.file.enums;

import com.xht.cloud.framework.exception.constant.IErrorStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：
 * 描述 ：文件异常错误码
 * <h3> 7001到 71999 段</h3>
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum FileErrorStatusCode implements IErrorStatusCode {

    FILE_ERROR(200001, "未知错误"),
    FILE_NOT_EXISTS(200002, "文件不存在"),
    
    ;
    // @formatter:on
    private final Integer code;

    private final String message;
}
