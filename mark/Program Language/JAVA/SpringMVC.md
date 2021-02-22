### SpringMVC

------

#### 核心类

* DispatcherServlet

* ServletContext 中通过ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE 属性引用 WebApplicationContext

  ```java
  WebApplicationContext wac = (WebApplicationContext)servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
  ```

* ServletContextListener



#### 容器启动流程

* Web容器提供一个全局的上下文(ServletContext)

* Spring IOC容器通过属性 WebApplicationContext.ROOTWEBAPPLICATIONCONTEXTATTRIBUTE 存储于ServletContext中

* Web容器中配置多个Servlet，以Spring 的DispatcherServlet为例，其拥有自己的IOC上下文,会利用ServeltContext中的WebApplicationContext作为自己的上下文的parent 上下文，

  ```java
  //DispatcherServlet的初始化
  DispatcherServlet.initStrategies
  ```

  

