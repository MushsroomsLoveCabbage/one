package com.zxy.learning.algorithm.leetcode;

/**
 * https://leetcode-cn.com/problems/divide-two-integers
 */
public class LeetCode29 {
    public static void main(String[] args) {
       System.out.println(new LeetCode29().divide(10, -3));

    }
    public int divide(int dividend, int divisor) {
        if(dividend == 0){
            return 0;
        }
        boolean flag = ((dividend ^ divisor) >=0);
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        int temp = 0;
        int result = 0;
        while(temp < dividend){
            temp += divisor;
            result++;
        }
        result--;
        return flag ? result : -result;
    }
}
