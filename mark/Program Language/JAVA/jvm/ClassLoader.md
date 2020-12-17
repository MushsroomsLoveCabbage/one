### ClassLoader

------

#### 双亲委派机制

#### ClassLoader 的传递性

* 方法执行时遇到一个未知的类，选用当前调用类的classLoader来加载类

#### SPI机制

* 在Bootstrap 加载接口以及实现类时，由于实现类在jar包中，由application classloader加载，因此需要设置线程上下文的classloader 来打双亲委派机制

#### 线程上下文

* 可以通过设置指定当前线程的classloader



#### 使用场景

* 类隔离
* 