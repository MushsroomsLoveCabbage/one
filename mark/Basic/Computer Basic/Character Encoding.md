#### 1. 字符集

##### Unicode

- 统一字符编码集
- 16进制表达

| 汉字 | unicode | 2进制               |
| ---- | ------- | ------------------- |
| 机   | 0x673A  | 0110,0111,0011,1010 |

#### 2. 字符编码

##### 2.1 ASCII

- 英文字符编码

##### 2.2 UTF-8

- 对unicode字符进行1-4个字节编码

| Unicode编码   | **UTF-8 字节流(二进制)**            |
| ------------- | ----------------------------------- |
| 000000-00007F | 0xxxxxxx                            |
| 000080-0007FF | 110xxxxx 10xxxxxx                   |
| 000800-00FFFF | 1110xxxx 10xxxxxx 10xxxxxx          |
| 010000-10FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx |

##### 2.3 编码格式

* \u unicode 
* \x 16进制
* \b 二进制 
* \o 8进制