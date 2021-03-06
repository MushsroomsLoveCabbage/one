## 网络

#### 1.网络图谱

- https://static001.geekbang.org/resource/image/79/aa/79adec391c62b8cf90c210804b704daa.jpg
- MAC地址是工作在一个局域网中，核心是在局域网内确定唯一设备（局域网内多个设备共享一个ip地址）
- Linux 默认的逻辑是非同网段的调用，数据包是发给网关的。
- **动态主机配置协议（Dynamic Host Configuration Protocol）DHCP**。新加入的主机同过这个来申请自己的IP地址。

#### 2.Protocol

##### 2.1 核心要素

- 约定
- 格式

##### 2.2 主要协议

- HTTP, HTTPS, DNS, HTTP DNS (优化DNS的缓存刷新不及时问题),CDN
- TCP/UDP
- IP,ICMP,OSPF,BGP,IPSec,GRE

### 1.应用层

------
#### 1.1 HTTP

##### 1.1.1 SSL

* SSL（Secure Sockets Layer）及其继任者传输层安全（Transport Layer Security，TLS） 是为网络通信提供安全及数据完整性的一种安全协议。TLS与SSL在传输层对网络连接进行加密。

##### 1.1.2 REST

* REST即表述性状态传递（英文：Representational State Transfer，简称REST）
* 核心是操作资源(数据和表现形式组合)

#### 1.2 DNS

##### 1.2.1) 负载策略

* weight round robin
* latency-based
* geolocation-based

#### 1.3 CDN

* 

### 4. 传输层
------
#### TCP

* 面向数据流

##### 滑动窗口

* 同时发送和接收多个数据包来进行流量控制，
* TCP单个包数据在1500字节（待确定），（TCP报文头+IP报文头+报文）

##### TCP分包粘包问题

* 应用程序同时从滑动窗口中读取到多个TCP数据包
* 分包黏包问题的本质是TCP作为传输层，只保证用户的数据按照传输的顺序准确的被传送到对端即传送到系统的TCP缓冲区，当应用层接受到的数据是以一个一个的过来时候（消费完一个来一个的方式）即不存在这些问题，但实际是会存在多个数据被合并在一起，或者小的数据段被拆分开来传送过去，数据都到达了缓冲区，因此需要对这些数据在拆分或者合并。主要的方式是
  * 固定长度，即每个消息段固定长度，不足的部分补齐（补什么空格字节？）
  * 固定分隔符
  * 开头固定字节标记数据长度

##### TCP攻击

* 对于将建立的连接，TCP这边会本地保存一个数据。client 这边发送虚假地址的SYN_1请求，server端会向client 端发送响应(SYN+ACK报文)，但是找不到对应的client,并等待一段时间（SYN timeout）

##### TCP连接流程

```sequence
Title: Connect
participant Client
Note right of Server: Closed
Note left of Client: Closed
Note right of Server: Listen
Note left of Client: SYN_SENT
Note right of Server: SYN_RECV
Note left of Client: ESTABLISHED
Note right of Server: ESTABLISHED
participant Server


```

##### TCP断开流程

```sequence
participant Client
Client -> Server: FIN(M)
Note left of Client: FIN_WAIT_1

Server -> Client: ACK(M+1)
Note right of Server: CLOSE_WAIT
Note left of Client: FIN_WAIT_2
Server -> Client: FIN(N)
Note right of Server: LAST_ACK
Client -> Server: ACK(N+1)
Note left of Client: TIME_WAIT \n (2MSL)
Note left of Client: CLOSED
Note right of Server: CLOSED
participant Server
```



#### UDP

* 面向数据报
* 可以一对多，多对多的模式

***长度***

* 因为UDP的数据长度限制为65535 还要去除（ip首部和udp首部）

* 数据超过MTU后会被数据分片 （TCP中有个分段，保证数据小于拆分限制，因此可以TCP数据包无需分片）

  ```java
  //拆分后的数据格式
  IP首部 + udp首部 + 数据
  IP首部 + 数据  
    
    数据发生丢失时候需要整个数据包重传。
  ```
```

* UDP的时间数据长度会比理论值小，因为受限于系统的实现

* Quick UDP Internet Connections 

### 网络层

------

#### IP

#### ICMP


```