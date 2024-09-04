package com.xht.cloud.admin.module.org.domain.response;

import com.xht.cloud.admin.module.org.constant.SysOrgTypeEnums;
import com.xht.cloud.framework.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 组织机构
 *
 * @author 小糊涂
 */
@Data
@Schema(name = "SysOrgResponse", description = "组织机构")
public class SysOrgResponse extends Response {
  
    /**
     * 主管ID(用户id)
     */
    @Schema(description = "主管ID(用户id)")
    private String directorId;

    /**
     * 上级机构id
     */
    @Schema(description = "上级机构id")
    private Long parentId;

    /**
     * 上级机构名称
     */
    @Schema(description = "上级机构名称")
    private String parentName;

    /**
     * 机构名称
     */
    @Schema(description = "机构名称")
    private String orgName;

    /**
     * 机构编码
     */
    @Schema(description = "机构编码")
    private String orgCode;

    /**
     * 机构分类
     */
    @Schema(description = "机构分类")
    private SysOrgTypeEnums orgType;

    /**
     * 机构状态
     */
    @Schema(description = "机构状态")
    private String orgStatus;

    /**
     * 机构排序
     */
    @Schema(description = "机构排序")
    private Integer orgSort;

    /**
     * 机构名称
     */
    @Schema(description = "机构名称")
    private String orgPath;

    /**
     * 机构描述
     */
    @Schema(description = "机构描述")
    private String orgDesc;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String orgPhone;

    /**
     * 联系邮箱
     */
    @Schema(description = "联系邮箱")
    private String orgEmail;

}
