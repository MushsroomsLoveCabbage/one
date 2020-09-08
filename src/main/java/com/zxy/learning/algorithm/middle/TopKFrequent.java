package com.zxy.learning.algorithm.middle;

import java.util.*;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName TopKFrequent.java
 * @Description 347 (统计频率最高的前N个元素)
 * @createTime 2019年08月08日 15:39:00
 */
public class TopKFrequent {

    public static void main(String[] args) {
        new TopKFrequent().TopK(new int[]{1,1,1,1,1,2,2,2,2,3,3,3,4,4,5},3);
    }
    public int[] TopK(int[] source, int k){
        Map<Integer, Integer> map = new HashMap<>();
        for(int single : source){
            map.put(single, map.getOrDefault(single, 0) + 1);
        }
        //核心在于堆，
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((a,b)-> map.get(a) - map.get(b));
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            priorityQueue.offer(entry.getKey());
            if(priorityQueue.size() > k){
                priorityQueue.poll();
            }
        }
        int[] result = new int[k];
        while(!priorityQueue.isEmpty()){
            result[--k] = priorityQueue.poll();
        }
        return result;
    }

    public List<Integer> getTopKFrequentByStack(int[] data, int size){
        // 使用字典，统计每个元素出现的次数，元素为键，元素出现的次数为值
        HashMap<Integer,Integer> map = new HashMap();
        for(int num : data){
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        // 遍历map，用最小堆保存频率最大的k个元素
        PriorityQueue<Integer> pq = new PriorityQueue<>(
                (a,b) -> map.get(a) - map.get(b) > 0 ? 1 : map.get(a) - map.get(b) == 0 ? 0 : -1
        );
        for (Integer key : map.keySet()) {
            if (pq.size() < size) {
                pq.add(key);
            } else if (map.get(key) > map.get(pq.peek())) {
                pq.remove();
                pq.add(key);
            }
        }
        // 取出最小堆中的元素
        List<Integer> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            res.add(pq.remove());
        }
        return res;
    }


    /**
     * @Title
     * @Description 
     * @Author zxy 
     * @Param [data, size]
     * @UpdateTime 2019/8/8 16:01 
     * @Return java.util.List<java.lang.Integer>
     * @throws 
     */
    public List<Integer> getTopKFrequentByBucket(int[] data, int size){
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> frequentMap = new HashMap<>();
        for(int i = 0; i < data.length; i++){
            frequentMap.put(data[i],  frequentMap.getOrDefault(data[i], 0) + 1);
        }

        List<Integer>[] listArray = new List[data.length + 1];

        for(int key : frequentMap.keySet()){
            int index = frequentMap.get(key);
            List<Integer> list = listArray[index];
            if(list == null){
                list = new ArrayList<>();
                listArray[index] = list;
            }
            list.add(key);
        }
        for(int index = listArray.length-1; index >= 0 && result.size() < size; index --){
            result.addAll(listArray[index]);
        }
        return result;
    }

}
