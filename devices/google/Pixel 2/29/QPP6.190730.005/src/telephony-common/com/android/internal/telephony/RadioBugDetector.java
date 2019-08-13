/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  com.android.internal.annotations.VisibleForTesting
 */
package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import com.android.internal.annotations.VisibleForTesting;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class RadioBugDetector {
    private static final int DEFAULT_SYSTEM_ERROR_COUNT_THRESHOLD = 100;
    private static final int DEFAULT_WAKELOCK_TIMEOUT_COUNT_THRESHOLD = 10;
    private static final int RADIO_BUG_NONE = 0;
    @VisibleForTesting
    protected static final int RADIO_BUG_REPETITIVE_SYSTEM_ERROR = 2;
    private static final int RADIO_BUG_REPETITIVE_WAKELOCK_TIMEOUT_ERROR = 1;
    private Context mContext;
    private int mContinuousWakelockTimoutCount = 0;
    private int mRadioBugStatus = 0;
    private int mSlotId;
    private HashMap<Integer, Integer> mSysErrRecord = new HashMap();
    private int mSystemErrorThreshold = 0;
    private int mWakelockTimeoutThreshold = 0;

    public RadioBugDetector(Context context, int n) {
        this.mContext = context;
        this.mSlotId = n;
        this.init();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void broadcastBug(boolean bl) {
        synchronized (this) {
            int n;
            if (bl) {
                boolean bl2 = this.isFrequentSystemError();
                if (!bl2) {
                    return;
                }
            } else {
                int n2 = this.mContinuousWakelockTimoutCount;
                n = this.mWakelockTimeoutThreshold;
                if (n2 < n) {
                    return;
                }
            }
            if (this.mRadioBugStatus == 0) {
                n = bl ? 2 : 1;
                this.mRadioBugStatus = n;
                Intent intent = new Intent("com.android.internal.telephony.ACTION_REPORT_RADIO_BUG");
                intent.addFlags(16777216);
                intent.putExtra("slotId", this.mSlotId);
                intent.putExtra("radioBugType", this.mRadioBugStatus);
                this.mContext.sendBroadcast(intent, "android.permission.READ_PRIVILEGED_PHONE_STATE");
            }
            return;
        }
    }

    private void init() {
        this.mWakelockTimeoutThreshold = Settings.Global.getInt((ContentResolver)this.mContext.getContentResolver(), (String)"radio_bug_wakelock_timeout_count_threshold", (int)10);
        this.mSystemErrorThreshold = Settings.Global.getInt((ContentResolver)this.mContext.getContentResolver(), (String)"radio_bug_system_error_count_threshold", (int)100);
    }

    private boolean isFrequentSystemError() {
        boolean bl;
        block1 : {
            int n = 0;
            boolean bl2 = false;
            Iterator<Integer> iterator = this.mSysErrRecord.values().iterator();
            do {
                bl = bl2;
                if (!iterator.hasNext()) break block1;
            } while ((n += iterator.next().intValue()) < this.mSystemErrorThreshold);
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void detectRadioBug(int n, int n2) {
        synchronized (this) {
            this.mContinuousWakelockTimoutCount = 0;
            if (n2 == 39) {
                n2 = this.mSysErrRecord.getOrDefault(n, 0);
                this.mSysErrRecord.put(n, n2 + 1);
                this.broadcastBug(true);
            } else {
                this.mSysErrRecord.remove(n);
                if (!this.isFrequentSystemError()) {
                    this.mRadioBugStatus = 0;
                }
            }
            return;
        }
    }

    @VisibleForTesting
    public int getRadioBugStatus() {
        return this.mRadioBugStatus;
    }

    @VisibleForTesting
    public int getSystemErrorThreshold() {
        return this.mSystemErrorThreshold;
    }

    @VisibleForTesting
    public int getWakelockTimeoutThreshold() {
        return this.mWakelockTimeoutThreshold;
    }

    @VisibleForTesting
    public int getWakelockTimoutCount() {
        return this.mContinuousWakelockTimoutCount;
    }

    public void processWakelockTimeout() {
        ++this.mContinuousWakelockTimoutCount;
        this.broadcastBug(false);
    }
}

