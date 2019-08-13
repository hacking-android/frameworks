/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.NetworkUtils;
import android.net.SocketKeepalive;
import android.net.util.IpUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.net.InetAddress;

public class KeepalivePacketData
implements Parcelable {
    public static final Parcelable.Creator<KeepalivePacketData> CREATOR = new Parcelable.Creator<KeepalivePacketData>(){

        @Override
        public KeepalivePacketData createFromParcel(Parcel parcel) {
            return new KeepalivePacketData(parcel);
        }

        public KeepalivePacketData[] newArray(int n) {
            return new KeepalivePacketData[n];
        }
    };
    protected static final int IPV4_HEADER_LENGTH = 20;
    private static final String TAG = "KeepalivePacketData";
    protected static final int UDP_HEADER_LENGTH = 8;
    public final InetAddress dstAddress;
    public final int dstPort;
    private final byte[] mPacket;
    public final InetAddress srcAddress;
    public final int srcPort;

    protected KeepalivePacketData(Parcel parcel) {
        this.srcAddress = NetworkUtils.numericToInetAddress(parcel.readString());
        this.dstAddress = NetworkUtils.numericToInetAddress(parcel.readString());
        this.srcPort = parcel.readInt();
        this.dstPort = parcel.readInt();
        this.mPacket = parcel.createByteArray();
    }

    protected KeepalivePacketData(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2, byte[] arrby) throws SocketKeepalive.InvalidPacketException {
        this.srcAddress = inetAddress;
        this.dstAddress = inetAddress2;
        this.srcPort = n;
        this.dstPort = n2;
        this.mPacket = arrby;
        if (inetAddress != null && inetAddress2 != null && inetAddress.getClass().getName().equals(inetAddress2.getClass().getName())) {
            if (IpUtils.isValidUdpOrTcpPort(n) && IpUtils.isValidUdpOrTcpPort(n2)) {
                return;
            }
            Log.e(TAG, "Invalid ports in KeepalivePacketData");
            throw new SocketKeepalive.InvalidPacketException(-22);
        }
        Log.e(TAG, "Invalid or mismatched InetAddresses in KeepalivePacketData");
        throw new SocketKeepalive.InvalidPacketException(-21);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getPacket() {
        return (byte[])this.mPacket.clone();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.srcAddress.getHostAddress());
        parcel.writeString(this.dstAddress.getHostAddress());
        parcel.writeInt(this.srcPort);
        parcel.writeInt(this.dstPort);
        parcel.writeByteArray(this.mPacket);
    }

}

