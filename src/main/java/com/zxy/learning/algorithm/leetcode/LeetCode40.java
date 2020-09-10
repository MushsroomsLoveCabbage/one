package com.zxy.learning.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 递归算法，
 * 处理去重， 减枝
 * https://leetcode-cn.com/problems/combination-sum-ii/
 */
public class LeetCode40 {

    public static void main(String[] args) {
        new LeetCode40().combinationSum2(new int[]{10,1,2,7,6,1,1,1,5}, 8);
        new LeetCode40().combinationSum(new int[]{2,3,7}, 7);
    }

    int length;

    List<List<Integer>> list = new ArrayList<>();

    List<Integer> tempList = new ArrayList<>();

    long jump;

    //不允许重复使用，不允许重复结果集
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        length = candidates.length;
        Arrays.sort(candidates);
        jump  = candidates[0] -1;
        dfs(candidates,0, target);
        return list;
    }

    //允许重复使用数字，但允许重复结果集
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        length = candidates.length;
        Arrays.sort(candidates);
        dfs1(candidates,0, target);
        return list;
    }
//    public void dfs1(int[] source, int index, int target) {
//        if (index == length){
//            return;
//        }
//        if(target == 0){
//            list.add(new ArrayList<>(tempList));
//            return;
//        }
//        //减枝
//        int val = source[index];
//        if(val > target){
//            return;
//        }
//        dfs1(source, index+1,  target);
//        if(target - source[index] >= 0){
//            tempList.add(source[index]);
//            dfs1(source, index, target - source[index]);
//            tempList.remove(tempList.size() - 1);
//        }
//    }

    public void dfs1(int[] source, int index, int target) {
        if (index == length){
            return;
        }
        if(target == 0){
            list.add(new ArrayList<>(tempList));
            return;
        }
        //减枝
        int val = source[index];
        if(val > target){
            return;
        }
        //不取值，然后取值，结果集限定在从大到小的遍历中，不断递减
            dfs1(source, index+1, target);
            tempList.add(source[index]);
            dfs1(source, index,  target - source[index]);
            tempList.remove(tempList.size() - 1);
    }

    //dfs 对存在
    //1,1,1,  1,1,1 -> 1,1,0 -> 1,0,0 -> 0,0,0
    //
    public void dfs(int[] source, int index, int target) {
        if (index == length || target == 0){
            if(target == 0){
                list.add(new ArrayList<>(tempList));
            }
            return;
        }
        //减枝
        int val = source[index];
        if(val > target){
            return;
        }
        //对于遍历， 以 0,1来表示，先选取后不取，即第一次遍历完结果是1,1,1,1
        // (隐藏的现有状态是1, 11, 111),因此在遍历完最后一个数值后，对相同的数值不再采用
        if(index > 0 && source[index] == source[index-1] && jump == source[index]){
            dfs(source, index+1,  target);
        } else {
            tempList.add(source[index]);
            dfs(source, index + 1, target - source[index]);
            tempList.remove(tempList.size() - 1);
            if(jump != source[index]){
                jump = source[index];
            }
            dfs(source, index+1,  target);
        }
    }
    // 1    1    1
    // 1    1    0
    // 1    0    1
    // 1    0    0


}