package com.xht.cloud.admin.module.user.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserAdminDO;
import com.xht.cloud.admin.module.user.domain.request.SysUserAdminQueryRequest;
import com.xht.cloud.admin.module.user.mapper.SysUserAdminMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.mybatis.tool.SqlHelper;
import com.xht.cloud.framework.utils.support.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 描述 ：用户-管理员
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysUserAdminDao extends BaseDaoImpl<SysUserAdminMapper, SysUserAdminDO> {

    /**
     * 根据主键修改数据
     *
     * @param entity 实体
     * @return 修改
     */
    @Override
    public boolean update(SysUserAdminDO entity) {
        // @formatter:off
        LambdaUpdateWrapper<SysUserAdminDO> updateWrapper = new LambdaUpdateWrapper<SysUserAdminDO>()
                .set(StringUtils.hasText(entity.getUserName()), SysUserAdminDO::getUserName, entity.getUserName())
                .set(StringUtils.hasText(entity.getUserAvatar()), SysUserAdminDO::getUserAvatar, entity.getUserAvatar())
                .set(StringUtils.hasText(entity.getContactMobile()), SysUserAdminDO::getContactMobile, entity.getContactMobile())
                .eq(SysUserAdminDO::getUserName, entity.getUserName())
                .ne(SysUserAdminDO::getId, entity.getId());
        return update(updateWrapper);
        // @formatter:on
    }

    /**
     * 查询账号是否存在
     *
     * @param userName 账号
     * @return 存在true
     */
    public Boolean existUserName(String userName) {
        return SqlHelper.exist(getBaseMapper().selectCount(SysUserAdminDO::getUserName, userName));
    }

    /**
     * 修改密码
     *
     * @param userId   用户id
     * @param passWord 密码
     * @return {@link Boolean} true 成功
     */
    public Boolean updatePassWord(Integer userId, String passWord) {
        LambdaUpdateWrapper<SysUserAdminDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(SysUserAdminDO::getPassWord, passWord)
                .eq(SysUserAdminDO::getId, userId);
        return update(lambdaUpdateWrapper);
    }

    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public Page<SysUserAdminDO> findPage(SysUserAdminQueryRequest queryRequest) {
        Page<SysUserAdminDO> page = PageTool.getPage(queryRequest);
        LambdaQueryWrapper<SysUserAdminDO> wrapper = new LambdaQueryWrapper<SysUserAdminDO>()
                .like(StringUtils.hasText(queryRequest.getUserName()), SysUserAdminDO::getUserName, queryRequest.getUserName())
                .like(StringUtils.hasText(queryRequest.getContactMobile()), SysUserAdminDO::getContactMobile, queryRequest.getContactMobile());
        return page(page, wrapper);
    }
}
