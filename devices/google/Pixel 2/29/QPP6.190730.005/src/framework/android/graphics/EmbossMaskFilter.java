/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.MaskFilter;

public class EmbossMaskFilter
extends MaskFilter {
    @Deprecated
    public EmbossMaskFilter(float[] arrf, float f, float f2, float f3) {
        if (arrf.length >= 3) {
            this.native_instance = EmbossMaskFilter.nativeConstructor(arrf, f, f2, f3);
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    private static native long nativeConstructor(float[] var0, float var1, float var2, float var3);
}

