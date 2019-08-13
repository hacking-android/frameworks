/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;

public class UnknownHostException
extends IOException {
    private static final long serialVersionUID = -4639126076052875403L;

    public UnknownHostException() {
    }

    public UnknownHostException(String string) {
        super(string);
    }
}

