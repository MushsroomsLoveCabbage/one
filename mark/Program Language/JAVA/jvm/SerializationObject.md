#### 对象序列化

##### 

```java
static class SerializationObject implements Serializable {

        private static final long serialVersionUID = 1L;

        private byte bbbbbb;

        private byte aaaaaa;

        private int cccccc;

        public SerializationObject(byte byteType, int intType) {
            this.bbbbbb = byteType;
            this.aaaaaa = byteType;
            this.cccccc = intType;
        }
    }
//序列化后的byte数组数据 ascill
-84,-19, (short)  ObjectStreamConstants STREAM_MAGIC
0,5,     (short)  ObjectStreamConstants STREAM_VERSION
115,     (byte)   ObjectStreamConstants  TC_OBJECT
114,     (byte)   ObjectStreamConstants  TC_CLASSDESC
0,65,    (short)  class名长度
(99,111,109,46,122,120,121,46,100,117,98,98,111,46
112,114,111,118,105,100,101,114,46,116,101,115,116,46,83,101,114,105,97,108,
105,122,97,116,105,111,110,84,101,115,116,36,83,101,114,105,97,108,105,122,
97,116,105,111,110,79,98,106,101,99,116)  class名 SerializationTest$SerializationObject
0,0,0,0,0,0,0,1, (long)  序列化UID 
2,       (byte) ObjectStreamConstants  Bit masks
0,3,     (short) 类字段属性的数量
数据类型(66 -> B -> Byte) 字段名长度(0,6 -> 6->(short类型)), 字段名(98,98,98,98,98,98 -> aaaaaa)
66,0,6, 97,97,97,97,97,97, 
66,0,6, 98,98,98,98,98,98,
73,0,6, 99,99,99,99,99,99,
120,    (byte) ObjectStreamConstants TC_ENDBLOCKDATA
112,    (byte) ObjectStreamConstants TC_NULL 父类（） writeClassDesc
5,  (byte) 数据
5,  (byte) 数据
0,0,0,7 (int) 数据

```

