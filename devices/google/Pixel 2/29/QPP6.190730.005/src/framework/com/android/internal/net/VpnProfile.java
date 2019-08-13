/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.net;

import android.annotation.UnsupportedAppUsage;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class VpnProfile
implements Cloneable,
Parcelable {
    public static final Parcelable.Creator<VpnProfile> CREATOR = new Parcelable.Creator<VpnProfile>(){

        @Override
        public VpnProfile createFromParcel(Parcel parcel) {
            return new VpnProfile(parcel);
        }

        public VpnProfile[] newArray(int n) {
            return new VpnProfile[n];
        }
    };
    public static final int PROXY_MANUAL = 1;
    public static final int PROXY_NONE = 0;
    private static final String TAG = "VpnProfile";
    public static final int TYPE_IPSEC_HYBRID_RSA = 5;
    public static final int TYPE_IPSEC_XAUTH_PSK = 3;
    public static final int TYPE_IPSEC_XAUTH_RSA = 4;
    public static final int TYPE_L2TP_IPSEC_PSK = 1;
    public static final int TYPE_L2TP_IPSEC_RSA = 2;
    public static final int TYPE_MAX = 5;
    public static final int TYPE_PPTP = 0;
    public String dnsServers;
    public String ipsecCaCert;
    public String ipsecIdentifier;
    public String ipsecSecret;
    public String ipsecServerCert;
    public String ipsecUserCert;
    @UnsupportedAppUsage
    public final String key;
    public String l2tpSecret;
    public boolean mppe;
    @UnsupportedAppUsage
    public String name = "";
    public String password;
    public ProxyInfo proxy;
    public String routes;
    @UnsupportedAppUsage
    public boolean saveLogin;
    public String searchDomains;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public String server;
    @UnsupportedAppUsage
    public int type;
    @UnsupportedAppUsage
    public String username;

    @UnsupportedAppUsage
    public VpnProfile(Parcel parcel) {
        boolean bl = false;
        this.type = 0;
        this.server = "";
        this.username = "";
        this.password = "";
        this.dnsServers = "";
        this.searchDomains = "";
        this.routes = "";
        this.mppe = true;
        this.l2tpSecret = "";
        this.ipsecIdentifier = "";
        this.ipsecSecret = "";
        this.ipsecUserCert = "";
        this.ipsecCaCert = "";
        this.ipsecServerCert = "";
        this.proxy = null;
        this.saveLogin = false;
        this.key = parcel.readString();
        this.name = parcel.readString();
        this.type = parcel.readInt();
        this.server = parcel.readString();
        this.username = parcel.readString();
        this.password = parcel.readString();
        this.dnsServers = parcel.readString();
        this.searchDomains = parcel.readString();
        this.routes = parcel.readString();
        boolean bl2 = parcel.readInt() != 0;
        this.mppe = bl2;
        this.l2tpSecret = parcel.readString();
        this.ipsecIdentifier = parcel.readString();
        this.ipsecSecret = parcel.readString();
        this.ipsecUserCert = parcel.readString();
        this.ipsecCaCert = parcel.readString();
        this.ipsecServerCert = parcel.readString();
        bl2 = bl;
        if (parcel.readInt() != 0) {
            bl2 = true;
        }
        this.saveLogin = bl2;
        this.proxy = (ProxyInfo)parcel.readParcelable(null);
    }

    public VpnProfile(String string2) {
        this.type = 0;
        this.server = "";
        this.username = "";
        this.password = "";
        this.dnsServers = "";
        this.searchDomains = "";
        this.routes = "";
        this.mppe = true;
        this.l2tpSecret = "";
        this.ipsecIdentifier = "";
        this.ipsecSecret = "";
        this.ipsecUserCert = "";
        this.ipsecCaCert = "";
        this.ipsecServerCert = "";
        this.proxy = null;
        this.saveLogin = false;
        this.key = string2;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static VpnProfile decode(String object, byte[] object2) {
        if (object == null) {
            return null;
        }
        try {
            String string2 = new String((byte[])object2, StandardCharsets.UTF_8);
            String[] arrstring = string2.split("\u0000", -1);
            if (arrstring.length < 14) return null;
            if (arrstring.length > 19) {
                return null;
            }
            VpnProfile vpnProfile = new VpnProfile((String)object);
            boolean bl = false;
            vpnProfile.name = arrstring[0];
            vpnProfile.type = Integer.parseInt(arrstring[1]);
            if (vpnProfile.type < 0) return null;
            if (vpnProfile.type > 5) {
                return null;
            }
            vpnProfile.server = arrstring[2];
            vpnProfile.username = arrstring[3];
            vpnProfile.password = arrstring[4];
            vpnProfile.dnsServers = arrstring[5];
            vpnProfile.searchDomains = arrstring[6];
            vpnProfile.routes = arrstring[7];
            vpnProfile.mppe = Boolean.parseBoolean(arrstring[8]);
            vpnProfile.l2tpSecret = arrstring[9];
            vpnProfile.ipsecIdentifier = arrstring[10];
            vpnProfile.ipsecSecret = arrstring[11];
            vpnProfile.ipsecUserCert = arrstring[12];
            vpnProfile.ipsecCaCert = arrstring[13];
            int n = arrstring.length;
            Object object3 = "";
            object = n > 14 ? arrstring[14] : "";
            vpnProfile.ipsecServerCert = object;
            if (arrstring.length > 15) {
                object = arrstring.length > 15 ? arrstring[15] : "";
                object2 = arrstring.length > 16 ? arrstring[16] : "";
                string2 = arrstring.length > 17 ? arrstring[17] : "";
                if (arrstring.length > 18) {
                    object3 = arrstring[18];
                }
                if (((String)object3).isEmpty()) {
                    n = ((String)object2).isEmpty() ? 0 : Integer.parseInt((String)object2);
                    vpnProfile.proxy = object3 = new ProxyInfo((String)object, n, string2);
                } else {
                    vpnProfile.proxy = object = new ProxyInfo((String)object3);
                }
            }
            if (!vpnProfile.username.isEmpty() || !vpnProfile.password.isEmpty()) {
                bl = true;
            }
            vpnProfile.saveLogin = bl;
            return vpnProfile;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public boolean areDnsAddressesNumeric() {
        int n;
        String[] arrstring;
        try {
            arrstring = this.dnsServers.split(" +");
            n = arrstring.length;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            InetAddress.parseNumericAddress((String)arrstring[i]);
            continue;
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] encode() {
        StringBuilder stringBuilder = new StringBuilder(this.name);
        stringBuilder.append('\u0000');
        stringBuilder.append(this.type);
        stringBuilder.append('\u0000');
        stringBuilder.append(this.server);
        stringBuilder.append('\u0000');
        boolean bl = this.saveLogin;
        String string2 = "";
        String string3 = bl ? this.username : "";
        stringBuilder.append(string3);
        stringBuilder.append('\u0000');
        string3 = this.saveLogin ? this.password : "";
        stringBuilder.append(string3);
        stringBuilder.append('\u0000');
        stringBuilder.append(this.dnsServers);
        stringBuilder.append('\u0000');
        stringBuilder.append(this.searchDomains);
        stringBuilder.append('\u0000');
        stringBuilder.append(this.routes);
        stringBuilder.append('\u0000');
        stringBuilder.append(this.mppe);
        stringBuilder.append('\u0000');
        stringBuilder.append(this.l2tpSecret);
        stringBuilder.append('\u0000');
        stringBuilder.append(this.ipsecIdentifier);
        stringBuilder.append('\u0000');
        stringBuilder.append(this.ipsecSecret);
        stringBuilder.append('\u0000');
        stringBuilder.append(this.ipsecUserCert);
        stringBuilder.append('\u0000');
        stringBuilder.append(this.ipsecCaCert);
        stringBuilder.append('\u0000');
        stringBuilder.append(this.ipsecServerCert);
        if (this.proxy != null) {
            stringBuilder.append('\u0000');
            string3 = this.proxy.getHost() != null ? this.proxy.getHost() : "";
            stringBuilder.append(string3);
            stringBuilder.append('\u0000');
            stringBuilder.append(this.proxy.getPort());
            stringBuilder.append('\u0000');
            string3 = this.proxy.getExclusionListAsString() != null ? this.proxy.getExclusionListAsString() : string2;
            stringBuilder.append(string3);
            stringBuilder.append('\u0000');
            stringBuilder.append(this.proxy.getPacFileUrl().toString());
        }
        return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }

    public boolean hasDns() {
        return TextUtils.isEmpty(this.dnsServers) ^ true;
    }

    public boolean isServerAddressNumeric() {
        try {
            InetAddress.parseNumericAddress((String)this.server);
            return true;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    public boolean isTypeValidForLockdown() {
        boolean bl = this.type != 0;
        return bl;
    }

    public boolean isValidLockdownProfile() {
        boolean bl = this.isTypeValidForLockdown() && this.isServerAddressNumeric() && this.hasDns() && this.areDnsAddressesNumeric();
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.key);
        parcel.writeString(this.name);
        parcel.writeInt(this.type);
        parcel.writeString(this.server);
        parcel.writeString(this.username);
        parcel.writeString(this.password);
        parcel.writeString(this.dnsServers);
        parcel.writeString(this.searchDomains);
        parcel.writeString(this.routes);
        parcel.writeInt((int)this.mppe);
        parcel.writeString(this.l2tpSecret);
        parcel.writeString(this.ipsecIdentifier);
        parcel.writeString(this.ipsecSecret);
        parcel.writeString(this.ipsecUserCert);
        parcel.writeString(this.ipsecCaCert);
        parcel.writeString(this.ipsecServerCert);
        parcel.writeInt((int)this.saveLogin);
        parcel.writeParcelable(this.proxy, n);
    }

}

