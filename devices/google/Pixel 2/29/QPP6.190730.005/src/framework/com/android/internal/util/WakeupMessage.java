/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.android.internal.annotations.VisibleForTesting;

public class WakeupMessage
implements AlarmManager.OnAlarmListener {
    private final AlarmManager mAlarmManager;
    @VisibleForTesting
    protected final int mArg1;
    @VisibleForTesting
    protected final int mArg2;
    @VisibleForTesting
    protected final int mCmd;
    @VisibleForTesting
    protected final String mCmdName;
    @VisibleForTesting
    protected final Handler mHandler;
    @VisibleForTesting
    protected final Object mObj;
    private final Runnable mRunnable;
    private boolean mScheduled;

    public WakeupMessage(Context context, Handler handler, String string2, int n) {
        this(context, handler, string2, n, 0, 0, null);
    }

    public WakeupMessage(Context context, Handler handler, String string2, int n, int n2) {
        this(context, handler, string2, n, n2, 0, null);
    }

    public WakeupMessage(Context context, Handler handler, String string2, int n, int n2, int n3) {
        this(context, handler, string2, n, n2, n3, null);
    }

    public WakeupMessage(Context context, Handler handler, String string2, int n, int n2, int n3, Object object) {
        this.mAlarmManager = WakeupMessage.getAlarmManager(context);
        this.mHandler = handler;
        this.mCmdName = string2;
        this.mCmd = n;
        this.mArg1 = n2;
        this.mArg2 = n3;
        this.mObj = object;
        this.mRunnable = null;
    }

    public WakeupMessage(Context context, Handler handler, String string2, Runnable runnable) {
        this.mAlarmManager = WakeupMessage.getAlarmManager(context);
        this.mHandler = handler;
        this.mCmdName = string2;
        this.mCmd = 0;
        this.mArg1 = 0;
        this.mArg2 = 0;
        this.mObj = null;
        this.mRunnable = runnable;
    }

    private static AlarmManager getAlarmManager(Context context) {
        return (AlarmManager)context.getSystemService("alarm");
    }

    public void cancel() {
        synchronized (this) {
            if (this.mScheduled) {
                this.mAlarmManager.cancel(this);
                this.mScheduled = false;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void onAlarm() {
        // MONITORENTER : this
        boolean bl = this.mScheduled;
        this.mScheduled = false;
        // MONITOREXIT : this
        if (!bl) return;
        Object object = this.mRunnable;
        object = object == null ? this.mHandler.obtainMessage(this.mCmd, this.mArg1, this.mArg2, this.mObj) : Message.obtain(this.mHandler, (Runnable)object);
        this.mHandler.dispatchMessage((Message)object);
        ((Message)object).recycle();
    }

    public void schedule(long l) {
        synchronized (this) {
            this.mAlarmManager.setExact(2, l, this.mCmdName, this, this.mHandler);
            this.mScheduled = true;
            return;
        }
    }
}

