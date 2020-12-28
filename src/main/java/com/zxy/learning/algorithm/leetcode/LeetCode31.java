package com.zxy.learning.algorithm.leetcode;

/**
 * 下一个排列
 * https://leetcode-cn.com/problems/next-permutation/submissions/
 */
public class LeetCode31 {
    public static void main(String[] args) {
        new LeetCode31().nextPermutation(new int[]{1,5,1});
    }

    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int length = nums.length;
        int small = -1;
        for (int i = length - 2; i >= 0; i--) {
            if (nums[i] <= nums[i + 1]) {
                small = i;
                break;
            }
        }
        if (small >= 0) {
            int big = 0;
            for (int i = length-1; i >= 0; i--) {
                if (nums[i] > nums[small]) {
                    big = i;
                    break;
                }
            }
            swap(nums, small, big);
        }
        reverse(nums, ++small);
    }

    public void reverse(int[] source, int begin) {
        int end = source.length - 1;
        while (begin < end) {
            swap(source, begin++, end--);
        }
    }

    public void swap(int[] source, int i, int j) {
        int temp = source[i];
        source[i] = source[j];
        source[j] = temp;
    }
}
