/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.android.internal.telephony.cdnr;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class CarrierDisplayNameData
implements Parcelable {
    public static final Parcelable.Creator<CarrierDisplayNameData> CREATOR = new Parcelable.Creator<CarrierDisplayNameData>(){

        public CarrierDisplayNameData createFromParcel(Parcel parcel) {
            return new CarrierDisplayNameData(parcel);
        }

        public CarrierDisplayNameData[] newArray(int n) {
            return new CarrierDisplayNameData[n];
        }
    };
    private final String mDataSpn;
    private final String mPlmn;
    private final boolean mShowPlmn;
    private final boolean mShowSpn;
    private final String mSpn;

    private CarrierDisplayNameData(Parcel parcel) {
        this.mSpn = parcel.readString();
        this.mDataSpn = parcel.readString();
        this.mPlmn = parcel.readString();
        this.mShowSpn = parcel.readBoolean();
        this.mShowPlmn = parcel.readBoolean();
    }

    private CarrierDisplayNameData(String string, String string2, boolean bl, String string3, boolean bl2) {
        this.mSpn = string;
        this.mDataSpn = string2;
        this.mShowSpn = bl;
        this.mPlmn = string3;
        this.mShowPlmn = bl2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (CarrierDisplayNameData)object;
            if (!(this.mShowSpn == ((CarrierDisplayNameData)object).mShowSpn && this.mShowPlmn == ((CarrierDisplayNameData)object).mShowPlmn && Objects.equals(this.mSpn, ((CarrierDisplayNameData)object).mSpn) && Objects.equals(this.mDataSpn, ((CarrierDisplayNameData)object).mDataSpn) && Objects.equals(this.mPlmn, ((CarrierDisplayNameData)object).mPlmn))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public String getDataSpn() {
        return this.mDataSpn;
    }

    public String getPlmn() {
        return this.mPlmn;
    }

    public String getSpn() {
        return this.mSpn;
    }

    public int hashCode() {
        return Objects.hash(this.mSpn, this.mDataSpn, this.mPlmn, this.mShowSpn, this.mShowPlmn);
    }

    public boolean shouldShowPlmn() {
        return this.mShowPlmn;
    }

    public boolean shouldShowSpn() {
        return this.mShowSpn;
    }

    public String toString() {
        return String.format("{ spn = %s, dataSpn = %s, showSpn = %b, plmn = %s, showPlmn = %b", this.mSpn, this.mDataSpn, this.mShowSpn, this.mPlmn, this.mShowPlmn);
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mSpn);
        parcel.writeString(this.mDataSpn);
        parcel.writeString(this.mPlmn);
        parcel.writeBoolean(this.mShowSpn);
        parcel.writeBoolean(this.mShowPlmn);
    }

    public static final class Builder {
        private String mDataSpn = null;
        private String mPlmn = null;
        private boolean mShowPlmn = false;
        private boolean mShowSpn = false;
        private String mSpn = null;

        public CarrierDisplayNameData build() {
            return new CarrierDisplayNameData(this.mSpn, this.mDataSpn, this.mShowSpn, this.mPlmn, this.mShowPlmn);
        }

        public Builder setDataSpn(String string) {
            this.mDataSpn = string;
            return this;
        }

        public Builder setPlmn(String string) {
            this.mPlmn = string;
            return this;
        }

        public Builder setShowPlmn(boolean bl) {
            this.mShowPlmn = bl;
            return this;
        }

        public Builder setShowSpn(boolean bl) {
            this.mShowSpn = bl;
            return this;
        }

        public Builder setSpn(String string) {
            this.mSpn = string;
            return this;
        }
    }

}

