package org.crayzer.nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * wrk -c 10 -d10s http://localhost:8802
 */
public class HttpServer02 {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8802);

        while (true) {
            Socket socket = ss.accept();
            new Thread(() -> service(socket)).start();
        }
    }

    private static void service(Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf8");
            String body = "hello, nio2";
            printWriter.println("Content-Length:" + body.getBytes(StandardCharsets.UTF_8).length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
