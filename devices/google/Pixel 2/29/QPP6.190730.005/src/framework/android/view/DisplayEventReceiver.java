/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  dalvik.system.CloseGuard
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.CloseGuard;
import java.lang.ref.WeakReference;

public abstract class DisplayEventReceiver {
    private static final String TAG = "DisplayEventReceiver";
    public static final int VSYNC_SOURCE_APP = 0;
    public static final int VSYNC_SOURCE_SURFACE_FLINGER = 1;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private MessageQueue mMessageQueue;
    @UnsupportedAppUsage
    private long mReceiverPtr;

    @UnsupportedAppUsage
    public DisplayEventReceiver(Looper looper) {
        this(looper, 0);
    }

    public DisplayEventReceiver(Looper looper, int n) {
        if (looper != null) {
            this.mMessageQueue = looper.getQueue();
            this.mReceiverPtr = DisplayEventReceiver.nativeInit(new WeakReference<DisplayEventReceiver>(this), this.mMessageQueue, n);
            this.mCloseGuard.open("dispose");
            return;
        }
        throw new IllegalArgumentException("looper must not be null");
    }

    private void dispatchConfigChanged(long l, long l2, int n) {
        this.onConfigChanged(l, l2, n);
    }

    @UnsupportedAppUsage
    private void dispatchHotplug(long l, long l2, boolean bl) {
        this.onHotplug(l, l2, bl);
    }

    @UnsupportedAppUsage
    private void dispatchVsync(long l, long l2, int n) {
        this.onVsync(l, l2, n);
    }

    private void dispose(boolean bl) {
        long l;
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            if (bl) {
                closeGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if ((l = this.mReceiverPtr) != 0L) {
            DisplayEventReceiver.nativeDispose(l);
            this.mReceiverPtr = 0L;
        }
        this.mMessageQueue = null;
    }

    private static native void nativeDispose(long var0);

    private static native long nativeInit(WeakReference<DisplayEventReceiver> var0, MessageQueue var1, int var2);

    @FastNative
    private static native void nativeScheduleVsync(long var0);

    public void dispose() {
        this.dispose(false);
    }

    protected void finalize() throws Throwable {
        try {
            this.dispose(true);
            return;
        }
        finally {
            super.finalize();
        }
    }

    public void onConfigChanged(long l, long l2, int n) {
    }

    @UnsupportedAppUsage
    public void onHotplug(long l, long l2, boolean bl) {
    }

    @UnsupportedAppUsage
    public void onVsync(long l, long l2, int n) {
    }

    @UnsupportedAppUsage
    public void scheduleVsync() {
        long l = this.mReceiverPtr;
        if (l == 0L) {
            Log.w("DisplayEventReceiver", "Attempted to schedule a vertical sync pulse but the display event receiver has already been disposed.");
        } else {
            DisplayEventReceiver.nativeScheduleVsync(l);
        }
    }
}

