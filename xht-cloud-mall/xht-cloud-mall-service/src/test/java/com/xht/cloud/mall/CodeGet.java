package com.xht.cloud.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CodeGet {

    public static void main(String[] args) throws Exception {
        String[] tables = getTables("xht-cloud-mall");
// 使用 FastAutoGenerator 快速配置代码生成器
        FastAutoGenerator.create("jdbc:p6spy:mysql://192.168.100.1:3306/xht-cloud-generate?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8", "root", "123456").globalConfig(builder -> {
                    builder.author("小糊涂") // 设置作者
                            .outputDir("F:\\my-project\\xht-cloud-platform\\xht-cloud-generate-service\\src\\main\\java") // 输出目录
                            .dateType(DateType.TIME_PACK) // 设置时间类型策略
                            .commentDate("yyyy-MM-dd").enableSpringdoc();
                }).packageConfig(builder -> {
                    builder.parent("com.xht.cloud.mall.module") // 设置父包名
                            .entity("domain.dataobject") // 设置实体类包名
                            .mapper("mapper") // 设置 Mapper 接口包名
                            .service("service") // 设置 Service 接口包名
                            .serviceImpl("service.impl") // 设置 Service 实现类包名
                            .controller("controller").moduleName("region").xml("mapper"); // 设置 Mapper XML 文件包名
                }).strategyConfig(builder -> {


                    builder.addInclude("gen_file_disk") // 设置需要生成的表名
                            .entityBuilder().enableLombok() // 启用 Lombok
                            .addSuperEntityColumns("created_by", "created_time", "is_deleted", "updated_by", "updated_time")
                            .idType(IdType.AUTO)
                            .formatFileName("%s")
                            .formatFileName("%sDO")
                            .enableTableFieldAnnotation() // 启用字段注解
                            .controllerBuilder()
                            .enableRestStyle()
                            .mapperBuilder()
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper")
                            .serviceBuilder()
                            .superServiceClass("")
                            .superServiceImplClass("")
                    ; // 启用 REST 风格
                }).templateEngine(new VelocityTemplateEngine()) // 使用 Freemarker 模板引擎
                .execute(); // 执行生成
    }

    /**
     * 获取某个数据库中的所有表名
     *
     * @param dbName
     * @return
     * @throws Exception
     */
    private static String[] getTables(String dbName) throws Exception {
        List<String> tables = new ArrayList<>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:p6spy:mysql://192.168.100.1:3306/xht-cloud-mall?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8", "root", "123456");
            String sql = "select table_name from information_schema.tables where table_schema=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, dbName);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                tables.add(resultSet.getString("table_name"));
            }
            String[] result = tables.toArray(new String[tables.size()]);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        throw new Exception("数据库连接异常！");
    }

    private static String processName(String name, NamingStrategy strategy, Set<String> prefix, Set<String> suffix) {
        String propertyName = name;
        // 删除前缀
        if (!prefix.isEmpty()) {
            propertyName = NamingStrategy.removePrefix(propertyName, prefix);
        }
        // 删除后缀
        if (!suffix.isEmpty()) {
            propertyName = NamingStrategy.removeSuffix(propertyName, suffix);
        }
        if (StringUtils.isBlank(propertyName)) {
            throw new RuntimeException(String.format("%s 的名称转换结果为空，请检查是否配置问题", name));
        }
        // 下划线转驼峰
        if (NamingStrategy.underline_to_camel.equals(strategy)) {
            return NamingStrategy.underlineToCamel(propertyName);
        }
        return propertyName;
    }
}
