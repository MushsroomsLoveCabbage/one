## Dubbo

#### 1.重要组成部分

| 关键词               | 作用                                               |
| -------------------- | -------------------------------------------------- |
| Consumer (Reference) | 服务调用者                                         |
| Provider (Service)   | 服务提供者                                         |
| ConfigCenter         | 注册中心，存储服务信息                             |
| SPI                  | 动态配置加载机制，在调用时候依据配置决定使用选择。 |
| Invoker              | 调用的核心                                         |
| Invocation           |                                                    |

#### 2.主要分层

* service 层
* config 层
* proxy 层
* registry 服务注册层
* cluster 集群层
* monitor 监控层
* protocal 
* exchange
* transport
* serialize 

#### 3.核心流程和机制

##### 3.1注册流程

```java

```

##### 3.2订阅流程

```java

```

##### 3.3服务调用流程

```java

```

##### 3.4 SPI机制

* 在`ExtensionLoader.getExtensionLoader(Protocol.class).get  `调用具体实现类时候根据

#### 4.负载策略

* random
* hash
* least active
* robin

#### 5.集群容错策略

* fail-over