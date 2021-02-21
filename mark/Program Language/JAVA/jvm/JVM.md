## JVM 
### 内存模型

JVM基于抽象的逻辑，屏蔽掉不同硬件底层的操作细节实现和差异，保证对内存访问能保证有效一致的机制和规范

* 可见性 （volatile）
* 原子性（synchronize）
* 有序性（volatile, synchronize）

### java 对象的基本构成

* 对象大小必须是8byte 的倍数 
* 对象由 对象头（8）+ 指针（4/8）+ 数据 + padding 
* 数组对象是 对象头（8） + 指针（4/8）+ size(int) + 数据 + padding 
* 默认开启指针压缩，如果内存超过32G 需要关掉（2 32次方 = 32G 即内存超过了指针标记范围）
* 对象类数据在编译时候会从大到小排序
* 对象头 
* ***linux + JVM*** 
 物理内存  <->  swap (磁盘空间) （当物理内存不够使用时,linux把一部分暂时不用的内存移动到磁盘中 swap）
（BIN(引导系统)，内核内存（System -> Buffer -> PageCache），用户内存）
 用户内存（用户进程（代码区，数据区，堆区，栈区，未使用））
 JVM内存(metadata,堆(永久代,老年代,年轻代),栈)

* java中NIO 快的原因在于 1)部分直接操作直接对应了内核内存，减少了数据在用户内存，和系统内存间的复制。2)非阻塞
* JVM 内存 基本等于（永久代, 堆,线程栈,NIO）  

 SWAP+GC 会导致GC时间过长，甚至崩溃。
 因为GC时需要遍历堆内对象，对于已经在swap 区的数据，需要复制回内存。则在遍历过程中需要把整个堆内存往swap写一遍。
 linux 对于swap的回收是滞后的。

 动态年龄计算（survivor中某个年龄的存活的占一半，按这个和MaxTenuringThreshold比较取最小值）

### CMS
***CMS清理过程***
* init-remark  初始标记（STW）
* concurrent-mark 并发标记
* concurrent-preclean 预清理
* concurrent-abortable-preclean 可中止预清理
* final-remark 最终标记（STW）
* concurrent-sweep 并发清除
* concurrent-reset 并发重置状态
两次小暂停来解决单次过长的STW

##### cms清理带来的问题

* 因为清理并不会停止工作线程，但会占用系统的资源使用，会导致系统的吞吐降低
* 非STW，导致在清理过程中会产生一些新的垃圾(浮动垃圾)，需要等下次GC 时候清理

### G1
------
***关键词***
* Region(内存分区)-XX:G1HeapRegionSize<1M-32M> 2的指数
（大对象会有H（Humongous Object ）标记,大于等于region的一半）
* Root Tracing
* SATB(Snapshot-At-The-Beginging)
（三色标记算法 白，灰（对象被标记，但它的field 未被标记或未被标记完），黑（对象被标记，field也被标记完））
* RSet(Remembered Set)
* CSet(Collection Set)
* Pause Prediction Model



### Command
------
* jps -lvm
* jstack {pid}
* jstat -gc {pid}  {timeInterval}  {number}