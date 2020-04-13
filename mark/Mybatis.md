### Mybatis

#### 核心功能点

* 配置解析（Configurian.parse）
* SQL执行（）
* 结果解析处理（）
* 缓存(一级缓存（SqlSession），二级缓存（nameSpace共享）)
* 插件 Interceptor(动态代理，层层嵌套，注意顺序), plugins

####  核心类

* BaseExecutor（执行类）
* MapperAnnotationBuilder/XMLMapperBuilder 构建类和xml的初始解析加载
* SqlSessionFactoryBean 负责生成SqlSessionFactory。 SqlSessionFactory 创建Sqlsession
* MapperProxy(JDK代理类，封装Mapper)
* 

JDK动态代理来实现封装功能调用

Bean 注入的启动是在什么时候？

执行流程

![屏幕快照 2020-01-07 下午11.20.10](/Users/zhaoxiangyu/Desktop/屏幕快照 2020-01-07 下午11.20.10.png)

MapperProxy ->Sqlsession ->Executor-> StatementHandler->ParamterHandler->TypeHandler->Statement

ResultSet->TypeHandler->ResultSetHandler->StatementHandler->Executor->SqlSession