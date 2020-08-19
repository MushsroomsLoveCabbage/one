package com.zxy.learning.algorithm.leetcode;

import org.omg.CORBA.MARSHAL;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName LeetCode17dot18.java
 * @Description
 * @createTime 2020年08月17日 13:37:00
 */
public class LeetCode17dot18 {
    public static void main(String[] args) {

    }

    //dp[i][j]
    public int[] minSubArray(int[] source, int[] target){
        HashMap<Integer, Integer> windowsMap = new HashMap<>();
        HashMap<Integer, Integer> targetMap = new HashMap<>();
        int match = 0;
        int left = 0, right = 0;
        int start = 0, minLength = Integer.MAX_VALUE;
        for(int single : target){
            targetMap.put(single, targetMap.getOrDefault(single,0) +1);
        }
        while(right < source.length){
            Integer val = source[right];
            if(targetMap.containsKey(val)){
                windowsMap.put(val, windowsMap.getOrDefault(val, 0) + 1);
                if(windowsMap.get(val).equals(targetMap.get(val))){
                    match++;
                }
            }
            right++;
            while(match == targetMap.size()){
                if(right -left < minLength){
                    start = left;
                    minLength = right-left;
                }
                Integer leftVal = source[left];
                if(targetMap.containsKey(leftVal)){
                    windowsMap.put(leftVal, windowsMap.getOrDefault(leftVal, 0)-1);
                    if(windowsMap.get(leftVal) < targetMap.get(leftVal)){
                        match--;
                    }
                }
                left++;
            }
        }
        return minLength == Integer.MAX_VALUE ? new int[0] : new int[]{start, minLength};
    }

}
