package com.xht.cloud.generate.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.cloud.framework.exception.Assert;
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
import com.xht.cloud.generate.utils.GenerateTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.xht.cloud.generate.constant.GenerateConstant.PATH_SEPARATOR;

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

    private final ColumnTypeManager columnTypeManager;

    /**
     * 查看代码
     *
     * @param genCodeRequest {@link GenCodeRequest}
     * @param tableId        表id
     * @return 代码文件树的格式
     */
    @Override
    public List<INode<String>> viewCode(GenCodeRequest genCodeRequest, String tableId) {
        List<GenFileDiskDO> genFileDiskDOS = generateCode(genCodeRequest, tableId);
        List<INode<String>> result = new ArrayList<>();
        for (GenFileDiskDO item : genFileDiskDOS) {
            result.add(new TreeNode<>(item.getId(), item.getParentId(), item.getFileSort()).setExtra(BeanUtil.beanToMap(item)));
        }
        return TreeUtils.buildList(result);
    }

    private List<GenFileDiskDO> generateCode(GenCodeRequest genCodeRequest, String tableId) {
        GenTableDO genTableDO = genTableMapper.findById(tableId).orElseThrow(() -> new GenerateException("表信息查询不到!"));
        Long configId = genTableDO.getConfigId();
        Assert.notNull(configId, () -> new GenerateException("表信息查询不到配置信息!"));
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
        List<GenFileDiskDO> result = new ArrayList<>();
        for (GenFileDiskDO item : fileDiskDOS) {
            item.setFileName(GenerateTool.generateCodeFilePath(genCodeRequest, genTableDO, item.getFileName()));
            item.setFilePath(GenerateTool.generateCodeFilePath(genCodeRequest, genTableDO, item.getFilePath()));
            item.setFileCodePath(GenerateTool.generateCodeFilePath(genCodeRequest, genTableDO, item.getFileCodePath()));
            if (Objects.equals("3", item.getFileType())) {
                GenerateTool.addColumnInfo(velocityContext, columns, item, columnTypeManager);
                item.setFileContent(GenerateTool.initCode(velocityContext, item.getFileName(), item.getFileContent()));
            }
            result.add(item);
        }
        return result;
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
        Assert.notEmpty(tableIds, () -> new GenerateException("查询不到表信息！"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream, StandardCharsets.UTF_8);
        zip.setComment("小糊涂代码生成器");
        // 添加到zip
        zip.putNextEntry(new ZipEntry("使用说明(不能完全依赖生成的代码!).txt"));
        IoUtil.write(zip, StandardCharsets.UTF_8, false, "");
        zip.flush();
        zip.closeEntry();
        for (String tableId : tableIds) {
            List<GenFileDiskDO> genFileDiskDOS = generateCode(genCodeRequest, tableId);
            for (GenFileDiskDO genFileDiskDO : genFileDiskDOS) {
                try {
                    //这里要对空白文件夹作处理
                    zip.putNextEntry(new ZipEntry(genFileDiskDO.getFilePath() + (Objects.equals("3", genFileDiskDO.getFileType()) ? "" : PATH_SEPARATOR)));
                    IoUtil.write(zip, StandardCharsets.UTF_8, false, genFileDiskDO.getFileContent());
                    zip.flush();
                    zip.closeEntry();
                } catch (Exception e) {
                    log.error("下载错误 {}", e.getMessage());
                    throw new GenerateException("代码文件导出失败！", e);
                }
            }
        }
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }
}
