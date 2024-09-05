package com.xht.cloud.admin.module.org.dao;

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
