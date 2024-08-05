package com.xht.cloud.admin.module.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.api.dict.dto.SysDictItemDTO;
import com.xht.cloud.admin.api.dict.enums.DictStatusEnums;
import com.xht.cloud.admin.exceptions.DictException;
import com.xht.cloud.admin.module.dict.convert.SysDictItemConvert;
import com.xht.cloud.admin.module.dict.convert.SysDictTypeConvert;
import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictItemDO;
import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictTypeDO;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemCreateRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemQueryRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemUpdateRequest;
import com.xht.cloud.admin.module.dict.domain.response.SysDictItemResponse;
import com.xht.cloud.admin.module.dict.mapper.SysDictItemMapper;
import com.xht.cloud.admin.module.dict.mapper.SysDictTypeMapper;
import com.xht.cloud.admin.module.dict.service.ISysDictItemService;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.mybatis.tool.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 描述 ：字典数据
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictItemServiceImpl implements ISysDictItemService {

    private final SysDictTypeConvert sysDictTypeConvert;

    private final SysDictTypeMapper sysDictTypeMapper;

    private final SysDictItemMapper sysDictItemMapper;

    private final SysDictItemConvert sysDictItemConvert;

    /**
     * 创建字典数据
     *
     * @param createRequest {@link SysDictItemCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(SysDictItemCreateRequest createRequest) {
        // @formatter:off
        Assert.notNull(createRequest, "字典数据添加信息不能为空");
        Assert.notNull(createRequest.getDictId(), "字典数据添加类型id不能为空");
        SysDictTypeDO dictTypeByDictId = getDictTypeByDictId(createRequest.getDictId());
        LambdaQueryWrapper<SysDictItemDO> wrapper = sysDictItemConvert
                .lambdaQuery()
                .eq(SysDictItemDO::getDictId, createRequest.getDictId())
                .eq(SysDictItemDO::getDictCode, createRequest.getDictCode());
        Long selectCount = sysDictItemMapper.selectCount(wrapper);
        if (Objects.nonNull(selectCount)) {
            throw new DictException(String.format("字典编码`%s`已存在，添加失败", createRequest.getDictCode()));
        }
        SysDictItemDO entity = sysDictItemConvert.toDO(createRequest);
        entity.setDictType(dictTypeByDictId.getDictType());
        sysDictItemMapper.insert(entity);
        return entity.getId();
        // @formatter:on
    }

    /**
     * 根据id修改字典数据
     *
     * @param updateRequest SysDictItemUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictItemUpdateRequest updateRequest) {
        // @formatter:off
        Assert.notNull(updateRequest, "字典数据修改信息不能为空");
        Assert.notNull(updateRequest.getDictId(), "字典数据添加类型id不能为空");
        Assert.notNull(updateRequest.getPkId(), "字典数据修改信息id不能为空");
        SysDictTypeDO dictTypeByDictId = getDictTypeByDictId(updateRequest.getDictId());
        LambdaQueryWrapper<SysDictItemDO> lambdaQueryWrapper = sysDictItemConvert.lambdaQuery()
                .select(
                        SysDictItemDO::getId
                )
                .eq(SysDictItemDO::getId, updateRequest.getId());
        SysDictItemDO sysDictItemDO = sysDictItemMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(sysDictItemDO)) {
            throw new BizException("字典数据息查询不到");
        }
        LambdaQueryWrapper<SysDictItemDO> wrapper = sysDictItemConvert
                .lambdaQuery()
                .eq(SysDictItemDO::getDictId, updateRequest.getDictId())
                .eq(SysDictItemDO::getDictCode, updateRequest.getDictCode())
                .ne(SysDictItemDO::getId, updateRequest.getId());
        Long dictCount = sysDictItemMapper.selectCount(wrapper);
        if (dictCount > 1) {
            throw new DictException(String.format("字典编码`%s`已存在，添加失败", updateRequest.getDictCode()));
        }
        SysDictItemDO itemDO = sysDictItemConvert.toDO(updateRequest);
        itemDO.setDictType(dictTypeByDictId.getDictType());
        sysDictItemMapper.updateById(itemDO);
        // @formatter:on
    }

    private SysDictTypeDO getDictTypeByDictId(String dictId) {
        LambdaQueryWrapper<SysDictTypeDO> queryWrapper = sysDictTypeConvert.lambdaQuery()
                .select(SysDictTypeDO::getId, SysDictTypeDO::getDictType)
                .eq(SysDictTypeDO::getId, dictId);
        SysDictTypeDO sysDictTypeDO = sysDictTypeMapper.selectOne(queryWrapper);
        if (Objects.isNull(sysDictTypeDO)) {
            throw new DictException("查询不到相对应的字典类型");
        }
        return sysDictTypeDO;
    }

    /**
     * 删除字典数据
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        // @formatter:off
        Assert.notEmpty(ids, "系统配置信息ids不能为空");
        sysDictItemMapper.deleteBatchIds(ids);
        // @formatter:on
    }

    /**
     * 根据id查询字典数据详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysDictItemResponse}
     */
    @Override
    public SysDictItemResponse findById(String id) {
        return sysDictItemConvert.toResponse(sysDictItemMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询字典数据
     *
     * @param queryRequest {@link SysDictItemQueryRequest}
     * @return {@link PageResponse<SysDictItemResponse>} 分页详情
     */
    @Override
    public PageResponse<SysDictItemResponse> findPage(SysDictItemQueryRequest queryRequest) {
        IPage<SysDictItemDO> sysDictItemIPage = sysDictItemMapper
                .selectPage(PageTool.getPage(queryRequest), sysDictItemConvert.lambdaQuery(sysDictItemConvert.toDO(queryRequest)));
        return sysDictItemConvert.toPageResponse(sysDictItemIPage);
    }

    /**
     * 根据字典id查询详细字典数据
     *
     * @param dictId {@link String} 数据库主键
     * @return {@link SysDictItemResponse}
     */
    @Override
    public List<SysDictItemResponse> findByDictId(String dictId) {
        LambdaQueryWrapper<SysDictItemDO> wrapper = sysDictItemConvert.lambdaQuery().eq(SysDictItemDO::getDictId, dictId);
        return sysDictItemConvert.toResponse(sysDictItemMapper.selectList(wrapper));
    }

    /**
     * 通过字典类型查找字典
     *
     * @param dictType   字典类型
     * @param dictStatus 字典状态
     * @return 同类型字典
     */
    @Override
    public List<SysDictItemDTO> findByDictType(String dictType, DictStatusEnums dictStatus) {
        Assert.hasText(dictType, "字典类型不能为空");
        Assert.notNull(dictStatus, "字典状态不能为空");
        if (!SqlHelper.exist(sysDictTypeMapper.selectCount(SysDictTypeDO::getDictType, dictType))) {
            throw new DictException("查询不到相对应的字典类型");
        }
        LambdaQueryWrapper<SysDictItemDO> wrapper =
                sysDictItemConvert.lambdaQuery()
                        .eq(SysDictItemDO::getDictType, dictType)
                        .eq(!Objects.equals(DictStatusEnums.ALL, dictStatus), SysDictItemDO::getDictStatus, dictStatus);
        List<SysDictItemDO> sysDictItemDOS = sysDictItemMapper.selectList(wrapper);
        return sysDictItemConvert.toDTO(sysDictItemDOS);
    }

}
