### ES本地调试代码

------

#### 基本元素

* ES 源码
* ES 编译好的可运行程序
* Gradle
* gradle-{version}-all.zip
* JDK(高版本)
* IDEA

#### 步骤

* 修改es 源码目录中gradle/wrapper/gradle-wrapper.properties 文件

  ```properties
  #distributionUrl=https\://services.gradle.org/distributions/gradle-6.7-all.zip
  distributionUrl=gradle-6.7-all.zip
  ```

* 配置gradle 下载加速

  ```properties
  #修改下面俩个文件
  elasticsearch\benchmarks\build.gradle
  elasticsearch\client\benchmark\build.gradle
  #增加 阿里云下载加速
  buildscript {
    repositories {
      maven {
        url 'http://maven.aliyun.com/nexus/content/groups/public/'
      }
    }
    dependencies {
      classpath 'com.github.jengelman.gradle.plugins:shadow:2.0.2'
    }
  }
  
  ```

* 在elasticsearch目录下 执行 gradlew -idea --debug

* idea 下gradle构造项目

* 项目运行参数

  ```properties
  -Des.path.conf=D:\program\elasticsearch-7.10.1\config
  -Des.path.home=D:\program\elasticsearch-7.10.1
  -Dlog4j2.disable.jmx=true
  #这个文件用来增加各种权限，看系统运行报哪个权限
  -Djava.security.policy=D:\program\elasticsearch-7.10.1\config\java.policy
  #java.policy 内容格式
  grant {
     permission java.lang.RuntimePermission "createClassLoader";
     permission java.lang.RuntimePermission "setContextClassLoader";
     permission java.lang.RuntimePermission "getClassLoader";
  };
  ```

* build.gradle 中的依赖问题

  ```java
  修改 compileOnly project(':libs:plugin-classloader') 
  为 compile project(':libs:plugin-classloader')
  再重新打包
  ```

  