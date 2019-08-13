/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

public abstract class CountDownTimer {
    private static final int MSG = 1;
    private boolean mCancelled = false;
    private final long mCountdownInterval;
    private Handler mHandler = new Handler(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message object) {
            object = CountDownTimer.this;
            synchronized (object) {
                if (CountDownTimer.this.mCancelled) {
                    return;
                }
                long l = CountDownTimer.this.mStopTimeInFuture - SystemClock.elapsedRealtime();
                if (l <= 0L) {
                    CountDownTimer.this.onFinish();
                } else {
                    long l2 = SystemClock.elapsedRealtime();
                    CountDownTimer.this.onTick(l);
                    l2 = SystemClock.elapsedRealtime() - l2;
                    if (l < CountDownTimer.this.mCountdownInterval) {
                        l = l2 = l - l2;
                        if (l2 < 0L) {
                            l = 0L;
                        }
                    } else {
                        l2 = CountDownTimer.this.mCountdownInterval - l2;
                        do {
                            l = l2;
                            if (l2 >= 0L) break;
                            l2 += CountDownTimer.this.mCountdownInterval;
                        } while (true);
                    }
                    this.sendMessageDelayed(this.obtainMessage(1), l);
                }
                return;
            }
        }
    };
    private final long mMillisInFuture;
    private long mStopTimeInFuture;

    public CountDownTimer(long l, long l2) {
        this.mMillisInFuture = l;
        this.mCountdownInterval = l2;
    }

    public final void cancel() {
        synchronized (this) {
            this.mCancelled = true;
            this.mHandler.removeMessages(1);
            return;
        }
    }

    public abstract void onFinish();

    public abstract void onTick(long var1);

    public final CountDownTimer start() {
        synchronized (this) {
            block4 : {
                this.mCancelled = false;
                if (this.mMillisInFuture > 0L) break block4;
                this.onFinish();
                return this;
            }
            this.mStopTimeInFuture = SystemClock.elapsedRealtime() + this.mMillisInFuture;
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1));
            return this;
        }
    }

}

