/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import javax.sip.SipException;

public class InvalidArgumentException
extends SipException {
    public InvalidArgumentException() {
    }

    public InvalidArgumentException(String string) {
        super(string);
    }

    public InvalidArgumentException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

