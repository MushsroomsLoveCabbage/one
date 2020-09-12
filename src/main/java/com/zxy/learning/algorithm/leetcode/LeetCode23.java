package com.zxy.learning.algorithm.leetcode;

public class LeetCode23 {

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length == 1){
            return lists[0];
        }
        return merge(lists, 0, lists.length -1);
    }

    ListNode merge(ListNode[] lists, int l, int r) {
        if (l == r) return lists[l];
        if (l > r) return null;
        int mid = (l + r) >> 1;
        return mergeTwoLists(merge(lists, l, mid), merge(lists, mid + 1, r));
    }
    public ListNode mergeTwoLists(ListNode left, ListNode right) {
        ListNode result = new ListNode();
        ListNode temp = result;
        while (left != null && right != null) {
            if (left.val > right.val) {
                temp.next = left;
                left = left.next;
            } else {
                temp.next = right;
                right = right.next;

            }
            temp = temp.next;
        }
        if (right != null) {
            temp.next = right;
        }
        if (left != null) {
            temp.next = left;
        }
        return result.next;
    }
}
