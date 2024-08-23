package com.xht.cloud.framework.mybatis.dao;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;
import com.xht.cloud.framework.mybatis.mapper.BaseMapperX;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    /**
     * 查询一个
     *
     * @param field 字段name
     * @param value 字段value
     */
    public Optional<T> selectOne(String field, Object value) {
        return getBaseMapper().selectOne(field, value);
    }

    /**
     * 查询一个
     *
     * @param field 字段name
     * @param value 字段value
     */
    public Optional<T> selectOne(SFunction<T, ?> field, Object value) {
        return getBaseMapper().selectOne(field, value);
    }

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    public List<T> selectList(String field, Object value) {
        return getBaseMapper().selectList(field, value);
    }

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    public List<T> selectList(SFunction<T, ?> field, Object value) {
        return getBaseMapper().selectList(field, value);
    }

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    public List<T> selectListIn(String field, Collection<?> value) {
        return getBaseMapper().selectListIn(field, value);
    }

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    public List<T> selectListIn(SFunction<T, ?> field, Collection<?> value) {
        return getBaseMapper().selectListIn(field, value);
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    public long selectCount(String field, Object value) {
        return getBaseMapper().selectCount(field, value);
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    public long selectCount(SFunction<T, ?> field, Object value) {
        return getBaseMapper().selectCount(field, value);
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    public long selectCountIn(String field, Collection<?> value) {
        return getBaseMapper().selectCountIn(field, value);
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    public long selectCountIn(SFunction<T, ?> field, Collection<?> value) {
        return getBaseMapper().selectCountIn(field, value);
    }

}
