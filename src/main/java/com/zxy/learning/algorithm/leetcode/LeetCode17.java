package com.zxy.learning.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 */
public class LeetCode17 {
    public static void main(String[] args) {
        letterCombinations("23");
    }

    public static List<String> letterCombinations(String digits) {
        List<String> list =  new ArrayList<>();
        if(digits.length() ==0) {
            return list;
        }
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        char[] sourceArray = digits.toCharArray();
        dfs(phoneMap, 0, digits, list, new StringBuilder());
        return list;
    }

    public static void dfs(Map<Character, String> phoneMap, int index, String digits, List<String> list, StringBuilder stringBuilder){
        if(index == digits.length()){
            list.add(stringBuilder.toString());
            return;
        }
        String temp = phoneMap.get( digits.charAt(index));
        if(temp != null){
            for(char single : temp.toCharArray()){
                dfs(phoneMap, index +1, digits, list, stringBuilder.append(single));
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            }
        }
    }
}
