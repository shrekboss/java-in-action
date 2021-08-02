package org.crayzer.spring.resource;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;

/**
 * 带有字符编码的 {@link FileSystemResource} 示例
 *
 * @author crayzer
 */
public class EncodedFileSystemResourceDemo {

    public static void main(String[] args) throws IOException {
        String currentJavaFilePath = System.getProperty("user.dir") +
                "/spring-in-action/thinking-in-spring/resource/src/main/java/" +
                "org/crayzer/spring/resource/EncodedFileSystemResourceDemo.java";
        /** FileSystemResource => WritableResource => Resource */
        FileSystemResource fileSystemResource = new FileSystemResource(currentJavaFilePath);
        EncodedResource encodedResource = new EncodedResource(fileSystemResource, "UTF-8");

        System.out.println(currentJavaFilePath);
        try (Reader reader = encodedResource.getReader()) {
            System.out.println(IOUtils.toString(reader));
        }
    }
}
