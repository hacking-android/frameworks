/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.net.SocketException;
import java.security.AccessController;
import java.util.concurrent.atomic.AtomicInteger;
import sun.security.action.GetPropertyAction;

public class ResourceManager {
    private static final int DEFAULT_MAX_SOCKETS = 25;
    private static final int maxSockets;
    private static final AtomicInteger numSockets;

    static {
        int n;
        String string = AccessController.doPrivileged(new GetPropertyAction("sun.net.maxDatagramSockets"));
        int n2 = n = 25;
        if (string != null) {
            try {
                n2 = Integer.parseInt(string);
            }
            catch (NumberFormatException numberFormatException) {
                n2 = n;
            }
        }
        maxSockets = n2;
        numSockets = new AtomicInteger(0);
    }

    public static void afterUdpClose() {
        if (System.getSecurityManager() != null) {
            numSockets.decrementAndGet();
        }
    }

    public static void beforeUdpCreate() throws SocketException {
        if (System.getSecurityManager() != null && numSockets.incrementAndGet() > maxSockets) {
            numSockets.decrementAndGet();
            throw new SocketException("maximum number of DatagramSockets reached");
        }
    }
}

