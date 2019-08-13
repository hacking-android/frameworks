/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.util.Log;
import com.android.internal.os.LoggingPrintStream;

class AndroidPrintStream
extends LoggingPrintStream {
    private final int priority;
    private final String tag;

    @UnsupportedAppUsage
    public AndroidPrintStream(int n, String string2) {
        if (string2 != null) {
            this.priority = n;
            this.tag = string2;
            return;
        }
        throw new NullPointerException("tag");
    }

    @Override
    protected void log(String string2) {
        Log.println(this.priority, this.tag, string2);
    }
}

