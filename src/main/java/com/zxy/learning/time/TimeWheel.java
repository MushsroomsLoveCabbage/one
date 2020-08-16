package com.zxy.learning.time;


import com.zxy.learning.time.Bucket;
import com.zxy.learning.time.TimeTask;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName TimeWheel.java
 * @Description
 * @createTime 2019年09月24日 19:23:00
 */
public class TimeWheel {

    //private static final Logger logger = LoggerFactory.getLogger(TimeWheel.class);

    /**
     * 一个时间槽的时间
     */
    private final long tickDuration;

    /**
     * 时间轮 大小
     */
    private final int wheelSize;

    /**
     * tickDuration * wheelSize
     */
    private long interval;

    /**
     * 槽
     */
    private final Bucket[] wheel;

    /**
     * 时间轮指针
     */
    private long currentTimeStamp;

    /**
     * 上级时间轮
     */
    private volatile TimeWheel overFlowWheel;

    private DelayQueue<Bucket> delayQueue;

    //时间轮级别
    private int level;

    public AtomicLong taskCounter = new AtomicLong(0);

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param tickDuration
     * @param
     * @param
     */
    public TimeWheel(long tickDuration, int wheelSize, long currentTimeStamp, DelayQueue<Bucket> delayQueue, int level) {
        this.tickDuration = tickDuration;
        this.wheelSize = wheelSize;
        this.delayQueue = delayQueue;
        this.interval = wheelSize * tickDuration;
        this.currentTimeStamp = currentTimeStamp - (currentTimeStamp % tickDuration);
        this.wheel = new Bucket[wheelSize];
        for (int i = 0; i < wheelSize; i++) {
            wheel[i] = new Bucket();
        }
        this.level = level;
    }

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: add
     * @Description:
     */
    public boolean addTask(TimeTask timeTask) {
        long expireTimeStamp = timeTask.getExpireTimeStamp();
        long delayMs = expireTimeStamp - currentTimeStamp;
        if (timeTask.isCancle() || delayMs < tickDuration) {
            return false;
        } else {
            if (delayMs < interval) {
                int bucketIndex = (int) (((expireTimeStamp) / tickDuration) % wheelSize);
                Bucket bucket = wheel[bucketIndex];
                bucket.addTask(timeTask);

                if (bucket.setExpire(expireTimeStamp - (expireTimeStamp % tickDuration))) {
                    delayQueue.offer(bucket);
                }
                taskCounter.incrementAndGet();
            } else {
                //二级时间轮
                TimeWheel timeWheel = getOverflowWheel();
                timeWheel.addTask(timeTask);
            }
        }
        return true;
    }

    /**
     * @param @param timeMs    设定文件
     * @return void    返回类型
     * @throws
     * @Title: advanceClock
     * @Description: 更新时间
     */
    public void advanceClock(long timestamp) {
        if (timestamp >= currentTimeStamp + tickDuration) {
            currentTimeStamp = timestamp - (timestamp % tickDuration);
            if (overFlowWheel != null) {
                this.getOverflowWheel().advanceClock(timestamp);
            }
        }
    }

    private TimeWheel getOverflowWheel() {
        if (overFlowWheel == null) {
            synchronized (this) {
                if (overFlowWheel == null) {
                    overFlowWheel = new TimeWheel(interval, wheelSize, currentTimeStamp, delayQueue, level + 1);
                }
            }
        }
        return overFlowWheel;
    }

    public Bucket[] getWheel() {
        return wheel;
    }
}
