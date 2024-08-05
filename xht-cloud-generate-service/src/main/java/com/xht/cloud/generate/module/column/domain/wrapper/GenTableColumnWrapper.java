package com.xht.cloud.generate.module.column.domain.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
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
                .eq(StringUtils.hasText(entity.getId()), GenTableColumnDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getTableId()), GenTableColumnDO::getTableId, entity.getTableId())
                .eq(StringUtils.hasText(entity.getTableSchema()), GenTableColumnDO::getTableSchema, entity.getTableSchema())
                .eq(StringUtils.hasText(entity.getTableName()), GenTableColumnDO::getTableName, entity.getTableName())
                .eq(StringUtils.hasText(entity.getColumnName()), GenTableColumnDO::getColumnName, entity.getColumnName())
                .eq(!ObjectUtils.isEmpty(entity.getColumnLength()), GenTableColumnDO::getColumnLength, entity.getColumnLength())
                .eq(StringUtils.hasText(entity.getColumnCodeName()), GenTableColumnDO::getColumnCodeName, entity.getColumnCodeName())
                .eq(StringUtils.hasText(entity.getColumnComment()), GenTableColumnDO::getColumnComment, entity.getColumnComment())
                .eq(StringUtils.hasText(entity.getColumnDbType()), GenTableColumnDO::getColumnDbType, entity.getColumnDbType())
                .eq(StringUtils.hasText(entity.getColumnJavaType()), GenTableColumnDO::getColumnJavaType, entity.getColumnJavaType())
                .eq(StringUtils.hasText(entity.getColumnTsType()), GenTableColumnDO::getColumnTsType, entity.getColumnTsType())
                .eq(StringUtils.hasText(entity.getColumnExample()), GenTableColumnDO::getColumnExample, entity.getColumnExample())
                .eq(StringUtils.hasText(entity.getColumnPk()), GenTableColumnDO::getColumnPk, entity.getColumnPk())
                .eq(StringUtils.hasText(entity.getColumnList()), GenTableColumnDO::getColumnList, entity.getColumnList())
                .eq(StringUtils.hasText(entity.getColumnSave()), GenTableColumnDO::getColumnSave, entity.getColumnSave())
                .eq(StringUtils.hasText(entity.getColumnUpdate()), GenTableColumnDO::getColumnUpdate, entity.getColumnUpdate())
                .eq(StringUtils.hasText(entity.getColumnQuery()), GenTableColumnDO::getColumnQuery, entity.getColumnQuery())
                .eq(StringUtils.hasText(entity.getColumnNullable()), GenTableColumnDO::getColumnNullable, entity.getColumnNullable())
                .eq(StringUtils.hasText(entity.getColumnDict()), GenTableColumnDO::getColumnDict, entity.getColumnDict())
                .eq(StringUtils.hasText(entity.getColumnHtml()), GenTableColumnDO::getColumnHtml, entity.getColumnHtml())
                .eq(!ObjectUtils.isEmpty(entity.getColumnSort()), GenTableColumnDO::getColumnSort, entity.getColumnSort())
                .eq(!ObjectUtils.isEmpty(entity.getColumnQueryType()), GenTableColumnDO::getColumnQueryType, entity.getColumnQueryType())
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
                .set(GenTableColumnDO::getTableSchema, entity.getTableSchema())
                .set(GenTableColumnDO::getTableName, entity.getTableName())
                .set(GenTableColumnDO::getColumnName, entity.getColumnName())
                .set(GenTableColumnDO::getColumnLength, entity.getColumnLength())
                .set(GenTableColumnDO::getColumnCodeName, entity.getColumnCodeName())
                .set(GenTableColumnDO::getColumnComment, entity.getColumnComment())
                .set(GenTableColumnDO::getColumnDbType, entity.getColumnDbType())
                .set(GenTableColumnDO::getColumnJavaType, entity.getColumnJavaType())
                .set(GenTableColumnDO::getColumnTsType, entity.getColumnTsType())
                .set(GenTableColumnDO::getColumnExample, entity.getColumnExample())
                .set(GenTableColumnDO::getColumnPk, entity.getColumnPk())
                .set(GenTableColumnDO::getColumnList, entity.getColumnList())
                .set(GenTableColumnDO::getColumnSave, entity.getColumnSave())
                .set(GenTableColumnDO::getColumnUpdate, entity.getColumnUpdate())
                .set(GenTableColumnDO::getColumnQuery, entity.getColumnQuery())
                .set(GenTableColumnDO::getColumnNullable, entity.getColumnNullable())
                .set(GenTableColumnDO::getColumnDict, entity.getColumnDict())
                .set(GenTableColumnDO::getColumnHtml, entity.getColumnHtml())
                .set(GenTableColumnDO::getColumnSort, entity.getColumnSort())
                .set(GenTableColumnDO::getColumnQueryType, entity.getColumnQueryType())
        ;
    }


}
