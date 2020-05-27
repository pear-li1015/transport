package sdu.cislc.shadow.transport.base;


import sdu.cislc.shadow.transport.base.ShadowSocket;
import sdu.cislc.shadow.transport.base.SymmetricEncryption;

import java.util.List;

/**
 * @Author: bin
 * @Date: 2020/5/13 10:42
 * @Description:
 * 默认的加解密器，无任何加密, 也是一种对称加密
 */
public class DefaultEncoder implements SymmetricEncryption {

    public DefaultEncoder() {
    }

    @Override
    public String encode(String text, ShadowSocket senderIdentify, ShadowSocket receiverIdentify) {
        return text;
    }

    @Override
    public String decode(String text, ShadowSocket senderIdentify, ShadowSocket receiverIdentify) {
        return text;
    }

    @Override
    public void setOtherProps(List<String> props) {

    }
}
