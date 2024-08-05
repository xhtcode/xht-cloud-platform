package com.xht.cloud.admin.module.dict.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.admin.api.dict.enums.DictStatusEnums;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

/**
 * 描述 ：字典数据
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_dict_item")
public class SysDictItemDO extends BaseDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 字典id
     */
    @TableField(value = "dict_id")
    private String dictId;

    /**
     * 字典类型
     */
    @TableField(value = "dict_type")
    private String dictType;

    /**
     * 字典编码
     */
    @TableField(value = "dict_code")
    private String dictCode;

    /**
     * 字典值
     */
    @TableField(value = "dict_value")
    private String dictValue;

    /**
     * tag展示的类型
     */
    @TableField(value = "tag_type")
    private String tagType;

    /**
     * 字典数据排序
     */
    @TableField(value = "dict_sort")
    private Integer dictSort;

    /**
     * 字典数据状态
     */
    @TableField(value = "dict_status")
    private DictStatusEnums dictStatus;


    /**
     * 字典备注
     */
    @TableField(value = "dict_desc")
    private String dictDesc;
}
