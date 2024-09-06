package com.xht.cloud.framework.jackson.translation.strategy;

import com.fasterxml.jackson.core.JsonGenerator;
import com.xht.cloud.framework.core.strategy.IStrategy;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.jackson.JsonGeneratorTool;
import com.xht.cloud.framework.jackson.translation.SkipTransDictThreadLocal;
import com.xht.cloud.framework.jackson.translation.annotation.TransDict;
import com.xht.cloud.framework.jackson.translation.domain.DictExtraDTO;
import com.xht.cloud.framework.jackson.translation.enums.TransDictEnum;
import com.xht.cloud.framework.jackson.translation.exception.TransDictException;
import com.xht.cloud.framework.jackson.translation.factory.TransDictFactory;
import com.xht.cloud.framework.utils.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

/**
 * 描述 ：策略模式
 *
 * @author : 小糊涂
 **/
public abstract class AbstractTransDictStrategy implements IStrategy<TransDictEnum>, InitializingBean {


    /**
     * 执行
     *
     * @param jsonGenerator {@link JsonGenerator} 用于输出结果Json内容的生成器
     * @param transDict     要翻译的字典配置信息
     */
    public final void execute(String value, JsonGenerator jsonGenerator, TransDict transDict) throws IOException {
        String currentFieldName = JsonGeneratorTool.getFieldName(jsonGenerator);
        String currentName = JsonGeneratorTool.getFieldName(jsonGenerator);
        if (SkipTransDictThreadLocal.isSkip(currentName)) {
            jsonGenerator.writeString(value);
            return;
        }
        String fieldName = transDict.fieldName();
        String writeFiledName = String.format("%s%s", currentFieldName, transDict.fieldNameSuffix());
        if (StringUtils.hasText(fieldName)) {
            writeFiledName = fieldName;
        }
        Assert.hasText(writeFiledName, () -> new TransDictException("【字典翻译】字典字段名称配置不能为空"));
        final DictExtraDTO fieldContent = new DictExtraDTO();
        writeValue(fieldContent, writeFiledName, transDict.dictType(), value);
        jsonGenerator.writeString(value);
        jsonGenerator.writePOJOField(writeFiledName, fieldContent.getFiledValue());
    }

    /**
     * 写入扩展字段
     *
     * @param fieldContent   扩展字段信息存储器
     * @param writeFiledName 写入字段名称
     * @param dictType       字典类型
     * @param dictValue      字典编码或者字典id
     */
    protected abstract void writeValue(final DictExtraDTO fieldContent, String writeFiledName, String dictType, String dictValue);


    @Override
    public void afterPropertiesSet() throws Exception {
        TransDictFactory.getInstance().register(support(), this);
    }
}
