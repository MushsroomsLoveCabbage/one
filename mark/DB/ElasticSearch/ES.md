#### ES

##### inverteindex 逆向索引

- 索引建立后不再进行修改

  - 避免了操作加锁
  - 索引可以直接加载进内存使用而不用担心修改

  

  ##### segement 

- 对整个数据集进行数据分段，每个segement一个checkPoint

 
- 数据在写入document 之后，需要写入更新到segment才能被看到

- 将缓存中的文档写入segment，segment数据输入到os Cache 中并打开segment使之可以被搜索的过程叫做refresh.refresh 默认1s

- 数据删除 通过记录已删除文档，查询后过滤的方式处理(.del 文件)

- 防丢失 translog

- segement 合并
