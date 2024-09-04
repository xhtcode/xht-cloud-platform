package com.xht.cloud.framework.file.upload;

import cn.hutool.core.io.file.FileNameUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.cloud.framework.core.builder.IBuilder;
import com.xht.cloud.framework.file.constant.FileTypeConstant;
import com.xht.cloud.framework.file.domain.FileType;
import com.xht.cloud.framework.file.factory.FileTypeFactory;
import com.xht.cloud.framework.file.utils.DataSizeUtils;
import com.xht.cloud.framework.utils.StringUtils;
import jakarta.servlet.http.Part;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 描述 ： 文件构建
 *
 * @author 小糊涂
 **/
@Getter
public class UploadFileBuilder implements IBuilder<UploadFileBO> {

    /**
     * 文件上传id
     */
    private String uploadId;

    /**
     * 文件上传的MultipartFile
     */
    @JsonIgnore
    private final MultipartFile sourceMultipartFile;

    /**
     * 文件上传的 Part
     */
    @JsonIgnore
    private final Part sourcePart;

    /**
     * 桶名称
     */
    private String bucket;


    /**
     * 存储桶路径
     */
    private String bucketPath;

    /**
     * 文件大小工具类
     */
    private DataSizeUtils dataSize;

    /**
     * 分块上传时每个部分的大小 默认-1
     */
    private Long partSize;

    /**
     * 文件上传的名称
     */
    private String originalFileName;

    /**
     * 文件上传的名称前缀
     */
    private String filePrefix;

    /**
     * 文件上传的名称后缀 不带“.”
     */
    private String fileSuffix;

    /**
     * 文件类型信息
     */
    private FileType fileType;

    /**
     * 文件字节
     */
    @JsonIgnore
    private byte[] fileBytes;

    /**
     * 文件流  默认{@link java.io.ByteArrayInputStream}
     */
    @JsonIgnore
    private InputStream inputStream;


    /**
     * 保存时文件名称
     */
    private String fileName;

    private UploadFileBuilder() {
        this.sourceMultipartFile = null;
        this.sourcePart = null;
    }
    private UploadFileBuilder(MultipartFile sourceMultipartFile) {
        this.sourceMultipartFile = sourceMultipartFile;
        this.sourcePart = null;
    }

    private UploadFileBuilder(Part sourcePart) {
        this.sourceMultipartFile = null;
        this.sourcePart = sourcePart;
    }

    /**
     * @return {@link UploadFileBuilder}
     */
    public static UploadFileBuilder of() {
        return new UploadFileBuilder();
    }
    /**
     * @param sourceMultipartFile {@link MultipartFile } 文件上传信息
     * @return {@link UploadFileBuilder}
     */
    public static UploadFileBuilder of(MultipartFile sourceMultipartFile) {
        return new UploadFileBuilder(sourceMultipartFile);
    }

    /**
     * @param sourcePart {@link Part }
     * @return {@link UploadFileBuilder}
     */
    public static UploadFileBuilder of(Part sourcePart) {
        return new UploadFileBuilder(sourcePart);
    }

    /**
     * @param uploadId {@link String}
     * @return {@link UploadFileBuilder}
     */
    public UploadFileBuilder uploadId(String uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    /**
     * @param bucket {@link String}
     * @return {@link UploadFileBuilder}
     */
    public UploadFileBuilder bucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    /**
     * @param bucketPath {@link String}
     * @return {@link UploadFileBuilder}
     */
    public UploadFileBuilder bucketPath(String bucketPath) {
        this.bucketPath = bucketPath;
        return this;
    }

    /**
     * @param dataSize {@link DataSizeUtils}
     * @return {@link UploadFileBuilder}
     */
    public UploadFileBuilder dataSize(DataSizeUtils dataSize) {
        this.dataSize = dataSize;
        return this;
    }

    /**
     * @param partSize {@link Long}
     * @return {@link UploadFileBuilder}
     */
    public UploadFileBuilder partSize(Long partSize) {
        this.partSize = partSize;
        return this;
    }

    /**
     * @param originalFileName {@link String}
     * @return {@link UploadFileBuilder}
     */
    public UploadFileBuilder originalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
        this.filePrefix = FileNameUtil.getPrefix(this.originalFileName);
        this.fileSuffix = FileNameUtil.getSuffix(this.originalFileName);
        return this;
    }



    /**
     * @param fileType {@link FileType}
     * @return {@link UploadFileBuilder}
     */
    public UploadFileBuilder fileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public UploadFileBuilder fileType(String contentType) {
        if (StringUtils.isEmpty(contentType)) {
            this.fileType = FileTypeConstant.DEFAULT_FILETYPE;
            return this;
        }
        this.fileType = FileTypeFactory.getFileTypeContentType(contentType).orElseGet(() -> new FileType(this.fileSuffix, contentType, "自定义"));
        return this;
    }
    /**
     * @param fileBytes {@link Byte}
     * @return {@link UploadFileBuilder}
     */
    public UploadFileBuilder fileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
        return this;
    }

    /**
     * @param fileName {@link Byte}
     * @return {@link UploadFileBuilder}
     */
    public UploadFileBuilder fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * @param inputStream {@link InputStream}
     * @return {@link UploadFileBuilder}
     */
    public UploadFileBuilder inputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    /**
     * 构建方法
     *
     * @return 被构建的对象
     */
    @Override
    public UploadFileBO build() {
        return new UploadFileBO(this);
    }
}