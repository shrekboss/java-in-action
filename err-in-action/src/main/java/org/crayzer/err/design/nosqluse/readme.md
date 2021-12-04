## 数据存储：NoSQL与RDBMS如何取长补短、相辅相成？
### 取长补短之 Redis vs MySQL：redisvsmysql
**实验一：**
比较一下从 MySQL 和 Redis 随机读取单条数据的性能。“公平”起见，像 Redis 那样，使用 MySQL 时也根据
 Key 来查 Value，也就是根据 name 字段来查 data 字段，并且给 name 字段做了索引：
wrk -t 10 -c 50 -d 10s http://localhost:45678/redisvsmysql/redis --latency
wrk -t 10 -c 50 -d 10s http://localhost:45678/redisvsmysql/mysql --latency

**实验二**
Redis 薄弱的地方是，不擅长做 Key 的搜索。对 MySQL，可以使用 LIKE 操作前匹配走 B+ 树索引实现快速
搜索；但对 Redis，使用 Keys 命令对 Key 的搜索，其实相当于在 MySQL 里做全表扫描：
**Redis 慢的原因有两个：**
- Redis 的 Keys 命令是 O(n) 时间复杂度。如果数据库中 Key 的数量很多，就会非常慢。
- Redis 是单线程的，对于慢的命令如果有并发，串行执行就会非常耗时。

结论：在 **QPS** 方面，MySQL 的 QPS 达到了 Redis 的 157 倍；在延迟方面，MySQL 的**延迟**只有 Redis 的
十分之一。

一般而言，使用 Redis 都是针对某一个 Key 来使用，而不能在业务代码中使用 Keys 命令从 Redis 中“搜索数
据”，因为这不是 Redis 的擅长。对于 Key 的搜索，可以先通过关系型数据库进行，然后再从 Redis 存取数据
（如果实在需要搜索 Key 可以使用 **SCAN** 命令）。**在生产环境中，一般也会配置 Redis 禁用类似 
Keys 这种比较危险的命令**。

### 取长补短之 InfluxDB vs MySQL：influxdbvsmysql
肯定不能把 InfluxDB 当作普通数据库，原因是：
- InfluxDB 不支持数据更新操作，毕竟时间数据只能随着时间产生新数据，肯定无法对过去的数据做修改；
- 从数据结构上说，时间序列数据数据没有单一的主键标识，必须包含时间戳，数据只能和时间戳进行关联，
不适合普通业务数据。

**需要注意**，即便只是使用 InfluxDB 保存和时间相关的指标数据，也要注意不能滥用 tag。
influxDB 提供的 tag 功能，可以为每一个指标设置多个标签，并且 tag 有索引，可以对 tag 进行条件搜索或
分组。但是，tag 只能保存有限的、可枚举的标签，不能保存 URL 等信息，否则可能会出现
**high series cardinality 问题**，导致占用大量内存，甚至是 **OOM**。查看 series 和内存占用的关系。对于 
InfluxDB，无法把 URL 这种原始数据保存到数据库中，只能把数据进行归类，形成有限的 tag 进行保存。
- high series cardinality 问题：https://docs.influxdata.com/influxdb/v1.7/concepts/schema_and_data_layout/#don-t-have-too-many-serieshigh%20series%20cardinality
- OOM：https://docs.influxdata.com/influxdb/v1.7/guides/hardware_sizing/

**总结一下**，对于 MySQL 而言，针对大量的数据使用全表扫描的方式来聚合统计指标数据，性能非常差，一般
只能作为临时方案来使用。此时，引入 InfluxDB 之类的时间序列数据库，就很有必要了。时间序列数据库可
以作为特定场景（比如监控、统计）的主存储，也可以和关系型数据库搭配使用，作为一个辅助数据源，保存
业务系统的指标数据。

### 取长补短之 Elasticsearch vs MySQL：esvsmysql
MySQL：ID 建了索引，但是这个索引只能起到加速过滤分类 ID 这一单一条件的作用，对于文本内容的全文搜索，B+ 
树索引无能为力。
ES 这种以索引为核心的数据库，也不是万能的，频繁更新就是一个大问题。

MySQL 可以做到仅更新某行数据的某个字段，但 **ES 里每次数据字段更新都相当于整个文档索引重建**。即便 
ES 提供了文档部分更新的功能，但本质上只是节省了提交文档的网络流量，以及减少了更新冲突，其内部实现
还是**文档删除后重新构建索引**。因此，如果要在 ES 中保存一个类似计数器的值，要实现不断更新，其执
行效率会非常低。

ES 是一个分布式的全文搜索数据库，所以与 MySQL 相比的优势在于文本搜索，而且因为其分布式的特性，
可以使用一个大 ES 集群处理大规模数据的内容搜索。但，由于 **ES 的索引是文档维度的，所以不适用于频繁
更新的 OLTP 业务**。

### 结合NoSQL和MySQL应对高并发的复合数据库架构：N/A
有两种类型的查询任务可以交给 MySQL 来做，性能会比较好，这也是 MySQL 擅长的地方：
- 按照主键 ID 的查询。直接查询聚簇索引，其性能会很高。但是单表数据量超过亿级后，性能也会衰退，而
且单个数据库无法承受超大的查询并发，因此可以把数据表进行 Sharding 操作，均匀拆分到多个数据库实例
中保存。把这套数据库集群称作 Sharding 集群。
- 按照各种条件进行范围查询，查出主键 ID。对二级索引进行查询得到主键，只需要查询一棵 B+ 树，效率同
样很高。但索引的值不宜过大，比如对 varchar(1000) 进行索引不太合适，而索引外键（一般是 int 或 bigint
 类型）性能就会比较好。因此，可以在 MySQL 中建立一张“索引表”，除了保存主键外，主要是保存各种关联
 表的外键，以及尽可能少的 varchar 类型的字段。这张索引表的大部分列都可以建上二级索引，用于进行简单
 搜索，搜索的结果是主键的列表，而不是完整的数据。由于索引表字段轻量并且数量不多（一般控制在 10 个
 以内），所以即便索引表没有进行 Sharding 拆分，问题也不会很大。
 
 写入两种 MySQL 数据表和发送 MQ 消息的这三步，用一个同步写服务完成了。在“异步处理”中提到，所有
 异步流程都需要补偿，这里的异步流程同样需要。只不过为了简洁，我在这里省略了补偿流程。
 
 然后，如图中绿色线所示，有一个异步写服务，监听 MQ 的消息，继续完成辅助数据的更新操作。这里选用
 了 ES 和 InfluxDB 这两种辅助数据库，因此整个异步写数据操作有三步：
 - MQ 消息不一定包含完整的数据，甚至可能只包含一个最新数据的主键 ID，需要根据 ID 从查询服务查询
 到完整的数据。
 - 写入 InfluxDB 的数据一般可以按时间间隔进行简单聚合，定时写入 InfluxDB。因此，这里会进行简单的
 客户端聚合，然后写入 InfluxDB。
 - ES 不适合在各索引之间做连接（Join）操作，适合保存扁平化的数据。比如，可以把订单下的用户、商户、
 商品列表等信息，作为内嵌对象嵌入整个订单 JSON，然后把整个扁平化的 JSON 直接存入 ES。
 
 对于数据写入操作，认为操作返回的时候同步数据一定是写入成功的，但是由于各种原因，异步数据写入无法
 确保立即成功，会有一定延迟，比如：
 - 异步消息丢失的情况，需要补偿处理；
 - 写入 ES 的索引操作本身就会比较慢；
 - 写入 InfluxDB 的数据需要客户端定时聚合。
 
 因此，对于查询服务，红色线所示，需要根据一定的上下文条件（比如查询一致性要求、时效性要求、搜索的
 条件、需要返回的数据字段、搜索时间区间等）来把请求路由到合适的数据库，并且做一些聚合处理：
 - 需要根据主键查询单条数据，可以从 MySQL Sharding 集群或 Redis 查询，如果对实时性要求不高也可以从 ES 查询。
 - 按照多个条件搜索订单的场景，可以从 MySQL 索引表查询出主键列表，然后再根据主键从 MySQL Sharding 
 集群或 Redis 获取数据详情。
 - 各种后台系统需要使用比较复杂的搜索条件，甚至全文搜索来查询订单数据，或是定时分析任务需要一次查
 询大量数据，这些场景对数据实时性要求都不高，可以到 ES 进行搜索。此外，MySQL 中的数据可以归档，
 可以在 ES 中保留更久的数据，而且查询历史数据一般并发不会很大，可以统一路由到 ES 查询。监控系统或
 后台报表系统需要呈现业务监控图表或表格，可以把请求路由到 InfluxDB 查询。

## 注意，运行esvsmysql之前需要先为ES安装IK分词器
步骤如下：
- 进入容器：docker exec -it es01 /bin/bash
- 安装IK分词插件：bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.5.2/elasticsearch-analysis-ik-7.5.2.zip
- 重启容器：docker restart es01
- 把es01替换为es02和es03，重新执行上面三步