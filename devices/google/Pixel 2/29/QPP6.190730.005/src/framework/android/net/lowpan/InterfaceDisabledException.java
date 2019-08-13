/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanException;

public class InterfaceDisabledException
extends LowpanException {
    public InterfaceDisabledException() {
    }

    protected InterfaceDisabledException(Exception exception) {
        super(exception);
    }

    public InterfaceDisabledException(String string2) {
        super(string2);
    }

    public InterfaceDisabledException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

