/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;

public class SocketException
extends IOException {
    private static final long serialVersionUID = -5935874303556886934L;

    public SocketException() {
    }

    public SocketException(String string) {
        super(string);
    }

    public SocketException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SocketException(Throwable throwable) {
        super(throwable);
    }
}

