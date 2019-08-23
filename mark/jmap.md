***jmap***

* jmap <pid>
  使用不带选项参数的jmap打印共享对象映射， 
  将会打印目标虚拟机中加载的每个共享对象的起始地址、
  映射大小以及共享对象文件的路径全称。

## jinfo
***GC 信息（-gc）***  
* S0C：年轻代中第一个survivor（幸存区）的容量 （字节）
* S1C：年轻代中第二个survivor（幸存区）的容量 (字节)
* S0U   ：年轻代中第一个survivor（幸存区）目前已使用空间 (字节)
* S1U     ：年轻代中第二个survivor（幸存区）目前已使用空间 (字节)
* EC      ：年轻代中Eden（伊甸园）的容量 (字节)
* EU       ：年轻代中Eden（伊甸园）目前已使用空间 (字节)
* OC        ：Old代的容量 (字节)
* OU      ：Old代目前已使用空间 (字节)
* MC：metaspace(元空间)的容量 (字节)
* MU：metaspace(元空间)目前已使用空间 (字节)
* YGC    ：从应用程序启动到采样时年轻代中gc次数
* YGCT   ：从应用程序启动到采样时年轻代中gc所用时间(s)
* FGC   ：从应用程序启动到采样时old代(全gc)gc次数
* FGCT    ：从应用程序启动到采样时old代(全gc)gc所用时间(s)
* GCT：从应用程序启动到采样时gc用的总时间(s)

***内存中young,old,perm 使用和占用(-gccapacity)***
* NGCMN   ：年轻代(young)中初始化(最小)的大小(字节)
* NGCMX    ：年轻代(young)的最大容量 (字节)
* NGC    ：年轻代(young)中当前的容量 (字节)
* S0C  ：年轻代中第一个survivor（幸存区）的容量 (字节)
* S1C  ：    年轻代中第二个survivor（幸存区）的容量 (字节)
* EC    ：年轻代中Eden（伊甸园）的容量 (字节)
* OGCMN     ：old代中初始化(最小)的大小 (字节)
* OGCMX      ：old代的最大容量(字节)
* OGC：old代当前新生成的容量 (字节)
* OC     ：Old代的容量 (字节)
* MCMN：metaspace(元空间)中初始化(最小)的大小 (字节)
* MCMX ：metaspace(元空间)的最大容量 (字节)
* MC ：metaspace(元空间)当前新生成的容量 (字节)
* CCSMN：最小压缩类空间大小
* CCSMX：最大压缩类空间大小
* CCSC：当前压缩类空间大小
* YGC   ：从应用程序启动到采样时年轻代中gc次数
* FGC：从应用程序启动到采样时old代(全gc)gc次数
