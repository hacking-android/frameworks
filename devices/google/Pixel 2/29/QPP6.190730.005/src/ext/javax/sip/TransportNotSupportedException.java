/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import javax.sip.SipException;

public class TransportNotSupportedException
extends SipException {
    public TransportNotSupportedException() {
    }

    public TransportNotSupportedException(String string) {
        super(string);
    }

    public TransportNotSupportedException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

