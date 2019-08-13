/*
 * Decompiled with CFR 0.145.
 */
package android.net.ssl;

import com.android.org.conscrypt.Conscrypt;
import javax.net.ssl.SSLEngine;

public class SSLEngines {
    private SSLEngines() {
    }

    private static void checkSupported(SSLEngine sSLEngine) {
        if (SSLEngines.isSupportedEngine(sSLEngine)) {
            return;
        }
        throw new IllegalArgumentException("Engine is not a supported engine.");
    }

    public static boolean isSupportedEngine(SSLEngine sSLEngine) {
        return Conscrypt.isConscrypt(sSLEngine);
    }

    public static void setUseSessionTickets(SSLEngine sSLEngine, boolean bl) {
        SSLEngines.checkSupported(sSLEngine);
        Conscrypt.setUseSessionTickets(sSLEngine, bl);
    }
}

