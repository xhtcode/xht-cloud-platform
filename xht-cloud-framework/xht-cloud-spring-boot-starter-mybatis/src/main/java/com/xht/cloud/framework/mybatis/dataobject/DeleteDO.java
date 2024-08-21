package com.xht.cloud.framework.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xht.cloud.framework.mybatis.enums.DelFlagEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Data
public abstract class DeleteDO extends AbstractDO implements Serializable {

    /**
     * 是否删除(0未删除1已经删除)
     */
    @TableLogic
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private DelFlagEnum delFlag;

}
