package com.xht.cloud.admin.module.user.service;

import com.xht.cloud.admin.module.user.domain.request.SysUserAdminCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserAdminQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserAdminUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserAdminResponse;
import com.xht.cloud.framework.domain.response.PageResponse;

import java.util.List;

/**
 * 描述 ：管理员
 *
 * @author : 小糊涂
 **/
public interface ISysUserAdminService {

    /**
     * 创建管理员
     *
     * @param createRequest {@link SysUserAdminCreateRequest}
     * @return {@link Integer} 主键
     */
    Integer create(SysUserAdminCreateRequest createRequest);

    /**
     * 根据id修改管理员
     *
     * @param updateRequest {@link SysUserAdminUpdateRequest}
     */
    Boolean update(SysUserAdminUpdateRequest updateRequest);

    /**
     * 删除管理员
     *
     * @param ids {@link String} id集合
     */
    void remove(List<Integer> ids);

    /**
     * 根据id查询管理员详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysUserAdminResponse}
     */
    SysUserAdminResponse findById(Integer id);


    /**
     * 分页查询管理员
     *
     * @param queryRequest {@link SysUserAdminQueryRequest}
     * @return {@link SysUserAdminResponse} 分页详情
     */
    PageResponse<SysUserAdminResponse> findPage(SysUserAdminQueryRequest queryRequest);

    /**
     * 修改密码
     *
     * @param userId   用户id
     * @param passWord 密码
     * @return {@link Boolean} true 成功
     */
    Boolean updatePassWord(Integer userId,String passWord);

}
