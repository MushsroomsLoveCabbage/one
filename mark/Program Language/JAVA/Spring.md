## Spring
------
#### 1 总概

##### 1.1核心思想

- 解耦 + 分层

##### 1.2 Design Pattern

- Template    bean的 init  method
- Proxy   aop
- Strategy 
- intercept  chain 

##### 1.3 **容器启动原理**

* Spring应用的IOC容器通过tomcat的Servlet或Listener监听启动加载；Spring MVC的容器由DispatchServlet作为入口加载；Spring容器是Spring MVC容器的父容器

### 2. IOC

##### 2.1核心思想

* bean对象 由工厂统一管理

##### 2.1核心类

* BeanFactory  Bean 管理的核心类

  * **AutowireCapableBeanFactory**  自动注入

  * **ConfigurableBeanFactory**   它定义了设置类装载器、属性编辑器、容器初始化后置处理器
  * **HierarchicalBeanFactory**  父子级联 IoC 容器的接口
  * **ListableBeanFactory**  
  * **SingletonBeanRegistry** 缓存已经生成的实例bean
  * ApplicationContext 继承BeanFactory 对类功能进行拓展和延伸(开发者操作的核心类)
* BeanDefinition  Bean 信息
  * BeanDefinitionRegistry 注册管理BeanDefinition
* BeanNameAware、BeanFactoryAware、InitializingBean和DiposableBean, Bean级生命周期接口方法　
* InstantiationAwareBeanPostProcessor 和 BeanPostProcessor  容器级生命周期接口方法　

##### 2.2 Bean 生命周期

###### 2.2.1

![](..\..\resource\Spring\SpringBeanLife.jpg)

##### 2.3 Bean加载

![](..\..\resource\Spring\SpringBeanLoad.jpg)

```java
public void refresh() throws BeansException, IllegalStateException {
        synchronized (this.startupShutdownMonitor) {
            // Prepare this context for refreshing.
            prepareRefresh();
            // Tell the subclass to refresh the internal bean factory.
            //创建beanfactory,装载类的BeanDefinition
            ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
            // Prepare the bean factory for use in this context.
            // 标准上下文特性，如类装载器，PostProcesser
            prepareBeanFactory(beanFactory);
            try {
                // Allows post-processing of the bean factory in context subclasses.
                postProcessBeanFactory(beanFactory);
                // Invoke factory processors registered as beans in the context.
                invokeBeanFactoryPostProcessors(beanFactory);
                // Register bean processors that intercept bean creation.
                registerBeanPostProcessors(beanFactory);
                // Initialize message source for this context.
                initMessageSource();
                // Initialize event multicaster for this context.
                initApplicationEventMulticaster();
                // Initialize other special beans in specific context subclasses.
                onRefresh();
                // Check for listener beans and register them.
                registerListeners();
                // Instantiate all remaining (non-lazy-init) singletons.
                finishBeanFactoryInitialization(beanFactory);
                // Last step: publish corresponding event.
                finishRefresh();
            }
            catch (BeansException ex) {
                // Destroy already created singletons to avoid dangling resources.
                beanFactory.destroySingletons();
                // Reset 'active' flag.
                cancelRefresh(ex);
                // Propagate exception to caller.
                throw ex;
            }
        }
    }
```

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

bean 在构造前会加入到（3）中，构造完，在populate 属性前会加入到（4）中， 在注入属性时候发现注入的类在（3）中，则看（2）中是否有，没有则会在（4）中的为装配属性的对象返回，并且把对象放入到(2)中。

A->B->C->A(C中注入的属性对象就是为装配的的A)

### 3. AOP

------

#### 3.1 核心是在目标的代理对象上做功能拓展

#### Spring bean 的BeanDefinition



#### 

#### 4.拓展点

* ApplicationContextInitializer
* SpringApplicationRunListener.contextLoad();
* BeanDefinitionRegistryPostProcessor