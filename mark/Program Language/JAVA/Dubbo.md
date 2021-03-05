## Dubbo

### 1.总体认识

------

![dubbo-framework](../../resource/Dubbo/dubbo-framework.jpg)

#### 2.重要组成部分

| 关键词               | 作用                                                         |
| -------------------- | ------------------------------------------------------------ |
| Consumer (Reference) | 服务调用者                                                   |
| Provider (Service)   | 服务提供者                                                   |
| ConfigCenter         | 注册中心，存储服务信息                                       |
| SPI                  | 动态配置加载机制，在调用时候依据配置决定使用选择。           |
| Invoker              | Invoker 是实体域，它是 Dubbo 的核心模型，其它模型都向它靠扰，或转换成它，它代表一个可执行体，可向它发起 invoke 调用，它有可能是一个本地的实现，也可能是一个远程的实现，也可能一个集群实现 |
| Invocation           |                                                              |

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

![](../../resource/Dubbo/dubbo-export.jpg)

```java
1.配置检查-
```

##### 3.2订阅流程

![dubbo-refer](..\..\resource\Dubbo\dubbo-refer.jpg)

```java

```

##### 3.3服务调用流程

```java
//Consumer 端
proxy0#sayHello(String)
  —> InvokerInvocationHandler#invoke(Object, Method, Object[])
    —> MockClusterInvoker#invoke(Invocation)
      —> AbstractClusterInvoker#invoke(Invocation)
        —> FailoverClusterInvoker#doInvoke(Invocation, List<Invoker<T>>, LoadBalance)
          —> Filter#invoke(Invoker, Invocation)  // 包含多个 Filter 调用
            —> ListenerInvokerWrapper#invoke(Invocation) 
              —> AbstractInvoker#invoke(Invocation) 
                —> DubboInvoker#doInvoke(Invocation)
                  —> ReferenceCountExchangeClient#request(Object, int)
                    —> HeaderExchangeClient#request(Object, int)
                      —> HeaderExchangeChannel#request(Object, int)
                        —> AbstractPeer#send(Object)
                          —> AbstractClient#send(Object, boolean)
                            —> NettyChannel#send(Object, boolean)
                              —> NioClientSocketChannel#write(Object)
//Producer 端
NettyHandler#messageReceived(ChannelHandlerContext, MessageEvent)
  —> AbstractPeer#received(Channel, Object)
    —> MultiMessageHandler#received(Channel, Object)
      —> HeartbeatHandler#received(Channel, Object)
        —> AllChannelHandler#received(Channel, Object)
          —> ExecutorService#execute(Runnable)    // 由线程池执行后续的调用逻辑
ChannelEventRunnable#run()
  —> DecodeHandler#received(6Channel, Object)
    —> HeaderExchangeHandler#received(Channel, Object)
      —> HeaderExchangeHandler#handleRequest(ExchangeChannel, Request)
        —> DubboProtocol.requestHandler#reply(ExchangeChannel, Object)
          —> Filter#invoke(Invoker, Invocation)
            —> AbstractProxyInvoker#invoke(Invocation)
              —> Wrapper0#invokeMethod(Object, String, Class[], Object[])
                —> DemoServiceImpl#sayHello(String)
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
* fail-back
* fail-fast
* fail-safe
* forking

##### 6.调用链

* context中 传递隐含参数traceId, 这个需要借助全局traceId生成器来生成。