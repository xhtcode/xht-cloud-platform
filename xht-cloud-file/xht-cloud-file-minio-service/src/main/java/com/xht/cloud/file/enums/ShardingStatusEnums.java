package com.xht.cloud.file.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：分片状态
 *
 * @author : 小糊涂
 **/
@Getter
@AllArgsConstructor
public enum ShardingStatusEnums implements IEnum<Integer> {

    NO(0, "未合并"),

    YES(1, "已合并"),

    ;

    @JsonValue
    @EnumValue
    private final Integer value;


    private final String desc;

}
