/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.ServiceManager;
import android.util.Log;
import com.android.net.IProxyService;
import com.google.android.collect.Lists;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PacProxySelector
extends ProxySelector {
    private static final String PROXY = "PROXY ";
    public static final String PROXY_SERVICE = "com.android.net.IProxyService";
    private static final String SOCKS = "SOCKS ";
    private static final String TAG = "PacProxySelector";
    private final List<Proxy> mDefaultList;
    private IProxyService mProxyService = IProxyService.Stub.asInterface(ServiceManager.getService("com.android.net.IProxyService"));

    public PacProxySelector() {
        if (this.mProxyService == null) {
            Log.e(TAG, "PacManager: no proxy service");
        }
        this.mDefaultList = Lists.newArrayList(Proxy.NO_PROXY);
    }

    private static List<Proxy> parseResponse(String arrstring) {
        arrstring = arrstring.split(";");
        ArrayList<Proxy> arrayList = Lists.newArrayList();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            Object object = arrstring[i].trim();
            if (((String)object).equals("DIRECT")) {
                arrayList.add(Proxy.NO_PROXY);
                continue;
            }
            if (((String)object).startsWith(PROXY)) {
                if ((object = PacProxySelector.proxyFromHostPort(Proxy.Type.HTTP, ((String)object).substring(PROXY.length()))) == null) continue;
                arrayList.add((Proxy)object);
                continue;
            }
            if (!((String)object).startsWith(SOCKS) || (object = PacProxySelector.proxyFromHostPort(Proxy.Type.SOCKS, ((String)object).substring(SOCKS.length()))) == null) continue;
            arrayList.add((Proxy)object);
        }
        if (arrayList.size() == 0) {
            arrayList.add(Proxy.NO_PROXY);
        }
        return arrayList;
    }

    private static Proxy proxyFromHostPort(Proxy.Type object, String string2) {
        try {
            String[] arrstring = string2.split(":");
            object = new Proxy((Proxy.Type)((Object)object), InetSocketAddress.createUnresolved(arrstring[0], Integer.parseInt(arrstring[1])));
            return object;
        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException runtimeException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to parse proxy ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(runtimeException);
            Log.d(TAG, ((StringBuilder)object).toString());
            return null;
        }
    }

    @Override
    public void connectFailed(URI uRI, SocketAddress socketAddress, IOException iOException) {
    }

    @Override
    public List<Proxy> select(URI object) {
        Object object2;
        URI uRI;
        Object object3;
        Object var2_5;
        block11 : {
            if (this.mProxyService == null) {
                this.mProxyService = IProxyService.Stub.asInterface(ServiceManager.getService(PROXY_SERVICE));
            }
            if (this.mProxyService == null) {
                Log.e(TAG, "select: no proxy service return NO_PROXY");
                return Lists.newArrayList(Proxy.NO_PROXY);
            }
            var2_5 = null;
            uRI = object;
            object3 = object;
            object2 = object;
            if ("http".equalsIgnoreCase(((URI)object).getScheme())) break block11;
            object3 = object;
            object2 = object;
            object3 = object;
            object2 = object;
            uRI = new URI(((URI)object).getScheme(), null, ((URI)object).getHost(), ((URI)object).getPort(), "/", null, null);
        }
        object3 = uRI;
        object2 = uRI;
        try {
            object = uRI.toURL().toString();
        }
        catch (MalformedURLException malformedURLException) {
            object = ((URI)object3).getHost();
            uRI = object3;
        }
        catch (URISyntaxException uRISyntaxException) {
            object = ((URI)object2).getHost();
            uRI = object2;
        }
        try {
            object = this.mProxyService.resolvePacFile(uRI.getHost(), (String)object);
        }
        catch (Exception exception) {
            Log.e(TAG, "Error resolving PAC File", exception);
            object = var2_5;
        }
        if (object == null) {
            return this.mDefaultList;
        }
        return PacProxySelector.parseResponse((String)object);
    }
}

