package com.xht.cloud.admin.module.area.domain.response;

import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

/**
 * 描述 ：地区信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "SysAreaInfoResponse(地区信息-响应信息)")
public class SysAreaInfoResponse extends Response {

    /**
     * 主键
     */
    @Schema(description = "主键")
    private String id;

    /**
     * 父级区划代码
     */
    @Schema(description = "父级区划代码")
    private String parentId;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 级别1-5,省市县镇村1级：省、直辖市、自治区2级：地级市3级：市辖区、县（旗）、县级市、自治县（自治旗）、特区、林区4级：镇、乡、民族乡、县辖区、街道5级：村、居委会
     */
    @Getter
    @Schema(description = "级别1-5,省市县镇村1级：省、直辖市、自治区2级：地级市3级：市辖区、县（旗）、县级市、自治县（自治旗）、特区、林区4级：镇、乡、民族乡、县辖区、街道5级：村、居委会")
    private String level;

    /**
     * 区划代码
     */
    @Schema(description = "区划代码")
    private String areaNo;

    /**
     * 城乡分类111表示主城区；112表示城乡接合区；121表示镇中心区；122表示镇乡接合区；123表示特殊区域；210表示乡中心区；220表示村庄
     */
    @Schema(description = "城乡分类111表示主城区；112表示城乡接合区；121表示镇中心区；122表示镇乡接合区；123表示特殊区域；210表示乡中心区；220表示村庄")
    private String category;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String msg;

    /**
     * 此字段数据冗余字段
     */
    @Schema(description = "数据冗余字段")
    private boolean leaf;

}
