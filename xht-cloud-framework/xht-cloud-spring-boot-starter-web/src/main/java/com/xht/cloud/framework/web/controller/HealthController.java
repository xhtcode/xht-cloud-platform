package com.xht.cloud.framework.web.controller;

import com.xht.cloud.framework.core.developer.DeveloperTool;
import com.xht.cloud.framework.core.domain.KeyValue;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.utils.spring.SpringContextUtil;
import com.xht.cloud.framework.utils.support.ClassUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * 描述 ：web服务接口检查
 *
 * @author 小糊涂
 **/
@Tag(name = "开发测试", description = "web服务接口检查")
@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthController {


    @Operation(summary = "监控检查")
    @GetMapping("/health")
    @ResponseBody
    public R<String> health() {
        return R.ok("服务正常!");
    }


    @Operation(summary = "状态查询")
    @GetMapping("/codes")
    @ResponseBody
    public List<KeyValue<Integer, String>> codeStatus() {
        if (DeveloperTool.isProdActiveProfile(SpringContextUtil.getActiveProfile())) {
            return Collections.emptyList();
        }
        return ClassUtils.getIErrorStatusCodeResult();
    }


}
