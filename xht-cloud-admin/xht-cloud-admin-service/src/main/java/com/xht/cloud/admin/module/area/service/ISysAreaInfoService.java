package com.xht.cloud.admin.module.area.service;

import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoCreateRequest;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoQueryRequest;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoUpdateRequest;
import com.xht.cloud.admin.module.area.domain.response.SysAreaInfoResponse;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.utils.treenode.INode;

import java.util.List;

/**
 * 描述 ：地区信息
 *
 * @author 小糊涂
 **/
public interface ISysAreaInfoService {

    /**
     * 创建
     *
     * @param createRequest {@link SysAreaInfoCreateRequest}
     * @return {@link String} 主键
     */
    String create(SysAreaInfoCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysAreaInfoUpdateRequest}
     */
    void update(SysAreaInfoUpdateRequest updateRequest);

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    void remove(List<String> ids);

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysAreaInfoResponse}
     */
    SysAreaInfoResponse findById(String id);

    /**
     * 按条件查询全部
     *
     * @param queryRequest {@link SysAreaInfoQueryRequest}
     * @return {@link PageResponse<SysAreaInfoResponse>} 详情
     */
    List<SysAreaInfoResponse> list(SysAreaInfoQueryRequest queryRequest);

    /**
     * 地区 转换成树结构
     *
     * @param queryRequest {@link SysAreaInfoQueryRequest}
     * @return 树结构
     */
    List<INode<String>> convert(SysAreaInfoQueryRequest queryRequest);

}
