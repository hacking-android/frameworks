/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core.net;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public interface NetworkLayer {
    public DatagramSocket createDatagramSocket() throws SocketException;

    public DatagramSocket createDatagramSocket(int var1, InetAddress var2) throws SocketException;

    public SSLServerSocket createSSLServerSocket(int var1, int var2, InetAddress var3) throws IOException;

    public SSLSocket createSSLSocket(InetAddress var1, int var2) throws IOException;

    public SSLSocket createSSLSocket(InetAddress var1, int var2, InetAddress var3) throws IOException;

    public ServerSocket createServerSocket(int var1, int var2, InetAddress var3) throws IOException;

    public Socket createSocket(InetAddress var1, int var2) throws IOException;

    public Socket createSocket(InetAddress var1, int var2, InetAddress var3) throws IOException;

    public Socket createSocket(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException;
}

