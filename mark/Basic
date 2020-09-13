1)使用log打印日志时，需要 isDebugEnabled,isInfoEnabled判断。
  因为使用的话，即使当前系统日志级别高于log级别，不会输出日志，但日志输出内容还是会拼接字符串，即如果直接输出信息则无所谓。
2)Ctrl+shift+alt+u 展示类关系结构
  Alt + 7  展示类结构
  Ctrl+Alt+L : 一键格式化代碼
  Ctrl + I  实现继承方法
  Ctrl+shift+BackSpace 返回上一个文件
  Ctrl+Shift+u 大小写
  ctrl+alt+t 快速插入代码
  alt -  ctrl + w  
3)进入VI后，按/，然后输入字符串，回车，按N或者n是向前向后搜索该字符串

4)/AEGame/Unity3dM_cn_cn_Server/S340010000/WebRoot/WEB-INF/

update map_cell set n_tid = (SELECT n_rid FROM dict_map_resource 
WHERE n_rid >= (SELECT floor(RAND() * (SELECT MAX(n_rid) FROM dict_map_resource)))  
ORDER BY n_rid LIMIT 1) where n_type=3

Oriented 面向
Ambiguity 双关
SSL（Secure Sockets Layer）及其继任者传输层安全（Transport Layer Security，TLS）
是为网络通信提供安全及数据完整性的一种安全协议。TLS与SSL在传输层对网络连接进行加密。

5) linux rz 上传 sz 下载

6) builder 适用于对象构造参数多，对象需要vaild的。

7) linux 日志查找 cat game.GameServer.nohup |grep NPC配置

8) windows service.msc

9) netstat -an -p tcp | find "X.X.X.X:PORT" | find "ESTABLISHED" 

10) java.io.FileNotFoundException: .\xxx\xxx.txt (系统找不到指定的路径。)是当前所指定的文件不存在或者目录不存在。 
java.io.FileNotFoundException: .\xx\xx (拒绝访问。)是因为你访问了一个文件目录，而不是文件，因此会抛出问题2的异常。

11）windows 文件默认编码是ANSI,为了支持Unicode 通过在文件头添加BOM来标识。如果是0xFF 0xFE，是UTF16LE，如果是0xFE 0xFF则UTF16BE，如果是0xEF 0xBB 0xBF，则是UTF-8。（window打开时候默认是通过这些来选择解码方式，这在linux中会导致乱码问题）