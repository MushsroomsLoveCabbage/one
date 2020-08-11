package com.zxy.learning.algorithm.hard;

/**
 * word1
 * word2
 * 增 删 改
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

package com.zxy.learning.algorithm.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName Insert.java
 * @Description
 * @createTime 2019年08月23日 17:08:00
 */
public class Insert {

    private static List<int[]> insert(List<int[]> source, int[] target){
        int start = target[0];
        int end = target[target.length - 1];

        if(source.get(0)[0] > end){
            source.add(0, target);
            return source;
        }

        if(source.get(source.size() -1)[source.get(source.size() -1).length - 1] < start){
            source.add(source.size(), target);
            return source;
        }
        int startIndex = 0;
        int endIndex = 0;
        for(int[] array : source){
            int tempStart = array[0];
            int tempEnd = array[array.length - 1];
            if((tempStart <= start && tempEnd >= start) || tempStart > start ){
                break;
            } else{
                startIndex++;
            }
        }

        for(int[] array : source){
            int tempStart = array[0];
            int tempEnd = array[array.length - 1];
            if((tempStart >= end && tempEnd <= end) || tempStart > end ){
                break;
            } else {
                endIndex++;
            }
        }
        int[] array = combin(source, target, startIndex, endIndex);
        for(int i = startIndex; i< endIndex;i ++){
            source.remove(i);
        }
        source.add(startIndex,array);
        return source;

    }

    public static int[] combin(List<int[]> source, int[] target, int start, int end){

        int[] array = new int[2];
        int targetStart = target[0];
        int targetEnd = target[target.length - 1];
        int sourceStart = source.get(start)[0];
        int sourceEnd = source.get(end)[source.get(end).length -1 ];
        if(targetStart > sourceStart){
            array[0] = sourceStart;
        } else {
            array[0] = targetStart;
        }
        if(targetEnd > sourceEnd){
            array[1] = sourceEnd;
        } else {
            array[1] = targetEnd;
        }
        return array;
    }

    public static void main(String[] args) {
        //int[][] source = new int[][]{{1,2,3},{5,6,7},{9,10,11},{15,16,17}};
//        List<int[]> source =new ArrayList<>();
//        source.add(new int[]{1,3});
//        source.add(new int[]{5,6});
//        source.add(new int[]{9,11});
//        source.add(new int[]{15,17});
//        insert(source, new int[]{7,8}).forEach(temp->{
//            for(int i : temp){
//                System.out.print(i + " ");
//            }
//            System.out.println();
//        });
        String manReward = "109_1";
        String womanReward = "101_1,102_1,109_1";
        StringBuffer man = new StringBuffer();
        StringBuffer woman = new StringBuffer();
        String piece = "109";
        if(manReward.contains(piece)) {
            for(String temp : manReward.split(",")) {
                if(!temp.contains(piece)) {
                    man.append(temp).append(",");
                }
            }
        }

        System.out.println(man.substring(0, man.length() - 1));
        if(womanReward.contains(piece)) {
            for(String temp : womanReward.split(",")) {
                if(!temp.contains(piece)) {
                    woman.append(temp).append(",");
                }
            }
        }
        System.out.println(woman.substring(0, man.length() - 1));
    }
}


package com.zxy.learning.algorithm.hard;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName JumpGame.java
 * @Description
 * @createTime 2019年09月09日 22:25:00
 */
public class JumpGame {

    public static void main(String[] args) {

        int[] array = new int[]{1,2,3,4,5,6,7,8,9,10};
    }

    private boolean jumpGame(int[] source) {
        int[] dp = new int[source.length];
        dp[0] = 0;
        for(int i = 1; i < source.length; i++){
            if(dp[i - 1] + source[i - 1] >= source.length) {
                return true;
            } else {
                dp[i] = Math.max(dp[i - 1], source[i - 1])- 1;
                if(dp[i] < 0){
                    return false;
                }
            }
        }
        return false;
    }
}


package com.zxy.learning.algorithm.hard;

import com.google.common.collect.Lists;
import com.zxy.learning.data.structure.binarytree.TreeNode;
import com.zxy.learning.designpattern.decoratee.Source;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.min;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName MaxRange.java
 * @Description
 * @createTime 2020年03月21日 18:58:00
 */
public class MaxRange implements Cloneable {

    public static void test12(){
        String a ="ab";
        String b = "a"+"b";
    }

    public static List<String> getIp(String source){
        List<String> result = new LinkedList<>();
        int length = 0;
        int index = 0;
        for(int i = 0; i< 4; i++){
            for(int j = 0; j < 3; j++){
                //source.substring()
            }
        }
        return result;
    }

    public static void test11(){
        String a = "ab";
        String b = "a"+ "b";
        System.out.println(a==b);
    }


    public static void test1(){
        long[] a = new long[2];
        a[0] = 1L;
        a[1] = 2;
        Queue queue = new ArrayBlockingQueue(10);
        //AbstractQueuedSynchronizer
    }
    public void test10(){
       //ExecutorService executorService = Executors.newCachedThreadPool();
       for(int i = 0; i< 3; i++){
           new Thread(()->{
               while(true) {
                   Test test = new Test();
                   test.setId(1);
                   list.add(test);
                   try {
                       Thread.sleep(1000);
                   }catch (Exception E){

                   }
               }
           }).start();
       }
        for(int i = 0; i< 2; i++) {
            new Thread(()->{
                while(true) {
                    for(Test temp : list){
                        temp.getId();
                    }
                }
            }).start();
        }
        System.out.println("1");
    }
    class Test{
        private int id;
        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return id;
        }

    }
    static List<Test> list = new ArrayList<>();
    public static void test3(){
        System.out.println(Integer.toBinaryString((1 << 29) -1));

//        Collections.synchronizedList(list);
//        List<Integer> list = new ArrayList<>();
//        Lists.newArrayList(list);
//        Iterator<Integer> iterator =  list.iterator();


    }
    public static void test2(){
        Object[] a = new Object[2];

        a[0] = "1";
        a[1] = new long[2];

    }
    public static void test(){
        String[] a = new String[2];
        Object[] b = a;
        a[0] = "1";
        b[1] = new Object();
        Long[] array = new Long[2];

    }

    public static void countNumber(int[][] source){
        int result = 0;
        for(int i = 0; i < source.length; i++){
            for(int j = i+1; j<source.length; j++){
                double ij = Math.pow((double)(source[i][0] - source[j][0]), 2) +
                        Math.pow((double)(source[i][0] - source[j][1]),2);
                for(int k = j+1; j<source.length; k++){
                    double ik = Math.pow((double)(source[i][0] - source[k][0]), 2) +
                            Math.pow((double)(source[i][0] - source[k][1]),2);
                    if(ij == ik){
                        result++;
                    }else {
                        double jk = Math.pow((double) (source[k][0] - source[j][0]), 2) +
                                Math.pow((double) (source[k][0] - source[j][1]), 2);
                        if(ij == jk || ik == jk){
                            result++;
                        }
                    }
                }
            }
        }
    }

//    public static void main(String[] args) {
//
//        System.out.println(Integer.toBinaryString(~((1 << 29) -1) & (-1<<29)));
//        System.out.println(Integer.toBinaryString(-536870912));
//        System.out.println(Integer.toBinaryString((-1)));
//
////        ThreadPoolExecutor executor = new ThreadPoolExecutor(4,16,1000,TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024));
////        for(int i = 0; i< 10; i++) {
////            executor.execute(() -> {
////                while (true) {
////                    System.out.println("1");
////                    try {
////                        Thread.sleep(1000);
////                    } catch (Exception e) {
////                    }
////                }
////            });
////        }
////        test2();
////        test();
////
////        Stack<Integer> stack = new Stack<>();
////        stack.add(1);
////        stack.add(2);
////        stack.add(3);
////        System.out.println(stack.toString());
////        System.out.println(stack.subList(0,2).toString());
////        stack.remove(0);
////        System.out.println(stack.toString());
////        System.out.println(maxRange(new int[]{1,2,3,4,5,4,3,2,1}, 3));
////
//        //System.out.println(minUniqueIncreaseNumber(new int[]{1,2,3,4,5,4,3,2,1}, 10));
//
//        //System.out.println(maxNumber(new int[]{1,10,92,3}));
////        System.out.println(maxWaterSloutionTwo(new int[]{3,2,3,2,5}));
////        System.out.println(maxWaterSloutionTwo(new int[]{3,2,3,2,1}));
////        System.out.println(maxWaterSloutionTwo(new int[]{1,2,3,2,1}));
////        System.out.println(binaryWatch().toString());
////        System.out.println(notCoverArray(new int[]{2,2,3,3,5,6}));
//
//        int[][] result = merageInterval(new int[][]{new int[]{1,2},new int[]{3,5},new int[]{6,7},new int[]{8,10},
//                new int[]{15,16}});
//        for(int[] temp: result){
//            System.out.println(temp[0] + "-" +temp[1] +",");
//        }
//
////        result = question57Sloution1(new int[][]{new int[]{1,2},new int[]{3,5},new int[]{6,7},new int[]{8,10},
////                new int[]{12,16}}, new int[]{17,81});
////        for(int[] temp: result){
////            System.out.println(temp[0] + "-" +temp[1] +",");
////        }
////
////        result = question57Sloution1(new int[][]{new int[]{1,2},new int[]{4,5},new int[]{6,7},new int[]{8,10},
////                new int[]{12,16}}, new int[]{3,8});
////        for(int[] temp: result){
////            System.out.println(temp[0] + "-" +temp[1] +",");
////        }
////        result = question57Sloution1(new int[][]{new int[]{1,2},new int[]{4,5},new int[]{6,7},new int[]{8,10},
////                new int[]{15,16}}, new int[]{11,13});
////        for(int[] temp: result){
////            System.out.println(temp[0] + "-" +temp[1] +",");
////        }
//
//        result = merageInterval(new int[][]{new int[]{1,4},new int[]{2,5},new int[]{6,7}, new int[]{8,9},new int[]{8,10},
//                new int[]{9,16}});
//        for(int[] temp: result){
//            System.out.println(temp[0] + "-" +temp[1] +",");
//        }
//
//
//        //NQueen(8);
//    }

    public static int[] initData(Map<Integer,Integer> data, int basic, int end){
        int[] result = new int[end -basic];
        for(Map.Entry<Integer, Integer> entry : data.entrySet()){
            int key = entry.getKey();
            int value = entry.getValue();
            result[key - basic] = value;
        }
        return result;
    }

    public static int maxRange(int[] source, int range){
        int max = Integer.MIN_VALUE;
        int count = 0;
        int number = 0;
        for(int index =0; index< source.length; index++){
            count+= source[index];
            if(++number == range){
                if(count>max){
                    max = count;
                }
                count -= source[index-range+1];
                number--;
            }
        }
        return max;
    }
    
    
    /**
     * @Title
     * @Description 
     * @Author zxy 
     * @Param [source]
     * @UpdateTime 2020/3/23 10:12 
     * @Return int
     * @throws 
     */
    public static int minUniqueIncreaseNumber(int[] source, int max){
        if(source.length < 2) {
            return 0;
        }
        int[] tempArray = new int[max];
        Arrays.fill(tempArray, -1);
        int result = 0;
        for(int i: source){
            int index = findPosition(tempArray, i);
            result += (index - i);
        }
        return result;
    }

    private static int findPosition(int[] source, int index){
        int temp = source[index];
        if(temp == -1){
            source[index] = index;
            return index;
        }
        temp = findPosition(source, index + 1);
        source[index] = temp;
        return temp;
    }

    public static int countTriangleNumber(int[] source){
        //A + B > C
        Arrays.sort(source);
        int result = 0;
        for(int i = source.length; i > 0; i--){
            for(int j = i-1; source[j]> source[i]/2 && j > 0; j--){
                for(int k = j - 1; k>0; k--){
                    if(source[j] +source[k] < source[i]){
                        break;
                    }
                    result++;
                }
            }
        }
        return result;
    }
    
    /**
     * @Title
     * @Description 
     * @Author zxy 
     * @Param [source]
     * @UpdateTime 2020/3/29 1:08 
     * @Return int
     * @throws 
     */
    public int maxArrayDistance(int[][] source){
        int max =Integer.MIN_VALUE;
        int min =Integer.MAX_VALUE;
        for(int[] tempArray : source){
            max = max > tempArray[tempArray.length-1] ? tempArray[tempArray.length-1]:max;
            min = min < tempArray[0] ? min : tempArray[0];
        }
        return max - min;
    }

    /**
     * @Title
     * @Description 
     * @Author zxy 
     * @Param [subjectTime, maxEndTime, maxTime]
     * @UpdateTime 2020/3/29 1:20 
     * @Return int
     * @throws 
     */
    public int maxSubject(int[] subjectTime, int[] maxEndTime, int maxTime){
        int[] dp = new int[maxTime+1];
        for(int i = 0; i<subjectTime.length;i++){
            //for()
        }
        return dp[maxTime];
    }

    public static int count = 0;

    public static void NQueen(int k){
        int[] record = new int[k];
        queen(record, 0, k);
        System.out.println(count);
    }

    private static void queen(int[] source, int x, int k){
        if(x == k){
            count++;
            return;
        }
        for(int y = 0; y < k; y++){
            source[x] = y;
            if(check(source, x)){
                queen(source, x+1, k);
            }

        }
    }

    private static boolean check(int[] source, int end){
        for(int index = end-1; index >= 0; index--){
            if(Math.abs(source[index] - source[end]) == end - index || source[index] == source[end]){
                return false;
            }
        }
        return true;
    }

    public static String maxNumber(int[] source){
        return Arrays.stream(source).mapToObj(String::valueOf).sorted((s1,s2) ->(s2+s1).compareTo(s1+s2))
                .reduce((s1,s2)-> s1.equals("0") ? s2: s1+s2).get();
    }

    /**
     * 分割子数组，数组中最高两个值，然后分割成A B C 三个数组
     * A C（A的最大值是其右边界，C的最大值是其左边界）
     * 同样方式再分割
     * [1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1]
     * [1,2],[2,3],[3,4]
     * @Title
     * @Description
     * @Author zxy
     * @Param [source]
     * @UpdateTime 2020/3/29 19:39
     * @Return int
     * @throws
     */
    public static int maxWater(int[] source){
        if(source.length <= 1) {
            return 0;
        }
        int startValue = source[0];
        int startIndex = 0;
        int result = 0;
        for(int index = 1; index < source.length; index++){
            if(source[index] >= startValue){
                result += startValue * (index - startIndex);
                startIndex = index;
                startValue = source[index];
            }

        }

        if(startIndex != source.length - 1){
            int endIndex = source.length - 1;
            int endValue = source[endIndex];
            for(int index = endIndex - 1; index >= startIndex ; index--){
                if(source[index] >= endValue){
                    result += endValue * (endIndex - index);
                    endIndex = index;
                    endValue = source[index];
                }

            }
        }
        return result;
    }

    public static int maxWaterSloutionTwo(int[] source){
        int result = 0;
        int startIndex = 0;
        int endIndex = source.length-1;
        int startValue = source[startIndex];
        int endValue = source[endIndex];
        int start = startIndex+1, end = endIndex -1 ;

        while(end >= start){
            if(source[start] >= startValue){
                result += startValue * (start - startIndex);
                startIndex = start;
                startValue = source[start];
            }
            start++;
            if(source[end] >= endValue){
                result += endValue * (endIndex - end);
                endIndex = end;
                endValue = source[end];
            }
            end--;
        }
        if(end != start){
            result += Math.min(source[endIndex], source[startIndex]) * (endIndex -startIndex);
        }
        return result;
    }


    public static List<String> binaryWatch(){
        List<String> result = new LinkedList<>();
        for(int i = 0; i < 4; i++){
           result.add((1<<i) + ":00");
        }
        for(int i = 0; i < 6; i++){
            String minute = (1<<i) > 10 ?  String.valueOf(1<<i) : "0" +  (1<<i);
            result.add("00:" + minute);
        }
        return result;
    }


    /**
     * [2,2,3,4]
     *
     * @Title
     * @Description
     * @Author zxy
     * @Param [source]
     * @UpdateTime 2020/3/30 0:42
     * @Return int[]
     * @throws
     */
    public static int[] notCoverArray(int[] source){
        for(int i:source){
            int temp = i;
            if(temp < 0){
                temp = -temp;
            }
            if(source[temp - 1] > 0) {
                source[temp - 1] = - source[temp-1];
            }
        }
        int index = 0 ;
        for(int i= 0; i<source.length; i++){
            if(source[i]>0){
                source[index++] = ++i;
            }
        }

        return  Arrays.copyOf(source, index);
    }

    /**
     * [[1,3],[5,9],[11,14]] , []
     *
     * @Title
     * @Description 
     * @Author zxy 
     * @Param [source, interval]
     * @UpdateTime 2020/3/24 19:51 
     * @Return void
     * @throws 
     */
    public static int[][] question57(int[][] source, int[] interval){
        if(interval == null || interval.length != 2){
            return source;
        }
        List<int[]> resultList = new LinkedList<>();
        if(interval[1]< source[0][0]){
            resultList.add(interval);
            resultList.addAll(Arrays.asList(source));
            return (int[][])resultList.toArray();
        }
        if(interval[1] > source[source.length-1][1]){
            resultList.addAll(Arrays.asList(source));
            resultList.add(interval);
            return (int[][])resultList.toArray();
        }
        int[] mergeArray = new int[2];
        for(int i = 0; i< source.length; i++){
            int start = source[i][0];
            int end = source[i][1];
            if(interval[0] >= start && interval[1] <= end){
                return source;
            }
            if(interval[0] >= start && interval[0] <= end){
                mergeArray[0] = start;
            }
            if(interval[1] >= start && interval[1] <= end){
                mergeArray[1] = start;
            }
//            if(interval[0] > end){
//                resultList.add(source[i]);
//            }
            if(interval[1] < start){
                mergeArray[1] = interval[1];
                resultList.add(mergeArray);
            }

            if(interval[0] < start){
                mergeArray[0] = interval[0];
            }
//            if(interval[1] > end && interval[1] < source[i+1][0]){
//                mergeArray[1] = interval[1];
//
//            }


        }
        return (int[][])resultList.toArray();
    }

    public static int[][] question57Sloution1(int[][] source, int[] interval){
        if(interval == null || interval.length != 2){
            return source;
        }
        List<int[]> resultList = new LinkedList<>();
        int[] merge = new int[2];
        boolean flag = false;
        boolean endFlag = false;
        for(int i = 0; i< source.length; i++){
            int start = source[i][0];
            int end = source[i][1];
            if(interval[0] < start && !flag){
                merge[0] = interval[0];
                flag = true;
            }
            if(interval[0] >= start && interval[0] <= end){
                merge[0] = start;
                flag = true;
            }
            if(interval[1] < start){
                merge[1] = interval[1];
                endFlag = true;
                i--;

            }
            if(interval[1] >= start && interval[1] <= end){
                merge[1] = end;
                endFlag = true;
            }
            if(interval[0] > end && !flag){
                resultList.add(source[i]);
            }

            if(endFlag){
                resultList.add(merge);
                resultList.addAll(Arrays.asList(Arrays.copyOfRange(source,i+1, source.length)));
                return resultList.toArray(new int[0][]);
            }
        }
        if(!flag){
            resultList.add(interval);
        }
        return resultList.toArray(new int[0][]);
    }


    /**
     * 3 3
     * 0 0 -> 3 0
     * 0 3 -> 0 0
     * n n 3
     * i j ->()
     * [i,j] -> []
     * 00 -> 02
     * 20 -> 00
     * 10 -> 01
     * [
     *   [1,2,3],
     *   [4,5,6],
     *   [7,8,9]
     * ]
     * 00 01 02
     * 10 11 12
     * 20 21 22
     * @Title
     * @Description
     * @Author zxy
     * @Param [source]
     * @UpdateTime 2020/3/24 23:44
     * @Return int[][]
     * @throws
     */
    public int[][] rotateImage(int[][] source){

        return source;
    }


    public static  int[][] merageInterval(int[][] source){
        int end = source[0][1];
        int[] merge =new int[2];
        merge[0] = source[0][0];
        List<int[]> result = new LinkedList<>();
        for(int i = 1; i< source.length; i++){
            if(source[i][0] > end){
                result.add(new int[]{merge[0], source[i-1][1]});
                    end = source[i][1];
                merge[0] = source[i][0];
                merge[1] = source[i][1];
            } else {
                end = Math.max(end, source[i][1]);
                merge[1] = end;
            }
        }
        result.add(merge);
        return result.toArray(new int[0][]);
    }
    public static int dp(int i){
        int[] dp = new int[i+1];
        //dp[n] = dp[n-1] + dp[n-2];
        dp[0] = 1;
        dp[1] = 1;
        for(int index = 2; index<= i; index++){
            dp[i] = dp[i-1]+dp[i-2];
        }
        return dp[i];
    }

    public static List<String> valid(int n){
        List<String> result = new ArrayList<>();
        slou("", n,result);
        return result;
    }
    public static void slou(String s, int n, List<String> result){
        if(n == 0){
            result.add(s);
        }

    }

    public static int minDepth(TreeNode treeNode){
        if(treeNode == null){
            return 0;
        }
        int left =  minDepth(treeNode.leftNode);
        int right = minDepth(treeNode.rightNode);
        return (left == 0|| right ==0) ?  left + right + 1 : Math.min(left, right) + 1;
    }

    /**
     * @Title
     * @Description
     * @Author zxy
     * @Param [source, k(下标最大差值), t(值)]
     * @UpdateTime 2020/4/2 13:48
     * @Return int
     * @throws
     */
    public static boolean question220(int[] source, int k, int t){
        if(source.length < 2){
            return false;
        }
        int start = 0;
        for(; start <source.length; start++){
            for(int end = start+1; end <= start+k; end++){
                if(Math.abs(source[end] - source[start]) <= t) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Title
     * @Description 
     * @Author zxy 
     * @Param [k, n]
     * @UpdateTime 2020/4/2 14:12 
     * @Return java.util.List<int[]>
     * @throws 
     */
    public static List<int[]> question216(int k,int n){
        List<int[]> result = new LinkedList<>();
        int[] resultArray = new int[k+1];
        sloution216(resultArray, 1, k, n,result);
        return result;
    }

    static void sloution216(int[] res, int index, int k, int n, List<int[]> result){
        if(0 == k || index == 10){
            if(n == 0 && 0 == k) {
                int[] tmep = Arrays.copyOfRange(res, 0, res.length);
                result.add(tmep);
            }
            return;
        }

        res[k] = index;
        sloution216(res,index+1, k-1, n-index, result);
        res[k] = 0;
        sloution216(res,index+1, k, n, result);

    }

    public static void main(String[] args)throws Exception{
        question216(3, 15);
        MaxRange origin = new MaxRange();
        origin.record ="1111";
        MaxRange copy = (MaxRange)origin.clone();
        origin.record ="2222";
        System.out.println(origin.record + copy.record);
        System.out.println("努力的昭君".getBytes().length);

    }


    private String record;
    Integer integer = 1;
    Double aDouble = 1D;
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

package com.zxy.learning.algorithm.hard;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName Tramfer.java
 * @Description
 * @createTime 2019年12月13日 08:44:00
 */
public class Tramfer {

    public static void main(String[] args) {

    }

    public void test(int distance, int cost, int sum, int capicity){
        int[] dpArray = new int[distance];
        for(int i =0; i <= distance; i++){
            int number =  capicity - 2 * distance;
            dpArray[i] = number >0 ? number :0;
        }

        int[] singleArray = new int[distance];
        for(int i =0; i <= distance; i++){
            int number = capicity - distance;
            singleArray[i] = number > 0 ? number :0;
        }
        int left = distance;
        int leftSum = 0;
        while(left >= 0){
            sum -= capicity;
            int realDistance = 0;

            if(sum > 0){
                leftSum += dpArray[distance];
            } else {
                leftSum += singleArray[distance];
            }
        }
    }
}


