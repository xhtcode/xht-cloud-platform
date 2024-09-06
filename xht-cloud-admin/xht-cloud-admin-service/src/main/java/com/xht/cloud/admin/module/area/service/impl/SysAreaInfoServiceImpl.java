package com.xht.cloud.admin.module.area.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xht.cloud.admin.module.area.convert.SysAreaInfoConvert;
import com.xht.cloud.admin.module.area.dao.SysAreaInfoDao;
import com.xht.cloud.admin.module.area.domain.dataobject.SysAreaInfoDO;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoCreateRequest;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoQueryRequest;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoUpdateRequest;
import com.xht.cloud.admin.module.area.domain.response.SysAreaInfoResponse;
import com.xht.cloud.admin.module.area.service.ISysAreaInfoService;
import com.xht.cloud.framework.constant.TreeConstant;
import com.xht.cloud.framework.exception.Assert;
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

/**
 * 描述 ：地区信息
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysAreaInfoServiceImpl implements ISysAreaInfoService {

    private final SysAreaInfoDao sysAreaInfoDao;

    private final SysAreaInfoConvert sysAreaInfoConvert;


    /**
     * 创建
     *
     * @param createRequest {@link SysAreaInfoCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(SysAreaInfoCreateRequest createRequest) {
        SysAreaInfoDO entity = sysAreaInfoConvert.toDO(createRequest);
        return sysAreaInfoDao.save(entity);
    }

    /**
     * 根据id修改
     *
     * @param updateRequest SysAreaInfoUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysAreaInfoUpdateRequest updateRequest) {
        sysAreaInfoDao.updateRequest(updateRequest);
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        boolean existsChild = sysAreaInfoDao.existsChild(ids);
        Assert.isTrue(existsChild, "删除数据中含有下级城市数据，禁止删除！");
        sysAreaInfoDao.removeBatchByIds(ids);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysAreaInfoResponse}
     */
    @Override
    public SysAreaInfoResponse findById(String id) {
        return sysAreaInfoConvert.toResponse(sysAreaInfoDao.getById(id));
    }

    /**
     * 按条件查询全部
     *
     * @param queryRequest {@link SysAreaInfoQueryRequest}
     * @return {@link SysAreaInfoResponse} 详情
     */
    @Override
    public List<SysAreaInfoResponse> list(SysAreaInfoQueryRequest queryRequest) {
        Assert.notNull(queryRequest, "地区查询条件不能为空");
        if (!StringUtils.hasText(queryRequest.getParentId()) && !StringUtils.hasText(queryRequest.getAreaNo()) &&
                !StringUtils.hasText(queryRequest.getAreaNo())) {
            queryRequest.setParentId(TreeConstant.TREE_PARENT_DEFAULT);
        }
        List<SysAreaInfoDO> sysAreaInfoDOS = sysAreaInfoDao.selectListByRequest(queryRequest);
        return sysAreaInfoConvert.toResponse(sysAreaInfoDOS);
    }

    /**
     * 地区 转换成树结构
     *
     * @param queryRequest {@link SysAreaInfoQueryRequest}
     * @return 树结构
     */
    @Override
    public List<INode<String>> convert(SysAreaInfoQueryRequest queryRequest) {
        Assert.notNull(queryRequest, "地区查询条件不能为空");
        List<SysAreaInfoDO> sysAreaInfoDOS = sysAreaInfoDao.selectListByRequest(queryRequest);
        if (CollectionUtils.isEmpty(sysAreaInfoDOS)) {
            return Collections.emptyList();
        }
        List<INode<String>> result = new ArrayList<>(sysAreaInfoDOS.size());
        for (int i = 0; i < sysAreaInfoDOS.size(); i++) {
            SysAreaInfoDO item = sysAreaInfoDOS.get(i);
            result.add(new TreeNode<>(item.getId(), item.getParentId(), i).setExtra(BeanUtil.beanToMap(item)));
        }
        return TreeUtils.buildList(result);
    }

}
