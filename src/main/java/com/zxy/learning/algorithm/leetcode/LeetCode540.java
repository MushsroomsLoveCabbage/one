package com.zxy.learning.algorithm.leetcode;

import sun.nio.cs.ext.MS874;

import java.util.Stack;

/**
 * 查找有序排列数组中唯一出现一次的数(其他数字出现两次)
 * O(logn)
 * @author zxy
 * @version 1.0.0
 * @ClassName LeetCode540.java
 * @Description
 * @createTime 2020年09月24日 15:51:00
 */
public class LeetCode540 {

    public static void main(String[] args) {
        System.out.println(new LeetCode540().findDifferentNumber(new int[]{1,1,2,2,4,4,5,5,3}));
        System.out.println(new LeetCode540().findDifferentNumber(new int[]{1,1,2,2,3,4,4,5,5}));
        System.out.println(new LeetCode540().findDifferentNumber(new int[]{1,1,3,4,4,5,5}));
        System.out.println(new LeetCode540().findDifferentNumber(new int[]{1,1,2,2,3,5,5}));
        System.out.println(new LeetCode540().findDifferentNumber(new int[]{3,1,1,2,2,5,5}));

        System.out.println(new LeetCode540().findDifferentNumber2(new int[]{1,1,2,2,4,4,5,5,3}));
        System.out.println(new LeetCode540().findDifferentNumber2(new int[]{1,1,2,2,3,4,4,5,5}));
        System.out.println(new LeetCode540().findDifferentNumber2(new int[]{3,1,1,2,2,4,4,5,5}));
        System.out.println(new LeetCode540().findDifferentNumber2(new int[]{1,1,3,4,4,5,5}));
        System.out.println(new LeetCode540().findDifferentNumber2(new int[]{1,1,2,2,3,5,5}));
        System.out.println(new LeetCode540().findDifferentNumber2(new int[]{3,1,1,2,2,5,5}));
    }

    /**
     * [1,1,2,2,3,4,4,5,5]
     *
     * @Title
     * @Description
     * @Author zxy
     * @Param [source]
     * @UpdateTime 2020/9/24 15:52
     * @Return int
     * @throws
     */
    public int findDifferentNumber(int[] source){
        int left = 0;
        int right = source.length-1;
        while(left < right){
            int middle = (left + right)/2;
            int value = source[middle];
            int leftValue = source[middle-1];
            int rightValue = source[middle+1];
            boolean leftOdd = (middle - left) % 2 == 0;
            if(leftValue == value){
                if(leftOdd){
                    right = middle -2;
                } else {
                    left = middle + 1;
                }
            } else if(rightValue == value) {
                if(leftOdd){
                    left = middle + 2;
                } else {
                    right = middle - 1;
                }
            } else {
                return source[middle];
            }
        }
        return source[left];
    }

    //[1,1,2,2,3,4,4,5,5]
     /**
     * 二分取偶数位,（中位转化为偶数位),快速筛选
     * @Title 
     * @Description 
     * @Author zxy 
     * @Param 
     * @UpdateTime 2020/9/23 10:26 
     * @Return 
     * @throws 
     */
    public int findDifferentNumber2(int[] source){
        int left = 0;
        int right = source.length-1;
        while (left < right){
            int middle = (right + left)/2;
            if(middle % 2 == 1) {
                middle --;
            }
            if(source[middle] == source[middle+1]){
                left += 2;
            } else {
                right = middle;
            }
        }
        return source[left];
    }
}
