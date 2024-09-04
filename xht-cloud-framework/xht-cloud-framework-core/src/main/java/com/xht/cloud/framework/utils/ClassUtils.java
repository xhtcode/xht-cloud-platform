package com.xht.cloud.framework.utils;

import cn.hutool.core.util.ClassUtil;
import com.xht.cloud.framework.domain.KeyValue;
import com.xht.cloud.framework.exception.constant.IErrorStatusCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述 ：class 工具类
 *
 * @author 小糊涂
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClassUtils extends ClassUtil {

    public static List<KeyValue<Integer, String>> getIErrorStatusCodeResult() {
        Class<IErrorStatusCode> iErrorStatusCodeClass = IErrorStatusCode.class;
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper("com.xht.cloud", iErrorStatusCodeClass);
        List<KeyValue<Integer, String>> keyValueList = new ArrayList<>();
        for (Class<?> aClass : classes) {
            if (ClassUtil.isEnum(aClass)) {
                Object[] enumConstants = aClass.getEnumConstants();
                for (Object enumConstant : enumConstants) {
                    if (enumConstant instanceof IErrorStatusCode errorStatusCode) {
                        KeyValue<Integer, String> keyValue = new KeyValue<>();
                        keyValue.setKey(errorStatusCode.getCode());
                        keyValue.setValue(errorStatusCode.getMessage());
                        keyValueList.add(keyValue);
                    }
                }
            }
        }
        return keyValueList.stream().sorted(Comparator.comparing(KeyValue::getKey, Comparator.naturalOrder())).collect(Collectors.toList());
    }

}
