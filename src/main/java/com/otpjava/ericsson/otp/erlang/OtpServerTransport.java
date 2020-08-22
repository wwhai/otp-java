package com.otpjava.ericsson.otp.erlang;

import java.io.IOException;

public interface OtpServerTransport {
    int getLocalPort();

    OtpTransport accept() throws IOException;

    void close() throws IOException;
}
