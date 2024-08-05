package com.xht.cloud.admin.module.dict.service;

import com.xht.cloud.admin.api.dict.dto.SysDictItemDTO;
import com.xht.cloud.admin.api.dict.enums.DictStatusEnums;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemCreateRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemQueryRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemUpdateRequest;
import com.xht.cloud.admin.module.dict.domain.response.SysDictItemResponse;
import com.xht.cloud.framework.core.domain.response.PageResponse;

import java.util.List;

/**
 * 描述 ：字典数据
 *
 * @author 小糊涂
 **/
public interface ISysDictItemService {

    /**
     * 创建字典数据
     *
     * @param createRequest {@link SysDictItemCreateRequest}
     * @return {@link String} 主键
     */
    String create(SysDictItemCreateRequest createRequest);

    /**
     * 根据id修改字典数据
     *
     * @param updateRequest {@link SysDictItemUpdateRequest}
     */
    void update(SysDictItemUpdateRequest updateRequest);

    /**
     * 删除字典数据
     *
     * @param ids {@link List<String>} id集合
     */
    void remove(List<String> ids);

    /**
     * 根据id查询字典数据详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysDictItemResponse}
     */
    SysDictItemResponse findById(String id);

    /**
     * 分页查询字典数据
     *
     * @param queryRequest {@link SysDictItemQueryRequest}
     * @return {@link PageResponse<SysDictItemResponse>} 分页详情
     */
    PageResponse<SysDictItemResponse> findPage(SysDictItemQueryRequest queryRequest);

    /**
     * 根据id查询字典数据详细
     *
     * @param dictId {@link String} 数据库主键
     * @return {@link SysDictItemResponse}
     */
    List<SysDictItemResponse> findByDictId(String dictId);

    /**
     * 通过字典类型查找字典
     *
     * @param dictType   字典类型
     * @param dictStatus 字典状态
     * @return 同类型字典
     */
    List<SysDictItemDTO> findByDictType(String dictType, DictStatusEnums dictStatus);
}
