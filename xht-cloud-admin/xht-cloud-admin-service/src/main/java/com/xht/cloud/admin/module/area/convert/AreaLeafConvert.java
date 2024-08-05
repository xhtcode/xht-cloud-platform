package com.xht.cloud.admin.module.area.convert;

import java.util.Objects;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public class AreaLeafConvert {

    public static Boolean convert(String source) {
        return Objects.equals("0", source);
    }
}
