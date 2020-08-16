package com.zxy.learning.algorithm.hard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName Insert.java
 * @Description
 * @createTime 2019年08月23日 17:08:00
 */
public class Insert {

    private static List<int[]> insert(List<int[]> source, int[] target){
        int start = target[0];
        int end = target[target.length - 1];

        if(source.get(0)[0] > end){
            source.add(0, target);
            return source;
        }

        if(source.get(source.size() -1)[source.get(source.size() -1).length - 1] < start){
            source.add(source.size(), target);
            return source;
        }
        int startIndex = 0;
        int endIndex = 0;
        for(int[] array : source){
            int tempStart = array[0];
            int tempEnd = array[array.length - 1];
            if((tempStart <= start && tempEnd >= start) || tempStart > start ){
                break;
            } else{
                startIndex++;
            }
        }

        for(int[] array : source){
            int tempStart = array[0];
            int tempEnd = array[array.length - 1];
            if((tempStart >= end && tempEnd <= end) || tempStart > end ){
                break;
            } else {
                endIndex++;
            }
        }
        int[] array = combin(source, target, startIndex, endIndex);
        for(int i = startIndex; i< endIndex;i ++){
            source.remove(i);
        }
        source.add(startIndex,array);
        return source;

    }

    public static int[] combin(List<int[]> source, int[] target, int start, int end){

        int[] array = new int[2];
        int targetStart = target[0];
        int targetEnd = target[target.length - 1];
        int sourceStart = source.get(start)[0];
        int sourceEnd = source.get(end)[source.get(end).length -1 ];
        if(targetStart > sourceStart){
            array[0] = sourceStart;
        } else {
            array[0] = targetStart;
        }
        if(targetEnd > sourceEnd){
            array[1] = sourceEnd;
        } else {
            array[1] = targetEnd;
        }
        return array;
    }

    public static void main(String[] args) {
        //int[][] source = new int[][]{{1,2,3},{5,6,7},{9,10,11},{15,16,17}};
        List<int[]> source =new ArrayList<>();
        source.add(new int[]{1,3});
        source.add(new int[]{5,6});
        source.add(new int[]{9,11});
        source.add(new int[]{15,17});
        insert(source, new int[]{7,8}).forEach(temp->{
            for(int i : temp){
                System.out.print(i + " ");
            }
            System.out.println();
        });
        String manReward = "109_1";
        String womanReward = "101_1,102_1,109_1";
        StringBuffer man = new StringBuffer();
        StringBuffer woman = new StringBuffer();
        String piece = "109";
        if(manReward.contains(piece)) {
            for(String temp : manReward.split(",")) {
                if(!temp.contains(piece)) {
                    man.append(temp).append(",");
                }
            }
        }

        System.out.println(man.substring(0, man.length() - 1));
        if(womanReward.contains(piece)) {
            for(String temp : womanReward.split(",")) {
                if(!temp.contains(piece)) {
                    woman.append(temp).append(",");
                }
            }
        }
        System.out.println(woman.substring(0, man.length() - 1));
    }
}
