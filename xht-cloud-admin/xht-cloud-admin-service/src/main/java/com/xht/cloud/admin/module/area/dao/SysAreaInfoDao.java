package com.xht.cloud.admin.module.area.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.area.domain.dataobject.SysAreaInfoDO;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoQueryRequest;
import com.xht.cloud.admin.module.area.domain.request.SysAreaInfoUpdateRequest;
import com.xht.cloud.admin.module.area.mapper.SysAreaInfoMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.SqlHelper;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class SysAreaInfoDao extends BaseDaoImpl<SysAreaInfoMapper, SysAreaInfoDO> {


    /**
     * 判断是否存在下级
     *
     * @param ids {}
     * @return 存在true
     */
    public boolean existsChild(List<String> ids) {
        return SqlHelper.exist(count(lambdaQuery().in(SysAreaInfoDO::getId, ids)));
    }

    public List<SysAreaInfoDO> selectListByRequest(SysAreaInfoQueryRequest queryRequest) {
        LambdaQueryWrapper<SysAreaInfoDO> wrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        wrapper
                .eq(StringUtils.hasText(queryRequest.getParentId()), SysAreaInfoDO::getParentId, queryRequest.getParentId())
                .like(StringUtils.hasText(queryRequest.getName()), SysAreaInfoDO::getName, queryRequest.getName())
                .like(StringUtils.hasText(queryRequest.getAreaNo()), SysAreaInfoDO::getAreaNo, queryRequest.getAreaNo());
        // @formatter:on
        return list(wrapper);
    }


    /**
     * 扩展的修改的接口
     *
     * @param updateRequest 修改参数
     * @return 修改成功true
     */
    public boolean updateRequest(SysAreaInfoUpdateRequest updateRequest) {
        updateRequest.checkId();
        // @formatter:off
        LambdaUpdateWrapper<SysAreaInfoDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper
                .set(SysAreaInfoDO::getParentId, updateRequest.getParentId())
                .set(SysAreaInfoDO::getName, updateRequest.getName())
                .set(SysAreaInfoDO::getLevel, updateRequest.getLevel())
                .set(SysAreaInfoDO::getAreaNo, updateRequest.getAreaNo())
                .set(SysAreaInfoDO::getCategory, updateRequest.getCategory())
                .set(SysAreaInfoDO::getMsg, updateRequest.getMsg())
                .eq(SysAreaInfoDO::getId, updateRequest.getId());
        // @formatter:on
        return update(wrapper);
    }
}
