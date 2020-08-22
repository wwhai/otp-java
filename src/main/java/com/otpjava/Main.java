package com.otpjava;

import com.otpjava.ericsson.otp.erlang.OtpErlangAtom;
import com.otpjava.ericsson.otp.erlang.OtpErlangObject;
import com.otpjava.extention.ErlangOtp;
import com.otpjava.extention.OtpConnectListener;

import java.io.IOException;

/**
 * @author wangwenhai
 * @date 2020/8/18
 * File description:
 */
public class Main {
    public static void main(String[] args) throws IOException {

        ErlangOtp erlangOtp = new ErlangOtp();
        // connect
        erlangOtp.connect("java@127.0.0.1", "node1@127.0.0.1", "test", new OtpConnectListener() {
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onConnect() {
                System.out.println("Connect success");
            }

            @Override
            public void onData(OtpErlangObject message) {
                System.out.println("Message received:" + message.toString());
            }
        });
        // send to erlang node
        erlangOtp.send("pname", new OtpErlangAtom("hello"));
    }
}
