package com.zxy.learning.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/generate-parentheses/
 */
public class LeetCode22 {

    public static void main(String[] args) {
        //generateParenthesis(3);
        
        new LeetCode22().dfsSimple(3);
        System.out.println("1");
    }
    List<String> result = new ArrayList<>();

    Set<String> resultSet = new HashSet<>();

    public List<String> generateParenthesis(int n) {
        dfs(new StringBuilder(), 0,0, n);
        return result;
    }



    //()
    //()(), (())
    //()()()
    public void dfs(StringBuilder sb, int left, int right, int n){
        if(left > n || right >n || left < right){
            return;
        }
        if(left == right && right == n){
            result.add(sb.toString());
            return;
        }
        dfs(sb.append('('),left+1,right,n);
        sb.deleteCharAt(sb.length()-1);
        dfs(sb.append(')'), left, right+1,n);
        sb.deleteCharAt(sb.length()-1);
    }

    //()
    //()(), (())
    //()()(), (())(), ()(()),     ()(()),(()()),((()))
    //在新加一层的()时，在开头 以及每个 ( 右边增加一个()即可;
    public void dfsSimple(int n){
        if(n == 0){
            result.addAll(resultSet);
            return;
        }
        Set<String> tempSet = new HashSet<>();
        for(String single : resultSet){
            StringBuilder temp = new StringBuilder();
            temp.append("()");
            temp.append(single);
            tempSet.add(temp.toString());
            temp = new StringBuilder();
            int index = -1;
            int formIndex = -1;
            while((index = single.indexOf('(', formIndex)) >= 0){
                temp.append(single.substring(0,index +1)).append("()").append(single.substring(index+1, single.length()));
                formIndex = index + 1;
                tempSet.add(temp.toString());
                temp = new StringBuilder();
            }
        }
        resultSet.clear();
        resultSet.addAll(tempSet);
        dfsSimple(n-1);
    }
}

