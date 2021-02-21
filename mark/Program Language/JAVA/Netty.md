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

![channelpipeline.jpg](https://cdn.jsdelivr.net/gh/clawhub/image/diffuser/blog/19/11/29/060b6337573afd1d8ba11b8c73b0712b.jpg)

![服务端 Netty Reactor 工作架构图.jpg](https://user-gold-cdn.xitu.io/2019/12/5/16ed52a8f4638f4d?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)

#### Zero copy

******

 **核心思想是减少不必要的数据复制**

* Bytebuf直接使用堆外内存进行socket的读写(最大努力选择DirectByteBuffer)
* Bytebuf进行可以组合和分拆（抽象层面的组合分拆），避免了以往直接内存拷贝的方式来处理。
* 对文件直接用transferTo 来传输(减少数据在用户态和内核态之间的拷贝)。



#### 处理TCP粘包分包问题

------

***主要类***

* LengthFieldBasedFrameDecoder(offset(起始偏移), length(报文长),adjustment（读取的增加长度）,strip(跳过多少字节))
* FixedLengthFrameDecoder(定长报文)
* DelimiterBasedFrameDecoder(特殊字符划分，常见的有 \n , \r\n)

