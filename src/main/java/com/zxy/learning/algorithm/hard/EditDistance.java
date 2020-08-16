package com.zxy.learning.algorithm.hard;

/**
 * word1
 * word2
 * 增 删 改
 * 动态规划思想
 * @author zxy
 * @version 1.0.0
 * @ClassName EditDistance.java
 * @Description
 * @createTime 2019年09月12日 11:05:00
 */
public class EditDistance {

    public static void main(String[] args) {
        System.out.println(sloveYX("aefgbfsdarc","abc"));
    }

//    private static int sloveXY(String source, String target){
//        int sourceLength = source.length();
//        int targetLength = target.length();
//        int[][] dpArray = new int[sourceLength + 1][targetLength + 1];
//        for(int i = 0; i <= sourceLength; i++){
//            dpArray[i][0] = i;
//        }
//        for(int j = 0; j <= targetLength; j++){
//            dpArray[0][j] = j;
//        }
//        char[] sourceArray = source.toCharArray();
//        char[] targetArray = target.toCharArray();
//        for(int i = 1; i<= sourceLength; i++){
//            for(int j = 1; j<= targetLength; j++){
//                if(sourceArray[i - 1] == targetArray[j - 1]){
//                    dpArray[i][j] = dpArray[i-1][j-1];
//                } else {
//                    dpArray[i][j] = Math.min(dpArray[i-1][j-1], Math.min(dpArray[i-1][j], dpArray[i][j-1])) + 1;
//                }
//            }
//        }
//        return dpArray[sourceLength][targetLength];
//    }

    public static int sloveYX(String source, String target){
        int sourceLength = source.length();
        int targetLength = target.length();
        int[][] dpArray = new int[targetLength + 1][sourceLength + 1];
        for(int i = 0; i <= sourceLength; i++){
            dpArray[0][i] = i;
        }
        for(int j = 0; j <= targetLength; j++){
            dpArray[j][0] = j;
        }
        char[] sourceArray = source.toCharArray();
        char[] targetArray = target.toCharArray();
        for(int j = 1; j<= targetLength; j++){
            for(int i = 1; i<= sourceLength; i++){
                if(sourceArray[i - 1] == targetArray[j - 1]){
                    dpArray[j][i] = dpArray[j-1][i-1];
                } else {
                    dpArray[j][i] = Math.min(dpArray[j-1][i-1], Math.min(dpArray[j][i-1], dpArray[j-1][i])) + 1;
                }
            }
        }
        return dpArray[targetLength][sourceLength];
    }


}