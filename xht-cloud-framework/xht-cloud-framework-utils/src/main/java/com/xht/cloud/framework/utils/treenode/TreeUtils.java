package com.xht.cloud.framework.utils.treenode;

import cn.hutool.core.lang.tree.Tree;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 描述 ：树工具类
 *
 * @author 小糊涂
 **/
public final class TreeUtils {


    /**
     * 把树给拆解成list集合
     *
     * @param node               {@link Tree<T>}
     * @param includeCurrentNode 否包含当前节点的名称
     * @return {@link List<TreeNode> }
     */
    public static <T> List<INode<T>> dismantle(INode<T> node, boolean includeCurrentNode) {
        List<INode<T>> result = new ArrayList<>();
        if (Objects.nonNull(node)) {
            if (includeCurrentNode) {
                result.add(node);
            }
            List<INode<T>> children = node.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                for (INode<T> child : children) {
                    dismantle(child, true);
                }
            }
        }
        return result;
    }


    /**
     * 把树给拆解成list集合
     *
     * @param nodes {@link List<TreeNode>}
     * @return {@link List<TreeNode> }
     */
    public static <T> List<INode<T>> dismantle(List<INode<T>> nodes) {
        List<INode<T>> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(nodes)) {
            for (INode<T> node : nodes) {
                List<INode<T>> dismantle = dismantle(node, true);
                if (!CollectionUtils.isEmpty(dismantle)) {
                    result.addAll(dismantle);
                }
            }
        }
        return result;
    }

    public static <T> List<INode<T>> buildList(List<INode<T>> result) {
        TreeIBuilder<T> of = TreeIBuilder.of();
        return of.appendList(result).buildList();
    }
}
