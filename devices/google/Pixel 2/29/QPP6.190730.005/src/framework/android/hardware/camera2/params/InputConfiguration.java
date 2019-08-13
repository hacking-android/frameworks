/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;

public final class InputConfiguration {
    private final int mFormat;
    private final int mHeight;
    private final int mWidth;

    public InputConfiguration(int n, int n2, int n3) {
        this.mWidth = n;
        this.mHeight = n2;
        this.mFormat = n3;
    }

    public boolean equals(Object object) {
        if (!(object instanceof InputConfiguration)) {
            return false;
        }
        return ((InputConfiguration)(object = (InputConfiguration)object)).getWidth() == this.mWidth && ((InputConfiguration)object).getHeight() == this.mHeight && ((InputConfiguration)object).getFormat() == this.mFormat;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(new int[]{this.mWidth, this.mHeight, this.mFormat});
    }

    public String toString() {
        return String.format("InputConfiguration(w:%d, h:%d, format:%d)", this.mWidth, this.mHeight, this.mFormat);
    }
}

