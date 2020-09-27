### The Google File System

#### 摘要

* 我们

#### 1.介绍

​	我们设计并实现GFS来实现快速增长的Google数据处理需求。GFS和先前其他的分布式系统有着相同的目标，例如性能，可扩展性，可靠性，可用性。然而，它已经被对我们应用的工作负载和技术环境所更新。反应了一个标记的部分从一些早期的文件系统的设计假设。我们已经反复试验传统的选择并且在设计上探求了完全不同的点。

​	首先，组件失效比异常还要正常。文件系统包含成百甚至上千的廉价的通用存储机器，并且被相同量的客户端请求。组件的数量和质量事实上保证某些不在工作在任意时间，一些不会从它们刚刚的错误中恢复。有一些问题由程序bug,操作系统bug,人为错误，磁盘损坏，内存，连接，网络，电源供应引起，自动恢复是系统所必须的。

​	第二，传统的标准中文件太大了，数GB文件是常见的。每个文件包含多个应用对象例如web文档，当我们经常与快速增长的包含几百万对象的数TB数据集，即使系统支持管理几百万近乎KB大小的文件，也是笨拙地。最后，设计假设和参数例如IO操作，块大小必须被改进。

​	第三，大多数文件被修改通过追加新数据的方式而不是重写已有数据。随机写一个文件实践中几乎不存在。一旦写入，这些文件就是只读的了，并且经常顺序读。大量的数据共享这些特性。一些构成大量的仓库数据分析应用。一些应用持续性的生成数据流。一些或许档案数据。一些是中间数据由一个应用产生被其他获得，不管同时或者延迟会。大文件上的获取模式，追加方式变成客户端丢失缓存数据时请求时性能优化和原子化的保证。例如我们在没有增加应用负担的情况下放宽了GFS的一致性模型来极大的简化文件系统。我们也引入了原子化追加操作来保证多客户端在没有其他同步操作下可以并发对同一文件追加操作。这些将会在文章后面章节详解讨论。

​	为了不同目的多个GFS集群被部署。最大的一个集群有超过一千个存储节点，300TB的硬盘存储，被上百个客户单在不同的机器上连续请求访问 。



#### 2. 设计总览

##### 2.1 假设

​	设计一个文件系统满足我们的需要，我们依据假设给我们提供了挑战和机会。我们前面省略一些关键点现在细节上更多的展示我们的假设。

* 建立这个系统的大量廉价通用组件经常挂掉，必须持续监控，监测，容忍，从组件失败中快速恢复。
* 系统存储一个适量的大文件，我们希望小百万的文件，每个大小上100MB及以上，多GB文件是常有情况并且需要被高效管理。小文件必须支持，但是我们无需为他优化。
* 工作负载基本上包括两种解读：大量流式读和小规模随机读。在大量流式读中，每个典型独立操作读取几百KB,1MB甚至更多。来自同个客户端的后面的操作通常读同个文件相邻区域。典型随机读操作读取几KB在一些任意的偏移上，性能应用京城批量并且排序这些微读来优化而不是
* 工作负载也包括许多大量在文件上追加数据的连续写操作。典型的操作数据大小也类似于这些读。一旦写入，文件很少被再次修改。在文件随机位移上小规模写是支持的但是没必要高效
* 为了多客户端并发在同个文件追加，系统必须高效地实现已定义好的语义。我们的文件经常被用于读写队列或者一些多方式合并。每个机器上运行上百生产者，将会并发在一个文件上追加。前面说所的最小的同步即原子性是必不可少的，文件会被后面读取，或者消费者同时通过文件读取。
* 高吞吐带宽比低延迟更重要，大多数我们的目标应用放置在 处理数据在高速率中。同时很少对每个读写操作有严格的响应时间要求

##### 2.2 接口

​	GFS提供一个熟悉的文件系统接口，尽管它没有实现标准的接口，类似于POSIX。文件在目录下被层级管理并且以路径名唯一标识。我们支持常规的创建，删除，打开，关闭，读，写操作。

​	GFS有快照和记录拼接操作。快照以低开销创建文件的副本或者目录树。记录拼接允许多个客户端在同一文件后并发地拼接数据并且保证每个单独操作的原子性。这个有效的对于实现多方式拼接和生产者消费者队列许多客户端可以同时无需额外锁拼接。我们发现这种类型的文件在构建大型分布式应用时的价值。快照和记录拼接在后面的3.4,和3,3章节单独讨论

2.3 架构

​	GFS集群包含可被多个客户端端访问的单个Master 和多个ChunkServer，如图1所示。这些中的每一个都是运行用户级进程的通用linux机器。也可以运行一个chunkserver和一个客户端在同一机器上。只要机器的资源允许并且由于运行片状的应用代码而导致的低可靠性能被接受。

​	文件被分割成固定大小的Chunk,每个chunk被创建时，由master用全局不变且唯一的64位bit的句柄标识。chunkServer存储分片在本地磁盘中以linux文件形式存储文件。并且读写chunk数据通过句柄和字节区间。为了可靠性。每个分片在多个ChunkServer上有副本。默认的，我们存储3个副本。尽管用户可以为文件命名空间的不同区域指定不同的复制级别

​	Master持有所有文件的原数据。包括命名空间，获取控制信息，文件到分片的映射，以及分片现在的位置。它也控制全系统的活动例如Chunk租约管理,对过期分片的垃圾回收，ChunkServer间的Chunk迁移。Master周期性的和ChunkServer通过心跳信息交互来下发指令和收集状态。

​	GFS客户端代表应用链接实现文件系统接口的应用并且与Master和ChunkServer通讯来读和写数据。客户端和Master交互来获得原数据，但是所有的承载数据通讯直接通过ChunkServer.我们不提供POSIX接口因此不需要深入到linux vnode 层。

​	客户端和ChunkServer都不缓存文件数据。客户端缓存提供一点点好处因为大多数应用流通过多个文件或者文件集太大而无法缓存。不缓存可以简化客户端并且消除缓存一致性问题来简化整个系统。（客户端缓存原数据）ChunkServer需要缓存文件因为Chunks像本地文件一样存储并且linux的buffer缓存已经使经常获取的数据存在内存中。

##### 2.4 单Master

​	单Master极大地简化了我们的设计并且使Master可以决定复杂的Chunk放置和复制决定通过全局共识。然而，我们必须最小化它所涉及的读或者写，防止他成为瓶颈。客户端从不通过Master读或者写。客户端请求Master请求哪个ChunkServer它该联系。它在限定的时间内缓存它的信息并且之后的操作与ChunkServer直接通讯

​	让我们来参考图1来解释下简单地读过程。首先，使用固定的Chunk大小。客户端转换应用指定的文件名和字节偏移量文件内的Chunk下标。之后，向Master节点发起带有文件名和Chunk下标的请求。Master回应相应的句柄和文件副本位置。客户端使用文件名和Chunk下标作为key缓存这些信息.

​	客户端向一个副本发送请求（最可能近的那个）。请求指定了Chunk句柄和分片内的字节范围。同一分片上更多的读请求不在需要客户端和Master交互了，直到缓存信息过期或者文件被重新打开。事实上客户端在一个请求中获取多个Chunk.并且Master可以立马包含分片信息。这些额外的信息在实践中避免了未来的客户端和Master通讯的开销

##### 2.5 Chunk 大小

​	分片大小是一个关键设计参数，我们选择64MB，比传统的文件系统block 大。每个Chunk副本作为一个linux空白文件存储在ChunkServer上，并且只在需要时拓展。惰性空间非配避免了空间浪费导致内部碎片化，或者最大的反对如此大的Chunk大小

​	一个巨大的Chunk大小提供几个重要的优点。首先，避免了客户端需要和Master通信，因为读和写在同一个Chunk上只需要和Master一次关于分片位置信息的初始化请求。这减少对于我们的工作负载时特别有意义的。因为应用大多数的有序地读和写文件。甚至小的随机读，客户端可以缓存所有的Chunk位置信息在一个数TB的工作集。第二，由于在一个大的Chunk,一个客户端更可能执行多个操作在同一个Chunk上。这可以减少网络负载通过维持和ChunkServer的TCP链接在一个可拓展的时间内。第三，减少Master上原数据的大小，可以让我们将原数据存储在内存中。这将会反过来带来一些我们在2.6.1中讨论的好处

​	另一方面，一个大的Chunk大小，即使时惰性分配，也是有缺点的。一个小的文件组成一个小的分片，或许仅仅一个。ChunkServer存储这些Chunks如果多个客户端访问的化会成为热点。在实践中热点没有成为主要的问题，因为我们的应用大多数顺序读大的多Chunk文件

​	然后，当GFS第一次在批处理队列系统中使用热点问题确实发生。一个执行是作为单Chunk文件写入GFS，然后在数百个机器上同时启动。很少的ChunkServer存储这些执行都超载了由于数百个同时的请求。我们解决了这个问题，通过更高的副本因素来存储这些可执行的并且使这些批处理队列系统和应用启动时间错开。一个潜在的长期解决办法是允许客户端在这种情况下从其他客户端读。

##### 2.6 原数据

​	Master存储3个主要类型元数据：文件和Chunk命名空间，文件和Chunk的映射，每个Chunk分片副本的位置。所有Metadata 存储在内存中。前两种通过操作日志相关日志操作持久化的存储在Master的硬盘上，并且复制到远程机器。使用日志使我们更新Master状态非常方便，可靠并且没有主宕机时候不一致的风险。Master没有持久化存储Chunk的位置信息。而是在Master启动并且任意一个ChunkServer加入集群中的时候询问Chunk信息

###### 2.6.1 内存数据结构

​	由于Metadata存储在内存中，Master首先操作，此外对于Master周期性在后台中扫描整个状态是方便且高效地。这个周期性的扫描操作用来实现Chunk垃圾回收，ChunkServer出现失败时候再复制，以及Chunk迁移时候平衡ChunkServer间的负载和磁盘空间使用。章节4.3和4.4将会更进一步讨论这些内容

​	对这种只用内存方式的一个潜在关心点是Chunk的数量和之后的整个系统的容量被限制为Master有多少内存。实际这不是一个严格的限制。Master对每个64MB的Chunk持有少于64字节的原数据。大多数Chunk是满的，应为大多数文件包含多个分片。只有最后可能是部分填充。类似的，每个文件的文件的命名空间数据需要少于64字节因为存储文件名采用前缀压缩。

###### 2.6.2 Chunk 位置

​	Master不存有哪个ChunkServer有某个指定Chunk的副本的持久化记录。它在启动时候简单地轮询ChunkServer相关信息。Master可以保持自己最新因为它控制所有Chunk的放置和监控ChunkServer状态通过心跳信息。

​	我们一开始尝试去在Master中持久化保存Chunk位置信息。但是我们决定在启动时以及之后周期性的向ChunkServer请求信息更加简单。这个可以消除Master和ChunkServer之间的同步问题在ChunkServer加入和离开集群，改名，失效，重启等问题。在一个有数百服务的集群中，这是问题很常见。

​	另一个方式去理解这个设计决定是意识到一个ChunkServer有对于在自己磁盘上Chunks在哪里不在哪里的最终话语权。没有任何意义去在Master上维持一个关于此的持久视图，因为一个ChunkServer上的错误可能会导致Chunk同时消失（磁盘损坏或者不可用）或者一个人重命名ChunkServer.

###### 2.6.3

​	操作日志包含对于决定性的Metadata变化的历史日志。这是GFS的核心，不仅仅这是Metadata的持久化记录，而且这作为决定并发操作的逻辑时间线的应用。文件和Chunks以及他们的版本，都是被逻辑时间永久唯一标识。

​	由于操作日志是决定性的。我们必须可靠地存储它并且变化对于客户端不可见直到Metadata改变被持久化。否则，我们失去整个文件系统或者最近的客户端操作即使Chunks还存活。因此我们在多个远程机器上复制这些信息，并且只有刷盘本地和远程的相关的记录才会响应客户端。Master刷盘前批量聚合服务日志记录来减少刷盘和复制对全局系统的吞吐量的影响。

​	Master恢复它的文件系统通过重印它的操作日志。为了最小化启动时间，我们必须保证日志小。Master在日志超过某个确定大小时候可以快照当前状态。因此它可以从本地磁盘中当前最近的checkpoint 恢复，仅仅重放在这之后的限定的数量的日志记录。checkpiont是一个压缩的B树结构。一种可以直接映射到内存并且用于命名空间检查不需要额外的解析的形式。

​	因为构造一个CheckPoint 点需要一些时间，Master内部状态是这样构造的。一个新的CheckPoint 可以创建时不用无延迟正在进行的操作。Master转换到一个新的日志文件并且在拧一个线程创建一个新的CheckPoint.新的CheckPoint包括所有转换前的改变。因为一个集群有几百万的文件它可以在几分钟甚至更久时间内创建。当创建完它被同时写入本地和远程磁盘。

​	恢复需要最新的完成CheckPoint和之后的日志文件。旧的CheckPoint 和日志文件可以被随意删除。尽管我们会保存一些来做灾备。Checkpoing 时的一个错不会影响正确性因为恢复吗检查和跳过不完备的CheckPoint.

##### 2.7 一致性模型

​	GFS有一个宽松的一致性模型来很好地支持我们的高度分布式系统并且报错相对简单和高效的。我们现在讨论GFS的保证和他们对应用来说意味着什么。我们会强调GFS如何来实现这些保证，细节留在论文部分。

###### 2.7.1 GFS的保证

​	文件命令空间改变操作是原子性的。他们只有Master处理。命名空间锁保证原子性和正确性。Master操作日志定义了这些操作是一个全局完全有序地。

​	再一次操作修改后一个文件区域的状态由操作类型决定。无论成功或者失败或者他们是并发的操作。表格1总结了结果。一个文件区域是一致的如果所有的客户端看到相同的数据，不管它们从哪个副本读。在一个文件数据操作后如果是一致的，并且客户端会看到它整个操作写，则区域是defined。如果一个操作没有并发写阻碍而成功。这个受影响的区域是defined:所有客户端永远会看到他们的改变操作(针对的是客户端在指定的offset上写的操作)。成功的并发操作使得区域Undefined 但是一致的。所有客户端会看到相同的数据，但是它可能不会显示被其他操作的写入。一个失败的操作使得区域不一致以及undefined.不同的客户端可能会看到不同数据在不同的时间点上。我们下面描述我们的应用如何区分defined区域和undefined区域。应用不需要在不同的类型的Undefined区间中进一步再区分。

​	数据操作可能是写或者记录拼接。一个写操作导致数据在应用特定的偏移位置被写入。拼接操作导致在并发的操作中数据可以被至少一次在GFS选定的偏移位置（3.3）原子化的追加。（常规的追加操作仅仅是在一个客户端认为是当下文件结尾的偏移位置写入）。偏移返回给客户端并且标记一个定义的区域的开始包含这个记录。另外，GFS或者插入空白或者重复的记录在两者间。他们占据空间被认为是不一致的，并且根据用户数据的量变小。

​	在一系列成功的操作后，保证被操作的文件区域是defined（一种状态）。并且持有最后修改操作的数据。GFS达成这个通过在一个Chunk的操作被相同顺序下应用到其他副本上。并且使用Chunk版本号来检测任何因为ChunkServer失败时候错误操作而导致过期的副本。过期的副本将不再被包括在操作以及包含在Master对client应答的Chunk位置信息中。它们将尽可能早地被垃圾回收

​	由于客户单缓存Chunk位置。他们将可能在信息刷新前从一个过期的副本中读取数据。这个窗口被缓存对过期时间限制并且下一个被打开的文件将 清除所有关于那个文件的缓存信息。由于大多数我们的文件是只追加，一个过期副本经常返回一个Chunk的提前结尾而不是过时的数据。当一个读者重试并且联系Master,他将会立马或者当前副本位置信息。

​	在一个成功操作之后。组件失效当然任然可以腐化或者毁坏数据。GFS标识失效的ChunkServer通过Master和ChunkServer间的常规的握手。并且所有ChunkServer和监测数据腐败通过校验和。一旦一个问题发生，数据从检验的副本尽可能的转存。几分钟内，Chunk失效只有所有的副本在GFS反应前同时失效才是不可逆转的。甚至在应用收到清除错误而不是腐败数据时，它成为不可用的而不是腐败的。

###### 2.7.2 对应用的影响

​	GFS可以适应宽松的一致性模型通过一些必要的简单地技术来实现其他目标：依赖拼接而不是复写，CheckPoint, 写自验证，自识别记录。

​	实际中我们所有的应用操作文件通过拼接而不是复写，在一个典型的应用中，一次写从开头到结尾生成文件。它原子性的重命名文件为一个永久的名称在写完所有数据之后。或者周期的CheckPiont多少已经被成功的写入。CheckPoint 也包含应用级别的校验和。读操作只有文件区域超过最新的CheckPoint才会验证并且处理，已知是在Defined状态。不管一致性和并发问题，这个方式已经很好地应用。拼接比随机写对应用失效来说更加高效并且更加弹性。校验点允许写操作渐进的方式重新开始。并且防止读处理被成功写入，但从应用角度来说还未完成的数据。

​	另一种典型的应用中，许多写操作并行的追加数据到一个文件，例如合并结果或者一个生产消费队列。记录追加的最少一次追加语义保证Writer的输出。Reader处理偶然的填充数据和重复内容。Writer在每条记录中都包含额外信息如检验和，来验证它的有效性。Reader可以通过校验和来识别并丢弃额外的的填充数据和记录片段。如果它不能容忍偶然的重复（如果这些数据触发了非幂等性操作），它可以通过使用记录中的唯一标识符来过滤他们。，这些唯一标识符通常用于命名程序中处理的实体对象，例如web文档。这些功能性来记录I/O（除了重复去除）在我们的程序共享库中，并且适用google的其他文件接口实现，通过那个，相同顺序记录加极少的重复被传递给Reader

#### 3. 系统介绍

​	我们设计这个系统来最小化Master对所有操作的参与。在这个背景下。我们现在描述客户端，Master,ChunkServer通讯来实现数据突变原子化记录追加，以及快照。

##### 3.1 租约和突变顺序

​	突变是一个操作改变内容或者是Chunk的原数据例如写或者追加操作。每个突变在所有的副本上作用。我们使用租约来实现所有副本上一致的突变顺序。Master保证Chunk租约只给副本中的一个，我们称这个是主副本。主对该chunk上所有的突变做序列化。所有副本追随这个顺序当应用突变时。因此全局的突变顺序是被先定义好的，通过Master对租约授权顺序。然后由租约中主Chunk分配的序列号决定。

​	租约机制被设计来最小化Master的管理。租约有初始化的60S超时时间。然后只要Chunk被修改，主节点可以请求并且从Master获得无限延期。这些眼球请求和授权都是包含在Master和所有ChunkServerz之间的心跳信息中。Master有时尝试在过期前取消租约（当Master想要去使一个已经被改名的文件失效）。即使Master失去和主的通信，它可以在旧的过期之后安全的授权新的租约。

​	在图2中，我们依据步骤编号，图解展现写入操作的控制流程。

 1. 客户单询问Master哪个ChunkServer持有哪个Chunk的当前租约以及他们的副本位置。如果没有任何有租约，Master会给它选择的一个授予租约。

 2. Master响应客户端主以及其他副本的位置。客户端缓存这些信息为未来的修改。只有当主不可达时候才会和Master再次通讯或者回复它不在持有租约。

 3. 客户端推送数据到所有副本。A客户端可以在任何顺序做这个操作。每个ChunkServer将会用内部LRU缓存存储数据指导数据被使用或者过期。通过解耦数据流和控制流。我们可以改进性能通过调度消耗高的数据基于网络技术不管哪个ChunkServer是主。章节3,2将会讨论这些。

 4. 一旦副本应答收到数据。客户端发送一个写请求给主副本，请求标识了之前推送给所有副本的数据。主副本分配序列号给所有它收到的修改操作。可能来自多个客户端，提供一个必要的序列化。它按序列化编号顺序应用修改操作到本地状态。

 5. 主副本向所有二级副本转运写请求。每个二级副本响应操作按主副本分配的相同的序列号

 6. 二级副本都响应主副本意味着他们都完成了操作。

 7. 主副本响应客户端。在任何副本发生的任何错误会报告给客户端。万一，写已经在主以及任意副本中成功。（如果在主副本中失败，将不会被分配一个序列号并转发）。客户端请求被认为是失败的。被修改的文件区域处于不一致状态。我们的客户端代码处理这些错误通过重试失败的操作。这将会在3到7操作上做几次尝试。在从头开始执行之前。

    如果应用程序一次写入大量数据或者跨越了多个Chunk，GFS客户端代码把这个拆分为多个小操作，他们都遵从上面描述的控制流但是会被打断或者来自其他客户端的并发操作覆写。因此，分享的文件区域包含来自不同客户端的分片。尽管副本将会被标识。因为每个操作在所有的副本上都以相同顺序成功地完成。这使得文件区域处于2.7章节提到的一致但是undefined 状态。

##### 3.2 数据流

​	我们解耦数据流和控制流来高效的使用网络。当控制流从客户端到主然后到所有的二级副本。数据线性地推送通过一个小心挑选的chunkServer链以管道的方式。我们的目标是完全最大化是有每个机器的带宽避免网络瓶颈和高延迟链接，最小化推送所有数据的延迟。

​	为了最大化利用每个机器的带宽，数据通过ChunkServer链被线性地推送而不是以其他一些技术分配。因此每个机器可以全部出带宽被用来尽可能快的传送数据而不是在多个接收者间分割。

​	为了尽可能地避免网络瓶颈以及高链接延迟（内部跳转链接经常都有）。每个机器转发数据给还未收到数据在网络拓扑结构中“最近的”的机器。假设客户端推送数据给ChunkServer S1通过S4,它推送数据给最近的ChunkServer S1, S1转发数据给最近的S2，尽管S4是最近的。类似的，S2转发给S3或者S4，哪个离S2更近。我们的网络技术足够简单距离可以通过IP地址来计算。

​	最后，我们基于TCP连接的管道化数据转发来最小化延迟。一旦一个ChunkServer收到一些数据，它开始立马转发。管道化特别有助于我们因为我们使用全双工连接的路由网络。立马传送数据并不会减少接收速率。没有网络阻塞，转发B字节数据到R个副本理想的延迟时间是B/T + RL。T是网络的吞吐量并且L是两个机器间转换字节数据的延迟。我们的网络连接是典型的100Mbps(T),L远低于1ms，因此1MB数据可以在80ms内有效的分发。

##### 3.3原子追加记录

​	GFS提供原子化追加操作称之为记录追加。传统的写，客户端指定特定的写的偏移量。并发的写相同区域不是序列化的。区域结尾可能包含来自多个客户端的数据分段。在一个记录追加，客户端只指定数据.GFS在其选择的偏移最少一次地原子化追加数据并且返回偏移给客户端。这个类似于Unix系统中向一个以0_APPEND模式打开的文件写入，没有竞争条件当多个并发写操作时。

​	记录追加在我们的分布式系统中同个文件被多个机器并发追加中大量的使用。如果按照传统的写操作，客户端需要额外的复杂的代价高的同步。比如通过一个分布式锁管理器。在我们的工作负载中，这些文件经常像多生产者-单消费者队列样服务，或者包含来自多个客户端的合并结果。

​	记录追加是一种操作类似于3.1中的控制流程，除了需要在主上有些额外的逻辑。客户端推送数据给文件最后一个Chunk的所有副本，之后发送请求给主副本。主校验来看是否在当前Chunk中追加记录会导致这个Chunk超过64MB限制，如果是，它会填充Chunk到最大尺寸，告诉二级副本照做，回应客户端操作需要在下一个副本位置重试。（记录追加被严格的限制在最多在最大Chunk尺寸的1/4，在可接受程度内防止最坏的碎片化）大多数情况下，如果记录在最大尺寸内适合。主会在自己的副本内追加数据，告诉二级副本在指定的偏移写入数据，最后成功返回客户端。

​	如果一个记录追加在任何一个副本失败，客户端重试这个操作。结果相同Chunk副本或许会包含不同的数据或者全部或者部分的重复的追加数据。GFS不保证所有的副本是逐位一致的。它只保证数据以原子单元内被最少写入一次。这个特性可以来自简单地观察推到出来：如果操作成功，数据必须被写入在相同的偏移在所有副本，这之后，所有的副本最少和记录结尾一样长。因此任何后续的记录将会被分配一个更高的偏移或者一个不同的Chunk即使其他副本之后会成为主副本。为了我们的一致性保证。记录追加操作成功的写入的数据的区域是defined，区域是不一致的，我们的应用可以处理2.7.2中讨论的非一致性区域。

##### 3.4 快照

​	快照操作在几乎同时创建一个文件的副本或者是一个目录树，同时最小化任何对其他操作的影响。我们的用户使用快速的创建大量数据集的分支副本（并且经常复制这些副本，）或者在做实验的前去CheckPoint 当前状态，操作可以在之后被轻松地提交或者是回滚。

​	像AFS,我们使用标准的Copy-on-write技术来实现快照。当Master收到一个快照请求。它首先取消掉任何在Chunk以及文件上的其他租约。这保证任何接下来的对这些Chunks写操作将会需要和Master通讯来找到租约持有者。这会给Master一个机会来首先创建副本。

​	在租约被取消或者过期之后。Master的记录操作到磁盘中。之后应用这些日志记录到内存状态中通过复制原数据。或者目录文件。最新的创建快照文件和源文件指向同一个Chunk.

 在快照操作后第一次客户端想要写入到Chunk C中。它发送一个请求给Master来找寻当前租约持有人。Master注意到Chunk C 的引用计数超过1，它延迟响应客户端请求并且立马选择一个最新的Chunk 句柄c'.之后询问每个有当前C副本的ChunkServer创建一个新的Chunk C'.通过创建新的副本在相同而ChunkServer上，我们保证数据可以被在本地复制。而不是通过网络（硬盘比我们网络100Mbps网络快3倍）从这个点。请求句柄和其他的Chunk副本没有不同。Master保证一个副本持有C'的租约。并且响应客户端。客户端可以正常写入副本而不知道这是刚从一个已存在的副本创建而来。

#### 4. Master 节点操作

* Master节点执行所有的名称空间操作。除此，它管理整个系统中的Chunk副本：决定Chunk的存储位置（放置在哪里），创建新的Chunk和它的副本，管理复制以及协调大量全系统的活动以保证Chunk被全量复制，在所有Chunk(块)服务器间进行负载均衡，回收所有未使用的存储空间。本节讨论上述的主题。

##### 4.1 名称空间管理和锁

* 许多Master 操作需要很长时间：例如，快照操作必须撤销Chunk(块)服务器上快照涉及的所有的Chunk的租约。（被快照覆盖的Chunk(块)服务器的租约）。我们不希望这些操作进行时，延迟其他Master节点的操作。因此我们允许多操作同时运行，通过使用名称空间的区域上的锁去保证正确的执行顺序。

* 不像其他传统的文件系统，GFS没有那种针对每个目录实现能够列出目录下所有文件列的数据结构。也不支持文件或者目录的链接（Unix 硬链接或者符号链接）逻辑上，GFS 的名称空间就是一个映射全路径名到原数据的查找表。通过前缀压缩，这个表可以高效的在内存中存储。名称空间树型结构上，每个节点（绝对文件名或者目录名）有一个关联的读写锁。

* 每个Master 操作执行前需要一系列的锁。例如：如果一个操作涉及 /d1/d2/.../dn/leaf,它将获取目录名/d1,/d1/d2,...,/d1/d2/.../dn,  上的读锁，并且再全路径名/d1/d2/.../dn/leaf 上的读锁或者写锁。注意，leaf 是一个文件还是一个目录取决于操作。

* 我们现在说明锁机制如何防止 /home/user/foo 文件被创建，当 /home/user 被快照到 /save/user。快照操作需要获得/home,/save上的读锁。以及 /home/user 和/save/user上的写锁。文件创建获取/home, /home/user 上的读锁。以及/home/user/foo 上的写锁。这两个操作将会顺序化执行因为它们尝试获得/home/users上的锁是冲突的。文件创建不需要获得父目录上的写锁，因为没有目录，或者像inode等禁止修改的数据结构。文件名上的读锁有效的防止了父目录被删除。
* 采用这种锁的优点是它允许同一目录上的并发的操作。比如在同个目录上可以同时创建多个文件：每个操作都获取目录名上的读锁以及文件名上的写锁。目录名上的读锁防止目录被删除，重命名或者被快照。文件名上的写锁序列化写操作，确保不会创建同名文件两次。
* 由于命名空间有许多节点，读写锁采用惰性分配（懒分配）策略，不再使用时立马删除。同样，锁在全序一致性下获取避免（防止）死锁：首先按命名空间的层次排序，同一层次下按字典（lexicographically）顺序排序。

#### 4.2 副本的位置

* 一个GFS集群是高度分布的多层次结构，而不是平面结构（一层）。典型的在多个不同机架中有上百的Chunk(块)服务器。这些Chunk(块)服务器被上百个来自相同或者不同的机架上的客户端轮流获取。不同机架上的机器间通信或者需要一次或者多次的网络交换机。另外一个机架上的出入带宽可能比机架上所有机器的加在一起的带宽低。多层次的分布式意味着对数据的灵活性，可靠性，可用性的一个特有挑战。
* Chunk副本的放置策略为了两个目的：最大的数据可靠性和可用性，最大的带宽利用率。为了两者。把副本分布在不同的机器上是不够的。这只能预防硬盘损坏或者机器失效以及最大化利用机器的带宽。我们必须把Chunk副本分配在不同的机架上。这保证了一些副本可以存活或者保持可用性当一个整个机架被破坏或者离线（例如,共享的资源 如网络交换机或者电源电路导致的问题）。这也意味着网络流量，特别是针对某个Chunk的读操作，可以利用多个机架的聚合带宽。另一方面写数据流必须流经多个机架。这是我们乐意做的取舍。

#### 4.3 创建，重新复制，重新负载均衡

* Chunk副本有三个创建的用途：Chunk创建，重新复制，重新负载均衡
* 当Master创建一个Chunk，选择哪里去放置初始化空副本。考虑几个因素。1.我们放置副本在磁盘使用率低的Chunk(块)服务器上。随着时间它会负载均衡Chunk(块)服务器间的磁盘使用2.我们想要限制每个Chunk(块)服务器上的最近创建的数量。尽管创建本身容易。但随之而来的事大量写入流量因为Chunk在Writer真正写入数据时候才被创建。并且在我们的一次追加读多次的工作模式下，他们一旦被完全的写入后成为只读的。3.综上所述，我们想要一份Chunk的分布副本在多机架上。
* 当Chunk的有效副本数量降到用户指定的数目以下，Master 会再复制Chunk。这可能因为多个原因发生：一个Chunk(块)服务器变的不可获得，他报告他的副本损坏了，它的磁盘因为错误变得不可用，或者复制因素增加了。每个需要被再复制的Chunk基于几个因素被优先级排序的。一个就是离复制目标差有多大.例如：我们给更高的优先级给丢失了两个副本的Chunk而不是丢失一个的。另外，我们选择先复制Chunk的活跃文件而不是刚被删除文件的Chunk。最后为了最小化失败对于运行的应用的影响，我们提高任何正在阻塞客户单进程的Chunk的优先级。
* Master 采用优先级最高的Chunk，命令Chunk(块)服务器去复制Chunk数据直接从可用的副本。新的副本的放置策略和被创建出来的类似。平衡磁盘利用率，限制单个Chunk(块)服务器上的有效的复制操作，机架上的分布副本。为了避免复制流量压倒客户端流量。Master 限制集群以及Chunk(块)服务器上有效的复制操作数量。每个Chunk(块)服务器通过限制它对于原Chunk的读请求频率来限制在复制操作上所占用的带宽。
* 最后，Master 周期性的对副本进行负载均衡： 他检验现在的副本的分布并且移动副本以来为了更好的利用磁盘空间和负载均衡。同样通过这个操作，Master缓慢的填满一个新的Chunk(块)服务器而不是用新Chunk立即填满它,与之而来的高写流量。新副本的放置策略就像上面所讨论的。另外Master 必须选择移除哪一个副本。正常来说，它选择移除那些Chunk(块)服务器上剩余空间低于平均值的来平衡磁盘空间使用



#### 4.4 垃圾收集

* 在文件被删除后，GFS不会立马回收物理空间。GFS空间回收采用惰性策略，只在文件和Chunk层上定期的垃圾收集时进行。我们发现这种方式使得系统更加的简单，可靠。

#### 4.4.1 机制

* 当一个文件被应用删除，Master 像记录其他操作一样，立刻以日志方式记录删除操作。然而，Master 不会立刻回收资源这个文件，而仅仅时把文件重命名为一个带有删除时间戳的隐藏名。在Master定期扫描文件系统命名空间，它将删除隐藏文件如果它们已经存在超过三天（时间间隔可配置）。直到被删除，文件始终可以被读通过一个新的特别的名称并且可以取消删除通过重命名它。当隐藏文件从命名空间删除，它在内存中的原数据被擦除。这个有效的切断文件和它包含的所有的Chunk的连接。
* 在对Chunk命名空间的常规扫描中。Master标记孤儿Chunk（不被任何文件包含的Chunk）并且擦除这些Chunk的原数据。在与master 交互的心跳信息中，每个Chunk(块)服务器报告它所拥有的Chunk子集信息，并且master回复Chunk 服务器 master元数据中不存在的所有Chunk。Chunk(块)服务器可以随意删除这些Chunk的副本。

#### 4.4.2 讨论

* 尽管分布式的垃圾回收是一个在编程语言中需要复杂的解决方案的难题。在我们的案例中非常简单。我们可以简单地获得有Chunk所有引用：存储在master的文件到块的映射表中。我们也可以简单的获得所有的Chunk副本：它们是每个Chunk(块)服务器中定义好的目录下的linux文件。任何不被Master知道的副本都是 “垃圾”。
* 垃圾回收相较于直接删除在空间回收上有几个优势。首先，这很简单并且可靠在一个组件失效是常态的大型的分布式系统中。Chunk创建可能在某些副本中成功而其他中不成功，失败的副本 处于Master 无法是别的状态。副本删除消息或许会丢失，Master需要记得重发删除消息包括它自身以及Chunk(块)服务器。垃圾回收提供一个统一的可靠的方式去清除无用的副本。第二，它合并存储空间回收操作到master的后台活动中。例如定期的命名空间扫描以及和Chunk(块)服务器的握手。因此这些是批量处理并且开销被分摊了。另外,垃圾回收只有Master相对空闲时候执行。Master可以快速响应那些更多需要快速反映的的客户端请求。第三，回收垃圾的延迟操作为意外，不可逆的删除提供了安全保障。
* 根据我们的使用经验，主要地缺点是延迟在存储紧缺时候有时阻碍用户的调优操作，应用快速的创建和删除临时文件或者不会去正确的释放空间。我们通过显式的再次删除一个被删除文件的方式 加速空间回收。我们也允许用户使用不同的复制或者回收策略在不同而命名空间上。例如，用户可以指定某些目录树下的文件不做复制，并且删除的文件被立即，不可恢复的从文件系统中删除。

#### 4.5 过期的副本检测

* Chunk(块)服务器失败或者在它宕机时候Chunk副本可能过期因为错过一些修改操作。对每个Chunk，Master 维持一个Chunk版本号来区分最新的和过期的副本
* 无论何时，Master 和Chunk签订一个新的租约，它增加Chunk的版本号，通知最新的副本。Master 和这些副本都记录新的版本号在自己的持久化存储的状态信息中。这些发生在所有客户端被通知前。因此发生在开始写Chunk之前。如果某个副本当下不可用，它的Chunk版本号将被更新。Master 将会检测这个Chunk(块)服务器有一个过期的副本，当这个Chunk服务重启并且报告自己的副本集和它的关联版本号。如果master 看到一个版本号大于自己记录里面的版本号。Master 假定它在授权租约时候失败并且用最高的版本号更新当前版本号。
* Master 在定期的垃圾收集中删除过期的副本。在这之前，它简单的认为过期的副本不存在当它应答客户端的请求Chunk信息时。 另一个防护措施，Master通知客户端哪个Chunk(块)服务器拥有租约，或者 它授权一个Chunk(块)服务器从其他Chunk(块)服务器复制时，消息都附带了Chunk的版本号。客户端或者Chunk(块)服务器校验版本号当它执行操作以确保它总是获得最新的数据。

#### 5 容错和诊断

​	在设计系统时我们最大的挑战是处理经常发生的组件失效。组件的质量和数量一起决定这些问题是否经常或者少于异常：我们不能完全相信机器，也不能完全相信磁盘。组件失效可能导致一个不可用的系统或者更糟糕脏数据。我们讨论如何实现这些挑战并且我们构建进系统的工具来诊断问题当它们不可避免的发生。

##### 5.1高可用

​	在GFS集群中的几百个服务，一些必定是不可靠的在给定时间内。我们保持整个系统高可用通过两个简单同样高效的策略：快速恢复和副本

###### 5.1.1 快速恢复

​	Master和ChunkServer被设计来恢复他们的状态并且在数秒内启动不管它们如何被终止。事实上，我们没有区分常规和非常规的终止。服务常规的停止通过杀死进程。客户端或者其他服务经理一个小的间隔例如它们在未解决的请求上超时。和重启的服务重连，并且重试。章节6.2.2包高观察启动时间。

###### 5.1.2Chunk复制

​	像前面讨论的，每个Chunk在多个ChunkServer，多个机架上复制。用户可以对不同的命名空间部分指定不同的副本级别。Master复制最近的副本 来保持每个副本完全复制像ChunkServer离线或者检测脏副本通过校验和验证，尽管副本已经很好地服务了，我们探索其他形式的跨服冗余。例如奇偶校验码或者擦除码为我们的增长制度存储需求。我们希望这是个挑战但是实现这些更多复杂冗余模式在我们的非常宽松结对的系统应为偶们的流量是被追加和读占据而不是小的随机写

###### 5.1.3Master复制

​	Master的状态被复制来保证可靠性，它的操作日志和CheckPoint 被复制到多个机器上。修改状态的操作只有它的日志记录已经被刷盘到本地磁盘和其他所有副本中才会被认为是提交了。为了简化，我们的Master进程保持负责所有修改操作如后台活动。例如垃圾收集在内部改变系统。当它失败，它可以立马重启。如果机器或者磁盘失效，GFS外的监控架构在别处通过复制的日志开启新的Master进程。客户端使用正式的Master名称，是DNS的别名可以被改变如果master被重新定位到新的机器上。

​	影子Master提供对文件系统的只读操作甚至当Master失效时候。他们是影子，不是镜子。因此它们可能稍微滞后于主节点。典型部分，他们增强读可获得性不介意获得稍微过期数据。事实上，由于文件内容从ChunkServer上读取，应用程序不观察过期文件内容。可能在一个短暂窗口内过期的是文件Metadata,像目录内容或者获得控制信息。

​	为了保持自身被通知。一个影子Master读增长的操作日志副本并且应用相同的顺序改变自身的数据结构如果Master一样。它在启动时候轮询ChunkServer来定位Chunk副本并且和它们 经常性交换握手信息来监控他们的状态，它仅仅在主决定创建或者删除副本是副本位置信息更新是才依赖主。

###### 5.2数据完整性

​	每个ChunkServer使用校验和来检测存储的数据是否过期。一个GFS集群经常有上千的磁盘在成百的机器上，经常经历磁盘失效，这导致数据腐败或者丢失读写路径。我们可以使用其他Chunk副本来从腐败中恢复。但是这将会妨碍检测腐败通过比较ChunkServer间的副本。更有，有分歧的副本可能成为合法的，GFS的修改语义特别是前面讨论的原子操作追加，不能保证相同的副本，因此每个副本必须单独校验他们的自己副本的完整性通过维持校验和。

​	Chunk被拆分成64KB块。每个有对应的32bit校验和。像其他的metadata，校验和保存在内存中，并且永久的存储通过日志和我们的用户数据分离。

​	为了读，ChunkServer验证数据块的校验和重复读的区域在给任何请求返回数据前，是否客户端或者其他ChunkServer.因此ChunkServer将不会传播腐败到其他机器。如果一个block和记录的校验和不匹配。ChunkServer会给请求者返回一个错误，并且向Master报告这个不匹配。作为响应，这个请求则会从其他副本读。同时Master会从其他副本克隆Chunk。在一个被验证的新副本，Master指示ChunkServer删除不匹配的副本。

​	校验和因为以下几个原因对读性能有一点点影响。由于大多数的读至少一些Blocks.我们需要读和校验众多额外数据中相关的很小的部分。GFS客户端代码极大的减少了这个过度通过尝试别名读在校验和块的边界。更有，校验和的寻找和压缩在ChunkServer没有任何I/O，并且校验和计算可以经常被重复。

​	为了在Chunk结尾追加写，校验和计算被极大的优化。因为他们被我们的工作负载。我们

###### 5.3诊断工具