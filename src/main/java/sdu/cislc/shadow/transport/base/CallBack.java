package sdu.cislc.shadow.transport.base;

/**
 * @Author: bin
 * @Date: 2020/5/20 14:21
 * @Description:
 */
public interface CallBack {

    public void receive(ShadowSocket sender, String message);
}
