package com.xht.cloud.admin.api.dict.feign;

import com.xht.cloud.admin.api.dict.dto.SysDictItemDTO;
import com.xht.cloud.framework.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 描述 ：系统字典
 *
 * @author 小糊涂
 **/
@FeignClient(value = "xht-cloud-admin-service", contextId = "sysDictClient")
public interface SysDictClient {

    /**
     * 通过字典ID查找字典
     *
     * @param dictId 字典id
     * @return 同类型字典
     */
    @GetMapping("/api/dict/item/{dictId}")
    R<SysDictItemDTO> getSysDictByDictId(@PathVariable("dictId") String dictId);

    /**
     * 通过字典ID查找字典
     *
     * @param dictType 字典类型
     * @param dictCode 字典编码
     * @return 同类型字典
     */
    @GetMapping("/api/dict/item/{dictType}/{dictCode}")
    R<SysDictItemDTO> findSysDictDTO(@PathVariable("dictType") String dictType, @PathVariable("dictCode") String dictCode);
}
