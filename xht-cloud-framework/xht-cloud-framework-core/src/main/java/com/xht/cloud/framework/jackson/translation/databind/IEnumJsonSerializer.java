package com.xht.cloud.framework.jackson.translation.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.xht.cloud.framework.core.enums.IEnum;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.jackson.JsonGeneratorTool;
import com.xht.cloud.framework.jackson.translation.annotation.TransEnum;
import com.xht.cloud.framework.jackson.translation.constant.TransConstant;

import java.io.IOException;
import java.util.Objects;

/**
 * 描述 ：枚举序列化
 *
 * @author : 小糊涂
 **/
public class IEnumJsonSerializer extends JsonSerializer<IEnum<?>> implements ContextualSerializer {

    /**
     * 字段后缀
     */
    private String fieldSuffix = TransConstant.FIELD_SUFFIX;


    @Override
    public void serialize(IEnum<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String fieldName = JsonGeneratorTool.getFieldName(gen);
        String fieldNameFormat = String.format("%s%s", fieldName, fieldSuffix);
        if (Objects.nonNull(value)) {
            gen.writeObject(value.getValue());
            gen.writePOJOField(fieldNameFormat, value.desc());
        } else {
            gen.writeNull();
            gen.writePOJOField(fieldNameFormat, null);
        }
    }


    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        // 获取属性上的 SensitiveField 注解
        TransEnum annotation = beanProperty.getAnnotation(TransEnum.class);
        if (Objects.nonNull(annotation) && beanProperty.getType().isTypeOrSubTypeOf(IEnum.class)) {
            this.fieldSuffix = annotation.suffix();
            return this;
        }
        throw new BizException("使用@TransEnum注解时，请检查字段是不是枚举并且实现了：{}", IEnum.class);
    }

}
