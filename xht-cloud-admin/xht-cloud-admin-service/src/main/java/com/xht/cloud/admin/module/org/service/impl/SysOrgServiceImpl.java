package com.xht.cloud.admin.module.org.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.cloud.admin.module.org.convert.SysOrgConvert;
import com.xht.cloud.admin.module.org.dao.SysOrgDao;
import com.xht.cloud.admin.module.org.domain.dataobject.SysOrgDO;
import com.xht.cloud.admin.module.org.domain.request.SysOrgCreateRequest;
import com.xht.cloud.admin.module.org.domain.request.SysOrgQueryRequest;
import com.xht.cloud.admin.module.org.domain.request.SysOrgUpdateRequest;
import com.xht.cloud.admin.module.org.domain.response.SysOrgResponse;
import com.xht.cloud.admin.module.org.service.ISysOrgService;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 组织机构
 *
 * @author 小糊涂
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysOrgServiceImpl implements ISysOrgService {

    private final SysOrgDao sysOrgDao;

    private final SysOrgConvert sysOrgConvert;

    /**
     * 创建组织机构
     *
     * @param createRequest {@link SysOrgCreateRequest} 创建参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(SysOrgCreateRequest createRequest) {
        Assert.notNull(createRequest, "createRequest 组织机构添加信息不能为空");
        SysOrgDO sysOrg = sysOrgConvert.toDO(createRequest);
        boolean parentExists = sysOrgDao.existsOrgParentId(sysOrg.getParentId());
        if (parentExists) {
            throw new BizException("上级组织机构不存在");
        }
        boolean orgCodExists = sysOrgDao.existsOrgCod(sysOrg.getOrgCode());
        if (orgCodExists) {
            throw new BizException("组织机构编码已重复");
        }
        return sysOrgDao.save(sysOrg);
    }

    /**
     * 根据主键修改组织机构
     *
     * @param updateRequest {@link SysOrgUpdateRequest} 修改参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Long id, SysOrgUpdateRequest updateRequest) {
        Assert.notNull(updateRequest, "updateRequest 组织机构修改信息不能为空");
        sysOrgDao.getOptById(updateRequest.getId()).orElseThrow(() -> new BizException("上级组织机构不存在"));
        boolean parentExists = sysOrgDao.existsOrgParentId(updateRequest.getParentId());
        if (parentExists) {
            throw new BizException("上级组织机构不存在");
        }
        boolean orgCodExists = sysOrgDao.existsOrgCod(updateRequest.getOrgCode());
        if (orgCodExists) {
            throw new BizException("组织机构编码已重复");
        }
        SysOrgDO sysOrgDO = sysOrgConvert.toDO(updateRequest);
        sysOrgDO.setId(id);
        return sysOrgDao.updateById(sysOrgDO);
    }

    /**
     * 根据主键删除组织机构
     *
     * @param id 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {
        Assert.notNull(id, "id 不能为空！");
        return sysOrgDao.removeById(id);
    }

    /**
     * 根据主键批量删除{serviceDesc}
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeBatch(List<Long> ids) {
        Assert.notEmpty(ids, "ids 不能为空！");
        return sysOrgDao.removeByIds(ids);
    }

    /**
     * 根据主键查询组织机构详细
     *
     * @param id 主键
     * @return {@link SysOrgResponse} 数据详细
     */
    @Override
    public SysOrgResponse findById(Long id) {
        Assert.notNull(id, "id 不能为空！");
        return sysOrgConvert.toResponse(sysOrgDao.getById(id));
    }

    /**
     * 分页查询组织机构
     *
     * @param queryRequest {@link SysOrgQueryRequest} 查询参数
     * @return 分页详情
     */
    @Override
    public PageResponse<SysOrgResponse> findPage(SysOrgQueryRequest queryRequest) {
        Page<SysOrgDO> page = PageTool.getPage(queryRequest);
        LambdaQueryWrapper<SysOrgDO> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper
                .eq(Objects.nonNull(queryRequest.getDirectorId()), SysOrgDO::getDirectorId, queryRequest.getDirectorId())
                .eq(Objects.nonNull(queryRequest.getParentId()), SysOrgDO::getParentId, queryRequest.getParentId())
                .eq(Objects.nonNull(queryRequest.getParentName()), SysOrgDO::getParentName, queryRequest.getParentName())
                .eq(Objects.nonNull(queryRequest.getOrgName()), SysOrgDO::getOrgName, queryRequest.getOrgName())
                .eq(Objects.nonNull(queryRequest.getOrgCode()), SysOrgDO::getOrgCode, queryRequest.getOrgCode())
                .eq(Objects.nonNull(queryRequest.getOrgType()), SysOrgDO::getOrgType, queryRequest.getOrgType())
                .eq(Objects.nonNull(queryRequest.getOrgStatus()), SysOrgDO::getOrgStatus, queryRequest.getOrgStatus())
                .eq(Objects.nonNull(queryRequest.getOrgSort()), SysOrgDO::getOrgSort, queryRequest.getOrgSort())
                .eq(Objects.nonNull(queryRequest.getOrgPath()), SysOrgDO::getOrgPath, queryRequest.getOrgPath())
                .eq(Objects.nonNull(queryRequest.getOrgDesc()), SysOrgDO::getOrgDesc, queryRequest.getOrgDesc())
                .eq(Objects.nonNull(queryRequest.getOrgPhone()), SysOrgDO::getOrgPhone, queryRequest.getOrgPhone())
                .eq(Objects.nonNull(queryRequest.getOrgEmail()), SysOrgDO::getOrgEmail, queryRequest.getOrgEmail())
			;
        // @formatter:on
        return sysOrgConvert.toPageResponse(sysOrgDao.page(page, queryWrapper));
    }

    @Override
    public List<Tree<Long>> tree() {
        List<SysOrgDO> sysOrgList = sysOrgDao.list();
        List<TreeNode<Long>> treeNodeList = sysOrgList.stream().map(sysOrg ->
                        new TreeNode<>(sysOrg.getId(), sysOrg.getParentId(),
                                sysOrg.getOrgName(), sysOrg.getOrgSort()).setExtra(JSONUtil.parseObj(sysOrg)))
                .collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, -1L);
    }
}
