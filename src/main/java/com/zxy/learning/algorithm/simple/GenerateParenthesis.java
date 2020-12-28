package com.zxy.learning.algorithm.simple;

import java.util.*;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName generateParenthesis.java
 * @Description
 * @createTime 2019年08月21日 14:49:00
 */
public class GenerateParenthesis {

    public static void main(String[] args) {
        /*generateParenthesisOther(5).forEach(temp ->{
            System.out.println(temp);
        });*/
        test();
//        generateParenthesisAnOther(3).forEach(temp ->{
//            System.out.println(temp);
//        });
    }

    public static void test(){
        new ArrayList<>();
        Vector<Integer> vector = new Vector<>();
        Stack<Integer> stack = new Stack<>();
        vector.add(3);
        vector.add(1);
        vector.add(5);
        vector.add(7);
        vector.add(9);

        stack.add(2);
        stack.add(4);
        stack.add(6);
        stack.add(8);

        vector.forEach(temp->{
            System.out.println(temp);
        });

        Queue<Integer> queue = new PriorityQueue<>();
        queue.addAll(vector);


        System.out.println(stack.pop());
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(queue.poll());
        //stack.pop();
//        stack.peek();
//        stack.peek();
//        stack.forEach(temp->{
//            System.out.println(temp);
//        });
    }


    private static List<String> generateParenthesis(int n){
        List<String> result = new ArrayList<>();
        help(n, n, "", result);
        return result;
    }

    private static void help(int left, int right, String out, List<String> result){
        //left > right 说明有 )( 这种情况出现，
        if(left < 0 || right < 0 || left > right){
            return;
        }
        if(left == 0 && right == 0){
            result.add(out);
            return;
        }
        help(left -1, right, out + "(", result);
        help(left, right-1, out + ")", result);
    }



    public static List<String> generateParenthesisAnOther(int n){
        Set<String> res = new HashSet<>();
        if(n == 0){
            res.add("");
            return new ArrayList<>(res);
        } else {
            List<String> pre = generateParenthesisAnOther(n - 1);
           for(String temp: pre){
                for(int i = 0; i< temp.length(); i++){
                    if(temp.charAt(i) == ')' ){
                        temp = temp.substring(0, i) +"()"+temp.substring(i, temp.length());
                        res.add(temp);
                        temp = temp.substring(0, i) + temp.substring(i+2, temp.length());
                    }
                }
                 res.add(temp + "()");
            }
        }
        return new ArrayList(res);
    }

    public static List<String> generateParenthesisOther(int n) {

        Set<String> res = new HashSet<>();
        if (n == 0) {
            res.add("");
        } else {
            List<String> pre = generateParenthesis(n - 1);
            for (String str : pre) {
                for (int i = 0; i < str.length(); ++i) {
                    if (str.charAt(i) == '(') {
                        str = str.substring(0, i + 1) + "()" + str.substring(i + 1, str.length());
                        res.add(str);
                        str = str.substring(0, i + 1) +  str.substring(i + 3, str.length());
                    }
                }
                res.add("()" + str);
            }
        }
        return new ArrayList(res);
    }
}
