### Redis 集群

------

#### 基本思路

* 中间代理层
* 修改redis来拓展实现
* redis Cluster集群，客户端+服务端（协助）

------

#### 基本实现方案

* Redis Cluster  [参考](https://redis.io/topics/cluster-tutorial)

  * 主要地目的

    * **automatically split your dataset among multiple nodes**.
    * **continue operations when a subset of the nodes are experiencing failures**

  * hash

    * The user can force multiple keys to be part of the same hash slot by using a concept called *hash tags*.

    * 16384个槽，key CRC16计算

  * **node timeout**.

* Codis

* Twemproxy



------

#### 存在的问题

* 数据丢失情况

  * 主从复制，数据还未复制过去，master宕机		
  * 脑裂问题，主脱离的网络，产生新的主（未完全复制），或者客户端还在与旧的主在交互，导致数据丢失了部分，然后旧master成为新master的从时候清除数据，导致数据丢失

* 解决方式

  * min-slaves-to-write 1

    min-slaves-max-lag 10