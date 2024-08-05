package com.xht.cloud.admin.module.sequence.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.admin.enums.SeqLoopEnum;
import com.xht.cloud.admin.enums.SeqResetFlagEnum;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

/**
 * 描述 ：序列生成器
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_sequence")
public class SysSequenceDO extends BaseDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 序列编码
     */
    @TableField(value = "seq_code")
    private String seqCode;

    /**
     * 序列名称
     */
    @TableField(value = "seq_name")
    private String seqName;

    /**
     * 序列当前值
     */
    @TableField(value = "current_value")
    private Integer currentValue;

    /**
     * 序列步进值
     */
    @TableField(value = "step_value")
    private Integer stepValue;

    /**
     * 序列最大值
     */
    @TableField(value = "max_value")
    private Integer maxValue;

    /**
     * 序列最小值
     */
    @TableField(value = "min_value")
    private Integer minValue;

    /**
     * 序列是否循环更新 是 超过序列值重新从1 计算 否 报错
     */
    @TableField(value = "seq_loop")
    private SeqLoopEnum seqLoop;

    /**
     * 序列重置周期 0 不重置 0每天 1月 2年
     */
    @TableField(value = "reset_flag")
    private SeqResetFlagEnum resetFlag;

    /**
     * 序列格式
     * 032000 {YYYYMMDD} - {N}
     * YYYYMMDD, 当前日期的格式定义，支持YYYY,YY,MM,DD几种格式组合
     * {N}原值显示当前值
     * {N6}当前值显示的最小长度为6位，不足时前面补零
     */
    @TableField(value = "seq_format")
    private String seqFormat;


    /**
     * 序列描述
     */
    @TableField(value = "seq_desc")
    private String seqDesc;


}
