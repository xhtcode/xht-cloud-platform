package com.xht.cloud.framework.file.utils;

import org.apache.tika.Tika;

/**
 * 描述 ： TikaUtils
 *
 * @author 小糊涂
 **/
public class TikaUtils {

    private final static Tika TIKA = new Tika();


    public static String getContentType(byte[] fileData) {
        return TIKA.detect(fileData);
    }
}
