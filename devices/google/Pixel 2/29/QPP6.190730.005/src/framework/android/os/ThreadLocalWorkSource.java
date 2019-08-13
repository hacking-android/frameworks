/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.-$
 *  android.os.-$$Lambda
 *  android.os.-$$Lambda$ThreadLocalWorkSource
 *  android.os.-$$Lambda$ThreadLocalWorkSource$IP9vRFCDG5YwbWbXAEGHH52B9IE
 */
package android.os;

import android.os.-$;
import android.os._$$Lambda$ThreadLocalWorkSource$IP9vRFCDG5YwbWbXAEGHH52B9IE;

public final class ThreadLocalWorkSource {
    public static final int UID_NONE = -1;
    private static final ThreadLocal<Integer> sWorkSourceUid = ThreadLocal.withInitial(_$$Lambda$ThreadLocalWorkSource$IP9vRFCDG5YwbWbXAEGHH52B9IE.INSTANCE);

    private ThreadLocalWorkSource() {
    }

    public static long clear() {
        return ThreadLocalWorkSource.setUid(-1);
    }

    private static long getToken() {
        return sWorkSourceUid.get().intValue();
    }

    public static int getUid() {
        return sWorkSourceUid.get();
    }

    static /* synthetic */ Integer lambda$static$0() {
        return -1;
    }

    private static int parseUidFromToken(long l) {
        return (int)l;
    }

    public static void restore(long l) {
        sWorkSourceUid.set(ThreadLocalWorkSource.parseUidFromToken(l));
    }

    public static long setUid(int n) {
        long l = ThreadLocalWorkSource.getToken();
        sWorkSourceUid.set(n);
        return l;
    }
}

