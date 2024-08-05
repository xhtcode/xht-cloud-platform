package com.xht.cloud.admin.module.dict.feign;

import com.xht.cloud.admin.api.dict.dto.SysDictItemDTO;
import com.xht.cloud.admin.api.dict.enums.DictStatusEnums;
import com.xht.cloud.admin.api.dict.feign.SysDictClient;
import com.xht.cloud.admin.module.dict.service.ISysDictItemService;
import com.xht.cloud.framework.security.resource.annotaion.SkipAuthentication;
import com.xht.cloud.framework.core.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.xht.cloud.framework.core.R.ok;

/**
 * 描述 ：字典信息查询内部专用
 *
 * @author 小糊涂
 **/
@Tag(name = "字典信息查询（内部专用）")
@RestController
@RequiredArgsConstructor
public class SysDictClientImpl implements SysDictClient {

    private final ISysDictItemService sysDictItemService;

    /**
     * 通过字典类型查找字典
     *
     * @param dictType   字典类型
     * @param dictStatus 字典状态
     * @return 同类型字典
     */
    @Override
    @Operation(summary = "通过字典类型查找字典")
    @GetMapping("/api/dict/type/{dictType}/{dictStatus}")
    @SkipAuthentication
    public R<List<SysDictItemDTO>> getSysDictByDictType(@PathVariable("dictType") String dictType, @PathVariable("dictStatus") DictStatusEnums dictStatus) {
        return ok(sysDictItemService.findByDictType(dictType, dictStatus));
    }
}
