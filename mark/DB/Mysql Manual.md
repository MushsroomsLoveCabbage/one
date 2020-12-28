#### Mysql Lock

- gap lock
- recrod lock
- Next-key lock (gap lock + record lock)

| ID   | NUMBER |
| ---- | ------ |
| 1    | 1      |
| 5    | 5      |
| 10   | 10     |

```sql
#锁住的区间是(-infinite, 1],(1,5)
select * from test where number >= 1 and number < 5 lock in share mode;
#锁住的区间是(-infinite, 1],(1,5),[5, +infinite)
select * from test where number >= 1 and number <= 5 lock in share mode;
#为什么锁覆盖左右infinite区域，如果不覆盖的话， 
select * from test where number >= 1 and number <= 10 lock in share mode; 
(-infinite, 1],(1,5),[5, 10],                             
select * from test where number >= 1 and number <= 11 lock in share mode;                 (-infinite, 1],(1,5),[5, 10], 这个11的 无法覆盖到                                           (核心是锁依赖于已有索引数据，当对于不存在的数据做操作时候需要左右边界来辅助限定范围，因此需要最小最大值来设限定核实的范围)
#对于不存在的边界，锁无法落到index上，因此锁住的区间是(5,10)
select * from test where number >= 7 and number <= 8 lock in share mode; 
#如果不锁上下边界的话
select * from test where number >= 7 and number <= 8 lock in share mode;                                                         
```

