/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.ssl.SSLSockets
 *  dalvik.annotation.compat.UnsupportedAppUsage
 *  dalvik.system.SocketTagger
 */
package com.android.okhttp.internal;

import android.net.ssl.SSLSockets;
import com.android.okhttp.Protocol;
import com.android.okhttp.internal.OptionalMethod;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.tls.RealTrustRootIndex;
import com.android.okhttp.internal.tls.TrustRootIndex;
import com.android.okhttp.okio.Buffer;
import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.system.SocketTagger;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

public class Platform {
    private static final OptionalMethod<Socket> GET_ALPN_SELECTED_PROTOCOL;
    private static final AtomicReference<Platform> INSTANCE_HOLDER;
    private static final OptionalMethod<Socket> SET_ALPN_PROTOCOLS;
    private static final OptionalMethod<Socket> SET_HOSTNAME;
    private static final OptionalMethod<Socket> SET_USE_SESSION_TICKETS;

    static {
        INSTANCE_HOLDER = new AtomicReference<Platform>(new Platform());
        SET_USE_SESSION_TICKETS = new OptionalMethod(null, "setUseSessionTickets", Boolean.TYPE);
        SET_HOSTNAME = new OptionalMethod(null, "setHostname", String.class);
        GET_ALPN_SELECTED_PROTOCOL = new OptionalMethod(byte[].class, "getAlpnSelectedProtocol", new Class[0]);
        SET_ALPN_PROTOCOLS = new OptionalMethod(null, "setAlpnProtocols", byte[].class);
    }

    protected Platform() {
    }

    static byte[] concatLengthPrefixed(List<Protocol> list) {
        Buffer buffer = new Buffer();
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            Protocol protocol = list.get(i);
            if (protocol == Protocol.HTTP_1_0) continue;
            buffer.writeByte(protocol.toString().length());
            buffer.writeUtf8(protocol.toString());
        }
        return buffer.readByteArray();
    }

    @UnsupportedAppUsage
    public static Platform get() {
        return INSTANCE_HOLDER.get();
    }

    public static Platform getAndSetForTest(Platform platform) {
        if (platform != null) {
            return INSTANCE_HOLDER.getAndSet(platform);
        }
        throw new NullPointerException();
    }

    private static String[] getProtocolIds(List<Protocol> list) {
        String[] arrstring = new String[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            arrstring[i] = list.get(i).toString();
        }
        return arrstring;
    }

    private static boolean isPlatformSocket(SSLSocket sSLSocket) {
        return sSLSocket.getClass().getName().startsWith("com.android.org.conscrypt");
    }

    private static <T> T readFieldOrNull(Object object, Class<T> class_, String string) {
        for (Class<?> class_2 = object.getClass(); class_2 != Object.class; class_2 = class_2.getSuperclass()) {
            block6 : {
                Object object2 = class_2.getDeclaredField(string);
                ((AccessibleObject)object2).setAccessible(true);
                object2 = ((Field)object2).get(object);
                if (object2 == null) break block6;
                try {
                    if (!class_.isInstance(object2)) break block6;
                    object2 = class_.cast(object2);
                }
                catch (IllegalAccessException illegalAccessException) {
                    throw new AssertionError();
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    continue;
                }
                return (T)object2;
            }
            return null;
        }
        if (!string.equals("delegate") && (object = Platform.readFieldOrNull(object, Object.class, "delegate")) != null) {
            return Platform.readFieldOrNull(object, class_, string);
        }
        return null;
    }

    public void afterHandshake(SSLSocket sSLSocket) {
    }

    public void configureTlsExtensions(SSLSocket sSLSocket, String arrby, List<Protocol> list) {
        SSLParameters sSLParameters = sSLSocket.getSSLParameters();
        if (arrby != null) {
            if (SSLSockets.isSupportedSocket((SSLSocket)sSLSocket)) {
                SSLSockets.setUseSessionTickets((SSLSocket)sSLSocket, (boolean)true);
            } else {
                SET_USE_SESSION_TICKETS.invokeOptionalWithoutCheckedException(sSLSocket, true);
            }
            try {
                SNIHostName sNIHostName = new SNIHostName((String)arrby);
                sSLParameters.setServerNames(Collections.singletonList(sNIHostName));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
            if (!Platform.isPlatformSocket(sSLSocket)) {
                SET_HOSTNAME.invokeOptionalWithoutCheckedException(sSLSocket, new Object[]{arrby});
            }
        }
        sSLParameters.setApplicationProtocols(Platform.getProtocolIds(list));
        if (!Platform.isPlatformSocket(sSLSocket) && SET_ALPN_PROTOCOLS.isSupported(sSLSocket)) {
            arrby = Platform.concatLengthPrefixed(list);
            SET_ALPN_PROTOCOLS.invokeWithoutCheckedException(sSLSocket, new Object[]{arrby});
        }
        sSLSocket.setSSLParameters(sSLParameters);
    }

    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int n) throws IOException {
        socket.connect(inetSocketAddress, n);
    }

    public String getPrefix() {
        return "X-Android";
    }

    public String getSelectedProtocol(SSLSocket arrby) {
        try {
            String string = arrby.getApplicationProtocol();
            return string;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            if (!GET_ALPN_SELECTED_PROTOCOL.isSupported((Socket)arrby)) {
                return null;
            }
            if ((arrby = (byte[])GET_ALPN_SELECTED_PROTOCOL.invokeWithoutCheckedException((Socket)arrby, new Object[0])) != null) {
                return new String(arrby, Util.UTF_8);
            }
            return null;
        }
    }

    @UnsupportedAppUsage
    public void logW(String string) {
        System.logW((String)string);
    }

    public void tagSocket(Socket socket) throws SocketException {
        SocketTagger.get().tag(socket);
    }

    public X509TrustManager trustManager(SSLSocketFactory sSLSocketFactory) {
        Class<?> class_;
        try {
            class_ = Class.forName("com.android.org.conscrypt.SSLParametersImpl");
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException(classNotFoundException);
        }
        return Platform.readFieldOrNull(Platform.readFieldOrNull(sSLSocketFactory, class_, "sslParameters"), X509TrustManager.class, "x509TrustManager");
    }

    public TrustRootIndex trustRootIndex(X509TrustManager x509TrustManager) {
        return new RealTrustRootIndex(x509TrustManager.getAcceptedIssuers());
    }

    public void untagSocket(Socket socket) throws SocketException {
        SocketTagger.get().untag(socket);
    }
}

