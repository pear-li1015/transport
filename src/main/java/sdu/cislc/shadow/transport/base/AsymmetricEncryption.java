package sdu.cislc.shadow.transport.base;

/**
 * @Author: bin
 * @Date: 2020/5/13 10:46
 * @Description:
 * 非对称加密算法
 * 发送方先检查自己是否有对方公钥，
 *
 * 发送方用对方公钥加密，
 * 接收方用自己私钥解密。
 */
public interface AsymmetricEncryption extends Encryption {

    /**
     * 获取本机公钥，
     * @return
     */
    String getPublicKey();


    // TODO MQTT是单向通信的， 非对称加密需要双向通信。
}
