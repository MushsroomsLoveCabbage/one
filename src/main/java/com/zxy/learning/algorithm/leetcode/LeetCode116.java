package com.zxy.learning.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node/
 */
public class LeetCode116 {

    public static void main(String[] args) {
      
        LeetCode116.Node left1 = new LeetCode116.Node(2, new LeetCode116.Node(4, null, null), new LeetCode116.Node(5, null, null));
        LeetCode116.Node right1 = new LeetCode116.Node(3, new LeetCode116.Node(6, null, null), new LeetCode116.Node(7, null, null));
        LeetCode116.Node root1 = new LeetCode116.Node(1, left1, right1);
        new LeetCode116().dfs(root1,null);
        new LeetCode116().connect(root1);

    }

    Map<Integer, Node> map = new HashMap<>();

    public Node connect(Node root) {
        dfs(root, 1);
        return root;
    }

    public void dfs(Node node, Node next){
        if(node == null){
            return;
        }
        node.next = next;
        dfs(node.left, node.right);
        dfs(node.right, node.next == null ? null : node.next.left);
    }
    public void dfs(Node node, int level){
        if(node == null){
            return;
        }
        Node next = map.get(level);
        if(next != null) {
            node.next = next;
        }
        map.put(level, node);
        dfs(node.right, level+1);
        dfs(node.left, level+1);
    }

    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
}
