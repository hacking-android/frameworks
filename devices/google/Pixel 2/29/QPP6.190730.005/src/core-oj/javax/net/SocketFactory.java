/*
 * Decompiled with CFR 0.145.
 */
package javax.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.net.DefaultSocketFactory;

public abstract class SocketFactory {
    private static SocketFactory theFactory;

    protected SocketFactory() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SocketFactory getDefault() {
        synchronized (SocketFactory.class) {
            if (theFactory == null) {
                DefaultSocketFactory defaultSocketFactory = new DefaultSocketFactory();
                theFactory = defaultSocketFactory;
            }
            return theFactory;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setDefault(SocketFactory socketFactory) {
        synchronized (SocketFactory.class) {
            theFactory = socketFactory;
            return;
        }
    }

    public Socket createSocket() throws IOException {
        UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
        SocketException socketException = new SocketException("Unconnected sockets not implemented");
        socketException.initCause(unsupportedOperationException);
        throw socketException;
    }

    public abstract Socket createSocket(String var1, int var2) throws IOException, UnknownHostException;

    public abstract Socket createSocket(String var1, int var2, InetAddress var3, int var4) throws IOException, UnknownHostException;

    public abstract Socket createSocket(InetAddress var1, int var2) throws IOException;

    public abstract Socket createSocket(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException;
}

