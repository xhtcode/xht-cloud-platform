package com.xht.cloud.admin.module.dept.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.xht.cloud.admin.module.dept.convert.SysDeptConvert;
import com.xht.cloud.admin.module.dept.dao.SysDeptDao;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysDeptDO;
import com.xht.cloud.admin.module.dept.domain.request.SysDeptCreateRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysDeptQueryRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysDeptUpdateRequest;
import com.xht.cloud.admin.module.dept.domain.response.SysDeptResponse;
import com.xht.cloud.admin.module.dept.service.ISysDeptService;
import com.xht.cloud.admin.module.permissions.domain.dataobject.SysMenuDO;
import com.xht.cloud.admin.module.user.dao.SysUserStaffDao;
import com.xht.cloud.admin.module.user.domain.dataobject.SysUserStaffDO;
import com.xht.cloud.framework.domain.request.Request;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.mybatis.core.DataScopeFieldBuilder;
import com.xht.cloud.framework.mybatis.enums.DataScopeTypeEnums;
import com.xht.cloud.framework.mybatis.handler.DataScopeSqlFactory;
import com.xht.cloud.framework.mybatis.tool.SqlHelper;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.framework.utils.treenode.INode;
import com.xht.cloud.framework.utils.treenode.TreeNode;
import com.xht.cloud.framework.utils.treenode.TreeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 描述 ：部门
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl implements ISysDeptService {

    private final SysDeptDao sysDeptDao;

    private final SysUserStaffDao sysUserStaffDao;

    private final DataScopeSqlFactory dataScopeFactory;

    private final SysDeptConvert sysDeptConvert;

    /**
     * 创建
     *
     * @param createRequest {@link SysDeptCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(SysDeptCreateRequest createRequest) {
        SysDeptDO entity = sysDeptConvert.toDO(createRequest);
        sysDeptDao.save(entity);
        return entity.getId();
    }

    /**
     * 根据id修改
     *
     * @param updateRequest SysDeptUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDeptUpdateRequest updateRequest) {
        sysDeptDao.updateRequest(updateRequest);
    }

    /**
     * 校验
     *
     * @param request {@link Request}
     */
    @Override
    public void validate(Request request) {
        Assert.isTrue(Objects.isNull(request), "部门信息为空");
        LambdaQueryChainWrapper<SysDeptDO> lambdaQuery = sysDeptDao.lambdaQuery();
        if (request instanceof SysDeptUpdateRequest re) {
            lambdaQuery.ne(SysDeptDO::getId, re.getId());
            lambdaQuery.eq(SysDeptDO::getDeptCode, re.getDeptCode());
        } else if (request instanceof SysDeptCreateRequest re) {
            lambdaQuery.eq(SysDeptDO::getDeptCode, re.getDeptCode());
        }
        List<SysDeptDO> sysDeptDOS = sysDeptDao.list(lambdaQuery);
        Assert.isTrue(!CollectionUtils.isEmpty(sysDeptDOS), "部门编码重复");
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        Assert.isTrue(sysDeptDao.existsChild(ids), "选择的部门存在有下级部门禁止删除!");
        long userCount = sysUserStaffDao.selectCountIn(SysUserStaffDO::getDeptId, ids);
        Assert.isTrue(SqlHelper.exist(userCount), "选择的部门中已绑定用户禁止删除!");
        sysDeptDao.removeBatchByIds(ids);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysDeptResponse}
     */
    @Override
    public SysDeptResponse findById(String id) {
        return sysDeptConvert.toResponse(sysDeptDao.getById(id));
    }

    /**
     * 查询
     *
     * @param queryRequest {@link SysDeptQueryRequest}
     * @return {@link List<SysDeptResponse>} 详情
     */
    @Override
    public List<SysDeptResponse> findList(SysDeptQueryRequest queryRequest) {
        LambdaQueryWrapper<SysDeptDO> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery
                .eq(StringUtils.hasText(queryRequest.getParentId()), SysDeptDO::getParentId, queryRequest.getParentId())
                .like(StringUtils.hasText(queryRequest.getDeptName()), SysDeptDO::getDeptName, queryRequest.getDeptName())
                .like(StringUtils.hasText(queryRequest.getDeptCode()), SysDeptDO::getDeptCode, queryRequest.getDeptCode())
                .eq(Objects.nonNull(queryRequest.getDeptStatus()), SysDeptDO::getDeptStatus, queryRequest.getDeptStatus())
        ;
        dataScopeFactory.getDataScopeHandler(DataScopeTypeEnums.DEPT_USER_TYPE).execute(DataScopeFieldBuilder.<SysDeptDO>builder()
                .deptField(SysDeptDO::getId)
                .build(), lambdaQuery);
        List<SysDeptDO> sysDeptDOS = sysDeptDao.list(lambdaQuery);
        return sysDeptConvert.toResponse(sysDeptDOS);
    }

    /**
     * 部门 转换成树结构
     *
     * @param deptResponses {@link SysMenuDO} 部门
     * @return 树结构
     */
    @Override
    public List<INode<String>> convert(List<SysDeptResponse> deptResponses) {
        if (CollectionUtils.isEmpty(deptResponses)) {
            return Collections.emptyList();
        }
        List<INode<String>> result = new ArrayList<>(deptResponses.size());
        for (SysDeptResponse item : deptResponses) {
            result.add(new TreeNode<>(item.getId(), item.getParentId(), item.getDeptSort()).setExtra(BeanUtil.beanToMap(item)));
        }
        return TreeUtils.buildList(result);
    }


}
