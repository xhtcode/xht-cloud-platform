package com.xht.cloud.framework.mybatis.handler;

import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.mybatis.enums.DataScopeTypeEnums;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 ：数据权限工厂
 *
 * @author 小糊涂
 **/
public class DataScopeSqlFactory {

    private final Map<DataScopeTypeEnums, DataScopeSqlHandler> handler;

    public DataScopeSqlFactory() {
        handler = new HashMap<>();
    }

    public void put(DataScopeTypeEnums key, DataScopeSqlHandler value) {
        handler.put(key, value);
    }

    public DataScopeSqlHandler getDataScopeHandler(DataScopeTypeEnums typeEnums) {
        Assert.notEmpty(handler, () -> new RuntimeException("查询不到数据权限Handler"));
        return handler.get(typeEnums);
    }

}
