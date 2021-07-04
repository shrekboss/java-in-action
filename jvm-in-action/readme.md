## . 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此 文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。

    - org.crayzer.jvm.HelloClassLoader

## . 画一张图，展示 Xmx、Xms、Xmn、Metaspache、DirectMemory、Xss 这些内存参数的关系。

![WeChatad70c6cf6eacbbc69d8c2759e6cec0a3.png](http://ww1.sinaimg.cn/large/7a13deb3ly1grs01nglavj21qy196h99.jpg)

    - 堆和非堆
        - 堆就是Java代码可及的内存，是留给开发人员使用的；
        - 非堆就是JVM留给自己用的， 方法区、JVM内部处理或优化所需的内存(如JIT编译后的代码 缓存)、每个类结构(如运行时常数池、字段和方法数据)以及方法和构造方法的代码都在非堆内 存中
    - 直接内存（Direct Memory）并不是虚拟机运行时数据区的一部分，也不是Java虚拟机规范中定 义的内存区域,也可能导致OutOfMemoryError 异常出现

    - 堆设置
       ```
       -Xms:初始堆大小
       -Xmx:最大堆大小
       -Xmn:新生代大小
       -XX:NewRatio:设置新生代和老年代的比值。如：为3，表示年轻代与老年代比值为1：3
       -XX:SurvivorRatio:新生代中Eden区与两个Survivor区的比值。注意Survivor区有两个。如：为3，表示Eden：Survivor=3：2，一个Survivor区占整个新生代的1/5  
       -XX:MaxTenuringThreshold:设置转入老年代的存活次数。如果是0，则直接跳过新生代进入老年代
       -XX:PermSize、-XX:MaxPermSize:分别设置永久代最小大小与最大大小（Java8以前）
       -XX:MetaspaceSize、-XX:MaxMetaspaceSize:分别设置元空间最小大小与最大大小（Java8以后）
       ```

## 不同 GC 和堆内存的总结
- gc.concMarkSweep.log
- gc.g1.log
- gc.parallel.log
- gc.serial.log

### SerialGC

#### Serial是用于新生代单线程的收集器，采用复制算法进行垃圾收集
1. serial 进行垃圾收集的时候，不仅只用一条线程执行垃圾收集工作，它在收集数据的同时，所有
的用户线程必须暂停（Stop The World）。
2. 当用户线程都执行到安全点的时候，所有的线程暂停执行，Serial收集器以单线程，采用复制算
法进行垃圾收集工作，收集完之后，用户线程开始执行
3. 适用场景：Client 模式；单核服务器

#### Serial Old 收集器是Serial的老年代版本，同样是一个单线程收集器，采用标记-整理算法
适用场景：
  - Client模式（桌面应用）；
  - 单核服务器；
  - 与Parallel Scavenge收集器搭配;
  - 作为CMS收集器的后备预案

### ParallelGC

#### Parallel Scavenge 用于新生代的多线程收集器

1. 与ParNew 的不同之处是ParNew 的目标是尽可能的缩短垃圾收集时用户线程的停顿时间， 
   Parallel Scavenge 的目标是达到一个可控制的吞吐量。

- 吞吐量：
    - 吞吐量就是CPU执行用户线程的时间与CPU执行总时间的比值
    - 吞吐量 = 运行用户代码的时间/(运行用户代码的时间+垃圾回收的时间 )
    - 比如虚拟机一共运行了100分钟，其中垃圾回收使用了1分钟，那么吞吐量就是99%。

2. 在新生代，当用户线程都执行到安全点时，所有线程暂停执行，Parallel Scavenge 收集器以多
  线程，采用复制算法进行收集工作，收集完之后，用户线程继续开始工作。
3. 在老年代，当用户线程都执行到安全点的时候，所有线程暂停工作，Parallel Old收集器以多线
  程，采用标记整理算法进行垃圾收集工作。
4. 适用场景：
    - 注重吞吐量，高效利用CPU,需要高效运算且不需要太多交互
    - 可以使用 -XX:+UseParallelGC 来选择Parallel Scavenge 作为新生代收集器
    - jdk7、jdk8 默认使用 Parallel Scavenge 作为新生代收集器。
    - 可以通过 -XX:MaxGCPauseMillis 来设置收集器尽可能在多长时间内完成内存回收
    - 可以通过 -XX:GCTimeRatio 来精确控制吞吐量。

#### Parallel Old 用于老年代的多线程收集器

是 Parallel Scavenge 的老年代版本，是一个多线程收集器，采用标记-整理算法。

1. 可以与 Parallel Scavenge 收集器搭配，可以充分利用多核 CPU 的计算能力。
2. 使用场景：
    - 与Parallel Scavenge 收集器搭配使用；注重吞吐量。
    - jdk7、jdk8 默认使用该收集器作为老年代收集器。
    - 使用 -XX:+UseParallelOldGC 来指定使用 Parallel Old 收集器。

### ConcMarkSweepGC

#### ParNew 收集器
是一个Serial的多线程版本，和 Serial无区别
1. ParNew在单核CPU环境下并不会比Serial收集器达到更好的效果，它默认开始的收集线程和 
CPU的数量是一致的，
2. 可以通过 -XX:ParallelGCThreads来设置垃圾收集的线程数
3. 使用场景： 
    - 多核服务器；
    - 与 CMS 收集器搭配使用。
    - 当使用 -XX:+UserConcMarkSweepGC 来选择 CMS 作为老年代收集器时，新生代收集器默认
   就是 ParNew 也可以用 -XX:+UseParNewGC 来指定使用 ParNew 作为新生代收集器。
   
#### CMS 收集器
是一种以最短回收停顿时间为目标的收集器，以“最短时间用户停顿时间”著称。使用的是标记清
除算法
1. 整个垃圾回收过程分为4个步骤：
    a. 初始标记： 标记一下GC Roots 能直接关联到的对象，速度较快。
    b. 并发标记： 进行GC Roots Tracing, 标记出全部的垃圾对象，耗时较长。
    c. 并发预清理
    d. 最终标记： 修正并发标记阶段因用户程序继续运行而导致变化的对象的标记记录，耗时较短。
    e. 并发清除： 用标记-清除算法清除垃圾对象，耗时较长
    f. 并发重置
2. 整个过程耗时最长的并发标记和并发清除都是和用户线程一起工作，所以总体上来说，CMS收
   集器收集可以看做是和用户线程并发执行的。
3. 使用场景
    a. 重视服务器的响应速度，要求系统提顿时间最短
    b. 可以使用 -XX:+UserConMarkSweepGC 来选择 CMS 作为老年代收集器		
4. 缺点： 
    a. 对CPU资源敏感：
        默认分配的垃圾回收线程数为（CPU数+3）/4,随着CPU数量下降，占用CPU资源越多，吞
        吐量越小
    b. 无法浮动处理垃圾：
        - 在并发清理阶段，由于用户线程还在运行，还会不断的产生新的垃圾，CMS收集器无法在
        当次收集中清除这部分垃圾。
        - 同时由于在垃圾收集阶段（并发标记）阶段用户线程也在并发执行，CMS收集器不能像其
        他收集器那样等老年代被填满时在进行收集，需要预留一部分空间提供用户线程运行使用。
        - 当CMS运行的时候，预留的空间无法满足用户线程的需要，就会出现 
        "Concurrent Mode Failure "的错误，这时将会启动后备预案，临时用 Serial Old 来重新进
        行老年代的垃圾收集。
    c. 因为CMS是基于标记清除算法的，所以垃圾回收后会产生空间碎片：
        可以通过-XX:UserCMSCompactAtFullCollection 开启碎片整理（默认开启）
    d. 在CMS进行Full GC之前，会进行内存碎片的整理。
        还可以用 -XX:CMSFullGCsBeforeCompaction 设置执行多少次不压缩（不进行碎片整理）
        的 Full GC 之后，跟着来一次带压缩（碎片整理）的 Full GC。

### G1GC

#### G1 收集器
- 进行垃圾收集的范围是整个堆内存,是jdk1.7才正式引用的商用收集器。现在jdk9默认的收集器；
- 它采用的是“化整为零”的思路。把整个堆内存划分为多个大小相等的独立区域（Region），在 G1 
  收集器中还保留着新生代和老年代的概念，它们分别都是一部分 Region


1. 每一个方块就是一个区域，每个区域可能是Eden,Survivor,老年代。每种区域的数量也不一定。
2. JVM启动的时候，会自动设置每个区域的大小（1M~32M,必须是2的次幂），
3. 最多可以设置2048个区域（即支持的最大的堆内存为32M*2048 = 64G），
4. 假如设置 -Xmx8g ,-Xms8g,则每个区域大小为8g/2048 = 4M;
5. 为了在GC Roots Tracing 的时候避免扫描全堆，在每个Region中，都有一个 Remembered Set 
   来实时记录该区域内的引用类型数据与其他区域数据的引用关系，在标记时直接参考这些引用
   关系就可以知道这些对象是否应该被清除，而不用扫描全堆的数据。
5. G1 收集器可以 “ 建立可预测的停顿时间模型 ”，它维护了一个列表用于记录每个 Region 回收的价值大小（回收后获得的空间大小以及回收所需时间的经验值），这样可以保证 G1 收集器在有限的时间内可以获得最大的回收效率。

6. G1收集器收集的过程有：
    - 阶段 1: Initial Mark（初始标记） 
    - 阶段 2: Root Region Scan（Root区扫描） 
    - 阶段 3: Concurrent Mark（并发标记） 
    - 阶段 4: Remark（再次标记） 
    - 阶段 5: Cleanup（清理）
	和CMS收集器的前几步过程很像

    初始标记：
        标记出 GC Roots 直接关联的对象，这个阶段速度较快，需要停止用户线程，单线程执行。

    并发标记：
        从 GC Root 开始对堆中的对象进行可达新分析，找出存活对象，这个阶段耗时较长，但可
   以和用户线程并发执行。

    最终标记：
        修正在并发标记阶段引用户程序执行而产生变动的标记记录。

    筛选回收：
        筛选回收阶段会对各个 Region 的回收价值和成本进行排序，根据用户所期望的 GC 停顿时
   间来指定回收计划（用最少的时间来回收包含垃圾最多的区域，这就是 Garbage First 的由来
   ——第一时间清理垃圾最多的区块），这里为了提高回收效率，并没有采用和用户线程并发执行
   的方式，而是停顿用户线程。

7. 使用场景：
    - 要求尽可能可控 GC 停顿时间；内存占用较大的应用。
    - 可以使用 -XX:+UseG1GC 使用 G1 收集器，jdk9 默认使用 G1 收集器。
