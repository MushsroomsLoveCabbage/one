### 面向对象原则

#### 封装、抽象、继承、多态

**封装(Encapsulation)**

```java 
package com.zxy.learning.designpattern;

import java.math.BigDecimal;

/**
 * 不提供全局所有个getter setter 方法，只提供部分对外开放的修改方法，在该方法下修改属    性
 * @author zxy
 * @version 1.0.0
 * @ClassName Wallet.java
 * @Description
 * @createTime 2020年11月05日 18:49:00
 */
public class Wallet {
    //属性不对外
    private int id;

    private BigDecimal money;

    private long midifyTime;

    private long createTime;

    public Wallet(){
        //通过唯一ID生成器生成
        //IdGenerator.getInstance().generate();
        this.id = 1;
        this.money = BigDecimal.ZERO;
        this.createTime = System.currentTimeMillis();
        this.midifyTime = createTime;
    }
    
    public void increaseMoney(BigDecimal increasedAmount){
        if (increasedAmount.compareTo(BigDecimal.ZERO) < 0) {
            //throw new InvalidAmountException("...");
        }
        this.money.add(increasedAmount);
        this.midifyTime = System.currentTimeMillis();
    }
    
    public void decreaseMoney(BigDecimal decreasedAmount){
        if (decreasedAmount.compareTo(BigDecimal.ZERO) < 0) {
            //throw new InvalidAmountException("...");
        }
        if (decreasedAmount.compareTo(this.money) > 0) {
            //throw new InsufficientAmountException("...");
        }
        this.money.subtract(decreasedAmount);
        this.midifyTime = System.currentTimeMillis();
    }
    
    public BigDecimal getMoney() {
        return money;
    }
    public int getId() {
        return id;
    }
    public long getMidifyTime() {
        return midifyTime;
    }
    public long getCreateTime() {
        return createTime;
    }
}

```

#### 防止滥用全局变量和方法

- 将公共属性都写在一个类当中会导致该文件被修改后，其他引用的文件也需要被重新编译

  ```java
  public class Constans{
      public static final String MYSQL_ADDR_KEY = "mysql_addr";
      public static final String MYSQL_DB_NAME_KEY = "db_name";
      public static final String MYSQL_USERNAME_KEY = "mysql_username";
      public static final String MYSQL_PASSWORD_KEY = "mysql_password";
      // ...省略更多的定义...
  }
  
  ```

  

#### 封装不稳定的实现，暴露稳定的接口

#### 多用组合而非继承

```java
public interface brid{
    public void fly();
    public void leg();
    public void shout();
}
public class sparrow implements brid{
    
} 
```

