/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import com.android.internal.util.Preconditions;

public final class SizeF {
    private final float mHeight;
    private final float mWidth;

    public SizeF(float f, float f2) {
        this.mWidth = Preconditions.checkArgumentFinite(f, "width");
        this.mHeight = Preconditions.checkArgumentFinite(f2, "height");
    }

    private static NumberFormatException invalidSizeF(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid SizeF: \"");
        stringBuilder.append(string2);
        stringBuilder.append("\"");
        throw new NumberFormatException(stringBuilder.toString());
    }

    public static SizeF parseSizeF(String string2) throws NumberFormatException {
        int n;
        Preconditions.checkNotNull(string2, "string must not be null");
        int n2 = n = string2.indexOf(42);
        if (n < 0) {
            n2 = string2.indexOf(120);
        }
        if (n2 >= 0) {
            try {
                SizeF sizeF = new SizeF(Float.parseFloat(string2.substring(0, n2)), Float.parseFloat(string2.substring(n2 + 1)));
                return sizeF;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw SizeF.invalidSizeF(string2);
            }
            catch (NumberFormatException numberFormatException) {
                throw SizeF.invalidSizeF(string2);
            }
        }
        throw SizeF.invalidSizeF(string2);
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof SizeF) {
            object = (SizeF)object;
            boolean bl2 = bl;
            if (this.mWidth == ((SizeF)object).mWidth) {
                bl2 = bl;
                if (this.mHeight == ((SizeF)object).mHeight) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public float getWidth() {
        return this.mWidth;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.mWidth) ^ Float.floatToIntBits(this.mHeight);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mWidth);
        stringBuilder.append("x");
        stringBuilder.append(this.mHeight);
        return stringBuilder.toString();
    }
}

