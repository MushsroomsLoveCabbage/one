分布式数据库的commit ->atomic commit
non blocking Weak Atomic Commitment = Consensus = uniform consensus

non blocking Atomic Commitment (Non Triviality)要求没有failure 发生
non blocking Weak Atomic Commitment （Non Triviality 要求没有任何Node 被怀疑failure）

2PC (Blocking Atomic Commitment) -> 节点挂了 ->  block ->高可用解决block
高可用需要 -> replicated state machine -> Consensus

Atomic commit
-> Consensus 
-> linearizability(看起来只有一个机器) 
-> sequential consistency (Atomic BroadCast) 
-> causal consistency  因果一致性 
-> FIFO consistency
-> Eventually

***Sequential Consistency***

- the result of any execution is the same as if the operations of all the processors were executed in some sequential order, and the operations of each individual processor appear in this sequence in the order specified by its program



Patterns, Principles, and Practices of Domain-Driven Design

If your application is completely data-centric and truly qualifies for a pure CRUD solution, 
where every operation is basically a simple database query to Create, Read, Update, or Delete.you don’t need DDD.
