package com.xht.cloud.file.controller;

import com.xht.cloud.framework.core.R;
import com.xht.cloud.framework.file.domain.cmd.bucket.BaseBucketCmd;
import com.xht.cloud.framework.file.domain.dto.BucketDTO;
import com.xht.cloud.framework.file.utils.FileSizeFormat;
import com.xht.cloud.framework.minio.service.MinioOssTemplate;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述 ：OSS 存储桶管理
 *
 * @author 小糊涂
 **/
@Slf4j
@Tag(name = "MinIo 存储桶管理")
@RequestMapping("/oss/minio/bucket")
@RestController
@RequiredArgsConstructor
public class MinioBucketController {

    private final MinioOssTemplate ossBucketOperations;

    private final MinioClient minioClient;

    /**
     * 查询所有存储桶
     *
     * @return Bucket 列表
     */
    @Operation(summary = "查询所有存储桶")
    @GetMapping("/list")
    public R<List<BucketDTO>> listBuckets() throws Exception {
        List<BucketDTO> bucketDTOS = ossBucketOperations.listBuckets();
        for (BucketDTO bucketDTO : bucketDTOS) {
            long fileCount = 0;
            long fileSizeCount = 0;
            ListObjectsArgs build = ListObjectsArgs.builder()
                    .bucket(bucketDTO.getBucketName())
                    .recursive(true)
                    .build();
            Iterable<Result<Item>> results = minioClient.listObjects(build);
            for (Result<Item> result : results) {
                Item item = result.get();
                fileCount += 1;
                fileSizeCount += item.size();
            }
            bucketDTO.setFileCount(fileCount);
            bucketDTO.setFileSizeCount(FileSizeFormat.formatFileSize(fileSizeCount));
        }
        return R.ok(bucketDTOS);
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return 是否存在，true 存在，false 不存在
     */
    @Operation(summary = "存储桶是否存在", parameters = {@Parameter(name = "bucketName", description = "存储桶名称")})
    @GetMapping("/exists/{bucketName}")
    public R<Boolean> bucketExists(@PathVariable("bucketName") String bucketName) {
        return R.ok(ossBucketOperations.bucketExists(bucketName));
    }

    /**
     * 创建存储桶
     *
     * @param bucketCmd 存储桶信息
     */
    @Operation(summary = "创建存储桶")
    @PostMapping("/create")
    public R<Boolean> makeBucket(@RequestBody BaseBucketCmd bucketCmd) {
        ossBucketOperations.makeBucket(bucketCmd);
        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除一个已存在的存储桶 如果存储桶存在对象不存在时，删除会报错。
     *
     * @param bucketName 存储桶名称
     */
    @Operation(summary = "删除一个已存在的存储桶", parameters = {@Parameter(name = "bucketName", description = "存储桶名称")})
    @DeleteMapping("/remove/{bucketName}")
    public R<Boolean> removeBucket(@PathVariable("bucketName") String bucketName) {
        ossBucketOperations.removeBucket(bucketName);
        return R.ok(Boolean.TRUE);
    }
}
