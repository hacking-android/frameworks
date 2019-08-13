/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanException;

public class WrongStateException
extends LowpanException {
    public WrongStateException() {
    }

    protected WrongStateException(Exception exception) {
        super(exception);
    }

    public WrongStateException(String string2) {
        super(string2);
    }

    public WrongStateException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

