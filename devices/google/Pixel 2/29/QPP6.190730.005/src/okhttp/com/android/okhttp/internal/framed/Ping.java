/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.framed;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class Ping {
    private final CountDownLatch latch = new CountDownLatch(1);
    private long received = -1L;
    private long sent = -1L;

    Ping() {
    }

    void cancel() {
        long l;
        if (this.received == -1L && (l = this.sent) != -1L) {
            this.received = l - 1L;
            this.latch.countDown();
            return;
        }
        throw new IllegalStateException();
    }

    void receive() {
        if (this.received == -1L && this.sent != -1L) {
            this.received = System.nanoTime();
            this.latch.countDown();
            return;
        }
        throw new IllegalStateException();
    }

    public long roundTripTime() throws InterruptedException {
        this.latch.await();
        return this.received - this.sent;
    }

    public long roundTripTime(long l, TimeUnit timeUnit) throws InterruptedException {
        if (this.latch.await(l, timeUnit)) {
            return this.received - this.sent;
        }
        return -2L;
    }

    void send() {
        if (this.sent == -1L) {
            this.sent = System.nanoTime();
            return;
        }
        throw new IllegalStateException();
    }
}

