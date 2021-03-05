package com.zxy.learning.util.file;

public class Test {

    class TreeNode {
        TreeNode left;
        TreeNode right;
    }
    public int treeDepth(TreeNode treeNode){
        if(treeNode == null){
            return 0;
        }
        return Math.max(treeDepth(treeNode.left) +1, treeDepth(treeNode.right)+1);
    }
}
