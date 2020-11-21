### Mysql

#### 安装

**注意点**

* 使用管理员权限操作
* 手动创建配置.ini文件

**命令**

* mysqld install
* mysqld --initialize-insecure



##### Cascading Aborts

#### two phase-lock

- 事务开启后，会依次有加锁解锁阶段，加锁阶段只能加或者升级锁，解锁阶段只能解锁或者降级锁

***stict two phase lock***

- 显式的标记rollback/commit 为解锁阶段(事务太过复杂，数据库无法判断什么时候是主动的判定什么时候是加锁或者解锁阶段)



#### 事务隔离

- 死锁是发生在写数据阶段（写锁）排他锁

