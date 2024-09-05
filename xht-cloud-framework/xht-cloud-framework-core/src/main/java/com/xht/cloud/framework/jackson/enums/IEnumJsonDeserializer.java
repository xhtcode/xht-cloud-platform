package com.xht.cloud.framework.jackson.enums;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.xht.cloud.framework.core.enums.IEnum;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.utils.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * 描述 ：枚举反序列化
 *
 * @author : 小糊涂
 **/
public class IEnumJsonDeserializer extends JsonDeserializer<IEnum<?>> {

    @Override
    @SuppressWarnings("all")
    public IEnum<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String currentName = jsonParser.currentName();
        Object currentValue = jsonParser.getCurrentValue();
        String valueAsString = jsonParser.getValueAsString();
        Class<?> findPropertyType = BeanUtils.findPropertyType(currentName, currentValue.getClass());
        if (!findPropertyType.isEnum()) {
            throw new BizException("使用@TransEnum注解时，请检查字段是不是枚举并且实现了：{}", IEnum.class);
        }
        Object[] enumConstants = findPropertyType.getEnumConstants();
        for (Object enumConstant : enumConstants) {
            if (enumConstant instanceof IEnum<?> iEnum) {
                String value = StringUtils.str(iEnum.getValue());
                if (Objects.equals(value, valueAsString)) {
                    return iEnum;
                }
            }
        }
        return null;
    }
}
