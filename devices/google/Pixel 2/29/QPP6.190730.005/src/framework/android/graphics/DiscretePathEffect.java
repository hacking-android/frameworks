/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.PathEffect;

public class DiscretePathEffect
extends PathEffect {
    public DiscretePathEffect(float f, float f2) {
        this.native_instance = DiscretePathEffect.nativeCreate(f, f2);
    }

    private static native long nativeCreate(float var0, float var1);
}

