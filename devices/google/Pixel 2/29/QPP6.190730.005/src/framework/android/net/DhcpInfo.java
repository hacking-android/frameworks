/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.NetworkUtils;
import android.os.Parcel;
import android.os.Parcelable;

public class DhcpInfo
implements Parcelable {
    public static final Parcelable.Creator<DhcpInfo> CREATOR = new Parcelable.Creator<DhcpInfo>(){

        @Override
        public DhcpInfo createFromParcel(Parcel parcel) {
            DhcpInfo dhcpInfo = new DhcpInfo();
            dhcpInfo.ipAddress = parcel.readInt();
            dhcpInfo.gateway = parcel.readInt();
            dhcpInfo.netmask = parcel.readInt();
            dhcpInfo.dns1 = parcel.readInt();
            dhcpInfo.dns2 = parcel.readInt();
            dhcpInfo.serverAddress = parcel.readInt();
            dhcpInfo.leaseDuration = parcel.readInt();
            return dhcpInfo;
        }

        public DhcpInfo[] newArray(int n) {
            return new DhcpInfo[n];
        }
    };
    public int dns1;
    public int dns2;
    public int gateway;
    public int ipAddress;
    public int leaseDuration;
    public int netmask;
    public int serverAddress;

    public DhcpInfo() {
    }

    public DhcpInfo(DhcpInfo dhcpInfo) {
        if (dhcpInfo != null) {
            this.ipAddress = dhcpInfo.ipAddress;
            this.gateway = dhcpInfo.gateway;
            this.netmask = dhcpInfo.netmask;
            this.dns1 = dhcpInfo.dns1;
            this.dns2 = dhcpInfo.dns2;
            this.serverAddress = dhcpInfo.serverAddress;
            this.leaseDuration = dhcpInfo.leaseDuration;
        }
    }

    private static void putAddress(StringBuffer stringBuffer, int n) {
        stringBuffer.append(NetworkUtils.intToInetAddress(n).getHostAddress());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ipaddr ");
        DhcpInfo.putAddress(stringBuffer, this.ipAddress);
        stringBuffer.append(" gateway ");
        DhcpInfo.putAddress(stringBuffer, this.gateway);
        stringBuffer.append(" netmask ");
        DhcpInfo.putAddress(stringBuffer, this.netmask);
        stringBuffer.append(" dns1 ");
        DhcpInfo.putAddress(stringBuffer, this.dns1);
        stringBuffer.append(" dns2 ");
        DhcpInfo.putAddress(stringBuffer, this.dns2);
        stringBuffer.append(" DHCP server ");
        DhcpInfo.putAddress(stringBuffer, this.serverAddress);
        stringBuffer.append(" lease ");
        stringBuffer.append(this.leaseDuration);
        stringBuffer.append(" seconds");
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.ipAddress);
        parcel.writeInt(this.gateway);
        parcel.writeInt(this.netmask);
        parcel.writeInt(this.dns1);
        parcel.writeInt(this.dns2);
        parcel.writeInt(this.serverAddress);
        parcel.writeInt(this.leaseDuration);
    }

}

