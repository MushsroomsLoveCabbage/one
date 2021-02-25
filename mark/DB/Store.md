### Key Word

Open-channel SSD

SSTable 与SSD的Block 对齐 ->  LST-Tree的删除操作与SSD的Block 回收合并



在 [『The Case for Learned Index Structures』](https://link.zhihu.com/?target=https%3A//arxiv.org/pdf/1712.01208.pdf) 文章中，作者提到了一个典型的场景，数据库的索引。传统的索引通常采用 B 树，或 B 树的变种。然而这些数据结构通常是为了一个通用的场景，以及最差的数据分布而进行设计的，并没有考虑到实际应用中数据分布情况



LSM-TREE  (读，写，存储放大)顺序写

WiscKey  key-value 分开存储Range 查询并发读，来达到接近顺序读的性能上限。

