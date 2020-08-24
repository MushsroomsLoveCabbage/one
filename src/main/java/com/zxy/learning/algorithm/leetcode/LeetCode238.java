package com.zxy.learning.algorithm.leetcode;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName LeetCode238.java
 * @Description
 * @createTime 2020年08月27日 08:34:00
 */
public class LeetCode238 {

    public static void main(String[] args) {
        getSumWithoutDiv(new int[]{1,2,3,4});
    }
    public static int[] getSumWithoutDiv(int[] source){
        int length = source.length;
        int[] result = new int[length];
        result[0] = 1;
        for(int i = 1; i< length; i++){
            result[i] = source[i-1] * result[i-1];
        }
        int right = 1;
        for(int end = length-1; end >= 0; end--){
            result[end] = result[end] * right;
            right = right * source[end];

        }

        return result;
    }

}
