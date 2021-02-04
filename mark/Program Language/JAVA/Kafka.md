### Kafka

#### Key word

- consumer

- provider

- consumer offset

- consumer Group

- Rebalance

- Topic

- partition

- Replica

- record (binary )

- offset

  [参考图](http://geek.ft.com/#/column/191?aid=99318)

##### Kafka

- 框架内的精准一次处理语义 
- [Kafka作分布式存储](https://www.confluent.io/blog/okay-store-data-apache-kafka/)
- kafka 作为小型的流处理系统。适用于低需求

#### 基本点

- 顺序读写，
- 网络带宽占满70%就会出现丢包情况。

#### KAFKA 集群参数

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

##### 操作系统参数

ulimit -u  

文件系统 zfs>xfs>ext4>ext3

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

```java

```

##### Controller 控制器管理（）

- 每个Broker 上都有控制器，应用启动是抢注册
- 支持failover 机制
- **rmr /controller**（碰到broker卡死等问题可以在zookeeper 上删除controller 来实现快速的恢复）

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
* replica 在拉取数据时候向leader提交自己的LEO，以此leader 来更新自身的高水位。
* 同时replica 依据数据来更新自己的LEO
* Leader epoch
#### HW + leader epoch
- HW 用来标记当前的已经确认的数据，(需要在从都确认后确定偏移量在更新Leader 的hw ,之后follwer 再下一次拉取更新自己的HW)
- LEO (log end offset) 当前已经获得的消息偏移量
- Leader epoch <version, offset> 
- follwer 在重启之后根据leader epoch 来更新自己的HW 和处理是否数据截断，

### 消息保证（Producer 端，Consumer 端需要自己保证）

##### At most once

##### At least Once

##### Exactly Once Semantics 

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
