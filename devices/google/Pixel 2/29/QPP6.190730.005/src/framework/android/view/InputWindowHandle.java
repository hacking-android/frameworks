/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Region;
import android.os.IBinder;
import android.view.IWindow;
import android.view.InputApplicationHandle;
import android.view.SurfaceControl;
import java.lang.ref.WeakReference;

public final class InputWindowHandle {
    public boolean canReceiveKeys;
    public final IWindow clientWindow;
    public long dispatchingTimeoutNanos;
    public int displayId;
    public int frameBottom;
    public int frameLeft;
    public int frameRight;
    public int frameTop;
    public boolean hasFocus;
    public boolean hasWallpaper;
    public final InputApplicationHandle inputApplicationHandle;
    public int inputFeatures;
    public int layer;
    public int layoutParamsFlags;
    public int layoutParamsType;
    public String name;
    public int ownerPid;
    public int ownerUid;
    public boolean paused;
    public int portalToDisplayId = -1;
    private long ptr;
    public boolean replaceTouchableRegionWithCrop;
    public float scaleFactor;
    public int surfaceInset;
    public IBinder token;
    public final Region touchableRegion = new Region();
    public WeakReference<IBinder> touchableRegionCropHandle = new WeakReference<Object>(null);
    public boolean visible;

    public InputWindowHandle(InputApplicationHandle inputApplicationHandle, IWindow iWindow, int n) {
        this.inputApplicationHandle = inputApplicationHandle;
        this.clientWindow = iWindow;
        this.displayId = n;
    }

    private native void nativeDispose();

    protected void finalize() throws Throwable {
        try {
            this.nativeDispose();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public void replaceTouchableRegionWithCrop(SurfaceControl surfaceControl) {
        this.setTouchableRegionCrop(surfaceControl);
        this.replaceTouchableRegionWithCrop = true;
    }

    public void setTouchableRegionCrop(SurfaceControl surfaceControl) {
        if (surfaceControl != null) {
            this.touchableRegionCropHandle = new WeakReference<IBinder>(surfaceControl.getHandle());
        }
    }

    public String toString() {
        CharSequence charSequence = this.name;
        if (charSequence == null) {
            charSequence = "";
        }
        charSequence = new StringBuilder((String)charSequence);
        ((StringBuilder)charSequence).append(", layer=");
        ((StringBuilder)charSequence).append(this.layer);
        ((StringBuilder)charSequence).append(", frame=[");
        ((StringBuilder)charSequence).append(this.frameLeft);
        ((StringBuilder)charSequence).append(",");
        ((StringBuilder)charSequence).append(this.frameTop);
        ((StringBuilder)charSequence).append(",");
        ((StringBuilder)charSequence).append(this.frameRight);
        ((StringBuilder)charSequence).append(",");
        ((StringBuilder)charSequence).append(this.frameBottom);
        ((StringBuilder)charSequence).append("]");
        ((StringBuilder)charSequence).append(", touchableRegion=");
        ((StringBuilder)charSequence).append(this.touchableRegion);
        ((StringBuilder)charSequence).append(", visible=");
        ((StringBuilder)charSequence).append(this.visible);
        return ((StringBuilder)charSequence).toString();
    }
}

