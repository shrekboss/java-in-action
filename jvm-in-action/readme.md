1. 自己写一个简单的 Hello.java，里面需要涉及基本类型、四则运行、if 和 for， 然后自己分析
   一下对应的字节码，有问题群里讨论。
   - org.crayzer.jvm.Hello
   
   ```
   public int maxArea(int[]);
    Code:
       0: iconst_0
       1: istore_2
       2: iconst_0
       3: istore_3
       4: aload_1
       5: arraylength
       6: iconst_1
       7: isub
       8: istore        4
      10: iload_3
      11: iload         4
      13: if_icmpge     67
      16: aload_1
      17: iload_3
      18: iaload
      19: aload_1
      20: iload         4
      22: iaload
      23: if_icmpge     35
      26: aload_1
      27: iload_3
      28: iinc          3, 1
      31: iaload
      32: goto          42
      35: aload_1
      36: iload         4
      38: iinc          4, -1
      41: iaload
      42: istore        5
      44: iload         4
      46: iload_3
      47: isub
      48: iconst_1
      49: iadd
      50: iload         5
      52: imul
      53: istore        6
      55: iload         6
      57: iload_2
      58: if_icmple     64
      61: iload         6
      63: istore_2
      64: goto          10
      67: iload_2
      68: ireturn
   ```

2. 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此 文件内容是一个
   Hello.class 文件所有字节（x=255-x）处理后的文件。
   - org.crayzer.jvm.HelloClassLoader

3. 画一张图，展示 Xmx、Xms、Xmn、Metaspache、DirectMemory、Xss 这些内存参数的关系。
   ![WeChatad70c6cf6eacbbc69d8c2759e6cec0a3.png](http://ww1.sinaimg.cn/large/7a13deb3ly1grs01nglavj21qy196h99.jpg)
   堆设置
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