/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructCapUserData
 *  android.system.StructCapUserHeader
 *  dalvik.system.DexFile
 *  dalvik.system.VMRuntime
 *  dalvik.system.VMRuntime$HiddenApiUsageLogger
 *  dalvik.system.ZygoteHooks
 *  libcore.io.IoUtils
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.app.ApplicationLoaders;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.VersionedPackage;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.IInstalld;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.ZygoteProcess;
import android.os.storage.StorageManager;
import android.security.keystore.AndroidKeyStoreProvider;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructCapUserData;
import android.system.StructCapUserHeader;
import android.text.Hyphenator;
import android.util.EventLog;
import android.util.Log;
import android.util.TimingsTraceLog;
import android.webkit.WebViewFactory;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.ClassLoaderFactory;
import com.android.internal.os.RuntimeInit;
import com.android.internal.os.WrapperInit;
import com.android.internal.os.Zygote;
import com.android.internal.os.ZygoteArguments;
import com.android.internal.os.ZygoteServer;
import com.android.internal.util.Preconditions;
import dalvik.system.DexFile;
import dalvik.system.VMRuntime;
import dalvik.system.ZygoteHooks;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.Provider;
import java.security.Security;
import java.util.List;
import libcore.io.IoUtils;

public class ZygoteInit {
    private static final String ABI_LIST_ARG = "--abi-list=";
    private static final int LOG_BOOT_PROGRESS_PRELOAD_END = 3030;
    private static final int LOG_BOOT_PROGRESS_PRELOAD_START = 3020;
    private static final String PRELOADED_CLASSES = "/system/etc/preloaded-classes";
    private static final int PRELOAD_GC_THRESHOLD = 50000;
    public static final boolean PRELOAD_RESOURCES = true;
    private static final String PROPERTY_DISABLE_GRAPHICS_DRIVER_PRELOADING = "ro.zygote.disable_gl_preload";
    private static final int ROOT_GID = 0;
    private static final int ROOT_UID = 0;
    private static final String SOCKET_NAME_ARG = "--socket-name=";
    private static final String TAG = "Zygote";
    private static final int UNPRIVILEGED_GID = 9999;
    private static final int UNPRIVILEGED_UID = 9999;
    @UnsupportedAppUsage
    private static Resources mResources;
    private static ClassLoader sCachedSystemServerClassLoader;
    private static boolean sPreloadComplete;

    static {
        sCachedSystemServerClassLoader = null;
    }

    private ZygoteInit() {
    }

    private static void beginPreload() {
        Log.i(TAG, "Calling ZygoteHooks.beginPreload()");
        ZygoteHooks.onBeginPreload();
    }

    private static void cacheNonBootClasspathClassLoaders() {
        SharedLibraryInfo sharedLibraryInfo = new SharedLibraryInfo("/system/framework/android.hidl.base-V1.0-java.jar", null, null, null, 0L, 0, null, null, null);
        SharedLibraryInfo sharedLibraryInfo2 = new SharedLibraryInfo("/system/framework/android.hidl.manager-V1.0-java.jar", null, null, null, 0L, 0, null, null, null);
        sharedLibraryInfo2.addDependency(sharedLibraryInfo);
        ApplicationLoaders.getDefault().createAndCacheNonBootclasspathSystemClassLoaders(new SharedLibraryInfo[]{sharedLibraryInfo, sharedLibraryInfo2});
    }

    static final Runnable childZygoteInit(int n, String[] object, ClassLoader classLoader) {
        object = new RuntimeInit.Arguments((String[])object);
        return RuntimeInit.findStaticMain(object.startClass, object.startArgs, classLoader);
    }

    static ClassLoader createPathClassLoader(String string2, int n) {
        String string3 = System.getProperty("java.library.path");
        return ClassLoaderFactory.createClassLoader(string2, string3, string3, ClassLoader.getSystemClassLoader().getParent(), n, true, null);
    }

    private static void createSystemServerClassLoader() {
        if (sCachedSystemServerClassLoader != null) {
            return;
        }
        String string2 = Os.getenv((String)"SYSTEMSERVERCLASSPATH");
        if (string2 != null) {
            sCachedSystemServerClassLoader = ZygoteInit.createPathClassLoader(string2, 10000);
        }
    }

    private static String encodeSystemServerClassPath(String string2, String string3) {
        if (string2 != null && !string2.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(":");
            stringBuilder.append(string3);
            string2 = stringBuilder.toString();
        } else {
            string2 = string3;
        }
        return string2;
    }

    private static void endPreload() {
        ZygoteHooks.onEndPreload();
        Log.i(TAG, "Called ZygoteHooks.endPreload()");
    }

    private static Runnable forkSystemServer(String string2, String string3, ZygoteServer zygoteServer) {
        block6 : {
            ZygoteArguments zygoteArguments;
            long l = ZygoteInit.posixCapabilitiesAsBits(OsConstants.CAP_IPC_LOCK, OsConstants.CAP_KILL, OsConstants.CAP_NET_ADMIN, OsConstants.CAP_NET_BIND_SERVICE, OsConstants.CAP_NET_BROADCAST, OsConstants.CAP_NET_RAW, OsConstants.CAP_SYS_MODULE, OsConstants.CAP_SYS_NICE, OsConstants.CAP_SYS_PTRACE, OsConstants.CAP_SYS_TIME, OsConstants.CAP_SYS_TTY_CONFIG, OsConstants.CAP_WAKE_ALARM, OsConstants.CAP_BLOCK_SUSPEND);
            Object object = new StructCapUserHeader(OsConstants._LINUX_CAPABILITY_VERSION_3, 0);
            try {
                object = Os.capget((StructCapUserHeader)object);
            }
            catch (ErrnoException errnoException) {
                throw new RuntimeException("Failed to capget()", errnoException);
            }
            long l2 = object[0].effective;
            l2 = ((long)object[1].effective << 32 | l2) & l;
            object = new StringBuilder();
            ((StringBuilder)object).append("--capabilities=");
            ((StringBuilder)object).append(l2);
            ((StringBuilder)object).append(",");
            ((StringBuilder)object).append(l2);
            object = ((StringBuilder)object).toString();
            try {
                int n;
                zygoteArguments = new ZygoteArguments(new String[]{"--setuid=1000", "--setgid=1000", "--setgroups=1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1018,1021,1023,1024,1032,1065,3001,3002,3003,3006,3007,3009,3010", object, "--nice-name=system_server", "--runtime-args", "--target-sdk-version=10000", "com.android.server.SystemServer"});
                Zygote.applyDebuggerSystemProperty(zygoteArguments);
                Zygote.applyInvokeWithSystemProperty(zygoteArguments);
                if (SystemProperties.getBoolean("dalvik.vm.profilesystemserver", false)) {
                    zygoteArguments.mRuntimeFlags |= 16384;
                }
                if ((n = Zygote.forkSystemServer(zygoteArguments.mUid, zygoteArguments.mGid, zygoteArguments.mGids, zygoteArguments.mRuntimeFlags, null, zygoteArguments.mPermittedCapabilities, zygoteArguments.mEffectiveCapabilities)) != 0) break block6;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new RuntimeException(illegalArgumentException);
            }
            if (ZygoteInit.hasSecondZygote(string2)) {
                ZygoteInit.waitForSecondaryZygote(string3);
            }
            zygoteServer.closeServerSocket();
            return ZygoteInit.handleSystemServerProcess(zygoteArguments);
        }
        return null;
    }

    private static void gcAndFinalize() {
        ZygoteHooks.gcAndFinalize();
    }

    private static String getSystemServerClassLoaderContext(String string2) {
        if (string2 == null) {
            string2 = "PCL[]";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PCL[");
            stringBuilder.append(string2);
            stringBuilder.append("]");
            string2 = stringBuilder.toString();
        }
        return string2;
    }

    private static Runnable handleSystemServerProcess(ZygoteArguments zygoteArguments) {
        String string2;
        Os.umask((int)(OsConstants.S_IRWXG | OsConstants.S_IRWXO));
        if (zygoteArguments.mNiceName != null) {
            Process.setArgV0(zygoteArguments.mNiceName);
        }
        if ((string2 = Os.getenv((String)"SYSTEMSERVERCLASSPATH")) != null) {
            if (ZygoteInit.performSystemServerDexOpt(string2)) {
                sCachedSystemServerClassLoader = null;
            }
            if (SystemProperties.getBoolean("dalvik.vm.profilesystemserver", false) && (Build.IS_USERDEBUG || Build.IS_ENG)) {
                try {
                    ZygoteInit.prepareSystemServerProfile(string2);
                }
                catch (Exception exception) {
                    Log.wtf(TAG, "Failed to set up system server profile", exception);
                }
            }
        }
        if (zygoteArguments.mInvokeWith != null) {
            String[] arrstring;
            String[] arrstring2 = arrstring = zygoteArguments.mRemainingArgs;
            if (string2 != null) {
                arrstring2 = new String[arrstring.length + 2];
                arrstring2[0] = "-cp";
                arrstring2[1] = string2;
                System.arraycopy(arrstring, 0, arrstring2, 2, arrstring.length);
            }
            WrapperInit.execApplication(zygoteArguments.mInvokeWith, zygoteArguments.mNiceName, zygoteArguments.mTargetSdkVersion, VMRuntime.getCurrentInstructionSet(), null, arrstring2);
            throw new IllegalStateException("Unexpected return from WrapperInit.execApplication");
        }
        ZygoteInit.createSystemServerClassLoader();
        ClassLoader classLoader = sCachedSystemServerClassLoader;
        if (classLoader != null) {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        return ZygoteInit.zygoteInit(zygoteArguments.mTargetSdkVersion, zygoteArguments.mRemainingArgs, classLoader);
    }

    private static boolean hasSecondZygote(String string2) {
        return SystemProperties.get("ro.product.cpu.abilist").equals(string2) ^ true;
    }

    static boolean isPreloadComplete() {
        return sPreloadComplete;
    }

    public static void lazyPreload() {
        Preconditions.checkState(sPreloadComplete ^ true);
        Log.i(TAG, "Lazily preloading resources.");
        ZygoteInit.preload(new TimingsTraceLog("ZygoteInitTiming_lazy", 16384L));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public static void main(String[] object) {
        ZygoteServer object2;
        Object object3;
        block22 : {
            Object object5;
            Object object4;
            boolean bl;
            boolean bl3;
            TimingsTraceLog timingsTraceLog;
            block21 : {
                object2 = null;
                ZygoteHooks.startZygoteNoThreadCreation();
                Os.setpgid((int)0, (int)0);
                object3 = object2;
                {
                    catch (ErrnoException errnoException) {
                        throw new RuntimeException("Failed to setpgid(0,0)", errnoException);
                    }
                }
                if (!"1".equals(SystemProperties.get("sys.boot_completed"))) {
                    object3 = object2;
                    MetricsLogger.histogram(null, "boot_zygote_init", (int)SystemClock.elapsedRealtime());
                }
                object3 = object2;
                object4 = Process.is64Bit() ? "Zygote64Timing" : "Zygote32Timing";
                object3 = object2;
                object3 = object2;
                timingsTraceLog = new TimingsTraceLog((String)object4, 16384L);
                object3 = object2;
                timingsTraceLog.traceBegin("ZygoteInit");
                object3 = object2;
                RuntimeInit.enableDdms();
                bl = false;
                object5 = "zygote";
                object4 = null;
                bl3 = false;
                int n = 1;
                do {
                    object3 = object2;
                    if (n >= ((Object)object).length) break block21;
                    object3 = object2;
                    if ("start-system-server".equals(object[n])) {
                        bl = true;
                    } else {
                        object3 = object2;
                        if ("--enable-lazy-preload".equals(object[n])) {
                            bl3 = true;
                        } else {
                            object3 = object2;
                            if (((String)object[n]).startsWith(ABI_LIST_ARG)) {
                                object3 = object2;
                                object4 = ((String)object[n]).substring(ABI_LIST_ARG.length());
                            } else {
                                object3 = object2;
                                if (!((String)object[n]).startsWith(SOCKET_NAME_ARG)) break;
                                object3 = object2;
                                object5 = ((String)object[n]).substring(SOCKET_NAME_ARG.length());
                            }
                        }
                    }
                    ++n;
                } while (true);
                object3 = object2;
                object3 = object2;
                object3 = object2;
                object5 = new StringBuilder();
                object3 = object2;
                ((StringBuilder)object5).append("Unknown command line argument: ");
                object3 = object2;
                ((StringBuilder)object5).append((String)object[n]);
                object3 = object2;
                object4 = new RuntimeException(((StringBuilder)object5).toString());
                object3 = object2;
                throw object4;
            }
            object3 = object2;
            boolean bl2 = ((String)object5).equals("zygote");
            if (object4 == null) break block22;
            if (!bl3) {
                object3 = object2;
                timingsTraceLog.traceBegin("ZygotePreload");
                object3 = object2;
                EventLog.writeEvent(3020, SystemClock.uptimeMillis());
                object3 = object2;
                ZygoteInit.preload(timingsTraceLog);
                object3 = object2;
                EventLog.writeEvent(3030, SystemClock.uptimeMillis());
                object3 = object2;
                timingsTraceLog.traceEnd();
            } else {
                object3 = object2;
                Zygote.resetNicePriority();
            }
            object3 = object2;
            timingsTraceLog.traceBegin("PostZygoteInitGC");
            object3 = object2;
            ZygoteInit.gcAndFinalize();
            object3 = object2;
            timingsTraceLog.traceEnd();
            object3 = object2;
            timingsTraceLog.traceEnd();
            object3 = object2;
            Trace.setTracingEnabled(false, 0);
            object3 = object2;
            Zygote.initNativeState(bl2);
            object3 = object2;
            ZygoteHooks.stopZygoteNoThreadCreation();
            object3 = object2;
            object3 = object2;
            object = new ZygoteServer(bl2);
            if (bl) {
                object3 = object;
                if ((object5 = ZygoteInit.forkSystemServer((String)object4, (String)object5, (ZygoteServer)object)) != null) {
                    object3 = object;
                    object5.run();
                    ((ZygoteServer)object).closeServerSocket();
                    return;
                }
            }
            object3 = object;
            try {
                Log.i(TAG, "Accepting command socket connections");
                object3 = object;
                object4 = ((ZygoteServer)object).runSelectLoop((String)object4);
                ((ZygoteServer)object).closeServerSocket();
                if (object4 == null) return;
                object4.run();
                return;
            }
            catch (Throwable throwable) {
                try {
                    Log.e(TAG, "System zygote died with exception", throwable);
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (object3 == null) throw throwable2;
                    ((ZygoteServer)object3).closeServerSocket();
                    throw throwable2;
                }
            }
        }
        object3 = object2;
        object3 = object2;
        object = new RuntimeException("No ABI list supplied.");
        object3 = object2;
        throw object;
    }

    private static void maybePreloadGraphicsDriver() {
        if (!SystemProperties.getBoolean(PROPERTY_DISABLE_GRAPHICS_DRIVER_PRELOADING, false)) {
            ZygoteInit.nativePreloadGraphicsDriver();
        }
    }

    private static native void nativePreloadAppProcessHALs();

    static native void nativePreloadGraphicsDriver();

    private static final native void nativeZygoteInit();

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static boolean performSystemServerDexOpt(String string2) {
        String[] arrstring = string2.split(":");
        IInstalld iInstalld = IInstalld.Stub.asInterface(ServiceManager.getService("installd"));
        String string3 = VMRuntime.getRuntime().vmInstructionSet();
        int n = arrstring.length;
        string2 = "";
        boolean bl = false;
        int i = 0;
        while (i < n) {
            block7 : {
                int n2;
                CharSequence charSequence;
                String string4 = arrstring[i];
                CharSequence charSequence2 = SystemProperties.get("dalvik.vm.systemservercompilerfilter", "speed");
                try {
                    n2 = DexFile.getDexOptNeeded((String)string4, (String)string3, (String)charSequence2, null, (boolean)false, (boolean)false);
                }
                catch (IOException iOException) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Error checking classpath element for system server: ");
                    ((StringBuilder)charSequence).append(string4);
                    Log.w("Zygote", ((StringBuilder)charSequence).toString(), iOException);
                    n2 = 0;
                }
                if (n2 != 0) {
                    charSequence = StorageManager.UUID_PRIVATE_INTERNAL;
                    String string5 = ZygoteInit.getSystemServerClassLoaderContext(string2);
                    try {
                        iInstalld.dexopt(string4, 1000, "*", string3, n2, null, 0, (String)charSequence2, (String)charSequence, string5, null, false, 0, null, null, "server-dexopt");
                        bl = true;
                    }
                    catch (RemoteException | ServiceSpecificException exception) {
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append("Failed compiling classpath element for system server: ");
                        ((StringBuilder)charSequence2).append(string4);
                        Log.w("Zygote", ((StringBuilder)charSequence2).toString(), exception);
                    }
                }
                string2 = ZygoteInit.encodeSystemServerClassPath(string2, string4);
                break block7;
                catch (FileNotFoundException fileNotFoundException) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("Missing classpath element for system server: ");
                    ((StringBuilder)charSequence2).append(string4);
                    Log.w("Zygote", ((StringBuilder)charSequence2).toString());
                }
            }
            ++i;
        }
        return bl;
    }

    private static long posixCapabilitiesAsBits(int ... arrn) {
        long l = 0L;
        for (int n : arrn) {
            if (n >= 0 && n <= OsConstants.CAP_LAST_CAP) {
                l |= 1L << n;
                continue;
            }
            throw new IllegalArgumentException(String.valueOf(n));
        }
        return l;
    }

    static void preload(TimingsTraceLog timingsTraceLog) {
        Log.d("Zygote", "begin preload");
        timingsTraceLog.traceBegin("BeginPreload");
        ZygoteInit.beginPreload();
        timingsTraceLog.traceEnd();
        timingsTraceLog.traceBegin("PreloadClasses");
        ZygoteInit.preloadClasses();
        timingsTraceLog.traceEnd();
        timingsTraceLog.traceBegin("CacheNonBootClasspathClassLoaders");
        ZygoteInit.cacheNonBootClasspathClassLoaders();
        timingsTraceLog.traceEnd();
        timingsTraceLog.traceBegin("PreloadResources");
        ZygoteInit.preloadResources();
        timingsTraceLog.traceEnd();
        Trace.traceBegin(16384L, "PreloadAppProcessHALs");
        ZygoteInit.nativePreloadAppProcessHALs();
        Trace.traceEnd(16384L);
        Trace.traceBegin(16384L, "PreloadGraphicsDriver");
        ZygoteInit.maybePreloadGraphicsDriver();
        Trace.traceEnd(16384L);
        ZygoteInit.preloadSharedLibraries();
        ZygoteInit.preloadTextResources();
        WebViewFactory.prepareWebViewInZygote();
        ZygoteInit.endPreload();
        ZygoteInit.warmUpJcaProviders();
        Log.d("Zygote", "end preload");
        sPreloadComplete = true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void preloadClasses() {
        block34 : {
            block35 : {
                block36 : {
                    var0 = VMRuntime.getRuntime();
                    try {
                        var1_1 = new FileInputStream("/system/etc/preloaded-classes");
                    }
                    catch (FileNotFoundException var8_23) {
                        Log.e("Zygote", "Couldn't find /system/etc/preloaded-classes.");
                        return;
                    }
                    Log.i("Zygote", "Preloading classes...");
                    var2_2 = SystemClock.uptimeMillis();
                    var4_3 = Os.getuid();
                    var5_4 = Os.getgid();
                    var7_6 = var6_5 = 0;
                    if (var4_3 == 0) {
                        var7_6 = var6_5;
                        if (var5_4 == 0) {
                            try {
                                Os.setregid((int)0, (int)9999);
                                Os.setreuid((int)0, (int)9999);
                                var7_6 = 1;
                            }
                            catch (ErrnoException var8_7) {
                                throw new RuntimeException("Failed to drop root", var8_7);
                            }
                        }
                    }
                    var9_24 = var0.getTargetHeapUtilization();
                    var0.setTargetHeapUtilization(0.8f);
                    var10_25 = var9_24;
                    var11_26 = var9_24;
                    try {
                        var10_25 = var9_24;
                        var11_26 = var9_24;
                        var10_25 = var9_24;
                        var11_26 = var9_24;
                        var8_8 = new InputStreamReader(var1_1);
                        var10_25 = var9_24;
                        var11_26 = var9_24;
                        var12_27 = new BufferedReader((Reader)var8_8, 256);
                        var6_5 = 0;
                    }
                    catch (Throwable var8_16) {
                        var9_24 = var10_25;
                        break block34;
                    }
                    catch (IOException var8_17) {
                        var9_24 = var11_26;
                        break block35;
                    }
                    do {
                        var10_25 = var9_24;
                        var11_26 = var9_24;
                        var8_8 = var12_27.readLine();
                        if (var8_8 == null) break block36;
                        if ((var8_8 = var8_8.trim()).startsWith("#") || (var13_29 = var8_8.equals(""))) continue;
                        Trace.traceBegin(16384L, (String)var8_8);
                        try {
                            Class.forName((String)var8_8, true, null);
                            ++var6_5;
                            ** GOTO lbl98
                        }
                        catch (Throwable var12_28) {
                            try {
                                block37 : {
                                    var14_30 = new StringBuilder();
                                    var14_30.append("Error preloading ");
                                    var14_30.append((String)var8_8);
                                    var14_30.append(".");
                                    Log.e("Zygote", var14_30.toString(), var12_28);
                                    if (var12_28 instanceof Error != false) throw (Error)var12_28;
                                    if (var12_28 instanceof RuntimeException) {
                                        throw (RuntimeException)var12_28;
                                    }
                                    var8_8 = new RuntimeException(var12_28);
                                    throw var8_8;
                                    catch (UnsatisfiedLinkError var15_32) {
                                        var14_30 = new StringBuilder();
                                        var14_30.append("Problem preloading ");
                                        var14_30.append((String)var8_8);
                                        var14_30.append(": ");
                                        var14_30.append(var15_32);
                                        Log.w("Zygote", var14_30.toString());
                                        break block37;
                                    }
                                    catch (ClassNotFoundException var14_31) {
                                        var14_30 = new StringBuilder();
                                        var14_30.append("Class not found for preloading: ");
                                        var14_30.append((String)var8_8);
                                        Log.w("Zygote", var14_30.toString());
                                    }
                                }
                                Trace.traceEnd(16384L);
                                continue;
                            }
                            catch (Throwable var8_9) {
                                break block34;
                            }
                            catch (IOException var8_10) {
                                break block35;
                            }
                        }
                        break;
                    } while (true);
                    catch (Throwable var8_11) {
                        break block34;
                    }
                    catch (IOException var8_12) {
                        break block35;
                    }
                }
                var8_8 = new StringBuilder();
                var8_8.append("...preloaded ");
                var8_8.append(var6_5);
                var8_8.append(" classes in ");
                var8_8.append(SystemClock.uptimeMillis() - var2_2);
                var8_8.append("ms.");
                Log.i("Zygote", var8_8.toString());
                IoUtils.closeQuietly((AutoCloseable)var1_1);
                var0.setTargetHeapUtilization(var9_24);
                Trace.traceBegin(16384L, "PreloadDexCaches");
                var0.preloadDexCaches();
                Trace.traceEnd(16384L);
                if (var7_6 == 0) return;
                try {
                    Os.setreuid((int)0, (int)0);
                    Os.setregid((int)0, (int)0);
                    return;
                }
                catch (ErrnoException var8_13) {
                    throw new RuntimeException("Failed to restore root", var8_13);
                }
                catch (Throwable var8_14) {
                    break block34;
                }
                catch (IOException var8_15) {}
            }
            Log.e("Zygote", "Error reading /system/etc/preloaded-classes.", (Throwable)var8_18);
            IoUtils.closeQuietly((AutoCloseable)var1_1);
            var0.setTargetHeapUtilization(var9_24);
            Trace.traceBegin(16384L, "PreloadDexCaches");
            var0.preloadDexCaches();
            Trace.traceEnd(16384L);
            if (var7_6 == 0) return;
            try {
                Os.setreuid((int)0, (int)0);
                Os.setregid((int)0, (int)0);
                return;
            }
            catch (ErrnoException var8_19) {
                throw new RuntimeException("Failed to restore root", var8_19);
            }
            catch (Throwable var8_20) {
                // empty catch block
            }
        }
        IoUtils.closeQuietly((AutoCloseable)var1_1);
        var0.setTargetHeapUtilization(var9_24);
        Trace.traceBegin(16384L, "PreloadDexCaches");
        var0.preloadDexCaches();
        Trace.traceEnd(16384L);
        if (var7_6 == 0) throw var8_21;
        try {
            Os.setreuid((int)0, (int)0);
            Os.setregid((int)0, (int)0);
            throw var8_21;
        }
        catch (ErrnoException var8_22) {
            throw new RuntimeException("Failed to restore root", var8_22);
        }
    }

    private static int preloadColorStateLists(TypedArray typedArray) {
        int n = typedArray.length();
        for (int i = 0; i < n; ++i) {
            int n2 = typedArray.getResourceId(i, 0);
            if (n2 == 0 || mResources.getColorStateList(n2, null) != null) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to find preloaded color resource #0x");
            stringBuilder.append(Integer.toHexString(n2));
            stringBuilder.append(" (");
            stringBuilder.append(typedArray.getString(i));
            stringBuilder.append(")");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return n;
    }

    private static int preloadDrawables(TypedArray typedArray) {
        int n = typedArray.length();
        for (int i = 0; i < n; ++i) {
            int n2 = typedArray.getResourceId(i, 0);
            if (n2 == 0 || mResources.getDrawable(n2, null) != null) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to find preloaded drawable resource #0x");
            stringBuilder.append(Integer.toHexString(n2));
            stringBuilder.append(" (");
            stringBuilder.append(typedArray.getString(i));
            stringBuilder.append(")");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return n;
    }

    private static void preloadResources() {
        VMRuntime.getRuntime();
        try {
            mResources = Resources.getSystem();
            mResources.startPreloading();
            Log.i("Zygote", "Preloading resources...");
            long l = SystemClock.uptimeMillis();
            Object object = mResources.obtainTypedArray(17236096);
            int n = ZygoteInit.preloadDrawables((TypedArray)object);
            ((TypedArray)object).recycle();
            object = new StringBuilder();
            ((StringBuilder)object).append("...preloaded ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" resources in ");
            ((StringBuilder)object).append(SystemClock.uptimeMillis() - l);
            ((StringBuilder)object).append("ms.");
            Log.i("Zygote", ((StringBuilder)object).toString());
            l = SystemClock.uptimeMillis();
            object = mResources.obtainTypedArray(17236095);
            n = ZygoteInit.preloadColorStateLists((TypedArray)object);
            ((TypedArray)object).recycle();
            object = new StringBuilder();
            ((StringBuilder)object).append("...preloaded ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" resources in ");
            ((StringBuilder)object).append(SystemClock.uptimeMillis() - l);
            ((StringBuilder)object).append("ms.");
            Log.i("Zygote", ((StringBuilder)object).toString());
            if (mResources.getBoolean(17891459)) {
                l = SystemClock.uptimeMillis();
                object = mResources.obtainTypedArray(17236097);
                n = ZygoteInit.preloadDrawables((TypedArray)object);
                ((TypedArray)object).recycle();
                object = new StringBuilder();
                ((StringBuilder)object).append("...preloaded ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" resource in ");
                ((StringBuilder)object).append(SystemClock.uptimeMillis() - l);
                ((StringBuilder)object).append("ms.");
                Log.i("Zygote", ((StringBuilder)object).toString());
            }
            mResources.finishPreloading();
        }
        catch (RuntimeException runtimeException) {
            Log.w("Zygote", "Failure preloading resources", runtimeException);
        }
    }

    private static void preloadSharedLibraries() {
        Log.i("Zygote", "Preloading shared libraries...");
        System.loadLibrary("android");
        System.loadLibrary("compiler_rt");
        System.loadLibrary("jnigraphics");
    }

    private static void preloadTextResources() {
        Hyphenator.init();
        TextView.preloadFontCache();
    }

    private static void prepareSystemServerProfile(String arrstring) throws RemoteException {
        if (arrstring.isEmpty()) {
            return;
        }
        arrstring = arrstring.split(":");
        IInstalld.Stub.asInterface(ServiceManager.getService("installd")).prepareAppProfile("android", 0, UserHandle.getAppId(1000), "primary.prof", arrstring[0], null);
        VMRuntime.registerAppInfo((String)new File(Environment.getDataProfilesDePackageDirectory(0, "android"), "primary.prof").getAbsolutePath(), (String[])arrstring);
    }

    public static void setApiBlacklistExemptions(String[] arrstring) {
        VMRuntime.getRuntime().setHiddenApiExemptions(arrstring);
    }

    public static void setHiddenApiAccessLogSampleRate(int n) {
        VMRuntime.getRuntime().setHiddenApiAccessLogSamplingRate(n);
    }

    public static void setHiddenApiUsageLogger(VMRuntime.HiddenApiUsageLogger hiddenApiUsageLogger) {
        VMRuntime.getRuntime();
        VMRuntime.setHiddenApiUsageLogger((VMRuntime.HiddenApiUsageLogger)hiddenApiUsageLogger);
    }

    private static void waitForSecondaryZygote(String string2) {
        String string3 = "zygote";
        string2 = "zygote".equals(string2) ? "zygote_secondary" : string3;
        ZygoteProcess.waitForConnectionToZygote(string2);
    }

    private static void warmUpJcaProviders() {
        long l = SystemClock.uptimeMillis();
        Trace.traceBegin(16384L, "Starting installation of AndroidKeyStoreProvider");
        AndroidKeyStoreProvider.install();
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Installed AndroidKeyStoreProvider in ");
        ((StringBuilder)object).append(SystemClock.uptimeMillis() - l);
        ((StringBuilder)object).append("ms.");
        Log.i("Zygote", ((StringBuilder)object).toString());
        Trace.traceEnd(16384L);
        l = SystemClock.uptimeMillis();
        Trace.traceBegin(16384L, "Starting warm up of JCA providers");
        object = Security.getProviders();
        int n = ((Provider[])object).length;
        for (int i = 0; i < n; ++i) {
            object[i].warmUpServiceProvision();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Warmed up JCA providers in ");
        ((StringBuilder)object).append(SystemClock.uptimeMillis() - l);
        ((StringBuilder)object).append("ms.");
        Log.i("Zygote", ((StringBuilder)object).toString());
        Trace.traceEnd(16384L);
    }

    public static final Runnable zygoteInit(int n, String[] arrstring, ClassLoader classLoader) {
        Trace.traceBegin(64L, "ZygoteInit");
        RuntimeInit.redirectLogStreams();
        RuntimeInit.commonInit();
        ZygoteInit.nativeZygoteInit();
        return RuntimeInit.applicationInit(n, arrstring, classLoader);
    }
}

