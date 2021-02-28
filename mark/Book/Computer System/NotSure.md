## linux 








#### 共享文件

- 描述符表

- 文件表

- V-node 表

  segment 段

segment descriptors -> Global Descriptor Table



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
