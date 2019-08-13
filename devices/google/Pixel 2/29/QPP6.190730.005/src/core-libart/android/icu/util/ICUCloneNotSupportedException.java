/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.ICUException;

public class ICUCloneNotSupportedException
extends ICUException {
    private static final long serialVersionUID = -4824446458488194964L;

    public ICUCloneNotSupportedException() {
    }

    public ICUCloneNotSupportedException(String string) {
        super(string);
    }

    public ICUCloneNotSupportedException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ICUCloneNotSupportedException(Throwable throwable) {
        super(throwable);
    }
}

