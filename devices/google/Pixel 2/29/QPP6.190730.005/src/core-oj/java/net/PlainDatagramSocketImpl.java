/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.OsConstants
 *  android.system.StructGroupReq
 *  libcore.io.IoBridge
 *  libcore.io.Libcore
 *  libcore.io.Os
 *  libcore.util.EmptyArray
 */
package java.net;

import android.system.ErrnoException;
import android.system.OsConstants;
import android.system.StructGroupReq;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.net.AbstractPlainDatagramSocketImpl;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketOption;
import jdk.net.ExtendedSocketOptions;
import jdk.net.SocketFlow;
import libcore.io.IoBridge;
import libcore.io.Libcore;
import libcore.io.Os;
import libcore.util.EmptyArray;
import sun.net.ExtendedOptionsImpl;

class PlainDatagramSocketImpl
extends AbstractPlainDatagramSocketImpl {
    PlainDatagramSocketImpl() {
    }

    private void doRecv(DatagramPacket datagramPacket, int n) throws IOException {
        if (!this.isClosed()) {
            if (this.timeout != 0) {
                IoBridge.poll((FileDescriptor)this.fd, (int)(OsConstants.POLLIN | OsConstants.POLLERR), (int)this.timeout);
            }
            IoBridge.recvfrom((boolean)false, (FileDescriptor)this.fd, (byte[])datagramPacket.getData(), (int)datagramPacket.getOffset(), (int)datagramPacket.bufLength, (int)n, (DatagramPacket)datagramPacket, (boolean)this.connected);
            return;
        }
        throw new SocketException("Socket closed");
    }

    private static StructGroupReq makeGroupReq(InetAddress inetAddress, NetworkInterface networkInterface) {
        int n = networkInterface != null ? networkInterface.getIndex() : 0;
        return new StructGroupReq(n, inetAddress);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void bind0(int n, InetAddress serializable) throws SocketException {
        synchronized (this) {
            if (this.isClosed()) {
                SocketException socketException = new SocketException("Socket closed");
                throw socketException;
            }
            IoBridge.bind((FileDescriptor)this.fd, (InetAddress)serializable, (int)n);
            this.localPort = n == 0 ? IoBridge.getLocalInetSocketAddress((FileDescriptor)this.fd).getPort() : n;
            return;
        }
    }

    @Override
    protected void connect0(InetAddress inetAddress, int n) throws SocketException {
        if (!this.isClosed()) {
            IoBridge.connect((FileDescriptor)this.fd, (InetAddress)inetAddress, (int)n);
            return;
        }
        throw new SocketException("Socket closed");
    }

    @Override
    protected void datagramSocketClose() {
        try {
            IoBridge.closeAndSignalBlockedThreads((FileDescriptor)this.fd);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    protected void datagramSocketCreate() throws SocketException {
        this.fd = IoBridge.socket((int)OsConstants.AF_INET6, (int)OsConstants.SOCK_DGRAM, (int)0);
        IoBridge.setSocketOption((FileDescriptor)this.fd, (int)32, (Object)true);
        try {
            Libcore.os.setsockoptInt(this.fd, OsConstants.IPPROTO_IP, OsConstants.IP_MULTICAST_ALL, 0);
            return;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsSocketException();
        }
    }

    @Override
    protected void disconnect0(int n) {
        if (this.isClosed()) {
            return;
        }
        InetAddress inetAddress = new InetAddress();
        inetAddress.holder().family = OsConstants.AF_UNSPEC;
        try {
            IoBridge.connect((FileDescriptor)this.fd, (InetAddress)inetAddress, (int)0);
        }
        catch (SocketException socketException) {
            // empty catch block
        }
    }

    @Override
    protected <T> T getOption(SocketOption<T> object) throws IOException {
        if (!object.equals(ExtendedSocketOptions.SO_FLOW_SLA)) {
            return super.getOption(object);
        }
        if (!this.isClosed()) {
            ExtendedOptionsImpl.checkGetOptionPermission(object);
            object = SocketFlow.create();
            ExtendedOptionsImpl.getFlowOption(this.getFileDescriptor(), (SocketFlow)object);
            return (T)object;
        }
        throw new SocketException("Socket closed");
    }

    @Override
    protected byte getTTL() throws IOException {
        return (byte)this.getTimeToLive();
    }

    @Override
    protected int getTimeToLive() throws IOException {
        return (Integer)IoBridge.getSocketOption((FileDescriptor)this.fd, (int)17);
    }

    @Override
    protected void join(InetAddress inetAddress, NetworkInterface networkInterface) throws IOException {
        if (!this.isClosed()) {
            IoBridge.setSocketOption((FileDescriptor)this.fd, (int)19, (Object)PlainDatagramSocketImpl.makeGroupReq(inetAddress, networkInterface));
            return;
        }
        throw new SocketException("Socket closed");
    }

    @Override
    protected void leave(InetAddress inetAddress, NetworkInterface networkInterface) throws IOException {
        if (!this.isClosed()) {
            IoBridge.setSocketOption((FileDescriptor)this.fd, (int)20, (Object)PlainDatagramSocketImpl.makeGroupReq(inetAddress, networkInterface));
            return;
        }
        throw new SocketException("Socket closed");
    }

    @Override
    protected int peek(InetAddress inetAddress) throws IOException {
        synchronized (this) {
            DatagramPacket datagramPacket = new DatagramPacket(EmptyArray.BYTE, 0);
            this.doRecv(datagramPacket, OsConstants.MSG_PEEK);
            inetAddress.holder().address = datagramPacket.getAddress().holder().address;
            int n = datagramPacket.getPort();
            return n;
        }
    }

    @Override
    protected int peekData(DatagramPacket datagramPacket) throws IOException {
        synchronized (this) {
            this.doRecv(datagramPacket, OsConstants.MSG_PEEK);
            int n = datagramPacket.getPort();
            return n;
        }
    }

    @Override
    protected void receive0(DatagramPacket datagramPacket) throws IOException {
        synchronized (this) {
            this.doRecv(datagramPacket, 0);
            return;
        }
    }

    @Override
    protected void send(DatagramPacket datagramPacket) throws IOException {
        if (!this.isClosed()) {
            if (datagramPacket.getData() != null && datagramPacket.getAddress() != null) {
                int n = this.connected ? 0 : datagramPacket.getPort();
                InetAddress inetAddress = this.connected ? null : datagramPacket.getAddress();
                IoBridge.sendto((FileDescriptor)this.fd, (byte[])datagramPacket.getData(), (int)datagramPacket.getOffset(), (int)datagramPacket.getLength(), (int)0, (InetAddress)inetAddress, (int)n);
                return;
            }
            throw new NullPointerException("null buffer || null address");
        }
        throw new SocketException("Socket closed");
    }

    @Override
    protected <T> void setOption(SocketOption<T> socketOption, T t) throws IOException {
        block4 : {
            block3 : {
                block2 : {
                    if (socketOption.equals(ExtendedSocketOptions.SO_FLOW_SLA)) break block2;
                    super.setOption(socketOption, t);
                    break block3;
                }
                if (this.isClosed()) break block4;
                ExtendedOptionsImpl.checkSetOptionPermission(socketOption);
                ExtendedOptionsImpl.checkValueType(t, SocketFlow.class);
                ExtendedOptionsImpl.setFlowOption(this.getFileDescriptor(), (SocketFlow)t);
            }
            return;
        }
        throw new SocketException("Socket closed");
    }

    @Override
    protected void setTTL(byte by) throws IOException {
        this.setTimeToLive(by & 255);
    }

    @Override
    protected void setTimeToLive(int n) throws IOException {
        IoBridge.setSocketOption((FileDescriptor)this.fd, (int)17, (Object)n);
    }

    @Override
    protected Object socketGetOption(int n) throws SocketException {
        if (!this.isClosed()) {
            return IoBridge.getSocketOption((FileDescriptor)this.fd, (int)n);
        }
        throw new SocketException("Socket closed");
    }

    @Override
    protected void socketSetOption(int n, Object object) throws SocketException {
        SocketException socketException2;
        block2 : {
            try {
                this.socketSetOption0(n, object);
            }
            catch (SocketException socketException2) {
                if (!this.connected) break block2;
            }
            return;
        }
        throw socketException2;
    }

    protected void socketSetOption0(int n, Object object) throws SocketException {
        if (!this.isClosed()) {
            IoBridge.setSocketOption((FileDescriptor)this.fd, (int)n, (Object)object);
            return;
        }
        throw new SocketException("Socket closed");
    }
}

