package com.xht.cloud.framework.utils.treenode;

import cn.hutool.core.map.MapUtil;
import com.xht.cloud.framework.core.builder.IBuilder;
import com.xht.cloud.framework.exception.Assert;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 描述 ：树节点构建器
 *
 * @author 小糊涂
 **/
public class TreeIBuilder<E> implements IBuilder<INode<E>> {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主节点
     */
    private final INode<E> root;
    /**
     * 所有树节点
     * key : 节点的id
     */
    private final Map<E, INode<E>> nodeList;
    private boolean isBuild;


    private TreeIBuilder() {
        root = new TreeNode<>();
        this.nodeList = new HashMap<>();
    }

    /**
     * 创建构造器
     */
    public static <T> TreeIBuilder<T> of() {
        return new TreeIBuilder<>();
    }


    /**
     * 增加节点列表，增加的节点是不带子节点的
     *
     * @param tree 节点
     * @return this
     */
    public TreeIBuilder<E> append(TreeNode<E> tree) {
        this.nodeList.put(tree.getId(), tree);
        return this;
    }

    /**
     * 增加节点列表，增加的节点是不带子节点的
     *
     * @param trees 节点列表
     * @return this
     */
    public TreeIBuilder<E> appendList(List<INode<E>> trees) {
        for (INode<E> tree : trees) {
            this.nodeList.put(tree.getId(), tree);
        }
        return this;
    }

    /**
     * 构建方法
     *
     * @return 被构建的对象
     */
    @Override
    public INode<E> build() {
        checkBuilt();
        this.isBuild = true;
        formatter();
        this.nodeList.clear();
        return this.root;
    }

    public List<INode<E>> buildList() {
        if (isBuild) {
            // 已经构建过了
            return this.root.getChildren();
        }
        return build().getChildren();
    }

    private void formatter() {
        Map<E, INode<E>> nodeEmp = MapUtil.sortByValue(this.nodeList, false);
        for (INode<E> node : nodeEmp.values()) {
            if (null == node) {
                continue;
            }
            //获取上级节点的id
            E parentId = node.getParentId();
            //获取上级节点的数据
            INode<E> parentNode = nodeEmp.get(parentId);
            //如果上级节点的数据是空的，证明没有上级节点，他就是一级节点
            // 如果不是空的那么当前节点是上级节点的子节点
            if (Objects.isNull(parentNode)) {
                this.root.addChildren(node);
            } else {
                parentNode.addChildren(node);
            }
        }
    }

    /**
     * 检查是否已经构建
     */
    private void checkBuilt() {
        Assert.isTrue(isBuild, "目前的树已经建成。");
    }


}
