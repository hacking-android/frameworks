/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.input;

import android.os.Parcel;
import android.os.Parcelable;

public class TouchCalibration
implements Parcelable {
    public static final Parcelable.Creator<TouchCalibration> CREATOR;
    public static final TouchCalibration IDENTITY;
    private final float mXOffset;
    private final float mXScale;
    private final float mXYMix;
    private final float mYOffset;
    private final float mYScale;
    private final float mYXMix;

    static {
        IDENTITY = new TouchCalibration();
        CREATOR = new Parcelable.Creator<TouchCalibration>(){

            @Override
            public TouchCalibration createFromParcel(Parcel parcel) {
                return new TouchCalibration(parcel);
            }

            public TouchCalibration[] newArray(int n) {
                return new TouchCalibration[n];
            }
        };
    }

    public TouchCalibration() {
        this(1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }

    public TouchCalibration(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mXScale = f;
        this.mXYMix = f2;
        this.mXOffset = f3;
        this.mYXMix = f4;
        this.mYScale = f5;
        this.mYOffset = f6;
    }

    public TouchCalibration(Parcel parcel) {
        this.mXScale = parcel.readFloat();
        this.mXYMix = parcel.readFloat();
        this.mXOffset = parcel.readFloat();
        this.mYXMix = parcel.readFloat();
        this.mYScale = parcel.readFloat();
        this.mYOffset = parcel.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof TouchCalibration) {
            object = (TouchCalibration)object;
            if (((TouchCalibration)object).mXScale != this.mXScale || ((TouchCalibration)object).mXYMix != this.mXYMix || ((TouchCalibration)object).mXOffset != this.mXOffset || ((TouchCalibration)object).mYXMix != this.mYXMix || ((TouchCalibration)object).mYScale != this.mYScale || ((TouchCalibration)object).mYOffset != this.mYOffset) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public float[] getAffineTransform() {
        return new float[]{this.mXScale, this.mXYMix, this.mXOffset, this.mYXMix, this.mYScale, this.mYOffset};
    }

    public int hashCode() {
        return Float.floatToIntBits(this.mXScale) ^ Float.floatToIntBits(this.mXYMix) ^ Float.floatToIntBits(this.mXOffset) ^ Float.floatToIntBits(this.mYXMix) ^ Float.floatToIntBits(this.mYScale) ^ Float.floatToIntBits(this.mYOffset);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloat(this.mXScale);
        parcel.writeFloat(this.mXYMix);
        parcel.writeFloat(this.mXOffset);
        parcel.writeFloat(this.mYXMix);
        parcel.writeFloat(this.mYScale);
        parcel.writeFloat(this.mYOffset);
    }

}

