/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.GeneralSecurityException;

public class CRLException
extends GeneralSecurityException {
    private static final long serialVersionUID = -6694728944094197147L;

    public CRLException() {
    }

    public CRLException(String string) {
        super(string);
    }

    public CRLException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CRLException(Throwable throwable) {
        super(throwable);
    }
}

