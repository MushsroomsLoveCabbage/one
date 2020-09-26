***Consistency 要求的是对多个节点相同对象的值相同，而非正确的***

***Strong Consistency***

- ensure that only consistent state can be seen
- (Sequential Consistency)

***Weak Consistency***

- for when the fast access requirement dominates



***Total Order***

- 时间戳，来给所有操作排序。在分布式环境下，不考虑代价的情况下，例如google 的spanner
- 

***Linearizability Consistency***

- total order
- 任何一次读都读到最新的值
- 系统中的所有进程看到的操作顺序和全局时钟下的顺序（Total Order）一样

***Sequential Consistency***

- partial order（single processor）

- 任何读都读取到最新的值(有观察者观察到最新值，旧值就不在被可见)

  ```
  the result of any execution is the same as if the operations of all the processors were executed in some sequential order, and the operations of each individual processor appear in this sequence in the order specified by its program
  (所有节点的操作以某种顺序排序，单个节点上的操作顺序依然按照单独顺序)
  ```

***Causal Consistency***

- 

分布式数据库的commit ->atomic commit
non blocking Weak Atomic Commitment = Consensus = uniform consensus

non blocking Atomic Commitment (Non Triviality)要求没有failure 发生
non blocking Weak Atomic Commitment （Non Triviality 要求没有任何Node 被怀疑failure）

2PC (Blocking Atomic Commitment) -> 节点挂了 ->  block ->高可用解决block
高可用需要 -> replicated state machine -> Consensus

Atomic commit
-> Consensus 
-> linearizability Consistency(看起来只有一个机器) 
-> sequential Consistency (Atomic BroadCast) 
-> causal Consistency  因果一致性 
-> FIFO Consistency
-> Eventually Consistency

***Sequential Consistency***

- the result of any execution is the same as if the operations of all the processors were executed in some sequential order, and the operations of each individual processor appear in this sequence in the order specified by its program

#### 

***Patterns, Principles, and Practices of Domain-Driven Design***

- If your application is completely data-centric and truly qualifies for a pure CRUD solution. where every operation is basically a simple database query to Create, Read, Update, or Delete.you don’t need DDD.

