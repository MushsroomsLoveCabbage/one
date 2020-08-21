package com.zxy.learning.handle.question;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A B两个线程同步执行，
 * A输出1,2,3,4,5....
 * B在A输出100,200,300,时输出1,2,3，
 * 分AB 结果集1,2,3,4...99,100,1,101,102
 */
public class ConcurrentQuestion {

    static Object concurrentObj = new Object();

    static AtomicInteger atomicInteger = new AtomicInteger(0);

    static CountDownLatch countDownLatchA = new CountDownLatch(1);
    static CountDownLatch countDownLatchB = new CountDownLatch(1);

    static Semaphore semaphore = new Semaphore(1);

    static int START_NUMBER = 1;
    //方案1 wait  notify
    //方案2 CountDownLatch
    //方案3 队列

    class ThreadA implements Runnable{
        @Override
        public void run() {
            try {
//                synchronized (concurrentObj){
//                    int start = START_NUMBER;
//                    while(true){
//                        System.out.println(start++);
//                        if(start % 100 == 0){
//                            concurrentObj.notify();
//                            concurrentObj.wait();
//                        }
//                    }
//                }

                BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue();

                int start = START_NUMBER;
                while(true){
                    System.out.println(start++);
                    if(start % 100 == 0){
                        countDownLatchA.countDown();
                        countDownLatchA = new CountDownLatch(1);
                        countDownLatchA.await();
                    }
                }


            } catch (Exception e){

            }

        }
    }

    class ThreadB implements Runnable{
        @Override
        public void run() {
            try {
//                synchronized (concurrentObj){
//                    concurrentObj.wait();
//                    int start = START_NUMBER;
//                    while(true){
//                        System.out.println(start++);
//                        concurrentObj.notify();
//                        concurrentObj.wait();
//                    }
//                }
                int start = START_NUMBER;
                while(true){
                    countDownLatchA.await();
                    System.out.println(start++);

                    countDownLatchA.countDown();

                }
            } catch (Exception e){

            }

        }
    }
}
