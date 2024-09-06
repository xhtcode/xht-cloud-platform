package com.xht.cloud.framework.jackson.translation.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.jackson.translation.annotation.TransDict;
import com.xht.cloud.framework.jackson.translation.exception.TransDictException;
import com.xht.cloud.framework.jackson.translation.factory.TransDictFactory;
import com.xht.cloud.framework.jackson.translation.strategy.AbstractTransDictStrategy;

import java.io.IOException;
import java.util.Objects;

/**
 * 描述 ：字典序列化
 *
 * @author : 小糊涂
 **/
public class TransDictJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 翻译配置
     */
    private TransDict transDict;

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        AbstractTransDictStrategy strategy = TransDictFactory.getInstance().getStrategy(transDict.type());
        Assert.notNull(strategy, () -> new TransDictException("【字典翻译】查询不到具体翻译策略"));
        strategy.execute(value, jsonGenerator, transDict);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        TransDict annotation = beanProperty.getAnnotation(TransDict.class);
        if (Objects.nonNull(annotation) && beanProperty.getType().isTypeOrSubTypeOf(String.class)) {
            this.transDict = annotation;
            return this;
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }
}
