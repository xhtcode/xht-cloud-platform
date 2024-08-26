package com.xht.cloud.generate.module.config.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.domain.KeyValue;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.file.upload.UploadFileBO;
import com.xht.cloud.framework.file.upload.helper.MultipartFileHelper;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.generate.exception.GenerateException;
import com.xht.cloud.generate.module.config.convert.GenCodeConfigConvert;
import com.xht.cloud.generate.module.config.domain.dataobject.GenCodeConfigDO;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigCreateRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigQueryRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigUpdateRequest;
import com.xht.cloud.generate.module.config.domain.response.GenCodeConfigResponse;
import com.xht.cloud.generate.module.config.domain.wrapper.GenCodeConfigWrapper;
import com.xht.cloud.generate.module.config.mapper.GenCodeConfigMapper;
import com.xht.cloud.generate.module.config.service.IGenCodeConfigService;
import com.xht.cloud.generate.module.filedisk.dao.GenFileDiskDao;
import com.xht.cloud.generate.module.filedisk.domain.dataobject.GenFileDiskDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static com.xht.cloud.framework.core.constant.StringConstant.EMPTY_STR;
import static com.xht.cloud.generate.constant.GenerateConstant.*;
/**
 * 描述 ：代码生成器-配置中心
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenCodeConfigServiceImpl implements IGenCodeConfigService {

    private final GenCodeConfigMapper genCodeConfigMapper;

    private final GenCodeConfigConvert genCodeConfigConvert;

    private final GenFileDiskDao fileDiskMapper;

    /**
     * 创建
     *
     * @param createRequest {@link GenCodeConfigCreateRequest}
     * @return {@link Long} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(GenCodeConfigCreateRequest createRequest) {
        GenCodeConfigDO entity = genCodeConfigConvert.toDO(createRequest);
        genCodeConfigMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 根据id修改
     *
     * @param updateRequest GenCodeConfigUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GenCodeConfigUpdateRequest updateRequest) {
        genCodeConfigMapper.updateById(genCodeConfigConvert.toDO(updateRequest));
    }

    /**
     * 删除
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        genCodeConfigMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link GenCodeConfigResponse}
     */
    @Override
    public GenCodeConfigResponse findById(String id) {
        return genCodeConfigConvert.toResponse(genCodeConfigMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link GenCodeConfigQueryRequest}
     * @return {@link GenCodeConfigResponse} 分页详情
     */
    @Override
    public PageResponse<GenCodeConfigResponse> findPage(GenCodeConfigQueryRequest queryRequest) {
        IPage<GenCodeConfigDO> genCodeConfigIPage = genCodeConfigMapper.selectPage(PageTool.getPage(queryRequest), GenCodeConfigWrapper.getInstance().lambdaQuery(genCodeConfigConvert.toDO(queryRequest)));
        return genCodeConfigConvert.toPageResponse(genCodeConfigIPage);
    }

    @Override
    public List<GenCodeConfigResponse> list() {
        List<GenCodeConfigDO> genCodeConfigDOS = genCodeConfigMapper.selectList(new LambdaQueryWrapper<GenCodeConfigDO>().select(GenCodeConfigDO::getId, GenCodeConfigDO::getConfigName, GenCodeConfigDO::getConfigDesc));
        return genCodeConfigConvert.toResponse(genCodeConfigDOS);
    }

    /**
     * 导出-配置中心
     *
     * @param configId 配置id
     * @return 导出内容
     */
    @Override
    public KeyValue<String, byte[]> exportZip(Long configId) {
        GenCodeConfigDO codeConfigDO = genCodeConfigMapper.findById(configId).orElseThrow(() -> new GenerateException("查询不到相关配置"));
        List<GenFileDiskDO> genFileDiskDOS = fileDiskMapper.selectList(GenFileDiskDO::getConfigId, codeConfigDO.getId());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream, StandardCharsets.UTF_8);
        for (GenFileDiskDO genFileDiskDO : genFileDiskDOS) {
            try {
                //这里要对空白文件夹作处理
                zip.putNextEntry(new ZipEntry(genFileDiskDO.getFilePath() + (Objects.equals("3", genFileDiskDO.getFileType()) ? TEMPLATE_SUFFIX : PATH_SEPARATOR)));
                IoUtil.write(zip, StandardCharsets.UTF_8, false, genFileDiskDO.getFileContent());
                zip.flush();
                zip.closeEntry();
            } catch (Exception e) {
                log.error("下载错误 {}", e.getMessage());
                throw new GenerateException("模板文件导出失败！", e);
            }
        }
        byte[] byteArray = outputStream.toByteArray();
        IoUtil.close(outputStream);
        return new KeyValue<>(codeConfigDO.getConfigName(), byteArray);
    }

    /**
     * 导入-配置中心
     *
     * @param file {@link MultipartFile} 文件信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importZip(MultipartFile file) {
        UploadFileBO uploadFileBO = MultipartFileHelper.init(file).checkFileType(fileType -> {
            String fileSuffix = fileType.getFileSuffix();
            Assert.isTrue(!Objects.equals("zip", fileSuffix), () -> new GenerateException("上传文件格式错误！"));
        }).format();
        GenCodeConfigDO codeConfigDO = new GenCodeConfigDO();
        codeConfigDO.setConfigName(uploadFileBO.getFilePrefix());
        codeConfigDO.setConfigDesc(uploadFileBO.getOriginalFileName());
        codeConfigDO.setConfigSort(0);
        genCodeConfigMapper.insert(codeConfigDO);
        InputStream inputStream = uploadFileBO.getInputStream();
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        List<GenFileDiskDO> result = new ArrayList<>();
        Map<String, GenFileDiskDO> resultMap = new HashMap<>();
        try {
            ZipEntry nextEntry;
            while ((nextEntry = zipInputStream.getNextEntry()) != null) {
                GenFileDiskDO genFileDiskDO = new GenFileDiskDO();
                genFileDiskDO.setConfigId(codeConfigDO.getId());
                String filePath = StrUtil.removeSuffix(StrUtil.addPrefixIfNot(nextEntry.getName(), PATH_SEPARATOR), PATH_SEPARATOR);
                String codePath = getCodePath(filePath);
                genFileDiskDO.setFileName(StringUtils.replace(FileUtil.getName(filePath), TEMPLATE_SUFFIX, EMPTY_STR));
                genFileDiskDO.setFileDesc("暂无");
                genFileDiskDO.setFileCodePath(StrUtil.removeSuffix(codePath, PATH_SEPARATOR + FileUtil.getName(filePath)));
                genFileDiskDO.setFilePath(filePath);
                genFileDiskDO.setFileSort(0);
                if (nextEntry.isDirectory()) {
                    genFileDiskDO.setFileType(StringUtils.hasText(codePath) ? "1" : "2");
                } else {
                    genFileDiskDO.setFileType("3");
                    genFileDiskDO.setFileContent(new String(IoUtil.readBytes(zipInputStream, false)));
                }
                resultMap.put(genFileDiskDO.getFilePath(), genFileDiskDO);
            }
            Set<String> keys = resultMap.keySet();
            Map<String, List<Path>> pathListMap = buildHierarchy(keys);
            genFileId(ROOT_FOLDER, File.separator, result, resultMap, pathListMap);
            fileDiskMapper.saveBatch(result);
        } catch (IOException e) {
            throw new GenerateException("模板解析错误", e);
        }
        return Boolean.TRUE;
    }

    public static void genFileId(String parentId, String rootPath, List<GenFileDiskDO> result,
                                 Map<String, GenFileDiskDO> fileDataMap,
                                 Map<String, List<Path>> pathListMap) {
        List<Path> paths = pathListMap.get(rootPath);
        if (CollectionUtils.isEmpty(paths)) return;
        for (Path path : paths) {
            GenFileDiskDO fileDiskDO = fileDataMap.get(StrUtil.replace(path.toString(), File.separator, PATH_SEPARATOR));
            String objectId = IdUtil.objectId();
            fileDiskDO.setId(objectId);
            fileDiskDO.setParentId(parentId);
            result.add(fileDiskDO);
            genFileId(objectId, path.toString(), result, fileDataMap, pathListMap);
        }
    }

    private String getCodePath(String path) {
        for (String empPath : GENERATE_PATH_JAVA) {
            if (StrUtil.contains(path, empPath)) {
                List<String> split = StrUtil.split(path, empPath);
                return PATH_SEPARATOR + split.get(split.size() - 1);
            }
        }
        for (String empPath : GENERATE_PATH_VUE) {
            if (StrUtil.contains(path, empPath)) {
                List<String> split = StrUtil.split(path, empPath);
                return empPath + split.get(split.size() - 1);
            }
        }
        for (String empPath : GENERATE_PATH_OTHER) {
            if (StrUtil.contains(path, empPath)) {
                List<String> split = StrUtil.split(path, empPath);
                return empPath + split.get(split.size() - 1);
            }
        }
        return null;
    }


    private static Map<String, List<Path>> buildHierarchy(Set<String> paths) {
        Map<String, List<Path>> hierarchy = new HashMap<>();
        for (String pathStr : paths) {
            Path path = Paths.get(pathStr);
            Path parent = path.getParent();
            if (parent == null) {
                hierarchy.put(path.toString(), new ArrayList<>());
            } else {
                hierarchy.computeIfAbsent(parent.toString(), k -> new ArrayList<>()).add(path);
            }
        }
        return hierarchy;
    }

}
