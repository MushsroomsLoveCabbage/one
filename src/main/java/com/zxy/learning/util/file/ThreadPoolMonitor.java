package com.zxy.learning.util.file;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;

public class ThreadPoolMonitor {

    ExecutorService executorService = Executors.newCachedThreadPool();
    ThreadPoolExecutor threadPoolExecutor=  new ThreadPoolExecutor(0,Integer.MAX_VALUE,
                                      60L,TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    public void test(){
        threadPoolExecutor.getActiveCount();
        threadPoolExecutor.getCompletedTaskCount();
        threadPoolExecutor.getCorePoolSize();
        threadPoolExecutor.getQueue().size();
    }
}
