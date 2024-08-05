package com.xht.cloud.framework.web.convert;

import com.google.common.collect.Maps;
import com.xht.cloud.framework.core.enums.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述 ：string 转 IEnum枚举工厂
 *
 * @author 小糊涂
 **/
public class IEnumsStringConverterFactory implements ConverterFactory<String, IEnum<Serializable>> {

    private static final Map<Class<?>, Converter<String, ? extends IEnum<Serializable>>> CONVERTERS = Maps.newHashMap();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IEnum<Serializable>> Converter<String, T> getConverter(Class<T> targetType) {
        Converter<String, ? extends IEnum<Serializable>> converter = CONVERTERS.get(targetType);
        if (converter == null) {
            converter = new IEnumsStringConvert(targetType);
            CONVERTERS.put(targetType, converter);
        }
        return (Converter<String, T>) new IEnumsStringConvert(targetType);
    }
}
