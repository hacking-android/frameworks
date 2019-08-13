/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core.net;

import gov.nist.core.net.NetworkLayer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SslNetworkLayer
implements NetworkLayer {
    private SSLServerSocketFactory sslServerSocketFactory;
    private SSLSocketFactory sslSocketFactory;

    public SslNetworkLayer(String string, String string2, char[] arrc, String object) throws GeneralSecurityException, FileNotFoundException, IOException {
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        Object object2 = KeyManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance((String)object2);
        object2 = KeyManagerFactory.getInstance((String)object2);
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextInt();
        KeyStore keyStore = KeyStore.getInstance((String)object);
        object = KeyStore.getInstance((String)object);
        keyStore.load(new FileInputStream(string2), arrc);
        ((KeyStore)object).load(new FileInputStream(string), arrc);
        trustManagerFactory.init((KeyStore)object);
        ((KeyManagerFactory)object2).init(keyStore, arrc);
        sSLContext.init(((KeyManagerFactory)object2).getKeyManagers(), trustManagerFactory.getTrustManagers(), secureRandom);
        this.sslServerSocketFactory = sSLContext.getServerSocketFactory();
        this.sslSocketFactory = sSLContext.getSocketFactory();
    }

    @Override
    public DatagramSocket createDatagramSocket() throws SocketException {
        return new DatagramSocket();
    }

    @Override
    public DatagramSocket createDatagramSocket(int n, InetAddress inetAddress) throws SocketException {
        return new DatagramSocket(n, inetAddress);
    }

    @Override
    public SSLServerSocket createSSLServerSocket(int n, int n2, InetAddress inetAddress) throws IOException {
        return (SSLServerSocket)this.sslServerSocketFactory.createServerSocket(n, n2, inetAddress);
    }

    @Override
    public SSLSocket createSSLSocket(InetAddress inetAddress, int n) throws IOException {
        return (SSLSocket)this.sslSocketFactory.createSocket(inetAddress, n);
    }

    @Override
    public SSLSocket createSSLSocket(InetAddress inetAddress, int n, InetAddress inetAddress2) throws IOException {
        return (SSLSocket)this.sslSocketFactory.createSocket(inetAddress, n, inetAddress2, 0);
    }

    @Override
    public ServerSocket createServerSocket(int n, int n2, InetAddress inetAddress) throws IOException {
        return new ServerSocket(n, n2, inetAddress);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n) throws IOException {
        return new Socket(inetAddress, n);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n, InetAddress inetAddress2) throws IOException {
        if (inetAddress2 != null) {
            return new Socket(inetAddress, n, inetAddress2, 0);
        }
        return new Socket(inetAddress, n);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n, InetAddress object, int n2) throws IOException {
        if (object != null) {
            return new Socket(inetAddress, n, (InetAddress)object, n2);
        }
        if (n != 0) {
            object = new Socket();
            ((Socket)object).bind(new InetSocketAddress(n));
            ((Socket)object).connect(new InetSocketAddress(inetAddress, n));
            return object;
        }
        return new Socket(inetAddress, n);
    }
}

