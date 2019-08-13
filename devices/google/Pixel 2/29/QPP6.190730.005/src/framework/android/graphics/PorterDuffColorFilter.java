/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;

public class PorterDuffColorFilter
extends ColorFilter {
    private int mColor;
    private PorterDuff.Mode mMode;

    public PorterDuffColorFilter(int n, PorterDuff.Mode mode) {
        this.mColor = n;
        this.mMode = mode;
    }

    private static native long native_CreateBlendModeFilter(int var0, int var1);

    @Override
    long createNativeInstance() {
        return PorterDuffColorFilter.native_CreateBlendModeFilter(this.mColor, this.mMode.nativeInt);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (PorterDuffColorFilter)object;
            if (this.mColor != ((PorterDuffColorFilter)object).mColor || this.mMode.nativeInt != object.mMode.nativeInt) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @UnsupportedAppUsage
    public int getColor() {
        return this.mColor;
    }

    @UnsupportedAppUsage
    public PorterDuff.Mode getMode() {
        return this.mMode;
    }

    public int hashCode() {
        return this.mMode.hashCode() * 31 + this.mColor;
    }
}

