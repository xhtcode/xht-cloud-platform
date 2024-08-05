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
@TableName(value = "sys_dict_type")
public class SysDictTypeDO extends BaseDO {

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 字典类型
     */
    @TableField(value = "dict_type")
    private String dictType;

    /**
     * 字典名称
     */
    @TableField(value = "dict_name")
    private String dictName;

    /**
     * 字典状态
     */
    @TableField(value = "dict_status")
    private DictStatusEnums dictStatus;


    /**
     * 字典类型备注
     */
    @TableField(value = "dict_desc")
    private String dictDesc;

    /**
     * 字典类型排序
     */
    @TableField(value = "dict_sort")
    private Integer dictSort;

}
