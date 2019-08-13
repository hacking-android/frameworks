/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telecom.Log
 *  android.util.ArraySet
 */
package com.android.ims.internal;

import android.telecom.Log;
import android.util.ArraySet;
import com.android.ims.internal._$$Lambda$VideoPauseTracker$s27lPMyD4hPTfNQr9bbkfdTbLK8;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VideoPauseTracker {
    public static final int SOURCE_DATA_ENABLED = 2;
    private static final String SOURCE_DATA_ENABLED_STR = "DATA_ENABLED";
    public static final int SOURCE_INCALL = 1;
    private static final String SOURCE_INCALL_STR = "INCALL";
    private Set<Integer> mPauseRequests = new ArraySet(2);
    private Object mPauseRequestsLock = new Object();

    private String sourceToString(int n) {
        if (n != 1) {
            if (n != 2) {
                return "unknown";
            }
            return SOURCE_DATA_ENABLED_STR;
        }
        return SOURCE_INCALL_STR;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String sourcesToString(Collection<Integer> object) {
        Object object2 = this.mPauseRequestsLock;
        synchronized (object2) {
            Stream<Integer> stream = object.stream();
            object = new _$$Lambda$VideoPauseTracker$s27lPMyD4hPTfNQr9bbkfdTbLK8(this);
            return stream.map(object).collect(Collectors.joining(", "));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void clearPauseRequests() {
        Object object = this.mPauseRequestsLock;
        synchronized (object) {
            this.mPauseRequests.clear();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isPaused() {
        Object object = this.mPauseRequestsLock;
        synchronized (object) {
            if (this.mPauseRequests.isEmpty()) return false;
            return true;
        }
    }

    public /* synthetic */ String lambda$sourcesToString$0$VideoPauseTracker(Integer n) {
        return this.sourceToString(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean shouldPauseVideoFor(int n) {
        Object object = this.mPauseRequestsLock;
        synchronized (object) {
            boolean bl = this.isPaused();
            this.mPauseRequests.add(n);
            if (!bl) {
                Log.i((Object)this, (String)"shouldPauseVideoFor: source=%s, pendingRequests=%s - should pause", (Object[])new Object[]{this.sourceToString(n), this.sourcesToString(this.mPauseRequests)});
                return true;
            }
            Log.i((Object)this, (String)"shouldPauseVideoFor: source=%s, pendingRequests=%s - already paused", (Object[])new Object[]{this.sourceToString(n), this.sourcesToString(this.mPauseRequests)});
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean shouldResumeVideoFor(int n) {
        Object object = this.mPauseRequestsLock;
        synchronized (object) {
            boolean bl = this.isPaused();
            this.mPauseRequests.remove(n);
            boolean bl2 = this.isPaused();
            if (bl && !bl2) {
                Log.i((Object)this, (String)"shouldResumeVideoFor: source=%s, pendingRequests=%s - should resume", (Object[])new Object[]{this.sourceToString(n), this.sourcesToString(this.mPauseRequests)});
                return true;
            }
            if (bl && bl2) {
                Log.i((Object)this, (String)"shouldResumeVideoFor: source=%s, pendingRequests=%s - stay paused", (Object[])new Object[]{this.sourceToString(n), this.sourcesToString(this.mPauseRequests)});
                return false;
            }
            Log.i((Object)this, (String)"shouldResumeVideoFor: source=%s, pendingRequests=%s - not paused", (Object[])new Object[]{this.sourceToString(n), this.sourcesToString(this.mPauseRequests)});
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean wasVideoPausedFromSource(int n) {
        Object object = this.mPauseRequestsLock;
        synchronized (object) {
            return this.mPauseRequests.contains(n);
        }
    }
}

