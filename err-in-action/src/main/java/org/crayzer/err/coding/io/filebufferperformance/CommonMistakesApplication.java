package org.crayzer.err.coding.io.filebufferperformance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.*;

@Slf4j
public class CommonMistakesApplication {

    public static void main(String[] args) throws IOException {

        StopWatch stopWatch = new StopWatch();
        init();
        stopWatch.start("perByteOperation");
        perByteOperation();
        stopWatch.stop();
        stopWatch.start("bufferOperationWith100Buffer");
        bufferOperationWith100Buffer();
        stopWatch.stop();
        stopWatch.start("bufferedStreamByteOperation");
        bufferedStreamByteOperation();
        stopWatch.stop();
        stopWatch.start("bufferedStreamBufferOperation");
        bufferedStreamBufferOperation();
        stopWatch.stop();
        stopWatch.start("largerBufferOperation");
        largerBufferOperation();
        stopWatch.stop();
        stopWatch.start("fileChannelOperation");
        fileChannelOperation();
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

    /**
     * 创建一个文件随机写入 100 万行数据，文件大小在 35MB 左右
     */
    private static void init() throws IOException {

        Files.write(Paths.get("src.txt"),
                IntStream.rangeClosed(1, 1_000_000).mapToObj(i -> UUID.randomUUID().toString()).collect(Collectors.toList())
                , UTF_8, CREATE, TRUNCATE_EXISTING);
    }

    private static void perByteOperation() throws IOException {
        Files.deleteIfExists(Paths.get("dest.txt"));

        try (FileInputStream fileInputStream = new FileInputStream("src.txt");
             FileOutputStream fileOutputStream = new FileOutputStream("dest.txt")) {
            int i;
            while ((i = fileInputStream.read()) != -1) {
                fileOutputStream.write(i);
            }
        }
    }

    private static void bufferOperationWith100Buffer() throws IOException {
        Files.deleteIfExists(Paths.get("dest.txt"));

        try (FileInputStream fileInputStream = new FileInputStream("src.txt");
             FileOutputStream fileOutputStream = new FileOutputStream("dest.txt")) {
            byte[] buffer = new byte[100];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        }
    }

    //直接使用FileInputStream和FileOutputStream，再使用一个8KB的缓冲
    private static void largerBufferOperation() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("src.txt");
             FileOutputStream fileOutputStream = new FileOutputStream("dest.txt")) {
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        }
    }

    //使用BufferedInputStream和BufferedOutputStream
    private static void bufferedStreamByteOperation() throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("src.txt"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("dest.txt"))) {
            int i;
            while ((i = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(i);
            }
        }
    }

    //额外使用一个8KB缓冲，再使用BufferedInputStream和BufferedOutputStream
    private static void bufferedStreamBufferOperation() throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("src.txt"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("dest.txt"))) {
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, len);
            }
        }
    }

    private static void fileChannelOperation() throws IOException {
        Files.deleteIfExists(Paths.get("dest.txt"));

        FileChannel in = FileChannel.open(Paths.get("src.txt"), StandardOpenOption.READ);
        FileChannel out = FileChannel.open(Paths.get("dest.txt"), CREATE, WRITE);
        in.transferTo(0, in.size(), out);
    }
}

