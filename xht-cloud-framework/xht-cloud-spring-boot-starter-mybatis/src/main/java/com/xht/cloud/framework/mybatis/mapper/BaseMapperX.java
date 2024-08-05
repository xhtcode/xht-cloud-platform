package com.xht.cloud.framework.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 描述 ：BaseMapper 扩展
 *
 * @author 小糊涂
 * @see BaseMapper
 **/
public interface BaseMapperX<T extends AbstractDO> extends BaseMapper<T> {

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    default Optional<T> findById(Serializable id) {
        return Optional.ofNullable(selectById(id));
    }

    /**
     * 批量插入，适合大量数据插入
     *
     * @param entities 实体们
     */
    default void insertBatch(Collection<T> entities) {
        Db.saveBatch(entities);
    }

    /**
     * 批量插入，适合大量数据插入
     *
     * @param entities 实体们
     * @param size     插入批次数量  插入数量 Db.saveBatch 默认为 1000
     */
    default void insertBatch(Collection<T> entities, int size) {
        Db.saveBatch(entities, size);
    }


    /**
     * 查询一个
     *
     * @param field 字段name
     * @param value 字段value
     */
    default Optional<T> selectOne(String field, Object value) {
        return Optional.ofNullable(selectOne(new QueryWrapper<T>().eq(field, value)));
    }

    /**
     * 查询一个
     *
     * @param field 字段name
     * @param value 字段value
     */
    default Optional<T> selectOne(SFunction<T, ?> field, Object value) {
        return Optional.ofNullable(selectOne(new LambdaQueryWrapper<T>().eq(field, value)));
    }

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    default List<T> selectList(String field, Object value) {
        return selectList(new QueryWrapper<T>().eq(field, value));
    }

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    default List<T> selectList(SFunction<T, ?> field, Object value) {
        return selectList(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    default List<T> selectListIn(String field, Collection<?> value) {
        return selectList(new QueryWrapper<T>().in(field, value));
    }

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    default List<T> selectListIn(SFunction<T, ?> field, Collection<?> value) {
        return selectList(new LambdaQueryWrapper<T>().in(field, value));
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    default long selectCount(String field, Object value) {
        return selectCount(new QueryWrapper<T>().eq(field, value));
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    default long selectCount(SFunction<T, ?> field, Object value) {
        return selectCount(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    default long selectCountIn(String field, Collection<?> value) {
        return selectCount(new QueryWrapper<T>().in(field, value));
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    default long selectCountIn(SFunction<T, ?> field, Collection<?> value) {
        return selectCount(new LambdaQueryWrapper<T>().in(field, value));
    }
}
