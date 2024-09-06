package com.xht.cloud.admin.api.dict.translation;

import com.xht.cloud.admin.api.dict.dto.SysDictItemDTO;
import com.xht.cloud.admin.api.dict.feign.SysDictClient;
import com.xht.cloud.framework.jackson.translation.domain.DictExtraDTO;
import com.xht.cloud.framework.jackson.translation.enums.TransDictEnum;
import com.xht.cloud.framework.jackson.translation.strategy.AbstractTransDictStrategy;
import com.xht.cloud.framework.utils.ROptional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
public class TransDictIdStrategy extends AbstractTransDictStrategy {

    @Resource
    private SysDictClient sysDictClient;

    /**
     * 判断策略是否执行
     *
     * @return 策略类型
     */
    @Override
    public TransDictEnum support() {
        return TransDictEnum.DICT_ID;
    }

    /**
     * 写入扩展字段
     *
     * @param fieldContent   扩展字段信息存储器
     * @param writeFiledName 写入字段名称
     * @param dictType       字典类型
     * @param dictValue      字典编码或者字典id
     */
    @Override
    protected void writeValue(DictExtraDTO fieldContent, String writeFiledName, String dictType, String dictValue) {
        SysDictItemDTO sysDictItemDTO = ROptional.of(sysDictClient.getSysDictByDictId(dictValue)).getData().orElse(null);
        if (Objects.nonNull(sysDictItemDTO)) {
            fieldContent.setFiledValue(sysDictItemDTO.getDictValue());
        }
    }
}
