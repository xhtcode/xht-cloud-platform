package com.xht.cloud.admin.module.dept.dao;

import com.xht.cloud.admin.module.dept.domain.dataobject.SysDeptDO;
import com.xht.cloud.admin.module.dept.mapper.SysDeptMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述 ：部门
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysDeptDao extends BaseDaoImpl<SysDeptMapper, SysDeptDO> {

    /**
     * 判断是否有下级部门
     *
     * @param deptIds 部门id
     */
    public boolean existsChild(List<String> deptIds) {
        return exists(lambdaQuery()
                .in(SysDeptDO::getParentId, deptIds));
    }
}
