package com.zxy.learning.timewheel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName Timer.java
 * @Description
 * @createTime 2019年09月24日 14:48:00
 */
public class Timer {

    private static final Logger logger = LoggerFactory.getLogger(Timer.class);

    private TimeWheel timeWheel;

    private DelayQueue<Bucket> delayQueue = new DelayQueue<>();

    private ExecutorService bossThreadPool;

    private ExecutorService workThreadPool;

    private static Timer INSTANCE ;

    private static AtomicLong taskCounter = new AtomicLong(0);

    private ReentrantLock lock = new ReentrantLock();

    private AtomicLong counter = new AtomicLong(0);

    public static Timer getInstance() {
        if(INSTANCE == null) {
            synchronized(Timer.class){
                if(INSTANCE == null) {
                    INSTANCE = new Timer();
                }
            }
        }
        return INSTANCE;
    }

    public Timer() {
        bossThreadPool = Executors.newFixedThreadPool(1);

        workThreadPool = Executors.newFixedThreadPool(100);
        //初始化基础时间轮（秒盘）60轮 1000毫秒,
        timeWheel = new TimeWheel(100, 600, System.currentTimeMillis(), delayQueue, 1);

        bossThreadPool.execute(()->{
            while(true) {
                //拨动0.1秒
                INSTANCE.advanceClock(100);
            }
        });
    }

    /**
     *
     * @Title: addTask
     * @Description: 新增任务
     * @param @param timeTask    设定文件
     * @return void    返回类型
     * @throws
     */
    private void addTask(TimeTask timeTask) {
        //达到运行时间（加入的任务当前立马执行） 或者 被取消所以失败
        if(!timeWheel.addTask(timeTask)) {
            //没有被取消
            if(!timeTask.isCancle()) {
                Runnable task = timeTask.getTask();
                FutureTask<Integer> futureTask = new FutureTask<Integer>((Task)task);
                workThreadPool.submit(futureTask);
            }
            taskCounter.incrementAndGet();
        }
    }


    /**
     *
     * @Title: advanceTask
     * @Description: 拨动时间
     * @param @param time    设定文件
     * @return void    返回类型
     * @throws
     */
    private void advanceClock(long time) {
        try {
            Bucket bucket = delayQueue.poll(time, TimeUnit.MILLISECONDS);
            if (bucket != null) {
                lock.lock();
                try {
                    while (bucket != null) {
                        timeWheel.advanceClock(bucket.getExpiration());
                        bucket.flush(this::addTask);
                        bucket = delayQueue.poll();
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            logger.error("延迟队列异常");
        }
    }

    static class Task implements Runnable, Callable{

        private long excuteTime;

        public Task(long excuteTime) {
            this.excuteTime = excuteTime;
        }

        @Override
        public Object call() throws Exception {
            logger.error("call: {} : {} ", excuteTime,  excuteTime - System.currentTimeMillis());
            return new Random().nextInt();
        }

        @Override
        public void run() {
            logger.error("run: {} : {} : {}", excuteTime , excuteTime - System.currentTimeMillis());
        }
    }

    public static void main(String[] args) {
        Timer timer = Timer.getInstance();
        ExecutorService workThreadPool = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 30; i++){
            workThreadPool.submit(()->{
                for (int j = 0; j < 1000; j++) {
                    long excuted = 100 * new Random().nextInt(600);
                    timer.addTask(new TimeTask(new Task(System.currentTimeMillis() + excuted ), excuted));
                    try {
                        Thread.sleep(new Random().nextInt(10000));
                    } catch (Exception e){

                    }
                }
                System.out.println(taskCounter.get());
            });
        }

//		for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 1000; j++) {
//                long excuted = 1000 * new Random().nextInt(40000);
//                timer.addTask(new TimeTask(new Task(System.currentTimeMillis() + excuted), excuted));
//            }
//        }
    }
}
