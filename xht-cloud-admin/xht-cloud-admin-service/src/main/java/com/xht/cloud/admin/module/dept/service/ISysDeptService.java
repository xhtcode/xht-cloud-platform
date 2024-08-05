package com.xht.cloud.admin.module.dept.service;

import com.xht.cloud.admin.module.dept.domain.request.SysDeptCreateRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysDeptQueryRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysDeptUpdateRequest;
import com.xht.cloud.admin.module.dept.domain.response.SysDeptResponse;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysMenuDO;
import com.xht.cloud.framework.core.domain.request.Request;
import com.xht.cloud.framework.utils.treenode.INode;

import java.util.List;

/**
 * 描述 ：部门
 *
 * @author 小糊涂
 **/
public interface ISysDeptService {

    /**
     * 创建
     *
     * @param createRequest {@link SysDeptCreateRequest}
     * @return {@link String} 主键
     */
    String create(SysDeptCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysDeptUpdateRequest}
     */
    void update(SysDeptUpdateRequest updateRequest);

    /**
     * 校验
     *
     * @param request {@link SysDeptUpdateRequest} {@link SysDeptCreateRequest}
     */
    void validate(Request request) throws Exception;

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
     * @return {@link SysDeptResponse}
     */
    SysDeptResponse findById(String id);

    /**
     * 查询
     *
     * @param queryRequest {@link SysDeptQueryRequest}
     * @return {@link List<SysDeptResponse>} 详情
     */
    List<SysDeptResponse> findList(SysDeptQueryRequest queryRequest);

    /**
     * 部门 转换成树结构
     *
     * @param deptResponses {@link SysMenuDO} 部门
     * @return 树结构
     */
    List<INode<String>> convert(List<SysDeptResponse> deptResponses);

}
