package com.zxy.learning.algorithm.leetcode;

import java.util.List;

/**
 * 快慢指针法
 * https://leetcode-cn.com/problems/reverse-nodes-in-k-group/
 */
public class LeetCode25 {

    // 1->2->3->4->5
    //0->1
    //0->2->1

    public static void main(String[] args) {
        ListNode result = reverseKGroup(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5,null))))),3);
   System.out.println("1");
    }
    public static ListNode reverseKGroup(ListNode head, int k) {
        if(k == 1){
            return head;
        }
        ListNode result = new ListNode(0);

        ListNode temp = result;
        ListNode reverseNodeHead = head;
        int i = 1;
        while(head != null && head.next != null){
            head = head.next;
            i++;
            if(i == k){
                i = 1;
                ListNode tempTail = head;
                if(head != null) {
                    head = head.next;
                }
                ListNode tempNode = reversePart(reverseNodeHead, tempTail);
                reverseNodeHead = head;
                temp.next = tempNode;
                while(temp.next != null){
                    temp = temp.next;
                }
            }
        }
        temp.next = reverseNodeHead;
        return result.next;
    }

    public static ListNode reversePart(ListNode head, ListNode tail){
        ListNode result = new ListNode(0);
        boolean continueReverse = true;
        while(continueReverse && head != null){
            if(head.equals(tail)){
                continueReverse = false;
            }
            ListNode node = head;
            head = head.next;
            node.next = result.next;
            result.next = node;

        }
        return result.next;
    }

   static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
        ListNode(int x, ListNode next) {
            val = x;
            this.next =next;
        }
    }
}
