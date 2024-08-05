package com.xht.cloud.admin.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.api.user.dto.SysUserResDTO;
import com.xht.cloud.admin.exceptions.SequenceException;
import com.xht.cloud.admin.module.user.convert.SysAdminConvert;
import com.xht.cloud.admin.module.user.domain.dataobject.SysAdminDO;
import com.xht.cloud.admin.module.user.domain.request.SysAdminCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysAdminQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysAdminUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysAdminResponse;
import com.xht.cloud.admin.module.user.mapper.SysAdminMapper;
import com.xht.cloud.admin.module.user.service.ISysAdminService;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.mybatis.tool.SqlHelper;
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
public class SysAdminServiceImpl implements ISysAdminService {

    private final SysAdminMapper sysAdminMapper;

    private final SysAdminConvert sysAdminConvert;

    private final PasswordEncoder passwordEncoder;

    /**
     * 创建管理员
     *
     * @param createRequest {@link SysAdminCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer create(SysAdminCreateRequest createRequest) {
        Assert.notNull(createRequest, "管理员添加信息不能为空");
        long SysAdminCount = sysAdminMapper.selectCount(SysAdminDO::getUserName, createRequest.getUserName());
        if (SysAdminCount > 0) {
            throw new SequenceException(String.format("管理员账号`%s`不能重复", createRequest.getUserName()));
        }
        SysAdminDO entity = sysAdminConvert.toDO(createRequest);
        entity.setPassWord(passwordEncoder.encode("123456"));
        sysAdminMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 根据id修改管理员
     *
     * @param updateRequest SysAdminUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(SysAdminUpdateRequest updateRequest) {
        // @formatter:off
        Assert.notNull(updateRequest, "管理员修改信息不能为空");
        Assert.notNull(updateRequest.getPkId(), "管理员修改信息id不能为空");
        LambdaUpdateWrapper<SysAdminDO> seqCodeQuery = sysAdminConvert.lambdaUpdate(sysAdminConvert.toDO(updateRequest))
                .eq(SysAdminDO::getUserName, updateRequest.getUserName())
                .ne(SysAdminDO::getId, updateRequest.getPkId());
        return SqlHelper.update(sysAdminMapper.update(seqCodeQuery));
        // @formatter:on
    }


    /**
     * 删除管理员
     *
     * @param ids {@link List <String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<Integer> ids) {
        Assert.notEmpty(ids, "系统配置信息ids不能为空");
        sysAdminMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id查询管理员详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysAdminResponse}
     */
    @Override
    public SysAdminResponse findById(Integer id) {
        return sysAdminConvert.toResponse(sysAdminMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询管理员
     *
     * @param queryRequest {@link SysAdminQueryRequest}
     * @return {@link SysAdminResponse} 分页详情
     */
    @Override
    public PageResponse<SysAdminResponse> findPage(SysAdminQueryRequest queryRequest) {
        IPage<SysAdminDO> sysConfigIPage = sysAdminMapper.selectPage(PageTool.getPage(queryRequest), sysAdminConvert.lambdaQuery(sysAdminConvert.toDO(queryRequest)));
        return sysAdminConvert.toPageResponse(sysConfigIPage);
    }

    /**
     * 修改密码
     *
     * @param userId   用户id
     * @param passWord 密码
     * @return {@link Boolean} true 成功
     */
    @Override
    public Boolean updatePassWord(Integer userId, String passWord) {
        Assert.notNull(userId, "管理员ID查询不到！");
        Assert.hasText(passWord, "密码不能为空！");
        LambdaUpdateWrapper<SysAdminDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(SysAdminDO::getPassWord, passWord)
                .eq(SysAdminDO::getId, userId);
        return SqlHelper.update(sysAdminMapper.update(lambdaUpdateWrapper));
    }

    /**
     * 根据管理员账号查询管理员
     *
     * @param userName 管理员账号
     * @return {@link SysUserResDTO} 管理员信息
     */
    @Override
    public SysAdminResponse findUserByAccount(String userName) {
        SysAdminDO sysAdminDO = sysAdminMapper.selectOne(SysAdminDO::getUserName, userName).orElse(null);
        return sysAdminConvert.toResponse(sysAdminDO);
    }
}
