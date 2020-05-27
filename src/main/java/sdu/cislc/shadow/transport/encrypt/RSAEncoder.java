package sdu.cislc.shadow.transport.encrypt;

import sdu.cislc.shadow.transport.base.ShadowSocket;
import sdu.cislc.shadow.transport.base.SymmetricEncryption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: bin
 * @Date: 2020/5/24 19:44
 * @Description:
 * https://blog.csdn.net/qy20115549/article/details/83105736
 */
public class RSAEncoder implements SymmetricEncryption {
    // 存放其他通信方的 公钥 <id, 公钥>
    private static Map<Integer, String> publicKeys = new HashMap<>();
    // 当前 公、私钥
    private static String publicKey, privateKey;


    @Override
    public String encode(String text, ShadowSocket senderIdentify, ShadowSocket receiverIdentify) {
        return null;
    }

    @Override
    public String decode(String text, ShadowSocket senderIdentify, ShadowSocket receiverIdentify) {
        return null;
    }

    @Override
    public void setOtherProps(List<String> props) {

    }

    static {
        otherProps.add("shadow.communicate.rsa.publicKey");
        otherProps.add("shadow.communicate.rsa.privateKey");
    }

    /**
     * 获取 当前设备的 公私钥
     */
    private static void getKeys() {
        // 1、尝试从指定位置读取
        // 2、生成新的 公私钥 对
        // 3、尝试将公私钥保存至指定位置

    }
}
