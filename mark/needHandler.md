### Algorithm

##### Data block

##### chunk 

*  we just need to care about the last level data access.(硬盘)

##### Cache aware algorithm



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

fork() 函数 新增一个子 进程并继续与父进程共同执行下面的代码

select version()
group by col (order by col 默认的) order by null 干掉排序

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

netstat -ant|find  / Igrep "192.168.1.1"
netstat -na -p tcp| findstr 80 | find  "ESTABLISH"
netstat -an -p tcp | find "127.0.0.1" | find  "2112"
netstat -an |find  ":80" 



Horizontal Scaling
hierarchy
Vertical Scaling
Cacheing
Loading Balancing
Database replication
Database partitioning

performance vs scalability
Latency vs Scalability
Availability vs Scalability

A service is scalable if it results in increased performance in a manner proportional(成比例的) to resources added.

aim for maximal throughput with acceptable latency.

Fail-over adds more hardware and additional complexity.

hierarchical


verify service integrity

benchmark and profile to simulate and uncover bottlenecks

efficient retrieval of key ranges

Don't focus on nitty gritty details for the following articles, instead:

Identify shared principles, common technologies, and patterns within these articles
Study what problems are solved by each component, where it works, where it doesn't
Review the lessons learned

http://dancres.github.io/Pages/

semi structure


video works
video format

adaptive bitrate streaming
multiple rendition 
以一定条件分割文件,推送数据流时候按segment推送，manifest头文件信息里包括文件的大小，数据流的格式