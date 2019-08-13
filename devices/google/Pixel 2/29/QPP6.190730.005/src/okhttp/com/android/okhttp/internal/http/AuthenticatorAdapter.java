/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.Authenticator;
import com.android.okhttp.Challenge;
import com.android.okhttp.Credentials;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.Request;
import com.android.okhttp.Response;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.util.List;

public final class AuthenticatorAdapter
implements Authenticator {
    public static final Authenticator INSTANCE = new AuthenticatorAdapter();

    private InetAddress getConnectToInetAddress(Proxy object, HttpUrl httpUrl) throws IOException {
        object = object != null && ((Proxy)object).type() != Proxy.Type.DIRECT ? ((InetSocketAddress)((Proxy)object).address()).getAddress() : InetAddress.getByName(httpUrl.host());
        return object;
    }

    @Override
    public Request authenticate(Proxy object, Response object2) throws IOException {
        List<Challenge> list = ((Response)object2).challenges();
        object2 = ((Response)object2).request();
        HttpUrl httpUrl = ((Request)object2).httpUrl();
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            Object object3 = list.get(i);
            if (!"Basic".equalsIgnoreCase(((Challenge)object3).getScheme()) || (object3 = java.net.Authenticator.requestPasswordAuthentication(httpUrl.host(), this.getConnectToInetAddress((Proxy)object, httpUrl), httpUrl.port(), httpUrl.scheme(), ((Challenge)object3).getRealm(), ((Challenge)object3).getScheme(), httpUrl.url(), Authenticator.RequestorType.SERVER)) == null) {
                continue;
            }
            object = Credentials.basic(((PasswordAuthentication)object3).getUserName(), new String(((PasswordAuthentication)object3).getPassword()));
            return ((Request)object2).newBuilder().header("Authorization", (String)object).build();
        }
        return null;
    }

    @Override
    public Request authenticateProxy(Proxy object, Response object2) throws IOException {
        List<Challenge> list = ((Response)object2).challenges();
        object2 = ((Response)object2).request();
        HttpUrl httpUrl = ((Request)object2).httpUrl();
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            InetSocketAddress inetSocketAddress;
            Object object3 = list.get(i);
            if (!"Basic".equalsIgnoreCase(((Challenge)object3).getScheme()) || (object3 = java.net.Authenticator.requestPasswordAuthentication((inetSocketAddress = (InetSocketAddress)((Proxy)object).address()).getHostName(), this.getConnectToInetAddress((Proxy)object, httpUrl), inetSocketAddress.getPort(), httpUrl.scheme(), ((Challenge)object3).getRealm(), ((Challenge)object3).getScheme(), httpUrl.url(), Authenticator.RequestorType.PROXY)) == null) {
                continue;
            }
            object = Credentials.basic(((PasswordAuthentication)object3).getUserName(), new String(((PasswordAuthentication)object3).getPassword()));
            return ((Request)object2).newBuilder().header("Proxy-Authorization", (String)object).build();
        }
        return null;
    }
}

