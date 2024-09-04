package com.xht.cloud.admin.module.area.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.cloud.framework.constant.TreeConstant;
import com.xht.cloud.framework.domain.request.PageQueryRequest;
import com.xht.cloud.framework.utils.StringUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：地区信息-查询请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysAreaInfoRequest(地区信息-查询请求信息)")
public class SysAreaInfoQueryRequest extends PageQueryRequest {
    /**
     * 父级区划代码
     */
    @Schema(description = "父级区划代码")
    @NotBlank(message = "父级区划代码 `parentId` 校验不通过")
    private String parentId;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @NotBlank(message = "名称 `requestFileName` 校验不通过")
    private String name;

    /**
     * 区划代码
     */
    @Schema(description = "区划代码")
    @NotBlank(message = "区划代码 `areaNo` 校验不通过")
    private String areaNo;

    @JsonIgnore
    public String getQueryParentId() {
        if (!StringUtils.hasText(this.parentId) && !StringUtils.hasText(this.areaNo) &&
                !StringUtils.hasText(this.name)) {
            return TreeConstant.TREE_PARENT_DEFAULT;
        }
        return parentId;
    }
}
