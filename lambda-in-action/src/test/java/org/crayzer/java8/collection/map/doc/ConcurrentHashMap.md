## [神奇啊！ConcurrentHashMap 竟然还能挖出这些东西！](https://mp.weixin.qq.com/s/88vdouvU--fUa4tfrQlxFg)

### putVal
> put 主流程
> 1. 首先会判断数组是否已经初始化，如若未初始化，会先去初始化数组(**initTable()**);
> 2. 如果当前要插入的节点为null，尝试使用 cas 插入数据；
> 3. 如果不为null，则判断节点hash值是否为-1；-1表示数组正在初始化，会先去协助扩容，再回来继续插
> 入数据；
> 4. 最后会执行上锁，并插入数据，最后判断是否需要返回旧值；如果不是覆盖旧值，需要更新map中的
> 节点数，**addCount()** 方法。

#### 不允许插入空值或空键
-  允许value空值会导致get方法返回null时有两种情况：
    - 找不到对应的key 
    - 找到了但是value为null；
- 当get方法返回null时无法判断是哪种情况，在并发环境下containsKey方法已不再可靠，需要返回null来
  表示查询不到数据。允许key空值需要额外的逻辑处理，占用了数组空间，且并没有多大的实用价值。
- **HashMap支持键和值为null，但基于以上原因，ConcurrentHashMap是不支持空键值。**

**注意:** 到源码中有两个关键方法：初始化数组的initTable()，修改map中节点总数的addCount。这两个
方法是如何实现线程安全的呢?

#### 初始化数组：initTable()
> initTable 主流程
> 1. 首先会判断数组是否为null，如果否说明另一个线程初始化结束了，直接返回该数组；
> 2. 判断是否正在初始化，如果是会让出cpu执行时间，当前线程自旋等待；
> 3. 如果数组为null，且没有另外的线程正在初始化，那么会尝试获取自旋锁，获取成功则进行初始化
> 获取失败则表示发生了并发冲突，继续循环等待。

**private transient volatile int sizeCtl;
默认为0，-1表示正在初始化，<-1表示有多少个线程正在帮助扩容，>0表示阈值**
`Table initialization and resizing control.  When negative, the
table is being initialized or resized: -1 for initialization,
else -(1 + the number of active resizing threads).  Otherwise,
when table is null, holds the initial table size to use upon
creation, or 0 for default. After initialization, holds the
next element count value upon which to resize the table.
表初始化和大小调整控制。如果为负，则表将被初始化或调整大小：-1用于初始化，
否则-（1 +活动的调整大小线程数）。否则，当table为null时，保留创建时要使用
的初始表大小，或者默认为0。初始化后，保留下一个要调整表大小的元素计数值。` 

#### 修改节点总数：addCount()
> 把ConcurrentHashMap的节点总数进行+1，ConcurrentHashMap并不是一个单独的size变量，它把size
> 进行拆分，好处就是每个线程可以单独修改对应的变量
>
> addCount 主流程
> 1. 当前线程首先会使用CAS修改basecount的值，修改失败则进入数组分配CountCell修改;
> 2. 判断CounterCell数组是否为空，如果CounterCell数组为空，则初始化数组
> 3. 如果CounterCell数组不为空，使用线程随机数找到下标
> 4. 如果该下标的的counterCell对象还没初始化，则先创建一个CounterCell。创建了CounterCell之后还
> 需要考虑是否需要数组扩容
> 5. 如果counterCell对象不为null，使用CAS尝试修改，失败则重新来一次
> 6. 如果上面两种情况都不满足，则会回去再尝试CAS修改一下basecount

- 在ConcurrentHashMap中每个size被用一个CounterCell对象包装：
    ```java
    static final class CounterCell {
        volatile long value;
        CounterCell(long x) { value = x; }
    }
    ```

##### 每个线程如何分配到对应的自己的CounterCell呢？
ConcurrentHashMap中采用了类似HashMap的思路，获取线程随机数，再对这个随机数进行取模得到对应
的CounterCell。获取到对应的CounterCell之后，当前线程会尝试使用CAS进行修改，如果修改失败，则
重新获取线程随机数，换一个CounterCell再来一次，直到修改成功。

#### 扩容方案：transfer()











































