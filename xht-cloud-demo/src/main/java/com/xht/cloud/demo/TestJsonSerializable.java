package com.xht.cloud.demo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.xht.cloud.framework.core.enums.IEnum;
import com.xht.cloud.framework.domain.KeyValue;

import java.io.IOException;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
public class TestJsonSerializable extends JsonSerializer<IEnum<?>> {

    @Override
    public void serialize(IEnum<?> value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeObject(value);
        String currentName = null;
        JsonStreamContext jsc = jsonGenerator.getOutputContext();
        if (null != jsc) {
            currentName = jsc.getCurrentName();
        }
        KeyValue<Object, Object> objectObjectKeyValue = new KeyValue<>();
        objectObjectKeyValue.setKey(value.getValue());
        objectObjectKeyValue.setValue(value.desc());
        jsonGenerator.writePOJOField(currentName + "View", value.desc());
    }

}
