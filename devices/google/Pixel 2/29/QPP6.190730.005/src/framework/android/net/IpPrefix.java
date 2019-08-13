/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.net.NetworkUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Comparator;

public final class IpPrefix
implements Parcelable {
    public static final Parcelable.Creator<IpPrefix> CREATOR = new Parcelable.Creator<IpPrefix>(){

        @Override
        public IpPrefix createFromParcel(Parcel parcel) {
            return new IpPrefix(parcel.createByteArray(), parcel.readInt());
        }

        public IpPrefix[] newArray(int n) {
            return new IpPrefix[n];
        }
    };
    private final byte[] address;
    private final int prefixLength;

    @SystemApi
    public IpPrefix(String object) {
        object = NetworkUtils.parseIpAndMask((String)object);
        this.address = ((InetAddress)((Pair)object).first).getAddress();
        this.prefixLength = (Integer)((Pair)object).second;
        this.checkAndMaskAddressAndPrefixLength();
    }

    @SystemApi
    public IpPrefix(InetAddress inetAddress, int n) {
        this.address = inetAddress.getAddress();
        this.prefixLength = n;
        this.checkAndMaskAddressAndPrefixLength();
    }

    public IpPrefix(byte[] arrby, int n) {
        this.address = (byte[])arrby.clone();
        this.prefixLength = n;
        this.checkAndMaskAddressAndPrefixLength();
    }

    private void checkAndMaskAddressAndPrefixLength() {
        Object object = this.address;
        if (((byte[])object).length != 4 && ((byte[])object).length != 16) {
            object = new StringBuilder();
            ((StringBuilder)object).append("IpPrefix has ");
            ((StringBuilder)object).append(this.address.length);
            ((StringBuilder)object).append(" bytes which is neither 4 nor 16");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        NetworkUtils.maskRawAddress(this.address, this.prefixLength);
    }

    public static Comparator<IpPrefix> lengthComparator() {
        return new Comparator<IpPrefix>(){

            @Override
            public int compare(IpPrefix arrby, IpPrefix arrby2) {
                int n;
                int n2;
                if (arrby.isIPv4()) {
                    if (arrby2.isIPv6()) {
                        return -1;
                    }
                } else if (arrby2.isIPv4()) {
                    return 1;
                }
                if ((n = arrby.getPrefixLength()) < (n2 = arrby2.getPrefixLength())) {
                    return -1;
                }
                if (n2 < n) {
                    return 1;
                }
                n2 = (arrby = ((IpPrefix)arrby).address).length < (arrby2 = ((IpPrefix)arrby2).address).length ? arrby.length : arrby2.length;
                for (n = 0; n < n2; ++n) {
                    if (arrby[n] < arrby2[n]) {
                        return -1;
                    }
                    if (arrby[n] <= arrby2[n]) continue;
                    return 1;
                }
                if (arrby2.length < n2) {
                    return 1;
                }
                if (arrby.length < n2) {
                    return -1;
                }
                return 0;
            }
        };
    }

    public boolean contains(InetAddress arrby) {
        if ((arrby = arrby.getAddress()) != null && arrby.length == this.address.length) {
            NetworkUtils.maskRawAddress(arrby, this.prefixLength);
            return Arrays.equals(this.address, arrby);
        }
        return false;
    }

    public boolean containsPrefix(IpPrefix arrby) {
        if (arrby.getPrefixLength() < this.prefixLength) {
            return false;
        }
        arrby = arrby.getRawAddress();
        NetworkUtils.maskRawAddress(arrby, this.prefixLength);
        return Arrays.equals(arrby, this.address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof IpPrefix;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (IpPrefix)object;
        bl = bl2;
        if (Arrays.equals(this.address, ((IpPrefix)object).address)) {
            bl = bl2;
            if (this.prefixLength == ((IpPrefix)object).prefixLength) {
                bl = true;
            }
        }
        return bl;
    }

    public InetAddress getAddress() {
        try {
            InetAddress inetAddress = InetAddress.getByAddress(this.address);
            return inetAddress;
        }
        catch (UnknownHostException unknownHostException) {
            throw new IllegalArgumentException("Address is invalid");
        }
    }

    public int getPrefixLength() {
        return this.prefixLength;
    }

    public byte[] getRawAddress() {
        return (byte[])this.address.clone();
    }

    public int hashCode() {
        return Arrays.hashCode(this.address) + this.prefixLength * 11;
    }

    public boolean isIPv4() {
        return this.getAddress() instanceof Inet4Address;
    }

    public boolean isIPv6() {
        return this.getAddress() instanceof Inet6Address;
    }

    public String toString() {
        try {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(InetAddress.getByAddress(this.address).getHostAddress());
            charSequence.append("/");
            charSequence.append(this.prefixLength);
            charSequence = charSequence.toString();
            return charSequence;
        }
        catch (UnknownHostException unknownHostException) {
            throw new IllegalStateException("IpPrefix with invalid address! Shouldn't happen.", unknownHostException);
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.address);
        parcel.writeInt(this.prefixLength);
    }

}

