package com.zxy.learning.time;

import com.zxy.learning.time.TimeTask;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName Bucket.java
 * @Description
 * @createTime 2019年09月24日 19:23:00
 */
public class Bucket implements Delayed{

    private AtomicLong expiration = new AtomicLong(-1L);

    /** 根节点 */
    private TimeTask root = new TimeTask(null, -1L);

    {
        root.pre = root;
        root.next = root;
    }

    @Override
    public int compareTo(Delayed delayed) {
        if (delayed instanceof Bucket) {
            return Long.compare(expiration.get(), ((Bucket) delayed).expiration.get());
        }
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return Math.max(0, unit.convert(expiration.get() - System.currentTimeMillis(), TimeUnit.MILLISECONDS));
    }

    public long getExpiration() {
        return expiration.get();
    }

    public void setExpiration(AtomicLong expiration) {
        this.expiration = expiration;
    }

    public boolean setExpire(long expire) {
        return expiration.getAndSet(expire) != expire;
    }

    /**
     * 新增任务到bucket
     */
    public void addTask(TimeTask timedTask) {
        synchronized (this) {
            //tail <- root   tail<->task<->root
            TimeTask tail = root.pre;
            timedTask.next = root;
            timedTask.pre = tail;
            tail.next = timedTask;
            root.pre = timedTask;
        }
    }

    /**
     * 从bucket移除任务
     */
    public void removeTask(TimeTask timedTask) {
        synchronized (this) {
            timedTask.next.pre = timedTask.pre;
            timedTask.pre.next = timedTask.next;
            timedTask.next = null;
            timedTask.pre = null;
        }
    }

    /**
     * 重新分配槽
     */
    public synchronized void flush(Consumer<TimeTask> flush) {
        // 从尾巴开始（最先加进去的）
        TimeTask timedTask = root.next;
        while (!timedTask.equals(root)) {
            this.removeTask(timedTask);
            flush.accept(timedTask);
            timedTask = root.next;
        }
        expiration.set(-1L);
    }
}
