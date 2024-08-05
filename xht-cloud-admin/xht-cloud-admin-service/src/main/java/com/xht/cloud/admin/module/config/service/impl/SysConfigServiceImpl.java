package com.xht.cloud.admin.module.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.module.config.convert.SysConfigConvert;
import com.xht.cloud.admin.module.config.domain.dataobject.SysConfigDO;
import com.xht.cloud.admin.module.config.domain.request.SysConfigCreateRequest;
import com.xht.cloud.admin.module.config.domain.request.SysConfigQueryRequest;
import com.xht.cloud.admin.module.config.domain.request.SysConfigUpdateRequest;
import com.xht.cloud.admin.module.config.domain.response.SysConfigResponse;
import com.xht.cloud.admin.module.config.mapper.SysConfigMapper;
import com.xht.cloud.admin.module.config.service.ISysConfigService;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 描述 ：系统配置信息
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl implements ISysConfigService {

    private final SysConfigMapper sysConfigMapper;

    private final SysConfigConvert sysConfigConvert;

    /**
     * 创建
     *
     * @param createRequest {@link SysConfigCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(SysConfigCreateRequest createRequest) {
        Assert.notNull(createRequest, "系统配置添加信息不能为空");
        SysConfigDO entity = sysConfigConvert.toDO(createRequest);
        sysConfigMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 根据id修改
     *
     * @param updateRequest SysConfigUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysConfigUpdateRequest updateRequest) {
        // @formatter:off
        Assert.notNull(updateRequest, "系统配置修改信息不能为空");
        Assert.notNull(updateRequest.getPkId(), "系统配置修改信息id不能为空");
        sysConfigMapper.updateById(sysConfigConvert.toDO(updateRequest));
        // @formatter:on
    }

    /**
     * 删除系统配置
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        // @formatter:off
        Assert.notEmpty(ids, "系统配置信息ids不能为空");
        LambdaQueryWrapper<SysConfigDO> lambdaQueryWrapper = sysConfigConvert.lambdaQuery()
                .select(
                        SysConfigDO::getId
                )
                .in(SysConfigDO::getId, ids);
        List<SysConfigDO> sysConfigDOS = sysConfigMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(sysConfigDOS) || sysConfigDOS.size() != ids.size()) {
            throw new BizException("删除的系统配置对象不存在");
        }
        sysConfigMapper.deleteBatchIds(ids);
        // @formatter:on
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysConfigResponse}
     */
    @Override
    public SysConfigResponse findById(String id) {
        Assert.hasText(id, "系统配置信息id不能为空");
        return sysConfigConvert.toResponse(sysConfigMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysConfigQueryRequest}
     * @return {@link PageResponse<SysConfigResponse>} 分页详情
     */
    @Override
    public PageResponse<SysConfigResponse> findPage(SysConfigQueryRequest queryRequest) {
        SysConfigConvert instance = sysConfigConvert;
        IPage<SysConfigDO> sysConfigIPage = sysConfigMapper.selectPage(PageTool.getPage(queryRequest),
                instance.lambdaQuery(sysConfigConvert.toDO(queryRequest)));
        return instance.toPageResponse(sysConfigIPage);
    }

}
