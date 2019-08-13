/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.net.TransportInfo;
import android.os.Parcel;
import android.os.Parcelable;
import java.net.Inet6Address;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Objects;

public final class WifiAwareNetworkInfo
implements TransportInfo,
Parcelable {
    public static final Parcelable.Creator<WifiAwareNetworkInfo> CREATOR = new Parcelable.Creator<WifiAwareNetworkInfo>(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public WifiAwareNetworkInfo createFromParcel(Parcel parcel) {
            try {
                byte[] arrby = parcel.createByteArray();
                String string2 = parcel.readString();
                NetworkInterface networkInterface = null;
                Object object = networkInterface;
                if (string2 != null) {
                    try {
                        object = NetworkInterface.getByName(string2);
                    }
                    catch (SocketException socketException) {
                        socketException.printStackTrace();
                        object = networkInterface;
                    }
                }
                object = Inet6Address.getByAddress(null, arrby, (NetworkInterface)object);
                return new WifiAwareNetworkInfo((Inet6Address)object, parcel.readInt(), parcel.readInt());
            }
            catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
                return null;
            }
        }

        public WifiAwareNetworkInfo[] newArray(int n) {
            return new WifiAwareNetworkInfo[n];
        }
    };
    private Inet6Address mIpv6Addr;
    private int mPort = 0;
    private int mTransportProtocol = -1;

    public WifiAwareNetworkInfo(Inet6Address inet6Address) {
        this.mIpv6Addr = inet6Address;
    }

    public WifiAwareNetworkInfo(Inet6Address inet6Address, int n, int n2) {
        this.mIpv6Addr = inet6Address;
        this.mPort = n;
        this.mTransportProtocol = n2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof WifiAwareNetworkInfo)) {
            return false;
        }
        object = (WifiAwareNetworkInfo)object;
        if (!Objects.equals(this.mIpv6Addr, ((WifiAwareNetworkInfo)object).mIpv6Addr) || this.mPort != ((WifiAwareNetworkInfo)object).mPort || this.mTransportProtocol != ((WifiAwareNetworkInfo)object).mTransportProtocol) {
            bl = false;
        }
        return bl;
    }

    public Inet6Address getPeerIpv6Addr() {
        return this.mIpv6Addr;
    }

    public int getPort() {
        return this.mPort;
    }

    public int getTransportProtocol() {
        return this.mTransportProtocol;
    }

    public int hashCode() {
        return Objects.hash(this.mIpv6Addr, this.mPort, this.mTransportProtocol);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("AwareNetworkInfo: IPv6=");
        stringBuilder.append(this.mIpv6Addr);
        stringBuilder.append(", port=");
        stringBuilder.append(this.mPort);
        stringBuilder.append(", transportProtocol=");
        stringBuilder.append(this.mTransportProtocol);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.mIpv6Addr.getAddress());
        Object object = this.mIpv6Addr.getScopedInterface();
        object = object == null ? null : ((NetworkInterface)object).getName();
        parcel.writeString((String)object);
        parcel.writeInt(this.mPort);
        parcel.writeInt(this.mTransportProtocol);
    }

}

