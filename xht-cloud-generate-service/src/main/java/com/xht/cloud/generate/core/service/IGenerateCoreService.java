package com.xht.cloud.generate.core.service;

import com.xht.cloud.framework.utils.treenode.INode;
import com.xht.cloud.generate.core.controller.request.GenCodeRequest;

import java.util.List;

/**
 * 描述 ：代码生成器核心任务
 *
 * @author 小糊涂
 **/
public interface IGenerateCoreService {

    /**
     * 查看代码
     *
     * @param genCodeRequest {@link GenCodeRequest}
     * @param tableId 表id
     * @return 代码文件树的格式
     */
    List<INode<String>> viewCode(GenCodeRequest genCodeRequest,final String tableId);

    /**
     * 代码下载
     *
     * @param genCodeRequest {@link GenCodeRequest}
     * @return byte[] 数据
     * @throws Exception Exception
     */
    byte[] downloadCode(GenCodeRequest genCodeRequest) throws Exception;


}
