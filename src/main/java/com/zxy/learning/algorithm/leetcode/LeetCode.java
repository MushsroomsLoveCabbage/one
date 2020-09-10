package com.zxy.learning.algorithm.leetcode;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName LeetCode.java
 * @Description
 * @createTime 2020年09月06日 17:10:00
 */
public class LeetCode {

    public int findDistinctNumber(int[] source){
        if(source.length <= 1){
            return source[0];
        }
        int result = 0;
        for(int single:source){
            result ^= single;
        }
        return result;
    }

    public int[] findTwoDistinctNumber(int[] source){
        if(source.length == 2){
            return source;
        }
        int sum = 0;
        for(int single:source){
            sum ^= single;
        }

        int index = 0;
        while((sum & 1) == 0 && index < 32){
            index++;
            sum = sum >>1;
        }

        int num1 = 0, num2= 0;
        for(int single: source){
            if((single & 1<< index) == 1) {
                num1 ^= single;
            } else {
                num2 ^= single;
            }
        }
        return new int[]{num1, num2};
    }
}
