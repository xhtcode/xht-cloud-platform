package com.xht.cloud.admin.module.user.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserStaffDO;
import com.xht.cloud.admin.module.user.domain.request.SysUserQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserStaffUpdateRequest;
import com.xht.cloud.admin.module.user.mapper.SysUserStaffMapper;
import com.xht.cloud.framework.mybatis.core.DataScopeFieldBuilder;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.enums.DataScopeTypeEnums;
import com.xht.cloud.framework.mybatis.handler.DataScopeSqlFactory;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class SysUserStaffDao extends BaseDaoImpl<SysUserStaffMapper, SysUserStaffDO> {

    private final DataScopeSqlFactory dataScopeFactory;

    /**
     * 根据账号查询用户id
     *
     * @param userName 账号
     * @return 用户id
     */
    public String getUserIdByUserName(String userName) {
        // @formatter:off
        LambdaQueryWrapper<SysUserStaffDO> wrapper = new LambdaQueryWrapper<SysUserStaffDO>()
                .select(SysUserStaffDO::getId)
                .eq(SysUserStaffDO::getUserName, userName);
        SysUserStaffDO sysUserAdminDO = getBaseMapper().selectOne(wrapper);
        // @formatter:on
        return Objects.nonNull(sysUserAdminDO) ? sysUserAdminDO.getId() : null;
    }


    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return {@link SysUserStaffDO}
     */
    public IPage<SysUserStaffDO> findPage(SysUserQueryRequest queryRequest) {
        // @formatter:off
        Page<SysUserStaffDO> page = PageTool.getPage(queryRequest);
        LambdaQueryWrapper<SysUserStaffDO> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery
                .in(!CollectionUtils.isEmpty(queryRequest.getUserIds()), SysUserStaffDO::getId, queryRequest.getUserIds())
                .like(StringUtils.hasText(queryRequest.getUserName()), SysUserStaffDO::getUserName, queryRequest.getUserName())
                .like(StringUtils.hasText(queryRequest.getNickName()), SysUserStaffDO::getNickName, queryRequest.getNickName())
                .eq(StringUtils.hasText(queryRequest.getDeptId()), SysUserStaffDO::getDeptId, queryRequest.getDeptId())
                .eq(Objects.nonNull(queryRequest.getUserStatus()), SysUserStaffDO::getUserStatus, queryRequest.getUserStatus())
        ;
        dataScopeFactory
                .getDataScopeHandler(DataScopeTypeEnums.DEPT_USER_TYPE)
                .execute(
                        DataScopeFieldBuilder.<SysUserStaffDO>builder()
                                .deptField(SysUserStaffDO::getDeptId)
                                .userField(SysUserStaffDO::getId)
                                .build(),
                        lambdaQuery
                );
        // @formatter:on
        return page(page, lambdaQuery);
    }

    /**
     * 根据用户id修改密码
     *
     * @param userId       用户id
     * @param passWord     密码
     * @param passWordSalt 密码盐值
     * @return 修改成功true
     */
    public boolean updatePassWord(String userId, String passWord, String passWordSalt) {
        // @formatter:off
        LambdaUpdateWrapper<SysUserStaffDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(SysUserStaffDO::getPassWord, passWord)
                .set(SysUserStaffDO::getPassWordSalt, passWordSalt)
                .eq(SysUserStaffDO::getId, userId);
        // @formatter:on
        return update(updateWrapper);
    }

    /**
     * 扩展的修改的接口
     *
     * @param updateRequest 修改参数
     * @return 修改成功true
     */
    public boolean updateRequest(SysUserStaffUpdateRequest updateRequest, String addressName) {
        updateRequest.checkId();
        LambdaUpdateWrapper<SysUserStaffDO> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(StringUtils.hasText(updateRequest.getNickName()), SysUserStaffDO::getNickName, updateRequest.getNickName())
                .set(StringUtils.hasText(updateRequest.getUserName()), SysUserStaffDO::getUserName, updateRequest.getUserName())
                .set(StringUtils.hasText(updateRequest.getDeptId()), SysUserStaffDO::getDeptId, updateRequest.getDeptId())
                .set(StringUtils.hasText(updateRequest.getUserAvatar()), SysUserStaffDO::getUserAvatar, updateRequest.getUserAvatar())
                .set(Objects.nonNull(updateRequest.getUserStatus()), SysUserStaffDO::getUserStatus, updateRequest.getUserStatus())
                .set(StringUtils.hasText(updateRequest.getIdentityCard()), SysUserStaffDO::getIdentityCard, updateRequest.getIdentityCard())
                .set(Objects.nonNull(updateRequest.getUserSex()), SysUserStaffDO::getUserSex, updateRequest.getUserSex())
                .set(StringUtils.hasText(updateRequest.getUserAge()), SysUserStaffDO::getUserAge, updateRequest.getUserAge())
                .set(StringUtils.hasText(updateRequest.getContactMobile()), SysUserStaffDO::getContactMobile, updateRequest.getContactMobile())
                .set(StringUtils.hasText(updateRequest.getAddressId()), SysUserStaffDO::getAddressId, updateRequest.getAddressId())
                .set(StringUtils.hasText(addressName), SysUserStaffDO::getAddressName, addressName)
                .set(StringUtils.hasText(updateRequest.getUserSignature()), SysUserStaffDO::getUserSignature, updateRequest.getUserSignature())
                .eq(SysUserStaffDO::getId, updateRequest.getId());
        // @formatter:on
        return update(updateWrapper);
    }
}
