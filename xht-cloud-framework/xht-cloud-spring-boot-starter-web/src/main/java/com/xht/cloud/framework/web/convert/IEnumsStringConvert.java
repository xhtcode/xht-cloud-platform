package com.xht.cloud.framework.web.convert;

import com.google.common.collect.Maps;
import com.xht.cloud.framework.core.enums.IEnum;
import com.xht.cloud.framework.exception.SysException;
import com.xht.cloud.framework.utils.support.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述 ：string 转 IEnum枚举
 *
 * @author 小糊涂
 **/
@Slf4j
public class IEnumsStringConvert implements Converter<String, IEnum<Serializable>> {
    private final Map<String, IEnum<Serializable>> ENUM_MAP = Maps.newHashMap();

    IEnumsStringConvert(Class<? extends IEnum<Serializable>> enumType) {
        IEnum<Serializable>[] enumConstants = enumType.getEnumConstants();
        for (IEnum<Serializable> e : enumConstants) {
            try {
                String value = StringUtils.str(e.getValue());
                if (StringUtils.hasText(value)) {
                    ENUM_MAP.put(value, e);
                } else {
                    throw new SysException("系统配置错误 com.xht.cloud.framework.core.enums.IEnum.getValue is null!");
                }
            } catch (Exception exception) {
                log.error("string 转 IEnum枚举 异常:{}", exception.getMessage(), exception);
            }
        }
    }

    @Override
    public IEnum<Serializable> convert(String source) {
        return ENUM_MAP.get(source);
    }
}