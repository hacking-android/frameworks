/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 */
package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.NetworkUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.system.OsConstants;
import android.util.Pair;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;

public class LinkAddress
implements Parcelable {
    public static final Parcelable.Creator<LinkAddress> CREATOR = new Parcelable.Creator<LinkAddress>(){

        @Override
        public LinkAddress createFromParcel(Parcel parcel) {
            InetAddress inetAddress = null;
            try {
                InetAddress inetAddress2;
                inetAddress = inetAddress2 = InetAddress.getByAddress(parcel.createByteArray());
            }
            catch (UnknownHostException unknownHostException) {
                // empty catch block
            }
            return new LinkAddress(inetAddress, parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public LinkAddress[] newArray(int n) {
            return new LinkAddress[n];
        }
    };
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private InetAddress address;
    private int flags;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int prefixLength;
    private int scope;

    @SystemApi
    public LinkAddress(String string2) {
        this(string2, 0, 0);
        this.scope = LinkAddress.scopeForUnicastAddress(this.address);
    }

    @SystemApi
    public LinkAddress(String object, int n, int n2) {
        object = NetworkUtils.parseIpAndMask((String)object);
        this.init((InetAddress)((Pair)object).first, (Integer)((Pair)object).second, n, n2);
    }

    @SystemApi
    public LinkAddress(InetAddress inetAddress, int n) {
        this(inetAddress, n, 0, 0);
        this.scope = LinkAddress.scopeForUnicastAddress(inetAddress);
    }

    @SystemApi
    public LinkAddress(InetAddress inetAddress, int n, int n2, int n3) {
        this.init(inetAddress, n, n2, n3);
    }

    public LinkAddress(InterfaceAddress interfaceAddress) {
        this(interfaceAddress.getAddress(), interfaceAddress.getNetworkPrefixLength());
    }

    private void init(InetAddress inetAddress, int n, int n2, int n3) {
        if (!(inetAddress == null || inetAddress.isMulticastAddress() || n < 0 || inetAddress instanceof Inet4Address && n > 32 || n > 128)) {
            this.address = inetAddress;
            this.prefixLength = n;
            this.flags = n2;
            this.scope = n3;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad LinkAddress params ");
        stringBuilder.append(inetAddress);
        stringBuilder.append("/");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private boolean isIpv6ULA() {
        boolean bl = this.isIpv6();
        boolean bl2 = false;
        if (bl) {
            if ((this.address.getAddress()[0] & -2) == -4) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    private static int scopeForUnicastAddress(InetAddress inetAddress) {
        if (inetAddress.isAnyLocalAddress()) {
            return OsConstants.RT_SCOPE_HOST;
        }
        if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
            if (!(inetAddress instanceof Inet4Address) && inetAddress.isSiteLocalAddress()) {
                return OsConstants.RT_SCOPE_SITE;
            }
            return OsConstants.RT_SCOPE_UNIVERSE;
        }
        return OsConstants.RT_SCOPE_LINK;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof LinkAddress;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (LinkAddress)object;
        bl = bl2;
        if (this.address.equals(((LinkAddress)object).address)) {
            bl = bl2;
            if (this.prefixLength == ((LinkAddress)object).prefixLength) {
                bl = bl2;
                if (this.flags == ((LinkAddress)object).flags) {
                    bl = bl2;
                    if (this.scope == ((LinkAddress)object).scope) {
                        bl = true;
                    }
                }
            }
        }
        return bl;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    public int getFlags() {
        return this.flags;
    }

    @UnsupportedAppUsage
    public int getNetworkPrefixLength() {
        return this.getPrefixLength();
    }

    public int getPrefixLength() {
        return this.prefixLength;
    }

    public int getScope() {
        return this.scope;
    }

    public int hashCode() {
        return this.address.hashCode() + this.prefixLength * 11 + this.flags * 19 + this.scope * 43;
    }

    @SystemApi
    public boolean isGlobalPreferred() {
        boolean bl = this.scope == OsConstants.RT_SCOPE_UNIVERSE && !this.isIpv6ULA() && (long)(this.flags & (OsConstants.IFA_F_DADFAILED | OsConstants.IFA_F_DEPRECATED)) == 0L && ((long)(this.flags & OsConstants.IFA_F_TENTATIVE) == 0L || (long)(this.flags & OsConstants.IFA_F_OPTIMISTIC) != 0L);
        return bl;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean isIPv6() {
        return this.isIpv6();
    }

    @SystemApi
    public boolean isIpv4() {
        return this.address instanceof Inet4Address;
    }

    @SystemApi
    public boolean isIpv6() {
        return this.address instanceof Inet6Address;
    }

    @SystemApi
    public boolean isSameAddressAs(LinkAddress linkAddress) {
        boolean bl = false;
        if (linkAddress == null) {
            return false;
        }
        boolean bl2 = bl;
        if (this.address.equals(linkAddress.address)) {
            bl2 = bl;
            if (this.prefixLength == linkAddress.prefixLength) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.address.getHostAddress());
        stringBuilder.append("/");
        stringBuilder.append(this.prefixLength);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.address.getAddress());
        parcel.writeInt(this.prefixLength);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.scope);
    }

}

