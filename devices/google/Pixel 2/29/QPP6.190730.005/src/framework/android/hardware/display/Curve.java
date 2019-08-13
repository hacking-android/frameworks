/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.os.Parcel;
import android.os.Parcelable;

public final class Curve
implements Parcelable {
    public static final Parcelable.Creator<Curve> CREATOR = new Parcelable.Creator<Curve>(){

        @Override
        public Curve createFromParcel(Parcel parcel) {
            return new Curve(parcel.createFloatArray(), parcel.createFloatArray());
        }

        public Curve[] newArray(int n) {
            return new Curve[n];
        }
    };
    private final float[] mX;
    private final float[] mY;

    public Curve(float[] arrf, float[] arrf2) {
        this.mX = arrf;
        this.mY = arrf2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float[] getX() {
        return this.mX;
    }

    public float[] getY() {
        return this.mY;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        int n = this.mX.length;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("(");
            stringBuilder.append(this.mX[i]);
            stringBuilder.append(", ");
            stringBuilder.append(this.mY[i]);
            stringBuilder.append(")");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloatArray(this.mX);
        parcel.writeFloatArray(this.mY);
    }

}

