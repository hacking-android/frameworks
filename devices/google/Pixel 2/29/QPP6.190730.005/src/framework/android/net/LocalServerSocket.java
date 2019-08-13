/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.LocalSocketImpl;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;

public class LocalServerSocket
implements Closeable {
    private static final int LISTEN_BACKLOG = 50;
    private final LocalSocketImpl impl;
    private final LocalSocketAddress localAddress;

    public LocalServerSocket(FileDescriptor fileDescriptor) throws IOException {
        this.impl = new LocalSocketImpl(fileDescriptor);
        this.impl.listen(50);
        this.localAddress = this.impl.getSockAddress();
    }

    public LocalServerSocket(String string2) throws IOException {
        this.impl = new LocalSocketImpl();
        this.impl.create(2);
        this.localAddress = new LocalSocketAddress(string2);
        this.impl.bind(this.localAddress);
        this.impl.listen(50);
    }

    public LocalSocket accept() throws IOException {
        LocalSocketImpl localSocketImpl = new LocalSocketImpl();
        this.impl.accept(localSocketImpl);
        return LocalSocket.createLocalSocketForAccept(localSocketImpl);
    }

    @Override
    public void close() throws IOException {
        this.impl.close();
    }

    public FileDescriptor getFileDescriptor() {
        return this.impl.getFileDescriptor();
    }

    public LocalSocketAddress getLocalSocketAddress() {
        return this.localAddress;
    }
}

