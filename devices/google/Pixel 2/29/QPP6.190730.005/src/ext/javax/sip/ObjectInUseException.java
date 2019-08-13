/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import javax.sip.SipException;

public class ObjectInUseException
extends SipException {
    public ObjectInUseException() {
    }

    public ObjectInUseException(String string) {
        super(string);
    }

    public ObjectInUseException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

