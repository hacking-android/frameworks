/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.BlockGuard
 */
package java.net;

import dalvik.system.BlockGuard;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.AbstractPlainSocketImpl;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.FileChannel;
import sun.net.ConnectionResetException;

class SocketInputStream
extends FileInputStream {
    private boolean closing = false;
    private boolean eof;
    private AbstractPlainSocketImpl impl = null;
    private Socket socket = null;
    private byte[] temp;

    SocketInputStream(AbstractPlainSocketImpl abstractPlainSocketImpl) throws IOException {
        super(abstractPlainSocketImpl.getFileDescriptor());
        this.impl = abstractPlainSocketImpl;
        this.socket = abstractPlainSocketImpl.getSocket();
    }

    private int socketRead(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3) throws IOException {
        return this.socketRead0(fileDescriptor, arrby, n, n2, n3);
    }

    private native int socketRead0(FileDescriptor var1, byte[] var2, int var3, int var4, int var5) throws IOException;

    @Override
    public int available() throws IOException {
        if (this.eof) {
            return 0;
        }
        return this.impl.available();
    }

    @Override
    public void close() throws IOException {
        if (this.closing) {
            return;
        }
        this.closing = true;
        Socket socket = this.socket;
        if (socket != null) {
            if (!socket.isClosed()) {
                this.socket.close();
            }
        } else {
            this.impl.close();
        }
        this.closing = false;
    }

    @Override
    protected void finalize() {
    }

    @Override
    public final FileChannel getChannel() {
        return null;
    }

    @Override
    public int read() throws IOException {
        if (this.eof) {
            return -1;
        }
        this.temp = new byte[1];
        if (this.read(this.temp, 0, 1) <= 0) {
            return -1;
        }
        return this.temp[0] & 255;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        return this.read(arrby, n, n2, this.impl.getTimeout());
    }

    int read(byte[] arrby, int n, int n2, int n3) throws IOException {
        if (this.eof) {
            return -1;
        }
        if (!this.impl.isConnectionReset()) {
            if (n2 > 0 && n >= 0 && n2 <= arrby.length - n) {
                boolean bl = false;
                FileDescriptor fileDescriptor = this.impl.acquireFD();
                try {
                    BlockGuard.getThreadPolicy().onNetwork();
                    int n4 = this.socketRead(fileDescriptor, arrby, n, n2, n3);
                    if (n4 > 0) {
                        this.impl.releaseFD();
                        return n4;
                    }
                    this.impl.releaseFD();
                }
                catch (Throwable throwable) {
                    this.impl.releaseFD();
                    throw throwable;
                }
                catch (ConnectionResetException connectionResetException) {
                    bl = true;
                    this.impl.releaseFD();
                }
                if (bl) {
                    this.impl.setConnectionResetPending();
                    this.impl.acquireFD();
                    try {
                        n = this.socketRead(fileDescriptor, arrby, n, n2, n3);
                        if (n > 0) {
                            this.impl.releaseFD();
                            return n;
                        }
                        this.impl.releaseFD();
                    }
                    catch (Throwable throwable) {
                        this.impl.releaseFD();
                        throw throwable;
                    }
                    catch (ConnectionResetException connectionResetException) {
                        this.impl.releaseFD();
                    }
                }
                if (!this.impl.isClosedOrPending()) {
                    if (this.impl.isConnectionResetPending()) {
                        this.impl.setConnectionReset();
                    }
                    if (!this.impl.isConnectionReset()) {
                        this.eof = true;
                        return -1;
                    }
                    throw new SocketException("Connection reset");
                }
                throw new SocketException("Socket closed");
            }
            if (n2 == 0) {
                return 0;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("length == ");
            stringBuilder.append(n2);
            stringBuilder.append(" off == ");
            stringBuilder.append(n);
            stringBuilder.append(" buffer length == ");
            stringBuilder.append(arrby.length);
            throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
        }
        throw new SocketException("Connection reset");
    }

    void setEOF(boolean bl) {
        this.eof = bl;
    }

    @Override
    public long skip(long l) throws IOException {
        long l2;
        int n;
        if (l <= 0L) {
            return 0L;
        }
        int n2 = (int)Math.min(1024L, l2);
        byte[] arrby = new byte[n2];
        for (l2 = l; l2 > 0L && (n = this.read(arrby, 0, (int)Math.min((long)n2, l2))) >= 0; l2 -= (long)n) {
        }
        return l - l2;
    }
}

