package com.xht.cloud.generate.module.table.domain.request;

import com.xht.cloud.framework.domain.request.CreateRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
public class ImportRequest extends CreateRequest {

    @NotEmpty(message = "表名不能为空!")
    private List<String> tableNames;

    private String tableName;

    @NotNull(message = "数据库id不能为空!")
    private Long dbId;

    /**
     * 模块名称
     */
    @NotEmpty(message = "模块名称")
    private String moduleName;

    /**
     * 配置中心
     */
    @NotNull(message = "配置中心")
    private Long configId;

}
