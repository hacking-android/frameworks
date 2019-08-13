/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public final class SparseRectFArray
implements Parcelable {
    public static final Parcelable.Creator<SparseRectFArray> CREATOR = new Parcelable.Creator<SparseRectFArray>(){

        @Override
        public SparseRectFArray createFromParcel(Parcel parcel) {
            return new SparseRectFArray(parcel);
        }

        public SparseRectFArray[] newArray(int n) {
            return new SparseRectFArray[n];
        }
    };
    private final float[] mCoordinates;
    private final int[] mFlagsArray;
    private final int[] mKeys;

    public SparseRectFArray(Parcel parcel) {
        this.mKeys = parcel.createIntArray();
        this.mCoordinates = parcel.createFloatArray();
        this.mFlagsArray = parcel.createIntArray();
    }

    private SparseRectFArray(SparseRectFArrayBuilder sparseRectFArrayBuilder) {
        if (sparseRectFArrayBuilder.mCount == 0) {
            this.mKeys = null;
            this.mCoordinates = null;
            this.mFlagsArray = null;
        } else {
            this.mKeys = new int[sparseRectFArrayBuilder.mCount];
            this.mCoordinates = new float[sparseRectFArrayBuilder.mCount * 4];
            this.mFlagsArray = new int[sparseRectFArrayBuilder.mCount];
            System.arraycopy(sparseRectFArrayBuilder.mKeys, 0, this.mKeys, 0, sparseRectFArrayBuilder.mCount);
            System.arraycopy(sparseRectFArrayBuilder.mCoordinates, 0, this.mCoordinates, 0, sparseRectFArrayBuilder.mCount * 4);
            System.arraycopy(sparseRectFArrayBuilder.mFlagsArray, 0, this.mFlagsArray, 0, sparseRectFArrayBuilder.mCount);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block3 : {
            bl = false;
            if (object == null) {
                return false;
            }
            if (this == object) {
                return true;
            }
            if (!(object instanceof SparseRectFArray)) {
                return false;
            }
            object = (SparseRectFArray)object;
            if (!Arrays.equals(this.mKeys, ((SparseRectFArray)object).mKeys) || !Arrays.equals(this.mCoordinates, ((SparseRectFArray)object).mCoordinates) || !Arrays.equals(this.mFlagsArray, ((SparseRectFArray)object).mFlagsArray)) break block3;
            bl = true;
        }
        return bl;
    }

    public RectF get(int n) {
        int[] arrn = this.mKeys;
        if (arrn == null) {
            return null;
        }
        if (n < 0) {
            return null;
        }
        if ((n = Arrays.binarySearch(arrn, n)) < 0) {
            return null;
        }
        arrn = this.mCoordinates;
        return new RectF(arrn[n *= 4], arrn[n + 1], arrn[n + 2], arrn[n + 3]);
    }

    public int getFlags(int n, int n2) {
        int[] arrn = this.mKeys;
        if (arrn == null) {
            return n2;
        }
        if (n < 0) {
            return n2;
        }
        if ((n = Arrays.binarySearch(arrn, n)) < 0) {
            return n2;
        }
        return this.mFlagsArray[n];
    }

    public int hashCode() {
        int[] arrn = this.mKeys;
        if (arrn != null && arrn.length != 0) {
            int n = arrn.length;
            for (int i = 0; i < 4; ++i) {
                n = (int)((float)(n * 31) + this.mCoordinates[i]);
            }
            return n * 31 + this.mFlagsArray[0];
        }
        return 0;
    }

    public String toString() {
        if (this.mKeys != null && this.mCoordinates != null && this.mFlagsArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SparseRectFArray{");
            for (int i = 0; i < this.mKeys.length; ++i) {
                if (i != 0) {
                    stringBuilder.append(", ");
                }
                int n = i * 4;
                stringBuilder.append(this.mKeys[i]);
                stringBuilder.append(":[");
                stringBuilder.append(this.mCoordinates[n + 0]);
                stringBuilder.append(",");
                stringBuilder.append(this.mCoordinates[n + 1]);
                stringBuilder.append("],[");
                stringBuilder.append(this.mCoordinates[n + 2]);
                stringBuilder.append(",");
                stringBuilder.append(this.mCoordinates[n + 3]);
                stringBuilder.append("]:flagsArray=");
                stringBuilder.append(this.mFlagsArray[i]);
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        return "SparseRectFArray{}";
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeIntArray(this.mKeys);
        parcel.writeFloatArray(this.mCoordinates);
        parcel.writeIntArray(this.mFlagsArray);
    }

    public static final class SparseRectFArrayBuilder {
        private static int INITIAL_SIZE = 16;
        private float[] mCoordinates = null;
        private int mCount = 0;
        private int[] mFlagsArray = null;
        private int[] mKeys = null;

        private void checkIndex(int n) {
            int n2 = this.mCount;
            if (n2 == 0) {
                return;
            }
            if (this.mKeys[n2 - 1] < n) {
                return;
            }
            throw new IllegalArgumentException("key must be greater than all existing keys.");
        }

        private void ensureBufferSize() {
            int n;
            int n2;
            int[] arrn;
            int[] arrn2;
            int n3;
            if (this.mKeys == null) {
                this.mKeys = new int[INITIAL_SIZE];
            }
            if (this.mCoordinates == null) {
                this.mCoordinates = new float[INITIAL_SIZE * 4];
            }
            if (this.mFlagsArray == null) {
                this.mFlagsArray = new int[INITIAL_SIZE];
            }
            if ((arrn2 = this.mKeys).length <= (n = (n3 = this.mCount) + 1)) {
                arrn = new int[n * 2];
                System.arraycopy(arrn2, 0, arrn, 0, n3);
                this.mKeys = arrn;
            }
            if ((arrn = this.mCoordinates).length <= (n2 = ((n3 = this.mCount) + 1) * 4)) {
                arrn2 = new float[n2 * 2];
                System.arraycopy(arrn, 0, arrn2, 0, n3 * 4);
                this.mCoordinates = arrn2;
            }
            if ((arrn = this.mFlagsArray).length <= n) {
                arrn2 = new int[n * 2];
                System.arraycopy(arrn, 0, arrn2, 0, this.mCount);
                this.mFlagsArray = arrn2;
            }
        }

        public SparseRectFArrayBuilder append(int n, float f, float f2, float f3, float f4, int n2) {
            this.checkIndex(n);
            this.ensureBufferSize();
            int n3 = this.mCount;
            int n4 = n3 * 4;
            float[] arrf = this.mCoordinates;
            arrf[n4 + 0] = f;
            arrf[n4 + 1] = f2;
            arrf[n4 + 2] = f3;
            arrf[n4 + 3] = f4;
            n4 = this.mCount;
            this.mFlagsArray[n4] = n2;
            this.mKeys[n3] = n;
            this.mCount = n3 + 1;
            return this;
        }

        public SparseRectFArray build() {
            return new SparseRectFArray(this);
        }

        public boolean isEmpty() {
            boolean bl = this.mCount <= 0;
            return bl;
        }

        public void reset() {
            if (this.mCount == 0) {
                this.mKeys = null;
                this.mCoordinates = null;
                this.mFlagsArray = null;
            }
            this.mCount = 0;
        }
    }

}

