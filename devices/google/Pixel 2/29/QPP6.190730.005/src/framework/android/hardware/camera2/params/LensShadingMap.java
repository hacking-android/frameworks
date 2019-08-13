/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.hardware.camera2.params.RggbChannelVector;
import android.hardware.camera2.utils.HashCodeHelpers;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

public final class LensShadingMap {
    public static final float MINIMUM_GAIN_FACTOR = 1.0f;
    private final int mColumns;
    private final float[] mElements;
    private final int mRows;

    public LensShadingMap(float[] arrf, int n, int n2) {
        this.mRows = Preconditions.checkArgumentPositive(n, "rows must be positive");
        this.mColumns = Preconditions.checkArgumentPositive(n2, "columns must be positive");
        this.mElements = Preconditions.checkNotNull(arrf, "elements must not be null");
        if (arrf.length == this.getGainFactorCount()) {
            Preconditions.checkArrayElementsInRange(arrf, 1.0f, Float.MAX_VALUE, "elements");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("elements must be ");
        stringBuilder.append(this.getGainFactorCount());
        stringBuilder.append(" length, received ");
        stringBuilder.append(arrf.length);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void copyGainFactors(float[] arrf, int n) {
        Preconditions.checkArgumentNonnegative(n, "offset must not be negative");
        Preconditions.checkNotNull(arrf, "destination must not be null");
        if (arrf.length + n >= this.getGainFactorCount()) {
            System.arraycopy(this.mElements, 0, arrf, n, this.getGainFactorCount());
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
        if (object instanceof LensShadingMap) {
            object = (LensShadingMap)object;
            if (this.mRows == ((LensShadingMap)object).mRows && this.mColumns == ((LensShadingMap)object).mColumns && Arrays.equals(this.mElements, ((LensShadingMap)object).mElements)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public int getColumnCount() {
        return this.mColumns;
    }

    public float getGainFactor(int n, int n2, int n3) {
        if (n >= 0 && n <= 4) {
            int n4;
            if (n2 >= 0 && n2 < (n4 = this.mColumns)) {
                if (n3 >= 0 && n3 < this.mRows) {
                    return this.mElements[(n4 * n3 + n2) * 4 + n];
                }
                throw new IllegalArgumentException("row out of range");
            }
            throw new IllegalArgumentException("column out of range");
        }
        throw new IllegalArgumentException("colorChannel out of range");
    }

    public int getGainFactorCount() {
        return this.mRows * this.mColumns * 4;
    }

    public RggbChannelVector getGainFactorVector(int n, int n2) {
        int n3;
        if (n >= 0 && n < (n3 = this.mColumns)) {
            if (n2 >= 0 && n2 < this.mRows) {
                n = (n3 * n2 + n) * 4;
                float[] arrf = this.mElements;
                return new RggbChannelVector(arrf[n + 0], arrf[n + 1], arrf[n + 2], arrf[n + 3]);
            }
            throw new IllegalArgumentException("row out of range");
        }
        throw new IllegalArgumentException("column out of range");
    }

    public int getRowCount() {
        return this.mRows;
    }

    public int hashCode() {
        int n = HashCodeHelpers.hashCode(this.mElements);
        return HashCodeHelpers.hashCode(new int[]{this.mRows, this.mColumns, n});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LensShadingMap{");
        for (int i = 0; i < 4; ++i) {
            stringBuilder.append(new String[]{"R:(", "G_even:(", "G_odd:(", "B:("}[i]);
            for (int j = 0; j < this.mRows; ++j) {
                stringBuilder.append("[");
                for (int k = 0; k < this.mColumns; ++k) {
                    stringBuilder.append(this.getGainFactor(i, k, j));
                    if (k >= this.mColumns - 1) continue;
                    stringBuilder.append(", ");
                }
                stringBuilder.append("]");
                if (j >= this.mRows - 1) continue;
                stringBuilder.append(", ");
            }
            stringBuilder.append(")");
            if (i >= 3) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

