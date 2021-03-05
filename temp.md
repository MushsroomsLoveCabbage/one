20201123
netstat -anp|grep {port}
ls /proc/{pid}/fd -l
lsof -iTCP:{port}

20201124
GC
起服时候的 频繁GC  动态扩容 参考 xms 和xmx 
min -> lowWaterMark-> committed-> capacity-> max

//System.gc() 的触发类型由 foreground 改为background
-XX:+ExplicitGCInvokesConcurrent 和 -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses

// MetaSpace
-XX:MinMetaspaceFreeRatio 和 -XX:MaxMetaspaceFreeRatio
jcmd <PID> GC.class_stats|awk '{print$13}'|sed  's/\(.*\)\.\(.*\)/\1/g'|sort |uniq -c|sort -nrk1
//详细的类加载卸载
XX:+TraceClassLoading 和 -XX:+TraceClassUnLoading

//过早晋升
Young/Eden 过小
年龄带调整

//GC 引用日志 
-XX:+PrintReferenceGC 

-XX:PrintFLSStatistics 来观察内存碎片率情况4.7.3 策略

分析到具体原因后，我们就可以针对性解决了，具体思路还是从根因出发，具体解决策略：

内存碎片：通过配置 -XX:UseCMSCompactAtFullCollection=true 来控制 Full GC的过程中是否进行空间的整理（默认开启，注意是Full GC，不是普通CMS GC），以及 -XX: CMSFullGCsBeforeCompaction=n 来控制多少次 Full GC 后进行一次压缩。
增量收集：降低触发 CMS GC 的阈值，即参数 -XX:CMSInitiatingOccupancyFraction 的值，让 CMS GC 尽早执行，以保证有足够的连续空间，也减少 Old 区空间的使用大小，另外需要使用 -XX:+UseCMSInitiatingOccupancyOnly 来配合使用，不然 JVM 仅在第一次使用设定值，后续则自动调整。
浮动垃圾：视情况控制每次晋升对象的大小，或者缩短每次 CMS GC 的时间，必要时可调节 NewRatio 的值。另外就是使用 -XX:+CMSScavengeBeforeRemark 在过程中提前触发一次 Young GC，防止后续晋升过多对象。
[](https://zhuanlan.zhihu.com/p/291044796)
//
histogram 柱状图

####20201125
predecessors 前辈
