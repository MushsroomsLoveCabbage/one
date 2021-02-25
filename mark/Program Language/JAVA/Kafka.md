## Kafka

### 1. Key word

| **名词**                | **解释**                                                     |
| ----------------------- | ------------------------------------------------------------ |
| Producer                | 消息的生成者                                                 |
| Consumer                | 消息的消费者                                                 |
| ConsumerGroup           | 消费者组，可以并行消费Topic中的partition的消息               |
| Broker                  | 缓存代理,管理连接和消息的转发                                |
| Topic                   | Kafka处理资源的消息源(feeds of messages)的不同分类           |
| Partition               | Topic物理上的分组，一个topic可以分为多个partion,每个partion是一个有序的队列。partion中每条消息都会被分配一个有序的Id(offset) |
| Message                 | 消息，是通信的基本单位。                                     |
| Offset                  | 消息的偏移                                                   |
| Consumers               | 重平衡                                                       |
| Leader/follower/Replica | 这几个概念都是针对的partition                                |
|                         |                                                              |
|                         |                                                              |
|                         |                                                              |

##### 特性和场景

- 框架内的精准一次处理语义 
- [Kafka作分布式存储](https://www.confluent.io/blog/okay-store-data-apache-kafka/)
- kafka 作为小型的流处理系统。适用于低需求
- 顺序读, zero Copy, 批量压缩
  * 在写入数据时，按顺序写入到磁盘文件中

  * 写入并非直接写入，而是使用mmap(Memory Mapped Files)接口.

    ```c
    //mmap 内存映射机制
    //日常读取文件都是需要从文件中读取文件到内核缓冲区，在复制到用户空间。写入时候也是先写入到内核缓冲区再写入到磁盘文件。
    //mmap 直接在内核中分配内存空间映射整个文件，无需再进行缓冲区的IO的多次往复读取写写入。
    
    void  *mmap(void  *start,  size_t length,  int prot,  int flags,  int fd,  off_t offsize)
    int  munmap(void *start,   size_t length) 
    ```

    

- `producer.type`控制写入时候是否每次都flush 

- 网络带宽占满70%就会出现丢包情况。

### 2.核心流程

-------
#### 2.1 选举主broker?

#### 2.2 踢出broker?

#### 2.3 replica 复制数据

#### 2.4 集群的消息的分发

### 3. KAFKA 集群参数

-------

### 1. 总体认知

- 顺序读写，
- 网络带宽占满70%就会出现丢包情况。
- zero copy 体现在consumer上,broker直接向网卡复制数据，无需经过应用内核

### 2. Key word

| 关键词                   | 作用 |
| ------------------------ | ---- |
| consumer、consumer Group |      |
| provider                 |      |
| Broker                   |      |
| Topic                    |      |
| Partition                |      |
| Rebalance                |      |
| Replica/slave/master     |      |
| offset、consumer offset  |      |
| record (binary)          |      |



#### 3. KAFKA 参数

##### 3.1）集群参数

- log.dirs  这是非常重要的参数，指定了Broker需要使用的若干个文件目录路径。要知道这个参数是没有默认值的，这说明什么？这说明它必须由你亲自指定。
- `zookeeper.connect zk1:2181,zk2:2181,zk3:2181/kafka1`和`zk1:2181,zk2:2181,zk3:2181/kafka2`
- `auto.create.topics.enable`：是否允许自动创建Topic。 false 
- `unclean.leader.election.enable`：是否允许Unclean Leader选举。 false
- `auto.leader.rebalance.enable`：是否允许定期进行Leader选举。 false 

- unclean.leader.election.enable  false 
- `log.retention.{hour|minutes|ms}`：这是个“三兄弟”，都是控制一条消息数据被保存多长时间。从优先级上来说ms设置最高、minutes次之、hour最低。
- `log.retention.bytes`：这是指定Broker为消息保存的总磁盘容量大小。
- `message.max.bytes`：控制Broker能够接收的最大消息大小。
- `retention.ms`：规定了该Topic消息被保存的时长。默认是7天，即该Topic只保存最近7天的消息。一旦设置了这个值，它会覆盖掉Broker端的全局参数值。
- `retention.bytes`：规定了要为该Topic预留多大的磁盘空间。和全局参数作用相似，这个值通常在多租户的Kafka集群中会有用武之地。当前默认值是-1，表示可以无限使用磁盘空间。

```shell
创建topic
bin/kafka-topics.sh--bootstrap-serverlocalhost:9092--create--topictransaction--partitions1--replication-factor1--configretention.ms=15552000000--configmax.message.bytes=5242880
修改topic 级别参数
bin/kafka-configs.sh--zookeeperlocalhost:2181--entity-typetopics--entity-nametransaction--alter--add-configmax.message.bytes=10485760
```

##### 3.2) 操作系统参数

* ulimit -u  

* 文件系统 zfs>xfs>ext4>ext3

### 5.kafka 核心流程

##### 5.1 producer 连接Broker流程

* 根据配置文件信息，连接所有的Broker
* 消息直接给具体的主Partition

##### 5.2 consumer连接流程

* 根据配置文件信息，连接所有的Broker，上传指定消费的Topic Partition

#### kafka客户端实现

```java
分区(Partition, MongoDB和Elasticsearch的shard, HBase的region, Cassandra的vnode)
org.apache.kafka.clients.producer.Partitioner 实现
```

```java
压缩
consumer端，或者broker压缩，如果两边设置值不一样会导致在broker 端触发解压缩，重压缩情况。
吞吐量 LZ4 > Snappy > zstd和GZIP
压缩比 zstd > LZ4 > GZIP > Snappy
```

```java
消息无丢失处理
produc
eplication.factor >= 3
min.insync.replicas > 1
确保 replication.factor > min.insync.replicas   
consumer 端 设置为false

拦截器 
org.apache.kafka.clients.producer.ProducerInterceptor
org.apache.kafka.clients.consumer.ConsumerInterceptor

kafka 实现excatly once 
通过enable.idempotence producer端保证幂等(因此只保证单个producer 在单次运行期间的幂等性) 
事务保证多分区间的幂等性，数据要么全部被提交，要么全部取消

```

##### Controller 控制器管理

- 每个Broker 上都有控制器，应用启动是抢注册
- 支持failover 机制
- **`rmr /controller`**（碰到broker卡死等问题可以在zookeeper 上删除controller 来实现快速的恢复）

##### 消费者组重平衡

- 触发条件
  - 新成员变化（新加入或者离开）
  - 分区变化
  - 订阅主题变化
- 通过心跳来通知消费者重平衡（心跳信息携带 REBALANCE_IN_PROGRESS）因此heartbeat.interval.ms 参数可以控制重平衡速率

- 状态
  - Empty
  - Dead
  - PreparedRebalance
  - CompletingRebalance
  - Stable
- joinGroup & syncGroup
- 每次重平衡时候都需要成员重新入组

##### 副本间的同步如何实现

* kafka 为leader 和每个replica 记录一个高水位和LEO 标记，用来标记已经被提交的消息偏移和最大接收偏移，
* replica 在`拉取`数据时候向leader提交自己的LEO，以此leader 来更新自身的高水位。
* 同时replica 依据数据来更新自己的LEO
* Leader epoch
#### HW + leader epoch
- HW 用来标记当前的已经确认的数据，(需要在从都确认后确定偏移量在更新Leader 的hw ,之后follwer 再下一次拉取更新自己的HW)
- LEO (log end offset) 当前已经获得的消息偏移量
- Leader epoch <version, offset> 
- follwer 在重启之后根据leader epoch 来更新自己的HW 和处理是否数据截断，

------

#### Rebalance

##### 发生的时机有三个

- 组成员数量发生变化
  - 需要避免消费太慢被误踢出
  - max.poll.records
  - max.poll.interval.ms（消费不完主动离开）
  - 系统GC 
- 订阅主题数量发生变化
- 订阅主题的分区数发生变化

#### Coordinator

##### Consumer

- consumer 启动先发送findcoordinator 获取broker,然后与broker 建立连接

##### Producer

- KafkaProducer实例创建时启动Sender线程，从而创建与bootstrap.servers中所有Broker的TCP连接。
- KafkaProducer实例首次更新元数据信息之后，还会再次创建与集群中所有Broker的TCP连接。
- 如果Producer端发送消息到某台Broker时发现没有与该Broker的TCP连接，那么也会立即创建连接。
- 如果设置Producer端connections.max.idle.ms参数大于0，则步骤1中创建的TCP连接会被自动关闭；如果设置该参数=-1，那么步骤1中创建的TCP连接将无法被关闭，从而成为“僵尸”连接。

### 5.消息保证（Producer 端，Consumer 端需要自己保证）

------

##### 5.1）At most once

##### 5.2）At least Once

##### 5.3）Exactly Once Semantics 

- Idempotent Producer：Exactly-once，in-order，delivery per partition；
- Transactions：Atomic writes across partitions；
  - 解决幂等性下单个producer 写多个partition 部分失败问题
  - 解决producer 失败重启，导致消息重复问题
- Exactly-Once stream processing across read-process-write tasks；



- 保证手段 (Idempotence, Transaction)
- 参数 enable.idempotence
- 实现逻辑是broker 端多保存一些字段，来比较收到的消息，保证消息不重复（幂等性的只能保证在单事务下的幂等）
- 事务型的producer, Consumer 端需要设置isolation.level 消费隔离级别

```java
producer.initTransactions();
try {
            producer.beginTransaction();
            producer.send(record1);
            producer.send(record2);
            producer.commitTransaction();
} catch (KafkaException e) {
            producer.abortTransaction();
}
```

- (参考文档)[https://cloud.tencent.com/developer/article/1430986]

### 7.kafka 优化改造

------

**参考文章**

[**美团kafka改造**](https://tech.meituan.com/2021/01/14/kafka-ssd.html )

##### 问题

- I/O线程统一将请求中的数据写入到操作系统的PageCache后立即返回,当消息条数到达一定阈值后，Kafka应用本身或操作系统内核会触发强制刷盘操作。当存在consumer 实例消费慢，导致请求的数据已经落盘，会导致因缺页而依据LRU策略刷部分数据磁盘中，导致现有的主要线程消费的数据不在pagecache->往复导致吞吐降低

##### 处理方式

- FlashCache
- Kafka层改造
  - 增加SSD作为缓存
  - 依据数据消息偏移量，来减少非热点数据读刷到SSD。
  - 非缓存的数据直接从HDD中获取
  - LogSegment，后台线程定期把inactive的写到SSD

#### 源码阅读

------



- Core 包 
  - log
  - controller
  - coordinator包下group包
  - network,server
- KafkaApis (broker顶端入口)
- Client包
  - **org.apache.kafka.common.record**。这个包下面是各种Kafka消息实体类
  - **org.apache.kafka.common.network。** 你重点关注下Selector、KafkaChannel就好了，尤其是前者，它们是实现Client和Broker之间网络传输的重要机制。如果你完全搞懂了这个包下的Java代码，Kafka的很多网络异常问题也就迎刃而解了。
  - **org.apache.kafka.clients.producer**它是Producer的代码实现包，里面的Java类很多，你可以重点看看KafkaProducer、Sender和RecordAccumulator这几个类。
  - **org.apache.kafka.clients.consumer包。**它是Consumer的代码实现包。同样地，我推荐你重点阅读KafkaConsumer、AbstractCoordinator和Fetcher这几个Java文件

#### 10.使用场景

- 框架内的精准一次处理语义 
- [Kafka作分布式存储](https://www.confluent.io/blog/okay-store-data-apache-kafka/)
- kafka 作为小型的流处理系统。适用于低需求

#### 12.参考资料

* https://www.cnblogs.com/huxi2b/
* https://www.confluent.io/blog/ 

