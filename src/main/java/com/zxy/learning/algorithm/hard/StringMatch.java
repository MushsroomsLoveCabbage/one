package com.zxy.learning.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName StringMatch.java
 * @Description
 * @createTime 2020年09月06日 07:02:00
 */
public class StringMatch {

    public static void main(String[] args) {
        new StringMatch().bruteForce("abcabcabcabc", "abc");
        new StringMatch().RabinKarp("abcabcabcabc", "abc");

    }

    /**
     * 暴力匹配算法
     * 拿子串和主串一一匹配，完全匹配则记录，不匹配则主串后移一位
     */
    public List<Integer> bruteForce(String source, String target){
        List<Integer> resultList = new ArrayList<>();
        if(target== null || target.length() == 0){
            return resultList;
        }
        char[] sourceArray = source.toCharArray();
        char[] targetArray = target.toCharArray();
        int length = sourceArray.length;
        int targetLength = targetArray.length;

        for(int index = 0; index < length; index++){
            int tempIndex = index;
            for(int targetIndex = 0; targetIndex < targetLength; targetIndex++){
                if(sourceArray[index] == targetArray[targetIndex]){
                    index ++;
                    if(targetIndex == targetLength -1){
                        resultList.add(tempIndex);
                    }
                    continue;
                } else {
                    break;
                }
            }
            index = tempIndex;
        }
        return resultList;
    }

    /**
     * 相较于上一种，不再做复杂的字符一一比较，
     * 而是先用子串和目标串的hash值(不同hash算法复杂度不一样,冲突概率也不一样)比较,
     * 只有这个相等才可能字符串相等，
     * 存在hash 冲突，再一一比较字符
     *
     */
    public List<Integer> RabinKarp(String source, String target){
        List<Integer> resultList = new ArrayList<>();
        if(target== null || target.length() == 0){
            return resultList;
        }
        char[] tagetArray = target.toCharArray();
        int hash = 0;
        for(char single : tagetArray){
            hash+=single;
        }
        int targetLength = tagetArray.length - 1;
        char[] sourceArray = source.toCharArray();
        int length = sourceArray.length;
        int tempHash = 0;
        int start = 0;
        for(int index = 0; index < length; index++){
            tempHash += sourceArray[index];
            if(index - start == targetLength){
                if(tempHash == hash){
                    int tempIndex = 0;
                    while(tempIndex <= targetLength && sourceArray[start] == tagetArray[tempIndex]){
                        tempIndex++;
                        start++;
                    }
                    start = index - targetLength;
                    if(tempIndex - 1 == targetLength){
                        resultList.add(start);
                    }
                }
                tempHash -= sourceArray[start];
                start++;

            }
        }
        return resultList;
    }

    /**
     * abcdabc
     * abed
     *   e    坏字符 (找到匹配字符)
     *    d   好后缀 (找对应的匹配的前缀子串)
     *
     *
     *
     */
    public List<Integer> boyerMoore(String source, String target){
        List<Integer> resultList = new ArrayList<>();
        if(target== null || target.length() == 0){
            return resultList;
        }
        char[] tagetArray = target.toCharArray();
        char[] sourceArray = source.toCharArray();
        int targetLength = tagetArray.length;
        return resultList;
    }

    public void generateBC(){

    }

    public int bm(String source, String target){

        return -1;
    }


    public int KPM(String source, String target){
        char[] targetArray = target.toCharArray();
        int[] sufiArray = getNexts(targetArray, targetArray.length);

        return -1;
    }

    private int[] getNexts(char[] b, int m) {
        int[] next = new int[m];
        next[0] = -1;
        int k = -1;
        for (int i = 1; i < m; ++i) {
            while (k != -1 && b[k + 1] != b[i]) {
                k = next[k];
            }
            if (b[k + 1] == b[i]) {
                ++k;
            }
            next[i] = k;
        }
        return next;
    }
    //0  k-1 k   i-1 i
    //最长可匹配后缀子串, 最长可匹配前缀子串。
    // [r,i]  是 [0,i]d [r,i-1] [0,i-1]
    //
}
