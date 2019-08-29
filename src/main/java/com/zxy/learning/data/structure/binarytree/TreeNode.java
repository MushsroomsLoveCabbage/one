package com.zxy.learning.data.structure.binarytree;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName TreeNode.java
 * @Description 二叉树
 * @createTime 2019年03月04日 10:41:00
 */
public class TreeNode<K extends Comparable, T> {

    public K key;

    public T data;

    public TreeNode<K, T> parent;

    public TreeNode<K, T> leftNode;

    public TreeNode<K, T> rightNode;

    public TreeNode(K key, T data) {
        this.data = data;
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TreeNode<K, T> getParent() {
        return parent;
    }

    public void setParent(TreeNode<K, T> parent) {
        this.parent = parent;
    }

    public TreeNode<K, T> getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(TreeNode<K, T> leftNode) {
        this.leftNode = leftNode;
    }

    public TreeNode<K, T> getRightNode() {
        return rightNode;
    }

    public void setRightNode(TreeNode<K, T> rightNode) {
        this.rightNode = rightNode;
    }

}
