package com.xht.cloud.framework.utils.treenode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.cloud.framework.exception.Assert;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述 ：树节点
 *
 * @author 小糊涂
 **/
@SuppressWarnings("unchecked")
public class TreeNode<T> extends LinkedHashMap<String, Object> implements INode<T> {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 权重
     */
    @JsonIgnore
    private int weight;


    public TreeNode() {
    }

    public TreeNode(T id, T parentId, int weight) {
        this.setId(id).setParentId(parentId).setWeight(weight);
    }

    public TreeNode(T id, T parentId) {
        this.setId(id).setParentId(parentId);
    }

    /**
     * 获取ID
     *
     * @return ID
     */
    @Override
    public T getId() {
        return (T) this.get("id");
    }

    /**
     * 设置ID
     *
     * @param id ID
     * @return this
     */
    @Override
    public INode<T> setId(T id) {
        this.put("id", id);
        return this;
    }

    /**
     * 获取父节点ID
     *
     * @return 父节点ID
     */
    @Override
    public T getParentId() {
        return (T) this.get("parentId");
    }

    /**
     * 设置父节点ID
     *
     * @param parentId 父节点ID
     * @return this
     */
    @Override
    public INode<T> setParentId(T parentId) {
        this.put("parentId", parentId);
        return this;
    }

    /**
     * 获取权重
     *
     * @return 权重
     */
    @Override
    public Integer getWeight() {
        return this.weight;
    }

    /**
     * 设置权重
     *
     * @param weight 权重
     * @return this
     */
    @Override
    public INode<T> setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    /**
     * @param node {@link INode}
     */
    @Override
    public void addChildren(INode<T> node) {
        List<INode<T>> children = this.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            children = new ArrayList<>();
        }
        children.add(node);
        this.setChildren(children);
    }

    /**
     * 子节点
     */
    @Override
    public List<INode<T>> getChildren() {
        return (List<INode<T>>) this.get("children");
    }

    /**
     * 设置子节点，设置后会覆盖所有原有子节点
     *
     * @param children 子节点列表
     */
    public void setChildren(List<INode<T>> children) {
        this.put("children", children);
    }

    public TreeNode<T> setExtra(Map<String, Object> beanToMap) {
        Assert.notNull(beanToMap, "beanToMap is null");
        this.putAll(beanToMap);
        return this;
    }
}
