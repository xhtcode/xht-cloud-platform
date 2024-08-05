package com.xht.cloud.generate.module.template.domain.response;

import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：代码生成器-代码模板
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenCodeTemplateResponse(代码生成器-代码模板-响应信息)", description = "代码生成器-代码模板")
public class GenCodeTemplateResponse extends Response {

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 分组id
     */
    @Schema(description = "分组id")
    private String groupId;

    /**
     * 模板名称
     */
    @Schema(description = "模板名称")
    private String telName;

    /**
     * 模板描述
     */
    @Schema(description = "模板描述")
    private String telRemark;

    /**
     * 模板内容
     */
    @Schema(description = "模板内容")
    private String telContent;

    /**
     * 模板生成文件类型
     */
    @Schema(description = "模板生成文件类型")
    private String telFileType;

    /**
     * 模板生成名称
     */
    @Schema(description = "模板生成名称")
    private String fileName;

    /**
     * 状态(0禁用1启用)
     */
    @Schema(description = "状态(0禁用1启用)")
    private String telStatus;

    /**
     * 忽略字段
     */
    @Schema(description = "忽略字段")
    private String ignoreField;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private String telSort;

}
