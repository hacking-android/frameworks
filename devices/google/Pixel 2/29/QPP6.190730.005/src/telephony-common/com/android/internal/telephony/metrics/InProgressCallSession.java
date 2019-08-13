/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 */
package com.android.internal.telephony.metrics;

import android.os.SystemClock;
import com.android.internal.telephony.metrics.CallSessionEventBuilder;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.nano.TelephonyProto;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class InProgressCallSession {
    private static final int MAX_EVENTS = 300;
    public final Deque<TelephonyProto.TelephonyCallSession.Event> events;
    private boolean mEventsDropped = false;
    private long mLastElapsedTimeMs;
    private int mLastKnownPhoneState;
    public final int phoneId;
    public final long startElapsedTimeMs;
    public final int startSystemTimeMin;

    public InProgressCallSession(int n) {
        this.phoneId = n;
        this.events = new ArrayDeque<TelephonyProto.TelephonyCallSession.Event>();
        this.startSystemTimeMin = TelephonyMetrics.roundSessionStart(System.currentTimeMillis());
        this.mLastElapsedTimeMs = this.startElapsedTimeMs = SystemClock.elapsedRealtime();
    }

    public void addEvent(long l, CallSessionEventBuilder callSessionEventBuilder) {
        synchronized (this) {
            if (this.events.size() >= 300) {
                this.events.removeFirst();
                this.mEventsDropped = true;
            }
            callSessionEventBuilder.setDelay(TelephonyMetrics.toPrivacyFuzzedTimeInterval(this.mLastElapsedTimeMs, l));
            this.events.add(callSessionEventBuilder.build());
            this.mLastElapsedTimeMs = l;
            return;
        }
    }

    public void addEvent(CallSessionEventBuilder callSessionEventBuilder) {
        this.addEvent(SystemClock.elapsedRealtime(), callSessionEventBuilder);
    }

    public boolean containsCsCalls() {
        Iterator<TelephonyProto.TelephonyCallSession.Event> iterator = this.events.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().type != 10) continue;
            return true;
        }
        return false;
    }

    public boolean isEventsDropped() {
        return this.mEventsDropped;
    }

    public boolean isPhoneIdle() {
        int n = this.mLastKnownPhoneState;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public void setLastKnownPhoneState(int n) {
        this.mLastKnownPhoneState = n;
    }
}

