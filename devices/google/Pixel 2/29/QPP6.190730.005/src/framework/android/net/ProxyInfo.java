/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.Proxy;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.List;
import java.util.Locale;

public class ProxyInfo
implements Parcelable {
    public static final Parcelable.Creator<ProxyInfo> CREATOR = new Parcelable.Creator<ProxyInfo>(){

        @Override
        public ProxyInfo createFromParcel(Parcel parcel) {
            String string2 = null;
            int n = 0;
            if (parcel.readByte() != 0) {
                return new ProxyInfo(Uri.CREATOR.createFromParcel(parcel), parcel.readInt());
            }
            if (parcel.readByte() != 0) {
                string2 = parcel.readString();
                n = parcel.readInt();
            }
            return new ProxyInfo(string2, n, parcel.readString(), parcel.readStringArray());
        }

        public ProxyInfo[] newArray(int n) {
            return new ProxyInfo[n];
        }
    };
    public static final String LOCAL_EXCL_LIST = "";
    public static final String LOCAL_HOST = "localhost";
    public static final int LOCAL_PORT = -1;
    private final String mExclusionList;
    private final String mHost;
    private final Uri mPacFileUrl;
    private final String[] mParsedExclusionList;
    private final int mPort;

    public ProxyInfo(ProxyInfo proxyInfo) {
        if (proxyInfo != null) {
            this.mHost = proxyInfo.getHost();
            this.mPort = proxyInfo.getPort();
            this.mPacFileUrl = proxyInfo.mPacFileUrl;
            this.mExclusionList = proxyInfo.getExclusionListAsString();
            this.mParsedExclusionList = proxyInfo.mParsedExclusionList;
        } else {
            this.mHost = null;
            this.mPort = 0;
            this.mExclusionList = null;
            this.mParsedExclusionList = null;
            this.mPacFileUrl = Uri.EMPTY;
        }
    }

    public ProxyInfo(Uri uri) {
        this.mHost = LOCAL_HOST;
        this.mPort = -1;
        this.mExclusionList = LOCAL_EXCL_LIST;
        this.mParsedExclusionList = ProxyInfo.parseExclusionList(this.mExclusionList);
        if (uri != null) {
            this.mPacFileUrl = uri;
            return;
        }
        throw new NullPointerException();
    }

    public ProxyInfo(Uri uri, int n) {
        this.mHost = LOCAL_HOST;
        this.mPort = n;
        this.mExclusionList = LOCAL_EXCL_LIST;
        this.mParsedExclusionList = ProxyInfo.parseExclusionList(this.mExclusionList);
        if (uri != null) {
            this.mPacFileUrl = uri;
            return;
        }
        throw new NullPointerException();
    }

    public ProxyInfo(String string2) {
        this.mHost = LOCAL_HOST;
        this.mPort = -1;
        this.mExclusionList = LOCAL_EXCL_LIST;
        this.mParsedExclusionList = ProxyInfo.parseExclusionList(this.mExclusionList);
        this.mPacFileUrl = Uri.parse(string2);
    }

    @UnsupportedAppUsage
    public ProxyInfo(String string2, int n, String string3) {
        this.mHost = string2;
        this.mPort = n;
        this.mExclusionList = string3;
        this.mParsedExclusionList = ProxyInfo.parseExclusionList(this.mExclusionList);
        this.mPacFileUrl = Uri.EMPTY;
    }

    private ProxyInfo(String string2, int n, String string3, String[] arrstring) {
        this.mHost = string2;
        this.mPort = n;
        this.mExclusionList = string3;
        this.mParsedExclusionList = arrstring;
        this.mPacFileUrl = Uri.EMPTY;
    }

    public static ProxyInfo buildDirectProxy(String string2, int n) {
        return new ProxyInfo(string2, n, null);
    }

    public static ProxyInfo buildDirectProxy(String string2, int n, List<String> arrobject) {
        arrobject = arrobject.toArray(new String[arrobject.size()]);
        return new ProxyInfo(string2, n, TextUtils.join((CharSequence)",", arrobject), (String[])arrobject);
    }

    public static ProxyInfo buildPacProxy(Uri uri) {
        return new ProxyInfo(uri);
    }

    private static String[] parseExclusionList(String string2) {
        if (string2 == null) {
            return new String[0];
        }
        return string2.toLowerCase(Locale.ROOT).split(",");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof ProxyInfo;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (ProxyInfo)object;
        if (!Uri.EMPTY.equals(this.mPacFileUrl)) {
            bl = bl2;
            if (this.mPacFileUrl.equals(((ProxyInfo)object).getPacFileUrl())) {
                bl = bl2;
                if (this.mPort == ((ProxyInfo)object).mPort) {
                    bl = true;
                }
            }
            return bl;
        }
        if (!Uri.EMPTY.equals(((ProxyInfo)object).mPacFileUrl)) {
            return false;
        }
        String string2 = this.mExclusionList;
        if (string2 != null && !string2.equals(((ProxyInfo)object).getExclusionListAsString())) {
            return false;
        }
        if (this.mHost != null && ((ProxyInfo)object).getHost() != null && !this.mHost.equals(((ProxyInfo)object).getHost())) {
            return false;
        }
        if (this.mHost != null && ((ProxyInfo)object).mHost == null) {
            return false;
        }
        if (this.mHost == null && ((ProxyInfo)object).mHost != null) {
            return false;
        }
        return this.mPort == ((ProxyInfo)object).mPort;
    }

    public String[] getExclusionList() {
        return this.mParsedExclusionList;
    }

    public String getExclusionListAsString() {
        return this.mExclusionList;
    }

    public String getHost() {
        return this.mHost;
    }

    public Uri getPacFileUrl() {
        return this.mPacFileUrl;
    }

    public int getPort() {
        return this.mPort;
    }

    public InetSocketAddress getSocketAddress() {
        InetSocketAddress inetSocketAddress = null;
        try {
            InetSocketAddress inetSocketAddress2;
            inetSocketAddress = inetSocketAddress2 = new InetSocketAddress(this.mHost, this.mPort);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        return inetSocketAddress;
    }

    public int hashCode() {
        String string2 = this.mHost;
        int n = 0;
        int n2 = string2 == null ? 0 : string2.hashCode();
        string2 = this.mExclusionList;
        if (string2 != null) {
            n = string2.hashCode();
        }
        return n2 + n + this.mPort;
    }

    public boolean isValid() {
        int n;
        boolean bl = Uri.EMPTY.equals(this.mPacFileUrl);
        boolean bl2 = true;
        if (!bl) {
            return true;
        }
        String string2 = this.mHost;
        String string3 = LOCAL_EXCL_LIST;
        String string4 = string2;
        if (string2 == null) {
            string4 = LOCAL_EXCL_LIST;
        }
        string2 = (n = this.mPort) == 0 ? LOCAL_EXCL_LIST : Integer.toString(n);
        String string5 = this.mExclusionList;
        if (string5 != null) {
            string3 = string5;
        }
        if (Proxy.validate(string4, string2, string3) != 0) {
            bl2 = false;
        }
        return bl2;
    }

    public java.net.Proxy makeProxy() {
        java.net.Proxy proxy = java.net.Proxy.NO_PROXY;
        String string2 = this.mHost;
        java.net.Proxy proxy2 = proxy;
        if (string2 != null) {
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(string2, this.mPort);
                proxy2 = new java.net.Proxy(Proxy.Type.HTTP, inetSocketAddress);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                proxy2 = proxy;
            }
        }
        return proxy2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!Uri.EMPTY.equals(this.mPacFileUrl)) {
            stringBuilder.append("PAC Script: ");
            stringBuilder.append(this.mPacFileUrl);
        }
        if (this.mHost != null) {
            stringBuilder.append("[");
            stringBuilder.append(this.mHost);
            stringBuilder.append("] ");
            stringBuilder.append(Integer.toString(this.mPort));
            if (this.mExclusionList != null) {
                stringBuilder.append(" xl=");
                stringBuilder.append(this.mExclusionList);
            }
        } else {
            stringBuilder.append("[ProxyProperties.mHost == null]");
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (!Uri.EMPTY.equals(this.mPacFileUrl)) {
            parcel.writeByte((byte)1);
            this.mPacFileUrl.writeToParcel(parcel, 0);
            parcel.writeInt(this.mPort);
            return;
        }
        parcel.writeByte((byte)0);
        if (this.mHost != null) {
            parcel.writeByte((byte)1);
            parcel.writeString(this.mHost);
            parcel.writeInt(this.mPort);
        } else {
            parcel.writeByte((byte)0);
        }
        parcel.writeString(this.mExclusionList);
        parcel.writeStringArray(this.mParsedExclusionList);
    }

}

