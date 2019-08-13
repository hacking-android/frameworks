/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Camera {
    @UnsupportedAppUsage
    long native_instance;

    public Camera() {
        this.nativeConstructor();
    }

    private native void nativeApplyToCanvas(long var1);

    private native void nativeConstructor();

    private native void nativeDestructor();

    private native void nativeGetMatrix(long var1);

    public void applyToCanvas(Canvas canvas) {
        this.nativeApplyToCanvas(canvas.getNativeCanvasWrapper());
    }

    public native float dotWithNormal(float var1, float var2, float var3);

    protected void finalize() throws Throwable {
        try {
            this.nativeDestructor();
            this.native_instance = 0L;
            return;
        }
        finally {
            super.finalize();
        }
    }

    public native float getLocationX();

    public native float getLocationY();

    public native float getLocationZ();

    public void getMatrix(Matrix matrix) {
        this.nativeGetMatrix(matrix.native_instance);
    }

    public native void restore();

    public native void rotate(float var1, float var2, float var3);

    public native void rotateX(float var1);

    public native void rotateY(float var1);

    public native void rotateZ(float var1);

    public native void save();

    public native void setLocation(float var1, float var2, float var3);

    public native void translate(float var1, float var2, float var3);
}

