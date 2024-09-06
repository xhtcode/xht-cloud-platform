package com.xht.cloud.admin.module.user.service.impl;

import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import com.xht.cloud.admin.module.area.dao.SysAreaInfoDao;
import com.xht.cloud.admin.module.area.domain.dataobject.SysAreaInfoDO;
import com.xht.cloud.admin.module.user.convert.SysUserStaffConvert;
import com.xht.cloud.admin.module.user.dao.SysUserStaffDao;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserStaffDO;
import com.xht.cloud.admin.module.user.domain.request.SysUserQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserStaffCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserStaffUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserStaffResponse;
import com.xht.cloud.admin.module.user.service.ISysUserStaffService;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.xht.cloud.admin.constant.UserConstant.UPDATE_USER_DEPT_ID;
import static com.xht.cloud.admin.constant.UserConstant.UPDATE_USER_STATUS;

/**
 * 描述 ：用户
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserStaffServiceImpl implements ISysUserStaffService {

    private final SysUserStaffDao sysUserStaffDao;

    private final PasswordEncoder passwordEncoder;

    private final SysUserStaffConvert sysUserStaffConvert;

    private final SysAreaInfoDao sysAreaInfoDao;

    /**
     * 创建用户
     *
     * @param createRequest {@link SysUserStaffCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(SysUserStaffCreateRequest createRequest) {
        Assert.notNull(createRequest, "用户添加信息不能为空");
        SysUserStaffDO entity = sysUserStaffConvert.toDO(createRequest);
        SysAreaInfoDO sysAreaInfoDO = sysAreaInfoDao.getOptById(entity.getAddressId()).orElseGet(SysAreaInfoDO::new);
        entity.setUserName(RandomUtil.randomNumbers(10));
        String randomGenerator = new RandomGenerator(6).generate();
        entity.setPassWord(passwordEncoder.encode(String.format("123456%s", randomGenerator)));
        entity.setPassWordSalt(randomGenerator);
        entity.setUserStatus(UserStatusEnums.NORMAL);
        entity.setRegisteredTime(LocalDateTime.now());
        entity.setAddressName(sysAreaInfoDO.getName());
        sysUserStaffDao.save(entity);
        return entity.getUserName();
    }

    /**
     * 根据id修改用户
     *
     * @param updateRequest {@link SysUserStaffUpdateRequest}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(SysUserStaffUpdateRequest updateRequest) {
        Assert.notNull(updateRequest, "修改的用户信息不能为空");
        String addressName = "";
        if (StringUtils.hasText(updateRequest.getAddressId())) {
            SysAreaInfoDO sysAreaInfoDO = sysAreaInfoDao.getOptById(updateRequest.getAddressId()).orElseThrow(() -> new BizException("地区信息查询不到!"));
            addressName = sysAreaInfoDO.getName();
        }
        if (!SecurityContextUtil.hasAnyAuthority(UPDATE_USER_STATUS)) {
            updateRequest.setUserStatus(null);
        }
        if (!SecurityContextUtil.hasAnyAuthority(UPDATE_USER_DEPT_ID)) {
            updateRequest.setDeptId(null);
        }
        return sysUserStaffDao.updateRequest(updateRequest, addressName);
    }

    /**
     * 删除用户
     *
     * @param userIds {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean remove(List<String> userIds) {
        return sysUserStaffDao.removeBatchByIds(userIds);
    }

    /**
     * 根据id查询用户详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysUserStaffResponse}
     */
    @Override
    public SysUserStaffResponse findById(String id) {
        return sysUserStaffConvert.toResponse(sysUserStaffDao.getOptById(id).orElse(null));
    }

    /**
     * 分页查询用户
     *
     * @param queryRequest {@link SysUserQueryRequest}
     * @return {@link SysUserStaffResponse} 分页详情
     */
    @Override
    public PageResponse<SysUserStaffResponse> findPage(SysUserQueryRequest queryRequest) {
        IPage<SysUserStaffDO> sysUserIPage = sysUserStaffDao.findPage(queryRequest);
        return sysUserStaffConvert.toPageResponse(sysUserIPage);
    }

}

