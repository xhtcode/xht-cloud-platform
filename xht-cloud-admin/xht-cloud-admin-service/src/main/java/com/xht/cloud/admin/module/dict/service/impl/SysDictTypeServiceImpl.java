package com.xht.cloud.admin.module.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.exceptions.DictException;
import com.xht.cloud.admin.module.dict.convert.SysDictItemConvert;
import com.xht.cloud.admin.module.dict.convert.SysDictTypeConvert;
import com.xht.cloud.admin.module.dict.dao.SysDictItemDao;
import com.xht.cloud.admin.module.dict.dao.SysDictTypeDao;
import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictItemDO;
import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictTypeDO;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeCreateRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeQueryRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeUpdateRequest;
import com.xht.cloud.admin.module.dict.domain.response.SysDictTypeResponse;
import com.xht.cloud.admin.module.dict.domain.response.SysDictTypeVo;
import com.xht.cloud.admin.module.dict.service.ISysDictTypeService;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 描述 ：字典类型
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl implements ISysDictTypeService {

    private final SysDictTypeDao sysDictTypeDao;

    private final SysDictItemDao sysDictItemDao;

    private final SysDictTypeConvert sysDictTypeConvert;

    private final SysDictItemConvert sysDictItemConvert;

    /**
     * 创建字典类型
     *
     * @param createRequest {@link SysDictTypeCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(SysDictTypeCreateRequest createRequest) {
        if (sysDictTypeDao.existByDictType(createRequest.getDictType(), null)) {
            throw new DictException("字典类型`{}`已存在，添加失败", createRequest.getDictType());
        }
        SysDictTypeDO entity = sysDictTypeConvert.toDO(createRequest);
        sysDictTypeDao.save(entity);
        return entity.getId();
    }

    /**
     * 根据id修改字典类型
     *
     * @param updateRequest SysDictTypeUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictTypeUpdateRequest updateRequest) {
        // @formatter:off
        Assert.notNull(updateRequest, "字典类型修改信息不能为空");
        Assert.notNull(updateRequest.getId(), "字典类型修改信息id不能为空");
        if (existByDictType(updateRequest.getDictType(), updateRequest.getId())) {
            throw new DictException(String.format("字典类型`%s`已存在，添加失败",updateRequest.getDictType()));
        }
        sysDictTypeDao.updateById(sysDictTypeConvert.toDO(updateRequest));
        // @formatter:on
    }

    /**
     * 删除字典类型
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        Assert.notEmpty(ids, "字典类型信息ids不能为空");
        LambdaQueryWrapper<SysDictTypeDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SysDictTypeDO::getId, ids);
        if (sysDictTypeDao.exists(lambdaQueryWrapper)) {
            throw new BizException("删除的字典类型对象不存在");
        }
        sysDictItemDao.removeBatchByDictIds(ids);
        sysDictTypeDao.removeBatchByIds(ids);
    }

    /**
     * 根据id查询字典类型详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysDictTypeResponse}
     */
    @Override
    public SysDictTypeResponse findById(String id) {
        return sysDictTypeConvert.toResponse(sysDictTypeDao.getOptById(id).orElse(null));
    }

    /**
     * 分页查询字典类型
     *
     * @param queryRequest {@link SysDictTypeQueryRequest}
     * @return {@link SysDictTypeResponse} 分页详情
     */
    @Override
    public PageResponse<SysDictTypeResponse> findPage(SysDictTypeQueryRequest queryRequest) {
        IPage<SysDictTypeDO> sysDictIPage = sysDictTypeDao.pageQueryRequest(queryRequest);
        return sysDictTypeConvert.toPageResponse(sysDictIPage);
    }

    /**
     * 根据字典编码 dictType 判断是否存在
     *
     * @param dictType {@link String} 字典编码
     * @param dictId       {@link String} 字典id
     * @return {@link Boolean} true 存在 false不存在
     */
    @Override
    public boolean existByDictType(String dictType, String dictId) {
        if (!StringUtils.hasText(dictType)) {
            return true;
        }
        return sysDictTypeDao.existByDictType(dictType, dictId);
    }

    /**
     * 根据dictType 字典类型查询详细
     *
     * @param dictType {@link String} 字典类型
     * @return {@link SysDictTypeVo}
     */
    @Override
    public SysDictTypeVo findByDictType(String dictType) {
        Assert.hasText(dictType, () -> new DictException("查询时 字典编码 不能为空!"));
        SysDictTypeDO sysDictDO = sysDictTypeDao.selectOne(SysDictTypeDO::getDictType, dictType).orElse(null);
        SysDictTypeVo sysDictVo = sysDictTypeConvert.toVo(sysDictDO);
        if (Objects.nonNull(sysDictVo)) {
            List<SysDictItemDO> sysDictItemDOS = sysDictItemDao.selectListByDictId(sysDictVo.getId());
            sysDictVo.setChildren(sysDictItemConvert.toResponse(sysDictItemDOS));
        }
        return sysDictVo;
    }

}
