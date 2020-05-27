package sdu.cislc.shadow.transport.encrypt;

import sdu.cislc.shadow.transport.base.ShadowSocket;
import sdu.cislc.shadow.transport.base.SymmetricEncryption;
import sdu.cislc.shadow.transport.base.TransportException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.List;

/**
 * @Author: bin
 * @Date: 2020/5/22 12:07
 * @Description:
 */
public class AESEncoder implements SymmetricEncryption {

    static {
        otherProps.add("shadow.communicate.aes.key");  // 加密随机种子
    }

    private static Cipher encodeCipher;
    private static Cipher decodeCipher;

    @Override
    public String encode(String text, ShadowSocket senderIdentify, ShadowSocket receiverIdentify) {
        if (encodeCipher == null) {
            throw new TransportException("加密器初始化失败，无法加密");
        }
        try {
            byte [] byte_AES=encodeCipher.doFinal(text.getBytes());
            //10.将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
            //11.将字符串返回
            return AES_encode;
        } catch (Exception e) {
            throw new TransportException("加密失败");
        }
    }

    @Override
    public String decode(String text, ShadowSocket senderIdentify, ShadowSocket receiverIdentify) {
        if (decodeCipher == null) {
            throw new TransportException("解密器初始化失败，无法解密");
        }
        try {

            //8.将加密并编码后的内容解码成字节数组
            byte [] byte_content= new BASE64Decoder().decodeBuffer(text);
            /*
             * 解密
             */
            byte [] byte_decode=decodeCipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            return AES_decode;
        } catch (Exception e) {
            throw new TransportException("解密失败");
        }
    }

    @Override
    public void setOtherProps(List<String> props) {
        if (props.size() == 0 || props.get(0) == null) {
            throw new TransportException("请先配置 shadow.communicate.aes.key");
        }
        encodeCipher = getEncodeCipher(props.get(0));
        decodeCipher = getDecodeCipher(props.get(0));
    }

    private static Cipher getEncodeCipher(String encodeRules) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher;
        } catch (Exception e) {
            throw new TransportException("加密器初始化失败");
        }
    }

    private static Cipher getDecodeCipher(String encodeRules) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher;
        } catch (Exception e) {
            throw new TransportException("解密器初始化失败");
        }
    }

}
