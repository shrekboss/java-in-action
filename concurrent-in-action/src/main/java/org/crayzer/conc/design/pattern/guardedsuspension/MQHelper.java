package org.crayzer.conc.design.pattern.guardedsuspension;

public class MQHelper {

    //处理浏览器发来的请求
    public Respond handleWebReq() {
        int id = 0;  // 序号生成器.get();
        //创建一消息
        Message msg1 = new Message(id, "{...}");
        //创建GuardedObject实例
        GuardedObject<Message> go =
                GuardedObject.create(id);
        //发送消息
        send(msg1);
        //等待MQ消息
        Message r = go.get(
                t -> t != null);

        return null; //todo
    }

    public static void onMessage(Message msg) {
        //唤醒等待的线程
        GuardedObject.fireEvent( msg.id, msg);
    }

    //该方法可以发送消息
    public void send(Message msg) {
        //省略相关代码
    }
}
