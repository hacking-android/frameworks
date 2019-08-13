/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import javax.sip.SipException;

public class TransactionAlreadyExistsException
extends SipException {
    public TransactionAlreadyExistsException() {
    }

    public TransactionAlreadyExistsException(String string) {
        super(string);
    }

    public TransactionAlreadyExistsException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

