package com.otpjava.extention;

import com.otpjava.ericsson.otp.erlang.OtpErlangObject;
import com.otpjava.ericsson.otp.erlang.OtpMailBox;

/**
 * @author wangwenhai
 * @date 2020/8/18
 * File description: Otp message receiver
 */
public class OtpReceiverThread extends Thread {
    private OtpMailBox mailBox;
    private OtpConnectListener otpConnectListener;
    private String peerNode;

    OtpReceiverThread(OtpMailBox mailBox, OtpConnectListener otpConnectListener, String peerNode) {
        setName("OtpReceiverThread");
        this.mailBox = mailBox;
        this.otpConnectListener = otpConnectListener;
        this.peerNode = peerNode;
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                //if (mailBox.ping(peerNode, 1000)) {
                    OtpErlangObject erlangObject = mailBox.receive();
                    this.otpConnectListener.onData(erlangObject);
                //}
            } catch (Exception e) {
                this.otpConnectListener.onError(e);
            }
        }
    }
}
