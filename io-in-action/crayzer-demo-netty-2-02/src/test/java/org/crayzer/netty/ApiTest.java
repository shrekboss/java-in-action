package org.crayzer.netty;

import com.googlecode.protobuf.format.JsonFormat;
import org.crayzer.netty.domain.MsgBody;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class ApiTest {

    public static void main(String[] args) throws JsonFormat.ParseException {
        MsgBody.Builder msg = MsgBody.newBuilder();
        msg.setChannelId("abD01223");
        msg.setMsgInfo("hi helloworld");
        MsgBody msgBody = msg.build();

        //protobuf转Json 需要引入protobuf-java-format
        String msgBodyStr = JsonFormat.printToString(msgBody);
        System.out.println(msgBodyStr);

        //json转protobuf 需要引入protobuf-java-format
        JsonFormat.merge("{\"channelId\": \"HBdhi993\",\"msgInfo\": \"hi bugstack虫洞栈\"}", msg);
        msgBody = msg.build();
        System.out.println(msgBody.getChannelId());
        System.out.println(msgBody.getMsgInfo());

    }
}
