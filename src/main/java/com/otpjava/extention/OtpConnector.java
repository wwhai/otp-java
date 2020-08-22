package com.otpjava.extention;

import com.otpjava.ericsson.otp.erlang.OtpErlangObject;
import com.otpjava.ericsson.otp.erlang.OtpMailBox;
import com.otpjava.ericsson.otp.erlang.OtpNode;

import java.io.IOException;

class OtpConnector {
    private OtpConnectListener otpConnectListener;
    private String peerName;
    private OtpNode otpNode;
    private OtpMailBox otpMailBox;

    OtpConnector(String nodeName, String peerName, String cookie, OtpConnectListener otpConnectListener) throws IOException {
        this.peerName = peerName;
        this.otpConnectListener = otpConnectListener;
        try {
            otpNode = new OtpNode(nodeName, cookie);
        } catch (Exception e) {
            throw new IOException("Java otp node start failed,check your epmd if enabled.");
        }
        // mailbox: virtual pseudo erlang process
        otpMailBox = otpNode.createMailBox();
        // name: virtual pseudo erlang process name
        otpMailBox.registerName(nodeName);
        OtpReceiverThread receiveLoop = new OtpReceiverThread(otpMailBox, otpConnectListener, peerName);
        ErlangOtp.executorService.submit(receiveLoop);
    }

    void connect() throws IOException {
        if (!otpNode.ping(peerName, 1000)) {
            throw new IOException("Connect to:" + getPeerName() + "failed.");
        } else {
            this.otpConnectListener.onConnect();
        }
    }

    void send(String processName, OtpErlangObject otpErlangObject) {

        otpMailBox.send(processName, otpErlangObject);
    }


    private String getPeerName() {
        return peerName;
    }


    boolean isConnected() {
        return otpNode.ping(peerName, 1000);
    }

}
