package com.xht.cloud.admin.module.user.service;

import com.xht.cloud.admin.api.user.dto.SysUserResDTO;
import com.xht.cloud.admin.module.user.domain.request.SysAdminCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysAdminQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysAdminUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysAdminResponse;
import com.xht.cloud.framework.core.domain.response.PageResponse;

import java.util.List;

/**
 * 描述 ：管理员
 *
 * @author : 小糊涂
 **/
public interface ISysAdminService {

    /**
     * 创建管理员
     *
     * @param createRequest {@link SysAdminCreateRequest}
     * @return {@link Integer} 主键
     */
    Integer create(SysAdminCreateRequest createRequest);

    /**
     * 根据id修改管理员
     *
     * @param updateRequest {@link SysAdminUpdateRequest}
     */
    Boolean update(SysAdminUpdateRequest updateRequest);

    /**
     * 删除管理员
     *
     * @param ids {@link List <String>} id集合
     */
    void remove(List<Integer> ids);

    /**
     * 根据id查询管理员详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysAdminResponse}
     */
    SysAdminResponse findById(Integer id);


    /**
     * 分页查询管理员
     *
     * @param queryRequest {@link SysAdminQueryRequest}
     * @return {@link SysAdminResponse} 分页详情
     */
    PageResponse<SysAdminResponse> findPage(SysAdminQueryRequest queryRequest);

    /**
     * 修改密码
     *
     * @param userId   用户id
     * @param passWord 密码
     * @return {@link Boolean} true 成功
     */
    Boolean updatePassWord(Integer userId,String passWord);

    /**
     * 根据管理员账号查询管理员
     *
     * @param userName 管理员账号
     * @return {@link SysUserResDTO} 管理员信息
     */
    SysAdminResponse findUserByAccount(String userName);
}
