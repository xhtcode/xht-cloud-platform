package com.xht.cloud.admin.api.sequence.feign;

import com.xht.cloud.framework.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 描述 ：序列
 *
 * @author 小糊涂
 **/
@FeignClient(value = "xht-cloud-admin-service", contextId = "generateSequenceClient")
public interface GenerateSequenceClient {


    /**
     * 生成系列
     *
     * @param typeCode 系列类型编码
     * @param count    序列生成个数
     * @param seqCode  序列编码
     * @return {@link String} 序列值
     */
    @PostMapping("/api/sys/sequence/generate/{typeCode}")
    R<String> generate(@PathVariable("typeCode") Integer typeCode, @RequestParam(value = "count", defaultValue = "1") Integer count, @RequestParam(value = "seqCode", required = false) String seqCode);
}
