package com.xht.cloud.generate.constant;

/**
 * 描述 ：代码生成器基本类型
 *
 * @author 小糊涂
 **/
public interface GenerateConstant {

    String PATH_SEPARATOR = "/";
    String POINT = ".";

    String SUCCESS_STATUS = "1";

    String ERROR_STATUS = "0";

    String[] BASE_COLUMN_NAME = new String[]{"id", "create_time", "create_by", "update_time", "update_by"};

    String[] BASE_COLUMN_BASE_DELETE_NAME = new String[]{"create_time", "create_by", "update_time", "update_by", "del_flag"};
    String[] BASE_COLUMN_BASE_DELETE_NAME_ = new String[]{"id","create_time", "create_by", "update_time", "update_by", "del_flag"};

    String[] BASE_COLUMN_DELETE_NAME = new String[]{"del_flag"};
}
