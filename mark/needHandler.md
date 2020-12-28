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

Unix
Ox12345678  12 位高字节 
网络字节顺序，即大端法 数据高字节保存在低地址，低字节保存在高地址
unix IO {RIO(缓冲区), 标准IO}
Socketaddr 抽象
bind listen accept 

fault tolerant 
high throughout
move compution node
simple coherency model
large data set
streaming data access (sacrifice 牺牲)

a single NameNode, a master

namespace
number dataNode

Dynamo/Cassandra

数据库就是存储数据的,业务逻辑应该由应用程序处理。

Hbase 
HregionServer{n Hregion} 超过阈值会分裂，生成两个HregionServer
LSM Tree

(mapreduce, Spark)
(Storm,Flink,spark streaming) 数据流切小分片。

spark
split task -> DAG
DAGScheduler
Spark 
Not Thing Boring,actually the way we do,is boring.

* 抽象所带来的复杂度，需要在其他地方大大简化系统整体复杂度。
  * 需求获取,问题抽象，测试，运维，监控，部署，框架运用，
    系统间集成，灵活性，未来拓展性，易用性，系统健壮性，多版本实验性，高层业务决策

fork() 函数 新增一个子 进程并继续与父进程共同执行下面的代码

select version()
group by col (order by col 默认的) order by null 干掉排序
\u unicode \x 16进制
\b 二进制 \o 8进制

CURRENT_TIMESTAMP

ssh -p xx user@ip 
结构化
上下线性
推演

Buffer Manager 

RedoLog  事务日志，防止crash 来减少脏页的刷盘。
undoLog  logical 级别的只针对某一行。
compensation log record

@r1:=@r1+1 AS roleId,
(SELECT @r1:=0) tmp

lsof -i:端口号
netstat -tunlp

select DATE_SUB(now(), INTERVAL ROUND(161)  DAY) from dual;

cat -n log |grep ""
cat -n log |tail  -n +1000 |head -n 100

find / -name  "title"

netstat -ant|find  /I "192.168.1.1"
netstat -na -p tcp| findstr 80 | find  "ESTABLISH"
netstat -an -p tcp | find "127.0.0.1" | find  "2112"
netstat -an |find  ":80" 

jps -l
jstat -gc pid timeInterval number


TCP层的安全可靠是针对于TCP层
消息的可靠传递需要Context
