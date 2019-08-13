/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.JoinFailedException;

public class JoinFailedAtScanException
extends JoinFailedException {
    public JoinFailedAtScanException() {
    }

    public JoinFailedAtScanException(Exception exception) {
        super(exception);
    }

    public JoinFailedAtScanException(String string2) {
        super(string2);
    }

    public JoinFailedAtScanException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

