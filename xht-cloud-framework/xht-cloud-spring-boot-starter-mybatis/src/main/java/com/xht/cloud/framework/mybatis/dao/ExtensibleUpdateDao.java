package com.xht.cloud.framework.mybatis.dao;

import com.xht.cloud.framework.domain.request.Request;

/**
 * 描述 ： dao 扩展接口
 *
 * @author : 小糊涂
 **/
@FunctionalInterface
public interface ExtensibleUpdateDao<T extends Request> {

    /**
     * 扩展的修改的接口
     *
     * @param updateRequest 修改参数
     * @return 修改成功true
     */
    boolean update(String id, T updateRequest);
}
