package com.xht.cloud.framework.file.enums;


import com.xht.cloud.framework.exception.constant.IErrorStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：oss文件上传异常错误码
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum OssErrorStatusCode implements IErrorStatusCode {

    // @formatter:off
    BUSINESS_ERROR(400001, "OSS业务异常"),

    SYSTEM_ERROR(400002, "OSS服务异常"),

    BUCKET_EXIST(400003, "存储桶已存在"),

    BUCKET_NO_EXIST(400004, "存储桶不存在"),

    BUCKET_NAME_EMPTY(400005, "存储桶名称为空"),

    OBJECT_NAME_EMPTY(400006, "对象名称为空"),

    OBJECT_STREAM_ERROR(400007, "对象流异常"),

    SAVE_FILE_PATH_ERROR(400008, "保存路径不合法"),


    ;
    // @formatter:on
    private final Integer code;

    private final String message;

}
