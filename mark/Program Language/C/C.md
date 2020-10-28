#### C

* http://c.biancheng.net/view/1993.html

##### 基础部分

- GCC 编译器
- 文件格式 .c, .h 结尾,编译后.o 结尾

##### 关键字

- | 关键字   | 说明                                             |
  | -------- | ------------------------------------------------ |
  | register | 声明寄存器变量                                   |
  | extern   | 声明变量或函数是在其它文件或本文件的其他位置定义 |

##### 数据类型



##### 指针
##### 

```c
int array[] = {1,2,3,4,5};
int* p = arr;//p获取的是数组的起始地址
int array1 = *(P+1);//取数组第二个元素，(地址增加会按照指针类型和起始地址来增加，本质上地址是个整数)
int* p2 = *arr[2];//指针指向数组第二个元素地址

```


- &var 获取变量地址
- 定义 ： type \*var-name \
- 编译器会将`p->member`变成访问`p+offset_member`这个内存地址的变量
- 编译器会将`s.member`变成访问`&s+offset_member`这个内存地址的变量

```C
#include <stdio.h>
 typedef struct redisObject {
    unsigned type:4;
    unsigned encoding:4;
    unsigned lru:LRU_BITS; /* LRU time (relative to global lru_clock) or
                            * LFU data (least significant 8 bits frequency
                            * and most significant 16 bits access time). */
    int refcount;
    void *ptr;
} robj;
 
int main ()
{
   int  var = 20;   /* 实际变量的声明 */
   // var 取变量值
   // &var 取变量地址
   // p存变量地址
   // *P 变量值
   int  *ip;        /* 指针变量的声明 */ 
   ip = &var;  /* 在指针变量中存储 var 的地址 */ 
   printf("Address of var variable: %p\n", &var  );
   /* 在指针变量中存储的地址 */
   printf("Address stored in ip variable: %p\n", ip );
   /* 使用指针访问值 */
   printf("Value of *ip variable: %d\n", *ip );
   //数组指针的使用
   int array[10];
   //指针拿到了数组的初始地址
   int* arrayPoint = &array;
   //指针数组虽然未定义，但是在装换中直接使用初始的地址+偏移量获得了内存位置中的对象
   arrayPoint[0];//获得的是
   //redis 中client 对象属性 
   robj **argv;
   //获得*argv的首地址+i*offset的对象的ptr指针
   c->argv[i]->ptr;
   return 0;
}
```

- 函数指针

  ```c
  
  ```

  

- 回调函数

  ```c
  
  ```

```c
typedef 定义新的类型(可以用来定义对象)
#定义BYTE 为 unsinged char
typedef unsigned char BYTE;
BYTE B1,B2;

typedef struct Book{
    char title[100];
    char author[100];
} Book;

Book boo;


```

