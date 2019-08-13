/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.DatagramSocketImpl;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketOption;
import java.net.SocketTimeoutException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.NetworkChannel;
import java.nio.channels.SelectableChannel;
import sun.nio.ch.DatagramChannelImpl;
import sun.nio.ch.Net;

public class DatagramSocketAdaptor
extends DatagramSocket {
    private static final DatagramSocketImpl dummyDatagramSocket = new DatagramSocketImpl(){

        @Override
        protected void bind(int n, InetAddress inetAddress) throws SocketException {
        }

        @Override
        protected void close() {
        }

        @Override
        protected void create() throws SocketException {
        }

        @Override
        public Object getOption(int n) throws SocketException {
            return null;
        }

        @Deprecated
        @Override
        protected byte getTTL() throws IOException {
            return 0;
        }

        @Override
        protected int getTimeToLive() throws IOException {
            return 0;
        }

        @Override
        protected void join(InetAddress inetAddress) throws IOException {
        }

        @Override
        protected void joinGroup(SocketAddress socketAddress, NetworkInterface networkInterface) throws IOException {
        }

        @Override
        protected void leave(InetAddress inetAddress) throws IOException {
        }

        @Override
        protected void leaveGroup(SocketAddress socketAddress, NetworkInterface networkInterface) throws IOException {
        }

        @Override
        protected int peek(InetAddress inetAddress) throws IOException {
            return 0;
        }

        @Override
        protected int peekData(DatagramPacket datagramPacket) throws IOException {
            return 0;
        }

        @Override
        protected void receive(DatagramPacket datagramPacket) throws IOException {
        }

        @Override
        protected void send(DatagramPacket datagramPacket) throws IOException {
        }

        @Override
        public void setOption(int n, Object object) throws SocketException {
        }

        @Deprecated
        @Override
        protected void setTTL(byte by) throws IOException {
        }

        @Override
        protected void setTimeToLive(int n) throws IOException {
        }
    };
    private final DatagramChannelImpl dc;
    private volatile int timeout = 0;

    private DatagramSocketAdaptor(DatagramChannelImpl datagramChannelImpl) throws IOException {
        super(dummyDatagramSocket);
        this.dc = datagramChannelImpl;
    }

    private void connectInternal(SocketAddress serializable) throws SocketException {
        int n = Net.asInetSocketAddress((SocketAddress)serializable).getPort();
        if (n >= 0 && n <= 65535) {
            if (serializable != null) {
                if (this.isClosed()) {
                    return;
                }
                try {
                    this.dc.connect((SocketAddress)serializable);
                }
                catch (Exception exception) {
                    Net.translateToSocketException(exception);
                }
                return;
            }
            throw new IllegalArgumentException("connect: null address");
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("connect: ");
        ((StringBuilder)serializable).append(n);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public static DatagramSocket create(DatagramChannelImpl closeable) {
        try {
            closeable = new DatagramSocketAdaptor((DatagramChannelImpl)closeable);
            return closeable;
        }
        catch (IOException iOException) {
            throw new Error(iOException);
        }
    }

    private boolean getBooleanOption(SocketOption<Boolean> socketOption) throws SocketException {
        try {
            boolean bl = this.dc.getOption(socketOption);
            return bl;
        }
        catch (IOException iOException) {
            Net.translateToSocketException(iOException);
            return false;
        }
    }

    private int getIntOption(SocketOption<Integer> socketOption) throws SocketException {
        try {
            int n = this.dc.getOption(socketOption);
            return n;
        }
        catch (IOException iOException) {
            Net.translateToSocketException(iOException);
            return -1;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SocketAddress receive(ByteBuffer object) throws IOException {
        if (this.timeout == 0) {
            return this.dc.receive((ByteBuffer)object);
        }
        this.dc.configureBlocking(false);
        try {
            SocketAddress socketAddress = this.dc.receive((ByteBuffer)object);
            if (socketAddress != null) {
                return socketAddress;
            }
            long l = this.timeout;
            while (this.dc.isOpen()) {
                long l2 = System.currentTimeMillis();
                int n = this.dc.poll(Net.POLLIN, l);
                if (n > 0 && (Net.POLLIN & n) != 0 && (socketAddress = this.dc.receive((ByteBuffer)object)) != null) {
                    return socketAddress;
                }
                if ((l -= System.currentTimeMillis() - l2) > 0L) continue;
                object = new SocketTimeoutException();
                throw object;
            }
            object = new ClosedChannelException();
            throw object;
        }
        finally {
            if (this.dc.isOpen()) {
                this.dc.configureBlocking(true);
            }
        }
    }

    private void setBooleanOption(SocketOption<Boolean> socketOption, boolean bl) throws SocketException {
        try {
            this.dc.setOption((SocketOption)socketOption, (Object)bl);
        }
        catch (IOException iOException) {
            Net.translateToSocketException(iOException);
        }
    }

    private void setIntOption(SocketOption<Integer> socketOption, int n) throws SocketException {
        try {
            this.dc.setOption((SocketOption)socketOption, (Object)n);
        }
        catch (IOException iOException) {
            Net.translateToSocketException(iOException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void bind(SocketAddress var1_1) throws SocketException {
        var2_3 = var1_1;
        if (var1_1 != null) ** GOTO lbl5
        try {
            var2_3 = new InetSocketAddress(0);
lbl5: // 2 sources:
            this.dc.bind(var2_3);
            return;
        }
        catch (Exception var1_2) {
            Net.translateToSocketException(var1_2);
        }
    }

    @Override
    public void close() {
        try {
            this.dc.close();
            return;
        }
        catch (IOException iOException) {
            throw new Error(iOException);
        }
    }

    @Override
    public void connect(InetAddress inetAddress, int n) {
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, n);
            this.connectInternal(inetSocketAddress);
        }
        catch (SocketException socketException) {
            // empty catch block
        }
    }

    @Override
    public void connect(SocketAddress socketAddress) throws SocketException {
        if (socketAddress != null) {
            this.connectInternal(socketAddress);
            return;
        }
        throw new IllegalArgumentException("Address can't be null");
    }

    @Override
    public void disconnect() {
        try {
            this.dc.disconnect();
            return;
        }
        catch (IOException iOException) {
            throw new Error(iOException);
        }
    }

    @Override
    public boolean getBroadcast() throws SocketException {
        return this.getBooleanOption(StandardSocketOptions.SO_BROADCAST);
    }

    @Override
    public DatagramChannel getChannel() {
        return this.dc;
    }

    @Override
    public final FileDescriptor getFileDescriptor$() {
        return this.dc.fd;
    }

    @Override
    public InetAddress getInetAddress() {
        InetAddress inetAddress = this.isConnected() ? Net.asInetSocketAddress(this.dc.remoteAddress()).getAddress() : null;
        return inetAddress;
    }

    @Override
    public InetAddress getLocalAddress() {
        if (this.isClosed()) {
            return null;
        }
        Serializable serializable = this.dc.localAddress();
        Object object = serializable;
        if (serializable == null) {
            object = new InetSocketAddress(0);
        }
        serializable = ((InetSocketAddress)object).getAddress();
        object = System.getSecurityManager();
        if (object != null) {
            try {
                ((SecurityManager)object).checkConnect(((InetAddress)serializable).getHostAddress(), -1);
            }
            catch (SecurityException securityException) {
                return new InetSocketAddress(0).getAddress();
            }
        }
        return serializable;
    }

    @Override
    public int getLocalPort() {
        block4 : {
            if (this.isClosed()) {
                return -1;
            }
            SocketAddress socketAddress = this.dc.getLocalAddress();
            if (socketAddress == null) break block4;
            try {
                int n = ((InetSocketAddress)socketAddress).getPort();
                return n;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return 0;
    }

    @Override
    public int getPort() {
        int n = this.isConnected() ? Net.asInetSocketAddress(this.dc.remoteAddress()).getPort() : -1;
        return n;
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
    public int getSoTimeout() throws SocketException {
        return this.timeout;
    }

    @Override
    public int getTrafficClass() throws SocketException {
        return this.getIntOption(StandardSocketOptions.IP_TOS);
    }

    @Override
    public boolean isBound() {
        boolean bl = this.dc.localAddress() != null;
        return bl;
    }

    @Override
    public boolean isClosed() {
        return this.dc.isOpen() ^ true;
    }

    @Override
    public boolean isConnected() {
        boolean bl = this.dc.remoteAddress() != null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void receive(DatagramPacket object) throws IOException {
        Object object2 = this.dc.blockingLock();
        // MONITORENTER : object2
        boolean bl = this.dc.isBlocking();
        if (bl) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(((DatagramPacket)object).getData(), ((DatagramPacket)object).getOffset(), ((DatagramPacket)object).getLength());
            ((DatagramPacket)object).setSocketAddress(this.receive(byteBuffer));
            ((DatagramPacket)object).setLength(byteBuffer.position() - ((DatagramPacket)object).getOffset());
            // MONITOREXIT : object
            return;
        }
        object = new IllegalBlockingModeException();
        throw object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void send(DatagramPacket object) throws IOException {
        Object object2 = this.dc.blockingLock();
        // MONITORENTER : object2
        boolean bl = this.dc.isBlocking();
        if (!bl) {
            object = new IllegalBlockingModeException();
            throw object;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(((DatagramPacket)object).getData(), ((DatagramPacket)object).getOffset(), ((DatagramPacket)object).getLength());
        if (!this.dc.isConnected()) {
            this.dc.send(byteBuffer, ((DatagramPacket)object).getSocketAddress());
            // MONITOREXIT : object
            return;
        }
        if (((DatagramPacket)object).getAddress() == null) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress)this.dc.remoteAddress();
            ((DatagramPacket)object).setPort(inetSocketAddress.getPort());
            ((DatagramPacket)object).setAddress(inetSocketAddress.getAddress());
            this.dc.write(byteBuffer);
            return;
        }
        this.dc.send(byteBuffer, ((DatagramPacket)object).getSocketAddress());
    }

    @Override
    public void setBroadcast(boolean bl) throws SocketException {
        this.setBooleanOption(StandardSocketOptions.SO_BROADCAST, bl);
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
    public void setSoTimeout(int n) throws SocketException {
        this.timeout = n;
    }

    @Override
    public void setTrafficClass(int n) throws SocketException {
        this.setIntOption(StandardSocketOptions.IP_TOS, n);
    }

}

