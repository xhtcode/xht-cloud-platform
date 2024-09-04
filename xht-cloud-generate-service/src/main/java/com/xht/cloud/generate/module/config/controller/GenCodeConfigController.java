package com.xht.cloud.generate.module.config.controller;

import cn.hutool.core.io.IoUtil;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.domain.KeyValue;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.generate.exception.GenerateException;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigCreateRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigQueryRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigUpdateRequest;
import com.xht.cloud.generate.module.config.domain.response.GenCodeConfigResponse;
import com.xht.cloud.generate.module.config.service.IGenCodeConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.xht.cloud.framework.domain.R.ok;

/**
 * 描述 ：代码生成器-配置中心
 *
 * @author 小糊涂
 **/
@Slf4j
@RestController
@RequestMapping("/gen/code/config")
@RequiredArgsConstructor
@Tag(name = "代码生成器-配置中心")
public class GenCodeConfigController {

    private final IGenCodeConfigService genCodeConfigService;

    /**
     * 创建
     *
     * @param createRequest {@link GenCodeConfigCreateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-代码生成器-配置中心")
    @PostMapping
    public R<Boolean> create(@Validated @RequestBody GenCodeConfigCreateRequest createRequest) {
        genCodeConfigService.create(createRequest);
        return ok(true);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest {@link GenCodeConfigUpdateRequest}
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-代码生成器-配置中心")
    @PutMapping
    public R<Boolean> update(@Validated @RequestBody GenCodeConfigUpdateRequest updateRequest) {
        genCodeConfigService.update(updateRequest);
        return ok(true);
    }

    /**
     * 根据id删除
     *
     * @param ids {@link String} id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-代码生成器-配置中心")
    @Parameter(name = "id", description = "id", required = true)
    @DeleteMapping
    public R<Boolean> remove(@RequestBody List<String> ids) {
        genCodeConfigService.remove(ids);
        return ok(true);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenCodeConfigResponse}
     */
    @Operation(summary = "根据id查询详细-配置中心")
    @GetMapping("/{id}")
    public R<GenCodeConfigResponse> findById(@PathVariable("id") String id) {
        return R.ok(genCodeConfigService.findById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenCodeConfigQueryRequest}
     * @return {@link GenCodeConfigResponse} 分页详情
     */
    @Operation(summary = "分页查询-配置中心")
    @GetMapping
    public R<PageResponse<GenCodeConfigResponse>> findPage(GenCodeConfigQueryRequest queryRequest) {
        return R.ok(genCodeConfigService.findPage(queryRequest));
    }


    /**
     * 查询全部
     *
     * @return {@link GenCodeConfigResponse}
     */
    @Operation(summary = "查询全部-配置中心")
    @GetMapping("/list/")
    public R<List<GenCodeConfigResponse>> list() {
        return R.ok(genCodeConfigService.list());
    }

    /**
     * 导出-配置中心
     *
     * @param configId 配置id
     * @param response {@link HttpServletResponse}
     */
    @Operation(summary = "导出-配置中心")
    @GetMapping("/export/{configId}")
    public void exportZip(@PathVariable("configId") Long configId, HttpServletResponse response) {
        try {
            KeyValue<String, byte[]> keyValue = genCodeConfigService.exportZip(configId);
            byte[] data = keyValue.getValue();
            response.reset();
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.zip\"", keyValue.getKey()));
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IoUtil.write(response.getOutputStream(), true, data);
        } catch (Exception e) {
            log.info("代码下载异常{}", e.getMessage(), e);
            throw new GenerateException("代码下载异常！");
        }
    }

    /**
     * 导入-配置中心
     *
     * @param file {@link MultipartFile} 文件信息
     */
    @Operation(summary = "导入-配置中心")
    @PostMapping("/import")
    public R<Boolean> importZip(@RequestPart("file") MultipartFile file) {
        return R.ok(genCodeConfigService.importZip(file));
    }
}
