/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.ProxyInfo;
import android.net.StaticIpConfiguration;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class IpConfiguration
implements Parcelable {
    public static final Parcelable.Creator<IpConfiguration> CREATOR = new Parcelable.Creator<IpConfiguration>(){

        @Override
        public IpConfiguration createFromParcel(Parcel parcel) {
            IpConfiguration ipConfiguration = new IpConfiguration();
            ipConfiguration.ipAssignment = IpAssignment.valueOf(parcel.readString());
            ipConfiguration.proxySettings = ProxySettings.valueOf(parcel.readString());
            ipConfiguration.staticIpConfiguration = (StaticIpConfiguration)parcel.readParcelable(null);
            ipConfiguration.httpProxy = (ProxyInfo)parcel.readParcelable(null);
            return ipConfiguration;
        }

        public IpConfiguration[] newArray(int n) {
            return new IpConfiguration[n];
        }
    };
    private static final String TAG = "IpConfiguration";
    @UnsupportedAppUsage
    public ProxyInfo httpProxy;
    public IpAssignment ipAssignment;
    public ProxySettings proxySettings;
    public StaticIpConfiguration staticIpConfiguration;

    public IpConfiguration() {
        this.init(IpAssignment.UNASSIGNED, ProxySettings.UNASSIGNED, null, null);
    }

    @UnsupportedAppUsage
    public IpConfiguration(IpAssignment ipAssignment, ProxySettings proxySettings, StaticIpConfiguration staticIpConfiguration, ProxyInfo proxyInfo) {
        this.init(ipAssignment, proxySettings, staticIpConfiguration, proxyInfo);
    }

    public IpConfiguration(IpConfiguration ipConfiguration) {
        this();
        if (ipConfiguration != null) {
            this.init(ipConfiguration.ipAssignment, ipConfiguration.proxySettings, ipConfiguration.staticIpConfiguration, ipConfiguration.httpProxy);
        }
    }

    private void init(IpAssignment object, ProxySettings proxySettings, StaticIpConfiguration staticIpConfiguration, ProxyInfo proxyInfo) {
        this.ipAssignment = object;
        this.proxySettings = proxySettings;
        proxySettings = null;
        object = staticIpConfiguration == null ? null : new StaticIpConfiguration(staticIpConfiguration);
        this.staticIpConfiguration = object;
        object = proxyInfo == null ? proxySettings : new ProxyInfo(proxyInfo);
        this.httpProxy = object;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof IpConfiguration)) {
            return false;
        }
        object = (IpConfiguration)object;
        if (this.ipAssignment != ((IpConfiguration)object).ipAssignment || this.proxySettings != ((IpConfiguration)object).proxySettings || !Objects.equals(this.staticIpConfiguration, ((IpConfiguration)object).staticIpConfiguration) || !Objects.equals(this.httpProxy, ((IpConfiguration)object).httpProxy)) {
            bl = false;
        }
        return bl;
    }

    public ProxyInfo getHttpProxy() {
        return this.httpProxy;
    }

    public IpAssignment getIpAssignment() {
        return this.ipAssignment;
    }

    public ProxySettings getProxySettings() {
        return this.proxySettings;
    }

    public StaticIpConfiguration getStaticIpConfiguration() {
        return this.staticIpConfiguration;
    }

    public int hashCode() {
        StaticIpConfiguration staticIpConfiguration = this.staticIpConfiguration;
        int n = staticIpConfiguration != null ? staticIpConfiguration.hashCode() : 0;
        return n + 13 + this.ipAssignment.ordinal() * 17 + this.proxySettings.ordinal() * 47 + this.httpProxy.hashCode() * 83;
    }

    public void setHttpProxy(ProxyInfo proxyInfo) {
        this.httpProxy = proxyInfo;
    }

    public void setIpAssignment(IpAssignment ipAssignment) {
        this.ipAssignment = ipAssignment;
    }

    public void setProxySettings(ProxySettings proxySettings) {
        this.proxySettings = proxySettings;
    }

    public void setStaticIpConfiguration(StaticIpConfiguration staticIpConfiguration) {
        this.staticIpConfiguration = staticIpConfiguration;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("IP assignment: ");
        stringBuilder2.append(this.ipAssignment.toString());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("\n");
        if (this.staticIpConfiguration != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Static configuration: ");
            stringBuilder2.append(this.staticIpConfiguration.toString());
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder.append("\n");
        }
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Proxy settings: ");
        stringBuilder2.append(this.proxySettings.toString());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("\n");
        if (this.httpProxy != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("HTTP proxy: ");
            stringBuilder2.append(this.httpProxy.toString());
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.ipAssignment.name());
        parcel.writeString(this.proxySettings.name());
        parcel.writeParcelable(this.staticIpConfiguration, n);
        parcel.writeParcelable(this.httpProxy, n);
    }

    public static enum IpAssignment {
        STATIC,
        DHCP,
        UNASSIGNED;
        
    }

    public static enum ProxySettings {
        NONE,
        STATIC,
        UNASSIGNED,
        PAC;
        
    }

}

