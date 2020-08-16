package com.zxy.learning.algorithm.leetcode;

import java.util.Arrays;
import java.util.Map;

/**
 * 回溯法遍历所有情况
 * 双指针遍历
 * https://leetcode-cn.com/problems/3sum-closest/
 */
public class LeetCode16 {

    public static void main(String[] args) {
        //threeSumClosest(new int[]{-1,2,1,-4}, 1);
        threeSumClosest(new int[]{0,0,0}, 1);
        threeSumClosestWithDoublePointer(new int[]{-1,2,1,-4}, 1);
    }

    //先排序，然后双指针遍历解决问题
    public static int threeSumClosestWithDoublePointer(int[] nums, int target){
        Arrays.sort(nums);
        int best = 10000000;
        for(int i = 0; i< nums.length -2; i++){
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            int two = i+1;
            int three = nums.length-1;
            while(two < three){
                int sum = nums[i] + nums[two] + nums[three];
                if(sum == target){
                    return target;
                }
                if(Math.abs(sum - target) < Math.abs(best - target)){
                    best = sum;
                }
                if(sum > target){
                    //three --;
                    //局部优化，相同的直接跳过
                    int threeTemp = three;
                    while(threeTemp > two && nums[threeTemp] == nums[three]){
                        threeTemp--;
                    }
                    three = threeTemp;
                } else {
                    //two ++;
                    //局部优化，相同的直接跳过
                    int twoTemp = two;
                    while(twoTemp < three && nums[twoTemp] == nums[two]){
                        twoTemp++;
                    }
                    two = twoTemp;
                }

            }
        }
        return best;
    }

    public static int threeSumClosest(int[] nums, int target) {
        targetVal = target;
        source = nums;
        cloest(0,-1, 0);
        return result;
    }

    static int[] source;

    static int targetVal;

    static int result;

    static int val = Integer.MAX_VALUE;

    public static void cloest(int index, int numberIndex, int sum){
        if(numberIndex + 3 - index >= source.length){
            return;
        }
        if(index == 3){
            long temp = Math.abs((long)sum - (long)targetVal);
            if(temp < val){
                val = (int)temp;
                result = sum;
            }
            return;
        }
        for(int j = numberIndex+1; j< source.length; j++){
            sum += source[j];
            cloest(index+1,j, sum);
            sum -= source[j];
        }
    }

}
