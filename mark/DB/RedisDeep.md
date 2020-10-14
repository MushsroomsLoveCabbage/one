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

