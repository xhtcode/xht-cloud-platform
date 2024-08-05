package com.xht.cloud.admin.module.sequence.domain.request;

import com.xht.cloud.admin.enums.SeqLoopEnum;
import com.xht.cloud.admin.enums.SeqResetFlagEnum;
import com.xht.cloud.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：序列生成器
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysSequenceQueryRequest(序列生成器-公共请求信息)")
public class SysSequenceQueryRequest extends PageQueryRequest {

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
     * 序列是否循环更新
     */
    @Schema(description = "序列是否循环更新")
    private SeqLoopEnum seqLoop;

    /**
     * 序列重置周期 0 不重置 0每天 1月 2年
     */
    @Schema(description = "序列重置周期")
    private SeqResetFlagEnum resetFlag;

}
