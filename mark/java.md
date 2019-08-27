#### 编译时候添加编码格式
* javac -encoding UTF-8 ***.java

#### JAVA测试注意事项（摘录于R大知乎评论）
* 在HotSpot VM上跑microbenchmark切记不要在main()里跑循环计时就完事。这是典型错误。
重要的事情重复三遍：请用JMH，请用JMH，请用JMH。除非非常了解HotSpot的实现细节，
在main里这样跑循环计时得到的结果其实对一般程序员来说根本没有任何意义，因为无法解释。
* 就算计时，用System.nanoTime()也比new Date().getTime()好太多
* 要给常量赋个别名的话，为了保证靠谱请习惯性加上final。例如说这里题主要是写 final int range = 8000;
就会发现第三层循环的条件改用range也一样快。
* 跑microbenchmark多留意一下 -XX:+PrintCompilation 的输出。

***java 动态追踪***
关键接口：java.lang.instrument.Instrumentation
redefineClasses 重定义class文件（用新文件，替换旧的文件）（元层）
retransformClasses 修改class文件（在旧文件上修改）

ASM框架直接修改class文件生成子类(Spring cglib)
Spring直接依赖这个来创造class文件，然后加载。
* [btrace](https://github.com/btraceio/btrace)
* [Arthas](https://github.com/alibaba/arthas)

* [参考文章/美团技术团队 Java动态追踪技术探究](https://tech.meituan.com/2019/02/28/java-dynamic-trace.html)
