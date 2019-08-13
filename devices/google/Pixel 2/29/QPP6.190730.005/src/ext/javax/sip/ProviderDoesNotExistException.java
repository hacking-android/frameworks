/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import javax.sip.SipException;

public class ProviderDoesNotExistException
extends SipException {
    public ProviderDoesNotExistException() {
    }

    public ProviderDoesNotExistException(String string) {
        super(string);
    }

    public ProviderDoesNotExistException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

