/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanException;

public class OperationCanceledException
extends LowpanException {
    public OperationCanceledException() {
    }

    protected OperationCanceledException(Exception exception) {
        super(exception);
    }

    public OperationCanceledException(String string2) {
        super(string2);
    }

    public OperationCanceledException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

