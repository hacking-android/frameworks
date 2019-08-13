/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.BlockGuard
 */
package java.net;

import dalvik.system.BlockGuard;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.AbstractPlainSocketImpl;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.FileChannel;
import sun.net.ConnectionResetException;

class SocketOutputStream
extends FileOutputStream {
    private boolean closing = false;
    private AbstractPlainSocketImpl impl = null;
    private Socket socket = null;
    private byte[] temp = new byte[1];

    SocketOutputStream(AbstractPlainSocketImpl abstractPlainSocketImpl) throws IOException {
        super(abstractPlainSocketImpl.getFileDescriptor());
        this.impl = abstractPlainSocketImpl;
        this.socket = abstractPlainSocketImpl.getSocket();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void socketWrite(byte[] arrby, int n, int n2) throws IOException {
        Throwable throwable2222;
        Object object;
        if (n2 > 0 && n >= 0 && n2 <= arrby.length - n) {
            object = this.impl.acquireFD();
            BlockGuard.getThreadPolicy().onNetwork();
            this.socketWrite0((FileDescriptor)object, arrby, n, n2);
            this.impl.releaseFD();
            return;
        }
        if (n2 == 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("len == ");
        stringBuilder.append(n2);
        stringBuilder.append(" off == ");
        stringBuilder.append(n);
        stringBuilder.append(" buffer length == ");
        stringBuilder.append(arrby.length);
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
        {
            catch (Throwable throwable2222) {
            }
            catch (SocketException socketException) {}
            object = socketException;
            {
                if (socketException instanceof ConnectionResetException) {
                    this.impl.setConnectionResetPending();
                    object = new SocketException("Connection reset");
                }
                if (!this.impl.isClosedOrPending()) throw object;
                socketException = new SocketException("Socket closed");
                throw socketException;
            }
        }
        this.impl.releaseFD();
        throw throwable2222;
    }

    private native void socketWrite0(FileDescriptor var1, byte[] var2, int var3, int var4) throws IOException;

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
    public void write(int n) throws IOException {
        byte[] arrby = this.temp;
        arrby[0] = (byte)n;
        this.socketWrite(arrby, 0, 1);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.socketWrite(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.socketWrite(arrby, n, n2);
    }
}

