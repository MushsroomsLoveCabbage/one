### Distributed System

#### 1. 总体

##### 1.1 主要目的

- 通过集成多个机器来提供更高的可用性

##### 1.2主要难点

- 网络不可靠
- 机器不可靠

##### 1.3 Core  

- 计算，存储，通信，资源池化
- 协同，调度，追踪与高可用，部署

#### 2. 分布式协调与同步

- 互斥（集中式（引入协调者），分布式，令牌环）
- 选举(bully, raft, zab)
- 事务（XA,TCC,Eventual）
  - XA属于两阶段提交
- 锁
  - redis setnx
  - zookeeper  临时节点，临时顺序节点

#### 3. 分布式通信

- 远程调用
- 发布订阅
- 消息队列

#### 4. 分布式存储

- CAP
- Rehah

#### 5. 分布式高可用

- 负载均衡
- 熔断
- 降级
- 故障恢复

#### 6. Distributed Consensus 

------

##### Consensus Algorithm

- **Raft**

  - Leader, Candidate, follower
  - Leader 有任期（term）
  - 获得选票最多的为Leader

- Bully

  - election message, alive message(ack election), victory message

  - 让ID更大的为Leader

- **Raft ***

- **paxos**

- **ZAB**

  - Leader, follwer, Observer

  - <epoch, vote_id,vote_zxID>
  - 数据最新或者ID最大的为Leader

  

#### 6.参考资料

- https://www.safaribooksonline.com/videos/distributed-systems-in/9781491924914、
- https://youtu.be/Y6Ev8GIlbxc
- https://www.cl.cam.ac.uk/techreports/UCAM-CL-TR-935.pdf
- https://dl.acm.org/doi/pdf/10.1145/3183713.3196937
- https://www.allthingsdistributed.com/files/p1041-verbitski.pdf
- https://www.usenix.org/conference/fast19/presentation/kaiyrakhmet
- https://raft.github.io/ 
