## Netty

### 核心类

* AbstraBootstrap
* NiOEventLoop（死循环监听，并处理监听事件）
* NioEventLoopGroup(管理子线程组)（包含选择器）
* Channel (封装JAVA Channel)
* ChannelPipeline
* Future， Promise(Netty 的异步结果)
* Unsafe(真正的处理与Socket之间的交互)

### 初始化

* 初始->绑定->注册
* NioEventLoop.run()->selector->process(注册完交给子EventLoop)

![channelpipeline.jpg](..\..\resource\Netty\NettyChannel.jpg)

![服务端 Netty Reactor 工作架构图.jpg](..\..\resource\Netty\NettyCore.jpg)

#### Zero copy

******

 **核心思想是减少不必要的数据复制**

* Bytebuf直接使用堆外内存进行socket的读写(最大努力选择DirectByteBuffer)
* Bytebuf进行可以组合和分拆（CompositeByteBuf 抽象层面的组合分拆），避免了以往直接内存拷贝的方式来处理。
* 对文件直接调用JAVA底层的transferTo 来传输(减少数据在用户态和内核态之间的拷贝)。

#### JVM Heap 到 C Headp的Copy

- 在基于VM的IO操作中，需要把VM 中的数据复制到 c malloc() 或者类似函数分配的Heap 内存中，然后再复制到内核的写缓冲区中。对于JVM 来说是堆外内存，对应OS来说进程内的堆区。这么设计是因为JVM的对象实际内存布局不同于C，给的可能是个对象头而不是地址。垃圾回收会导致对象的位置移动

#### 处理TCP粘包分包问题

------

***主要类***

* LengthFieldBasedFrameDecoder(offset(起始偏移), length(报文长),adjustment（读取的增加长度）,strip(跳过多少字节))
* FixedLengthFrameDecoder(定长报文)
* DelimiterBasedFrameDecoder(特殊字符划分，常见的有 \n , \r\n)
