package com.xht.cloud.admin.module.dept.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.module.dept.convert.SysPositionConvert;
import com.xht.cloud.admin.module.dept.dao.SysPositionDao;
import com.xht.cloud.admin.module.dept.domain.dataobject.SysPositionDO;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionCreateRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionQueryRequest;
import com.xht.cloud.admin.module.dept.domain.request.SysPositionUpdateRequest;
import com.xht.cloud.admin.module.dept.domain.response.SysPositionResponse;
import com.xht.cloud.admin.module.dept.service.ISysPositionService;
import com.xht.cloud.framework.domain.response.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述 ：岗位信息
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysPositionServiceImpl implements ISysPositionService {

    private final SysPositionDao sysPositionDao;

    private final SysPositionConvert sysPositionConvert;

    /**
     * 创建
     *
     * @param createRequest {@link SysPositionCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(SysPositionCreateRequest createRequest) {
        SysPositionDO entity = sysPositionConvert.toDO(createRequest);
        sysPositionDao.save(entity);
        return entity.getId();
    }

    /**
     * 根据id修改
     *
     * @param updateRequest SysPositionUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysPositionUpdateRequest updateRequest) {
        sysPositionDao.updateById(sysPositionConvert.toDO(updateRequest));
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        sysPositionDao.removeBatchByIds(ids);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysPositionResponse}
     */
    @Override
    public SysPositionResponse findById(String id) {
        return sysPositionConvert.toResponse(sysPositionDao.getById(id));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysPositionQueryRequest}
     * @return {@link SysPositionResponse} 分页详情
     */
    @Override
    public PageResponse<SysPositionResponse> findPage(SysPositionQueryRequest queryRequest) {
        IPage<SysPositionDO> sysPositionIPage = sysPositionDao.pageQueryRequest(queryRequest);
        return sysPositionConvert.toPageResponse(sysPositionIPage);
    }

}
