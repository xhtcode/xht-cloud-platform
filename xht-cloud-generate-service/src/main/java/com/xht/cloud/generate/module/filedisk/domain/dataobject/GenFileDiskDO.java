package com.xht.cloud.generate.module.filedisk.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.BaseNoneDeleteDO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件管理
 *
 * @author 小糊涂
 */
@Data
@TableName("gen_file_disk")
public class GenFileDiskDO extends BaseNoneDeleteDO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 上级目录
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 配置id
     */
    @TableField("config_id")
    private Long configId;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件描述
     */
    @TableField("file_desc")
    private String fileDesc;


    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;


    /**
     * 代码路径
     */
    @TableField("file_code_path")
    private String fileCodePath;
    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 模板内容
     */
    @TableField("file_content")
    private String fileContent;

    /**
     * 文件排序
     */
    @TableField("file_sort")
    private Integer fileSort;

    /**
     * 忽略字段
     */
    @TableField("ignore_field")
    private String ignoreField;

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
