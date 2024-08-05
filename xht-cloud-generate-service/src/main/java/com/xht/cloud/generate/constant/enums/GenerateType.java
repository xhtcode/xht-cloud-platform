package com.xht.cloud.generate.constant.enums;


import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：生成类型
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum GenerateType implements IEnum<String> {

    ZIP("0"),

    LOCALITY("1"),
    ;

    private final String value;


}
