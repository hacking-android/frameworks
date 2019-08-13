/*
 * Decompiled with CFR 0.145.
 */
package org.apache.http.conn.ssl;

import android.annotation.UnsupportedAppUsage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

@Deprecated
public class SSLSocketFactory
implements LayeredSocketFactory {
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
    public static final String TLS = "TLS";
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private X509HostnameVerifier hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
    @UnsupportedAppUsage
    private final HostNameResolver nameResolver;
    @UnsupportedAppUsage
    private final javax.net.ssl.SSLSocketFactory socketfactory;
    @UnsupportedAppUsage
    private final SSLContext sslcontext;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private SSLSocketFactory() {
        this.sslcontext = null;
        this.socketfactory = HttpsURLConnection.getDefaultSSLSocketFactory();
        this.nameResolver = null;
    }

    public SSLSocketFactory(String arrkeyManager, KeyStore arrtrustManager, String string2, KeyStore keyStore, SecureRandom secureRandom, HostNameResolver hostNameResolver) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        KeyManager[] arrkeyManager2 = arrkeyManager;
        if (arrkeyManager == null) {
            arrkeyManager2 = TLS;
        }
        arrkeyManager = null;
        if (arrtrustManager != null) {
            arrkeyManager = SSLSocketFactory.createKeyManagers((KeyStore)arrtrustManager, string2);
        }
        arrtrustManager = null;
        if (keyStore != null) {
            arrtrustManager = SSLSocketFactory.createTrustManagers(keyStore);
        }
        this.sslcontext = SSLContext.getInstance((String)arrkeyManager2);
        this.sslcontext.init(arrkeyManager, arrtrustManager, secureRandom);
        this.socketfactory = this.sslcontext.getSocketFactory();
        this.nameResolver = hostNameResolver;
    }

    public SSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(TLS, null, null, keyStore, null, null);
    }

    public SSLSocketFactory(KeyStore keyStore, String string2) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(TLS, keyStore, string2, null, null, null);
    }

    public SSLSocketFactory(KeyStore keyStore, String string2, KeyStore keyStore2) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(TLS, keyStore, string2, keyStore2, null, null);
    }

    @UnsupportedAppUsage
    public SSLSocketFactory(javax.net.ssl.SSLSocketFactory sSLSocketFactory) {
        this.sslcontext = null;
        this.socketfactory = sSLSocketFactory;
        this.nameResolver = null;
    }

    @UnsupportedAppUsage
    private static KeyManager[] createKeyManagers(KeyStore keyStore, String object) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        if (keyStore != null) {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            object = object != null ? object.toCharArray() : null;
            keyManagerFactory.init(keyStore, (char[])object);
            return keyManagerFactory.getKeyManagers();
        }
        throw new IllegalArgumentException("Keystore may not be null");
    }

    @UnsupportedAppUsage
    private static TrustManager[] createTrustManagers(KeyStore keyStore) throws KeyStoreException, NoSuchAlgorithmException {
        if (keyStore != null) {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            return trustManagerFactory.getTrustManagers();
        }
        throw new IllegalArgumentException("Keystore may not be null");
    }

    public static SSLSocketFactory getSocketFactory() {
        return NoPreloadHolder.DEFAULT_FACTORY;
    }

    @Override
    public Socket connectSocket(Socket object, String string2, int n, InetAddress inetAddress, int n2, HttpParams httpParams) throws IOException {
        if (string2 != null) {
            if (httpParams != null) {
                int n3;
                if (object == null) {
                    object = this.createSocket();
                }
                SSLSocket sSLSocket = (SSLSocket)object;
                if (inetAddress != null || n2 > 0) {
                    n3 = n2;
                    if (n2 < 0) {
                        n3 = 0;
                    }
                    sSLSocket.bind(new InetSocketAddress(inetAddress, n3));
                }
                n2 = HttpConnectionParams.getConnectionTimeout(httpParams);
                n3 = HttpConnectionParams.getSoTimeout(httpParams);
                object = this.nameResolver;
                object = object != null ? new InetSocketAddress(object.resolve(string2), n) : new InetSocketAddress(string2, n);
                sSLSocket.connect((SocketAddress)object, n2);
                sSLSocket.setSoTimeout(n3);
                try {
                    sSLSocket.startHandshake();
                    this.hostnameVerifier.verify(string2, sSLSocket);
                    return sSLSocket;
                }
                catch (IOException iOException) {
                    try {
                        sSLSocket.close();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    throw iOException;
                }
            }
            throw new IllegalArgumentException("Parameters may not be null.");
        }
        throw new IllegalArgumentException("Target host may not be null.");
    }

    @Override
    public Socket createSocket() throws IOException {
        return (SSLSocket)this.socketfactory.createSocket();
    }

    @Override
    public Socket createSocket(Socket socket, String string2, int n, boolean bl) throws IOException, UnknownHostException {
        socket = (SSLSocket)this.socketfactory.createSocket(socket, string2, n, bl);
        ((SSLSocket)socket).startHandshake();
        this.hostnameVerifier.verify(string2, (SSLSocket)socket);
        return socket;
    }

    public X509HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Override
    public boolean isSecure(Socket socket) throws IllegalArgumentException {
        if (socket != null) {
            if (socket instanceof SSLSocket) {
                if (!socket.isClosed()) {
                    return true;
                }
                throw new IllegalArgumentException("Socket is closed.");
            }
            throw new IllegalArgumentException("Socket not created by this factory.");
        }
        throw new IllegalArgumentException("Socket may not be null.");
    }

    public void setHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        if (x509HostnameVerifier != null) {
            this.hostnameVerifier = x509HostnameVerifier;
            return;
        }
        throw new IllegalArgumentException("Hostname verifier may not be null");
    }

    private static class NoPreloadHolder {
        private static final SSLSocketFactory DEFAULT_FACTORY = new SSLSocketFactory();

        private NoPreloadHolder() {
        }
    }

}

