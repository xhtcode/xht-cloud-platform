package com.xht.cloud.framework.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述 ：公共创建请求实体
 *
 * @author 小糊涂
 **/
@Data
@Schema(description = "公共创建请求实体")
public abstract class CreateRequest extends Request implements Serializable {
}
