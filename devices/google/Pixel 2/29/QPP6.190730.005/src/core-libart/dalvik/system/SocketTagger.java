/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.FileDescriptor;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public abstract class SocketTagger {
    private static SocketTagger tagger = new SocketTagger(){

        @Override
        public void tag(FileDescriptor fileDescriptor) throws SocketException {
        }

        @Override
        public void untag(FileDescriptor fileDescriptor) throws SocketException {
        }
    };

    @UnsupportedAppUsage
    public static SocketTagger get() {
        synchronized (SocketTagger.class) {
            SocketTagger socketTagger = tagger;
            return socketTagger;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void set(SocketTagger object) {
        synchronized (SocketTagger.class) {
            if (object != null) {
                tagger = object;
                return;
            }
            try {
                object = new NullPointerException("tagger == null");
                throw object;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public abstract void tag(FileDescriptor var1) throws SocketException;

    public final void tag(DatagramSocket datagramSocket) throws SocketException {
        if (!datagramSocket.isClosed()) {
            this.tag(datagramSocket.getFileDescriptor$());
        }
    }

    @UnsupportedAppUsage
    public final void tag(Socket socket) throws SocketException {
        if (!socket.isClosed()) {
            this.tag(socket.getFileDescriptor$());
        }
    }

    public abstract void untag(FileDescriptor var1) throws SocketException;

    public final void untag(DatagramSocket datagramSocket) throws SocketException {
        if (!datagramSocket.isClosed()) {
            this.untag(datagramSocket.getFileDescriptor$());
        }
    }

    @UnsupportedAppUsage
    public final void untag(Socket socket) throws SocketException {
        if (!socket.isClosed()) {
            this.untag(socket.getFileDescriptor$());
        }
    }

}

