package com.zxy.learning.algorithm.leetcode;

/**
 * https://leetcode-cn.com/problems/last-substring-in-lexicographical-order/
 *
 */
public class LeetCode1163 {
    public static void main(String[] args) {
        new LeetCode1163().lastSubstring("cacacb");
        new LeetCode1163().lastSubstring("abab");
    }
    //一定是后缀子串
    public String lastSubstring(String s) {
        int index = 0;
        char[] sArray = s.toCharArray();
        char tempMax = 'a' - 1;
        for (int i = s.length()-1; i>=0; i--){
            if(sArray[i] > tempMax){
                index = i;
                tempMax = sArray[i];
            } else if(sArray[i] == tempMax) {
                //减少遍历 下一个字符既然一样，可以下一个再去比较
                if(i-1>= 0 && sArray[i] == sArray[i-1])
                    continue;
                int tempMaxIndex = index + 1 ;
                int tempIndex = i + 1;
                for(;tempMaxIndex <= sArray.length; tempMaxIndex++){
                    if(tempMaxIndex == sArray.length || sArray[tempIndex] > sArray[tempMaxIndex]){
                        index = i;
                        break;
                    } else if(sArray[tempIndex] < sArray[tempMaxIndex]){
                        break;
                    }
                    tempIndex++;
                }
            }
        }
        return s.substring(index);
    }
}
