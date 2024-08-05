package com.xht.cloud.admin.module.sequence.domain.request;

import com.xht.cloud.admin.enums.SeqLoopEnum;
import com.xht.cloud.admin.enums.SeqResetFlagEnum;
import com.xht.cloud.framework.core.domain.request.CreateRequest;
import com.xht.cloud.framework.web.validation.group.Create;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 描述 ：序列生成器
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysSequenceCreateRequest(序列生成器-公共增加信息)")
public class SysSequenceCreateRequest extends CreateRequest {

    /**
     * 序列编码
     */
    @Schema(description = "序列编码")
    @NotEmpty(message = "序列编码不合法", groups = {Create.class})
    private String seqCode;

    /**
     * 序列名称
     */
    @Schema(description = "序列名称")
    @NotEmpty(message = "序列名称不合法", groups = {Create.class})
    private String seqName;

    /**
     * 序列当前值
     */
    @Schema(description = "序列当前值")
    @NotNull(message = "序列当前值不合法", groups = {Create.class})
    private Integer currentValue;

    /**
     * 序列步进值
     */
    @Schema(description = "序列步进值")
    @NotEmpty(message = "序列步进值不合法", groups = {Create.class})
    private Integer stepValue;

    /**
     * 序列最大值
     */
    @Schema(description = "序列最大值")
    @NotEmpty(message = "序列最大值不合法", groups = {Create.class})
    private Integer maxValue;

    /**
     * 序列最小值
     */
    @Schema(description = "序列最小值")
    @NotEmpty(message = "序列最小值不合法", groups = {Create.class})
    private Integer minValue;

    /**
     * 序列是否循环更新 是 超过序列值重新从1 计算 否 报错
     */
    @Schema(description = "序列是否循环更新")
    @NotNull(message = "序列是否循环更新不合法", groups = {Create.class})
    private SeqLoopEnum seqLoop;

    /**
     * 序列重置周期 0 不重置 0每天 1月 2年
     */
    @Schema(description = "序列重置周期")
    @NotNull(message = "序列重置周期不合法", groups = {Create.class})
    private SeqResetFlagEnum resetFlag;

    /**
     * 序列格式
     * 032000 {YYYYMMDD} - {N}
     * YYYYMMDD, 当前日期的格式定义，支持YYYY,YY,MM,DD几种格式组合
     * {N}原值显示当前值
     * {N6}当前值显示的最小长度为6位，不足时前面补零
     */
    @Schema(description = "序列格式")
    @NotEmpty(message = "序列格式不合法", groups = {Create.class})
    private String seqFormat;

    /**
     * 序列描述
     */
    @Schema(description = "序列描述")
    @NotEmpty(message = "序列描述不合法", groups = {Create.class})
    private String seqDesc;

}
