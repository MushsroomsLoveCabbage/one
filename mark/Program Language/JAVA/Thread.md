#### Thread


##### JAVA线程状态
- New（只是构建对象，并未处于线程状态）
- Runnable
- Blocked
- WAITING
- TIMED_WAIT
- TERMINATED
- Running

##### 操作系统内线程状态

- RunnableS
- Running
- Blocked（java 的 blcked,waiting, timed_wait 都对应的这个状态）
##### JAVA - Operation System
java 中线程状态的最后都会转换映射到系统内核的线程状态，因此对线程的操作也会转化为对系统操作线程的操作。需要关联起来考虑。
#### Object 

- wait ，notify 方法都是针对获得了Monitor 的对象，把持有monitor的对象挂起或者是唤醒正在

##### Yield ,sleep(0) , sleep(1)的区别

- yield 会主动释放时间片，让步给所有线程一起竞争
- sleep是释放当前时间片，并休眠，让资源给其他线程
- sleep(0)所有线程一起，sleep(1)除当前线程外一起竞争。

