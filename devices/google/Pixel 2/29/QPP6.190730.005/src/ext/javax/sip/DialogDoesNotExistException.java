/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import javax.sip.SipException;

public class DialogDoesNotExistException
extends SipException {
    public DialogDoesNotExistException() {
    }

    public DialogDoesNotExistException(String string) {
        super(string);
    }

    public DialogDoesNotExistException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

