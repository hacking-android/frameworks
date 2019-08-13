/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.util.AndroidRuntimeException;

public class LowpanRuntimeException
extends AndroidRuntimeException {
    public LowpanRuntimeException() {
    }

    public LowpanRuntimeException(Exception exception) {
        super(exception);
    }

    public LowpanRuntimeException(String string2) {
        super(string2);
    }

    public LowpanRuntimeException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

