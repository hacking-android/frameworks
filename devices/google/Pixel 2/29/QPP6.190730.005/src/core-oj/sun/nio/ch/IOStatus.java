/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

public final class IOStatus {
    public static final int EOF = -1;
    public static final int INTERRUPTED = -3;
    public static final int THROWN = -5;
    public static final int UNAVAILABLE = -2;
    public static final int UNSUPPORTED = -4;
    public static final int UNSUPPORTED_CASE = -6;

    private IOStatus() {
    }

    public static boolean check(int n) {
        boolean bl = n >= -2;
        return bl;
    }

    public static boolean check(long l) {
        boolean bl = l >= -2L;
        return bl;
    }

    public static boolean checkAll(long l) {
        boolean bl = l > -1L || l < -6L;
        return bl;
    }

    public static int normalize(int n) {
        if (n == -2) {
            return 0;
        }
        return n;
    }

    public static long normalize(long l) {
        if (l == -2L) {
            return 0L;
        }
        return l;
    }
}

