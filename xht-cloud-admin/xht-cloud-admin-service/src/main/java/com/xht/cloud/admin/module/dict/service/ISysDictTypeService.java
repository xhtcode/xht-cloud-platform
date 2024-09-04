package com.xht.cloud.admin.module.dict.service;

import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeCreateRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeQueryRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeUpdateRequest;
import com.xht.cloud.admin.module.dict.domain.response.SysDictItemResponse;
import com.xht.cloud.admin.module.dict.domain.response.SysDictTypeResponse;
import com.xht.cloud.admin.module.dict.domain.response.SysDictTypeVo;
import com.xht.cloud.framework.domain.response.PageResponse;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 描述 ：字典
 *
 * @author 小糊涂
 **/
public interface ISysDictTypeService {

    /**
     * 创建
     *
     * @param createRequest {@link SysDictTypeCreateRequest}
     * @return {@link String} 主键
     */
    String create(SysDictTypeCreateRequest createRequest);

    /**
     * 根据id修改
     *
     * @param updateRequest {@link SysDictTypeUpdateRequest}
     */
    void update(SysDictTypeUpdateRequest updateRequest);

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    void remove(List<String> ids);

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysDictTypeResponse}
     */
    SysDictTypeResponse findById(String id);

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysDictTypeQueryRequest}
     * @return {@link SysDictTypeResponse} 分页详情
     */
    PageResponse<SysDictTypeResponse> findPage(SysDictTypeQueryRequest queryRequest);

    /**
     * 根据字典编码 dictCode 判断是否存在
     * 当有id的时候是不包括自己
     *
     * @param dictType {@link String} 字典编码
     * @param id       {@link String} 字典id
     * @return {@link Boolean} true 存在 false不存在
     */
    boolean existByDictType(String dictType, String id);

    /**
     * 根据 dictType 字典类型查询详细
     *
     * @param dictType {@link String} 字典类型
     * @return {@link SysDictTypeVo}
     */
    SysDictTypeVo findByDictType(String dictType);


    /**
     * 根据 dictType 字典类型查询详细
     *
     * @param dictType {@link String} 字典类型
     * @return {@link SysDictTypeVo}
     */
    default List<SysDictItemResponse> findItemByDictType(String dictType) {
        SysDictTypeVo byCode = findByDictType(dictType);
        if (Objects.nonNull(byCode)) {
            return byCode.getChildren();
        }
        return Collections.emptyList();
    }

}
