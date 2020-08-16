package com.zxy.learning.algorithm.hard;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName Tramfer.java
 * @Description
 * @createTime 2019年12月13日 08:44:00
 */
public class Tramfer {

    public static void main(String[] args) {

    }

    public void test(int distance, int cost, int sum, int capicity){
        int[] dpArray = new int[distance];
        for(int i =0; i <= distance; i++){
            int number =  capicity - 2 * distance;
            dpArray[i] = number >0 ? number :0;
        }

        int[] singleArray = new int[distance];
        for(int i =0; i <= distance; i++){
            int number = capicity - distance;
            singleArray[i] = number > 0 ? number :0;
        }
        int left = distance;
        int leftSum = 0;
        while(left >= 0){
            sum -= capicity;
            int realDistance = 0;

            if(sum > 0){
                leftSum += dpArray[distance];
            } else {
                leftSum += singleArray[distance];
            }
        }
    }
}