package sdu.cislc.shadow.transport.mqtt;

import com.rabbitmq.client.*;
import sdu.cislc.shadow.transport.base.AbstractMessageHandler;
import sdu.cislc.shadow.transport.base.ShadowSocket;
import sdu.cislc.shadow.transport.base.TransportUtil;

import java.io.IOException;
import java.util.List;

/**
 * @Author: bin
 * @Date: 2020/5/21 11:00
 * @Description:
 */
public class MQTTMessageHandler extends AbstractMessageHandler {
    static Channel channel;

    static {
        otherProps.add("shadow.rabbitmq.host");
        otherProps.add("shadow.rabbitmq.port");
        otherProps.add("shadow.rabbitmq.username");
        otherProps.add("shadow.rabbitmq.password");
    }


    @Override
    public void sendMessage(ShadowSocket receiver, String msg) {
        try {
            // 如果队列不存在，先声明
            channel.queueDeclare(receiver.getId(), false, false, false, null);
            // 向队列中发送消息
            String encodedMsg = TransportUtil.getEncryption().encode(msg, TransportUtil.getTempSocket(), receiver);
            channel.basicPublish("", receiver.getId(), null, encodedMsg.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onReceiveMsg() {

    }

    @Override
    public void setOtherProps(List<String> props) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(props.get(0));
            factory.setPort(Integer.parseInt(props.get(1)));
            factory.setUsername(props.get(2));
            factory.setPassword(props.get(3));

            Connection connection = factory.newConnection();
            channel = connection.createChannel();

            // 声明自己要监听的队列
            channel.queueDeclare(TransportUtil.getTempSocket().getId(), false, false, false, null);

            // 监听队列
            channel.basicConsume(TransportUtil.getTempSocket().getId(), true, new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery message) throws IOException {

                    ShadowSocket sender = new MQTTShadowSocket();
                    // 执行回调
                    String decodedMsg = TransportUtil.getEncryption().decode(new String(message.getBody()), sender, TransportUtil.getTempSocket());
                    TransportUtil.callBack.receive(sender, decodedMsg);
                }
            }, consumerTag -> {
            });

            // 发送测试消息
//            sendMessage(TransportUtil.getTempSocket(), "this is a test message");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
