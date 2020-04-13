package com.zxy.learning.data.structure.bst;

//import com.zxy.learning.data.structure.binarytree.LevelErgodic;
import com.zxy.learning.data.structure.binarytree.Tree;
import com.zxy.learning.data.structure.binarytree.TreeNode;

import java.util.ArrayDeque;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName BinarySearchTree.java
 * @Description 二叉搜索树
 * @createTime 2019年08月29日 13:47:00
 */
public class BinarySearchTree implements Tree<TreeNode<Integer, Integer>> {

    private static int COMPARE_STATE = 0;

    private TreeNode<Integer, Integer> root;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public String preOrder() {
        return null;
    }

    @Override
    public String inOrder() {
        return null;
    }

    @Override
    public String postOrder() {
        return null;
    }

    @Override
    public String levelOrder() {
        return null;
    }


    public TreeNode insert(Integer key, Integer val){
        if(key == null){
            throw new IllegalArgumentException("key can not be null");
        }
        if(root == null){
            root = new TreeNode<>(key, val);
            return root;
        }
        return insert(this.root,new TreeNode<>(key, val) );
    }

    @Override
    public TreeNode insert(TreeNode curr, TreeNode node) {
        if(curr == null){
            curr = node;
            return curr;
        }
        int compareState = curr.getKey().compareTo(node.getKey());
        if(compareState == COMPARE_STATE){
            curr.data = node.data;
        } else if(compareState > COMPARE_STATE) {
            TreeNode insert = insert(curr.leftNode, node);
            curr.leftNode = insert;
            insert.parent = curr;
        } else {
            TreeNode insert = insert(curr.rightNode, node);
            curr.rightNode = insert;
            insert.parent = curr;
        }
        return curr;
    }


    @Override
    public void remove(TreeNode target) {
        remove(root, target);
    }


    public TreeNode remove(Integer key) {
        if(key == null){
            throw new IllegalArgumentException("key can not be null");
        }
        return remove(root, key);
    }

    private TreeNode remove(TreeNode curr, Integer target){
        TreeNode parent = root;
        boolean isLeftChild = false;
        while(curr.key.compareTo(target) != 0){
            parent = curr;
            if(curr.key.compareTo(target) > 0){
                curr = curr.leftNode;
                isLeftChild = true;
            } else {
                curr = curr.rightNode;
                isLeftChild = false;
            }
            if(curr == null){
                //找不到对应的节点
                return curr;
            }
        }

        //叶子节点
        if(curr.leftNode == null && curr.rightNode == null){
            if(curr == root){
                root = null;
            }
            if(isLeftChild){
                parent.leftNode = null;
            } else {
                parent.rightNode = null;
            }
        } else if(curr.leftNode == null){ //左节点为空
            if(curr == root){
                root = curr.rightNode;
                root.parent = null;
            } else {
                if (isLeftChild) {
                    parent.leftNode = curr.rightNode;
                } else {
                    parent.rightNode = curr.rightNode;
                }
                curr.rightNode.parent = parent;
            }
        } else if(curr.rightNode == null){
            if(curr == root){
                root = curr.leftNode;
                root.parent = null;
            } else {
                if(isLeftChild){
                    parent.leftNode = curr.leftNode;
                } else {
                    parent.rightNode = curr.leftNode;
                }
                curr.leftNode.parent = parent;
            }
        } else {
            TreeNode successor = findPostNode(curr);
            successor.parent = null;
            if (curr == root) {
                root = successor;
                successor.leftNode = curr.leftNode;
                successor.rightNode = curr.rightNode;
            } else if (isLeftChild) {
                parent.leftNode = successor;
                successor.leftNode = curr.leftNode;
                successor.rightNode = curr.rightNode;

            } else {
                parent.rightNode = successor;
                successor.leftNode = curr.leftNode;
                successor.rightNode = curr.rightNode;
            }
            successor.leftNode = curr.leftNode;

        }
        return curr;

//        int compareState = curr.getKey().compareTo(target);
//        if(compareState > COMPARE_STATE){
//            curr = remove(curr.leftNode, target);
//        } else if( compareState < COMPARE_STATE){
//            curr = remove(curr.rightNode, target);
//        } else {
//            if(curr.leftNode == null){
//                curr.parent.rightNode = curr.rightNode;
//            } else if (curr.rightNode == null){
//                curr.parent.leftNode = curr.leftNode;
//            } else {
//                //TreeNode temp = curr;
//                TreeNode max = findMax(curr.getLeftNode());
//                max.parent = curr.parent;
//                curr.parent.leftNode = max;
//            }
//        }
//        return curr;
    }

    /**
     *          8
     *     4           12
     *   2   6     10     14
     *  1 3 5 7  9   11 13  15
     *
     * 无子节点，直接删    1
     * 只有1个子节点 接上  6 -> 7
     * 两个子节点， 用左节点最大一个，代替现在位置 4->3
     *            用右节点最小一个，代替现在位置 4->6
     * @Title
     * @Description
     * @Author zxy
     * @Param [curr, target]
     * @UpdateTime 2019/8/29 15:27
     * @Return com.zxy.learning.data.structure.binarytree.TreeNode
     * @throws
     */
    private void remove(TreeNode curr, TreeNode target){
        if(curr == null){

        }
        int compareState = curr.getKey().compareTo(target.getKey());
        boolean left = false;
        if(compareState > COMPARE_STATE){
            left = true;
            remove(curr.leftNode, target);
        } else if( compareState < COMPARE_STATE){
            left = false;
            remove(curr.rightNode, target);
        } else {
            if(curr.rightNode == null && curr.leftNode == null){
                if(left) {
                    curr.parent.leftNode = null;
                } else {
                    curr.parent.rightNode = null;
                }
            } else if(curr.leftNode == null){
                curr.parent.rightNode = curr.rightNode;
               // return curr.rightNode;
            } else if(curr.rightNode == null){
                curr.parent.leftNode = curr.leftNode;
               // return curr.leftNode;
            } else {
                TreeNode next = findPostNode(curr);

                //TreeNode temp = curr;
                TreeNode max = findMax(curr.getLeftNode());
                max.parent = curr.parent;
                curr.parent.leftNode = max;
            }
        }

    }

    private TreeNode findPreNode(TreeNode node){
        if(node == null){
            return null;
        }
        if(node.leftNode != null){
             return findMax(node.leftNode);
        }
        //node 是右子， 前驱节点为父节点
        //node 是左子， 前驱节点为node 的祖先节点
        TreeNode parent = node.parent;
        while(parent != null && parent.leftNode == node){
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }


    private TreeNode findPostNode(TreeNode node){
        if(node == null){
            return null;
        }
        if(node.rightNode != null){
            return findMin(node.rightNode);
        }
        //node 是右子，后驱节点为node 的祖先节点
        //node 是左子，后驱节点为父节点
        TreeNode parent = node.parent;
        while(parent != null && parent.rightNode == node){
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }


    @Override
    public TreeNode findMax() {
        TreeNode currNode = this.root;
        while(currNode.getRightNode() != null){
            currNode = currNode.getRightNode();
        }
        return currNode;
    }

    private TreeNode findMax(TreeNode root){
        TreeNode currNode = root;
        while(currNode.getRightNode() != null){
            currNode = currNode.getRightNode();
        }
        return currNode;
    }

    @Override
    public TreeNode finMin() {
        TreeNode currNode = this.root;
        while (currNode.getLeftNode() != null){
            currNode = currNode.getLeftNode();
        }
        return currNode;
    }

    private TreeNode findMin(TreeNode root){
        TreeNode currNode = root;
        while (currNode.getLeftNode() != null){
            currNode = currNode.getLeftNode();
        }
        return currNode;
    }

    public TreeNode findNodeByKey(Integer key){
        if(key == null){
            throw new IllegalArgumentException("key can not be null");
        }
        return findNode(key);
    }

    @Override
    public TreeNode findNode(TreeNode<Integer, Integer> node) {
        return null;
    }

    public TreeNode findNode(Integer target) {
        TreeNode currNode = this.root;
        while (currNode != null && currNode.key != target){
            int state = currNode.getKey().compareTo(target);
            if(state > 0){
                currNode = currNode.getLeftNode();
            } else if(state < 0) {
                currNode = currNode.getRightNode();
            } else if(state == 0){
                break;
            }
        }
        return currNode;
    }

    @Override
    public boolean contains(TreeNode data) throws Exception {
        return false;
    }

    @Override
    public void clear() {

    }

    public TreeNode<Integer, Integer> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<Integer, Integer> root) {
        this.root = root;
    }


    /**
     *          8
     *     4           12
     *   2   6     10     14
     *  1 3 5 7  9   11 13  15
     * @Title
     * @Description
     * @Author zxy
     * @Param [args]
     * @UpdateTime 2019/8/29 17:09
     * @Return void
     * @throws
     */
    public static void main(String[] args) {
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        binarySearchTree.insert(8,8);
        binarySearchTree.insert(4,4);
        binarySearchTree.insert(12,12);
        binarySearchTree.insert(2,2);
        binarySearchTree.insert(6,6);
        binarySearchTree.insert(10,10);
        binarySearchTree.insert(14,14);
        binarySearchTree.insert(1,1);
        binarySearchTree.insert(3,3);
        binarySearchTree.insert(5,5);
        binarySearchTree.insert(7,7);
        binarySearchTree.insert(9,9);
        binarySearchTree.insert(11,11);
        binarySearchTree.insert(13,13);
        binarySearchTree.insert(15,15);

        binarySearchTree.finMin();
        binarySearchTree.findMax();
        binarySearchTree.findNode(7);
        binarySearchTree.remove(7);
        binarySearchTree.remove(6);
        binarySearchTree.remove(2);
    }

}
