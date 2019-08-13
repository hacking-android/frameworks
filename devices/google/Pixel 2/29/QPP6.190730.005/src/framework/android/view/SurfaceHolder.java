/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.Surface;

public interface SurfaceHolder {
    @Deprecated
    public static final int SURFACE_TYPE_GPU = 2;
    @Deprecated
    public static final int SURFACE_TYPE_HARDWARE = 1;
    @Deprecated
    public static final int SURFACE_TYPE_NORMAL = 0;
    @Deprecated
    public static final int SURFACE_TYPE_PUSH_BUFFERS = 3;

    public void addCallback(Callback var1);

    public Surface getSurface();

    public Rect getSurfaceFrame();

    public boolean isCreating();

    public Canvas lockCanvas();

    public Canvas lockCanvas(Rect var1);

    default public Canvas lockHardwareCanvas() {
        throw new IllegalStateException("This SurfaceHolder doesn't support lockHardwareCanvas");
    }

    public void removeCallback(Callback var1);

    public void setFixedSize(int var1, int var2);

    public void setFormat(int var1);

    public void setKeepScreenOn(boolean var1);

    public void setSizeFromLayout();

    @Deprecated
    public void setType(int var1);

    public void unlockCanvasAndPost(Canvas var1);

    public static class BadSurfaceTypeException
    extends RuntimeException {
        public BadSurfaceTypeException() {
        }

        public BadSurfaceTypeException(String string2) {
            super(string2);
        }
    }

    public static interface Callback {
        public void surfaceChanged(SurfaceHolder var1, int var2, int var3, int var4);

        public void surfaceCreated(SurfaceHolder var1);

        public void surfaceDestroyed(SurfaceHolder var1);
    }

    public static interface Callback2
    extends Callback {
        public void surfaceRedrawNeeded(SurfaceHolder var1);

        default public void surfaceRedrawNeededAsync(SurfaceHolder surfaceHolder, Runnable runnable) {
            this.surfaceRedrawNeeded(surfaceHolder);
            runnable.run();
        }
    }

}

