package com.xht.cloud.generate.module.database.domain.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.generate.module.database.domain.dataobject.GenDatabaseDO;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * 描述 ：代码生成器-数据源管理
 *
 * @author 小糊涂
 **/
public final class GenDatabaseWrapper implements EntityWrapper<GenDatabaseDO> {

    /**
     * 私有化构造器
     */
    private GenDatabaseWrapper() {
    }

    /**
     * 获取实例
     */
    public static GenDatabaseWrapper getInstance() {
        return Instance.INSTANCE.getInstance();
    }

    /**
     * 实例处理化
     */
    private enum Instance {

        INSTANCE;

        private final GenDatabaseWrapper wrapper;

        Instance() {
            wrapper = new GenDatabaseWrapper();
        }

        public GenDatabaseWrapper getInstance() {
            return wrapper;
        }
    }

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    public LambdaQueryWrapper<GenDatabaseDO> lambdaQuery(GenDatabaseDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<GenDatabaseDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(Objects.nonNull(entity.getId()), GenDatabaseDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getConnName()), GenDatabaseDO::getConnName, entity.getConnName())
                .eq(StringUtils.hasText(entity.getDbUrl()), GenDatabaseDO::getDbUrl, entity.getDbUrl())
                .eq(StringUtils.hasText(entity.getDbType()), GenDatabaseDO::getDbType, entity.getDbType())
                .eq(StringUtils.hasText(entity.getDbName()), GenDatabaseDO::getDbName, entity.getDbName())
                .eq(StringUtils.hasText(entity.getDbDescribe()), GenDatabaseDO::getDbDescribe, entity.getDbDescribe())
                .eq(StringUtils.hasText(entity.getHost()), GenDatabaseDO::getHost, entity.getHost())
                .eq(Objects.nonNull(entity.getPort()), GenDatabaseDO::getPort, entity.getPort())
                .eq(StringUtils.hasText(entity.getUserName()), GenDatabaseDO::getUserName, entity.getUserName())
                .eq(StringUtils.hasText(entity.getPassWord()), GenDatabaseDO::getPassWord, entity.getPassWord())
                .eq(!ObjectUtils.isEmpty(entity.getSort()), GenDatabaseDO::getSort, entity.getSort())
        ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    public LambdaUpdateWrapper<GenDatabaseDO> lambdaUpdate(GenDatabaseDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<GenDatabaseDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(GenDatabaseDO::getConnName, entity.getConnName())
                .set(GenDatabaseDO::getDbUrl, entity.getDbUrl())
                .set(GenDatabaseDO::getDbType, entity.getDbType())
                .set(GenDatabaseDO::getDbName, entity.getDbName())
                .set(GenDatabaseDO::getDbDescribe, entity.getDbDescribe())
                .set(GenDatabaseDO::getHost, entity.getHost())
                .set(GenDatabaseDO::getPort, entity.getPort())
                .set(GenDatabaseDO::getUserName, entity.getUserName())
                .set(GenDatabaseDO::getPassWord, entity.getPassWord())
                .set(GenDatabaseDO::getSort, entity.getSort())
        ;
    }


}
