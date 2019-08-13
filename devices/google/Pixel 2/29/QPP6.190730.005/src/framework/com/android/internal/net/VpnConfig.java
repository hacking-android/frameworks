/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.net;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.Network;
import android.net.ProxyInfo;
import android.net.RouteInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VpnConfig
implements Parcelable {
    public static final Parcelable.Creator<VpnConfig> CREATOR = new Parcelable.Creator<VpnConfig>(){

        @Override
        public VpnConfig createFromParcel(Parcel parcel) {
            VpnConfig vpnConfig = new VpnConfig();
            vpnConfig.user = parcel.readString();
            vpnConfig.interfaze = parcel.readString();
            vpnConfig.session = parcel.readString();
            vpnConfig.mtu = parcel.readInt();
            parcel.readTypedList(vpnConfig.addresses, LinkAddress.CREATOR);
            parcel.readTypedList(vpnConfig.routes, RouteInfo.CREATOR);
            vpnConfig.dnsServers = parcel.createStringArrayList();
            vpnConfig.searchDomains = parcel.createStringArrayList();
            vpnConfig.allowedApplications = parcel.createStringArrayList();
            vpnConfig.disallowedApplications = parcel.createStringArrayList();
            vpnConfig.configureIntent = (PendingIntent)parcel.readParcelable(null);
            vpnConfig.startTime = parcel.readLong();
            int n = parcel.readInt();
            boolean bl = true;
            boolean bl2 = n != 0;
            vpnConfig.legacy = bl2;
            bl2 = parcel.readInt() != 0;
            vpnConfig.blocking = bl2;
            bl2 = parcel.readInt() != 0;
            vpnConfig.allowBypass = bl2;
            bl2 = parcel.readInt() != 0;
            vpnConfig.allowIPv4 = bl2;
            bl2 = parcel.readInt() != 0;
            vpnConfig.allowIPv6 = bl2;
            bl2 = parcel.readInt() != 0 ? bl : false;
            vpnConfig.isMetered = bl2;
            vpnConfig.underlyingNetworks = parcel.createTypedArray(Network.CREATOR);
            vpnConfig.proxyInfo = (ProxyInfo)parcel.readParcelable(null);
            return vpnConfig;
        }

        public VpnConfig[] newArray(int n) {
            return new VpnConfig[n];
        }
    };
    public static final String DIALOGS_PACKAGE = "com.android.vpndialogs";
    public static final String LEGACY_VPN = "[Legacy VPN]";
    public static final String SERVICE_INTERFACE = "android.net.VpnService";
    public List<LinkAddress> addresses = new ArrayList<LinkAddress>();
    public boolean allowBypass;
    public boolean allowIPv4;
    public boolean allowIPv6;
    public List<String> allowedApplications;
    public boolean blocking;
    public PendingIntent configureIntent;
    public List<String> disallowedApplications;
    public List<String> dnsServers;
    public String interfaze;
    public boolean isMetered = true;
    public boolean legacy;
    public int mtu = -1;
    public ProxyInfo proxyInfo;
    public List<RouteInfo> routes = new ArrayList<RouteInfo>();
    public List<String> searchDomains;
    public String session;
    public long startTime = -1L;
    public Network[] underlyingNetworks;
    public String user;

    public static Intent getIntentForConfirmation() {
        Intent intent = new Intent();
        ComponentName componentName = ComponentName.unflattenFromString(Resources.getSystem().getString(17039692));
        intent.setClassName(componentName.getPackageName(), componentName.getClassName());
        return intent;
    }

    public static PendingIntent getIntentForStatusPanel(Context context) {
        Intent intent = new Intent();
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.ManageDialog");
        intent.addFlags(1350565888);
        return PendingIntent.getActivityAsUser(context, 0, intent, 0, null, UserHandle.CURRENT);
    }

    public static CharSequence getVpnLabel(Context object, String string2) throws PackageManager.NameNotFoundException {
        object = ((Context)object).getPackageManager();
        Object object2 = new Intent(SERVICE_INTERFACE);
        ((Intent)object2).setPackage(string2);
        object2 = ((PackageManager)object).queryIntentServices((Intent)object2, 0);
        if (object2 != null && object2.size() == 1) {
            return ((ResolveInfo)object2.get(0)).loadLabel((PackageManager)object);
        }
        return ((PackageManager)object).getApplicationInfo(string2, 0).loadLabel((PackageManager)object);
    }

    static <T> String toString(List<T> list) {
        if (list == null) {
            return "null";
        }
        return Arrays.toString(list.toArray());
    }

    public void addLegacyAddresses(String object) {
        if (((String)object).trim().equals("")) {
            return;
        }
        String[] arrstring = ((String)object).trim().split(" ");
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            object = new LinkAddress(arrstring[i]);
            this.addresses.add((LinkAddress)object);
            this.updateAllowedFamilies(((LinkAddress)object).getAddress());
        }
    }

    public void addLegacyRoutes(String arrstring) {
        if (arrstring.trim().equals("")) {
            return;
        }
        arrstring = arrstring.trim().split(" ");
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            RouteInfo routeInfo = new RouteInfo(new IpPrefix(arrstring[i]), null);
            this.routes.add(routeInfo);
            this.updateAllowedFamilies(routeInfo.getDestination().getAddress());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VpnConfig");
        stringBuilder.append("{ user=");
        stringBuilder.append(this.user);
        stringBuilder.append(", interface=");
        stringBuilder.append(this.interfaze);
        stringBuilder.append(", session=");
        stringBuilder.append(this.session);
        stringBuilder.append(", mtu=");
        stringBuilder.append(this.mtu);
        stringBuilder.append(", addresses=");
        stringBuilder.append(VpnConfig.toString(this.addresses));
        stringBuilder.append(", routes=");
        stringBuilder.append(VpnConfig.toString(this.routes));
        stringBuilder.append(", dns=");
        stringBuilder.append(VpnConfig.toString(this.dnsServers));
        stringBuilder.append(", searchDomains=");
        stringBuilder.append(VpnConfig.toString(this.searchDomains));
        stringBuilder.append(", allowedApps=");
        stringBuilder.append(VpnConfig.toString(this.allowedApplications));
        stringBuilder.append(", disallowedApps=");
        stringBuilder.append(VpnConfig.toString(this.disallowedApplications));
        stringBuilder.append(", configureIntent=");
        stringBuilder.append(this.configureIntent);
        stringBuilder.append(", startTime=");
        stringBuilder.append(this.startTime);
        stringBuilder.append(", legacy=");
        stringBuilder.append(this.legacy);
        stringBuilder.append(", blocking=");
        stringBuilder.append(this.blocking);
        stringBuilder.append(", allowBypass=");
        stringBuilder.append(this.allowBypass);
        stringBuilder.append(", allowIPv4=");
        stringBuilder.append(this.allowIPv4);
        stringBuilder.append(", allowIPv6=");
        stringBuilder.append(this.allowIPv6);
        stringBuilder.append(", underlyingNetworks=");
        stringBuilder.append(Arrays.toString(this.underlyingNetworks));
        stringBuilder.append(", proxyInfo=");
        stringBuilder.append(this.proxyInfo.toString());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void updateAllowedFamilies(InetAddress inetAddress) {
        if (inetAddress instanceof Inet4Address) {
            this.allowIPv4 = true;
        } else {
            this.allowIPv6 = true;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.user);
        parcel.writeString(this.interfaze);
        parcel.writeString(this.session);
        parcel.writeInt(this.mtu);
        parcel.writeTypedList(this.addresses);
        parcel.writeTypedList(this.routes);
        parcel.writeStringList(this.dnsServers);
        parcel.writeStringList(this.searchDomains);
        parcel.writeStringList(this.allowedApplications);
        parcel.writeStringList(this.disallowedApplications);
        parcel.writeParcelable(this.configureIntent, n);
        parcel.writeLong(this.startTime);
        parcel.writeInt((int)this.legacy);
        parcel.writeInt((int)this.blocking);
        parcel.writeInt((int)this.allowBypass);
        parcel.writeInt((int)this.allowIPv4);
        parcel.writeInt((int)this.allowIPv6);
        parcel.writeInt((int)this.isMetered);
        parcel.writeTypedArray((Parcelable[])this.underlyingNetworks, n);
        parcel.writeParcelable(this.proxyInfo, n);
    }

}

