package com.xht.cloud.admin.module.user.service.impl;

import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.api.user.enums.SuperAdminUserEnums;
import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.enums.DeptStatusEnums;
import com.xht.cloud.admin.enums.MenuTypeEnums;
import com.xht.cloud.admin.module.dept.convert.SysDeptConvert;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysDeptDO;
import com.xht.cloud.admin.module.dept.domain.response.SysDeptResponse;
import com.xht.cloud.admin.module.dept.mapper.SysDeptMapper;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysMenuDO;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysRoleDO;
import com.xht.cloud.admin.module.permissions.mapper.SysMenuMapper;
import com.xht.cloud.admin.module.permissions.mapper.SysRoleMapper;
import com.xht.cloud.admin.module.user.convert.SysUserConvert;
import com.xht.cloud.admin.module.user.convert.SysUserProfileConvert;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserDO;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserProfileDO;
import com.xht.cloud.admin.module.user.domain.request.SysUserBaseAddUpdate;
import com.xht.cloud.admin.module.user.domain.request.SysUserProfileRequest;
import com.xht.cloud.admin.module.user.domain.request.SysUserQueryRequest;
import com.xht.cloud.admin.module.user.domain.request.UpdatePassWordRequest;
import com.xht.cloud.admin.module.user.domain.response.SysUserProfileResponse;
import com.xht.cloud.admin.module.user.domain.response.SysUserResponse;
import com.xht.cloud.admin.module.user.domain.response.SysUserVo;
import com.xht.cloud.admin.module.user.mapper.SysUserMapper;
import com.xht.cloud.admin.module.user.mapper.SysUserProfileMapper;
import com.xht.cloud.admin.module.user.service.ISysUserService;
import com.xht.cloud.admin.tool.ExceptionTool;
import com.xht.cloud.framework.core.constant.TreeConstant;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.exception.user.UserNotFountException;
import com.xht.cloud.framework.mybatis.core.DataScopeFieldBuilder;
import com.xht.cloud.framework.mybatis.enums.DataScopeTypeEnums;
import com.xht.cloud.framework.mybatis.handler.DataScopeSqlFactory;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.redis.key.RedisKeyTool;
import com.xht.cloud.framework.redis.service.RedisService;
import com.xht.cloud.framework.security.exception.PassWordException;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import com.xht.cloud.framework.utils.support.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 描述 ：用户
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements ISysUserService {

    private final SysUserMapper sysUserMapper;

    private final SysUserProfileMapper sysUserProfileMapper;

    private final SysRoleMapper sysRoleMapper;

    private final SysMenuMapper sysMenuMapper;

    private final DataScopeSqlFactory dataScopeFactory;

    private final PasswordEncoder passwordEncoder;

    private final RedisService redisService;

    private final SysDeptMapper sysDeptMapper;

    private final SysUserConvert sysUserConvert;

    private final SysUserProfileConvert sysUserProfileConvert;

    private final SysDeptConvert sysDeptConvert;

    private static SysDeptResponse getDeptResponse() {
        SysDeptResponse deptResponse = new SysDeptResponse();
        deptResponse.setId(TreeConstant.TREE_PARENT_DEFAULT);
        deptResponse.setDeptName("顶级部门");
        deptResponse.setDeptSort(1);
        deptResponse.setDeptStatus(DeptStatusEnums.NORMAL);
        return deptResponse;
    }

    /**
     * 创建用户
     *
     * @param request {@link SysUserBaseAddUpdate}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(SysUserBaseAddUpdate request) {
        Assert.notNull(request, "用户添加信息不能为空");
        SysUserDO entity = sysUserConvert.toDO(request.getSysUser());
        entity.setUserAccount("CS" + RandomUtil.randomNumbers(5));
        String randomGenerator = new RandomGenerator(6).generate();
        entity.setPassWord(passwordEncoder.encode(String.format("123456%s", randomGenerator)));
        entity.setPassWordSalt(randomGenerator);
        entity.setUserStatus(UserStatusEnums.UN_ACTIVATED);
        entity.setAdminStatus(SuperAdminUserEnums.NO);
        entity.setUserType(UserTypeEnums.SYSTEM_USER);
        entity.setRegisteredTime(LocalDateTime.now());
        SysUserProfileRequest profile = request.getProfile();
        if (Objects.isNull(profile)) {
            profile = new SysUserProfileRequest();
        }
        SysUserProfileDO sysUserProfileDO = sysUserProfileConvert.toDO(profile);
        sysUserMapper.insert(entity);
        sysUserProfileDO.setUserId(entity.getId());
        sysUserProfileMapper.insert(sysUserProfileDO);
        return entity.getUserAccount();
    }

    /**
     * 根据id修改用户
     *
     * @param request {@link SysUserBaseAddUpdate}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(SysUserBaseAddUpdate request) {
        // @formatter:off
        Assert.notNull(request, "修改的用户信息不能为空");
        Assert.notNull(request.getSysUser(), "修改的用户信息不能为空");
        String userId = request.getSysUser().getId();
        Assert.hasText(userId, "修改的用户信息id不能为空");
        LambdaQueryWrapper<SysUserDO> userDOLambdaQueryWrapper = sysUserConvert.lambdaQuery()
                .select(
                        SysUserDO::getId,
                        SysUserDO::getAdminStatus,
                        SysUserDO::getUserAccount
                )
                .eq(SysUserDO::getId, userId);
        SysUserDO sysUserDO = sysUserMapper.selectOne(userDOLambdaQueryWrapper);
        ExceptionTool.permissionValidation(sysUserDO);
        SysUserProfileDO userProfile = sysUserProfileConvert.toDO(request.getProfile());
        userProfile.setUserId(userId);
        SysUserProfileDO dbSysUserProfileDO = sysUserProfileMapper.selectOne(SysUserProfileDO::getUserId, userId).orElse(null);
        if (Objects.nonNull(dbSysUserProfileDO)) {
            userProfile.setId(dbSysUserProfileDO.getId());
            sysUserProfileMapper.updateById(userProfile);
        } else {
            sysUserProfileMapper.insert(userProfile);
        }
        redisService.delete(RedisKeyTool.createKeyTemplate("user:profile:info:{}", sysUserDO.getUserAccount()));
        sysUserMapper.updateById(sysUserConvert.toDO(request.getSysUser()));
        return sysUserDO.getUserAccount();
        // @formatter:on
    }

    /**
     * 删除用户
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        // @formatter:off
        Assert.notEmpty(ids, "用户信息信息ids不能为空");
        LambdaQueryWrapper<SysUserDO> userDOLambdaQueryWrapper = sysUserConvert.lambdaQuery()
                .select(
                        SysUserDO::getId,
                        SysUserDO::getAdminStatus,
                        SysUserDO::getUserAccount
                )
                .in(SysUserDO::getId, ids);
        List<SysUserDO> sysUserDOS = sysUserMapper.selectList(userDOLambdaQueryWrapper);
        if (CollectionUtils.isEmpty(sysUserDOS) || sysUserDOS.size() != ids.size()) {
            throw new BizException("删除的用户对象不存在！");
        }
        sysUserDOS.forEach(ExceptionTool::permissionValidation);
        Set<String> collect = sysUserDOS.stream().map(item ->
                RedisKeyTool.createKeyTemplate("user:profile:info:{}", item.getUserAccount())
        ).collect(Collectors.toSet());
        redisService.delete(collect);
        sysUserMapper.deleteBatchIds(ids);
        // @formatter:on
    }

    /**
     * 根据id查询用户详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysUserResponse}
     */
    @Override
    public SysUserVo findById(String id) {
        SysUserVo result = new SysUserVo();
        SysUserProfileResponse sysUserProfileResponse = null;
        SysUserResponse sysUserResponse = sysUserConvert
                .toResponse(sysUserMapper.findById(id).orElse(null));
        if (Objects.nonNull(sysUserResponse)) {
            sysUserProfileResponse = sysUserProfileConvert
                    .toResponse(sysUserProfileMapper.selectOne(SysUserProfileDO::getUserId, sysUserResponse.getId())
                            .orElse(new SysUserProfileDO()));
            sysUserResponse.setPassWord(null);
            sysUserResponse.setPassWordSalt(null);
        }
        result.setSysUser(sysUserResponse);
        result.setProfile(sysUserProfileResponse);
        return result;
    }

    /**
     * 分页查询用户
     *
     * @param queryRequest {@link SysUserQueryRequest}
     * @return {@link SysUserResponse} 分页详情
     */
    @Override
    public PageResponse<SysUserResponse> findPage(SysUserQueryRequest queryRequest) {
        LambdaQueryWrapper<SysUserDO> lambdaQuery = sysUserConvert.lambdaQuery(sysUserConvert.toDO(queryRequest));
        dataScopeFactory.getDataScopeHandler(DataScopeTypeEnums.DEPT_USER_TYPE).execute(DataScopeFieldBuilder.<SysUserDO>builder()
                .deptField(SysUserDO::getDeptId)
                .userField(SysUserDO::getId)
                .build(), lambdaQuery);
        if (!CollectionUtils.isEmpty(queryRequest.getUserIds())) {
            lambdaQuery.in(SysUserDO::getId, queryRequest.getUserIds());
        }
        IPage<SysUserDO> sysUserIPage = sysUserMapper.selectPage(PageTool.getPage(queryRequest), lambdaQuery);
        return sysUserConvert.toPageResponse(sysUserIPage);
    }

    /**
     * 根据userAccount查询用户详细
     *
     * @param userAccount {@link String} 用户账号
     * @return {@link SysUserVo}
     */
    @Override
    public SysUserVo findByUserAccount(String userAccount) {
        // @formatter:off
        return redisService.get(RedisKeyTool.createKeyTemplate("user:profile:info:{}", userAccount),
                RandomUtil.randomInt(30, 60), TimeUnit.MINUTES, () -> {
                    SysUserVo sysUserVo = new SysUserVo();
                    SysUserDO sysUserDO = sysUserMapper
                            .selectOne(SysUserDO::getUserAccount, userAccount)
                            .orElse(null);
                    Assert.notNull(sysUserDO, () -> new UserNotFountException(userAccount));
                    assert sysUserDO != null;
                    String userId = sysUserDO.getId();
                    SysUserResponse response = sysUserConvert.toResponse(sysUserDO);
                    sysUserVo.setProfile(sysUserProfileConvert.toResponse(
                            sysUserProfileMapper.selectOne(SysUserProfileDO::getUserId, userId).orElse(null)));
                    List<SysRoleDO> sysRoleDOS = sysRoleMapper.selectListByUserId(userId);
                    if (!CollectionUtils.isEmpty(sysRoleDOS)) {
                        sysUserVo.setRoleCode(sysRoleDOS.stream().map(SysRoleDO::getRoleCode).collect(Collectors.toSet()));
                        Integer dataScope = sysRoleDOS.stream().map(item ->
                                        item.getDataScope().getValue())
                                .min(Comparator.comparingInt(o -> o))
                                .orElse(null);
                        sysUserVo.setDataScope(dataScope);
                        response.setDataScope(dataScope);
                    }
                    List<SysMenuDO> sysMenuDOS;
                    if (SecurityContextUtil.isAdmin()) {
                        sysMenuDOS = sysMenuMapper.selectListIn(SysMenuDO::getMenuType, MenuTypeEnums.getValues());
                    } else {
                        sysMenuDOS = sysMenuMapper.selectByUserIdAndMenuType(sysUserDO.getId(), MenuTypeEnums.getValues());
                    }
                    if (!CollectionUtils.isEmpty(sysMenuDOS)) {
                        sysUserVo.setAuthorities(sysMenuDOS.stream()
                                .filter(Objects::nonNull)
                                .map(SysMenuDO::getMenuAuthority)
                                .filter(StringUtils::hasText)
                                .collect(Collectors.toSet()));
                    }
                    if (Objects.nonNull(sysUserDO.getDeptId())) {
                        if (Objects.equals(TreeConstant.TREE_PARENT_DEFAULT, sysUserDO.getDeptId())) {
                            SysDeptResponse deptResponse = getDeptResponse();
                            sysUserVo.setDept(deptResponse);
                        } else {
                            SysDeptDO sysDeptDO = sysDeptMapper.selectById(sysUserDO.getDeptId());
                            sysUserVo.setDept(sysDeptConvert.toResponse(sysDeptDO));
                        }
                    }
                    sysUserVo.setSysUser(response);
                    return sysUserVo;
                });
        // @formatter:on
    }

    /**
     * 校验用户账号是否存在
     *
     * @param userAccount 用户账号
     * @return {@link Boolean}
     */
    @Override
    public boolean validationUserAccount(String userAccount) {
        SysUserDO sysUserDO = sysUserMapper.selectOne(SysUserDO::getUserAccount, userAccount).orElse(null);
        return Objects.nonNull(sysUserDO);
    }

    /**
     * 修改登录用户信息
     *
     * @param userAccount 用户账号
     * @param request     请求信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserProfile(String userAccount, SysUserProfileRequest request) {
        SysUserDO sysUserDO = sysUserMapper.selectOne(SysUserDO::getUserAccount, userAccount).orElse(null);
        if (Objects.isNull(sysUserDO)) {
            throw new UserNotFountException(userAccount);
        }
        SysUserProfileDO sysUserProfileDO = sysUserProfileMapper.selectOne(SysUserProfileDO::getUserId, sysUserDO.getId()).orElse(null);
        SysUserProfileDO sysUserProfileDORequest = sysUserProfileConvert.toDO(request);
        sysUserProfileDORequest.setUserId(sysUserDO.getId());
        if (Objects.isNull(sysUserProfileDO)) {
            sysUserProfileDORequest.setId(null);
            sysUserProfileMapper.insert(sysUserProfileDORequest);
        } else {
            sysUserProfileDORequest.setId(sysUserProfileDO.getId());
            sysUserProfileMapper.updateById(sysUserProfileDORequest);
        }
        sysUserMapper.updateById(sysUserDO);
        redisService.delete(RedisKeyTool.createKeyTemplate("user:profile:info:{}", userAccount));
    }

    /**
     * 修改当前登录用户密码
     *
     * @param userAccount 用户账号
     * @param request     请求信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserPassword(String userAccount, UpdatePassWordRequest request) {
        SysUserDO sysUserDO = sysUserMapper.selectOne(SysUserDO::getUserAccount, userAccount).orElse(null);
        if (Objects.isNull(sysUserDO)) {
            throw new UserNotFountException(userAccount);
        }
        if (!passwordEncoder.matches(request.getOldPassword() + sysUserDO.getPassWordSalt(), sysUserDO.getPassWord())) {
            throw new PassWordException("输入旧密码不正确!");
        }
        if (passwordEncoder.matches(request.getNewPassword() + sysUserDO.getPassWordSalt(), sysUserDO.getPassWord())) {
            throw new PassWordException("新密码不能与旧密码相同!");
        }
        if (passwordEncoder.matches(request.getNewPassword() + sysUserDO.getPassWordSaltOld(), sysUserDO.getPassWordOld())) {
            throw new PassWordException("不能设置使用过的密码!");
        }
        String randomGenerator = new RandomGenerator(6).generate();
        LambdaUpdateWrapper<SysUserDO> eq =  sysUserConvert.lambdaUpdate()
                .set(SysUserDO::getPassWord, passwordEncoder.encode(String.format("%s%s", request.getNewPassword(), randomGenerator)))
                .set(SysUserDO::getPassWordSalt, randomGenerator)
                .set(SysUserDO::getPassWordOld, sysUserDO.getPassWordOld())
                .set(SysUserDO::getPassWordSaltOld, sysUserDO.getPassWordSaltOld())
                .eq(SysUserDO::getId, sysUserDO.getId());
        sysUserMapper.update(eq);
        redisService.delete(RedisKeyTool.createKeyTemplate("user:profile:info:{}", userAccount));
    }


    /**
     * 重置用户密码
     *
     * @param userId 用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String resetUserPassword(String userId) {
        // @formatter:off
        LambdaQueryWrapper<SysUserDO> userDOLambdaQueryWrapper = sysUserConvert.lambdaQuery()
                .select(
                        SysUserDO::getId,
                        SysUserDO::getAdminStatus,
                        SysUserDO::getUserAccount
                )
                .eq(SysUserDO::getId, userId);
        SysUserDO sysUserDO = sysUserMapper.selectOne(userDOLambdaQueryWrapper);
        String userAccount = sysUserDO.getUserAccount();
        String randomGenerator = new RandomGenerator(6).generate();
        sysUserMapper.update( sysUserConvert.lambdaUpdate()
                .set(SysUserDO::getPassWord, passwordEncoder.encode(String.format("%s%s%s", sysUserDO.getUserAccount(), "123456", randomGenerator)))
                .set(SysUserDO::getPassWordSalt, randomGenerator)
                .set(SysUserDO::getPassWordOld, sysUserDO.getPassWordOld())
                .set(SysUserDO::getPassWordSaltOld, sysUserDO.getPassWordSaltOld())
                .eq(SysUserDO::getId, sysUserDO.getId()));
        redisService.delete(RedisKeyTool.createKeyTemplate("user:profile:info:{}", userAccount));
        return userAccount;
        // @formatter:on
    }

    /**
     * 修改当前登录用户头像
     *
     * @param userAccount 用户账号
     * @param inputStream 头像io流
     * @return 头像地址
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateUserAvatar(String userAccount, InputStream inputStream) {
        SysUserDO sysUserDO = sysUserMapper.selectOne(SysUserDO::getUserAccount, userAccount).orElse(null);
        if (Objects.isNull(sysUserDO)) {
            throw new UserNotFountException(userAccount);
        }
        String result = "";
        sysUserDO.setUserAvatar(result);
        sysUserMapper.updateById(sysUserDO);
        redisService.delete(RedisKeyTool.createKeyTemplate("user:profile:info:{}", userAccount));
        return result;
    }
}

