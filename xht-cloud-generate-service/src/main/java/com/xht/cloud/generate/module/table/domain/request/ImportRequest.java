package com.xht.cloud.generate.module.table.domain.request;

import com.xht.cloud.framework.core.domain.request.CreateRequest;
import com.xht.cloud.framework.web.validation.group.Create;
import com.xht.cloud.framework.web.validation.group.Query;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
public class ImportRequest extends CreateRequest {

    @NotEmpty(groups = {Create.class}, message = "表名不能为空!")
    private List<String> tableNames;

    private String tableName;

    @NotEmpty(groups = {Query.class, Create.class}, message = "数据库id不能为空!")
    private String dbId;
}
