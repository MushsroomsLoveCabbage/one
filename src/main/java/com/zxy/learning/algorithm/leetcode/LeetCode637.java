package com.zxy.learning.algorithm.leetcode;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class LeetCode637 {
    static class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
        TreeNode(int x, TreeNode left, TreeNode right) { val = x;
     this.left = left;
     this.right = right;
     }
 }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(2147483647, new TreeNode(2147483647), new TreeNode(2147483647));
        new LeetCode637().averageOfLevels(treeNode);
    }
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            //整数相加需要考虑到数值越界的问题。
            long val = 0;
            for(int i = 0; i< size; i++){
                TreeNode temp = queue.poll();
                val += temp.val;
                if(temp.left != null){
                    queue.add(temp.left);
                }
                if(temp.right != null){
                    queue.add(temp.right);
                }
            }
            result.add(val / (double) size);
        }

        return result;
    }
}