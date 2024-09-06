package com.xht.cloud.framework.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述 ：公共修改请求实体
 *
 * @author 小糊涂
 **/
@Data
@Schema(description = "公共修改请求实体")
@SuppressWarnings("unused")
public abstract class UpdateRequest<T extends Serializable> extends Request implements Serializable {

    /**
     * id
     */
    @Schema(description = "id")
    private T id;

    @JsonIgnore
    public void checkId() {
        Assert.notNull(this.id, () -> new BizException("修改时参数错误，查询不到数据！"));
    }


}
