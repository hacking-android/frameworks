/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.PathEffect;

public class DashPathEffect
extends PathEffect {
    public DashPathEffect(float[] arrf, float f) {
        if (arrf.length >= 2) {
            this.native_instance = DashPathEffect.nativeCreate(arrf, f);
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    private static native long nativeCreate(float[] var0, float var1);
}

