package com.xht.cloud.framework.mybatis.interceptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.db.sql.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * 描述 ：用于输出每条 SQL 语句及其执行时间
 * 会执行错误
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@Slf4j
@Deprecated
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class SqlLogInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        Object[] args = invocation.getArgs();
        String sqlId = mappedStatement.getId();
        BoundSql boundSql;
        if (args.length == 4 || args.length == 2) {
            boundSql = mappedStatement.getBoundSql(parameter);
        } else {
            // 几乎不可能走进这里面,除非使用Executor的代理对象调用query[args[6]]
            boundSql = (BoundSql) args[5];
        }
        Configuration configuration = mappedStatement.getConfiguration();

        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = invocation.proceed();
        } catch (Exception e) {
            log.info("执行错误 {}", e.getMessage(), e);
        } finally {
            try {
                long sqlCostTime = System.currentTimeMillis() - startTime;
                String sql = getSql(configuration, boundSql);
                formatSqlLog(mappedStatement.getSqlCommandType(), sqlId, sql, sqlCostTime, result);
            } catch (Exception exception) {
                log.info("SQL插件执行失败 Mapper:{} 参数对象：{}", sqlId, boundSql.getParameterObject(), exception);
            }
        }
        return result;
    }

    private String getSql(Configuration configuration, BoundSql boundSql) {
        // 输入sql字符串空判断
        String sql = boundSql.getSql();
        if (!StringUtils.hasText(sql)) {
            return "";
        }

        //去掉换行符
        sql = sql.replaceAll("[\\s\n ]+", " ");

        //填充占位符, 目前基本不用mybatis存储过程调用,故此处不做考虑
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (!parameterMappings.isEmpty() && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = this.replacePlaceholder(sql, parameterObject);
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = replacePlaceholder(sql, obj);
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = replacePlaceholder(sql, obj);
                    }
                }
            }
        }
        return sql;
    }


    private String replacePlaceholder(String sql, Object parameterObject) {
        return sql.replaceFirst("\\?", String.format("'%s'", Convert.toStr(parameterObject, "NULL")));
    }

    private void formatSqlLog(SqlCommandType sqlCommandType, String sqlId, String sql, long costTime, Object obj) {
        if (sql.contains("oauth2_registered_client")) {
            return;
        }
        final StringBuilder sqlBuffer = new StringBuilder();
        sqlBuffer.append("\n==========================================  Sql Start  ==========================================");
        sqlBuffer.append("\nExecute ID  ：{}");
        sqlBuffer.append("\nExecute SQL ：\n\n{}");
        sqlBuffer.append("\n\nExecute Time：{} ms");
        if (sqlCommandType == SqlCommandType.UPDATE || sqlCommandType == SqlCommandType.INSERT || sqlCommandType == SqlCommandType.DELETE) {
            sqlBuffer.append("\n影响行数：{}");
            String format = StrFormatter.format(sqlBuffer.toString(), sqlId, SqlUtil.formatSql(sql), costTime, obj);
            System.err.println(format);
        }
        if (sqlCommandType == SqlCommandType.SELECT) {
            sqlBuffer.append("\n结果行数：{}");
            Collection<?> obj1 = (Collection<?>) obj;
            String format = StrFormatter.format(sqlBuffer.toString(), sqlId, SqlUtil.formatSql(sql), costTime, CollectionUtils.isEmpty(obj1) ? 0 : obj1.size());
            System.err.println(format);
        }
        System.err.println("\n==========================================  Sql  End   ==========================================\n");
    }

}
