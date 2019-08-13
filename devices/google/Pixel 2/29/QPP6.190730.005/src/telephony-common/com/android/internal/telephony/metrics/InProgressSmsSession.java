/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 */
package com.android.internal.telephony.metrics;

import android.os.SystemClock;
import com.android.internal.telephony.metrics.SmsSessionEventBuilder;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.nano.TelephonyProto;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;

public class InProgressSmsSession {
    private static final int MAX_EVENTS = 20;
    public final Deque<TelephonyProto.SmsSession.Event> events;
    private boolean mEventsDropped = false;
    private long mLastElapsedTimeMs;
    private AtomicInteger mNumExpectedResponses = new AtomicInteger(0);
    public final int phoneId;
    public final long startElapsedTimeMs;
    public final int startSystemTimeMin;

    public InProgressSmsSession(int n) {
        this.phoneId = n;
        this.events = new ArrayDeque<TelephonyProto.SmsSession.Event>();
        this.startSystemTimeMin = TelephonyMetrics.roundSessionStart(System.currentTimeMillis());
        this.mLastElapsedTimeMs = this.startElapsedTimeMs = SystemClock.elapsedRealtime();
    }

    public void addEvent(long l, SmsSessionEventBuilder smsSessionEventBuilder) {
        synchronized (this) {
            if (this.events.size() >= 20) {
                this.events.removeFirst();
                this.mEventsDropped = true;
            }
            smsSessionEventBuilder.setDelay(TelephonyMetrics.toPrivacyFuzzedTimeInterval(this.mLastElapsedTimeMs, l));
            this.events.add(smsSessionEventBuilder.build());
            this.mLastElapsedTimeMs = l;
            return;
        }
    }

    public void addEvent(SmsSessionEventBuilder smsSessionEventBuilder) {
        this.addEvent(SystemClock.elapsedRealtime(), smsSessionEventBuilder);
    }

    public void decreaseExpectedResponse() {
        this.mNumExpectedResponses.decrementAndGet();
    }

    public int getNumExpectedResponses() {
        return this.mNumExpectedResponses.get();
    }

    public void increaseExpectedResponse() {
        this.mNumExpectedResponses.incrementAndGet();
    }

    public boolean isEventsDropped() {
        return this.mEventsDropped;
    }
}

