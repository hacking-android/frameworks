/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.NetworkUtils;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public final class RouteInfo
implements Parcelable {
    public static final Parcelable.Creator<RouteInfo> CREATOR = new Parcelable.Creator<RouteInfo>(){

        @Override
        public RouteInfo createFromParcel(Parcel parcel) {
            IpPrefix ipPrefix = (IpPrefix)parcel.readParcelable(null);
            Object object = null;
            Object object2 = parcel.createByteArray();
            try {
                object = object2 = InetAddress.getByAddress(object2);
            }
            catch (UnknownHostException unknownHostException) {}
            return new RouteInfo(ipPrefix, (InetAddress)object, parcel.readString(), parcel.readInt());
        }

        public RouteInfo[] newArray(int n) {
            return new RouteInfo[n];
        }
    };
    @SystemApi
    public static final int RTN_THROW = 9;
    @SystemApi
    public static final int RTN_UNICAST = 1;
    @SystemApi
    public static final int RTN_UNREACHABLE = 7;
    private final IpPrefix mDestination;
    @UnsupportedAppUsage
    private final InetAddress mGateway;
    private final boolean mHasGateway;
    private final String mInterface;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final boolean mIsHost;
    private final int mType;

    public RouteInfo(IpPrefix ipPrefix) {
        this(ipPrefix, null, null);
    }

    public RouteInfo(IpPrefix ipPrefix, int n) {
        this(ipPrefix, null, null, n);
    }

    public RouteInfo(IpPrefix ipPrefix, InetAddress inetAddress) {
        this(ipPrefix, inetAddress, null);
    }

    @UnsupportedAppUsage
    public RouteInfo(IpPrefix ipPrefix, InetAddress inetAddress, String string2) {
        this(ipPrefix, inetAddress, string2, 1);
    }

    @SystemApi
    public RouteInfo(IpPrefix object, InetAddress inetAddress, String charSequence, int n) {
        if (n != 1 && n != 7 && n != 9) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown route type ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        IpPrefix ipPrefix = object;
        if (object == null) {
            if (inetAddress != null) {
                ipPrefix = inetAddress instanceof Inet4Address ? new IpPrefix(Inet4Address.ANY, 0) : new IpPrefix(Inet6Address.ANY, 0);
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Invalid arguments passed in: ");
                ((StringBuilder)charSequence).append(inetAddress);
                ((StringBuilder)charSequence).append(",");
                ((StringBuilder)charSequence).append(object);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
        }
        object = inetAddress;
        if (inetAddress == null) {
            object = ipPrefix.getAddress() instanceof Inet4Address ? Inet4Address.ANY : Inet6Address.ANY;
        }
        this.mHasGateway = true ^ ((InetAddress)object).isAnyLocalAddress();
        if (ipPrefix.getAddress() instanceof Inet4Address && !(object instanceof Inet4Address) || ipPrefix.getAddress() instanceof Inet6Address && !(object instanceof Inet6Address)) {
            throw new IllegalArgumentException("address family mismatch in RouteInfo constructor");
        }
        this.mDestination = ipPrefix;
        this.mGateway = object;
        this.mInterface = charSequence;
        this.mType = n;
        this.mIsHost = this.isHost();
    }

    public RouteInfo(LinkAddress linkAddress) {
        this(linkAddress, null, null);
    }

    @UnsupportedAppUsage
    public RouteInfo(LinkAddress linkAddress, InetAddress inetAddress) {
        this(linkAddress, inetAddress, null);
    }

    @UnsupportedAppUsage
    public RouteInfo(LinkAddress parcelable, InetAddress inetAddress, String string2) {
        parcelable = parcelable == null ? null : new IpPrefix(parcelable.getAddress(), parcelable.getPrefixLength());
        this((IpPrefix)parcelable, inetAddress, string2);
    }

    @UnsupportedAppUsage
    public RouteInfo(InetAddress inetAddress) {
        this((IpPrefix)null, inetAddress, null);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private boolean isHost() {
        boolean bl = this.mDestination.getAddress() instanceof Inet4Address && this.mDestination.getPrefixLength() == 32 || this.mDestination.getAddress() instanceof Inet6Address && this.mDestination.getPrefixLength() == 128;
        return bl;
    }

    public static RouteInfo makeHostRoute(InetAddress inetAddress, String string2) {
        return RouteInfo.makeHostRoute(inetAddress, null, string2);
    }

    public static RouteInfo makeHostRoute(InetAddress inetAddress, InetAddress inetAddress2, String string2) {
        if (inetAddress == null) {
            return null;
        }
        if (inetAddress instanceof Inet4Address) {
            return new RouteInfo(new IpPrefix(inetAddress, 32), inetAddress2, string2);
        }
        return new RouteInfo(new IpPrefix(inetAddress, 128), inetAddress2, string2);
    }

    @UnsupportedAppUsage
    public static RouteInfo selectBestRoute(Collection<RouteInfo> object, InetAddress inetAddress) {
        if (object != null && inetAddress != null) {
            Collection<RouteInfo> collection = null;
            Iterator<RouteInfo> iterator = object.iterator();
            while (iterator.hasNext()) {
                RouteInfo routeInfo = iterator.next();
                object = collection;
                if (NetworkUtils.addressTypeMatches(routeInfo.mDestination.getAddress(), inetAddress)) {
                    if (collection != null && ((RouteInfo)collection).mDestination.getPrefixLength() >= routeInfo.mDestination.getPrefixLength()) continue;
                    object = collection;
                    if (routeInfo.matches(inetAddress)) {
                        object = routeInfo;
                    }
                }
                collection = object;
            }
            return collection;
        }
        return null;
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
        if (!(object instanceof RouteInfo)) {
            return false;
        }
        if (!(Objects.equals(this.mDestination, ((RouteInfo)(object = (RouteInfo)object)).getDestination()) && Objects.equals(this.mGateway, ((RouteInfo)object).getGateway()) && Objects.equals(this.mInterface, ((RouteInfo)object).getInterface()) && this.mType == ((RouteInfo)object).getType())) {
            bl = false;
        }
        return bl;
    }

    public IpPrefix getDestination() {
        return this.mDestination;
    }

    public LinkAddress getDestinationLinkAddress() {
        return new LinkAddress(this.mDestination.getAddress(), this.mDestination.getPrefixLength());
    }

    public InetAddress getGateway() {
        return this.mGateway;
    }

    public String getInterface() {
        return this.mInterface;
    }

    @SystemApi
    public int getType() {
        return this.mType;
    }

    public boolean hasGateway() {
        return this.mHasGateway;
    }

    public int hashCode() {
        int n = this.mDestination.hashCode();
        Object object = this.mGateway;
        int n2 = 0;
        int n3 = object == null ? 0 : ((InetAddress)object).hashCode() * 47;
        object = this.mInterface;
        if (object != null) {
            n2 = ((String)object).hashCode() * 67;
        }
        return n * 41 + n3 + n2 + this.mType * 71;
    }

    public boolean isDefaultRoute() {
        int n = this.mType;
        boolean bl = true;
        if (n != 1 || this.mDestination.getPrefixLength() != 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isHostRoute() {
        return this.mIsHost;
    }

    public boolean isIPv4Default() {
        boolean bl = this.isDefaultRoute() && this.mDestination.getAddress() instanceof Inet4Address;
        return bl;
    }

    public boolean isIPv6Default() {
        boolean bl = this.isDefaultRoute() && this.mDestination.getAddress() instanceof Inet6Address;
        return bl;
    }

    public boolean matches(InetAddress inetAddress) {
        return this.mDestination.contains(inetAddress);
    }

    public String toString() {
        int n;
        Object object = "";
        Object object2 = this.mDestination;
        if (object2 != null) {
            object = ((IpPrefix)object2).toString();
        }
        if ((n = this.mType) == 7) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(" unreachable");
            object = ((StringBuilder)object2).toString();
        } else if (n == 9) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(" throw");
            object = ((StringBuilder)object2).toString();
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(" ->");
            object = object2 = ((StringBuilder)object2).toString();
            if (this.mGateway != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(" ");
                ((StringBuilder)object).append(this.mGateway.getHostAddress());
                object = ((StringBuilder)object).toString();
            }
            object2 = object;
            if (this.mInterface != null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(" ");
                ((StringBuilder)object2).append(this.mInterface);
                object2 = ((StringBuilder)object2).toString();
            }
            object = object2;
            if (this.mType != 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(" unknown type ");
                ((StringBuilder)object).append(this.mType);
                object = ((StringBuilder)object).toString();
            }
        }
        return object;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mDestination, n);
        Object object = this.mGateway;
        object = object == null ? null : object.getAddress();
        parcel.writeByteArray((byte[])object);
        parcel.writeString(this.mInterface);
        parcel.writeInt(this.mType);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RouteType {
    }

}

