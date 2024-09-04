package com.xht.cloud.generate.module.filedisk.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xht.cloud.framework.domain.R;
import com.xht.cloud.framework.utils.treenode.INode;
import com.xht.cloud.framework.utils.treenode.TreeNode;
import com.xht.cloud.framework.utils.treenode.TreeUtils;
import com.xht.cloud.generate.module.filedisk.domain.request.GenFileDiskCreateRequest;
import com.xht.cloud.generate.module.filedisk.domain.request.GenFileDiskQueryRequest;
import com.xht.cloud.generate.module.filedisk.domain.request.GenFileDiskUpdateRequest;
import com.xht.cloud.generate.module.filedisk.domain.response.GenFileDiskResponse;
import com.xht.cloud.generate.module.filedisk.service.IGenFileDiskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.xht.cloud.framework.domain.R.ok;

/**
 * 文件管理
 *
 * @author 小糊涂
 */
@Tag(name = "GenFileDiskController", description = "文件管理")
@RestController
@RequestMapping("/region/genFileDiskDO")
@RequiredArgsConstructor
public class GenFileDiskController {

    private final IGenFileDiskService fileDiskService;

    /**
     * 创建文件
     *
     * @param createRequest {@link GenFileDiskCreateRequest} 创建参数
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "创建-文件")
    @PostMapping
    public R<Boolean> create(@Validated @RequestBody GenFileDiskCreateRequest createRequest) {
        fileDiskService.create(createRequest);
        return ok(Boolean.TRUE);
    }

    /**
     * 根据id修改文件
     *
     * @param updateRequest {@link GenFileDiskUpdateRequest} 修改参数
     * @return {@linkplain Boolean} true成功 false失败
     */
    @Operation(summary = "根据id修改-文件")
    @PutMapping
    public R<Boolean> update(@Validated @RequestBody GenFileDiskUpdateRequest updateRequest) {
        fileDiskService.update(updateRequest);
        return ok(Boolean.TRUE);
    }

    @Operation(summary = "根据id修改-文件")
    @PutMapping("/{source}/{target}/{configId}")
    public R<Boolean> moveFile(@PathVariable("source") String source, @PathVariable("target") String target, @PathVariable("configId") Long configId) {
        fileDiskService.moveFile(source, target, configId);
        return ok(Boolean.TRUE);
    }

    /**
     * 根据id删除文件
     *
     * @param ids id集合
     * @return {@link Boolean} true成功 false失败
     */
    @Operation(summary = "根据id删除-文件")
    @DeleteMapping
    public R<Boolean> remove(@RequestBody List<String> ids) {
        fileDiskService.remove(ids);
        return ok(Boolean.TRUE);
    }

    /**
     * 根据id查询详细文件
     *
     * @param id 数据库主键
     * @return {@link GenFileDiskResponse} 文件详细
     */
    @Operation(summary = "根据id查询详细-文件")
    @GetMapping("/{id}")
    public R<GenFileDiskResponse> findById(@PathVariable("id") String id) {
        return R.ok(fileDiskService.findById(id));
    }

    /**
     * 查询文件列表
     *
     * @param queryRequest {@link GenFileDiskQueryRequest} 查询参数
     * @return 分页详情
     */
    @Operation(summary = "分页查询-文件")
    @GetMapping
    public R<List<GenFileDiskResponse>> findList(@Validated GenFileDiskQueryRequest queryRequest) {
        return R.ok(fileDiskService.findList(queryRequest));
    }

    @GetMapping("/tree")
    public R<List<INode<String>>> findTreeList(@Validated GenFileDiskQueryRequest queryRequest) {
        List<GenFileDiskResponse> fileDiskServiceList = fileDiskService.findList(queryRequest);
        List<INode<String>> result = new ArrayList<>();
        for (GenFileDiskResponse response : fileDiskServiceList) {
            result.add(new TreeNode<>(response.getId(), response.getParentId(), response.getFileSort()).setExtra(BeanUtil.beanToMap(response)));
        }
        return R.ok(TreeUtils.buildList(result));
    }

    /**
     * 按条件查询文件
     *
     * @param configId 配置信息id
     * @param parentId 上级目录id
     * @return 文件信息
     */
    @Operation(summary = "分页查询-按条件查询文件")
    @GetMapping("/{configId}/{parentId}")
    public R<List<GenFileDiskResponse>> findListInfo(@PathVariable("configId") Long configId, @PathVariable("parentId") String parentId) {
        return R.ok(fileDiskService.findListInfo(configId, parentId));
    }

}
