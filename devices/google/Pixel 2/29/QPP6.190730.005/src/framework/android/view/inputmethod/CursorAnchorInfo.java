/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.inputmethod.SparseRectFArray;
import java.util.Arrays;
import java.util.Objects;

public final class CursorAnchorInfo
implements Parcelable {
    public static final Parcelable.Creator<CursorAnchorInfo> CREATOR = new Parcelable.Creator<CursorAnchorInfo>(){

        @Override
        public CursorAnchorInfo createFromParcel(Parcel parcel) {
            return new CursorAnchorInfo(parcel);
        }

        public CursorAnchorInfo[] newArray(int n) {
            return new CursorAnchorInfo[n];
        }
    };
    public static final int FLAG_HAS_INVISIBLE_REGION = 2;
    public static final int FLAG_HAS_VISIBLE_REGION = 1;
    public static final int FLAG_IS_RTL = 4;
    private final SparseRectFArray mCharacterBoundsArray;
    private final CharSequence mComposingText;
    private final int mComposingTextStart;
    private final int mHashCode;
    private final float mInsertionMarkerBaseline;
    private final float mInsertionMarkerBottom;
    private final int mInsertionMarkerFlags;
    private final float mInsertionMarkerHorizontal;
    private final float mInsertionMarkerTop;
    private final float[] mMatrixValues;
    private final int mSelectionEnd;
    private final int mSelectionStart;

    private CursorAnchorInfo(int n, int n2, int n3, CharSequence charSequence, int n4, float f, float f2, float f3, float f4, SparseRectFArray sparseRectFArray, float[] arrf) {
        this.mSelectionStart = n;
        this.mSelectionEnd = n2;
        this.mComposingTextStart = n3;
        this.mComposingText = charSequence;
        this.mInsertionMarkerFlags = n4;
        this.mInsertionMarkerHorizontal = f;
        this.mInsertionMarkerTop = f2;
        this.mInsertionMarkerBaseline = f3;
        this.mInsertionMarkerBottom = f4;
        this.mCharacterBoundsArray = sparseRectFArray;
        this.mMatrixValues = arrf;
        this.mHashCode = Objects.hashCode(this.mComposingText) * 31 + Arrays.hashCode(arrf);
    }

    public CursorAnchorInfo(Parcel parcel) {
        this.mHashCode = parcel.readInt();
        this.mSelectionStart = parcel.readInt();
        this.mSelectionEnd = parcel.readInt();
        this.mComposingTextStart = parcel.readInt();
        this.mComposingText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mInsertionMarkerFlags = parcel.readInt();
        this.mInsertionMarkerHorizontal = parcel.readFloat();
        this.mInsertionMarkerTop = parcel.readFloat();
        this.mInsertionMarkerBaseline = parcel.readFloat();
        this.mInsertionMarkerBottom = parcel.readFloat();
        this.mCharacterBoundsArray = (SparseRectFArray)parcel.readParcelable(SparseRectFArray.class.getClassLoader());
        this.mMatrixValues = parcel.createFloatArray();
    }

    private static boolean areSameFloatImpl(float f, float f2) {
        boolean bl = Float.isNaN(f);
        boolean bl2 = true;
        if (bl && Float.isNaN(f2)) {
            return true;
        }
        if (f != f2) {
            bl2 = false;
        }
        return bl2;
    }

    private static float[] computeMatrixValues(Matrix arrf, CursorAnchorInfo cursorAnchorInfo) {
        if (arrf.isIdentity()) {
            return cursorAnchorInfo.mMatrixValues;
        }
        Matrix matrix = new Matrix();
        matrix.setValues(cursorAnchorInfo.mMatrixValues);
        matrix.postConcat((Matrix)arrf);
        arrf = new float[9];
        matrix.getValues(arrf);
        return arrf;
    }

    private static CursorAnchorInfo create(Builder builder) {
        SparseRectFArray sparseRectFArray = builder.mCharacterBoundsArrayBuilder != null ? builder.mCharacterBoundsArrayBuilder.build() : null;
        float[] arrf = new float[9];
        if (builder.mMatrixInitialized) {
            System.arraycopy(builder.mMatrixValues, 0, arrf, 0, 9);
        } else {
            Matrix.IDENTITY_MATRIX.getValues(arrf);
        }
        return new CursorAnchorInfo(builder.mSelectionStart, builder.mSelectionEnd, builder.mComposingTextStart, builder.mComposingText, builder.mInsertionMarkerFlags, builder.mInsertionMarkerHorizontal, builder.mInsertionMarkerTop, builder.mInsertionMarkerBaseline, builder.mInsertionMarkerBottom, sparseRectFArray, arrf);
    }

    public static CursorAnchorInfo createForAdditionalParentMatrix(CursorAnchorInfo cursorAnchorInfo, Matrix matrix) {
        return new CursorAnchorInfo(cursorAnchorInfo.mSelectionStart, cursorAnchorInfo.mSelectionEnd, cursorAnchorInfo.mComposingTextStart, cursorAnchorInfo.mComposingText, cursorAnchorInfo.mInsertionMarkerFlags, cursorAnchorInfo.mInsertionMarkerHorizontal, cursorAnchorInfo.mInsertionMarkerTop, cursorAnchorInfo.mInsertionMarkerBaseline, cursorAnchorInfo.mInsertionMarkerBottom, cursorAnchorInfo.mCharacterBoundsArray, CursorAnchorInfo.computeMatrixValues(matrix, cursorAnchorInfo));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof CursorAnchorInfo)) {
            return false;
        }
        object = (CursorAnchorInfo)object;
        if (this.hashCode() != ((CursorAnchorInfo)object).hashCode()) {
            return false;
        }
        if (this.mSelectionStart == ((CursorAnchorInfo)object).mSelectionStart && this.mSelectionEnd == ((CursorAnchorInfo)object).mSelectionEnd) {
            if (this.mInsertionMarkerFlags == ((CursorAnchorInfo)object).mInsertionMarkerFlags && CursorAnchorInfo.areSameFloatImpl(this.mInsertionMarkerHorizontal, ((CursorAnchorInfo)object).mInsertionMarkerHorizontal) && CursorAnchorInfo.areSameFloatImpl(this.mInsertionMarkerTop, ((CursorAnchorInfo)object).mInsertionMarkerTop) && CursorAnchorInfo.areSameFloatImpl(this.mInsertionMarkerBaseline, ((CursorAnchorInfo)object).mInsertionMarkerBaseline) && CursorAnchorInfo.areSameFloatImpl(this.mInsertionMarkerBottom, ((CursorAnchorInfo)object).mInsertionMarkerBottom)) {
                if (!Objects.equals(this.mCharacterBoundsArray, ((CursorAnchorInfo)object).mCharacterBoundsArray)) {
                    return false;
                }
                if (this.mComposingTextStart == ((CursorAnchorInfo)object).mComposingTextStart && Objects.equals(this.mComposingText, ((CursorAnchorInfo)object).mComposingText)) {
                    float[] arrf;
                    if (this.mMatrixValues.length != ((CursorAnchorInfo)object).mMatrixValues.length) {
                        return false;
                    }
                    for (int i = 0; i < (arrf = this.mMatrixValues).length; ++i) {
                        if (arrf[i] == ((CursorAnchorInfo)object).mMatrixValues[i]) continue;
                        return false;
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public RectF getCharacterBounds(int n) {
        SparseRectFArray sparseRectFArray = this.mCharacterBoundsArray;
        if (sparseRectFArray == null) {
            return null;
        }
        return sparseRectFArray.get(n);
    }

    public int getCharacterBoundsFlags(int n) {
        SparseRectFArray sparseRectFArray = this.mCharacterBoundsArray;
        if (sparseRectFArray == null) {
            return 0;
        }
        return sparseRectFArray.getFlags(n, 0);
    }

    public CharSequence getComposingText() {
        return this.mComposingText;
    }

    public int getComposingTextStart() {
        return this.mComposingTextStart;
    }

    public float getInsertionMarkerBaseline() {
        return this.mInsertionMarkerBaseline;
    }

    public float getInsertionMarkerBottom() {
        return this.mInsertionMarkerBottom;
    }

    public int getInsertionMarkerFlags() {
        return this.mInsertionMarkerFlags;
    }

    public float getInsertionMarkerHorizontal() {
        return this.mInsertionMarkerHorizontal;
    }

    public float getInsertionMarkerTop() {
        return this.mInsertionMarkerTop;
    }

    public Matrix getMatrix() {
        Matrix matrix = new Matrix();
        matrix.setValues(this.mMatrixValues);
        return matrix;
    }

    public int getSelectionEnd() {
        return this.mSelectionEnd;
    }

    public int getSelectionStart() {
        return this.mSelectionStart;
    }

    public int hashCode() {
        return this.mHashCode;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CursorAnchorInfo{mHashCode=");
        stringBuilder.append(this.mHashCode);
        stringBuilder.append(" mSelection=");
        stringBuilder.append(this.mSelectionStart);
        stringBuilder.append(",");
        stringBuilder.append(this.mSelectionEnd);
        stringBuilder.append(" mComposingTextStart=");
        stringBuilder.append(this.mComposingTextStart);
        stringBuilder.append(" mComposingText=");
        stringBuilder.append(Objects.toString(this.mComposingText));
        stringBuilder.append(" mInsertionMarkerFlags=");
        stringBuilder.append(this.mInsertionMarkerFlags);
        stringBuilder.append(" mInsertionMarkerHorizontal=");
        stringBuilder.append(this.mInsertionMarkerHorizontal);
        stringBuilder.append(" mInsertionMarkerTop=");
        stringBuilder.append(this.mInsertionMarkerTop);
        stringBuilder.append(" mInsertionMarkerBaseline=");
        stringBuilder.append(this.mInsertionMarkerBaseline);
        stringBuilder.append(" mInsertionMarkerBottom=");
        stringBuilder.append(this.mInsertionMarkerBottom);
        stringBuilder.append(" mCharacterBoundsArray=");
        stringBuilder.append(Objects.toString(this.mCharacterBoundsArray));
        stringBuilder.append(" mMatrix=");
        stringBuilder.append(Arrays.toString(this.mMatrixValues));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mHashCode);
        parcel.writeInt(this.mSelectionStart);
        parcel.writeInt(this.mSelectionEnd);
        parcel.writeInt(this.mComposingTextStart);
        TextUtils.writeToParcel(this.mComposingText, parcel, n);
        parcel.writeInt(this.mInsertionMarkerFlags);
        parcel.writeFloat(this.mInsertionMarkerHorizontal);
        parcel.writeFloat(this.mInsertionMarkerTop);
        parcel.writeFloat(this.mInsertionMarkerBaseline);
        parcel.writeFloat(this.mInsertionMarkerBottom);
        parcel.writeParcelable(this.mCharacterBoundsArray, n);
        parcel.writeFloatArray(this.mMatrixValues);
    }

    public static final class Builder {
        private SparseRectFArray.SparseRectFArrayBuilder mCharacterBoundsArrayBuilder = null;
        private CharSequence mComposingText = null;
        private int mComposingTextStart = -1;
        private float mInsertionMarkerBaseline = Float.NaN;
        private float mInsertionMarkerBottom = Float.NaN;
        private int mInsertionMarkerFlags = 0;
        private float mInsertionMarkerHorizontal = Float.NaN;
        private float mInsertionMarkerTop = Float.NaN;
        private boolean mMatrixInitialized = false;
        private float[] mMatrixValues = null;
        private int mSelectionEnd = -1;
        private int mSelectionStart = -1;

        public Builder addCharacterBounds(int n, float f, float f2, float f3, float f4, int n2) {
            if (n >= 0) {
                if (this.mCharacterBoundsArrayBuilder == null) {
                    this.mCharacterBoundsArrayBuilder = new SparseRectFArray.SparseRectFArrayBuilder();
                }
                this.mCharacterBoundsArrayBuilder.append(n, f, f2, f3, f4, n2);
                return this;
            }
            throw new IllegalArgumentException("index must not be a negative integer.");
        }

        public CursorAnchorInfo build() {
            boolean bl;
            SparseRectFArray.SparseRectFArrayBuilder sparseRectFArrayBuilder;
            if (!(this.mMatrixInitialized || !(bl = (sparseRectFArrayBuilder = this.mCharacterBoundsArrayBuilder) != null && !sparseRectFArrayBuilder.isEmpty()) && Float.isNaN(this.mInsertionMarkerHorizontal) && Float.isNaN(this.mInsertionMarkerTop) && Float.isNaN(this.mInsertionMarkerBaseline) && Float.isNaN(this.mInsertionMarkerBottom))) {
                throw new IllegalArgumentException("Coordinate transformation matrix is required when positional parameters are specified.");
            }
            return CursorAnchorInfo.create(this);
        }

        public void reset() {
            this.mSelectionStart = -1;
            this.mSelectionEnd = -1;
            this.mComposingTextStart = -1;
            this.mComposingText = null;
            this.mInsertionMarkerFlags = 0;
            this.mInsertionMarkerHorizontal = Float.NaN;
            this.mInsertionMarkerTop = Float.NaN;
            this.mInsertionMarkerBaseline = Float.NaN;
            this.mInsertionMarkerBottom = Float.NaN;
            this.mMatrixInitialized = false;
            SparseRectFArray.SparseRectFArrayBuilder sparseRectFArrayBuilder = this.mCharacterBoundsArrayBuilder;
            if (sparseRectFArrayBuilder != null) {
                sparseRectFArrayBuilder.reset();
            }
        }

        public Builder setComposingText(int n, CharSequence charSequence) {
            this.mComposingTextStart = n;
            this.mComposingText = charSequence == null ? null : new SpannedString(charSequence);
            return this;
        }

        public Builder setInsertionMarkerLocation(float f, float f2, float f3, float f4, int n) {
            this.mInsertionMarkerHorizontal = f;
            this.mInsertionMarkerTop = f2;
            this.mInsertionMarkerBaseline = f3;
            this.mInsertionMarkerBottom = f4;
            this.mInsertionMarkerFlags = n;
            return this;
        }

        public Builder setMatrix(Matrix matrix) {
            if (this.mMatrixValues == null) {
                this.mMatrixValues = new float[9];
            }
            if (matrix == null) {
                matrix = Matrix.IDENTITY_MATRIX;
            }
            matrix.getValues(this.mMatrixValues);
            this.mMatrixInitialized = true;
            return this;
        }

        public Builder setSelectionRange(int n, int n2) {
            this.mSelectionStart = n;
            this.mSelectionEnd = n2;
            return this;
        }
    }

}

