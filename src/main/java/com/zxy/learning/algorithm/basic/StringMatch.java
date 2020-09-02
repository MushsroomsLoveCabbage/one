package com.zxy.learning.algorithm.basic;

import java.util.*;

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

     /**
     * abcebc
     * abd
     * 碰到不匹配字符 找主串中匹配字符下个字符 即 i + target.length() - j
     * i = 2,j=2,length=3 => i = 2 - 2 + 3,

     * abcdbc   abcdbc
     * abd       abd
     * 如果匹配串中有这个字符 i移动到整个匹配串最后位前moveIndex位
     * i = 3,moveIndex=3 => i= i-moveIndex= 0, j=0
     * abcebc
     * abd
     * 没有这个字符 i 移动到整个匹配串的下一位
     * j=0;i=3
     *
     */
    public int Sunday(String source, String target){
        Map<Character, Integer> charIndexMap = new HashMap<>();
        char[] charArray =  target.toCharArray();
        for(int i = 0; i< target.length(); i++){
            char tempChar = charArray[i];
            charIndexMap.put(tempChar, i+1);
        }
        char[] sourceArray = source.toCharArray();
        int j = 0;
        for(int i = 0; i< sourceArray.length; i++){
            if(sourceArray[i] != charArray[j]){
                i = i -j + charArray.length;
                char next = sourceArray[ i];
                Integer moveIndex = charIndexMap.get(next);
                if(moveIndex != null){
                    i -= moveIndex;
                }
                j = 0;
            } else {
                j++;
            }
            if(j == charArray.length){
                return i - j + 1;
            }
        }
        return -1;
    }
    
     public void generateBC(String source,Map<Character, Integer> BadCharIndexMap){
        char[] sourceArray = source.toCharArray();
        for(int i = 0; i< source.length(); i++){
            BadCharIndexMap.put(sourceArray[i], i);
        }
    }

    /**
     * cabcab
     * suffix  模式串中和后缀子串匹配的子串的最右下标 不存在则-1
     * 2 b
     * 1 ab
     * 0 cab
     * -1 bcab
     * -1 abcab
     *
     * prefix 好后缀的后缀子串中 找最长的能跟模式串前缀子串匹配的后缀子串
     *  false b
     *  false ab
     *  true cab
     *  false bcab
     *  false abcab
     */
    private void generateGS(char[] b, int m, int[] suffix, boolean[] prefix) {
        // 初始化
        for (int i = 0; i < m; ++i) {
            suffix[i] = -1;
            prefix[i] = false;
        }
        // b[0, i]
        for (int i = 0; i < m - 1; ++i) {
            int j = i;
            // 公共后缀子串长度
            int k = 0;
            // 与b[0, m-1]求公共后缀子串
            while (j >= 0 && b[j] == b[m-1-k]) {
                --j;
                ++k;
                //j+1表示公共后缀子串在b[0, i]中的起始下标
                suffix[k] = j+1;
            }

            if (j == -1) {
                //如果公共后缀子串也是模式串的前缀子串
                prefix[k] = true;
            }
        }
    }



    /**
     * 坏字符原则
     * 好后缀原则
     *
     */
    public int bm(String source, String target){
        char[] a = source.toCharArray();
        int n = source.length();
        char[] b = target.toCharArray();
        int m = target.length();
            // 记录模式串中每个字符最后出现的位置
        Map<Character, Integer> BadCharIndexMap = new HashMap<>();
            // 构建坏字符哈希表
        generateBC(source, BadCharIndexMap);

            int[] suffix = new int[m];
            boolean[] prefix = new boolean[m];
            generateGS(b, m, suffix, prefix);
           // j表示主串与模式串匹配的第一个字符
            int i = 0;
            while (i <= n - m) {
                int j;
                // 模式串从后往前匹配
                for (j = m - 1; j >= 0; --j) {
                    if (a[i+j] != b[j]) {
                        break; // 坏字符对应模式串中的下标是j
                    }
                }
                if (j < 0) {
                    // 匹配成功，返回主串与模式串第一个匹配的字符的位置
                    return i;
                }
                int x = j - BadCharIndexMap.getOrDefault((int)a[i+j], -1);
                int y = 0;
                // 如果有好后缀的话
                if (j < m-1) {
                    y = moveByGS(j, m, suffix, prefix);
                }
                i = i + Math.max(x, y);
            }
            return -1;
        }

    private int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        // 好后缀长度
        int k = m - 1 - j;
        if (suffix[k] != -1) {
            return j - suffix[k] +1;
        }
        for (int r = j+2; r <= m-1; ++r) {
            if (prefix[m-r] == true) {
                return r;
            }
        }
        return m;
    }
}
