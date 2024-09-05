package com.xht.cloud.admin.module.dict.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.module.dict.domain.dataobject.SysDictItemDO;
import com.xht.cloud.admin.module.dict.domain.request.SysDictItemQueryRequest;
import com.xht.cloud.admin.module.dict.mapper.SysDictItemMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * 描述 ：字典数据
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysDictItemDao extends BaseDaoImpl<SysDictItemMapper, SysDictItemDO> {

    /**
     * 跟据字典id删除数据
     *
     * @param dictIds 字典id
     */
    public boolean removeBatchByDictIds(List<String> dictIds) {
        return remove(new LambdaQueryWrapper<SysDictItemDO>().in(SysDictItemDO::getDictId, dictIds));
    }


    /**
     * 跟据字典id查询字典数据
     *
     * @param dictId 字典id
     * @return 字典数据
     */
    public List<SysDictItemDO> selectListByDictId(String dictId) {
        return list(lambdaQuery()
                .eq(SysDictItemDO::getDictId, dictId)
                .orderByAsc(SysDictItemDO::getDictSort));
    }

    /**
     * 根据 字典id和字典编码判断是否存在
     *
     * @param dictId   {@link String} 字典id
     * @param dictCode {@link String} 字典编码
     * @return {@link Boolean} true 存在 false不存在
     */
    public boolean exists(String dictId, String dictCode) {
        return exists(lambdaQuery()
                .eq(SysDictItemDO::getDictId, dictId)
                .eq(SysDictItemDO::getDictCode, dictCode));
    }

    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public IPage<SysDictItemDO> pageQueryRequest(SysDictItemQueryRequest queryRequest) {
        LambdaQueryWrapper<SysDictItemDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(StringUtils.hasText(queryRequest.getDictId()), SysDictItemDO::getDictId, queryRequest.getDictId())
                .like(StringUtils.hasText(queryRequest.getDictCode()), SysDictItemDO::getDictCode, queryRequest.getDictCode())
                .like(StringUtils.hasText(queryRequest.getDictValue()), SysDictItemDO::getDictValue, queryRequest.getDictValue())
                .eq(Objects.nonNull(queryRequest.getDictStatus()), SysDictItemDO::getDictStatus, queryRequest.getDictStatus());
        return page(PageTool.getPage(queryRequest), lambdaQueryWrapper);
    }
}
