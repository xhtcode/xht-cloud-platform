package com.xht.cloud.admin.module.org.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.org.domain.dataobject.SysOrgDO;
import com.xht.cloud.admin.module.org.mapper.SysOrgMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.SqlHelper;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 组织机构 DAO
 *
 * @author 小糊涂
 */
@Slf4j
@Repository
public class SysOrgDao extends BaseDaoImpl<SysOrgMapper, SysOrgDO> {

    /**
     * 根据主键修改数据
     *
     * @param entity 实体
     * @return 修改
     */
    @Override
    public boolean update(SysOrgDO entity) {
        if (Objects.isNull(entity)) return Boolean.FALSE;
        LambdaUpdateWrapper<SysOrgDO> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(Objects.nonNull(entity.getDirectorId()), SysOrgDO::getDirectorId, entity.getDirectorId())
                .set(Objects.nonNull(entity.getParentId()), SysOrgDO::getParentId, entity.getParentId())
                .set(Objects.nonNull(entity.getParentName()), SysOrgDO::getParentName, entity.getParentName())
                .set(Objects.nonNull(entity.getOrgName()), SysOrgDO::getOrgName, entity.getOrgName())
                .set(Objects.nonNull(entity.getOrgCode()), SysOrgDO::getOrgCode, entity.getOrgCode())
                .set(Objects.nonNull(entity.getOrgType()), SysOrgDO::getOrgType, entity.getOrgType())
                .set(Objects.nonNull(entity.getOrgStatus()), SysOrgDO::getOrgStatus, entity.getOrgStatus())
                .set(Objects.nonNull(entity.getOrgSort()), SysOrgDO::getOrgSort, entity.getOrgSort())
                .set(Objects.nonNull(entity.getOrgPath()), SysOrgDO::getOrgPath, entity.getOrgPath())
                .set(Objects.nonNull(entity.getOrgDesc()), SysOrgDO::getOrgDesc, entity.getOrgDesc())
                .set(Objects.nonNull(entity.getOrgPhone()), SysOrgDO::getOrgPhone, entity.getOrgPhone())
                .set(Objects.nonNull(entity.getOrgEmail()), SysOrgDO::getOrgEmail, entity.getOrgEmail())
				.eq(SysOrgDO::getId, entity.getId());
        // @formatter:on
        return update(updateWrapper);
    }

    /**
     * 判断上级部门存在不存在
     *
     * @param parentId 上级部门id
     * @return 存在true
     */
    public boolean existsOrgParentId(Long parentId) {
        if (Objects.equals(-1L, parentId)) return true;
        return SqlHelper.exist(count(lambdaQuery().eq(SysOrgDO::getId, parentId).eq(SysOrgDO::getOrgStatus, "1")));
    }

    /**
     * 判断上部门code存在不存在
     *
     * @param orgCode 部门code
     * @return 存在true
     */
    public boolean existsOrgCod(String orgCode) {
        if (StringUtils.isEmpty(orgCode)) return true;
        return SqlHelper.exist(selectCount(SysOrgDO::getOrgCode, orgCode));
    }
}
