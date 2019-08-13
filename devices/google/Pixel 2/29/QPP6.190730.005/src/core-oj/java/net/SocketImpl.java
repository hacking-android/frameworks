/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.SocketOptions;
import java.net.StandardSocketOptions;

public abstract class SocketImpl
implements SocketOptions {
    protected InetAddress address;
    protected FileDescriptor fd;
    protected int localport;
    protected int port;
    ServerSocket serverSocket = null;
    Socket socket = null;

    protected abstract void accept(SocketImpl var1) throws IOException;

    protected abstract int available() throws IOException;

    protected abstract void bind(InetAddress var1, int var2) throws IOException;

    protected abstract void close() throws IOException;

    protected abstract void connect(String var1, int var2) throws IOException;

    protected abstract void connect(InetAddress var1, int var2) throws IOException;

    protected abstract void connect(SocketAddress var1, int var2) throws IOException;

    protected abstract void create(boolean var1) throws IOException;

    public FileDescriptor getFD$() {
        return this.fd;
    }

    protected FileDescriptor getFileDescriptor() {
        return this.fd;
    }

    protected InetAddress getInetAddress() {
        return this.address;
    }

    protected abstract InputStream getInputStream() throws IOException;

    protected int getLocalPort() {
        return this.localport;
    }

    <T> T getOption(SocketOption<T> socketOption) throws IOException {
        if (socketOption == StandardSocketOptions.SO_KEEPALIVE) {
            return (T)this.getOption(8);
        }
        if (socketOption == StandardSocketOptions.SO_SNDBUF) {
            return (T)this.getOption(4097);
        }
        if (socketOption == StandardSocketOptions.SO_RCVBUF) {
            return (T)this.getOption(4098);
        }
        if (socketOption == StandardSocketOptions.SO_REUSEADDR) {
            return (T)this.getOption(4);
        }
        if (socketOption == StandardSocketOptions.SO_LINGER) {
            return (T)this.getOption(128);
        }
        if (socketOption == StandardSocketOptions.IP_TOS) {
            return (T)this.getOption(3);
        }
        if (socketOption == StandardSocketOptions.TCP_NODELAY) {
            return (T)this.getOption(1);
        }
        throw new UnsupportedOperationException("unsupported option");
    }

    protected abstract OutputStream getOutputStream() throws IOException;

    protected int getPort() {
        return this.port;
    }

    ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    Socket getSocket() {
        return this.socket;
    }

    protected abstract void listen(int var1) throws IOException;

    void reset() throws IOException {
        this.address = null;
        this.port = 0;
        this.localport = 0;
    }

    protected abstract void sendUrgentData(int var1) throws IOException;

    <T> void setOption(SocketOption<T> socketOption, T t) throws IOException {
        block9 : {
            block3 : {
                block8 : {
                    block7 : {
                        block6 : {
                            block5 : {
                                block4 : {
                                    block2 : {
                                        if (socketOption != StandardSocketOptions.SO_KEEPALIVE) break block2;
                                        this.setOption(8, t);
                                        break block3;
                                    }
                                    if (socketOption != StandardSocketOptions.SO_SNDBUF) break block4;
                                    this.setOption(4097, t);
                                    break block3;
                                }
                                if (socketOption != StandardSocketOptions.SO_RCVBUF) break block5;
                                this.setOption(4098, t);
                                break block3;
                            }
                            if (socketOption != StandardSocketOptions.SO_REUSEADDR) break block6;
                            this.setOption(4, t);
                            break block3;
                        }
                        if (socketOption != StandardSocketOptions.SO_LINGER) break block7;
                        this.setOption(128, t);
                        break block3;
                    }
                    if (socketOption != StandardSocketOptions.IP_TOS) break block8;
                    this.setOption(3, t);
                    break block3;
                }
                if (socketOption != StandardSocketOptions.TCP_NODELAY) break block9;
                this.setOption(1, t);
            }
            return;
        }
        throw new UnsupportedOperationException("unsupported option");
    }

    protected void setPerformancePreferences(int n, int n2, int n3) {
    }

    void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    void setSocket(Socket socket) {
        this.socket = socket;
    }

    protected void shutdownInput() throws IOException {
        throw new IOException("Method not implemented!");
    }

    protected void shutdownOutput() throws IOException {
        throw new IOException("Method not implemented!");
    }

    protected boolean supportsUrgentData() {
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Socket[addr=");
        stringBuilder.append(this.getInetAddress());
        stringBuilder.append(",port=");
        stringBuilder.append(this.getPort());
        stringBuilder.append(",localport=");
        stringBuilder.append(this.getLocalPort());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

