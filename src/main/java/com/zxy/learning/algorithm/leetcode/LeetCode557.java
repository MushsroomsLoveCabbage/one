package com.zxy.learning.algorithm.leetcode;

/**
 * https://leetcode-cn.com/problems/reverse-words-in-a-string-iii/
 */
public class LeetCode557 {
    public static void main(String[] args) {
        System.out.println(new LeetCode557().reverseWords("Let's go to sleep!"));

        System.out.println(new LeetCode557().reverseWords(  "Let's take LeetCode contest"));
    }
    //在原数组上做交换
    //空间O(1)
    //时间O(n)
    public String reverseWords(String s) {
        if(s == null || s.length() == 0){
            return s;
        }
        int start = 0;
        int end = -1;
        char[] sourceArray = s.toCharArray();
        int length = sourceArray.length;
        for(char single : sourceArray){
              if(single == ' ' || end == length-2) {
                  int left = start;
                  int right = end;
                  if(end== length-2){
                      right++;
                  }
                  while (left < right) {
                      char tempChar = sourceArray[right];
                      sourceArray[right] = sourceArray[left];
                      sourceArray[left] = tempChar;
                      left++;
                      right--;
                  }
                  start = end + 2;
              }
              end++;
        }
        return new String(sourceArray);
    }
}
