package com.zxy.learning.algorithm.middle;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName LongestPalindromeString.java
 * @Description
 * @createTime 2019年08月25日 09:25:00
 */
public class LongestPalindromeString {

    /**
     * S[j...i] 子字符串
     * dp[j][i] (i = j => true)
     * dp[j][i] (i-j =1 => s[i] == s[j])
     * dp[j][i] (i-j > 1 => s[i] == s[j] && d[j+1][i-1])
     *
     * @Title
     * @Description 
     * @Author zxy 
     * @Param [s]
     * @UpdateTime 2019/8/25 9:35 
     * @Return java.lang.String
     * @throws 
     */
    public static String longestPalindrome(String s){
        int size = s.length();
        boolean[][] dp = new boolean[size][size];
        int start = 0, end = 0, length = 0;
        for(int i = 0; i < size; i++){
            for(int j = 0; j <= i; j++){
                if(i - j < 2) {
                    dp[j][i] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[j][i] = (s.charAt(i) == s.charAt(j)) && dp[j+1][i-1];
                }
                if(dp[j][i] && i - j + 1 > length ){
                    length = i - j + 1;
                    start = j;
                    end = i;
                }
            }
        }
        return s.substring(start, end + 1);
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("abccbaqwe"));
    }
}
