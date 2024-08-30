package com.xht.cloud.admin.module.user.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserStaffDO;
import com.xht.cloud.admin.module.user.domain.request.SysUserQueryRequest;
import com.xht.cloud.admin.module.user.mapper.SysUserStaffMapper;
import com.xht.cloud.framework.mybatis.core.DataScopeFieldBuilder;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.enums.DataScopeTypeEnums;
import com.xht.cloud.framework.mybatis.handler.DataScopeSqlFactory;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.support.StringUtils;
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
        LambdaQueryWrapper<SysUserStaffDO> wrapper = new LambdaQueryWrapper<SysUserStaffDO>()
                .select(SysUserStaffDO::getId)
                .eq(SysUserStaffDO::getUserName, userName);
        SysUserStaffDO sysUserAdminDO = getBaseMapper().selectOne(wrapper);
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
     * 根据主键修改数据
     *
     * @param requestUser 实体
     * @return 修改
     */
    @Override
    public boolean update(SysUserStaffDO requestUser) {
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
                .eq(SysUserStaffDO::getId, requestUser.getId());
        return update(wrapper);
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
        LambdaUpdateWrapper<SysUserStaffDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(SysUserStaffDO::getPassWord, passWord)
                .set(SysUserStaffDO::getPassWordSalt, passWordSalt)
                .eq(SysUserStaffDO::getId, userId);
        return update(updateWrapper);
    }
}
