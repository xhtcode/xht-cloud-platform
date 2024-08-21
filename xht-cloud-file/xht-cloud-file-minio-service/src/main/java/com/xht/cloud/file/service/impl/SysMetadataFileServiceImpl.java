package com.xht.cloud.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.file.convert.SysMetadataFileConvert;
import com.xht.cloud.file.domain.dataobject.SysMetadataFileDO;
import com.xht.cloud.file.domain.request.SysMetadataFileQueryRequest;
import com.xht.cloud.file.domain.response.SysMetadataFileResponse;
import com.xht.cloud.file.enums.FileStatusEnums;
import com.xht.cloud.file.mapper.SysMetadataFileMapper;
import com.xht.cloud.file.service.ISysMetadataFileService;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.file.exception.FileException;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.mybatis.tool.SqlHelper;
import com.xht.cloud.framework.utils.support.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 描述 ：文件元数据信息
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMetadataFileServiceImpl implements ISysMetadataFileService {

    private final SysMetadataFileMapper sysMetadataFileMapper;

    private final SysMetadataFileConvert sysMetadataFileConvert;

    /**
     * 根据id修改文件状态
     *
     * @param id     {@link String} id集合
     * @param status {@link FileStatusEnums} 文件状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFileStatus(String id, FileStatusEnums status) {
        if (sysMetadataFileMapper.selectCount(SysMetadataFileDO::getId, id) <= 0) {
            throw new FileException("文件查询不到!");
        }
        LambdaUpdateWrapper<SysMetadataFileDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysMetadataFileDO::getFileStatus, status)
                .eq(SysMetadataFileDO::getId, id);
        sysMetadataFileMapper.update(updateWrapper);
    }

    /**
     * 根据id删除文件信息
     *
     * @param id {@link String} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(String id) {
        if (SqlHelper.remove(sysMetadataFileMapper.deleteById(id))) {
            throw new BizException(id + "删除失败!");
        }
    }

    /**
     * 根据id查询文件信息详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysMetadataFileResponse}
     */
    @Override
    public SysMetadataFileResponse findById(String id) {
        return sysMetadataFileConvert.convert(sysMetadataFileMapper.findById(id).orElse(null));
    }

    /**
     * 查询文件列表信息
     *
     * @param queryRequest {@link SysMetadataFileQueryRequest}
     * @return {@link PageResponse<SysMetadataFileResponse>} 分页详情
     */
    @Override
    public PageResponse<SysMetadataFileResponse> findPage(SysMetadataFileQueryRequest queryRequest) {
        String fileName = queryRequest.getFileName();
        String bucket = queryRequest.getBucket();
        FileStatusEnums fileStatus = queryRequest.getFileStatus();
        LambdaQueryWrapper<SysMetadataFileDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .like(Objects.nonNull(bucket), SysMetadataFileDO::getBucket, bucket)
                .like(StringUtils.hasText(fileName), SysMetadataFileDO::getFileName, fileName)
                .eq(Objects.nonNull(fileStatus), SysMetadataFileDO::getFileStatus, fileStatus);
        IPage<SysMetadataFileDO> sysFileDOIPage = sysMetadataFileMapper.selectPage(PageTool.getPage(queryRequest), lambdaQueryWrapper);
        return sysMetadataFileConvert.convert(sysFileDOIPage);
    }
}
