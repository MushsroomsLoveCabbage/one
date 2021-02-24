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





### Netty

- duplicate()，slice()等等生成的派生缓冲区ByteBuf会共享原生缓冲区的内部存储区域。此外，派生缓冲区并没有自己独立的引用计数而需要共享原生缓冲区的引用计数。也就是说，当我们需要将派生缓冲区传入下一个组件时，一定要注意先调用retain()方法。Netty的编解码处理器中，正是使用了这样的方法。

  ```java
  ByteBuf parent = ctx.alloc().directBuffer(512);
  parent.writeBytes(...);
  
  try {
      while (parent.isReadable(16)) {
          ByteBuf derived = parent.readSlice(16);
          derived.retain();   // 一定要先增加引用计数
          process(derived);   // 传递给下一个组件
      }
  } finally {
      parent.release();   // 原生缓冲区释放
  }
  ...
  public void process(ByteBuf buf) {
      ...
      buf.release();  // 派生缓冲区释放
  } 
  ```