/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

public class Timeout {
    public static final Timeout NONE = new Timeout(){

        @Override
        public Timeout deadlineNanoTime(long l) {
            return this;
        }

        @Override
        public void throwIfReached() throws IOException {
        }

        @Override
        public Timeout timeout(long l, TimeUnit timeUnit) {
            return this;
        }
    };
    private long deadlineNanoTime;
    private boolean hasDeadline;
    private long timeoutNanos;

    public Timeout clearDeadline() {
        this.hasDeadline = false;
        return this;
    }

    public Timeout clearTimeout() {
        this.timeoutNanos = 0L;
        return this;
    }

    public final Timeout deadline(long l, TimeUnit object) {
        if (l > 0L) {
            if (object != null) {
                return this.deadlineNanoTime(System.nanoTime() + ((TimeUnit)((Object)object)).toNanos(l));
            }
            throw new IllegalArgumentException("unit == null");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("duration <= 0: ");
        ((StringBuilder)object).append(l);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public long deadlineNanoTime() {
        if (this.hasDeadline) {
            return this.deadlineNanoTime;
        }
        throw new IllegalStateException("No deadline");
    }

    public Timeout deadlineNanoTime(long l) {
        this.hasDeadline = true;
        this.deadlineNanoTime = l;
        return this;
    }

    public boolean hasDeadline() {
        return this.hasDeadline;
    }

    public void throwIfReached() throws IOException {
        if (!Thread.interrupted()) {
            if (this.hasDeadline && this.deadlineNanoTime - System.nanoTime() <= 0L) {
                throw new InterruptedIOException("deadline reached");
            }
            return;
        }
        throw new InterruptedIOException("thread interrupted");
    }

    public Timeout timeout(long l, TimeUnit object) {
        if (l >= 0L) {
            if (object != null) {
                this.timeoutNanos = ((TimeUnit)((Object)object)).toNanos(l);
                return this;
            }
            throw new IllegalArgumentException("unit == null");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("timeout < 0: ");
        ((StringBuilder)object).append(l);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public long timeoutNanos() {
        return this.timeoutNanos;
    }

}

