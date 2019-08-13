/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import com.android.internal.util.Preconditions;

public final class Size {
    private final int mHeight;
    private final int mWidth;

    public Size(int n, int n2) {
        this.mWidth = n;
        this.mHeight = n2;
    }

    private static NumberFormatException invalidSize(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Size: \"");
        stringBuilder.append(string2);
        stringBuilder.append("\"");
        throw new NumberFormatException(stringBuilder.toString());
    }

    public static Size parseSize(String string2) throws NumberFormatException {
        int n;
        Preconditions.checkNotNull(string2, "string must not be null");
        int n2 = n = string2.indexOf(42);
        if (n < 0) {
            n2 = string2.indexOf(120);
        }
        if (n2 >= 0) {
            try {
                Size size = new Size(Integer.parseInt(string2.substring(0, n2)), Integer.parseInt(string2.substring(n2 + 1)));
                return size;
            }
            catch (NumberFormatException numberFormatException) {
                throw Size.invalidSize(string2);
            }
        }
        throw Size.invalidSize(string2);
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof Size) {
            object = (Size)object;
            boolean bl2 = bl;
            if (this.mWidth == ((Size)object).mWidth) {
                bl2 = bl;
                if (this.mHeight == ((Size)object).mHeight) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int hashCode() {
        int n = this.mHeight;
        int n2 = this.mWidth;
        return n ^ (n2 >>> 16 | n2 << 16);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mWidth);
        stringBuilder.append("x");
        stringBuilder.append(this.mHeight);
        return stringBuilder.toString();
    }
}

