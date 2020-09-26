
 1)ApplicationContextInitializer
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

### Spring 循环引用的问题

* singleObject（1）

* earlySingleObject（2）

* currentlyCreateOject（3）

* factoriesObject（4）

bean 在构造前会加入到（3）中，构造完，在populate 属性前会加入到（4）zhong， 在注入属性时候发现注入的类在（3）中，则看（2）中是否有，没有则会在（4）中的为装配属性的对象返回，并且把对象放入到(2)中。

A->B->C->A(C中注入的属性对象就是为装配的的A)



Spring bean 的BeanDefinition