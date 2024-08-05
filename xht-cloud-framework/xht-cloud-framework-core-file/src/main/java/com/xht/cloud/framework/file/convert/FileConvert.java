package com.xht.cloud.framework.file.convert;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 描述 ：文件转换类
 *
 * @author 小糊涂
 **/
@SuppressWarnings("unused")
public final class FileConvert {

    /**
     * 文件名称
     *
     * @param prefix 文件前缀
     * @param suffix 文件后缀
     * @return 文件名称
     */
    public static String getFileName(String prefix, String suffix) {
        return String.format("%s.%s", prefix, suffix);
    }


    /**
     * 获取base64字符串
     *
     * @param file {@link File} 文件对象
     * @return base64字符串
     */
    public static String getBase64(File file) {
        return Base64.encode(file);
    }

    /**
     * 获取base64字符串
     *
     * @param bytes {@link Byte} 文件的字节对象
     * @return base64字符串
     */
    public static String getBase64(byte[] bytes) {
        return Base64.encode(bytes);
    }

    /**
     * 获取base64字符串
     *
     * @param inputStream {@link InputStream} 文件输入流
     * @return base64字符串
     */
    public static String getBase64(InputStream inputStream) {
        return Base64.encode(inputStream);
    }

    /**
     * 获取文件字节
     *
     * @param file {@link File} 文件对象
     * @return 文件字节
     */
    public static byte[] getByte(File file) {
        return FileUtil.readBytes(file);
    }

    /**
     * 获取文件字节
     *
     * @param base64 base64字符串
     * @return 文件字节
     */
    public static byte[] getByte(String base64) {
        return Base64.decode(base64);
    }

    /**
     * 获取文件字节
     *
     * @param inputStream {@link InputStream} 文件输入流
     * @return 文件字节
     */
    public static byte[] getByte(InputStream inputStream) {
        return IoUtil.readBytes(inputStream);
    }

    /**
     * @param file {@link File} 文件对象
     * @return {@link InputStream} 文件输入流
     */
    public static InputStream getInputStream(File file) {
        return IoUtil.toStream(file);
    }

    /**
     * @param base64 base64字符串
     * @return {@link InputStream} 文件输入流
     */
    public static InputStream getInputStream(String base64) {
        byte[] decode = Base64.decode(base64);
        return IoUtil.toStream(decode);
    }

    /**
     * @param bytes {@link Byte} 文件的字节对象
     * @return {@link InputStream} 文件输入流
     */
    public static InputStream getInputStream(byte[] bytes) {
        return IoUtil.toStream(bytes);
    }

    /**
     * MultipartFile转File
     *
     * @param multipartFile {@link MultipartFile}
     * @param filePath      文件地址
     * @return {@link File}
     */
    public static File getFile(MultipartFile multipartFile, String filePath) throws IOException {
        // 检查MultipartFile是否为空
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IOException("文件为空");
        }
        // 创建File对象
        File file = new File(filePath);
        // 使用try-with-resources语句自动关闭资源
        try (InputStream inputStream = multipartFile.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(file)) {
            // 将输入流中的数据写入到输出流（即文件）中
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        // 返回创建的File对象
        return file;
    }

}
