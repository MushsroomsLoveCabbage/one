package com.zxy.learning.algorithm.leetcode;

/**
 * {
 *     { },
 *     { },
 *     { },
 * }
 * DP的思想去处理,[i][j]为右下角的一个黑色方块，
 * 其子块 [i-1][j-1] [i-1][j] [i][j-1] 肯定也是黑色方块且  [i-1][j],[i][j-1] 大小 >= [i-1][j-1]
 * @author zxy
 * @version 1.0.0
 * @ClassName LeetCode66.java
 * @Description
 * @createTime 2020年10月03日 18:45:00
 */
public class LeetCode17dot23 {

    public static void main(String[] args) {
        maxBlackBlock(new int[][]{{1,1,0,0,0},
                                  {1,1,0,0,0},
                                  {1,0,0,0,0},
                                  {1,1,1,0,0},
                                  {1,0,1,0,0}});
    }

    public static int[] maxBlackBlock(int[][] source){
        int[] result = new int[3];
        int[][] dp = new int[source[0].length][source.length];
        for(int i = 0; i<source[0].length; i++){
            dp[i][0] = source[i][0] == 0 ? 1 : 0;
        }
        for(int i = 0; i<source.length; i++){
            dp[0][i] = source[0][i] == 0 ? 1 : 0;
        }
        for(int i = 1; i< source.length; i++){
            for(int j = 1; j < source[0].length; j++){
                if(source[i][j] == 0){
                    if(dp[i-1][j-1] != 0 && dp[i-1][j] >= dp[i-1][j-1] && dp[i][j-1] >= dp[i-1][j-1]){
                        dp[i][j] = dp[i-1][j-1] + 1;
                        if(dp[i][j] > result[2]){
                            result[0] = i;
                            result[1] = j;
                            result[2] = dp[i][j];
                        }
                    } else {
                        dp[i][j] = 1;
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return result;
    }
}
