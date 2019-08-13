/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.graphics.PointF;
import android.hardware.camera2.utils.HashCodeHelpers;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

public final class TonemapCurve {
    public static final int CHANNEL_BLUE = 2;
    public static final int CHANNEL_GREEN = 1;
    public static final int CHANNEL_RED = 0;
    public static final float LEVEL_BLACK = 0.0f;
    public static final float LEVEL_WHITE = 1.0f;
    private static final int MIN_CURVE_LENGTH = 4;
    private static final int OFFSET_POINT_IN = 0;
    private static final int OFFSET_POINT_OUT = 1;
    public static final int POINT_SIZE = 2;
    private static final int TONEMAP_MIN_CURVE_POINTS = 2;
    private final float[] mBlue;
    private final float[] mGreen;
    private boolean mHashCalculated = false;
    private int mHashCode;
    private final float[] mRed;

    public TonemapCurve(float[] arrf, float[] arrf2, float[] arrf3) {
        Preconditions.checkNotNull(arrf, "red must not be null");
        Preconditions.checkNotNull(arrf2, "green must not be null");
        Preconditions.checkNotNull(arrf3, "blue must not be null");
        TonemapCurve.checkArgumentArrayLengthDivisibleBy(arrf, 2, "red");
        TonemapCurve.checkArgumentArrayLengthDivisibleBy(arrf2, 2, "green");
        TonemapCurve.checkArgumentArrayLengthDivisibleBy(arrf3, 2, "blue");
        TonemapCurve.checkArgumentArrayLengthNoLessThan(arrf, 4, "red");
        TonemapCurve.checkArgumentArrayLengthNoLessThan(arrf2, 4, "green");
        TonemapCurve.checkArgumentArrayLengthNoLessThan(arrf3, 4, "blue");
        Preconditions.checkArrayElementsInRange(arrf, 0.0f, 1.0f, "red");
        Preconditions.checkArrayElementsInRange(arrf2, 0.0f, 1.0f, "green");
        Preconditions.checkArrayElementsInRange(arrf3, 0.0f, 1.0f, "blue");
        this.mRed = Arrays.copyOf(arrf, arrf.length);
        this.mGreen = Arrays.copyOf(arrf2, arrf2.length);
        this.mBlue = Arrays.copyOf(arrf3, arrf3.length);
    }

    private static void checkArgumentArrayLengthDivisibleBy(float[] object, int n, String string2) {
        if (((float[])object).length % n == 0) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" size must be divisible by ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private static void checkArgumentArrayLengthNoLessThan(float[] object, int n, String string2) {
        if (((float[])object).length >= n) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" size must be at least ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private static int checkArgumentColorChannel(int n) {
        if (n != 0 && n != 1 && n != 2) {
            throw new IllegalArgumentException("colorChannel out of range");
        }
        return n;
    }

    private String curveToString(int n) {
        TonemapCurve.checkArgumentColorChannel(n);
        StringBuilder stringBuilder = new StringBuilder("[");
        float[] arrf = this.getCurve(n);
        int n2 = arrf.length / 2;
        int n3 = 0;
        n = 0;
        while (n3 < n2) {
            stringBuilder.append("(");
            stringBuilder.append(arrf[n]);
            stringBuilder.append(", ");
            stringBuilder.append(arrf[n + 1]);
            stringBuilder.append("), ");
            ++n3;
            n += 2;
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private float[] getCurve(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    return this.mBlue;
                }
                throw new AssertionError((Object)"colorChannel out of range");
            }
            return this.mGreen;
        }
        return this.mRed;
    }

    public void copyColorCurve(int n, float[] arrf, int n2) {
        Preconditions.checkArgumentNonnegative(n2, "offset must not be negative");
        Preconditions.checkNotNull(arrf, "destination must not be null");
        if (arrf.length + n2 >= this.getPointCount(n) * 2) {
            float[] arrf2 = this.getCurve(n);
            System.arraycopy(arrf2, 0, arrf, n2, arrf2.length);
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
        if (object instanceof TonemapCurve) {
            object = (TonemapCurve)object;
            if (Arrays.equals(this.mRed, ((TonemapCurve)object).mRed) && Arrays.equals(this.mGreen, ((TonemapCurve)object).mGreen) && Arrays.equals(this.mBlue, ((TonemapCurve)object).mBlue)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public PointF getPoint(int n, int n2) {
        TonemapCurve.checkArgumentColorChannel(n);
        if (n2 >= 0 && n2 < this.getPointCount(n)) {
            float[] arrf = this.getCurve(n);
            return new PointF(arrf[n2 * 2 + 0], arrf[n2 * 2 + 1]);
        }
        throw new IllegalArgumentException("index out of range");
    }

    public int getPointCount(int n) {
        TonemapCurve.checkArgumentColorChannel(n);
        return this.getCurve(n).length / 2;
    }

    public int hashCode() {
        if (this.mHashCalculated) {
            return this.mHashCode;
        }
        this.mHashCode = HashCodeHelpers.hashCodeGeneric(this.mRed, this.mGreen, this.mBlue);
        this.mHashCalculated = true;
        return this.mHashCode;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("TonemapCurve{");
        stringBuilder.append("R:");
        stringBuilder.append(this.curveToString(0));
        stringBuilder.append(", G:");
        stringBuilder.append(this.curveToString(1));
        stringBuilder.append(", B:");
        stringBuilder.append(this.curveToString(2));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

