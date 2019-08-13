/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketOption;
import java.net.SocketOptions;
import java.net.StandardSocketOptions;

public abstract class DatagramSocketImpl
implements SocketOptions {
    protected FileDescriptor fd;
    protected int localPort;
    DatagramSocket socket;

    protected abstract void bind(int var1, InetAddress var2) throws SocketException;

    protected abstract void close();

    protected void connect(InetAddress inetAddress, int n) throws SocketException {
    }

    protected abstract void create() throws SocketException;

    int dataAvailable() {
        return 0;
    }

    protected void disconnect() {
    }

    DatagramSocket getDatagramSocket() {
        return this.socket;
    }

    protected FileDescriptor getFileDescriptor() {
        return this.fd;
    }

    protected int getLocalPort() {
        return this.localPort;
    }

    <T> T getOption(SocketOption<T> socketOption) throws IOException {
        if (socketOption == StandardSocketOptions.SO_SNDBUF) {
            return (T)this.getOption(4097);
        }
        if (socketOption == StandardSocketOptions.SO_RCVBUF) {
            return (T)this.getOption(4098);
        }
        if (socketOption == StandardSocketOptions.SO_REUSEADDR) {
            return (T)this.getOption(4);
        }
        if (socketOption == StandardSocketOptions.IP_TOS) {
            return (T)this.getOption(3);
        }
        if (socketOption == StandardSocketOptions.IP_MULTICAST_IF && this.getDatagramSocket() instanceof MulticastSocket) {
            return (T)this.getOption(31);
        }
        if (socketOption == StandardSocketOptions.IP_MULTICAST_TTL && this.getDatagramSocket() instanceof MulticastSocket) {
            return this.getTimeToLive();
        }
        if (socketOption == StandardSocketOptions.IP_MULTICAST_LOOP && this.getDatagramSocket() instanceof MulticastSocket) {
            return (T)this.getOption(18);
        }
        throw new UnsupportedOperationException("unsupported option");
    }

    @Deprecated
    protected abstract byte getTTL() throws IOException;

    protected abstract int getTimeToLive() throws IOException;

    protected abstract void join(InetAddress var1) throws IOException;

    protected abstract void joinGroup(SocketAddress var1, NetworkInterface var2) throws IOException;

    protected abstract void leave(InetAddress var1) throws IOException;

    protected abstract void leaveGroup(SocketAddress var1, NetworkInterface var2) throws IOException;

    protected abstract int peek(InetAddress var1) throws IOException;

    protected abstract int peekData(DatagramPacket var1) throws IOException;

    protected abstract void receive(DatagramPacket var1) throws IOException;

    protected abstract void send(DatagramPacket var1) throws IOException;

    void setDatagramSocket(DatagramSocket datagramSocket) {
        this.socket = datagramSocket;
    }

    <T> void setOption(SocketOption<T> socketOption, T t) throws IOException {
        block10 : {
            block3 : {
                block8 : {
                    block9 : {
                        block7 : {
                            block6 : {
                                block5 : {
                                    block4 : {
                                        block2 : {
                                            if (socketOption != StandardSocketOptions.SO_SNDBUF) break block2;
                                            this.setOption(4097, t);
                                            break block3;
                                        }
                                        if (socketOption != StandardSocketOptions.SO_RCVBUF) break block4;
                                        this.setOption(4098, t);
                                        break block3;
                                    }
                                    if (socketOption != StandardSocketOptions.SO_REUSEADDR) break block5;
                                    this.setOption(4, t);
                                    break block3;
                                }
                                if (socketOption != StandardSocketOptions.IP_TOS) break block6;
                                this.setOption(3, t);
                                break block3;
                            }
                            if (socketOption != StandardSocketOptions.IP_MULTICAST_IF || !(this.getDatagramSocket() instanceof MulticastSocket)) break block7;
                            this.setOption(31, t);
                            break block3;
                        }
                        if (socketOption != StandardSocketOptions.IP_MULTICAST_TTL || !(this.getDatagramSocket() instanceof MulticastSocket)) break block8;
                        if (!(t instanceof Integer)) break block9;
                        this.setTimeToLive((Integer)t);
                        break block3;
                    }
                    throw new IllegalArgumentException("not an integer");
                }
                if (socketOption != StandardSocketOptions.IP_MULTICAST_LOOP || !(this.getDatagramSocket() instanceof MulticastSocket)) break block10;
                this.setOption(18, t);
            }
            return;
        }
        throw new UnsupportedOperationException("unsupported option");
    }

    @Deprecated
    protected abstract void setTTL(byte var1) throws IOException;

    protected abstract void setTimeToLive(int var1) throws IOException;
}

