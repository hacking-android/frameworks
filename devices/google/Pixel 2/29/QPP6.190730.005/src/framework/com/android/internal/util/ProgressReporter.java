/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.os.Bundle;
import android.os.IProgressListener;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.MathUtils;
import com.android.internal.annotations.GuardedBy;

public class ProgressReporter {
    private static final int STATE_FINISHED = 2;
    private static final int STATE_INIT = 0;
    private static final int STATE_STARTED = 1;
    @GuardedBy(value={"this"})
    private Bundle mExtras = new Bundle();
    private final int mId;
    @GuardedBy(value={"this"})
    private final RemoteCallbackList<IProgressListener> mListeners = new RemoteCallbackList();
    @GuardedBy(value={"this"})
    private int mProgress = 0;
    @GuardedBy(value={"this"})
    private int[] mSegmentRange = new int[]{0, 100};
    @GuardedBy(value={"this"})
    private int mState = 0;

    public ProgressReporter(int n) {
        this.mId = n;
    }

    private void notifyFinished(int n, Bundle bundle) {
        for (int i = this.mListeners.beginBroadcast() - 1; i >= 0; --i) {
            try {
                this.mListeners.getBroadcastItem(i).onFinished(n, bundle);
                continue;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        this.mListeners.finishBroadcast();
    }

    private void notifyProgress(int n, int n2, Bundle bundle) {
        for (int i = this.mListeners.beginBroadcast() - 1; i >= 0; --i) {
            try {
                this.mListeners.getBroadcastItem(i).onProgress(n, n2, bundle);
                continue;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        this.mListeners.finishBroadcast();
    }

    private void notifyStarted(int n, Bundle bundle) {
        for (int i = this.mListeners.beginBroadcast() - 1; i >= 0; --i) {
            try {
                this.mListeners.getBroadcastItem(i).onStarted(n, bundle);
                continue;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        this.mListeners.finishBroadcast();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addListener(IProgressListener iProgressListener) {
        if (iProgressListener == null) {
            return;
        }
        synchronized (this) {
            this.mListeners.register(iProgressListener);
            int n = this.mState;
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        try {
                            iProgressListener.onFinished(this.mId, null);
                        }
                        catch (RemoteException remoteException) {}
                    }
                } else {
                    try {
                        iProgressListener.onStarted(this.mId, null);
                        iProgressListener.onProgress(this.mId, this.mProgress, this.mExtras);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void endSegment(int[] arrn) {
        synchronized (this) {
            this.mProgress = this.mSegmentRange[0] + this.mSegmentRange[1];
            this.mSegmentRange = arrn;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void finish() {
        synchronized (this) {
            this.mState = 2;
            this.notifyFinished(this.mId, null);
            this.mListeners.kill();
            return;
        }
    }

    int getProgress() {
        return this.mProgress;
    }

    int[] getSegmentRange() {
        return this.mSegmentRange;
    }

    public void setProgress(int n) {
        this.setProgress(n, 100, null);
    }

    public void setProgress(int n, int n2) {
        this.setProgress(n, n2, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setProgress(int n, int n2, CharSequence object) {
        synchronized (this) {
            if (this.mState != 1) {
                object = new IllegalStateException("Must be started to change progress");
                throw object;
            }
            this.mProgress = this.mSegmentRange[0] + MathUtils.constrain(this.mSegmentRange[1] * n / n2, 0, this.mSegmentRange[1]);
            if (object != null) {
                this.mExtras.putCharSequence("android.intent.extra.TITLE", (CharSequence)object);
            }
            this.notifyProgress(this.mId, this.mProgress, this.mExtras);
            return;
        }
    }

    public void setProgress(int n, CharSequence charSequence) {
        this.setProgress(n, 100, charSequence);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void start() {
        synchronized (this) {
            this.mState = 1;
            this.notifyStarted(this.mId, null);
            this.notifyProgress(this.mId, this.mProgress, this.mExtras);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int[] startSegment(int n) {
        synchronized (this) {
            int[] arrn = this.mSegmentRange;
            this.mSegmentRange = new int[]{this.mProgress, this.mSegmentRange[1] * n / 100};
            return arrn;
        }
    }
}

