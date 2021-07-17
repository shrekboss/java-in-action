package org.crayzer.jvm.opcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogException {
    private static Logger logger
            = LoggerFactory.getLogger(TestLogException.class);

    public static void main(String[] args) {
        int num = 0;
        try {
            num = 8 / 0;
        } catch (Exception ex) {
            logger.error("发生错误:num={}; args={}; xxx={}", num, args, ex);
        }
    }
}
