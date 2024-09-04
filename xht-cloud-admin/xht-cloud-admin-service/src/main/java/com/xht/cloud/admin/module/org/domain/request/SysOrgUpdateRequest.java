package com.xht.cloud.admin.module.org.domain.request;

import com.xht.cloud.framework.domain.request.IUpdateRequestFun;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 组织机构
 *
 * @author 小糊涂
 */
@Data
@Schema(name = "SysOrgUpdateRequest", description = "组织机构")
public class SysOrgUpdateRequest extends SysOrgCreateRequest implements IUpdateRequestFun<Long> {

    /**
     * id
     */
    @Schema(description = "主键")
    private Long id;

}
