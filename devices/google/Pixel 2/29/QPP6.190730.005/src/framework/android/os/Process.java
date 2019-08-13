/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.Os
 *  android.system.OsConstants
 *  dalvik.system.VMRuntime
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.StrictMode;
import android.os.UserHandle;
import android.os.ZygoteProcess;
import android.system.Os;
import android.system.OsConstants;
import android.webkit.WebViewZygote;
import dalvik.system.VMRuntime;

public class Process {
    public static final int AUDIOSERVER_UID = 1041;
    public static final int BLUETOOTH_UID = 1002;
    public static final int CAMERASERVER_UID = 1047;
    public static final int CLAT_UID = 1029;
    public static final int DNS_TETHER_UID = 1052;
    @UnsupportedAppUsage
    public static final int DRM_UID = 1019;
    public static final int FIRST_APPLICATION_CACHE_GID = 20000;
    public static final int FIRST_APPLICATION_UID = 10000;
    public static final int FIRST_APP_ZYGOTE_ISOLATED_UID = 90000;
    public static final int FIRST_ISOLATED_UID = 99000;
    public static final int FIRST_SHARED_APPLICATION_GID = 50000;
    public static final int INCIDENTD_UID = 1067;
    public static final int INVALID_UID = -1;
    public static final int KEYSTORE_UID = 1017;
    public static final int LAST_APPLICATION_CACHE_GID = 29999;
    public static final int LAST_APPLICATION_UID = 19999;
    public static final int LAST_APP_ZYGOTE_ISOLATED_UID = 98999;
    public static final int LAST_ISOLATED_UID = 99999;
    public static final int LAST_SHARED_APPLICATION_GID = 59999;
    private static final String LOG_TAG = "Process";
    @UnsupportedAppUsage
    public static final int LOG_UID = 1007;
    public static final int MEDIA_RW_GID = 1023;
    @UnsupportedAppUsage
    public static final int MEDIA_UID = 1013;
    public static final int NETWORK_STACK_UID = 1073;
    @UnsupportedAppUsage
    public static final int NFC_UID = 1027;
    public static final int NOBODY_UID = 9999;
    public static final int NUM_UIDS_PER_APP_ZYGOTE = 100;
    public static final int OTA_UPDATE_UID = 1061;
    public static final int PACKAGE_INFO_GID = 1032;
    public static final int PHONE_UID = 1001;
    public static final int PROC_CHAR = 2048;
    @UnsupportedAppUsage
    public static final int PROC_COMBINE = 256;
    public static final int PROC_NEWLINE_TERM = 10;
    @UnsupportedAppUsage
    public static final int PROC_OUT_FLOAT = 16384;
    @UnsupportedAppUsage
    public static final int PROC_OUT_LONG = 8192;
    @UnsupportedAppUsage
    public static final int PROC_OUT_STRING = 4096;
    @UnsupportedAppUsage
    public static final int PROC_PARENS = 512;
    @UnsupportedAppUsage
    public static final int PROC_QUOTES = 1024;
    @UnsupportedAppUsage
    public static final int PROC_SPACE_TERM = 32;
    @UnsupportedAppUsage
    public static final int PROC_TAB_TERM = 9;
    @UnsupportedAppUsage
    public static final int PROC_TERM_MASK = 255;
    @UnsupportedAppUsage
    public static final int PROC_ZERO_TERM = 0;
    public static final int ROOT_UID = 0;
    public static final int SCHED_BATCH = 3;
    public static final int SCHED_FIFO = 1;
    public static final int SCHED_IDLE = 5;
    public static final int SCHED_OTHER = 0;
    public static final int SCHED_RESET_ON_FORK = 1073741824;
    public static final int SCHED_RR = 2;
    public static final int SE_UID = 1068;
    public static final int SHARED_RELRO_UID = 1037;
    public static final int SHARED_USER_GID = 9997;
    public static final int SHELL_UID = 2000;
    public static final int SIGNAL_KILL = 9;
    public static final int SIGNAL_QUIT = 3;
    public static final int SIGNAL_USR1 = 10;
    public static final int SYSTEM_UID = 1000;
    public static final int THREAD_GROUP_AUDIO_APP = 3;
    public static final int THREAD_GROUP_AUDIO_SYS = 4;
    public static final int THREAD_GROUP_BG_NONINTERACTIVE = 0;
    public static final int THREAD_GROUP_DEFAULT = -1;
    private static final int THREAD_GROUP_FOREGROUND = 1;
    public static final int THREAD_GROUP_RESTRICTED = 7;
    public static final int THREAD_GROUP_RT_APP = 6;
    public static final int THREAD_GROUP_SYSTEM = 2;
    public static final int THREAD_GROUP_TOP_APP = 5;
    public static final int THREAD_PRIORITY_AUDIO = -16;
    public static final int THREAD_PRIORITY_BACKGROUND = 10;
    public static final int THREAD_PRIORITY_DEFAULT = 0;
    public static final int THREAD_PRIORITY_DISPLAY = -4;
    public static final int THREAD_PRIORITY_FOREGROUND = -2;
    public static final int THREAD_PRIORITY_LESS_FAVORABLE = 1;
    public static final int THREAD_PRIORITY_LOWEST = 19;
    public static final int THREAD_PRIORITY_MORE_FAVORABLE = -1;
    public static final int THREAD_PRIORITY_URGENT_AUDIO = -19;
    public static final int THREAD_PRIORITY_URGENT_DISPLAY = -8;
    public static final int THREAD_PRIORITY_VIDEO = -10;
    @UnsupportedAppUsage
    public static final int VPN_UID = 1016;
    public static final int WEBVIEW_ZYGOTE_UID = 1053;
    @UnsupportedAppUsage
    public static final int WIFI_UID = 1010;
    public static final ZygoteProcess ZYGOTE_PROCESS = new ZygoteProcess();
    private static long sStartElapsedRealtime;
    private static long sStartUptimeMillis;

    public static final native long getElapsedCpuTime();

    public static final native int[] getExclusiveCores();

    @UnsupportedAppUsage
    public static final native long getFreeMemory();

    public static final native int getGidForName(String var0);

    @UnsupportedAppUsage
    public static final int getParentPid(int n) {
        long[] arrl = new long[]{-1L};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/proc/");
        stringBuilder.append(n);
        stringBuilder.append("/status");
        Process.readProcLines(stringBuilder.toString(), new String[]{"PPid:"}, arrl);
        return (int)arrl[0];
    }

    @UnsupportedAppUsage
    public static final native int[] getPids(String var0, int[] var1);

    @UnsupportedAppUsage
    public static final native int[] getPidsForCommands(String[] var0);

    public static final native int getProcessGroup(int var0) throws IllegalArgumentException, SecurityException;

    @UnsupportedAppUsage
    public static final native long getPss(int var0);

    public static final native long[] getRss(int var0);

    public static final long getStartElapsedRealtime() {
        return sStartElapsedRealtime;
    }

    public static final long getStartUptimeMillis() {
        return sStartUptimeMillis;
    }

    public static final int getThreadGroupLeader(int n) {
        long[] arrl = new long[]{-1L};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/proc/");
        stringBuilder.append(n);
        stringBuilder.append("/status");
        Process.readProcLines(stringBuilder.toString(), new String[]{"Tgid:"}, arrl);
        return (int)arrl[0];
    }

    public static final native int getThreadPriority(int var0) throws IllegalArgumentException;

    public static final native int getThreadScheduler(int var0) throws IllegalArgumentException;

    @UnsupportedAppUsage
    public static final native long getTotalMemory();

    public static final native int getUidForName(String var0);

    @UnsupportedAppUsage
    public static final int getUidForPid(int n) {
        long[] arrl = new long[]{-1L};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/proc/");
        stringBuilder.append(n);
        stringBuilder.append("/status");
        Process.readProcLines(stringBuilder.toString(), new String[]{"Uid:"}, arrl);
        return (int)arrl[0];
    }

    public static final boolean is64Bit() {
        return VMRuntime.getRuntime().is64Bit();
    }

    public static boolean isApplicationUid(int n) {
        return UserHandle.isApp(n);
    }

    public static boolean isCoreUid(int n) {
        return UserHandle.isCore(n);
    }

    public static final boolean isIsolated() {
        return Process.isIsolated(Process.myUid());
    }

    @UnsupportedAppUsage
    public static final boolean isIsolated(int n) {
        boolean bl = (n = UserHandle.getAppId(n)) >= 99000 && n <= 99999 || n >= 90000 && n <= 98999;
        return bl;
    }

    public static final boolean isThreadInProcess(int n, int n2) {
        StrictMode.ThreadPolicy threadPolicy;
        block3 : {
            threadPolicy = StrictMode.allowThreadDiskReads();
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("/proc/");
                stringBuilder.append(n);
                stringBuilder.append("/task/");
                stringBuilder.append(n2);
                boolean bl = Os.access((String)stringBuilder.toString(), (int)OsConstants.F_OK);
                if (!bl) break block3;
            }
            catch (Throwable throwable) {
                StrictMode.setThreadPolicy(threadPolicy);
                throw throwable;
            }
            catch (Exception exception) {
                StrictMode.setThreadPolicy(threadPolicy);
                return false;
            }
            StrictMode.setThreadPolicy(threadPolicy);
            return true;
        }
        StrictMode.setThreadPolicy(threadPolicy);
        return false;
    }

    public static final void killProcess(int n) {
        Process.sendSignal(n, 9);
    }

    public static final native int killProcessGroup(int var0, int var1);

    public static final void killProcessQuiet(int n) {
        Process.sendSignalQuiet(n, 9);
    }

    public static final int myPid() {
        return Os.getpid();
    }

    @UnsupportedAppUsage
    public static final int myPpid() {
        return Os.getppid();
    }

    public static final int myTid() {
        return Os.gettid();
    }

    public static final int myUid() {
        return Os.getuid();
    }

    public static UserHandle myUserHandle() {
        return UserHandle.of(UserHandle.getUserId(Process.myUid()));
    }

    @UnsupportedAppUsage
    public static final native boolean parseProcLine(byte[] var0, int var1, int var2, int[] var3, String[] var4, long[] var5, float[] var6);

    @UnsupportedAppUsage
    public static final native boolean readProcFile(String var0, int[] var1, String[] var2, long[] var3, float[] var4);

    @UnsupportedAppUsage
    public static final native void readProcLines(String var0, String[] var1, long[] var2);

    public static final native void removeAllProcessGroups();

    public static final native void sendSignal(int var0, int var1);

    public static final native void sendSignalQuiet(int var0, int var1);

    @UnsupportedAppUsage
    public static final native void setArgV0(String var0);

    public static final native void setCanSelfBackground(boolean var0);

    public static final native int setGid(int var0);

    @UnsupportedAppUsage
    public static final native void setProcessGroup(int var0, int var1) throws IllegalArgumentException, SecurityException;

    public static final void setStartTimes(long l, long l2) {
        sStartElapsedRealtime = l;
        sStartUptimeMillis = l2;
    }

    public static final native boolean setSwappiness(int var0, boolean var1);

    public static final native void setThreadGroup(int var0, int var1) throws IllegalArgumentException, SecurityException;

    public static final native void setThreadGroupAndCpuset(int var0, int var1) throws IllegalArgumentException, SecurityException;

    public static final native void setThreadPriority(int var0) throws IllegalArgumentException, SecurityException;

    public static final native void setThreadPriority(int var0, int var1) throws IllegalArgumentException, SecurityException;

    public static final native void setThreadScheduler(int var0, int var1, int var2) throws IllegalArgumentException;

    public static final native int setUid(int var0);

    public static ProcessStartResult start(String string2, String string3, int n, int n2, int[] arrn, int n3, int n4, int n5, String string4, String string5, String string6, String string7, String string8, String string9, String[] arrstring) {
        return ZYGOTE_PROCESS.start(string2, string3, n, n2, arrn, n3, n4, n5, string4, string5, string6, string7, string8, string9, true, arrstring);
    }

    public static ProcessStartResult startWebView(String string2, String string3, int n, int n2, int[] arrn, int n3, int n4, int n5, String string4, String string5, String string6, String string7, String string8, String string9, String[] arrstring) {
        return WebViewZygote.getProcess().start(string2, string3, n, n2, arrn, n3, n4, n5, string4, string5, string6, string7, string8, string9, false, arrstring);
    }

    @Deprecated
    public static final boolean supportsProcesses() {
        return true;
    }

    public static final class ProcessStartResult {
        public int pid;
        public boolean usingWrapper;
    }

}

