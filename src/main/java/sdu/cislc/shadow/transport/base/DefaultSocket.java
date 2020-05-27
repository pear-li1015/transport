package sdu.cislc.shadow.transport.base;

import sdu.cislc.shadow.transport.base.ShadowSocket;

import java.util.List;

/**
 * @Author: bin
 * @Date: 2020/5/21 10:32
 * @Description:
 */
public class DefaultSocket extends ShadowSocket {

    static {
        otherPropNames.add("test.DefaultSocket.config");
    }
    @Override
    public void setOtherProp(List<String> otherProp) {

    }
}
