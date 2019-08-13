/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 */
package android.net;

import android.net.InetAddresses;
import android.net.KeepalivePacketData;
import android.net.SocketKeepalive;
import android.net.util.IpUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.system.OsConstants;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class NattKeepalivePacketData
extends KeepalivePacketData
implements Parcelable {
    public static final Parcelable.Creator<NattKeepalivePacketData> CREATOR = new Parcelable.Creator<NattKeepalivePacketData>(){

        @Override
        public NattKeepalivePacketData createFromParcel(Parcel object) {
            Serializable serializable = InetAddresses.parseNumericAddress(((Parcel)object).readString());
            InetAddress inetAddress = InetAddresses.parseNumericAddress(((Parcel)object).readString());
            int n = ((Parcel)object).readInt();
            int n2 = ((Parcel)object).readInt();
            try {
                object = NattKeepalivePacketData.nattKeepalivePacket((InetAddress)serializable, n, inetAddress, n2);
                return object;
            }
            catch (SocketKeepalive.InvalidPacketException invalidPacketException) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Invalid NAT-T keepalive data: ");
                ((StringBuilder)serializable).append(invalidPacketException.error);
                throw new IllegalArgumentException(((StringBuilder)serializable).toString());
            }
        }

        public NattKeepalivePacketData[] newArray(int n) {
            return new NattKeepalivePacketData[n];
        }
    };

    private NattKeepalivePacketData(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2, byte[] arrby) throws SocketKeepalive.InvalidPacketException {
        super(inetAddress, n, inetAddress2, n2, arrby);
    }

    public static NattKeepalivePacketData nattKeepalivePacket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws SocketKeepalive.InvalidPacketException {
        if (inetAddress instanceof Inet4Address && inetAddress2 instanceof Inet4Address) {
            if (n2 == 4500) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(29);
                byteBuffer.order(ByteOrder.BIG_ENDIAN);
                byteBuffer.putShort((short)17664);
                byteBuffer.putShort((short)29);
                byteBuffer.putInt(0);
                byteBuffer.put((byte)64);
                byteBuffer.put((byte)OsConstants.IPPROTO_UDP);
                int n3 = byteBuffer.position();
                byteBuffer.putShort((short)0);
                byteBuffer.put(inetAddress.getAddress());
                byteBuffer.put(inetAddress2.getAddress());
                byteBuffer.putShort((short)n);
                byteBuffer.putShort((short)n2);
                byteBuffer.putShort((short)(29 - 20));
                int n4 = byteBuffer.position();
                byteBuffer.putShort((short)0);
                byteBuffer.put((byte)-1);
                byteBuffer.putShort(n3, IpUtils.ipChecksum(byteBuffer, 0));
                byteBuffer.putShort(n4, IpUtils.udpChecksum(byteBuffer, 0, 20));
                return new NattKeepalivePacketData(inetAddress, n, inetAddress2, n2, byteBuffer.array());
            }
            throw new SocketKeepalive.InvalidPacketException(-22);
        }
        throw new SocketKeepalive.InvalidPacketException(-21);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.srcAddress.getHostAddress());
        parcel.writeString(this.dstAddress.getHostAddress());
        parcel.writeInt(this.srcPort);
        parcel.writeInt(this.dstPort);
    }

}

