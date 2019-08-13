/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.os.Parcel;
import android.os.Parcelable;

public class Criteria
implements Parcelable {
    public static final int ACCURACY_COARSE = 2;
    public static final int ACCURACY_FINE = 1;
    public static final int ACCURACY_HIGH = 3;
    public static final int ACCURACY_LOW = 1;
    public static final int ACCURACY_MEDIUM = 2;
    public static final Parcelable.Creator<Criteria> CREATOR = new Parcelable.Creator<Criteria>(){

        @Override
        public Criteria createFromParcel(Parcel parcel) {
            Criteria criteria = new Criteria();
            criteria.mHorizontalAccuracy = parcel.readInt();
            criteria.mVerticalAccuracy = parcel.readInt();
            criteria.mSpeedAccuracy = parcel.readInt();
            criteria.mBearingAccuracy = parcel.readInt();
            criteria.mPowerRequirement = parcel.readInt();
            int n = parcel.readInt();
            boolean bl = true;
            boolean bl2 = n != 0;
            criteria.mAltitudeRequired = bl2;
            bl2 = parcel.readInt() != 0;
            criteria.mBearingRequired = bl2;
            bl2 = parcel.readInt() != 0;
            criteria.mSpeedRequired = bl2;
            bl2 = parcel.readInt() != 0 ? bl : false;
            criteria.mCostAllowed = bl2;
            return criteria;
        }

        public Criteria[] newArray(int n) {
            return new Criteria[n];
        }
    };
    public static final int NO_REQUIREMENT = 0;
    public static final int POWER_HIGH = 3;
    public static final int POWER_LOW = 1;
    public static final int POWER_MEDIUM = 2;
    private boolean mAltitudeRequired = false;
    private int mBearingAccuracy = 0;
    private boolean mBearingRequired = false;
    private boolean mCostAllowed = false;
    private int mHorizontalAccuracy = 0;
    private int mPowerRequirement = 0;
    private int mSpeedAccuracy = 0;
    private boolean mSpeedRequired = false;
    private int mVerticalAccuracy = 0;

    public Criteria() {
    }

    public Criteria(Criteria criteria) {
        this.mHorizontalAccuracy = criteria.mHorizontalAccuracy;
        this.mVerticalAccuracy = criteria.mVerticalAccuracy;
        this.mSpeedAccuracy = criteria.mSpeedAccuracy;
        this.mBearingAccuracy = criteria.mBearingAccuracy;
        this.mPowerRequirement = criteria.mPowerRequirement;
        this.mAltitudeRequired = criteria.mAltitudeRequired;
        this.mBearingRequired = criteria.mBearingRequired;
        this.mSpeedRequired = criteria.mSpeedRequired;
        this.mCostAllowed = criteria.mCostAllowed;
    }

    private static String accuracyToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return "???";
                    }
                    return "HIGH";
                }
                return "MEDIUM";
            }
            return "LOW";
        }
        return "---";
    }

    private static String powerToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return "???";
                    }
                    return "HIGH";
                }
                return "MEDIUM";
            }
            return "LOW";
        }
        return "NO_REQ";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAccuracy() {
        if (this.mHorizontalAccuracy >= 3) {
            return 1;
        }
        return 2;
    }

    public int getBearingAccuracy() {
        return this.mBearingAccuracy;
    }

    public int getHorizontalAccuracy() {
        return this.mHorizontalAccuracy;
    }

    public int getPowerRequirement() {
        return this.mPowerRequirement;
    }

    public int getSpeedAccuracy() {
        return this.mSpeedAccuracy;
    }

    public int getVerticalAccuracy() {
        return this.mVerticalAccuracy;
    }

    public boolean isAltitudeRequired() {
        return this.mAltitudeRequired;
    }

    public boolean isBearingRequired() {
        return this.mBearingRequired;
    }

    public boolean isCostAllowed() {
        return this.mCostAllowed;
    }

    public boolean isSpeedRequired() {
        return this.mSpeedRequired;
    }

    public void setAccuracy(int n) {
        if (n >= 0 && n <= 2) {
            this.mHorizontalAccuracy = n == 1 ? 3 : 1;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("accuracy=");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setAltitudeRequired(boolean bl) {
        this.mAltitudeRequired = bl;
    }

    public void setBearingAccuracy(int n) {
        if (n >= 0 && n <= 3) {
            this.mBearingAccuracy = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("accuracy=");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setBearingRequired(boolean bl) {
        this.mBearingRequired = bl;
    }

    public void setCostAllowed(boolean bl) {
        this.mCostAllowed = bl;
    }

    public void setHorizontalAccuracy(int n) {
        if (n >= 0 && n <= 3) {
            this.mHorizontalAccuracy = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("accuracy=");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setPowerRequirement(int n) {
        if (n >= 0 && n <= 3) {
            this.mPowerRequirement = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("level=");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setSpeedAccuracy(int n) {
        if (n >= 0 && n <= 3) {
            this.mSpeedAccuracy = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("accuracy=");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setSpeedRequired(boolean bl) {
        this.mSpeedRequired = bl;
    }

    public void setVerticalAccuracy(int n) {
        if (n >= 0 && n <= 3) {
            this.mVerticalAccuracy = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("accuracy=");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Criteria[power=");
        stringBuilder.append(Criteria.powerToString(this.mPowerRequirement));
        stringBuilder.append(" acc=");
        stringBuilder.append(Criteria.accuracyToString(this.mHorizontalAccuracy));
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mHorizontalAccuracy);
        parcel.writeInt(this.mVerticalAccuracy);
        parcel.writeInt(this.mSpeedAccuracy);
        parcel.writeInt(this.mBearingAccuracy);
        parcel.writeInt(this.mPowerRequirement);
        parcel.writeInt((int)this.mAltitudeRequired);
        parcel.writeInt((int)this.mBearingRequired);
        parcel.writeInt((int)this.mSpeedRequired);
        parcel.writeInt((int)this.mCostAllowed);
    }

}

