package com.xht.cloud.generate.module.entity.domain.response;

import com.xht.cloud.framework.core.domain.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：代码生成器-基类
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenCodeBaseClassResponse(代码生成器-基类-响应信息)", description = "代码生成器-基类")
public class GenCodeBaseClassResponse extends Response {

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 类名
     */
    @Schema(description = "类名")
    private String className;

    /**
     * 包名
     */
    @Schema(description = "包名")
    private String packageName;

    /**
     * 忽略字段(写数据库字段)
     */
    @Schema(description = "忽略字段(写数据库字段)")
    private String ignoreField;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private String classSort;

}
