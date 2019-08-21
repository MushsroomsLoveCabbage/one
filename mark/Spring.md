
**1)ApplicationContextInitializer**
---
  spring 容器刷新前的一个回调函数
  @Order(1) 
  *_实现方式*_
  * (1) application.addInitializer()
  * (2) 配置文件 context.initializer.classes = ?,?
  * (3) META/INF 文件夹下 spring.factories 
      org.springframework.context.ApplicationContextInitializer = ?
---
**2)ApplicationListener**
---
  * 观察者模式
  * 监听注册事件
---
**3)ApplicationContext**
  * (1)ContextRefreshedEvent
  * (2)ContextStartedEvent
  * (3)ContextStoppedEvent
  * (4)ContextClosedEvent
  * (5)RequestHandledEvent
