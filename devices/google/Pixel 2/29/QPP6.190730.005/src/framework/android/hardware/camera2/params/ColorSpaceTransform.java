/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import android.util.Rational;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

public final class ColorSpaceTransform {
    private static final int COLUMNS = 3;
    private static final int COUNT = 9;
    private static final int COUNT_INT = 18;
    private static final int OFFSET_DENOMINATOR = 1;
    private static final int OFFSET_NUMERATOR = 0;
    private static final int RATIONAL_SIZE = 2;
    private static final int ROWS = 3;
    private final int[] mElements;

    public ColorSpaceTransform(int[] arrn) {
        Preconditions.checkNotNull(arrn, "elements must not be null");
        if (arrn.length == 18) {
            for (int i = 0; i < arrn.length; ++i) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("element ");
                stringBuilder.append(i);
                stringBuilder.append(" must not be null");
                Preconditions.checkNotNull(arrn, stringBuilder.toString());
            }
            this.mElements = Arrays.copyOf(arrn, arrn.length);
            return;
        }
        throw new IllegalArgumentException("elements must be 18 length");
    }

    public ColorSpaceTransform(Rational[] arrrational) {
        Preconditions.checkNotNull(arrrational, "elements must not be null");
        if (arrrational.length == 9) {
            this.mElements = new int[18];
            for (int i = 0; i < arrrational.length; ++i) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("element[");
                stringBuilder.append(i);
                stringBuilder.append("] must not be null");
                Preconditions.checkNotNull(arrrational, stringBuilder.toString());
                this.mElements[i * 2 + 0] = arrrational[i].getNumerator();
                this.mElements[i * 2 + 1] = arrrational[i].getDenominator();
            }
            return;
        }
        throw new IllegalArgumentException("elements must be 9 length");
    }

    private String toShortString() {
        StringBuilder stringBuilder = new StringBuilder("(");
        int n = 0;
        for (int i = 0; i < 3; ++i) {
            stringBuilder.append("[");
            int n2 = 0;
            while (n2 < 3) {
                int[] arrn = this.mElements;
                int n3 = arrn[n + 0];
                int n4 = arrn[n + 1];
                stringBuilder.append(n3);
                stringBuilder.append("/");
                stringBuilder.append(n4);
                if (n2 < 2) {
                    stringBuilder.append(", ");
                }
                ++n2;
                n += 2;
            }
            stringBuilder.append("]");
            if (i >= 2) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void copyElements(int[] arrn, int n) {
        Preconditions.checkArgumentNonnegative(n, "offset must not be negative");
        Preconditions.checkNotNull(arrn, "destination must not be null");
        if (arrn.length - n >= 18) {
            for (int i = 0; i < 18; ++i) {
                arrn[i + n] = this.mElements[i];
            }
            return;
        }
        throw new ArrayIndexOutOfBoundsException("destination too small to fit elements");
    }

    public void copyElements(Rational[] arrrational, int n) {
        Preconditions.checkArgumentNonnegative(n, "offset must not be negative");
        Preconditions.checkNotNull(arrrational, "destination must not be null");
        if (arrrational.length - n >= 9) {
            int n2 = 0;
            int n3 = 0;
            while (n2 < 9) {
                int[] arrn = this.mElements;
                arrrational[n2 + n] = new Rational(arrn[n3 + 0], arrn[n3 + 1]);
                ++n2;
                n3 += 2;
            }
            return;
        }
        throw new ArrayIndexOutOfBoundsException("destination too small to fit elements");
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof ColorSpaceTransform) {
            object = (ColorSpaceTransform)object;
            int n = 0;
            int n2 = 0;
            while (n < 9) {
                int[] arrn = this.mElements;
                int n3 = arrn[n2 + 0];
                int n4 = arrn[n2 + 1];
                arrn = ((ColorSpaceTransform)object).mElements;
                int n5 = arrn[n2 + 0];
                int n6 = arrn[n2 + 1];
                if (!new Rational(n3, n4).equals((Object)new Rational(n5, n6))) {
                    return false;
                }
                ++n;
                n2 += 2;
            }
            return true;
        }
        return false;
    }

    public Rational getElement(int n, int n2) {
        if (n >= 0 && n < 3) {
            if (n2 >= 0 && n2 < 3) {
                int[] arrn = this.mElements;
                return new Rational(arrn[(n2 * 3 + n) * 2 + 0], arrn[(n2 * 3 + n) * 2 + 1]);
            }
            throw new IllegalArgumentException("row out of range");
        }
        throw new IllegalArgumentException("column out of range");
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(this.mElements);
    }

    public String toString() {
        return String.format("ColorSpaceTransform%s", this.toShortString());
    }
}

