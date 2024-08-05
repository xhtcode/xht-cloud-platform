package com.xht.cloud.generate.utils;

import com.xht.cloud.generate.constant.enums.DataBaseType;
import com.xht.cloud.generate.module.database.domain.dataobject.GenDatabaseDO;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;

/**
 * 描述 ：JDBC工具类
 * <pre> </pre>
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@Slf4j
public class JDBCUtils {

    private static final int CONNECTION_TIMEOUTS_SECONDS = 6;

    /**
     * 1.私有构造方法
     */
    private JDBCUtils() {
    }

    /**
     * 获得数据库连接
     */
    public static Connection getConnection(GenDatabaseDO database) {
        try {
            DriverManager.setLoginTimeout(CONNECTION_TIMEOUTS_SECONDS);
            DataBaseType byDbType = DataBaseType.getByDbType(database.getDbType());
            Class.forName(byDbType.getDriverClass());
            Connection connection = DriverManager.getConnection(database.getDbUrl(), database.getUserName(), database.getPassWord());
            log.info("{} 连接成功 数据库类型:`{}` url:`{}`", database.getConnName(), database.getDbType(), database.getDbUrl());
            return connection;
        } catch (Exception e) {
            log.debug("连接出错了 e={}", e.getMessage(), e);
            throw new RuntimeException("数据库链接失败");
        }
    }

    public static JdbcTemplate jdbcTemplate(GenDatabaseDO database) {
        DataBaseType byDbType = DataBaseType.getByDbType(database.getDbType());
        HikariDataSource datasource = new HikariDataSource();
        datasource.setJdbcUrl(database.getDbUrl());
        datasource.setUsername(database.getUserName());
        datasource.setPassword(database.getPassWord());
        datasource.setDriverClassName(byDbType.getDriverClass());
        return new JdbcTemplate(datasource);
    }

    /**
     * 5.查询操作时所使用的的释放资源方法
     *
     * @param conn Connection
     * @param st   Statement
     * @param rs   ResultSet
     */
    public static void close(Connection conn, Statement st, ResultSet rs) {
        close(conn, st);
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 5.增删改操作时所使用的的释放资源方法
     *
     * @param conn Connection
     * @param st   Statement
     */
    public static void close(Connection conn, Statement st) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
