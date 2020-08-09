package com.zxy.learning.algorithm.simple;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Reback {
    public static void main(String[] args) {
        slove(new int[]{1,2,3},0);
        System.out.println("1");
    }

    static List<List<Integer>> result = new ArrayList<>();

    static List<Integer> single =  new ArrayList<>();

    public static void slove(int[] source, int index){
        if(index >= source.length){
            List<Integer> temp = new ArrayList<Integer>(single);
            result.add(temp);
            return;
        }
        single.add(source[index]);
        slove(source, index + 1);
        single.remove(single.size() -1);
        slove(source, index + 1);
    }
}
