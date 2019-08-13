/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.util.AndroidRuntimeException;

final class IntentReceiverLeaked
extends AndroidRuntimeException {
    @UnsupportedAppUsage
    public IntentReceiverLeaked(String string2) {
        super(string2);
    }
}

