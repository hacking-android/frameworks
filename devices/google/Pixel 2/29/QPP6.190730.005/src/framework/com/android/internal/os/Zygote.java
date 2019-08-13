/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  dalvik.system.ZygoteHooks
 *  libcore.io.IoUtils
 */
package com.android.internal.os;

import android.content.pm.ApplicationInfo;
import android.net.Credentials;
import android.net.LocalServerSocket;
import android.os.FactoryTest;
import android.os.SystemProperties;
import android.os.Trace;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.os.ZygoteArguments;
import com.android.internal.os.ZygoteSecurityException;
import dalvik.system.ZygoteHooks;
import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.IOException;
import libcore.io.IoUtils;

public final class Zygote {
    private static final String ANDROID_SOCKET_PREFIX = "ANDROID_SOCKET_";
    public static final int API_ENFORCEMENT_POLICY_MASK = 12288;
    public static final int API_ENFORCEMENT_POLICY_SHIFT = Integer.numberOfTrailingZeros(12288);
    public static final String CHILD_ZYGOTE_ABI_LIST_ARG = "--abi-list=";
    public static final String CHILD_ZYGOTE_SOCKET_NAME_ARG = "--zygote-socket=";
    public static final String CHILD_ZYGOTE_UID_RANGE_END = "--uid-range-end=";
    public static final String CHILD_ZYGOTE_UID_RANGE_START = "--uid-range-start=";
    public static final int DEBUG_ALWAYS_JIT = 64;
    public static final int DEBUG_ENABLE_ASSERT = 4;
    public static final int DEBUG_ENABLE_CHECKJNI = 2;
    public static final int DEBUG_ENABLE_JDWP = 1;
    public static final int DEBUG_ENABLE_JNI_LOGGING = 16;
    public static final int DEBUG_ENABLE_SAFEMODE = 8;
    public static final int DEBUG_GENERATE_DEBUG_INFO = 32;
    public static final int DEBUG_GENERATE_MINI_DEBUG_INFO = 2048;
    public static final int DEBUG_JAVA_DEBUGGABLE = 256;
    public static final int DEBUG_NATIVE_DEBUGGABLE = 128;
    public static final int DISABLE_VERIFIER = 512;
    protected static final int[][] INT_ARRAY_2D = new int[0][0];
    public static final int MOUNT_EXTERNAL_DEFAULT = 1;
    public static final int MOUNT_EXTERNAL_FULL = 6;
    public static final int MOUNT_EXTERNAL_INSTALLER = 5;
    public static final int MOUNT_EXTERNAL_LEGACY = 4;
    public static final int MOUNT_EXTERNAL_NONE = 0;
    public static final int MOUNT_EXTERNAL_READ = 2;
    public static final int MOUNT_EXTERNAL_WRITE = 3;
    public static final int ONLY_USE_SYSTEM_OAT_FILES = 1024;
    public static final String PRIMARY_SOCKET_NAME = "zygote";
    public static final int PROFILE_FROM_SHELL = 32768;
    public static final int PROFILE_SYSTEM_SERVER = 16384;
    public static final long PROPERTY_CHECK_INTERVAL = 60000L;
    public static final String SECONDARY_SOCKET_NAME = "zygote_secondary";
    public static final int SOCKET_BUFFER_SIZE = 256;
    private static final String USAP_ERROR_PREFIX = "Invalid command to USAP: ";
    public static final int USAP_MANAGEMENT_MESSAGE_BYTES = 8;
    public static final String USAP_POOL_PRIMARY_SOCKET_NAME = "usap_pool_primary";
    public static final String USAP_POOL_SECONDARY_SOCKET_NAME = "usap_pool_secondary";
    public static final int USE_APP_IMAGE_STARTUP_CACHE = 65536;

    private Zygote() {
    }

    protected static void allowAppFilesAcrossFork(ApplicationInfo arrstring) {
        arrstring = arrstring.getAllApkPaths();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            Zygote.nativeAllowFileAcrossFork(arrstring[i]);
        }
    }

    public static void appendQuotedShellArgs(StringBuilder stringBuilder, String[] arrstring) {
        for (String string2 : arrstring) {
            stringBuilder.append(" '");
            stringBuilder.append(string2.replace("'", "'\\''"));
            stringBuilder.append("'");
        }
    }

    protected static void applyDebuggerSystemProperty(ZygoteArguments zygoteArguments) {
        if (RoSystemProperties.DEBUGGABLE) {
            zygoteArguments.mRuntimeFlags |= 1;
        }
    }

    protected static void applyInvokeWithSecurityPolicy(ZygoteArguments zygoteArguments, Credentials credentials) throws ZygoteSecurityException {
        int n = credentials.getUid();
        if (zygoteArguments.mInvokeWith != null && n != 0 && (zygoteArguments.mRuntimeFlags & 1) == 0) {
            throw new ZygoteSecurityException("Peer is permitted to specify an explicit invoke-with wrapper command only for debuggable applications.");
        }
    }

    protected static void applyInvokeWithSystemProperty(ZygoteArguments zygoteArguments) {
        if (zygoteArguments.mInvokeWith == null && zygoteArguments.mNiceName != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("wrap.");
            stringBuilder.append(zygoteArguments.mNiceName);
            zygoteArguments.mInvokeWith = SystemProperties.get(stringBuilder.toString());
            if (zygoteArguments.mInvokeWith != null && zygoteArguments.mInvokeWith.length() == 0) {
                zygoteArguments.mInvokeWith = null;
            }
        }
    }

    protected static void applyUidSecurityPolicy(ZygoteArguments zygoteArguments, Credentials credentials) throws ZygoteSecurityException {
        boolean bl;
        if (credentials.getUid() == 1000 && (bl = FactoryTest.getMode() == 0) && zygoteArguments.mUidSpecified && zygoteArguments.mUid < 1000) {
            throw new ZygoteSecurityException("System UID may not launch process with UID < 1000");
        }
        if (!zygoteArguments.mUidSpecified) {
            zygoteArguments.mUid = credentials.getUid();
            zygoteArguments.mUidSpecified = true;
        }
        if (!zygoteArguments.mGidSpecified) {
            zygoteArguments.mGid = credentials.getGid();
            zygoteArguments.mGidSpecified = true;
        }
    }

    private static void blockSigTerm() {
        Zygote.nativeBlockSigTerm();
    }

    private static void callPostForkChildHooks(int n, boolean bl, boolean bl2, String string2) {
        ZygoteHooks.postForkChild((int)n, (boolean)bl, (boolean)bl2, (String)string2);
    }

    private static void callPostForkSystemServerHooks() {
        ZygoteHooks.postForkSystemServer();
    }

    static LocalServerSocket createManagedSocketFromInitSocket(String object) {
        int n;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(ANDROID_SOCKET_PREFIX);
        charSequence.append((String)object);
        charSequence = charSequence.toString();
        try {
            n = Integer.parseInt(System.getenv((String)charSequence));
        }
        catch (RuntimeException runtimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Socket unset or invalid: ");
            stringBuilder.append((String)charSequence);
            throw new RuntimeException(stringBuilder.toString(), runtimeException);
        }
        try {
            object = new FileDescriptor();
            object.setInt$(n);
            object = new LocalServerSocket((FileDescriptor)object);
            return object;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Error building socket from file descriptor: ");
            ((StringBuilder)object).append(n);
            throw new RuntimeException(((StringBuilder)object).toString(), iOException);
        }
    }

    protected static void disableExecuteOnly(int n) {
        if (n < 29 && !Zygote.nativeDisableExecuteOnly()) {
            Log.e("Zygote", "Failed to set libraries to read+execute.");
        }
    }

    protected static void emptyUsapPool() {
        Zygote.nativeEmptyUsapPool();
    }

    public static void execShell(String string2) {
        String[] arrstring = new String[]{"/system/bin/sh", "-c", string2};
        try {
            Os.execv((String)arrstring[0], (String[])arrstring);
            return;
        }
        catch (ErrnoException errnoException) {
            throw new RuntimeException(errnoException);
        }
    }

    public static int forkAndSpecialize(int n, int n2, int[] arrn, int n3, int[][] arrn2, int n4, String string2, String string3, int[] arrn3, int[] arrn4, boolean bl, String string4, String string5, int n5) {
        ZygoteHooks.preFork();
        Zygote.resetNicePriority();
        n = Zygote.nativeForkAndSpecialize(n, n2, arrn, n3, arrn2, n4, string2, string3, arrn3, arrn4, bl, string4, string5);
        if (n == 0) {
            Zygote.disableExecuteOnly(n5);
            Trace.setTracingEnabled(true, n3);
            Trace.traceBegin(64L, "PostFork");
        }
        ZygoteHooks.postForkCommon();
        return n;
    }

    public static int forkSystemServer(int n, int n2, int[] arrn, int n3, int[][] arrn2, long l, long l2) {
        ZygoteHooks.preFork();
        Zygote.resetNicePriority();
        n = Zygote.nativeForkSystemServer(n, n2, arrn, n3, arrn2, l, l2);
        if (n == 0) {
            Trace.setTracingEnabled(true, n3);
        }
        ZygoteHooks.postForkCommon();
        return n;
    }

    static Runnable forkUsap(LocalServerSocket localServerSocket, int[] arrn) {
        FileDescriptor[] arrfileDescriptor;
        try {
            arrfileDescriptor = Os.pipe2((int)OsConstants.O_CLOEXEC);
        }
        catch (ErrnoException errnoException) {
            throw new IllegalStateException("Unable to create USAP pipe.", errnoException);
        }
        if (Zygote.nativeForkUsap(arrfileDescriptor[0].getInt$(), arrfileDescriptor[1].getInt$(), arrn) == 0) {
            IoUtils.closeQuietly((FileDescriptor)arrfileDescriptor[0]);
            return Zygote.usapMain(localServerSocket, arrfileDescriptor[1]);
        }
        IoUtils.closeQuietly((FileDescriptor)arrfileDescriptor[1]);
        return null;
    }

    public static String getConfigurationProperty(String string2, String string3) {
        return SystemProperties.get(String.join((CharSequence)".", "persist.device_config", "runtime_native", string2), string3);
    }

    public static boolean getConfigurationPropertyBoolean(String string2, Boolean bl) {
        return SystemProperties.getBoolean(String.join((CharSequence)".", "persist.device_config", "runtime_native", string2), bl);
    }

    protected static int[] getUsapPipeFDs() {
        return Zygote.nativeGetUsapPipeFDs();
    }

    static int getUsapPoolCount() {
        return Zygote.nativeGetUsapPoolCount();
    }

    static FileDescriptor getUsapPoolEventFD() {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setInt$(Zygote.nativeGetUsapPoolEventFD());
        return fileDescriptor;
    }

    static void initNativeState(boolean bl) {
        Zygote.nativeInitNativeState(bl);
    }

    protected static native void nativeAllowFileAcrossFork(String var0);

    private static native void nativeBlockSigTerm();

    private static native boolean nativeDisableExecuteOnly();

    private static native void nativeEmptyUsapPool();

    private static native int nativeForkAndSpecialize(int var0, int var1, int[] var2, int var3, int[][] var4, int var5, String var6, String var7, int[] var8, int[] var9, boolean var10, String var11, String var12);

    private static native int nativeForkSystemServer(int var0, int var1, int[] var2, int var3, int[][] var4, long var5, long var7);

    private static native int nativeForkUsap(int var0, int var1, int[] var2);

    private static native int[] nativeGetUsapPipeFDs();

    private static native int nativeGetUsapPoolCount();

    private static native int nativeGetUsapPoolEventFD();

    protected static native void nativeInitNativeState(boolean var0);

    protected static native void nativeInstallSeccompUidGidFilter(int var0, int var1);

    static native void nativePreApplicationInit();

    private static native boolean nativeRemoveUsapTableEntry(int var0);

    private static native void nativeSpecializeAppProcess(int var0, int var1, int[] var2, int var3, int[][] var4, int var5, String var6, String var7, boolean var8, String var9, String var10);

    private static native void nativeUnblockSigTerm();

    static String[] readArgumentList(BufferedReader bufferedReader) throws IOException {
        block6 : {
            int n;
            String[] arrstring;
            block5 : {
                arrstring = bufferedReader.readLine();
                if (arrstring != null) break block5;
                return null;
            }
            try {
                n = Integer.parseInt((String)arrstring);
                if (n > 1024) break block6;
            }
            catch (NumberFormatException numberFormatException) {
                Log.e("Zygote", "Invalid Zygote wire format: non-int at argc");
                throw new IOException("Invalid wire format");
            }
            arrstring = new String[n];
            for (int i = 0; i < n; ++i) {
                arrstring[i] = bufferedReader.readLine();
                if (arrstring[i] != null) {
                    continue;
                }
                throw new IOException("Truncated request");
            }
            return arrstring;
        }
        throw new IOException("Max arg count exceeded");
    }

    protected static boolean removeUsapTableEntry(int n) {
        return Zygote.nativeRemoveUsapTableEntry(n);
    }

    static void resetNicePriority() {
        Thread.currentThread().setPriority(5);
    }

    public static void specializeAppProcess(int n, int n2, int[] arrn, int n3, int[][] arrn2, int n4, String string2, String string3, boolean bl, String string4, String string5) {
        Zygote.nativeSpecializeAppProcess(n, n2, arrn, n3, arrn2, n4, string2, string3, bl, string4, string5);
        Trace.setTracingEnabled(true, n3);
        Trace.traceBegin(64L, "PostFork");
        ZygoteHooks.postForkCommon();
    }

    private static void unblockSigTerm() {
        Zygote.nativeUnblockSigTerm();
    }

    /*
     * Exception decompiling
     */
    private static Runnable usapMain(LocalServerSocket var0, FileDescriptor var1_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [22[UNCONDITIONALDOLOOP]], but top level block is 8[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static void validateUsapCommand(ZygoteArguments zygoteArguments) {
        if (!zygoteArguments.mAbiListQuery) {
            if (!zygoteArguments.mPidQuery) {
                if (!zygoteArguments.mPreloadDefault) {
                    if (zygoteArguments.mPreloadPackage == null) {
                        if (zygoteArguments.mPreloadApp == null) {
                            if (!zygoteArguments.mStartChildZygote) {
                                if (zygoteArguments.mApiBlacklistExemptions == null) {
                                    if (zygoteArguments.mHiddenApiAccessLogSampleRate == -1) {
                                        if (zygoteArguments.mHiddenApiAccessStatslogSampleRate == -1) {
                                            if (zygoteArguments.mInvokeWith == null) {
                                                if (zygoteArguments.mPermittedCapabilities == 0L && zygoteArguments.mEffectiveCapabilities == 0L) {
                                                    return;
                                                }
                                                StringBuilder stringBuilder = new StringBuilder();
                                                stringBuilder.append("Client may not specify capabilities: permitted=0x");
                                                stringBuilder.append(Long.toHexString(zygoteArguments.mPermittedCapabilities));
                                                stringBuilder.append(", effective=0x");
                                                stringBuilder.append(Long.toHexString(zygoteArguments.mEffectiveCapabilities));
                                                throw new ZygoteSecurityException(stringBuilder.toString());
                                            }
                                            throw new IllegalArgumentException("Invalid command to USAP: --invoke-with");
                                        }
                                        throw new IllegalArgumentException("Invalid command to USAP: --hidden-api-statslog-sampling-rate=");
                                    }
                                    throw new IllegalArgumentException("Invalid command to USAP: --hidden-api-log-sampling-rate=");
                                }
                                throw new IllegalArgumentException("Invalid command to USAP: --set-api-blacklist-exemptions");
                            }
                            throw new IllegalArgumentException("Invalid command to USAP: --start-child-zygote");
                        }
                        throw new IllegalArgumentException("Invalid command to USAP: --preload-app");
                    }
                    throw new IllegalArgumentException("Invalid command to USAP: --preload-package");
                }
                throw new IllegalArgumentException("Invalid command to USAP: --preload-default");
            }
            throw new IllegalArgumentException("Invalid command to USAP: --get-pid");
        }
        throw new IllegalArgumentException("Invalid command to USAP: --query-abi-list");
    }
}

