/*
 * Decompiled with CFR 0.145.
 */
package javax.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import javax.net.DefaultServerSocketFactory;

public abstract class ServerSocketFactory {
    private static ServerSocketFactory theFactory;

    protected ServerSocketFactory() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ServerSocketFactory getDefault() {
        synchronized (ServerSocketFactory.class) {
            if (theFactory == null) {
                DefaultServerSocketFactory defaultServerSocketFactory = new DefaultServerSocketFactory();
                theFactory = defaultServerSocketFactory;
            }
            return theFactory;
        }
    }

    public ServerSocket createServerSocket() throws IOException {
        throw new SocketException("Unbound server sockets not implemented");
    }

    public abstract ServerSocket createServerSocket(int var1) throws IOException;

    public abstract ServerSocket createServerSocket(int var1, int var2) throws IOException;

    public abstract ServerSocket createServerSocket(int var1, int var2, InetAddress var3) throws IOException;
}

