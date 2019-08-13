/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.NativeAllocatorTag;
import android.graphics.SurfaceTexture;
import android.media.MediaRecorder;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;

public class GLEnvironment {
    private int glEnvId;
    private boolean mManageContext = true;

    static {
        System.loadLibrary("filterfw");
    }

    public GLEnvironment() {
        this.nativeAllocate();
    }

    private GLEnvironment(NativeAllocatorTag nativeAllocatorTag) {
    }

    public static boolean isAnyContextActive() {
        return GLEnvironment.nativeIsAnyContextActive();
    }

    private native boolean nativeActivate();

    private native boolean nativeActivateSurfaceId(int var1);

    private native int nativeAddSurface(Surface var1);

    private native int nativeAddSurfaceFromMediaRecorder(MediaRecorder var1);

    private native int nativeAddSurfaceWidthHeight(Surface var1, int var2, int var3);

    private native boolean nativeAllocate();

    private native boolean nativeDeactivate();

    private native boolean nativeDeallocate();

    private native boolean nativeDisconnectSurfaceMediaSource(MediaRecorder var1);

    private native boolean nativeInitWithCurrentContext();

    private native boolean nativeInitWithNewContext();

    private native boolean nativeIsActive();

    private static native boolean nativeIsAnyContextActive();

    private native boolean nativeIsContextActive();

    private native boolean nativeRemoveSurfaceId(int var1);

    private native boolean nativeSetSurfaceTimestamp(long var1);

    private native boolean nativeSwapBuffers();

    @UnsupportedAppUsage
    public void activate() {
        if (Looper.myLooper() != null && Looper.myLooper().equals(Looper.getMainLooper())) {
            Log.e("FilterFramework", "Activating GL context in UI thread!");
        }
        if (this.mManageContext && !this.nativeActivate()) {
            throw new RuntimeException("Could not activate GLEnvironment!");
        }
    }

    @UnsupportedAppUsage
    public void activateSurfaceWithId(int n) {
        if (this.nativeActivateSurfaceId(n)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not activate surface ");
        stringBuilder.append(n);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public void deactivate() {
        if (this.mManageContext && !this.nativeDeactivate()) {
            throw new RuntimeException("Could not deactivate GLEnvironment!");
        }
    }

    protected void finalize() throws Throwable {
        this.tearDown();
    }

    public void initWithCurrentContext() {
        this.mManageContext = false;
        if (this.nativeInitWithCurrentContext()) {
            return;
        }
        throw new RuntimeException("Could not initialize GLEnvironment with current context!");
    }

    public void initWithNewContext() {
        this.mManageContext = true;
        if (this.nativeInitWithNewContext()) {
            return;
        }
        throw new RuntimeException("Could not initialize GLEnvironment with new context!");
    }

    @UnsupportedAppUsage
    public boolean isActive() {
        return this.nativeIsActive();
    }

    public boolean isContextActive() {
        return this.nativeIsContextActive();
    }

    public int registerSurface(Surface surface) {
        int n = this.nativeAddSurface(surface);
        if (n >= 0) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error registering surface ");
        stringBuilder.append(surface);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public int registerSurfaceFromMediaRecorder(MediaRecorder mediaRecorder) {
        int n = this.nativeAddSurfaceFromMediaRecorder(mediaRecorder);
        if (n >= 0) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error registering surface from MediaRecorder");
        stringBuilder.append(mediaRecorder);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    public int registerSurfaceTexture(SurfaceTexture surfaceTexture, int n, int n2) {
        Object object = new Surface(surfaceTexture);
        n = this.nativeAddSurfaceWidthHeight((Surface)object, n, n2);
        ((Surface)object).release();
        if (n >= 0) {
            return n;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Error registering surfaceTexture ");
        ((StringBuilder)object).append(surfaceTexture);
        ((StringBuilder)object).append("!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public void setSurfaceTimestamp(long l) {
        if (this.nativeSetSurfaceTimestamp(l)) {
            return;
        }
        throw new RuntimeException("Could not set timestamp for current surface!");
    }

    @UnsupportedAppUsage
    public void swapBuffers() {
        if (this.nativeSwapBuffers()) {
            return;
        }
        throw new RuntimeException("Error swapping EGL buffers!");
    }

    public void tearDown() {
        synchronized (this) {
            if (this.glEnvId != -1) {
                this.nativeDeallocate();
                this.glEnvId = -1;
            }
            return;
        }
    }

    @UnsupportedAppUsage
    public void unregisterSurfaceId(int n) {
        if (this.nativeRemoveSurfaceId(n)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not unregister surface ");
        stringBuilder.append(n);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }
}

