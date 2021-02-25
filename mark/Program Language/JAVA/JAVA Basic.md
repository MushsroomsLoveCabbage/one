## 基础知识点

### 6.Concurrent

------

#### 6.1 Lock

- Synchronized 重量级锁，优化到性能与ReentrantLock相差无几
- 可重入锁
- 自旋锁
- 偏向锁

#### 6.2 Volatile

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

#### 6.3 AbstractQueuedSynchronizer

- CAS 操作
- Node 节点链表
- FairSync (获取锁时需要排队). NonfairSync 直接尝试获取,获取失败在尝试排队获取
- 一直循环获取
- state

#### 6.4 CAS 

- 比较预期值，符合就改变，不符合就失败

- ABA 问题 -> 加版本号处理

- 无锁

#### 6.5 CountDownLatch, CyclicBarrier, Semaphore

  ```java
  //  CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；而CyclicBarrier是可以重用的。
  public static void main(String[] args){
          int number = 4;
          CyclicBarrier cyclicBarrier = new CyclicBarrier(number, ()->{
              System.out.println("当前线程:" + Thread.currentThread().getName());
          });
  
          for(int i = 0; i < 10; i++){
              new Writer(cyclicBarrier).run();
          }
      }
  
      static class Writer extends Thread{
  
          private CyclicBarrier cyclicBarrier;
  
          public Writer(CyclicBarrier cyclicBarrier) {
              this.cyclicBarrier = cyclicBarrier;
          }
  
          @Override
          public void run() {
              System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
              try {
                  //以睡眠来模拟写入数据操作
                  Thread.sleep(5000);
                  System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                  try {
                      cyclicBarrier.await(2000, TimeUnit.MILLISECONDS);
                  } catch (TimeoutException e) {
                      e.printStackTrace();
                  }
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }catch(BrokenBarrierException e){
                  e.printStackTrace();
              }
              System.out.println(Thread.currentThread().getName()+"所有线程写入完毕，继续处理其他任务...");
          }
      }
  
  /* CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：
   * CountDownLatch是不能够重用的，
   * CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；
   */
   public static void main(String[] args){
  
          CountDownLatch countDownLatch = new CountDownLatch(5);
  
          for(int i = 0; i < 10; i++) {
              new Worker(countDownLatch).start();
          }
          try {
              System.out.println("5...");
              countDownLatch.await();
              System.out.println("5个子线程已经执行完毕");
              System.out.println("继续执行主线程");
          } catch (Exception e){
  
          }
  
      }
  
      static class Worker extends Thread{
          private CountDownLatch countDownLatch;
  
          public Worker(CountDownLatch countDownLatch) {
              this.countDownLatch = countDownLatch;
          }
  
          @Override
          public void run() {
              try {
                  System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                  Thread.sleep(3000);
                  System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                  countDownLatch.countDown();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }
  
  // Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。
   public static void main(String[] args){
  
          Semaphore semaphore = new Semaphore(5);
  
          for(int i = 0; i < 10; i++){
              new worker(i, semaphore).start();
          }
  
      }
  
      static class worker extends Thread{
  
          private int num;
  
          private Semaphore semaphore;
  
          public worker(int num,Semaphore semaphore) {
              this.semaphore = semaphore;
              this.num = num;
          }
  
          @Override
          public void run() {
              try {
                  semaphore.acquire();
                  System.out.println("工人"+this.num+"占用一个机器在生产...");
                  Thread.sleep(2000);
                  System.out.println("工人"+this.num+"释放出机器");
                  semaphore.release();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }
  ```
