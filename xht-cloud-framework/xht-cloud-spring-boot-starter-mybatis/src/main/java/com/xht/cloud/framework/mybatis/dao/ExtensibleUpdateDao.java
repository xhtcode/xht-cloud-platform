package com.xht.cloud.framework.mybatis.dao;

import com.xht.cloud.framework.domain.request.UpdateRequest;

/**
 * 描述 ： dao 扩展接口
 *
 * @author : 小糊涂
 **/
@FunctionalInterface
public interface ExtensibleUpdateDao<T extends UpdateRequest> {

    /**
     * 扩展的修改的接口
     *
     * @param id        主键
     * @param updateReq 修改参数
     * @return 修改成功true
     */
    boolean update(String id, T updateReq);
}
