package com.xht.cloud.admin.module.area.domain.request;

import com.xht.cloud.framework.domain.request.UpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：地区信息-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysAreaInfoRequest(地区信息-修改请求信息)")
public class SysAreaInfoUpdateRequest extends UpdateRequest<String> {

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
     * 级别1-5,省市县镇村1级：省、直辖市、自治区2级：地级市3级：市辖区、县（旗）、县级市、自治县（自治旗）、特区、林区4级：镇、乡、民族乡、县辖区、街道5级：村、居委会
     */
    @Schema(description = "级别")
    @NotBlank(message = "级别 `level` 校验不通过")
    private String level;

    /**
     * 区划代码
     */
    @Schema(description = "区划代码")
    @NotBlank(message = "区划代码 `areaNo` 校验不通过")
    private String areaNo;

    /**
     * 城乡分类111表示主城区；112表示城乡接合区；121表示镇中心区；122表示镇乡接合区；123表示特殊区域；210表示乡中心区；220表示村庄
     */
    @Schema(description = "城乡分类")
    private String category;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String msg;

}
