package com.xht.cloud.generate.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.cloud.generate.constant.GenerateConstant;
import com.xht.cloud.generate.core.controller.request.GenCodeRequest;
import com.xht.cloud.generate.core.mananger.ColumnTypeManager;
import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import com.xht.cloud.generate.module.database.domain.dataobject.GenDatabaseDO;
import com.xht.cloud.generate.module.filedisk.domain.dataobject.GenFileDiskDO;
import com.xht.cloud.generate.module.table.domain.dataobject.GenTableDO;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

import static com.xht.cloud.framework.core.constant.StringConstant.EMPTY_STR;
import static com.xht.cloud.generate.constant.GenerateConstant.*;

/**
 * 描述 ：代码生成器格式化
 *
 * @author 小糊涂
 **/
public final class GenerateTool {

    public static String initCode(VelocityContext velocityContext, String telName, String code) {
        if (!StringUtils.hasText(telName) || !StringUtils.hasText(code)) return "";
        StringWriter stringWriter = new StringWriter();
        Velocity.evaluate(velocityContext, stringWriter, telName, code);
        return stringWriter.toString();
    }

    public static String generateCodeFilePath(GenCodeRequest genCodeRequest, GenTableDO genTableDO, String filePath) {
        Map<String, String> map = new HashMap<>();
        map.put("projectName", genCodeRequest.getProjectName());
        map.put("packageName", genCodeRequest.getPackageName());
        map.put("moduleName", genTableDO.getModuleName());
        map.put("serviceName", genTableDO.getServiceName());
        map.put("fileName", genTableDO.getCodeName());
        return StrFormatter.format(filePath, map, false);
    }

    /**
     * 初始化模板数据信息
     *
     * @param genCodeRequest {@link GenCodeRequest }生成代码请求
     * @param databaseDO     {@link GenDatabaseDO} 数据源信息
     * @param genTableDO     {@link GenTableDO } 数据表信息
     * @return 模板所需数据
     */
    public static VelocityContext initVelocityContext(GenCodeRequest genCodeRequest, GenDatabaseDO databaseDO, GenTableDO genTableDO) {
        VelocityContext ctx = new VelocityContext();
        ctx.put("database", databaseDO);
        ctx.put("projectName", genCodeRequest.getProjectName());
        ctx.put("packageName", genCodeRequest.getPackageName());
        ctx.put("author", genCodeRequest.getAuthor());
        ctx.put("nowDate", DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN));
        ctx.put("tableSchema", genTableDO.getTableSchema());
        ctx.put("tableEngine", genTableDO.getTableEngine());
        ctx.put("tableName", genTableDO.getTableName());
        ctx.put("moduleName", genTableDO.getModuleName());
        ctx.put("serviceName", genTableDO.getServiceName());
        ctx.put("serviceDesc", genTableDO.getServiceDesc());
        ctx.put("pathUrl", genTableDO.getPathUrl());
        ctx.put("className", genTableDO.getCodeName());
        ctx.put("classNameFirstLower", StrUtil.lowerFirst(genTableDO.getCodeName()));
        ctx.put("perms", genTableDO.getAuthorizationPrefix());
        ctx.put("tableCreateTime", genTableDO.getTableCreateTime());
        ctx.put("tableUpdateTime", genTableDO.getTableUpdateTime());
        return ctx;
    }

    public static void addColumnInfo(VelocityContext ctx, List<GenTableColumnDO> columnDOS, GenFileDiskDO genFileDiskDO, ColumnTypeManager columnTypeManager) {
        List<GenTableColumnDO> columns = filterCustom(columnDOS, genFileDiskDO.getIgnoreField());
        for (GenTableColumnDO columnDO : columns) {
            columnDO.setColumnNameGet(String.format("get%s", StrUtil.upperFirst(columnDO.getColumnCodeName())));
            columnDO.setColumnNameSet(String.format("set%s", StrUtil.upperFirst(columnDO.getColumnCodeName())));
            columnDO.setTsName(columnTypeManager.getTsType(columnDO.getColumnDbType()));
            columnDO.setJavaName(columnTypeManager.getJavaType(columnDO.getColumnDbType()));
        }
        ctx.put("fileName", genFileDiskDO.getFileName());
        ctx.put("fileDesc", genFileDiskDO.getFileDesc());
        ctx.put("filePath", genFileDiskDO.getFilePath());
        String packageName = StrUtil.replace(genFileDiskDO.getFileCodePath(), PATH_SEPARATOR, POINT);
        String fileCodePath = StrUtil.replaceFirst(packageName, POINT, EMPTY_STR);
        ctx.put("fileCodePath", fileCodePath);
        ctx.put("fileCodePath2", StrUtil.replace(fileCodePath, POINT, PATH_SEPARATOR));
        ctx.put("pkColumn", columns.stream().filter(item -> item.getColumnPk().equals("1")).findFirst().orElse(new GenTableColumnDO()));
        ctx.put("columns", columns);//所有数据
        ctx.put("listColumns", filterList(columns));//列表展示数据
        ctx.put("queryColumns", filterQuery(columns));//查询数据
        ctx.put("operationColumns", filterOperation(columns));//增加、修改数据
        ctx.put("requiredColumns", filterRequired(columns));//必填数据
    }

    /**
     * 获取模块名称
     *
     * @param tableName 表明
     * @return String
     */
    public static String getModuleName(String tableName) {
        int lastIndex = tableName.indexOf("_");
        int nameLength = tableName.length();
        return StrUtil.sub(tableName, 0, lastIndex > 0 ? lastIndex : nameLength);
    }

    /**
     * 获取类名
     *
     * @param tableName 表名
     * @return 类 名
     */
    public static String getClassName(String tableName) {
        return StrUtil.upperFirst(StrUtil.toCamelCase(tableName));
    }

    /**
     * 获取 controller地址前缀
     *
     * @param tableName 表名
     * @return controller地址前缀
     */
    public static String getPathUrl(String tableName) {
        String url = StrUtil.replace(tableName, "_", "/");
        return StrUtil.addPrefixIfNot(url, "/");
    }

    /**
     * 获取类名
     *
     * @param tableName 表名
     * @return 类 名
     */
    public static String getColumnName(String tableName) {
        return StrUtil.lowerFirst(StrUtil.toCamelCase(tableName));
    }

    /**
     * 自定义字段
     */
    public static List<GenTableColumnDO> filterCustom(List<GenTableColumnDO> columnList, String columns) {
        if (!StringUtils.hasText(columns)) {
            return columnList;
        }
        Set<String> columnNameList = StrUtil.split(columns, ",").stream().map(String::toLowerCase).collect(Collectors.toSet());
        return columnList.stream().filter(item -> !columnNameList.contains(item.getColumnName().toLowerCase())).collect(Collectors.toList());
    }

    /**
     * 查询
     */
    public static List<GenTableColumnDO> filterList(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        return columnList.stream().filter(item -> Objects.equals(item.getColumnList(), SUCCESS_STATUS)).sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 查询
     */
    public static List<GenTableColumnDO> filterQuery(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        return columnList.stream().filter(item -> Objects.equals(item.getColumnQuery(), SUCCESS_STATUS)).sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 修改增加的字段
     */
    public static List<GenTableColumnDO> filterOperation(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        return columnList.stream().filter(item -> Objects.equals(item.getColumnOperation(), SUCCESS_STATUS) && !Objects.equals(item.getColumnPk(), SUCCESS_STATUS)).sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }


    /**
     * 添加
     */
    public static List<GenTableColumnDO> filterRequired(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        return columnList.stream().filter(item -> Objects.equals(item.getColumnRequired(), SUCCESS_STATUS)).sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 判断字段是否存在
     * 存在返回 {@code  GenerateConstant.ERROR_STATUS}
     * 不存在返回 {@code GenerateConstant.SUCCESS_STATUS}
     */
    public static String findColumnExits(String columnName, String... columnList) {
        return ArrayUtil.contains(columnList, columnName) ? GenerateConstant.ERROR_STATUS : SUCCESS_STATUS;
    }

    public static String getAuthorizationPrefix(String tableName) {
        String s = StrUtil.replaceFirst(tableName, "_", ":");
        return StrUtil.replace(s, "_", "-");
    }
}
