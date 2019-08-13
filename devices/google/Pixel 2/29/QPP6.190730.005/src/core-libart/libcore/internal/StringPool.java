/*
 * Decompiled with CFR 0.145.
 */
package libcore.internal;

public final class StringPool {
    private final String[] pool = new String[512];

    private static boolean contentEquals(String string, char[] arrc, int n, int n2) {
        if (string.length() != n2) {
            return false;
        }
        for (int i = 0; i < n2; ++i) {
            if (arrc[n + i] == string.charAt(i)) continue;
            return false;
        }
        return true;
    }

    public String get(char[] object, int n, int n2) {
        int n3;
        int n4 = 0;
        for (n3 = n; n3 < n + n2; ++n3) {
            n4 = n4 * 31 + object[n3];
        }
        n3 = n4 ^ (n4 >>> 20 ^ n4 >>> 12);
        Object object2 = this.pool;
        n3 = ((String[])object2).length - 1 & (n3 ^ (n3 >>> 7 ^ n3 >>> 4));
        if ((object2 = object2[n3]) != null && StringPool.contentEquals((String)object2, object, n, n2)) {
            return object2;
        }
        this.pool[n3] = object = new String((char[])object, n, n2);
        return object;
    }
}

