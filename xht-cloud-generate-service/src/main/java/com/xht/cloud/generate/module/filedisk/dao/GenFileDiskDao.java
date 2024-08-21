package com.xht.cloud.generate.module.filedisk.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.generate.module.filedisk.domain.dataobject.GenFileDiskDO;
import com.xht.cloud.generate.module.filedisk.mapper.GenFileDiskMapper;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 描述 ：Dao
 *
 * @author : 小糊涂
 **/
@Repository
public class GenFileDiskDao extends BaseDaoImpl<GenFileDiskMapper, GenFileDiskDO> {

    /**
     * 修改entity修改
     *
     * @param entity 实体
     * @return 修改
     */
    @Override
    public boolean update(GenFileDiskDO entity) {
        if (Objects.isNull(entity)) return Boolean.FALSE;
        LambdaUpdateWrapper<GenFileDiskDO> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(GenFileDiskDO::getParentId, entity.getParentId())
                .set(GenFileDiskDO::getConfigId, entity.getParentId())
                .set(GenFileDiskDO::getFileName, entity.getParentId())
                .set(GenFileDiskDO::getFileDesc, entity.getParentId())
                .set(GenFileDiskDO::getFileType, entity.getParentId())
                .set(GenFileDiskDO::getFilePath, entity.getParentId())
                .set(GenFileDiskDO::getFileContent, entity.getParentId())
                .set(GenFileDiskDO::getFileSort, entity.getParentId())
                .set(GenFileDiskDO::getIgnoreField, entity.getParentId())
                .set(GenFileDiskDO::getFileCodePath, entity.getParentId())
                .eq(GenFileDiskDO::getId, entity.getId());
        // @formatter:on
        return update(updateWrapper);
    }
}
