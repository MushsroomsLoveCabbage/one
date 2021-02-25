## Spring Cloud

------

### 1.Eureka

------

##### 1.1服务注册

```java
//eureka服务端
//服务注册
PeerAwareInstanceRegistryImpl.register();
//服务定时驱逐
AbstractInstanceRegistry.deltaRetentionTimer.schedule(getDeltaRetentionTask(),3*60*1000,3*60*1000)
//客户端
DiscoveryClient.initScheduledTasks() 
//定时心跳任务续约
TimedSupervisorTask
```

##### 1.2服务订阅

* Client 默认30S 拉取一次信息

##### 1.3 服务缓存

```java
ReadOnlyCacheMap
ReadWriteCacheMap    
Registry(注册表)
30s 注册表向上两个实体同步(清楚数据)
注册新实例时候会过期 ReadWriteCacheMap中 的数据
    
```



### 2.Feign

------

* 负责请求封装代理

### 3.Hystrix

------

* 负责系统熔断和降级
* 由单独的线程池去处理用户请求。

### 4.Robbin

------

* 负责负载均衡

### 5. Zuul

------



### 6. Bus

------

