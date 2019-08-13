/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.IVirtualDisplayCallback;
import android.view.Display;
import android.view.Surface;

public final class VirtualDisplay {
    private final Display mDisplay;
    private final DisplayManagerGlobal mGlobal;
    private Surface mSurface;
    private IVirtualDisplayCallback mToken;

    VirtualDisplay(DisplayManagerGlobal displayManagerGlobal, Display display, IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) {
        this.mGlobal = displayManagerGlobal;
        this.mDisplay = display;
        this.mToken = iVirtualDisplayCallback;
        this.mSurface = surface;
    }

    public Display getDisplay() {
        return this.mDisplay;
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    public void release() {
        IVirtualDisplayCallback iVirtualDisplayCallback = this.mToken;
        if (iVirtualDisplayCallback != null) {
            this.mGlobal.releaseVirtualDisplay(iVirtualDisplayCallback);
            this.mToken = null;
        }
    }

    public void resize(int n, int n2, int n3) {
        this.mGlobal.resizeVirtualDisplay(this.mToken, n, n2, n3);
    }

    public void setDisplayState(boolean bl) {
        IVirtualDisplayCallback iVirtualDisplayCallback = this.mToken;
        if (iVirtualDisplayCallback != null) {
            this.mGlobal.setVirtualDisplayState(iVirtualDisplayCallback, bl);
        }
    }

    public void setSurface(Surface surface) {
        if (this.mSurface != surface) {
            this.mGlobal.setVirtualDisplaySurface(this.mToken, surface);
            this.mSurface = surface;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VirtualDisplay{display=");
        stringBuilder.append(this.mDisplay);
        stringBuilder.append(", token=");
        stringBuilder.append(this.mToken);
        stringBuilder.append(", surface=");
        stringBuilder.append(this.mSurface);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static abstract class Callback {
        public void onPaused() {
        }

        public void onResumed() {
        }

        public void onStopped() {
        }
    }

}

