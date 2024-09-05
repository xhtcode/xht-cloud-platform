package com.xht.cloud.framework.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * 描述 ：公共修改请求实体
 *
 * @author 小糊涂
 **/
@Schema(description = "公共修改请求实体")
public abstract class UpdateRequest extends Request implements Serializable {

}
