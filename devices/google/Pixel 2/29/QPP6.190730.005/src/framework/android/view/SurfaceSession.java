/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;

public final class SurfaceSession {
    @UnsupportedAppUsage
    private long mNativeClient = SurfaceSession.nativeCreate();

    private static native long nativeCreate();

    private static native void nativeDestroy(long var0);

    private static native void nativeKill(long var0);

    protected void finalize() throws Throwable {
        try {
            if (this.mNativeClient != 0L) {
                SurfaceSession.nativeDestroy(this.mNativeClient);
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    @UnsupportedAppUsage
    public void kill() {
        SurfaceSession.nativeKill(this.mNativeClient);
    }
}

