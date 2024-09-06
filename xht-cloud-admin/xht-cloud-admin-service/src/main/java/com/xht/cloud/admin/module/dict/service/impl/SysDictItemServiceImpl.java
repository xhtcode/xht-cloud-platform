package com.xht.cloud.admin.module.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.cloud.admin.api.dict.dto.SysDictItemDTO;
import com.xht.cloud.admin.api.dict.enums.DictStatusEnums;
import com.xht.cloud.admin.exceptions.DictException;
import com.xht.cloud.admin.module.dict.convert.SysDictItemConvert;
import com.xht.cloud.admin.module.dict.convert.SysDictTypeConvert;
import com.xht.cloud.admin.module.dict.dao.SysDictItemDao;
import com.xht.cloud.admin.module.dict.dao.SysDictTypeDao;
import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictItemDO;
import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictTypeDO;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemCreateRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemQueryRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemUpdateRequest;
import com.xht.cloud.admin.module.dict.domain.response.SysDictItemResponse;
import com.xht.cloud.admin.module.dict.service.ISysDictItemService;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
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

    private final SysDictTypeDao sysDictTypeDao;

    private final SysDictItemDao sysDictItemDao;

    private final SysDictTypeConvert sysDictTypeConvert;

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
        Assert.notNull(createRequest, "字典数据添加信息不能为空");
        Assert.notNull(createRequest.getDictId(), "字典数据添加类型id不能为空");
        SysDictTypeDO dictTypeByDictId = getDictTypeByDictId(createRequest.getDictId());
        if (sysDictItemDao.exists(createRequest.getDictId(), createRequest.getDictCode())) {
            throw new DictException("字典编码`%s`已存在，添加失败", createRequest.getDictCode());
        }
        SysDictItemDO entity = sysDictItemConvert.toDO(createRequest);
        entity.setDictType(dictTypeByDictId.getDictType());
        sysDictItemDao.save(entity);
        return entity.getId();
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
        Assert.notNull(updateRequest.getId(), "字典数据修改信息id不能为空");
        SysDictTypeDO dictTypeByDictId = getDictTypeByDictId(updateRequest.getDictId());
        LambdaQueryWrapper<SysDictItemDO> wrapper =new  LambdaQueryWrapper<SysDictItemDO>()
                .eq(SysDictItemDO::getDictId, updateRequest.getDictId())
                .eq(SysDictItemDO::getDictCode, updateRequest.getDictCode())
                .ne(SysDictItemDO::getId, updateRequest.getId());
        if (sysDictItemDao.exists(wrapper)) {
            throw new DictException(String.format("字典编码`%s`已存在，添加失败", updateRequest.getDictCode()));
        }
        updateRequest.setDictType(dictTypeByDictId.getDictType());
        sysDictItemDao.updateRequest(updateRequest);
        // @formatter:on
    }

    private SysDictTypeDO getDictTypeByDictId(String dictId) {
        LambdaQueryWrapper<SysDictTypeDO> queryWrapper = new  LambdaQueryWrapper<SysDictTypeDO>()
                .select(SysDictTypeDO::getId, SysDictTypeDO::getDictType)
                .eq(SysDictTypeDO::getId, dictId);
        SysDictTypeDO sysDictTypeDO = sysDictTypeDao.getOne(queryWrapper);
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
        Assert.notEmpty(ids, "删除信息错误!");
        sysDictItemDao.removeBatchByIds(ids);
    }

    /**
     * 根据id查询字典数据详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysDictItemResponse}
     */
    @Override
    public SysDictItemResponse findById(String id) {
        return sysDictItemConvert.toResponse(sysDictItemDao.getById(id));
    }

    /**
     * 分页查询字典数据
     *
     * @param queryRequest {@link SysDictItemQueryRequest}
     * @return {@link SysDictItemResponse} 分页详情
     */
    @Override
    public PageResponse<SysDictItemResponse> findPage(SysDictItemQueryRequest queryRequest) {
        return sysDictItemConvert.toPageResponse(sysDictItemDao.pageQueryRequest(queryRequest));
    }

    /**
     * 根据字典id查询详细字典数据
     *
     * @param dictId {@link String} 数据库主键
     * @return {@link SysDictItemResponse}
     */
    @Override
    public List<SysDictItemResponse> findByDictId(String dictId) {
        return sysDictItemConvert.toResponse(sysDictItemDao.selectListByDictId(dictId));
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
        if (sysDictTypeDao.existByDictType(dictType, null)) {
            throw new DictException("查询不到相对应的字典类型:{}", dictType);
        }
        LambdaQueryWrapper<SysDictItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictItemDO::getDictType, dictType)
                .eq(!Objects.equals(DictStatusEnums.ALL, dictStatus), SysDictItemDO::getDictStatus, dictStatus);
        List<SysDictItemDO> sysDictItemDOS = sysDictItemDao.list(wrapper);
        return sysDictItemConvert.toDTO(sysDictItemDOS);
    }

    /**
     * 通过字典ID查找字典
     *
     * @param dictId 字典id
     * @return 同类型字典
     */
    @Override
    public SysDictItemDTO findDTOById(String dictId) {
        LambdaQueryWrapper<SysDictItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictItemDO::getId, dictId);
        wrapper.eq(SysDictItemDO::getDictStatus, DictStatusEnums.NORMAL);
        return sysDictItemConvert.toDTO(sysDictItemDao.getOne(wrapper));
    }

    /**
     * 通过字典ID查找字典
     *
     * @param dictType 字典类型
     * @param dictCode 字典编码
     * @return 同类型字典
     */
    @Override
    public SysDictItemDTO findSysDictDTO(String dictType, String dictCode) {
        LambdaQueryWrapper<SysDictItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictItemDO::getDictType, dictType);
        wrapper.eq(SysDictItemDO::getDictCode, dictCode);
        wrapper.eq(SysDictItemDO::getDictStatus, DictStatusEnums.NORMAL);
        return sysDictItemConvert.toDTO(sysDictItemDao.getOne(wrapper));
    }

}
