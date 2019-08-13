/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 */
package java.net;

import android.system.OsConstants;
import java.io.ObjectStreamException;
import java.net.InetAddress;

public final class Inet4Address
extends InetAddress {
    public static final InetAddress ALL;
    public static final InetAddress ANY;
    static final int INADDRSZ = 4;
    public static final InetAddress LOOPBACK;
    private static final long serialVersionUID = 3286316764910316507L;

    static {
        ANY = new Inet4Address(null, new byte[]{0, 0, 0, 0});
        ALL = new Inet4Address(null, new byte[]{-1, -1, -1, -1});
        LOOPBACK = new Inet4Address("localhost", new byte[]{127, 0, 0, 1});
    }

    Inet4Address() {
        this.holder().hostName = null;
        this.holder().address = 0;
        this.holder().family = OsConstants.AF_INET;
    }

    Inet4Address(String string, int n) {
        this.holder().hostName = string;
        this.holder().family = OsConstants.AF_INET;
        this.holder().address = n;
        this.holder().originalHostName = string;
    }

    Inet4Address(String string, byte[] arrby) {
        this.holder().hostName = string;
        this.holder().family = OsConstants.AF_INET;
        if (arrby != null && arrby.length == 4) {
            byte by = arrby[3];
            byte by2 = arrby[2];
            byte by3 = arrby[1];
            byte by4 = arrby[0];
            this.holder().address = by & 255 | by2 << 8 & 65280 | by3 << 16 & 16711680 | by4 << 24 & -16777216;
        }
        this.holder().originalHostName = string;
    }

    static String numericToTextFormat(byte[] arrby) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(arrby[0] & 255);
        stringBuilder.append(".");
        stringBuilder.append(arrby[1] & 255);
        stringBuilder.append(".");
        stringBuilder.append(arrby[2] & 255);
        stringBuilder.append(".");
        stringBuilder.append(arrby[3] & 255);
        return stringBuilder.toString();
    }

    private Object writeReplace() throws ObjectStreamException {
        InetAddress inetAddress = new InetAddress();
        inetAddress.holder().hostName = this.holder().getHostName();
        inetAddress.holder().address = this.holder().getAddress();
        inetAddress.holder().family = 2;
        return inetAddress;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object != null && object instanceof Inet4Address && ((InetAddress)object).holder().getAddress() == this.holder().getAddress();
        return bl;
    }

    @Override
    public byte[] getAddress() {
        int n = this.holder().getAddress();
        return new byte[]{(byte)(n >>> 24 & 255), (byte)(n >>> 16 & 255), (byte)(n >>> 8 & 255), (byte)(n & 255)};
    }

    @Override
    public String getHostAddress() {
        return Inet4Address.numericToTextFormat(this.getAddress());
    }

    @Override
    public int hashCode() {
        return this.holder().getAddress();
    }

    @Override
    public boolean isAnyLocalAddress() {
        boolean bl = this.holder().getAddress() == 0;
        return bl;
    }

    @Override
    public boolean isLinkLocalAddress() {
        int n = this.holder().getAddress();
        boolean bl = (n >>> 24 & 255) == 169 && (n >>> 16 & 255) == 254;
        return bl;
    }

    @Override
    public boolean isLoopbackAddress() {
        byte[] arrby = this.getAddress();
        boolean bl = false;
        if (arrby[0] == 127) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean isMCGlobal() {
        boolean bl;
        block2 : {
            block3 : {
                boolean bl2;
                byte[] arrby = this.getAddress();
                bl = bl2 = false;
                if ((arrby[0] & 255) < 224) break block2;
                bl = bl2;
                if ((arrby[0] & 255) > 238) break block2;
                if ((arrby[0] & 255) != 224 || arrby[1] != 0) break block3;
                bl = bl2;
                if (arrby[2] == 0) break block2;
            }
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean isMCLinkLocal() {
        int n = this.holder().getAddress();
        boolean bl = (n >>> 24 & 255) == 224 && (n >>> 16 & 255) == 0 && (n >>> 8 & 255) == 0;
        return bl;
    }

    @Override
    public boolean isMCNodeLocal() {
        return false;
    }

    @Override
    public boolean isMCOrgLocal() {
        int n = this.holder().getAddress();
        boolean bl = (n >>> 24 & 255) == 239 && (n >>> 16 & 255) >= 192 && (n >>> 16 & 255) <= 195;
        return bl;
    }

    @Override
    public boolean isMCSiteLocal() {
        int n = this.holder().getAddress();
        boolean bl = (n >>> 24 & 255) == 239 && (n >>> 16 & 255) == 255;
        return bl;
    }

    @Override
    public boolean isMulticastAddress() {
        boolean bl = (this.holder().getAddress() & -268435456) == -536870912;
        return bl;
    }

    @Override
    public boolean isSiteLocalAddress() {
        int n = this.holder().getAddress();
        boolean bl = (n >>> 24 & 255) == 10 || (n >>> 24 & 255) == 172 && (n >>> 16 & 240) == 16 || (n >>> 24 & 255) == 192 && (n >>> 16 & 255) == 168;
        return bl;
    }
}

