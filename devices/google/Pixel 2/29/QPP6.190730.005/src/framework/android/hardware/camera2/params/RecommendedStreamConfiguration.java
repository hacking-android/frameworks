/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.utils.HashCodeHelpers;

public final class RecommendedStreamConfiguration
extends StreamConfiguration {
    private final int mUsecaseBitmap;

    public RecommendedStreamConfiguration(int n, int n2, int n3, boolean bl, int n4) {
        super(n, n2, n3, bl);
        this.mUsecaseBitmap = n4;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof RecommendedStreamConfiguration) {
            object = (RecommendedStreamConfiguration)object;
            boolean bl2 = bl;
            if (this.mFormat == ((RecommendedStreamConfiguration)object).mFormat) {
                bl2 = bl;
                if (this.mWidth == ((RecommendedStreamConfiguration)object).mWidth) {
                    bl2 = bl;
                    if (this.mHeight == ((RecommendedStreamConfiguration)object).mHeight) {
                        bl2 = bl;
                        if (this.mUsecaseBitmap == ((RecommendedStreamConfiguration)object).mUsecaseBitmap) {
                            bl2 = bl;
                            if (this.mInput == ((RecommendedStreamConfiguration)object).mInput) {
                                bl2 = true;
                            }
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public int getUsecaseBitmap() {
        return this.mUsecaseBitmap;
    }

    @Override
    public int hashCode() {
        return HashCodeHelpers.hashCode(new int[]{this.mFormat, this.mWidth, this.mHeight, this.mInput ? 1 : 0, this.mUsecaseBitmap});
    }
}

