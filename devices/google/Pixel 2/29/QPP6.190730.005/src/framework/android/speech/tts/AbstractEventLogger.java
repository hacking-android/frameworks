/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.os.SystemClock;

abstract class AbstractEventLogger {
    protected final int mCallerPid;
    protected final int mCallerUid;
    private volatile long mEngineCompleteTime = -1L;
    private volatile long mEngineStartTime = -1L;
    private boolean mLogWritten = false;
    protected long mPlaybackStartTime = -1L;
    protected final long mReceivedTime;
    private volatile long mRequestProcessingStartTime = -1L;
    protected final String mServiceApp;

    AbstractEventLogger(int n, int n2, String string2) {
        this.mCallerUid = n;
        this.mCallerPid = n2;
        this.mServiceApp = string2;
        this.mReceivedTime = SystemClock.elapsedRealtime();
    }

    protected abstract void logFailure(int var1);

    protected abstract void logSuccess(long var1, long var3, long var5);

    public void onAudioDataWritten() {
        if (this.mPlaybackStartTime == -1L) {
            this.mPlaybackStartTime = SystemClock.elapsedRealtime();
        }
    }

    public void onCompleted(int n) {
        if (this.mLogWritten) {
            return;
        }
        this.mLogWritten = true;
        SystemClock.elapsedRealtime();
        if (n == 0 && this.mPlaybackStartTime != -1L && this.mEngineCompleteTime != -1L) {
            this.logSuccess(this.mPlaybackStartTime - this.mReceivedTime, this.mEngineStartTime - this.mRequestProcessingStartTime, this.mEngineCompleteTime - this.mRequestProcessingStartTime);
            return;
        }
        this.logFailure(n);
    }

    public void onEngineComplete() {
        this.mEngineCompleteTime = SystemClock.elapsedRealtime();
    }

    public void onEngineDataReceived() {
        if (this.mEngineStartTime == -1L) {
            this.mEngineStartTime = SystemClock.elapsedRealtime();
        }
    }

    public void onRequestProcessingStart() {
        this.mRequestProcessingStartTime = SystemClock.elapsedRealtime();
    }
}

