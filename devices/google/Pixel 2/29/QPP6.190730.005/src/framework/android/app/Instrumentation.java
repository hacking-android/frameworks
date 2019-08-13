/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.AppComponentFactory;
import android.app.Application;
import android.app.ContextImpl;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.IAppTask;
import android.app.IApplicationThread;
import android.app.IInstrumentationWatcher;
import android.app.IUiAutomationConnection;
import android.app.LoadedApk;
import android.app.ProfilerInfo;
import android.app.UiAutomation;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.Parcelable;
import android.os.PerformanceCollector;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.TestLooperManager;
import android.os.UserHandle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.IWindowManager;
import android.view.InputEvent;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowManagerGlobal;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.content.ReferrerIntent;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class Instrumentation {
    public static final String REPORT_KEY_IDENTIFIER = "id";
    public static final String REPORT_KEY_STREAMRESULT = "stream";
    private static final String TAG = "Instrumentation";
    private List<ActivityMonitor> mActivityMonitors;
    private final Object mAnimationCompleteLock = new Object();
    private Context mAppContext;
    private boolean mAutomaticPerformanceSnapshots = false;
    private ComponentName mComponent;
    private Context mInstrContext;
    private MessageQueue mMessageQueue = null;
    private Bundle mPerfMetrics = new Bundle();
    private PerformanceCollector mPerformanceCollector;
    private Thread mRunner;
    private final Object mSync = new Object();
    private ActivityThread mThread = null;
    private UiAutomation mUiAutomation;
    private IUiAutomationConnection mUiAutomationConnection;
    private List<ActivityWaiter> mWaitingActivities;
    private IInstrumentationWatcher mWatcher;

    private void addValue(String object, int n, Bundle bundle) {
        if (bundle.containsKey((String)object)) {
            if ((object = bundle.getIntegerArrayList((String)object)) != null) {
                object.add(n);
            }
        } else {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add(n);
            bundle.putIntegerArrayList((String)object, arrayList);
        }
    }

    private void checkInstrumenting(String string2) {
        if (this.mInstrContext != null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" cannot be called outside of instrumented processes");
        throw new RuntimeException(stringBuilder.toString());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static void checkStartActivityResult(int n, Object object) {
        if (!ActivityManager.isStartResultFatalError(n)) {
            return;
        }
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown error code ");
                stringBuilder.append(n);
                stringBuilder.append(" when starting ");
                stringBuilder.append(object);
                throw new AndroidRuntimeException(stringBuilder.toString());
            }
            case -89: {
                throw new IllegalStateException("Session calling startAssistantActivity does not match active session");
            }
            case -90: {
                throw new IllegalStateException("Cannot start assistant activity on a hidden session");
            }
            case -92: 
            case -91: {
                if (object instanceof Intent && ((Intent)object).getComponent() != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to find explicit activity class ");
                    stringBuilder.append(((Intent)object).getComponent().toShortString());
                    stringBuilder.append("; have you declared this activity in your AndroidManifest.xml?");
                    throw new ActivityNotFoundException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("No Activity found to handle ");
                stringBuilder.append(object);
                throw new ActivityNotFoundException(stringBuilder.toString());
            }
            case -93: {
                throw new AndroidRuntimeException("FORWARD_RESULT_FLAG used while also requesting a result");
            }
            case -94: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Not allowed to start activity ");
                stringBuilder.append(object);
                throw new SecurityException(stringBuilder.toString());
            }
            case -95: {
                throw new IllegalArgumentException("PendingIntent is not an activity");
            }
            case -96: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Activity could not be started for ");
                stringBuilder.append(object);
                throw new AndroidRuntimeException(stringBuilder.toString());
            }
            case -97: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Starting under voice control not allowed for: ");
                stringBuilder.append(object);
                throw new SecurityException(stringBuilder.toString());
            }
            case -99: {
                throw new IllegalStateException("Session calling startVoiceActivity does not match active session");
            }
            case -100: 
        }
        throw new IllegalStateException("Cannot start voice activity on a hidden session");
    }

    private AppComponentFactory getFactory(String object) {
        if (object == null) {
            Log.e(TAG, "No pkg specified, disabling AppComponentFactory");
            return AppComponentFactory.DEFAULT;
        }
        Object object2 = this.mThread;
        if (object2 == null) {
            Log.e(TAG, "Uninitialized ActivityThread, likely app-created Instrumentation, disabling AppComponentFactory", new Throwable());
            return AppComponentFactory.DEFAULT;
        }
        object = object2 = ((ActivityThread)object2).peekPackageInfo((String)object, true);
        if (object2 == null) {
            object = this.mThread.getSystemContext().mPackageInfo;
        }
        return ((LoadedApk)object).getAppFactory();
    }

    public static Application newApplication(Class<?> object, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        object = (Application)((Class)object).newInstance();
        ((Application)object).attach(context);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void postPerformCreate(Activity activity) {
        if (this.mActivityMonitors == null) return;
        Object object = this.mSync;
        synchronized (object) {
            int n = this.mActivityMonitors.size();
            int n2 = 0;
            while (n2 < n) {
                this.mActivityMonitors.get(n2).match(activity, activity, activity.getIntent());
                ++n2;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void prePerformCreate(Activity activity) {
        if (this.mWaitingActivities == null) return;
        Object object = this.mSync;
        synchronized (object) {
            int n = this.mWaitingActivities.size();
            int n2 = 0;
            while (n2 < n) {
                ActivityWaiter activityWaiter = this.mWaitingActivities.get(n2);
                if (activityWaiter.intent.filterEquals(activity.getIntent())) {
                    activityWaiter.activity = activity;
                    MessageQueue messageQueue = this.mMessageQueue;
                    ActivityGoing activityGoing = new ActivityGoing(activityWaiter);
                    messageQueue.addIdleHandler(activityGoing);
                }
                ++n2;
            }
            return;
        }
    }

    private final void validateNotAppThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }
        throw new RuntimeException("This method can not be called from the main application thread");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void waitForEnterAnimationComplete(Activity activity) {
        Object object = this.mAnimationCompleteLock;
        synchronized (object) {
            Throwable throwable2;
            block6 : {
                long l;
                long l2;
                for (long i = 5000L; i > 0L; i -= l - l2) {
                    try {
                        if (activity.mEnterAnimationComplete) break;
                        l2 = System.currentTimeMillis();
                        this.mAnimationCompleteLock.wait(i);
                        l = System.currentTimeMillis();
                        continue;
                    }
                    catch (Throwable throwable2) {
                        break block6;
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                        break;
                    }
                }
                return;
            }
            throw throwable2;
        }
    }

    public TestLooperManager acquireLooperManager(Looper looper) {
        this.checkInstrumenting("acquireLooperManager");
        return new TestLooperManager(looper);
    }

    public ActivityMonitor addMonitor(IntentFilter object, ActivityResult activityResult, boolean bl) {
        object = new ActivityMonitor((IntentFilter)object, activityResult, bl);
        this.addMonitor((ActivityMonitor)object);
        return object;
    }

    public ActivityMonitor addMonitor(String object, ActivityResult activityResult, boolean bl) {
        object = new ActivityMonitor((String)object, activityResult, bl);
        this.addMonitor((ActivityMonitor)object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addMonitor(ActivityMonitor activityMonitor) {
        Object object = this.mSync;
        synchronized (object) {
            if (this.mActivityMonitors == null) {
                ArrayList<ActivityMonitor> arrayList = new ArrayList<ActivityMonitor>();
                this.mActivityMonitors = arrayList;
            }
            this.mActivityMonitors.add(activityMonitor);
            return;
        }
    }

    public void addResults(Bundle bundle) {
        IActivityManager iActivityManager = ActivityManager.getService();
        try {
            iActivityManager.addInstrumentationResults(this.mThread.getApplicationThread(), bundle);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    final void basicInit(ActivityThread activityThread) {
        this.mThread = activityThread;
    }

    public void callActivityOnCreate(Activity activity, Bundle bundle) {
        this.prePerformCreate(activity);
        activity.performCreate(bundle);
        this.postPerformCreate(activity);
    }

    public void callActivityOnCreate(Activity activity, Bundle bundle, PersistableBundle persistableBundle) {
        this.prePerformCreate(activity);
        activity.performCreate(bundle, persistableBundle);
        this.postPerformCreate(activity);
    }

    public void callActivityOnDestroy(Activity activity) {
        activity.performDestroy();
    }

    public void callActivityOnNewIntent(Activity activity, Intent intent) {
        activity.performNewIntent(intent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void callActivityOnNewIntent(Activity activity, ReferrerIntent intent) {
        String string2;
        Throwable throwable2;
        block5 : {
            string2 = activity.mReferrer;
            if (intent != null) {
                try {
                    activity.mReferrer = intent.mReferrer;
                }
                catch (Throwable throwable2) {
                    break block5;
                }
            }
            if (intent != null) {
                Intent intent2 = new Intent(intent);
                intent = intent2;
            } else {
                intent = null;
            }
            this.callActivityOnNewIntent(activity, intent);
            activity.mReferrer = string2;
            return;
        }
        activity.mReferrer = string2;
        throw throwable2;
    }

    public void callActivityOnPause(Activity activity) {
        activity.performPause();
    }

    public void callActivityOnPostCreate(Activity activity, Bundle bundle) {
        activity.onPostCreate(bundle);
    }

    public void callActivityOnPostCreate(Activity activity, Bundle bundle, PersistableBundle persistableBundle) {
        activity.onPostCreate(bundle, persistableBundle);
    }

    public void callActivityOnRestart(Activity activity) {
        activity.onRestart();
    }

    public void callActivityOnRestoreInstanceState(Activity activity, Bundle bundle) {
        activity.performRestoreInstanceState(bundle);
    }

    public void callActivityOnRestoreInstanceState(Activity activity, Bundle bundle, PersistableBundle persistableBundle) {
        activity.performRestoreInstanceState(bundle, persistableBundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void callActivityOnResume(Activity activity) {
        activity.mResumed = true;
        activity.onResume();
        if (this.mActivityMonitors == null) return;
        Object object = this.mSync;
        synchronized (object) {
            int n = this.mActivityMonitors.size();
            int n2 = 0;
            while (n2 < n) {
                this.mActivityMonitors.get(n2).match(activity, activity, activity.getIntent());
                ++n2;
            }
            return;
        }
    }

    public void callActivityOnSaveInstanceState(Activity activity, Bundle bundle) {
        activity.performSaveInstanceState(bundle);
    }

    public void callActivityOnSaveInstanceState(Activity activity, Bundle bundle, PersistableBundle persistableBundle) {
        activity.performSaveInstanceState(bundle, persistableBundle);
    }

    public void callActivityOnStart(Activity activity) {
        activity.onStart();
    }

    public void callActivityOnStop(Activity activity) {
        activity.onStop();
    }

    public void callActivityOnUserLeaving(Activity activity) {
        activity.performUserLeaving();
    }

    public void callApplicationOnCreate(Application application) {
        application.onCreate();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean checkMonitorHit(ActivityMonitor activityMonitor, int n) {
        this.waitForIdleSync();
        Object object = this.mSync;
        synchronized (object) {
            if (activityMonitor.getHits() < n) {
                return false;
            }
            this.mActivityMonitors.remove(activityMonitor);
            return true;
        }
    }

    public void endPerformanceSnapshot() {
        if (!this.isProfiling()) {
            this.mPerfMetrics = this.mPerformanceCollector.endSnapshot();
        }
    }

    @UnsupportedAppUsage
    public void execStartActivities(Context context, IBinder iBinder, IBinder iBinder2, Activity activity, Intent[] arrintent, Bundle bundle) {
        this.execStartActivitiesAsUser(context, iBinder, iBinder2, activity, arrintent, bundle, context.getUserId());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public int execStartActivitiesAsUser(Context context, IBinder object, IBinder iBinder, Activity object2, Intent[] arrintent, Bundle bundle, int n) {
        int n2;
        IApplicationThread iApplicationThread = (IApplicationThread)object;
        if (this.mActivityMonitors != null) {
            object2 = this.mSync;
            synchronized (object2) {
                int n3 = this.mActivityMonitors.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    ActivityMonitor activityMonitor = this.mActivityMonitors.get(n2);
                    object = null;
                    if (activityMonitor.ignoreMatchingSpecificIntents()) {
                        object = activityMonitor.onStartActivity(arrintent[0]);
                    }
                    if (object != null) {
                        ++activityMonitor.mHits;
                        return -96;
                    }
                    if (!activityMonitor.match(context, null, arrintent[0])) continue;
                    ++activityMonitor.mHits;
                    if (!activityMonitor.isBlocking()) break;
                    return -96;
                }
            }
        }
        try {
            object = new String[arrintent.length];
            for (n2 = 0; n2 < arrintent.length; ++n2) {
                arrintent[n2].migrateExtraStreamToClipData();
                arrintent[n2].prepareToLeaveProcess(context);
                object[n2] = arrintent[n2].resolveTypeIfNeeded(context.getContentResolver());
            }
            n = ActivityTaskManager.getService().startActivities(iApplicationThread, context.getBasePackageName(), arrintent, (String[])object, iBinder, bundle, n);
            Instrumentation.checkStartActivityResult(n, arrintent[0]);
            return n;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("Failure from system", remoteException);
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
    public ActivityResult execStartActivity(Context object, IBinder object2, IBinder iBinder, Activity activity, Intent intent, int n, Bundle bundle) {
        IApplicationThread iApplicationThread;
        Object object3;
        IActivityTaskManager iActivityTaskManager;
        void var1_5;
        block15 : {
            iApplicationThread = (IApplicationThread)object2;
            iActivityTaskManager = null;
            object2 = activity != null ? activity.onProvideReferrer() : null;
            if (object2 != null) {
                intent.putExtra("android.intent.extra.REFERRER", (Parcelable)object2);
            }
            if (this.mActivityMonitors != null) {
                object3 = this.mSync;
                // MONITORENTER : object3
                int n2 = this.mActivityMonitors.size();
                for (int i = 0; i < n2; ++i) {
                    ActivityMonitor activityMonitor = this.mActivityMonitors.get(i);
                    object2 = null;
                    if (activityMonitor.ignoreMatchingSpecificIntents()) {
                        object2 = activityMonitor.onStartActivity(intent);
                    }
                    if (object2 != null) {
                        ++activityMonitor.mHits;
                        // MONITOREXIT : object3
                        return object2;
                    }
                    if (!activityMonitor.match((Context)object, null, intent)) continue;
                    ++activityMonitor.mHits;
                    if (!activityMonitor.isBlocking()) break;
                    object = iActivityTaskManager;
                    if (n >= 0) {
                        object = activityMonitor.getResult();
                    }
                    // MONITOREXIT : object3
                    return object;
                }
                // MONITOREXIT : object3
            }
            intent.migrateExtraStreamToClipData();
            intent.prepareToLeaveProcess((Context)object);
            iActivityTaskManager = ActivityTaskManager.getService();
            object2 = ((Context)object).getBasePackageName();
            object3 = intent.resolveTypeIfNeeded(((Context)object).getContentResolver());
            if (activity == null) break block15;
            try {
                object = activity.mEmbeddedID;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException("Failure from system", (Throwable)var1_5);
            }
        }
        object = null;
        try {
            Instrumentation.checkStartActivityResult(iActivityTaskManager.startActivity(iApplicationThread, (String)object2, intent, (String)object3, iBinder, (String)object, n, 0, null, bundle), intent);
            return null;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("Failure from system", (Throwable)var1_5);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        throw new RuntimeException("Failure from system", (Throwable)var1_5);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public ActivityResult execStartActivity(Context object, IBinder object2, IBinder iBinder, String string2, Intent intent, int n, Bundle bundle) {
        IApplicationThread iApplicationThread = (IApplicationThread)object2;
        object2 = this.mActivityMonitors;
        Object var9_10 = null;
        if (object2 != null) {
            Object object3 = this.mSync;
            synchronized (object3) {
                int n2 = this.mActivityMonitors.size();
                for (int i = 0; i < n2; ++i) {
                    ActivityMonitor activityMonitor = this.mActivityMonitors.get(i);
                    object2 = null;
                    if (activityMonitor.ignoreMatchingSpecificIntents()) {
                        object2 = activityMonitor.onStartActivity(intent);
                    }
                    if (object2 != null) {
                        ++activityMonitor.mHits;
                        return object2;
                    }
                    if (!activityMonitor.match((Context)object, null, intent)) continue;
                    ++activityMonitor.mHits;
                    if (!activityMonitor.isBlocking()) break;
                    object = var9_10;
                    if (n < 0) return object;
                    return activityMonitor.getResult();
                }
            }
        }
        try {
            intent.migrateExtraStreamToClipData();
            intent.prepareToLeaveProcess((Context)object);
            Instrumentation.checkStartActivityResult(ActivityTaskManager.getService().startActivity(iApplicationThread, ((Context)object).getBasePackageName(), intent, intent.resolveTypeIfNeeded(((Context)object).getContentResolver()), iBinder, string2, n, 0, null, bundle), intent);
            return null;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("Failure from system", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public ActivityResult execStartActivity(Context object, IBinder object2, IBinder iBinder, String string2, Intent intent, int n, Bundle bundle, UserHandle userHandle) {
        IApplicationThread iApplicationThread = (IApplicationThread)object2;
        object2 = this.mActivityMonitors;
        Object var10_11 = null;
        if (object2 != null) {
            Object object3 = this.mSync;
            synchronized (object3) {
                int n2 = this.mActivityMonitors.size();
                for (int i = 0; i < n2; ++i) {
                    ActivityMonitor activityMonitor = this.mActivityMonitors.get(i);
                    object2 = null;
                    if (activityMonitor.ignoreMatchingSpecificIntents()) {
                        object2 = activityMonitor.onStartActivity(intent);
                    }
                    if (object2 != null) {
                        ++activityMonitor.mHits;
                        return object2;
                    }
                    if (!activityMonitor.match((Context)object, null, intent)) continue;
                    ++activityMonitor.mHits;
                    if (!activityMonitor.isBlocking()) break;
                    object = var10_11;
                    if (n < 0) return object;
                    return activityMonitor.getResult();
                }
            }
        }
        try {
            intent.migrateExtraStreamToClipData();
            intent.prepareToLeaveProcess((Context)object);
            Instrumentation.checkStartActivityResult(ActivityTaskManager.getService().startActivityAsUser(iApplicationThread, ((Context)object).getBasePackageName(), intent, intent.resolveTypeIfNeeded(((Context)object).getContentResolver()), iBinder, string2, n, 0, null, bundle, userHandle.getIdentifier()), intent);
            return null;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("Failure from system", remoteException);
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
    public ActivityResult execStartActivityAsCaller(Context object, IBinder object2, IBinder iBinder, Activity activity, Intent intent, int n, Bundle bundle, IBinder iBinder2, boolean bl, int n2) {
        Object object3;
        void var1_5;
        IApplicationThread iApplicationThread = (IApplicationThread)object2;
        object2 = this.mActivityMonitors;
        String string2 = null;
        if (object2 != null) {
            object3 = this.mSync;
            // MONITORENTER : object3
            int n3 = this.mActivityMonitors.size();
            for (int i = 0; i < n3; ++i) {
                ActivityMonitor activityMonitor = this.mActivityMonitors.get(i);
                object2 = null;
                if (activityMonitor.ignoreMatchingSpecificIntents()) {
                    object2 = activityMonitor.onStartActivity(intent);
                }
                if (object2 != null) {
                    ++activityMonitor.mHits;
                    // MONITOREXIT : object3
                    return object2;
                }
                if (!activityMonitor.match((Context)object, null, intent)) continue;
                ++activityMonitor.mHits;
                if (!activityMonitor.isBlocking()) break;
                object = string2;
                if (n >= 0) {
                    object = activityMonitor.getResult();
                }
                // MONITOREXIT : object3
                return object;
            }
            // MONITOREXIT : object3
        }
        intent.migrateExtraStreamToClipData();
        intent.prepareToLeaveProcess((Context)object);
        object2 = ActivityTaskManager.getService();
        string2 = ((Context)object).getBasePackageName();
        object3 = intent.resolveTypeIfNeeded(((Context)object).getContentResolver());
        object = activity != null ? activity.mEmbeddedID : null;
        try {
            n = object2.startActivityAsCaller(iApplicationThread, string2, intent, (String)object3, iBinder, (String)object, n, 0, null, bundle, iBinder2, bl, n2);
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("Failure from system", (Throwable)var1_5);
        }
        try {
            Instrumentation.checkStartActivityResult(n, intent);
            return null;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("Failure from system", (Throwable)var1_5);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        throw new RuntimeException("Failure from system", (Throwable)var1_5);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void execStartActivityFromAppTask(Context context, IBinder object, IAppTask iAppTask, Intent intent, Bundle bundle) {
        IApplicationThread iApplicationThread = (IApplicationThread)object;
        if (this.mActivityMonitors != null) {
            Object object2 = this.mSync;
            synchronized (object2) {
                int n = this.mActivityMonitors.size();
                for (int i = 0; i < n; ++i) {
                    ActivityMonitor activityMonitor = this.mActivityMonitors.get(i);
                    object = null;
                    if (activityMonitor.ignoreMatchingSpecificIntents()) {
                        object = activityMonitor.onStartActivity(intent);
                    }
                    if (object != null) {
                        ++activityMonitor.mHits;
                        return;
                    }
                    if (!activityMonitor.match(context, null, intent)) continue;
                    ++activityMonitor.mHits;
                    if (!activityMonitor.isBlocking()) break;
                    return;
                }
            }
        }
        try {
            intent.migrateExtraStreamToClipData();
            intent.prepareToLeaveProcess(context);
            Instrumentation.checkStartActivityResult(iAppTask.startActivity(iApplicationThread.asBinder(), context.getBasePackageName(), intent, intent.resolveTypeIfNeeded(context.getContentResolver()), bundle), intent);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("Failure from system", remoteException);
        }
    }

    public void finish(int n, Bundle object) {
        if (this.mAutomaticPerformanceSnapshots) {
            this.endPerformanceSnapshot();
        }
        Bundle bundle = object;
        if (this.mPerfMetrics != null) {
            bundle = object;
            if (object == null) {
                bundle = new Bundle();
            }
            bundle.putAll(this.mPerfMetrics);
        }
        if ((object = this.mUiAutomation) != null && !((UiAutomation)object).isDestroyed()) {
            this.mUiAutomation.disconnect();
            this.mUiAutomation = null;
        }
        this.mThread.finishInstrumentation(n, bundle);
    }

    public Bundle getAllocCounts() {
        Bundle bundle = new Bundle();
        bundle.putLong("global_alloc_count", Debug.getGlobalAllocCount());
        bundle.putLong("global_alloc_size", Debug.getGlobalAllocSize());
        bundle.putLong("global_freed_count", Debug.getGlobalFreedCount());
        bundle.putLong("global_freed_size", Debug.getGlobalFreedSize());
        bundle.putLong("gc_invocation_count", Debug.getGlobalGcInvocationCount());
        return bundle;
    }

    public Bundle getBinderCounts() {
        Bundle bundle = new Bundle();
        bundle.putLong("sent_transactions", Debug.getBinderSentTransactions());
        bundle.putLong("received_transactions", Debug.getBinderReceivedTransactions());
        return bundle;
    }

    public ComponentName getComponentName() {
        return this.mComponent;
    }

    public Context getContext() {
        return this.mInstrContext;
    }

    public String getProcessName() {
        return this.mThread.getProcessName();
    }

    public Context getTargetContext() {
        return this.mAppContext;
    }

    public UiAutomation getUiAutomation() {
        return this.getUiAutomation(0);
    }

    public UiAutomation getUiAutomation(int n) {
        UiAutomation uiAutomation = this.mUiAutomation;
        boolean bl = uiAutomation == null || uiAutomation.isDestroyed();
        if (this.mUiAutomationConnection != null) {
            if (!bl && this.mUiAutomation.getFlags() == n) {
                return this.mUiAutomation;
            }
            if (bl) {
                this.mUiAutomation = new UiAutomation(this.getTargetContext().getMainLooper(), this.mUiAutomationConnection);
            } else {
                this.mUiAutomation.disconnect();
            }
            this.mUiAutomation.connect(n);
            return this.mUiAutomation;
        }
        return null;
    }

    final void init(ActivityThread activityThread, Context context, Context context2, ComponentName componentName, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection) {
        this.mThread = activityThread;
        this.mThread.getLooper();
        this.mMessageQueue = Looper.myQueue();
        this.mInstrContext = context;
        this.mAppContext = context2;
        this.mComponent = componentName;
        this.mWatcher = iInstrumentationWatcher;
        this.mUiAutomationConnection = iUiAutomationConnection;
    }

    public boolean invokeContextMenuAction(Activity object, int n, int n2) {
        this.validateNotAppThread();
        this.sendKeySync(new KeyEvent(0, 23));
        this.waitForIdleSync();
        try {
            Thread.sleep(ViewConfiguration.getLongPressTimeout());
            this.sendKeySync(new KeyEvent(1, 23));
            this.waitForIdleSync();
            object = new 1ContextMenuRunnable((Activity)object, n, n2);
            this.runOnMainSync((Runnable)object);
            return ((1ContextMenuRunnable)object).returnValue;
        }
        catch (InterruptedException interruptedException) {
            Log.e(TAG, "Could not sleep for long press timeout", interruptedException);
            return false;
        }
    }

    public boolean invokeMenuActionSync(Activity object, int n, int n2) {
        object = new 1MenuRunnable((Activity)object, n, n2);
        this.runOnMainSync((Runnable)object);
        return ((1MenuRunnable)object).returnValue;
    }

    public boolean isProfiling() {
        return this.mThread.isProfiling();
    }

    public Activity newActivity(Class<?> object, Context context, IBinder iBinder, Application application, Intent intent, ActivityInfo activityInfo, CharSequence charSequence, Activity activity, String string2, Object object2) throws InstantiationException, IllegalAccessException {
        object = (Activity)((Class)object).newInstance();
        if (application == null) {
            application = new Application();
        }
        ((Activity)object).attach(context, null, this, iBinder, 0, application, intent, activityInfo, charSequence, activity, string2, (Activity.NonConfigurationInstances)object2, new Configuration(), null, null, null, null, null);
        return object;
    }

    public Activity newActivity(ClassLoader classLoader, String string2, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String string3 = intent != null && intent.getComponent() != null ? intent.getComponent().getPackageName() : null;
        return this.getFactory(string3).instantiateActivity(classLoader, string2, intent);
    }

    public Application newApplication(ClassLoader object, String string2, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        object = this.getFactory(context.getPackageName()).instantiateApplication((ClassLoader)object, string2);
        ((Application)object).attach(context);
        return object;
    }

    public void onCreate(Bundle bundle) {
    }

    public void onDestroy() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onEnterAnimationComplete() {
        Object object = this.mAnimationCompleteLock;
        synchronized (object) {
            this.mAnimationCompleteLock.notifyAll();
            return;
        }
    }

    public boolean onException(Object object, Throwable throwable) {
        return false;
    }

    public void onStart() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeMonitor(ActivityMonitor activityMonitor) {
        Object object = this.mSync;
        synchronized (object) {
            this.mActivityMonitors.remove(activityMonitor);
            return;
        }
    }

    public void runOnMainSync(Runnable runnable) {
        this.validateNotAppThread();
        runnable = new SyncRunnable(runnable);
        this.mThread.getHandler().post(runnable);
        ((SyncRunnable)runnable).waitForComplete();
    }

    public void sendCharacterSync(int n) {
        this.sendKeySync(new KeyEvent(0, n));
        this.sendKeySync(new KeyEvent(1, n));
    }

    public void sendKeyDownUpSync(int n) {
        this.sendKeySync(new KeyEvent(0, n));
        this.sendKeySync(new KeyEvent(1, n));
    }

    public void sendKeySync(KeyEvent keyEvent) {
        int n;
        this.validateNotAppThread();
        long l = keyEvent.getDownTime();
        long l2 = keyEvent.getEventTime();
        int n2 = n = keyEvent.getSource();
        if (n == 0) {
            n2 = 257;
        }
        long l3 = l2;
        if (l2 == 0L) {
            l3 = SystemClock.uptimeMillis();
        }
        l2 = l;
        if (l == 0L) {
            l2 = l3;
        }
        KeyEvent keyEvent2 = new KeyEvent(keyEvent);
        keyEvent2.setTime(l2, l3);
        keyEvent2.setSource(n2);
        keyEvent2.setFlags(keyEvent.getFlags() | 8);
        InputManager.getInstance().injectInputEvent(keyEvent2, 2);
    }

    public void sendPointerSync(MotionEvent motionEvent) {
        this.validateNotAppThread();
        if ((motionEvent.getSource() & 2) == 0) {
            motionEvent.setSource(4098);
        }
        try {
            WindowManagerGlobal.getWindowManagerService().injectInputAfterTransactionsApplied(motionEvent, 2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void sendStatus(int n, Bundle bundle) {
        IInstrumentationWatcher iInstrumentationWatcher = this.mWatcher;
        if (iInstrumentationWatcher != null) {
            try {
                iInstrumentationWatcher.instrumentationStatus(this.mComponent, n, bundle);
            }
            catch (RemoteException remoteException) {
                this.mWatcher = null;
            }
        }
    }

    public void sendStringSync(String arrkeyEvent) {
        if (arrkeyEvent == null) {
            return;
        }
        arrkeyEvent = KeyCharacterMap.load(-1).getEvents(arrkeyEvent.toCharArray());
        if (arrkeyEvent != null) {
            for (int i = 0; i < arrkeyEvent.length; ++i) {
                this.sendKeySync(KeyEvent.changeTimeRepeat(arrkeyEvent[i], SystemClock.uptimeMillis(), 0));
            }
        }
    }

    public void sendTrackballEventSync(MotionEvent motionEvent) {
        this.validateNotAppThread();
        if ((motionEvent.getSource() & 4) == 0) {
            motionEvent.setSource(65540);
        }
        InputManager.getInstance().injectInputEvent(motionEvent, 2);
    }

    public void setAutomaticPerformanceSnapshots() {
        this.mAutomaticPerformanceSnapshots = true;
        this.mPerformanceCollector = new PerformanceCollector();
    }

    public void setInTouchMode(boolean bl) {
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window")).setInTouchMode(bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void start() {
        if (this.mRunner == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Instr: ");
            stringBuilder.append(this.getClass().getName());
            this.mRunner = new InstrumentationThread(stringBuilder.toString());
            this.mRunner.start();
            return;
        }
        throw new RuntimeException("Instrumentation already started");
    }

    public Activity startActivitySync(Intent intent) {
        return this.startActivitySync(intent, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Activity startActivitySync(Intent object, Bundle object2) {
        this.validateNotAppThread();
        Object object3 = this.mSync;
        synchronized (object3) {
            Intent intent = new Intent((Intent)object);
            object = intent.resolveActivityInfo(this.getTargetContext().getPackageManager(), 0);
            if (object == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unable to resolve activity for: ");
                ((StringBuilder)object2).append(intent);
                object = new RuntimeException(((StringBuilder)object2).toString());
                throw object;
            }
            Object object4 = this.mThread.getProcessName();
            if (!((ActivityInfo)object).processName.equals(object4)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Intent in process ");
                ((StringBuilder)object2).append((String)object4);
                ((StringBuilder)object2).append(" resolved to different process ");
                ((StringBuilder)object2).append(((ActivityInfo)object).processName);
                ((StringBuilder)object2).append(": ");
                ((StringBuilder)object2).append(intent);
                RuntimeException runtimeException = new RuntimeException(((StringBuilder)object2).toString());
                throw runtimeException;
            }
            object4 = new Object(object.applicationInfo.packageName, ((ActivityInfo)object).name);
            intent.setComponent((ComponentName)object4);
            object = new ActivityWaiter(intent);
            if (this.mWaitingActivities == null) {
                object4 = new Object();
                this.mWaitingActivities = object4;
            }
            this.mWaitingActivities.add((ActivityWaiter)object);
            this.getTargetContext().startActivity(intent, (Bundle)object2);
            do {
                try {
                    this.mSync.wait();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            } while (this.mWaitingActivities.contains(object));
            this.waitForEnterAnimationComplete(((ActivityWaiter)object).activity);
            return ((ActivityWaiter)object).activity;
        }
    }

    @Deprecated
    public void startAllocCounting() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
        Debug.resetAllCounts();
        Debug.startAllocCounting();
    }

    public void startPerformanceSnapshot() {
        if (!this.isProfiling()) {
            this.mPerformanceCollector.beginSnapshot(null);
        }
    }

    public void startProfiling() {
        if (this.mThread.isProfiling()) {
            File file = new File(this.mThread.getProfileFilePath());
            file.getParentFile().mkdirs();
            Debug.startMethodTracing(file.toString(), 8388608);
        }
    }

    @Deprecated
    public void stopAllocCounting() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
        Debug.stopAllocCounting();
    }

    public void stopProfiling() {
        if (this.mThread.isProfiling()) {
            Debug.stopMethodTracing();
        }
    }

    public void waitForIdle(Runnable runnable) {
        this.mMessageQueue.addIdleHandler(new Idler(runnable));
        this.mThread.getHandler().post(new EmptyRunnable());
    }

    public void waitForIdleSync() {
        this.validateNotAppThread();
        Idler idler = new Idler(null);
        this.mMessageQueue.addIdleHandler(idler);
        this.mThread.getHandler().post(new EmptyRunnable());
        idler.waitForIdle();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Activity waitForMonitor(ActivityMonitor activityMonitor) {
        Activity activity = activityMonitor.waitForActivity();
        Object object = this.mSync;
        synchronized (object) {
            this.mActivityMonitors.remove(activityMonitor);
            return activity;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Activity waitForMonitorWithTimeout(ActivityMonitor activityMonitor, long l) {
        Activity activity = activityMonitor.waitForActivityWithTimeout(l);
        Object object = this.mSync;
        synchronized (object) {
            this.mActivityMonitors.remove(activityMonitor);
            return activity;
        }
    }

    class 1ContextMenuRunnable
    implements Runnable {
        private final Activity activity;
        private final int flags;
        private final int identifier;
        boolean returnValue;

        public 1ContextMenuRunnable(Activity activity, int n, int n2) {
            this.activity = activity;
            this.identifier = n;
            this.flags = n2;
        }

        @Override
        public void run() {
            this.returnValue = this.activity.getWindow().performContextMenuIdentifierAction(this.identifier, this.flags);
        }
    }

    class 1MenuRunnable
    implements Runnable {
        private final Activity activity;
        private final int flags;
        private final int identifier;
        boolean returnValue;

        public 1MenuRunnable(Activity activity, int n, int n2) {
            this.activity = activity;
            this.identifier = n;
            this.flags = n2;
        }

        @Override
        public void run() {
            this.returnValue = this.activity.getWindow().performPanelIdentifierAction(0, this.identifier, this.flags);
        }
    }

    private final class ActivityGoing
    implements MessageQueue.IdleHandler {
        private final ActivityWaiter mWaiter;

        public ActivityGoing(ActivityWaiter activityWaiter) {
            this.mWaiter = activityWaiter;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final boolean queueIdle() {
            Object object = Instrumentation.this.mSync;
            synchronized (object) {
                Instrumentation.this.mWaitingActivities.remove(this.mWaiter);
                Instrumentation.this.mSync.notifyAll();
                return false;
            }
        }
    }

    public static class ActivityMonitor {
        private final boolean mBlock;
        private final String mClass;
        int mHits = 0;
        private final boolean mIgnoreMatchingSpecificIntents;
        Activity mLastActivity = null;
        private final ActivityResult mResult;
        private final IntentFilter mWhich;

        public ActivityMonitor() {
            this.mWhich = null;
            this.mClass = null;
            this.mResult = null;
            this.mBlock = false;
            this.mIgnoreMatchingSpecificIntents = true;
        }

        public ActivityMonitor(IntentFilter intentFilter, ActivityResult activityResult, boolean bl) {
            this.mWhich = intentFilter;
            this.mClass = null;
            this.mResult = activityResult;
            this.mBlock = bl;
            this.mIgnoreMatchingSpecificIntents = false;
        }

        public ActivityMonitor(String string2, ActivityResult activityResult, boolean bl) {
            this.mWhich = null;
            this.mClass = string2;
            this.mResult = activityResult;
            this.mBlock = bl;
            this.mIgnoreMatchingSpecificIntents = false;
        }

        public final IntentFilter getFilter() {
            return this.mWhich;
        }

        public final int getHits() {
            return this.mHits;
        }

        public final Activity getLastActivity() {
            return this.mLastActivity;
        }

        public final ActivityResult getResult() {
            return this.mResult;
        }

        final boolean ignoreMatchingSpecificIntents() {
            return this.mIgnoreMatchingSpecificIntents;
        }

        public final boolean isBlocking() {
            return this.mBlock;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        final boolean match(Context object, Activity activity, Intent intent) {
            if (this.mIgnoreMatchingSpecificIntents) {
                return false;
            }
            synchronized (this) {
                if (this.mWhich != null && this.mWhich.match(((Context)object).getContentResolver(), intent, true, Instrumentation.TAG) < 0) {
                    return false;
                }
                if (this.mClass != null) {
                    object = null;
                    if (activity != null) {
                        object = activity.getClass().getName();
                    } else if (intent.getComponent() != null) {
                        object = intent.getComponent().getClassName();
                    }
                    if (object == null || !this.mClass.equals(object)) {
                        return false;
                    }
                }
                if (activity != null) {
                    this.mLastActivity = activity;
                    this.notifyAll();
                }
                return true;
            }
        }

        public ActivityResult onStartActivity(Intent intent) {
            return null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final Activity waitForActivity() {
            synchronized (this) {
                do {
                    Activity activity;
                    if ((activity = this.mLastActivity) != null) {
                        activity = this.mLastActivity;
                        this.mLastActivity = null;
                        return activity;
                    }
                    try {
                        this.wait();
                    }
                    catch (InterruptedException interruptedException) {
                    }
                } while (true);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final Activity waitForActivityWithTimeout(long l) {
            synchronized (this) {
                Activity activity = this.mLastActivity;
                if (activity == null) {
                    try {
                        this.wait(l);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
                if (this.mLastActivity == null) {
                    return null;
                }
                activity = this.mLastActivity;
                this.mLastActivity = null;
                return activity;
            }
        }
    }

    public static final class ActivityResult {
        private final int mResultCode;
        private final Intent mResultData;

        public ActivityResult(int n, Intent intent) {
            this.mResultCode = n;
            this.mResultData = intent;
        }

        public int getResultCode() {
            return this.mResultCode;
        }

        public Intent getResultData() {
            return this.mResultData;
        }
    }

    private static final class ActivityWaiter {
        public Activity activity;
        public final Intent intent;

        public ActivityWaiter(Intent intent) {
            this.intent = intent;
        }
    }

    private static final class EmptyRunnable
    implements Runnable {
        private EmptyRunnable() {
        }

        @Override
        public void run() {
        }
    }

    private static final class Idler
    implements MessageQueue.IdleHandler {
        private final Runnable mCallback;
        private boolean mIdle;

        public Idler(Runnable runnable) {
            this.mCallback = runnable;
            this.mIdle = false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final boolean queueIdle() {
            Runnable runnable = this.mCallback;
            if (runnable != null) {
                runnable.run();
            }
            synchronized (this) {
                this.mIdle = true;
                this.notifyAll();
                return false;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void waitForIdle() {
            synchronized (this) {
                boolean bl;
                while (!(bl = this.mIdle)) {
                    try {
                        this.wait();
                    }
                    catch (InterruptedException interruptedException) {
                    }
                }
                return;
            }
        }
    }

    private final class InstrumentationThread
    extends Thread {
        public InstrumentationThread(String string2) {
            super(string2);
        }

        @Override
        public void run() {
            try {
                Process.setThreadPriority(-8);
            }
            catch (RuntimeException runtimeException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception setting priority of instrumentation thread ");
                stringBuilder.append(Process.myTid());
                Log.w(Instrumentation.TAG, stringBuilder.toString(), runtimeException);
            }
            if (Instrumentation.this.mAutomaticPerformanceSnapshots) {
                Instrumentation.this.startPerformanceSnapshot();
            }
            Instrumentation.this.onStart();
        }
    }

    private static final class SyncRunnable
    implements Runnable {
        private boolean mComplete;
        private final Runnable mTarget;

        public SyncRunnable(Runnable runnable) {
            this.mTarget = runnable;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            this.mTarget.run();
            synchronized (this) {
                this.mComplete = true;
                this.notifyAll();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void waitForComplete() {
            synchronized (this) {
                boolean bl;
                while (!(bl = this.mComplete)) {
                    try {
                        this.wait();
                    }
                    catch (InterruptedException interruptedException) {
                    }
                }
                return;
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface UiAutomationFlags {
    }

}

