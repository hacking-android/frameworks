/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Parcel;
import android.os.Parcelable;

public final class IpSecTunnelInterfaceResponse
implements Parcelable {
    public static final Parcelable.Creator<IpSecTunnelInterfaceResponse> CREATOR = new Parcelable.Creator<IpSecTunnelInterfaceResponse>(){

        @Override
        public IpSecTunnelInterfaceResponse createFromParcel(Parcel parcel) {
            return new IpSecTunnelInterfaceResponse(parcel);
        }

        public IpSecTunnelInterfaceResponse[] newArray(int n) {
            return new IpSecTunnelInterfaceResponse[n];
        }
    };
    private static final String TAG = "IpSecTunnelInterfaceResponse";
    public final String interfaceName;
    public final int resourceId;
    public final int status;

    public IpSecTunnelInterfaceResponse(int n) {
        if (n != 0) {
            this.status = n;
            this.resourceId = -1;
            this.interfaceName = "";
            return;
        }
        throw new IllegalArgumentException("Valid status implies other args must be provided");
    }

    public IpSecTunnelInterfaceResponse(int n, int n2, String string2) {
        this.status = n;
        this.resourceId = n2;
        this.interfaceName = string2;
    }

    private IpSecTunnelInterfaceResponse(Parcel parcel) {
        this.status = parcel.readInt();
        this.resourceId = parcel.readInt();
        this.interfaceName = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.status);
        parcel.writeInt(this.resourceId);
        parcel.writeString(this.interfaceName);
    }

}

