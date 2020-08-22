package com.otpjava.extention;

import com.otpjava.ericsson.otp.erlang.OtpErlangObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangwenhai
 * @date 2020/8/19
 * File description: JavaOtp User Interface
 */
public class ErlangOtp {
    // Pool
    static final ExecutorService executorService = Executors.newFixedThreadPool(3);
    // OtpConnector
    private OtpConnector otpConnector;

    /**
     * connect to erlang node
     *
     * @param nodeName           java node name
     * @param peerName           erlang node name
     * @param cookie             cookie
     * @param otpConnectListener callback
     * @throws IOException e
     */
    public void connect(String nodeName, String peerName, String cookie, OtpConnectListener otpConnectListener) throws IOException {
        otpConnector = new OtpConnector(nodeName, peerName, cookie, otpConnectListener);
        OtpSupervisorThread supervisorThread = new OtpSupervisorThread(otpConnector);
        executorService.submit(supervisorThread);
    }

    /**
     * send to erlang
     *
     * @param processName
     * @param otpErlangObject
     */
    public void send(String processName, OtpErlangObject otpErlangObject) {
        otpConnector.send(processName, otpErlangObject);
    }

}
