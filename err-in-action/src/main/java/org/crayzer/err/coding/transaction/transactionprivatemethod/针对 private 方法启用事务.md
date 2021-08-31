### 针对 private 方法启用事务：transactionprivatemethod
aspectj 的依赖和配置 aspectj-maven-plugin 插件，并且需要设置 Spring 开启 AspectJ 事务管理模式。具
体的实现方式，包括如下 4 步：
- 第一步，引入 spring-aspects 依赖： org.springframework spring-aspects
```dtd
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
</dependency>
```
- 第二步，加入 lombok 和 aspectj 插件：
```dtd
<!-- 使用 delombok 插件的目的是，把代码中的 Lombok 注解先编译为代码，这样 AspectJ 编译不会有问题，
同时需要设置中的 sourceDirectory 为 delombok 目录 -->
<plugin>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok-maven-plugin</artifactId>
    <version>1.18.0.0</version>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>delombok</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <addOutputDirectory>false</addOutputDirectory>
<!--        <sourceDirectory>src/main/java</sourceDirectory>-->
        <sourceDirectory>${project.build.directory}/generated-sources/delombok</sourceDirectory>
    </configuration>
</plugin>
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>1.10</version>
    <configuration>
        <complianceLevel>1.8</complianceLevel>
        <source>1.8</source>
        <aspectLibraries>
            <aspectLibrary>
                <groupId>org.springframework</groupId>
                <artifactId>spring-aspects</artifactId>
            </aspectLibrary>
        </aspectLibraries>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>compile</goal>
                <goal>test-compile</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
- 第三步，设置 @EnableTransactionManagement 注解，开启事务管理走 AspectJ 模式：
```java
@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class CommonMistakesApplication {}
```