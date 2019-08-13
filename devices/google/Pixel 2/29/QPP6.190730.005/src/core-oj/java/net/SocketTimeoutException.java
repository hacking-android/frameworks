/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.InterruptedIOException;

public class SocketTimeoutException
extends InterruptedIOException {
    private static final long serialVersionUID = -8846654841826352300L;

    public SocketTimeoutException() {
    }

    public SocketTimeoutException(String string) {
        super(string);
    }
}

