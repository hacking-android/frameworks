/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.BlendMode;
import android.graphics.ColorFilter;
import android.graphics.Xfermode;

public final class BlendModeColorFilter
extends ColorFilter {
    final int mColor;
    private final BlendMode mMode;

    public BlendModeColorFilter(int n, BlendMode blendMode) {
        this.mColor = n;
        this.mMode = blendMode;
    }

    private static native long native_CreateBlendModeFilter(int var0, int var1);

    @Override
    long createNativeInstance() {
        return BlendModeColorFilter.native_CreateBlendModeFilter(this.mColor, this.mMode.getXfermode().porterDuffMode);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            if (((BlendModeColorFilter)object).mMode != this.mMode) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getColor() {
        return this.mColor;
    }

    public BlendMode getMode() {
        return this.mMode;
    }

    public int hashCode() {
        return this.mMode.hashCode() * 31 + this.mColor;
    }
}

