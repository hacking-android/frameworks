/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AbstractConscryptSocket;
import com.android.org.conscrypt.EmptyArray;
import com.android.org.conscrypt.SSLUtils;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.security.PrivateKey;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public abstract class OpenSSLSocketImpl
extends AbstractConscryptSocket {
    OpenSSLSocketImpl() throws IOException {
    }

    OpenSSLSocketImpl(String string, int n) throws IOException {
        super(string, n);
    }

    OpenSSLSocketImpl(String string, int n, InetAddress inetAddress, int n2) throws IOException {
        super(string, n, inetAddress, n2);
    }

    OpenSSLSocketImpl(InetAddress inetAddress, int n) throws IOException {
        super(inetAddress, n);
    }

    OpenSSLSocketImpl(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        super(inetAddress, n, inetAddress2, n2);
    }

    OpenSSLSocketImpl(Socket socket, String string, int n, boolean bl) throws IOException {
        super(socket, string, n, bl);
    }

    @Deprecated
    @UnsupportedAppUsage
    @Override
    public final byte[] getAlpnSelectedProtocol() {
        return SSLUtils.toProtocolBytes(this.getApplicationProtocol());
    }

    @UnsupportedAppUsage
    @Override
    public abstract byte[] getChannelId() throws SSLException;

    @Override
    public FileDescriptor getFileDescriptor$() {
        return super.getFileDescriptor$();
    }

    @Override
    public abstract SSLSession getHandshakeSession();

    @UnsupportedAppUsage
    @Override
    public String getHostname() {
        return super.getHostname();
    }

    @UnsupportedAppUsage
    @Override
    public String getHostnameOrIP() {
        return super.getHostnameOrIP();
    }

    @Deprecated
    @UnsupportedAppUsage
    @Override
    public final byte[] getNpnSelectedProtocol() {
        return super.getNpnSelectedProtocol();
    }

    @UnsupportedAppUsage
    @Override
    public int getSoWriteTimeout() throws SocketException {
        return super.getSoWriteTimeout();
    }

    @Deprecated
    @UnsupportedAppUsage
    @Override
    public final void setAlpnProtocols(byte[] arrby) {
        if (arrby == null) {
            arrby = EmptyArray.BYTE;
        }
        this.setApplicationProtocols(SSLUtils.decodeProtocols(arrby));
    }

    @Deprecated
    @UnsupportedAppUsage
    @Override
    public final void setAlpnProtocols(String[] arrstring) {
        if (arrstring == null) {
            arrstring = EmptyArray.STRING;
        }
        this.setApplicationProtocols(arrstring);
    }

    @UnsupportedAppUsage
    @Override
    public abstract void setChannelIdEnabled(boolean var1);

    @UnsupportedAppUsage
    @Override
    public abstract void setChannelIdPrivateKey(PrivateKey var1);

    @UnsupportedAppUsage
    @Override
    public void setHandshakeTimeout(int n) throws SocketException {
        super.setHandshakeTimeout(n);
    }

    @UnsupportedAppUsage
    @Override
    public void setHostname(String string) {
        super.setHostname(string);
    }

    @Deprecated
    @UnsupportedAppUsage
    @Override
    public final void setNpnProtocols(byte[] arrby) {
        super.setNpnProtocols(arrby);
    }

    @UnsupportedAppUsage
    @Override
    public void setSoWriteTimeout(int n) throws SocketException {
        super.setSoWriteTimeout(n);
    }

    @UnsupportedAppUsage
    @Override
    public abstract void setUseSessionTickets(boolean var1);
}

