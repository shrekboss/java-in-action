### ThreadLocal使用问题内存泄露
- 什么是内存泄露
    - 某个对象不再有用，但是占用的内存却不能被回收。

- Value的泄露
    - 在ThreadLocalMap中的每个Entry都是一个对**key的弱引用**，同时，每个Entry都包含了一个
      对**value的强引用**。正常情况 ，当线程终止，保存在ThreadLocal里的value会被垃圾回收，
      因为没有任何强引用了。 但是，如果线程不终止（比如线程池需要保持很久），那么key对
      应的value就不能被回收。`Thread->ThreadLocalMap->Entry(key为Null)->Value` 因为value和
      Thread之间还存在这个强引用链路，所以导致value无法回收，就可能出现OOM；JDK已经考
      虑到这个问题，所以在set,remove,rehash方法中会扫描key为null，会把value也设置为null，
      这样value对象就可以被回收了。 但是如果一个ThreadLocal不被使用，那么实际上
      set,remove,rehash方法也不会被调用，如果同时线程又不停止，那么调用链就一直存在，那
      么就导致了value的内存泄露。

- 如何避免内存泄露呢？
    - 调用remove方法，就会删除对应的Entry对象，可以避免内存泄露，所以使用完ThreadLocal
      之后，应该调用remove方法。