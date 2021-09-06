package org.crayzer.err.coding.io.badencodingissue;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class FileBadEncodingIssueApplication {

    public static void main(String[] args) throws IOException {
        init();
//        wrong();
        right1();
//        right2();
    }

    private static void init() throws IOException {
        Files.deleteIfExists(Paths.get("hello.txt"));
        Files.write(Paths.get("hello.txt"), "你好hi".getBytes(Charset.forName("GBK")));
        log.info("bytes:{}", Hex.encodeHexString(Files.readAllBytes(Paths.get("hello.txt"))).toUpperCase());
    }

    private static void wrong() throws IOException {
        log.info("default charset: {}", Charset.defaultCharset());

        char[] chars = new char[10];
        String content = "";
        // FileReader 是以当前机器的默认字符集来读取文件的, ，如果希望指定字符集的话，需要直接使用
        // InputStreamReader 和 FileInputStream。
        try (FileReader fileReader = new FileReader("hello.txt")) {
            int count;
            while ((count = fileReader.read(chars)) != -1) {
                content += new String(chars, 0, count);
            }
        }
        log.info("result:{}", content);

        Files.write(Paths.get("hello2.txt"), "你好hi".getBytes(Charsets.UTF_8));
        log.info("bytes:{}", Hex.encodeHexString(Files.readAllBytes(Paths.get("hello2.txt"))).toUpperCase());
    }

    private static void right1() throws IOException {

        char[] chars = new char[10];
        String content = "";
        try (FileInputStream fileInputStream = new FileInputStream("hello.txt");
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("GBK"))) {
            int count;
            while ((count = inputStreamReader.read(chars)) != -1) {
                content += new String(chars, 0, count);
            }
        }

        log.info("result: {}", content);
    }

    private static void right2() throws IOException {
        // 但这种方式有个问题是，读取超出内存大小的大文件时会出现 OOM
        // readAllLines 读取文件所有内容后，放到一个 List 中返回，如果内存无法容纳这个 List，就会 OOM
        // 解决方案就是 File 类的 lines 方法，lines 方法返回的是 Stream 参考 【filestreamoperationneedclose】
        log.info("result: {}", Files.readAllLines(Paths.get("hello.txt"), Charset.forName("GBK")).stream().findFirst().orElse(""));
    }

}


