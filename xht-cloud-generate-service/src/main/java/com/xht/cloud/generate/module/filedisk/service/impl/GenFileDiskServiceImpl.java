package com.xht.cloud.generate.module.filedisk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.framework.constant.StringConstant;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.mybatis.tool.SqlHelper;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.generate.exception.GenerateException;
import com.xht.cloud.generate.module.config.dao.GenCodeConfigDao;
import com.xht.cloud.generate.module.config.domain.dataobject.GenCodeConfigDO;
import com.xht.cloud.generate.module.filedisk.convert.GenFileDiskConvert;
import com.xht.cloud.generate.module.filedisk.dao.GenFileDiskDao;
import com.xht.cloud.generate.module.filedisk.domain.dataobject.GenFileDiskDO;
import com.xht.cloud.generate.module.filedisk.domain.request.GenFileDiskCreateRequest;
import com.xht.cloud.generate.module.filedisk.domain.request.GenFileDiskQueryRequest;
import com.xht.cloud.generate.module.filedisk.domain.request.GenFileDiskUpdateRequest;
import com.xht.cloud.generate.module.filedisk.domain.response.GenFileDiskResponse;
import com.xht.cloud.generate.module.filedisk.service.IGenFileDiskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.xht.cloud.generate.constant.GenerateConstant.PATH_SEPARATOR;

/**
 * 文件管理
 *
 * @author 小糊涂
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GenFileDiskServiceImpl implements IGenFileDiskService {

    private final GenFileDiskDao genFileDiskDao;

    private final GenCodeConfigDao genCodeConfigDao;

    private final GenFileDiskConvert fileDiskConvert;

    public GenFileDiskDO findParentId(String parentId) {
        GenFileDiskDO parentGenFileDiskDO = new GenFileDiskDO();
        if (!Objects.equals("-1", parentId)) {
            parentGenFileDiskDO = genFileDiskDao.getOne(new LambdaQueryWrapper<GenFileDiskDO>().select(
                                    GenFileDiskDO::getId,
                                    GenFileDiskDO::getFileType,
                                    GenFileDiskDO::getFilePath,
                                    GenFileDiskDO::getFileCodePath
                            ).eq(GenFileDiskDO::getId, parentId)
                            .ne(GenFileDiskDO::getFileType, "3")
                            .isNotNull(GenFileDiskDO::getFileType)
            );
        }
        if (Objects.isNull(parentGenFileDiskDO)) {
            throw new BizException("上级文件夹信息查询不到，文件创建失败");
        }
        return parentGenFileDiskDO;
    }

    /**
     * 创建文件
     *
     * @param createRequest {@link GenFileDiskCreateRequest} 创建参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(GenFileDiskCreateRequest createRequest) {
        Assert.notNull(createRequest, "createRequest 文件信息不能为空");
        Long configId = createRequest.getConfigId();
        if (!SqlHelper.exist(genCodeConfigDao.selectCount(GenCodeConfigDO::getId, configId))) {
            throw new BizException("配置信息查询不到，文件创建失败");
        }
        String parentId = createRequest.getParentId();
        GenFileDiskDO parentGenFileDiskDO = findParentId(parentId);
        if (Objects.equals("2", parentGenFileDiskDO.getFileType()) && !Objects.equals("3", createRequest.getFileType())) {
            createRequest.setFileType("2");
        }
        GenFileDiskDO fileDiskDO = new GenFileDiskDO();
        fileDiskDO.setParentId(parentId);
        fileDiskDO.setConfigId(configId);
        fileDiskDO.setFileName(createRequest.getFileName());
        fileDiskDO.setFileDesc(createRequest.getFileDesc());
        fileDiskDO.setFileType(createRequest.getFileType());
        fileDiskDO.setFileSort(createRequest.getFileSort());
        fileDiskDO.setFilePath(String.format("%s%s%s", StringUtils.emptyToDefault(parentGenFileDiskDO.getFilePath(), StringConstant.EMPTY_STR),
                PATH_SEPARATOR, createRequest.getFileName()));
        if (Objects.equals("3", createRequest.getFileType())) {
            fileDiskDO.setFileContent(createRequest.getFileContent());
            fileDiskDO.setIgnoreField(createRequest.getIgnoreField());
            fileDiskDO.setFileCodePath(parentGenFileDiskDO.getFileCodePath());
        }
        if (Objects.equals("2", createRequest.getFileType())) {
            fileDiskDO.setFileCodePath(String.format(
                    "%s%s%s",
                    StringUtils.emptyToDefault(parentGenFileDiskDO.getFileCodePath(), StringConstant.EMPTY_STR),
                    PATH_SEPARATOR,
                    createRequest.getFileName())
            );
        }
        genFileDiskDao.save(fileDiskDO);
    }

    /**
     * 根据id修改文件
     *
     * @param updateRequest {@link GenFileDiskUpdateRequest} 修改参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GenFileDiskUpdateRequest updateRequest) {
        Assert.notNull(updateRequest, "updateRequest 文件信息不能为空");
        if (!SqlHelper.exist(genCodeConfigDao.selectCount(GenCodeConfigDO::getId, updateRequest.getConfigId()))) {
            throw new GenerateException("配置信息查询不到，文件创建失败");
        }
        String parentId = updateRequest.getParentId();
        GenFileDiskDO parentGenFileDiskDO = findParentId(parentId);
        if (Objects.equals("2", parentGenFileDiskDO.getFileType()) && !Objects.equals("3", updateRequest.getFileType())) {
            updateRequest.setFileType("2");
        }
        Long configId = updateRequest.getConfigId();
        String fileName = updateRequest.getFileName();
        String fileDesc = updateRequest.getFileDesc();
        String fileType = updateRequest.getFileType();
        Integer fileSort = updateRequest.getFileSort();
        String filePath = String.format("%s%s%s", StringUtils.emptyToDefault(parentGenFileDiskDO.getFilePath(), StringConstant.EMPTY_STR),
                PATH_SEPARATOR, updateRequest.getFileName());
        String fileContent = null;
        String ignoreField = null;
        String fileCodePath = null;
        if (Objects.equals("3", fileType)) {
            fileContent = StringUtils.emptyToDefault(updateRequest.getFileContent(), StringConstant.EMPTY_STR);
            ignoreField = updateRequest.getIgnoreField();
            fileCodePath = parentGenFileDiskDO.getFileCodePath();
        }
        if (Objects.equals("2", fileType)) {
            fileCodePath = String.format(
                    "%s%s%s",
                    StringUtils.emptyToDefault(parentGenFileDiskDO.getFileCodePath(), StringConstant.EMPTY_STR),
                    PATH_SEPARATOR,
                    updateRequest.getFileName()
            );
        }
        // @formatter:off
        LambdaUpdateWrapper<GenFileDiskDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(GenFileDiskDO::getParentId, parentId)
                .set(GenFileDiskDO::getConfigId, configId)
                .set(GenFileDiskDO::getFileName, fileName)
                .set(GenFileDiskDO::getFileDesc, fileDesc)
                .set(GenFileDiskDO::getFileType, fileType)
                .set(GenFileDiskDO::getFilePath, filePath)
                .set(GenFileDiskDO::getFileContent, fileContent)
                .set(GenFileDiskDO::getFileSort, fileSort)
                .set(GenFileDiskDO::getIgnoreField, ignoreField)
                .set(GenFileDiskDO::getFileCodePath, fileCodePath)
                .eq(GenFileDiskDO::getId, updateRequest.getId());
        // @formatter:on
        genFileDiskDao.update(updateWrapper);
    }

    /**
     * 根据id删除文件
     *
     * @param ids id集合
     */
    @Override
    public void remove(List<String> ids) {
        Assert.notEmpty(ids, "id 不能为空！");
        genFileDiskDao.removeByIds(ids);
    }

    /**
     * 根据id查询详细文件
     *
     * @param id 数据库主键
     * @return {@link GenFileDiskResponse} 文件详细
     */
    @Override
    public GenFileDiskResponse findById(String id) {
        Assert.hasText(id, "id 不能为空！");
        return fileDiskConvert.toResponse(genFileDiskDao.getById(id));
    }

    /**
     * 查询文件列表
     *
     * @param queryRequest {@link GenFileDiskQueryRequest} 查询参数
     * @return 分页详情
     */
    @Override
    public List<GenFileDiskResponse> findList(GenFileDiskQueryRequest queryRequest) {
        String parentId = queryRequest.getParentId();
        Long configId = queryRequest.getConfigId();
        String filePath = queryRequest.getFilePath();
        String notFileType = queryRequest.getNotFileType();
        Assert.notNull(queryRequest, "queryRequest 不能为空！");
        // @formatter:off
        LambdaQueryWrapper<GenFileDiskDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Objects.nonNull(parentId), GenFileDiskDO::getParentId, parentId)
                .eq(Objects.nonNull(configId), GenFileDiskDO::getConfigId, configId)
                .ne(StringUtils.hasText(notFileType), GenFileDiskDO::getFileType, notFileType)
                .eq(StringUtils.hasText(filePath), GenFileDiskDO::getFilePath, filePath)
                .orderByAsc(GenFileDiskDO::getFileType)
                .orderByDesc(GenFileDiskDO::getFileSort)
        ;
        // @formatter:on
        List<GenFileDiskDO> genFileDiskDOS = genFileDiskDao.list(queryWrapper);
        return fileDiskConvert.toResponse(genFileDiskDOS);
    }

    /**
     * 按条件查询文件
     *
     * @param configId 配置信息id
     * @param parentId 上级目录id
     * @return 文件信息
     */
    @Override
    public List<GenFileDiskResponse> findListInfo(Long configId, String parentId) {
        LambdaQueryWrapper<GenFileDiskDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(GenFileDiskDO::getParentId, parentId)
                .eq(GenFileDiskDO::getConfigId, configId)
                .orderByAsc(GenFileDiskDO::getFileType)
                .orderByDesc(GenFileDiskDO::getFileSort)
        ;
        List<GenFileDiskDO> genFileDiskDOS = genFileDiskDao.list(queryWrapper);
        return fileDiskConvert.toResponse(genFileDiskDOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveFile(String source, String target, Long configId) {
        GenFileDiskDO fileDiskDO = new GenFileDiskDO();
        fileDiskDO.setConfigId(configId);
        if (!Objects.equals("-1", target)) {
            fileDiskDO = genFileDiskDao.getOptById(target).orElseThrow(() -> new BizException("找不到文件信息"));
        }
        if (!Objects.equals("3", fileDiskDO.getFileType()) && Objects.equals(configId, fileDiskDO.getConfigId())) {
            // @formatter:off
            LambdaUpdateWrapper<GenFileDiskDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .set(GenFileDiskDO::getParentId, target)
                    .eq(GenFileDiskDO::getId, source)
                    .eq(GenFileDiskDO::getConfigId,configId)
            ;
            // @formatter:on
            genFileDiskDao.update(updateWrapper);
        }
        List<GenFileDiskDO> fileDiskDOS = new ArrayList<>();
        updatePath(fileDiskDO, fileDiskDOS);
        genFileDiskDao.updateBatchById(fileDiskDOS);
    }

    public void updatePath(GenFileDiskDO parentGenFileDiskDO, List<GenFileDiskDO> fileDiskDOS) {
        List<GenFileDiskDO> genFileDiskDOS = genFileDiskDao.selectList(GenFileDiskDO::getParentId, parentGenFileDiskDO.getParentId());
        if (!CollectionUtils.isEmpty(genFileDiskDOS)) {
            for (GenFileDiskDO genFileDiskDO : genFileDiskDOS) {
                String filePath = String.format("%s%s%s",
                        StringUtils.emptyToDefault(parentGenFileDiskDO.getFilePath(), StringConstant.EMPTY_STR), PATH_SEPARATOR,
                        genFileDiskDO.getFileName());
                String fileCodePath = String.format("%s%s%s", StringUtils.emptyToDefault(parentGenFileDiskDO.getFileCodePath(), StringConstant.EMPTY_STR), PATH_SEPARATOR,
                        genFileDiskDO.getFileName());
                genFileDiskDO.setFilePath(filePath);
                genFileDiskDO.setFileCodePath(fileCodePath);
                fileDiskDOS.add(genFileDiskDO);
                updatePath(genFileDiskDO, fileDiskDOS);
            }
        }
    }
}
