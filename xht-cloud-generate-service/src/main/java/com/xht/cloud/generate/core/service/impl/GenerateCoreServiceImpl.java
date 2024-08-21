package com.xht.cloud.generate.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.framework.utils.treenode.INode;
import com.xht.cloud.framework.utils.treenode.TreeNode;
import com.xht.cloud.framework.utils.treenode.TreeUtils;
import com.xht.cloud.generate.core.controller.request.GenCodeRequest;
import com.xht.cloud.generate.core.mananger.ColumnTypeManager;
import com.xht.cloud.generate.core.service.IGenerateCoreService;
import com.xht.cloud.generate.exception.GenerateException;
import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import com.xht.cloud.generate.module.column.mapper.GenTableColumnMapper;
import com.xht.cloud.generate.module.config.mapper.GenCodeConfigMapper;
import com.xht.cloud.generate.module.database.domain.dataobject.GenDatabaseDO;
import com.xht.cloud.generate.module.database.mapper.GenDatabaseMapper;
import com.xht.cloud.generate.module.filedisk.domain.dataobject.GenFileDiskDO;
import com.xht.cloud.generate.module.filedisk.mapper.GenFileDiskMapper;
import com.xht.cloud.generate.module.table.domain.dataobject.GenTableDO;
import com.xht.cloud.generate.module.table.mapper.GenTableMapper;
import com.xht.cloud.generate.module.template.mapper.GenCodeTemplateMapper;
import com.xht.cloud.generate.utils.GenerateTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 描述 ：代码生成器核心任务
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateCoreServiceImpl implements IGenerateCoreService {

    private final GenTableMapper genTableMapper;

    private final GenCodeConfigMapper genCodeConfigMapper;

    private final GenFileDiskMapper genFileDiskMapper;

    private final GenDatabaseMapper genDatabaseMapper;

    private final GenTableColumnMapper genTableColumnMapper;

    private final GenCodeTemplateMapper genCodeTemplateMapper;

    private final ColumnTypeManager columnTypeManager;

    /**
     * 查看代码
     *
     * @param genCodeRequest {@link GenCodeRequest}
     * @param tableId        表id
     * @return 代码文件树的格式
     */
    @Override
    public List<INode<Long>> viewCode(GenCodeRequest genCodeRequest, String tableId) {
        GenTableDO genTableDO = genTableMapper.findById(tableId).orElseThrow(() -> new GenerateException("表信息查询不到!"));
        String configId = genTableDO.getConfigId();
        Assert.hasText(configId, () -> new GenerateException("表信息查询不到配置信息!"));
        genCodeConfigMapper.findById(configId).orElseThrow(() -> new GenerateException("配置信息查询不到！"));
        LambdaQueryWrapper<GenFileDiskDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(GenFileDiskDO::getConfigId, configId)
                .orderByDesc(GenFileDiskDO::getFileSort);
        List<GenFileDiskDO> fileDiskDOS = genFileDiskMapper.selectList(lambdaQueryWrapper);
        Assert.notEmpty(fileDiskDOS, () -> new GenerateException("查询不到配置详细"));
        //获取数据源信息
        GenDatabaseDO databaseDO = genDatabaseMapper.findById(genTableDO.getGenDbId()).orElseThrow(() -> new GenerateException("数据源信息查询不到！"));
        //获取字段信息
        List<GenTableColumnDO> columns = genTableColumnMapper.selectList(GenTableColumnDO::getTableId, genTableDO.getId());
        // 初始化模板字段信息
        VelocityContext velocityContext = GenerateTool.initVelocityContext(genCodeRequest, databaseDO, genTableDO);
        List<INode<Long>> result = new ArrayList<>();
        for (GenFileDiskDO item : fileDiskDOS) {
            item.setFileName(GenerateTool.generateCodeFilePath(genCodeRequest, genTableDO, item.getFileName()));
            item.setFilePath(GenerateTool.generateCodeFilePath(genCodeRequest, genTableDO, item.getFilePath()));
            item.setFileCodePath(GenerateTool.generateCodeFilePath(genCodeRequest, genTableDO, item.getFileCodePath()));
            if (Objects.equals("3", item.getFileType())) {
                GenerateTool.addColumnInfo(velocityContext, columns, item, columnTypeManager);
                item.setFileContent(GenerateTool.initCode(velocityContext, item.getFileName(), item.getFileContent()));
            }
            result.add(new TreeNode<>(item.getId(), item.getParentId(), item.getFileSort()).setExtra(BeanUtil.beanToMap(item)));
        }
        return TreeUtils.buildList(result);
    }

    private void createTreeNode(List<INode<String>> result, String path, String code) {
        if (!StringUtils.hasText(path)) return;
        List<String> split = StrUtil.split(path, File.separator);
        for (int i = 0; i < split.size(); i++) {
            String id = StringUtils.collectionToDelimitedString(split.subList(0, i + 1), File.separator);
            String parentId = StringUtils.collectionToDelimitedString(split.subList(0, i), File.separator);
            String label = split.get(i);
            TreeNode<String> treeNode = new TreeNode<>(id, parentId, label.length());
            treeNode.put("label", label);
            treeNode.put("fileType", "0");
            if (StringUtils.hasText(code)) {
                treeNode.put("fileType", "1");
                treeNode.put("code", code);
            }
            result.add(treeNode);
        }
    }

    /**
     * 代码下载
     *
     * @param genCodeRequest {@link GenCodeRequest}
     * @return byte[] 数据
     * @throws Exception Exception
     */
    @Override
    public byte[] downloadCode(GenCodeRequest genCodeRequest) throws Exception {
        List<String> tableIds = genCodeRequest.getTableIds();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream, StandardCharsets.UTF_8);
        zip.setComment("小糊涂代码生成器");
        // 添加到zip
        zip.putNextEntry(new ZipEntry("使用说明(不能完全依赖生成的代码!).txt"));
        IoUtil.write(zip, StandardCharsets.UTF_8, false, "");
        zip.flush();
        zip.closeEntry();
        for (String tableId : tableIds) {
            GenTableDO genTableDO = genTableMapper.findById(tableId).orElse(null);
            List<Map<String, String>> codeMaps = generateCode(genCodeRequest, genTableDO);
            for (Map<String, String> codeMap : codeMaps) {
                codeMap.forEach((k, v) -> {
                    try {
                        //这里要对空白文件夹作处理
                        zip.putNextEntry(new ZipEntry(k + (StringUtils.hasText(v) ? "" : "/")));
                        IoUtil.write(zip, StandardCharsets.UTF_8, false, v);
                        zip.flush();
                        zip.closeEntry();
                    } catch (Exception e) {
                        log.error("下载错误 {}", e.getMessage());
                    }
                });
            }
        }
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }


    public List<Map<String, String>> generateCode(GenCodeRequest genCodeRequest, GenTableDO genTableDO) {
        List<Map<String, String>> result = new ArrayList<>();
        if (Objects.isNull(genTableDO)) return result;
//        //获取数据源信息
//        GenDatabaseDO databaseDO = genDatabaseMapper.findById(genTableDO.getGenDbId()).orElse(null);
//        //获取配置信息 并且格式化数据获取文件信息
//        GenCodeConfigDO configDO = genCodeConfigMapper.selectById(genTableDO.getConfigId());
//        //获取字段信息
//        List<GenTableColumnDO> columns = genTableColumnMapper.selectList(GenTableColumnDO::getTableId, genTableDO.getId());
//        // 初始化模板字段信息
//        VelocityContext velocityContext = GenerateTool.initVelocityContext(genCodeRequest, databaseDO, genTableDO);
//        for (ConfigTreeDTO configTreeDTO : configTreeDTOS) {
//            Map<String, ConfigTreeDTO> treeDTOMap = GenerateTool.flatArray(configTreeDTO);
//            Set<String> keys = treeDTOMap.keySet();
//            Map<String, String> codeMap = new HashMap<>();
//            for (String key : keys) {
//                String code = null;
//                String telName = null;
//                ConfigTreeDTO codeConfig = treeDTOMap.get(key);
//                String filePath = GenerateTool.initCodeFilePath(genCodeRequest, genTableDO, key);
//                if (StringUtils.hasText(codeConfig.getTemplateId())) {
//                    GenCodeTemplateDO templateDO = genCodeTemplateMapper.findById(codeConfig.getTemplateId()).orElseGet(() -> {
//                        GenCodeTemplateDO genCodeTemplateDO = new GenCodeTemplateDO();
//                        genCodeTemplateDO.setFileName("Undefined");
//                        genCodeTemplateDO.setIgnoreField(null);
//                        genCodeTemplateDO.setTelContent("模板内容丢失，请重新构建模板，并分配模板!");
//                        genCodeTemplateDO.setTelFileType("txt");
//                        return genCodeTemplateDO;
//                    });
//                    code = templateDO.getTelContent();
//                    telName = templateDO.getTelName();
//                    GenerateTool.initVelocityContext(velocityContext, columns, templateDO.getIgnoreField(), columnTypeManager);
//                }
//                codeMap.put(filePath, GenerateTool.initCode(velocityContext, telName, code));
//            }
//            result.add(codeMap);
//        }
        return result;
    }

}
