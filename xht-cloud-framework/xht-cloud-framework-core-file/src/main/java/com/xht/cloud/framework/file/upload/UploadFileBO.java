package com.xht.cloud.framework.file.upload;

import cn.hutool.core.io.IoUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.cloud.framework.constant.StringConstant;
import com.xht.cloud.framework.file.domain.FileType;
import com.xht.cloud.framework.file.domain.cmd.file.OssBaseFileCmd;
import com.xht.cloud.framework.file.utils.DataSizeUtils;
import com.xht.cloud.framework.utils.secret.MD5Utils;
import com.xht.cloud.framework.utils.StringUtils;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 描述 ： MultipartFile格式化文件存储信息
 *
 * @author 小糊涂
 **/
@SuppressWarnings("unused")
public class UploadFileBO extends OssBaseFileCmd implements Serializable {

    /**
     * 文件上传id
     */
    @Setter
    @Getter
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
    @Getter
    private String bucket;

    /**
     * 存储桶路径
     */
    private String bucketPath;

    /**
     * 文件大小工具类
     */
    private final DataSizeUtils dataSize;

    /**
     * 分块上传时每个部分的大小 默认-1
     */
    private final Long partSize;

    /**
     * 保存时文件名称
     */
    private String fileName;

    /**
     * 文件上传的名称
     */
    @Getter
    private String originalFileName;

    /**
     * 文件上传的名称前缀
     */
    @Getter
    private final String filePrefix;

    /**
     * 文件上传的名称后缀 不带“.”
     */
    @Getter
    private final String fileSuffix;

    /**
     * 文件类型信息
     */
    @Getter
    private final FileType fileType;

    /**
     * 文件字节
     */
    @JsonIgnore
    @Getter
    private final byte[] fileBytes;

    /**
     * 文件流  默认{@link java.io.ByteArrayInputStream}
     */
    @JsonIgnore
    @Getter
    private final InputStream inputStream;

    public UploadFileBO(UploadFileBuilder uploadFileBuilder) {
        this.uploadId = uploadFileBuilder.getUploadId();
        this.sourceMultipartFile = uploadFileBuilder.getSourceMultipartFile();
        this.sourcePart = uploadFileBuilder.getSourcePart();
        this.bucket = uploadFileBuilder.getBucket();
        this.bucketPath = uploadFileBuilder.getBucketPath();
        this.dataSize = uploadFileBuilder.getDataSize();
        this.partSize = uploadFileBuilder.getPartSize();
        this.originalFileName = uploadFileBuilder.getOriginalFileName();
        this.filePrefix = uploadFileBuilder.getFilePrefix();
        this.fileSuffix = uploadFileBuilder.getFileSuffix();
        this.fileType = uploadFileBuilder.getFileType();
        this.fileBytes = uploadFileBuilder.getFileBytes();
        this.inputStream = uploadFileBuilder.getInputStream();
        this.fileName = uploadFileBuilder.getFileName();
    }

    /**
     * 获取文件大小
     *
     * @return 文件大小
     */
    @JsonIgnore
    public long getSize() {
        return this.dataSize.size();
    }

    /**
     * 获取文件ContentType
     *
     * @return 文件ContentType
     */
    @JsonIgnore
    public String getFileContentType() {
        return this.fileType.contentType();
    }


    @JsonIgnore
    public void closeStream() {
        IoUtil.close(this.inputStream);
    }


    /**
     * @return 生成的文件名称
     */
    @JsonIgnore
    public String getFileName() {
        if (StringUtils.isEmpty(this.fileName)) {
            this.fileName = this.fileType.generateName();
        }
        return this.fileName;
    }

    /**
     * @return 生成的文件路径
     */
    @JsonIgnore
    public String getBucketPath() {
        if (StringUtils.isEmpty(this.bucketPath)) {
            LocalDateTime now = LocalDateTime.now();
            int monthValue = now.getMonthValue();
            String month = monthValue + "";
            if (monthValue < 10) {
                month = String.format("0%s", monthValue);
            }
            this.bucketPath = String.format("/%s/%s/%s/", now.getYear(), month, this.fileType.type());
        }
        return this.bucketPath;
    }


    @JsonIgnore
    public Long getPartSize() {
        if (Objects.isNull(this.partSize)) return -1L;
        return this.partSize;
    }

    @JsonIgnore
    public String md5() {
        if (Objects.isNull(this.fileBytes)) return StringConstant.EMPTY_STR;
        return MD5Utils.generateSignature(this.fileBytes);
    }

    @JsonIgnore
    public String getObjectName() {
        return String.format("%s%s.%s", this.getBucketPath(), this.getFileName(), this.fileSuffix);
    }
}
