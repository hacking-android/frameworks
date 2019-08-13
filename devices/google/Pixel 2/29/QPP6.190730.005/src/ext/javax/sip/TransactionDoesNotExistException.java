/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import javax.sip.SipException;

public class TransactionDoesNotExistException
extends SipException {
    public TransactionDoesNotExistException() {
    }

    public TransactionDoesNotExistException(String string) {
        super(string);
    }

    public TransactionDoesNotExistException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

