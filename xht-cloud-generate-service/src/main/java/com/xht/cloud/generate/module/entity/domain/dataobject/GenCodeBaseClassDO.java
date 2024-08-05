package com.xht.cloud.generate.module.entity.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

/**
 * 描述 ：代码生成器-基类
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "gen_code_base_class")
public class GenCodeBaseClassDO extends BaseDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 类名
     */
    @TableField(value = "class_name")
    private String className;

    /**
     * 包名
     */
    @TableField(value = "package_name")
    private String packageName;

    /**
     * 忽略字段
     */
    @TableField(value = "ignore_field")
    private String ignoreField;

    /**
     * 排序
     */
    @TableField(value = "class_sort")
    private String classSort;

}
