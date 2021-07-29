package org.crayzer.java8.lambda.example.file;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FilesExample {

    /**
     * 利用 Files.walk 返回一个 Path 的流，通过两行代码就能实现递归搜索 +grep 的操作。
     * 整个逻辑是：递归搜索文件夹，查找所有的.java 文件；
     * 然后读取文件每一行内容，用正则表达式匹配 public class 关键字；最后输出文件名和这行内容。
     *
     * @throws IOException
     */
    @Test
    public void filesExample() throws IOException {
        try (Stream<Path> pathStream = Files.walk(Paths.get("."))) {
            // 只查看普通文件
            pathStream.filter(Files::isRegularFile)
                    // 搜索Java源码文件
                    .filter(FileSystems.getDefault().getPathMatcher("glob:**/*.java")::matches)
                    //读取文件内容，转换为Stream<List>
                    .flatMap(ThrowingFunction.unchecked(path -> Files.readAllLines(path).stream()
                            // 使用正则过滤带有public class的行
                            .filter(line -> Pattern.compile("public class").matcher(line).find())
                            // 把这行文件内容转换为文件名+行
                            .map(line -> path.getFileName() + " >> " + line)))
                    .forEach(System.out::println);
        }
    }
}
