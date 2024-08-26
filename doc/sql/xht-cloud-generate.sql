DROP
    DATABASE IF EXISTS `xht-cloud-generate`;

CREATE
    DATABASE  `xht-cloud-generate` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

SET NAMES utf8mb4;
SET
    FOREIGN_KEY_CHECKS = 0;

USE
    `xht-cloud-generate`;

DROP TABLE IF EXISTS `gen_code_config`;
CREATE TABLE `gen_code_config`
(
    `id`             bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    `config_name`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置名称',
    `config_desc`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置描述',
    `config_sort`    int                                                           DEFAULT NULL COMMENT '排序',
    `config_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      DEFAULT NULL COMMENT '是否默认 1是',
    `create_by`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '创建者',
    `create_time`    datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '更新者',
    `update_time`    datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `del_flag`       char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='代码生成器-配置中心';
INSERT INTO `gen_code_config` (`id`, `config_name`, `config_desc`, `config_sort`, `config_default`, `create_by`,
                               `create_time`, `update_by`, `update_time`, `del_flag`)
VALUES (1, 'xht-cloud-platform', 'springboot3+vue3+ts（单表）', 1, '1', NULL, NULL, NULL, NULL, '0');

DROP TABLE IF EXISTS `gen_column_type`;
CREATE TABLE `gen_column_type`
(
    `id`       bigint                                                        NOT NULL AUTO_INCREMENT,
    `db_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库类型',
    `db_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库字段类型',
    `label`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '语言类型',
    `value`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码类型',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='代码生成器-字段类型对应表';
INSERT INTO `gen_column_type` (`id`, `db_label`, `db_value`, `label`, `value`)
VALUES (1, 'MySql', 'timestamp', 'Java', 'LocalDateTime'),
       (2, 'MySql', 'longtext', 'Java', 'String'),
       (3, 'MySql', 'mediumtext', 'Java', 'String'),
       (4, 'MySql', 'text', 'Java', 'String'),
       (5, 'MySql', 'tinytext', 'Java', 'String'),
       (6, 'MySql', 'varchar', 'Java', 'String'),
       (7, 'MySql', 'char', 'Java', 'String'),
       (8, 'MySql', 'bit', 'Java', 'Boolean'),
       (9, 'MySql', 'decimal', 'Java', 'BigDecimal'),
       (10, 'MySql', 'double', 'Java', 'Double'),
       (11, 'MySql', 'float', 'Java', 'Float'),
       (12, 'MySql', 'bigint', 'Java', 'Long'),
       (13, 'MySql', 'integer', 'Java', 'Integer'),
       (14, 'MySql', 'int', 'Java', 'Integer'),
       (15, 'MySql', 'mediumint', 'Java', 'Integer'),
       (16, 'MySql', 'smallint', 'Java', 'Integer'),
       (17, 'MySql', 'tinyint', 'Java', 'Integer'),
       (18, 'MySql', 'date', 'Java', 'LocalDateTime'),
       (19, 'MySql', 'datetime', 'Java', 'LocalDateTime'),
       (20, 'MySql', 'timestamp', 'TS', 'Date'),
       (21, 'MySql', 'longtext', 'TS', 'string'),
       (22, 'MySql', 'mediumtext', 'TS', 'string'),
       (23, 'MySql', 'text', 'TS', 'string'),
       (24, 'MySql', 'tinytext', 'TS', 'string'),
       (25, 'MySql', 'varchar', 'TS', 'string'),
       (26, 'MySql', 'char', 'TS', 'string'),
       (27, 'MySql', 'bit', 'TS', 'boolean'),
       (28, 'MySql', 'decimal', 'TS', 'number'),
       (29, 'MySql', 'double', 'TS', 'number'),
       (30, 'MySql', 'float', 'TS', 'number'),
       (31, 'MySql', 'bigint', 'TS', 'number'),
       (32, 'MySql', 'integer', 'TS', 'number'),
       (33, 'MySql', 'int', 'TS', 'number'),
       (34, 'MySql', 'mediumint', 'TS', 'number'),
       (35, 'MySql', 'smallint', 'TS', 'number'),
       (36, 'MySql', 'tinyint', 'TS', 'number'),
       (37, 'MySql', 'date', 'TS', 'Date'),
       (38, 'MySql', 'datetime', 'TS', 'Date');

DROP TABLE IF EXISTS `gen_database`;
CREATE TABLE `gen_database`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `conn_name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '连接名称',
    `db_url`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据库连接',
    `db_type`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '数据库类型',
    `db_name`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '数据库名称',
    `db_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据库描述',
    `host`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'host',
    `port`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '端口',
    `user_name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
    `pass_word`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
    `sort`        int                                                           DEFAULT NULL COMMENT '排序',
    `del_flag`    char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      DEFAULT NULL COMMENT '是否删除(0未删除1已经删除)',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '创建者',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '更新者',
    `create_time` datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                                      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='代码生成器-数据源管理';

DROP TABLE IF EXISTS `gen_file_disk`;
CREATE TABLE `gen_file_disk`
(
    `id`        varchar(36) NOT NULL,
    `parent_id` varchar(36) DEFAULT NULL COMMENT '上级目录',
    `config_id` bigint      NOT NULL COMMENT '配置id',
    `file_name`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '文件名称',
    `file_desc`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '文件描述',
    `file_type`      varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   DEFAULT NULL COMMENT '文件类型',
    `file_path`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '文件路径',
    `file_code_path` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
    `file_content`   longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '模板内容',
    `file_sort`      int                                                            DEFAULT NULL COMMENT '文件排序',
    `ignore_field`   varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '忽略字段',
    `del_flag`       char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci       DEFAULT NULL COMMENT '是否删除(0未删除1已经删除)',
    `create_by`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime                                                       DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '更新人',
    `update_time`    datetime                                                       DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='代码生成器-代码模板';

DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `config_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '配置中心',
                             `gen_db_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据源id',
                             `table_schema` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表所在的数据库名称',
                             `table_engine` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库引擎',
                             `table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表名称',
                             `module_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块名称',
                             `service_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务名称',
                             `service_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务描述',
                             `authorization_prefix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '权限前缀',
                             `path_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'controller地址前缀',
                             `code_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码名称',
                             `parent_id` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '树表的上级id',
                             `parent_name` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '树表的名字',
                             `table_create_time` datetime DEFAULT NULL COMMENT '表创建时间',
                             `table_update_time` datetime DEFAULT NULL COMMENT '表更新时间',
                             `create_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
                             `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='代码生成器-数据库表信息';
DROP TABLE IF EXISTS `gen_table_column`;

CREATE TABLE `gen_table_column` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `table_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表id',
                                    `column_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段名字',
                                    `column_length` int NOT NULL COMMENT '字段长度',
                                    `column_code_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段名字',
                                    `column_comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段描述',
                                    `column_db_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库字段类型',
                                    `column_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '是否主键（1是）',
                                    `column_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '是否列表（1是）',
                                    `column_operation` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '是否增加（1是）',
                                    `column_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '是否查询（1是）',
                                    `column_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '是否必填（1是）',
                                    `column_sort` int DEFAULT NULL COMMENT '字段排序',
                                    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
                                    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新者',
                                    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='代码生成器-业务表字段';
