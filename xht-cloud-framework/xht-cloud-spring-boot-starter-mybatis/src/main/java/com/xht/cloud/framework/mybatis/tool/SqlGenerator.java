package com.xht.cloud.framework.mybatis.tool;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.util.List;
import java.util.Objects;

/**
 * 描述 ：数据权限数据拼装类
 *
 * @author 小糊涂
 **/
public class SqlGenerator {

    public static String generateInClause(String fieldName, List<String> dataScope) {
        if (Objects.isNull(dataScope) || dataScope.isEmpty()) {
            return "";
        } else if (dataScope.size() == 1) {
            return " fieldName = " + dataScope.get(0);
        }
        StringBuilder sb = new StringBuilder(fieldName);
        sb.append(" IN ( ");
        for (int i = 0; i < dataScope.size(); i++) {
            sb.append("'").append(dataScope.get(i)).append("'");
            if (i != dataScope.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static <T> void generateInClause(SFunction<T, ?> deptField, List<String> dataScope, LambdaQueryWrapper<T> queryWrapper) {
        if (Objects.isNull(queryWrapper)) {
            throw new RuntimeException(" queryWrapper is null!");
        }
        if (Objects.nonNull(dataScope) && dataScope.size() == 1) {
            queryWrapper.eq(deptField, dataScope.get(0));
            return;
        }
        if (!dataScope.isEmpty()) {
            queryWrapper.in(deptField, dataScope);
        }
    }

    public static <T> void generateInClause(String field, List<String> dataScope, QueryWrapper<T> queryWrapper) {
        if (Objects.isNull(queryWrapper)) {
            throw new RuntimeException(" queryWrapper is null!");
        }
        if (Objects.nonNull(dataScope) && dataScope.size() == 1) {
            queryWrapper.eq(field, dataScope.get(0));
            return;
        }
        if (!dataScope.isEmpty()) {
            queryWrapper.in(field, dataScope);
        }
    }
}
