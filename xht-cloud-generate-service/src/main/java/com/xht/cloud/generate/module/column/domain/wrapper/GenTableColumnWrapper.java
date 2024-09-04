package com.xht.cloud.generate.module.column.domain.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * 描述 ：代码生成业务字段
 *
 * @author 小糊涂
 **/
public final class GenTableColumnWrapper implements EntityWrapper<GenTableColumnDO> {

    /**
     * 私有化构造器
     */
    private GenTableColumnWrapper() {
    }

    /**
     * 获取实例
     */
    public static GenTableColumnWrapper getInstance() {
        return Instance.INSTANCE.getInstance();
    }

    /**
     * 实例处理化
     */
    private enum Instance {

        INSTANCE;

        private final GenTableColumnWrapper wrapper;

        Instance() {
            wrapper = new GenTableColumnWrapper();
        }

        public GenTableColumnWrapper getInstance() {
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
    public LambdaQueryWrapper<GenTableColumnDO> lambdaQuery(GenTableColumnDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<GenTableColumnDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(Objects.nonNull(entity.getId()), GenTableColumnDO::getId, entity.getId())
                .eq(Objects.nonNull(entity.getTableId()), GenTableColumnDO::getTableId, entity.getTableId())
                .eq(StringUtils.hasText(entity.getColumnName()), GenTableColumnDO::getColumnName, entity.getColumnName())
                .eq(!ObjectUtils.isEmpty(entity.getColumnLength()), GenTableColumnDO::getColumnLength, entity.getColumnLength())
                .eq(StringUtils.hasText(entity.getColumnCodeName()), GenTableColumnDO::getColumnCodeName, entity.getColumnCodeName())
                .eq(StringUtils.hasText(entity.getColumnComment()), GenTableColumnDO::getColumnComment, entity.getColumnComment())
                .eq(StringUtils.hasText(entity.getColumnDbType()), GenTableColumnDO::getColumnDbType, entity.getColumnDbType())
                .eq(StringUtils.hasText(entity.getColumnPk()), GenTableColumnDO::getColumnPk, entity.getColumnPk())
                .eq(StringUtils.hasText(entity.getColumnList()), GenTableColumnDO::getColumnList, entity.getColumnList())
                .eq(StringUtils.hasText(entity.getColumnOperation()), GenTableColumnDO::getColumnOperation, entity.getColumnOperation())
                .eq(StringUtils.hasText(entity.getColumnQuery()), GenTableColumnDO::getColumnQuery, entity.getColumnQuery())
                .eq(StringUtils.hasText(entity.getColumnRequired()), GenTableColumnDO::getColumnRequired, entity.getColumnRequired())
                .eq(!ObjectUtils.isEmpty(entity.getColumnSort()), GenTableColumnDO::getColumnSort, entity.getColumnSort())
        ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    public LambdaUpdateWrapper<GenTableColumnDO> lambdaUpdate(GenTableColumnDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<GenTableColumnDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(GenTableColumnDO::getTableId, entity.getTableId())
                .set(GenTableColumnDO::getColumnName, entity.getColumnName())
                .set(GenTableColumnDO::getColumnLength, entity.getColumnLength())
                .set(GenTableColumnDO::getColumnCodeName, entity.getColumnCodeName())
                .set(GenTableColumnDO::getColumnComment, entity.getColumnComment())
                .set(GenTableColumnDO::getColumnDbType, entity.getColumnDbType())
                .set(GenTableColumnDO::getColumnPk, entity.getColumnPk())
                .set(GenTableColumnDO::getColumnList, entity.getColumnList())
                .set(GenTableColumnDO::getColumnOperation, entity.getColumnOperation())
                .set(GenTableColumnDO::getColumnQuery, entity.getColumnQuery())
                .set(GenTableColumnDO::getColumnRequired, entity.getColumnRequired())
                .set(GenTableColumnDO::getColumnSort, entity.getColumnSort())
        ;
    }


}
