/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public abstract class BaseSurfaceHolder
implements SurfaceHolder {
    static final boolean DEBUG = false;
    private static final String TAG = "BaseSurfaceHolder";
    public final ArrayList<SurfaceHolder.Callback> mCallbacks = new ArrayList();
    SurfaceHolder.Callback[] mGottenCallbacks;
    boolean mHaveGottenCallbacks;
    long mLastLockTime = 0L;
    protected int mRequestedFormat = -1;
    int mRequestedHeight = -1;
    int mRequestedType = -1;
    int mRequestedWidth = -1;
    public Surface mSurface = new Surface();
    final Rect mSurfaceFrame = new Rect();
    public final ReentrantLock mSurfaceLock = new ReentrantLock();
    Rect mTmpDirty;
    int mType = -1;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final Canvas internalLockCanvas(Rect var1_1, boolean var2_4) {
        block8 : {
            if (this.mType == 3) throw new SurfaceHolder.BadSurfaceTypeException("Surface type is SURFACE_TYPE_PUSH_BUFFERS");
            this.mSurfaceLock.lock();
            var4_6 = var3_5 = null;
            if (!this.onAllowLockCanvas()) break block8;
            var4_6 = var1_1;
            if (var1_1 == null) {
                if (this.mTmpDirty == null) {
                    this.mTmpDirty = new Rect();
                }
                this.mTmpDirty.set(this.mSurfaceFrame);
                var4_6 = this.mTmpDirty;
            }
            if (!var2_4) ** GOTO lbl15
            try {
                block9 : {
                    var1_1 = this.mSurface.lockHardwareCanvas();
                    break block9;
lbl15: // 1 sources:
                    var1_1 = this.mSurface.lockCanvas((Rect)var4_6);
                }
                var4_6 = var1_1;
            }
            catch (Exception var1_2) {
                Log.e("BaseSurfaceHolder", "Exception locking surface", var1_2);
                var4_6 = var3_5;
            }
        }
        if (var4_6 != null) {
            this.mLastLockTime = SystemClock.uptimeMillis();
            return var4_6;
        }
        var5_7 = SystemClock.uptimeMillis();
        var7_8 = this.mLastLockTime + 100L;
        var9_9 = var5_7;
        if (var7_8 > var5_7) {
            try {
                Thread.sleep(var7_8 - var5_7);
            }
            catch (InterruptedException var1_3) {
                // empty catch block
            }
            var9_9 = SystemClock.uptimeMillis();
        }
        this.mLastLockTime = var9_9;
        this.mSurfaceLock.unlock();
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addCallback(SurfaceHolder.Callback callback) {
        ArrayList<SurfaceHolder.Callback> arrayList = this.mCallbacks;
        synchronized (arrayList) {
            if (!this.mCallbacks.contains(callback)) {
                this.mCallbacks.add(callback);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SurfaceHolder.Callback[] getCallbacks() {
        if (this.mHaveGottenCallbacks) {
            return this.mGottenCallbacks;
        }
        ArrayList<SurfaceHolder.Callback> arrayList = this.mCallbacks;
        synchronized (arrayList) {
            int n = this.mCallbacks.size();
            if (n > 0) {
                if (this.mGottenCallbacks == null || this.mGottenCallbacks.length != n) {
                    this.mGottenCallbacks = new SurfaceHolder.Callback[n];
                }
                this.mCallbacks.toArray(this.mGottenCallbacks);
            } else {
                this.mGottenCallbacks = null;
            }
            this.mHaveGottenCallbacks = true;
            return this.mGottenCallbacks;
        }
    }

    public int getRequestedFormat() {
        return this.mRequestedFormat;
    }

    public int getRequestedHeight() {
        return this.mRequestedHeight;
    }

    public int getRequestedType() {
        return this.mRequestedType;
    }

    public int getRequestedWidth() {
        return this.mRequestedWidth;
    }

    @Override
    public Surface getSurface() {
        return this.mSurface;
    }

    @Override
    public Rect getSurfaceFrame() {
        return this.mSurfaceFrame;
    }

    @Override
    public Canvas lockCanvas() {
        return this.internalLockCanvas(null, false);
    }

    @Override
    public Canvas lockCanvas(Rect rect) {
        return this.internalLockCanvas(rect, false);
    }

    @Override
    public Canvas lockHardwareCanvas() {
        return this.internalLockCanvas(null, true);
    }

    public abstract boolean onAllowLockCanvas();

    public abstract void onRelayoutContainer();

    public abstract void onUpdateSurface();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void removeCallback(SurfaceHolder.Callback callback) {
        ArrayList<SurfaceHolder.Callback> arrayList = this.mCallbacks;
        synchronized (arrayList) {
            this.mCallbacks.remove(callback);
            return;
        }
    }

    @Override
    public void setFixedSize(int n, int n2) {
        if (this.mRequestedWidth != n || this.mRequestedHeight != n2) {
            this.mRequestedWidth = n;
            this.mRequestedHeight = n2;
            this.onRelayoutContainer();
        }
    }

    @Override
    public void setFormat(int n) {
        if (this.mRequestedFormat != n) {
            this.mRequestedFormat = n;
            this.onUpdateSurface();
        }
    }

    @Override
    public void setSizeFromLayout() {
        if (this.mRequestedWidth != -1 || this.mRequestedHeight != -1) {
            this.mRequestedHeight = -1;
            this.mRequestedWidth = -1;
            this.onRelayoutContainer();
        }
    }

    public void setSurfaceFrameSize(int n, int n2) {
        Rect rect = this.mSurfaceFrame;
        rect.top = 0;
        rect.left = 0;
        rect.right = n;
        rect.bottom = n2;
    }

    @Override
    public void setType(int n) {
        if (n == 1 || n == 2) {
            n = 0;
        }
        if ((n == 0 || n == 3) && this.mRequestedType != n) {
            this.mRequestedType = n;
            this.onUpdateSurface();
        }
    }

    public void ungetCallbacks() {
        this.mHaveGottenCallbacks = false;
    }

    @Override
    public void unlockCanvasAndPost(Canvas canvas) {
        this.mSurface.unlockCanvasAndPost(canvas);
        this.mSurfaceLock.unlock();
    }
}

