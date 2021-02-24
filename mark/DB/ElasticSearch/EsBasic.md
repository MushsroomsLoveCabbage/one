### ES的基本信息

------

参考手册  ----  [官方资料](https://www.elastic.co/guide/cn/elasticsearch/guide/current/_distributed_nature.html)

#### 基本属性

* 天然分布式，且透明
* 集群中任一节点的请求,路由到存有相关数据的节点
* 如果只有单一节点，那分片和副本在同一个节点上，副本是处于unassigned 状态
* 你的应用需要意识到 Elasticsearch 的近实时的性质，并接受它的不足。

#### 核心概念

* Lucene
* Cluster
* Node （coordinating Node 负责数据写入，Master Node）
* Shard
* Index
* Document & Field
* Key
* Type
* Replica

### 核心的操作

------

#### 写入

![](..\..\resource\ElasticSearch\ESwrite.png)

##### Segement 

- 对整个数据集进行数据分段，每个segement一个CheckPoint


- 数据在写入document 之后，需要写入更新到segement才能被看到
- segement 合并

##### Refresh

* 把`buffer` 中的数据写入到一个新的`Segement`中。(数据即可被搜索到)

##### Merge

* 对多个Segement 文件合并

##### Flush 操作（Commit）

* Buffer 数据 `refresh` 到OS cache 中，清空buffer。写一个CheckPoint到磁盘文件中，里面标志着Commit Point 对应的segment file。同时强行将 `os cache` 中目前所有的数据都 `fsync` 到磁盘文件中去。最后**清空** 现有 translog 日志文件，重启一个 translog，此时 commit 操作完成。

##### translog

* 数据的修改操作在数据完全Commit写入磁盘前，数据处于`buffer` 或者 `OS Cache` 中。如果此时系统宕机就会导致数据丢失。因此用 `translog`(磁盘文件顺序写，吞吐高)来记录数据。translog 是数据写入内`buffer`同时写入translog。 translog默认是`5S`同步刷盘一次，因此最多丢失这段时间的数据。可以手动改为每次写都提交并刷盘(不建议)

##### 数据删除

* 删除操作生成.del文件，把doc标识为deleted  



#### 查询优化

* 保证segement 文件存储在fileSystemCache 中（docment 减少存储非必要数据）
* 数据预热(热点数据，系统后台先查出来，进行加载)
* 禁止深度分页（scroll api 优化，不过只支持类似微博这种不许跳页）

##### 集群

* Master宕机

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

