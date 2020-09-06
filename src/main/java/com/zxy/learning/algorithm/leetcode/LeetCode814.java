package com.zxy.learning.algorithm.leetcode;

import com.zxy.learning.data.structure.binarytree.Tree;

/**
 * 给定二叉树根结点 root ，此外树的每个结点的值要么是 0，要么是 1。
 * <p>
 * 返回移除了所有不包含 1 的子树的原二叉树。
 * <p>
 * ( 节点 X 的子树为 X 本身，以及所有 X 的后代。)
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-pruning
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LeetCode814 {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1, null, new TreeNode(0, new TreeNode(0, null, null), new TreeNode(1, null, null)));
        new LeetCode814().pruneTree(root);
        TreeNode left1 = new TreeNode(0, new TreeNode(0, null, null), new TreeNode(0, null, null));
        TreeNode right1 = new TreeNode(1, new TreeNode(0, null, null), new TreeNode(1, null, null));
        TreeNode root1 = new TreeNode(1, left1, right1);
        new LeetCode814().pruneTree(root1);
        TreeNode single = new TreeNode(0, null, null);
        TreeNode left2 = new TreeNode(1, new TreeNode(1, single, null), new TreeNode(1, null, null));
        TreeNode right2 = new TreeNode(0, new TreeNode(0, null, null), new TreeNode(1, null, null));
        TreeNode root2 = new TreeNode(1, left2, right2);
        new LeetCode814().pruneTree(root2);

    }
    //优化写法
    public TreeNode pruneTree1(TreeNode root){
        if (root == null) {
            return null;
        }
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        if( root.left  == null && root.right == null && root.val == 0){
            return null;
        }
        return root;
    }
    public TreeNode pruneTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode result = new TreeNode();
        result.left = root;
        dfs(root, result, true);
        return result.left;
    }

    public void dfs(TreeNode node, TreeNode parent, boolean state) {
//        if(node.left == null && node.right == null){
//            if(node.val == 0) {
//                if (state) {
//                    parent.left = null;
//                } else {
//                    parent.right = null;
//                }
//            }
//            return;
//        }
        if (node.left != null) {
            dfs(node.left, node, true);
        }
        if (node.right != null) {
            dfs(node.right, node, false);
        }
        //放在后面防止子节点都变null,导致本节点需要也边null;
        if (node.left == null && node.right == null) {
            if (node.val == 0) {
                if (state) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
            return;
        }
    }
}
