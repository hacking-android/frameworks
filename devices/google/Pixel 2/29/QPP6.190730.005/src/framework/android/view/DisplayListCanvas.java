/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.graphics.BaseRecordingCanvas;
import android.graphics.CanvasProperty;
import android.graphics.Paint;

public abstract class DisplayListCanvas
extends BaseRecordingCanvas {
    protected DisplayListCanvas(long l) {
        super(l);
    }

    @UnsupportedAppUsage
    public abstract void drawCircle(CanvasProperty<Float> var1, CanvasProperty<Float> var2, CanvasProperty<Float> var3, CanvasProperty<Paint> var4);

    @UnsupportedAppUsage
    public abstract void drawRoundRect(CanvasProperty<Float> var1, CanvasProperty<Float> var2, CanvasProperty<Float> var3, CanvasProperty<Float> var4, CanvasProperty<Float> var5, CanvasProperty<Float> var6, CanvasProperty<Paint> var7);
}

