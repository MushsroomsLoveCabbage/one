package com.zxy.learning.algorithm.leetcode;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前缀和
 *
 * ROAD1 = A-B-C-D-E-F
 * ROAD2 = A
 * ROAD1 - ROAD2 = B-C-D-E-F
 * @author zxy
 * @version 1.0.0
 * @ClassName LeetCode437.java
 * @Description
 * @createTime 2020年10月01日 12:24:00
 */
public class LeetCode437 {

    public static int TARGET_VAL;

    public static List<Node> nodeList = new ArrayList<>();

    public static List<List<Node>> result = new ArrayList<>();

    static Map<Integer, Integer> recordMap = new HashMap<>();


    public int matchPrefix(Node node, int val){
        int resultVal = 0;

        return resultVal;
    }

    public static void main(String[] args) {
        Integer[] source = new Integer[]{10,5,-3,3,2,null,11,3,-2,null,1};
        Node sourceNode = castArrayToNode(source);
        getMatchListNode(sourceNode, 8);

    }

    public static Node castArrayToNode(Integer[] source){
        Node result =new Node();
        nodeCast(result, source,0);
        return result;
    }

    public static void nodeCast(Node node, Integer[] source, int i){
        if(i >= source.length ){
            return;
        }
        if(source[i] == null){
            node = null;
            return;
        }
        node.val = source[i];
        if(2*i+1 < source.length){
            if(source[2*i+1] != null){
                node.left = new Node();
                nodeCast(node.left , source,2*i+1);
            }
        }
        if(2*i+2 < source.length){
            if(source[2*i+2] != null){
                node.right = new Node();
                nodeCast(node.right , source,2*i+2);
            }
        }
    }

    public static List<List<Node>> getMatchListNode(Node source, int target){
        TARGET_VAL = target;
        match(source, target);
        return result;
    }

    static void match(Node source, int target){
        nodeList.add(source);
        if(source.val == target){
            result.add( new ArrayList<>(nodeList));
            return;
        }

        int tempVal = target - source.val;

        if(source.left != null){
             match(source.left, tempVal);
             nodeList.remove(source.left);
        }

        if(source.right != null) {
            match(source.right, tempVal);
            nodeList.remove(source.right);
        }

        nodeList.clear();
        if(source.left != null){
            match(source.left, TARGET_VAL);
            nodeList.remove(source.left);
        }
        if(source.right != null) {
            match(source.right, TARGET_VAL);
        }
    }

    static class Node{

        public int val;

        public Node left;

        public Node right;

        public Node(int val, Node left, Node right){
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public Node(){

        }
    }
}
