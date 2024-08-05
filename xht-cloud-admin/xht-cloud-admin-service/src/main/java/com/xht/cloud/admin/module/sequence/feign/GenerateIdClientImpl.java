package com.xht.cloud.admin.module.sequence.feign;

import com.xht.cloud.admin.api.sequence.feign.GenerateSequenceClient;
import com.xht.cloud.admin.enums.GenerateIdType;
import com.xht.cloud.admin.exceptions.SequenceException;
import com.xht.cloud.admin.module.sequence.domain.request.IdRequest;
import com.xht.cloud.admin.module.sequence.generate.GenerateIdFactory;
import com.xht.cloud.framework.core.R;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 ：id生成
 *
 * @author 小糊涂
 **/
@Slf4j
@Tag(name = "系列生成（内部专用）")
@RestController
@RequiredArgsConstructor
public class GenerateIdClientImpl implements GenerateSequenceClient {

    /**
     * 生成系列
     *
     * @param typeCode 系列类型编码
     * @param count    序列生成个数
     * @param seqCode  序列编码
     * @return {@link String} 序列值
     */
    @Override
    @PostMapping("/api/sys/sequence/generate/{typeCode}")
    public R<String> generate(@PathVariable("typeCode") Integer typeCode,
                              @RequestHeader(value = "count", defaultValue = "1") Integer count,
                              @RequestHeader(value = "seqCode", required = false) String seqCode) {
        GenerateIdType generateIdType = GenerateIdType.generateIdType(typeCode);
        return R.ok(GenerateIdFactory.
                getHandler(generateIdType).orElseThrow(() -> new SequenceException("找不到系列类型"))
                .generate(IdRequest.builder().code(typeCode).count(count).seqCode(seqCode).build()));
    }

}
