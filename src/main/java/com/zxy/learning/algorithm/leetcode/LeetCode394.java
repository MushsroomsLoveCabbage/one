package com.zxy.learning.algorithm.leetcode;

import java.util.Stack;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName LeetCode394.java
 * @Description
 * @createTime 2020年09月23日 10:18:00
 */
public class LeetCode394 {

    public static void main(String[] args) {
        System.out.println(new LeetCode394().decodeString("3[a]2[bc]"));
        System.out.println(new LeetCode394().decodeString("3[a2[c]]"));
        System.out.println(new LeetCode394().decodeString("2[abc]3[cd]ef"));
    }
    /**
     *  s = "3[a]2[bc]", 返回 "aaabcbc".
     * 	s = "3[a2[c]]", 返回 "accaccacc".
     * 	s = "2[abc]3[cd]ef", 返回 "abcabccdcdcdef".
     *
     * 由内往外构造,
     * 从左往右遍历过程中，采用堆结构（先入后出）
     * 数字记录下倍数 注意要('1' - '0')
     * 碰到 '[' 存放倍数和当前字符串
     * 碰到 ']' 弹出倍数和字符串  （弹出字符 + 倍数拼接当前字符串）拼接
     * 字符 拼接字符串
     * ->由内往外
     * @Title
     * @Description
     * @Author zxy
     * @Param [soruce]
     * @UpdateTime 2020/9/22 17:46
     * @Return java.lang.String
     * @throws
     */
    public String decodeString(String source){
        StringBuilder sb = new StringBuilder();
        Stack<Integer> multiStack = new Stack<>();
        Stack<String> resStack = new Stack<>();
        char[] sourceArray = source.toCharArray();
        int multi = 0;
        for(int i = 0; i < source.length(); i++){
            char temp = sourceArray[i];
            if(temp == '['){
                multiStack.push(multi);
                multi = 0;
                resStack.push(sb.toString());
                sb = new StringBuilder();
            } else if(temp == ']'){
                StringBuilder tempSb = new StringBuilder();
                int tempMulti = multiStack.pop();
                for(int j = 0; j< tempMulti; j++){
                    tempSb.append(sb);
                }
                sb = new StringBuilder(resStack.pop() + tempSb);
            } else if(temp >= '0' && temp <= '9'){
                multi = multi * 10 + temp - '0';
            } else {
                sb.append(temp);
            }
        }
        return sb.toString();
    }
}
