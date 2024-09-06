package com.xht.cloud.framework.jackson.desensitization.jackson;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.jackson.JsonGeneratorTool;
import com.xht.cloud.framework.jackson.desensitization.SkipSensitiveThreadLocal;
import com.xht.cloud.framework.jackson.desensitization.annotation.SensitiveField;
import com.xht.cloud.framework.jackson.desensitization.convert.ISensitiveFieldConvert;
import com.xht.cloud.framework.jackson.desensitization.factory.SensitiveFieldConvertFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * 描述 ：自定义的脱敏JSON序列化器
 *
 * @author 小糊涂
 **/
public class SensitiveFieldJsonSerializable extends JsonSerializer<String> implements ContextualSerializer {

    private ISensitiveFieldConvert iSensitiveFieldConvert; //脱敏转换器

    public SensitiveFieldJsonSerializable() {
    }

    @Override
    public void serialize(String source, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(iSensitiveFieldConvert)) {
            throw new BizException("You used the annotation `@SensitiveField` but did not inject `ISensitiveFieldConvert`");
        }
        String currentName = JsonGeneratorTool.getFieldName(jsonGenerator);
        if (SkipSensitiveThreadLocal.isSkip(currentName)) {
            jsonGenerator.writeString(source);
            return;
        }
        // 将字符串按照设定的脱敏策略进行脱敏处理后序列化到 JSON 中
        jsonGenerator.writeString(iSensitiveFieldConvert.desensitization(source));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        // 获取属性上的 SensitiveField 注解
        SensitiveField annotation = beanProperty.getAnnotation(SensitiveField.class);
        // 判断注解不为空且属性类型为 String
        if (Objects.nonNull(annotation) && Objects.equals(String.class, beanProperty.getType().getRawClass())) {
            ISensitiveFieldConvert strategy = SensitiveFieldConvertFactory.getInstance().getStrategy(annotation.type());
            if (Objects.nonNull(strategy)) {
                this.iSensitiveFieldConvert = strategy;// 设置脱敏转换器
                return this;
            }
        }
        // 返回默认的序列化器
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }


}