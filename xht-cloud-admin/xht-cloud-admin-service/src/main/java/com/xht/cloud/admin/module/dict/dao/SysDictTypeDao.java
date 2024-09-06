package com.xht.cloud.admin.module.dict.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictTypeDO;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeQueryRequest;
import com.xht.cloud.admin.module.dict.domain.request.SysDictTypeUpdateRequest;
import com.xht.cloud.admin.module.dict.mapper.SysDictTypeMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.mybatis.tool.SqlHelper;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 描述 ：字典类型
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysDictTypeDao extends BaseDaoImpl<SysDictTypeMapper, SysDictTypeDO> {

    /**
     * 判断字典编码 dictType是否存在
     *
     * @param dictType {@link String} 字典编码
     * @param dictId   {@link String} 字典id
     * @return {@link Boolean} true 存在 false不存在
     */
    public Boolean existByDictType(String dictType, String dictId) {
        // @formatter:off
        return SqlHelper.exist(count(lambdaQuery()
                .ne(StringUtils.hasText(dictId), SysDictTypeDO::getId, dictId)
                .eq(SysDictTypeDO::getDictType, dictType))
        );
        // @formatter:on
    }

    /**
     * 判断字典id是否存在
     *
     * @param dictId 字典id
     * @return 存在true
     */
    public Boolean existByDictId(String dictId) {
        return exists(lambdaQuery().eq(StringUtils.hasText(dictId), SysDictTypeDO::getId, dictId));
    }

    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public IPage<SysDictTypeDO> pageQueryRequest(SysDictTypeQueryRequest queryRequest) {
        LambdaQueryWrapper<SysDictTypeDO> wrapper = new LambdaQueryWrapper<>();
        // @formatter:off
         wrapper
                .like(StringUtils.hasText(queryRequest.getDictType()), SysDictTypeDO::getDictType, queryRequest.getDictType())
                .like(StringUtils.hasText(queryRequest.getDictName()), SysDictTypeDO::getDictName, queryRequest.getDictName())
                .eq(Objects.nonNull(queryRequest.getDictStatus()), SysDictTypeDO::getDictStatus, queryRequest.getDictStatus());
        // @formatter:on
        return page(PageTool.getPage(queryRequest), wrapper);
    }

    /**
     * 扩展的修改的接口
     *
     * @param updateRequest 修改参数
     * @return 修改成功true
     */
    public boolean updateRequest(SysDictTypeUpdateRequest updateRequest) {
        updateRequest.checkId();
        LambdaUpdateWrapper<SysDictTypeDO> wrapper = new LambdaUpdateWrapper<>();
        // @formatter:on
        wrapper
                .set(SysDictTypeDO::getDictType, updateRequest.getDictType())
                .set(SysDictTypeDO::getDictName, updateRequest.getDictName())
                .set(SysDictTypeDO::getDictStatus, updateRequest.getDictStatus())
                .set(SysDictTypeDO::getDictDesc, updateRequest.getDictDesc())
                .set(SysDictTypeDO::getDictSort, updateRequest.getDictSort())
                .eq(SysDictTypeDO::getId, updateRequest.getId());
        // @formatter:on
        return update(wrapper);
    }
}
