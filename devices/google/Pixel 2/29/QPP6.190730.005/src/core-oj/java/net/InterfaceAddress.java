/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.net.Inet4Address;
import java.net.InetAddress;

public class InterfaceAddress {
    private InetAddress address = null;
    private Inet4Address broadcast = null;
    private short maskLength = (short)(false ? 1 : 0);

    InterfaceAddress() {
    }

    InterfaceAddress(InetAddress inetAddress, Inet4Address inet4Address, InetAddress inetAddress2) {
        this.address = inetAddress;
        this.broadcast = inet4Address;
        this.maskLength = this.countPrefixLength(inetAddress2);
    }

    private short countPrefixLength(InetAddress arrby) {
        short s = 0;
        arrby = arrby.getAddress();
        int n = arrby.length;
        short s2 = s;
        for (int i = 0; i < n; ++i) {
            s = arrby[i];
            short s3 = s2;
            while (s != 0) {
                s = (byte)(s << 1);
                s3 = (short)(s3 + 1);
            }
            s2 = s3;
        }
        return s2;
    }

    public boolean equals(Object object) {
        if (!(object instanceof InterfaceAddress)) {
            return false;
        }
        object = (InterfaceAddress)object;
        InetAddress inetAddress = this.address;
        if (!(inetAddress != null ? inetAddress.equals(((InterfaceAddress)object).address) : ((InterfaceAddress)object).address == null)) {
            return false;
        }
        inetAddress = this.broadcast;
        if (!(inetAddress != null ? ((Inet4Address)inetAddress).equals(((InterfaceAddress)object).broadcast) : ((InterfaceAddress)object).broadcast == null)) {
            return false;
        }
        return this.maskLength == ((InterfaceAddress)object).maskLength;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    public InetAddress getBroadcast() {
        return this.broadcast;
    }

    public short getNetworkPrefixLength() {
        return this.maskLength;
    }

    public int hashCode() {
        int n = this.address.hashCode();
        Inet4Address inet4Address = this.broadcast;
        int n2 = inet4Address != null ? inet4Address.hashCode() : 0;
        return n + n2 + this.maskLength;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.address);
        stringBuilder.append("/");
        stringBuilder.append(this.maskLength);
        stringBuilder.append(" [");
        stringBuilder.append(this.broadcast);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

