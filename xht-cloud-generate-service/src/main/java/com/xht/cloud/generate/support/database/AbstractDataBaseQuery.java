package com.xht.cloud.generate.support.database;

import cn.hutool.core.convert.Convert;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import com.xht.cloud.generate.module.table.domain.dataobject.GenTableDO;
import org.springframework.jdbc.core.RowMapper;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public abstract class AbstractDataBaseQuery {

    /**
     * 根据表名模糊查询
     *
     * @param tableName 表名
     * @return sql
     */
    protected abstract String getTableLikeQuery(String tableName);

    /**
     * 根据表名查询
     *
     * @param tableName 表名
     * @return sql
     */
    protected abstract String getTableEqQuery(String tableName);

    /**
     * 根据表名查询字段
     *
     * @param tableName 表名
     * @return sql
     */
    protected abstract String getColumnQuery(String tableName);

    protected String getColumnLike(String column) {
        if (!StringUtils.hasText(column)) {
            return String.join("gen_", "%", "%");
        }
        return String.join(column, "%", "%");
    }

    protected final RowMapper<GenTableDO> rowTableMapper() {
        return (resultSet, rowNum) -> {
            GenTableDO entity = new GenTableDO();
            entity.setTableSchema(resultSet.getString("table_schema"));
            entity.setTableName(resultSet.getString("table_name"));
            entity.setTableEngine(resultSet.getString("table_engine"));
            entity.setServiceDesc(resultSet.getString("table_msg"));
            entity.setTableCreateTime(Convert.toLocalDateTime(resultSet.getDate("table_create_time")));
            entity.setTableUpdateTime(Convert.toLocalDateTime(resultSet.getDate("table_update_time")));
            return entity;
        };
    }


    protected final RowMapper<GenTableColumnDO> rowColumnMapper() {
        return (resultSet, rowNum) -> {
            GenTableColumnDO entity = new GenTableColumnDO();
            entity.setColumnName(resultSet.getString("column_name"));
            entity.setColumnPk(resultSet.getString("is_pk"));
            entity.setColumnRequired(resultSet.getString("is_required"));
            entity.setColumnSort(resultSet.getInt("sort"));
            entity.setColumnComment(StringUtils.emptyToDefault(resultSet.getString("column_comment"), "暂无"));
            entity.setColumnDbType(resultSet.getString("column_db_type"));
            entity.setColumnLength(resultSet.getLong("column_length") > 4000 ? -1 : resultSet.getInt("column_length"));
            return entity;
        };
    }
}
