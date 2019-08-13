/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Int32Ref
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructLinger
 *  android.system.StructTimeval
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.Credentials;
import android.net.LocalSocketAddress;
import android.system.ErrnoException;
import android.system.Int32Ref;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructLinger;
import android.system.StructTimeval;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class LocalSocketImpl {
    private FileDescriptor fd;
    private SocketInputStream fis;
    private SocketOutputStream fos;
    @UnsupportedAppUsage
    FileDescriptor[] inboundFileDescriptors;
    private boolean mFdCreatedInternally;
    @UnsupportedAppUsage
    FileDescriptor[] outboundFileDescriptors;
    private Object readMonitor = new Object();
    private Object writeMonitor = new Object();

    @UnsupportedAppUsage
    LocalSocketImpl() {
    }

    LocalSocketImpl(FileDescriptor fileDescriptor) {
        this.fd = fileDescriptor;
    }

    private native void bindLocal(FileDescriptor var1, String var2, int var3) throws IOException;

    private native void connectLocal(FileDescriptor var1, String var2, int var3) throws IOException;

    private native Credentials getPeerCredentials_native(FileDescriptor var1) throws IOException;

    private static int javaSoToOsOpt(int n) {
        if (n != 4) {
            if (n != 4097) {
                if (n == 4098) {
                    return OsConstants.SO_RCVBUF;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown option: ");
                stringBuilder.append(n);
                throw new UnsupportedOperationException(stringBuilder.toString());
            }
            return OsConstants.SO_SNDBUF;
        }
        return OsConstants.SO_REUSEADDR;
    }

    private native int read_native(FileDescriptor var1) throws IOException;

    private native int readba_native(byte[] var1, int var2, int var3, FileDescriptor var4) throws IOException;

    private native void write_native(int var1, FileDescriptor var2) throws IOException;

    private native void writeba_native(byte[] var1, int var2, int var3, FileDescriptor var4) throws IOException;

    protected void accept(LocalSocketImpl localSocketImpl) throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor != null) {
            try {
                localSocketImpl.fd = Os.accept((FileDescriptor)fileDescriptor, null);
                localSocketImpl.mFdCreatedInternally = true;
                return;
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsIOException();
            }
        }
        throw new IOException("socket not created");
    }

    protected int available() throws IOException {
        return this.getInputStream().available();
    }

    public void bind(LocalSocketAddress localSocketAddress) throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor != null) {
            this.bindLocal(fileDescriptor, localSocketAddress.getName(), localSocketAddress.getNamespace().getId());
            return;
        }
        throw new IOException("socket not created");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() throws IOException {
        synchronized (this) {
            boolean bl;
            if (this.fd != null && (bl = this.mFdCreatedInternally)) {
                try {
                    Os.close((FileDescriptor)this.fd);
                }
                catch (ErrnoException errnoException) {
                    errnoException.rethrowAsIOException();
                }
                this.fd = null;
                return;
            }
            this.fd = null;
            return;
        }
    }

    protected void connect(LocalSocketAddress localSocketAddress, int n) throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor != null) {
            this.connectLocal(fileDescriptor, localSocketAddress.getName(), localSocketAddress.getNamespace().getId());
            return;
        }
        throw new IOException("socket not created");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void create(int n) throws IOException {
        if (this.fd != null) throw new IOException("LocalSocketImpl already has an fd");
        if (n != 1) {
            if (n != 2) {
                if (n != 3) throw new IllegalStateException("unknown sockType");
                n = OsConstants.SOCK_SEQPACKET;
            } else {
                n = OsConstants.SOCK_STREAM;
            }
        } else {
            n = OsConstants.SOCK_DGRAM;
        }
        try {
            this.fd = Os.socket((int)OsConstants.AF_UNIX, (int)n, (int)0);
            this.mFdCreatedInternally = true;
            return;
        }
        catch (ErrnoException errnoException) {
            errnoException.rethrowAsIOException();
        }
    }

    protected void finalize() throws IOException {
        this.close();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public FileDescriptor[] getAncillaryFileDescriptors() throws IOException {
        Object object = this.readMonitor;
        synchronized (object) {
            FileDescriptor[] arrfileDescriptor = this.inboundFileDescriptors;
            this.inboundFileDescriptors = null;
            return arrfileDescriptor;
        }
    }

    protected FileDescriptor getFileDescriptor() {
        return this.fd;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected InputStream getInputStream() throws IOException {
        if (this.fd == null) {
            throw new IOException("socket not created");
        }
        synchronized (this) {
            SocketInputStream socketInputStream;
            if (this.fis != null) return this.fis;
            this.fis = socketInputStream = new SocketInputStream();
            return this.fis;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object getOption(int n) throws IOException {
        Object object = this.fd;
        if (object == null) throw new IOException("socket not created");
        if (n != 1) {
            if (n != 4) {
                if (n != 128) {
                    if (n == 4102) return (int)Os.getsockoptTimeval((FileDescriptor)object, (int)OsConstants.SOL_SOCKET, (int)OsConstants.SO_SNDTIMEO).toMillis();
                    if (n != 4097 && n != 4098) {
                        try {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unknown option: ");
                            ((StringBuilder)object).append(n);
                            IOException iOException = new IOException(((StringBuilder)object).toString());
                            throw iOException;
                        }
                        catch (ErrnoException errnoException) {
                            throw errnoException.rethrowAsIOException();
                        }
                    }
                } else {
                    if ((object = Os.getsockoptLinger((FileDescriptor)object, (int)OsConstants.SOL_SOCKET, (int)OsConstants.SO_LINGER)).isOn()) return ((StructLinger)object).l_linger;
                    return -1;
                }
            }
            n = LocalSocketImpl.javaSoToOsOpt(n);
            return Os.getsockoptInt((FileDescriptor)this.fd, (int)OsConstants.SOL_SOCKET, (int)n);
        }
        n = Os.getsockoptInt((FileDescriptor)object, (int)OsConstants.IPPROTO_TCP, (int)OsConstants.TCP_NODELAY);
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected OutputStream getOutputStream() throws IOException {
        if (this.fd == null) {
            throw new IOException("socket not created");
        }
        synchronized (this) {
            SocketOutputStream socketOutputStream;
            if (this.fos != null) return this.fos;
            this.fos = socketOutputStream = new SocketOutputStream();
            return this.fos;
        }
    }

    public Credentials getPeerCredentials() throws IOException {
        return this.getPeerCredentials_native(this.fd);
    }

    public LocalSocketAddress getSockAddress() throws IOException {
        return null;
    }

    protected void listen(int n) throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor != null) {
            try {
                Os.listen((FileDescriptor)fileDescriptor, (int)n);
                return;
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsIOException();
            }
        }
        throw new IOException("socket not created");
    }

    protected void sendUrgentData(int n) throws IOException {
        throw new RuntimeException("not impled");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setFileDescriptorsForSend(FileDescriptor[] arrfileDescriptor) {
        Object object = this.writeMonitor;
        synchronized (object) {
            this.outboundFileDescriptors = arrfileDescriptor;
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setOption(int var1_1, Object var2_2) throws IOException {
        block8 : {
            block9 : {
                if (this.fd == null) throw new IOException("socket not created");
                var3_4 = -1;
                var4_5 = 0;
                if (var2_2 instanceof Integer) {
                    var4_5 = (Integer)var2_2;
                } else {
                    if (!(var2_2 instanceof Boolean)) {
                        var5_7 = new StringBuilder();
                        var5_7.append("bad value: ");
                        var5_7.append(var2_2);
                        throw new IOException(var5_7.toString());
                    }
                    var3_4 = ((Boolean)var2_2).booleanValue() ? 1 : 0;
                }
                if (var1_1 == 1) break block8;
                if (var1_1 == 4) break block9;
                if (var1_1 == 128) ** GOTO lbl35
                if (var1_1 != 4102) {
                    if (var1_1 != 4097 && var1_1 != 4098) {
                        try {
                            var2_2 = new StringBuilder();
                            var2_2.append("Unknown option: ");
                            var2_2.append(var1_1);
                            var5_6 = new IOException(var2_2.toString());
                            throw var5_6;
                        }
                        catch (ErrnoException var2_3) {
                            throw var2_3.rethrowAsIOException();
                        }
                    }
                } else {
                    var2_2 = StructTimeval.fromMillis((long)var4_5);
                    Os.setsockoptTimeval((FileDescriptor)this.fd, (int)OsConstants.SOL_SOCKET, (int)OsConstants.SO_RCVTIMEO, (StructTimeval)var2_2);
                    Os.setsockoptTimeval((FileDescriptor)this.fd, (int)OsConstants.SOL_SOCKET, (int)OsConstants.SO_SNDTIMEO, (StructTimeval)var2_2);
                    return;
lbl35: // 1 sources:
                    var2_2 = new StructLinger(var3_4, var4_5);
                    Os.setsockoptLinger((FileDescriptor)this.fd, (int)OsConstants.SOL_SOCKET, (int)OsConstants.SO_LINGER, (StructLinger)var2_2);
                    return;
                }
            }
            var1_1 = LocalSocketImpl.javaSoToOsOpt(var1_1);
            Os.setsockoptInt((FileDescriptor)this.fd, (int)OsConstants.SOL_SOCKET, (int)var1_1, (int)var4_5);
            return;
        }
        Os.setsockoptInt((FileDescriptor)this.fd, (int)OsConstants.IPPROTO_TCP, (int)OsConstants.TCP_NODELAY, (int)var4_5);
    }

    protected void shutdownInput() throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor != null) {
            try {
                Os.shutdown((FileDescriptor)fileDescriptor, (int)OsConstants.SHUT_RD);
                return;
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsIOException();
            }
        }
        throw new IOException("socket not created");
    }

    protected void shutdownOutput() throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor != null) {
            try {
                Os.shutdown((FileDescriptor)fileDescriptor, (int)OsConstants.SHUT_WR);
                return;
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsIOException();
            }
        }
        throw new IOException("socket not created");
    }

    protected boolean supportsUrgentData() {
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(" fd:");
        stringBuilder.append(this.fd);
        return stringBuilder.toString();
    }

    class SocketInputStream
    extends InputStream {
        SocketInputStream() {
        }

        @Override
        public int available() throws IOException {
            FileDescriptor fileDescriptor = LocalSocketImpl.this.fd;
            if (fileDescriptor != null) {
                Int32Ref int32Ref = new Int32Ref(0);
                try {
                    Os.ioctlInt((FileDescriptor)fileDescriptor, (int)OsConstants.FIONREAD, (Int32Ref)int32Ref);
                    return int32Ref.value;
                }
                catch (ErrnoException errnoException) {
                    throw errnoException.rethrowAsIOException();
                }
            }
            throw new IOException("socket closed");
        }

        @Override
        public void close() throws IOException {
            LocalSocketImpl.this.close();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int read() throws IOException {
            Object object = LocalSocketImpl.this.readMonitor;
            synchronized (object) {
                Object object2 = LocalSocketImpl.this.fd;
                if (object2 != null) {
                    return LocalSocketImpl.this.read_native((FileDescriptor)object2);
                }
                object2 = new IOException("socket closed");
                throw object2;
            }
        }

        @Override
        public int read(byte[] arrby) throws IOException {
            return this.read(arrby, 0, arrby.length);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int read(byte[] object, int n, int n2) throws IOException {
            Object object2 = LocalSocketImpl.this.readMonitor;
            synchronized (object2) {
                FileDescriptor fileDescriptor = LocalSocketImpl.this.fd;
                if (fileDescriptor == null) {
                    object = new IOException("socket closed");
                    throw object;
                }
                if (n >= 0 && n2 >= 0 && n + n2 <= ((byte[])object).length) {
                    return LocalSocketImpl.this.readba_native(object, n, n2, fileDescriptor);
                }
                object = new ArrayIndexOutOfBoundsException();
                throw object;
            }
        }
    }

    class SocketOutputStream
    extends OutputStream {
        SocketOutputStream() {
        }

        @Override
        public void close() throws IOException {
            LocalSocketImpl.this.close();
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void flush() throws IOException {
            fileDescriptor = LocalSocketImpl.access$000(LocalSocketImpl.this);
            if (fileDescriptor == null) throw new IOException("socket closed");
            int32Ref = new Int32Ref(0);
            do lbl-1000: // 2 sources:
            {
                Os.ioctlInt((FileDescriptor)fileDescriptor, (int)OsConstants.TIOCOUTQ, (Int32Ref)int32Ref);
                if (int32Ref.value > 0) ** break block5
                return;
                break;
            } while (true);
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsIOException();
            }
            {
                try {
                    Thread.sleep(10L);
                    continue;
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                    return;
                }
                ** while (true)
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void write(int n) throws IOException {
            Object object = LocalSocketImpl.this.writeMonitor;
            synchronized (object) {
                Object object2 = LocalSocketImpl.this.fd;
                if (object2 != null) {
                    LocalSocketImpl.this.write_native(n, (FileDescriptor)object2);
                    return;
                }
                object2 = new IOException("socket closed");
                throw object2;
            }
        }

        @Override
        public void write(byte[] arrby) throws IOException {
            this.write(arrby, 0, arrby.length);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void write(byte[] object, int n, int n2) throws IOException {
            Object object2 = LocalSocketImpl.this.writeMonitor;
            synchronized (object2) {
                FileDescriptor fileDescriptor = LocalSocketImpl.this.fd;
                if (fileDescriptor == null) {
                    object = new IOException("socket closed");
                    throw object;
                }
                if (n >= 0 && n2 >= 0 && n + n2 <= ((byte[])object).length) {
                    LocalSocketImpl.this.writeba_native(object, n, n2, fileDescriptor);
                    return;
                }
                object = new ArrayIndexOutOfBoundsException();
                throw object;
            }
        }
    }

}

