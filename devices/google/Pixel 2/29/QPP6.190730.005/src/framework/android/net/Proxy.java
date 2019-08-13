/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkUtils;
import android.net.PacProxySelector;
import android.net.ProxyInfo;
import android.net.Uri;
import android.text.TextUtils;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Proxy {
    private static final Pattern EXCLLIST_PATTERN;
    private static final String EXCLLIST_REGEXP = "^$|^[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*(,[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*)*$";
    private static final String EXCL_REGEX = "[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*";
    @Deprecated
    public static final String EXTRA_PROXY_INFO = "android.intent.extra.PROXY_INFO";
    private static final Pattern HOSTNAME_PATTERN;
    private static final String HOSTNAME_REGEXP = "^$|^[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*(\\.[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*)*$";
    private static final String NAME_IP_REGEX = "[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*(\\.[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*)*";
    public static final String PROXY_CHANGE_ACTION = "android.intent.action.PROXY_CHANGE";
    public static final int PROXY_EXCLLIST_INVALID = 5;
    public static final int PROXY_HOSTNAME_EMPTY = 1;
    public static final int PROXY_HOSTNAME_INVALID = 2;
    public static final int PROXY_PORT_EMPTY = 3;
    public static final int PROXY_PORT_INVALID = 4;
    public static final int PROXY_VALID = 0;
    private static final String TAG = "Proxy";
    private static ConnectivityManager sConnectivityManager;
    private static final ProxySelector sDefaultProxySelector;

    static {
        sConnectivityManager = null;
        HOSTNAME_PATTERN = Pattern.compile(HOSTNAME_REGEXP);
        EXCLLIST_PATTERN = Pattern.compile(EXCLLIST_REGEXP);
        sDefaultProxySelector = ProxySelector.getDefault();
    }

    @Deprecated
    public static final String getDefaultHost() {
        String string2 = System.getProperty("http.proxyHost");
        if (TextUtils.isEmpty(string2)) {
            return null;
        }
        return string2;
    }

    @Deprecated
    public static final int getDefaultPort() {
        if (Proxy.getDefaultHost() == null) {
            return -1;
        }
        try {
            int n = Integer.parseInt(System.getProperty("http.proxyPort"));
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            return -1;
        }
    }

    @Deprecated
    public static final String getHost(Context object) {
        if ((object = Proxy.getProxy((Context)object, null)) == java.net.Proxy.NO_PROXY) {
            return null;
        }
        try {
            object = ((InetSocketAddress)((java.net.Proxy)object).address()).getHostName();
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Deprecated
    public static final int getPort(Context object) {
        if ((object = Proxy.getProxy((Context)object, null)) == java.net.Proxy.NO_PROXY) {
            return -1;
        }
        try {
            int n = ((InetSocketAddress)((java.net.Proxy)object).address()).getPort();
            return n;
        }
        catch (Exception exception) {
            return -1;
        }
    }

    @UnsupportedAppUsage
    public static final java.net.Proxy getProxy(Context list, String string2) {
        if (string2 != null && !Proxy.isLocalHost("")) {
            list = URI.create(string2);
            list = ProxySelector.getDefault().select((URI)((Object)list));
            if (list.size() > 0) {
                return list.get(0);
            }
        }
        return java.net.Proxy.NO_PROXY;
    }

    private static final boolean isLocalHost(String string2) {
        block5 : {
            if (string2 == null) {
                return false;
            }
            if (!string2.equalsIgnoreCase("localhost")) break block5;
            return true;
        }
        try {
            boolean bl = NetworkUtils.numericToInetAddress(string2).isLoopbackAddress();
            if (bl) {
                return true;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        return false;
    }

    @UnsupportedAppUsage
    public static final void setHttpProxySystemProperty(ProxyInfo proxyInfo) {
        String string2 = null;
        String string3 = null;
        String string4 = null;
        Uri uri = Uri.EMPTY;
        if (proxyInfo != null) {
            string2 = proxyInfo.getHost();
            string3 = Integer.toString(proxyInfo.getPort());
            string4 = proxyInfo.getExclusionListAsString();
            uri = proxyInfo.getPacFileUrl();
        }
        Proxy.setHttpProxySystemProperty(string2, string3, string4, uri);
    }

    public static final void setHttpProxySystemProperty(String string2, String string3, String string4, Uri uri) {
        String string5 = string4;
        if (string4 != null) {
            string5 = string4.replace(",", "|");
        }
        if (string2 != null) {
            System.setProperty("http.proxyHost", string2);
            System.setProperty("https.proxyHost", string2);
        } else {
            System.clearProperty("http.proxyHost");
            System.clearProperty("https.proxyHost");
        }
        if (string3 != null) {
            System.setProperty("http.proxyPort", string3);
            System.setProperty("https.proxyPort", string3);
        } else {
            System.clearProperty("http.proxyPort");
            System.clearProperty("https.proxyPort");
        }
        if (string5 != null) {
            System.setProperty("http.nonProxyHosts", string5);
            System.setProperty("https.nonProxyHosts", string5);
        } else {
            System.clearProperty("http.nonProxyHosts");
            System.clearProperty("https.nonProxyHosts");
        }
        if (!Uri.EMPTY.equals(uri)) {
            ProxySelector.setDefault(new PacProxySelector());
        } else {
            ProxySelector.setDefault(sDefaultProxySelector);
        }
    }

    public static int validate(String string2, String string3, String object) {
        Matcher matcher = HOSTNAME_PATTERN.matcher(string2);
        object = EXCLLIST_PATTERN.matcher((CharSequence)object);
        if (!matcher.matches()) {
            return 2;
        }
        if (!((Matcher)object).matches()) {
            return 5;
        }
        if (string2.length() > 0 && string3.length() == 0) {
            return 3;
        }
        if (string3.length() > 0) {
            if (string2.length() == 0) {
                return 1;
            }
            try {
                int n = Integer.parseInt(string3);
                if (n <= 0 || n > 65535) {
                    return 4;
                }
            }
            catch (NumberFormatException numberFormatException) {
                return 4;
            }
        }
        return 0;
    }
}

