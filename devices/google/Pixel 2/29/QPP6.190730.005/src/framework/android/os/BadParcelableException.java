/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.util.AndroidRuntimeException;

public class BadParcelableException
extends AndroidRuntimeException {
    public BadParcelableException(Exception exception) {
        super(exception);
    }

    public BadParcelableException(String string2) {
        super(string2);
    }
}

