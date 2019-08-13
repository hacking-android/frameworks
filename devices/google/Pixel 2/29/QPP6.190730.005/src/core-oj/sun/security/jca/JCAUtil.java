/*
 * Decompiled with CFR 0.145.
 */
package sun.security.jca;

import java.security.SecureRandom;

public final class JCAUtil {
    private static final int ARRAY_SIZE = 4096;

    private JCAUtil() {
    }

    public static SecureRandom getSecureRandom() {
        return CachedSecureRandomHolder.instance;
    }

    public static int getTempArraySize(int n) {
        return Math.min(4096, n);
    }

    private static class CachedSecureRandomHolder {
        public static SecureRandom instance = new SecureRandom();

        private CachedSecureRandomHolder() {
        }
    }

}

