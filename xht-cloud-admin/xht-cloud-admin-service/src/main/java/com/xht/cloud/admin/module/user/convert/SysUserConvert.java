package com.xht.cloud.admin.module.user.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserDO;
import com.xht.cloud.admin.module.user.domain.request.SysUserCreateRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserUpdateRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.support.StringUtils;
import org.mapstruct.Mapper;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * 描述 ：用户
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysUserConvert extends IBaseConvert<SysUserCreateRequest, SysUserUpdateRequest, SysUserQueryRequest, SysUserResponse, SysUserDO>, EntityWrapper<SysUserDO> {

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<SysUserDO> lambdaQuery(SysUserDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<SysUserDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), SysUserDO::getId, entity.getId())
                .like(StringUtils.hasText(entity.getUserAccount()), SysUserDO::getUserAccount, entity.getUserAccount())
                .like(StringUtils.hasText(entity.getUserName()), SysUserDO::getUserName, entity.getUserName())
                .eq(StringUtils.hasText(entity.getPassWord()), SysUserDO::getPassWord, entity.getPassWord())
                .eq(StringUtils.hasText(entity.getPassWordSalt()), SysUserDO::getPassWordSalt, entity.getPassWordSalt())
                .eq(StringUtils.hasText(entity.getPassWordOld()), SysUserDO::getPassWordOld, entity.getPassWordOld())
                .eq(StringUtils.hasText(entity.getPassWordSaltOld()), SysUserDO::getPassWordSaltOld, entity.getPassWordSaltOld())
                .eq(StringUtils.hasText(entity.getDeptId()), SysUserDO::getDeptId, entity.getDeptId())
                .eq(StringUtils.hasText(entity.getUserAvatar()), SysUserDO::getUserAvatar, entity.getUserAvatar())
                .eq(Objects.nonNull(entity.getUserType()), SysUserDO::getUserType, entity.getUserType())
                .eq(StringUtils.hasText(entity.getQqOpenid()), SysUserDO::getQqOpenid, entity.getQqOpenid())
                .eq(StringUtils.hasText(entity.getWxOpenid()), SysUserDO::getWxOpenid, entity.getWxOpenid())
                .eq(StringUtils.hasText(entity.getWxUnionid()), SysUserDO::getWxUnionid, entity.getWxUnionid())
                .eq(Objects.nonNull(entity.getUserStatus()), SysUserDO::getUserStatus, entity.getUserStatus())
                .eq(Objects.nonNull(entity.getAdminStatus()), SysUserDO::getAdminStatus, entity.getAdminStatus())
                .eq(!ObjectUtils.isEmpty(entity.getRegisteredTime()), SysUserDO::getRegisteredTime, entity.getRegisteredTime())
                .eq(!ObjectUtils.isEmpty(entity.getLastLoginTime()), SysUserDO::getLastLoginTime, entity.getLastLoginTime())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<SysUserDO> lambdaUpdate(SysUserDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<SysUserDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(SysUserDO::getUserAccount, entity.getUserAccount())
                .set(SysUserDO::getUserName, entity.getUserName())
                .set(SysUserDO::getPassWord, entity.getPassWord())
                .set(SysUserDO::getPassWordSalt, entity.getPassWordSalt())
                .set(SysUserDO::getPassWordOld, entity.getPassWordOld())
                .set(SysUserDO::getPassWordSaltOld, entity.getPassWordSaltOld())
                .set(SysUserDO::getDeptId, entity.getDeptId())
                .set(SysUserDO::getUserAvatar, entity.getUserAvatar())
                .set(SysUserDO::getUserType, entity.getUserType())
                .set(SysUserDO::getQqOpenid, entity.getQqOpenid())
                .set(SysUserDO::getWxOpenid, entity.getWxOpenid())
                .set(SysUserDO::getWxUnionid, entity.getWxUnionid())
                .set(SysUserDO::getUserStatus, entity.getUserStatus())
                .set(SysUserDO::getAdminStatus, entity.getAdminStatus())
                .set(SysUserDO::getRegisteredTime, entity.getRegisteredTime())
                .set(SysUserDO::getLastLoginTime, entity.getLastLoginTime())
                ;
    }


}
