package org.crayzer.netty.bio.client;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 客户端
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class BioClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.65.162", 7397);
            System.out.println("crayzer-demo-netty client start done.");
            BioClientHandler bioClientHandler = new BioClientHandler(socket, Charset.forName("utf-8"));
            bioClientHandler.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
