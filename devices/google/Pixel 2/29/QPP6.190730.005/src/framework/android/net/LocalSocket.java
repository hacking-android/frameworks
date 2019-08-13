/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.Credentials;
import android.net.LocalSocketAddress;
import android.net.LocalSocketImpl;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LocalSocket
implements Closeable {
    public static final int SOCKET_DGRAM = 1;
    public static final int SOCKET_SEQPACKET = 3;
    public static final int SOCKET_STREAM = 2;
    static final int SOCKET_UNKNOWN = 0;
    @UnsupportedAppUsage
    private final LocalSocketImpl impl;
    private volatile boolean implCreated;
    private boolean isBound;
    private boolean isConnected;
    private LocalSocketAddress localAddress;
    private final int sockType;

    public LocalSocket() {
        this(2);
    }

    public LocalSocket(int n) {
        this(new LocalSocketImpl(), n);
    }

    private LocalSocket(LocalSocketImpl localSocketImpl, int n) {
        this.impl = localSocketImpl;
        this.sockType = n;
        this.isConnected = false;
        this.isBound = false;
    }

    private static LocalSocket createConnectedLocalSocket(LocalSocketImpl object, int n) {
        object = new LocalSocket((LocalSocketImpl)object, n);
        ((LocalSocket)object).isConnected = true;
        ((LocalSocket)object).isBound = true;
        ((LocalSocket)object).implCreated = true;
        return object;
    }

    public static LocalSocket createConnectedLocalSocket(FileDescriptor fileDescriptor) {
        return LocalSocket.createConnectedLocalSocket(new LocalSocketImpl(fileDescriptor), 0);
    }

    static LocalSocket createLocalSocketForAccept(LocalSocketImpl localSocketImpl) {
        return LocalSocket.createConnectedLocalSocket(localSocketImpl, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void implCreateIfNeeded() throws IOException {
        if (this.implCreated) return;
        synchronized (this) {
            boolean bl = this.implCreated;
            if (bl) return;
            try {
                this.impl.create(this.sockType);
            }
            finally {
                this.implCreated = true;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void bind(LocalSocketAddress object) throws IOException {
        this.implCreateIfNeeded();
        synchronized (this) {
            if (!this.isBound) {
                this.localAddress = object;
                this.impl.bind(this.localAddress);
                this.isBound = true;
                return;
            }
            object = new IOException("already bound");
            throw object;
        }
    }

    @Override
    public void close() throws IOException {
        this.implCreateIfNeeded();
        this.impl.close();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void connect(LocalSocketAddress object) throws IOException {
        synchronized (this) {
            if (!this.isConnected) {
                this.implCreateIfNeeded();
                this.impl.connect((LocalSocketAddress)object, 0);
                this.isConnected = true;
                this.isBound = true;
                return;
            }
            object = new IOException("already connected");
            throw object;
        }
    }

    public void connect(LocalSocketAddress localSocketAddress, int n) throws IOException {
        throw new UnsupportedOperationException();
    }

    public FileDescriptor[] getAncillaryFileDescriptors() throws IOException {
        return this.impl.getAncillaryFileDescriptors();
    }

    public FileDescriptor getFileDescriptor() {
        return this.impl.getFileDescriptor();
    }

    public InputStream getInputStream() throws IOException {
        this.implCreateIfNeeded();
        return this.impl.getInputStream();
    }

    public LocalSocketAddress getLocalSocketAddress() {
        return this.localAddress;
    }

    public OutputStream getOutputStream() throws IOException {
        this.implCreateIfNeeded();
        return this.impl.getOutputStream();
    }

    public Credentials getPeerCredentials() throws IOException {
        return this.impl.getPeerCredentials();
    }

    public int getReceiveBufferSize() throws IOException {
        return (Integer)this.impl.getOption(4098);
    }

    public LocalSocketAddress getRemoteSocketAddress() {
        throw new UnsupportedOperationException();
    }

    public int getSendBufferSize() throws IOException {
        return (Integer)this.impl.getOption(4097);
    }

    public int getSoTimeout() throws IOException {
        return (Integer)this.impl.getOption(4102);
    }

    public boolean isBound() {
        synchronized (this) {
            boolean bl = this.isBound;
            return bl;
        }
    }

    public boolean isClosed() {
        throw new UnsupportedOperationException();
    }

    public boolean isConnected() {
        synchronized (this) {
            boolean bl = this.isConnected;
            return bl;
        }
    }

    public boolean isInputShutdown() {
        throw new UnsupportedOperationException();
    }

    public boolean isOutputShutdown() {
        throw new UnsupportedOperationException();
    }

    public void setFileDescriptorsForSend(FileDescriptor[] arrfileDescriptor) {
        this.impl.setFileDescriptorsForSend(arrfileDescriptor);
    }

    public void setReceiveBufferSize(int n) throws IOException {
        this.impl.setOption(4098, n);
    }

    public void setSendBufferSize(int n) throws IOException {
        this.impl.setOption(4097, n);
    }

    public void setSoTimeout(int n) throws IOException {
        this.impl.setOption(4102, n);
    }

    public void shutdownInput() throws IOException {
        this.implCreateIfNeeded();
        this.impl.shutdownInput();
    }

    public void shutdownOutput() throws IOException {
        this.implCreateIfNeeded();
        this.impl.shutdownOutput();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(" impl:");
        stringBuilder.append(this.impl);
        return stringBuilder.toString();
    }
}

