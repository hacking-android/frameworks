/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;

public class SmsCbLocation
implements Parcelable {
    public static final Parcelable.Creator<SmsCbLocation> CREATOR = new Parcelable.Creator<SmsCbLocation>(){

        @Override
        public SmsCbLocation createFromParcel(Parcel parcel) {
            return new SmsCbLocation(parcel);
        }

        public SmsCbLocation[] newArray(int n) {
            return new SmsCbLocation[n];
        }
    };
    private final int mCid;
    private final int mLac;
    private final String mPlmn;

    public SmsCbLocation() {
        this.mPlmn = "";
        this.mLac = -1;
        this.mCid = -1;
    }

    public SmsCbLocation(Parcel parcel) {
        this.mPlmn = parcel.readString();
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
    }

    public SmsCbLocation(String string2) {
        this.mPlmn = string2;
        this.mLac = -1;
        this.mCid = -1;
    }

    public SmsCbLocation(String string2, int n, int n2) {
        this.mPlmn = string2;
        this.mLac = n;
        this.mCid = n2;
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
        if (object != null && object instanceof SmsCbLocation) {
            object = (SmsCbLocation)object;
            if (!this.mPlmn.equals(((SmsCbLocation)object).mPlmn) || this.mLac != ((SmsCbLocation)object).mLac || this.mCid != ((SmsCbLocation)object).mCid) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getCid() {
        return this.mCid;
    }

    public int getLac() {
        return this.mLac;
    }

    public String getPlmn() {
        return this.mPlmn;
    }

    public int hashCode() {
        return (this.mPlmn.hashCode() * 31 + this.mLac) * 31 + this.mCid;
    }

    public boolean isInLocationArea(SmsCbLocation smsCbLocation) {
        int n = this.mCid;
        if (n != -1 && n != smsCbLocation.mCid) {
            return false;
        }
        n = this.mLac;
        if (n != -1 && n != smsCbLocation.mLac) {
            return false;
        }
        return this.mPlmn.equals(smsCbLocation.mPlmn);
    }

    public boolean isInLocationArea(String string2, int n, int n2) {
        if (!this.mPlmn.equals(string2)) {
            return false;
        }
        int n3 = this.mLac;
        if (n3 != -1 && n3 != n) {
            return false;
        }
        n = this.mCid;
        return n == -1 || n == n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        stringBuilder.append(this.mPlmn);
        stringBuilder.append(',');
        stringBuilder.append(this.mLac);
        stringBuilder.append(',');
        stringBuilder.append(this.mCid);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPlmn);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
    }

}

