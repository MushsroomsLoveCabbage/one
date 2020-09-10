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

        int sum = Arrays.stream(new int[]{-79583480,-70078020,-65863359,-21202664}).sum();

        long sumVal = Arrays.stream(new int[]{-76072023,-59608044,-58094433,-42953023}).sum();

        threeSumDFS(new int[]{91277418,66271374,38763793,4092006,11415077,60468277,1122637,72398035,-62267800,22082642,60359529,-16540633,92671879,-64462734,
                -55855043,-40899846,88007957,-57387813,-49552230,-96789394,18318594,-3246760,-44346548,-21370279,42493875,25185969,83216261,-70078020,-53687927,
                -76072023,-65863359,-61708176,-29175835,85675811,-80575807,-92211746,44755622,-23368379,23619674,-749263,-40707953,-68966953,72694581,-52328726,
                -78618474,40958224,-2921736,-55902268,-74278762,63342010,29076029,58781716,56045007,-67966567,-79405127,-45778231,-47167435,1586413,-58822903,
                -51277270,87348634,-86955956,-47418266,74884315,-36952674,-29067969,-98812826,-44893101,-22516153,-34522513,34091871,-79583480,47562301,6154068,
                87601405,-48859327,-2183204,17736781,31189878,-23814871,-35880166,39204002,93248899,-42067196,-49473145,-75235452,-61923200,64824322,-88505198,
                20903451,-80926102,56089387,-58094433,37743524,-71480010,-14975982,19473982,47085913,-90793462,-33520678,70775566,-76347995,-16091435,94700640,
                7183454,85735982,90399615,-86251609,-68167910,-95327478,90586275,-99524469,16999817,27815883,-88279865,53092631,75125438,44270568,-23129316,
                -846252,-59608044,90938699,80923976,3534451,6218186,41256179,-9165388,-11897463,92423776,-38991231,-6082654,92275443,74040861,77457712,-80549965,
                -42515693,69918944,-95198414,15677446,-52451179,-50111167,-23732840,39520751,-90474508,-27860023,65164540,26582346,-20183515,99018741,-2826130,
                -28461563,-24759460,-83828963,-1739800,71207113,26434787,52931083,-33111208,38314304,-29429107,-5567826,-5149750,9582750,85289753,75490866,-93202942,
                -85974081,7365682,-42953023,21825824,68329208,-87994788,3460985,18744871,-49724457,-12982362,-47800372,39958829,-95981751,-71017359,-18397211,
                27941418,-34699076,74174334,96928957,44328607,49293516,
                -39034828,5945763,-47046163,10986423,63478877,30677010,-21202664,-86235407,3164123,8956697,-9003909,-18929014,-73824245}, -236727523);
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
    static int[] singleArray = new int[4];
    static long jump;

    //数据溢出的情况不考虑 A+B+C+D < 2^32 然后转变为
    public static List<List<Integer>> threeSumDFS(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums == null || nums.length <= 3){
            return resultList;
        }
        Arrays.sort(nums);
        jump = nums[0] - 1;
        dfs(nums, 0, 0, target);
        return resultList;
    }

    //{0,0,0,1,1,1,5,5,5}
    //0 0
    public static void dfs(int[] nums, int index, int number, int val){
        if(index == nums.length || singleList.size() == 4){
            if(val == 0 && singleList.size() == 4){
                resultList.add(new ArrayList<>(singleList));
            }
            return;
        }
        //作为第一个元素 和上一个元素一样，即直接跳过
        if(index > 0 && nums[index] == nums[index - 1] && jump == nums[index]){
            dfs(nums, index + 1, number, val);
        } else {
            if(val < nums[index]){
                return;
            }
            //
            singleArray[number] = nums[index];
            int tempSum = Arrays.stream(singleArray).sum();

            singleList.add(nums[index]);
            singleList.stream().count();
            dfs(nums, index + 1, number + 1, val - nums[index]);
            singleList.remove(singleList.size() - 1);
            if(jump != nums[index]){
                jump = nums[index];
            }
            dfs(nums, index + 1, number, val);
        }

    }
}
