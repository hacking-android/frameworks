/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.OsConstants
 *  libcore.io.AsynchronousCloseMonitor
 *  libcore.io.IoBridge
 *  libcore.io.IoUtils
 *  libcore.io.Libcore
 *  libcore.io.Os
 */
package java.net;

import android.system.ErrnoException;
import android.system.OsConstants;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.AbstractPlainSocketImpl;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.SocketOption;
import java.net.SocketTimeoutException;
import jdk.net.ExtendedSocketOptions;
import jdk.net.SocketFlow;
import libcore.io.AsynchronousCloseMonitor;
import libcore.io.IoBridge;
import libcore.io.IoUtils;
import libcore.io.Libcore;
import libcore.io.Os;
import sun.net.ExtendedOptionsImpl;

class PlainSocketImpl
extends AbstractPlainSocketImpl {
    PlainSocketImpl() {
        this.fd = new FileDescriptor();
    }

    private FileDescriptor getMarkerFD() throws SocketException {
        FileDescriptor fileDescriptor = new FileDescriptor();
        FileDescriptor fileDescriptor2 = new FileDescriptor();
        try {
            Libcore.os.socketpair(OsConstants.AF_UNIX, OsConstants.SOCK_STREAM, 0, fileDescriptor, fileDescriptor2);
            Libcore.os.shutdown(fileDescriptor, OsConstants.SHUT_RDWR);
            Libcore.os.close(fileDescriptor2);
            return fileDescriptor;
        }
        catch (ErrnoException errnoException) {
            return null;
        }
    }

    @Override
    protected <T> T getOption(SocketOption<T> object) throws IOException {
        if (!object.equals(ExtendedSocketOptions.SO_FLOW_SLA)) {
            return super.getOption(object);
        }
        if (!this.isClosedOrPending()) {
            ExtendedOptionsImpl.checkGetOptionPermission(object);
            object = SocketFlow.create();
            ExtendedOptionsImpl.getFlowOption(this.getFileDescriptor(), (SocketFlow)object);
            return (T)object;
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
                if (this.isClosedOrPending()) break block4;
                ExtendedOptionsImpl.checkSetOptionPermission(socketOption);
                ExtendedOptionsImpl.checkValueType(t, SocketFlow.class);
                ExtendedOptionsImpl.setFlowOption(this.getFileDescriptor(), (SocketFlow)t);
            }
            return;
        }
        throw new SocketException("Socket closed");
    }

    @Override
    void socketAccept(SocketImpl object) throws IOException {
        if (this.fd != null && this.fd.valid()) {
            ErrnoException errnoException2;
            block5 : {
                block6 : {
                    if (this.timeout <= 0) {
                        IoBridge.poll((FileDescriptor)this.fd, (int)(OsConstants.POLLIN | OsConstants.POLLERR), (int)-1);
                    } else {
                        IoBridge.poll((FileDescriptor)this.fd, (int)(OsConstants.POLLIN | OsConstants.POLLERR), (int)this.timeout);
                    }
                    InetSocketAddress inetSocketAddress = new InetSocketAddress();
                    try {
                        FileDescriptor fileDescriptor = Libcore.os.accept(this.fd, (SocketAddress)inetSocketAddress);
                        ((SocketImpl)object).fd.setInt$(fileDescriptor.getInt$());
                        IoUtils.setFdOwner((FileDescriptor)((SocketImpl)object).fd, (Object)object);
                        ((SocketImpl)object).address = inetSocketAddress.getAddress();
                        ((SocketImpl)object).port = inetSocketAddress.getPort();
                    }
                    catch (ErrnoException errnoException2) {
                        if (errnoException2.errno == OsConstants.EAGAIN) break block5;
                        if (errnoException2.errno == OsConstants.EINVAL || errnoException2.errno == OsConstants.EBADF) break block6;
                        errnoException2.rethrowAsSocketException();
                    }
                    ((SocketImpl)object).localport = IoBridge.getLocalInetSocketAddress((FileDescriptor)((SocketImpl)object).fd).getPort();
                    return;
                }
                throw new SocketException("Socket closed");
            }
            object = new SocketTimeoutException();
            ((Throwable)object).initCause(errnoException2);
            throw object;
        }
        throw new SocketException("Socket closed");
    }

    @Override
    int socketAvailable() throws IOException {
        return IoBridge.available((FileDescriptor)this.fd);
    }

    @Override
    void socketBind(InetAddress inetAddress, int n) throws IOException {
        if (this.fd != null && this.fd.valid()) {
            IoBridge.bind((FileDescriptor)this.fd, (InetAddress)inetAddress, (int)n);
            this.address = inetAddress;
            this.localport = n == 0 ? IoBridge.getLocalInetSocketAddress((FileDescriptor)this.fd).getPort() : n;
            return;
        }
        throw new SocketException("Socket closed");
    }

    @Override
    void socketClose0(boolean bl) throws IOException {
        if (this.fd != null && this.fd.valid()) {
            FileDescriptor fileDescriptor = null;
            if (bl) {
                fileDescriptor = this.getMarkerFD();
            }
            if (bl && fileDescriptor != null) {
                try {
                    Libcore.os.dup2(fileDescriptor, this.fd.getInt$());
                    Libcore.os.close(fileDescriptor);
                    AsynchronousCloseMonitor.signalBlockedThreads((FileDescriptor)this.fd);
                }
                catch (ErrnoException errnoException) {}
            } else {
                IoBridge.closeAndSignalBlockedThreads((FileDescriptor)this.fd);
            }
            return;
        }
        throw new SocketException("socket already closed");
    }

    @Override
    void socketConnect(InetAddress inetAddress, int n, int n2) throws IOException {
        if (this.fd != null && this.fd.valid()) {
            IoBridge.connect((FileDescriptor)this.fd, (InetAddress)inetAddress, (int)n, (int)n2);
            this.address = inetAddress;
            this.port = n;
            if (this.localport == 0 && !this.isClosedOrPending()) {
                this.localport = IoBridge.getLocalInetSocketAddress((FileDescriptor)this.fd).getPort();
            }
            return;
        }
        throw new SocketException("Socket closed");
    }

    @Override
    void socketCreate(boolean bl) throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        int n = OsConstants.AF_INET6;
        int n2 = bl ? OsConstants.SOCK_STREAM : OsConstants.SOCK_DGRAM;
        fileDescriptor.setInt$(IoBridge.socket((int)n, (int)n2, (int)0).getInt$());
        IoUtils.setFdOwner((FileDescriptor)this.fd, (Object)this);
        if (this.serverSocket != null) {
            IoUtils.setBlocking((FileDescriptor)this.fd, (boolean)false);
            IoBridge.setSocketOption((FileDescriptor)this.fd, (int)4, (Object)true);
        }
    }

    @Override
    Object socketGetOption(int n) throws SocketException {
        return IoBridge.getSocketOption((FileDescriptor)this.fd, (int)n);
    }

    @Override
    void socketListen(int n) throws IOException {
        if (this.fd != null && this.fd.valid()) {
            try {
                Libcore.os.listen(this.fd, n);
                return;
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsSocketException();
            }
        }
        throw new SocketException("Socket closed");
    }

    @Override
    void socketSendUrgentData(int n) throws IOException {
        if (this.fd != null && this.fd.valid()) {
            byte by = (byte)n;
            try {
                Os os = Libcore.os;
                FileDescriptor fileDescriptor = this.fd;
                n = OsConstants.MSG_OOB;
                os.sendto(fileDescriptor, new byte[]{by}, 0, 1, n, null, 0);
                return;
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsSocketException();
            }
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
                if (this.socket == null || !this.socket.isConnected()) break block2;
            }
            return;
        }
        throw socketException2;
    }

    void socketSetOption0(int n, Object object) throws SocketException {
        if (n == 4102) {
            return;
        }
        IoBridge.setSocketOption((FileDescriptor)this.fd, (int)n, (Object)object);
    }

    @Override
    void socketShutdown(int n) throws IOException {
        try {
            Libcore.os.shutdown(this.fd, n);
            return;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
    }
}

