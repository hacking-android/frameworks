/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.BitUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public final class MacAddress
implements Parcelable {
    @UnsupportedAppUsage
    public static final MacAddress ALL_ZEROS_ADDRESS;
    private static final MacAddress BASE_GOOGLE_MAC;
    public static final MacAddress BROADCAST_ADDRESS;
    public static final Parcelable.Creator<MacAddress> CREATOR;
    private static final byte[] ETHER_ADDR_BROADCAST;
    private static final int ETHER_ADDR_LEN = 6;
    private static final long LOCALLY_ASSIGNED_MASK;
    private static final long MULTICAST_MASK;
    private static final long NIC_MASK;
    private static final long OUI_MASK;
    public static final int TYPE_BROADCAST = 3;
    public static final int TYPE_MULTICAST = 2;
    public static final int TYPE_UNICAST = 1;
    public static final int TYPE_UNKNOWN = 0;
    private static final long VALID_LONG_MASK = 0xFFFFFFFFFFFFL;
    private final long mAddr;

    static {
        ETHER_ADDR_BROADCAST = MacAddress.addr(255, 255, 255, 255, 255, 255);
        BROADCAST_ADDRESS = MacAddress.fromBytes(ETHER_ADDR_BROADCAST);
        ALL_ZEROS_ADDRESS = new MacAddress(0L);
        LOCALLY_ASSIGNED_MASK = MacAddress.fromString((String)"2:0:0:0:0:0").mAddr;
        MULTICAST_MASK = MacAddress.fromString((String)"1:0:0:0:0:0").mAddr;
        OUI_MASK = MacAddress.fromString((String)"ff:ff:ff:0:0:0").mAddr;
        NIC_MASK = MacAddress.fromString((String)"0:0:0:ff:ff:ff").mAddr;
        BASE_GOOGLE_MAC = MacAddress.fromString("da:a1:19:0:0:0");
        CREATOR = new Parcelable.Creator<MacAddress>(){

            @Override
            public MacAddress createFromParcel(Parcel parcel) {
                return new MacAddress(parcel.readLong());
            }

            public MacAddress[] newArray(int n) {
                return new MacAddress[n];
            }
        };
    }

    private MacAddress(long l) {
        this.mAddr = 0xFFFFFFFFFFFFL & l;
    }

    private static byte[] addr(int ... arrn) {
        if (arrn.length == 6) {
            byte[] arrby = new byte[6];
            for (int i = 0; i < 6; ++i) {
                arrby[i] = (byte)arrn[i];
            }
            return arrby;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Arrays.toString(arrn));
        stringBuilder.append(" was not an array with length equal to ");
        stringBuilder.append(6);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static byte[] byteAddrFromLongAddr(long l) {
        byte[] arrby = new byte[6];
        int n = 6;
        do {
            int n2 = n - 1;
            if (n <= 0) break;
            arrby[n2] = (byte)l;
            l >>= 8;
            n = n2;
        } while (true);
        return arrby;
    }

    public static byte[] byteAddrFromStringAddr(String string2) {
        Preconditions.checkNotNull(string2);
        Object object = string2.split(":");
        if (((String[])object).length == 6) {
            byte[] arrby = new byte[6];
            for (int i = 0; i < 6; ++i) {
                int n = Integer.valueOf((String)object[i], 16);
                if (n >= 0 && 255 >= n) {
                    arrby[i] = (byte)n;
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("was not a valid MAC address");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            return arrby;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" was not a valid MAC address");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static MacAddress createRandomUnicastAddress() {
        return new MacAddress((new SecureRandom().nextLong() & 0xFFFFFFFFFFFFL | LOCALLY_ASSIGNED_MASK) & MULTICAST_MASK);
    }

    public static MacAddress createRandomUnicastAddress(MacAddress macAddress, Random random) {
        return new MacAddress((macAddress.mAddr & OUI_MASK | NIC_MASK & random.nextLong() | LOCALLY_ASSIGNED_MASK) & MULTICAST_MASK);
    }

    public static MacAddress createRandomUnicastAddressWithGoogleBase() {
        return MacAddress.createRandomUnicastAddress(BASE_GOOGLE_MAC, new SecureRandom());
    }

    public static MacAddress fromBytes(byte[] arrby) {
        return new MacAddress(MacAddress.longAddrFromByteAddr(arrby));
    }

    public static MacAddress fromString(String string2) {
        return new MacAddress(MacAddress.longAddrFromStringAddr(string2));
    }

    public static boolean isMacAddress(byte[] arrby) {
        boolean bl = arrby != null && arrby.length == 6;
        return bl;
    }

    private static long longAddrFromByteAddr(byte[] arrby) {
        Preconditions.checkNotNull(arrby);
        if (MacAddress.isMacAddress(arrby)) {
            long l = 0L;
            int n = arrby.length;
            for (int i = 0; i < n; ++i) {
                l = (l << 8) + (long)BitUtils.uint8(arrby[i]);
            }
            return l;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Arrays.toString(arrby));
        stringBuilder.append(" was not a valid MAC address");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static long longAddrFromStringAddr(String string2) {
        Preconditions.checkNotNull(string2);
        Object object = string2.split(":");
        if (((String[])object).length == 6) {
            long l = 0L;
            for (int i = 0; i < ((String[])object).length; ++i) {
                int n = Integer.valueOf((String)object[i], 16);
                if (n >= 0 && 255 >= n) {
                    l = (long)n + (l << 8);
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("was not a valid MAC address");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            return l;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" was not a valid MAC address");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static int macAddressType(byte[] arrby) {
        if (!MacAddress.isMacAddress(arrby)) {
            return 0;
        }
        return MacAddress.fromBytes(arrby).getAddressType();
    }

    public static String stringAddrFromByteAddr(byte[] arrby) {
        if (!MacAddress.isMacAddress(arrby)) {
            return null;
        }
        return String.format("%02x:%02x:%02x:%02x:%02x:%02x", arrby[0], arrby[1], arrby[2], arrby[3], arrby[4], arrby[5]);
    }

    private static String stringAddrFromLongAddr(long l) {
        return String.format("%02x:%02x:%02x:%02x:%02x:%02x", l >> 40 & 255L, l >> 32 & 255L, l >> 24 & 255L, l >> 16 & 255L, l >> 8 & 255L, l & 255L);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof MacAddress && ((MacAddress)object).mAddr == this.mAddr;
        return bl;
    }

    public int getAddressType() {
        if (this.equals(BROADCAST_ADDRESS)) {
            return 3;
        }
        if (this.isMulticastAddress()) {
            return 2;
        }
        return 1;
    }

    public Inet6Address getLinkLocalIpv6FromEui48Mac() {
        byte[] arrby = this.toByteArray();
        Object object = new byte[16];
        object[0] = (byte)-2;
        object[1] = (byte)-128;
        object[8] = (byte)(arrby[0] ^ 2);
        object[9] = arrby[1];
        object[10] = arrby[2];
        object[11] = (byte)-1;
        object[12] = (byte)-2;
        object[13] = arrby[3];
        object[14] = arrby[4];
        object[15] = arrby[5];
        try {
            object = Inet6Address.getByAddress(null, object, 0);
            return object;
        }
        catch (UnknownHostException unknownHostException) {
            return null;
        }
    }

    public int hashCode() {
        long l = this.mAddr;
        return (int)(l ^ l >> 32);
    }

    public boolean isLocallyAssigned() {
        boolean bl = (this.mAddr & LOCALLY_ASSIGNED_MASK) != 0L;
        return bl;
    }

    public boolean isMulticastAddress() {
        boolean bl = (this.mAddr & MULTICAST_MASK) != 0L;
        return bl;
    }

    public boolean matches(MacAddress macAddress, MacAddress macAddress2) {
        Preconditions.checkNotNull(macAddress);
        Preconditions.checkNotNull(macAddress2);
        long l = this.mAddr;
        long l2 = macAddress2.mAddr;
        boolean bl = (l & l2) == (l2 & macAddress.mAddr);
        return bl;
    }

    public byte[] toByteArray() {
        return MacAddress.byteAddrFromLongAddr(this.mAddr);
    }

    public String toOuiString() {
        return String.format("%02x:%02x:%02x", this.mAddr >> 40 & 255L, this.mAddr >> 32 & 255L, this.mAddr >> 24 & 255L);
    }

    public String toString() {
        return MacAddress.stringAddrFromLongAddr(this.mAddr);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mAddr);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MacAddressType {
    }

}

