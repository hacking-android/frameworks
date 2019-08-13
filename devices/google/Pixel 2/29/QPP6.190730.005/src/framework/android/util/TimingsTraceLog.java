/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.os.Build;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Pair;
import android.util.Slog;
import java.util.ArrayDeque;
import java.util.Deque;

public class TimingsTraceLog {
    private static final boolean DEBUG_BOOT_TIME = Build.IS_USER ^ true;
    private final Deque<Pair<String, Long>> mStartTimes;
    private final String mTag;
    private long mThreadId;
    private long mTraceTag;

    public TimingsTraceLog(String string2, long l) {
        ArrayDeque<Pair<String, Long>> arrayDeque = DEBUG_BOOT_TIME ? new ArrayDeque<Pair<String, Long>>() : null;
        this.mStartTimes = arrayDeque;
        this.mTag = string2;
        this.mTraceTag = l;
        this.mThreadId = Thread.currentThread().getId();
    }

    private void assertSameThread() {
        Thread thread = Thread.currentThread();
        if (thread.getId() == this.mThreadId) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Instance of TimingsTraceLog can only be called from the thread it was created on (tid: ");
        stringBuilder.append(this.mThreadId);
        stringBuilder.append("), but was from ");
        stringBuilder.append(thread.getName());
        stringBuilder.append(" (tid: ");
        stringBuilder.append(thread.getId());
        stringBuilder.append(")");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void logDuration(String string2, long l) {
        String string3 = this.mTag;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" took to complete: ");
        stringBuilder.append(l);
        stringBuilder.append("ms");
        Slog.d(string3, stringBuilder.toString());
    }

    public void traceBegin(String string2) {
        this.assertSameThread();
        Trace.traceBegin(this.mTraceTag, string2);
        if (DEBUG_BOOT_TIME) {
            this.mStartTimes.push(Pair.create(string2, SystemClock.elapsedRealtime()));
        }
    }

    public void traceEnd() {
        this.assertSameThread();
        Trace.traceEnd(this.mTraceTag);
        if (!DEBUG_BOOT_TIME) {
            return;
        }
        if (this.mStartTimes.peek() == null) {
            Slog.w(this.mTag, "traceEnd called more times than traceBegin");
            return;
        }
        Pair<String, Long> pair = this.mStartTimes.pop();
        this.logDuration((String)pair.first, SystemClock.elapsedRealtime() - (Long)pair.second);
    }
}

