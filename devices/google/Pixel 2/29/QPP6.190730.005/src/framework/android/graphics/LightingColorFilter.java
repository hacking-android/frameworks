/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.ColorFilter;

public class LightingColorFilter
extends ColorFilter {
    private int mAdd;
    private int mMul;

    public LightingColorFilter(int n, int n2) {
        this.mMul = n;
        this.mAdd = n2;
    }

    private static native long native_CreateLightingFilter(int var0, int var1);

    @Override
    long createNativeInstance() {
        return LightingColorFilter.native_CreateLightingFilter(this.mMul, this.mAdd);
    }

    public int getColorAdd() {
        return this.mAdd;
    }

    public int getColorMultiply() {
        return this.mMul;
    }

    @UnsupportedAppUsage
    public void setColorAdd(int n) {
        if (this.mAdd != n) {
            this.mAdd = n;
            this.discardNativeInstance();
        }
    }

    @UnsupportedAppUsage
    public void setColorMultiply(int n) {
        if (this.mMul != n) {
            this.mMul = n;
            this.discardNativeInstance();
        }
    }
}

