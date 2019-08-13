/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanException;

public class JoinFailedException
extends LowpanException {
    public JoinFailedException() {
    }

    protected JoinFailedException(Exception exception) {
        super(exception);
    }

    public JoinFailedException(String string2) {
        super(string2);
    }

    public JoinFailedException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

