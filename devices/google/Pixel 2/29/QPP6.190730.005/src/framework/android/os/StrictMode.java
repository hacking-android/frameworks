/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.-$
 *  android.os.-$$Lambda
 *  android.os.-$$Lambda$StrictMode
 *  android.os.-$$Lambda$StrictMode$1yH8AK0bTwVwZOb9x8HoiSBdzr0
 *  android.os.-$$Lambda$StrictMode$lu9ekkHJ2HMz0jd3F8K8MnhenxQ
 *  dalvik.system.BlockGuard
 *  dalvik.system.BlockGuard$Policy
 *  dalvik.system.BlockGuard$VmPolicy
 *  dalvik.system.CloseGuard
 *  dalvik.system.CloseGuard$Reporter
 *  dalvik.system.VMDebug
 *  dalvik.system.VMRuntime
 */
package android.os;

import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.IActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.-$;
import android.os.Binder;
import android.os.Build;
import android.os.DeadObjectException;
import android.os.FileUriExposedException;
import android.os.Handler;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.NetworkOnMainThreadException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os._$$Lambda$StrictMode$1yH8AK0bTwVwZOb9x8HoiSBdzr0;
import android.os._$$Lambda$StrictMode$AndroidBlockGuardPolicy$9nBulCQKaMajrWr41SB7f7YRT1I;
import android.os._$$Lambda$StrictMode$AndroidBlockGuardPolicy$FxZGA9KtfTewqdcxlUwvIe5Nx9I;
import android.os._$$Lambda$StrictMode$UFC_nI1x6u8ZwMQmA7bmj9NHZz4;
import android.os._$$Lambda$StrictMode$lu9ekkHJ2HMz0jd3F8K8MnhenxQ;
import android.os._$$Lambda$StrictMode$yZJXPvy2veRNA_xL_SWdXzX_OLg;
import android.os.storage.IStorageManager;
import android.os.strictmode.CleartextNetworkViolation;
import android.os.strictmode.ContentUriWithoutPermissionViolation;
import android.os.strictmode.CredentialProtectedWhileLockedViolation;
import android.os.strictmode.CustomViolation;
import android.os.strictmode.DiskReadViolation;
import android.os.strictmode.DiskWriteViolation;
import android.os.strictmode.ExplicitGcViolation;
import android.os.strictmode.FileUriExposedViolation;
import android.os.strictmode.ImplicitDirectBootViolation;
import android.os.strictmode.InstanceCountViolation;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.os.strictmode.LeakedClosableViolation;
import android.os.strictmode.NetworkViolation;
import android.os.strictmode.NonSdkApiUsedViolation;
import android.os.strictmode.ResourceMismatchViolation;
import android.os.strictmode.ServiceConnectionLeakedViolation;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.os.strictmode.UnbufferedIoViolation;
import android.os.strictmode.UntaggedSocketViolation;
import android.os.strictmode.Violation;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Printer;
import android.util.Singleton;
import android.view.IWindowManager;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.RuntimeInit;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.HexDump;
import dalvik.system.BlockGuard;
import dalvik.system.CloseGuard;
import dalvik.system.VMDebug;
import dalvik.system.VMRuntime;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public final class StrictMode {
    private static final String CLEARTEXT_PROPERTY = "persist.sys.strictmode.clear";
    private static final int DETECT_THREAD_ALL = 65535;
    private static final int DETECT_THREAD_CUSTOM = 8;
    private static final int DETECT_THREAD_DISK_READ = 2;
    private static final int DETECT_THREAD_DISK_WRITE = 1;
    private static final int DETECT_THREAD_EXPLICIT_GC = 64;
    private static final int DETECT_THREAD_NETWORK = 4;
    private static final int DETECT_THREAD_RESOURCE_MISMATCH = 16;
    private static final int DETECT_THREAD_UNBUFFERED_IO = 32;
    private static final int DETECT_VM_ACTIVITY_LEAKS = 4;
    private static final int DETECT_VM_ALL = 65535;
    private static final int DETECT_VM_CLEARTEXT_NETWORK = 64;
    private static final int DETECT_VM_CLOSABLE_LEAKS = 2;
    private static final int DETECT_VM_CONTENT_URI_WITHOUT_PERMISSION = 128;
    private static final int DETECT_VM_CREDENTIAL_PROTECTED_WHILE_LOCKED = 2048;
    private static final int DETECT_VM_CURSOR_LEAKS = 1;
    private static final int DETECT_VM_FILE_URI_EXPOSURE = 32;
    private static final int DETECT_VM_IMPLICIT_DIRECT_BOOT = 1024;
    private static final int DETECT_VM_INSTANCE_LEAKS = 8;
    private static final int DETECT_VM_NON_SDK_API_USAGE = 512;
    private static final int DETECT_VM_REGISTRATION_LEAKS = 16;
    private static final int DETECT_VM_UNTAGGED_SOCKET = 256;
    private static final boolean DISABLE = false;
    public static final String DISABLE_PROPERTY = "persist.sys.strictmode.disable";
    private static final HashMap<Class, Integer> EMPTY_CLASS_LIMIT_MAP;
    private static final ViolationLogger LOGCAT_LOGGER;
    private static final boolean LOG_V;
    private static final int MAX_OFFENSES_PER_LOOP = 10;
    private static final int MAX_SPAN_TAGS = 20;
    private static final long MIN_DIALOG_INTERVAL_MS = 30000L;
    private static final long MIN_LOG_INTERVAL_MS = 1000L;
    private static final long MIN_VM_INTERVAL_MS = 1000L;
    public static final int NETWORK_POLICY_ACCEPT = 0;
    public static final int NETWORK_POLICY_LOG = 1;
    public static final int NETWORK_POLICY_REJECT = 2;
    private static final Span NO_OP_SPAN;
    public static final int PENALTY_ALL = -65536;
    public static final int PENALTY_DEATH = 268435456;
    public static final int PENALTY_DEATH_ON_CLEARTEXT_NETWORK = 16777216;
    public static final int PENALTY_DEATH_ON_FILE_URI_EXPOSURE = 8388608;
    public static final int PENALTY_DEATH_ON_NETWORK = 33554432;
    public static final int PENALTY_DIALOG = 536870912;
    public static final int PENALTY_DROPBOX = 67108864;
    public static final int PENALTY_FLASH = 134217728;
    public static final int PENALTY_GATHER = Integer.MIN_VALUE;
    public static final int PENALTY_LOG = 1073741824;
    private static final String TAG = "StrictMode";
    private static final ThreadLocal<AndroidBlockGuardPolicy> THREAD_ANDROID_POLICY;
    private static final ThreadLocal<Handler> THREAD_HANDLER;
    public static final String VISUAL_PROPERTY = "persist.sys.strictmode.visual";
    private static final BlockGuard.VmPolicy VM_ANDROID_POLICY;
    private static final ThreadLocal<ArrayList<ViolationInfo>> gatheredViolations;
    private static final AtomicInteger sDropboxCallsInFlight;
    @GuardedBy(value={"StrictMode.class"})
    private static final HashMap<Class, Integer> sExpectedActivityInstanceCount;
    private static boolean sIsIdlerRegistered;
    private static long sLastInstanceCountCheckMillis;
    @UnsupportedAppUsage
    private static final HashMap<Integer, Long> sLastVmViolationTime;
    private static volatile ViolationLogger sLogger;
    private static final Consumer<String> sNonSdkApiUsageConsumer;
    private static final MessageQueue.IdleHandler sProcessIdleHandler;
    private static final ThreadLocal<ThreadSpanState> sThisThreadSpanState;
    private static final ThreadLocal<Executor> sThreadViolationExecutor;
    private static final ThreadLocal<OnThreadViolationListener> sThreadViolationListener;
    private static volatile boolean sUserKeyUnlocked;
    private static volatile VmPolicy sVmPolicy;
    @UnsupportedAppUsage
    private static Singleton<IWindowManager> sWindowManager;
    @UnsupportedAppUsage
    private static final ThreadLocal<ArrayList<ViolationInfo>> violationsBeingTimed;

    static {
        LOG_V = Log.isLoggable(TAG, 2);
        EMPTY_CLASS_LIMIT_MAP = new HashMap();
        sVmPolicy = VmPolicy.LAX;
        sLogger = LOGCAT_LOGGER = _$$Lambda$StrictMode$1yH8AK0bTwVwZOb9x8HoiSBdzr0.INSTANCE;
        sThreadViolationListener = new ThreadLocal();
        sThreadViolationExecutor = new ThreadLocal();
        sDropboxCallsInFlight = new AtomicInteger(0);
        sNonSdkApiUsageConsumer = _$$Lambda$StrictMode$lu9ekkHJ2HMz0jd3F8K8MnhenxQ.INSTANCE;
        gatheredViolations = new ThreadLocal<ArrayList<ViolationInfo>>(){

            @Override
            protected ArrayList<ViolationInfo> initialValue() {
                return null;
            }
        };
        violationsBeingTimed = new ThreadLocal<ArrayList<ViolationInfo>>(){

            @Override
            protected ArrayList<ViolationInfo> initialValue() {
                return new ArrayList<ViolationInfo>();
            }
        };
        THREAD_HANDLER = new ThreadLocal<Handler>(){

            @Override
            protected Handler initialValue() {
                return new Handler();
            }
        };
        THREAD_ANDROID_POLICY = new ThreadLocal<AndroidBlockGuardPolicy>(){

            @Override
            protected AndroidBlockGuardPolicy initialValue() {
                return new AndroidBlockGuardPolicy(0);
            }
        };
        VM_ANDROID_POLICY = new BlockGuard.VmPolicy(){

            public void onPathAccess(String string2) {
                if (string2 == null) {
                    return;
                }
                if (!(string2.startsWith("/data/user/") || string2.startsWith("/data/media/") || string2.startsWith("/data/system_ce/") || string2.startsWith("/data/misc_ce/") || string2.startsWith("/data/vendor_ce/") || string2.startsWith("/storage/emulated/"))) {
                    if (string2.startsWith("/data/data/")) {
                        StrictMode.onCredentialProtectedPathAccess(string2, 0);
                    }
                } else {
                    int n = string2.indexOf(47, string2.indexOf(47, 1) + 1);
                    int n2 = string2.indexOf(47, n + 1);
                    if (n2 == -1) {
                        return;
                    }
                    try {
                        StrictMode.onCredentialProtectedPathAccess(string2, Integer.parseInt(string2.substring(n + 1, n2)));
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
            }
        };
        sLastInstanceCountCheckMillis = 0L;
        sIsIdlerRegistered = false;
        sProcessIdleHandler = new MessageQueue.IdleHandler(){

            @Override
            public boolean queueIdle() {
                long l = SystemClock.uptimeMillis();
                if (l - sLastInstanceCountCheckMillis > 30000L) {
                    sLastInstanceCountCheckMillis = l;
                    StrictMode.conditionallyCheckInstanceCounts();
                }
                return true;
            }
        };
        sUserKeyUnlocked = false;
        sLastVmViolationTime = new HashMap();
        NO_OP_SPAN = new Span(){

            @Override
            public void finish() {
            }
        };
        sThisThreadSpanState = new ThreadLocal<ThreadSpanState>(){

            @Override
            protected ThreadSpanState initialValue() {
                return new ThreadSpanState();
            }
        };
        sWindowManager = new Singleton<IWindowManager>(){

            @Override
            protected IWindowManager create() {
                return IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            }
        };
        sExpectedActivityInstanceCount = new HashMap();
    }

    private StrictMode() {
    }

    public static ThreadPolicy allowThreadDiskReads() {
        return new ThreadPolicy(StrictMode.allowThreadDiskReadsMask(), sThreadViolationListener.get(), sThreadViolationExecutor.get());
    }

    public static int allowThreadDiskReadsMask() {
        int n = StrictMode.getThreadPolicyMask();
        int n2 = n & -3;
        if (n2 != n) {
            StrictMode.setThreadPolicyMask(n2);
        }
        return n;
    }

    public static ThreadPolicy allowThreadDiskWrites() {
        return new ThreadPolicy(StrictMode.allowThreadDiskWritesMask(), sThreadViolationListener.get(), sThreadViolationExecutor.get());
    }

    public static int allowThreadDiskWritesMask() {
        int n = StrictMode.getThreadPolicyMask();
        int n2 = n & -4;
        if (n2 != n) {
            StrictMode.setThreadPolicyMask(n2);
        }
        return n;
    }

    public static ThreadPolicy allowThreadViolations() {
        ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicyMask(0);
        return threadPolicy;
    }

    public static VmPolicy allowVmViolations() {
        VmPolicy vmPolicy = StrictMode.getVmPolicy();
        sVmPolicy = VmPolicy.LAX;
        return vmPolicy;
    }

    static void clearGatheredViolations() {
        gatheredViolations.set(null);
    }

    public static void conditionallyCheckInstanceCounts() {
        VmPolicy vmPolicy = StrictMode.getVmPolicy();
        int n = vmPolicy.classInstanceLimit.size();
        if (n == 0) {
            return;
        }
        System.gc();
        System.runFinalization();
        System.gc();
        Class[] arrclass = vmPolicy.classInstanceLimit.keySet().toArray(new Class[n]);
        long[] arrl = VMDebug.countInstancesOfClasses((Class[])arrclass, (boolean)false);
        for (n = 0; n < arrclass.length; ++n) {
            long l = arrl[n];
            Class class_ = arrclass[n];
            int n2 = vmPolicy.classInstanceLimit.get(class_);
            if (l <= (long)n2) continue;
            StrictMode.onVmPolicyViolation(new InstanceCountViolation(class_, l, n2));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static void decrementExpectedActivityCount(Class class_) {
        if (class_ == null) {
            return;
        }
        // MONITORENTER : android.os.StrictMode.class
        if ((StrictMode.sVmPolicy.mask & 4) == 0) {
            // MONITOREXIT : android.os.StrictMode.class
            return;
        }
        Integer n = sExpectedActivityInstanceCount.get(class_);
        int n2 = n != null && n != 0 ? n - 1 : 0;
        if (n2 == 0) {
            sExpectedActivityInstanceCount.remove(class_);
        } else {
            sExpectedActivityInstanceCount.put(class_, n2);
        }
        // MONITOREXIT : android.os.StrictMode.class
        if (InstanceTracker.getInstanceCount(class_) <= ++n2) {
            return;
        }
        System.gc();
        System.runFinalization();
        System.gc();
        long l = VMDebug.countInstancesOfClass((Class)class_, (boolean)false);
        if (l <= (long)n2) return;
        StrictMode.onVmPolicyViolation(new InstanceCountViolation(class_, l, n2));
    }

    @UnsupportedAppUsage
    public static void disableDeathOnFileUriExposure() {
        sVmPolicy = new VmPolicy(-8388641 & StrictMode.sVmPolicy.mask, StrictMode.sVmPolicy.classInstanceLimit, StrictMode.sVmPolicy.mListener, StrictMode.sVmPolicy.mCallbackExecutor);
    }

    private static void dropboxViolationAsync(int n, ViolationInfo violationInfo) {
        int n2 = sDropboxCallsInFlight.incrementAndGet();
        if (n2 > 20) {
            sDropboxCallsInFlight.decrementAndGet();
            return;
        }
        if (LOG_V) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Dropboxing async; in-flight=");
            stringBuilder.append(n2);
            Log.d(TAG, stringBuilder.toString());
        }
        BackgroundThread.getHandler().post(new _$$Lambda$StrictMode$yZJXPvy2veRNA_xL_SWdXzX_OLg(n, violationInfo));
    }

    @UnsupportedAppUsage
    public static void enableDeathOnFileUriExposure() {
        sVmPolicy = new VmPolicy(8388608 | (StrictMode.sVmPolicy.mask | 32), StrictMode.sVmPolicy.classInstanceLimit, StrictMode.sVmPolicy.mListener, StrictMode.sVmPolicy.mCallbackExecutor);
    }

    public static void enableDefaults() {
        StrictMode.setThreadPolicy(new ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static Span enterCriticalSpan(String string2) {
        if (Build.IS_USER) {
            return NO_OP_SPAN;
        }
        if (string2 != null && !string2.isEmpty()) {
            ThreadSpanState threadSpanState = sThisThreadSpanState.get();
            synchronized (threadSpanState) {
                Span span;
                if (threadSpanState.mFreeListHead != null) {
                    span = threadSpanState.mFreeListHead;
                    threadSpanState.mFreeListHead = span.mNext;
                    --threadSpanState.mFreeListSize;
                } else {
                    span = new Span(threadSpanState);
                }
                span.mName = string2;
                span.mCreateMillis = SystemClock.uptimeMillis();
                span.mNext = threadSpanState.mActiveHead;
                span.mPrev = null;
                threadSpanState.mActiveHead = span;
                ++threadSpanState.mActiveSize;
                if (span.mNext != null) {
                    span.mNext.mPrev = span;
                }
                if (LOG_V) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Span enter=");
                    stringBuilder.append(string2);
                    stringBuilder.append("; size=");
                    stringBuilder.append(threadSpanState.mActiveSize);
                    Log.d(TAG, stringBuilder.toString());
                }
                return span;
            }
        }
        throw new IllegalArgumentException("name must be non-null and non-empty");
    }

    public static ThreadPolicy getThreadPolicy() {
        return new ThreadPolicy(StrictMode.getThreadPolicyMask(), sThreadViolationListener.get(), sThreadViolationExecutor.get());
    }

    @UnsupportedAppUsage
    public static int getThreadPolicyMask() {
        BlockGuard.Policy policy = BlockGuard.getThreadPolicy();
        if (policy instanceof AndroidBlockGuardPolicy) {
            return ((AndroidBlockGuardPolicy)policy).getThreadPolicyMask();
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static VmPolicy getVmPolicy() {
        synchronized (StrictMode.class) {
            return sVmPolicy;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void handleApplicationStrictModeViolation(int n, ViolationInfo violationInfo) {
        int n2;
        Throwable throwable22;
        block6 : {
            n2 = StrictMode.getThreadPolicyMask();
            try {
                try {
                    StrictMode.setThreadPolicyMask(0);
                    IActivityManager iActivityManager = ActivityManager.getService();
                    if (iActivityManager == null) {
                        Log.w(TAG, "No activity manager; failed to Dropbox violation.");
                    } else {
                        iActivityManager.handleApplicationStrictModeViolation(RuntimeInit.getApplicationObject(), n, violationInfo);
                    }
                }
                catch (RemoteException remoteException) {
                    if (remoteException instanceof DeadObjectException) break block6;
                    Log.e(TAG, "RemoteException handling StrictMode violation", remoteException);
                }
            }
            catch (Throwable throwable22) {}
        }
        StrictMode.setThreadPolicyMask(n2);
        return;
        StrictMode.setThreadPolicyMask(n2);
        throw throwable22;
    }

    static boolean hasGatheredViolations() {
        boolean bl = gatheredViolations.get() != null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void incrementExpectedActivityCount(Class class_) {
        if (class_ == null) {
            return;
        }
        synchronized (StrictMode.class) {
            if ((StrictMode.sVmPolicy.mask & 4) == 0) {
                return;
            }
            Integer n = sExpectedActivityInstanceCount.get(class_);
            int n2 = 1;
            if (n != null) {
                n2 = 1 + n;
            }
            sExpectedActivityInstanceCount.put(class_, n2);
            return;
        }
    }

    public static void initThreadDefaults(ApplicationInfo applicationInfo) {
        ThreadPolicy.Builder builder = new ThreadPolicy.Builder();
        int n = applicationInfo != null ? applicationInfo.targetSdkVersion : 10000;
        if (n >= 11) {
            builder.detectNetwork();
            builder.penaltyDeathOnNetwork();
        }
        if (!Build.IS_USER && !SystemProperties.getBoolean(DISABLE_PROPERTY, false)) {
            if (Build.IS_USERDEBUG) {
                if (StrictMode.isBundledSystemApp(applicationInfo)) {
                    builder.detectAll();
                    builder.penaltyDropBox();
                    if (SystemProperties.getBoolean(VISUAL_PROPERTY, false)) {
                        builder.penaltyFlashScreen();
                    }
                }
            } else if (Build.IS_ENG && StrictMode.isBundledSystemApp(applicationInfo)) {
                builder.detectAll();
                builder.penaltyDropBox();
                builder.penaltyLog();
                builder.penaltyFlashScreen();
            }
        }
        StrictMode.setThreadPolicy(builder.build());
    }

    public static void initVmDefaults(ApplicationInfo applicationInfo) {
        VmPolicy.Builder builder = new VmPolicy.Builder();
        int n = applicationInfo != null ? applicationInfo.targetSdkVersion : 10000;
        if (n >= 24) {
            builder.detectFileUriExposure();
            builder.penaltyDeathOnFileUriExposure();
        }
        if (!Build.IS_USER && !SystemProperties.getBoolean(DISABLE_PROPERTY, false)) {
            if (Build.IS_USERDEBUG) {
                if (StrictMode.isBundledSystemApp(applicationInfo)) {
                    builder.detectAll();
                    builder.permitActivityLeaks();
                    builder.penaltyDropBox();
                }
            } else if (Build.IS_ENG && StrictMode.isBundledSystemApp(applicationInfo)) {
                builder.detectAll();
                builder.penaltyDropBox();
                builder.penaltyLog();
            }
        }
        StrictMode.setVmPolicy(builder.build());
    }

    public static boolean isBundledSystemApp(ApplicationInfo applicationInfo) {
        if (applicationInfo != null && applicationInfo.packageName != null) {
            if (applicationInfo.isSystemApp()) {
                if (!applicationInfo.packageName.equals("com.android.vending") && !applicationInfo.packageName.equals("com.android.chrome")) {
                    if (applicationInfo.packageName.equals("com.android.phone")) {
                        return false;
                    }
                    if (applicationInfo.packageName.equals("android") || applicationInfo.packageName.startsWith("android.") || applicationInfo.packageName.startsWith("com.android.")) {
                        return true;
                    }
                } else {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    private static boolean isUserKeyUnlocked(int n) {
        IStorageManager iStorageManager = IStorageManager.Stub.asInterface(ServiceManager.getService("mount"));
        if (iStorageManager != null) {
            try {
                boolean bl = iStorageManager.isUserKeyUnlocked(n);
                return bl;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    static /* synthetic */ void lambda$dropboxViolationAsync$2(int n, ViolationInfo object) {
        StrictMode.handleApplicationStrictModeViolation(n, (ViolationInfo)object);
        n = sDropboxCallsInFlight.decrementAndGet();
        if (LOG_V) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Dropbox complete; in-flight=");
            ((StringBuilder)object).append(n);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
    }

    static /* synthetic */ void lambda$onVmPolicyViolation$3(OnVmViolationListener onVmViolationListener, Violation violation) {
        VmPolicy vmPolicy = StrictMode.allowVmViolations();
        try {
            onVmViolationListener.onVmViolation(violation);
            return;
        }
        finally {
            StrictMode.setVmPolicy(vmPolicy);
        }
    }

    static /* synthetic */ void lambda$static$0(ViolationInfo violationInfo) {
        CharSequence charSequence;
        if (violationInfo.durationMillis != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("StrictMode policy violation; ~duration=");
            ((StringBuilder)charSequence).append(violationInfo.durationMillis);
            ((StringBuilder)charSequence).append(" ms:");
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "StrictMode policy violation:";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" ");
        stringBuilder.append(violationInfo.getStackTrace());
        Log.d(TAG, stringBuilder.toString());
    }

    static /* synthetic */ void lambda$static$1(String string2) {
        StrictMode.onVmPolicyViolation(new NonSdkApiUsedViolation(string2));
    }

    public static void noteDiskRead() {
        BlockGuard.Policy policy = BlockGuard.getThreadPolicy();
        if (!(policy instanceof AndroidBlockGuardPolicy)) {
            return;
        }
        policy.onReadFromDisk();
    }

    public static void noteDiskWrite() {
        BlockGuard.Policy policy = BlockGuard.getThreadPolicy();
        if (!(policy instanceof AndroidBlockGuardPolicy)) {
            return;
        }
        policy.onWriteToDisk();
    }

    public static void noteResourceMismatch(Object object) {
        BlockGuard.Policy policy = BlockGuard.getThreadPolicy();
        if (!(policy instanceof AndroidBlockGuardPolicy)) {
            return;
        }
        ((AndroidBlockGuardPolicy)policy).onResourceMismatch(object);
    }

    public static void noteSlowCall(String string2) {
        BlockGuard.Policy policy = BlockGuard.getThreadPolicy();
        if (!(policy instanceof AndroidBlockGuardPolicy)) {
            return;
        }
        ((AndroidBlockGuardPolicy)policy).onCustomSlowCall(string2);
    }

    public static void noteUnbufferedIO() {
        BlockGuard.Policy policy = BlockGuard.getThreadPolicy();
        if (!(policy instanceof AndroidBlockGuardPolicy)) {
            return;
        }
        policy.onUnbufferedIO();
    }

    @UnsupportedAppUsage
    private static void onBinderStrictModePolicyChange(int n) {
        StrictMode.setBlockGuardPolicy(n);
    }

    public static void onCleartextNetworkDetected(byte[] object) {
        Object object2 = null;
        boolean bl = false;
        Object object3 = object2;
        if (object != null) {
            if (((byte[])object).length >= 20 && (object[0] & 240) == 64) {
                object3 = new byte[4];
                System.arraycopy(object, 16, object3, 0, 4);
            } else {
                object3 = object2;
                if (((byte[])object).length >= 40) {
                    object3 = object2;
                    if ((object[0] & 240) == 96) {
                        object3 = new byte[16];
                        System.arraycopy(object, 24, object3, 0, 16);
                    }
                }
            }
        }
        int n = Process.myUid();
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Detected cleartext network traffic from UID ");
        ((StringBuilder)object2).append(n);
        String string2 = ((StringBuilder)object2).toString();
        object2 = string2;
        if (object3 != null) {
            try {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append(" to ");
                ((StringBuilder)object2).append(InetAddress.getByAddress((byte[])object3));
                object2 = ((StringBuilder)object2).toString();
            }
            catch (UnknownHostException unknownHostException) {
                object2 = string2;
            }
        }
        object3 = new StringBuilder();
        ((StringBuilder)object3).append((String)object2);
        ((StringBuilder)object3).append(HexDump.dumpHexString(object).trim());
        ((StringBuilder)object3).append(" ");
        object = ((StringBuilder)object3).toString();
        if ((StrictMode.sVmPolicy.mask & 16777216) != 0) {
            bl = true;
        }
        StrictMode.onVmPolicyViolation(new CleartextNetworkViolation((String)object), bl);
    }

    public static void onContentUriWithoutPermission(Uri uri, String string2) {
        StrictMode.onVmPolicyViolation(new ContentUriWithoutPermissionViolation(uri, string2));
    }

    private static void onCredentialProtectedPathAccess(String string2, int n) {
        if (n == UserHandle.myUserId()) {
            if (sUserKeyUnlocked) {
                return;
            }
            if (StrictMode.isUserKeyUnlocked(n)) {
                sUserKeyUnlocked = true;
                return;
            }
        } else if (StrictMode.isUserKeyUnlocked(n)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Accessed credential protected path ");
        stringBuilder.append(string2);
        stringBuilder.append(" while user ");
        stringBuilder.append(n);
        stringBuilder.append(" was locked");
        StrictMode.onVmPolicyViolation(new CredentialProtectedWhileLockedViolation(stringBuilder.toString()));
    }

    public static void onFileUriExposed(Uri object, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(object);
        stringBuilder.append(" exposed beyond app through ");
        stringBuilder.append(string2);
        object = stringBuilder.toString();
        if ((StrictMode.sVmPolicy.mask & 8388608) == 0) {
            StrictMode.onVmPolicyViolation(new FileUriExposedViolation((String)object));
            return;
        }
        throw new FileUriExposedException((String)object);
    }

    public static void onImplicitDirectBoot() {
        StrictMode.onVmPolicyViolation(new ImplicitDirectBootViolation());
    }

    public static void onIntentReceiverLeaked(Throwable throwable) {
        StrictMode.onVmPolicyViolation(new IntentReceiverLeakedViolation(throwable));
    }

    public static void onServiceConnectionLeaked(Throwable throwable) {
        StrictMode.onVmPolicyViolation(new ServiceConnectionLeakedViolation(throwable));
    }

    public static void onSqliteObjectLeaked(String string2, Throwable throwable) {
        StrictMode.onVmPolicyViolation(new SqliteObjectLeakedViolation(string2, throwable));
    }

    public static void onUntaggedSocket() {
        StrictMode.onVmPolicyViolation(new UntaggedSocketViolation());
    }

    public static void onVmPolicyViolation(Violation violation) {
        StrictMode.onVmPolicyViolation(violation, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static void onVmPolicyViolation(Violation violation, boolean bl) {
        int n = StrictMode.sVmPolicy.mask;
        boolean bl2 = true;
        n = (n & 67108864) != 0 ? 1 : 0;
        boolean bl3 = (StrictMode.sVmPolicy.mask & 268435456) != 0 || bl;
        if ((StrictMode.sVmPolicy.mask & 1073741824) == 0) {
            bl2 = false;
        }
        Object object = new ViolationInfo(violation, -65536 & StrictMode.sVmPolicy.mask);
        ((ViolationInfo)object).numAnimationsRunning = 0;
        ((ViolationInfo)object).tags = null;
        ((ViolationInfo)object).broadcastIntentAction = null;
        Object object2 = ((ViolationInfo)object).hashCode();
        long l = SystemClock.uptimeMillis();
        long l2 = Long.MAX_VALUE;
        Object object3 = sLastVmViolationTime;
        // MONITORENTER : object3
        if (sLastVmViolationTime.containsKey(object2)) {
            l2 = l - sLastVmViolationTime.get(object2);
        }
        if (l2 > 1000L) {
            sLastVmViolationTime.put((Integer)object2, l);
        }
        // MONITOREXIT : object3
        if (l2 <= 1000L) {
            return;
        }
        if (bl2 && sLogger != null && l2 > 1000L) {
            sLogger.log((ViolationInfo)object);
        }
        if (n != 0) {
            if (bl3) {
                StrictMode.handleApplicationStrictModeViolation(67108864, (ViolationInfo)object);
            } else {
                StrictMode.dropboxViolationAsync(67108864, (ViolationInfo)object);
            }
        }
        if (bl3) {
            System.err.println("StrictMode VmPolicy violation with POLICY_DEATH; shutting down.");
            Process.killProcess(Process.myPid());
            System.exit(10);
        }
        if (StrictMode.sVmPolicy.mListener == null) return;
        if (StrictMode.sVmPolicy.mCallbackExecutor == null) return;
        object = StrictMode.sVmPolicy.mListener;
        try {
            object3 = StrictMode.sVmPolicy.mCallbackExecutor;
            object2 = new _$$Lambda$StrictMode$UFC_nI1x6u8ZwMQmA7bmj9NHZz4((OnVmViolationListener)object, violation);
            object3.execute((Runnable)object2);
            return;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            Log.e(TAG, "VmPolicy penaltyCallback failed", rejectedExecutionException);
        }
    }

    @UnsupportedAppUsage
    public static void onWebViewMethodCalledOnWrongThread(Throwable throwable) {
        StrictMode.onVmPolicyViolation(new WebViewMethodCalledOnWrongThreadViolation(throwable));
    }

    static void readAndHandleBinderCallViolations(Parcel parcel) {
        Throwable throwable = new Throwable();
        boolean bl = (Integer.MIN_VALUE & StrictMode.getThreadPolicyMask()) != 0;
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            boolean bl2 = !bl;
            ViolationInfo violationInfo = new ViolationInfo(parcel, bl2);
            violationInfo.addLocalStack(throwable);
            BlockGuard.Policy policy = BlockGuard.getThreadPolicy();
            if (!(policy instanceof AndroidBlockGuardPolicy)) continue;
            ((AndroidBlockGuardPolicy)policy).handleViolationWithTimingAttempt(violationInfo);
        }
    }

    private static void setBlockGuardPolicy(int n) {
        if (n == 0) {
            BlockGuard.setThreadPolicy((BlockGuard.Policy)BlockGuard.LAX_POLICY);
            return;
        }
        BlockGuard.Policy policy = BlockGuard.getThreadPolicy();
        if (policy instanceof AndroidBlockGuardPolicy) {
            policy = (AndroidBlockGuardPolicy)policy;
        } else {
            policy = THREAD_ANDROID_POLICY.get();
            BlockGuard.setThreadPolicy((BlockGuard.Policy)policy);
        }
        policy.setThreadPolicyMask(n);
    }

    private static void setBlockGuardVmPolicy(int n) {
        if ((n & 2048) != 0) {
            BlockGuard.setVmPolicy((BlockGuard.VmPolicy)VM_ANDROID_POLICY);
        } else {
            BlockGuard.setVmPolicy((BlockGuard.VmPolicy)BlockGuard.LAX_VM_POLICY);
        }
    }

    private static void setCloseGuardEnabled(boolean bl) {
        if (!(CloseGuard.getReporter() instanceof AndroidCloseGuardReporter)) {
            CloseGuard.setReporter((CloseGuard.Reporter)new AndroidCloseGuardReporter());
        }
        CloseGuard.setEnabled((boolean)bl);
    }

    public static void setThreadPolicy(ThreadPolicy threadPolicy) {
        StrictMode.setThreadPolicyMask(threadPolicy.mask);
        sThreadViolationListener.set(threadPolicy.mListener);
        sThreadViolationExecutor.set(threadPolicy.mCallbackExecutor);
    }

    public static void setThreadPolicyMask(int n) {
        StrictMode.setBlockGuardPolicy(n);
        Binder.setThreadStrictModePolicy(n);
    }

    public static void setViolationLogger(ViolationLogger violationLogger) {
        ViolationLogger violationLogger2 = violationLogger;
        if (violationLogger == null) {
            violationLogger2 = LOGCAT_LOGGER;
        }
        sLogger = violationLogger2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setVmPolicy(VmPolicy object) {
        synchronized (StrictMode.class) {
            sVmPolicy = object;
            StrictMode.setCloseGuardEnabled(StrictMode.vmClosableObjectLeaksEnabled());
            Object object2 = Looper.getMainLooper();
            if (object2 != null) {
                object2 = ((Looper)object2).mQueue;
                if (((VmPolicy)object).classInstanceLimit.size() != 0 && (StrictMode.sVmPolicy.mask & -65536) != 0) {
                    if (!sIsIdlerRegistered) {
                        ((MessageQueue)object2).addIdleHandler(sProcessIdleHandler);
                        sIsIdlerRegistered = true;
                    }
                } else {
                    ((MessageQueue)object2).removeIdleHandler(sProcessIdleHandler);
                    sIsIdlerRegistered = false;
                }
            }
            int n = 0;
            if ((StrictMode.sVmPolicy.mask & 64) != 0) {
                n = (StrictMode.sVmPolicy.mask & 268435456) == 0 && (StrictMode.sVmPolicy.mask & 16777216) == 0 ? 1 : 2;
            }
            if ((object = INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"))) != null) {
                try {
                    object.setUidCleartextNetworkPolicy(Process.myUid(), n);
                }
                catch (RemoteException remoteException) {}
            } else if (n != 0) {
                Log.w(TAG, "Dropping requested network policy due to missing service!");
            }
            if ((StrictMode.sVmPolicy.mask & 512) != 0) {
                VMRuntime.setNonSdkApiUsageConsumer(sNonSdkApiUsageConsumer);
                VMRuntime.setDedupeHiddenApiWarnings((boolean)false);
            } else {
                VMRuntime.setNonSdkApiUsageConsumer(null);
                VMRuntime.setDedupeHiddenApiWarnings((boolean)true);
            }
            StrictMode.setBlockGuardVmPolicy(StrictMode.sVmPolicy.mask);
            return;
        }
    }

    private static boolean tooManyViolationsThisLoop() {
        boolean bl = violationsBeingTimed.get().size() >= 10;
        return bl;
    }

    public static Object trackActivity(Object object) {
        return new InstanceTracker(object);
    }

    public static boolean vmCleartextNetworkEnabled() {
        boolean bl = (StrictMode.sVmPolicy.mask & 64) != 0;
        return bl;
    }

    public static boolean vmClosableObjectLeaksEnabled() {
        boolean bl = (StrictMode.sVmPolicy.mask & 2) != 0;
        return bl;
    }

    public static boolean vmContentUriWithoutPermissionEnabled() {
        boolean bl = (StrictMode.sVmPolicy.mask & 128) != 0;
        return bl;
    }

    public static boolean vmCredentialProtectedWhileLockedEnabled() {
        boolean bl = (StrictMode.sVmPolicy.mask & 2048) != 0;
        return bl;
    }

    public static boolean vmFileUriExposureEnabled() {
        boolean bl = (StrictMode.sVmPolicy.mask & 32) != 0;
        return bl;
    }

    public static boolean vmImplicitDirectBootEnabled() {
        boolean bl = (StrictMode.sVmPolicy.mask & 1024) != 0;
        return bl;
    }

    public static boolean vmRegistrationLeaksEnabled() {
        boolean bl = (StrictMode.sVmPolicy.mask & 16) != 0;
        return bl;
    }

    public static boolean vmSqliteObjectLeaksEnabled() {
        int n = StrictMode.sVmPolicy.mask;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public static boolean vmUntaggedSocketEnabled() {
        boolean bl = (StrictMode.sVmPolicy.mask & 256) != 0;
        return bl;
    }

    static void writeGatheredViolationsToParcel(Parcel parcel) {
        ArrayList<ViolationInfo> arrayList = gatheredViolations.get();
        if (arrayList == null) {
            parcel.writeInt(0);
        } else {
            int n = Math.min(arrayList.size(), 3);
            parcel.writeInt(n);
            for (int i = 0; i < n; ++i) {
                arrayList.get(i).writeToParcel(parcel, 0);
            }
        }
        gatheredViolations.set(null);
    }

    private static class AndroidBlockGuardPolicy
    implements BlockGuard.Policy {
        private ArrayMap<Integer, Long> mLastViolationTime;
        private int mThreadPolicyMask;

        public AndroidBlockGuardPolicy(int n) {
            this.mThreadPolicyMask = n;
        }

        static /* synthetic */ void lambda$onThreadPolicyViolation$1(OnThreadViolationListener onThreadViolationListener, Violation violation) {
            ThreadPolicy threadPolicy = StrictMode.allowThreadViolations();
            try {
                onThreadViolationListener.onThreadViolation(violation);
                return;
            }
            finally {
                StrictMode.setThreadPolicy(threadPolicy);
            }
        }

        public int getPolicyMask() {
            return this.mThreadPolicyMask;
        }

        public int getThreadPolicyMask() {
            return this.mThreadPolicyMask;
        }

        void handleViolationWithTimingAttempt(ViolationInfo object) {
            if (Looper.myLooper() != null && ((ViolationInfo)object).mPenaltyMask != 268435456) {
                ArrayList arrayList = (ArrayList)violationsBeingTimed.get();
                if (arrayList.size() >= 10) {
                    return;
                }
                arrayList.add(object);
                if (arrayList.size() > 1) {
                    return;
                }
                if ((object = ((ViolationInfo)object).penaltyEnabled(134217728) ? (IWindowManager)sWindowManager.get() : null) != null) {
                    try {
                        object.showStrictModeViolation(true);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
                ((Handler)THREAD_HANDLER.get()).postAtFrontOfQueue(new _$$Lambda$StrictMode$AndroidBlockGuardPolicy$9nBulCQKaMajrWr41SB7f7YRT1I(this, (IWindowManager)object, arrayList));
                return;
            }
            ((ViolationInfo)object).durationMillis = -1;
            this.onThreadPolicyViolation((ViolationInfo)object);
        }

        public /* synthetic */ void lambda$handleViolationWithTimingAttempt$0$StrictMode$AndroidBlockGuardPolicy(IWindowManager object, ArrayList arrayList) {
            long l = SystemClock.uptimeMillis();
            if (object != null) {
                try {
                    object.showStrictModeViolation(false);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            for (int i = 0; i < arrayList.size(); ++i) {
                object = (ViolationInfo)arrayList.get(i);
                ((ViolationInfo)object).violationNumThisLoop = i + 1;
                ((ViolationInfo)object).durationMillis = (int)(l - ((ViolationInfo)object).violationUptimeMillis);
                this.onThreadPolicyViolation((ViolationInfo)object);
            }
            arrayList.clear();
        }

        void onCustomSlowCall(String string2) {
            if ((this.mThreadPolicyMask & 8) == 0) {
                return;
            }
            if (StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            this.startHandlingViolationException(new CustomViolation(string2));
        }

        public void onExplicitGc() {
            if ((this.mThreadPolicyMask & 64) == 0) {
                return;
            }
            if (StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            this.startHandlingViolationException(new ExplicitGcViolation());
        }

        public void onNetwork() {
            int n = this.mThreadPolicyMask;
            if ((n & 4) == 0) {
                return;
            }
            if ((n & 33554432) == 0) {
                if (StrictMode.tooManyViolationsThisLoop()) {
                    return;
                }
                this.startHandlingViolationException(new NetworkViolation());
                return;
            }
            throw new NetworkOnMainThreadException();
        }

        public void onReadFromDisk() {
            if ((this.mThreadPolicyMask & 2) == 0) {
                return;
            }
            if (StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            this.startHandlingViolationException(new DiskReadViolation());
        }

        void onResourceMismatch(Object object) {
            if ((this.mThreadPolicyMask & 16) == 0) {
                return;
            }
            if (StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            this.startHandlingViolationException(new ResourceMismatchViolation(object));
        }

        void onThreadPolicyViolation(ViolationInfo object) {
            int n;
            Serializable serializable;
            if (LOG_V) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("onThreadPolicyViolation; penalty=");
                ((StringBuilder)serializable).append(((ViolationInfo)object).mPenaltyMask);
                Log.d(StrictMode.TAG, ((StringBuilder)serializable).toString());
            }
            boolean bl = ((ViolationInfo)object).penaltyEnabled(Integer.MIN_VALUE);
            boolean bl2 = true;
            if (bl) {
                Object object2 = (ArrayList)gatheredViolations.get();
                serializable = object2;
                if (object2 == null) {
                    serializable = new ArrayList(1);
                    gatheredViolations.set(serializable);
                }
                object2 = ((ArrayList)serializable).iterator();
                while (object2.hasNext()) {
                    ViolationInfo violationInfo = (ViolationInfo)object2.next();
                    if (!((ViolationInfo)object).getStackTrace().equals(violationInfo.getStackTrace())) continue;
                    return;
                }
                ((ArrayList)serializable).add(object);
                return;
            }
            serializable = Integer.valueOf(((ViolationInfo)object).hashCode());
            long l = 0L;
            ArrayMap<Integer, Long> arrayMap = this.mLastViolationTime;
            if (arrayMap != null) {
                if ((arrayMap = arrayMap.get(serializable)) != null) {
                    l = (Long)((Object)arrayMap);
                }
            } else {
                this.mLastViolationTime = new ArrayMap(1);
            }
            long l2 = SystemClock.uptimeMillis();
            this.mLastViolationTime.put((Integer)serializable, l2);
            l2 = l == 0L ? Long.MAX_VALUE : (l2 -= l);
            if (((ViolationInfo)object).penaltyEnabled(1073741824) && l2 > 1000L) {
                sLogger.log((ViolationInfo)object);
            }
            serializable = ((ViolationInfo)object).mViolation;
            int n2 = n = 0;
            if (((ViolationInfo)object).penaltyEnabled(536870912)) {
                n2 = n;
                if (l2 > 30000L) {
                    n2 = 0 | 536870912;
                }
            }
            if (((ViolationInfo)object).penaltyEnabled(67108864) && l == 0L) {
                n2 |= 67108864;
            }
            if (n2 != 0) {
                if (((ViolationInfo)object).mPenaltyMask != 67108864) {
                    bl2 = false;
                }
                if (bl2) {
                    StrictMode.dropboxViolationAsync(n2, (ViolationInfo)object);
                } else {
                    StrictMode.handleApplicationStrictModeViolation(n2, (ViolationInfo)object);
                }
            }
            if (!((ViolationInfo)object).penaltyEnabled(268435456)) {
                arrayMap = (OnThreadViolationListener)sThreadViolationListener.get();
                Executor executor = (Executor)sThreadViolationExecutor.get();
                if (arrayMap != null && executor != null) {
                    try {
                        object = new _$$Lambda$StrictMode$AndroidBlockGuardPolicy$FxZGA9KtfTewqdcxlUwvIe5Nx9I((OnThreadViolationListener)((Object)arrayMap), (Violation)serializable);
                        executor.execute((Runnable)object);
                    }
                    catch (RejectedExecutionException rejectedExecutionException) {
                        Log.e(StrictMode.TAG, "ThreadPolicy penaltyCallback failed", rejectedExecutionException);
                    }
                }
                return;
            }
            throw new RuntimeException("StrictMode ThreadPolicy violation", (Throwable)serializable);
        }

        public void onUnbufferedIO() {
            if ((this.mThreadPolicyMask & 32) == 0) {
                return;
            }
            if (StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            this.startHandlingViolationException(new UnbufferedIoViolation());
        }

        public void onWriteToDisk() {
            if ((this.mThreadPolicyMask & 1) == 0) {
                return;
            }
            if (StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            this.startHandlingViolationException(new DiskWriteViolation());
        }

        public void setThreadPolicyMask(int n) {
            this.mThreadPolicyMask = n;
        }

        void startHandlingViolationException(Violation object) {
            object = new ViolationInfo((Violation)object, this.mThreadPolicyMask & -65536);
            ((ViolationInfo)object).violationUptimeMillis = SystemClock.uptimeMillis();
            this.handleViolationWithTimingAttempt((ViolationInfo)object);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AndroidBlockGuardPolicy; mPolicyMask=");
            stringBuilder.append(this.mThreadPolicyMask);
            return stringBuilder.toString();
        }
    }

    private static class AndroidCloseGuardReporter
    implements CloseGuard.Reporter {
        private AndroidCloseGuardReporter() {
        }

        public void report(String string2, Throwable throwable) {
            StrictMode.onVmPolicyViolation(new LeakedClosableViolation(string2, throwable));
        }
    }

    private static final class InstanceTracker {
        private static final HashMap<Class<?>, Integer> sInstanceCounts = new HashMap();
        private final Class<?> mKlass;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public InstanceTracker(Object hashMap) {
            this.mKlass = hashMap.getClass();
            hashMap = sInstanceCounts;
            synchronized (hashMap) {
                Integer n = sInstanceCounts.get(this.mKlass);
                int n2 = 1;
                if (n != null) {
                    n2 = 1 + n;
                }
                sInstanceCounts.put(this.mKlass, n2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static int getInstanceCount(Class<?> serializable) {
            HashMap<Class<?>, Integer> hashMap = sInstanceCounts;
            synchronized (hashMap) {
                serializable = sInstanceCounts.get(serializable);
                if (serializable == null) return 0;
                return (Integer)serializable;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void finalize() throws Throwable {
            block8 : {
                Integer n;
                try {
                    HashMap<Class<?>, Integer> hashMap = sInstanceCounts;
                    synchronized (hashMap) {
                        n = sInstanceCounts.get(this.mKlass);
                        if (n == null) break block8;
                    }
                }
                catch (Throwable throwable) {
                    super.finalize();
                    throw throwable;
                }
                {
                    int n2 = n - 1;
                    if (n2 > 0) {
                        sInstanceCounts.put(this.mKlass, n2);
                    } else {
                        sInstanceCounts.remove(this.mKlass);
                    }
                }
            }
            super.finalize();
        }
    }

    public static interface OnThreadViolationListener {
        public void onThreadViolation(Violation var1);
    }

    public static interface OnVmViolationListener {
        public void onVmViolation(Violation var1);
    }

    public static class Span {
        private final ThreadSpanState mContainerState;
        private long mCreateMillis;
        private String mName;
        private Span mNext;
        private Span mPrev;

        protected Span() {
            this.mContainerState = null;
        }

        Span(ThreadSpanState threadSpanState) {
            this.mContainerState = threadSpanState;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @UnsupportedAppUsage
        public void finish() {
            ThreadSpanState threadSpanState = this.mContainerState;
            synchronized (threadSpanState) {
                if (this.mName == null) {
                    return;
                }
                if (this.mPrev != null) {
                    this.mPrev.mNext = this.mNext;
                }
                if (this.mNext != null) {
                    this.mNext.mPrev = this.mPrev;
                }
                if (threadSpanState.mActiveHead == this) {
                    threadSpanState.mActiveHead = this.mNext;
                }
                --threadSpanState.mActiveSize;
                if (LOG_V) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Span finished=");
                    stringBuilder.append(this.mName);
                    stringBuilder.append("; size=");
                    stringBuilder.append(threadSpanState.mActiveSize);
                    Log.d(StrictMode.TAG, stringBuilder.toString());
                }
                this.mCreateMillis = -1L;
                this.mName = null;
                this.mPrev = null;
                this.mNext = null;
                if (threadSpanState.mFreeListSize < 5) {
                    this.mNext = threadSpanState.mFreeListHead;
                    threadSpanState.mFreeListHead = this;
                    ++threadSpanState.mFreeListSize;
                }
                return;
            }
        }
    }

    public static final class ThreadPolicy {
        public static final ThreadPolicy LAX = new ThreadPolicy(0, null, null);
        final Executor mCallbackExecutor;
        final OnThreadViolationListener mListener;
        @UnsupportedAppUsage
        final int mask;

        private ThreadPolicy(int n, OnThreadViolationListener onThreadViolationListener, Executor executor) {
            this.mask = n;
            this.mListener = onThreadViolationListener;
            this.mCallbackExecutor = executor;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[StrictMode.ThreadPolicy; mask=");
            stringBuilder.append(this.mask);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        public static final class Builder {
            private Executor mExecutor;
            private OnThreadViolationListener mListener;
            private int mMask = 0;

            public Builder() {
                this.mMask = 0;
            }

            public Builder(ThreadPolicy threadPolicy) {
                this.mMask = threadPolicy.mask;
                this.mListener = threadPolicy.mListener;
                this.mExecutor = threadPolicy.mCallbackExecutor;
            }

            private Builder disable(int n) {
                this.mMask &= n;
                return this;
            }

            private Builder enable(int n) {
                this.mMask |= n;
                return this;
            }

            public ThreadPolicy build() {
                int n;
                if (this.mListener == null && (n = this.mMask) != 0 && (n & 1946157056) == 0) {
                    this.penaltyLog();
                }
                return new ThreadPolicy(this.mMask, this.mListener, this.mExecutor);
            }

            public Builder detectAll() {
                this.detectDiskReads();
                this.detectDiskWrites();
                this.detectNetwork();
                int n = VMRuntime.getRuntime().getTargetSdkVersion();
                if (n >= 11) {
                    this.detectCustomSlowCalls();
                }
                if (n >= 23) {
                    this.detectResourceMismatches();
                }
                if (n >= 26) {
                    this.detectUnbufferedIo();
                }
                return this;
            }

            public Builder detectCustomSlowCalls() {
                return this.enable(8);
            }

            public Builder detectDiskReads() {
                return this.enable(2);
            }

            public Builder detectDiskWrites() {
                return this.enable(1);
            }

            public Builder detectExplicitGc() {
                return this.enable(64);
            }

            public Builder detectNetwork() {
                return this.enable(4);
            }

            public Builder detectResourceMismatches() {
                return this.enable(16);
            }

            public Builder detectUnbufferedIo() {
                return this.enable(32);
            }

            public Builder penaltyDeath() {
                return this.enable(268435456);
            }

            public Builder penaltyDeathOnNetwork() {
                return this.enable(33554432);
            }

            public Builder penaltyDialog() {
                return this.enable(536870912);
            }

            public Builder penaltyDropBox() {
                return this.enable(67108864);
            }

            public Builder penaltyFlashScreen() {
                return this.enable(134217728);
            }

            public Builder penaltyListener(OnThreadViolationListener onThreadViolationListener, Executor executor) {
                return this.penaltyListener(executor, onThreadViolationListener);
            }

            public Builder penaltyListener(Executor executor, OnThreadViolationListener onThreadViolationListener) {
                if (executor != null) {
                    this.mListener = onThreadViolationListener;
                    this.mExecutor = executor;
                    return this;
                }
                throw new NullPointerException("executor must not be null");
            }

            public Builder penaltyLog() {
                return this.enable(1073741824);
            }

            public Builder permitAll() {
                return this.disable(65535);
            }

            public Builder permitCustomSlowCalls() {
                return this.disable(8);
            }

            public Builder permitDiskReads() {
                return this.disable(2);
            }

            public Builder permitDiskWrites() {
                return this.disable(1);
            }

            public Builder permitExplicitGc() {
                return this.disable(64);
            }

            public Builder permitNetwork() {
                return this.disable(4);
            }

            public Builder permitResourceMismatches() {
                return this.disable(16);
            }

            public Builder permitUnbufferedIo() {
                return this.disable(32);
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ThreadPolicyMask {
    }

    private static class ThreadSpanState {
        public Span mActiveHead;
        public int mActiveSize;
        public Span mFreeListHead;
        public int mFreeListSize;

        private ThreadSpanState() {
        }
    }

    public static final class ViolationInfo
    implements Parcelable {
        public static final Parcelable.Creator<ViolationInfo> CREATOR = new Parcelable.Creator<ViolationInfo>(){

            @Override
            public ViolationInfo createFromParcel(Parcel parcel) {
                return new ViolationInfo(parcel);
            }

            public ViolationInfo[] newArray(int n) {
                return new ViolationInfo[n];
            }
        };
        public String broadcastIntentAction;
        public int durationMillis = -1;
        private final Deque<StackTraceElement[]> mBinderStack = new ArrayDeque<StackTraceElement[]>();
        private final int mPenaltyMask;
        private String mStackTrace;
        private final Violation mViolation;
        public int numAnimationsRunning = 0;
        public long numInstances = -1L;
        public String[] tags;
        public int violationNumThisLoop;
        public long violationUptimeMillis;

        public ViolationInfo(Parcel parcel) {
            this(parcel, false);
        }

        public ViolationInfo(Parcel parcel, boolean bl) {
            int n;
            this.mViolation = (Violation)parcel.readSerializable();
            int n2 = parcel.readInt();
            for (n = 0; n < n2; ++n) {
                StackTraceElement[] arrstackTraceElement = new StackTraceElement[parcel.readInt()];
                for (int i = 0; i < arrstackTraceElement.length; ++i) {
                    arrstackTraceElement[i] = new StackTraceElement(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt());
                }
                this.mBinderStack.add(arrstackTraceElement);
            }
            n = parcel.readInt();
            this.mPenaltyMask = bl ? Integer.MAX_VALUE & n : n;
            this.durationMillis = parcel.readInt();
            this.violationNumThisLoop = parcel.readInt();
            this.numAnimationsRunning = parcel.readInt();
            this.violationUptimeMillis = parcel.readLong();
            this.numInstances = parcel.readLong();
            this.broadcastIntentAction = parcel.readString();
            this.tags = parcel.readStringArray();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        ViolationInfo(Violation object, int n) {
            this.mViolation = object;
            this.mPenaltyMask = n;
            this.violationUptimeMillis = SystemClock.uptimeMillis();
            this.numAnimationsRunning = ValueAnimator.getCurrentAnimationsCount();
            Object object2 = ActivityThread.getIntentBeingBroadcast();
            if (object2 != null) {
                this.broadcastIntentAction = ((Intent)object2).getAction();
            }
            object2 = (ThreadSpanState)sThisThreadSpanState.get();
            if (object instanceof InstanceCountViolation) {
                this.numInstances = ((InstanceCountViolation)object).getNumberOfInstances();
            }
            synchronized (object2) {
                int n2;
                n = n2 = ((ThreadSpanState)object2).mActiveSize;
                if (n2 > 20) {
                    n = 20;
                }
                if (n != 0) {
                    this.tags = new String[n];
                    object = ((ThreadSpanState)object2).mActiveHead;
                    for (n2 = 0; object != null && n2 < n; ++n2) {
                        this.tags[n2] = ((Span)object).mName;
                        object = ((Span)object).mNext;
                    }
                }
                return;
            }
        }

        void addLocalStack(Throwable throwable) {
            this.mBinderStack.addFirst(throwable.getStackTrace());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public void dump(Printer printer, String string2) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("stackTrace: ");
            ((StringBuilder)object).append(this.getStackTrace());
            printer.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("penalty: ");
            ((StringBuilder)object).append(this.mPenaltyMask);
            printer.println(((StringBuilder)object).toString());
            if (this.durationMillis != -1) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("durationMillis: ");
                ((StringBuilder)object).append(this.durationMillis);
                printer.println(((StringBuilder)object).toString());
            }
            if (this.numInstances != -1L) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("numInstances: ");
                ((StringBuilder)object).append(this.numInstances);
                printer.println(((StringBuilder)object).toString());
            }
            if (this.violationNumThisLoop != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("violationNumThisLoop: ");
                ((StringBuilder)object).append(this.violationNumThisLoop);
                printer.println(((StringBuilder)object).toString());
            }
            if (this.numAnimationsRunning != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("numAnimationsRunning: ");
                ((StringBuilder)object).append(this.numAnimationsRunning);
                printer.println(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("violationUptimeMillis: ");
            ((StringBuilder)object).append(this.violationUptimeMillis);
            printer.println(((StringBuilder)object).toString());
            if (this.broadcastIntentAction != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("broadcastIntentAction: ");
                ((StringBuilder)object).append(this.broadcastIntentAction);
                printer.println(((StringBuilder)object).toString());
            }
            if ((object = this.tags) != null) {
                int n = 0;
                int n2 = ((Object)object).length;
                int n3 = 0;
                while (n3 < n2) {
                    Object object2 = object[n3];
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(string2);
                    stringBuilder.append("tag[");
                    stringBuilder.append(n);
                    stringBuilder.append("]: ");
                    stringBuilder.append((String)object2);
                    printer.println(stringBuilder.toString());
                    ++n3;
                    ++n;
                }
            }
        }

        public String getStackTrace() {
            if (this.mStackTrace == null) {
                StringWriter stringWriter = new StringWriter();
                FastPrintWriter fastPrintWriter = new FastPrintWriter(stringWriter, false, 256);
                this.mViolation.printStackTrace(fastPrintWriter);
                for (StackTraceElement[] arrstackTraceElement : this.mBinderStack) {
                    fastPrintWriter.append("# via Binder call with stack:\n");
                    for (StackTraceElement stackTraceElement : arrstackTraceElement) {
                        fastPrintWriter.append("\tat ");
                        fastPrintWriter.append(stackTraceElement.toString());
                        fastPrintWriter.append('\n');
                    }
                }
                ((PrintWriter)fastPrintWriter).flush();
                ((PrintWriter)fastPrintWriter).close();
                this.mStackTrace = stringWriter.toString();
            }
            return this.mStackTrace;
        }

        public Class<? extends Violation> getViolationClass() {
            return this.mViolation.getClass();
        }

        public String getViolationDetails() {
            return this.mViolation.getMessage();
        }

        public int hashCode() {
            int n = 17;
            Object object = this.mViolation;
            if (object != null) {
                n = 17 * 37 + object.hashCode();
            }
            int n2 = n;
            if (this.numAnimationsRunning != 0) {
                n2 = n * 37;
            }
            object = this.broadcastIntentAction;
            n = n2;
            if (object != null) {
                n = n2 * 37 + object.hashCode();
            }
            object = this.tags;
            int n3 = n;
            if (object != null) {
                int n4 = ((String[])object).length;
                n2 = 0;
                do {
                    n3 = n;
                    if (n2 >= n4) break;
                    n = n * 37 + object[n2].hashCode();
                    ++n2;
                } while (true);
            }
            return n3;
        }

        boolean penaltyEnabled(int n) {
            boolean bl = (this.mPenaltyMask & n) != 0;
            return bl;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeSerializable(this.mViolation);
            parcel.writeInt(this.mBinderStack.size());
            for (StackTraceElement[] arrstackTraceElement : this.mBinderStack) {
                parcel.writeInt(arrstackTraceElement.length);
                for (StackTraceElement stackTraceElement : arrstackTraceElement) {
                    parcel.writeString(stackTraceElement.getClassName());
                    parcel.writeString(stackTraceElement.getMethodName());
                    parcel.writeString(stackTraceElement.getFileName());
                    parcel.writeInt(stackTraceElement.getLineNumber());
                }
            }
            parcel.dataPosition();
            parcel.writeInt(this.mPenaltyMask);
            parcel.writeInt(this.durationMillis);
            parcel.writeInt(this.violationNumThisLoop);
            parcel.writeInt(this.numAnimationsRunning);
            parcel.writeLong(this.violationUptimeMillis);
            parcel.writeLong(this.numInstances);
            parcel.writeString(this.broadcastIntentAction);
            parcel.writeStringArray(this.tags);
            parcel.dataPosition();
        }

    }

    public static interface ViolationLogger {
        public void log(ViolationInfo var1);
    }

    public static final class VmPolicy {
        public static final VmPolicy LAX = new VmPolicy(0, StrictMode.access$100(), null, null);
        final HashMap<Class, Integer> classInstanceLimit;
        final Executor mCallbackExecutor;
        final OnVmViolationListener mListener;
        @UnsupportedAppUsage
        final int mask;

        private VmPolicy(int n, HashMap<Class, Integer> hashMap, OnVmViolationListener onVmViolationListener, Executor executor) {
            if (hashMap != null) {
                this.mask = n;
                this.classInstanceLimit = hashMap;
                this.mListener = onVmViolationListener;
                this.mCallbackExecutor = executor;
                return;
            }
            throw new NullPointerException("classInstanceLimit == null");
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[StrictMode.VmPolicy; mask=");
            stringBuilder.append(this.mask);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        public static final class Builder {
            private HashMap<Class, Integer> mClassInstanceLimit;
            private boolean mClassInstanceLimitNeedCow = false;
            private Executor mExecutor;
            private OnVmViolationListener mListener;
            @UnsupportedAppUsage
            private int mMask;

            public Builder() {
                this.mMask = 0;
            }

            public Builder(VmPolicy vmPolicy) {
                this.mMask = vmPolicy.mask;
                this.mClassInstanceLimitNeedCow = true;
                this.mClassInstanceLimit = vmPolicy.classInstanceLimit;
                this.mListener = vmPolicy.mListener;
                this.mExecutor = vmPolicy.mCallbackExecutor;
            }

            private Builder enable(int n) {
                this.mMask |= n;
                return this;
            }

            public VmPolicy build() {
                int n;
                if (this.mListener == null && (n = this.mMask) != 0 && (n & 1946157056) == 0) {
                    this.penaltyLog();
                }
                n = this.mMask;
                HashMap hashMap = this.mClassInstanceLimit;
                if (hashMap == null) {
                    hashMap = EMPTY_CLASS_LIMIT_MAP;
                }
                return new VmPolicy(n, hashMap, this.mListener, this.mExecutor);
            }

            public Builder detectActivityLeaks() {
                return this.enable(4);
            }

            public Builder detectAll() {
                this.detectLeakedSqlLiteObjects();
                int n = VMRuntime.getRuntime().getTargetSdkVersion();
                if (n >= 11) {
                    this.detectActivityLeaks();
                    this.detectLeakedClosableObjects();
                }
                if (n >= 16) {
                    this.detectLeakedRegistrationObjects();
                }
                if (n >= 18) {
                    this.detectFileUriExposure();
                }
                if (n >= 23 && SystemProperties.getBoolean(StrictMode.CLEARTEXT_PROPERTY, false)) {
                    this.detectCleartextNetwork();
                }
                if (n >= 26) {
                    this.detectContentUriWithoutPermission();
                    this.detectUntaggedSockets();
                }
                if (n >= 29) {
                    this.detectCredentialProtectedWhileLocked();
                }
                return this;
            }

            public Builder detectCleartextNetwork() {
                return this.enable(64);
            }

            public Builder detectContentUriWithoutPermission() {
                return this.enable(128);
            }

            public Builder detectCredentialProtectedWhileLocked() {
                return this.enable(2048);
            }

            public Builder detectFileUriExposure() {
                return this.enable(32);
            }

            public Builder detectImplicitDirectBoot() {
                return this.enable(1024);
            }

            public Builder detectLeakedClosableObjects() {
                return this.enable(2);
            }

            public Builder detectLeakedRegistrationObjects() {
                return this.enable(16);
            }

            public Builder detectLeakedSqlLiteObjects() {
                return this.enable(1);
            }

            public Builder detectNonSdkApiUsage() {
                return this.enable(512);
            }

            public Builder detectUntaggedSockets() {
                return this.enable(256);
            }

            Builder disable(int n) {
                this.mMask &= n;
                return this;
            }

            public Builder penaltyDeath() {
                return this.enable(268435456);
            }

            public Builder penaltyDeathOnCleartextNetwork() {
                return this.enable(16777216);
            }

            public Builder penaltyDeathOnFileUriExposure() {
                return this.enable(8388608);
            }

            public Builder penaltyDropBox() {
                return this.enable(67108864);
            }

            public Builder penaltyListener(OnVmViolationListener onVmViolationListener, Executor executor) {
                return this.penaltyListener(executor, onVmViolationListener);
            }

            public Builder penaltyListener(Executor executor, OnVmViolationListener onVmViolationListener) {
                if (executor != null) {
                    this.mListener = onVmViolationListener;
                    this.mExecutor = executor;
                    return this;
                }
                throw new NullPointerException("executor must not be null");
            }

            public Builder penaltyLog() {
                return this.enable(1073741824);
            }

            public Builder permitActivityLeaks() {
                return this.disable(4);
            }

            public Builder permitCredentialProtectedWhileLocked() {
                return this.disable(2048);
            }

            public Builder permitImplicitDirectBoot() {
                return this.disable(1024);
            }

            public Builder permitNonSdkApiUsage() {
                return this.disable(512);
            }

            public Builder permitUntaggedSockets() {
                return this.disable(256);
            }

            public Builder setClassInstanceLimit(Class class_, int n) {
                if (class_ != null) {
                    if (this.mClassInstanceLimitNeedCow) {
                        if (this.mClassInstanceLimit.containsKey(class_) && this.mClassInstanceLimit.get(class_) == n) {
                            return this;
                        }
                        this.mClassInstanceLimitNeedCow = false;
                        this.mClassInstanceLimit = (HashMap)this.mClassInstanceLimit.clone();
                    } else if (this.mClassInstanceLimit == null) {
                        this.mClassInstanceLimit = new HashMap();
                    }
                    this.mMask |= 8;
                    this.mClassInstanceLimit.put(class_, n);
                    return this;
                }
                throw new NullPointerException("klass == null");
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface VmPolicyMask {
    }

}

