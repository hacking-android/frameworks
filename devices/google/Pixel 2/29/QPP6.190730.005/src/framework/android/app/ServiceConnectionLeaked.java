/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.util.AndroidRuntimeException;

final class ServiceConnectionLeaked
extends AndroidRuntimeException {
    @UnsupportedAppUsage
    public ServiceConnectionLeaked(String string2) {
        super(string2);
    }
}

