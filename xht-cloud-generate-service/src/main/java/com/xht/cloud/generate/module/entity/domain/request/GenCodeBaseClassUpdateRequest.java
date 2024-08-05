package com.xht.cloud.generate.module.entity.domain.request;

import com.xht.cloud.framework.core.domain.request.UpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 描述 ：代码生成器-基类-修改请求信息
 *
 * @author 小糊涂
 **/
@Data
@Schema(name = "GenCodeBaseClassRequest(代码生成器-基类-修改请求信息)", description = "代码生成器-基类-修改请求信息")
public class GenCodeBaseClassUpdateRequest extends UpdateRequest<String> {

    /**
     * id
     */
    @Schema(description = "id")
    @NotBlank(message = "id `id` 校验不通过")
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

    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return this.id;
    }
}
