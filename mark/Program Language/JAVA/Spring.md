## Spring
------
#### 核心思想

- 解耦合 + 分层

#### Design Pattern

- Template    bean的 init  method
- Proxy   aop
- Strategy 
- intercept  chain 

**容器启动原理：**Spring应用的IOC容器通过tomcat的Servlet或Listener监听启动加载；Spring MVC的容器由DispatchServlet作为入口加载；Spring容器是Spring MVC容器的父容器

### IOC

#### 核心类

* BeanFactory  Bean 管理的核心类

  * **AutowireCapableBeanFactory**  自动注入

  * **ConfigurableBeanFactory**   它定义了设置类装载器、属性编辑器、容器初始化后置处理器
  * **HierarchicalBeanFactory**  父子级联 IoC 容器的接口
  * **ListableBeanFactory**  
  * **SingletonBeanRegistry** 缓存已经生成的实例bean
  * ApplicationContext 继承BeanFactory 对类功能进行拓展和延伸(开发者操作的核心类)

* BeanDefinition 

* BeanDefinitionRegistry

#### Bean 生命周期

![](..\..\resource\Spring\SpringBeanLife.jpg)



#### Bean加载

![](..\..\resource\Spring\SpringBeanLoad.jpg)

####  ApplicationContextInitializer

  spring 容器刷新前的一个回调函数
  @Order(1) 
  **实现方式**

  * application.addInitializer()
  *  配置文件 context.initializer.classes = ?,?
  * META/INF 文件夹下 spring.factories 
      org.springframework.context.ApplicationContextInitializer = ?
#### **ApplicationListener**
  * 观察者模式
  * 监听注册事件
#### Spring Event
```java
  * ContextRefreshedEvent
  * ContextStartedEvent
  * ContextStoppedEvent
  * ContextClosedEvent
  * RequestHandledEvent
```
#### Spring 循环引用的问题

* singleObject（1）

* earlySingleObject（2） 

* currentlyCreateOject（3）

* factoriesObject（4）

bean 在构造前会加入到（3）中，构造完，在populate 属性前会加入到（4）zhong， 在注入属性时候发现注入的类在（3）中，则看（2）中是否有，没有则会在（4）中的为装配属性的对象返回，并且把对象放入到(2)中。

A->B->C->A(C中注入的属性对象就是为装配的的A)



### AOP

------

#### Spring bean 的BeanDefinition