package com.xht.cloud.framework.file.upload.helper;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.file.domain.FileType;
import com.xht.cloud.framework.file.exception.FileException;
import com.xht.cloud.framework.file.factory.FileTypeFactory;
import com.xht.cloud.framework.file.upload.UploadFileBO;
import com.xht.cloud.framework.file.upload.UploadFileBuilder;
import com.xht.cloud.framework.file.utils.DataSizeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 描述 ：MultipartFile 文件助手工具类
 *
 * @author 小糊涂
 **/
@Slf4j
@SuppressWarnings("unused")
public final class MultipartFileHelper extends AbstractFileHelper {

    /**
     * 文件上传的MultipartFile
     */
    private final MultipartFile multipartFile;

    private MultipartFileHelper(MultipartFile multipartFile) {
        super();
        this.multipartFile = multipartFile;
        this.name = multipartFile.getName();
        this.originalFileName = FileNameUtil.getName(this.multipartFile.getOriginalFilename());
        this.filePrefix = FileNameUtil.getPrefix(this.originalFileName);
        this.fileSuffix = FileNameUtil.getSuffix(this.originalFileName);
    }

    /**
     * 初始化一个 {@link MultipartFileHelper}
     *
     * @param multipartFile 上传的文件信息
     * @return {@link MultipartFileHelper}
     */
    public static MultipartFileHelper init(MultipartFile multipartFile) {
        Assert.notNull(multipartFile, () -> new FileException("multipartFile is null!"));
        return new MultipartFileHelper(multipartFile);
    }

    /**
     * 生成文件文件字节数组
     */
    private void generateFileByte() {
        try {
            this.fileBytes = this.multipartFile.getBytes();
            this.inputStream = IoUtil.toStream(this.fileBytes);
        } catch (IOException e) {
            throw new BizException("文件字节流生成获取失败", e);
        }
    }


    /**
     * 生成文件类型
     */
    private void generateFileType() {
        this.fileType = FileTypeFactory.getFileTypeBySuffix(this.fileSuffix).orElseGet(() -> new FileType(fileSuffix, this.multipartFile.getContentType(), ""));
    }

    /**
     * @param fileType {@link FileType} 文件类型信息
     * @return 文件处理助手
     */
    @Override
    public AbstractFileHelper checkFileType(Consumer<FileType> fileType) {
        if (Objects.isNull(this.fileType)) {
            generateFileType();
        }
        fileType.accept(this.fileType);
        return this;
    }

    /**
     * 生成文件大小
     */
    private void generateSize() {
        if (Objects.isNull(size)) {
            this.size = new DataSizeUtils(this.multipartFile.getSize());
        }
    }

    /**
     * @param customizer {@link DataSizeUtils} 文件大小信息
     * @return 文件处理助手
     */
    @Override
    public AbstractFileHelper checkFileSize(Consumer<DataSizeUtils> customizer) {
        generateSize();
        customizer.accept(this.size);
        return this;
    }

    /**
     * 获取一个 {@link UploadFileBO} 对象，内部封装了一些MultipartFile的信息
     *
     * @return {@link UploadFileBO} MultipartFile的信息
     */
    @Override
    public UploadFileBO format(Consumer<UploadFileBuilder> builder) {
        generateSize();
        generateFileType();
        generateFileByte();
        UploadFileBuilder uploadFileBuilder = UploadFileBuilder.of(this.multipartFile)
                .dataSize(this.size)
                .originalFileName(this.originalFileName)
                .fileType(this.multipartFile.getContentType())
                .fileBytes(this.fileBytes)
                .inputStream(this.inputStream);
        builder.accept(uploadFileBuilder);
        return new UploadFileBO(uploadFileBuilder);
    }


}
