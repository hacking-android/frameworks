/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core.net;

import gov.nist.core.net.NetworkLayer;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class DefaultNetworkLayer
implements NetworkLayer {
    public static final DefaultNetworkLayer SINGLETON = new DefaultNetworkLayer();
    private SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
    private SSLSocketFactory sslSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();

    private DefaultNetworkLayer() {
    }

    @Override
    public DatagramSocket createDatagramSocket() throws SocketException {
        return new DatagramSocket();
    }

    @Override
    public DatagramSocket createDatagramSocket(int n, InetAddress inetAddress) throws SocketException {
        if (inetAddress.isMulticastAddress()) {
            try {
                MulticastSocket multicastSocket = new MulticastSocket(n);
                multicastSocket.joinGroup(inetAddress);
                return multicastSocket;
            }
            catch (IOException iOException) {
                throw new SocketException(iOException.getLocalizedMessage());
            }
        }
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

