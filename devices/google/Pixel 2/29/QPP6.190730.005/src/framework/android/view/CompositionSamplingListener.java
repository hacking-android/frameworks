/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Rect;
import android.os.IBinder;
import android.view._$$Lambda$CompositionSamplingListener$hrbPutjnKRv7VkkiY9eg32N6QA8;
import com.android.internal.util.Preconditions;
import java.util.concurrent.Executor;

public abstract class CompositionSamplingListener {
    private final Executor mExecutor;
    private final long mNativeListener;

    public CompositionSamplingListener(Executor executor) {
        this.mExecutor = executor;
        this.mNativeListener = CompositionSamplingListener.nativeCreate(this);
    }

    private static void dispatchOnSampleCollected(CompositionSamplingListener compositionSamplingListener, float f) {
        compositionSamplingListener.mExecutor.execute(new _$$Lambda$CompositionSamplingListener$hrbPutjnKRv7VkkiY9eg32N6QA8(compositionSamplingListener, f));
    }

    static /* synthetic */ void lambda$dispatchOnSampleCollected$0(CompositionSamplingListener compositionSamplingListener, float f) {
        compositionSamplingListener.onSampleCollected(f);
    }

    private static native long nativeCreate(CompositionSamplingListener var0);

    private static native void nativeDestroy(long var0);

    private static native void nativeRegister(long var0, IBinder var2, int var3, int var4, int var5, int var6);

    private static native void nativeUnregister(long var0);

    public static void register(CompositionSamplingListener compositionSamplingListener, int n, IBinder iBinder, Rect rect) {
        boolean bl = n == 0;
        Preconditions.checkArgument(bl, "default display only for now");
        CompositionSamplingListener.nativeRegister(compositionSamplingListener.mNativeListener, iBinder, rect.left, rect.top, rect.right, rect.bottom);
    }

    public static void unregister(CompositionSamplingListener compositionSamplingListener) {
        CompositionSamplingListener.nativeUnregister(compositionSamplingListener.mNativeListener);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mNativeListener != 0L) {
                CompositionSamplingListener.unregister(this);
                CompositionSamplingListener.nativeDestroy(this.mNativeListener);
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public abstract void onSampleCollected(float var1);
}

