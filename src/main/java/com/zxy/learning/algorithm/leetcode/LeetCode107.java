package com.zxy.learning.algorithm.leetcode;

import com.zxy.learning.data.structure.binarytree.Tree;

import java.util.*;

public class LeetCode107 {
}

/**
 * Definition for a binary tree node.
 *
 */
class Solution {

    //正向层序遍历，然后反转list
    //
    Map<Integer, List<Integer>> levelMap = new HashMap<>();


    public List<List<Integer>> levelOrderBottomWithQueue(TreeNode root) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        if(root == null){
            return result;
        }
        Queue<TreeNode> queue = new LinkedList();
        queue.add(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> levelList = new LinkedList<>();
            for(int i = 0; i< size; i++){
                TreeNode single = queue.poll();
                levelList.add(single.val);
                if(single.left != null){
                    queue.add(single.left);
                }
                if(single.right != null){
                    queue.add(single.right);
                }
            }
            result.addFirst(levelList);
        }
        return result;
    }
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        //Stack<TreeNode> stack = new Stack<>();
        List<List<Integer>> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        interator(root,1);
        for(int i = levelMap.size(); i>0;i--){
            result.add(levelMap.get(i));
        }
        return result;
    }

    public void interator(TreeNode node, int level){
        if(node.left == null && node.right == null){
            List tempList = levelMap.getOrDefault(level, new ArrayList<>());
            tempList.add(node.val);
            levelMap.put(level, tempList);
            return;
        }
        if(node.left != null){
            interator(node.left, level + 1);
        }
        if(node.right != null){
            interator(node.right, level + 1);
        }
    }
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}