package com.xht.cloud.admin.module.sequence.domain.request;

import com.xht.cloud.framework.domain.request.Request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "IdRequest(id生成器-公共请求信息)")
public class IdRequest extends Request {

    /**
     * id类型
     */
    @NotNull(message = "序列类型不能为空")
    private Integer code;

    /**
     * 序列code
     */
    private String seqCode;


    /**
     * 生成次数
     */
    private int count;

}
