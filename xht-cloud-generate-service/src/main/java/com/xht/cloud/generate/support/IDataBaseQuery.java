package com.xht.cloud.generate.support;

import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import com.xht.cloud.generate.module.database.domain.dataobject.GenDatabaseDO;
import com.xht.cloud.generate.module.table.domain.dataobject.GenTableDO;

import java.util.List;

/**
 * 描述 ：获取数据库信息
 * <pre> </pre>
 *
 * @author 小糊涂
 * @version : 1.0
 **/
public interface IDataBaseQuery {

    /**
     * 根据表名查询据单表信息
     *
     * @param databaseDO 数据库连接信息
     * @param tableName  表名
     * @return {@link GenTableDO}
     */
    GenTableDO selectTableByTableName(GenDatabaseDO databaseDO, String tableName);

    /**
     * 根据表名查询据多表信息
     *
     * @param databaseDO 数据库连接信息
     * @param tableName  表名
     * @return {@link GenTableDO}
     */
    List<GenTableDO> selectListTableByLikeTableName(GenDatabaseDO databaseDO, String tableName);


    /**
     * 根据业务描述查询字段信息
     *
     * @param GenDatabaseDO 数据库连接信息
     * @param tableName     业务描述
     * @return 字段信息
     */
    List<GenTableColumnDO> selectTableColumnsByTableName(GenDatabaseDO GenDatabaseDO, String tableName);


    /**
     * 数据库类型选择serice
     *
     * @param dbType 数据库类型
     * @return Boolean
     */
    Boolean support(String dbType);

}
