/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public final class EuiccInfo
implements Parcelable {
    public static final Parcelable.Creator<EuiccInfo> CREATOR = new Parcelable.Creator<EuiccInfo>(){

        @Override
        public EuiccInfo createFromParcel(Parcel parcel) {
            return new EuiccInfo(parcel);
        }

        public EuiccInfo[] newArray(int n) {
            return new EuiccInfo[n];
        }
    };
    @UnsupportedAppUsage
    private final String osVersion;

    private EuiccInfo(Parcel parcel) {
        this.osVersion = parcel.readString();
    }

    public EuiccInfo(String string2) {
        this.osVersion = string2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.osVersion);
    }

}

