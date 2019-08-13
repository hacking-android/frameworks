/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.locks;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;
import sun.misc.Unsafe;

public class LockSupport {
    private static final long PARKBLOCKER;
    private static final long SECONDARY;
    private static final Unsafe U;

    static {
        U = Unsafe.getUnsafe();
        try {
            PARKBLOCKER = U.objectFieldOffset(Thread.class.getDeclaredField("parkBlocker"));
            SECONDARY = U.objectFieldOffset(Thread.class.getDeclaredField("threadLocalRandomSecondarySeed"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    private LockSupport() {
    }

    public static Object getBlocker(Thread thread) {
        if (thread != null) {
            return U.getObjectVolatile(thread, PARKBLOCKER);
        }
        throw new NullPointerException();
    }

    static final int nextSecondarySeed() {
        Thread thread = Thread.currentThread();
        int n = U.getInt(thread, SECONDARY);
        if (n != 0) {
            n = n << 13 ^ n;
            n ^= n >>> 17;
            n ^= n << 5;
        } else {
            n = ThreadLocalRandom.current().nextInt();
            if (n == 0) {
                n = 1;
            }
        }
        U.putInt(thread, SECONDARY, n);
        return n;
    }

    public static void park() {
        U.park(false, 0L);
    }

    public static void park(Object object) {
        Thread thread = Thread.currentThread();
        LockSupport.setBlocker(thread, object);
        U.park(false, 0L);
        LockSupport.setBlocker(thread, null);
    }

    public static void parkNanos(long l) {
        if (l > 0L) {
            U.park(false, l);
        }
    }

    public static void parkNanos(Object object, long l) {
        if (l > 0L) {
            Thread thread = Thread.currentThread();
            LockSupport.setBlocker(thread, object);
            U.park(false, l);
            LockSupport.setBlocker(thread, null);
        }
    }

    public static void parkUntil(long l) {
        U.park(true, l);
    }

    public static void parkUntil(Object object, long l) {
        Thread thread = Thread.currentThread();
        LockSupport.setBlocker(thread, object);
        U.park(true, l);
        LockSupport.setBlocker(thread, null);
    }

    private static void setBlocker(Thread thread, Object object) {
        U.putObject(thread, PARKBLOCKER, object);
    }

    public static void unpark(Thread thread) {
        if (thread != null) {
            U.unpark(thread);
        }
    }
}

