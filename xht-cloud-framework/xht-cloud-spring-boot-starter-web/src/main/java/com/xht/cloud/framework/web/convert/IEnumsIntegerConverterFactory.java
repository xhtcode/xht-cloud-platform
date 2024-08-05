package com.xht.cloud.framework.web.convert;

import com.google.common.collect.Maps;
import com.xht.cloud.framework.core.enums.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;

/**
 * 描述 ：Integer 转 IEnum枚举工厂
 *
 * @author 小糊涂
 **/
public class IEnumsIntegerConverterFactory implements ConverterFactory<Integer, IEnum<Integer>> {

    private static final Map<Class<? extends IEnum<Integer>>, Converter<Integer, ? extends IEnum<Integer>>> CONVERTERS = Maps.newHashMap();

    @Override
    @SuppressWarnings("all")
    public <T extends IEnum<Integer>> Converter<Integer, T> getConverter(Class<T> targetType) {
        Converter<Integer, ? extends IEnum<Integer>> converter = CONVERTERS.get(targetType);
        if (converter == null) {
            converter = new IEnumsIntegerConvert(targetType);
            CONVERTERS.put(targetType, converter);
        }
        return (Converter<Integer, T>) converter;
    }
}
