### Linux

#### 1.常见监控

* pidstat -t 

- lsof -i:{port}
- netstat -tunlp|grep {port}

#### 2.CPU上下文切换 (Core Processor Unit)

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


#### 3. 内存

##### 3.1 PageCache

- free -m 查看
- 4K(逻辑上多个BufferCache)

##### BufferCache

- 1K(物理上的磁盘)

##### 4.DMA

* (协助cpu 做数据搬移)
* 把 数据从磁盘文件复制到内核缓冲区。

#### 内核IO体系

- VFS 屏蔽文件系统区别

  ```c
  VFS 层的通用对象包括
  superblock 对应结构体 super_operations 
  定义整个文件系统的元信息
  inode 对应结构体 inode_operation
  inode结构体定义了文件的元数据，比如大小、最后修改时间、权限等，除此之外还有一系列的函数指针
  dentry 对应结构体 dentry_operations
  dentry是目录项，由于每一个文件必定存在于某个目录内
  file 对应结构体 file_operations
  ```

- PageCache(顺序读) / Direct IO

  - 映射层(多个文件系统(Ext系列、XFS等，打开文件系统的文件)以及块设备文件(直接打开裸设备文件)组成)

- 通用块层 (屏蔽物理硬件的区别)

- IO调度层

- 块设备驱动层

参考文献

- [io体系](https://zhuanlan.zhihu.com/p/96391501)