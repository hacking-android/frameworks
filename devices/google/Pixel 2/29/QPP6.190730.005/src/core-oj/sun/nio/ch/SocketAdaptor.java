/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.SocketOption;
import java.net.SocketTimeoutException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.NetworkChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import sun.nio.ch.ChannelInputStream;
import sun.nio.ch.ExtendedSocketOption;
import sun.nio.ch.FileDescriptorHolderSocketImpl;
import sun.nio.ch.Net;
import sun.nio.ch.SocketChannelImpl;

public class SocketAdaptor
extends Socket {
    private final SocketChannelImpl sc;
    private InputStream socketInputStream = null;
    private volatile int timeout = 0;

    private SocketAdaptor(SocketChannelImpl socketChannelImpl) throws SocketException {
        super(new FileDescriptorHolderSocketImpl(socketChannelImpl.getFD()));
        this.sc = socketChannelImpl;
    }

    static /* synthetic */ int access$100(SocketAdaptor socketAdaptor) {
        return socketAdaptor.timeout;
    }

    public static Socket create(SocketChannelImpl closeable) {
        try {
            closeable = new SocketAdaptor((SocketChannelImpl)closeable);
            return closeable;
        }
        catch (SocketException socketException) {
            throw new InternalError("Should not reach here");
        }
    }

    private boolean getBooleanOption(SocketOption<Boolean> socketOption) throws SocketException {
        try {
            boolean bl = this.sc.getOption(socketOption);
            return bl;
        }
        catch (IOException iOException) {
            Net.translateToSocketException(iOException);
            return false;
        }
    }

    private int getIntOption(SocketOption<Integer> socketOption) throws SocketException {
        try {
            int n = this.sc.getOption(socketOption);
            return n;
        }
        catch (IOException iOException) {
            Net.translateToSocketException(iOException);
            return -1;
        }
    }

    private void setBooleanOption(SocketOption<Boolean> socketOption, boolean bl) throws SocketException {
        try {
            this.sc.setOption((SocketOption)socketOption, (Object)bl);
        }
        catch (IOException iOException) {
            Net.translateToSocketException(iOException);
        }
    }

    private void setIntOption(SocketOption<Integer> socketOption, int n) throws SocketException {
        try {
            this.sc.setOption((SocketOption)socketOption, (Object)n);
        }
        catch (IOException iOException) {
            Net.translateToSocketException(iOException);
        }
    }

    @Override
    public void bind(SocketAddress socketAddress) throws IOException {
        try {
            this.sc.bind(socketAddress);
        }
        catch (Exception exception) {
            Net.translateException(exception);
        }
    }

    @Override
    public void close() throws IOException {
        this.sc.close();
    }

    @Override
    public void connect(SocketAddress socketAddress) throws IOException {
        this.connect(socketAddress, 0);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void connect(SocketAddress var1_1, int var2_6) throws IOException {
        if (var1_1 /* !! */  == null) throw new IllegalArgumentException("connect: The address can't be null");
        if (var2_6 < 0) throw new IllegalArgumentException("connect: timeout can't be negative");
        var3_7 = this.sc.blockingLock();
        // MONITORENTER : var3_7
        var4_8 = this.sc.isBlocking();
        if (!var4_8) {
            var1_1 /* !! */  = new IllegalBlockingModeException();
            throw var1_1 /* !! */ ;
        }
        if (var2_6 != 0) ** GOTO lbl19
        try {
            this.sc.connect(var1_1 /* !! */ );
            return;
        }
        catch (Exception var1_2) {
            block21 : {
                Net.translateException(var1_2);
                // MONITOREXIT : var3_7
                return;
lbl19: // 1 sources:
                this.sc.configureBlocking(false);
                var4_8 = this.sc.connect(var1_1 /* !! */ );
                if (!var4_8) break block21;
                if (this.sc.isOpen()) {
                    this.sc.configureBlocking(true);
                }
                // MONITOREXIT : var3_7
                return;
            }
            var5_9 = var2_6;
            do {
                block22 : {
                    if (!this.sc.isOpen()) ** GOTO lbl54
                    var7_10 = System.currentTimeMillis();
                    if (this.sc.poll(Net.POLLCONN, var5_9) <= 0 || !(var4_8 = this.sc.finishConnect())) break block22;
                    if (!this.sc.isOpen()) return;
                    {
                        this.sc.configureBlocking(true);
                        return;
                    }
                }
                var9_11 = System.currentTimeMillis();
                if ((var5_9 -= var9_11 - var7_10) > 0L) continue;
                break;
            } while (true);
            try {
                try {
                    this.sc.close();
                }
                catch (IOException var1_3) {
                    // empty catch block
                }
                var1_1 /* !! */  = new SocketTimeoutException();
                throw var1_1 /* !! */ ;
lbl54: // 1 sources:
                var1_1 /* !! */  = new ClosedChannelException();
                throw var1_1 /* !! */ ;
            }
            catch (Throwable var1_4) {
                try {
                    if (this.sc.isOpen() == false) throw var1_4;
                    this.sc.configureBlocking(true);
                    throw var1_4;
                }
                catch (Exception var1_5) {
                    Net.translateException(var1_5, true);
                }
            }
        }
    }

    @Override
    public SocketChannel getChannel() {
        return this.sc;
    }

    @Override
    public FileDescriptor getFileDescriptor$() {
        return this.sc.getFD();
    }

    @Override
    public InetAddress getInetAddress() {
        if (!this.isConnected()) {
            return null;
        }
        SocketAddress socketAddress = this.sc.remoteAddress();
        if (socketAddress == null) {
            return null;
        }
        return ((InetSocketAddress)socketAddress).getAddress();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (this.sc.isOpen()) {
            if (this.sc.isConnected()) {
                if (this.sc.isInputOpen()) {
                    if (this.socketInputStream == null) {
                        try {
                            PrivilegedExceptionAction<InputStream> privilegedExceptionAction = new PrivilegedExceptionAction<InputStream>(){

                                @Override
                                public InputStream run() throws IOException {
                                    return new SocketInputStream();
                                }
                            };
                            this.socketInputStream = AccessController.doPrivileged(privilegedExceptionAction);
                        }
                        catch (PrivilegedActionException privilegedActionException) {
                            throw (IOException)privilegedActionException.getException();
                        }
                    }
                    return this.socketInputStream;
                }
                throw new SocketException("Socket input is shutdown");
            }
            throw new SocketException("Socket is not connected");
        }
        throw new SocketException("Socket is closed");
    }

    @Override
    public boolean getKeepAlive() throws SocketException {
        return this.getBooleanOption(StandardSocketOptions.SO_KEEPALIVE);
    }

    @Override
    public InetAddress getLocalAddress() {
        InetSocketAddress inetSocketAddress;
        if (this.sc.isOpen() && (inetSocketAddress = this.sc.localAddress()) != null) {
            return Net.getRevealedLocalAddress(inetSocketAddress).getAddress();
        }
        return new InetSocketAddress(0).getAddress();
    }

    @Override
    public int getLocalPort() {
        InetSocketAddress inetSocketAddress = this.sc.localAddress();
        if (inetSocketAddress == null) {
            return -1;
        }
        return inetSocketAddress.getPort();
    }

    @Override
    public boolean getOOBInline() throws SocketException {
        return this.getBooleanOption(ExtendedSocketOption.SO_OOBINLINE);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        if (this.sc.isOpen()) {
            if (this.sc.isConnected()) {
                if (this.sc.isOutputOpen()) {
                    try {
                        PrivilegedExceptionAction<OutputStream> privilegedExceptionAction = new PrivilegedExceptionAction<OutputStream>(){

                            @Override
                            public OutputStream run() throws IOException {
                                return Channels.newOutputStream(SocketAdaptor.this.sc);
                            }
                        };
                        privilegedExceptionAction = AccessController.doPrivileged(privilegedExceptionAction);
                        return privilegedExceptionAction;
                    }
                    catch (PrivilegedActionException privilegedActionException) {
                        throw (IOException)privilegedActionException.getException();
                    }
                }
                throw new SocketException("Socket output is shutdown");
            }
            throw new SocketException("Socket is not connected");
        }
        throw new SocketException("Socket is closed");
    }

    @Override
    public int getPort() {
        if (!this.isConnected()) {
            return 0;
        }
        SocketAddress socketAddress = this.sc.remoteAddress();
        if (socketAddress == null) {
            return 0;
        }
        return ((InetSocketAddress)socketAddress).getPort();
    }

    @Override
    public int getReceiveBufferSize() throws SocketException {
        return this.getIntOption(StandardSocketOptions.SO_RCVBUF);
    }

    @Override
    public boolean getReuseAddress() throws SocketException {
        return this.getBooleanOption(StandardSocketOptions.SO_REUSEADDR);
    }

    @Override
    public int getSendBufferSize() throws SocketException {
        return this.getIntOption(StandardSocketOptions.SO_SNDBUF);
    }

    @Override
    public int getSoLinger() throws SocketException {
        return this.getIntOption(StandardSocketOptions.SO_LINGER);
    }

    @Override
    public int getSoTimeout() throws SocketException {
        return this.timeout;
    }

    @Override
    public boolean getTcpNoDelay() throws SocketException {
        return this.getBooleanOption(StandardSocketOptions.TCP_NODELAY);
    }

    @Override
    public int getTrafficClass() throws SocketException {
        return this.getIntOption(StandardSocketOptions.IP_TOS);
    }

    @Override
    public boolean isBound() {
        boolean bl = this.sc.localAddress() != null;
        return bl;
    }

    @Override
    public boolean isClosed() {
        return this.sc.isOpen() ^ true;
    }

    @Override
    public boolean isConnected() {
        return this.sc.isConnected();
    }

    @Override
    public boolean isInputShutdown() {
        return this.sc.isInputOpen() ^ true;
    }

    @Override
    public boolean isOutputShutdown() {
        return this.sc.isOutputOpen() ^ true;
    }

    @Override
    public void sendUrgentData(int n) throws IOException {
        if (this.sc.sendOutOfBandData((byte)n) != 0) {
            return;
        }
        throw new IOException("Socket buffer full");
    }

    @Override
    public void setKeepAlive(boolean bl) throws SocketException {
        this.setBooleanOption(StandardSocketOptions.SO_KEEPALIVE, bl);
    }

    @Override
    public void setOOBInline(boolean bl) throws SocketException {
        this.setBooleanOption(ExtendedSocketOption.SO_OOBINLINE, bl);
    }

    @Override
    public void setReceiveBufferSize(int n) throws SocketException {
        if (n > 0) {
            this.setIntOption(StandardSocketOptions.SO_RCVBUF, n);
            return;
        }
        throw new IllegalArgumentException("Invalid receive size");
    }

    @Override
    public void setReuseAddress(boolean bl) throws SocketException {
        this.setBooleanOption(StandardSocketOptions.SO_REUSEADDR, bl);
    }

    @Override
    public void setSendBufferSize(int n) throws SocketException {
        if (n > 0) {
            this.setIntOption(StandardSocketOptions.SO_SNDBUF, n);
            return;
        }
        throw new IllegalArgumentException("Invalid send size");
    }

    @Override
    public void setSoLinger(boolean bl, int n) throws SocketException {
        if (!bl) {
            n = -1;
        }
        this.setIntOption(StandardSocketOptions.SO_LINGER, n);
    }

    @Override
    public void setSoTimeout(int n) throws SocketException {
        if (n >= 0) {
            this.timeout = n;
            return;
        }
        throw new IllegalArgumentException("timeout can't be negative");
    }

    @Override
    public void setTcpNoDelay(boolean bl) throws SocketException {
        this.setBooleanOption(StandardSocketOptions.TCP_NODELAY, bl);
    }

    @Override
    public void setTrafficClass(int n) throws SocketException {
        this.setIntOption(StandardSocketOptions.IP_TOS, n);
    }

    @Override
    public void shutdownInput() throws IOException {
        try {
            this.sc.shutdownInput();
        }
        catch (Exception exception) {
            Net.translateException(exception);
        }
    }

    @Override
    public void shutdownOutput() throws IOException {
        try {
            this.sc.shutdownOutput();
        }
        catch (Exception exception) {
            Net.translateException(exception);
        }
    }

    @Override
    public String toString() {
        if (this.sc.isConnected()) {
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
        return "Socket[unconnected]";
    }

    private class SocketInputStream
    extends ChannelInputStream {
        private SocketInputStream() {
            super(SocketAdaptor.this.sc);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected int read(ByteBuffer var1_1) throws IOException {
            var2_3 = SocketAdaptor.access$000(SocketAdaptor.this).blockingLock();
            synchronized (var2_3) {
                if (!SocketAdaptor.access$000(SocketAdaptor.this).isBlocking()) {
                    var1_1 = new IllegalBlockingModeException();
                    throw var1_1;
                }
                if (SocketAdaptor.access$100(SocketAdaptor.this) == 0) {
                    return SocketAdaptor.access$000(SocketAdaptor.this).read((ByteBuffer)var1_1);
                }
                SocketAdaptor.access$000(SocketAdaptor.this).configureBlocking(false);
                var3_5 = SocketAdaptor.access$000(SocketAdaptor.this).read((ByteBuffer)var1_1);
                ** if (var3_5 == 0) goto lbl-1000
lbl13: // 1 sources:
                return var3_5;
lbl-1000: // 1 sources:
                {
                    var4_6 = SocketAdaptor.access$100(SocketAdaptor.this);
                    while (SocketAdaptor.access$000(SocketAdaptor.this).isOpen()) {
                        var6_7 = System.currentTimeMillis();
                        if (SocketAdaptor.access$000(SocketAdaptor.this).poll(Net.POLLIN, var4_6) > 0) {
                            var3_5 = SocketAdaptor.access$000(SocketAdaptor.this).read((ByteBuffer)var1_1);
                            if (var3_5 != 0) return var3_5;
                        }
                        if ((var4_6 -= System.currentTimeMillis() - var6_7) > 0L) continue;
                        var1_1 = new SocketTimeoutException();
                        throw var1_1;
                    }
                    var1_1 = new ClosedChannelException();
                    throw var1_1;
                }
                finally {
                    if (SocketAdaptor.access$000(SocketAdaptor.this).isOpen()) {
                        SocketAdaptor.access$000(SocketAdaptor.this).configureBlocking(true);
                    }
                }
            }
        }
    }

}

