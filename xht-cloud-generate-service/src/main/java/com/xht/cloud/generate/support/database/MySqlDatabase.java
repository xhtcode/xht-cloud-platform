package com.xht.cloud.generate.support.database;

import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import com.xht.cloud.generate.module.database.domain.dataobject.GenDatabaseDO;
import com.xht.cloud.generate.module.table.domain.dataobject.GenTableDO;
import com.xht.cloud.generate.support.IDataBaseQuery;
import com.xht.cloud.generate.utils.JDBCUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述 ：
 * <pre> </pre>
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@Slf4j
@Component
public class MySqlDatabase extends AbstractDataBaseQuery implements IDataBaseQuery {


    /**
     * 根据表名查询据单表信息
     *
     * @param databaseDO 数据库连接信息
     * @param tableName  表名
     * @return {@link GenTableDO}
     */
    @Override
    @SuppressWarnings("all")
    public GenTableDO selectTableByTableName(JdbcTemplate jdbcTemplate,GenDatabaseDO databaseDO, String tableName) {

        try {
            return jdbcTemplate.queryForObject(getTableEqQuery(tableName), rowTableMapper(), tableName);
        } catch (Exception e) {
            log.info("{}出错了", databaseDO.getConnName(), e);
        }
        return null;
    }

    @Override
    @SuppressWarnings("all")
    public List<GenTableDO> selectListTableByLikeTableName(GenDatabaseDO databaseDO, String tableName) {
        JdbcTemplate jdbcTemplate = JDBCUtils.jdbcTemplate(databaseDO);
        try {
            return jdbcTemplate.query(getTableLikeQuery(tableName), rowTableMapper());
        } catch (Exception e) {
            log.info("{}出错了", databaseDO.getConnName(), e);
            throw new RuntimeException("表数据获取错误!");
        }
    }

    @Override
    @SuppressWarnings("all")
    public List<GenTableColumnDO> selectTableColumnsByTableName(JdbcTemplate jdbcTemplate,GenDatabaseDO databaseDO, String tableName) {
        try {
            return jdbcTemplate.query(getColumnQuery(tableName), rowColumnMapper(), tableName);
        } catch (Exception e) {
            log.info("{}出错了", databaseDO.getConnName(), e);
            throw new RuntimeException("字段获取错误!");
        }
    }

    /**
     * 数据库类型选择
     *
     * @param dbType 数据库类型
     * @return Boolean
     */
    @Override
    public Boolean support(String dbType) {
        return "mysql".equalsIgnoreCase(dbType);
    }


    /**
     * 根据表名模糊查询
     *
     * @param tableName 表名
     * @return sql
     */
    @Override
    public String getTableLikeQuery(String tableName) {
        StringBuilder sql = new StringBuilder("select table_schema table_schema,table_name,engine table_engine,table_comment table_msg,create_time table_create_time,update_time table_update_time from information_schema.tables ");
        sql.append(" where table_schema = (select database()) ");
        if (StringUtils.hasText(tableName)) {
            sql.append(" and table_name  like '");
            sql.append(getColumnLike(tableName));
            sql.append("' ");
        }
        sql.append("order by create_time desc,update_time desc");
        return sql.toString();
    }

    /**
     * 根据表名查询
     *
     * @param tableName 表名
     * @return sql
     */
    @Override
    public String getTableEqQuery(String tableName) {
        return """
                SELECT
                	table_schema table_schema,
                	table_name,
                	ENGINE table_engine,
                	table_comment table_msg,
                	create_time table_create_time,
                	update_time table_update_time
                FROM
                	information_schema.TABLES
                WHERE
                	table_schema = (
                	SELECT DATABASE
                	())
                	AND table_name = ?
                """;
    }

    /**
     * 根据表名查询字段
     *
     * @param tableName 表名
     * @return sql
     */
    @Override
    protected String getColumnQuery(String tableName) {
        return """
                SELECT
                	table_name,
                	table_schema,
                	column_name,
                    ( CASE WHEN is_nullable = 'no' THEN '1' ELSE '0' END ) AS is_required,
                	( CASE WHEN column_key = 'PRI' THEN '1' ELSE '0' END ) AS is_pk,
                	ordinal_position AS sort,
                	column_comment,
                	data_type AS column_db_type,
                	CHARACTER_MAXIMUM_LENGTH AS column_length
                FROM
                	information_schema.COLUMNS
                WHERE
                	table_schema = (
                	SELECT DATABASE())
                	AND table_name = ?
                ORDER BY
                	ordinal_position
                """;
    }
}
