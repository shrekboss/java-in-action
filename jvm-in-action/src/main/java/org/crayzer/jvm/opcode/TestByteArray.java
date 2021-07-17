package org.crayzer.jvm.opcode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class TestByteArray {

    public static void main(String... args) throws IOException {
        final String UTF_8 = "UTF-8";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write("Hello-ByteArray!\n".getBytes(UTF_8));
        outputStream.write("Bye".getBytes(Charset.forName(UTF_8)));
        outputStream.flush();
        byte[] bytes = outputStream.toByteArray();
        System.out.println(new String(bytes, UTF_8));
    }
}
