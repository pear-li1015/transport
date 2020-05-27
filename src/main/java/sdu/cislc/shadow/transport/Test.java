package sdu.cislc.shadow.transport;

import sdu.cislc.shadow.transport.base.ShadowSocket;
import sdu.cislc.shadow.transport.base.TransportUtil;
import sdu.cislc.shadow.transport.base.WorkMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: bin
 * @Date: 2020/5/20 10:31
 * @Description:
 */
public class Test {


    public static void main(String[] args) {
        // 设置与通信方式相关的socket应使用哪种模式，得到应配置的其他参数
        List<String> socketProps = TransportUtil.setShadowSocket("default");
        System.out.print("socket需配置的其他参数个数");
        List<String> props3 = readConfig(socketProps);
        // 配置当前socket参数
        TransportUtil.setShadowSocketProps("tempId", props3);

        // 设置通讯方式，并得到需要的其他参数
        List<String> transportProps = TransportUtil.setTransport("sdu.cislc.shadow.transport.mqtt.MQTTMessageHandler");
        System.out.print("通讯方式需配置的其他参数个数");
        List<String> props = Arrays.asList("192.168.1.8", "5672", "root", "root");  //readConfig(transportProps);
        // 设置通讯方式其他参数
        TransportUtil.setTransportProps(props);

        // 设置加密方式
        List<String> encryptionProps = TransportUtil.setEncryption("sdu.cislc.shadow.transport.encrypt.AESEncoder");  // none
        System.out.print("加密方式需配置的其他参数个数");
        List<String> props2 = readConfig(encryptionProps);
        TransportUtil.setEncryptionProps(Arrays.asList("testKey"));

        // 设置收到消息的回调
        TransportUtil.setCallBack((ShadowSocket sender, String message) -> {
            System.out.println(message);
        });

        // 设置 server 工作模式
//        TransportUtil.setWorkMode(WorkMode.SERVER);

        // 设置 client 工作模式
        TransportUtil.setWorkMode("newId", Arrays.asList());

        // 发送测试消息
        TransportUtil.getMessageHandler().sendMessage(TransportUtil.getServerSocket(), "client send to server");

    }



    private static List<String> readConfig(List<String> in) {
        List<String> out = new ArrayList<>();
        System.out.println(in.size());
        for (String i : in) {
            System.out.print(i + ", ");
            out.add(i + Math.random());
        }
        System.out.println();
        return out;
    }

}
