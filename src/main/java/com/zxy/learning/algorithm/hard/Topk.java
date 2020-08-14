package com.zxy.learning.algorithm.basic;

import com.zxy.learning.data.structure.Node;

import java.util.*;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName Topk.java
 * @Description
 * @createTime 2020年08月14日 09:41:00
 */
public class Topk {

    public static void main(String[] args) {
        topKNumber(new int[]{1,2,3,4,5,6,7,8},3);
        removeZeroToLast(new int[]{0,1,0,2,0,3,0,4});
        List<Node> list = new ArrayList<>();
        list.add(new Node(1, new Node(3, new Node(5,null))));
        list.add(new Node(2, new Node(8, new Node(10,null))));
        list.add(new Node(0, new Node(7, new Node(9,null))));
        list.add(new Node(4, new Node(6, new Node(12,null))));
        Node node = mergeTreeNode(list);
    }

    public static List<Integer> topKNumber(int[] source, int k){

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1,o2)->o2-o1);
        for(int single:source){
            priorityQueue.add(single);
        }
        List<Integer> result = new ArrayList<>();
        for(int i =0; i< k; i++) {

            result.add(Optional.of(priorityQueue.poll()).get());
        }
        return result;
    }




    public static Node mergeTreeNode(List<Node> source){
        if(source.size() == 0){
            return null;
        }
        if(source.size() == 1){
            return source.get(0);
        }
        if(source.size() == 2){
            return mergeTwoNode(source.get(0), source.get(1));
        }
        int middle  = source.size() /2;
        Node left = mergeTreeNode(source.subList(0, middle ));
        Node right = mergeTreeNode(source.subList(middle, source.size()));
        return mergeTwoNode(left, right);

    }

    public static Node mergeTwoNode(Node part1, Node part2){
        Node result = new Node(0,null);
        Node temp = result;
        while (part1 != null && part2 != null){
            if(part1.getVal() > part2.getVal()){
                result.setNext(part2);
                part2 = part2.getNext();

            } else {
                result.setNext(part1);
                part1 = part1.getNext();
            }
            result = result.getNext();
        }
        if(part1 != null){
            result.setNext(part1);
        }

        if(part2 != null){
            result.setNext(part2);
        }

        return temp.getNext();
    }

    //{0,1,0,2,0,3,0,4}
    public static int[] removeZeroToLast(int[] source){
        int index = 0;
        int length = source.length-1;
        for(int i = 0; i< source.length; i++){
            if(source[i]==0){
                index ++;
            } else {
                if(index != 0){
                    source[i-index] = source[i];
                }
            }

        }
        for(int i = 0; i < index;i++){
            source[length-i] = 0;
        }
        return source;
    }

}
