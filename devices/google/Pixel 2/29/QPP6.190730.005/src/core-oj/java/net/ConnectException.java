/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.net.SocketException;

public class ConnectException
extends SocketException {
    private static final long serialVersionUID = 3831404271622369215L;

    public ConnectException() {
    }

    public ConnectException(String string) {
        super(string);
    }

    public ConnectException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

