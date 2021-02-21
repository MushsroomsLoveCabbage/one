* 抽象所带来的复杂度，必须在另外的地方大大的减小系统的复杂度。以退为进，抽象的目的是简化。

##### CQRS (Command Query Responsibility Segregation)

* CRUD 中 R与CUD分离，R 直接Query 语句直接操作数据库，CUD 则需要走Command bus 

