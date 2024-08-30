package com.xht.cloud.admin.constant;


import com.xht.cloud.framework.core.constant.StringConstant;

/**
 * 描述 ：菜单常量
 *
 * @author 小糊涂
 * @version : 1.0
 **/
public interface MenuConstant {

    /**
     * 菜单顶级id
     */
    String TREE_DEFAULT = "-1";
    /**
     * 菜单状态 总
     */
    String[] STATUS = new String[]{"0", "1"};


    String STATUS_ERROR = "0";

    String STATUS_SUCCESS = "1";


    String DELIMITER = StringConstant.DEFAULT_DELIMITER;


    /**
     * Layout组件标识
     */
    String VUE_LAYOUT = "Layout";
    String VUE_LAYOUT_PATH = "layout/index";

    /**
     * ParentView组件标识 多级目录专用
     */
    String VUE_NESTED_VIEW = "nested";

    String VUE_NESTED_VIEW_PATH = "layout/nested";


}
