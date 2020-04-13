##Hashmap

### 关键部分

* factor 扩容因子 默认 0.75 threshold 阈值 16

* hash key , (table size -1 & hashkey 决定在数组中的位置） 

  hashKey (h= key.hashCode()) ^ (h>>>16) 高16位^低16位 （这样保证高位会参与到hash计算中，不然会因为table size 小，table size的高位全为0，导致key的碰撞变多）

  

* 扩容时候数组对象如果是链表/红黑树拆分，到新数组中的相同下标和扩容数组中位置。（红黑树可能会退化为链表）

*  基本结构是由数组+ 链表/红黑树组成, 在链表数据达到8个的时候会转换为红黑树，并在红黑树对象减少到6个时候会退化为链表。

### 并发死锁问题

* 1.8 put时候 ++size 会导致size 值不准确
* 1.7 resize 时候导致链表生成环。当get 时候导致死锁

### 延伸

* string hashcode  用 31(素数，较好的碰撞和计算量级的平衡)

## ConcurrentHashMap

### 关键部分

* 在数组中的Node<K,V> 对象上加锁，提供良好并发性