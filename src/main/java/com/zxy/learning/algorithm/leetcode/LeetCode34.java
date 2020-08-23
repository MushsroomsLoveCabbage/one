package com.zxy.learning.algorithm.leetcode;

/**
 *
 * https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/comments/
 */
public class LeetCode34 {
    public static void main(String[] args) {
        findNumber(new int[]{1,2,3,4,5,5,5,5,6},5);
        findIndexBy(new int[]{1,2,3,4,5,5,5,5,6},5);
    }

    //二分查找
    public static int[] findNumber(int[] source, int target){
        int[] result = new int[]{-1, -1};
        int start = 0;
        int end = source.length - 1;
        //左节点
        while(start < end){
            int middle  = (start + end) >> 1;
            if(source[middle] >= target){
                end = middle;
            } else {
                start = middle+1;
            }
        }
        if(source[start] != target){
            return result;
        }
        result[0] = start;
        //右节点
        end = source.length;
        while(start < end){
            int middle  = (start + end) >> 1;
            if(source[middle] <= target){
                start = middle + 1;
            } else {
                end = middle;
            }
        }
        result[1] = end - 1;
        return result;
    }

    public static int[] findIndexBy(int[] source, int target){

        int start = -1;
        int end = -1;
        for(int i = 0; i< source.length; i++){
            if(source[i] == target){
                if(start == -1){
                    start = i;
                }
                end = i;
            }
        }
        return new int[]{start, end};
    }
}
