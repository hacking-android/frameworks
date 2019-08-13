/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.org.conscrypt.ClientSessionContext
 *  com.android.org.conscrypt.OpenSSLSocketImpl
 *  com.android.org.conscrypt.SSLClientSessionCache
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.SSLSessionCache;
import android.os.SystemProperties;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.RoSystemProperties;
import com.android.org.conscrypt.ClientSessionContext;
import com.android.org.conscrypt.OpenSSLSocketImpl;
import com.android.org.conscrypt.SSLClientSessionCache;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@Deprecated
public class SSLCertificateSocketFactory
extends SSLSocketFactory {
    @UnsupportedAppUsage
    private static final TrustManager[] INSECURE_TRUST_MANAGER = new TrustManager[]{new X509TrustManager(){

        @Override
        public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string2) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string2) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }};
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static final String TAG = "SSLCertificateSocketFactory";
    @UnsupportedAppUsage
    private byte[] mAlpnProtocols;
    @UnsupportedAppUsage
    private PrivateKey mChannelIdPrivateKey;
    @UnsupportedAppUsage
    private final int mHandshakeTimeoutMillis;
    @UnsupportedAppUsage
    private SSLSocketFactory mInsecureFactory;
    @UnsupportedAppUsage
    private KeyManager[] mKeyManagers;
    @UnsupportedAppUsage
    private byte[] mNpnProtocols;
    @UnsupportedAppUsage
    private final boolean mSecure;
    @UnsupportedAppUsage
    private SSLSocketFactory mSecureFactory;
    @UnsupportedAppUsage
    private final SSLClientSessionCache mSessionCache;
    @UnsupportedAppUsage
    private TrustManager[] mTrustManagers;

    @Deprecated
    public SSLCertificateSocketFactory(int n) {
        this(n, null, true);
    }

    @UnsupportedAppUsage
    private SSLCertificateSocketFactory(int n, SSLSessionCache sSLSessionCache, boolean bl) {
        Object var4_4 = null;
        this.mInsecureFactory = null;
        this.mSecureFactory = null;
        this.mTrustManagers = null;
        this.mKeyManagers = null;
        this.mNpnProtocols = null;
        this.mAlpnProtocols = null;
        this.mChannelIdPrivateKey = null;
        this.mHandshakeTimeoutMillis = n;
        sSLSessionCache = sSLSessionCache == null ? var4_4 : sSLSessionCache.mSessionCache;
        this.mSessionCache = sSLSessionCache;
        this.mSecure = bl;
    }

    @UnsupportedAppUsage
    private static OpenSSLSocketImpl castToOpenSSLSocket(Socket socket) {
        if (socket instanceof OpenSSLSocketImpl) {
            return (OpenSSLSocketImpl)socket;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Socket not created by this factory: ");
        stringBuilder.append(socket);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static SocketFactory getDefault(int n) {
        return new SSLCertificateSocketFactory(n, null, true);
    }

    public static SSLSocketFactory getDefault(int n, SSLSessionCache sSLSessionCache) {
        return new SSLCertificateSocketFactory(n, sSLSessionCache, true);
    }

    @UnsupportedAppUsage
    private SSLSocketFactory getDelegate() {
        synchronized (this) {
            block8 : {
                if (!this.mSecure || SSLCertificateSocketFactory.isSslCheckRelaxed()) break block8;
                if (this.mSecureFactory == null) {
                    this.mSecureFactory = this.makeSocketFactory(this.mKeyManagers, this.mTrustManagers);
                }
                SSLSocketFactory sSLSocketFactory = this.mSecureFactory;
                return sSLSocketFactory;
            }
            if (this.mInsecureFactory == null) {
                if (this.mSecure) {
                    Log.w(TAG, "*** BYPASSING SSL SECURITY CHECKS (socket.relaxsslcheck=yes) ***");
                } else {
                    Log.w(TAG, "Bypassing SSL security checks at caller's request");
                }
                this.mInsecureFactory = this.makeSocketFactory(this.mKeyManagers, INSECURE_TRUST_MANAGER);
            }
            SSLSocketFactory sSLSocketFactory = this.mInsecureFactory;
            return sSLSocketFactory;
        }
    }

    @Deprecated
    public static org.apache.http.conn.ssl.SSLSocketFactory getHttpSocketFactory(int n, SSLSessionCache sSLSessionCache) {
        return new org.apache.http.conn.ssl.SSLSocketFactory(new SSLCertificateSocketFactory(n, sSLSessionCache, true));
    }

    public static SSLSocketFactory getInsecure(int n, SSLSessionCache sSLSessionCache) {
        return new SSLCertificateSocketFactory(n, sSLSessionCache, false);
    }

    @UnsupportedAppUsage
    private static boolean isSslCheckRelaxed() {
        boolean bl;
        block0 : {
            boolean bl2 = RoSystemProperties.DEBUGGABLE;
            bl = false;
            if (!bl2 || !SystemProperties.getBoolean("socket.relaxsslcheck", false)) break block0;
            bl = true;
        }
        return bl;
    }

    @UnsupportedAppUsage
    private SSLSocketFactory makeSocketFactory(KeyManager[] object, TrustManager[] arrtrustManager) {
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS", "AndroidOpenSSL");
            sSLContext.init((KeyManager[])object, arrtrustManager, null);
            ((ClientSessionContext)sSLContext.getClientSessionContext()).setPersistentCache(this.mSessionCache);
            object = sSLContext.getSocketFactory();
            return object;
        }
        catch (KeyManagementException | NoSuchAlgorithmException | NoSuchProviderException generalSecurityException) {
            Log.wtf(TAG, generalSecurityException);
            return (SSLSocketFactory)SSLSocketFactory.getDefault();
        }
    }

    @VisibleForTesting
    public static byte[] toLengthPrefixedList(byte[] ... object) {
        if (((byte[][])object).length != 0) {
            Object object2;
            int n;
            int n2 = ((byte[][])object).length;
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                object2 = object[n];
                if (((byte[])object2).length != 0 && ((byte[])object2).length <= 255) {
                    n3 += ((byte[])object2).length + 1;
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("s.length == 0 || s.length > 255: ");
                ((StringBuilder)object).append(((byte[])object2).length);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            byte[] arrby = new byte[n3];
            int n4 = ((byte[][])object).length;
            n = 0;
            for (n3 = 0; n3 < n4; ++n3) {
                object2 = object[n3];
                arrby[n] = (byte)((byte[])object2).length;
                int n5 = ((byte[])object2).length;
                ++n;
                n2 = 0;
                while (n2 < n5) {
                    arrby[n] = object2[n2];
                    ++n2;
                    ++n;
                }
            }
            return arrby;
        }
        throw new IllegalArgumentException("items.length == 0");
    }

    @UnsupportedAppUsage
    public static void verifyHostname(Socket object, String string2) throws IOException {
        if (object instanceof SSLSocket) {
            if (!SSLCertificateSocketFactory.isSslCheckRelaxed()) {
                object = (SSLSocket)object;
                ((SSLSocket)object).startHandshake();
                object = ((SSLSocket)object).getSession();
                if (object != null) {
                    if (!HttpsURLConnection.getDefaultHostnameVerifier().verify(string2, (SSLSession)object)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Cannot verify hostname: ");
                        ((StringBuilder)object).append(string2);
                        throw new SSLPeerUnverifiedException(((StringBuilder)object).toString());
                    }
                } else {
                    throw new SSLException("Cannot verify SSL socket without session");
                }
            }
            return;
        }
        throw new IllegalArgumentException("Attempt to verify non-SSL socket");
    }

    @Override
    public Socket createSocket() throws IOException {
        OpenSSLSocketImpl openSSLSocketImpl = (OpenSSLSocketImpl)this.getDelegate().createSocket();
        openSSLSocketImpl.setNpnProtocols(this.mNpnProtocols);
        openSSLSocketImpl.setAlpnProtocols(this.mAlpnProtocols);
        openSSLSocketImpl.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        openSSLSocketImpl.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        return openSSLSocketImpl;
    }

    @Override
    public Socket createSocket(String string2, int n) throws IOException {
        OpenSSLSocketImpl openSSLSocketImpl = (OpenSSLSocketImpl)this.getDelegate().createSocket(string2, n);
        openSSLSocketImpl.setNpnProtocols(this.mNpnProtocols);
        openSSLSocketImpl.setAlpnProtocols(this.mAlpnProtocols);
        openSSLSocketImpl.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        openSSLSocketImpl.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        if (this.mSecure) {
            SSLCertificateSocketFactory.verifyHostname((Socket)openSSLSocketImpl, string2);
        }
        return openSSLSocketImpl;
    }

    @Override
    public Socket createSocket(String string2, int n, InetAddress inetAddress, int n2) throws IOException {
        inetAddress = (OpenSSLSocketImpl)this.getDelegate().createSocket(string2, n, inetAddress, n2);
        inetAddress.setNpnProtocols(this.mNpnProtocols);
        inetAddress.setAlpnProtocols(this.mAlpnProtocols);
        inetAddress.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        inetAddress.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        if (this.mSecure) {
            SSLCertificateSocketFactory.verifyHostname((Socket)((Object)inetAddress), string2);
        }
        return inetAddress;
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n) throws IOException {
        inetAddress = (OpenSSLSocketImpl)this.getDelegate().createSocket(inetAddress, n);
        inetAddress.setNpnProtocols(this.mNpnProtocols);
        inetAddress.setAlpnProtocols(this.mAlpnProtocols);
        inetAddress.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        inetAddress.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        return inetAddress;
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        inetAddress = (OpenSSLSocketImpl)this.getDelegate().createSocket(inetAddress, n, inetAddress2, n2);
        inetAddress.setNpnProtocols(this.mNpnProtocols);
        inetAddress.setAlpnProtocols(this.mAlpnProtocols);
        inetAddress.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        inetAddress.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        return inetAddress;
    }

    @Override
    public Socket createSocket(Socket socket, String string2, int n, boolean bl) throws IOException {
        socket = (OpenSSLSocketImpl)this.getDelegate().createSocket(socket, string2, n, bl);
        socket.setNpnProtocols(this.mNpnProtocols);
        socket.setAlpnProtocols(this.mAlpnProtocols);
        socket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        socket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        if (this.mSecure) {
            SSLCertificateSocketFactory.verifyHostname(socket, string2);
        }
        return socket;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public byte[] getAlpnSelectedProtocol(Socket socket) {
        return SSLCertificateSocketFactory.castToOpenSSLSocket(socket).getAlpnSelectedProtocol();
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return this.getDelegate().getDefaultCipherSuites();
    }

    public byte[] getNpnSelectedProtocol(Socket socket) {
        return SSLCertificateSocketFactory.castToOpenSSLSocket(socket).getNpnSelectedProtocol();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return this.getDelegate().getSupportedCipherSuites();
    }

    @UnsupportedAppUsage
    public void setAlpnProtocols(byte[][] arrby) {
        this.mAlpnProtocols = SSLCertificateSocketFactory.toLengthPrefixedList(arrby);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setChannelIdPrivateKey(PrivateKey privateKey) {
        this.mChannelIdPrivateKey = privateKey;
    }

    public void setHostname(Socket socket, String string2) {
        SSLCertificateSocketFactory.castToOpenSSLSocket(socket).setHostname(string2);
    }

    public void setKeyManagers(KeyManager[] arrkeyManager) {
        this.mKeyManagers = arrkeyManager;
        this.mSecureFactory = null;
        this.mInsecureFactory = null;
    }

    public void setNpnProtocols(byte[][] arrby) {
        this.mNpnProtocols = SSLCertificateSocketFactory.toLengthPrefixedList(arrby);
    }

    @UnsupportedAppUsage
    public void setSoWriteTimeout(Socket socket, int n) throws SocketException {
        SSLCertificateSocketFactory.castToOpenSSLSocket(socket).setSoWriteTimeout(n);
    }

    public void setTrustManagers(TrustManager[] arrtrustManager) {
        this.mTrustManagers = arrtrustManager;
        this.mSecureFactory = null;
    }

    public void setUseSessionTickets(Socket socket, boolean bl) {
        SSLCertificateSocketFactory.castToOpenSSLSocket(socket).setUseSessionTickets(bl);
    }

}

