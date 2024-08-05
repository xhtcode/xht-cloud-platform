package com.xht.cloud.generate.constant.enums;


import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：查询类型
 *
 * @author 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum QueryTypeEnum  implements IEnum<String> {

    eq("eq"),

    ne("ne"),

    gt("gt"),

    ge("ge"),

    lt("lt"),

    le("le"),

    like("like"),

    leftLike("leftLike"),

    rightLike("rightLike"),
    ;

    private final String value;

}
