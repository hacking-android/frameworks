/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.JoinFailedException;

public class JoinFailedAtAuthException
extends JoinFailedException {
    public JoinFailedAtAuthException() {
    }

    public JoinFailedAtAuthException(Exception exception) {
        super(exception);
    }

    public JoinFailedAtAuthException(String string2) {
        super(string2);
    }

    public JoinFailedAtAuthException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

