/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.telephony.Rlog
 */
package com.android.server.sip;

import android.os.PowerManager;
import android.telephony.Rlog;
import java.util.HashSet;

class SipWakeLock {
    private static final boolean DBG = false;
    private static final String TAG = "SipWakeLock";
    private HashSet<Object> mHolders = new HashSet();
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mTimerWakeLock;
    private PowerManager.WakeLock mWakeLock;

    SipWakeLock(PowerManager powerManager) {
        this.mPowerManager = powerManager;
    }

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    void acquire(long l) {
        synchronized (this) {
            if (this.mTimerWakeLock == null) {
                this.mTimerWakeLock = this.mPowerManager.newWakeLock(1, "SipWakeLock.timer");
                this.mTimerWakeLock.setReferenceCounted(true);
            }
            this.mTimerWakeLock.acquire(l);
            return;
        }
    }

    void acquire(Object object) {
        synchronized (this) {
            this.mHolders.add(object);
            if (this.mWakeLock == null) {
                this.mWakeLock = this.mPowerManager.newWakeLock(1, TAG);
            }
            if (!this.mWakeLock.isHeld()) {
                this.mWakeLock.acquire();
            }
            return;
        }
    }

    void release(Object object) {
        synchronized (this) {
            this.mHolders.remove(object);
            if (this.mWakeLock != null && this.mHolders.isEmpty() && this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
            return;
        }
    }

    void reset() {
        synchronized (this) {
            this.mHolders.clear();
            this.release(null);
            return;
        }
    }
}

