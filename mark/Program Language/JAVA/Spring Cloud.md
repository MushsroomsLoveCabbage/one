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

### 2.Feign

------



### 3.Hystix

------



### 4.Robbin



### 5.Bus