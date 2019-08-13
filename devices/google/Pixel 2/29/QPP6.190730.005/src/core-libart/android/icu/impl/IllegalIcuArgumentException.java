/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import dalvik.annotation.compat.UnsupportedAppUsage;

public class IllegalIcuArgumentException
extends IllegalArgumentException {
    private static final long serialVersionUID = 3789261542830211225L;

    @UnsupportedAppUsage
    public IllegalIcuArgumentException(String string) {
        super(string);
    }

    public IllegalIcuArgumentException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public IllegalIcuArgumentException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public IllegalIcuArgumentException initCause(Throwable throwable) {
        synchronized (this) {
            throwable = (IllegalIcuArgumentException)super.initCause(throwable);
            return throwable;
        }
    }
}

