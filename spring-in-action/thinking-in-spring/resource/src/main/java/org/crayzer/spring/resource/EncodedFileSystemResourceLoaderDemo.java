package org.crayzer.spring.resource;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;

/**
 * 带有字符编码的 {@link FileSystemResourceLoader} 示例
 *
 * @author crayzer
 */
public class EncodedFileSystemResourceLoaderDemo {

    public static void main(String[] args) throws IOException {
        String currentJavaFilePath = "/" + System.getProperty("user.dir") +
                "/spring-in-action/thinking-in-spring/resource/src/main/java/" +
                "org/crayzer/spring/resource/EncodedFileSystemResourceLoaderDemo.java";
        // 新建一个 FileSystemResourceLoader 对象
        FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
        // FileSystemResource => WritableResource => Resource
        Resource resource = fileSystemResourceLoader.getResource(currentJavaFilePath);
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");

        try(Reader reader = encodedResource.getReader()) {
            System.out.println(IOUtils.toString(reader));
        }
    }
}
