/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.PathEffect;

public class ComposePathEffect
extends PathEffect {
    public ComposePathEffect(PathEffect pathEffect, PathEffect pathEffect2) {
        this.native_instance = ComposePathEffect.nativeCreate(pathEffect.native_instance, pathEffect2.native_instance);
    }

    private static native long nativeCreate(long var0, long var2);
}

