/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.DrawFilter;

public class PaintFlagsDrawFilter
extends DrawFilter {
    public PaintFlagsDrawFilter(int n, int n2) {
        this.mNativeInt = PaintFlagsDrawFilter.nativeConstructor(n, n2);
    }

    private static native long nativeConstructor(int var0, int var1);
}

