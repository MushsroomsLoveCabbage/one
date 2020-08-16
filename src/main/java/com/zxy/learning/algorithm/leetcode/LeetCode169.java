package com.zxy.learning.algorithm.simple;

/**
 * 寻找存在最多的数
 * 摩尔投票算法
 * @author zxy
 * @version 1.0.0
 * @ClassName Question169.java
 * @Description
 * @createTime 2020年08月12日 21:39:00
 */
public class LeetCode169 {

    public static int findMaxNumber(int[] source){
        int count = 1;
        int number = source[0];
        for(int i = 1; i<source.length; i++){
            if(number == source[i]){
                count ++;
            } else {
                count--;
                if(count == 0){
                    number = source[i];
                    count++;
                }
            }
        }
        return number;
    }

    public static void main(String[] args) {
        findMaxNumber(new int[]{1,2,3,3,3,2,1});
    }

}
