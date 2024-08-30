package com.xht.cloud.framework.mybatis.handler.dto;

import com.xht.cloud.framework.core.enums.IEnum;
import lombok.Builder;
import lombok.Getter;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Getter
@Builder
public class DataScopeDTO<T extends IEnum<?>> {

    /**
     * 如果返回false 那么就不会拼装sql数据 用户最大数据权限使用
     */
    private boolean verify;

    private String deptId;

    private String userId;

    private T dataScope;
}
