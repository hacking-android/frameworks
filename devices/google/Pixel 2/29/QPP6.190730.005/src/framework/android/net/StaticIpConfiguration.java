/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.RouteInfo;
import android.net.shared.InetAddressUtils;
import android.os.Parcel;
import android.os.Parcelable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@SystemApi
public final class StaticIpConfiguration
implements Parcelable {
    public static final Parcelable.Creator<StaticIpConfiguration> CREATOR = new Parcelable.Creator<StaticIpConfiguration>(){

        @Override
        public StaticIpConfiguration createFromParcel(Parcel parcel) {
            return StaticIpConfiguration.readFromParcel(parcel);
        }

        public StaticIpConfiguration[] newArray(int n) {
            return new StaticIpConfiguration[n];
        }
    };
    @UnsupportedAppUsage
    public final ArrayList<InetAddress> dnsServers = new ArrayList();
    @UnsupportedAppUsage
    public String domains;
    @UnsupportedAppUsage
    public InetAddress gateway;
    @UnsupportedAppUsage
    public LinkAddress ipAddress;

    public StaticIpConfiguration() {
    }

    public StaticIpConfiguration(StaticIpConfiguration staticIpConfiguration) {
        this();
        if (staticIpConfiguration != null) {
            this.ipAddress = staticIpConfiguration.ipAddress;
            this.gateway = staticIpConfiguration.gateway;
            this.dnsServers.addAll(staticIpConfiguration.dnsServers);
            this.domains = staticIpConfiguration.domains;
        }
    }

    public static StaticIpConfiguration readFromParcel(Parcel parcel) {
        StaticIpConfiguration staticIpConfiguration = new StaticIpConfiguration();
        staticIpConfiguration.ipAddress = (LinkAddress)parcel.readParcelable(null);
        staticIpConfiguration.gateway = InetAddressUtils.unparcelInetAddress(parcel);
        staticIpConfiguration.dnsServers.clear();
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            staticIpConfiguration.dnsServers.add(InetAddressUtils.unparcelInetAddress(parcel));
        }
        staticIpConfiguration.domains = parcel.readString();
        return staticIpConfiguration;
    }

    public void addDnsServer(InetAddress inetAddress) {
        this.dnsServers.add(inetAddress);
    }

    public void clear() {
        this.ipAddress = null;
        this.gateway = null;
        this.dnsServers.clear();
        this.domains = null;
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
        if (!(object instanceof StaticIpConfiguration)) {
            return false;
        }
        if (!((object = (StaticIpConfiguration)object) != null && Objects.equals(this.ipAddress, ((StaticIpConfiguration)object).ipAddress) && Objects.equals(this.gateway, ((StaticIpConfiguration)object).gateway) && this.dnsServers.equals(((StaticIpConfiguration)object).dnsServers) && Objects.equals(this.domains, ((StaticIpConfiguration)object).domains))) {
            bl = false;
        }
        return bl;
    }

    public List<InetAddress> getDnsServers() {
        return this.dnsServers;
    }

    public String getDomains() {
        return this.domains;
    }

    public InetAddress getGateway() {
        return this.gateway;
    }

    public LinkAddress getIpAddress() {
        return this.ipAddress;
    }

    public List<RouteInfo> getRoutes(String string2) {
        ArrayList<RouteInfo> arrayList = new ArrayList<RouteInfo>(3);
        Object object = this.ipAddress;
        if (object != null) {
            RouteInfo routeInfo = new RouteInfo((LinkAddress)object, null, string2);
            arrayList.add(routeInfo);
            object = this.gateway;
            if (object != null && !routeInfo.matches((InetAddress)object)) {
                arrayList.add(RouteInfo.makeHostRoute(this.gateway, string2));
            }
        }
        if ((object = this.gateway) != null) {
            arrayList.add(new RouteInfo((IpPrefix)null, (InetAddress)object, string2));
        }
        return arrayList;
    }

    public int hashCode() {
        Object object = this.ipAddress;
        int n = 0;
        int n2 = object == null ? 0 : ((LinkAddress)object).hashCode();
        object = this.gateway;
        int n3 = object == null ? 0 : ((InetAddress)object).hashCode();
        object = this.domains;
        if (object != null) {
            n = ((String)object).hashCode();
        }
        return (((13 * 47 + n2) * 47 + n3) * 47 + n) * 47 + this.dnsServers.hashCode();
    }

    public LinkProperties toLinkProperties(String iterator) {
        LinkProperties linkProperties = new LinkProperties();
        linkProperties.setInterfaceName((String)((Object)iterator));
        LinkAddress linkAddress = this.ipAddress;
        if (linkAddress != null) {
            linkProperties.addLinkAddress(linkAddress);
        }
        iterator = this.getRoutes((String)((Object)iterator)).iterator();
        while (iterator.hasNext()) {
            linkProperties.addRoute((RouteInfo)iterator.next());
        }
        iterator = this.dnsServers.iterator();
        while (iterator.hasNext()) {
            linkProperties.addDnsServer(iterator.next());
        }
        linkProperties.setDomains(this.domains);
        return linkProperties;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("IP address ");
        LinkAddress object2 = this.ipAddress;
        if (object2 != null) {
            stringBuffer.append(object2);
            stringBuffer.append(" ");
        }
        stringBuffer.append("Gateway ");
        InetAddress inetAddress = this.gateway;
        if (inetAddress != null) {
            stringBuffer.append(inetAddress.getHostAddress());
            stringBuffer.append(" ");
        }
        stringBuffer.append(" DNS servers: [");
        for (InetAddress inetAddress2 : this.dnsServers) {
            stringBuffer.append(" ");
            stringBuffer.append(inetAddress2.getHostAddress());
        }
        stringBuffer.append(" ] Domains ");
        String string2 = this.domains;
        if (string2 != null) {
            stringBuffer.append(string2);
        }
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.ipAddress, n);
        InetAddressUtils.parcelInetAddress(parcel, this.gateway, n);
        parcel.writeInt(this.dnsServers.size());
        Iterator<InetAddress> iterator = this.dnsServers.iterator();
        while (iterator.hasNext()) {
            InetAddressUtils.parcelInetAddress(parcel, iterator.next(), n);
        }
        parcel.writeString(this.domains);
    }

    public static final class Builder {
        private Iterable<InetAddress> mDnsServers;
        private String mDomains;
        private InetAddress mGateway;
        private LinkAddress mIpAddress;

        public StaticIpConfiguration build() {
            StaticIpConfiguration staticIpConfiguration = new StaticIpConfiguration();
            staticIpConfiguration.ipAddress = this.mIpAddress;
            staticIpConfiguration.gateway = this.mGateway;
            for (InetAddress inetAddress : this.mDnsServers) {
                staticIpConfiguration.dnsServers.add(inetAddress);
            }
            staticIpConfiguration.domains = this.mDomains;
            return staticIpConfiguration;
        }

        public Builder setDnsServers(Iterable<InetAddress> iterable) {
            this.mDnsServers = iterable;
            return this;
        }

        public Builder setDomains(String string2) {
            this.mDomains = string2;
            return this;
        }

        public Builder setGateway(InetAddress inetAddress) {
            this.mGateway = inetAddress;
            return this;
        }

        public Builder setIpAddress(LinkAddress linkAddress) {
            this.mIpAddress = linkAddress;
            return this;
        }
    }

}

