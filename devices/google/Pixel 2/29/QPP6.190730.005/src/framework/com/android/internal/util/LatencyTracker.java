/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.EventLog;
import android.util.Log;
import android.util.SparseLongArray;

public class LatencyTracker {
    public static final int ACTION_CHECK_CREDENTIAL = 3;
    public static final int ACTION_CHECK_CREDENTIAL_UNLOCKED = 4;
    public static final int ACTION_EXPAND_PANEL = 0;
    public static final int ACTION_FACE_WAKE_AND_UNLOCK = 6;
    public static final int ACTION_FINGERPRINT_WAKE_AND_UNLOCK = 2;
    private static final String ACTION_RELOAD_PROPERTY = "com.android.systemui.RELOAD_LATENCY_TRACKER_PROPERTY";
    public static final int ACTION_ROTATE_SCREEN = 6;
    public static final int ACTION_TOGGLE_RECENTS = 1;
    public static final int ACTION_TURN_ON_SCREEN = 5;
    private static final String[] NAMES = new String[]{"expand panel", "toggle recents", "fingerprint wake-and-unlock", "check credential", "check credential unlocked", "turn on screen", "rotate the screen", "face wake-and-unlock"};
    private static final String TAG = "LatencyTracker";
    private static LatencyTracker sLatencyTracker;
    private boolean mEnabled;
    private final SparseLongArray mStartRtc = new SparseLongArray();

    private LatencyTracker(Context context) {
        context.registerReceiver(new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                LatencyTracker.this.reloadProperty();
            }
        }, new IntentFilter(ACTION_RELOAD_PROPERTY));
        this.reloadProperty();
    }

    public static LatencyTracker getInstance(Context context) {
        if (sLatencyTracker == null) {
            sLatencyTracker = new LatencyTracker(context.getApplicationContext());
        }
        return sLatencyTracker;
    }

    public static boolean isEnabled(Context context) {
        boolean bl = Build.IS_DEBUGGABLE && LatencyTracker.getInstance((Context)context).mEnabled;
        return bl;
    }

    public static void logAction(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("action=");
        stringBuilder.append(n);
        stringBuilder.append(" latency=");
        stringBuilder.append(n2);
        Log.i(TAG, stringBuilder.toString());
        EventLog.writeEvent(36070, n, n2);
    }

    private void reloadProperty() {
        this.mEnabled = SystemProperties.getBoolean("debug.systemui.latency_tracking", false);
    }

    public void onActionEnd(int n) {
        if (!this.mEnabled) {
            return;
        }
        long l = SystemClock.elapsedRealtime();
        long l2 = this.mStartRtc.get(n, -1L);
        if (l2 == -1L) {
            return;
        }
        this.mStartRtc.delete(n);
        Trace.asyncTraceEnd(4096L, NAMES[n], 0);
        LatencyTracker.logAction(n, (int)(l - l2));
    }

    public void onActionStart(int n) {
        if (!this.mEnabled) {
            return;
        }
        Trace.asyncTraceBegin(4096L, NAMES[n], 0);
        this.mStartRtc.put(n, SystemClock.elapsedRealtime());
    }

}

