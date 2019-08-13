/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.InetAddresses;
import android.net.LinkAddress;
import android.net.RouteInfo;
import android.net.StaticIpConfiguration;
import android.net.shared.InetAddressUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class DhcpResults
implements Parcelable {
    public static final Parcelable.Creator<DhcpResults> CREATOR = new Parcelable.Creator<DhcpResults>(){

        @Override
        public DhcpResults createFromParcel(Parcel parcel) {
            return DhcpResults.readFromParcel(parcel);
        }

        public DhcpResults[] newArray(int n) {
            return new DhcpResults[n];
        }
    };
    private static final String TAG = "DhcpResults";
    @UnsupportedAppUsage
    public final ArrayList<InetAddress> dnsServers;
    @UnsupportedAppUsage
    public String domains;
    @UnsupportedAppUsage
    public InetAddress gateway;
    @UnsupportedAppUsage
    public LinkAddress ipAddress;
    @UnsupportedAppUsage
    public int leaseDuration;
    @UnsupportedAppUsage
    public int mtu;
    @UnsupportedAppUsage
    public Inet4Address serverAddress;
    public String serverHostName;
    @UnsupportedAppUsage
    public String vendorInfo;

    public DhcpResults() {
        this.dnsServers = new ArrayList();
    }

    public DhcpResults(DhcpResults dhcpResults) {
        StaticIpConfiguration staticIpConfiguration = dhcpResults == null ? null : dhcpResults.toStaticIpConfiguration();
        this(staticIpConfiguration);
        if (dhcpResults != null) {
            this.serverAddress = dhcpResults.serverAddress;
            this.vendorInfo = dhcpResults.vendorInfo;
            this.leaseDuration = dhcpResults.leaseDuration;
            this.mtu = dhcpResults.mtu;
            this.serverHostName = dhcpResults.serverHostName;
        }
    }

    public DhcpResults(StaticIpConfiguration staticIpConfiguration) {
        this.dnsServers = new ArrayList();
        if (staticIpConfiguration != null) {
            this.ipAddress = staticIpConfiguration.getIpAddress();
            this.gateway = staticIpConfiguration.getGateway();
            this.dnsServers.addAll(staticIpConfiguration.getDnsServers());
            this.domains = staticIpConfiguration.getDomains();
        }
    }

    private static DhcpResults readFromParcel(Parcel parcel) {
        DhcpResults dhcpResults = new DhcpResults(StaticIpConfiguration.CREATOR.createFromParcel(parcel));
        dhcpResults.leaseDuration = parcel.readInt();
        dhcpResults.mtu = parcel.readInt();
        dhcpResults.serverAddress = (Inet4Address)InetAddressUtils.unparcelInetAddress(parcel);
        dhcpResults.vendorInfo = parcel.readString();
        dhcpResults.serverHostName = parcel.readString();
        return dhcpResults;
    }

    public boolean addDns(String string2) {
        if (!TextUtils.isEmpty(string2)) {
            try {
                this.dnsServers.add(InetAddresses.parseNumericAddress(string2));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("addDns failed with addrString ");
                stringBuilder.append(string2);
                Log.e(TAG, stringBuilder.toString());
                return true;
            }
        }
        return false;
    }

    public void addDnsServer(InetAddress inetAddress) {
        this.dnsServers.add(inetAddress);
    }

    public void clear() {
        this.ipAddress = null;
        this.gateway = null;
        this.dnsServers.clear();
        this.domains = null;
        this.serverAddress = null;
        this.vendorInfo = null;
        this.leaseDuration = 0;
        this.mtu = 0;
        this.serverHostName = null;
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
        if (!(object instanceof DhcpResults)) {
            return false;
        }
        object = (DhcpResults)object;
        if (!(this.toStaticIpConfiguration().equals(((DhcpResults)object).toStaticIpConfiguration()) && Objects.equals(this.serverAddress, ((DhcpResults)object).serverAddress) && Objects.equals(this.vendorInfo, ((DhcpResults)object).vendorInfo) && Objects.equals(this.serverHostName, ((DhcpResults)object).serverHostName) && this.leaseDuration == ((DhcpResults)object).leaseDuration && this.mtu == ((DhcpResults)object).mtu)) {
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

    public int getLeaseDuration() {
        return this.leaseDuration;
    }

    public int getMtu() {
        return this.mtu;
    }

    public List<RouteInfo> getRoutes(String string2) {
        return this.toStaticIpConfiguration().getRoutes(string2);
    }

    public Inet4Address getServerAddress() {
        return this.serverAddress;
    }

    public String getVendorInfo() {
        return this.vendorInfo;
    }

    public boolean hasMeteredHint() {
        String string2 = this.vendorInfo;
        if (string2 != null) {
            return string2.contains("ANDROID_METERED");
        }
        return false;
    }

    public void setDomains(String string2) {
        this.domains = string2;
    }

    public void setGateway(InetAddress inetAddress) {
        this.gateway = inetAddress;
    }

    public boolean setGateway(String string2) {
        try {
            this.gateway = InetAddresses.parseNumericAddress(string2);
            return false;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setGateway failed with addrString ");
            stringBuilder.append(string2);
            Log.e(TAG, stringBuilder.toString());
            return true;
        }
    }

    public void setIpAddress(LinkAddress linkAddress) {
        this.ipAddress = linkAddress;
    }

    public boolean setIpAddress(String string2, int n) {
        try {
            LinkAddress linkAddress;
            Inet4Address inet4Address = (Inet4Address)InetAddresses.parseNumericAddress(string2);
            this.ipAddress = linkAddress = new LinkAddress(inet4Address, n);
            return false;
        }
        catch (ClassCastException | IllegalArgumentException runtimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setIpAddress failed with addrString ");
            stringBuilder.append(string2);
            stringBuilder.append("/");
            stringBuilder.append(n);
            Log.e(TAG, stringBuilder.toString());
            return true;
        }
    }

    public void setLeaseDuration(int n) {
        this.leaseDuration = n;
    }

    public void setMtu(int n) {
        this.mtu = n;
    }

    public void setServerAddress(Inet4Address inet4Address) {
        this.serverAddress = inet4Address;
    }

    public void setVendorInfo(String string2) {
        this.vendorInfo = string2;
    }

    public StaticIpConfiguration toStaticIpConfiguration() {
        return new StaticIpConfiguration.Builder().setIpAddress(this.ipAddress).setGateway(this.gateway).setDnsServers(this.dnsServers).setDomains(this.domains).build();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(super.toString());
        stringBuffer.append(" DHCP server ");
        stringBuffer.append(this.serverAddress);
        stringBuffer.append(" Vendor info ");
        stringBuffer.append(this.vendorInfo);
        stringBuffer.append(" lease ");
        stringBuffer.append(this.leaseDuration);
        stringBuffer.append(" seconds");
        if (this.mtu != 0) {
            stringBuffer.append(" MTU ");
            stringBuffer.append(this.mtu);
        }
        stringBuffer.append(" Servername ");
        stringBuffer.append(this.serverHostName);
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.toStaticIpConfiguration().writeToParcel(parcel, n);
        parcel.writeInt(this.leaseDuration);
        parcel.writeInt(this.mtu);
        InetAddressUtils.parcelInetAddress(parcel, this.serverAddress, n);
        parcel.writeString(this.vendorInfo);
        parcel.writeString(this.serverHostName);
    }

}

