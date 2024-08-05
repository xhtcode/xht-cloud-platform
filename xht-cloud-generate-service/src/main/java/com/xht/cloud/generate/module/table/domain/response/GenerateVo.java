package com.xht.cloud.generate.module.table.domain.response;

import com.xht.cloud.generate.module.column.domain.response.GenTableColumnResponse;
import lombok.Data;

import java.util.List;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
public class GenerateVo {

    private GenTableResponse table;

    private List<GenTableColumnResponse> columns;
}
