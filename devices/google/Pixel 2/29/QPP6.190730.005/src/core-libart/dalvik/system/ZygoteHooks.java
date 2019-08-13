/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import android.icu.impl.CacheValue;
import android.icu.text.DecimalFormatSymbols;
import android.icu.util.ULocale;
import dalvik.system.VMRuntime;
import java.io.File;

public final class ZygoteHooks {
    private static long token;

    private ZygoteHooks() {
    }

    public static void gcAndFinalize() {
        VMRuntime vMRuntime = VMRuntime.getRuntime();
        System.gc();
        vMRuntime.runFinalizationSync();
        System.gc();
    }

    private static native void nativePostForkChild(long var0, int var2, boolean var3, boolean var4, String var5);

    private static native void nativePostForkSystemServer();

    private static native void nativePostZygoteFork();

    private static native long nativePreFork();

    public static void onBeginPreload() {
        CacheValue.setStrength(CacheValue.Strength.STRONG);
        ULocale[] arruLocale = new ULocale[3];
        ULocale uLocale = ULocale.ROOT;
        arruLocale[0] = uLocale;
        arruLocale[1] = ULocale.US;
        arruLocale[2] = ULocale.getDefault();
        int n = arruLocale.length;
        for (int i = 0; i < n; ++i) {
            new DecimalFormatSymbols(arruLocale[i]);
        }
    }

    public static void onEndPreload() {
        CacheValue.setStrength(CacheValue.Strength.SOFT);
    }

    public static void postForkChild(int n, boolean bl, boolean bl2, String string) {
        ZygoteHooks.nativePostForkChild(token, n, bl, bl2, string);
        Math.setRandomSeedInternal((long)System.currentTimeMillis());
    }

    public static void postForkCommon() {
        Daemons.startPostZygoteFork();
        ZygoteHooks.nativePostZygoteFork();
    }

    public static void postForkSystemServer() {
        ZygoteHooks.nativePostForkSystemServer();
    }

    public static void preFork() {
        Daemons.stop();
        token = ZygoteHooks.nativePreFork();
        ZygoteHooks.waitUntilAllThreadsStopped();
    }

    public static native void startZygoteNoThreadCreation();

    public static native void stopZygoteNoThreadCreation();

    private static void waitUntilAllThreadsStopped() {
        File file = new File("/proc/self/task");
        while (file.list().length > 1) {
            Thread.yield();
        }
    }
}

