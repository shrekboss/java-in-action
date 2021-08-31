explain select * from person where NAME >'name84059' and create_time>'2020-01-24 00:00:00';
explain select * from person where NAME >'name84059' and create_time>'2020-01-24 00:00:00';

# MySQL 维护了表的统计信息
show table status like 'person';

# 强制使用索引
EXPLAIN SELECT * FROM person FORCE INDEX(name_score) WHERE NAME >'name84059' AND create_time>'2020-01-24 05:00:00';

# 解优化器的选择过程
SET optimizer_trace="enabled=on";
explain SELECT * FROM person WHERE `name` >'name84059' AND `create_time` > '2020-07-31 01:00:00';
SELECT * FROM information_schema.OPTIMIZER_TRACE;
SET optimizer_trace="enabled=off";

SET optimizer_trace="enabled=on";
explain select * from person where NAME='name1';
SELECT * FROM information_schema.OPTIMIZER_TRACE; # 回表 read_only = false
explain select NAME,SCORE from person where NAME='name1';
SELECT * FROM information_schema.OPTIMIZER_TRACE; # 覆盖索引 read_only = true
SET optimizer_trace="enabled=off";

explain SELECT * FROM person order by `name` desc;
explain SELECT * FROM person order by `score`; # Extra: Using filesort 内存或者磁盘排序
