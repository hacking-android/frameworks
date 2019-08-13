/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.PathEffect;

public class CornerPathEffect
extends PathEffect {
    public CornerPathEffect(float f) {
        this.native_instance = CornerPathEffect.nativeCreate(f);
    }

    private static native long nativeCreate(float var0);
}

