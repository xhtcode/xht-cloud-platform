package com.xht.cloud.framework.file.upload.helper;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.file.upload.UploadFileBuilder;
import com.xht.cloud.framework.file.domain.FileType;
import com.xht.cloud.framework.file.upload.UploadFileBO;
import com.xht.cloud.framework.file.exception.FileException;
import com.xht.cloud.framework.file.factory.FileTypeFactory;
import com.xht.cloud.framework.file.utils.DataSizeUtils;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 描述 ：Part 文件助手工具类
 *
 * @author 小糊涂
 **/
@Slf4j
@SuppressWarnings("unused")
public class PartFileHelper extends AbstractFileHelper {

    /**
     * 文件上传的MultipartFile
     */
    private final Part part;

    private PartFileHelper(Part part) {
        super();
        this.part = part;
        this.name = part.getName();
        this.originalFileName = FileNameUtil.getName(this.part.getSubmittedFileName());
        this.filePrefix = FileNameUtil.getPrefix(this.originalFileName);
        this.fileSuffix = FileNameUtil.getSuffix(this.originalFileName);
    }

    /**
     * 初始化一个 {@link Part}
     *
     * @param part 上传的文件信息
     * @return {@link PartFileHelper}
     */
    public static PartFileHelper init(Part part) {
        Assert.notNull(part, () -> new FileException("multipartFile is null!"));
        return new PartFileHelper(part);
    }

    /**
     * 生成文件类型
     */
    private void generateFileType() {
        this.fileType = FileTypeFactory.getFileTypeBySuffix(this.fileSuffix).orElseGet(() -> new FileType(fileSuffix, this.part.getContentType(), ""));
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
            this.size = new DataSizeUtils(this.part.getSize());
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
     * 生成文件文件字节数组
     */
    private void generateFileByte() {
        try {
            InputStream inputStream = this.part.getInputStream();
            this.inputStream = inputStream;
            this.fileBytes = IoUtil.readBytes(inputStream);
        } catch (IOException e) {
            throw new BizException("文件字节流生成获取失败", e);
        }
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
        UploadFileBuilder UploadFileBOBuilder = UploadFileBuilder.of(this.part)
                .dataSize(this.size)
                .originalFileName(this.originalFileName)
                .fileType(this.part.getContentType())
                .fileBytes(this.fileBytes)
                .inputStream(this.inputStream);
        builder.accept(UploadFileBOBuilder);
        return new UploadFileBO(UploadFileBOBuilder);
    }
}
