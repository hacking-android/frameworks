/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.io.IOException;

public class SSLException
extends IOException {
    private static final long serialVersionUID = 4511006460650708967L;

    public SSLException(String string) {
        super(string);
    }

    public SSLException(String string, Throwable throwable) {
        super(string);
        this.initCause(throwable);
    }

    public SSLException(Throwable throwable) {
        String string = throwable == null ? null : throwable.toString();
        super(string);
        this.initCause(throwable);
    }
}

