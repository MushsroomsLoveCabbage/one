package com.zxy.learning.algorithm.leetcode;

import java.util.Arrays;

public class LeetCode416 {
    public static void main(String[] args) {
        //splitArray(new int[]{1,2,3,4,5,6,7,8});
        splitArray(new int[]{2,2,3,5});
    }

    public static boolean splitArray(int[] source){
        if(source == null || source.length == 1){
            return false;
        }

        int sum = Arrays.stream(source).sum();
        if(sum % 2 ==1){
            return false;
        }
        sum = sum >> 1;
        return dsf(source,sum, 0);
    }

    public static boolean dsf(int[] source, int target, int index){
        if(index == source.length){
            return false;
        }
        int tempVal = source[index];
        if(target == source[index]){
            return true;
        }
        if(target < tempVal){
            return false;
        }
        return dsf(source, target- tempVal, index+1) || dsf(source, target, index+1);
    }
}
