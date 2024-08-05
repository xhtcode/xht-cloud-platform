package com.xht.cloud.framework.core.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述 ：公共请求实体
 *
 * @author 小糊涂
 **/
@Data
@Schema(description = "公共请求实体")
public abstract class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Hidden
    @JsonProperty("_t")
    private Long t;

}
