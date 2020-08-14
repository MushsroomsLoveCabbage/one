package com.zxy.learning.data.structure;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName Node.java
 * @Description
 * @createTime 2020年08月24日 00:36:00
 */
public class Node {

    private int val;

    private Node next;

    public Node(int val, Node next){
        this.val = val;
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
