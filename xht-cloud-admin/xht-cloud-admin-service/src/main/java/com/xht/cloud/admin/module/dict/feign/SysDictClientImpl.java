package com.xht.cloud.admin.module.dict.feign;

import com.xht.cloud.admin.api.dict.dto.SysDictItemDTO;
import com.xht.cloud.admin.api.dict.feign.SysDictClient;
import com.xht.cloud.admin.module.dict.service.ISysDictItemService;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.security.resource.annotaion.SkipAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.xht.cloud.framework.domain.R.ok;

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
     * 通过字典ID查找字典
     *
     * @param dictId 字典id
     * @return 同类型字典
     */
    @Override
    @SkipAuthentication
    @GetMapping("/api/dict/item/{dictId}")
    public R<SysDictItemDTO> getSysDictByDictId(@PathVariable("dictId") String dictId) {
        return ok(sysDictItemService.findDTOById(dictId));
    }

    /**
     * 通过字典ID查找字典
     *
     * @param dictType 字典类型
     * @param dictCode 字典编码
     * @return 同类型字典
     */
    @Override
    @GetMapping("/api/dict/item/{dictType}/{dictCode}")
    public R<SysDictItemDTO> findSysDictDTO(@PathVariable("dictType") String dictType, @PathVariable("dictCode") String dictCode) {
        return ok(sysDictItemService.findSysDictDTO(dictType, dictCode));
    }
}
