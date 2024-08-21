package com.xht.cloud.generate.module.template.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.DeleteDO;
import lombok.Data;

/**
 * 描述 ：代码生成器-代码模板
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "gen_code_template")
public class GenCodeTemplateDO extends DeleteDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 分组id
     */
    @TableField(value = "group_id")
    private String groupId;

    /**
     * 模板名称
     */
    @TableField(value = "tel_name")
    private String telName;

    /**
     * 模板描述
     */
    @TableField(value = "tel_remark")
    private String telRemark;

    /**
     * 模板内容
     */
    @TableField(value = "tel_content")
    private String telContent;

    /**
     * 模板生成文件类型
     */
    @TableField(value = "tel_file_type")
    private String telFileType;

    /**
     * 模板生成名称
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 状态(0禁用1启用)
     */
    @TableField(value = "tel_status")
    private String telStatus;

    /**
     * 忽略字段
     */
    @TableField(value = "ignore_field")
    private String ignoreField;

    /**
     * 排序
     */
    @TableField(value = "tel_sort")
    private Integer telSort;

}
