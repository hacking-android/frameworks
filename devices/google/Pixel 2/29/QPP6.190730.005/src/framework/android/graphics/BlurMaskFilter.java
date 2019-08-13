/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.MaskFilter;

public class BlurMaskFilter
extends MaskFilter {
    public BlurMaskFilter(float f, Blur blur) {
        this.native_instance = BlurMaskFilter.nativeConstructor(f, blur.native_int);
    }

    private static native long nativeConstructor(float var0, int var1);

    public static enum Blur {
        NORMAL(0),
        SOLID(1),
        OUTER(2),
        INNER(3);
        
        final int native_int;

        private Blur(int n2) {
            this.native_int = n2;
        }
    }

}

