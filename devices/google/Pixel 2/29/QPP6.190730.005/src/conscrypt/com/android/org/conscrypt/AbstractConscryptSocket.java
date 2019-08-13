/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.ApplicationProtocolSelector;
import com.android.org.conscrypt.ApplicationProtocolSelectorAdapter;
import com.android.org.conscrypt.PeerInfoProvider;
import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.Preconditions;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

abstract class AbstractConscryptSocket
extends SSLSocket {
    private final boolean autoClose;
    private final List<HandshakeCompletedListener> listeners = new ArrayList<HandshakeCompletedListener>(2);
    private String peerHostname;
    private final PeerInfoProvider peerInfoProvider = new PeerInfoProvider(){

        @Override
        String getHostname() {
            return AbstractConscryptSocket.this.getHostname();
        }

        @Override
        String getHostnameOrIP() {
            return AbstractConscryptSocket.this.getHostnameOrIP();
        }

        @Override
        int getPort() {
            return AbstractConscryptSocket.this.getPort();
        }
    };
    private final int peerPort;
    private int readTimeoutMilliseconds;
    final Socket socket;

    AbstractConscryptSocket() throws IOException {
        this.socket = this;
        this.peerHostname = null;
        this.peerPort = -1;
        this.autoClose = false;
    }

    AbstractConscryptSocket(String string, int n) throws IOException {
        super(string, n);
        this.socket = this;
        this.peerHostname = string;
        this.peerPort = n;
        this.autoClose = false;
    }

    AbstractConscryptSocket(String string, int n, InetAddress inetAddress, int n2) throws IOException {
        super(string, n, inetAddress, n2);
        this.socket = this;
        this.peerHostname = string;
        this.peerPort = n;
        this.autoClose = false;
    }

    AbstractConscryptSocket(InetAddress inetAddress, int n) throws IOException {
        super(inetAddress, n);
        this.socket = this;
        this.peerHostname = null;
        this.peerPort = -1;
        this.autoClose = false;
    }

    AbstractConscryptSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        super(inetAddress, n, inetAddress2, n2);
        this.socket = this;
        this.peerHostname = null;
        this.peerPort = -1;
        this.autoClose = false;
    }

    AbstractConscryptSocket(Socket socket, String string, int n, boolean bl) throws IOException {
        this.socket = Preconditions.checkNotNull(socket, "socket");
        this.peerHostname = string;
        this.peerPort = n;
        this.autoClose = bl;
    }

    private boolean isDelegating() {
        Socket socket = this.socket;
        boolean bl = socket != null && socket != this;
        return bl;
    }

    @Override
    public void addHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        boolean bl = handshakeCompletedListener != null;
        Preconditions.checkArgument(bl, "Provided listener is null");
        this.listeners.add(handshakeCompletedListener);
    }

    @Override
    public void bind(SocketAddress socketAddress) throws IOException {
        if (this.isDelegating()) {
            this.socket.bind(socketAddress);
        } else {
            super.bind(socketAddress);
        }
    }

    final void checkOpen() throws SocketException {
        if (!this.isClosed()) {
            return;
        }
        throw new SocketException("Socket is closed");
    }

    @Override
    public void close() throws IOException {
        if (this.isDelegating()) {
            if (this.autoClose && !this.socket.isClosed()) {
                this.socket.close();
            }
        } else if (!super.isClosed()) {
            super.close();
        }
    }

    @Override
    public final void connect(SocketAddress socketAddress) throws IOException {
        this.connect(socketAddress, 0);
    }

    @Override
    public final void connect(SocketAddress socketAddress, int n) throws IOException {
        if (this.peerHostname == null && socketAddress instanceof InetSocketAddress) {
            this.peerHostname = Platform.getHostStringFromInetSocketAddress((InetSocketAddress)socketAddress);
        }
        if (this.isDelegating()) {
            this.socket.connect(socketAddress, n);
        } else {
            super.connect(socketAddress, n);
        }
    }

    abstract byte[] exportKeyingMaterial(String var1, byte[] var2, int var3) throws SSLException;

    abstract SSLSession getActiveSession();

    @Deprecated
    @UnsupportedAppUsage
    abstract byte[] getAlpnSelectedProtocol();

    public abstract String getApplicationProtocol();

    @UnsupportedAppUsage
    abstract String[] getApplicationProtocols();

    @Override
    public SocketChannel getChannel() {
        return null;
    }

    @UnsupportedAppUsage
    abstract byte[] getChannelId() throws SSLException;

    public FileDescriptor getFileDescriptor$() {
        if (this.isDelegating()) {
            return Platform.getFileDescriptor(this.socket);
        }
        return Platform.getFileDescriptorFromSSLSocket(this);
    }

    public abstract String getHandshakeApplicationProtocol();

    @Override
    public abstract SSLSession getHandshakeSession();

    @UnsupportedAppUsage
    String getHostname() {
        return this.peerHostname;
    }

    @UnsupportedAppUsage
    String getHostnameOrIP() {
        Object object = this.peerHostname;
        if (object != null) {
            return object;
        }
        object = this.getInetAddress();
        if (object != null) {
            return Platform.getOriginalHostNameFromInetAddress((InetAddress)object);
        }
        return null;
    }

    @Override
    public InetAddress getInetAddress() {
        if (this.isDelegating()) {
            return this.socket.getInetAddress();
        }
        return super.getInetAddress();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (this.isDelegating()) {
            return this.socket.getInputStream();
        }
        return super.getInputStream();
    }

    @Override
    public boolean getKeepAlive() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getKeepAlive();
        }
        return super.getKeepAlive();
    }

    @Override
    public InetAddress getLocalAddress() {
        if (this.isDelegating()) {
            return this.socket.getLocalAddress();
        }
        return super.getLocalAddress();
    }

    @Override
    public int getLocalPort() {
        if (this.isDelegating()) {
            return this.socket.getLocalPort();
        }
        return super.getLocalPort();
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        if (this.isDelegating()) {
            return this.socket.getLocalSocketAddress();
        }
        return super.getLocalSocketAddress();
    }

    @Deprecated
    @UnsupportedAppUsage
    byte[] getNpnSelectedProtocol() {
        return null;
    }

    @Override
    public boolean getOOBInline() throws SocketException {
        return false;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        if (this.isDelegating()) {
            return this.socket.getOutputStream();
        }
        return super.getOutputStream();
    }

    @Override
    public final int getPort() {
        if (this.isDelegating()) {
            return this.socket.getPort();
        }
        int n = this.peerPort;
        if (n != -1) {
            return n;
        }
        return super.getPort();
    }

    @Override
    public int getReceiveBufferSize() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getReceiveBufferSize();
        }
        return super.getReceiveBufferSize();
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        if (this.isDelegating()) {
            return this.socket.getRemoteSocketAddress();
        }
        return super.getRemoteSocketAddress();
    }

    @Override
    public boolean getReuseAddress() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getReuseAddress();
        }
        return super.getReuseAddress();
    }

    @Override
    public int getSendBufferSize() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getSendBufferSize();
        }
        return super.getSendBufferSize();
    }

    @Override
    public int getSoLinger() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getSoLinger();
        }
        return super.getSoLinger();
    }

    @Override
    public final int getSoTimeout() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getSoTimeout();
        }
        return this.readTimeoutMilliseconds;
    }

    @UnsupportedAppUsage
    int getSoWriteTimeout() throws SocketException {
        return 0;
    }

    @Override
    public boolean getTcpNoDelay() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getTcpNoDelay();
        }
        return super.getTcpNoDelay();
    }

    abstract byte[] getTlsUnique();

    @Override
    public int getTrafficClass() throws SocketException {
        if (this.isDelegating()) {
            return this.socket.getTrafficClass();
        }
        return super.getTrafficClass();
    }

    @Override
    public boolean isBound() {
        if (this.isDelegating()) {
            return this.socket.isBound();
        }
        return super.isBound();
    }

    @Override
    public boolean isClosed() {
        if (this.isDelegating()) {
            return this.socket.isClosed();
        }
        return super.isClosed();
    }

    @Override
    public boolean isConnected() {
        if (this.isDelegating()) {
            return this.socket.isConnected();
        }
        return super.isConnected();
    }

    @Override
    public boolean isInputShutdown() {
        if (this.isDelegating()) {
            return this.socket.isInputShutdown();
        }
        return super.isInputShutdown();
    }

    @Override
    public boolean isOutputShutdown() {
        if (this.isDelegating()) {
            return this.socket.isOutputShutdown();
        }
        return super.isOutputShutdown();
    }

    final void notifyHandshakeCompletedListeners() {
        List<HandshakeCompletedListener> list = this.listeners;
        if (list != null && !list.isEmpty()) {
            list = new HandshakeCompletedEvent(this, this.getActiveSession());
            for (HandshakeCompletedListener handshakeCompletedListener : this.listeners) {
                try {
                    handshakeCompletedListener.handshakeCompleted((HandshakeCompletedEvent)((Object)list));
                }
                catch (RuntimeException runtimeException) {
                    Thread thread = Thread.currentThread();
                    thread.getUncaughtExceptionHandler().uncaughtException(thread, runtimeException);
                }
            }
        }
    }

    final PeerInfoProvider peerInfoProvider() {
        return this.peerInfoProvider;
    }

    @Override
    public void removeHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        boolean bl = handshakeCompletedListener != null;
        Preconditions.checkArgument(bl, "Provided listener is null");
        if (this.listeners.remove(handshakeCompletedListener)) {
            return;
        }
        throw new IllegalArgumentException("Provided listener is not registered");
    }

    @Override
    public final void sendUrgentData(int n) throws IOException {
        throw new SocketException("Method sendUrgentData() is not supported.");
    }

    @Deprecated
    @UnsupportedAppUsage
    abstract void setAlpnProtocols(byte[] var1);

    @Deprecated
    @UnsupportedAppUsage
    abstract void setAlpnProtocols(String[] var1);

    abstract void setApplicationProtocolSelector(ApplicationProtocolSelector var1);

    abstract void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter var1);

    @UnsupportedAppUsage
    abstract void setApplicationProtocols(String[] var1);

    @UnsupportedAppUsage
    abstract void setChannelIdEnabled(boolean var1);

    @UnsupportedAppUsage
    abstract void setChannelIdPrivateKey(PrivateKey var1);

    @UnsupportedAppUsage
    void setHandshakeTimeout(int n) throws SocketException {
        throw new SocketException("Method setHandshakeTimeout() is not supported.");
    }

    @UnsupportedAppUsage
    void setHostname(String string) {
        this.peerHostname = string;
    }

    @Override
    public void setKeepAlive(boolean bl) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setKeepAlive(bl);
        } else {
            super.setKeepAlive(bl);
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    void setNpnProtocols(byte[] arrby) {
    }

    @Override
    public final void setOOBInline(boolean bl) throws SocketException {
        throw new SocketException("Method setOOBInline() is not supported.");
    }

    @Override
    public void setPerformancePreferences(int n, int n2, int n3) {
        if (this.isDelegating()) {
            this.socket.setPerformancePreferences(n, n2, n3);
        } else {
            super.setPerformancePreferences(n, n2, n3);
        }
    }

    @Override
    public void setReceiveBufferSize(int n) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setReceiveBufferSize(n);
        } else {
            super.setReceiveBufferSize(n);
        }
    }

    @Override
    public void setReuseAddress(boolean bl) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setReuseAddress(bl);
        } else {
            super.setReuseAddress(bl);
        }
    }

    @Override
    public void setSendBufferSize(int n) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setSendBufferSize(n);
        } else {
            super.setSendBufferSize(n);
        }
    }

    @Override
    public void setSoLinger(boolean bl, int n) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setSoLinger(bl, n);
        } else {
            super.setSoLinger(bl, n);
        }
    }

    @Override
    public final void setSoTimeout(int n) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setSoTimeout(n);
        } else {
            super.setSoTimeout(n);
            this.readTimeoutMilliseconds = n;
        }
    }

    @UnsupportedAppUsage
    void setSoWriteTimeout(int n) throws SocketException {
        throw new SocketException("Method setSoWriteTimeout() is not supported.");
    }

    @Override
    public void setTcpNoDelay(boolean bl) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setTcpNoDelay(bl);
        } else {
            super.setTcpNoDelay(bl);
        }
    }

    @Override
    public void setTrafficClass(int n) throws SocketException {
        if (this.isDelegating()) {
            this.socket.setTrafficClass(n);
        } else {
            super.setTrafficClass(n);
        }
    }

    @UnsupportedAppUsage
    abstract void setUseSessionTickets(boolean var1);

    @Override
    public void shutdownInput() throws IOException {
        if (this.isDelegating()) {
            this.socket.shutdownInput();
        } else {
            super.shutdownInput();
        }
    }

    @Override
    public void shutdownOutput() throws IOException {
        if (this.isDelegating()) {
            this.socket.shutdownOutput();
        } else {
            super.shutdownOutput();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("SSL socket over ");
        if (this.isDelegating()) {
            stringBuilder.append(this.socket.toString());
        } else {
            stringBuilder.append(super.toString());
        }
        return stringBuilder.toString();
    }

}

