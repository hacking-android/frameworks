/*
 * Decompiled with CFR 0.145.
 */
package javax.security.auth.callback;

import javax.security.auth.callback.Callback;

public class UnsupportedCallbackException
extends Exception {
    private static final long serialVersionUID = -6873556327655666839L;
    private Callback callback;

    public UnsupportedCallbackException(Callback callback) {
        this.callback = callback;
    }

    public UnsupportedCallbackException(Callback callback, String string) {
        super(string);
        this.callback = callback;
    }

    public Callback getCallback() {
        return this.callback;
    }
}

