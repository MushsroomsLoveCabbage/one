package com.zxy.learning.algorithm.simple;

import java.util.Arrays;

/**
 * 除了某个元素完其他元素都出现两次
 * 核心就是 ^
 * a^a = 0
 * 0^a = a
 * @author zxy
 * @version 1.0.0
 * @ClassName Question136.java
 * @Description
 * @createTime 2020年08月12日 21:25:00
 */
public class Question136 {

    //2. map 集合存

    //1.位运算
    public int notDuplicateNumber(int[] source){
//        int result = 0;
//        for(int single : source){
//           result = single^result;
//        }
//        return result;
        
        return Arrays.stream(source).reduce((a,b)-> a^b).getAsInt();
    }


}
