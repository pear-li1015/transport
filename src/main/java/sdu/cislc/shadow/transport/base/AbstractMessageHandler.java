package sdu.cislc.shadow.transport.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: bin
 * @Date: 2020/5/20 11:15
 * @Description:
 */
public abstract class AbstractMessageHandler {

    WorkMode workMode;

    public AbstractMessageHandler() {
    }


    public abstract void sendMessage(ShadowSocket receiver, String msg);

//    public abstract  ShadowSocket getTempSocket();


    public abstract void onReceiveMsg();

    // TODO
    public final void receiveMessage(ShadowSocket sender, String msg) {
        // 解密得到明文
        String text = TransportUtil.getEncryption().decode(msg, sender, TransportUtil.getTempSocket());
        System.out.println("收到消息发送给振华");
        System.out.println(text);
    }

    public static List<String> otherProps = new ArrayList<>();

    public static List<String> getOtherPropNames() {
        return otherProps;
    }

    public abstract void setOtherProps(List<String> props);
}
