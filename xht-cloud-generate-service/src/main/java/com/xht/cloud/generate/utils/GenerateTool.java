package com.xht.cloud.generate.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.cloud.framework.utils.jackson.JsonUtils;
import com.xht.cloud.generate.constant.GenerateConstant;
import com.xht.cloud.generate.constant.dto.ConfigTreeDTO;
import com.xht.cloud.generate.core.controller.request.GenCodeRequest;
import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import com.xht.cloud.generate.module.database.domain.dataobject.GenDatabaseDO;
import com.xht.cloud.generate.module.table.domain.dataobject.GenTableDO;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 描述 ：代码生成器格式化
 *
 * @author 小糊涂
 **/
public final class GenerateTool {
    private static final Snowflake snowflake = IdUtil.getSnowflake();

    public static String initCode(VelocityContext velocityContext, String telName, String code) {
        if (!StringUtils.hasText(telName) || !StringUtils.hasText(code)) return "";
        StringWriter stringWriter = new StringWriter();
        Velocity.evaluate(velocityContext, stringWriter, telName, code);
        return stringWriter.toString();
    }

    public static String initCodeFilePath(GenCodeRequest genCodeRequest, GenTableDO genTableDO, String filePath) {
        Map<String, String> map = new HashMap<>();
        map.put("projectName", genCodeRequest.getProjectName());
        map.put("packageName", StrUtil.replace(genCodeRequest.getPackageName(), ".", File.separator));
        map.put("moduleLabel", genTableDO.getModuleLabel());
        map.put("moduleName", genTableDO.getModuleName());
        map.put("className", genTableDO.getCodeName());
        return StrFormatter.format(filePath, map, false);
    }


    public static VelocityContext initVelocityContext(GenCodeRequest genCodeRequest, GenDatabaseDO databaseDO, GenTableDO genTableDO) {
        VelocityContext ctx = new VelocityContext();
        ctx.put("projectName", genCodeRequest.getProjectName());
        ctx.put("packageName", genCodeRequest.getPackageName());
        ctx.put("author", genCodeRequest.getAuthor());
        ctx.put("date", DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN));
        for (int i = 0; i < 20; i++) {
            ctx.put("id" + i, snowflake.nextId());
        }
        ctx.put("database", databaseDO);
        ctx.put("tableSchema", genTableDO.getTableSchema());
        ctx.put("tableEngine", genTableDO.getTableEngine());
        ctx.put("tableName", genTableDO.getTableName());
        ctx.put("moduleLabel", genTableDO.getModuleLabel());
        ctx.put("moduleValue", genTableDO.getModuleValue());
        ctx.put("moduleName", genTableDO.getModuleName());
        ctx.put("pathUrl", genTableDO.getPathUrl());
        ctx.put("className", genTableDO.getCodeName());
        ctx.put("classNameFirstLower", StrUtil.lowerFirst(genTableDO.getCodeName()));
        ctx.put("genType", genTableDO.getGenType());
        ctx.put("perms", genTableDO.getAuthorizationPrefix());
        ctx.put("menuId", genTableDO.getMenuId());
        ctx.put("lombok", Objects.equals(GenerateConstant.SUCCESS_STATUS, genTableDO.getLombok()));
        ctx.put("jsr303", Objects.equals(GenerateConstant.SUCCESS_STATUS, genTableDO.getJsr303()));
        ctx.put("authorization", Objects.equals(GenerateConstant.SUCCESS_STATUS, genTableDO.getAuthorization()));
        ctx.put("swagger", Objects.equals(GenerateConstant.SUCCESS_STATUS, genTableDO.getSwagger()));
        ctx.put("parentId", genTableDO.getParentId());
        ctx.put("parentName", genTableDO.getParentName());
        ctx.put("tableCreateTime", genTableDO.getTableCreateTime());
        ctx.put("tableUpdateTime", genTableDO.getTableUpdateTime());
        return ctx;
    }

    public static void initVelocityContext(VelocityContext ctx, List<GenTableColumnDO> columnDOS, String ignoreField) {
        for (GenTableColumnDO columnDO : columnDOS) {
            columnDO.setColumnNameGet(String.format("get%s", StrUtil.upperFirst(columnDO.getColumnCodeName())));
            columnDO.setColumnNameSet(String.format("set%s", StrUtil.upperFirst(columnDO.getColumnCodeName())));
            if (!Objects.equals("String", columnDO.getColumnJavaType())) {
                columnDO.setConvertMethod("!ObjectUtils.isEmpty");
            } else {
                columnDO.setConvertMethod("StringUtils.hasText");
            }
        }
        ctx.put("pkColumn", columnDOS.stream().filter(item -> item.getColumnPk().equals("1")).findFirst().orElse(new GenTableColumnDO()));
        List<GenTableColumnDO> columns = filterCustom(columnDOS, ignoreField);
        ctx.put("columns", columns);
        ctx.put("listColumns", filterList(columns));
        ctx.put("queryColumns", filterQuery(columns));
        ctx.put("editColumns", filterEdit(columns));
        ctx.put("saveColumns", filterSave(columns));
        ctx.put("nullableColumns", filterNullable(columns));
        ctx.put("queryAndEditColumns", filterQueryAndEdit(columns));
        ctx.put("queryAndSaveColumns", filterQueryAndSave(columns));
        ctx.put("editAndSaveColumns", filterEditAndSave(columns));
        ctx.put("queryAndEditDifferenceSetColumns", filterQueryAndEditDifferenceSet(columns));
        ctx.put("queryAndSaveDifferenceSetColumns", filterQueryAndSaveDifferenceSet(columns));
        ctx.put("editAndSaveDifferenceSetColumns", filterEditAndSaveDifferenceSet(columns));
        ctx.put("queryAndEditIntersectionColumns", filterQueryAndEditIntersection(columns));
        ctx.put("queryAndSaveIntersectionColumns", filterQueryAndSaveIntersection(columns));
        ctx.put("editAndSaveIntersectionColumns", filterEditAndSaveIntersection(columns));
        ctx.put("listAndEditAndSaveIntersection", filterListAndEditAndSave(columns));
    }

    /**
     * 配置信息格式化
     */
    public static List<ConfigTreeDTO> convertToConfigTreeDTO(String json) {
        List<ConfigTreeDTO> result = new ArrayList<>();
        List<Object> list = JsonUtils.toObject(json, List.class);
        if (CollectionUtils.isEmpty(list)) return result;
        for (Object o : list) {
            ConfigTreeDTO data = JsonUtils.toObject(JsonUtils.toJsonString(o), ConfigTreeDTO.class);
            result.add(data);
        }
        return result;
    }

    /**
     * 配置转化成map
     */
    public static Map<String, ConfigTreeDTO> flatArray(ConfigTreeDTO configTreeDTO) {
        Map<String, ConfigTreeDTO> result = new HashMap<>();
        flatArray(configTreeDTO, result, "");
        return result;
    }

    private static void flatArray(ConfigTreeDTO configTreeDTO, Map<String, ConfigTreeDTO> result, String path) {
        if (Objects.isNull(configTreeDTO)) {
            return;
        }
        String pathEmp = StringUtils.hasText(path) ? path + File.separator : "";
        pathEmp += configTreeDTO.getLabel();
        List<ConfigTreeDTO> children = configTreeDTO.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            result.put(pathEmp, ConfigTreeDTO.builder().id(configTreeDTO.getId()).fileType(configTreeDTO.getFileType()).label(configTreeDTO.getLabel()).templateId(configTreeDTO.getTemplateId()).build());
        } else {
            for (ConfigTreeDTO child : children) {
                flatArray(child, result, pathEmp);
            }
        }
    }

    /**
     * 获取业务名称
     *
     * @param tableName 表明
     * @return String
     */
    public static String getModuleLabel(String tableName) {
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        return StrUtil.sub(tableName, lastIndex + 1, nameLength);
    }

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
     * 基础字段
     */
    public static List<GenTableColumnDO> filter(List<GenTableColumnDO> columnList, String[] columns) {
        return columnList.stream().filter(item -> ArrayUtil.contains(columns, item.getColumnDbType().toLowerCase())).collect(Collectors.toList());
    }

    /**
     * 查询
     */
    public static List<GenTableColumnDO> filterList(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        return columnList.stream().filter(item -> Objects.equals(item.getColumnList(), GenerateConstant.SUCCESS_STATUS)).sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 查询
     */
    public static List<GenTableColumnDO> filterQuery(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        return columnList.stream().filter(item -> Objects.equals(item.getColumnQuery(), GenerateConstant.SUCCESS_STATUS)).sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 修改
     */
    public static List<GenTableColumnDO> filterEdit(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        return columnList.stream().filter(item -> Objects.equals(item.getColumnUpdate(), GenerateConstant.SUCCESS_STATUS)).sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 添加
     */
    public static List<GenTableColumnDO> filterSave(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        return columnList.stream().filter(item -> Objects.equals(item.getColumnSave(), GenerateConstant.SUCCESS_STATUS)).sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 添加
     */
    public static List<GenTableColumnDO> filterNullable(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        return columnList.stream().filter(item -> Objects.equals(item.getColumnNullable(), GenerateConstant.SUCCESS_STATUS)).sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 查询 + 修改 并集
     */
    public static Collection<GenTableColumnDO> filterQueryAndEdit(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        List<GenTableColumnDO> query = filterQuery(columnList);
        List<GenTableColumnDO> edit = filterEdit(columnList);
        return CollectionUtil.union(query, edit).stream().sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 查询 + 添加 并集
     */
    public static Collection<GenTableColumnDO> filterQueryAndSave(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        List<GenTableColumnDO> query = filterQuery(columnList);
        List<GenTableColumnDO> save = filterSave(columnList);
        return CollectionUtil.union(query, save).stream().sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 修改 + 添加 并集
     */
    public static Collection<GenTableColumnDO> filterEditAndSave(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        List<GenTableColumnDO> edit = filterEdit(columnList);
        List<GenTableColumnDO> save = filterSave(columnList);
        return CollectionUtil.union(edit, save).stream().sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 查询 + 修改 差集
     */
    public static Collection<GenTableColumnDO> filterQueryAndEditDifferenceSet(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        List<GenTableColumnDO> query = filterQuery(columnList);
        List<GenTableColumnDO> edit = filterEdit(columnList);
        return CollectionUtil.disjunction(query, edit).stream().sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 查询 + 添加 差集
     */
    public static Collection<GenTableColumnDO> filterQueryAndSaveDifferenceSet(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        List<GenTableColumnDO> query = filterQuery(columnList);
        List<GenTableColumnDO> save = filterSave(columnList);
        return CollectionUtil.disjunction(query, save).stream().sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 修改 + 添加 差集
     */
    public static Collection<GenTableColumnDO> filterEditAndSaveDifferenceSet(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        List<GenTableColumnDO> edit = filterEdit(columnList);
        List<GenTableColumnDO> save = filterSave(columnList);
        return CollectionUtil.disjunction(edit, save).stream().sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 查询 + 修改 交集
     */
    public static Collection<GenTableColumnDO> filterQueryAndEditIntersection(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        List<GenTableColumnDO> query = filterQuery(columnList);
        List<GenTableColumnDO> edit = filterEdit(columnList);
        return CollectionUtil.intersection(query, edit).stream().sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 查询 + 添加 交集
     */
    public static Collection<GenTableColumnDO> filterQueryAndSaveIntersection(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        List<GenTableColumnDO> query = filterQuery(columnList);
        List<GenTableColumnDO> save = filterSave(columnList);
        return CollectionUtil.intersection(query, save).stream().sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 修改 + 添加 交集
     */
    public static Collection<GenTableColumnDO> filterEditAndSaveIntersection(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        List<GenTableColumnDO> edit = filterEdit(columnList);
        List<GenTableColumnDO> save = filterSave(columnList);
        return CollectionUtil.intersection(edit, save).stream().sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 列表 + 修改 + 添加 并集
     */
    public static Collection<GenTableColumnDO> filterListAndEditAndSave(List<GenTableColumnDO> columnList) {
        if (CollectionUtils.isEmpty(columnList)) return Collections.emptyList();
        List<GenTableColumnDO> list = filterList(columnList);
        Collection<GenTableColumnDO> filterEditAndSaveIntersection = filterEditAndSave(columnList);
        return CollectionUtil.union(list, filterEditAndSaveIntersection).stream().sorted(Comparator.comparingInt(GenTableColumnDO::getColumnSort)).collect(Collectors.toList());
    }

    /**
     * 判断字段是否存在
     * 存在返回 {@code  GenerateConstant.ERROR_STATUS}
     * 不存在返回 {@code GenerateConstant.SUCCESS_STATUS}
     */
    public static String findColumnExits(String columnName, String... columnList) {
        return ArrayUtil.contains(columnList, columnName) ? GenerateConstant.ERROR_STATUS : GenerateConstant.SUCCESS_STATUS;
    }

    public static String getAuthorizationPrefix(String tableName) {
        String s = StrUtil.replaceFirst(tableName, "_", ":");
        return StrUtil.replace(s, "_", "-");
    }
}
