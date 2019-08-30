## 树

### B-Tree(m阶BalanceTree)
* 1)每个节点最多能有m个孩子
* 2)除根节点与叶子节点外每个节点至少有ceil(m/2)个节点 
* 3)所有叶子节点在同一层
4)枝节点的关键字数 >=ceil(m/2)-1 && <= m-1
### B+Tree
* 所有叶子节点包含所有关键字,指向关键字的指针。
* 每个节点的关键字数和孩子数相等
***优化点***
相较于B-Tree,同一磁盘内存放的节点更多（非叶子节点不存关键字索引）

### B*Tree

***优化点***
* 相较于B+树,非叶子节点增加指向兄弟的指针

### BST(BinarySearchTree)
* 时间在(O(log N) - O(N)) 最坏，退化为链表。

### AVL(二叉平衡搜索树)(AVL是严格的平衡二叉树)


### RBST(红黑树)
* 根节点，叶子节点(nil)为黑
* 红节点的子节点为黑
* 任意节点到叶子节点的路径黑节点树相同