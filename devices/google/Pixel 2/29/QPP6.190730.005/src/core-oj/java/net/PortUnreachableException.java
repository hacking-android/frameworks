/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.net.SocketException;

public class PortUnreachableException
extends SocketException {
    private static final long serialVersionUID = 8462541992376507323L;

    public PortUnreachableException() {
    }

    public PortUnreachableException(String string) {
        super(string);
    }

    public PortUnreachableException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

