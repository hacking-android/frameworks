/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Paint;
import com.android.internal.util.VirtualRefBasePtr;

public final class CanvasProperty<T> {
    private VirtualRefBasePtr mProperty;

    private CanvasProperty(long l) {
        this.mProperty = new VirtualRefBasePtr(l);
    }

    @UnsupportedAppUsage
    public static CanvasProperty<Float> createFloat(float f) {
        return new CanvasProperty<Float>(CanvasProperty.nCreateFloat(f));
    }

    @UnsupportedAppUsage
    public static CanvasProperty<Paint> createPaint(Paint paint) {
        return new CanvasProperty<Paint>(CanvasProperty.nCreatePaint(paint.getNativeInstance()));
    }

    private static native long nCreateFloat(float var0);

    private static native long nCreatePaint(long var0);

    public long getNativeContainer() {
        return this.mProperty.get();
    }
}

