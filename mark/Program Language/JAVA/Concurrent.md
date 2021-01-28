#### Synchronized

- 重量级锁，优化到性能与ReentrantLock相差无几

#### Volatile

- JMM
- 内存屏障
- 指令重排序
- 保证对象可见性

```java
 public static void main(String[] args) {
        VolatileObject volatileObject = new VolatileObject();
        new Thread(()->{
            volatileObject.setFlag(true);
            System.out.println("0.0");
        }).start();
        for(;;) {
            if (volatileObject.isFlag()) {
                //永远不会输出，
                //因为另一个线程的修改未对当前线程可见
                System.out.println("1111111");
            }
        }
    }
    static class VolatileObject{
        boolean flag;
        public void setFlag(boolean flag){
            flag = flag;
        }
        public boolean isFlag() {
            return flag;
        }
    }
```



#### AbstractQueuedSynchronizer

- CAS 
- Node 节点链表
- FairSync (获取锁时需要排队)NonfairSync 直接尝试获取，获取失败在尝试排队获取
- 一直循环获取，



#### CAS

- 比较预期值
- ABA 问题 —> 加版本号处理





### Dubbo

#### 架构

- Invoker Invocation
