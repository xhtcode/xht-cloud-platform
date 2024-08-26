package com.xht.cloud.generate.constant;

/**
 * 描述 ：代码生成器基本类型
 *
 * @author 小糊涂
 **/
public interface GenerateConstant {

    String ROOT_FOLDER = "-1";

    String TEMPLATE_SUFFIX = ".vm";

    String PATH_SEPARATOR = "/";
    String POINT = ".";

    String SUCCESS_STATUS = "1";

    String ERROR_STATUS = "0";

    String[] BASE_COLUMN_BASE_DELETE_NAME_ = new String[]{"id","create_time", "create_by", "update_time", "update_by", "del_flag"};

    String[] GENERATE_PATH_JAVA = new String[]{"/src/main/java/", "/src/main/resources/", "/src/test/java/", "/src/test/resources/"};
    String[] GENERATE_PATH_VUE = new String[]{"/src/api", "/src/views"};
    String[] GENERATE_PATH_OTHER = new String[]{"/sql"};

}
