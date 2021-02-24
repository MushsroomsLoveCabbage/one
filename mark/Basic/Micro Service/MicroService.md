### 微服务

------

### 1.节点存活

##### 1.1判定方式

- 控制中心决定服务提供者是否存活
  - 网络阻塞会影响判断
  - 节点失活，全局通知导致网络流量突刺
- 消费者决定服务提供者是否存活

##### 1.2保护措施

- 心跳开关保护机制 （节点不可用时，通知部分消费者）
- 服务节点摘除保护机制 （注册中心摘除节点时候，只摘除部分）



### 2. ServiceMesh

#### 2.1 SideCar

- Load balance
- Circuit breaking
- Retris and deadlines
- Instrumentation
- Request Routing
- Service Discovery
- Metrics Collection
- Routing Policy

#### 2.2 SideCar 实现方式

- 基于iptables 拦截

  - (流程)9080 ->15001 ==> 9080==>15001->9080

  - 网络层拦截，更适合云原生

- 协议转换的方式

  - (流程)9080 ->15001==>15001->9080