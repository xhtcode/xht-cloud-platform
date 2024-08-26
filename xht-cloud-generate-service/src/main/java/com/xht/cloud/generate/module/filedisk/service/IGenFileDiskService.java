package com.xht.cloud.generate.module.filedisk.service;

import com.xht.cloud.generate.module.filedisk.domain.request.GenFileDiskCreateRequest;
import com.xht.cloud.generate.module.filedisk.domain.request.GenFileDiskQueryRequest;
import com.xht.cloud.generate.module.filedisk.domain.request.GenFileDiskUpdateRequest;
import com.xht.cloud.generate.module.filedisk.domain.response.GenFileDiskResponse;

import java.util.List;

/**
 * 文件管理
 *
 * @author 小糊涂
 */
public interface IGenFileDiskService {

    /**
     * 创建文件
     *
     * @param createRequest {@link GenFileDiskCreateRequest} 创建参数
     */
    void create(GenFileDiskCreateRequest createRequest);


    /**
     * 根据id修改文件
     *
     * @param updateRequest {@link GenFileDiskUpdateRequest} 修改参数
     */
    void update(GenFileDiskUpdateRequest updateRequest);

    /**
     * 根据id删除文件
     *
     * @param ids id集合
     */
    void remove(List<String> ids);

    /**
     * 根据id查询详细文件
     *
     * @param id 数据库主键
     * @return {@link GenFileDiskResponse} 文件详细
     */
    GenFileDiskResponse findById(String id);

    /**
     * 查询文件列表
     *
     * @param queryRequest {@link GenFileDiskQueryRequest} 查询参数
     * @return 分页详情
     */
    List<GenFileDiskResponse> findList(GenFileDiskQueryRequest queryRequest);

    /**
     * 按条件查询文件
     *
     * @param configId 配置信息id
     * @param parentId 上级目录id
     * @return 文件信息
     */
    List<GenFileDiskResponse> findListInfo(Long configId, String parentId);

    void moveFile(String source, String target, Long configId);
}
