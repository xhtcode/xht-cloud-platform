//package com.xht.cloud.service.generate.utils;
//
//import cn.hutool.core.date.LocalDateTimeUtil;
//import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
//import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
//import com.xht.cloud.common.constant.CommonConstants;
//import com.xht.cloud.common.constant.StringConstant;
//import com.xht.cloud.common.utils.array.ArrayUtils;
//import com.xht.cloud.common.utils.spring.SpringUtils;
//import com.xht.cloud.framework.utils.support.StringUtils;
//import com.xht.cloud.service.generate.constant.GenerateConstant;
//import com.xht.cloud.service.generate.constant.enums.DbConvertType;
//import com.xht.cloud.service.generate.constant.enums.HtmlTypeEnum;
//import com.xht.cloud.service.generate.database.dataobject.GenDatabaseDO;
//import com.xht.cloud.service.generate.database.dataobject.GenTableColumnDO;
//import com.xht.cloud.service.generate.database.dataobject.GenTableDO;
//import com.xht.cloud.service.generate.domain.bo.GenInfoBo;
//import com.xht.cloud.service.generate.service.IGenCodeBaseClassService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.velocity.VelocityContext;
//import org.apache.velocity.app.Velocity;
//
//import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * <pre>
// * 描述 ：代码生成器
// *
// * </pre>
// *
// * @author 小糊涂
// * @version : 1.0
// **/
//@Slf4j
//public class GenerateUtils {
//
//    /**
//     * 主机和进程的机器码
//     */
//    private final static IdentifierGenerator IDENTIFIER_GENERATOR = new DefaultIdentifierGenerator();
//
//    /**
//     * 初始配置表信息
//     *
//     * @param GenTableDO 数据库查询的表信息
//     * @param configEntity   项目配置
//     */
//    public static void initTable(GenTableDO GenTableDO, GenCodeConfigEntity configEntity, GenDatabaseDO genDatabase) {
//        log.info("'{}'表信息初始化开始", GenTableDO.getTableName());
//        GenTableDO.setConfigId(configEntity.getId());
//        String[] strings = StringUtils.splitToArray(configEntity.getProjectTablePrefix(), StringConstant.EN_COMMA);
//        String javaNamePrefix = "";
//        for (String item : strings) {
//            javaNamePrefix = StringUtils.lineToHump(StringUtils.removePrefix(GenTableDO.getTableName(), item));
//        }
//        GenTableDO.setGenDbId(genDatabase.getId());
//        GenTableDO.setJavaNamePrefix(StringUtils.upperFirst(javaNamePrefix));
//        GenTableDO.setTableMsg(StringUtils.toString(GenTableDO.getTableMsg(), GenerateConstant.NOT_AVAILABLE));
//        GenTableDO.setModuleName(getModuleName(GenTableDO.getTableName()));
//        GenTableDO.setBusinessName(StringUtils.replace(GenTableDO.getTableMsg(), "表", StringConstant.EMPTY));
//        GenTableDO.setPathNamePrefix(StringUtils.addStartsContext(StringUtils.replace(GenTableDO.getTableName(), StringConstant.SEPARATOR_, StringConstant.URL_SEPARATOR), StringConstant.URL_SEPARATOR));
//        GenTableDO.setGenType(GenerateConstant.NO_REQUIRE);
//        GenTableDO.setMenuId(CommonConstants.TREE_DEFAULT);
//        log.info("'{}'表信息初始化结束", GenTableDO.getTableName());
//    }
//
//
//    /**
//     * 初始化字段信息
//     *
//     * @param columnEntity   字段信息
//     * @param GenTableDO 表信息
//     */
//    public static void initColumnField(GenTableColumnDO columnEntity, GenTableDO GenTableDO) {
//        log.info("`{}`.{} 字段信息初始化开始", GenTableDO.getTableName(), columnEntity.getColumnName());
//        columnEntity.setTableId(GenTableDO.getId());
//        columnEntity.setColumnJavaName(StringUtils.lineToHump(columnEntity.getColumnName().toLowerCase()));
//        DbConvertType dbConvertType = DbConvertType.getByDbType(columnEntity.getColumnDbType());
//        columnEntity.setColumnJavaType(dbConvertType.getJavaType());
//        columnEntity.setColumnTsType(dbConvertType.getTsType());
//        if (!ArrayUtils.containsIgnoreCase(GenerateConstant.BASE_COLUMNS, columnEntity.getColumnName())) {
//            columnEntity.setIsQuery(GenerateConstant.REQUIRE);
//            columnEntity.setIsList(GenerateConstant.REQUIRE);
//            columnEntity.setIsInsertEdit(GenerateConstant.REQUIRE);
//            columnEntity.setIsRequired(GenerateConstant.REQUIRE);
//        }
//        if (StringUtils.equals("del_flag", columnEntity.getColumnName())) {
//            columnEntity.setIsRequired(GenerateConstant.NO_REQUIRE);
//        }
//        columnEntity.setQueryType(GenerateConstant.QUERY_EQ);
//        columnEntity.setHtmlType(dbConvertType.getHtmlType().getValue());
//        String columnName = columnEntity.getColumnName();
//        if (StringUtils.containsIgnoreCase(columnName, "status")) {// 状态字段设置单选框
//            columnEntity.setHtmlType(HtmlTypeEnum.RADIO.getValue());
//        } else if (StringUtils.containsIgnoreCase(columnName, "type") || StringUtils.containsIgnoreCase(columnName, "sex")) {// 类型&性别字段设置下拉框
//            columnEntity.setHtmlType(HtmlTypeEnum.SELECT.getValue());
//        } else if (StringUtils.containsIgnoreCase(columnName, "image")) {// 图片字段设置图片上传控件
//            columnEntity.setHtmlType(HtmlTypeEnum.IMAGE_UPLOAD.getValue());
//        } else if (StringUtils.containsIgnoreCase(columnName, "file")) {// 文件字段设置文件上传控件
//            columnEntity.setHtmlType(HtmlTypeEnum.FILE_UPLOAD.getValue());
//        } else if (StringUtils.containsIgnoreCase(columnName, "content") || StringUtils.containsIgnoreCase(columnName, "desc")) {// 内容字段设置富文本控件
//            columnEntity.setHtmlType(HtmlTypeEnum.EDITOR.getValue());
//        } else if (StringUtils.containsIgnoreCase(columnName, "sort") || dbConvertType.getTsType().equals("number")) {// 内容字段设置数字控件
//            columnEntity.setHtmlType(HtmlTypeEnum.INPUT_NUMBER.getValue());
//        }
//        log.info("`{}`.{} 字段信息初始化结束", GenTableDO.getTableName(), columnEntity.getColumnName());
//    }
//
//    /**
//     * 获取
//     *
//     * @param tableName 表明
//     * @return String
//     */
//    public static String getModuleName(String tableName) {
//        int lastIndex = tableName.lastIndexOf("_");
//        int nameLength = tableName.length();
//        return StringUtils.sub(tableName, lastIndex + 1, nameLength);
//    }
//
//
//    public static String initCode(String code, GenInfoBo info, GenCodeTemplateEntity template) {
//        List<GenTableColumnDO> columns = info.getColumns();
//        GenTableDO table = info.getTable();
//        GenCodeConfigEntity GenCodeConfig = info.getConfig();
//        Velocity.init();
//        VelocityContext velocityContext = new VelocityContext();
//        for (int i = 1; i < 10; i++) {
//            velocityContext.put("id" + i, IDENTIFIER_GENERATOR.nextId(info));//插入9个id
//        }
//        velocityContext.put("menuId", table.getMenuId());//菜单id
//        velocityContext.put("perms", StringUtils.replace(table.getTableName(), StringConstant.SEPARATOR_, StringConstant.EN_COLON));//权限前缀
//        velocityContext.put("tableSchema", table.getTableSchema());//数据库所在的schema
//        velocityContext.put("tableName", table.getTableName());//表名
//        velocityContext.put("tableMsg", table.getTableMsg());//表描述
//        velocityContext.put("tableEngine", table.getTableEngine());//表的数据库引擎
//        velocityContext.put("tableCreateTime", LocalDateTimeUtil.format(table.getTableCreateTime(), "yyyy-MM-dd HH:mm:ss"));//表创建时间
//        velocityContext.put("tableUpdateTime", LocalDateTimeUtil.format(table.getTableUpdateTime(), "yyyy-MM-dd HH:mm:ss"));//表更新时间
//        velocityContext.put("telFileName", StringUtils.format(template.getTelFileName(), table.getJavaNamePrefix()));//文件名不带文件类型后缀
//        velocityContext.put("className", table.getJavaNamePrefix());//类名
//        velocityContext.put("classNameLower", StringUtils.lowerFirst(table.getJavaNamePrefix()));//类名首字母小写
//        velocityContext.put("moduleName", table.getModuleName());//模块名
//        velocityContext.put("businessName", table.getBusinessName());//业务简介
//        velocityContext.put("pathNamePrefix", table.getPathNamePrefix());//前缀
//        velocityContext.put("genType", table.getGenType());//生成方式
//        velocityContext.put("packageName", GenCodeConfig.getPackageName());//包名
//        String[] pName = StringUtils.splitToArray(GenCodeConfig.getPackageName(), StringConstant.DIAN);
//        velocityContext.put("webPackageName", pName[pName.length - 1]);//前端包名
//        velocityContext.put("auth", GenCodeConfig.getProjectAuth());//项目作者
//        velocityContext.put("projectName", GenCodeConfig.getProjectName());//项目名称
//        velocityContext.put("projectRemark", GenCodeConfig.getProjectRemark());//项目描述
//        velocityContext.put("projectTablePrefix", GenCodeConfig.getProjectTablePrefix());//忽略的表前缀
//        String baseClassId = template.getBaseClassId();
//        if (!StringUtils.isEmpty(baseClassId)) {
//            velocityContext.put("exClassName", template.getBaseClassName());
//            velocityContext.put("allColumns", columns);//全部字段
//            GenCodeBaseClassEntity baseClass = SpringUtils.getBean(IGenCodeBaseClassService.class).getById(baseClassId);
//            velocityContext.put("exPackageName", baseClass.getPackageName());
//            String[] strings = ArrayUtils.toStrArray(baseClass.getIgnoreField(), StringConstant.EN_COMMA);
//            velocityContext.put("ignoreColumns", baseClass.getIgnoreField());//忽略字段
//            velocityContext.put("columns", columns.stream().filter(item -> !ArrayUtils.contains(strings, item.getColumnName())).collect(Collectors.toList()));//字段信息
//        } else {
//            velocityContext.put("columns", columns);//字段信息
//        }
//        velocityContext.put("updateColumns", columns.stream().filter(item -> !ArrayUtils.contains(GenerateConstant.BASE_COLUMNS_, item.getColumnName())).collect(Collectors.toList()));//convert 修改字段
//        velocityContext.put("pkColumns", columns.stream().filter(item -> item.getIsPk().equals(GenerateConstant.REQUIRE)).collect(Collectors.toList()));//主键字段
//        velocityContext.put("insertEditColumns", columns.stream().filter(item -> item.getIsInsertEdit().equals(GenerateConstant.REQUIRE)).collect(Collectors.toList()));//增加编辑
//        velocityContext.put("listColumns", columns.stream().filter(item -> item.getIsList().equals(GenerateConstant.REQUIRE)).collect(Collectors.toList()));//列表字段（显示字段）
//        velocityContext.put("queryColumns", columns.stream().filter(item -> item.getIsQuery().equals(GenerateConstant.REQUIRE)).collect(Collectors.toList()));//查询字段
//        velocityContext.put("requiredColumns", columns.stream().filter(item -> item.getIsRequired().equals(GenerateConstant.REQUIRE)).collect(Collectors.toList()));//查询字段
//        StringWriter stringWriter = new StringWriter();
//        Velocity.evaluate(velocityContext, stringWriter, template.getTelName(), code);
//        return stringWriter.toString();
//    }
//
//
//    public static void main(String[] args) {
//        Velocity.init();
//        VelocityContext velocityContext = new VelocityContext();
//        List<String> objects = new ArrayList<>();
//        for (int i = 1; i < 10; i++) {
//            System.out.println(i);
//            objects.add(i + "");
//        }
//        velocityContext.put("size", objects.size());
//        velocityContext.put("objects", objects);
//        String str =
//                "#set($iterator =$objects.iterator() )" +
//                        "#foreach($i in [1..$size])\n" +
//                        "#if($i % 2 != 0)" +
//                        "  <p>$iterator.next()</p>\n" +
//                        "#if($iterator.hasNext())\n" +
//                        "  <p>$iterator.next()</p>\n" +
//                        "#end" +
//                        "#end" +
//                        "#end";
//
//        StringWriter stringWriter = new StringWriter();
//        Velocity.evaluate(velocityContext, stringWriter, "test", str);
//        System.out.println(stringWriter);
//        System.out.println(str);
//    }
//
//
//}
