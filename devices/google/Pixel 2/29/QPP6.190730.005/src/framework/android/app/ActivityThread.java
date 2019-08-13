/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.-$
 *  android.app.-$$Lambda
 *  android.app.-$$Lambda$ActivityThread
 *  android.app.-$$Lambda$ActivityThread$ApplicationThread
 *  android.app.-$$Lambda$ActivityThread$ApplicationThread$nBC_BR7B9W6ftKAxur3BC53SJYc
 *  android.app.-$$Lambda$ActivityThread$ApplicationThread$tUGFX7CUhzB4Pg5wFd5yeqOnu38
 *  android.app.-$$Lambda$ActivityThread$ApplicationThread$uR_ee-5oPoxu4U_by7wU55jwtdU
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 *  com.android.org.conscrypt.OpenSSLSocketImpl
 *  com.android.org.conscrypt.TrustedCertificateStore
 *  dalvik.system.CloseGuard
 *  dalvik.system.VMDebug
 *  dalvik.system.VMRuntime
 *  libcore.io.ForwardingOs
 *  libcore.io.IoUtils
 *  libcore.io.Os
 *  libcore.net.event.NetworkEventDispatcher
 *  org.apache.harmony.dalvik.ddmc.DdmVmInternal
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.-$;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AppComponentFactory;
import android.app.Application;
import android.app.ApplicationPackageManager;
import android.app.ClientTransactionHandler;
import android.app.ContentProviderHolder;
import android.app.ContextImpl;
import android.app.DirectAction;
import android.app.FragmentController;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.IInstrumentationWatcher;
import android.app.IUiAutomationConnection;
import android.app.Instrumentation;
import android.app.LoadedApk;
import android.app.OnActivityPausedListener;
import android.app.ProfilerInfo;
import android.app.QueuedWork;
import android.app.RemoteServiceException;
import android.app.ResourcesManager;
import android.app.ResultInfo;
import android.app.Service;
import android.app.ServiceStartArgs;
import android.app.VoiceInteractor;
import android.app._$$Lambda$ActivityThread$A4ykhsPb8qV3ffTqpQDklHSMDJ0;
import android.app._$$Lambda$ActivityThread$ActivityClientRecord$HOrG1qglSjSUHSjKBn2rXtX0gGg;
import android.app._$$Lambda$ActivityThread$ApplicationThread$nBC_BR7B9W6ftKAxur3BC53SJYc;
import android.app._$$Lambda$ActivityThread$ApplicationThread$tUGFX7CUhzB4Pg5wFd5yeqOnu38;
import android.app._$$Lambda$ActivityThread$ApplicationThread$uR_ee_5oPoxu4U_by7wU55jwtdU;
import android.app._$$Lambda$ActivityThread$FmvGY8exyv0L0oqZrnunpl8OFI8;
import android.app._$$Lambda$ActivityThread$Wg40iAoNYFxps_KmrqtgptTB054;
import android.app._$$Lambda$ZsFzoG2loyqNOR2cNbo_thrNK5c;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.app.backup.BackupAgent;
import android.app.servertransaction.ActivityLifecycleItem;
import android.app.servertransaction.ActivityRelaunchItem;
import android.app.servertransaction.ActivityResultItem;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.PendingTransactionActions;
import android.app.servertransaction.TransactionExecutor;
import android.app.servertransaction.TransactionExecutorHelper;
import android.content.AutofillOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.IContentProvider;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.res.AssetManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDebug;
import android.ddm.DdmHandleAppName;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.HardwareRenderer;
import android.graphics.ImageDecoder;
import android.hardware.display.DisplayManagerGlobal;
import android.net.ConnectivityManager;
import android.net.IConnectivityManager;
import android.net.Network;
import android.net.Proxy;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BaseBundle;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.GraphicsEnvironment;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.LocaleList;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.FontsContract;
import android.renderscript.RenderScriptCacheDir;
import android.security.NetworkSecurityPolicy;
import android.security.net.config.NetworkSecurityConfigProvider;
import android.system.ErrnoException;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.Log;
import android.util.MergedConfiguration;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SuperNotCalledException;
import android.util.UtilConfig;
import android.util.proto.ProtoOutputStream;
import android.view.Choreographer;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.webkit.WebView;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.content.ReferrerIntent;
import com.android.internal.os.BinderInternal;
import com.android.internal.os.RuntimeInit;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.org.conscrypt.OpenSSLSocketImpl;
import com.android.org.conscrypt.TrustedCertificateStore;
import dalvik.system.CloseGuard;
import dalvik.system.VMDebug;
import dalvik.system.VMRuntime;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import libcore.io.ForwardingOs;
import libcore.io.IoUtils;
import libcore.io.Os;
import libcore.net.event.NetworkEventDispatcher;
import org.apache.harmony.dalvik.ddmc.DdmVmInternal;

public final class ActivityThread
extends ClientTransactionHandler {
    private static final int ACTIVITY_THREAD_CHECKIN_VERSION = 4;
    private static final long CONTENT_PROVIDER_RETAIN_TIME = 1000L;
    private static final boolean DEBUG_BACKUP = false;
    public static final boolean DEBUG_BROADCAST = false;
    public static final boolean DEBUG_CONFIGURATION = false;
    public static final boolean DEBUG_MEMORY_TRIM = false;
    static final boolean DEBUG_MESSAGES = false;
    public static final boolean DEBUG_ORDER = false;
    private static final boolean DEBUG_PROVIDER = false;
    private static final boolean DEBUG_RESULTS = false;
    private static final boolean DEBUG_SERVICE = false;
    private static final String HEAP_COLUMN = "%13s %8s %8s %8s %8s %8s %8s %8s";
    private static final String HEAP_FULL_COLUMN = "%13s %8s %8s %8s %8s %8s %8s %8s %8s %8s %8s";
    public static final long INVALID_PROC_STATE_SEQ = -1L;
    private static final long MIN_TIME_BETWEEN_GCS = 5000L;
    private static final String ONE_COUNT_COLUMN = "%21s %8d";
    private static final String ONE_COUNT_COLUMN_HEADER = "%21s %8s";
    private static final long PENDING_TOP_PROCESS_STATE_TIMEOUT = 1000L;
    public static final String PROC_START_SEQ_IDENT = "seq=";
    private static final boolean REPORT_TO_ACTIVITY = true;
    public static final int SERVICE_DONE_EXECUTING_ANON = 0;
    public static final int SERVICE_DONE_EXECUTING_START = 1;
    public static final int SERVICE_DONE_EXECUTING_STOP = 2;
    private static final int SQLITE_MEM_RELEASED_EVENT_LOG_TAG = 75003;
    public static final String TAG = "ActivityThread";
    private static final Bitmap.Config THUMBNAIL_FORMAT = Bitmap.Config.RGB_565;
    private static final String TWO_COUNT_COLUMNS = "%21s %8d %21s %8d";
    private static final int VM_PROCESS_STATE_JANK_IMPERCEPTIBLE = 1;
    private static final int VM_PROCESS_STATE_JANK_PERCEPTIBLE = 0;
    static final boolean localLOGV = false;
    @UnsupportedAppUsage
    private static volatile ActivityThread sCurrentActivityThread;
    private static final ThreadLocal<Intent> sCurrentBroadcastIntent;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    static volatile Handler sMainThreadHandler;
    @UnsupportedAppUsage
    static volatile IPackageManager sPackageManager;
    @UnsupportedAppUsage
    final ArrayMap<IBinder, ActivityClientRecord> mActivities = new ArrayMap();
    final Map<IBinder, ClientTransactionItem> mActivitiesToBeDestroyed = Collections.synchronizedMap(new ArrayMap());
    @UnsupportedAppUsage
    final ArrayList<Application> mAllApplications = new ArrayList();
    @UnsupportedAppUsage
    final ApplicationThread mAppThread = new ApplicationThread();
    private final SparseArray<ArrayMap<String, BackupAgent>> mBackupAgentsByUser = new SparseArray();
    @UnsupportedAppUsage
    AppBindData mBoundApplication;
    Configuration mCompatConfiguration;
    @UnsupportedAppUsage
    Configuration mConfiguration;
    Bundle mCoreSettings = null;
    @UnsupportedAppUsage
    int mCurDefaultDisplayDpi;
    @UnsupportedAppUsage
    boolean mDensityCompatMode;
    final Executor mExecutor = new HandlerExecutor(this.mH);
    final GcIdler mGcIdler = new GcIdler();
    boolean mGcIdlerScheduled = false;
    @GuardedBy(value={"mGetProviderLocks"})
    final ArrayMap<ProviderKey, Object> mGetProviderLocks = new ArrayMap();
    @UnsupportedAppUsage
    final H mH = new H();
    boolean mHiddenApiWarningShown = false;
    @UnsupportedAppUsage
    Application mInitialApplication;
    @UnsupportedAppUsage
    Instrumentation mInstrumentation;
    @UnsupportedAppUsage
    String mInstrumentationAppDir = null;
    String mInstrumentationLibDir = null;
    String mInstrumentationPackageName = null;
    String[] mInstrumentationSplitAppDirs = null;
    @UnsupportedAppUsage
    String mInstrumentedAppDir = null;
    String mInstrumentedLibDir = null;
    String[] mInstrumentedSplitAppDirs = null;
    ArrayList<WeakReference<AssistStructure>> mLastAssistStructures = new ArrayList();
    @GuardedBy(value={"mAppThread"})
    private int mLastProcessState = -1;
    private int mLastSessionId;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    final ArrayMap<IBinder, ProviderClientRecord> mLocalProviders = new ArrayMap();
    @UnsupportedAppUsage
    final ArrayMap<ComponentName, ProviderClientRecord> mLocalProvidersByName = new ArrayMap();
    @UnsupportedAppUsage
    final Looper mLooper = Looper.myLooper();
    private Configuration mMainThreadConfig = new Configuration();
    @GuardedBy(value={"mNetworkPolicyLock"})
    private long mNetworkBlockSeq = -1L;
    private final Object mNetworkPolicyLock = new Object();
    ActivityClientRecord mNewActivities = null;
    private final AtomicInteger mNumLaunchingActivities = new AtomicInteger();
    @UnsupportedAppUsage
    int mNumVisibleActivities = 0;
    final ArrayMap<Activity, ArrayList<OnActivityPausedListener>> mOnPauseListeners = new ArrayMap();
    @UnsupportedAppUsage
    @GuardedBy(value={"mResourcesManager"})
    final ArrayMap<String, WeakReference<LoadedApk>> mPackages = new ArrayMap();
    @UnsupportedAppUsage
    @GuardedBy(value={"mResourcesManager"})
    Configuration mPendingConfiguration = null;
    @GuardedBy(value={"mAppThread"})
    private int mPendingProcessState = -1;
    Profiler mProfiler;
    @UnsupportedAppUsage
    final ArrayMap<ProviderKey, ProviderClientRecord> mProviderMap = new ArrayMap();
    @UnsupportedAppUsage
    final ArrayMap<IBinder, ProviderRefCount> mProviderRefCountMap = new ArrayMap();
    final PurgeIdler mPurgeIdler = new PurgeIdler();
    boolean mPurgeIdlerScheduled = false;
    @GuardedBy(value={"mResourcesManager"})
    final ArrayList<ActivityClientRecord> mRelaunchingActivities = new ArrayList();
    @GuardedBy(value={"this"})
    private Map<SafeCancellationTransport, CancellationSignal> mRemoteCancellations;
    @UnsupportedAppUsage
    @GuardedBy(value={"mResourcesManager"})
    final ArrayMap<String, WeakReference<LoadedApk>> mResourcePackages = new ArrayMap();
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final ResourcesManager mResourcesManager = ResourcesManager.getInstance();
    @UnsupportedAppUsage
    final ArrayMap<IBinder, Service> mServices = new ArrayMap();
    boolean mSomeActivitiesChanged = false;
    @UnsupportedAppUsage
    private ContextImpl mSystemContext;
    boolean mSystemThread = false;
    private ContextImpl mSystemUiContext;
    private final TransactionExecutor mTransactionExecutor = new TransactionExecutor(this);
    boolean mUpdatingSystemConfig = false;

    static {
        sCurrentBroadcastIntent = new ThreadLocal();
    }

    @UnsupportedAppUsage
    ActivityThread() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void applyPendingProcessState() {
        ApplicationThread applicationThread = this.mAppThread;
        synchronized (applicationThread) {
            if (this.mPendingProcessState == -1) {
                return;
            }
            int n = this.mPendingProcessState;
            this.mPendingProcessState = -1;
            if (n == this.mLastProcessState) {
                this.updateVmProcessState(n);
            }
            return;
        }
    }

    @UnsupportedAppUsage
    private void attach(boolean bl, long l) {
        block5 : {
            block4 : {
                sCurrentActivityThread = this;
                this.mSystemThread = bl;
                if (bl) break block4;
                DdmHandleAppName.setAppName("<pre-initialized>", UserHandle.myUserId());
                RuntimeInit.setApplicationObject(this.mAppThread.asBinder());
                IActivityManager iActivityManager = ActivityManager.getService();
                try {
                    iActivityManager.attachApplication(this.mAppThread, l);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                BinderInternal.addGcWatcher(new Runnable(){

                    @Override
                    public void run() {
                        if (!ActivityThread.this.mSomeActivitiesChanged) {
                            return;
                        }
                        Runtime runtime = Runtime.getRuntime();
                        long l = runtime.maxMemory();
                        if (runtime.totalMemory() - runtime.freeMemory() > 3L * l / 4L) {
                            ActivityThread.this.mSomeActivitiesChanged = false;
                            try {
                                ActivityTaskManager.getService().releaseSomeActivities(ActivityThread.this.mAppThread);
                            }
                            catch (RemoteException remoteException) {
                                throw remoteException.rethrowFromSystemServer();
                            }
                        }
                    }
                });
                break block5;
            }
            DdmHandleAppName.setAppName("system_process", UserHandle.myUserId());
            try {
                Instrumentation instrumentation;
                this.mInstrumentation = instrumentation = new Instrumentation();
                this.mInstrumentation.basicInit(this);
                this.mInitialApplication = ContextImpl.createAppContext((ActivityThread)this, (LoadedApk)this.getSystemContext().mPackageInfo).mPackageInfo.makeApplication(true, null);
                this.mInitialApplication.onCreate();
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to instantiate Application():");
                stringBuilder.append(exception.toString());
                throw new RuntimeException(stringBuilder.toString(), exception);
            }
        }
        ViewRootImpl.addConfigCallback(new _$$Lambda$ActivityThread$Wg40iAoNYFxps_KmrqtgptTB054(this));
    }

    private static boolean attemptAttachAgent(String string2, ClassLoader classLoader) {
        try {
            VMDebug.attachAgent((String)string2, (ClassLoader)classLoader);
            return true;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attaching agent with ");
            stringBuilder.append(classLoader);
            stringBuilder.append(" failed: ");
            stringBuilder.append(string2);
            Slog.e(TAG, stringBuilder.toString());
            return false;
        }
    }

    private void callActivityOnSaveInstanceState(ActivityClientRecord activityClientRecord) {
        activityClientRecord.state = new Bundle();
        activityClientRecord.state.setAllowFds(false);
        if (activityClientRecord.isPersistable()) {
            activityClientRecord.persistentState = new PersistableBundle();
            this.mInstrumentation.callActivityOnSaveInstanceState(activityClientRecord.activity, activityClientRecord.state, activityClientRecord.persistentState);
        } else {
            this.mInstrumentation.callActivityOnSaveInstanceState(activityClientRecord.activity, activityClientRecord.state);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void callActivityOnStop(ActivityClientRecord activityClientRecord, boolean bl, String charSequence) {
        Exception exception22222222;
        block4 : {
            boolean bl2 = bl && !activityClientRecord.activity.mFinished && activityClientRecord.state == null && !activityClientRecord.isPreHoneycomb();
            bl = activityClientRecord.isPreP();
            if (bl2 && bl) {
                this.callActivityOnSaveInstanceState(activityClientRecord);
            }
            try {
                activityClientRecord.activity.performStop(activityClientRecord.mPreserveWindow, (String)charSequence);
            }
            catch (Exception exception22222222) {
                if (!this.mInstrumentation.onException(activityClientRecord.activity, exception22222222)) break block4;
            }
            activityClientRecord.setState(5);
            if (!bl2) return;
            if (bl) return;
            this.callActivityOnSaveInstanceState(activityClientRecord);
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unable to stop activity ");
        ((StringBuilder)charSequence).append(activityClientRecord.intent.getComponent().toShortString());
        ((StringBuilder)charSequence).append(": ");
        ((StringBuilder)charSequence).append(exception22222222.toString());
        throw new RuntimeException(((StringBuilder)charSequence).toString(), exception22222222);
        catch (SuperNotCalledException superNotCalledException) {
            throw superNotCalledException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void checkAndBlockForNetworkAccess() {
        Object object = this.mNetworkPolicyLock;
        synchronized (object) {
            long l = this.mNetworkBlockSeq;
            if (l != -1L) {
                try {
                    ActivityManager.getService().waitForNetworkStateUpdate(this.mNetworkBlockSeq);
                    this.mNetworkBlockSeq = -1L;
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            return;
        }
    }

    static final void cleanUpPendingRemoveWindows(ActivityClientRecord activityClientRecord, boolean bl) {
        if (activityClientRecord.mPreserveWindow && !bl) {
            return;
        }
        if (activityClientRecord.mPendingRemoveWindow != null) {
            activityClientRecord.mPendingRemoveWindowManager.removeViewImmediate(activityClientRecord.mPendingRemoveWindow.getDecorView());
            IBinder iBinder = activityClientRecord.mPendingRemoveWindow.getDecorView().getWindowToken();
            if (iBinder != null) {
                WindowManagerGlobal.getInstance().closeAll(iBinder, activityClientRecord.activity.getClass().getName(), "Activity");
            }
        }
        activityClientRecord.mPendingRemoveWindow = null;
        activityClientRecord.mPendingRemoveWindowManager = null;
    }

    private ContextImpl createBaseContextForActivity(ActivityClientRecord arrn) {
        int n;
        try {
            n = ActivityTaskManager.getService().getActivityDisplayId(arrn.token);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        ContextImpl contextImpl = ContextImpl.createActivityContext(this, arrn.packageInfo, arrn.activityInfo, arrn.token, n, arrn.overrideConfig);
        DisplayManagerGlobal displayManagerGlobal = DisplayManagerGlobal.getInstance();
        String string2 = SystemProperties.get("debug.second-display.pkg");
        ContextImpl contextImpl2 = contextImpl;
        if (string2 != null) {
            contextImpl2 = contextImpl;
            if (!string2.isEmpty()) {
                contextImpl2 = contextImpl;
                if (arrn.packageInfo.mPackageName.contains(string2)) {
                    arrn = displayManagerGlobal.getDisplayIds();
                    int n2 = arrn.length;
                    n = 0;
                    do {
                        contextImpl2 = contextImpl;
                        if (n >= n2) break;
                        int n3 = arrn[n];
                        if (n3 != 0) {
                            contextImpl2 = (ContextImpl)contextImpl.createDisplayContext(displayManagerGlobal.getCompatibleDisplay(n3, contextImpl.getResources()));
                            break;
                        }
                        ++n;
                    } while (true);
                }
            }
        }
        return contextImpl2;
    }

    private static Configuration createNewConfigAndUpdateIfNotNull(Configuration configuration, Configuration configuration2) {
        if (configuration2 == null) {
            return configuration;
        }
        configuration = new Configuration(configuration);
        configuration.updateFrom(configuration2);
        return configuration;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SafeCancellationTransport createSafeCancellationTransport(CancellationSignal cancellationSignal) {
        synchronized (this) {
            ArrayMap<SafeCancellationTransport, CancellationSignal> arrayMap;
            if (this.mRemoteCancellations == null) {
                arrayMap = new ArrayMap<SafeCancellationTransport, CancellationSignal>();
                this.mRemoteCancellations = arrayMap;
            }
            arrayMap = new ArrayMap<SafeCancellationTransport, CancellationSignal>(this, cancellationSignal);
            this.mRemoteCancellations.put((SafeCancellationTransport)((Object)arrayMap), cancellationSignal);
            return arrayMap;
        }
    }

    @UnsupportedAppUsage
    public static ActivityThread currentActivityThread() {
        return sCurrentActivityThread;
    }

    @UnsupportedAppUsage
    public static Application currentApplication() {
        Object object = ActivityThread.currentActivityThread();
        object = object != null ? ((ActivityThread)object).mInitialApplication : null;
        return object;
    }

    public static String currentOpPackageName() {
        Object object = ActivityThread.currentActivityThread();
        object = object != null && ((ActivityThread)object).getApplication() != null ? ((ActivityThread)object).getApplication().getOpPackageName() : null;
        return object;
    }

    @UnsupportedAppUsage
    public static String currentPackageName() {
        Object object = ActivityThread.currentActivityThread();
        object = object != null && (object = ((ActivityThread)object).mBoundApplication) != null ? object.appInfo.packageName : null;
        return object;
    }

    @UnsupportedAppUsage
    public static String currentProcessName() {
        Object object = ActivityThread.currentActivityThread();
        object = object != null && (object = ((ActivityThread)object).mBoundApplication) != null ? ((AppBindData)object).processName : null;
        return object;
    }

    private void deliverNewIntents(ActivityClientRecord activityClientRecord, List<ReferrerIntent> list) {
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            ReferrerIntent referrerIntent = list.get(i);
            referrerIntent.setExtrasClassLoader(activityClientRecord.activity.getClassLoader());
            referrerIntent.prepareToEnterProcess();
            activityClientRecord.activity.mFragments.noteStateNotSaved();
            this.mInstrumentation.callActivityOnNewIntent(activityClientRecord.activity, referrerIntent);
        }
    }

    private void deliverResults(ActivityClientRecord activityClientRecord, List<ResultInfo> object, String string2) {
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            Exception exception2;
            ResultInfo resultInfo;
            block4 : {
                resultInfo = object.get(i);
                try {
                    if (resultInfo.mData != null) {
                        resultInfo.mData.setExtrasClassLoader(activityClientRecord.activity.getClassLoader());
                        resultInfo.mData.prepareToEnterProcess();
                    }
                    activityClientRecord.activity.dispatchActivityResult(resultInfo.mResultWho, resultInfo.mRequestCode, resultInfo.mResultCode, resultInfo.mData, string2);
                }
                catch (Exception exception2) {
                    if (!this.mInstrumentation.onException(activityClientRecord.activity, exception2)) break block4;
                }
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Failure delivering result ");
            ((StringBuilder)object).append(resultInfo);
            ((StringBuilder)object).append(" to activity ");
            ((StringBuilder)object).append(activityClientRecord.intent.getComponent().toShortString());
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(exception2.toString());
            throw new RuntimeException(((StringBuilder)object).toString(), exception2);
        }
    }

    public static void dumpMemInfoTable(ProtoOutputStream protoOutputStream, Debug.MemoryInfo memoryInfo, boolean bl, boolean bl2, long l, long l2, long l3, long l4, long l5, long l6) {
        if (!bl2) {
            int n;
            long l7 = protoOutputStream.start(1146756268035L);
            ActivityThread.dumpMemoryInfo(protoOutputStream, 1146756268033L, "Native Heap", memoryInfo.nativePss, memoryInfo.nativeSwappablePss, memoryInfo.nativeSharedDirty, memoryInfo.nativePrivateDirty, memoryInfo.nativeSharedClean, memoryInfo.nativePrivateClean, memoryInfo.hasSwappedOutPss, memoryInfo.nativeSwappedOut, memoryInfo.nativeSwappedOutPss);
            ProtoOutputStream protoOutputStream2 = protoOutputStream;
            protoOutputStream2.write(1120986464258L, l);
            protoOutputStream2.write(1120986464259L, l2);
            protoOutputStream2.write(1120986464260L, l3);
            protoOutputStream2.end(l7);
            l7 = protoOutputStream2.start(1146756268036L);
            int n2 = memoryInfo.dalvikPss;
            int n3 = memoryInfo.dalvikSwappablePss;
            int n4 = memoryInfo.dalvikSharedDirty;
            int n5 = memoryInfo.dalvikPrivateDirty;
            int n6 = memoryInfo.dalvikSharedClean;
            int n7 = memoryInfo.dalvikPrivateClean;
            bl2 = memoryInfo.hasSwappedOutPss;
            int n8 = memoryInfo.dalvikSwappedOut;
            int n9 = memoryInfo.dalvikSwappedOutPss;
            ActivityThread.dumpMemoryInfo(protoOutputStream, 1146756268033L, "Dalvik Heap", n2, n3, n4, n5, n6, n7, bl2, n8, n9);
            protoOutputStream2.write(1120986464258L, l4);
            protoOutputStream2.write(1120986464259L, l5);
            protoOutputStream2.write(1120986464260L, l6);
            protoOutputStream2.end(l7);
            Debug.MemoryInfo memoryInfo2 = memoryInfo;
            int n10 = memoryInfo2.otherPss;
            n2 = memoryInfo2.otherSwappablePss;
            n9 = memoryInfo2.otherSharedDirty;
            n4 = memoryInfo2.otherPrivateDirty;
            n8 = memoryInfo2.otherSharedClean;
            n3 = memoryInfo2.otherPrivateClean;
            n6 = memoryInfo2.otherSwappedOut;
            n7 = memoryInfo2.otherSwappedOutPss;
            n5 = 0;
            do {
                memoryInfo2 = memoryInfo;
                if (n5 >= 17) break;
                int n11 = memoryInfo2.getOtherPss(n5);
                int n12 = memoryInfo2.getOtherSwappablePss(n5);
                int n13 = memoryInfo2.getOtherSharedDirty(n5);
                int n14 = memoryInfo2.getOtherPrivateDirty(n5);
                int n15 = memoryInfo2.getOtherSharedClean(n5);
                int n16 = memoryInfo2.getOtherPrivateClean(n5);
                int n17 = memoryInfo2.getOtherSwappedOut(n5);
                int n18 = memoryInfo2.getOtherSwappedOutPss(n5);
                if (n11 != 0 || n13 != 0 || n14 != 0 || n15 != 0 || n16 != 0 || (n = memoryInfo2.hasSwappedOutPss ? n18 : n17) != 0) {
                    ActivityThread.dumpMemoryInfo(protoOutputStream, 2246267895813L, Debug.MemoryInfo.getOtherLabel(n5), n11, n12, n13, n14, n15, n16, memoryInfo2.hasSwappedOutPss, n17, n18);
                    n10 -= n11;
                    n2 -= n12;
                    n9 -= n13;
                    n4 -= n14;
                    n8 -= n15;
                    n3 -= n16;
                    n6 -= n17;
                    n7 -= n18;
                }
                ++n5;
            } while (true);
            bl2 = memoryInfo.hasSwappedOutPss;
            memoryInfo2 = memoryInfo;
            ActivityThread.dumpMemoryInfo(protoOutputStream, 1146756268038L, "Unknown", n10, n2, n9, n4, n8, n3, bl2, n6, n7);
            l7 = protoOutputStream2.start(1146756268039L);
            ActivityThread.dumpMemoryInfo(protoOutputStream, 1146756268033L, "TOTAL", memoryInfo.getTotalPss(), memoryInfo.getTotalSwappablePss(), memoryInfo.getTotalSharedDirty(), memoryInfo.getTotalPrivateDirty(), memoryInfo.getTotalSharedClean(), memoryInfo.getTotalPrivateClean(), memoryInfo2.hasSwappedOutPss, memoryInfo.getTotalSwappedOut(), memoryInfo.getTotalSwappedOutPss());
            protoOutputStream2.write(1120986464258L, l + l4);
            protoOutputStream2.write(1120986464259L, l2 + l5);
            protoOutputStream2.write(1120986464260L, l3 + l6);
            l = l7;
            protoOutputStream2.end(l);
            if (bl) {
                for (n7 = 17; n7 < 31; ++n7) {
                    n4 = memoryInfo2.getOtherPss(n7);
                    n9 = memoryInfo2.getOtherSwappablePss(n7);
                    n2 = memoryInfo2.getOtherSharedDirty(n7);
                    n5 = memoryInfo2.getOtherPrivateDirty(n7);
                    n10 = memoryInfo2.getOtherSharedClean(n7);
                    n = memoryInfo2.getOtherPrivateClean(n7);
                    n8 = memoryInfo2.getOtherSwappedOut(n7);
                    n3 = memoryInfo2.getOtherSwappedOutPss(n7);
                    if (n4 == 0 && n2 == 0 && n5 == 0 && n10 == 0 && n == 0 && (n6 = memoryInfo2.hasSwappedOutPss ? n3 : n8) == 0) continue;
                    ActivityThread.dumpMemoryInfo(protoOutputStream, 2246267895816L, Debug.MemoryInfo.getOtherLabel(n7), n4, n9, n2, n5, n10, n, memoryInfo2.hasSwappedOutPss, n8, n3);
                }
            }
        }
        l = protoOutputStream.start(1146756268041L);
        protoOutputStream.write(1120986464257L, memoryInfo.getSummaryJavaHeap());
        protoOutputStream.write(1120986464258L, memoryInfo.getSummaryNativeHeap());
        protoOutputStream.write(1120986464259L, memoryInfo.getSummaryCode());
        protoOutputStream.write(1120986464260L, memoryInfo.getSummaryStack());
        protoOutputStream.write(1120986464261L, memoryInfo.getSummaryGraphics());
        protoOutputStream.write(1120986464262L, memoryInfo.getSummaryPrivateOther());
        protoOutputStream.write(1120986464263L, memoryInfo.getSummarySystem());
        if (memoryInfo.hasSwappedOutPss) {
            protoOutputStream.write(1120986464264L, memoryInfo.getSummaryTotalSwapPss());
        } else {
            protoOutputStream.write(1120986464264L, memoryInfo.getSummaryTotalSwap());
        }
        protoOutputStream.end(l);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void dumpMemInfoTable(PrintWriter printWriter, Debug.MemoryInfo memoryInfo, boolean bl, boolean bl2, boolean bl3, boolean bl4, int n, String string2, long l, long l2, long l3, long l4, long l5, long l6) {
        String string3;
        block29 : {
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int n10;
            int n11;
            block28 : {
                block26 : {
                    block27 : {
                        block25 : {
                            if (!bl) break block25;
                            printWriter.print(4);
                            printWriter.print(',');
                            printWriter.print(n);
                            printWriter.print(',');
                            printWriter.print(string2);
                            printWriter.print(',');
                            printWriter.print(l);
                            printWriter.print(',');
                            printWriter.print(l4);
                            printWriter.print(',');
                            printWriter.print("N/A,");
                            printWriter.print(l + l4);
                            printWriter.print(',');
                            printWriter.print(l2);
                            printWriter.print(',');
                            printWriter.print(l5);
                            printWriter.print(',');
                            printWriter.print("N/A,");
                            printWriter.print(l2 + l5);
                            printWriter.print(',');
                            printWriter.print(l3);
                            printWriter.print(',');
                            printWriter.print(l6);
                            printWriter.print(',');
                            printWriter.print("N/A,");
                            printWriter.print(l3 + l6);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.nativePss);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.dalvikPss);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.otherPss);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.getTotalPss());
                            printWriter.print(',');
                            printWriter.print(memoryInfo.nativeSwappablePss);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.dalvikSwappablePss);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.otherSwappablePss);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.getTotalSwappablePss());
                            printWriter.print(',');
                            printWriter.print(memoryInfo.nativeSharedDirty);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.dalvikSharedDirty);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.otherSharedDirty);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.getTotalSharedDirty());
                            printWriter.print(',');
                            printWriter.print(memoryInfo.nativeSharedClean);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.dalvikSharedClean);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.otherSharedClean);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.getTotalSharedClean());
                            printWriter.print(',');
                            printWriter.print(memoryInfo.nativePrivateDirty);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.dalvikPrivateDirty);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.otherPrivateDirty);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.getTotalPrivateDirty());
                            printWriter.print(',');
                            printWriter.print(memoryInfo.nativePrivateClean);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.dalvikPrivateClean);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.otherPrivateClean);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.getTotalPrivateClean());
                            printWriter.print(',');
                            printWriter.print(memoryInfo.nativeSwappedOut);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.dalvikSwappedOut);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.otherSwappedOut);
                            printWriter.print(',');
                            printWriter.print(memoryInfo.getTotalSwappedOut());
                            printWriter.print(',');
                            if (memoryInfo.hasSwappedOutPss) {
                                printWriter.print(memoryInfo.nativeSwappedOutPss);
                                printWriter.print(',');
                                printWriter.print(memoryInfo.dalvikSwappedOutPss);
                                printWriter.print(',');
                                printWriter.print(memoryInfo.otherSwappedOutPss);
                                printWriter.print(',');
                                printWriter.print(memoryInfo.getTotalSwappedOutPss());
                                printWriter.print(',');
                            } else {
                                printWriter.print("N/A,");
                                printWriter.print("N/A,");
                                printWriter.print("N/A,");
                                printWriter.print("N/A,");
                            }
                            break block26;
                        }
                        string3 = "------";
                        if (bl4) break block27;
                        if (bl2) {
                            string2 = memoryInfo.hasSwappedOutPss ? "SwapPss" : "Swap";
                            ActivityThread.printRow(printWriter, HEAP_FULL_COLUMN, "", "Pss", "Pss", "Shared", "Private", "Shared", "Private", string2, "Heap", "Heap", "Heap");
                            ActivityThread.printRow(printWriter, HEAP_FULL_COLUMN, "", "Total", "Clean", "Dirty", "Dirty", "Clean", "Clean", "Dirty", "Size", "Alloc", "Free");
                            ActivityThread.printRow(printWriter, HEAP_FULL_COLUMN, "", "------", "------", "------", "------", "------", "------", "------", "------", "------", "------");
                            n8 = memoryInfo.nativePss;
                            n5 = memoryInfo.nativeSwappablePss;
                            n10 = memoryInfo.nativeSharedDirty;
                            n9 = memoryInfo.nativePrivateDirty;
                            n2 = memoryInfo.nativeSharedClean;
                            n3 = memoryInfo.nativePrivateClean;
                            n = memoryInfo.hasSwappedOutPss ? memoryInfo.nativeSwappedOutPss : memoryInfo.nativeSwappedOut;
                            ActivityThread.printRow(printWriter, HEAP_FULL_COLUMN, "Native Heap", n8, n5, n10, n9, n2, n3, n, l, l2, l3);
                            n8 = memoryInfo.dalvikPss;
                            n3 = memoryInfo.dalvikSwappablePss;
                            n2 = memoryInfo.dalvikSharedDirty;
                            n10 = memoryInfo.dalvikPrivateDirty;
                            n9 = memoryInfo.dalvikSharedClean;
                            n5 = memoryInfo.dalvikPrivateClean;
                            n = memoryInfo.hasSwappedOutPss ? memoryInfo.dalvikSwappedOutPss : memoryInfo.dalvikSwappedOut;
                            ActivityThread.printRow(printWriter, HEAP_FULL_COLUMN, "Dalvik Heap", n8, n3, n2, n10, n9, n5, n, l4, l5, l6);
                        } else {
                            string2 = memoryInfo.hasSwappedOutPss ? "SwapPss" : "Swap";
                            ActivityThread.printRow(printWriter, HEAP_COLUMN, "", "Pss", "Private", "Private", string2, "Heap", "Heap", "Heap");
                            ActivityThread.printRow(printWriter, HEAP_COLUMN, "", "Total", "Dirty", "Clean", "Dirty", "Size", "Alloc", "Free");
                            ActivityThread.printRow(printWriter, HEAP_COLUMN, "", "------", "------", "------", "------", "------", "------", "------", "------");
                            n3 = memoryInfo.nativePss;
                            n2 = memoryInfo.nativePrivateDirty;
                            n8 = memoryInfo.nativePrivateClean;
                            n = memoryInfo.hasSwappedOutPss ? memoryInfo.nativeSwappedOutPss : memoryInfo.nativeSwappedOut;
                            ActivityThread.printRow(printWriter, HEAP_COLUMN, "Native Heap", n3, n2, n8, n, l, l2, l3);
                            n2 = memoryInfo.dalvikPss;
                            n8 = memoryInfo.dalvikPrivateDirty;
                            n3 = memoryInfo.dalvikPrivateClean;
                            n = memoryInfo.hasSwappedOutPss ? memoryInfo.dalvikSwappedOutPss : memoryInfo.dalvikSwappedOut;
                            ActivityThread.printRow(printWriter, HEAP_COLUMN, "Dalvik Heap", n2, n8, n3, n, l4, l5, l6);
                        }
                        n7 = memoryInfo.otherPss;
                        n11 = memoryInfo.otherSwappablePss;
                        n4 = memoryInfo.otherSharedDirty;
                        n9 = memoryInfo.otherPrivateDirty;
                        n5 = memoryInfo.otherSharedClean;
                        n10 = memoryInfo.otherPrivateClean;
                        n8 = memoryInfo.otherSwappedOut;
                        n = memoryInfo.otherSwappedOutPss;
                        string2 = string3;
                        break block28;
                    }
                    string3 = "------";
                    break block29;
                }
                n = 0;
                while (n < 17) {
                    printWriter.print(Debug.MemoryInfo.getOtherLabel(n));
                    printWriter.print(',');
                    printWriter.print(memoryInfo.getOtherPss(n));
                    printWriter.print(',');
                    printWriter.print(memoryInfo.getOtherSwappablePss(n));
                    printWriter.print(',');
                    printWriter.print(memoryInfo.getOtherSharedDirty(n));
                    printWriter.print(',');
                    printWriter.print(memoryInfo.getOtherSharedClean(n));
                    printWriter.print(',');
                    printWriter.print(memoryInfo.getOtherPrivateDirty(n));
                    printWriter.print(',');
                    printWriter.print(memoryInfo.getOtherPrivateClean(n));
                    printWriter.print(',');
                    printWriter.print(memoryInfo.getOtherSwappedOut(n));
                    printWriter.print(',');
                    if (memoryInfo.hasSwappedOutPss) {
                        printWriter.print(memoryInfo.getOtherSwappedOutPss(n));
                        printWriter.print(',');
                    } else {
                        printWriter.print("N/A,");
                    }
                    ++n;
                }
                return;
            }
            for (n6 = 0; n6 < 17; ++n6) {
                int n12;
                int n13;
                int n14;
                int n15;
                int n16;
                int n17;
                int n18;
                int n19;
                block31 : {
                    int n20;
                    int n21;
                    int n22;
                    int n23;
                    int n24;
                    int n25;
                    block30 : {
                        n24 = memoryInfo.getOtherPss(n6);
                        n22 = memoryInfo.getOtherSwappablePss(n6);
                        n20 = memoryInfo.getOtherSharedDirty(n6);
                        n23 = memoryInfo.getOtherPrivateDirty(n6);
                        n21 = memoryInfo.getOtherSharedClean(n6);
                        n25 = memoryInfo.getOtherPrivateClean(n6);
                        n2 = memoryInfo.getOtherSwappedOut(n6);
                        n3 = memoryInfo.getOtherSwappedOutPss(n6);
                        if (n24 != 0 || n20 != 0 || n23 != 0 || n21 != 0 || n25 != 0) break block30;
                        int n26 = memoryInfo.hasSwappedOutPss ? n3 : n2;
                        n17 = n11;
                        n12 = n4;
                        n14 = n9;
                        n18 = n7;
                        n13 = n5;
                        n16 = n10;
                        n19 = n8;
                        n15 = n;
                        if (n26 == 0) break block31;
                    }
                    if (bl2) {
                        string3 = Debug.MemoryInfo.getOtherLabel(n6);
                        n15 = memoryInfo.hasSwappedOutPss ? n3 : n2;
                        ActivityThread.printRow(printWriter, HEAP_FULL_COLUMN, string3, n24, n22, n20, n23, n21, n25, n15, "", "", "");
                    } else {
                        string3 = Debug.MemoryInfo.getOtherLabel(n6);
                        n15 = memoryInfo.hasSwappedOutPss ? n3 : n2;
                        ActivityThread.printRow(printWriter, HEAP_COLUMN, string3, n24, n23, n25, n15, "", "", "");
                    }
                    n18 = n7 - n24;
                    n17 = n11 - n22;
                    n12 = n4 - n20;
                    n14 = n9 - n23;
                    n13 = n5 - n21;
                    n16 = n10 - n25;
                    n19 = n8 - n2;
                    n15 = n - n3;
                }
                n11 = n17;
                n4 = n12;
                n9 = n14;
                n7 = n18;
                n5 = n13;
                n10 = n16;
                n8 = n19;
                n = n15;
            }
            if (bl2) {
                if (memoryInfo.hasSwappedOutPss) {
                    n8 = n;
                }
                ActivityThread.printRow(printWriter, HEAP_FULL_COLUMN, "Unknown", n7, n11, n4, n9, n5, n10, n8, "", "", "");
                n3 = memoryInfo.getTotalPss();
                n2 = memoryInfo.getTotalSwappablePss();
                n9 = memoryInfo.getTotalSharedDirty();
                n8 = memoryInfo.getTotalPrivateDirty();
                n5 = memoryInfo.getTotalSharedClean();
                n10 = memoryInfo.getTotalPrivateClean();
                n = memoryInfo.hasSwappedOutPss ? memoryInfo.getTotalSwappedOutPss() : memoryInfo.getTotalSwappedOut();
                ActivityThread.printRow(printWriter, HEAP_FULL_COLUMN, "TOTAL", n3, n2, n9, n8, n5, n10, n, l + l4, l2 + l5, l3 + l6);
            } else {
                if (!memoryInfo.hasSwappedOutPss) {
                    n = n8;
                }
                ActivityThread.printRow(printWriter, HEAP_COLUMN, "Unknown", n7, n9, n10, n, "", "", "");
                n8 = memoryInfo.getTotalPss();
                n3 = memoryInfo.getTotalPrivateDirty();
                n2 = memoryInfo.getTotalPrivateClean();
                n = memoryInfo.hasSwappedOutPss ? memoryInfo.getTotalSwappedOutPss() : memoryInfo.getTotalSwappedOut();
                ActivityThread.printRow(printWriter, HEAP_COLUMN, "TOTAL", n8, n3, n2, n, l + l4, l2 + l5, l3 + l6);
            }
            string3 = string2;
            if (bl3) {
                printWriter.println(" ");
                printWriter.println(" Dalvik Details");
                n3 = 17;
                do {
                    string3 = string2;
                    if (n3 >= 31) break;
                    n9 = memoryInfo.getOtherPss(n3);
                    n4 = memoryInfo.getOtherSwappablePss(n3);
                    n6 = memoryInfo.getOtherSharedDirty(n3);
                    n10 = memoryInfo.getOtherPrivateDirty(n3);
                    n7 = memoryInfo.getOtherSharedClean(n3);
                    n5 = memoryInfo.getOtherPrivateClean(n3);
                    n8 = memoryInfo.getOtherSwappedOut(n3);
                    n = memoryInfo.getOtherSwappedOutPss(n3);
                    if (n9 != 0 || n6 != 0 || n10 != 0 || n7 != 0 || n5 != 0 || (n2 = memoryInfo.hasSwappedOutPss ? n : n8) != 0) {
                        if (bl2) {
                            string3 = Debug.MemoryInfo.getOtherLabel(n3);
                            if (!memoryInfo.hasSwappedOutPss) {
                                n = n8;
                            }
                            ActivityThread.printRow(printWriter, HEAP_FULL_COLUMN, string3, n9, n4, n6, n10, n7, n5, n, "", "", "");
                        } else {
                            string3 = Debug.MemoryInfo.getOtherLabel(n3);
                            if (!memoryInfo.hasSwappedOutPss) {
                                n = n8;
                            }
                            ActivityThread.printRow(printWriter, HEAP_COLUMN, string3, n9, n10, n5, n, "", "", "");
                        }
                    }
                    ++n3;
                } while (true);
            }
        }
        printWriter.println(" ");
        printWriter.println(" App Summary");
        ActivityThread.printRow(printWriter, ONE_COUNT_COLUMN_HEADER, "", "Pss(KB)");
        ActivityThread.printRow(printWriter, ONE_COUNT_COLUMN_HEADER, "", string3);
        ActivityThread.printRow(printWriter, ONE_COUNT_COLUMN, "Java Heap:", memoryInfo.getSummaryJavaHeap());
        ActivityThread.printRow(printWriter, ONE_COUNT_COLUMN, "Native Heap:", memoryInfo.getSummaryNativeHeap());
        ActivityThread.printRow(printWriter, ONE_COUNT_COLUMN, "Code:", memoryInfo.getSummaryCode());
        ActivityThread.printRow(printWriter, ONE_COUNT_COLUMN, "Stack:", memoryInfo.getSummaryStack());
        ActivityThread.printRow(printWriter, ONE_COUNT_COLUMN, "Graphics:", memoryInfo.getSummaryGraphics());
        ActivityThread.printRow(printWriter, ONE_COUNT_COLUMN, "Private Other:", memoryInfo.getSummaryPrivateOther());
        ActivityThread.printRow(printWriter, ONE_COUNT_COLUMN, "System:", memoryInfo.getSummarySystem());
        printWriter.println(" ");
        if (memoryInfo.hasSwappedOutPss) {
            ActivityThread.printRow(printWriter, TWO_COUNT_COLUMNS, "TOTAL:", memoryInfo.getSummaryTotalPss(), "TOTAL SWAP PSS:", memoryInfo.getSummaryTotalSwapPss());
            return;
        }
        ActivityThread.printRow(printWriter, TWO_COUNT_COLUMNS, "TOTAL:", memoryInfo.getSummaryTotalPss(), "TOTAL SWAP (KB):", memoryInfo.getSummaryTotalSwap());
    }

    private static void dumpMemoryInfo(ProtoOutputStream protoOutputStream, long l, String string2, int n, int n2, int n3, int n4, int n5, int n6, boolean bl, int n7, int n8) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1138166333441L, string2);
        protoOutputStream.write(1120986464258L, n);
        protoOutputStream.write(1120986464259L, n2);
        protoOutputStream.write(1120986464260L, n3);
        protoOutputStream.write(1120986464261L, n4);
        protoOutputStream.write(1120986464262L, n5);
        protoOutputStream.write(1120986464263L, n6);
        if (bl) {
            protoOutputStream.write(1120986464265L, n8);
        } else {
            protoOutputStream.write(1120986464264L, n7);
        }
        protoOutputStream.end(l);
    }

    static void freeTextLayoutCachesIfNeeded(int n) {
        if (n != 0 && (n = (n & 4) != 0 ? 1 : 0) != 0) {
            Canvas.freeTextLayoutCaches();
        }
    }

    private ArrayMap<String, BackupAgent> getBackupAgentsForUser(int n) {
        ArrayMap<String, BackupAgent> arrayMap;
        ArrayMap<String, BackupAgent> arrayMap2 = arrayMap = this.mBackupAgentsByUser.get(n);
        if (arrayMap == null) {
            arrayMap2 = new ArrayMap();
            this.mBackupAgentsByUser.put(n, arrayMap2);
        }
        return arrayMap2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object getGetProviderLock(String object, int n) {
        ProviderKey providerKey = new ProviderKey((String)object, n);
        ArrayMap<ProviderKey, Object> arrayMap = this.mGetProviderLocks;
        synchronized (arrayMap) {
            Object object2;
            object = object2 = this.mGetProviderLocks.get(providerKey);
            if (object2 == null) {
                object = providerKey;
                this.mGetProviderLocks.put(providerKey, object);
            }
            return object;
        }
    }

    private String getInstrumentationLibrary(ApplicationInfo object, InstrumentationInfo instrumentationInfo) {
        if (((ApplicationInfo)object).primaryCpuAbi != null && ((ApplicationInfo)object).secondaryCpuAbi != null && ((ApplicationInfo)object).secondaryCpuAbi.equals(instrumentationInfo.secondaryCpuAbi)) {
            object = VMRuntime.getInstructionSet((String)((ApplicationInfo)object).secondaryCpuAbi);
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("ro.dalvik.vm.isa.");
            ((StringBuilder)charSequence).append((String)object);
            charSequence = SystemProperties.get(((StringBuilder)charSequence).toString());
            if (!((String)charSequence).isEmpty()) {
                object = charSequence;
            }
            if (VMRuntime.getRuntime().vmInstructionSet().equals(object)) {
                return instrumentationInfo.secondaryNativeLibraryDir;
            }
        }
        return instrumentationInfo.nativeLibraryDir;
    }

    public static Intent getIntentBeingBroadcast() {
        return sCurrentBroadcastIntent.get();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private LoadedApk getPackageInfo(ApplicationInfo weakReference, CompatibilityInfo arrayMap, ClassLoader weakReference2, boolean bl, boolean bl2, boolean bl3) {
        boolean bl4 = UserHandle.myUserId() != UserHandle.getUserId(((ApplicationInfo)weakReference).uid);
        ResourcesManager resourcesManager = this.mResourcesManager;
        synchronized (resourcesManager) {
            Reference<Object> reference = bl4 ? null : (bl2 ? this.mPackages.get(((ApplicationInfo)weakReference).packageName) : this.mResourcePackages.get(((ApplicationInfo)weakReference).packageName));
            reference = reference != null ? (LoadedApk)reference.get() : null;
            if (reference != null) {
                if (!ActivityThread.isLoadedApkResourceDirsUpToDate(reference, (ApplicationInfo)((Object)weakReference))) {
                    ((LoadedApk)((Object)reference)).updateApplicationInfo((ApplicationInfo)((Object)weakReference), null);
                }
                return reference;
            }
            boolean bl5 = bl2 && (((ApplicationInfo)weakReference).flags & 4) != 0;
            reference = new LoadedApk(this, (ApplicationInfo)((Object)weakReference), (CompatibilityInfo)((Object)arrayMap), (ClassLoader)((Object)weakReference2), bl, bl5, bl3);
            if (this.mSystemThread && "android".equals(((ApplicationInfo)weakReference).packageName)) {
                ((LoadedApk)((Object)reference)).installSystemApplicationInfo((ApplicationInfo)((Object)weakReference), this.getSystemContext().mPackageInfo.getClassLoader());
            }
            if (!bl4) {
                if (bl2) {
                    arrayMap = this.mPackages;
                    weakReference = ((ApplicationInfo)weakReference).packageName;
                    weakReference2 = new WeakReference<Object>(reference);
                    arrayMap.put((String)((Object)weakReference), (WeakReference<LoadedApk>)weakReference2);
                } else {
                    arrayMap = this.mResourcePackages;
                    weakReference2 = ((ApplicationInfo)weakReference).packageName;
                    weakReference = new WeakReference<Object>(reference);
                    arrayMap.put((String)((Object)weakReference2), (WeakReference<LoadedApk>)weakReference);
                }
            }
            return reference;
        }
    }

    @UnsupportedAppUsage
    public static IPackageManager getPackageManager() {
        if (sPackageManager != null) {
            return sPackageManager;
        }
        sPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        return sPackageManager;
    }

    static void handleAttachAgent(String string2, LoadedApk object) {
        if (ActivityThread.attemptAttachAgent(string2, (ClassLoader)(object = object != null ? ((LoadedApk)object).getClassLoader() : null))) {
            return;
        }
        if (object != null) {
            ActivityThread.attemptAttachAgent(string2, null);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private void handleBindApplication(AppBindData var1_1) {
        block50 : {
            block49 : {
                block53 : {
                    block52 : {
                        block51 : {
                            VMRuntime.registerSensitiveThread();
                            var2_7 = SystemProperties.get("debug.allocTracker.stackDepth");
                            if (var2_7.length() != 0) {
                                VMDebug.setAllocTrackerStackDepth((int)Integer.parseInt((String)var2_7));
                            }
                            if (var1_1.trackAllocation) {
                                DdmVmInternal.enableRecentAllocations((boolean)true);
                            }
                            Process.setStartTimes(SystemClock.elapsedRealtime(), SystemClock.uptimeMillis());
                            this.mBoundApplication = var1_1;
                            this.mConfiguration = new Configuration(var1_1.config);
                            this.mCompatConfiguration = new Configuration(var1_1.config);
                            this.mProfiler = new Profiler();
                            if (var1_1.initProfilerInfo == null) ** GOTO lbl-1000
                            this.mProfiler.profileFile = var1_1.initProfilerInfo.profileFile;
                            this.mProfiler.profileFd = var1_1.initProfilerInfo.profileFd;
                            this.mProfiler.samplingInterval = var1_1.initProfilerInfo.samplingInterval;
                            this.mProfiler.autoStopProfiler = var1_1.initProfilerInfo.autoStopProfiler;
                            this.mProfiler.streamingOutput = var1_1.initProfilerInfo.streamingOutput;
                            if (var1_1.initProfilerInfo.attachAgentDuringBind) {
                                var2_7 = var1_1.initProfilerInfo.agent;
                            } else lbl-1000: // 2 sources:
                            {
                                var2_7 = null;
                            }
                            Process.setArgV0(var1_1.processName);
                            DdmHandleAppName.setAppName(var1_1.processName, UserHandle.myUserId());
                            VMRuntime.setProcessPackageName((String)var1_1.appInfo.packageName);
                            VMRuntime.setProcessDataDirectory((String)var1_1.appInfo.dataDir);
                            if (this.mProfiler.profileFd != null) {
                                this.mProfiler.startProfiling();
                            }
                            if (var1_1.appInfo.targetSdkVersion <= 12) {
                                AsyncTask.setDefaultExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            }
                            var3_12 = var1_1.appInfo.targetSdkVersion >= 29;
                            UtilConfig.setThrowExceptionForUpperArrayOutOfBounds(var3_12);
                            Message.updateCheckRecycle(var1_1.appInfo.targetSdkVersion);
                            ImageDecoder.sApiLevel = var1_1.appInfo.targetSdkVersion;
                            TimeZone.setDefault(null);
                            LocaleList.setDefault(var1_1.config.getLocales());
                            var4_13 = this.mResourcesManager;
                            // MONITORENTER : var4_13
                            this.mResourcesManager.applyConfigurationToResourcesLocked(var1_1.config, var1_1.compatInfo);
                            this.mCurDefaultDisplayDpi = var1_1.config.densityDpi;
                            this.applyCompatConfiguration(this.mCurDefaultDisplayDpi);
                            // MONITOREXIT : var4_13
                            var1_1.info = this.getPackageInfoNoCheck(var1_1.appInfo, var1_1.compatInfo);
                            if (var2_7 != null) {
                                ActivityThread.handleAttachAgent((String)var2_7, var1_1.info);
                            }
                            if ((var1_1.appInfo.flags & 8192) == 0) {
                                this.mDensityCompatMode = true;
                                Bitmap.setDefaultDensity(160);
                            }
                            this.updateDefaultDensity();
                            var2_7 = this.mCoreSettings.getString("time_12_24");
                            var2_7 = var2_7 != null ? ("24".equals(var2_7) ? Boolean.TRUE : Boolean.FALSE) : null;
                            DateFormat.set24HourTimePref((Boolean)var2_7);
                            this.updateDebugViewAttributeState();
                            StrictMode.initThreadDefaults(var1_1.appInfo);
                            StrictMode.initVmDefaults(var1_1.appInfo);
                            if (var1_1.debugMode != 0) {
                                Debug.changeDebugPort(8100);
                                if (var1_1.debugMode == 2) {
                                    var2_7 = new StringBuilder();
                                    var2_7.append("Application ");
                                    var2_7.append(var1_1.info.getPackageName());
                                    var2_7.append(" is waiting for the debugger on port 8100...");
                                    Slog.w("ActivityThread", var2_7.toString());
                                    var2_7 = ActivityManager.getService();
                                    try {
                                        var2_7.showWaitingForDebugger(this.mAppThread, true);
                                    }
                                    catch (RemoteException var1_3) {
                                        throw var1_3.rethrowFromSystemServer();
                                    }
                                    Debug.waitForDebugger();
                                    try {
                                        var2_7.showWaitingForDebugger(this.mAppThread, false);
                                    }
                                    catch (RemoteException var1_2) {
                                        throw var1_2.rethrowFromSystemServer();
                                    }
                                }
                                var2_7 = new StringBuilder();
                                var2_7.append("Application ");
                                var2_7.append(var1_1.info.getPackageName());
                                var2_7.append(" can be debugged on port 8100...");
                                Slog.w("ActivityThread", var2_7.toString());
                            }
                            var3_12 = var1_1.appInfo.isProfileableByShell();
                            Trace.setAppTracingAllowed(var3_12);
                            if (var3_12 && var1_1.enableBinderTracking) {
                                Binder.enableTracing();
                            }
                            if (var3_12 || Build.IS_DEBUGGABLE) {
                                this.nInitZygoteChildHeapProfiling();
                            }
                            var3_12 = (var5_14 = (var1_1.appInfo.flags & 2) != 0 ? 1 : 0) != 0 || Build.IS_DEBUGGABLE;
                            HardwareRenderer.setDebuggingEnabled(var3_12);
                            HardwareRenderer.setPackageName(var1_1.appInfo.packageName);
                            Trace.traceBegin(64L, "Setup proxies");
                            var2_7 = ServiceManager.getService("connectivity");
                            if (var2_7 != null) {
                                var2_7 = IConnectivityManager.Stub.asInterface((IBinder)var2_7);
                                try {
                                    Proxy.setHttpProxySystemProperty(var2_7.getProxyForNetwork(null));
                                }
                                catch (RemoteException var1_4) {
                                    Trace.traceEnd(64L);
                                    throw var1_4.rethrowFromSystemServer();
                                }
                            }
                            Trace.traceEnd(64L);
                            if (var1_1.instrumentationName == null) break block51;
                            try {
                                var2_7 = new ApplicationPackageManager(null, ActivityThread.getPackageManager());
                                var4_13 = var2_7.getInstrumentationInfo(var1_1.instrumentationName, 0);
                            }
                            catch (PackageManager.NameNotFoundException var2_8) {
                                var2_9 = new StringBuilder();
                                var2_9.append("Unable to find instrumentation info for: ");
                                var2_9.append(var1_1.instrumentationName);
                                throw new RuntimeException(var2_9.toString());
                            }
                            if (!Objects.equals(var1_1.appInfo.primaryCpuAbi, var4_13.primaryCpuAbi) || !Objects.equals(var1_1.appInfo.secondaryCpuAbi, var4_13.secondaryCpuAbi)) {
                                var2_7 = new StringBuilder();
                                var2_7.append("Package uses different ABI(s) than its instrumentation: package[");
                                var2_7.append(var1_1.appInfo.packageName);
                                var2_7.append("]: ");
                                var2_7.append(var1_1.appInfo.primaryCpuAbi);
                                var2_7.append(", ");
                                var2_7.append(var1_1.appInfo.secondaryCpuAbi);
                                var2_7.append(" instrumentation[");
                                var2_7.append(var4_13.packageName);
                                var2_7.append("]: ");
                                var2_7.append(var4_13.primaryCpuAbi);
                                var2_7.append(", ");
                                var2_7.append(var4_13.secondaryCpuAbi);
                                Slog.w("ActivityThread", var2_7.toString());
                            }
                            this.mInstrumentationPackageName = var4_13.packageName;
                            this.mInstrumentationAppDir = var4_13.sourceDir;
                            this.mInstrumentationSplitAppDirs = var4_13.splitSourceDirs;
                            this.mInstrumentationLibDir = this.getInstrumentationLibrary(var1_1.appInfo, (InstrumentationInfo)var4_13);
                            this.mInstrumentedAppDir = var1_1.info.getAppDir();
                            this.mInstrumentedSplitAppDirs = var1_1.info.getSplitAppDirs();
                            this.mInstrumentedLibDir = var1_1.info.getLibDir();
                            break block52;
                        }
                        var4_13 = null;
                    }
                    var6_15 = ContextImpl.createAppContext(this, var1_1.info);
                    this.updateLocaleListFromAppContext((Context)var6_15, this.mResourcesManager.getConfiguration().getLocales());
                    if (!Process.isIsolated()) {
                        var5_14 = StrictMode.allowThreadDiskWritesMask();
                        try {
                            this.setupGraphicsSupport((Context)var6_15);
                        }
                        finally {
                            StrictMode.setThreadPolicyMask(var5_14);
                        }
                    } else {
                        HardwareRenderer.setIsolatedProcess(true);
                    }
                    Trace.traceBegin(64L, "NetworkSecurityConfigProvider.install");
                    NetworkSecurityConfigProvider.install((Context)var6_15);
                    Trace.traceEnd(64L);
                    if (var4_13 == null) break block53;
                    try {
                        var2_7 = ActivityThread.getPackageManager().getApplicationInfo(var4_13.packageName, 0, UserHandle.myUserId());
                    }
                    catch (RemoteException var2_10) {
                        var2_7 = null;
                    }
                    if (var2_7 == null) {
                        var2_7 = new ApplicationInfo();
                    }
                    var4_13.copyTo((ApplicationInfo)var2_7);
                    var2_7.initForUser(UserHandle.myUserId());
                    var7_17 = var1_1.compatInfo;
                    var8_18 = var6_15.getClassLoader();
                    var9_19 = var6_15;
                    var2_7 = this.getPackageInfo((ApplicationInfo)var2_7, (CompatibilityInfo)var7_17, (ClassLoader)var8_18, false, true, false);
                    var7_17 = ContextImpl.createAppContext(this, (LoadedApk)var2_7, var9_19.getOpPackageName());
                    try {
                        this.mInstrumentation = (Instrumentation)var7_17.getClassLoader().loadClass(var1_1.instrumentationName.getClassName()).newInstance();
                        var2_7 = new ComponentName(var4_13.packageName, var4_13.name);
                        this.mInstrumentation.init(this, (Context)var7_17, var9_19, (ComponentName)var2_7, var1_1.instrumentationWatcher, var1_1.instrumentationUiAutomationConnection);
                        if (this.mProfiler.profileFile == null || var4_13.handleProfiling || this.mProfiler.profileFd != null) break block49;
                        var2_7 = this.mProfiler;
                        var2_7.handlingProfiling = true;
                        var2_7 = new File(var2_7.profileFile);
                        var2_7.getParentFile().mkdirs();
                    }
                    catch (Exception var2_11) {
                        var4_13 = new StringBuilder();
                        var4_13.append("Unable to instantiate instrumentation ");
                        var4_13.append(var1_1.instrumentationName);
                        var4_13.append(": ");
                        var4_13.append(var2_11.toString());
                        throw new RuntimeException(var4_13.toString(), var2_11);
                    }
                    Debug.startMethodTracing(var2_7.toString(), 8388608);
                    break block49;
                }
                this.mInstrumentation = new Instrumentation();
                this.mInstrumentation.basicInit(this);
            }
            if ((var1_1.appInfo.flags & 1048576) != 0) {
                VMRuntime.getRuntime().clearGrowthLimit();
            } else {
                VMRuntime.getRuntime().clampGrowthLimit();
            }
            var2_7 = StrictMode.allowThreadDiskWrites();
            var4_13 = StrictMode.getThreadPolicy();
            var7_17 = var1_1.info.makeApplication(var1_1.restrictedBackupMode, null);
            var7_17.setAutofillOptions(var1_1.autofillOptions);
            var7_17.setContentCaptureOptions(var1_1.contentCaptureOptions);
            this.mInitialApplication = var7_17;
            if (!var1_1.restrictedBackupMode && !ArrayUtils.isEmpty(var1_1.providers)) {
                this.installContentProviders((Context)var7_17, var1_1.providers);
            }
            try {
                this.mInstrumentation.onCreate(var1_1.instrumentationArgs);
            }
            catch (Exception var9_21) {
                var7_17 = new StringBuilder();
                var7_17.append("Exception thrown in onCreate() of ");
                var7_17.append(var1_1.instrumentationName);
                var7_17.append(": ");
                var7_17.append(var9_21.toString());
                var6_15 = new RuntimeException(var7_17.toString(), var9_21);
                throw var6_15;
            }
            try {
                this.mInstrumentation.callApplicationOnCreate((Application)var7_17);
            }
            catch (Exception var9_20) {
                var3_12 = this.mInstrumentation.onException(var7_17, var9_20);
                if (!var3_12) break block50;
            }
            FontsContract.setApplicationContextForResources((Context)var6_15);
            if (Process.isIsolated() != false) return;
            try {
                var2_7 = ActivityThread.getPackageManager().getApplicationInfo(var1_1.appInfo.packageName, 128, UserHandle.myUserId());
                if (var2_7.metaData == null) return;
                var5_14 = var2_7.metaData.getInt("preloaded_fonts", 0);
                if (var5_14 == 0) return;
                var1_1.info.getResources().preloadFonts(var5_14);
                return;
            }
            catch (RemoteException var1_6) {
                throw var1_6.rethrowFromSystemServer();
            }
        }
        var6_15 = new StringBuilder();
        var6_15.append("Unable to create application ");
        var6_15.append(var7_17.getClass().getName());
        var6_15.append(": ");
        var6_15.append(var9_20.toString());
        var8_18 = new RuntimeException(var6_15.toString(), var9_20);
        throw var8_18;
        finally {
            if (var1_1.appInfo.targetSdkVersion < 27 || StrictMode.getThreadPolicy().equals(var4_13)) {
                StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)var2_7);
            }
        }
    }

    private void handleBindService(BindServiceData bindServiceData) {
        block8 : {
            Service service = this.mServices.get(bindServiceData.token);
            if (service != null) {
                bindServiceData.intent.setExtrasClassLoader(service.getClassLoader());
                bindServiceData.intent.prepareToEnterProcess();
                try {
                    if (!bindServiceData.rebind) {
                        IBinder iBinder = service.onBind(bindServiceData.intent);
                        ActivityManager.getService().publishService(bindServiceData.token, bindServiceData.intent, iBinder);
                    } else {
                        service.onRebind(bindServiceData.intent);
                        ActivityManager.getService().serviceDoneExecuting(bindServiceData.token, 0, 0, 0);
                    }
                }
                catch (RemoteException remoteException) {
                    try {
                        throw remoteException.rethrowFromSystemServer();
                    }
                    catch (Exception exception) {
                        if (this.mInstrumentation.onException(service, exception)) break block8;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unable to bind to service ");
                        stringBuilder.append(service);
                        stringBuilder.append(" with ");
                        stringBuilder.append(bindServiceData.intent);
                        stringBuilder.append(": ");
                        stringBuilder.append(exception.toString());
                        throw new RuntimeException(stringBuilder.toString(), exception);
                    }
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void handleConfigurationChanged(Configuration configuration, CompatibilityInfo object) {
        int n;
        boolean bl;
        Object object2;
        Resources.Theme theme = this.getSystemContext().getTheme();
        Resources.Theme theme2 = this.getSystemUiContext().getTheme();
        ResourcesManager resourcesManager = this.mResourcesManager;
        synchronized (resourcesManager) {
            object2 = configuration;
            if (this.mPendingConfiguration != null) {
                object2 = configuration;
                if (!this.mPendingConfiguration.isOtherSeqNewer(configuration)) {
                    object2 = this.mPendingConfiguration;
                    this.mCurDefaultDisplayDpi = ((Configuration)object2).densityDpi;
                    this.updateDefaultDensity();
                }
                this.mPendingConfiguration = null;
            }
            if (object2 == null) {
                return;
            }
            bl = this.mConfiguration != null && this.mConfiguration.diffPublicOnly((Configuration)object2) == 0;
            this.mResourcesManager.applyConfigurationToResourcesLocked((Configuration)object2, (CompatibilityInfo)object);
            this.updateLocaleListFromAppContext(this.mInitialApplication.getApplicationContext(), this.mResourcesManager.getConfiguration().getLocales());
            if (this.mConfiguration == null) {
                this.mConfiguration = configuration = new Configuration();
            }
            if (!this.mConfiguration.isOtherSeqNewer((Configuration)object2) && object == null) {
                return;
            }
            n = this.mConfiguration.updateFrom((Configuration)object2);
            configuration = this.applyCompatConfiguration(this.mCurDefaultDisplayDpi);
            if ((theme.getChangingConfigurations() & n) != 0) {
                theme.rebase();
            }
            if ((theme2.getChangingConfigurations() & n) != 0) {
                theme2.rebase();
            }
        }
        object = this.collectComponentCallbacks(false, configuration);
        ActivityThread.freeTextLayoutCachesIfNeeded(n);
        if (object != null) {
            int n2 = ((ArrayList)object).size();
            for (n = 0; n < n2; ++n) {
                object2 = (ComponentCallbacks2)((ArrayList)object).get(n);
                if (object2 instanceof Activity) {
                    object2 = (Activity)object2;
                    this.performConfigurationChangedForActivity(this.mActivities.get(((Activity)object2).getActivityToken()), configuration);
                    continue;
                }
                if (bl) continue;
                this.performConfigurationChanged((ComponentCallbacks2)object2, configuration);
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void handleCreateBackupAgent(CreateBackupAgentData var1_1) {
        block18 : {
            block16 : {
                block17 : {
                    block15 : {
                        var2_5 = ActivityThread.getPackageManager().getPackageInfo((String)var1_1.appInfo.packageName, (int)0, (int)UserHandle.myUserId()).applicationInfo.uid;
                        var3_6 = Process.myUid();
                        if (var2_5 != var3_6) {
                            var4_7 = new StringBuilder();
                            var4_7.append("Asked to instantiate non-matching package ");
                            var4_7.append(var1_1.appInfo.packageName);
                            Slog.w("ActivityThread", var4_7.toString());
                            return;
                        }
                        this.unscheduleGcIdler();
                        var5_9 = this.getPackageInfoNoCheck(var1_1.appInfo, var1_1.compatInfo);
                        var6_10 = var5_9.mPackageName;
                        if (var6_10 != null) break block15;
                        Slog.d("ActivityThread", "Asked to create backup agent for nonexistent package");
                        return;
                    }
                    var4_8 = var1_1.appInfo.backupAgentName;
                    var7_11 = var4_8;
                    if (var4_8 != null) break block16;
                    if (var1_1.backupMode == 1) break block17;
                    var7_11 = var4_8;
                    if (var1_1.backupMode != 3) break block16;
                }
                var7_11 = "android.app.backup.FullBackupAgent";
            }
            var8_12 = null;
            var9_13 = this.getBackupAgentsForUser(var1_1.userId);
            var4_8 = var9_13.get(var6_10);
            if (var4_8 == null) break block18;
            var8_12 = var4_8.onBind();
            ** GOTO lbl78
        }
        var4_8 = var8_12;
        {
            catch (Exception var1_3) {
                var4_8 = new StringBuilder();
                var4_8.append("Unable to create BackupAgent ");
                var4_8.append(var7_11);
                var4_8.append(": ");
                var4_8.append(var1_3.toString());
                throw new RuntimeException(var4_8.toString(), var1_3);
            }
        }
        try {
            var10_15 = (BackupAgent)var5_9.getClassLoader().loadClass(var7_11).newInstance();
            var4_8 = var8_12;
            var5_9 = ContextImpl.createAppContext(this, (LoadedApk)var5_9);
            var4_8 = var8_12;
            var5_9.setOuterContext(var10_15);
            var4_8 = var8_12;
            var10_15.attach((Context)var5_9);
            var4_8 = var8_12;
            var10_15.onCreate(UserHandle.of(var1_1.userId));
            var4_8 = var8_12;
            var4_8 = var8_12 = var10_15.onBind();
            var9_13.put(var6_10, var10_15);
            ** GOTO lbl78
        }
        catch (Exception var9_14) {
            var8_12 = new StringBuilder();
            var8_12.append("Agent threw during creation: ");
            var8_12.append(var9_14);
            Slog.e("ActivityThread", var8_12.toString());
            var8_12 = var4_8;
            if (var1_1.backupMode != 2) {
                if (var1_1.backupMode != 3) throw var9_14;
                var8_12 = var4_8;
            }
lbl78: // 5 sources:
            try {
                ActivityManager.getService().backupAgentCreated(var6_10, (IBinder)var8_12, var1_1.userId);
                return;
            }
            catch (RemoteException var1_2) {
                throw var1_2.rethrowFromSystemServer();
            }
        }
        catch (RemoteException var1_4) {
            throw var1_4.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    private void handleCreateService(CreateServiceData createServiceData) {
        Exception exception3;
        Object object;
        block7 : {
            Exception exception22;
            block8 : {
                Object object2;
                this.unscheduleGcIdler();
                Object object3 = this.getPackageInfoNoCheck(createServiceData.info.applicationInfo, createServiceData.compatInfo);
                object = null;
                try {
                    object2 = ((LoadedApk)object3).getClassLoader();
                    object = object2 = ((LoadedApk)object3).getAppFactory().instantiateService((ClassLoader)object2, createServiceData.info.name, createServiceData.intent);
                }
                catch (Exception exception3) {
                    if (!this.mInstrumentation.onException(null, exception3)) break block7;
                }
                object2 = ContextImpl.createAppContext(this, (LoadedApk)object3);
                ((ContextImpl)object2).setOuterContext((Context)object);
                object3 = ((LoadedApk)object3).makeApplication(false, this.mInstrumentation);
                ((Service)object).attach((Context)object2, this, createServiceData.info.name, createServiceData.token, (Application)object3, ActivityManager.getService());
                ((Service)object).onCreate();
                this.mServices.put(createServiceData.token, (Service)object);
                try {
                    ActivityManager.getService().serviceDoneExecuting(createServiceData.token, 0, 0, 0);
                }
                catch (RemoteException remoteException) {
                    try {
                        throw remoteException.rethrowFromSystemServer();
                    }
                    catch (Exception exception22) {
                        if (!this.mInstrumentation.onException(object, exception22)) break block8;
                    }
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to create service ");
            ((StringBuilder)object).append(createServiceData.info.name);
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(exception22.toString());
            throw new RuntimeException(((StringBuilder)object).toString(), exception22);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to instantiate service ");
        ((StringBuilder)object).append(createServiceData.info.name);
        ((StringBuilder)object).append(": ");
        ((StringBuilder)object).append(exception3.toString());
        throw new RuntimeException(((StringBuilder)object).toString(), exception3);
    }

    private void handleDestroyBackupAgent(CreateBackupAgentData createBackupAgentData) {
        String string2 = this.getPackageInfoNoCheck((ApplicationInfo)createBackupAgentData.appInfo, (CompatibilityInfo)createBackupAgentData.compatInfo).mPackageName;
        ArrayMap<String, BackupAgent> arrayMap = this.getBackupAgentsForUser(createBackupAgentData.userId);
        BackupAgent backupAgent = arrayMap.get(string2);
        if (backupAgent != null) {
            try {
                backupAgent.onDestroy();
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception thrown in onDestroy by backup agent of ");
                stringBuilder.append(createBackupAgentData.appInfo);
                Slog.w(TAG, stringBuilder.toString());
                exception.printStackTrace();
            }
            arrayMap.remove(string2);
        } else {
            arrayMap = new StringBuilder();
            ((StringBuilder)((Object)arrayMap)).append("Attempt to destroy unknown backup agent ");
            ((StringBuilder)((Object)arrayMap)).append(createBackupAgentData);
            Slog.w(TAG, ((StringBuilder)((Object)arrayMap)).toString());
        }
    }

    private void handleDumpActivity(DumpComponentInfo dumpComponentInfo) {
        StrictMode.ThreadPolicy threadPolicy;
        block4 : {
            threadPolicy = StrictMode.allowThreadDiskWrites();
            ActivityClientRecord activityClientRecord = this.mActivities.get(dumpComponentInfo.token);
            if (activityClientRecord == null) break block4;
            if (activityClientRecord.activity == null) break block4;
            FileOutputStream fileOutputStream = new FileOutputStream(dumpComponentInfo.fd.getFileDescriptor());
            FastPrintWriter fastPrintWriter = new FastPrintWriter(fileOutputStream);
            activityClientRecord.activity.dump(dumpComponentInfo.prefix, dumpComponentInfo.fd.getFileDescriptor(), fastPrintWriter, dumpComponentInfo.args);
            ((PrintWriter)fastPrintWriter).flush();
        }
        return;
        finally {
            IoUtils.closeQuietly((AutoCloseable)dumpComponentInfo.fd);
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static void handleDumpHeap(DumpHeapData dumpHeapData) {
        block19 : {
            if (dumpHeapData.runGc) {
                System.gc();
                System.runFinalization();
                System.gc();
            }
            ParcelFileDescriptor parcelFileDescriptor = dumpHeapData.fd;
            if (dumpHeapData.managed) {
                Debug.dumpHprofData(dumpHeapData.path, parcelFileDescriptor.getFileDescriptor());
            } else if (dumpHeapData.mallocInfo) {
                Debug.dumpNativeMallocInfo(parcelFileDescriptor.getFileDescriptor());
            } else {
                Debug.dumpNativeHeap(parcelFileDescriptor.getFileDescriptor());
            }
            if (parcelFileDescriptor == null) break block19;
            parcelFileDescriptor.close();
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (parcelFileDescriptor == null) throw throwable2;
                    try {
                        parcelFileDescriptor.close();
                        throw throwable2;
                    }
                    catch (Throwable throwable3) {
                        try {
                            throwable.addSuppressed(throwable3);
                            throw throwable2;
                        }
                        catch (RuntimeException runtimeException) {
                            Slog.wtf(TAG, "Heap dumper threw a runtime exception", runtimeException);
                        }
                        catch (IOException iOException) {
                            if (dumpHeapData.managed) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Managed heap dump failed on path ");
                                stringBuilder.append(dumpHeapData.path);
                                stringBuilder.append(" -- can the process access this path?");
                                Slog.w(TAG, stringBuilder.toString(), iOException);
                                break block19;
                            }
                            Slog.w(TAG, "Failed to dump heap", iOException);
                        }
                    }
                }
            }
        }
        try {
            ActivityManager.getService().dumpHeapFinished(dumpHeapData.path);
            if (dumpHeapData.finishCallback == null) return;
            dumpHeapData.finishCallback.sendResult(null);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private void handleDumpProvider(DumpComponentInfo dumpComponentInfo) {
        StrictMode.ThreadPolicy threadPolicy;
        block4 : {
            threadPolicy = StrictMode.allowThreadDiskWrites();
            ProviderClientRecord providerClientRecord = this.mLocalProviders.get(dumpComponentInfo.token);
            if (providerClientRecord == null) break block4;
            if (providerClientRecord.mLocalProvider == null) break block4;
            FileOutputStream fileOutputStream = new FileOutputStream(dumpComponentInfo.fd.getFileDescriptor());
            FastPrintWriter fastPrintWriter = new FastPrintWriter(fileOutputStream);
            providerClientRecord.mLocalProvider.dump(dumpComponentInfo.fd.getFileDescriptor(), fastPrintWriter, dumpComponentInfo.args);
            ((PrintWriter)fastPrintWriter).flush();
        }
        return;
        finally {
            IoUtils.closeQuietly((AutoCloseable)dumpComponentInfo.fd);
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    private void handleDumpService(DumpComponentInfo dumpComponentInfo) {
        StrictMode.ThreadPolicy threadPolicy;
        block4 : {
            threadPolicy = StrictMode.allowThreadDiskWrites();
            Service service = this.mServices.get(dumpComponentInfo.token);
            if (service == null) break block4;
            FileOutputStream fileOutputStream = new FileOutputStream(dumpComponentInfo.fd.getFileDescriptor());
            FastPrintWriter fastPrintWriter = new FastPrintWriter(fileOutputStream);
            service.dump(dumpComponentInfo.fd.getFileDescriptor(), fastPrintWriter, dumpComponentInfo.args);
            ((PrintWriter)fastPrintWriter).flush();
        }
        return;
        finally {
            IoUtils.closeQuietly((AutoCloseable)dumpComponentInfo.fd);
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    private void handleEnterAnimationComplete(IBinder object) {
        if ((object = this.mActivities.get(object)) != null) {
            ((ActivityClientRecord)object).activity.dispatchEnterAnimationComplete();
        }
    }

    private void handleLocalVoiceInteractionStarted(IBinder object, IVoiceInteractor iVoiceInteractor) {
        if ((object = this.mActivities.get(object)) != null) {
            ((ActivityClientRecord)object).voiceInteractor = iVoiceInteractor;
            ((ActivityClientRecord)object).activity.setVoiceInteractor(iVoiceInteractor);
            if (iVoiceInteractor == null) {
                ((ActivityClientRecord)object).activity.onLocalVoiceInteractionStopped();
            } else {
                ((ActivityClientRecord)object).activity.onLocalVoiceInteractionStarted();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handlePerformDirectAction(IBinder object, String string2, Bundle object2, CancellationSignal cancellationSignal, RemoteCallback remoteCallback) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(object);
        if (activityClientRecord == null) {
            remoteCallback.sendResult(null);
            return;
        }
        int n = activityClientRecord.getLifecycleState();
        if (n >= 2 && n < 5) {
            object = object2 != null ? object2 : Bundle.EMPTY;
            object2 = activityClientRecord.activity;
            Objects.requireNonNull(remoteCallback);
            ((Activity)object2).onPerformDirectAction(string2, (Bundle)object, cancellationSignal, new _$$Lambda$ZsFzoG2loyqNOR2cNbo_thrNK5c(remoteCallback));
            return;
        }
        remoteCallback.sendResult(null);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private void handleReceiver(ReceiverData object) {
        Throwable throwable2222;
        String string2;
        Object object2;
        Exception exception2222;
        IActivityManager iActivityManager;
        block8 : {
            this.unscheduleGcIdler();
            string2 = ((ReceiverData)object).intent.getComponent().getClassName();
            LoadedApk loadedApk = this.getPackageInfoNoCheck(object.info.applicationInfo, ((ReceiverData)object).compatInfo);
            iActivityManager = ActivityManager.getService();
            object2 = (ContextImpl)loadedApk.makeApplication(false, this.mInstrumentation).getBaseContext();
            ContextImpl contextImpl = object2;
            if (object.info.splitName != null) {
                contextImpl = (ContextImpl)((ContextImpl)object2).createContextForSplit(object.info.splitName);
            }
            object2 = contextImpl.getClassLoader();
            ((ReceiverData)object).intent.setExtrasClassLoader((ClassLoader)object2);
            ((ReceiverData)object).intent.prepareToEnterProcess();
            ((BroadcastReceiver.PendingResult)object).setExtrasClassLoader((ClassLoader)object2);
            object2 = loadedApk.getAppFactory().instantiateReceiver((ClassLoader)object2, object.info.name, ((ReceiverData)object).intent);
            try {
                try {
                    sCurrentBroadcastIntent.set(((ReceiverData)object).intent);
                    ((BroadcastReceiver)object2).setPendingResult((BroadcastReceiver.PendingResult)object);
                    ((BroadcastReceiver)object2).onReceive(contextImpl.getReceiverRestrictedContext(), ((ReceiverData)object).intent);
                }
                catch (Exception exception2222) {
                    ((BroadcastReceiver.PendingResult)object).sendFinished(iActivityManager);
                    boolean bl = this.mInstrumentation.onException(object2, exception2222);
                    if (!bl) break block8;
                }
                sCurrentBroadcastIntent.set(null);
                if (((BroadcastReceiver)object2).getPendingResult() == null) return;
            }
            catch (Throwable throwable2222) {}
            ((BroadcastReceiver.PendingResult)object).finish();
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Unable to start receiver ");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(": ");
        ((StringBuilder)object2).append(exception2222.toString());
        object = new RuntimeException(((StringBuilder)object2).toString(), exception2222);
        throw object;
        sCurrentBroadcastIntent.set(null);
        throw throwable2222;
        catch (Exception exception3) {
            ((BroadcastReceiver.PendingResult)object).sendFinished(iActivityManager);
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to instantiate receiver ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(exception3.toString());
            throw new RuntimeException(((StringBuilder)object).toString(), exception3);
        }
    }

    private void handleRelaunchActivityInner(ActivityClientRecord activityClientRecord, int n, List<ResultInfo> list, List<ReferrerIntent> list2, PendingTransactionActions pendingTransactionActions, boolean bl, Configuration configuration, String string2) {
        Intent intent = activityClientRecord.activity.mIntent;
        if (!activityClientRecord.paused) {
            this.performPauseActivity(activityClientRecord, false, string2, null);
        }
        if (!activityClientRecord.stopped) {
            this.callActivityOnStop(activityClientRecord, true, string2);
        }
        this.handleDestroyActivity(activityClientRecord.token, false, n, true, string2);
        activityClientRecord.activity = null;
        activityClientRecord.window = null;
        activityClientRecord.hideForNow = false;
        activityClientRecord.nextIdle = null;
        if (list != null) {
            if (activityClientRecord.pendingResults == null) {
                activityClientRecord.pendingResults = list;
            } else {
                activityClientRecord.pendingResults.addAll(list);
            }
        }
        if (list2 != null) {
            if (activityClientRecord.pendingIntents == null) {
                activityClientRecord.pendingIntents = list2;
            } else {
                activityClientRecord.pendingIntents.addAll(list2);
            }
        }
        activityClientRecord.startsNotResumed = bl;
        activityClientRecord.overrideConfig = configuration;
        this.handleLaunchActivity(activityClientRecord, pendingTransactionActions, intent);
    }

    private void handleRelaunchActivityLocally(IBinder object) {
        Object object2 = this.mActivities.get(object);
        if (object2 == null) {
            Log.w(TAG, "Activity to relaunch no longer exists");
            return;
        }
        int n = ((ActivityClientRecord)object2).getLifecycleState();
        if (n >= 3 && n <= 5) {
            object = ((ActivityClientRecord)object2).createdConfig != null ? ((ActivityClientRecord)object2).createdConfig : this.mConfiguration;
            object = ActivityRelaunchItem.obtain(null, null, 0, new MergedConfiguration((Configuration)object, ((ActivityClientRecord)object2).overrideConfig), ((ActivityClientRecord)object2).mPreserveWindow);
            ActivityLifecycleItem activityLifecycleItem = TransactionExecutorHelper.getLifecycleRequestForCurrentState((ActivityClientRecord)object2);
            object2 = ClientTransaction.obtain(this.mAppThread, ((ActivityClientRecord)object2).token);
            ((ClientTransaction)object2).addCallback((ClientTransactionItem)object);
            ((ClientTransaction)object2).setLifecycleStateRequest(activityLifecycleItem);
            this.executeTransaction((ClientTransaction)object2);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Activity state must be in [ON_RESUME..ON_STOP] in order to be relaunched,current state is ");
        ((StringBuilder)object).append(n);
        Log.w(TAG, ((StringBuilder)object).toString());
    }

    private void handleRequestDirectActions(IBinder object, IVoiceInteractor object2, CancellationSignal cancellationSignal, RemoteCallback remoteCallback) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(object);
        if (activityClientRecord == null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("requestDirectActions(): no activity for ");
            ((StringBuilder)object2).append(object);
            Log.w(TAG, ((StringBuilder)object2).toString());
            remoteCallback.sendResult(null);
            return;
        }
        int n = activityClientRecord.getLifecycleState();
        if (n >= 2 && n < 5) {
            if (activityClientRecord.activity.mVoiceInteractor == null || activityClientRecord.activity.mVoiceInteractor.mInteractor.asBinder() != object2.asBinder()) {
                if (activityClientRecord.activity.mVoiceInteractor != null) {
                    activityClientRecord.activity.mVoiceInteractor.destroy();
                }
                activityClientRecord.activity.mVoiceInteractor = new VoiceInteractor((IVoiceInteractor)object2, activityClientRecord.activity, activityClientRecord.activity, Looper.myLooper());
            }
            activityClientRecord.activity.onGetDirectActions(cancellationSignal, new _$$Lambda$ActivityThread$FmvGY8exyv0L0oqZrnunpl8OFI8(activityClientRecord, remoteCallback));
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("requestDirectActions(");
        ((StringBuilder)object).append(activityClientRecord);
        ((StringBuilder)object).append("): wrong lifecycle: ");
        ((StringBuilder)object).append(n);
        Log.w(TAG, ((StringBuilder)object).toString());
        remoteCallback.sendResult(null);
    }

    private void handleRunIsolatedEntryPoint(String string2, String[] arrstring) {
        try {
            Class.forName(string2).getMethod("main", String[].class).invoke(null, new Object[]{arrstring});
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new AndroidRuntimeException("runIsolatedEntryPoint failed", reflectiveOperationException);
        }
        System.exit(0);
    }

    private void handleServiceArgs(ServiceArgsData serviceArgsData) {
        block10 : {
            Service service = this.mServices.get(serviceArgsData.token);
            if (service != null) {
                int n;
                block9 : {
                    if (serviceArgsData.args != null) {
                        serviceArgsData.args.setExtrasClassLoader(service.getClassLoader());
                        serviceArgsData.args.prepareToEnterProcess();
                    }
                    if (!serviceArgsData.taskRemoved) {
                        n = service.onStartCommand(serviceArgsData.args, serviceArgsData.flags, serviceArgsData.startId);
                        break block9;
                    }
                    service.onTaskRemoved(serviceArgsData.args);
                    n = 1000;
                }
                QueuedWork.waitToFinish();
                try {
                    ActivityManager.getService().serviceDoneExecuting(serviceArgsData.token, 1, serviceArgsData.startId, n);
                }
                catch (RemoteException remoteException) {
                    try {
                        throw remoteException.rethrowFromSystemServer();
                    }
                    catch (Exception exception) {
                        if (this.mInstrumentation.onException(service, exception)) break block10;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unable to start service ");
                        stringBuilder.append(service);
                        stringBuilder.append(" with ");
                        stringBuilder.append(serviceArgsData.args);
                        stringBuilder.append(": ");
                        stringBuilder.append(exception.toString());
                        throw new RuntimeException(stringBuilder.toString(), exception);
                    }
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void handleSetCoreSettings(Bundle bundle) {
        ResourcesManager resourcesManager = this.mResourcesManager;
        synchronized (resourcesManager) {
            this.mCoreSettings = bundle;
        }
        this.onCoreSettingsChange();
    }

    private void handleSleeping(IBinder iBinder, boolean bl) {
        Object object = this.mActivities.get(iBinder);
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("handleSleeping: no activity for token ");
            ((StringBuilder)object).append(iBinder);
            Log.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        if (bl) {
            if (!((ActivityClientRecord)object).stopped && !((ActivityClientRecord)object).isPreHoneycomb()) {
                this.callActivityOnStop((ActivityClientRecord)object, true, "sleeping");
            }
            if (!((ActivityClientRecord)object).isPreHoneycomb()) {
                QueuedWork.waitToFinish();
            }
            try {
                ActivityTaskManager.getService().activitySlept(((ActivityClientRecord)object).token);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        } else if (((ActivityClientRecord)object).stopped && object.activity.mVisibleFromServer) {
            ((ActivityClientRecord)object).activity.performRestart(true, "handleSleeping");
            ((ActivityClientRecord)object).setState(2);
        }
    }

    private void handleStartBinderTracking() {
        Binder.enableTracing();
    }

    private void handleStopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) {
        try {
            Binder.disableTracing();
            Binder.getTransactionTracker().writeTracesToFile(parcelFileDescriptor);
            return;
        }
        finally {
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            Binder.getTransactionTracker().clearTraces();
        }
    }

    private void handleStopService(IBinder object) {
        block8 : {
            Object object2 = this.mServices.remove(object);
            if (object2 != null) {
                ((Service)object2).onDestroy();
                ((Service)object2).detachAndCleanUp();
                Context context = ((ContextWrapper)object2).getBaseContext();
                if (context instanceof ContextImpl) {
                    String string2 = ((Service)object2).getClassName();
                    ((ContextImpl)context).scheduleFinalCleanup(string2, "Service");
                }
                QueuedWork.waitToFinish();
                try {
                    ActivityManager.getService().serviceDoneExecuting((IBinder)object, 2, 0, 0);
                    break block8;
                }
                catch (RemoteException remoteException) {
                    try {
                        throw remoteException.rethrowFromSystemServer();
                    }
                    catch (Exception exception) {
                        if (this.mInstrumentation.onException(object2, exception)) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("handleStopService: exception for ");
                            ((StringBuilder)object2).append(object);
                            Slog.i(TAG, ((StringBuilder)object2).toString(), exception);
                            break block8;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unable to stop service ");
                        ((StringBuilder)object).append(object2);
                        ((StringBuilder)object).append(": ");
                        ((StringBuilder)object).append(exception.toString());
                        throw new RuntimeException(((StringBuilder)object).toString(), exception);
                    }
                }
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("handleStopService: token=");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(" not found.");
            Slog.i(TAG, ((StringBuilder)object2).toString());
        }
    }

    private void handleTrimMemory(int n) {
        Trace.traceBegin(64L, "trimMemory");
        ArrayList<ComponentCallbacks2> arrayList = this.collectComponentCallbacks(true, null);
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            arrayList.get(i).onTrimMemory(n);
        }
        WindowManagerGlobal.getInstance().trimMemory(n);
        Trace.traceEnd(64L);
        if (SystemProperties.getInt("debug.am.run_gc_trim_level", Integer.MAX_VALUE) <= n) {
            this.unscheduleGcIdler();
            this.doGcIfNeeded("tm");
        }
        if (SystemProperties.getInt("debug.am.run_mallopt_trim_level", Integer.MAX_VALUE) <= n) {
            this.unschedulePurgeIdler();
            this.purgePendingResources();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void handleUnbindService(BindServiceData var1_1) {
        var2_2 = this.mServices.get(var1_1.token);
        if (var2_2 == null) return;
        try {
            var1_1.intent.setExtrasClassLoader(var2_2.getClassLoader());
            var1_1.intent.prepareToEnterProcess();
            var3_3 = var2_2.onUnbind(var1_1.intent);
            if (!var3_3) ** GOTO lbl11
            try {
                ActivityManager.getService().unbindFinished(var1_1.token, var1_1.intent, var3_3);
                return;
lbl11: // 1 sources:
                ActivityManager.getService().serviceDoneExecuting(var1_1.token, 0, 0, 0);
                return;
            }
            catch (RemoteException var4_4) {
                throw var4_4.rethrowFromSystemServer();
            }
        }
        catch (Exception var5_6) {
            if (this.mInstrumentation.onException(var2_2, var5_6)) {
                return;
            }
            var4_5 = new StringBuilder();
            var4_5.append("Unable to unbind to service ");
            var4_5.append(var2_2);
            var4_5.append(" with ");
            var4_5.append(var1_1.intent);
            var4_5.append(": ");
            var4_5.append(var5_6.toString());
            throw new RuntimeException(var4_5.toString(), var5_6);
        }
    }

    private void handleUpdatePackageCompatibilityInfo(UpdateCompatibilityData updateCompatibilityData) {
        LoadedApk loadedApk = this.peekPackageInfo(updateCompatibilityData.pkg, false);
        if (loadedApk != null) {
            loadedApk.setCompatibilityInfo(updateCompatibilityData.info);
        }
        if ((loadedApk = this.peekPackageInfo(updateCompatibilityData.pkg, true)) != null) {
            loadedApk.setCompatibilityInfo(updateCompatibilityData.info);
        }
        this.handleConfigurationChanged(this.mConfiguration, updateCompatibilityData.info);
        WindowManagerGlobal.getInstance().reportNewConfiguration(this.mConfiguration);
    }

    private final void incProviderRefLocked(ProviderRefCount providerRefCount, boolean bl) {
        if (bl) {
            ++providerRefCount.stableCount;
            if (providerRefCount.stableCount == 1) {
                int n;
                if (providerRefCount.removePending) {
                    n = -1;
                    providerRefCount.removePending = false;
                    this.mH.removeMessages(131, providerRefCount);
                } else {
                    n = 0;
                }
                try {
                    ActivityManager.getService().refContentProvider(providerRefCount.holder.connection, 1, n);
                }
                catch (RemoteException remoteException) {}
            }
        } else {
            ++providerRefCount.unstableCount;
            if (providerRefCount.unstableCount == 1) {
                if (providerRefCount.removePending) {
                    providerRefCount.removePending = false;
                    this.mH.removeMessages(131, providerRefCount);
                } else {
                    try {
                        ActivityManager.getService().refContentProvider(providerRefCount.holder.connection, 0, 1);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            }
        }
    }

    @UnsupportedAppUsage
    private void installContentProviders(Context context, List<ProviderInfo> object) {
        ArrayList<ContentProviderHolder> arrayList = new ArrayList<ContentProviderHolder>();
        object = object.iterator();
        while (object.hasNext()) {
            ContentProviderHolder contentProviderHolder = this.installProvider(context, null, (ProviderInfo)object.next(), false, true, true);
            if (contentProviderHolder == null) continue;
            contentProviderHolder.noReleaseNeeded = true;
            arrayList.add(contentProviderHolder);
        }
        try {
            ActivityManager.getService().publishContentProviders(this.getApplicationThread(), arrayList);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private ContentProviderHolder installProvider(Context object, ContentProviderHolder object2, ProviderInfo object3, boolean bl, boolean bl2, boolean bl3) {
        void var5_11;
        Object object5;
        void var6_12;
        Object object6;
        Object object7;
        Object object4;
        Object object8;
        if (object4 != null && ((ContentProviderHolder)object4).provider != null) {
            object = ((ContentProviderHolder)object4).provider;
            object6 = null;
        } else {
            block32 : {
                void var4_10;
                if (var4_10 != false) {
                    object6 = new StringBuilder();
                    ((StringBuilder)object6).append("Loading provider ");
                    ((StringBuilder)object6).append(((ProviderInfo)object8).authority);
                    ((StringBuilder)object6).append(": ");
                    ((StringBuilder)object6).append(((ProviderInfo)object8).name);
                    Slog.d(TAG, ((StringBuilder)object6).toString());
                }
                object6 = null;
                object5 = ((ProviderInfo)object8).applicationInfo;
                if (!((Context)object).getPackageName().equals(((ApplicationInfo)object5).packageName)) {
                    object7 = this.mInitialApplication;
                    if (object7 != null && ((ContextWrapper)object7).getPackageName().equals(((ApplicationInfo)object5).packageName)) {
                        object = this.mInitialApplication;
                    } else {
                        object7 = ((ApplicationInfo)object5).packageName;
                        try {
                            object = ((Context)object).createPackageContext((String)object7, 1);
                        }
                        catch (PackageManager.NameNotFoundException nameNotFoundException) {
                            object = object6;
                        }
                    }
                    break block32;
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        object = object6;
                    }
                }
            }
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to get context for package ");
                ((StringBuilder)object).append(((ApplicationInfo)object5).packageName);
                ((StringBuilder)object).append(" while loading content provider ");
                ((StringBuilder)object).append(((ProviderInfo)object8).name);
                Slog.w(TAG, ((StringBuilder)object).toString());
                return null;
            }
            object6 = object;
            if (((ProviderInfo)object8).splitName != null) {
                try {
                    object6 = ((Context)object).createContextForSplit(((ProviderInfo)object8).splitName);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    throw new RuntimeException(nameNotFoundException);
                }
            }
            object7 = ((Context)object6).getClassLoader();
            object = object5 = this.peekPackageInfo(((ApplicationInfo)object5).packageName, true);
            if (object5 == null) {
                object = this.getSystemContext().mPackageInfo;
            }
            object5 = ((LoadedApk)object).getAppFactory().instantiateProvider((ClassLoader)object7, ((ProviderInfo)object8).name);
            object = ((ContentProvider)object5).getIContentProvider();
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to instantiate class ");
                ((StringBuilder)object).append(((ProviderInfo)object8).name);
                ((StringBuilder)object).append(" from sourceDir ");
                ((StringBuilder)object).append(object8.applicationInfo.sourceDir);
                Slog.e(TAG, ((StringBuilder)object).toString());
                return null;
            }
            ((ContentProvider)object5).attachInfo((Context)object6, (ProviderInfo)object8);
            object6 = object5;
        }
        object5 = this.mProviderMap;
        // MONITORENTER : object5
        object7 = object.asBinder();
        if (object6 != null) {
            ComponentName componentName = new ComponentName(((ProviderInfo)object8).packageName, ((ProviderInfo)object8).name);
            object4 = this.mLocalProvidersByName.get(componentName);
            if (object4 != null) {
                object = ((ProviderClientRecord)object4).mProvider;
                object = object4;
                return ((ProviderClientRecord)object).mHolder;
            }
            object4 = new ContentProviderHolder((ProviderInfo)object8);
            ((ContentProviderHolder)object4).provider = object;
            ((ContentProviderHolder)object4).noReleaseNeeded = true;
            object = this.installProviderAuthoritiesLocked((IContentProvider)object, (ContentProvider)object6, (ContentProviderHolder)object4);
            this.mLocalProviders.put((IBinder)object7, (ProviderClientRecord)object);
            this.mLocalProvidersByName.put(componentName, (ProviderClientRecord)object);
            return ((ProviderClientRecord)object).mHolder;
        }
        object8 = this.mProviderRefCountMap.get(object7);
        if (object8 != null) {
            object = object8;
            if (var5_11 == false) {
                this.incProviderRefLocked((ProviderRefCount)object8, (boolean)var6_12);
                try {
                    ActivityManager.getService().removeContentProvider(((ContentProviderHolder)object4).connection, (boolean)var6_12);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
                object = object8;
            }
        } else {
            object8 = this.installProviderAuthoritiesLocked((IContentProvider)object, (ContentProvider)object6, (ContentProviderHolder)object4);
            object = var5_11 != false ? new ProviderRefCount((ContentProviderHolder)object4, (ProviderClientRecord)object8, 1000, 1000) : (var6_12 != false ? new ProviderRefCount((ContentProviderHolder)object4, (ProviderClientRecord)object8, 1, 0) : new ProviderRefCount((ContentProviderHolder)object4, (ProviderClientRecord)object8, 0, 1));
            this.mProviderRefCountMap.put((IBinder)object7, (ProviderRefCount)object);
        }
        object = ((ProviderRefCount)object).holder;
        // MONITOREXIT : object5
        return object;
        catch (Throwable throwable) {
            throw throwable;
        }
        catch (Exception exception) {
            if (this.mInstrumentation.onException(null, exception)) {
                return null;
            }
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("Unable to get provider ");
            ((StringBuilder)object4).append(((ProviderInfo)object8).name);
            ((StringBuilder)object4).append(": ");
            ((StringBuilder)object4).append(exception.toString());
            throw new RuntimeException(((StringBuilder)object4).toString(), exception);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private ProviderClientRecord installProviderAuthoritiesLocked(IContentProvider var1_1, ContentProvider var2_2, ContentProviderHolder var3_3) {
        block16 : {
            var4_4 = var3_3.info.authority.split(";");
            var5_5 = UserHandle.getUserId(var3_3.info.applicationInfo.uid);
            var6_6 = 0;
            if (var1_1 == null) break block16;
            var7_7 = var4_4.length;
            block12 : for (var8_8 = 0; var8_8 < var7_7; ++var8_8) {
                block17 : {
                    var9_9 = var4_4[var8_8];
                    var10_10 = -1;
                    switch (var9_9.hashCode()) {
                        case 1995645513: {
                            if (!var9_9.equals("com.android.blockednumber")) break;
                            var10_10 = 3;
                            ** break;
                        }
                        case 1312704747: {
                            if (!var9_9.equals("downloads")) break;
                            var10_10 = 5;
                            ** break;
                        }
                        case 783201304: {
                            if (!var9_9.equals("telephony")) break;
                            var10_10 = 6;
                            ** break;
                        }
                        case 63943420: {
                            if (!var9_9.equals("call_log_shadow")) break;
                            var10_10 = 2;
                            ** break;
                        }
                        case -172298781: {
                            if (!var9_9.equals("call_log")) break;
                            var10_10 = 1;
                            ** break;
                        }
                        case -456066902: {
                            if (!var9_9.equals("com.android.calendar")) break;
                            var10_10 = 4;
                            ** break;
                        }
                        case -845193793: {
                            if (!var9_9.equals("com.android.contacts")) break;
                            var10_10 = 0;
                            break block17;
                        }
                    }
                    ** break;
                }
                switch (var10_10) {
                    default: {
                        continue block12;
                    }
                    case 0: 
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: 
                    case 6: 
                }
                Binder.allowBlocking(var1_1.asBinder());
            }
        }
        var2_2 = new ProviderClientRecord(var4_4, (IContentProvider)var1_1, (ContentProvider)var2_2, (ContentProviderHolder)var3_3);
        var8_8 = var4_4.length;
        var10_10 = var6_6;
        while (var10_10 < var8_8) {
            var1_1 = var4_4[var10_10];
            var3_3 = new ProviderKey((String)var1_1, var5_5);
            if (this.mProviderMap.get(var3_3) != null) {
                var3_3 = new StringBuilder();
                var3_3.append("Content provider ");
                var3_3.append(var2_2.mHolder.info.name);
                var3_3.append(" already published as ");
                var3_3.append((String)var1_1);
                Slog.w("ActivityThread", var3_3.toString());
            } else {
                this.mProviderMap.put((ProviderKey)var3_3, (ProviderClientRecord)var2_2);
            }
            ++var10_10;
        }
        return var2_2;
    }

    private static boolean isLoadedApkResourceDirsUpToDate(LoadedApk arrstring, ApplicationInfo arrstring2) {
        Resources resources = arrstring.mResources;
        arrstring = ArrayUtils.defeatNullable(arrstring.getOverlayDirs());
        arrstring2 = ArrayUtils.defeatNullable(arrstring2.resourceDirs);
        boolean bl = (resources == null || resources.getAssets().isUpToDate()) && arrstring.length == arrstring2.length && ArrayUtils.containsAll(arrstring, arrstring2);
        return bl;
    }

    public static boolean isSystem() {
        boolean bl = sCurrentActivityThread != null ? ActivityThread.sCurrentActivityThread.mSystemThread : false;
        return bl;
    }

    public static /* synthetic */ void lambda$A4ykhsPb8qV3ffTqpQDklHSMDJ0(ActivityThread activityThread) {
        activityThread.applyPendingProcessState();
    }

    static /* synthetic */ void lambda$handleRequestDirectActions$0(ActivityClientRecord object, RemoteCallback remoteCallback, List list) {
        Preconditions.checkNotNull(list);
        Preconditions.checkCollectionElementsNotNull(list, "actions");
        if (!list.isEmpty()) {
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                ((DirectAction)list.get(i)).setSource(((ActivityClientRecord)object).activity.getTaskId(), ((ActivityClientRecord)object).activity.getAssistToken());
            }
            object = new Bundle();
            ((Bundle)object).putParcelable("actions_list", new ParceledListSlice(list));
            remoteCallback.sendResult((Bundle)object);
        } else {
            remoteCallback.sendResult(null);
        }
    }

    public static void main(String[] object) {
        long l;
        Trace.traceBegin(64L, "ActivityThreadMain");
        AndroidOs.install();
        CloseGuard.setEnabled((boolean)false);
        Environment.initForCurrentUser();
        TrustedCertificateStore.setDefaultUserDirectory((File)Environment.getUserConfigDirectory(UserHandle.myUserId()));
        Process.setArgV0("<pre-initialized>");
        Looper.prepareMainLooper();
        long l2 = l = 0L;
        if (object != null) {
            int n = ((String[])object).length - 1;
            do {
                l2 = l;
                if (n < 0) break;
                l2 = l;
                if (object[n] != null) {
                    l2 = l;
                    if (((String)object[n]).startsWith(PROC_START_SEQ_IDENT)) {
                        l2 = Long.parseLong(((String)object[n]).substring(PROC_START_SEQ_IDENT.length()));
                    }
                }
                --n;
                l = l2;
            } while (true);
        }
        object = new ActivityThread();
        ActivityThread.super.attach(false, l2);
        if (sMainThreadHandler == null) {
            sMainThreadHandler = ((ActivityThread)object).getHandler();
        }
        Trace.traceEnd(64L);
        Looper.loop();
        throw new RuntimeException("Main thread loop unexpectedly exited");
    }

    private native void nDumpGraphicsInfo(FileDescriptor var1);

    private native void nInitZygoteChildHeapProfiling();

    private native void nPurgePendingResources();

    private void onCoreSettingsChange() {
        if (this.updateDebugViewAttributeState()) {
            this.relaunchAllActivities(false);
        }
    }

    private Configuration performActivityConfigurationChanged(Activity activity, Configuration object, Configuration configuration, int n, boolean bl) {
        block7 : {
            block8 : {
                IBinder iBinder;
                boolean bl2;
                block10 : {
                    int n2;
                    boolean bl3;
                    block11 : {
                        block9 : {
                            if (activity == null) break block7;
                            iBinder = activity.getActivityToken();
                            if (iBinder == null) break block8;
                            bl3 = false;
                            if (activity.mCurrentConfig != null) break block9;
                            bl2 = true;
                            break block10;
                        }
                        n2 = activity.mCurrentConfig.diffPublicOnly((Configuration)object);
                        if (n2 != 0) break block11;
                        bl2 = bl3;
                        if (this.mResourcesManager.isSameResourcesOverrideConfig(iBinder, configuration)) break block10;
                    }
                    bl2 = this.mUpdatingSystemConfig && (activity.mActivityInfo.getRealConfigChanged() & n2) != 0 ? bl3 : true;
                }
                if (!bl2 && !bl) {
                    return null;
                }
                Configuration configuration2 = activity.getOverrideConfiguration();
                configuration = ActivityThread.createNewConfigAndUpdateIfNotNull(configuration, configuration2);
                this.mResourcesManager.updateResourcesForActivity(iBinder, configuration, n, bl);
                activity.mConfigChangeFlags = 0;
                activity.mCurrentConfig = new Configuration((Configuration)object);
                object = ActivityThread.createNewConfigAndUpdateIfNotNull((Configuration)object, configuration2);
                if (bl) {
                    activity.dispatchMovedToDisplay(n, (Configuration)object);
                }
                if (bl2) {
                    activity.mCalled = false;
                    activity.onConfigurationChanged((Configuration)object);
                    if (!activity.mCalled) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Activity ");
                        ((StringBuilder)object).append(activity.getLocalClassName());
                        ((StringBuilder)object).append(" did not call through to super.onConfigurationChanged()");
                        throw new SuperNotCalledException(((StringBuilder)object).toString());
                    }
                }
                return object;
            }
            throw new IllegalArgumentException("Activity token not set. Is the activity attached?");
        }
        throw new IllegalArgumentException("No activity provided.");
    }

    private void performConfigurationChanged(ComponentCallbacks2 componentCallbacks2, Configuration configuration) {
        Configuration configuration2 = null;
        if (componentCallbacks2 instanceof ContextThemeWrapper) {
            configuration2 = ((ContextThemeWrapper)((Object)componentCallbacks2)).getOverrideConfiguration();
        }
        componentCallbacks2.onConfigurationChanged(ActivityThread.createNewConfigAndUpdateIfNotNull(configuration, configuration2));
    }

    private Configuration performConfigurationChangedForActivity(ActivityClientRecord activityClientRecord, Configuration configuration, int n, boolean bl) {
        activityClientRecord.tmpConfig.setTo(configuration);
        if (activityClientRecord.overrideConfig != null) {
            activityClientRecord.tmpConfig.updateFrom(activityClientRecord.overrideConfig);
        }
        configuration = this.performActivityConfigurationChanged(activityClientRecord.activity, activityClientRecord.tmpConfig, activityClientRecord.overrideConfig, n, bl);
        ActivityThread.freeTextLayoutCachesIfNeeded(activityClientRecord.activity.mCurrentConfig.diff(activityClientRecord.tmpConfig));
        return configuration;
    }

    private void performConfigurationChangedForActivity(ActivityClientRecord activityClientRecord, Configuration configuration) {
        this.performConfigurationChangedForActivity(activityClientRecord, configuration, activityClientRecord.activity.getDisplayId(), false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private Activity performLaunchActivity(ActivityClientRecord var1_1, Intent var2_23) {
        block44 : {
            block48 : {
                var3_24 = var1_1.activityInfo;
                if (var1_1.packageInfo == null) {
                    var1_1.packageInfo = this.getPackageInfo(var3_24.applicationInfo, var1_1.compatInfo, 1);
                }
                var4_25 = var1_1.intent.getComponent();
                var3_24 = var4_25;
                if (var4_25 == null) {
                    var3_24 = var1_1.intent.resolveActivity(this.mInitialApplication.getPackageManager());
                    var1_1.intent.setComponent((ComponentName)var3_24);
                }
                var4_25 = var1_1.activityInfo.targetActivity != null ? new ComponentName(var1_1.activityInfo.packageName, var1_1.activityInfo.targetActivity) : var3_24;
                var5_26 = this.createBaseContextForActivity((ActivityClientRecord)var1_1);
                var3_24 = var6_27 = null;
                try {
                    var7_29 = var5_26.getClassLoader();
                    var3_24 = var6_27;
                    var3_24 = var6_27 = this.mInstrumentation.newActivity((ClassLoader)var7_29, var4_25.getClassName(), var1_1.intent);
                    StrictMode.incrementExpectedActivityCount(var6_27.getClass());
                    var3_24 = var6_27;
                    var1_1.intent.setExtrasClassLoader((ClassLoader)var7_29);
                    var3_24 = var6_27;
                    var1_1.intent.prepareToEnterProcess();
                    var3_24 = var6_27;
                    if (var1_1.state != null) {
                        var3_24 = var6_27;
                        var1_1.state.setClassLoader((ClassLoader)var7_29);
                    }
                    var3_24 = var6_27;
                }
                catch (Exception var6_28) {
                    if (!this.mInstrumentation.onException(var3_24, var6_28)) break block44;
                }
                try {
                    block49 : {
                        block47 : {
                            block46 : {
                                block45 : {
                                    var6_27 = var1_1.packageInfo;
                                    try {
                                        var8_30 = var6_27.makeApplication(false, this.mInstrumentation);
                                        if (var3_24 == null) ** GOTO lbl139
                                        var7_29 = var1_1.activityInfo.loadLabel(var5_26.getPackageManager());
                                        var9_31 = new Configuration(this.mCompatConfiguration);
                                        var6_27 = var1_1.overrideConfig;
                                        if (var6_27 == null) break block45;
                                    }
                                    catch (SuperNotCalledException var1_20) {
                                        throw var1_17;
                                    }
                                    var9_31.updateFrom(var1_1.overrideConfig);
                                }
                                var6_27 = var1_1.mPendingRemoveWindow;
                                if (var6_27 == null) break block46;
                                try {
                                    if (!var1_1.mPreserveWindow) break block46;
                                    var6_27 = var1_1.mPendingRemoveWindow;
                                    var1_1.mPendingRemoveWindow = null;
                                    var1_1.mPendingRemoveWindowManager = null;
                                    break block47;
                                }
                                catch (Exception var1_2) {
                                    break block48;
                                }
                                catch (SuperNotCalledException var1_3) {
                                    throw var1_17;
                                }
                            }
                            var6_27 = null;
                        }
                        var5_26.setOuterContext((Context)var3_24);
                        var10_32 = this.getInstrumentation();
                        var11_33 = var1_1.token;
                        var12_34 = var1_1.ident;
                        var13_35 = var1_1.intent;
                        var14_36 = var1_1.activityInfo;
                        var15_37 = var1_1.parent;
                        var16_38 = var1_1.embeddedID;
                        var17_39 = var1_1.lastNonConfigurationInstances;
                        var18_40 = var1_1.referrer;
                        var19_41 = var1_1.voiceInteractor;
                        var20_42 = var1_1.configCallback;
                        var21_43 = var1_1.assistToken;
                        var3_24.attach(var5_26, this, var10_32, var11_33, var12_34, var8_30, var13_35, var14_36, (CharSequence)var7_29, var15_37, var16_38, var17_39, var9_31, var18_40, var19_41, (Window)var6_27, var20_42, var21_43);
                        if (var2_23 == null) break block49;
                        try {
                            var3_24.mIntent = var2_23;
                        }
                        catch (Exception var1_4) {
                            break block48;
                        }
                        catch (SuperNotCalledException var1_5) {
                            throw var1_17;
                        }
                    }
                    var6_27 = var3_24;
                    var2_23 = var1_1;
                    var2_23.lastNonConfigurationInstances = null;
                    this.checkAndBlockForNetworkAccess();
                    var6_27.mStartedActivity = false;
                    var12_34 = var2_23.activityInfo.getThemeResource();
                    if (var12_34 != 0) {
                        var6_27.setTheme(var12_34);
                    }
                    var6_27.mCalled = false;
                    var22_44 = var1_1.isPersistable();
                    if (!var22_44) ** GOTO lbl105
                    try {
                        block51 : {
                            block50 : {
                                this.mInstrumentation.callActivityOnCreate((Activity)var6_27, var2_23.state, var2_23.persistentState);
                                break block50;
lbl105: // 1 sources:
                                this.mInstrumentation.callActivityOnCreate((Activity)var6_27, var2_23.state);
                            }
                            if (!var6_27.mCalled) {
                                var6_27 = new StringBuilder();
                                var6_27.append("Activity ");
                                var6_27.append(var2_23.intent.getComponent().toShortString());
                                var6_27.append(" did not call through to super.onCreate()");
                                var1_1 = new SuperNotCalledException(var6_27.toString());
                                throw var1_1;
                            }
                            var2_23.activity = var6_27;
                            break block51;
                            catch (Exception var1_6) {
                                break block48;
                            }
                            catch (SuperNotCalledException var1_7) {
                                throw var1_17;
                            }
                            catch (Exception var1_8) {
                                break block48;
                            }
                            catch (SuperNotCalledException var1_9) {
                                throw var1_17;
                            }
                            catch (Exception var1_10) {
                                break block48;
                            }
                            catch (SuperNotCalledException var1_11) {
                                throw var1_17;
                            }
                            catch (Exception var1_12) {
                                break block48;
                            }
                            catch (SuperNotCalledException var1_13) {
                                throw var1_17;
                            }
                            catch (Exception var1_14) {
                                break block48;
                            }
                            catch (SuperNotCalledException var1_16) {
                                throw var1_17;
                            }
                        }
                        var2_23 = this;
                        var1_1.setState(1);
                        var6_27 = var2_23.mResourcesManager;
                        // MONITORENTER : var6_27
                    }
                    catch (Exception var1_18) {
                        break block48;
                    }
                    catch (SuperNotCalledException var1_19) {
                        throw var1_17;
                    }
                    var2_23.mActivities.put(var1_1.token, (ActivityClientRecord)var1_1);
                    // MONITOREXIT : var6_27
                    return var3_24;
                }
                catch (Exception var1_21) {
                    // empty catch block
                }
            }
            if (this.mInstrumentation.onException(var3_24, (Throwable)var1_15)) {
                return var3_24;
            }
            var2_23 = new StringBuilder();
            var2_23.append("Unable to start activity ");
            var2_23.append(var4_25);
            var2_23.append(": ");
            var2_23.append(var1_15.toString());
            throw new RuntimeException(var2_23.toString(), (Throwable)var1_15);
            catch (SuperNotCalledException var1_22) {
                // empty catch block
            }
            throw var1_17;
        }
        var1_1 = new StringBuilder();
        var1_1.append("Unable to instantiate activity ");
        var1_1.append(var4_25);
        var1_1.append(": ");
        var1_1.append(var6_28.toString());
        throw new RuntimeException(var1_1.toString(), var6_28);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private Bundle performPauseActivity(ActivityClientRecord activityClientRecord, boolean bl, String object, PendingTransactionActions pendingTransactionActions) {
        Serializable serializable;
        boolean bl2 = activityClientRecord.paused;
        Object var6_6 = null;
        if (bl2) {
            if (activityClientRecord.activity.mFinished) {
                return null;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Performing pause of activity that is not resumed: ");
            ((StringBuilder)serializable).append(activityClientRecord.intent.getComponent().toShortString());
            serializable = new RuntimeException(((StringBuilder)serializable).toString());
            Slog.e("ActivityThread", ((Throwable)serializable).getMessage(), (Throwable)serializable);
        }
        boolean bl3 = true;
        if (bl) {
            activityClientRecord.activity.mFinished = true;
        }
        bl = activityClientRecord.activity.mFinished;
        int n = 0;
        if (bl || !activityClientRecord.isPreHoneycomb()) {
            bl3 = false;
        }
        if (bl3) {
            this.callActivityOnSaveInstanceState(activityClientRecord);
        }
        this.performPauseActivityIfNeeded(activityClientRecord, (String)object);
        object = this.mOnPauseListeners;
        // MONITORENTER : object
        serializable = this.mOnPauseListeners.remove(activityClientRecord.activity);
        // MONITOREXIT : object
        if (serializable != null) {
            n = ((ArrayList)serializable).size();
        }
        for (int i = 0; i < n; ++i) {
            ((OnActivityPausedListener)((ArrayList)serializable).get(i)).onPaused(activityClientRecord.activity);
        }
        object = pendingTransactionActions != null ? pendingTransactionActions.getOldState() : null;
        if (object != null && activityClientRecord.isPreHoneycomb()) {
            activityClientRecord.state = object;
        }
        object = var6_6;
        if (!bl3) return object;
        return activityClientRecord.state;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void performPauseActivityIfNeeded(ActivityClientRecord activityClientRecord, String charSequence) {
        Exception exception22222222;
        block5 : {
            if (activityClientRecord.paused) {
                return;
            }
            this.reportTopResumedActivityChanged(activityClientRecord, false, "pausing");
            try {
                activityClientRecord.activity.mCalled = false;
                this.mInstrumentation.callActivityOnPause(activityClientRecord.activity);
                if (!activityClientRecord.activity.mCalled) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Activity ");
                    ((StringBuilder)charSequence).append(ActivityThread.safeToComponentShortString(activityClientRecord.intent));
                    ((StringBuilder)charSequence).append(" did not call through to super.onPause()");
                    SuperNotCalledException superNotCalledException = new SuperNotCalledException(((StringBuilder)charSequence).toString());
                    throw superNotCalledException;
                }
            }
            catch (Exception exception22222222) {
                if (!this.mInstrumentation.onException(activityClientRecord.activity, exception22222222)) break block5;
            }
            activityClientRecord.setState(4);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to pause activity ");
        stringBuilder.append(ActivityThread.safeToComponentShortString(activityClientRecord.intent));
        stringBuilder.append(": ");
        stringBuilder.append(exception22222222.toString());
        throw new RuntimeException(stringBuilder.toString(), exception22222222);
        catch (SuperNotCalledException superNotCalledException) {
            throw superNotCalledException;
        }
    }

    private void performStopActivityInner(ActivityClientRecord activityClientRecord, PendingTransactionActions.StopInfo stopInfo, boolean bl, boolean bl2, boolean bl3, String charSequence) {
        if (activityClientRecord != null) {
            block8 : {
                if (!bl && activityClientRecord.stopped) {
                    if (activityClientRecord.activity.mFinished) {
                        return;
                    }
                    if (!bl3) {
                        Serializable serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Performing stop of activity that is already stopped: ");
                        ((StringBuilder)serializable).append(activityClientRecord.intent.getComponent().toShortString());
                        serializable = new RuntimeException(((StringBuilder)serializable).toString());
                        Slog.e("ActivityThread", ((Throwable)serializable).getMessage(), (Throwable)serializable);
                        Slog.e("ActivityThread", activityClientRecord.getStateString());
                    }
                }
                this.performPauseActivityIfNeeded(activityClientRecord, (String)charSequence);
                if (stopInfo != null) {
                    try {
                        stopInfo.setDescription(activityClientRecord.activity.onCreateDescription());
                    }
                    catch (Exception exception) {
                        if (this.mInstrumentation.onException(activityClientRecord.activity, exception)) break block8;
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Unable to save state of activity ");
                        ((StringBuilder)charSequence).append(activityClientRecord.intent.getComponent().toShortString());
                        ((StringBuilder)charSequence).append(": ");
                        ((StringBuilder)charSequence).append(exception.toString());
                        throw new RuntimeException(((StringBuilder)charSequence).toString(), exception);
                    }
                }
            }
            if (!bl) {
                this.callActivityOnStop(activityClientRecord, bl2, (String)charSequence);
            }
        }
    }

    static void printRow(PrintWriter printWriter, String string2, Object ... arrobject) {
        printWriter.println(String.format(string2, arrobject));
    }

    private void purgePendingResources() {
        Trace.traceBegin(64L, "purgePendingResources");
        this.nPurgePendingResources();
        Trace.traceEnd(64L);
    }

    private void relaunchAllActivities(boolean bl) {
        for (Map.Entry<IBinder, ActivityClientRecord> entry : this.mActivities.entrySet()) {
            ActivityClientRecord activityClientRecord = entry.getValue();
            if (activityClientRecord.activity.mFinished) continue;
            if (bl && activityClientRecord.window != null) {
                activityClientRecord.mPreserveWindow = true;
            }
            this.scheduleRelaunchActivity(entry.getKey());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private CancellationSignal removeSafeCancellationTransport(SafeCancellationTransport object) {
        synchronized (this) {
            object = this.mRemoteCancellations.remove(object);
            if (this.mRemoteCancellations.isEmpty()) {
                this.mRemoteCancellations = null;
            }
            return object;
        }
    }

    private void reportSizeConfigurations(ActivityClientRecord activityClientRecord) {
        if (this.mActivitiesToBeDestroyed.containsKey(activityClientRecord.token)) {
            return;
        }
        Configuration[] arrconfiguration = activityClientRecord.activity.getResources().getSizeConfigurations();
        if (arrconfiguration == null) {
            return;
        }
        SparseIntArray sparseIntArray = new SparseIntArray();
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        SparseIntArray sparseIntArray3 = new SparseIntArray();
        for (int i = arrconfiguration.length - 1; i >= 0; --i) {
            Configuration configuration = arrconfiguration[i];
            if (configuration.screenHeightDp != 0) {
                sparseIntArray2.put(configuration.screenHeightDp, 0);
            }
            if (configuration.screenWidthDp != 0) {
                sparseIntArray.put(configuration.screenWidthDp, 0);
            }
            if (configuration.smallestScreenWidthDp == 0) continue;
            sparseIntArray3.put(configuration.smallestScreenWidthDp, 0);
        }
        try {
            ActivityTaskManager.getService().reportSizeConfigurations(activityClientRecord.token, sparseIntArray.copyKeys(), sparseIntArray2.copyKeys(), sparseIntArray3.copyKeys());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private void reportTopResumedActivityChanged(ActivityClientRecord activityClientRecord, boolean bl, String string2) {
        if (activityClientRecord.lastReportedTopResumedState != bl) {
            activityClientRecord.lastReportedTopResumedState = bl;
            activityClientRecord.activity.performTopResumedActivityChanged(bl, string2);
        }
    }

    private static String safeToComponentShortString(Intent object) {
        object = (object = ((Intent)object).getComponent()) == null ? "[Unknown]" : ((ComponentName)object).toShortString();
        return object;
    }

    private void sendMessage(int n, Object object, int n2) {
        this.sendMessage(n, object, n2, 0, false);
    }

    private void sendMessage(int n, Object object, int n2, int n3) {
        this.sendMessage(n, object, n2, n3, false);
    }

    private void sendMessage(int n, Object object, int n2, int n3, int n4) {
        Message message = Message.obtain();
        message.what = n;
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.argi1 = n2;
        someArgs.argi2 = n3;
        someArgs.argi3 = n4;
        message.obj = someArgs;
        this.mH.sendMessage(message);
    }

    private void sendMessage(int n, Object object, int n2, int n3, boolean bl) {
        Message message = Message.obtain();
        message.what = n;
        message.obj = object;
        message.arg1 = n2;
        message.arg2 = n3;
        if (bl) {
            message.setAsynchronous(true);
        }
        this.mH.sendMessage(message);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void setupGraphicsSupport(Context var1_1) {
        Trace.traceBegin(64L, "setupGraphicsSupport");
        if (!"android".equals(var1_1.getPackageName())) {
            var2_3 = var1_1.getCacheDir();
            if (var2_3 != null) {
                System.setProperty("java.io.tmpdir", var2_3.getAbsolutePath());
            } else {
                Log.v("ActivityThread", "Unable to initialize \"java.io.tmpdir\" property due to missing cache directory");
            }
            var2_3 = var1_1.createDeviceProtectedStorageContext().getCodeCacheDir();
            if (var2_3 != null) {
                try {
                    var3_4 = Process.myUid();
                    if (ActivityThread.getPackageManager().getPackagesForUid(var3_4) == null) ** GOTO lbl23
                    HardwareRenderer.setupDiskCache(var2_3);
                    RenderScriptCacheDir.setupDiskCache(var2_3);
                }
                catch (RemoteException var1_2) {
                    Trace.traceEnd(64L);
                    throw var1_2.rethrowFromSystemServer();
                }
            } else {
                Log.w("ActivityThread", "Unable to use shader/script cache: missing code-cache directory");
            }
        }
lbl23: // 5 sources:
        GraphicsEnvironment.getInstance().setup(var1_1, this.mCoreSettings);
        Trace.traceEnd(64L);
    }

    @UnsupportedAppUsage
    public static ActivityThread systemMain() {
        if (!ActivityManager.isHighEndGfx()) {
            ThreadedRenderer.disable(true);
        } else {
            ThreadedRenderer.enableForegroundTrimming();
        }
        ActivityThread activityThread = new ActivityThread();
        activityThread.attach(true, 0L);
        return activityThread;
    }

    private boolean updateDebugViewAttributeState() {
        boolean bl = View.sDebugViewAttributes;
        Object object = this.mCoreSettings;
        String string2 = "";
        View.sDebugViewAttributesApplicationPackage = ((BaseBundle)object).getString("debug_view_attributes_application_package", "");
        object = this.mBoundApplication;
        if (object != null && ((AppBindData)object).appInfo != null) {
            string2 = this.mBoundApplication.appInfo.packageName;
        }
        object = this.mCoreSettings;
        boolean bl2 = false;
        boolean bl3 = ((BaseBundle)object).getInt("debug_view_attributes", 0) != 0 || View.sDebugViewAttributesApplicationPackage.equals(string2);
        View.sDebugViewAttributes = bl3;
        bl3 = bl2;
        if (bl != View.sDebugViewAttributes) {
            bl3 = true;
        }
        return bl3;
    }

    private void updateDefaultDensity() {
        int n = this.mCurDefaultDisplayDpi;
        if (!this.mDensityCompatMode && n != 0 && n != DisplayMetrics.DENSITY_DEVICE) {
            DisplayMetrics.DENSITY_DEVICE = n;
            Bitmap.setDefaultDensity(n);
        }
    }

    public static void updateHttpProxy(Context context) {
        Proxy.setHttpProxySystemProperty(ConnectivityManager.from(context).getDefaultProxy());
    }

    private void updateLocaleListFromAppContext(Context object, LocaleList localeList) {
        object = ((Context)object).getResources().getConfiguration().getLocales().get(0);
        int n = localeList.size();
        for (int i = 0; i < n; ++i) {
            if (!((Locale)object).equals(localeList.get(i))) continue;
            LocaleList.setDefault(localeList, i);
            return;
        }
        LocaleList.setDefault(new LocaleList((Locale)object, localeList));
    }

    private void updateVisibility(ActivityClientRecord activityClientRecord, boolean bl) {
        View view = activityClientRecord.activity.mDecor;
        if (view != null) {
            if (bl) {
                if (!activityClientRecord.activity.mVisibleFromServer) {
                    activityClientRecord.activity.mVisibleFromServer = true;
                    ++this.mNumVisibleActivities;
                    if (activityClientRecord.activity.mVisibleFromClient) {
                        activityClientRecord.activity.makeVisible();
                    }
                }
                if (activityClientRecord.newConfig != null) {
                    this.performConfigurationChangedForActivity(activityClientRecord, activityClientRecord.newConfig);
                    activityClientRecord.newConfig = null;
                }
            } else if (activityClientRecord.activity.mVisibleFromServer) {
                activityClientRecord.activity.mVisibleFromServer = false;
                --this.mNumVisibleActivities;
                view.setVisibility(4);
            }
        }
    }

    private void updateVmProcessState(int n) {
        n = n <= 7 ? 0 : 1;
        VMRuntime.getRuntime().updateProcessState(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public final IContentProvider acquireExistingProvider(Context object, String object2, int n, boolean bl) {
        object = this.mProviderMap;
        synchronized (object) {
            Object object3 = new ProviderKey((String)object2, n);
            object3 = this.mProviderMap.get(object3);
            if (object3 == null) {
                return null;
            }
            Object object4 = ((ProviderClientRecord)object3).mProvider;
            object3 = object4.asBinder();
            if (!object3.isBinderAlive()) {
                object4 = new StringBuilder();
                ((StringBuilder)object4).append("Acquiring provider ");
                ((StringBuilder)object4).append((String)object2);
                ((StringBuilder)object4).append(" for user ");
                ((StringBuilder)object4).append(n);
                ((StringBuilder)object4).append(": existing object's process dead");
                Log.i("ActivityThread", ((StringBuilder)object4).toString());
                this.handleUnstableProviderDiedLocked((IBinder)object3, true);
                return null;
            }
            object2 = this.mProviderRefCountMap.get(object3);
            if (object2 != null) {
                this.incProviderRefLocked((ProviderRefCount)object2, bl);
            }
            return object4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public final IContentProvider acquireProvider(Context object, String string2, int n, boolean bl) {
        Object object2 = this.acquireExistingProvider((Context)object, string2, n, bl);
        if (object2 != null) {
            return object2;
        }
        try {
            object2 = this.getGetProviderLock(string2, n);
            // MONITORENTER : object2
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        ContentProviderHolder contentProviderHolder = ActivityManager.getService().getContentProvider(this.getApplicationThread(), ((Context)object).getOpPackageName(), string2, n, bl);
        // MONITOREXIT : object2
        if (contentProviderHolder != null) return this.installProvider((Context)object, (ContentProviderHolder)contentProviderHolder, (ProviderInfo)contentProviderHolder.info, (boolean)true, (boolean)contentProviderHolder.noReleaseNeeded, (boolean)bl).provider;
        object = new StringBuilder();
        ((StringBuilder)object).append("Failed to find provider info for ");
        ((StringBuilder)object).append(string2);
        Slog.e("ActivityThread", ((StringBuilder)object).toString());
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void appNotRespondingViaProvider(IBinder object) {
        ArrayMap<ProviderKey, ProviderClientRecord> arrayMap = this.mProviderMap;
        synchronized (arrayMap) {
            object = this.mProviderRefCountMap.get(object);
            if (object != null) {
                try {
                    ActivityManager.getService().appNotRespondingViaProvider(object.holder.connection);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    final Configuration applyCompatConfiguration(int n) {
        Configuration configuration = this.mConfiguration;
        if (this.mCompatConfiguration == null) {
            this.mCompatConfiguration = new Configuration();
        }
        this.mCompatConfiguration.setTo(this.mConfiguration);
        if (this.mResourcesManager.applyCompatConfigurationLocked(n, this.mCompatConfiguration)) {
            configuration = this.mCompatConfiguration;
        }
        return configuration;
    }

    Configuration applyConfigCompatMainThread(int n, Configuration configuration, CompatibilityInfo compatibilityInfo) {
        if (configuration == null) {
            return null;
        }
        Configuration configuration2 = configuration;
        if (!compatibilityInfo.supportsScreen()) {
            this.mMainThreadConfig.setTo(configuration);
            configuration2 = this.mMainThreadConfig;
            compatibilityInfo.applyToConfiguration(n, configuration2);
        }
        return configuration2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void applyConfigurationToResources(Configuration configuration) {
        ResourcesManager resourcesManager = this.mResourcesManager;
        synchronized (resourcesManager) {
            this.mResourcesManager.applyConfigurationToResourcesLocked(configuration, null);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    ArrayList<ComponentCallbacks2> collectComponentCallbacks(boolean bl, Configuration object) {
        int n;
        int n2;
        ArrayList<ComponentCallbacks2> arrayList = new ArrayList<ComponentCallbacks2>();
        ResourcesManager resourcesManager = this.mResourcesManager;
        synchronized (resourcesManager) {
            n = this.mAllApplications.size();
            for (n2 = 0; n2 < n; ++n2) {
                arrayList.add(this.mAllApplications.get(n2));
            }
            n = this.mActivities.size();
            for (n2 = 0; n2 < n; ++n2) {
                ActivityClientRecord activityClientRecord = this.mActivities.valueAt(n2);
                Activity activity = activityClientRecord.activity;
                if (activity == null) continue;
                Configuration configuration = this.applyConfigCompatMainThread(this.mCurDefaultDisplayDpi, (Configuration)object, activityClientRecord.packageInfo.getCompatibilityInfo());
                if (!(activityClientRecord.activity.mFinished || !bl && activityClientRecord.paused)) {
                    arrayList.add(activity);
                    continue;
                }
                if (configuration == null) continue;
                activityClientRecord.newConfig = configuration;
            }
            n = this.mServices.size();
            for (n2 = 0; n2 < n; ++n2) {
                arrayList.add(this.mServices.valueAt(n2));
            }
        }
        object = this.mProviderMap;
        synchronized (object) {
            n = this.mLocalProviders.size();
            n2 = 0;
            while (n2 < n) {
                arrayList.add(this.mLocalProviders.valueAt((int)n2).mLocalProvider);
                ++n2;
            }
            return arrayList;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void completeRemoveProvider(ProviderRefCount providerRefCount) {
        ArrayMap<ProviderKey, ProviderClientRecord> arrayMap = this.mProviderMap;
        synchronized (arrayMap) {
            if (!providerRefCount.removePending) {
                return;
            }
            providerRefCount.removePending = false;
            IBinder iBinder = providerRefCount.holder.provider.asBinder();
            if (this.mProviderRefCountMap.get(iBinder) == providerRefCount) {
                this.mProviderRefCountMap.remove(iBinder);
            }
            for (int i = this.mProviderMap.size() - 1; i >= 0; --i) {
                if (this.mProviderMap.valueAt((int)i).mProvider.asBinder() != iBinder) continue;
                this.mProviderMap.removeAt(i);
            }
        }
        try {
            ActivityManager.getService().removeContentProvider(providerRefCount.holder.connection, false);
            return;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void countLaunchingActivities(int n) {
        this.mNumLaunchingActivities.getAndAdd(n);
    }

    public ContextImpl createSystemUiContext(int n) {
        return ContextImpl.createSystemUiContext(this.getSystemUiContext(), n);
    }

    void doGcIfNeeded() {
        this.doGcIfNeeded("bg");
    }

    void doGcIfNeeded(String string2) {
        this.mGcIdlerScheduled = false;
        long l = SystemClock.uptimeMillis();
        if (BinderInternal.getLastGcTime() + 5000L < l) {
            BinderInternal.forceGc(string2);
        }
    }

    final void finishInstrumentation(int n, Bundle bundle) {
        IActivityManager iActivityManager = ActivityManager.getService();
        if (this.mProfiler.profileFile != null && this.mProfiler.handlingProfiling && this.mProfiler.profileFd == null) {
            Debug.stopMethodTracing();
        }
        try {
            iActivityManager.finishInstrumentation(this.mAppThread, n, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public Map<IBinder, ClientTransactionItem> getActivitiesToBeDestroyed() {
        return this.mActivitiesToBeDestroyed;
    }

    @UnsupportedAppUsage
    @Override
    public final Activity getActivity(IBinder object) {
        object = (object = this.mActivities.get(object)) != null ? ((ActivityClientRecord)object).activity : null;
        return object;
    }

    @Override
    public ActivityClientRecord getActivityClient(IBinder iBinder) {
        return this.mActivities.get(iBinder);
    }

    @UnsupportedAppUsage
    public Application getApplication() {
        return this.mInitialApplication;
    }

    @UnsupportedAppUsage
    public ApplicationThread getApplicationThread() {
        return this.mAppThread;
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    @UnsupportedAppUsage
    final Handler getHandler() {
        return this.mH;
    }

    @UnsupportedAppUsage
    public Instrumentation getInstrumentation() {
        return this.mInstrumentation;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getIntCoreSetting(String string2, int n) {
        ResourcesManager resourcesManager = this.mResourcesManager;
        synchronized (resourcesManager) {
            if (this.mCoreSettings == null) return n;
            return this.mCoreSettings.getInt(string2, n);
        }
    }

    @UnsupportedAppUsage
    public Looper getLooper() {
        return this.mLooper;
    }

    @UnsupportedAppUsage
    public final LoadedApk getPackageInfo(ApplicationInfo object, CompatibilityInfo object2, int n) {
        boolean bl = (n & 1) != 0;
        boolean bl2 = bl && ((ApplicationInfo)object).uid != 0 && ((ApplicationInfo)object).uid != 1000 && (this.mBoundApplication == null || !UserHandle.isSameApp(((ApplicationInfo)object).uid, this.mBoundApplication.appInfo.uid));
        boolean bl3 = bl && (1073741824 & n) != 0;
        if ((n & 3) == 1 && bl2) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Requesting code from ");
            ((StringBuilder)object2).append(((ApplicationInfo)object).packageName);
            ((StringBuilder)object2).append(" (with uid ");
            ((StringBuilder)object2).append(((ApplicationInfo)object).uid);
            ((StringBuilder)object2).append(")");
            object = object2 = ((StringBuilder)object2).toString();
            if (this.mBoundApplication != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(" to be run in process ");
                ((StringBuilder)object).append(this.mBoundApplication.processName);
                ((StringBuilder)object).append(" (with uid ");
                ((StringBuilder)object).append(this.mBoundApplication.appInfo.uid);
                ((StringBuilder)object).append(")");
                object = ((StringBuilder)object).toString();
            }
            throw new SecurityException((String)object);
        }
        return this.getPackageInfo((ApplicationInfo)object, (CompatibilityInfo)object2, null, bl2, bl, bl3);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public final LoadedApk getPackageInfo(String string2, CompatibilityInfo compatibilityInfo, int n) {
        return this.getPackageInfo(string2, compatibilityInfo, n, UserHandle.myUserId());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final LoadedApk getPackageInfo(String string2, CompatibilityInfo object, int n, int n2) {
        Object object2;
        ApplicationInfo applicationInfo;
        boolean bl = UserHandle.myUserId() != n2;
        try {
            object2 = ActivityThread.getPackageManager();
            if (n2 < 0) {
                n2 = UserHandle.myUserId();
            }
            applicationInfo = object2.getApplicationInfo(string2, 268436480, n2);
            ResourcesManager resourcesManager = this.mResourcesManager;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        object2 = bl ? null : ((n & 1) != 0 ? this.mPackages.get(string2) : this.mResourcePackages.get(string2));
        object2 = object2 != null ? (LoadedApk)((Reference)object2).get() : null;
        if (applicationInfo != null && object2 != null) {
            if (!ActivityThread.isLoadedApkResourceDirsUpToDate(object2, applicationInfo)) {
                ((LoadedApk)object2).updateApplicationInfo(applicationInfo, null);
            }
            if (!((LoadedApk)object2).isSecurityViolation()) {
                // MONITOREXIT : resourcesManager
                return object2;
            }
            if ((n & 2) != 0) {
                return object2;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Requesting code from ");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(" to be run in process ");
            ((StringBuilder)object2).append(this.mBoundApplication.processName);
            ((StringBuilder)object2).append("/");
            ((StringBuilder)object2).append(this.mBoundApplication.appInfo.uid);
            object = new SecurityException(((StringBuilder)object2).toString());
            throw object;
        }
        // MONITOREXIT : resourcesManager
        if (applicationInfo == null) return null;
        return this.getPackageInfo(applicationInfo, (CompatibilityInfo)object, n);
    }

    @UnsupportedAppUsage
    @Override
    public final LoadedApk getPackageInfoNoCheck(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo) {
        return this.getPackageInfo(applicationInfo, compatibilityInfo, null, false, true, false);
    }

    @UnsupportedAppUsage
    public String getProcessName() {
        return this.mBoundApplication.processName;
    }

    public String getProfileFilePath() {
        return this.mProfiler.profileFile;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public ContextImpl getSystemContext() {
        synchronized (this) {
            if (this.mSystemContext != null) return this.mSystemContext;
            this.mSystemContext = ContextImpl.createSystemContext(this);
            return this.mSystemContext;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ContextImpl getSystemUiContext() {
        synchronized (this) {
            if (this.mSystemUiContext != null) return this.mSystemUiContext;
            this.mSystemUiContext = ContextImpl.createSystemUiContext(this.getSystemContext());
            return this.mSystemUiContext;
        }
    }

    Resources getTopLevelResources(String string2, String[] arrstring, String[] arrstring2, String[] arrstring3, int n, LoadedApk loadedApk) {
        return this.mResourcesManager.getResources(null, string2, arrstring, arrstring2, arrstring3, n, null, loadedApk.getCompatibilityInfo(), loadedApk.getClassLoader());
    }

    @Override
    TransactionExecutor getTransactionExecutor() {
        return this.mTransactionExecutor;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void handleActivityConfigurationChanged(IBinder object, Configuration configuration, int n) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(object);
        if (activityClientRecord == null) return;
        if (activityClientRecord.activity == null) {
            return;
        }
        boolean bl = n != -1 && n != activityClientRecord.activity.getDisplayId();
        // MONITORENTER : activityClientRecord
        object = configuration;
        if (activityClientRecord.mPendingOverrideConfig != null) {
            object = configuration;
            if (!activityClientRecord.mPendingOverrideConfig.isOtherSeqNewer(configuration)) {
                object = activityClientRecord.mPendingOverrideConfig;
            }
        }
        configuration = null;
        activityClientRecord.mPendingOverrideConfig = null;
        // MONITOREXIT : activityClientRecord
        if (activityClientRecord.overrideConfig != null && !activityClientRecord.overrideConfig.isOtherSeqNewer((Configuration)object) && !bl) {
            return;
        }
        activityClientRecord.overrideConfig = object;
        object = activityClientRecord.activity.mDecor != null ? activityClientRecord.activity.mDecor.getViewRootImpl() : configuration;
        if (bl) {
            configuration = this.performConfigurationChangedForActivity(activityClientRecord, this.mCompatConfiguration, n, true);
            if (object != null) {
                ((ViewRootImpl)object).onMovedToDisplay(n, configuration);
            }
        } else {
            this.performConfigurationChangedForActivity(activityClientRecord, this.mCompatConfiguration);
        }
        if (object != null) {
            ((ViewRootImpl)object).updateConfiguration(n);
        }
        this.mSomeActivitiesChanged = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void handleApplicationInfoChanged(ApplicationInfo parcelable) {
        Object object;
        Object object2;
        String[] arrstring = this.mResourcesManager;
        synchronized (arrstring) {
            object2 = this.mPackages.get(parcelable.packageName);
            object2 = object2 != null ? (LoadedApk)((Reference)object2).get() : null;
            object = this.mResourcePackages.get(parcelable.packageName);
            object = object != null ? (LoadedApk)((Reference)object).get() : null;
        }
        arrstring = new String[2];
        int n = 0;
        if (object2 != null) {
            arrstring[0] = ((LoadedApk)object2).getResDir();
            ArrayList<String> arrayList = new ArrayList<String>();
            LoadedApk.makePaths(this, ((LoadedApk)object2).getApplicationInfo(), arrayList);
            ((LoadedApk)object2).updateApplicationInfo((ApplicationInfo)parcelable, (List<String>)arrayList);
        }
        if (object != null) {
            arrstring[1] = ((LoadedApk)object).getResDir();
            object2 = new ArrayList<String>();
            LoadedApk.makePaths(this, ((LoadedApk)object).getApplicationInfo(), object2);
            ((LoadedApk)object).updateApplicationInfo((ApplicationInfo)parcelable, (List<String>)object2);
        }
        object2 = this.mResourcesManager;
        synchronized (object2) {
            this.mResourcesManager.applyNewResourceDirsLocked((ApplicationInfo)parcelable, arrstring);
        }
        ApplicationPackageManager.configurationChanged();
        parcelable = new Configuration();
        object2 = this.mConfiguration;
        if (object2 != null) {
            n = ((Configuration)object2).assetsSeq;
        }
        ((Configuration)parcelable).assetsSeq = n + 1;
        this.handleConfigurationChanged((Configuration)parcelable, null);
        this.relaunchAllActivities(true);
    }

    @Override
    public void handleConfigurationChanged(Configuration configuration) {
        Trace.traceBegin(64L, "configChanged");
        this.mCurDefaultDisplayDpi = configuration.densityDpi;
        this.mUpdatingSystemConfig = true;
        try {
            this.handleConfigurationChanged(configuration, null);
            Trace.traceEnd(64L);
            return;
        }
        finally {
            this.mUpdatingSystemConfig = false;
        }
    }

    @Override
    public void handleDestroyActivity(IBinder iBinder, boolean bl, int n, boolean bl2, String object) {
        if ((object = this.performDestroyActivity(iBinder, bl, n, bl2, (String)object)) != null) {
            ActivityThread.cleanUpPendingRemoveWindows((ActivityClientRecord)object, bl);
            Object object2 = ((ActivityClientRecord)object).activity.getWindowManager();
            View view = object.activity.mDecor;
            if (view != null) {
                if (object.activity.mVisibleFromServer) {
                    --this.mNumVisibleActivities;
                }
                IBinder iBinder2 = view.getWindowToken();
                if (object.activity.mWindowAdded) {
                    if (((ActivityClientRecord)object).mPreserveWindow) {
                        ((ActivityClientRecord)object).mPendingRemoveWindow = ((ActivityClientRecord)object).window;
                        ((ActivityClientRecord)object).mPendingRemoveWindowManager = object2;
                        ((ActivityClientRecord)object).window.clearContentView();
                    } else {
                        object2.removeViewImmediate(view);
                    }
                }
                if (iBinder2 != null && ((ActivityClientRecord)object).mPendingRemoveWindow == null) {
                    WindowManagerGlobal.getInstance().closeAll(iBinder2, ((ActivityClientRecord)object).activity.getClass().getName(), "Activity");
                } else if (((ActivityClientRecord)object).mPendingRemoveWindow != null) {
                    WindowManagerGlobal.getInstance().closeAllExceptView(iBinder, view, ((ActivityClientRecord)object).activity.getClass().getName(), "Activity");
                }
                object.activity.mDecor = null;
            }
            if (((ActivityClientRecord)object).mPendingRemoveWindow == null) {
                WindowManagerGlobal.getInstance().closeAll(iBinder, ((ActivityClientRecord)object).activity.getClass().getName(), "Activity");
            }
            if ((object2 = ((ActivityClientRecord)object).activity.getBaseContext()) instanceof ContextImpl) {
                ((ContextImpl)object2).scheduleFinalCleanup(((ActivityClientRecord)object).activity.getClass().getName(), "Activity");
            }
        }
        if (bl) {
            try {
                ActivityTaskManager.getService().activityDestroyed(iBinder);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        this.mSomeActivitiesChanged = true;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    final void handleDispatchPackageBroadcast(int n, String[] arrstring) {
        boolean bl;
        block37 : {
            int n2;
            boolean bl2;
            block41 : {
                void var2_5;
                ArrayList<String> arrayList;
                block39 : {
                    block40 : {
                        block35 : {
                            block38 : {
                                block36 : {
                                    bl2 = false;
                                    bl = false;
                                    n2 = 0;
                                    if (n == 0 || n == 2) break block35;
                                    if (n == 3) break block36;
                                    bl = bl2;
                                    break block37;
                                }
                                if (arrstring != null) break block38;
                                bl = bl2;
                                break block37;
                            }
                            arrayList = new ArrayList<String>();
                            ResourcesManager resourcesManager = this.mResourcesManager;
                            // MONITORENTER : resourcesManager
                            n2 = arrstring.length;
                            bl = false;
                            --n2;
                            break block39;
                        }
                        if (n == 0) {
                            n2 = 1;
                        }
                        if (arrstring != null) break block40;
                        bl = bl2;
                        break block37;
                    }
                    ResourcesManager resourcesManager = this.mResourcesManager;
                    // MONITORENTER : resourcesManager
                    break block41;
                }
                while (n2 >= 0) {
                    block34 : {
                        Object object32;
                        String string2 = arrstring[n2];
                        Object object2 = this.mPackages.get(string2);
                        ApplicationInfo applicationInfo = null;
                        object2 = object2 != null ? (LoadedApk)((Reference)object2).get() : null;
                        if (object2 != null) {
                            bl = true;
                        } else {
                            object32 = this.mResourcePackages.get(string2);
                            object2 = applicationInfo;
                            if (object32 != null) {
                                object2 = (LoadedApk)((Reference)object32).get();
                            }
                            if (object2 != null) {
                                bl = true;
                            }
                        }
                        if (object2 == null) break block34;
                        try {
                            arrayList.add(string2);
                            try {
                                applicationInfo = sPackageManager.getApplicationInfo(string2, 1024, UserHandle.myUserId());
                                if (this.mActivities.size() > 0) {
                                    for (Object object32 : this.mActivities.values()) {
                                        if (!object32.activityInfo.applicationInfo.packageName.equals(string2)) continue;
                                        object32.activityInfo.applicationInfo = applicationInfo;
                                        ((ActivityClientRecord)object32).packageInfo = object2;
                                    }
                                }
                                string2 = ((LoadedApk)object2).getResDir();
                                object32 = new ArrayList();
                                LoadedApk.makePaths(this, ((LoadedApk)object2).getApplicationInfo(), object32);
                                ((LoadedApk)object2).updateApplicationInfo(applicationInfo, (List<String>)object32);
                                object2 = this.mResourcesManager;
                                // MONITORENTER : object2
                            }
                            catch (RemoteException remoteException) {
                            }
                            this.mResourcesManager.applyNewResourceDirsLocked(applicationInfo, new String[]{string2});
                            // MONITOREXIT : object2
                        }
                        catch (Throwable throwable) {
                            throw var2_5;
                        }
                    }
                    --n2;
                }
                try {
                    ActivityThread.getPackageManager().notifyPackagesReplacedReceived(arrayList.toArray(new String[0]));
                }
                catch (RemoteException remoteException) {}
                break block37;
                catch (Throwable throwable) {
                    // empty catch block
                }
                // MONITOREXIT : resourcesManager
                throw var2_5;
            }
            for (int i = arrstring.length - 1; i >= 0; --i) {
                bl2 = bl;
                if (!bl) {
                    WeakReference<LoadedApk> weakReference = this.mPackages.get(arrstring[i]);
                    if (weakReference != null && weakReference.get() != null) {
                        bl2 = true;
                    } else {
                        weakReference = this.mResourcePackages.get(arrstring[i]);
                        bl2 = bl;
                        if (weakReference != null) {
                            bl2 = bl;
                            if (weakReference.get() != null) {
                                bl2 = true;
                            }
                        }
                    }
                }
                if (n2 != 0) {
                    this.mPackages.remove(arrstring[i]);
                    this.mResourcePackages.remove(arrstring[i]);
                }
                bl = bl2;
            }
            // MONITOREXIT : resourcesManager
        }
        ApplicationPackageManager.handlePackageBroadcast(n, arrstring, bl);
    }

    public void handleInstallProvider(ProviderInfo providerInfo) {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.allowThreadDiskWrites();
        try {
            this.installContentProviders(this.mInitialApplication, Arrays.asList(providerInfo));
            return;
        }
        finally {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Activity handleLaunchActivity(ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions, Intent object) {
        this.unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        if (activityClientRecord.profilerInfo != null) {
            this.mProfiler.setProfiler(activityClientRecord.profilerInfo);
            this.mProfiler.startProfiling();
        }
        this.handleConfigurationChanged(null, null);
        if (!ThreadedRenderer.sRendererDisabled && (activityClientRecord.activityInfo.flags & 512) != 0) {
            HardwareRenderer.preload();
        }
        WindowManagerGlobal.initialize();
        GraphicsEnvironment.hintActivityLaunch();
        object = this.performLaunchActivity(activityClientRecord, (Intent)object);
        if (object != null) {
            activityClientRecord.createdConfig = new Configuration(this.mConfiguration);
            this.reportSizeConfigurations(activityClientRecord);
            if (activityClientRecord.activity.mFinished) return object;
            if (pendingTransactionActions == null) return object;
            pendingTransactionActions.setOldState(activityClientRecord.state);
            pendingTransactionActions.setRestoreInstanceState(true);
            pendingTransactionActions.setCallOnPostCreate(true);
            return object;
        }
        try {
            ActivityTaskManager.getService().finishActivity(activityClientRecord.token, 0, null, 0);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    final void handleLowMemory() {
        ArrayList<ComponentCallbacks2> arrayList = this.collectComponentCallbacks(true, null);
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).onLowMemory();
        }
        if (Process.myUid() != 1000) {
            EventLog.writeEvent(75003, SQLiteDatabase.releaseMemory());
        }
        Canvas.freeCaches();
        Canvas.freeTextLayoutCaches();
        BinderInternal.forceGc("mem");
    }

    @Override
    public void handleMultiWindowModeChanged(IBinder object, boolean bl, Configuration configuration) {
        if ((object = this.mActivities.get(object)) != null) {
            Configuration configuration2 = new Configuration(this.mConfiguration);
            if (configuration != null) {
                configuration2.updateFrom(configuration);
            }
            ((ActivityClientRecord)object).activity.dispatchMultiWindowModeChanged(bl, configuration2);
        }
    }

    @Override
    public void handleNewIntent(IBinder object, List<ReferrerIntent> list) {
        if ((object = this.mActivities.get(object)) == null) {
            return;
        }
        this.checkAndBlockForNetworkAccess();
        this.deliverNewIntents((ActivityClientRecord)object, list);
    }

    @Override
    public void handlePauseActivity(IBinder object, boolean bl, boolean bl2, int n, PendingTransactionActions pendingTransactionActions, String string2) {
        ActivityClientRecord activityClientRecord = this.mActivities.get(object);
        if (activityClientRecord != null) {
            if (bl2) {
                this.performUserLeavingActivity(activityClientRecord);
            }
            object = activityClientRecord.activity;
            ((Activity)object).mConfigChangeFlags |= n;
            this.performPauseActivity(activityClientRecord, bl, string2, pendingTransactionActions);
            if (activityClientRecord.isPreHoneycomb()) {
                QueuedWork.waitToFinish();
            }
            this.mSomeActivitiesChanged = true;
        }
    }

    @Override
    public void handlePictureInPictureModeChanged(IBinder object, boolean bl, Configuration configuration) {
        if ((object = this.mActivities.get(object)) != null) {
            Configuration configuration2 = new Configuration(this.mConfiguration);
            if (configuration != null) {
                configuration2.updateFrom(configuration);
            }
            ((ActivityClientRecord)object).activity.dispatchPictureInPictureModeChanged(bl, configuration2);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    final void handleProfilerControl(boolean var1_1, ProfilerInfo var2_2, int var3_3) {
        if (!var1_1) {
            this.mProfiler.stopProfiling();
            return;
        }
        this.mProfiler.setProfiler(var2_2);
        this.mProfiler.startProfiling();
lbl7: // 2 sources:
        do {
            var2_2.closeFd();
            return;
            break;
        } while (true);
        {
            catch (Throwable var4_4) {
            }
            catch (RuntimeException var4_5) {}
            {
                var4_6 = new StringBuilder();
                var4_6.append("Profiling failed on path ");
                var4_6.append(var2_2.profileFile);
                var4_6.append(" -- can the process access this path?");
                Slog.w("ActivityThread", var4_6.toString());
                ** continue;
            }
        }
        var2_2.closeFd();
        throw var4_4;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void handleRelaunchActivity(ActivityClientRecord object, PendingTransactionActions pendingTransactionActions) {
        int n;
        Activity activity;
        Object object2;
        block23 : {
            IBinder iBinder;
            int n2;
            void var1_7;
            int n3;
            block21 : {
                this.unscheduleGcIdler();
                this.mSomeActivitiesChanged = true;
                activity = null;
                ResourcesManager resourcesManager = this.mResourcesManager;
                // MONITORENTER : resourcesManager
                n2 = this.mRelaunchingActivities.size();
                try {
                    iBinder = ((ActivityClientRecord)object).token;
                    n3 = 0;
                    n = 0;
                    object2 = null;
                    break block21;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                throw var1_7;
            }
            while (n3 < n2) {
                int n4;
                int n5;
                int n6;
                block22 : {
                    object = this.mRelaunchingActivities.get(n3);
                    IBinder iBinder2 = ((ActivityClientRecord)object).token;
                    n4 = n2;
                    n5 = n3;
                    n6 = n;
                    if (iBinder2 != iBinder) break block22;
                    try {
                        n6 = ((ActivityClientRecord)object).pendingConfigChanges;
                    }
                    catch (Throwable throwable) {
                        throw var1_7;
                    }
                    try {
                        this.mRelaunchingActivities.remove(n3);
                    }
                    catch (Throwable throwable) {
                        throw var1_7;
                    }
                    n5 = n3 - 1;
                    n4 = n2 - 1;
                    object2 = object;
                    n6 |= n;
                }
                n3 = n5 + 1;
                n2 = n4;
                n = n6;
            }
            if (object2 != null) break block23;
            try {
                // MONITOREXIT : resourcesManager
                return;
            }
            catch (Throwable throwable) {
                throw var1_7;
            }
        }
        object = activity;
        if (this.mPendingConfiguration != null) {
            object = this.mPendingConfiguration;
            this.mPendingConfiguration = null;
        }
        // MONITOREXIT : resourcesManager
        if (((ActivityClientRecord)object2).createdConfig != null && (this.mConfiguration == null || ((ActivityClientRecord)object2).createdConfig.isOtherSeqNewer(this.mConfiguration) && this.mConfiguration.diff(((ActivityClientRecord)object2).createdConfig) != 0) && (object == null || ((ActivityClientRecord)object2).createdConfig.isOtherSeqNewer((Configuration)object))) {
            object = ((ActivityClientRecord)object2).createdConfig;
        }
        if (object != null) {
            this.mCurDefaultDisplayDpi = ((Configuration)object).densityDpi;
            this.updateDefaultDensity();
            this.handleConfigurationChanged((Configuration)object, null);
        }
        if ((object = this.mActivities.get(((ActivityClientRecord)object2).token)) == null) {
            return;
        }
        activity = ((ActivityClientRecord)object).activity;
        activity.mConfigChangeFlags |= n;
        ((ActivityClientRecord)object).mPreserveWindow = ((ActivityClientRecord)object2).mPreserveWindow;
        object.activity.mChangingConfigurations = true;
        try {
            if (((ActivityClientRecord)object).mPreserveWindow) {
                WindowManagerGlobal.getWindowSession().prepareToReplaceWindows(((ActivityClientRecord)object).token, true);
            }
            this.handleRelaunchActivityInner((ActivityClientRecord)object, n, ((ActivityClientRecord)object2).pendingResults, ((ActivityClientRecord)object2).pendingIntents, pendingTransactionActions, ((ActivityClientRecord)object2).startsNotResumed, ((ActivityClientRecord)object2).overrideConfig, "handleRelaunchActivity");
            if (pendingTransactionActions == null) return;
            pendingTransactionActions.setReportRelaunchToWindowManager(true);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void handleRequestAssistContextExtras(RequestAssistContextExtras object) {
        Bundle bundle;
        void var1_4;
        long l;
        Parcelable parcelable;
        Object object2;
        AssistStructure assistStructure;
        AssistContent assistContent;
        block20 : {
            block19 : {
                int n;
                boolean bl;
                Intent intent;
                block22 : {
                    block21 : {
                        n = ((RequestAssistContextExtras)object).requestType;
                        int n2 = 0;
                        bl = n == 2;
                        if (this.mLastSessionId != ((RequestAssistContextExtras)object).sessionId) {
                            this.mLastSessionId = ((RequestAssistContextExtras)object).sessionId;
                            for (n = this.mLastAssistStructures.size() - 1; n >= 0; --n) {
                                parcelable = (AssistStructure)this.mLastAssistStructures.get(n).get();
                                if (parcelable != null) {
                                    parcelable.clearSendChannel();
                                }
                                this.mLastAssistStructures.remove(n);
                            }
                        }
                        bundle = new Bundle();
                        assistStructure = null;
                        assistContent = bl ? null : new AssistContent();
                        l = SystemClock.uptimeMillis();
                        object2 = this.mActivities.get(((RequestAssistContextExtras)object).activityToken);
                        parcelable = null;
                        if (object2 == null) break block19;
                        if (!bl) {
                            ((ActivityClientRecord)object2).activity.getApplication().dispatchOnProvideAssistData(((ActivityClientRecord)object2).activity, bundle);
                            ((ActivityClientRecord)object2).activity.onProvideAssistData(bundle);
                            parcelable = ((ActivityClientRecord)object2).activity.onProvideReferrer();
                        }
                        if (((RequestAssistContextExtras)object).requestType != 1 && !bl) break block20;
                        assistStructure = new AssistStructure(((ActivityClientRecord)object2).activity, bl, ((RequestAssistContextExtras)object).flags);
                        intent = ((ActivityClientRecord)object2).activity.getIntent();
                        if (((ActivityClientRecord)object2).window == null) break block21;
                        n = n2;
                        if ((object2.window.getAttributes().flags & 8192) != 0) break block22;
                    }
                    n = 1;
                }
                if (intent != null && n != 0) {
                    if (!bl) {
                        intent = new Intent(intent);
                        intent.setFlags(intent.getFlags() & -67);
                        intent.removeUnsafeExtras();
                        assistContent.setDefaultIntent(intent);
                    }
                } else if (!bl) {
                    assistContent.setDefaultIntent(new Intent());
                }
                if (!bl) {
                    ((ActivityClientRecord)object2).activity.onProvideAssistContent(assistContent);
                }
                break block20;
            }
            parcelable = null;
        }
        if (assistStructure == null) {
            assistStructure = new AssistStructure();
        }
        assistStructure.setAcquisitionStartTime(l);
        assistStructure.setAcquisitionEndTime(SystemClock.uptimeMillis());
        this.mLastAssistStructures.add(new WeakReference<AssistStructure>(assistStructure));
        object2 = ActivityTaskManager.getService();
        object = ((RequestAssistContextExtras)object).requestToken;
        try {
            object2.reportAssistContextExtras((IBinder)object, bundle, assistStructure, assistContent, (Uri)parcelable);
            return;
        }
        catch (RemoteException remoteException) {
            throw var1_4.rethrowFromSystemServer();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        throw var1_4.rethrowFromSystemServer();
    }

    @Override
    public void handleResumeActivity(IBinder object, boolean bl, boolean bl2, String object2) {
        Object object3;
        this.unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        object2 = this.performResumeActivity((IBinder)object, bl, (String)object2);
        if (object2 == null) {
            return;
        }
        if (this.mActivitiesToBeDestroyed.containsKey(object)) {
            return;
        }
        object = ((ActivityClientRecord)object2).activity;
        int n = bl2 ? 256 : 0;
        bl = bl2 = ((Activity)object).mStartedActivity ^ true;
        if (!bl2) {
            try {
                bl = ActivityTaskManager.getService().willActivityBeVisible(((Activity)object).getActivityToken());
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        if (((ActivityClientRecord)object2).window == null && !((Activity)object).mFinished && bl) {
            ((ActivityClientRecord)object2).window = ((ActivityClientRecord)object2).activity.getWindow();
            object3 = ((ActivityClientRecord)object2).window.getDecorView();
            ((View)object3).setVisibility(4);
            WindowManager windowManager = ((Activity)object).getWindowManager();
            WindowManager.LayoutParams layoutParams = ((ActivityClientRecord)object2).window.getAttributes();
            ((Activity)object).mDecor = object3;
            layoutParams.type = 1;
            layoutParams.softInputMode |= n;
            if (((ActivityClientRecord)object2).mPreserveWindow) {
                ((Activity)object).mWindowAdded = true;
                ((ActivityClientRecord)object2).mPreserveWindow = false;
                ViewRootImpl viewRootImpl = ((View)object3).getViewRootImpl();
                if (viewRootImpl != null) {
                    viewRootImpl.notifyChildRebuilt();
                }
            }
            if (((Activity)object).mVisibleFromClient) {
                if (!((Activity)object).mWindowAdded) {
                    ((Activity)object).mWindowAdded = true;
                    windowManager.addView((View)object3, layoutParams);
                } else {
                    ((Activity)object).onWindowAttributesChanged(layoutParams);
                }
            }
        } else if (!bl) {
            ((ActivityClientRecord)object2).hideForNow = true;
        }
        ActivityThread.cleanUpPendingRemoveWindows((ActivityClientRecord)object2, false);
        if (!object2.activity.mFinished && bl && object2.activity.mDecor != null && !((ActivityClientRecord)object2).hideForNow) {
            if (((ActivityClientRecord)object2).newConfig != null) {
                this.performConfigurationChangedForActivity((ActivityClientRecord)object2, ((ActivityClientRecord)object2).newConfig);
                ((ActivityClientRecord)object2).newConfig = null;
            }
            object3 = ((ActivityClientRecord)object2).window.getAttributes();
            if ((256 & ((WindowManager.LayoutParams)object3).softInputMode) != n) {
                ((WindowManager.LayoutParams)object3).softInputMode = ((WindowManager.LayoutParams)object3).softInputMode & -257 | n;
                if (object2.activity.mVisibleFromClient) {
                    ((Activity)object).getWindowManager().updateViewLayout(((ActivityClientRecord)object2).window.getDecorView(), (ViewGroup.LayoutParams)object3);
                }
            }
            object2.activity.mVisibleFromServer = true;
            ++this.mNumVisibleActivities;
            if (object2.activity.mVisibleFromClient) {
                ((ActivityClientRecord)object2).activity.makeVisible();
            }
        }
        ((ActivityClientRecord)object2).nextIdle = this.mNewActivities;
        this.mNewActivities = object2;
        Looper.myQueue().addIdleHandler(new Idler());
    }

    @Override
    public void handleSendResult(IBinder object, List<ResultInfo> object2, String string2) {
        if ((object = this.mActivities.get(object)) != null) {
            boolean bl = ((ActivityClientRecord)object).paused ^ true;
            if (!object.activity.mFinished && object.activity.mDecor != null && ((ActivityClientRecord)object).hideForNow && bl) {
                this.updateVisibility((ActivityClientRecord)object, true);
            }
            if (bl) {
                try {
                    object.activity.mCalled = false;
                    this.mInstrumentation.callActivityOnPause(((ActivityClientRecord)object).activity);
                    if (!object.activity.mCalled) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Activity ");
                        stringBuilder.append(((ActivityClientRecord)object).intent.getComponent().toShortString());
                        stringBuilder.append(" did not call through to super.onPause()");
                        SuperNotCalledException superNotCalledException = new SuperNotCalledException(stringBuilder.toString());
                        throw superNotCalledException;
                    }
                }
                catch (Exception exception) {
                    if (!this.mInstrumentation.onException(((ActivityClientRecord)object).activity, exception)) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Unable to pause activity ");
                        ((StringBuilder)object2).append(((ActivityClientRecord)object).intent.getComponent().toShortString());
                        ((StringBuilder)object2).append(": ");
                        ((StringBuilder)object2).append(exception.toString());
                        throw new RuntimeException(((StringBuilder)object2).toString(), exception);
                    }
                }
                catch (SuperNotCalledException superNotCalledException) {
                    throw superNotCalledException;
                }
            }
            this.checkAndBlockForNetworkAccess();
            this.deliverResults((ActivityClientRecord)object, (List<ResultInfo>)object2, string2);
            if (bl) {
                ((ActivityClientRecord)object).activity.performResume(false, string2);
            }
        }
    }

    @Override
    public void handleStartActivity(ActivityClientRecord activityClientRecord, PendingTransactionActions object) {
        Activity activity = activityClientRecord.activity;
        if (activityClientRecord.activity == null) {
            return;
        }
        if (activityClientRecord.stopped) {
            if (activityClientRecord.activity.mFinished) {
                return;
            }
            activity.performStart("handleStartActivity");
            activityClientRecord.setState(2);
            if (object == null) {
                return;
            }
            if (((PendingTransactionActions)object).shouldRestoreInstanceState()) {
                if (activityClientRecord.isPersistable()) {
                    if (activityClientRecord.state != null || activityClientRecord.persistentState != null) {
                        this.mInstrumentation.callActivityOnRestoreInstanceState(activity, activityClientRecord.state, activityClientRecord.persistentState);
                    }
                } else if (activityClientRecord.state != null) {
                    this.mInstrumentation.callActivityOnRestoreInstanceState(activity, activityClientRecord.state);
                }
            }
            if (((PendingTransactionActions)object).shouldCallOnPostCreate()) {
                activity.mCalled = false;
                if (activityClientRecord.isPersistable()) {
                    this.mInstrumentation.callActivityOnPostCreate(activity, activityClientRecord.state, activityClientRecord.persistentState);
                } else {
                    this.mInstrumentation.callActivityOnPostCreate(activity, activityClientRecord.state);
                }
                if (!activity.mCalled) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Activity ");
                    ((StringBuilder)object).append(activityClientRecord.intent.getComponent().toShortString());
                    ((StringBuilder)object).append(" did not call through to super.onPostCreate()");
                    throw new SuperNotCalledException(((StringBuilder)object).toString());
                }
            }
            return;
        }
        throw new IllegalStateException("Can't start activity that is not stopped.");
    }

    @Override
    public void handleStopActivity(IBinder object, boolean bl, int n, PendingTransactionActions pendingTransactionActions, boolean bl2, String string2) {
        object = this.mActivities.get(object);
        Object object2 = ((ActivityClientRecord)object).activity;
        ((Activity)object2).mConfigChangeFlags |= n;
        object2 = new PendingTransactionActions.StopInfo();
        this.performStopActivityInner((ActivityClientRecord)object, (PendingTransactionActions.StopInfo)object2, bl, true, bl2, string2);
        this.updateVisibility((ActivityClientRecord)object, bl);
        if (!((ActivityClientRecord)object).isPreHoneycomb()) {
            QueuedWork.waitToFinish();
        }
        ((PendingTransactionActions.StopInfo)object2).setActivity((ActivityClientRecord)object);
        ((PendingTransactionActions.StopInfo)object2).setState(((ActivityClientRecord)object).state);
        ((PendingTransactionActions.StopInfo)object2).setPersistentState(((ActivityClientRecord)object).persistentState);
        pendingTransactionActions.setStopInfo((PendingTransactionActions.StopInfo)object2);
        this.mSomeActivitiesChanged = true;
    }

    public void handleSystemApplicationInfoChanged(ApplicationInfo applicationInfo) {
        Preconditions.checkState(this.mSystemThread, "Must only be called in the system process");
        this.handleApplicationInfoChanged(applicationInfo);
    }

    @Override
    public void handleTopResumedActivityChanged(IBinder object, boolean bl, String object2) {
        object2 = this.mActivities.get(object);
        if (object2 != null && ((ActivityClientRecord)object2).activity != null) {
            if (((ActivityClientRecord)object2).isTopResumedActivity != bl) {
                ((ActivityClientRecord)object2).isTopResumedActivity = bl;
                if (((ActivityClientRecord)object2).getLifecycleState() == 3) {
                    this.reportTopResumedActivityChanged((ActivityClientRecord)object2, bl, "topStateChangedWhenResumed");
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Activity top position already set to onTop=");
            ((StringBuilder)object).append(bl);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Not found target activity to report position change for token: ");
        ((StringBuilder)object2).append(object);
        Slog.w("ActivityThread", ((StringBuilder)object2).toString());
    }

    public void handleTranslucentConversionComplete(IBinder object, boolean bl) {
        if ((object = this.mActivities.get(object)) != null) {
            ((ActivityClientRecord)object).activity.onTranslucentConversionComplete(bl);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    final void handleUnstableProviderDied(IBinder iBinder, boolean bl) {
        ArrayMap<ProviderKey, ProviderClientRecord> arrayMap = this.mProviderMap;
        synchronized (arrayMap) {
            this.handleUnstableProviderDiedLocked(iBinder, bl);
            return;
        }
    }

    final void handleUnstableProviderDiedLocked(IBinder iBinder, boolean bl) {
        ProviderRefCount providerRefCount = this.mProviderRefCountMap.get(iBinder);
        if (providerRefCount != null) {
            this.mProviderRefCountMap.remove(iBinder);
            for (int i = this.mProviderMap.size() - 1; i >= 0; --i) {
                ProviderClientRecord providerClientRecord = this.mProviderMap.valueAt(i);
                if (providerClientRecord == null || providerClientRecord.mProvider.asBinder() != iBinder) continue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Removing dead content provider:");
                stringBuilder.append(providerClientRecord.mProvider.toString());
                Slog.i("ActivityThread", stringBuilder.toString());
                this.mProviderMap.removeAt(i);
            }
            if (bl) {
                try {
                    ActivityManager.getService().unstableProviderDied(providerRefCount.holder.connection);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
        }
    }

    @Override
    public void handleWindowVisibility(IBinder iBinder, boolean bl) {
        Object object = this.mActivities.get(iBinder);
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("handleWindowVisibility: no activity for token ");
            ((StringBuilder)object).append(iBinder);
            Log.w("ActivityThread", ((StringBuilder)object).toString());
            return;
        }
        if (!bl && !((ActivityClientRecord)object).stopped) {
            this.performStopActivityInner((ActivityClientRecord)object, null, bl, false, false, "handleWindowVisibility");
        } else if (bl && ((ActivityClientRecord)object).getLifecycleState() == 5) {
            this.unscheduleGcIdler();
            ((ActivityClientRecord)object).activity.performRestart(true, "handleWindowVisibility");
            ((ActivityClientRecord)object).setState(2);
        }
        if (object.activity.mDecor != null) {
            this.updateVisibility((ActivityClientRecord)object, bl);
        }
        this.mSomeActivitiesChanged = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void installSystemApplicationInfo(ApplicationInfo object, ClassLoader classLoader) {
        synchronized (this) {
            this.getSystemContext().installSystemApplicationInfo((ApplicationInfo)object, classLoader);
            this.getSystemUiContext().installSystemApplicationInfo((ApplicationInfo)object, classLoader);
            this.mProfiler = object = new Profiler();
            return;
        }
    }

    @UnsupportedAppUsage
    public final void installSystemProviders(List<ProviderInfo> list) {
        if (list != null) {
            this.installContentProviders(this.mInitialApplication, list);
        }
    }

    public boolean isProfiling() {
        Profiler profiler = this.mProfiler;
        boolean bl = profiler != null && profiler.profileFile != null && this.mProfiler.profileFd == null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$attach$1$ActivityThread(Configuration configuration) {
        ResourcesManager resourcesManager = this.mResourcesManager;
        synchronized (resourcesManager) {
            if (this.mResourcesManager.applyConfigurationToResourcesLocked(configuration, null)) {
                this.updateLocaleListFromAppContext(this.mInitialApplication.getApplicationContext(), this.mResourcesManager.getConfiguration().getLocales());
                if (this.mPendingConfiguration == null || this.mPendingConfiguration.isOtherSeqNewer(configuration)) {
                    this.mPendingConfiguration = configuration;
                    this.sendMessage(118, configuration);
                }
            }
            return;
        }
    }

    public void onNewActivityOptions(IBinder object, ActivityOptions activityOptions) {
        if ((object = this.mActivities.get(object)) != null) {
            ((ActivityClientRecord)object).activity.onNewActivityOptions(activityOptions);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public final LoadedApk peekPackageInfo(String weakReference, boolean bl) {
        ResourcesManager resourcesManager = this.mResourcesManager;
        synchronized (resourcesManager) {
            weakReference = bl ? this.mPackages.get(weakReference) : this.mResourcePackages.get(weakReference);
            if (weakReference == null) return null;
            return (LoadedApk)weakReference.get();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    ActivityClientRecord performDestroyActivity(IBinder var1_1, boolean var2_3, int var3_4, boolean var4_5, String var5_6) {
        block13 : {
            block12 : {
                var6_7 = this.mActivities.get(var1_1);
                var5_6 = null;
                if (var6_7 == null) break block13;
                var5_6 = var6_7.activity.getClass();
                var7_8 = var6_7.activity;
                var7_8.mConfigChangeFlags |= var3_4;
                if (var2_3) {
                    var6_7.activity.mFinished = true;
                }
                this.performPauseActivityIfNeeded(var6_7, "destroy");
                if (!var6_7.stopped) {
                    this.callActivityOnStop(var6_7, false, "destroy");
                }
                if (var4_5) {
                    try {
                        var6_7.lastNonConfigurationInstances = var6_7.activity.retainNonConfigurationInstances();
                    }
                    catch (Exception var7_9) {
                        if (this.mInstrumentation.onException(var6_7.activity, var7_9)) break block12;
                        var1_1 = new StringBuilder();
                        var1_1.append("Unable to retain activity ");
                        var1_1.append(var6_7.intent.getComponent().toShortString());
                        var1_1.append(": ");
                        var1_1.append(var7_9.toString());
                        throw new RuntimeException(var1_1.toString(), var7_9);
                    }
                }
            }
            try {
                var6_7.activity.mCalled = false;
                this.mInstrumentation.callActivityOnDestroy(var6_7.activity);
                if (!var6_7.activity.mCalled) {
                    var8_11 = new StringBuilder();
                    var8_11.append("Activity ");
                    var8_11.append(ActivityThread.safeToComponentShortString(var6_7.intent));
                    var8_11.append(" did not call through to super.onDestroy()");
                    var7_8 = new SuperNotCalledException(var8_11.toString());
                    throw var7_8;
                }
                if (var6_7.window == null) ** GOTO lbl47
                var6_7.window.closeAllPanels();
            }
            catch (Exception var7_10) {
                if (!this.mInstrumentation.onException(var6_7.activity, var7_10)) ** GOTO lbl49
            }
lbl47: // 3 sources:
            var6_7.setState(6);
            break block13;
lbl49: // 1 sources:
            var1_1 = new StringBuilder();
            var1_1.append("Unable to destroy activity ");
            var1_1.append(ActivityThread.safeToComponentShortString(var6_7.intent));
            var1_1.append(": ");
            var1_1.append(var7_10.toString());
            throw new RuntimeException(var1_1.toString(), var7_10);
            catch (SuperNotCalledException var1_2) {
                throw var1_2;
            }
            ** GOTO lbl47
        }
        this.schedulePurgeIdler();
        var7_8 = this.mResourcesManager;
        // MONITORENTER : var7_8
        this.mActivities.remove(var1_1);
        // MONITOREXIT : var7_8
        StrictMode.decrementExpectedActivityCount(var5_6);
        return var6_7;
    }

    final Bundle performPauseActivity(IBinder object, boolean bl, String string2, PendingTransactionActions pendingTransactionActions) {
        object = (object = this.mActivities.get(object)) != null ? this.performPauseActivity((ActivityClientRecord)object, bl, string2, pendingTransactionActions) : null;
        return object;
    }

    @Override
    public void performRestartActivity(IBinder object, boolean bl) {
        object = this.mActivities.get(object);
        if (((ActivityClientRecord)object).stopped) {
            ((ActivityClientRecord)object).activity.performRestart(bl, "performRestartActivity");
            if (bl) {
                ((ActivityClientRecord)object).setState(2);
            }
        }
    }

    @VisibleForTesting
    public ActivityClientRecord performResumeActivity(IBinder object, boolean bl, String object2) {
        if ((object = this.mActivities.get(object)) != null && !object.activity.mFinished) {
            Exception exception2;
            block8 : {
                if (((ActivityClientRecord)object).getLifecycleState() == 3) {
                    if (!bl) {
                        object2 = new IllegalStateException("Trying to resume activity which is already resumed");
                        Slog.e("ActivityThread", ((Throwable)object2).getMessage(), (Throwable)object2);
                        Slog.e("ActivityThread", ((ActivityClientRecord)object).getStateString());
                    }
                    return null;
                }
                if (bl) {
                    ((ActivityClientRecord)object).hideForNow = false;
                    object.activity.mStartedActivity = false;
                }
                try {
                    ((ActivityClientRecord)object).activity.onStateNotSaved();
                    object.activity.mFragments.noteStateNotSaved();
                    this.checkAndBlockForNetworkAccess();
                    if (((ActivityClientRecord)object).pendingIntents != null) {
                        this.deliverNewIntents((ActivityClientRecord)object, ((ActivityClientRecord)object).pendingIntents);
                        ((ActivityClientRecord)object).pendingIntents = null;
                    }
                    if (((ActivityClientRecord)object).pendingResults != null) {
                        this.deliverResults((ActivityClientRecord)object, ((ActivityClientRecord)object).pendingResults, (String)object2);
                        ((ActivityClientRecord)object).pendingResults = null;
                    }
                    ((ActivityClientRecord)object).activity.performResume(((ActivityClientRecord)object).startsNotResumed, (String)object2);
                    ((ActivityClientRecord)object).state = null;
                    ((ActivityClientRecord)object).persistentState = null;
                    ((ActivityClientRecord)object).setState(3);
                    this.reportTopResumedActivityChanged((ActivityClientRecord)object, ((ActivityClientRecord)object).isTopResumedActivity, "topWhenResuming");
                }
                catch (Exception exception2) {
                    if (!this.mInstrumentation.onException(((ActivityClientRecord)object).activity, exception2)) break block8;
                }
                return object;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to resume activity ");
            stringBuilder.append(((ActivityClientRecord)object).intent.getComponent().toShortString());
            stringBuilder.append(": ");
            stringBuilder.append(exception2.toString());
            throw new RuntimeException(stringBuilder.toString(), exception2);
        }
        return null;
    }

    @UnsupportedAppUsage
    final void performStopActivity(IBinder iBinder, boolean bl, String string2) {
        this.performStopActivityInner(this.mActivities.get(iBinder), null, false, bl, false, string2);
    }

    final void performUserLeavingActivity(ActivityClientRecord activityClientRecord) {
        this.mInstrumentation.callActivityOnUserLeaving(activityClientRecord.activity);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ActivityClientRecord prepareRelaunchActivity(IBinder iBinder, List<ResultInfo> list, List<ReferrerIntent> list2, int n, MergedConfiguration mergedConfiguration, boolean bl) {
        int n2;
        ActivityClientRecord activityClientRecord = null;
        int n3 = 0;
        ResourcesManager resourcesManager = this.mResourcesManager;
        synchronized (resourcesManager) {
            ActivityClientRecord activityClientRecord2;
            n2 = 0;
            do {
                activityClientRecord2 = activityClientRecord;
                if (n2 >= this.mRelaunchingActivities.size()) break;
                ActivityClientRecord activityClientRecord3 = this.mRelaunchingActivities.get(n2);
                if (activityClientRecord3.token == iBinder) {
                    activityClientRecord = activityClientRecord3;
                    if (list != null) {
                        if (activityClientRecord3.pendingResults != null) {
                            activityClientRecord3.pendingResults.addAll(list);
                        } else {
                            activityClientRecord3.pendingResults = list;
                        }
                    }
                    activityClientRecord2 = activityClientRecord;
                    if (list2 == null) break;
                    if (activityClientRecord3.pendingIntents != null) {
                        activityClientRecord3.pendingIntents.addAll(list2);
                        activityClientRecord2 = activityClientRecord;
                        break;
                    }
                    activityClientRecord3.pendingIntents = list2;
                    activityClientRecord2 = activityClientRecord;
                    break;
                }
                ++n2;
            } while (true);
            activityClientRecord = activityClientRecord2;
            n2 = n3;
            if (activityClientRecord2 == null) {
                activityClientRecord = new ActivityClientRecord();
                activityClientRecord.token = iBinder;
                activityClientRecord.pendingResults = list;
                activityClientRecord.pendingIntents = list2;
                activityClientRecord.mPreserveWindow = bl;
                this.mRelaunchingActivities.add(activityClientRecord);
                n2 = 1;
            }
            activityClientRecord.createdConfig = mergedConfiguration.getGlobalConfiguration();
            activityClientRecord.overrideConfig = mergedConfiguration.getOverrideConfiguration();
            activityClientRecord.pendingConfigChanges |= n;
        }
        if (n2 == 0) return null;
        return activityClientRecord;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void registerOnActivityPausedListener(Activity activity, OnActivityPausedListener onActivityPausedListener) {
        ArrayMap<Activity, ArrayList<OnActivityPausedListener>> arrayMap = this.mOnPauseListeners;
        synchronized (arrayMap) {
            ArrayList<OnActivityPausedListener> arrayList;
            ArrayList<OnActivityPausedListener> arrayList2 = arrayList = this.mOnPauseListeners.get(activity);
            if (arrayList == null) {
                arrayList2 = new ArrayList<OnActivityPausedListener>();
                this.mOnPauseListeners.put(activity, arrayList2);
            }
            arrayList2.add(onActivityPausedListener);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public final boolean releaseProvider(IContentProvider object, boolean bl) {
        int n = 0;
        if (object == null) {
            return false;
        }
        Object object2 = object.asBinder();
        object = this.mProviderMap;
        synchronized (object) {
            Object object3;
            object2 = this.mProviderRefCountMap.get(object2);
            if (object2 == null) {
                return false;
            }
            int n2 = 0;
            if (bl) {
                if (((ProviderRefCount)object2).stableCount == 0) {
                    return false;
                }
                --((ProviderRefCount)object2).stableCount;
                if (((ProviderRefCount)object2).stableCount == 0) {
                    n2 = ((ProviderRefCount)object2).unstableCount;
                    n2 = n2 == 0 ? 1 : 0;
                    try {
                        IActivityManager iActivityManager = ActivityManager.getService();
                        object3 = object2.holder.connection;
                        if (n2 != 0) {
                            n = 1;
                        }
                        iActivityManager.refContentProvider((IBinder)object3, -1, n);
                    }
                    catch (RemoteException remoteException) {}
                }
            } else {
                if (((ProviderRefCount)object2).unstableCount == 0) {
                    return false;
                }
                --((ProviderRefCount)object2).unstableCount;
                if (((ProviderRefCount)object2).unstableCount == 0) {
                    n2 = ((ProviderRefCount)object2).stableCount;
                    n2 = n2 == 0 ? 1 : 0;
                    n2 = n = n2;
                    if (n == 0) {
                        try {
                            ActivityManager.getService().refContentProvider(object2.holder.connection, 0, -1);
                            n2 = n;
                        }
                        catch (RemoteException remoteException) {
                            n2 = n;
                        }
                    }
                }
            }
            if (n2 != 0) {
                if (!((ProviderRefCount)object2).removePending) {
                    ((ProviderRefCount)object2).removePending = true;
                    object2 = this.mH.obtainMessage(131, object2);
                    this.mH.sendMessageDelayed((Message)object2, 1000L);
                } else {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Duplicate remove pending of provider ");
                    ((StringBuilder)object3).append(object2.holder.info.name);
                    Slog.w("ActivityThread", ((StringBuilder)object3).toString());
                }
            }
            return true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void reportRelaunch(IBinder object, PendingTransactionActions pendingTransactionActions) {
        try {
            ActivityTaskManager.getService().activityRelaunched((IBinder)object);
            object = this.mActivities.get(object);
            if (!pendingTransactionActions.shouldReportRelaunchToWindowManager() || object == null) return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        if (((ActivityClientRecord)object).window == null) return;
        ((ActivityClientRecord)object).window.reportActivityRelaunched();
    }

    @Override
    public void reportStop(PendingTransactionActions pendingTransactionActions) {
        this.mH.post(pendingTransactionActions.getStopInfo());
    }

    public final ActivityInfo resolveActivityInfo(Intent intent) {
        ActivityInfo activityInfo = intent.resolveActivityInfo(this.mInitialApplication.getPackageManager(), 1024);
        if (activityInfo == null) {
            Instrumentation.checkStartActivityResult(-92, intent);
        }
        return activityInfo;
    }

    final void scheduleContextCleanup(ContextImpl contextImpl, String string2, String string3) {
        ContextCleanupInfo contextCleanupInfo = new ContextCleanupInfo();
        contextCleanupInfo.context = contextImpl;
        contextCleanupInfo.who = string2;
        contextCleanupInfo.what = string3;
        this.sendMessage(119, contextCleanupInfo);
    }

    @UnsupportedAppUsage
    void scheduleGcIdler() {
        if (!this.mGcIdlerScheduled) {
            this.mGcIdlerScheduled = true;
            Looper.myQueue().addIdleHandler(this.mGcIdler);
        }
        this.mH.removeMessages(120);
    }

    void schedulePurgeIdler() {
        if (!this.mPurgeIdlerScheduled) {
            this.mPurgeIdlerScheduled = true;
            Looper.myQueue().addIdleHandler(this.mPurgeIdler);
        }
        this.mH.removeMessages(161);
    }

    void scheduleRelaunchActivity(IBinder iBinder) {
        this.mH.removeMessages(160, iBinder);
        this.sendMessage(160, iBinder);
    }

    @UnsupportedAppUsage
    public final void sendActivityResult(IBinder object, String string2, int n, int n2, Intent intent) {
        ArrayList<ResultInfo> arrayList = new ArrayList<ResultInfo>();
        arrayList.add(new ResultInfo(string2, n, n2, intent));
        object = ClientTransaction.obtain(this.mAppThread, (IBinder)object);
        ((ClientTransaction)object).addCallback(ActivityResultItem.obtain(arrayList));
        try {
            this.mAppThread.scheduleTransaction((ClientTransaction)object);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    void sendMessage(int n, Object object) {
        this.sendMessage(n, object, 0, 0, false);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public final Activity startActivityNow(Activity activity, String string2, Intent intent, ActivityInfo activityInfo, IBinder iBinder, Bundle bundle, Activity.NonConfigurationInstances nonConfigurationInstances, IBinder iBinder2) {
        ActivityClientRecord activityClientRecord = new ActivityClientRecord();
        activityClientRecord.token = iBinder;
        activityClientRecord.assistToken = iBinder2;
        activityClientRecord.ident = 0;
        activityClientRecord.intent = intent;
        activityClientRecord.state = bundle;
        activityClientRecord.parent = activity;
        activityClientRecord.embeddedID = string2;
        activityClientRecord.activityInfo = activityInfo;
        activityClientRecord.lastNonConfigurationInstances = nonConfigurationInstances;
        return this.performLaunchActivity(activityClientRecord, null);
    }

    public void stopProfiling() {
        Profiler profiler = this.mProfiler;
        if (profiler != null) {
            profiler.stopProfiling();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void unregisterOnActivityPausedListener(Activity object, OnActivityPausedListener onActivityPausedListener) {
        ArrayMap<Activity, ArrayList<OnActivityPausedListener>> arrayMap = this.mOnPauseListeners;
        synchronized (arrayMap) {
            object = this.mOnPauseListeners.get(object);
            if (object != null) {
                ((ArrayList)object).remove(onActivityPausedListener);
            }
            return;
        }
    }

    void unscheduleGcIdler() {
        if (this.mGcIdlerScheduled) {
            this.mGcIdlerScheduled = false;
            Looper.myQueue().removeIdleHandler(this.mGcIdler);
        }
        this.mH.removeMessages(120);
    }

    void unschedulePurgeIdler() {
        if (this.mPurgeIdlerScheduled) {
            this.mPurgeIdlerScheduled = false;
            Looper.myQueue().removeIdleHandler(this.mPurgeIdler);
        }
        this.mH.removeMessages(161);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void updatePendingActivityConfiguration(IBinder object, Configuration configuration) {
        ResourcesManager resourcesManager = this.mResourcesManager;
        // MONITORENTER : resourcesManager
        object = this.mActivities.get(object);
        // MONITOREXIT : resourcesManager
        if (object == null) {
            return;
        }
        // MONITORENTER : object
        ((ActivityClientRecord)object).mPendingOverrideConfig = configuration;
        // MONITOREXIT : object
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void updatePendingConfiguration(Configuration configuration) {
        ResourcesManager resourcesManager = this.mResourcesManager;
        synchronized (resourcesManager) {
            if (this.mPendingConfiguration == null || this.mPendingConfiguration.isOtherSeqNewer(configuration)) {
                this.mPendingConfiguration = configuration;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void updateProcessState(int n, boolean bl) {
        ApplicationThread applicationThread = this.mAppThread;
        synchronized (applicationThread) {
            if (this.mLastProcessState == n) {
                return;
            }
            this.mLastProcessState = n;
            if (n == 2 && this.mNumLaunchingActivities.get() > 0) {
                this.mPendingProcessState = n;
                H h = this.mH;
                _$$Lambda$ActivityThread$A4ykhsPb8qV3ffTqpQDklHSMDJ0 _$$Lambda$ActivityThread$A4ykhsPb8qV3ffTqpQDklHSMDJ0 = new _$$Lambda$ActivityThread$A4ykhsPb8qV3ffTqpQDklHSMDJ0(this);
                h.postDelayed(_$$Lambda$ActivityThread$A4ykhsPb8qV3ffTqpQDklHSMDJ0, 1000L);
            } else {
                this.mPendingProcessState = -1;
                this.updateVmProcessState(n);
            }
            return;
        }
    }

    public static final class ActivityClientRecord {
        @UnsupportedAppUsage
        Activity activity;
        @UnsupportedAppUsage
        ActivityInfo activityInfo;
        public IBinder assistToken;
        @UnsupportedAppUsage
        CompatibilityInfo compatInfo;
        ViewRootImpl.ActivityConfigCallback configCallback;
        Configuration createdConfig;
        String embeddedID;
        boolean hideForNow;
        int ident;
        @UnsupportedAppUsage
        Intent intent;
        public final boolean isForward;
        boolean isTopResumedActivity;
        Activity.NonConfigurationInstances lastNonConfigurationInstances;
        boolean lastReportedTopResumedState;
        private int mLifecycleState = 0;
        @GuardedBy(value={"this"})
        private Configuration mPendingOverrideConfig;
        Window mPendingRemoveWindow;
        WindowManager mPendingRemoveWindowManager;
        @UnsupportedAppUsage
        boolean mPreserveWindow;
        Configuration newConfig;
        ActivityClientRecord nextIdle;
        Configuration overrideConfig;
        @UnsupportedAppUsage
        public LoadedApk packageInfo;
        Activity parent;
        @UnsupportedAppUsage
        boolean paused;
        int pendingConfigChanges;
        List<ReferrerIntent> pendingIntents;
        List<ResultInfo> pendingResults;
        PersistableBundle persistentState;
        ProfilerInfo profilerInfo;
        String referrer;
        boolean startsNotResumed;
        Bundle state;
        @UnsupportedAppUsage
        boolean stopped;
        private Configuration tmpConfig = new Configuration();
        @UnsupportedAppUsage
        public IBinder token;
        IVoiceInteractor voiceInteractor;
        Window window;

        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        @VisibleForTesting
        public ActivityClientRecord() {
            this.isForward = false;
            this.init();
        }

        public ActivityClientRecord(IBinder iBinder, Intent intent, int n, ActivityInfo activityInfo, Configuration configuration, CompatibilityInfo compatibilityInfo, String string2, IVoiceInteractor iVoiceInteractor, Bundle bundle, PersistableBundle persistableBundle, List<ResultInfo> list, List<ReferrerIntent> list2, boolean bl, ProfilerInfo profilerInfo, ClientTransactionHandler clientTransactionHandler, IBinder iBinder2) {
            this.token = iBinder;
            this.assistToken = iBinder2;
            this.ident = n;
            this.intent = intent;
            this.referrer = string2;
            this.voiceInteractor = iVoiceInteractor;
            this.activityInfo = activityInfo;
            this.compatInfo = compatibilityInfo;
            this.state = bundle;
            this.persistentState = persistableBundle;
            this.pendingResults = list;
            this.pendingIntents = list2;
            this.isForward = bl;
            this.profilerInfo = profilerInfo;
            this.overrideConfig = configuration;
            this.packageInfo = clientTransactionHandler.getPackageInfoNoCheck(this.activityInfo.applicationInfo, compatibilityInfo);
            this.init();
        }

        private void init() {
            this.parent = null;
            this.embeddedID = null;
            this.paused = false;
            this.stopped = false;
            this.hideForNow = false;
            this.nextIdle = null;
            this.configCallback = new _$$Lambda$ActivityThread$ActivityClientRecord$HOrG1qglSjSUHSjKBn2rXtX0gGg(this);
        }

        private boolean isPreHoneycomb() {
            Activity activity = this.activity;
            boolean bl = activity != null && activity.getApplicationInfo().targetSdkVersion < 11;
            return bl;
        }

        private boolean isPreP() {
            Activity activity = this.activity;
            boolean bl = activity != null && activity.getApplicationInfo().targetSdkVersion < 28;
            return bl;
        }

        public int getLifecycleState() {
            return this.mLifecycleState;
        }

        public String getStateString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ActivityClientRecord{");
            stringBuilder.append("paused=");
            stringBuilder.append(this.paused);
            stringBuilder.append(", stopped=");
            stringBuilder.append(this.stopped);
            stringBuilder.append(", hideForNow=");
            stringBuilder.append(this.hideForNow);
            stringBuilder.append(", startsNotResumed=");
            stringBuilder.append(this.startsNotResumed);
            stringBuilder.append(", isForward=");
            stringBuilder.append(this.isForward);
            stringBuilder.append(", pendingConfigChanges=");
            stringBuilder.append(this.pendingConfigChanges);
            stringBuilder.append(", preserveWindow=");
            stringBuilder.append(this.mPreserveWindow);
            if (this.activity != null) {
                stringBuilder.append(", Activity{");
                stringBuilder.append("resumed=");
                stringBuilder.append(this.activity.mResumed);
                stringBuilder.append(", stopped=");
                stringBuilder.append(this.activity.mStopped);
                stringBuilder.append(", finished=");
                stringBuilder.append(this.activity.isFinishing());
                stringBuilder.append(", destroyed=");
                stringBuilder.append(this.activity.isDestroyed());
                stringBuilder.append(", startedActivity=");
                stringBuilder.append(this.activity.mStartedActivity);
                stringBuilder.append(", changingConfigurations=");
                stringBuilder.append(this.activity.mChangingConfigurations);
                stringBuilder.append("}");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public boolean isPersistable() {
            boolean bl = this.activityInfo.persistableMode == 2;
            return bl;
        }

        public boolean isVisibleFromServer() {
            Activity activity = this.activity;
            boolean bl = activity != null && activity.mVisibleFromServer;
            return bl;
        }

        public /* synthetic */ void lambda$init$0$ActivityThread$ActivityClientRecord(Configuration configuration, int n) {
            Activity activity = this.activity;
            if (activity != null) {
                activity.mMainThread.handleActivityConfigurationChanged(this.token, configuration, n);
                return;
            }
            throw new IllegalStateException("Received config update for non-existing activity");
        }

        public void setState(int n) {
            this.mLifecycleState = n;
            if ((n = this.mLifecycleState) != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n == 5) {
                                this.paused = true;
                                this.stopped = true;
                            }
                        } else {
                            this.paused = true;
                            this.stopped = false;
                        }
                    } else {
                        this.paused = false;
                        this.stopped = false;
                    }
                } else {
                    this.paused = true;
                    this.stopped = false;
                }
            } else {
                this.paused = true;
                this.stopped = true;
            }
        }

        public String toString() {
            Object object = this.intent;
            object = object != null ? ((Intent)object).getComponent() : null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ActivityRecord{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" token=");
            stringBuilder.append(this.token);
            stringBuilder.append(" ");
            object = object == null ? "no component name" : ((ComponentName)object).toShortString();
            stringBuilder.append((String)object);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    private static class AndroidOs
    extends ForwardingOs {
        private AndroidOs(Os os) {
            super(os);
        }

        private void deleteDeprecatedDataPath(String object) throws ErrnoException {
            Uri uri = ContentResolver.translateDeprecatedDataPath((String)object);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Redirecting ");
            stringBuilder.append((String)object);
            stringBuilder.append(" to ");
            stringBuilder.append(uri);
            Log.v(ActivityThread.TAG, stringBuilder.toString());
            object = ActivityThread.currentActivityThread().getApplication().getContentResolver();
            try {
                if (((ContentResolver)object).delete(uri, null, null) != 0) {
                    return;
                }
                object = new FileNotFoundException();
                throw object;
            }
            catch (FileNotFoundException fileNotFoundException) {
                throw new ErrnoException(fileNotFoundException.getMessage(), OsConstants.ENOENT);
            }
            catch (SecurityException securityException) {
                throw new ErrnoException(securityException.getMessage(), OsConstants.EACCES);
            }
        }

        public static void install() {
            Os os;
            if (!ContentResolver.DEPRECATE_DATA_COLUMNS) {
                return;
            }
            while (!Os.compareAndSetDefault((Os)(os = Os.getDefault()), (Os)new AndroidOs(os))) {
            }
        }

        private FileDescriptor openDeprecatedDataPath(String object, int n) throws ErrnoException {
            Uri uri = ContentResolver.translateDeprecatedDataPath((String)object);
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("Redirecting ");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(" to ");
            ((StringBuilder)object2).append(uri);
            Log.v(ActivityThread.TAG, ((StringBuilder)object2).toString());
            object = ActivityThread.currentActivityThread().getApplication().getContentResolver();
            try {
                object2 = new FileDescriptor();
                object2.setInt$(((ContentResolver)object).openFileDescriptor(uri, FileUtils.translateModePosixToString(n)).detachFd());
                return object2;
            }
            catch (FileNotFoundException fileNotFoundException) {
                throw new ErrnoException(fileNotFoundException.getMessage(), OsConstants.ENOENT);
            }
            catch (SecurityException securityException) {
                throw new ErrnoException(securityException.getMessage(), OsConstants.EACCES);
            }
        }

        public boolean access(String string2, int n) throws ErrnoException {
            if (string2 != null && string2.startsWith("/mnt/content/")) {
                IoUtils.closeQuietly((FileDescriptor)this.openDeprecatedDataPath(string2, FileUtils.translateModeAccessToPosix(n)));
                return true;
            }
            return super.access(string2, n);
        }

        public FileDescriptor open(String string2, int n, int n2) throws ErrnoException {
            if (string2 != null && string2.startsWith("/mnt/content/")) {
                return this.openDeprecatedDataPath(string2, n2);
            }
            return super.open(string2, n, n2);
        }

        public void remove(String string2) throws ErrnoException {
            if (string2 != null && string2.startsWith("/mnt/content/")) {
                this.deleteDeprecatedDataPath(string2);
            } else {
                super.remove(string2);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public void rename(String object, String string2) throws ErrnoException {
            try {
                super.rename((String)object, string2);
                return;
            }
            catch (ErrnoException errnoException) {
                if (errnoException.errno != OsConstants.EXDEV) throw errnoException;
                Object object2 = new StringBuilder();
                ((StringBuilder)object2).append("Recovering failed rename ");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(" to ");
                ((StringBuilder)object2).append(string2);
                Log.v(ActivityThread.TAG, ((StringBuilder)object2).toString());
                try {
                    object2 = new File((String)object);
                    object2 = ((File)object2).toPath();
                    object = new File(string2);
                    Files.move((Path)object2, ((File)object).toPath(), new CopyOption[0]);
                    return;
                }
                catch (IOException iOException) {
                    throw errnoException;
                }
            }
        }

        public StructStat stat(String object) throws ErrnoException {
            if (object != null && ((String)object).startsWith("/mnt/content/")) {
                object = this.openDeprecatedDataPath((String)object, OsConstants.O_RDONLY);
                try {
                    StructStat structStat = android.system.Os.fstat((FileDescriptor)object);
                    return structStat;
                }
                finally {
                    IoUtils.closeQuietly((FileDescriptor)object);
                }
            }
            return super.stat((String)object);
        }

        public void unlink(String string2) throws ErrnoException {
            if (string2 != null && string2.startsWith("/mnt/content/")) {
                this.deleteDeprecatedDataPath(string2);
            } else {
                super.unlink(string2);
            }
        }
    }

    static final class AppBindData {
        @UnsupportedAppUsage
        ApplicationInfo appInfo;
        AutofillOptions autofillOptions;
        String buildSerial;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        CompatibilityInfo compatInfo;
        Configuration config;
        ContentCaptureOptions contentCaptureOptions;
        int debugMode;
        boolean enableBinderTracking;
        @UnsupportedAppUsage
        LoadedApk info;
        ProfilerInfo initProfilerInfo;
        @UnsupportedAppUsage
        Bundle instrumentationArgs;
        ComponentName instrumentationName;
        IUiAutomationConnection instrumentationUiAutomationConnection;
        IInstrumentationWatcher instrumentationWatcher;
        @UnsupportedAppUsage
        boolean persistent;
        @UnsupportedAppUsage
        String processName;
        @UnsupportedAppUsage
        List<ProviderInfo> providers;
        @UnsupportedAppUsage
        boolean restrictedBackupMode;
        boolean trackAllocation;

        AppBindData() {
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AppBindData{appInfo=");
            stringBuilder.append(this.appInfo);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    private class ApplicationThread
    extends IApplicationThread.Stub {
        private static final String DB_INFO_FORMAT = "  %8s %8s %14s %14s  %s";

        private ApplicationThread() {
        }

        private void dumpDatabaseInfo(ParcelFileDescriptor closeable, String[] arrstring, boolean bl) {
            closeable = new FastPrintWriter(new FileOutputStream(((ParcelFileDescriptor)closeable).getFileDescriptor()));
            SQLiteDebug.dump(new PrintWriterPrinter((PrintWriter)closeable), arrstring, bl);
            ((PrintWriter)closeable).flush();
        }

        private void dumpMemInfo(ProtoOutputStream protoOutputStream, Debug.MemoryInfo object, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
            block3 : {
                long l = Debug.getNativeHeapSize() / 1024L;
                long l2 = Debug.getNativeHeapAllocatedSize() / 1024L;
                long l3 = Debug.getNativeHeapFreeSize() / 1024L;
                Object object2 = Runtime.getRuntime();
                object2.gc();
                long l4 = object2.totalMemory() / 1024L;
                long l5 = object2.freeMemory() / 1024L;
                bl = false;
                object2 = VMDebug.countInstancesOfClasses((Class[])new Class[]{ContextImpl.class, Activity.class, WebView.class, OpenSSLSocketImpl.class}, (boolean)true);
                long l6 = object2[0];
                long l7 = object2[1];
                long l8 = object2[2];
                long l9 = object2[3];
                long l10 = ViewDebug.getViewInstanceCount();
                long l11 = ViewDebug.getViewRootImplCount();
                int n = AssetManager.getGlobalAssetCount();
                int n2 = AssetManager.getGlobalAssetManagerCount();
                int n3 = Debug.getBinderLocalObjectCount();
                int n4 = Debug.getBinderProxyObjectCount();
                int n5 = Debug.getBinderDeathObjectCount();
                long l12 = Parcel.getGlobalAllocSize();
                long l13 = Parcel.getGlobalAllocCount();
                SQLiteDebug.PagerStats pagerStats = SQLiteDebug.getDatabaseInfo();
                long l14 = protoOutputStream.start(1146756268033L);
                protoOutputStream.write(1120986464257L, Process.myPid());
                object2 = ActivityThread.this.mBoundApplication != null ? ActivityThread.this.mBoundApplication.processName : "unknown";
                protoOutputStream.write(1138166333442L, (String)object2);
                ActivityThread.dumpMemInfoTable(protoOutputStream, (Debug.MemoryInfo)object, bl2, bl3, l, l2, l3, l4, l4 - l5, l5);
                protoOutputStream.end(l14);
                l2 = protoOutputStream.start(1146756268034L);
                protoOutputStream.write(1120986464257L, l10);
                protoOutputStream.write(1120986464258L, l11);
                protoOutputStream.write(1120986464259L, l6);
                protoOutputStream.write(1120986464260L, l7);
                protoOutputStream.write(1120986464261L, n);
                protoOutputStream.write(1120986464262L, n2);
                protoOutputStream.write(1120986464263L, n3);
                protoOutputStream.write(1120986464264L, n4);
                protoOutputStream.write(1112396529673L, l12 / 1024L);
                protoOutputStream.write(1120986464266L, l13);
                protoOutputStream.write(1120986464267L, n5);
                protoOutputStream.write(1120986464268L, l9);
                protoOutputStream.write(1120986464269L, l8);
                protoOutputStream.end(l2);
                l12 = protoOutputStream.start(1146756268035L);
                object = pagerStats;
                protoOutputStream.write(1120986464257L, ((SQLiteDebug.PagerStats)object).memoryUsed / 1024);
                protoOutputStream.write(1120986464258L, ((SQLiteDebug.PagerStats)object).pageCacheOverflow / 1024);
                protoOutputStream.write(1120986464259L, ((SQLiteDebug.PagerStats)object).largestMemAlloc / 1024);
                n2 = ((SQLiteDebug.PagerStats)object).dbStats.size();
                for (n4 = 0; n4 < n2; ++n4) {
                    object2 = ((SQLiteDebug.PagerStats)object).dbStats.get(n4);
                    l13 = protoOutputStream.start(2246267895812L);
                    protoOutputStream.write(1138166333441L, object2.dbName);
                    protoOutputStream.write(1120986464258L, object2.pageSize);
                    protoOutputStream.write(1120986464259L, object2.dbSize);
                    protoOutputStream.write(1120986464260L, object2.lookaside);
                    protoOutputStream.write(1138166333445L, object2.cache);
                    protoOutputStream.end(l13);
                }
                protoOutputStream.end(l12);
                object = AssetManager.getAssetAllocations();
                if (object != null) {
                    protoOutputStream.write(1138166333444L, (String)object);
                }
                if (!bl4) break block3;
                n4 = ActivityThread.this.mBoundApplication == null ? 0 : ActivityThread.this.mBoundApplication.appInfo.flags;
                if ((n4 & 2) != 0 || Build.IS_DEBUGGABLE) {
                    bl = true;
                }
                protoOutputStream.write(1138166333445L, Debug.getUnreachableMemory(100, bl));
            }
        }

        private void dumpMemInfo(PrintWriter printWriter, Debug.MemoryInfo object, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
            block6 : {
                long l = Debug.getNativeHeapSize() / 1024L;
                long l2 = Debug.getNativeHeapAllocatedSize() / 1024L;
                long l3 = Debug.getNativeHeapFreeSize() / 1024L;
                Object object2 = Runtime.getRuntime();
                object2.gc();
                long l4 = object2.totalMemory() / 1024L;
                long l5 = object2.freeMemory() / 1024L;
                object2 = VMDebug.countInstancesOfClasses((Class[])new Class[]{ContextImpl.class, Activity.class, WebView.class, OpenSSLSocketImpl.class}, (boolean)true);
                long l6 = object2[0];
                long l7 = object2[1];
                long l8 = object2[2];
                long l9 = object2[3];
                long l10 = ViewDebug.getViewInstanceCount();
                long l11 = ViewDebug.getViewRootImplCount();
                int n = AssetManager.getGlobalAssetCount();
                int n2 = AssetManager.getGlobalAssetManagerCount();
                int n3 = Debug.getBinderLocalObjectCount();
                int n4 = Debug.getBinderProxyObjectCount();
                int n5 = Debug.getBinderDeathObjectCount();
                long l12 = Parcel.getGlobalAllocSize();
                long l13 = Parcel.getGlobalAllocCount();
                SQLiteDebug.PagerStats pagerStats = SQLiteDebug.getDatabaseInfo();
                int n6 = Process.myPid();
                object2 = ActivityThread.this.mBoundApplication != null ? ActivityThread.this.mBoundApplication.processName : "unknown";
                int n7 = 1;
                ActivityThread.dumpMemInfoTable(printWriter, (Debug.MemoryInfo)object, bl, bl2, bl3, bl4, n6, (String)object2, l, l2, l3, l4, l4 - l5, l5);
                if (bl) {
                    printWriter.print(l10);
                    printWriter.print(',');
                    printWriter.print(l11);
                    printWriter.print(',');
                    printWriter.print(l6);
                    printWriter.print(',');
                    printWriter.print(l7);
                    printWriter.print(',');
                    printWriter.print(n);
                    printWriter.print(',');
                    printWriter.print(n2);
                    printWriter.print(',');
                    printWriter.print(n3);
                    printWriter.print(',');
                    printWriter.print(n4);
                    printWriter.print(',');
                    printWriter.print(n5);
                    printWriter.print(',');
                    printWriter.print(l9);
                    printWriter.print(',');
                    object = pagerStats;
                    printWriter.print(((SQLiteDebug.PagerStats)object).memoryUsed / 1024);
                    printWriter.print(',');
                    printWriter.print(((SQLiteDebug.PagerStats)object).memoryUsed / 1024);
                    printWriter.print(',');
                    printWriter.print(((SQLiteDebug.PagerStats)object).pageCacheOverflow / 1024);
                    printWriter.print(',');
                    printWriter.print(((SQLiteDebug.PagerStats)object).largestMemAlloc / 1024);
                    for (n3 = 0; n3 < ((SQLiteDebug.PagerStats)object).dbStats.size(); ++n3) {
                        object2 = ((SQLiteDebug.PagerStats)object).dbStats.get(n3);
                        printWriter.print(',');
                        printWriter.print(object2.dbName);
                        printWriter.print(',');
                        printWriter.print(object2.pageSize);
                        printWriter.print(',');
                        printWriter.print(object2.dbSize);
                        printWriter.print(',');
                        printWriter.print(object2.lookaside);
                        printWriter.print(',');
                        printWriter.print(object2.cache);
                        printWriter.print(',');
                        printWriter.print(object2.cache);
                    }
                    printWriter.println();
                    return;
                }
                object = " ";
                printWriter.println(" ");
                printWriter.println(" Objects");
                object2 = new Object[4];
                object2[0] = (long)"Views:";
                object2[n7] = l10;
                object2[2] = (long)"ViewRootImpl:";
                object2[3] = l11;
                ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, object2);
                object2 = new Object[4];
                object2[0] = (long)"AppContexts:";
                object2[n7] = l6;
                object2[2] = (long)"Activities:";
                object2[3] = l7;
                ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, object2);
                object2 = new Object[4];
                object2[0] = (long)"Assets:";
                object2[n7] = n;
                object2[2] = (long)"AssetManagers:";
                object2[3] = n2;
                ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, object2);
                object2 = new Object[4];
                object2[0] = (long)"Local Binders:";
                object2[n7] = n3;
                object2[2] = (long)"Proxy Binders:";
                object2[3] = n4;
                ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, object2);
                object2 = new Object[4];
                object2[0] = (long)"Parcel memory:";
                object2[n7] = l12 / 1024L;
                object2[2] = (long)"Parcel count:";
                object2[3] = l13;
                ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, object2);
                object2 = new Object[4];
                object2[0] = (long)"Death Recipients:";
                object2[n7] = n5;
                object2[2] = (long)"OpenSSL Sockets:";
                object2[3] = l9;
                ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, object2);
                object2 = new Object[2];
                object2[0] = (long)"WebViews:";
                object2[n7] = l8;
                ActivityThread.printRow(printWriter, ActivityThread.ONE_COUNT_COLUMN, object2);
                printWriter.println(" ");
                printWriter.println(" SQL");
                object2 = new Object[2];
                object2[0] = (long)"MEMORY_USED:";
                object2[n7] = pagerStats.memoryUsed / 1024;
                ActivityThread.printRow(printWriter, ActivityThread.ONE_COUNT_COLUMN, object2);
                object2 = new Object[4];
                object2[0] = (long)"PAGECACHE_OVERFLOW:";
                object2[n7] = pagerStats.pageCacheOverflow / 1024;
                object2[2] = (long)"MALLOC_SIZE:";
                object2[3] = pagerStats.largestMemAlloc / 1024;
                ActivityThread.printRow(printWriter, ActivityThread.TWO_COUNT_COLUMNS, object2);
                printWriter.println(" ");
                n5 = pagerStats.dbStats.size();
                if (n5 > 0) {
                    printWriter.println(" DATABASES");
                    object2 = new Object[5];
                    object2[0] = (long)"pgsz";
                    object2[n7] = (long)"dbsz";
                    object2[2] = (long)"Lookaside(b)";
                    object2[3] = (long)"cache";
                    object2[4] = (long)"Dbname";
                    ActivityThread.printRow(printWriter, DB_INFO_FORMAT, object2);
                    for (n3 = 0; n3 < n5; ++n3) {
                        SQLiteDebug.DbStats dbStats = pagerStats.dbStats.get(n3);
                        Object[] arrobject = new Object[5];
                        object2 = dbStats.pageSize > 0L ? String.valueOf(dbStats.pageSize) : object;
                        arrobject[0] = object2;
                        object2 = dbStats.dbSize > 0L ? String.valueOf(dbStats.dbSize) : object;
                        arrobject[n7] = object2;
                        object2 = dbStats.lookaside > 0 ? String.valueOf(dbStats.lookaside) : object;
                        arrobject[2] = object2;
                        arrobject[3] = dbStats.cache;
                        arrobject[4] = dbStats.dbName;
                        ActivityThread.printRow(printWriter, DB_INFO_FORMAT, arrobject);
                    }
                } else {
                    object = " ";
                }
                if ((object2 = AssetManager.getAssetAllocations()) != null) {
                    printWriter.println((String)object);
                    printWriter.println(" Asset Allocations");
                    printWriter.print((String)object2);
                }
                if (!bl5) break block6;
                n3 = ActivityThread.this.mBoundApplication != null && (2 & ActivityThread.this.mBoundApplication.appInfo.flags) != 0 || Build.IS_DEBUGGABLE ? n7 : 0;
                printWriter.println((String)object);
                printWriter.println(" Unreachable memory");
                printWriter.print(Debug.getUnreachableMemory(100, n3 != 0));
            }
        }

        private File getDatabasesDir(Context context) {
            return context.getDatabasePath("a").getParentFile();
        }

        static /* synthetic */ void lambda$performDirectAction$2(Object object, IBinder iBinder, String string2, Bundle bundle, CancellationSignal cancellationSignal, RemoteCallback remoteCallback) {
            ((ActivityThread)object).handlePerformDirectAction(iBinder, string2, bundle, cancellationSignal, remoteCallback);
        }

        static /* synthetic */ void lambda$requestDirectActions$1(Object object, IBinder iBinder, IVoiceInteractor iVoiceInteractor, CancellationSignal cancellationSignal, RemoteCallback remoteCallback) {
            ((ActivityThread)object).handleRequestDirectActions(iBinder, iVoiceInteractor, cancellationSignal, remoteCallback);
        }

        static /* synthetic */ void lambda$scheduleTrimMemory$0(Object object, int n) {
            ((ActivityThread)object).handleTrimMemory(n);
        }

        @Override
        public void attachAgent(String string2) {
            ActivityThread.this.sendMessage(155, string2);
        }

        @Override
        public final void bindApplication(String string2, ApplicationInfo applicationInfo, List<ProviderInfo> list, ComponentName componentName, ProfilerInfo profilerInfo, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, Configuration configuration, CompatibilityInfo compatibilityInfo, Map object, Bundle bundle2, String string3, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions) {
            if (object != null) {
                ServiceManager.initServiceCache((Map<String, IBinder>)object);
            }
            this.setCoreSettings(bundle2);
            object = new AppBindData();
            ((AppBindData)object).processName = string2;
            ((AppBindData)object).appInfo = applicationInfo;
            ((AppBindData)object).providers = list;
            ((AppBindData)object).instrumentationName = componentName;
            ((AppBindData)object).instrumentationArgs = bundle;
            ((AppBindData)object).instrumentationWatcher = iInstrumentationWatcher;
            ((AppBindData)object).instrumentationUiAutomationConnection = iUiAutomationConnection;
            ((AppBindData)object).debugMode = n;
            ((AppBindData)object).enableBinderTracking = bl;
            ((AppBindData)object).trackAllocation = bl2;
            ((AppBindData)object).restrictedBackupMode = bl3;
            ((AppBindData)object).persistent = bl4;
            ((AppBindData)object).config = configuration;
            ((AppBindData)object).compatInfo = compatibilityInfo;
            ((AppBindData)object).initProfilerInfo = profilerInfo;
            ((AppBindData)object).buildSerial = string3;
            ((AppBindData)object).autofillOptions = autofillOptions;
            ((AppBindData)object).contentCaptureOptions = contentCaptureOptions;
            ActivityThread.this.sendMessage(110, object);
        }

        @Override
        public void clearDnsCache() {
            InetAddress.clearDnsCache();
            NetworkEventDispatcher.getInstance().onNetworkConfigurationChanged();
        }

        @Override
        public void dispatchPackageBroadcast(int n, String[] arrstring) {
            ActivityThread.this.sendMessage(133, arrstring, n);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void dumpActivity(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String string2, String[] arrstring) {
            DumpComponentInfo dumpComponentInfo = new DumpComponentInfo();
            try {
                try {
                    dumpComponentInfo.fd = parcelFileDescriptor.dup();
                    dumpComponentInfo.token = iBinder;
                    dumpComponentInfo.prefix = string2;
                    dumpComponentInfo.args = arrstring;
                    ActivityThread.this.sendMessage(136, dumpComponentInfo, 0, 0, true);
                }
                catch (IOException iOException) {
                    Slog.w(ActivityThread.TAG, "dumpActivity failed", iOException);
                }
            }
            catch (Throwable throwable2) {}
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            return;
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            throw throwable2;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void dumpDbInfo(ParcelFileDescriptor parcelFileDescriptor, final String[] arrstring) {
            Throwable throwable2222;
            if (!ActivityThread.this.mSystemThread) {
                this.dumpDatabaseInfo(parcelFileDescriptor, arrstring, false);
                IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
                return;
            }
            final ParcelFileDescriptor parcelFileDescriptor2 = parcelFileDescriptor.dup();
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        ApplicationThread.this.dumpDatabaseInfo(parcelFileDescriptor2, arrstring, true);
                        return;
                    }
                    finally {
                        IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor2);
                    }
                }
            });
            return;
            {
                catch (Throwable throwable2222) {
                }
                catch (IOException iOException) {}
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Could not dup FD ");
                    stringBuilder.append(parcelFileDescriptor.getFileDescriptor().getInt$());
                    Log.w(ActivityThread.TAG, stringBuilder.toString());
                }
                IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
                return;
            }
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            throw throwable2222;
        }

        @Override
        public void dumpGfxInfo(ParcelFileDescriptor parcelFileDescriptor, String[] arrstring) {
            ActivityThread.this.nDumpGraphicsInfo(parcelFileDescriptor.getFileDescriptor());
            WindowManagerGlobal.getInstance().dumpGfxInfo(parcelFileDescriptor.getFileDescriptor(), arrstring);
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
        }

        @Override
        public void dumpHeap(boolean bl, boolean bl2, boolean bl3, String string2, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) {
            DumpHeapData dumpHeapData = new DumpHeapData();
            dumpHeapData.managed = bl;
            dumpHeapData.mallocInfo = bl2;
            dumpHeapData.runGc = bl3;
            dumpHeapData.path = string2;
            try {
                dumpHeapData.fd = parcelFileDescriptor.dup();
                dumpHeapData.finishCallback = remoteCallback;
            }
            catch (IOException iOException) {
                Slog.e(ActivityThread.TAG, "Failed to duplicate heap dump file descriptor", iOException);
                return;
            }
            ActivityThread.this.sendMessage(135, dumpHeapData, 0, 0, true);
        }

        @Override
        public void dumpMemInfo(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, String[] object) {
            object = new FastPrintWriter(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
            try {
                this.dumpMemInfo((PrintWriter)object, memoryInfo, bl, bl2, bl3, bl4, bl5);
                return;
            }
            finally {
                ((PrintWriter)object).flush();
                IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            }
        }

        @Override
        public void dumpMemInfoProto(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean bl, boolean bl2, boolean bl3, boolean bl4, String[] object) {
            object = new ProtoOutputStream(parcelFileDescriptor.getFileDescriptor());
            try {
                this.dumpMemInfo((ProtoOutputStream)object, memoryInfo, bl, bl2, bl3, bl4);
                return;
            }
            finally {
                ((ProtoOutputStream)object).flush();
                IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void dumpProvider(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] arrstring) {
            DumpComponentInfo dumpComponentInfo = new DumpComponentInfo();
            try {
                try {
                    dumpComponentInfo.fd = parcelFileDescriptor.dup();
                    dumpComponentInfo.token = iBinder;
                    dumpComponentInfo.args = arrstring;
                    ActivityThread.this.sendMessage(141, dumpComponentInfo, 0, 0, true);
                }
                catch (IOException iOException) {
                    Slog.w(ActivityThread.TAG, "dumpProvider failed", iOException);
                }
            }
            catch (Throwable throwable2) {}
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            return;
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            throw throwable2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void dumpService(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] arrstring) {
            DumpComponentInfo dumpComponentInfo = new DumpComponentInfo();
            try {
                try {
                    dumpComponentInfo.fd = parcelFileDescriptor.dup();
                    dumpComponentInfo.token = iBinder;
                    dumpComponentInfo.args = arrstring;
                    ActivityThread.this.sendMessage(123, dumpComponentInfo, 0, 0, true);
                }
                catch (IOException iOException) {
                    Slog.w(ActivityThread.TAG, "dumpService failed", iOException);
                }
            }
            catch (Throwable throwable2) {}
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            return;
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            throw throwable2;
        }

        @Override
        public void handleTrustStorageUpdate() {
            NetworkSecurityPolicy.getInstance().handleTrustStorageUpdate();
        }

        @Override
        public void notifyCleartextNetwork(byte[] arrby) {
            if (StrictMode.vmCleartextNetworkEnabled()) {
                StrictMode.onCleartextNetworkDetected(arrby);
            }
        }

        @Override
        public void performDirectAction(IBinder iBinder, String string2, Bundle bundle, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            if (remoteCallback != null) {
                SafeCancellationTransport safeCancellationTransport = ActivityThread.this.createSafeCancellationTransport(cancellationSignal);
                Bundle bundle2 = new Bundle();
                bundle2.putBinder("key_cancellation_signal", safeCancellationTransport.asBinder());
                remoteCallback.sendResult(bundle2);
            }
            ActivityThread.this.mH.sendMessage(PooledLambda.obtainMessage(_$$Lambda$ActivityThread$ApplicationThread$nBC_BR7B9W6ftKAxur3BC53SJYc.INSTANCE, ActivityThread.this, iBinder, string2, bundle, cancellationSignal, remoteCallback2));
        }

        @Override
        public void processInBackground() {
            ActivityThread.this.mH.removeMessages(120);
            ActivityThread.this.mH.sendMessage(ActivityThread.this.mH.obtainMessage(120));
        }

        @Override
        public void profilerControl(boolean bl, ProfilerInfo profilerInfo, int n) {
            ActivityThread.this.sendMessage(127, profilerInfo, (int)bl, n);
        }

        @Override
        public void requestAssistContextExtras(IBinder iBinder, IBinder iBinder2, int n, int n2, int n3) {
            RequestAssistContextExtras requestAssistContextExtras = new RequestAssistContextExtras();
            requestAssistContextExtras.activityToken = iBinder;
            requestAssistContextExtras.requestToken = iBinder2;
            requestAssistContextExtras.requestType = n;
            requestAssistContextExtras.sessionId = n2;
            requestAssistContextExtras.flags = n3;
            ActivityThread.this.sendMessage(143, requestAssistContextExtras);
        }

        @Override
        public void requestDirectActions(IBinder iBinder, IVoiceInteractor iVoiceInteractor, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            if (remoteCallback != null) {
                SafeCancellationTransport safeCancellationTransport = ActivityThread.this.createSafeCancellationTransport(cancellationSignal);
                Bundle bundle = new Bundle();
                bundle.putBinder("key_cancellation_signal", safeCancellationTransport.asBinder());
                remoteCallback.sendResult(bundle);
            }
            ActivityThread.this.mH.sendMessage(PooledLambda.obtainMessage(_$$Lambda$ActivityThread$ApplicationThread$uR_ee_5oPoxu4U_by7wU55jwtdU.INSTANCE, ActivityThread.this, iBinder, iVoiceInteractor, cancellationSignal, remoteCallback2));
        }

        @Override
        public final void runIsolatedEntryPoint(String string2, String[] arrstring) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = string2;
            someArgs.arg2 = arrstring;
            ActivityThread.this.sendMessage(158, someArgs);
        }

        @Override
        public void scheduleApplicationInfoChanged(ApplicationInfo applicationInfo) {
            ActivityThread.this.mH.removeMessages(156, applicationInfo);
            ActivityThread.this.sendMessage(156, applicationInfo);
        }

        @Override
        public final void scheduleBindService(IBinder iBinder, Intent intent, boolean bl, int n) {
            ActivityThread.this.updateProcessState(n, false);
            BindServiceData bindServiceData = new BindServiceData();
            bindServiceData.token = iBinder;
            bindServiceData.intent = intent;
            bindServiceData.rebind = bl;
            ActivityThread.this.sendMessage(121, bindServiceData);
        }

        @Override
        public void scheduleCrash(String string2) {
            ActivityThread.this.sendMessage(134, string2);
        }

        @Override
        public final void scheduleCreateBackupAgent(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, int n, int n2) {
            CreateBackupAgentData createBackupAgentData = new CreateBackupAgentData();
            createBackupAgentData.appInfo = applicationInfo;
            createBackupAgentData.compatInfo = compatibilityInfo;
            createBackupAgentData.backupMode = n;
            createBackupAgentData.userId = n2;
            ActivityThread.this.sendMessage(128, createBackupAgentData);
        }

        @Override
        public final void scheduleCreateService(IBinder iBinder, ServiceInfo serviceInfo, CompatibilityInfo compatibilityInfo, int n) {
            ActivityThread.this.updateProcessState(n, false);
            CreateServiceData createServiceData = new CreateServiceData();
            createServiceData.token = iBinder;
            createServiceData.info = serviceInfo;
            createServiceData.compatInfo = compatibilityInfo;
            ActivityThread.this.sendMessage(114, createServiceData);
        }

        @Override
        public final void scheduleDestroyBackupAgent(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, int n) {
            CreateBackupAgentData createBackupAgentData = new CreateBackupAgentData();
            createBackupAgentData.appInfo = applicationInfo;
            createBackupAgentData.compatInfo = compatibilityInfo;
            createBackupAgentData.userId = n;
            ActivityThread.this.sendMessage(129, createBackupAgentData);
        }

        @Override
        public void scheduleEnterAnimationComplete(IBinder iBinder) {
            ActivityThread.this.sendMessage(149, iBinder);
        }

        @Override
        public final void scheduleExit() {
            ActivityThread.this.sendMessage(111, null);
        }

        @Override
        public void scheduleInstallProvider(ProviderInfo providerInfo) {
            ActivityThread.this.sendMessage(145, providerInfo);
        }

        @Override
        public void scheduleLocalVoiceInteractionStarted(IBinder iBinder, IVoiceInteractor iVoiceInteractor) throws RemoteException {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = iBinder;
            someArgs.arg2 = iVoiceInteractor;
            ActivityThread.this.sendMessage(154, someArgs);
        }

        @Override
        public void scheduleLowMemory() {
            ActivityThread.this.sendMessage(124, null);
        }

        @Override
        public void scheduleOnNewActivityOptions(IBinder iBinder, Bundle bundle) {
            ActivityThread.this.sendMessage(146, new Pair<IBinder, ActivityOptions>(iBinder, ActivityOptions.fromBundle(bundle)));
        }

        @Override
        public final void scheduleReceiver(Intent object, ActivityInfo activityInfo, CompatibilityInfo compatibilityInfo, int n, String string2, Bundle bundle, boolean bl, int n2, int n3) {
            ActivityThread.this.updateProcessState(n3, false);
            object = new ReceiverData((Intent)object, n, string2, bundle, bl, false, ActivityThread.this.mAppThread.asBinder(), n2);
            ((ReceiverData)object).info = activityInfo;
            ((ReceiverData)object).compatInfo = compatibilityInfo;
            ActivityThread.this.sendMessage(113, object);
        }

        @Override
        public void scheduleRegisteredReceiver(IIntentReceiver iIntentReceiver, Intent intent, int n, String string2, Bundle bundle, boolean bl, boolean bl2, int n2, int n3) throws RemoteException {
            ActivityThread.this.updateProcessState(n3, false);
            iIntentReceiver.performReceive(intent, n, string2, bundle, bl, bl2, n2);
        }

        @Override
        public final void scheduleServiceArgs(IBinder iBinder, ParceledListSlice object) {
            List list = ((ParceledListSlice)object).getList();
            for (int i = 0; i < list.size(); ++i) {
                ServiceStartArgs serviceStartArgs = (ServiceStartArgs)list.get(i);
                object = new ServiceArgsData();
                ((ServiceArgsData)object).token = iBinder;
                ((ServiceArgsData)object).taskRemoved = serviceStartArgs.taskRemoved;
                ((ServiceArgsData)object).startId = serviceStartArgs.startId;
                ((ServiceArgsData)object).flags = serviceStartArgs.flags;
                ((ServiceArgsData)object).args = serviceStartArgs.args;
                ActivityThread.this.sendMessage(115, object);
            }
        }

        @Override
        public final void scheduleSleeping(IBinder iBinder, boolean bl) {
            ActivityThread.this.sendMessage(137, iBinder, (int)bl);
        }

        @Override
        public final void scheduleStopService(IBinder iBinder) {
            ActivityThread.this.sendMessage(116, iBinder);
        }

        @Override
        public final void scheduleSuicide() {
            ActivityThread.this.sendMessage(130, null);
        }

        @Override
        public void scheduleTransaction(ClientTransaction clientTransaction) throws RemoteException {
            ActivityThread.this.scheduleTransaction(clientTransaction);
        }

        @Override
        public void scheduleTranslucentConversionComplete(IBinder iBinder, boolean bl) {
            ActivityThread.this.sendMessage(144, iBinder, (int)bl);
        }

        @Override
        public void scheduleTrimMemory(int n) {
            PooledLambda pooledLambda = PooledLambda.obtainRunnable(_$$Lambda$ActivityThread$ApplicationThread$tUGFX7CUhzB4Pg5wFd5yeqOnu38.INSTANCE, ActivityThread.this, n).recycleOnUse();
            Choreographer choreographer = Choreographer.getMainThreadInstance();
            if (choreographer != null) {
                choreographer.postCallback(4, (Runnable)((Object)pooledLambda), null);
            } else {
                ActivityThread.this.mH.post((Runnable)((Object)pooledLambda));
            }
        }

        @Override
        public final void scheduleUnbindService(IBinder iBinder, Intent intent) {
            BindServiceData bindServiceData = new BindServiceData();
            bindServiceData.token = iBinder;
            bindServiceData.intent = intent;
            ActivityThread.this.sendMessage(122, bindServiceData);
        }

        @Override
        public void setCoreSettings(Bundle bundle) {
            ActivityThread.this.sendMessage(138, bundle);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setNetworkBlockSeq(long l) {
            Object object = ActivityThread.this.mNetworkPolicyLock;
            synchronized (object) {
                ActivityThread.this.mNetworkBlockSeq = l;
                return;
            }
        }

        @Override
        public void setProcessState(int n) {
            ActivityThread.this.updateProcessState(n, true);
        }

        @Override
        public void setSchedulingGroup(int n) {
            try {
                Process.setProcessGroup(Process.myPid(), n);
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed setting process group to ");
                stringBuilder.append(n);
                Slog.w(ActivityThread.TAG, stringBuilder.toString(), exception);
            }
        }

        @Override
        public void startBinderTracking() {
            ActivityThread.this.sendMessage(150, null);
        }

        @Override
        public void stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) {
            try {
                ActivityThread.this.sendMessage(151, parcelFileDescriptor.dup());
            }
            catch (Throwable throwable) {
                IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
                throw throwable;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
        }

        @Override
        public void unstableProviderDied(IBinder iBinder) {
            ActivityThread.this.sendMessage(142, iBinder);
        }

        @Override
        public void updateHttpProxy() {
            Context context = ActivityThread.this.getApplication() != null ? ActivityThread.this.getApplication() : ActivityThread.this.getSystemContext();
            ActivityThread.updateHttpProxy(context);
        }

        @Override
        public void updatePackageCompatibilityInfo(String string2, CompatibilityInfo compatibilityInfo) {
            UpdateCompatibilityData updateCompatibilityData = new UpdateCompatibilityData();
            updateCompatibilityData.pkg = string2;
            updateCompatibilityData.info = compatibilityInfo;
            ActivityThread.this.sendMessage(139, updateCompatibilityData);
        }

        @Override
        public final void updateTimePrefs(int n) {
            Boolean bl = n == 0 ? Boolean.FALSE : (n == 1 ? Boolean.TRUE : null);
            DateFormat.set24HourTimePref((Boolean)bl);
        }

        @Override
        public void updateTimeZone() {
            TimeZone.setDefault(null);
        }

    }

    static final class BindServiceData {
        @UnsupportedAppUsage
        Intent intent;
        boolean rebind;
        @UnsupportedAppUsage
        IBinder token;

        BindServiceData() {
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("BindServiceData{token=");
            stringBuilder.append(this.token);
            stringBuilder.append(" intent=");
            stringBuilder.append(this.intent);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    static final class ContextCleanupInfo {
        ContextImpl context;
        String what;
        String who;

        ContextCleanupInfo() {
        }
    }

    static final class CreateBackupAgentData {
        ApplicationInfo appInfo;
        int backupMode;
        CompatibilityInfo compatInfo;
        int userId;

        CreateBackupAgentData() {
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CreateBackupAgentData{appInfo=");
            stringBuilder.append(this.appInfo);
            stringBuilder.append(" backupAgent=");
            stringBuilder.append(this.appInfo.backupAgentName);
            stringBuilder.append(" mode=");
            stringBuilder.append(this.backupMode);
            stringBuilder.append(" userId=");
            stringBuilder.append(this.userId);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    static final class CreateServiceData {
        @UnsupportedAppUsage
        CompatibilityInfo compatInfo;
        @UnsupportedAppUsage
        ServiceInfo info;
        @UnsupportedAppUsage
        Intent intent;
        @UnsupportedAppUsage
        IBinder token;

        CreateServiceData() {
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CreateServiceData{token=");
            stringBuilder.append(this.token);
            stringBuilder.append(" className=");
            stringBuilder.append(this.info.name);
            stringBuilder.append(" packageName=");
            stringBuilder.append(this.info.packageName);
            stringBuilder.append(" intent=");
            stringBuilder.append(this.intent);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    static final class DumpComponentInfo {
        String[] args;
        ParcelFileDescriptor fd;
        String prefix;
        IBinder token;

        DumpComponentInfo() {
        }
    }

    static final class DumpHeapData {
        ParcelFileDescriptor fd;
        RemoteCallback finishCallback;
        public boolean mallocInfo;
        public boolean managed;
        String path;
        public boolean runGc;

        DumpHeapData() {
        }
    }

    final class GcIdler
    implements MessageQueue.IdleHandler {
        GcIdler() {
        }

        @Override
        public final boolean queueIdle() {
            ActivityThread.this.doGcIfNeeded();
            ActivityThread.this.purgePendingResources();
            return false;
        }
    }

    class H
    extends Handler {
        public static final int APPLICATION_INFO_CHANGED = 156;
        public static final int ATTACH_AGENT = 155;
        public static final int BIND_APPLICATION = 110;
        @UnsupportedAppUsage
        public static final int BIND_SERVICE = 121;
        public static final int CLEAN_UP_CONTEXT = 119;
        public static final int CONFIGURATION_CHANGED = 118;
        public static final int CREATE_BACKUP_AGENT = 128;
        @UnsupportedAppUsage
        public static final int CREATE_SERVICE = 114;
        public static final int DESTROY_BACKUP_AGENT = 129;
        public static final int DISPATCH_PACKAGE_BROADCAST = 133;
        public static final int DUMP_ACTIVITY = 136;
        public static final int DUMP_HEAP = 135;
        @UnsupportedAppUsage
        public static final int DUMP_PROVIDER = 141;
        public static final int DUMP_SERVICE = 123;
        @UnsupportedAppUsage
        public static final int ENTER_ANIMATION_COMPLETE = 149;
        public static final int EXECUTE_TRANSACTION = 159;
        @UnsupportedAppUsage
        public static final int EXIT_APPLICATION = 111;
        @UnsupportedAppUsage
        public static final int GC_WHEN_IDLE = 120;
        @UnsupportedAppUsage
        public static final int INSTALL_PROVIDER = 145;
        public static final int LOCAL_VOICE_INTERACTION_STARTED = 154;
        public static final int LOW_MEMORY = 124;
        public static final int ON_NEW_ACTIVITY_OPTIONS = 146;
        public static final int PROFILER_CONTROL = 127;
        public static final int PURGE_RESOURCES = 161;
        @UnsupportedAppUsage
        public static final int RECEIVER = 113;
        public static final int RELAUNCH_ACTIVITY = 160;
        @UnsupportedAppUsage
        public static final int REMOVE_PROVIDER = 131;
        public static final int REQUEST_ASSIST_CONTEXT_EXTRAS = 143;
        public static final int RUN_ISOLATED_ENTRY_POINT = 158;
        @UnsupportedAppUsage
        public static final int SCHEDULE_CRASH = 134;
        @UnsupportedAppUsage
        public static final int SERVICE_ARGS = 115;
        public static final int SET_CORE_SETTINGS = 138;
        public static final int SLEEPING = 137;
        public static final int START_BINDER_TRACKING = 150;
        public static final int STOP_BINDER_TRACKING_AND_DUMP = 151;
        @UnsupportedAppUsage
        public static final int STOP_SERVICE = 116;
        public static final int SUICIDE = 130;
        public static final int TRANSLUCENT_CONVERSION_COMPLETE = 144;
        @UnsupportedAppUsage
        public static final int UNBIND_SERVICE = 122;
        public static final int UNSTABLE_PROVIDER_DIED = 142;
        public static final int UPDATE_PACKAGE_COMPATIBILITY_INFO = 139;

        H() {
        }

        String codeToString(int n) {
            return Integer.toString(n);
        }

        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            boolean bl = true;
            boolean bl2 = true;
            boolean bl3 = true;
            switch (n) {
                default: {
                    break;
                }
                case 161: {
                    ActivityThread.this.schedulePurgeIdler();
                    break;
                }
                case 160: {
                    ActivityThread.this.handleRelaunchActivityLocally((IBinder)((Message)object).obj);
                    break;
                }
                case 159: {
                    ClientTransaction clientTransaction = (ClientTransaction)((Message)object).obj;
                    ActivityThread.this.mTransactionExecutor.execute(clientTransaction);
                    if (!ActivityThread.isSystem()) break;
                    clientTransaction.recycle();
                    break;
                }
                case 158: {
                    ActivityThread.this.handleRunIsolatedEntryPoint((String)((SomeArgs)object.obj).arg1, (String[])((SomeArgs)object.obj).arg2);
                    break;
                }
                case 156: {
                    ActivityThread activityThread = ActivityThread.this;
                    activityThread.mUpdatingSystemConfig = true;
                    try {
                        activityThread.handleApplicationInfoChanged((ApplicationInfo)((Message)object).obj);
                        break;
                    }
                    finally {
                        ActivityThread.this.mUpdatingSystemConfig = false;
                    }
                }
                case 155: {
                    Object object2 = ActivityThread.this.getApplication();
                    String string2 = (String)((Message)object).obj;
                    object2 = object2 != null ? ((Application)object2).mLoadedApk : null;
                    ActivityThread.handleAttachAgent(string2, (LoadedApk)object2);
                    break;
                }
                case 154: {
                    ActivityThread.this.handleLocalVoiceInteractionStarted((IBinder)((SomeArgs)object.obj).arg1, (IVoiceInteractor)((SomeArgs)object.obj).arg2);
                    break;
                }
                case 151: {
                    ActivityThread.this.handleStopBinderTrackingAndDump((ParcelFileDescriptor)((Message)object).obj);
                    break;
                }
                case 150: {
                    ActivityThread.this.handleStartBinderTracking();
                    break;
                }
                case 149: {
                    ActivityThread.this.handleEnterAnimationComplete((IBinder)((Message)object).obj);
                    break;
                }
                case 146: {
                    Pair pair = (Pair)((Message)object).obj;
                    ActivityThread.this.onNewActivityOptions((IBinder)pair.first, (ActivityOptions)pair.second);
                    break;
                }
                case 145: {
                    ActivityThread.this.handleInstallProvider((ProviderInfo)((Message)object).obj);
                    break;
                }
                case 144: {
                    ActivityThread activityThread = ActivityThread.this;
                    IBinder iBinder = (IBinder)((Message)object).obj;
                    if (((Message)object).arg1 != 1) {
                        bl3 = false;
                    }
                    activityThread.handleTranslucentConversionComplete(iBinder, bl3);
                    break;
                }
                case 143: {
                    ActivityThread.this.handleRequestAssistContextExtras((RequestAssistContextExtras)((Message)object).obj);
                    break;
                }
                case 142: {
                    ActivityThread.this.handleUnstableProviderDied((IBinder)((Message)object).obj, false);
                    break;
                }
                case 141: {
                    ActivityThread.this.handleDumpProvider((DumpComponentInfo)((Message)object).obj);
                    break;
                }
                case 139: {
                    ActivityThread.this.handleUpdatePackageCompatibilityInfo((UpdateCompatibilityData)((Message)object).obj);
                    break;
                }
                case 138: {
                    Trace.traceBegin(64L, "setCoreSettings");
                    ActivityThread.this.handleSetCoreSettings((Bundle)((Message)object).obj);
                    Trace.traceEnd(64L);
                    break;
                }
                case 137: {
                    Trace.traceBegin(64L, "sleeping");
                    ActivityThread activityThread = ActivityThread.this;
                    IBinder iBinder = (IBinder)((Message)object).obj;
                    bl3 = ((Message)object).arg1 != 0 ? bl : false;
                    activityThread.handleSleeping(iBinder, bl3);
                    Trace.traceEnd(64L);
                    break;
                }
                case 136: {
                    ActivityThread.this.handleDumpActivity((DumpComponentInfo)((Message)object).obj);
                    break;
                }
                case 135: {
                    ActivityThread.handleDumpHeap((DumpHeapData)((Message)object).obj);
                    break;
                }
                case 134: {
                    throw new RemoteServiceException((String)((Message)object).obj);
                }
                case 133: {
                    Trace.traceBegin(64L, "broadcastPackage");
                    ActivityThread.this.handleDispatchPackageBroadcast(((Message)object).arg1, (String[])((Message)object).obj);
                    Trace.traceEnd(64L);
                    break;
                }
                case 131: {
                    Trace.traceBegin(64L, "providerRemove");
                    ActivityThread.this.completeRemoveProvider((ProviderRefCount)((Message)object).obj);
                    Trace.traceEnd(64L);
                    break;
                }
                case 130: {
                    Process.killProcess(Process.myPid());
                    break;
                }
                case 129: {
                    Trace.traceBegin(64L, "backupDestroyAgent");
                    ActivityThread.this.handleDestroyBackupAgent((CreateBackupAgentData)((Message)object).obj);
                    Trace.traceEnd(64L);
                    break;
                }
                case 128: {
                    Trace.traceBegin(64L, "backupCreateAgent");
                    ActivityThread.this.handleCreateBackupAgent((CreateBackupAgentData)((Message)object).obj);
                    Trace.traceEnd(64L);
                    break;
                }
                case 127: {
                    ActivityThread activityThread = ActivityThread.this;
                    bl3 = ((Message)object).arg1 != 0 ? bl2 : false;
                    activityThread.handleProfilerControl(bl3, (ProfilerInfo)((Message)object).obj, ((Message)object).arg2);
                    break;
                }
                case 124: {
                    Trace.traceBegin(64L, "lowMemory");
                    ActivityThread.this.handleLowMemory();
                    Trace.traceEnd(64L);
                    break;
                }
                case 123: {
                    ActivityThread.this.handleDumpService((DumpComponentInfo)((Message)object).obj);
                    break;
                }
                case 122: {
                    Trace.traceBegin(64L, "serviceUnbind");
                    ActivityThread.this.handleUnbindService((BindServiceData)((Message)object).obj);
                    ActivityThread.this.schedulePurgeIdler();
                    Trace.traceEnd(64L);
                    break;
                }
                case 121: {
                    Trace.traceBegin(64L, "serviceBind");
                    ActivityThread.this.handleBindService((BindServiceData)((Message)object).obj);
                    Trace.traceEnd(64L);
                    break;
                }
                case 120: {
                    ActivityThread.this.scheduleGcIdler();
                    break;
                }
                case 119: {
                    ContextCleanupInfo contextCleanupInfo = (ContextCleanupInfo)((Message)object).obj;
                    contextCleanupInfo.context.performFinalCleanup(contextCleanupInfo.who, contextCleanupInfo.what);
                    break;
                }
                case 118: {
                    ActivityThread.this.handleConfigurationChanged((Configuration)((Message)object).obj);
                    break;
                }
                case 116: {
                    Trace.traceBegin(64L, "serviceStop");
                    ActivityThread.this.handleStopService((IBinder)((Message)object).obj);
                    ActivityThread.this.schedulePurgeIdler();
                    Trace.traceEnd(64L);
                    break;
                }
                case 115: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("serviceStart: ");
                    stringBuilder.append(String.valueOf(((Message)object).obj));
                    Trace.traceBegin(64L, stringBuilder.toString());
                    ActivityThread.this.handleServiceArgs((ServiceArgsData)((Message)object).obj);
                    Trace.traceEnd(64L);
                    break;
                }
                case 114: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("serviceCreate: ");
                    stringBuilder.append(String.valueOf(((Message)object).obj));
                    Trace.traceBegin(64L, stringBuilder.toString());
                    ActivityThread.this.handleCreateService((CreateServiceData)((Message)object).obj);
                    Trace.traceEnd(64L);
                    break;
                }
                case 113: {
                    Trace.traceBegin(64L, "broadcastReceiveComp");
                    ActivityThread.this.handleReceiver((ReceiverData)((Message)object).obj);
                    Trace.traceEnd(64L);
                    break;
                }
                case 111: {
                    if (ActivityThread.this.mInitialApplication != null) {
                        ActivityThread.this.mInitialApplication.onTerminate();
                    }
                    Looper.myLooper().quit();
                    break;
                }
                case 110: {
                    Trace.traceBegin(64L, "bindApplication");
                    AppBindData appBindData = (AppBindData)((Message)object).obj;
                    ActivityThread.this.handleBindApplication(appBindData);
                    Trace.traceEnd(64L);
                }
            }
            object = ((Message)object).obj;
            if (object instanceof SomeArgs) {
                ((SomeArgs)object).recycle();
            }
        }
    }

    private class Idler
    implements MessageQueue.IdleHandler {
        private Idler() {
        }

        @Override
        public final boolean queueIdle() {
            boolean bl;
            ActivityClientRecord activityClientRecord = ActivityThread.this.mNewActivities;
            boolean bl2 = bl = false;
            if (ActivityThread.this.mBoundApplication != null) {
                bl2 = bl;
                if (ActivityThread.this.mProfiler.profileFd != null) {
                    bl2 = bl;
                    if (ActivityThread.this.mProfiler.autoStopProfiler) {
                        bl2 = true;
                    }
                }
            }
            if (activityClientRecord != null) {
                ActivityClientRecord activityClientRecord2;
                ActivityThread.this.mNewActivities = null;
                IActivityTaskManager iActivityTaskManager = ActivityTaskManager.getService();
                do {
                    if (activityClientRecord.activity != null && !activityClientRecord.activity.mFinished) {
                        try {
                            iActivityTaskManager.activityIdle(activityClientRecord.token, activityClientRecord.createdConfig, bl2);
                            activityClientRecord.createdConfig = null;
                        }
                        catch (RemoteException remoteException) {
                            throw remoteException.rethrowFromSystemServer();
                        }
                    }
                    activityClientRecord2 = activityClientRecord.nextIdle;
                    activityClientRecord.nextIdle = null;
                    activityClientRecord = activityClientRecord2;
                } while (activityClientRecord2 != null);
            }
            if (bl2) {
                ActivityThread.this.mProfiler.stopProfiling();
            }
            ActivityThread.this.applyPendingProcessState();
            return false;
        }
    }

    static final class Profiler {
        boolean autoStopProfiler;
        boolean handlingProfiling;
        ParcelFileDescriptor profileFd;
        String profileFile;
        boolean profiling;
        int samplingInterval;
        boolean streamingOutput;

        Profiler() {
        }

        public void setProfiler(ProfilerInfo profilerInfo) {
            ParcelFileDescriptor parcelFileDescriptor = profilerInfo.profileFd;
            if (this.profiling) {
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                return;
            }
            ParcelFileDescriptor parcelFileDescriptor2 = this.profileFd;
            if (parcelFileDescriptor2 != null) {
                try {
                    parcelFileDescriptor2.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            this.profileFile = profilerInfo.profileFile;
            this.profileFd = parcelFileDescriptor;
            this.samplingInterval = profilerInfo.samplingInterval;
            this.autoStopProfiler = profilerInfo.autoStopProfiler;
            this.streamingOutput = profilerInfo.streamingOutput;
        }

        public void startProfiling() {
            if (this.profileFd != null && !this.profiling) {
                int n = SystemProperties.getInt("debug.traceview-buffer-size-mb", 8);
                String string2 = this.profileFile;
                Object object = this.profileFd.getFileDescriptor();
                boolean bl = this.samplingInterval != 0;
                try {
                    VMDebug.startMethodTracing((String)string2, (FileDescriptor)object, (int)(n * 1024 * 1024), (int)0, (boolean)bl, (int)this.samplingInterval, (boolean)this.streamingOutput);
                    this.profiling = true;
                }
                catch (RuntimeException runtimeException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Profiling failed on path ");
                    ((StringBuilder)object).append(this.profileFile);
                    Slog.w(ActivityThread.TAG, ((StringBuilder)object).toString(), runtimeException);
                    try {
                        this.profileFd.close();
                        this.profileFd = null;
                    }
                    catch (IOException iOException) {
                        Slog.w(ActivityThread.TAG, "Failure closing profile fd", iOException);
                    }
                }
                return;
            }
        }

        public void stopProfiling() {
            if (this.profiling) {
                this.profiling = false;
                Debug.stopMethodTracing();
                ParcelFileDescriptor parcelFileDescriptor = this.profileFd;
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                this.profileFd = null;
                this.profileFile = null;
            }
        }
    }

    final class ProviderClientRecord {
        @UnsupportedAppUsage
        final ContentProviderHolder mHolder;
        @UnsupportedAppUsage
        final ContentProvider mLocalProvider;
        final String[] mNames;
        @UnsupportedAppUsage
        final IContentProvider mProvider;

        ProviderClientRecord(String[] arrstring, IContentProvider iContentProvider, ContentProvider contentProvider, ContentProviderHolder contentProviderHolder) {
            this.mNames = arrstring;
            this.mProvider = iContentProvider;
            this.mLocalProvider = contentProvider;
            this.mHolder = contentProviderHolder;
        }
    }

    private static final class ProviderKey {
        final String authority;
        final int userId;

        public ProviderKey(String string2, int n) {
            this.authority = string2;
            this.userId = n;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof ProviderKey;
            boolean bl2 = false;
            if (bl) {
                object = (ProviderKey)object;
                bl = bl2;
                if (Objects.equals(this.authority, ((ProviderKey)object).authority)) {
                    bl = bl2;
                    if (this.userId == ((ProviderKey)object).userId) {
                        bl = true;
                    }
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            String string2 = this.authority;
            int n = string2 != null ? string2.hashCode() : 0;
            return n ^ this.userId;
        }
    }

    private static final class ProviderRefCount {
        public final ProviderClientRecord client;
        public final ContentProviderHolder holder;
        public boolean removePending;
        public int stableCount;
        public int unstableCount;

        ProviderRefCount(ContentProviderHolder contentProviderHolder, ProviderClientRecord providerClientRecord, int n, int n2) {
            this.holder = contentProviderHolder;
            this.client = providerClientRecord;
            this.stableCount = n;
            this.unstableCount = n2;
        }
    }

    final class PurgeIdler
    implements MessageQueue.IdleHandler {
        PurgeIdler() {
        }

        @Override
        public boolean queueIdle() {
            ActivityThread.this.purgePendingResources();
            return false;
        }
    }

    static final class ReceiverData
    extends BroadcastReceiver.PendingResult {
        @UnsupportedAppUsage
        CompatibilityInfo compatInfo;
        @UnsupportedAppUsage
        ActivityInfo info;
        @UnsupportedAppUsage
        Intent intent;

        public ReceiverData(Intent intent, int n, String string2, Bundle bundle, boolean bl, boolean bl2, IBinder iBinder, int n2) {
            super(n, string2, bundle, 0, bl, bl2, iBinder, n2, intent.getFlags());
            this.intent = intent;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ReceiverData{intent=");
            stringBuilder.append(this.intent);
            stringBuilder.append(" packageName=");
            stringBuilder.append(this.info.packageName);
            stringBuilder.append(" resultCode=");
            stringBuilder.append(this.getResultCode());
            stringBuilder.append(" resultData=");
            stringBuilder.append(this.getResultData());
            stringBuilder.append(" resultExtras=");
            stringBuilder.append(this.getResultExtras(false));
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    static final class RequestAssistContextExtras {
        IBinder activityToken;
        int flags;
        IBinder requestToken;
        int requestType;
        int sessionId;

        RequestAssistContextExtras() {
        }
    }

    private static final class SafeCancellationTransport
    extends ICancellationSignal.Stub {
        private final WeakReference<ActivityThread> mWeakActivityThread;

        SafeCancellationTransport(ActivityThread activityThread, CancellationSignal cancellationSignal) {
            this.mWeakActivityThread = new WeakReference<ActivityThread>(activityThread);
        }

        @Override
        public void cancel() {
            Object object = (ActivityThread)this.mWeakActivityThread.get();
            if (object != null && (object = ((ActivityThread)object).removeSafeCancellationTransport(this)) != null) {
                ((CancellationSignal)object).cancel();
            }
        }
    }

    static final class ServiceArgsData {
        @UnsupportedAppUsage
        Intent args;
        int flags;
        int startId;
        boolean taskRemoved;
        @UnsupportedAppUsage
        IBinder token;

        ServiceArgsData() {
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ServiceArgsData{token=");
            stringBuilder.append(this.token);
            stringBuilder.append(" startId=");
            stringBuilder.append(this.startId);
            stringBuilder.append(" args=");
            stringBuilder.append(this.args);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    static final class UpdateCompatibilityData {
        CompatibilityInfo info;
        String pkg;

        UpdateCompatibilityData() {
        }
    }

}

