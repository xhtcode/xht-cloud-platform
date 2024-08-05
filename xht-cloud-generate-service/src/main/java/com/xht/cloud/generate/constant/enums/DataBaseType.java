package com.xht.cloud.generate.constant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.xht.cloud.framework.core.enums.IEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * 描述 ：数据库类型
 * <pre> </pre>
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@Getter
public enum DataBaseType implements IEnum<String> {

    MySQL("MySQL", "com.mysql.cj.jdbc.Driver"),

    Oracle("Oracle", "oracle.jdbc.driver.OracleDriver");

    private final String dbType;

    private final String driverClass;

    DataBaseType(String dbType, String driverClass) {
        this.dbType = dbType;
        this.driverClass = driverClass;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DataBaseType getByDbType(String dbType) {
        return Arrays.stream(values()).filter(item -> item.dbType.equalsIgnoreCase(dbType)).findFirst().orElse(null);
    }

    /**
     * @return 字典值
     */
    @Override
    public String getValue() {
        return this.dbType;
    }
}
