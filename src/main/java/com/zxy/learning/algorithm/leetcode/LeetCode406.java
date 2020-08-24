package com.zxy.learning.algorithm.leetcode;

import java.util.*;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName LeetCode406.java
 * @Description
 * @createTime 2020年08月26日 23:03:00
 */
public class LeetCode406 {

    public static void main(String[] args) {
        sortArray(new int[][]{{7,0},{4,4},{7,1},{5,0},{6,1},{5,2}});
    }

    //
    // new Comparator<int[]>() {
    //            @Override
    //            public int compare(int[] a, int[] b) {
    //                return a[0] == b[0] ? a[1] - b[1] : a[0]-b[0];
    //            }
    //        }
    //[H,K]
    //假设一样高的情况下，直接按K值排序
    //不一样高的，低的人对高的人不可见，即低的不影响高的排序，那先排列高的,
    //从高到低排，相同高低按K从小到大排
    public static int[][] sortArray(int[][] source){
        Arrays.sort(source,(a, b) -> {return  a[0] == b[0] ?  a[1] - b[1] : b[0]- a[0];} );
        List<int[]> result = new ArrayList<>(20);
        for(int[] single : source){
            result.add(single[1], single);
        }
        return result.toArray(new int[result.size()][2]);
    }

}
