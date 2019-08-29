package com.zxy.learning.data.structure.binarytree;

import jdk.nashorn.internal.ir.BinaryNode;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName Tree.java
 * @Description 树的基本形态
 * @createTime 2019年03月04日 10:54:00
 */
public interface Tree<T> {

    boolean isEmpty();

    int size();

    int height();

    String preOrder();

    String inOrder();

    String postOrder();

    String levelOrder();

    TreeNode insert(T curr, T node);

    void remove(T node);

    T findMax();

    T finMin();

    TreeNode findNode(T node);

    boolean contains(T node) throws Exception;

    void clear();
}
