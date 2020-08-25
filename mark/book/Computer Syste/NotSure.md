## linux 

### Command

- ```shell
  d rwx r-x r-x 6  
  - rwx r-x r-x 1
  
  ```

$$
P = 2^P
$$



进程间通信可以用消息队列，大消息可以用共享内存的方式(不同的虚拟内存地址，相同的物理内存地址)(需要semaphore)

```c

```



IO 读取

打开一个文件描述符 

ulimit 

默认 0,1,2

带缓冲区 与无缓冲区



#### 共享文件

- 描述符表

- 文件表

- V-node 表

  ```C
  
  ```

segment 段

segment descriptors -> Global Descriptor Table

32G = 4K * 1024 * 1024 * 8 * 4
    = 4K * 4K * 4K * 32
	
PTE 64 * 64 * 64 * 32 

4 byte

16 16

4M 1K*4K
1K 

Dynamic Memory Allocator
each processor have brk 变量指向堆顶

when memory released, the pointer will not be deleted

memory mapping 
vm_area_struct 

Fragmentation 
分配{first, second, most}
merge fragmentation
immediate coalescing
Deferred coalescing
allocated block with{head, main, padding, foot} head 32位，low 1 bit low 2 bit  
boundary Tag (block with foot part)
释放块的位置策略 FIFO,地址顺序策略

segregrated Storage 分离存储 (每个类型大小{{1},{2},{4},{8}...{4096}}一个链表，FIFO策略回收)
