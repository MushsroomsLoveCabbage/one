package com.zxy.learning.algorithm.leetcode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/clone-graph/
 * 核心 回溯法去遍历，
 * 注意点 是要去记录已经clone过的
 */
public class LeetCode133 {

    public static void main(String[] args) {
        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3);
        Node four = new Node(4);
        one.neighbors.add(two);
        one.neighbors.add(four);
        two.neighbors.add(one);
        two.neighbors.add(three);
        three.neighbors.add(two);
        three.neighbors.add(four);
        four.neighbors.add(one);
        four.neighbors.add(three);
        cloneGraph(one);
        cloneGraph(null);
    }
    public static  Node cloneGraph(Node node) {
        if(node == null){
            return node;
        }
        if(visistedMap.containsKey(node)){
            return visistedMap.get(node);
        }
        Node cloneNode = new Node(node.val, new ArrayList<>());
        visistedMap.put(node, cloneNode);
        for(Node single : node.neighbors){
            cloneNode.neighbors.add(cloneGraph(single));
        }

        return cloneNode;
    }

    static Map<Node, Node> visistedMap = new HashMap<>();


}

class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}

