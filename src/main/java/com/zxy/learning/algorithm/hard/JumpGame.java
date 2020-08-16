package com.zxy.learning.algorithm.hard;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName JumpGame.java
 * @Description
 * @createTime 2019年09月09日 22:25:00
 */
public class JumpGame {

    public static void main(String[] args) {

        int[] array = new int[]{1,2,3,4,5,6,7,8,9,10};
    }

    private boolean jumpGame(int[] source) {
        int[] dp = new int[source.length];
        dp[0] = 0;
        for(int i = 1; i < source.length; i++){
            if(dp[i - 1] + source[i - 1] >= source.length) {
                return true;
            } else {
                dp[i] = Math.max(dp[i - 1], source[i - 1])- 1;
                if(dp[i] < 0){
                    return false;
                }
            }
        }
        return false;
    }
}
