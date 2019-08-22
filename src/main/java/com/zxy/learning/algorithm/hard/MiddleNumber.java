package com.zxy.learning.algorithm.hard;

/**
 * a [0...i][i+1...n]
 * b [0...j][j+1...m]
 * a.left.length + b.left.length = a.right.length + b.right.length
 * a[i] > b[j-1] && b[j] > a[i-1]
 * @author zxy
 * @version 1.0.0
 * @ClassName MiddleNumber.java
 * @Description
 * @createTime 2019年08月19日 10:04:00
 */
public class MiddleNumber {

    private static double middleNumber(int[] nums1, int[] nums2){
        int n = nums1.length;
        int m = nums2.length;
        if(n > m){
            int[] tempArray = nums1; nums1 = nums2; nums2 = tempArray;
            int tempLength = n; n = m; m = tempLength;
        }
        int min = 0, max = n, half = (n + m + 1)/2;
        while (min <= max){
            int i = (min + max)/2;
            int j = half - i;
            if(i < max && nums1[i] < nums2[j-1]){
                min = i+1;
            } else if (i > min && nums1[i-1] > nums2[j]){
                max = i-1;
            } else {
                int maxLeft = 0;
                if(i == 0){
                    maxLeft = nums2[j-1];
                } else if(j == 0){
                    maxLeft = nums1[i-1];
                } else {
                    maxLeft= Math.max(nums1[i-1], nums2[j-1]);
                }
                if((m+n)%2 == 1){
                    return maxLeft;
                }
                int minRight = 0;
                if(i == n){
                    minRight = nums2[j];
                } else if(j == m){
                    minRight = nums1[i];
                } else {
                    minRight= Math.min(nums1[i], nums2[j]);
                }
                return (maxLeft+ minRight)/2;

            }
        }
        return 0D;
    }

    public static void main(String[] args) {
        System.out.println(middleNumber(new int[]{1,2,3,4,5}, new int[]{6,7,8,9,10}));
    }
}
