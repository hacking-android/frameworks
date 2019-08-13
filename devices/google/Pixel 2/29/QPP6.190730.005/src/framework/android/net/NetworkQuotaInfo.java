/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class NetworkQuotaInfo
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<NetworkQuotaInfo> CREATOR = new Parcelable.Creator<NetworkQuotaInfo>(){

        @Override
        public NetworkQuotaInfo createFromParcel(Parcel parcel) {
            return new NetworkQuotaInfo(parcel);
        }

        public NetworkQuotaInfo[] newArray(int n) {
            return new NetworkQuotaInfo[n];
        }
    };
    public static final long NO_LIMIT = -1L;

    public NetworkQuotaInfo() {
    }

    public NetworkQuotaInfo(Parcel parcel) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public long getEstimatedBytes() {
        return 0L;
    }

    @UnsupportedAppUsage
    public long getHardLimitBytes() {
        return -1L;
    }

    @UnsupportedAppUsage
    public long getSoftLimitBytes() {
        return -1L;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
    }

}

