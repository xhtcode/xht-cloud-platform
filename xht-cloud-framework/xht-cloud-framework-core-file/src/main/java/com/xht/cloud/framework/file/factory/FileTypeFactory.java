package com.xht.cloud.framework.file.factory;

import com.xht.cloud.framework.exception.SysException;
import com.xht.cloud.framework.file.constant.FileTypeConstant;
import com.xht.cloud.framework.file.domain.FileType;
import com.xht.cloud.framework.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public final class FileTypeFactory {
    private final static Map<String, FileType> FILE_TYPES = new HashMap<>();
    private final static Map<String, FileType> FILE_TYPES_ = new HashMap<>();

    static {
        Class<FileTypeConstant> fileTypeClass = FileTypeConstant.class;
        Field[] fields = fileTypeClass.getFields();
        for (Field field : fields) {
            try {
                boolean isFinal = Modifier.isFinal(field.getModifiers());
                if (isFinal) {
                    Object o = field.get(null);
                    if (o instanceof FileType fileType) {
                        FILE_TYPES.put(fileType.getFileSuffix(), fileType);
                        FILE_TYPES_.put(fileType.contentType(), fileType);
                    }
                }
            } catch (Exception e) {
                throw new SysException(e);
            }
        }
    }

    public static Optional<FileType> getFileTypeBySuffix(String fileSuffix) {
        if (StringUtils.isEmpty(fileSuffix)) return Optional.empty();
        return Optional.ofNullable(FILE_TYPES.get(fileSuffix.toUpperCase()));
    }


    public static Optional<FileType> getFileTypeContentType(String contentType) {
        if (StringUtils.isEmpty(contentType)) return Optional.empty();
        return Optional.ofNullable(FILE_TYPES_.get(contentType.toUpperCase()));
    }
}
