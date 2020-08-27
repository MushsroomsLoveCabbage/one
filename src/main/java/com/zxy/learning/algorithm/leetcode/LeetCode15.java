package com.zxy.learning.algorithm.leetcode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/3sum/
 */
public class LeetCode15 {
    public static void main(String[] args) {
        threeSumDFS(new int[]{-3,-2,-1,0,0,1,2,3}, 0);
    }

    //双指针法 left right
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums == null || nums.length <=2){
            return result;
        }
        //排序 减少拍短
        Arrays.sort(nums);
        int first = 0;
        int length = nums.length;
        for(;first < length; first++){
            if(first>0 && nums[first] == nums[first-1]){
                continue;
            }
            int third = length - 1;
            int target = - nums[first];
            for(int second = first + 1; second < length; second++){
                if(second > first+1 && nums[second] == nums[second-1]){
                    continue;
                }
                while (second < third && nums[second] + nums[third] > target){
                    third--;
                }
                if(second == third){
                    break;
                }
                if( nums[second] + nums[third]  == target){
                    result.add(Arrays.asList(new Integer[]{nums[first],nums[second],nums[third]}));
                }
            }
        }
        return result;
    }

    static List<List<Integer>> resultList = new ArrayList<>();

    static List<Integer> singleList = new ArrayList<>();

    public static List<List<Integer>> threeSumDFS(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums == null || nums.length <= 3){
            return resultList;
        }
        Arrays.sort(nums);
        dfs(nums, 0, 0, target);

        return resultList;
    }
    public static void dfs(int[] nums, int index, int number, int val){
        if(index == nums.length || singleList.size() == 4 ){
            if(val == 0 && singleList.size() == 4){
                resultList.add(new ArrayList<>(singleList));
            }
            return;
        }
        //作为第一个元素 和上一个元素一样，即直接跳过
        if(index > 0 && nums[index] == nums[index - 1] && !singleList.contains(nums[index-1])){
            dfs(nums, index + 1, number, val);
        } else {
            singleList.add(nums[index]);
            dfs(nums, index + 1, number + 1, val - nums[index]);
            singleList.remove(singleList.size() - 1);
            dfs(nums, index + 1, number, val);
        }

    }
}
