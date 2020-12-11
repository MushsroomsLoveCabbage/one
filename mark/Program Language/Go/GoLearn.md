### GO

#### 任务调度的设计

##### 关键元素

- M-> 操作系统中的线程
- P->并发单元，CPU核心
- G->工作任务



#### 切换点（G主动交出控制权）

- I/O，select
- channel
- 等待锁
- runtime.Gosched()





##### 参考文档

[go语言并发之MPG模型](https://zhuanlan.zhihu.com/p/62683990)
