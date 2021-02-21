package com.zxy.learning.algorithm.basic;

public class SplitWooden {
    public static void main(String[] args) {
        //new SplitWooden().splitWoodenLeastWay(100,5);
        //new SplitWooden().splitWoodenLeast(100,5);
        new SplitWooden().handlerWordReverse("a b c");
        new SplitWooden().handlerWordReverse(" a b c ");
        new SplitWooden().handlerWordReverse(" abc ");
    }
    public String handlerWordReverse(String source){
        char[] sourceArray = source.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        int end = sourceArray.length -1;
        for(int i = end; i > 0; i --){
            if(sourceArray[i] == ' '){
                for(int start = i + 1; start <= end; start++){
                    stringBuilder.append(sourceArray[start]);
                }
                end--;
            }
        }
        return  stringBuilder.toString();
    }

    //
    public int splitWoodenLeast(int slices,int limit){
        int cnt = 0;
        int start = 1;
        while(slices > start){
            cnt ++;
            start += Math.min(start, limit);
        }
        return cnt;
    }

    public int splitWoodenLeastWay(int source, int limit){
        int leastCutTimes = source - 1;
        int canSplit = 1;
        int result = 0;
        while(true){
            if(leastCutTimes == 0){
                return result;
            }
            if(leastCutTimes <= canSplit) {
                result++;
                break;
            }
            result++;
            leastCutTimes -= canSplit;
            canSplit = canSplit<<1;
            if(canSplit > limit){
                canSplit = limit;
            }
        }
        return result;
    }
}
