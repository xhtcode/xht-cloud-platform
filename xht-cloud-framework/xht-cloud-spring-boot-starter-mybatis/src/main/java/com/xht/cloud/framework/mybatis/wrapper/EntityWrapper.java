package com.xht.cloud.framework.mybatis.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;

import java.io.Serializable;

/**
 * 描述 ：Wrapper构建
 *
 * @author 小糊涂
 **/
public interface EntityWrapper<T extends AbstractDO> extends Serializable {

    /**
     * 获取 {@link QueryWrapper }
     *
     * @return {@link QueryWrapper }
     */
    default QueryWrapper<T> query() {
        return new QueryWrapper<>();
    }

    /**
     * 获取 {@link LambdaQueryWrapper }
     *
     * @return {@link LambdaQueryWrapper }
     */
    default LambdaQueryWrapper<T> lambdaQuery() {
        return new LambdaQueryWrapper<>();
    }

    /**
     * 获取 {@link LambdaQueryWrapper }
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper }
     */
    LambdaQueryWrapper<T> lambdaQuery(T entity);

    /**
     * 获取 {@link UpdateWrapper }
     *
     * @return {@link UpdateWrapper }
     */
    default UpdateWrapper<T> update() {
        return new UpdateWrapper<>();
    }

    /**
     * 获取 {@link LambdaUpdateWrapper }
     *
     * @return {@link LambdaUpdateWrapper }
     */
    default LambdaUpdateWrapper<T> lambdaUpdate() {
        return new LambdaUpdateWrapper<>();
    }

    /**
     * 获取 {@link LambdaUpdateWrapper }
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper }
     */
    LambdaUpdateWrapper<T> lambdaUpdate(T entity);

}
