
用钱相关的来做比喻

算法 类似于数钱，一般人是一张一张数，或者有人用数钞机，算法的人就可能用(分类然后称重)这种方式来数。算法强调的是你的思路想法
编程 就像钱来表达价值，用人民币是一种方式，美元也是一种方式。
数据结构 用来存钱的容器，有人用保险柜存，安全但是取钱麻烦，有人用皮夹子存，快捷但是存的不多。不同的数据结构存的就是数据，也会有不同的优缺点。

最优子结构,主问题所需要的最优解,肯定依赖于子问题(子问题存在多个)的最优解。


计算机系统的一个重要概念在于通过抽象来隐藏实现的复杂性，
I/0 -> 文件
I/0 + 内存 -> 虚拟存储器
处理器 -> 指令级结构
I/0 + 内存 + 处理器 —> 进程
I/0 + 内存 + 处理器 + 操作系统 —> 虚拟机

script language -> dynamic language


高级语言->汇编语言->执行

解释型语言 java, python(运行时候再翻译)
编译型语言 C, C++(编译时候直接翻译转换成机器码)·

loopback address(127.0.0.1)
domain socket
inter-process communication
            缓存
查询，连接器，分析器，优化器，执行器


select version()
group by col (order by col 默认的) order by null 干掉排序
\u unicode \x 16进制
\b 二进制 \o 8进制

CURRENT_TIMESTAMP

ssh -p xx user@ip 
结构化
上下线性
推演

Buffer Manager 

RedoLog  事务日志，防止crash 来减少脏页的刷盘。
undoLog  logical 级别的只针对某一行。
compensation log record

@r1:=@r1+1 AS roleId,
(SELECT @r1:=0) tmp

lsof -i:端口号
netstat -tunlp

select DATE_SUB(now(), INTERVAL ROUND(161)  DAY) from dual;

cat -n log |grep ""
cat -n log |tail  -n +1000 |head -n 100

find / -name  "title"
