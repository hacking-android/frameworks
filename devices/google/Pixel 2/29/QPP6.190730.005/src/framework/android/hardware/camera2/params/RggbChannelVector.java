/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import com.android.internal.util.Preconditions;

public final class RggbChannelVector {
    public static final int BLUE = 3;
    public static final int COUNT = 4;
    public static final int GREEN_EVEN = 1;
    public static final int GREEN_ODD = 2;
    public static final int RED = 0;
    private final float mBlue;
    private final float mGreenEven;
    private final float mGreenOdd;
    private final float mRed;

    public RggbChannelVector(float f, float f2, float f3, float f4) {
        this.mRed = Preconditions.checkArgumentFinite(f, "red");
        this.mGreenEven = Preconditions.checkArgumentFinite(f2, "greenEven");
        this.mGreenOdd = Preconditions.checkArgumentFinite(f3, "greenOdd");
        this.mBlue = Preconditions.checkArgumentFinite(f4, "blue");
    }

    private String toShortString() {
        return String.format("{R:%f, G_even:%f, G_odd:%f, B:%f}", Float.valueOf(this.mRed), Float.valueOf(this.mGreenEven), Float.valueOf(this.mGreenOdd), Float.valueOf(this.mBlue));
    }

    public void copyTo(float[] arrf, int n) {
        Preconditions.checkNotNull(arrf, "destination must not be null");
        if (arrf.length - n >= 4) {
            arrf[n + 0] = this.mRed;
            arrf[n + 1] = this.mGreenEven;
            arrf[n + 2] = this.mGreenOdd;
            arrf[n + 3] = this.mBlue;
            return;
        }
        throw new ArrayIndexOutOfBoundsException("destination too small to fit elements");
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof RggbChannelVector) {
            object = (RggbChannelVector)object;
            boolean bl2 = bl;
            if (this.mRed == ((RggbChannelVector)object).mRed) {
                bl2 = bl;
                if (this.mGreenEven == ((RggbChannelVector)object).mGreenEven) {
                    bl2 = bl;
                    if (this.mGreenOdd == ((RggbChannelVector)object).mGreenOdd) {
                        bl2 = bl;
                        if (this.mBlue == ((RggbChannelVector)object).mBlue) {
                            bl2 = true;
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public float getBlue() {
        return this.mBlue;
    }

    public float getComponent(int n) {
        if (n >= 0 && n < 4) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n == 3) {
                            return this.mBlue;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unhandled case ");
                        stringBuilder.append(n);
                        throw new AssertionError((Object)stringBuilder.toString());
                    }
                    return this.mGreenOdd;
                }
                return this.mGreenEven;
            }
            return this.mRed;
        }
        throw new IllegalArgumentException("Color channel out of range");
    }

    public float getGreenEven() {
        return this.mGreenEven;
    }

    public float getGreenOdd() {
        return this.mGreenOdd;
    }

    public final float getRed() {
        return this.mRed;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.mRed) ^ Float.floatToIntBits(this.mGreenEven) ^ Float.floatToIntBits(this.mGreenOdd) ^ Float.floatToIntBits(this.mBlue);
    }

    public String toString() {
        return String.format("RggbChannelVector%s", this.toShortString());
    }
}

