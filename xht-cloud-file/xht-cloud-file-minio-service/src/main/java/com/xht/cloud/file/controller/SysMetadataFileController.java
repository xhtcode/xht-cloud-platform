package com.xht.cloud.file.controller;

import com.xht.cloud.file.domain.request.SysMetadataFileQueryRequest;
import com.xht.cloud.file.domain.response.SysMetadataFileResponse;
import com.xht.cloud.file.enums.FileStatusEnums;
import com.xht.cloud.file.service.ISysMetadataFileService;
import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.xht.cloud.framework.core.R.ok;

/**
 * 描述 ：文件元数据信息
 *
 * @author 小糊涂
 **/
@Slf4j
@Tag(name = "文件元数据信息")
@RequestMapping("/sys/file/metadata")
@RestController
@RequiredArgsConstructor
public class SysMetadataFileController {

    private final ISysMetadataFileService sysMetadataFileService;

    /**
     * 根据id修改文件状态
     *
     * @param id     {@link String} id集合
     * @param status {@link FileStatusEnums} 文件状态
     */
    @Operation(summary = "根据id修改文件状态")
    @PutMapping("/{id}/{status}")
    public R<Boolean> updateFileStatus(@PathVariable("id") String id, @PathVariable("status") FileStatusEnums status) {
        sysMetadataFileService.updateFileStatus(id, status);
        return ok(Boolean.TRUE);
    }

    /**
     * 根据id删除文件信息
     *
     * @param id {@link String} id集合
     */
    @Operation(summary = "根据id删除文件信息")
    @DeleteMapping("/{id}")
    public R<Boolean> remove(@PathVariable("id") String id) {
        sysMetadataFileService.remove(id);
        return ok(Boolean.TRUE);
    }

    /**
     * 根据id查询文件信息详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysMetadataFileResponse}
     */
    @Operation(summary = "根据id查询文件信息详细")
    @GetMapping("/{id}")
    public R<SysMetadataFileResponse> findById(@PathVariable("id") String id) {
        return ok(sysMetadataFileService.findById(id));
    }

    /**
     * 分页查询文件信息
     *
     * @param queryRequest {@link SysMetadataFileQueryRequest}
     * @return {@link PageResponse<SysMetadataFileResponse>} 分页详情
     */
    @Operation(summary = "分页查询文件信息")
    @GetMapping
    public R<PageResponse<SysMetadataFileResponse>> findPage(SysMetadataFileQueryRequest queryRequest) {
        return ok(sysMetadataFileService.findPage(queryRequest));
    }
}
