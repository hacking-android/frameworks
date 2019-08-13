/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.SystemClock;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;

public class CachedDeviceState {
    private volatile boolean mCharging;
    @GuardedBy(value={"mStopwatchLock"})
    private final ArrayList<TimeInStateStopwatch> mOnBatteryStopwatches = new ArrayList();
    private volatile boolean mScreenInteractive;
    private final Object mStopwatchesLock = new Object();

    public CachedDeviceState() {
        this.mCharging = true;
        this.mScreenInteractive = false;
    }

    @VisibleForTesting
    public CachedDeviceState(boolean bl, boolean bl2) {
        this.mCharging = bl;
        this.mScreenInteractive = bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateStopwatches(boolean bl) {
        Object object = this.mStopwatchesLock;
        synchronized (object) {
            int n = this.mOnBatteryStopwatches.size();
            int n2 = 0;
            while (n2 < n) {
                if (bl) {
                    this.mOnBatteryStopwatches.get(n2).start();
                } else {
                    this.mOnBatteryStopwatches.get(n2).stop();
                }
                ++n2;
            }
            return;
        }
    }

    public Readonly getReadonlyClient() {
        return new Readonly();
    }

    public void setCharging(boolean bl) {
        if (this.mCharging != bl) {
            this.mCharging = bl;
            this.updateStopwatches(bl ^ true);
        }
    }

    public void setScreenInteractive(boolean bl) {
        this.mScreenInteractive = bl;
    }

    public class Readonly {
        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public TimeInStateStopwatch createTimeOnBatteryStopwatch() {
            Object object = CachedDeviceState.this.mStopwatchesLock;
            synchronized (object) {
                TimeInStateStopwatch timeInStateStopwatch = new TimeInStateStopwatch();
                CachedDeviceState.this.mOnBatteryStopwatches.add(timeInStateStopwatch);
                if (!CachedDeviceState.this.mCharging) {
                    timeInStateStopwatch.start();
                }
                return timeInStateStopwatch;
            }
        }

        public boolean isCharging() {
            return CachedDeviceState.this.mCharging;
        }

        public boolean isScreenInteractive() {
            return CachedDeviceState.this.mScreenInteractive;
        }
    }

    public class TimeInStateStopwatch
    implements AutoCloseable {
        private final Object mLock = new Object();
        @GuardedBy(value={"mLock"})
        private long mStartTimeMillis;
        @GuardedBy(value={"mLock"})
        private long mTotalTimeMillis;

        private long elapsedTime() {
            long l = this.isRunning() ? SystemClock.elapsedRealtime() - this.mStartTimeMillis : 0L;
            return l;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void start() {
            Object object = this.mLock;
            synchronized (object) {
                if (!this.isRunning()) {
                    this.mStartTimeMillis = SystemClock.elapsedRealtime();
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void stop() {
            Object object = this.mLock;
            synchronized (object) {
                if (this.isRunning()) {
                    this.mTotalTimeMillis += this.elapsedTime();
                    this.mStartTimeMillis = 0L;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void close() {
            Object object = CachedDeviceState.this.mStopwatchesLock;
            synchronized (object) {
                CachedDeviceState.this.mOnBatteryStopwatches.remove(this);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public long getMillis() {
            Object object = this.mLock;
            synchronized (object) {
                long l = this.mTotalTimeMillis;
                long l2 = this.elapsedTime();
                return l + l2;
            }
        }

        @VisibleForTesting
        public boolean isRunning() {
            boolean bl = this.mStartTimeMillis > 0L;
            return bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void reset() {
            Object object = this.mLock;
            synchronized (object) {
                long l = 0L;
                this.mTotalTimeMillis = 0L;
                if (this.isRunning()) {
                    l = SystemClock.elapsedRealtime();
                }
                this.mStartTimeMillis = l;
                return;
            }
        }
    }

}

