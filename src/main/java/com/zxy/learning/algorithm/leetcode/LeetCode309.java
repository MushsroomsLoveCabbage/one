package com.zxy.learning.algorithm.leetcode;

public class LeetCode309 {
        public int maxProfit(int[] prices) {
            int n =prices.length;
            int[][] dp = new int[n][3];
            dp[0][0] = - prices[0];
            //0表示持有，1表示不持有，2表示过渡期
            for(int i = 1; i< prices.length; i++){
                //前一天不持有， 前一天过渡期
                dp[i][0] = Math.max(dp[i-1][0], dp[i-1][2] - prices[i]);
                dp[i][1] = dp[i-1][0] + prices[i];
                dp[i][2] = Math.max(dp[i-1][1],dp[i-1][2]);
            }

            return Math.max(dp[n-1][1], dp[-1][2]);
        }

}
