/*
 * Decompiled with CFR 0.145.
 */
package android.net.ssl;

import com.android.org.conscrypt.Conscrypt;
import javax.net.ssl.SSLSocket;

public class SSLSockets {
    private SSLSockets() {
    }

    private static void checkSupported(SSLSocket sSLSocket) {
        if (SSLSockets.isSupportedSocket(sSLSocket)) {
            return;
        }
        throw new IllegalArgumentException("Socket is not a supported socket.");
    }

    public static boolean isSupportedSocket(SSLSocket sSLSocket) {
        return Conscrypt.isConscrypt(sSLSocket);
    }

    public static void setUseSessionTickets(SSLSocket sSLSocket, boolean bl) {
        SSLSockets.checkSupported(sSLSocket);
        Conscrypt.setUseSessionTickets(sSLSocket, bl);
    }
}

