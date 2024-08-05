package com.xht.cloud.framework.utils.treenode;

import java.io.Serializable;
import java.util.List;

/**
 * 描述 ：树节点父类
 *
 * @author 小糊涂
 **/
public interface INode<T> extends Comparable<INode<T>>, Serializable {

    /**
     * 获取ID
     *
     * @return ID
     */
    T getId();

    /**
     * 设置ID
     *
     * @param id ID
     * @return this
     */
    INode<T> setId(T id);

    /**
     * 获取父节点ID
     *
     * @return 父节点ID
     */
    T getParentId();

    /**
     * 设置父节点ID
     *
     * @param parentId 父节点ID
     * @return this
     */
    INode<T> setParentId(T parentId);

    /**
     * 获取权重
     *
     * @return 权重
     */
    Integer getWeight();

    /**
     * 设置权重
     *
     * @param weight 权重
     * @return this
     */
    INode<T> setWeight(int weight);


    default int compareTo(INode<T> node) {
        if (null == node) {
            return 1;
        }
        return this.getWeight().compareTo(node.getWeight());
    }

    void addChildren(INode<T> node);

    List<INode<T>> getChildren();

}
