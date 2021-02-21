### Redis

### 优化

* key尽量短小
* 对key进行分区，让每个集合都能满足max-ziplist-entries    和 max-ziplist-value 设置的最大值(value 在适合的大小范围内可以使用压缩格式，减小内存使用)

##### 分布式锁

##### 操作方式

* Set key my_random_value nx px 3000

  set 随机值是防止在并发中，获得锁的线程A阻塞超时释放了锁，B获得了锁，然后A从阻塞中恢复，并释放了B的锁。

  非原子性操作都应由LUA 脚本来执行，保证原子性。

  ##### 问题点

  * redis 的主从复制是异步的，在主挂掉，主从切换期间。A线程已经获得了锁，B线程重新又获得了锁（主从复制，锁还未复制过来）
  * 在线程阻塞或者网络阻塞中，导致客户端获得的锁过期的情况。
  * 锁的超期时间（太长或者太短怎么办，如何设置合理的时间）

##### 安装Redis

* windows 上安装 redis 最新版 搜索关键字  cygwin redis 5

##### 系统初始化

- 设置回调（在系统进程被Kill时触发）
- 加载配置
- 启动服务
- 监听端口
- 初始化相关资源和线程池等



#### 关键对象

```c
typedef struct redisObject{
    //类型 4 byte (string, list, set, zset, hash)
    unsigned type:4;
    //编码 4 byte (ziplist, skiplist,)
    unsigned encoding:4;
    //lru 时间, 24字节
    unsigned lru:LRU_BITS;
    //引用计数 (引用计数为0才会被真正删除)，redis 本身对 0-10000中的数字进行池化复用
    int refcount; 
    //数据指针
    void *ptr
} robj;

typedef struct redisDb {
    // 一个数据库，就是一个kv字典，查找出 k 后，才能确定其数据类型 如 string, hash, list, set, zset
    dict *dict;                 /* The keyspace for this DB */
    // 过期数据队列
    dict *expires;              /* Timeout of keys with a timeout set */
    dict *blocking_keys;        /* Keys with clients waiting for data (BLPOP) */
    dict *ready_keys;           /* Blocked keys that received a PUSH */
    dict *watched_keys;         /* WATCHED keys for MULTI/EXEC CAS */
    struct evictionPoolEntry *eviction_pool;    /* Eviction pool of keys */
    // 数据库号, 默认是 16, 如果想支持更多数据库号，改外部db数组大小，增大这个值就可以了
   int id;                     /* Database ID */
    long long avg_ttl;          /* Average TTL, just for stats */
} redisDb;
```



#### 数据的删除处理主要有两种，一个是DEL命令的删除，一个是过期数据的删除



#### Command

```c
object encoding <key> 编码格式
       freq <key> 获取频率
       ldletime <key> 空闲时间
       refcount <key>  value 的引用计数
```

