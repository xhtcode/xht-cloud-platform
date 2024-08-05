package com.xht.cloud.generate.core.controller.request;


import com.xht.cloud.framework.core.domain.request.CreateRequest;
import com.xht.cloud.generate.core.groups.DownGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 描述 ：生成代码请求
 *
 * @author 小糊涂
 **/
@Data
@Schema(description = "GenCodeRequest(生成代码请求)")
public class GenCodeRequest extends CreateRequest {

    @NotEmpty(message = "作者不能为空")
    @Schema(description = "作者")
    private String author;

    @NotEmpty(message = "项目名称不能为空")
    @Schema(description = "项目名称")
    private String projectName;

    @NotEmpty(message = "包名不能为空")
    @Schema(description = "包名")
    private String packageName;

    @NotEmpty(message = "表id不能为空", groups = {DownGroups.class})
    @Schema(description = "表id")
    private List<String> tableIds;

}