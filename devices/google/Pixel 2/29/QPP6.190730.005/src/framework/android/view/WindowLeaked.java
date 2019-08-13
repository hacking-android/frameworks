/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.util.AndroidRuntimeException;

final class WindowLeaked
extends AndroidRuntimeException {
    @UnsupportedAppUsage
    public WindowLeaked(String string2) {
        super(string2);
    }
}

