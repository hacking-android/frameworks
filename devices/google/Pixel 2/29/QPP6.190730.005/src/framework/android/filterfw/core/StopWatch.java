/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.os.SystemClock;
import android.util.Log;

class StopWatch {
    private int STOP_WATCH_LOGGING_PERIOD = 200;
    private String TAG = "MFF";
    private String mName;
    private int mNumCalls;
    private long mStartTime;
    private long mTotalTime;

    public StopWatch(String string2) {
        this.mName = string2;
        this.mStartTime = -1L;
        this.mTotalTime = 0L;
        this.mNumCalls = 0;
    }

    public void start() {
        if (this.mStartTime == -1L) {
            this.mStartTime = SystemClock.elapsedRealtime();
            return;
        }
        throw new RuntimeException("Calling start with StopWatch already running");
    }

    public void stop() {
        if (this.mStartTime != -1L) {
            long l = SystemClock.elapsedRealtime();
            this.mTotalTime += l - this.mStartTime;
            ++this.mNumCalls;
            this.mStartTime = -1L;
            if (this.mNumCalls % this.STOP_WATCH_LOGGING_PERIOD == 0) {
                String string2 = this.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("AVG ms/call ");
                stringBuilder.append(this.mName);
                stringBuilder.append(": ");
                stringBuilder.append(String.format("%.1f", Float.valueOf((float)this.mTotalTime * 1.0f / (float)this.mNumCalls)));
                Log.i(string2, stringBuilder.toString());
                this.mTotalTime = 0L;
                this.mNumCalls = 0;
            }
            return;
        }
        throw new RuntimeException("Calling stop with StopWatch already stopped");
    }
}

