package sdu.cislc.shadow.transport.base;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author: bin
 * @Date: 2020/5/13 10:46
 * @Description:
 * 加密算法
 */
public interface Encryption {

    /**
     *
     * @param text 明文
     * @param senderIdentify 通信双方身份标识
     * @param receiverIdentify 通信双方身份标识
     * @return
     */
    String encode(String text, ShadowSocket senderIdentify, ShadowSocket receiverIdentify);

    String decode(String text, ShadowSocket senderIdentify, ShadowSocket receiverIdentify);

    static List<String> otherProps = new ArrayList<>();

    static List<String> getOtherPropNames() {
        return otherProps;
    }

    public abstract void setOtherProps(List<String> props);
}
