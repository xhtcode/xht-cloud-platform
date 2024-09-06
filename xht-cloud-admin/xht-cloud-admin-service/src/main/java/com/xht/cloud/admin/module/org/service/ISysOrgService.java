package com.xht.cloud.admin.module.org.service;

import cn.hutool.core.lang.tree.Tree;
import com.xht.cloud.admin.module.org.domain.request.SysOrgCreateRequest;
import com.xht.cloud.admin.module.org.domain.request.SysOrgQueryRequest;
import com.xht.cloud.admin.module.org.domain.request.SysOrgUpdateRequest;
import com.xht.cloud.admin.module.org.domain.response.SysOrgResponse;
import com.xht.cloud.framework.domain.response.PageResponse;

import java.util.List;

/**
 * 组织机构
 *
 * @author 小糊涂
 */
public interface ISysOrgService {

    /**
     * 新增组织机构
     *
     * @param createRequest {@link SysOrgCreateRequest} 创建参数
     */
    boolean create(SysOrgCreateRequest createRequest);

    /**
     * 根据主键修改组织机构
     *
     * @param updateRequest {@link SysOrgUpdateRequest} 修改参数
     */
    boolean update(SysOrgUpdateRequest updateRequest);

    /**
     * 根据主键删除{serviceDesc}
     *
     * @param id 主键
     */
    boolean remove(Long id);

    /**
     * 根据主键批量删除{serviceDesc}
     *
     * @param ids 主键集合
     */
    boolean removeBatch(List<Long> ids);

    /**
     * 根据主键查询组织机构详细
     *
     * @param id 主键
     * @return {@link SysOrgResponse} 数据详细
     */
    SysOrgResponse findById(Long id);

    /**
     * 分页查询组织机构
     *
     * @param queryRequest {@link SysOrgQueryRequest} 查询参数
     * @return 分页数据
     */
    PageResponse<SysOrgResponse> findPage(SysOrgQueryRequest queryRequest);

    /**
     * 获取组织树
     *
     * @return 数据
     */
    List<Tree<Long>> tree();
}
