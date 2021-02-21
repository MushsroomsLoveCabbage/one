### Redis 基础部分

#### 高性能和省内存始终都是应用Redis要关注的重点

------

#### 主从数据同步

##### COW(Copy On Write(Linux 的特性))

- bgsave 进程
  * Snapshot 依赖 Copy-on-Write 技术
    数据在被修改时,会对数据进行复制（缺页中断，复制页数据给父子进程分别使用）（因此在半数内存被占用，大量键值对被频繁更新的场景下，会内存不足）
- 子进程 

##### Rehash

- 装载因子 >= 1  & 表允许进行hash
- 装载因子 >=5 
- Rehash 是在定时任务中执行

##### 注意点

* 用ring buffer 来记录主从的操作指令偏移量，(落后很多会导致覆盖 repl_backlog_size)

```lua
//lua
local current
current = redis.call("incr",KEYS[1])
if tonumber(current) == 1 then
    redis.call("expire",KEYS[1],60)
end

if redis.call("get",KEYS[1]) == ARGV[1] then
    return redis.call("del",KEYS[1])
else
    return 0
end
```

#### 复制缓冲区 & 复制积压缓冲区

##### 复制缓冲区

- 复制缓冲区用于在从节点全量同步时候，存储复制期间主节点处理的的新的命令
- 复制缓冲区积压溢出，会导致连接关闭，然后重新开始全量同步
- client-output-buffer-limit slave  配合缓冲区大小

##### 复制积压缓冲区

- 主从常规同步时候，命令暂存于此
- 长时间断连接，会导致命令积压,被覆盖

- repl_backlog_size

#### 命令

- OBJECT ENCODING {key} 查看编码

#### 数据结构

| 基本数据类型 | 底层编码类型 | 特点                   |                                     |
| ------------ | ------------ | ---------------------- | ----------------------------------- |
| STRING       | INT          | 存整数 （long 型整数） |                                     |
| STRING       | EMBSTR       | 简化实现SDS （44字节） | redisObject 和sds的分配空间连在一起 |
| STRING       | RAW          | SDS （字节）           | redisObject 和sds的分配独立         |
| LIST         | ZIPLIST      |                        |                                     |
| LIST         | LinkedList   |                        |                                     |
| HASH         | ZIPLIST      |                        |                                     |
| HASH         | HashTable    |                        |                                     |
| SET          | INT_SET      |                        |                                     |
| SET          | HashTable    |                        |                                     |
| ZSET         | ZIPLIST      |                        |                                     |
| ZSET         | SKIPLIST     |                        |                                     |

------

#### SDS数据结构

| 字符串大小（次方） | SDS数据结构大小 |
| ------------------ | --------------- |
| 5                  | 1               |
| 8                  | 3               |
| 16                 | 5               |
| 32                 | 9               |
| 64                 | 17              |

整数共享对象在LRU 和 Ziplist 两种情况下会失效

#### 数据结构实现

```c
typedef struct redisObject{
     //类型
     unsigned type:4;
     //编码
     unsigned encoding:4;
     //指向底层数据结构的指针
     void *ptr;
     //引用计数
     int refcount;
     //记录最后一次被程序访问的时间
     unsigned lru:22;
 
}robj

//SDS
struct sdshdr{
     //记录buf数组中已使用字节的数量
     //等于 SDS 保存字符串的长度
     int len;
     //记录 buf 数组中未使用字节的数量
     int free;
     //字节数组，用于保存字符串
     char buf[];
}

//链表节点
typedef  struct listNode{
       //前置节点
       struct listNode *prev;
       //后置节点
       struct listNode *next;
       //节点的值
       void *value;  
}listNode
//链表
typedef struct list{
     //表头节点
     listNode *head;
     //表尾节点
     listNode *tail;
     //链表所包含的节点数量
     unsigned long len;
     //节点值复制函数
     void (*free) (void *ptr);
     //节点值释放函数
     void (*free) (void *ptr);
     //节点值对比函数
     int (*match) (void *ptr,void *key);
}list;
//字典集合
typedef struct dictht{
     //哈希表数组
     dictEntry **table;
     //哈希表大小
     unsigned long size;
     //哈希表大小掩码，用于计算索引值
     //总是等于 size-1
     unsigned long sizemask;
     //该哈希表已有节点的数量
     unsigned long used;
 
}dictht
//字典
typedef struct dictEntry{
     //键
     void *key;
     //值
     union{
          void *val;
          uint64_tu64;
          int64_ts64;
     }v;
 
     //指向下一个哈希表节点，形成链表
     struct dictEntry *next;
}dictEntry

//跳表
typedef struct zskiplist{
     //表头节点和表尾节点
     structz skiplistNode *header, *tail;
     //表中节点的数量
     unsigned long length;
     //表中层数最大的节点的层数
     int level;
 
}zskiplist;

//跳表节点
typedef struct zskiplistNode {
     //层
     struct zskiplistLevel{
           //前进指针
           struct zskiplistNode *forward;
           //跨度
           unsigned int span;
     }level[];
 
     //后退指针
     struct zskiplistNode *backward;
     //分值
     double score;
     //成员对象
     robj *obj;
 
} zskiplistNode

typedef struct intset{
     //编码方式
     uint32_t encoding;
     //集合包含的元素数量
     uint32_t length;
     //保存元素的数组
     int8_t contents[];
 
}intset;

//ziplist
typedef struct ziplist{
    //占用内存
    uint32_t zlbytes; 
    //尾节点偏移
    uint32_t zltail;
    //节点数
    uint16_t zllen;
    //
    struct zlentry head;
    //结尾 特殊值0xFF
    uint8_t zlend; 
}

//ziplist entry
typedef struct zlentry {
    unsigned int prevrawlensize; /* Bytes used to encode the previous entry len*/
    unsigned int prevrawlen;     /* Previous entry len. */
    unsigned int lensize;        /* Bytes used to encode this entry type/len.
                                    For example strings have a 1, 2 or 5 bytes
                                    header. Integers always use a single byte.*/
    unsigned int len;            /* Bytes used to represent the actual entry.
                                    For strings this is just the string length
                                    while for integers it is 1, 2, 3, 4, 8 or
                                    0 (for 4 bit immediate) depending on the
                                    number range. */
    unsigned int headersize;     /* prevrawlensize + lensize. */
    unsigned char encoding;      /* Set to ZIP_STR_* or ZIP_INT_* depending on
                                    the entry encoding. However for 4 bits
                                    immediate integers this can assume a range
                                    of values and must be range-checked. */
    unsigned char *p;            /* Pointer to the very start of the entry, that
                                    is, this points to prev-entry-len field. */
} zlentry;

//quiklistNode 节点
typedef struct quicklistNode {
    struct quicklistNode *prev;
    struct quicklistNode *next;
    unsigned char *zl;
    unsigned int sz;             /* ziplist size in bytes */
    unsigned int count : 16;     /* count of items in ziplist */
    unsigned int encoding : 2;   /* RAW==1 or LZF==2 */
    unsigned int container : 2;  /* NONE==1 or ZIPLIST==2 */
    unsigned int recompress : 1; /* was this node previous compressed? */
    unsigned int attempted_compress : 1; /* node can't compress; too small */
    unsigned int extra : 10; /* more bits to steal for future usage */
} quicklistNode;

/* quicklistLZF is a 4+N byte struct holding 'sz' followed by 'compressed'.
 * 'sz' is byte length of 'compressed' field.
 * 'compressed' is LZF data with total (compressed) length 'sz'
 * NOTE: uncompressed length is stored in quicklistNode->sz.
 * When quicklistNode->zl is compressed, node->zl points to a quicklistLZF */
typedef struct quicklistLZF {
    unsigned int sz; /* LZF size in bytes*/
    char compressed[];
} quicklistLZF;

typedef struct quicklistBookmark {
    quicklistNode *node;
    char *name;
} quicklistBookmark;

//quicList 是 ziplist 和 ziplist 的结合
typedef struct quicklist {
    quicklistNode *head;
    quicklistNode *tail;
    unsigned long count;        /* total count of all entries in all ziplists */
    unsigned long len;          /* number of quicklistNodes */
    int fill : QL_FILL_BITS;              /* fill factor for individual nodes */
    unsigned int compress : QL_COMP_BITS; /* depth of end nodes not to compress;0=off */
    unsigned int bookmark_count: QL_BM_BITS;
    quicklistBookmark bookmarks[];
} quicklist;

//ziplist -> LinkedList
//列表保存元素个数小于512个,每个元素长度小于64字节
// list-max-ziplist-value选项和 list-max-ziplist-entries 

// ziplist -> HT 
// 列表保存元素个数小于512个,
// set-max-intset-entries 

// INTSET -> HT
//列表保存元素个数小于512个,元素都是整数
// set-max-intset-entries 

//ZIPLIST->SKIPLIST
//1、保存的元素数量小于128；保存的所有元素长度都小于64字节。
//zset-max-ziplist-entries 选项和 zset-max-ziplist-value

```

#### Redis 命令协议（REdis Serialization Protocol）

- **命令**：这就是针对不同数据类型的操作命令。例如对String类型的SET、GET操作，对Hash类型的HSET、HGET等，这些命令就是代表操作语义的字符串。

- **键**：键值对中的键，可以直接用字符串表示。

- **单个值**：对应String类型的数据，数据本身可以是字符串、数值（整数或浮点数），布尔值（True或是False）等。

- **集合值**：对应List、Hash、Set、Sorted Set类型的数据，不仅包含多个值，而且每个值也可以是字符串、数值或布尔值等。

- **OK回复**：对应命令操作成功的结果，就是一个字符串的“OK”。

- **整数回复**：这里有两种情况。一种是，命令操作返回的结果是整数，例如LLEN命令返回列表的长度；另一种是，集合命令成功操作时，实际操作的元素个数，例如SADD命令返回成功添加的元素个数。

- **错误信息**：命令操作出错时的返回结果，包括“error”标识，以及具体的错误信息。

- 协议开头字符及对应意义 **+简单字符串, $长字符串, : 整数, - 错误, *数组**

- cmd telnet 实例IP 实例端口
  auth password
  
