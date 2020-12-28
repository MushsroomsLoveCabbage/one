### ES的基本信息

------

参考手册  ----  [官方资料](https://www.elastic.co/guide/cn/elasticsearch/guide/current/_distributed_nature.html)

#### 基本属性

* 天然分布式，且透明
* 集群中任一节点的请求路由到存有相关数据的节点
* 如果只有单一节点，那分片和副本在同一个节点上，副本是处于unassigned 状态
* 你的应用需要意识到 Elasticsearch 的近实时的性质，并接受它的不足。

```java
//刷新索引
POST /_refresh
//刷新部分索引    
POST /blogs/_refresh    
//提交translog 日志
POST /blogs/_flush 

POST /_flush?wait_for_ongoing 
//合并段日志    
```

------

#### 基本启动流程

* electMaster -> gate

