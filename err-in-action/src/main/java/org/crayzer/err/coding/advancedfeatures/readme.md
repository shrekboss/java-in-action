## 当反射、注解和泛型遇到OOP时，会有哪些坑？

### 反射调用方法不是以传参决定重载：reflectionissue
> 使用反射时的误区是：认为反射调用方法还是根据入参确定方法重载

### 泛型经过类型擦除多出桥接方法的坑：genericandinheritance
> 泛型因为类型擦除会导致泛型方法 T 占位符被替换为 Object，子类如果使用具体类型覆盖父类实现，编译器
会生成桥接方法。这样既满足子类方法重写父类方法的定义，又满足子类实现的方法有具体的类型。使用反射
来获取方法清单时，需要特别注意这一点。

#### 使用反射查询类方法清单时，要注意两点：
- getMethods 和 getDeclaredMethods 是有区别的，前者可以查询到父类方法，后者只能查询到当前类。
    - `getMethods` 能获得当前类和父类的所有public方法
    - `getDeclaredMethods` 仅获得当前类所有的public、protected、package和private方法
    - **反射进行方法调用要注意过滤桥接方法。**
- 反射获取类成员，需要注意 getXXX 和 getDeclaredXXX 方法的区别，其中 XXX 包括 Methods、Fields、
Constructors、Annotations。这两类方法，针对不同的成员类型 XXX 和对象，在实现上都有一些细节差异，
详情请查看官方文档。**getDeclaredMethods 方法无法获得父类定义的方法，而 getMethods 方法可以，只是
差异之一，不能适用于所有的 XXX。**

### 注解可以继承吗？：annotationinheritance
> 自定义注解可以通过标记元注解 @**Inherited 实现注解的继承，不过这只适用于类**。如果要继承定义在接口或方法上的注解，可以使用 Spring 的工具类 AnnotatedElementUtils，并注意各种 getXXX 方法和 
findXXX 方法的区别

|  | 编写自定义注解时未写@Inherited的运行结果 | 编写自定义注解时写了@Inherited的运行结果
------- | ---------------- | ---------:
子类的类上能否继承到父类的类上的注解？  | 否 | 能
子类方法，实现了父类上的抽象方法，这个方法能否继承到注解？  | 否 | 否
子类方法，继承了父类上的方法，这个方法能否继承到注解？  | 能 | 能
子类方法，覆盖了父类上的方法，这个方法能否继承到注解？  | 否 | 否

### 内部类的例子：innerclass
泛型类型擦除后会生成一个 bridge 方法，这个方法同时又是 synthetic 方法。除了泛型类型擦除，还有什么情况编译器会生成 synthetic 方法吗？

Synthetic 方法是编译器自动生成的方法（在源码中不出现）。除了文中提到的泛型类型擦除外，Synthetic 方法还可能出现的一个比较常见的场景，是内部类和顶层类需要相互访问对方的 private 字段或方法的时候。