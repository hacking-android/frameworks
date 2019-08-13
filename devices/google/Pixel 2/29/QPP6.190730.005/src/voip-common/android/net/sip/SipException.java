/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  javax.sip.SipException
 */
package android.net.sip;

public class SipException
extends Exception {
    public SipException() {
    }

    public SipException(String string) {
        super(string);
    }

    public SipException(String string, Throwable throwable) {
        if (throwable instanceof javax.sip.SipException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        super(string, throwable);
    }
}

