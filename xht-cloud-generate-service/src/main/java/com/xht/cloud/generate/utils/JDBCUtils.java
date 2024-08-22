package com.xht.cloud.generate.utils;

import com.xht.cloud.generate.constant.enums.DataBaseType;
import com.xht.cloud.generate.module.database.domain.dataobject.GenDatabaseDO;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Objects;

/**
 * 描述 ：JDBC工具类
 * <pre> </pre>
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@Slf4j
public class JDBCUtils {

    @Getter
    private final JdbcTemplate jdbcTemplate;

    private final HikariDataSource dataSource;

    public JDBCUtils(JdbcTemplate jdbcTemplate, HikariDataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    public static JDBCUtils jdbcTemplate(GenDatabaseDO database) {
        DataBaseType byDbType = DataBaseType.getByDbType(database.getDbType());
        HikariDataSource datasource = new HikariDataSource();
        datasource.setConnectionTestQuery("SELECT 1");
        datasource.setConnectionTimeout(60000);
        datasource.setMinimumIdle(2);
        datasource.setMaximumPoolSize(100);
        datasource.setMaxLifetime(600000);
        datasource.setValidationTimeout(5000);
        datasource.setIdleTimeout(300000);
        datasource.setLeakDetectionThreshold(500000);
        datasource.setJdbcUrl(database.getDbUrl());
        datasource.setUsername(database.getUserName());
        datasource.setPassword(database.getPassWord());
        datasource.setDriverClassName(byDbType.getDriverClass());
        return new JDBCUtils(new JdbcTemplate(datasource), datasource);
    }

    public void close() {
        if (Objects.nonNull(this.dataSource)) {
            try {
                this.dataSource.close();
            } catch (Exception ignored) {

            }
        }
    }

}
