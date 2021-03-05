### System Design

#### 1. Common 

* 设计与架构
* 扩展性、容错性、延迟要求
* 资源需求
  * 对于我们所要求的QPS和latency，需要多少台机器，其中CPU, 内存，硬盘等资源都是如何配置

#### 2. Core Idea

* Pros and cons 任何东西都有其正反面
* Everything is trade-off 任何方案都需要取舍
*  hybrid approach 

#### 3. 关注点

##### 3.1 Scalability (拓展性、伸缩性)

* every server contains exactly the same codebase and does not store any user-related data, like sessions or profile pictures, on local disc or memory.

##### 3.2 Avaliable

##### 3.3 Reliable

##### 3.4 Fault-tolerance

##### 3.5 Latency



### 4. System Design

* articulate your problem
* Theoretical study + empircial experiments
* DynamoDB 
* 
