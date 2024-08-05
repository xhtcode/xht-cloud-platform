package com.xht.cloud.framework.file.upload.helper;

import com.xht.cloud.framework.file.domain.FileType;
import com.xht.cloud.framework.file.upload.UploadFileBO;
import com.xht.cloud.framework.file.upload.UploadFileBuilder;
import com.xht.cloud.framework.file.utils.DataSizeUtils;

import java.io.InputStream;
import java.util.function.Consumer;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public abstract class AbstractFileHelper {

    /**
     * 文件上传id
     */
    private String uploadId;


    /**
     * 文件大小工具类
     */
    protected DataSizeUtils size;

    /**
     * 文件的接收参数 @RequestParam(name="file") MultipartFile multipartFile 中的 file
     */
    protected String name;

    /**
     * 文件上传的名称
     */
    protected String originalFileName;

    /**
     * 文件上传的名称前缀
     */
    protected String filePrefix;

    /**
     * 文件上传的名称后缀 不带“.”
     */
    protected String fileSuffix;

    /**
     * 文件类型信息
     */
    protected FileType fileType;

    /**
     * 文件字节
     */
    protected byte[] fileBytes;

    /**
     * 文件流 默认{@link java.io.ByteArrayInputStream}
     */
    protected InputStream inputStream;

    protected AbstractFileHelper() {
    }

    /**
     * @param fileType {@link FileType} 文件类型信息
     * @return 文件处理助手
     */
    public abstract AbstractFileHelper checkFileType(Consumer<FileType> fileType);

    /**
     * @param customizer {@link DataSizeUtils} 文件大小信息
     * @return 文件处理助手
     */
    public abstract AbstractFileHelper checkFileSize(Consumer<DataSizeUtils> customizer);


    /**
     * 获取一个 {@link UploadFileBO} 对象，内部封装了一些MultipartFile的信息
     *
     * @return {@link UploadFileBO} MultipartFile的信息
     */
    public abstract UploadFileBO format(Consumer<UploadFileBuilder> builder);
}
