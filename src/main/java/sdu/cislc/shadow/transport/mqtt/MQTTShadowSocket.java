package sdu.cislc.shadow.transport.mqtt;

import sdu.cislc.shadow.transport.base.ShadowSocket;

import java.util.List;

/**
 * @Author: bin
 * @Date: 2020/5/20 18:00
 * @Description:
 */
public class MQTTShadowSocket extends ShadowSocket {


    static {
//        otherPropNames.add("shadow.rabbitmq.host");
//        otherPropNames.add("shadow.rabbitmq.port");
//        otherPropNames.add("shadow.rabbitmq.username");
//        otherPropNames.add("shadow.rabbitmq.password");
    }

    public MQTTShadowSocket() {
    }

    public MQTTShadowSocket(String id) {
        super(id);
    }

    @Override
    public void setOtherProp(List<String> otherProp) {

    }

}
