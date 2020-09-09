#### 总体

master, chunkServer, chunk(64 MB) ->64bit handle(句柄),

master manage all metedata

##### metadata

- file <—> chunk NameSpace
- file <—> Chunk 
- 每个chunk的replica 位置

(控制流) client ->master->client-> chunkserver master - >chunkserver replica

(master tell client chunkserver, chunk offset etc info)

client-> chunkserver replica ->chunkserver replica->chunkserver replica



##### Consistency

- 客户端读不同replica,数据不同，不一致的
- 客户端读不同replica,数据相同， 一致的
- 所有客户端都可以看到上次修改的内容，并且一致，那就是**确定的（Defined）**
  - Data mutations may be writes or record appends，a write causes data to be written at an application-specifield file offset. (when  A, B write at same offset ,will case A or B can not get last mutation operation)

- 一次写入操作，并且没有其他并发操作，那就是Defined
- 多个写入并发成功。即一致但不是确定的。在这种情况下，客户端所能看到的数据通常不能直接体现出其中的任何一次修改
- 失败的写入导致不一致状态



##### single Master(multiple Shadow Master)



