package com.xht.cloud.admin.module.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.cloud.admin.exceptions.SequenceException;
import com.xht.cloud.admin.module.user.convert.SysUserAdminConvert;
import com.xht.cloud.admin.module.user.dao.SysUserAdminDao;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserAdminDO;
import com.xht.cloud.admin.module.user.domain.request.SysUserAdminCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserAdminQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserAdminUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserAdminResponse;
import com.xht.cloud.admin.module.user.service.ISysUserAdminService;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述 ：管理员
 *
 * @author : 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserAdminServiceImpl implements ISysUserAdminService {

    private final SysUserAdminDao sysUserAdminDao;

    private final SysUserAdminConvert sysUserAdminConvert;

    private final PasswordEncoder passwordEncoder;

    /**
     * 创建管理员
     *
     * @param createRequest {@link SysUserAdminCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer create(SysUserAdminCreateRequest createRequest) {
        Assert.notNull(createRequest, "管理员添加信息不能为空");
        if (sysUserAdminDao.existUserName(createRequest.getUserName())) {
            throw new SequenceException(String.format("管理员账号`%s`不能重复", createRequest.getUserName()));
        }
        SysUserAdminDO entity = sysUserAdminConvert.toDO(createRequest);
        entity.setPassWord(passwordEncoder.encode("123456"));
        sysUserAdminDao.save(entity);
        return entity.getId();
    }

    /**
     * 根据id修改管理员
     *
     * @param updateRequest SysUserAdminUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(SysUserAdminUpdateRequest updateRequest) {
        Assert.notNull(updateRequest, "管理员修改信息不能为空");
        Assert.notNull(updateRequest.getPkId(), "管理员修改信息id不能为空");
        SysUserAdminDO userAdminDO = sysUserAdminConvert.toDO(updateRequest);
        userAdminDO.setId(updateRequest.getPkId());
        return sysUserAdminDao.update(userAdminDO);
    }


    /**
     * 删除管理员
     *
     * @param ids {@link String} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<Integer> ids) {
        Assert.notEmpty(ids, "系统配置信息ids不能为空");
        sysUserAdminDao.removeByIds(ids);
    }

    /**
     * 根据id查询管理员详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysUserAdminResponse}
     */
    @Override
    public SysUserAdminResponse findById(Integer id) {
        return sysUserAdminConvert.toResponse(sysUserAdminDao.getOptById(id).orElse(null));
    }

    /**
     * 分页查询管理员
     *
     * @param queryRequest {@link SysUserAdminQueryRequest}
     * @return {@link SysUserAdminResponse} 分页详情
     */
    @Override
    public PageResponse<SysUserAdminResponse> findPage(SysUserAdminQueryRequest queryRequest) {
        Page<SysUserAdminDO> sysConfigIPage = sysUserAdminDao.findPage(queryRequest);
        return sysUserAdminConvert.toPageResponse(sysConfigIPage);
    }

    /**
     * 修改密码
     *
     * @param userId   用户id
     * @param passWord 密码
     * @return {@link Boolean} true 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePassWord(Integer userId, String passWord) {
        Assert.notNull(userId, "管理员ID查询不到！");
        Assert.hasText(passWord, "密码不能为空！");
        passWord = passwordEncoder.encode(passWord);
        return sysUserAdminDao.updatePassWord(userId, passWord);
    }

}
