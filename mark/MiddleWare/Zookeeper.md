### Zookeeper

### 1.核心作用

* 存储原信息
* 提供系统HA
  * 主系统创建临时节点，从系统监听。主挂掉后从注册临时节点
* 分布式锁
* 分布式协调
  * A系统想监听B系统的结果,可以让A对某个节点的值注册个监听器，然后B在做完后修改这个值。这样就可以通知到A了

### 2.

