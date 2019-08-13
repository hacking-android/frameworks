/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p;

import android.os.Parcel;
import android.os.Parcelable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class WifiP2pInfo
implements Parcelable {
    public static final Parcelable.Creator<WifiP2pInfo> CREATOR = new Parcelable.Creator<WifiP2pInfo>(){

        @Override
        public WifiP2pInfo createFromParcel(Parcel parcel) {
            WifiP2pInfo wifiP2pInfo = new WifiP2pInfo();
            byte by = parcel.readByte();
            boolean bl = false;
            boolean bl2 = by == 1;
            wifiP2pInfo.groupFormed = bl2;
            bl2 = bl;
            if (parcel.readByte() == 1) {
                bl2 = true;
            }
            wifiP2pInfo.isGroupOwner = bl2;
            if (parcel.readByte() == 1) {
                try {
                    wifiP2pInfo.groupOwnerAddress = InetAddress.getByAddress(parcel.createByteArray());
                }
                catch (UnknownHostException unknownHostException) {
                    // empty catch block
                }
            }
            return wifiP2pInfo;
        }

        public WifiP2pInfo[] newArray(int n) {
            return new WifiP2pInfo[n];
        }
    };
    public boolean groupFormed;
    public InetAddress groupOwnerAddress;
    public boolean isGroupOwner;

    public WifiP2pInfo() {
    }

    public WifiP2pInfo(WifiP2pInfo wifiP2pInfo) {
        if (wifiP2pInfo != null) {
            this.groupFormed = wifiP2pInfo.groupFormed;
            this.isGroupOwner = wifiP2pInfo.isGroupOwner;
            this.groupOwnerAddress = wifiP2pInfo.groupOwnerAddress;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("groupFormed: ");
        stringBuffer.append(this.groupFormed);
        stringBuffer.append(" isGroupOwner: ");
        stringBuffer.append(this.isGroupOwner);
        stringBuffer.append(" groupOwnerAddress: ");
        stringBuffer.append(this.groupOwnerAddress);
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByte((byte)this.groupFormed);
        parcel.writeByte((byte)this.isGroupOwner);
        if (this.groupOwnerAddress != null) {
            parcel.writeByte((byte)1);
            parcel.writeByteArray(this.groupOwnerAddress.getAddress());
        } else {
            parcel.writeByte((byte)0);
        }
    }

}

