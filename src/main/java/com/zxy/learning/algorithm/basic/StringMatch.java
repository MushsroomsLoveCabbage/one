package com.zxy.learning.algorithm.basic;

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
        new StringMatch().KPM("ABCABDABCD", "ABCD");
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
     *   e    坏字符 (找到匹配字符)  坏字符在模式串中的位置 - 坏字符在模式串中最右出现的为位置 不存在 -1
     *    d   好后缀 (找对应的匹配的前缀子串) 好后缀在模式串中的位置 - 好后缀在模式串上一次出现的位置 不存在 -1
     *    //好后缀用表来辅助记录
     *    //坏字符最后出现的问题也用辅助表记录
     *移动 两者的最大值
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

    //每次匹配不匹配时，选择文本串参加匹配的最末位字符下一个字符
    //substring searching algorithm
    //search
    //选择 i 移动 6+1
    //       search
    //选择r移动 3 + 1
    //文本端最后一个字符到尾部长度+1
    public int sunday(String source, String target){

        return -1;
    }

    /**
     * ABCAABCD ABCDE ABCAB
     * ABCAB
     *
     *
     */
    public int KPM(String source, String target){
        char[] targetArray = target.toCharArray();
        char[] sourceArray = source.toCharArray();
        int[] partialArray = getNexts(targetArray, targetArray.length);
        int i = 0;
        int j = 0;
        for(; i< source.length(); i++){
            while(j > 0 && sourceArray[i] != targetArray[j]){
                //abcabd
                //5-2
                j = partialArray[j];
            }
            //相等则j++
            if(sourceArray[i] == targetArray[j]){
                j++;
            }
            if(j == targetArray.length){
                return i - j + 1;
            }
        }
        return -1;
//        while(i < sourceArray.length && j< sourceArray.length){
//            if(j == -1 || sourceArray[i] == targetArray[j]){
//                j++;
//                i++;
//            } else {
//                j = partialArray[j];
//            }
//        }
//        if(j == sourceArray.length){
//            return i-j;
//        } else {
//          return -1;
//        }
    }

    private int[] getNext(char[] source){
        int[] next = new int [source.length];
        int k = -1;
        next[0] = -1;
        int j = 0;
        while(j < source.length-1){
            if(k == -1 || source[j] == source[k]) {
                j++;
                k++;
//                next[j] = k;
                //较之前next数组求法，改动在下面4行
                if (source[j] != source[k]){
                    next[j] = k;   //之前只有这一行
                } else {
                    //因为不能出现p[j] = p[ next[j]]，
                    // 所以当出现时需要继续递归，k = next[k] = next[next[k]]
                    next[j] = next[k];
                }
            }else {
                k = next[k];
            }
        }
        return next;
    }
    // 对于Pj{P0,P1,P2....Pj-1,Pj}
    // 存在 {P0,P1...Pk-1,Pk} == {Pj-k,Pj-k+1...Pj-1,Pj}
    // 则 k+1 为Pj的公共前缀后缀
    // A B C D A B C E
    // 0,0,0,0,1,2,3,0
    //abcabd
    //   abcabd;
    // 5 - 2 右移三位
    //-1,0
    private int[] getNexts(char[] b, int m) {
        int[] next = new int[m];
        next[0] = -1;
        int k = -1;
        for (int i = 1; i < m; ++i) {
            while (k != -1 && b[k + 1] != b[i]) {
                k = next[k];
            }
            //
            if (b[k + 1] == b[i]) {
                ++k;
            }
            //
            next[i] = k;
        }
        //return next;

        int i = 0;
        while(i < b.length - 1){
            if(k == -1 || b[i] == b[k]){
                k++;
                i++;
                next[i] = k;
            } else {
                k = next[k];
            }
        }
        return next;
    }
    //
    public int[] getNexts(char[] source){
        int[] next = new int[source.length];
        next[0] = -1;
        int k = -1;
        int j = 0;
        while(j < source.length-1){
            if(k == -1 || source[j] == source[k]){
                k++;
                j++;
                //避免 p[j] == p[next[j]]
                if(source[j] == source[k]){
                    next[j] = next[k];
                } else {
                    //p[j] != p[k]
                    next[j] = k;
                }
            } else {
                k = next[k];
            }
        }
        return next;
    }
    //0  k-1 k   i-1 i
    //最长可匹配后缀子串, 最长可匹配前缀子串。
    // [r,i]  是 [0,i] [r,i-1] [0,i-1]
    //
    //PMT表
    //Next[]
}
