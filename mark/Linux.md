#### Linux

##### 常见监控

* pidstat -t 



##### CPU上下文切换 (Core Processor Unit)

* 上下文切换就是CPU运行时间片按照一定逻辑分隔开来，进行处理任务，在处理任务过程中需要保存被暂停的任务的当前执行状态，比如程序计数器记录的运行位置，还有变量的下标，指针等

* 进程的运行空间分为内核和用户空间（R0 ->R1->R2->R3）

* 用户进程调用系统时候需要进行2次上下文切换，

* 进程上下文切换，线程上下文切换，中断上下文切换

  **vmstat**

  * cs（Context Switch）

  * in（interrupt）每秒中断

  * r (Running or Runnable) 就绪队列长度

  * b(blocked)不可中断睡眠状态

  * vmstat -w 中 cswch(自愿上下文切换) nvcswch

    

