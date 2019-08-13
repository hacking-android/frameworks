/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

public class PointF
implements Parcelable {
    public static final Parcelable.Creator<PointF> CREATOR = new Parcelable.Creator<PointF>(){

        @Override
        public PointF createFromParcel(Parcel parcel) {
            PointF pointF = new PointF();
            pointF.readFromParcel(parcel);
            return pointF;
        }

        public PointF[] newArray(int n) {
            return new PointF[n];
        }
    };
    public float x;
    public float y;

    public PointF() {
    }

    public PointF(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public PointF(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public static float length(float f, float f2) {
        return (float)Math.hypot(f, f2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public final boolean equals(float f, float f2) {
        boolean bl = this.x == f && this.y == f2;
        return bl;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (PointF)object;
            if (Float.compare(((PointF)object).x, this.x) != 0) {
                return false;
            }
            return Float.compare(((PointF)object).y, this.y) == 0;
        }
        return false;
    }

    public int hashCode() {
        float f = this.x;
        int n = 0;
        int n2 = f != 0.0f ? Float.floatToIntBits(f) : 0;
        f = this.y;
        if (f != 0.0f) {
            n = Float.floatToIntBits(f);
        }
        return n2 * 31 + n;
    }

    public final float length() {
        return PointF.length(this.x, this.y);
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public final void offset(float f, float f2) {
        this.x += f;
        this.y += f2;
    }

    public void readFromParcel(Parcel parcel) {
        this.x = parcel.readFloat();
        this.y = parcel.readFloat();
    }

    public final void set(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public final void set(PointF pointF) {
        this.x = pointF.x;
        this.y = pointF.y;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PointF(");
        stringBuilder.append(this.x);
        stringBuilder.append(", ");
        stringBuilder.append(this.y);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloat(this.x);
        parcel.writeFloat(this.y);
    }

}

