package com.otpjava.extention;

import java.io.IOException;

/**
 * @author wangwenhai
 * @date 2020/8/18
 * File description: Supervisor
 */
public class OtpSupervisorThread extends Thread {
    private OtpConnector otpConnector;

    OtpSupervisorThread(OtpConnector otpConnector) {
        setName("OtpSupervisor:" + getName());
        this.otpConnector = otpConnector;
    }

    @Override
    public void run() {
        try {
            this.otpConnector.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (; ; ) {
            try {

                if (!otpConnector.isConnected()) {
                    Thread.sleep(1000);
                    this.otpConnector.connect();
                }
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
