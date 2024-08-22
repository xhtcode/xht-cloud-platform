package com.xht.cloud.file.service;

import com.xht.cloud.file.domain.request.SysMetadataFileQueryRequest;
import com.xht.cloud.file.domain.response.SysMetadataFileResponse;
import com.xht.cloud.file.enums.FileStatusEnums;
import com.xht.cloud.framework.core.domain.response.PageResponse;

/**
 * 描述 ：文件元数据信息
 *
 * @author 小糊涂
 **/
public interface ISysMetadataFileService {

    /**
     * 根据id修改文件状态
     *
     * @param id     {@link String} id集合
     * @param status {@link FileStatusEnums} 文件状态
     */
    void updateFileStatus(String id, FileStatusEnums status);

    /**
     * 根据id删除文件信息
     *
     * @param id {@link String} id集合
     */
    void remove(String id);

    /**
     * 根据id查询文件信息详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysMetadataFileResponse}
     */
    SysMetadataFileResponse findById(String id);

    /**
     * 查询文件列表信息
     *
     * @param queryRequest {@link SysMetadataFileQueryRequest}
     * @return {@link SysMetadataFileResponse} 分页详情
     */
    PageResponse<SysMetadataFileResponse> findPage(SysMetadataFileQueryRequest queryRequest);

}
