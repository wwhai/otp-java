package com.otpjava.extention;

import com.otpjava.ericsson.otp.erlang.OtpErlangObject;

/**
 * Otp事件监听器
 */
public abstract class OtpConnectListener {

    public abstract void onError(Exception e);

    public abstract void onConnect();

    public abstract void onData(OtpErlangObject message);

}
