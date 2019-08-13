/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.net;

import android.os.Parcel;
import android.os.Parcelable;

public class VpnInfo
implements Parcelable {
    public static final Parcelable.Creator<VpnInfo> CREATOR = new Parcelable.Creator<VpnInfo>(){

        @Override
        public VpnInfo createFromParcel(Parcel parcel) {
            VpnInfo vpnInfo = new VpnInfo();
            vpnInfo.ownerUid = parcel.readInt();
            vpnInfo.vpnIface = parcel.readString();
            vpnInfo.primaryUnderlyingIface = parcel.readString();
            return vpnInfo;
        }

        public VpnInfo[] newArray(int n) {
            return new VpnInfo[n];
        }
    };
    public int ownerUid;
    public String primaryUnderlyingIface;
    public String vpnIface;

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VpnInfo{ownerUid=");
        stringBuilder.append(this.ownerUid);
        stringBuilder.append(", vpnIface='");
        stringBuilder.append(this.vpnIface);
        stringBuilder.append('\'');
        stringBuilder.append(", primaryUnderlyingIface='");
        stringBuilder.append(this.primaryUnderlyingIface);
        stringBuilder.append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.ownerUid);
        parcel.writeString(this.vpnIface);
        parcel.writeString(this.primaryUnderlyingIface);
    }

}

