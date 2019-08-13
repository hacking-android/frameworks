/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;

class ZygoteStartFailedEx
extends Exception {
    @UnsupportedAppUsage
    ZygoteStartFailedEx(String string2) {
        super(string2);
    }

    ZygoteStartFailedEx(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    @UnsupportedAppUsage
    ZygoteStartFailedEx(Throwable throwable) {
        super(throwable);
    }
}

