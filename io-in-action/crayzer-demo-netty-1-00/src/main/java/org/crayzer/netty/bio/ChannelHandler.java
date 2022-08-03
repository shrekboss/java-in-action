package org.crayzer.netty.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class ChannelHandler {

    private Socket socket;
    private Charset charset;

    public ChannelHandler(Socket socket, Charset charset) {
        this.socket = socket;
        this.charset = charset;
    }

    public void writeAndFlush(Object msg) {
        OutputStream out = null;
        try {
            out = socket.getOutputStream();
            out.write((msg.toString()).getBytes(charset));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket socket() {
        return socket;
    }
}
