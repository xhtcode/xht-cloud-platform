package com.xht.cloud.framework.mybatis.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;
import com.xht.cloud.framework.mybatis.mapper.BaseMapperX;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
public abstract class BaseDaoImpl<M extends BaseMapperX<T>, T extends AbstractDO> extends ServiceImpl<M, T> {

    /**
     * 根据主键修改数据
     *
     * @param entity 实体
     * @return 修改
     */
    public abstract boolean update(T entity);
}
