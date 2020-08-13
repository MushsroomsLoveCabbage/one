package com.zxy.learning.timewheel;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName TimeTask.java
 * @Description  时间run任务
 * @createTime 2019年09月24日 19:23:00
 */
public class TimeTask  {

    private Runnable task;

    private long delayMs;

    private long expireTimeStamp;

    private volatile boolean cancle;

    protected Bucket bucket;

    protected TimeTask next;

    protected TimeTask pre;

    public String desc;

    public TimeTask(Runnable task, long delayMs) {
        this.task = task;
        this.delayMs = delayMs;
        this.bucket = null;
        this.next = null;
        this.pre = null;
        this.expireTimeStamp = System.currentTimeMillis() + delayMs;
        this.cancle = false;
    }

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public long getDelayMs() {
        return delayMs;
    }

    public void setDelayMs(long delayMs) {
        this.delayMs = delayMs;
    }

    public long getExpireTimeStamp() {
        return expireTimeStamp;
    }

    public void setExpireTimeStamp(long expireTimeStamp) {
        this.expireTimeStamp = expireTimeStamp;
    }

    public boolean isCancle() {
        return cancle;
    }

    public void setCancle(boolean cancle) {
        this.cancle = cancle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
