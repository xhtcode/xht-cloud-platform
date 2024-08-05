package com.xht.cloud.framework.file.utils;

import java.text.DecimalFormat;

/**
 * 描述 ：文件大小格式化
 *
 * @author 小糊涂
 **/
@SuppressWarnings("unused")
public final class FileSizeFormat {
    public static final DecimalFormat df = new DecimalFormat("#.00");

    public static final String ZERO_B = "0B";

    public static final String B = "B";

    public static final String KB = "KB";

    public static final String MB = "MB";

    public static final String GB = "GB";


    /**
     * description: 格式化文件大小
     *
     * @param fileS 文件的字节长度
     */
    public static String formatFileSize(long fileS) {
        if (fileS == 0) return ZERO_B;
        if (fileS < 1024) {
            return String.format("%s B", df.format((double) fileS));
        } else if (fileS < 1048576) {
            return String.format("%s KB", df.format((double) fileS / 1204));
        } else if (fileS < 1073741824) {
            return String.format("%s MB", df.format((double) fileS / 1048576));
        } else {
            return String.format("%s GB", df.format((double) fileS / 1073741824));
        }
    }
}
