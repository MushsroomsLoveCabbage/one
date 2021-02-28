### 字符串匹配算法

* 基本共识 i 代表主串的下标偏移，j 代表匹配串的下标偏移。

#### 1.暴力法

* 从头到尾每个字符比对，遇到不匹配的，主串后移一位。

```java
    /**
     * 暴力匹配算法
     * 拿子串和主串一一匹配，完全匹配则记录，不匹配则主串后移一位
     */
    public List<Integer> bruteForce(String source, String target){
        List<Integer> resultList = new ArrayList<>();
        if(target== null || target.length() == 0){
            return resultList;
        }
        char[] sourceArray = source.toCharArray();
        char[] targetArray = target.toCharArray();
        int length = sourceArray.length;
        int targetLength = targetArray.length;
        for(int index = 0; index < length; index++){
            int tempIndex = index;
            for(int targetIndex = 0; targetIndex < targetLength; targetIndex++){
                if(sourceArray[index] == targetArray[targetIndex]){
                    index ++;
                    if(targetIndex == targetLength -1){
                        resultList.add(tempIndex);
                    }
                    continue;
                } else {
                    break;
                }
            }
            index = tempIndex;
        }
        return resultList;
    }
```



#### 2. Hash匹配法

* 计算匹配串的hash 值。然后在主串中移动计算所有hash值。相同的再比较每个字符值。不相同的后移一位

```java
    /**
     * 相较于上一种，不再做复杂的字符一一比较，
     * 而是先用子串和目标串的hash值(不同hash算法复杂度不一样,冲突概率也不一样)比较,
     * 只有这个相等才可能字符串相等，
     * 存在hash 冲突，再一一比较字符
     */
    public List<Integer> RabinKarp(String source, String target){
        List<Integer> resultList = new ArrayList<>();
        if(target== null || target.length() == 0){
            return resultList;
        }
        char[] tagetArray = target.toCharArray();
        int hash = 0;
        for(char single : tagetArray){
            hash+=single;
        }
        int targetLength = tagetArray.length - 1;
        char[] sourceArray = source.toCharArray();
        int length = sourceArray.length;
        int tempHash = 0;
        int start = 0;
        for(int index = 0; index < length; index++){
            tempHash += sourceArray[index];
            if(index - start == targetLength){
                if(tempHash == hash){
                    int tempIndex = 0;
                    while(tempIndex <= targetLength && sourceArray[start] == tagetArray[tempIndex]){
                        tempIndex++;
                        start++;
                    }
                    start = index - targetLength;
                    if(tempIndex - 1 == targetLength){
                        resultList.add(start);
                    }
                }
                tempHash -= sourceArray[start];
                start++;
            }
        }
        return resultList;
    }
```

####  3. KMP

***核心点***

* 从头往后匹配

* Partial Match Table

  例如 : ABAB

  | 子串 | 前缀子串 | 后缀子串 | 最大匹配串 |
  | :--: | :------: | :------: | :--------: |
  |  A   |          |          |            |
  |  AB  |    A     |    B     |            |
  | ABA  |  A ,AB   |   A,BA   |     A      |
  | ABAB | A,AB,ABA | A,BA,ABA |    ABA     |

  |           字符串           |      A       |  B   |  A   |  B   |
  | :------------------------: | :----------: | :--: | :--: | :--: |
  |            PMT             |      0       |  0   |  1   |  3   |
  | Next（相较于PMT 右移一位） | -1(特殊标记) |  0   |  0   |  1   |

  ```java
  abcabdabcabc
  abcabc      j = 5 不匹配
     abcabc   理论上应该移动j到2 实际移动到 0,对于匹配的前缀子串，如果前缀子串下一位不匹配，那我们需要再向前匹配 
      private int[] getNexts(char[] source) {
          int length = source.length;
          int[] next = new int[length];
          next[0] = -1;
          int k = -1;
          for (int i = 1; i < length; ++i) {
              while (k != -1 && source[k + 1] != source[i]) {
                  k = next[k];
              }           
              if (source[k + 1] == source[i]) {
                  ++k;
              }            
              next[i] = k;
          }
          return next;
       }
  ```

  

* 利用现有已匹配信息和匹配串信息既已匹配子串的前缀子串和后缀子串存在重合情况。
* 当某个字符不匹配时寻找已匹配字符串对应的下一个下标。

```java
    public int KPM(String source, String target){
        char[] targetArray = target.toCharArray();
        char[] sourceArray = source.toCharArray();
        int[] partialArray = getNexts(targetArray;
        int i = 0;
        int j = 0;
        for(; i< source.length(); i++){
            while(j > 0 && sourceArray[i] != targetArray[j]){   
                j = partialArray[j];
            }
            j = j == -1 ? 0 : j;
            //相等则j++
            if(sourceArray[i] == targetArray[j]){
                j++;
            }
            if(j == targetArray.length){
                return i - j + 1;
            }
        }
        return -1;
    }
```



#### 4. BM

* 从匹配串后面开始匹配

* 好后缀 + 坏字符

* 寻找坏字符在匹配串中的位置，来做偏移

* 好后缀用来寻找匹配的子串

  suffix 代表模式串中和后缀字串匹配的最右位置

  prefix 代表好后缀的后缀子串中 找最长的能跟模式串前缀子串匹配的后缀子串

  |        | c    | a     | b     | c    | a     | b     |
  | ------ | ---- | ----- | ----- | ---- | ----- | ----- |
  | suffix |      | -1    | -1    | 0    | 1     | 2     |
  | prefix |      | false | false | true | false | false |

  

  ```java
   private int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
          // 好后缀长度
          int k = m - 1 - j;
          if (suffix[k] != -1) {
              return j - suffix[k] +1;
          }
          for (int r = j+2; r <= m-1; ++r) {
              if (prefix[m-r] == true) {
                  return r;
              }
          }
          return m;
      }
      //cabcab
      private void generateGS(char[] b, int m, int[] suffix, boolean[] prefix) {
          // 初始化
          for (int i = 0; i < m; ++i) {
              suffix[i] = -1;
              prefix[i] = false;
          }
          // b[0, i]
          for (int i = 0; i < m - 1; ++i) {
              int j = i;
              // 公共后缀子串长度
              int k = 0;
              // 与b[0, m-1]求公共后缀子串
              while (j >= 0 && b[j] == b[m - 1 - k]) {
                  --j;
                  ++k;
                  //j+1表示公共后缀子串在b[0, i]中的起始下标
                  suffix[k] = j + 1;
              }
  
              if (j == -1) {
                  //如果公共后缀子串也是模式串的前缀子串
                  prefix[k] = true;
              }
          }
      }
  
      public void generateBC(String source, Map<Character, Integer> BadCharIndexMap) {
          char[] sourceArray = source.toCharArray();
          for (int i = 0; i < source.length(); i++) {
              BadCharIndexMap.put(sourceArray[i], i);
          }
      }
  
      public int bm(String source, String target) {
          char[] a = source.toCharArray();
          int n = source.length();
          char[] b = target.toCharArray();
          int m = target.length();
          // 记录模式串中每个字符最后出现的位置
          Map<Character, Integer> BadCharIndexMap = new HashMap<>();
          // 构建坏字符哈希表
          generateBC(source, BadCharIndexMap);
          int[] suffix = new int[m];
          boolean[] prefix = new boolean[m];
          generateGS(b, m, suffix, prefix);
          // j表示主串与模式串匹配的第一个字符
          int i = 0;
          while (i <= n - m) {
              int j;
              // 模式串从后往前匹配
              for (j = m - 1; j >= 0; --j) {
                  if (a[i + j] != b[j]) {
                      break; // 坏字符对应模式串中的下标是j
                  }
              }
              if (j < 0) {
                  // 匹配成功，返回主串与模式串第一个匹配的字符的位置
                  return i;
              }
              int x = j - BadCharIndexMap.getOrDefault((int) a[i + j], -1);
              int y = 0;
              // 如果有好后缀的话
              if (j < m - 1) {
                  y = moveByGS(j, m, suffix, prefix);
              }
              i = i + Math.max(x, y);
          }
          return -1;
      }
  ```


#### 5. Sunday

* 比较主串中坏字符的下个字符。确定其在匹配串中的位置来决定位移量

* 如果匹配串中有

  ```java
      /**
       * abcebc
       * abd
       * 碰到不匹配字符 找主串中匹配字符下个字符 即 i + target.length() - j
       * i = 2,j=2,length=3 => i = 2 - 2 + 3,
       * abcdbc   abcdbc
       * abd       abd
       * 如果匹配串中有这个字符 i移动到整个匹配串最后位前moveIndex位
       * i = 3,moveIndex=3 => i= i - moveIndex = 0, j=0
       
       * abcebc
       * abd
       * 没有这个字符 i 移动到整个匹配串的下一位
       * j=0;i=3
       *
       */
       public int Sunday(String source, String target){
          Map<Character, Integer> charIndexMap = new HashMap<>();
          char[] charArray =  target.toCharArray();
          for(int i = 0; i< target.length(); i++){
              char tempChar = charArray[i];
              charIndexMap.put(tempChar, i+1);
          }
          char[] sourceArray = source.toCharArray();
          int j = 0;
          for(int i = 0; i< sourceArray.length; i++){
              if(sourceArray[i] != charArray[j]){
                  i = i -j + charArray.length;
                  char next = sourceArray[ i];
                  Integer moveIndex = charIndexMap.get(next);
                  if(moveIndex != null){
                      i -= moveIndex;
                  }
                  j = 0;
              } else {
                  j++;
              }
              if(j == charArray.length){
                  return i - j + 1;
              }
          }
          return -1;
      }
  ```

  



