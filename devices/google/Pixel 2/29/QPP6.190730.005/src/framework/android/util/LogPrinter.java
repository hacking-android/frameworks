/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.Log;
import android.util.Printer;

public class LogPrinter
implements Printer {
    private final int mBuffer;
    private final int mPriority;
    private final String mTag;

    public LogPrinter(int n, String string2) {
        this.mPriority = n;
        this.mTag = string2;
        this.mBuffer = 0;
    }

    public LogPrinter(int n, String string2, int n2) {
        this.mPriority = n;
        this.mTag = string2;
        this.mBuffer = n2;
    }

    @Override
    public void println(String string2) {
        Log.println_native(this.mBuffer, this.mPriority, this.mTag, string2);
    }
}

