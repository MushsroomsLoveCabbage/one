### 待合并内容

------

### Redis

#### 复制缓冲区 & 复制积压缓冲区

##### 复制缓冲区

- 复制缓冲区用于在从节点全量同步时候，存储复制期间主节点处理的的新的命令
- 复制缓冲区积压溢出，会导致连接关闭，然后重新开始全量同步
- client-output-buffer-limit slave  配合缓冲区大小

##### 复制积压缓冲区

- 主从常规同步时候，命令暂存于此
- 长时间断连接，会导致命令积压,被覆盖

- repl_backlog_size

##### 高性能和省内存始终都是应用Redis要关注的重点

| 字符串大小（次方） | SDS数据结构大小 |
| ------------------ | --------------- |
| 5                  | 1               |
| 8                  | 3               |
| 16                 | 5               |
| 32                 | 9               |
| 64                 | 17              |

整数共享对象在LRU 和 Ziplist 两种情况下会失效

#### Redis 命令协议（REdis Serialization Protocol）

- **命令**：这就是针对不同数据类型的操作命令。例如对String类型的SET、GET操作，对Hash类型的HSET、HGET等，这些命令就是代表操作语义的字符串。
- **键**：键值对中的键，可以直接用字符串表示。
- **单个值**：对应String类型的数据，数据本身可以是字符串、数值（整数或浮点数），布尔值（True或是False）等。
- **集合值**：对应List、Hash、Set、Sorted Set类型的数据，不仅包含多个值，而且每个值也可以是字符串、数值或布尔值等。
- **OK回复**：对应命令操作成功的结果，就是一个字符串的“OK”。
- **整数回复**：这里有两种情况。一种是，命令操作返回的结果是整数，例如LLEN命令返回列表的长度；另一种是，集合命令成功操作时，实际操作的元素个数，例如SADD命令返回成功添加的元素个数。
- **错误信息**：命令操作出错时的返回结果，包括“error”标识，以及具体的错误信息。

- 协议开头字符及对应意义 **+简单字符串, $长字符串, : 整数, - 错误, *数组**

- 

  ```
  telnet 实例IP 实例端口
  auth password
  ```

#### 

------

#### 一致性Hash

```java
package com.zxy.learning.hash;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName ConsistentHash.java
 * @Description
 * @createTime 2019年11月08日 16:34:00
 */
public class ConsistentHash<T> {

    // Hash函数接口
    private final IhashService iHashService;

    // 每个机器节点关联的虚拟节点数量
    private final int numberOfReplicas;

    // 环形虚拟节点
    private final SortedMap<Long, T> circle = new TreeMap<>();

    public ConsistentHash(IhashService iHashService, int numberOfReplicas, Collection<T> nodes) {
        this.iHashService = iHashService;
        this.numberOfReplicas = numberOfReplicas;
        for (T node : nodes) {
            add(node);
        }
    }

    /**
     * 增加真实机器节点
     *
     * @param node T
     */
    public void add(T node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            circle.put(this.iHashService.hash(node.toString() + i), node);
        }
    }

    /**
     * 删除真实机器节点
     *
     * @param node T
     */
    public void remove(T node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            circle.remove(this.iHashService.hash(node.toString() + i));
        }
    }

    public T get(String key) {
        if (circle.isEmpty()){
            return null;
        }
        long hash = iHashService.hash(key);

        // 沿环的顺时针找到一个虚拟节点
        if (!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }
}
package com.zxy.learning.hash;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName HashServiceImpl.java
 * @Description
 * @createTime 2019年11月08日 16:27:00
 */
public class HashServiceImpl implements IhashService {
    @Override
    public long hash(String key) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;
        ByteOrder byteOrder = byteBuffer.order();
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        long m = 0xc6a4a793bd1e995L;
        int r = 47;
        long h = seed ^ (byteBuffer.remaining() * m);
        long k;
        while(byteBuffer.remaining() >= 8){
            k = byteBuffer.getLong();
            k *= m;
            k ^= k >>>r;
            k *= m;
            h ^= k;
            h *= m;
        }
        if(byteBuffer.remaining() > 0){
            ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            finish.put(byteBuffer).rewind();
            h ^= finish.getLong();
            h *= m;
        }
        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        byteBuffer.order(byteOrder);
        return h;
    }
}
package com.zxy.learning.hash;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName IhashService.java
 * @Description
 * @createTime 2019年11月08日 16:27:00
 */
public interface IhashService {
    long hash(String key);
}
package com.zxy.learning.hash;

/**
 * @author zxy
 * @version 1.0.0
 * @ClassName Node.java
 * @Description
 * @createTime 2019年11月08日 16:26:00
 */
public class Node<T> {

    private String ip;

    private String name;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }
}

```

------

