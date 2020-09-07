package com.zxy.learning.algorithm.leetcode;

import java.util.Stack;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName LeetCode739.java
 * @Description
 * @createTime 2020年09月04日 19:04:00
 */
public class LeetCode739 {

    public static void main(String[] args) {
        new LeetCode739().slove(new int[]{73,74,75,71,69,72,76,73});
        new LeetCode739().sloveWithStack(new int[]{73,74,75,71,69,72,76,73});
    }

    /**
     * 栈来存放等待寻找更大值的元素
     * 遍历堆栈如果当前元素大于堆中元素，则移除栈顶部。
     * 如果小于，则结束遍历
     * 把当前元素加入堆栈
     */
    public int[] sloveWithStack(int[] source){
        int[] result = new int[source.length];
        Stack<Integer> stack = new Stack<>();
        stack.add(0);
        for(int i = 1; i < source.length; i++){
            int tempVal = source[i];
            while(!stack.isEmpty()){
                if(tempVal > source[stack.peek()]){
                    int index = stack.pop();
                    result[index] = i - index;

                } else {
                    break;
                }
            }
            stack.push(i);
        }
        return result;
    }

    //
    public int[] slove(int[] source){
        int[] result = new int[source.length];
        int lastValue = source[0];
        int lastGreaterIndex = 0;
        for(int i = 0; i < source.length-1; i++){
            int tempVal = source[i];
            int j = i+1;
            //减少不必要的遍历,既然大于上个元素，即下个比这个大的元素肯定在上个元素之后
            if(tempVal >= lastValue){
                j = lastGreaterIndex;
            }
            for(; j < source.length; j++){
                if(source[j] > tempVal){
                    result[i] = j - i;
                    lastGreaterIndex = j;
                    break;
                }
            }
        }
        return result;
    }
}
