/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public final class Geofence
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<Geofence> CREATOR = new Parcelable.Creator<Geofence>(){

        @Override
        public Geofence createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            double d = parcel.readDouble();
            double d2 = parcel.readDouble();
            float f = parcel.readFloat();
            Geofence.checkType(n);
            return Geofence.createCircle(d, d2, f);
        }

        public Geofence[] newArray(int n) {
            return new Geofence[n];
        }
    };
    public static final int TYPE_HORIZONTAL_CIRCLE = 1;
    private final double mLatitude;
    private final double mLongitude;
    private final float mRadius;
    private final int mType;

    private Geofence(double d, double d2, float f) {
        Geofence.checkRadius(f);
        Geofence.checkLatLong(d, d2);
        this.mType = 1;
        this.mLatitude = d;
        this.mLongitude = d2;
        this.mRadius = f;
    }

    private static void checkLatLong(double d, double d2) {
        if (!(d > 90.0) && !(d < -90.0)) {
            if (!(d2 > 180.0) && !(d2 < -180.0)) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid longitude: ");
            stringBuilder.append(d2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid latitude: ");
        stringBuilder.append(d);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static void checkRadius(float f) {
        if (!(f <= 0.0f)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid radius: ");
        stringBuilder.append(f);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static void checkType(int n) {
        if (n == 1) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid type: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static Geofence createCircle(double d, double d2, float f) {
        return new Geofence(d, d2, f);
    }

    private static String typeToString(int n) {
        if (n != 1) {
            Geofence.checkType(n);
            return null;
        }
        return "CIRCLE";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof Geofence)) {
            return false;
        }
        object = (Geofence)object;
        if (this.mRadius != ((Geofence)object).mRadius) {
            return false;
        }
        if (this.mLatitude != ((Geofence)object).mLatitude) {
            return false;
        }
        if (this.mLongitude != ((Geofence)object).mLongitude) {
            return false;
        }
        return this.mType == ((Geofence)object).mType;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public float getRadius() {
        return this.mRadius;
    }

    public int getType() {
        return this.mType;
    }

    public int hashCode() {
        long l = Double.doubleToLongBits(this.mLatitude);
        int n = (int)(l >>> 32 ^ l);
        l = Double.doubleToLongBits(this.mLongitude);
        return (((1 * 31 + n) * 31 + (int)(l >>> 32 ^ l)) * 31 + Float.floatToIntBits(this.mRadius)) * 31 + this.mType;
    }

    public String toString() {
        return String.format("Geofence[%s %.6f, %.6f %.0fm]", Geofence.typeToString(this.mType), this.mLatitude, this.mLongitude, Float.valueOf(this.mRadius));
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        parcel.writeDouble(this.mLatitude);
        parcel.writeDouble(this.mLongitude);
        parcel.writeFloat(this.mRadius);
    }

}

