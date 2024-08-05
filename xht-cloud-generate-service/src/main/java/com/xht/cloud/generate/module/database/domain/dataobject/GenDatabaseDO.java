package com.xht.cloud.generate.module.database.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;
import lombok.Data;

/**
 * 描述 ：代码生成器-数据源管理
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "gen_database")
public class GenDatabaseDO extends AbstractDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 连接名称
     */
    @TableField(value = "conn_name")
    private String connName;

    /**
     * 数据库连接
     */
    @TableField(value = "db_url")
    private String dbUrl;

    /**
     * 数据库类型
     */
    @TableField(value = "db_type")
    private String dbType;

    /**
     * 数据库名称
     */
    @TableField(value = "db_name")
    private String dbName;

    /**
     * 数据库描述
     */
    @TableField(value = "db_describe")
    private String dbDescribe;

    /**
     * host
     */
    @TableField(value = "host")
    private String host;

    /**
     * 端口
     */
    @TableField(value = "port")
    private String port;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField(value = "pass_word")
    private String passWord;

    /**
     * 连接状态0失败1成功
     */
    @TableField(value = "status")
    private String status;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

}
