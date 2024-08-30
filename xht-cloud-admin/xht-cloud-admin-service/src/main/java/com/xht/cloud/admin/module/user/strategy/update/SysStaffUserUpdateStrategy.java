package com.xht.cloud.admin.module.user.strategy.update;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.module.user.dao.SysUserStaffDao;
import com.xht.cloud.admin.module.user.domain.PassWordBO;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserStaffDO;
import com.xht.cloud.admin.module.user.domain.request.UserUpdateRequest;
import com.xht.cloud.admin.module.user.strategy.AbstractUserUpdateStrategy;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.constant.GlobalErrorStatusCode;
import com.xht.cloud.framework.file.upload.UploadFileBO;
import com.xht.cloud.framework.utils.support.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * 描述 ：工作人员修改策略实现类
 *
 * @author : 小糊涂
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class SysStaffUserUpdateStrategy extends AbstractUserUpdateStrategy {

    private final SysUserStaffDao userStaffDao;

    /**
     * 根据用户id修改信息
     *
     * @param userId      用户id
     * @param requestUser 用户请求信息
     * @return true 修改成功
     */
    @Override
    public Boolean updateUserInfo(String userId, @Validated UserUpdateRequest requestUser) {
        Assert.hasText(userId, GlobalErrorStatusCode.PARAMS_ERROR);
        LambdaUpdateWrapper<SysUserStaffDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper
                .set(StringUtils.hasText(requestUser.getDeptId()), SysUserStaffDO::getDeptId, requestUser.getDeptId())
                .set(Objects.nonNull(requestUser.getUserStatus()), SysUserStaffDO::getUserStatus, requestUser.getUserStatus())
                .set(StringUtils.hasText(requestUser.getIdentityCard()), SysUserStaffDO::getIdentityCard, requestUser.getIdentityCard())
                .set(Objects.nonNull(requestUser.getUserSex()), SysUserStaffDO::getUserSex, requestUser.getUserSex())
                .set(StringUtils.hasText(requestUser.getUserAge()), SysUserStaffDO::getUserAge, requestUser.getUserAge())
                .set(StringUtils.hasText(requestUser.getContactMobile()), SysUserStaffDO::getContactMobile, requestUser.getContactMobile())
                .set(StringUtils.hasText(requestUser.getAddressId()), SysUserStaffDO::getAddressId, requestUser.getAddressId())
                .set(StringUtils.hasText(requestUser.getAddressName()), SysUserStaffDO::getAddressName, requestUser.getAddressName())
                .eq(SysUserStaffDO::getId, userId);
        return userStaffDao.update(wrapper);
    }


    /**
     * 根据用户id修改头像
     *
     * @param userId       用户id
     * @param uploadFileBO 头像上传信息
     * @return true 修改成功
     */
    @Override
    protected Boolean updateUserAvatar(String userId, UploadFileBO uploadFileBO) {
        return null;
    }

    /**
     * 根据用户id修改密码
     *
     * @param userId          用户id
     * @param passWordRequest 密码修改请求信息
     * @return true 修改成功
     */
    @Override
    public Boolean updatePassword(String userId, PassWordBO passWordRequest) {
        return null;
    }

    /**
     * 获取用户类型
     *
     * @return {@link UserTypeEnums} 用户类型
     */
    @Override
    public UserTypeEnums getUserType() {
        return UserTypeEnums.STAFF;
    }

}
