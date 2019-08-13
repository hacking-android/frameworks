/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.os.-$
 *  com.android.internal.os.-$$Lambda
 *  com.android.internal.os.-$$Lambda$RuntimeInit
 *  com.android.internal.os.-$$Lambda$RuntimeInit$ep4ioD9YINkHI5Q1wZ0N_7VFAOg
 *  dalvik.system.RuntimeHooks
 *  dalvik.system.VMRuntime
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.ApplicationErrorReport;
import android.app.IActivityManager;
import android.ddm.DdmRegister;
import android.os.Build;
import android.os.DeadObjectException;
import android.os.Debug;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.Log;
import android.util.Slog;
import com.android.internal.logging.AndroidConfig;
import com.android.internal.os.-$;
import com.android.internal.os.AndroidPrintStream;
import com.android.internal.os._$$Lambda$RuntimeInit$ep4ioD9YINkHI5Q1wZ0N_7VFAOg;
import com.android.server.NetworkManagementSocketTagger;
import dalvik.system.RuntimeHooks;
import dalvik.system.VMRuntime;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.LogManager;

public class RuntimeInit {
    static final boolean DEBUG = false;
    static final String TAG = "AndroidRuntime";
    @UnsupportedAppUsage
    private static boolean initialized;
    @UnsupportedAppUsage
    private static IBinder mApplicationObject;
    private static volatile boolean mCrashing;

    static {
        mCrashing = false;
    }

    private static int Clog_e(String string2, String string3, Throwable throwable) {
        return Log.printlns(4, 6, string2, string3, throwable);
    }

    protected static Runnable applicationInit(int n, String[] object, ClassLoader classLoader) {
        RuntimeInit.nativeSetExitWithoutCleanup(true);
        VMRuntime.getRuntime().setTargetHeapUtilization(0.75f);
        VMRuntime.getRuntime().setTargetSdkVersion(n);
        object = new Arguments((String[])object);
        Trace.traceEnd(64L);
        return RuntimeInit.findStaticMain(object.startClass, object.startArgs, classLoader);
    }

    @UnsupportedAppUsage
    protected static final void commonInit() {
        LoggingHandler loggingHandler = new LoggingHandler();
        RuntimeHooks.setUncaughtExceptionPreHandler((Thread.UncaughtExceptionHandler)loggingHandler);
        Thread.setDefaultUncaughtExceptionHandler(new KillApplicationHandler(loggingHandler));
        RuntimeHooks.setTimeZoneIdSupplier((Supplier)_$$Lambda$RuntimeInit$ep4ioD9YINkHI5Q1wZ0N_7VFAOg.INSTANCE);
        LogManager.getLogManager().reset();
        new AndroidConfig();
        System.setProperty("http.agent", RuntimeInit.getDefaultUserAgent());
        NetworkManagementSocketTagger.install();
        if (SystemProperties.get("ro.kernel.android.tracing").equals("1")) {
            Slog.i(TAG, "NOTE: emulator trace profiling enabled");
            Debug.enableEmulatorTraceOutput();
        }
        initialized = true;
    }

    static final void enableDdms() {
        DdmRegister.registerHandlers();
    }

    protected static Runnable findStaticMain(String string2, String[] object, ClassLoader object2) {
        try {
            object2 = Class.forName(string2, true, (ClassLoader)object2);
        }
        catch (ClassNotFoundException classNotFoundException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Missing class when invoking static main ");
            ((StringBuilder)object).append(string2);
            throw new RuntimeException(((StringBuilder)object).toString(), classNotFoundException);
        }
        try {
            object2 = ((Class)object2).getMethod("main", String[].class);
        }
        catch (SecurityException securityException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Problem getting static main on ");
            ((StringBuilder)object).append(string2);
            throw new RuntimeException(((StringBuilder)object).toString(), securityException);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Missing static main on ");
            ((StringBuilder)object2).append(string2);
            throw new RuntimeException(((StringBuilder)object2).toString(), noSuchMethodException);
        }
        int n = ((Method)object2).getModifiers();
        if (Modifier.isStatic(n) && Modifier.isPublic(n)) {
            return new MethodAndArgsCaller((Method)object2, (String[])object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Main method is not public and static on ");
        ((StringBuilder)object).append(string2);
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public static final IBinder getApplicationObject() {
        return mApplicationObject;
    }

    private static String getDefaultUserAgent() {
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append("Dalvik/");
        stringBuilder.append(System.getProperty("java.vm.version"));
        stringBuilder.append(" (Linux; U; Android ");
        String string2 = Build.VERSION.RELEASE;
        if (string2.length() <= 0) {
            string2 = "1.0";
        }
        stringBuilder.append(string2);
        if ("REL".equals(Build.VERSION.CODENAME) && (string2 = Build.MODEL).length() > 0) {
            stringBuilder.append("; ");
            stringBuilder.append(string2);
        }
        if ((string2 = Build.ID).length() > 0) {
            stringBuilder.append(" Build/");
            stringBuilder.append(string2);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    static /* synthetic */ String lambda$commonInit$0() {
        return SystemProperties.get("persist.sys.timezone");
    }

    @UnsupportedAppUsage
    public static final void main(String[] arrstring) {
        RuntimeInit.enableDdms();
        if (arrstring.length == 2 && arrstring[1].equals("application")) {
            RuntimeInit.redirectLogStreams();
        }
        RuntimeInit.commonInit();
        RuntimeInit.nativeFinishInit();
    }

    private static final native void nativeFinishInit();

    private static final native void nativeSetExitWithoutCleanup(boolean var0);

    public static void redirectLogStreams() {
        System.out.close();
        System.setOut(new AndroidPrintStream(4, "System.out"));
        System.err.close();
        System.setErr(new AndroidPrintStream(5, "System.err"));
    }

    public static final void setApplicationObject(IBinder iBinder) {
        mApplicationObject = iBinder;
    }

    public static void wtf(String string2, Throwable throwable, boolean bl) {
        block3 : {
            try {
                IActivityManager iActivityManager = ActivityManager.getService();
                IBinder iBinder = mApplicationObject;
                ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo = new ApplicationErrorReport.ParcelableCrashInfo(throwable);
                if (iActivityManager.handleApplicationWtf(iBinder, string2, bl, parcelableCrashInfo)) {
                    Process.killProcess(Process.myPid());
                    System.exit(10);
                }
            }
            catch (Throwable throwable2) {
                if (throwable2 instanceof DeadObjectException) break block3;
                Slog.e("AndroidRuntime", "Error reporting WTF", throwable2);
                Slog.e("AndroidRuntime", "Original WTF:", throwable);
            }
        }
    }

    static class Arguments {
        String[] startArgs;
        String startClass;

        Arguments(String[] arrstring) throws IllegalArgumentException {
            this.parseArgs(arrstring);
        }

        private void parseArgs(String[] arrstring) throws IllegalArgumentException {
            String[] arrstring2;
            int n;
            int n2 = 0;
            do {
                n = ++n2;
                if (n2 >= arrstring.length) break;
                arrstring2 = arrstring[n2];
                if (arrstring2.equals("--")) {
                    n = n2 + 1;
                    break;
                }
                if (arrstring2.startsWith("--")) continue;
                n = n2;
                break;
            } while (true);
            if (n != arrstring.length) {
                n2 = n + 1;
                this.startClass = arrstring[n];
                arrstring2 = this.startArgs = new String[arrstring.length - n2];
                System.arraycopy(arrstring, n2, arrstring2, 0, arrstring2.length);
                return;
            }
            throw new IllegalArgumentException("Missing classname argument to RuntimeInit!");
        }
    }

    private static class KillApplicationHandler
    implements Thread.UncaughtExceptionHandler {
        private final LoggingHandler mLoggingHandler;

        public KillApplicationHandler(LoggingHandler loggingHandler) {
            this.mLoggingHandler = Objects.requireNonNull(loggingHandler);
        }

        private void ensureLogging(Thread thread, Throwable throwable) {
            if (!this.mLoggingHandler.mTriggered) {
                try {
                    this.mLoggingHandler.uncaughtException(thread, throwable);
                }
                catch (Throwable throwable2) {
                    // empty catch block
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void uncaughtException(Thread object, Throwable throwable) {
            block9 : {
                boolean bl;
                block8 : {
                    this.ensureLogging((Thread)object, throwable);
                    bl = mCrashing;
                    if (!bl) break block8;
                    Process.killProcess(Process.myPid());
                    System.exit(10);
                    return;
                }
                try {
                    mCrashing = true;
                    if (ActivityThread.currentActivityThread() != null) {
                        ActivityThread.currentActivityThread().stopProfiling();
                    }
                    IActivityManager iActivityManager = ActivityManager.getService();
                    IBinder iBinder = mApplicationObject;
                    object = new ApplicationErrorReport.ParcelableCrashInfo(throwable);
                    iActivityManager.handleApplicationCrash(iBinder, (ApplicationErrorReport.ParcelableCrashInfo)object);
                }
                catch (Throwable throwable2) {
                    try {
                        bl = throwable2 instanceof DeadObjectException;
                        if (bl) break block9;
                    }
                    catch (Throwable throwable4) {
                        Process.killProcess(Process.myPid());
                        System.exit(10);
                        throw throwable4;
                    }
                    try {
                        RuntimeInit.Clog_e(RuntimeInit.TAG, "Error reporting crash", throwable2);
                    }
                    catch (Throwable throwable3) {
                        // empty catch block
                    }
                }
            }
            Process.killProcess(Process.myPid());
            System.exit(10);
        }
    }

    private static class LoggingHandler
    implements Thread.UncaughtExceptionHandler {
        public volatile boolean mTriggered = false;

        private LoggingHandler() {
        }

        @Override
        public void uncaughtException(Thread object, Throwable throwable) {
            this.mTriggered = true;
            if (mCrashing) {
                return;
            }
            if (mApplicationObject == null && 1000 == Process.myUid()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("*** FATAL EXCEPTION IN SYSTEM PROCESS: ");
                stringBuilder.append(((Thread)object).getName());
                RuntimeInit.Clog_e(RuntimeInit.TAG, stringBuilder.toString(), throwable);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("FATAL EXCEPTION: ");
                stringBuilder.append(((Thread)object).getName());
                stringBuilder.append("\n");
                object = ActivityThread.currentProcessName();
                if (object != null) {
                    stringBuilder.append("Process: ");
                    stringBuilder.append((String)object);
                    stringBuilder.append(", ");
                }
                stringBuilder.append("PID: ");
                stringBuilder.append(Process.myPid());
                RuntimeInit.Clog_e(RuntimeInit.TAG, stringBuilder.toString(), throwable);
            }
        }
    }

    static class MethodAndArgsCaller
    implements Runnable {
        private final String[] mArgs;
        private final Method mMethod;

        public MethodAndArgsCaller(Method method, String[] arrstring) {
            this.mMethod = method;
            this.mArgs = arrstring;
        }

        @Override
        public void run() {
            try {
                this.mMethod.invoke(null, new Object[]{this.mArgs});
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                Throwable throwable = invocationTargetException.getCause();
                if (!(throwable instanceof RuntimeException)) {
                    if (throwable instanceof Error) {
                        throw (Error)throwable;
                    }
                    throw new RuntimeException(invocationTargetException);
                }
                throw (RuntimeException)throwable;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new RuntimeException(illegalAccessException);
            }
        }
    }

}

