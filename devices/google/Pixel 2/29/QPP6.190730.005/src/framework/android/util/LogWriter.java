/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.util.Log;
import java.io.Writer;

public class LogWriter
extends Writer {
    private final int mBuffer;
    private StringBuilder mBuilder = new StringBuilder(128);
    private final int mPriority;
    private final String mTag;

    @UnsupportedAppUsage
    public LogWriter(int n, String string2) {
        this.mPriority = n;
        this.mTag = string2;
        this.mBuffer = 0;
    }

    public LogWriter(int n, String string2, int n2) {
        this.mPriority = n;
        this.mTag = string2;
        this.mBuffer = n2;
    }

    private void flushBuilder() {
        if (this.mBuilder.length() > 0) {
            Log.println_native(this.mBuffer, this.mPriority, this.mTag, this.mBuilder.toString());
            StringBuilder stringBuilder = this.mBuilder;
            stringBuilder.delete(0, stringBuilder.length());
        }
    }

    @Override
    public void close() {
        this.flushBuilder();
    }

    @Override
    public void flush() {
        this.flushBuilder();
    }

    @Override
    public void write(char[] arrc, int n, int n2) {
        for (int i = 0; i < n2; ++i) {
            char c = arrc[n + i];
            if (c == '\n') {
                this.flushBuilder();
                continue;
            }
            this.mBuilder.append(c);
        }
    }
}

