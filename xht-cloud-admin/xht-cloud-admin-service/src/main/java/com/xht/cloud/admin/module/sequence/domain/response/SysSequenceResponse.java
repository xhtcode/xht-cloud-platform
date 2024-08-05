package com.xht.cloud.admin.module.sequence.domain.response;

import com.xht.cloud.admin.enums.SeqLoopEnum;
import com.xht.cloud.admin.enums.SeqResetFlagEnum;
import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysSequenceUpdateResponse(序列生成器-响应信息)")
public class SysSequenceResponse extends Response {

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 序列编码
     */
    @Schema(description = "序列编码")
    private String seqCode;

    /**
     * 序列名称
     */
    @Schema(description = "序列名称")
    private String seqName;

    /**
     * 序列当前值
     */
    @Schema(description = "序列当前值")
    private Integer currentValue;

    /**
     * 序列步进值
     */
    @Schema(description = "序列步进值")
    private Integer stepValue;

    /**
     * 序列最大值
     */
    @Schema(description = "序列最大值")
    private Integer maxValue;

    /**
     * 序列最小值
     */
    @Schema(description = "序列最小值")
    private Integer minValue;

    /**
     * 序列是否循环更新
     */
    @Schema(description = "序列是否循环更新")
    private SeqLoopEnum seqLoop;

    /**
     * 序列重置周期 0 不重置 0每天 1月 2年
     */
    @Schema(description = "序列重置周期")
    private SeqResetFlagEnum resetFlag;

    /**
     * 序列格式
     */
    @Schema(description = "序列格式")
    private String seqFormat;

    /**
     * 序列描述
     */
    @Schema(description = "序列描述")
    private String seqDesc;


}
