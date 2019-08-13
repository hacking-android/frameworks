/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.net.SocketException;

public class BindException
extends SocketException {
    private static final long serialVersionUID = -5945005768251722951L;

    public BindException() {
    }

    public BindException(String string) {
        super(string);
    }

    public BindException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

