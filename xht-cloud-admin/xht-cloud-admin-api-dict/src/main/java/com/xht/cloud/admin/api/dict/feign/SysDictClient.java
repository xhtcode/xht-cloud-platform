package com.xht.cloud.admin.api.dict.feign;

import com.xht.cloud.admin.api.dict.dto.SysDictItemDTO;
import com.xht.cloud.admin.api.dict.enums.CommonDictTypeEnums;
import com.xht.cloud.admin.api.dict.enums.DictStatusEnums;
import com.xht.cloud.framework.core.server.ServerConstants;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.core.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 描述 ：系统字典
 *
 * @author 小糊涂
 **/
@FeignClient(value = ServerConstants.XHT_CLOUD_ADMIN, contextId = "sysDictClient")
public interface SysDictClient {

    default R<List<SysDictItemDTO>> getSysDictByDictType(CommonDictTypeEnums commonDictType) {
        Assert.notNull(commonDictType, "字典类型不能为空");
        return getSysDictByDictType(commonDictType.getValue(), DictStatusEnums.ALL);
    }

    default R<List<SysDictItemDTO>> getSysDictByDictType(CommonDictTypeEnums commonDictType, DictStatusEnums dictStatus) {
        Assert.notNull(commonDictType, "字典类型不能为空");
        Assert.notNull(dictStatus, "字典状态不能为空");
        return getSysDictByDictType(commonDictType.getValue(), dictStatus);
    }


    default R<List<SysDictItemDTO>> getSysDictByDictType(String dictType) {
        Assert.hasText(dictType, "字典类型不能为空");
        return getSysDictByDictType(dictType, DictStatusEnums.NORMAL);
    }


    /**
     * 通过字典类型查找字典
     *
     * @param dictType   字典类型
     * @param dictStatus 字典状态
     * @return 同类型字典
     */
    @GetMapping("/api/dict/type/{dictType}/{dictStatus}")
    R<List<SysDictItemDTO>> getSysDictByDictType(@PathVariable("dictType") String dictType, @PathVariable("dictStatus") DictStatusEnums dictStatus);

}
