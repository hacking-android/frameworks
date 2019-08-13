/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.WindowManager;
import android.view.contentcapture.ContentCaptureCondition;
import android.view.contentcapture.ContentCaptureHelper;
import android.view.contentcapture.DataRemovalRequest;
import android.view.contentcapture.IContentCaptureManager;
import android.view.contentcapture.MainContentCaptureSession;
import android.view.contentcapture._$$Lambda$ContentCaptureManager$F5a5O5ubPHwlndmmnmOInl75_sQ;
import android.view.contentcapture._$$Lambda$ContentCaptureManager$uvjEvSXcmP7_uA6i89N3m1TrKCk;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.Preconditions;
import com.android.internal.util.SyncResultReceiver;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Set;

public final class ContentCaptureManager {
    public static final int DEFAULT_IDLE_FLUSHING_FREQUENCY_MS = 5000;
    public static final int DEFAULT_LOG_HISTORY_SIZE = 10;
    public static final int DEFAULT_MAX_BUFFER_SIZE = 100;
    public static final int DEFAULT_TEXT_CHANGE_FLUSHING_FREQUENCY_MS = 1000;
    public static final String DEVICE_CONFIG_PROPERTY_IDLE_FLUSH_FREQUENCY = "idle_flush_frequency";
    public static final String DEVICE_CONFIG_PROPERTY_IDLE_UNBIND_TIMEOUT = "idle_unbind_timeout";
    public static final String DEVICE_CONFIG_PROPERTY_LOGGING_LEVEL = "logging_level";
    public static final String DEVICE_CONFIG_PROPERTY_LOG_HISTORY_SIZE = "log_history_size";
    public static final String DEVICE_CONFIG_PROPERTY_MAX_BUFFER_SIZE = "max_buffer_size";
    public static final String DEVICE_CONFIG_PROPERTY_SERVICE_EXPLICITLY_ENABLED = "service_explicitly_enabled";
    public static final String DEVICE_CONFIG_PROPERTY_TEXT_CHANGE_FLUSH_FREQUENCY = "text_change_flush_frequency";
    public static final int LOGGING_LEVEL_DEBUG = 1;
    public static final int LOGGING_LEVEL_OFF = 0;
    public static final int LOGGING_LEVEL_VERBOSE = 2;
    public static final int RESULT_CODE_FALSE = 2;
    public static final int RESULT_CODE_OK = 0;
    public static final int RESULT_CODE_SECURITY_EXCEPTION = -1;
    public static final int RESULT_CODE_TRUE = 1;
    private static final int SYNC_CALLS_TIMEOUT_MS = 5000;
    private static final String TAG = ContentCaptureManager.class.getSimpleName();
    private final Context mContext;
    @GuardedBy(value={"mLock"})
    private int mFlags;
    private final Handler mHandler;
    private final Object mLock = new Object();
    @GuardedBy(value={"mLock"})
    private MainContentCaptureSession mMainSession;
    final ContentCaptureOptions mOptions;
    private final IContentCaptureManager mService;

    public ContentCaptureManager(Context context, IContentCaptureManager object, ContentCaptureOptions object2) {
        this.mContext = Preconditions.checkNotNull(context, "context cannot be null");
        this.mService = Preconditions.checkNotNull(object, "service cannot be null");
        this.mOptions = Preconditions.checkNotNull(object2, "options cannot be null");
        ContentCaptureHelper.setLoggingLevel(this.mOptions.loggingLevel);
        if (ContentCaptureHelper.sVerbose) {
            object = TAG;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Constructor for ");
            ((StringBuilder)object2).append(context.getPackageName());
            Log.v((String)object, ((StringBuilder)object2).toString());
        }
        this.mHandler = Handler.createAsync(Looper.getMainLooper());
    }

    public static ComponentName getServiceSettingsComponentName() {
        IBinder iBinder = ServiceManager.checkService("content_capture");
        if (iBinder == null) {
            return null;
        }
        Object object = IContentCaptureManager.Stub.asInterface(iBinder);
        iBinder = new SyncResultReceiver(5000);
        try {
            object.getServiceSettingsActivity((IResultReceiver)((Object)iBinder));
            if (((SyncResultReceiver)iBinder).getIntResult() != -1) {
                return (ComponentName)((SyncResultReceiver)iBinder).getParcelableResult();
            }
            object = new SecurityException(((SyncResultReceiver)iBinder).getStringResult());
            throw object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private SyncResultReceiver syncRun(MyRunnable object) {
        SyncResultReceiver syncResultReceiver;
        block3 : {
            syncResultReceiver = new SyncResultReceiver(5000);
            try {
                object.run(syncResultReceiver);
                if (syncResultReceiver.getIntResult() == -1) break block3;
                return syncResultReceiver;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        object = new SecurityException(syncResultReceiver.getStringResult());
        throw object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(String object, PrintWriter printWriter) {
        printWriter.print((String)object);
        printWriter.println("ContentCaptureManager");
        CharSequence charSequence = new StringBuilder();
        charSequence.append((String)object);
        charSequence.append("  ");
        charSequence = charSequence.toString();
        object = this.mLock;
        synchronized (object) {
            printWriter.print((String)charSequence);
            printWriter.print("isContentCaptureEnabled(): ");
            printWriter.println(this.isContentCaptureEnabled());
            printWriter.print((String)charSequence);
            printWriter.print("Debug: ");
            printWriter.print(ContentCaptureHelper.sDebug);
            printWriter.print(" Verbose: ");
            printWriter.println(ContentCaptureHelper.sVerbose);
            printWriter.print((String)charSequence);
            printWriter.print("Context: ");
            printWriter.println(this.mContext);
            printWriter.print((String)charSequence);
            printWriter.print("User: ");
            printWriter.println(this.mContext.getUserId());
            printWriter.print((String)charSequence);
            printWriter.print("Service: ");
            printWriter.println(this.mService);
            printWriter.print((String)charSequence);
            printWriter.print("Flags: ");
            printWriter.println(this.mFlags);
            printWriter.print((String)charSequence);
            printWriter.print("Options: ");
            this.mOptions.dumpShort(printWriter);
            printWriter.println();
            if (this.mMainSession != null) {
                CharSequence charSequence2 = new StringBuilder();
                charSequence2.append((String)charSequence);
                charSequence2.append("  ");
                charSequence2 = charSequence2.toString();
                printWriter.print((String)charSequence);
                printWriter.println("Main session:");
                this.mMainSession.dump((String)charSequence2, printWriter);
            } else {
                printWriter.print((String)charSequence);
                printWriter.println("No sessions");
            }
            return;
        }
    }

    public void flush(int n) {
        if (this.mOptions.lite) {
            return;
        }
        this.getMainContentCaptureSession().flush(n);
    }

    public Set<ContentCaptureCondition> getContentCaptureConditions() {
        if (!this.isContentCaptureEnabled() && !this.mOptions.lite) {
            return null;
        }
        SyncResultReceiver syncResultReceiver = this.syncRun(new _$$Lambda$ContentCaptureManager$F5a5O5ubPHwlndmmnmOInl75_sQ(this));
        return ContentCaptureHelper.toSet(syncResultReceiver.getParcelableListResult());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MainContentCaptureSession getMainContentCaptureSession() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mMainSession != null) return this.mMainSession;
            Object object2 = new MainContentCaptureSession(this.mContext, this, this.mHandler, this.mService);
            this.mMainSession = object2;
            if (!ContentCaptureHelper.sVerbose) return this.mMainSession;
            String string2 = TAG;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("getMainContentCaptureSession(): created ");
            ((StringBuilder)object2).append(this.mMainSession);
            Log.v(string2, ((StringBuilder)object2).toString());
            return this.mMainSession;
        }
    }

    public ComponentName getServiceComponentName() {
        if (!this.isContentCaptureEnabled() && !this.mOptions.lite) {
            return null;
        }
        Object object = new SyncResultReceiver(5000);
        try {
            this.mService.getServiceComponentName((IResultReceiver)object);
            object = (ComponentName)((SyncResultReceiver)object).getParcelableResult();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isContentCaptureEnabled() {
        if (this.mOptions.lite) {
            return false;
        }
        Object object = this.mLock;
        synchronized (object) {
            MainContentCaptureSession mainContentCaptureSession = this.mMainSession;
            return mainContentCaptureSession == null || !mainContentCaptureSession.isDisabled();
        }
    }

    @SystemApi
    public boolean isContentCaptureFeatureEnabled() {
        int n = this.syncRun(new _$$Lambda$ContentCaptureManager$uvjEvSXcmP7_uA6i89N3m1TrKCk(this)).getIntResult();
        if (n != 1) {
            if (n != 2) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("received invalid result: ");
                stringBuilder.append(n);
                Log.wtf(string2, stringBuilder.toString());
                return false;
            }
            return false;
        }
        return true;
    }

    public /* synthetic */ void lambda$getContentCaptureConditions$0$ContentCaptureManager(SyncResultReceiver syncResultReceiver) throws RemoteException {
        this.mService.getContentCaptureConditions(this.mContext.getPackageName(), syncResultReceiver);
    }

    public /* synthetic */ void lambda$isContentCaptureFeatureEnabled$1$ContentCaptureManager(SyncResultReceiver syncResultReceiver) throws RemoteException {
        this.mService.isContentCaptureFeatureEnabled(syncResultReceiver);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onActivityCreated(IBinder iBinder, ComponentName componentName) {
        if (this.mOptions.lite) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            this.getMainContentCaptureSession().start(iBinder, componentName, this.mFlags);
            return;
        }
    }

    public void onActivityDestroyed() {
        if (this.mOptions.lite) {
            return;
        }
        this.getMainContentCaptureSession().destroy();
    }

    public void onActivityPaused() {
        if (this.mOptions.lite) {
            return;
        }
        this.getMainContentCaptureSession().notifySessionLifecycle(false);
    }

    public void onActivityResumed() {
        if (this.mOptions.lite) {
            return;
        }
        this.getMainContentCaptureSession().notifySessionLifecycle(true);
    }

    public void removeData(DataRemovalRequest dataRemovalRequest) {
        Preconditions.checkNotNull(dataRemovalRequest);
        try {
            this.mService.removeData(dataRemovalRequest);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setContentCaptureEnabled(boolean bl) {
        Object object;
        Object object2;
        if (ContentCaptureHelper.sDebug) {
            object = TAG;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("setContentCaptureEnabled(): setting to ");
            ((StringBuilder)object2).append(bl);
            ((StringBuilder)object2).append(" for ");
            ((StringBuilder)object2).append(this.mContext);
            Log.d((String)object, ((StringBuilder)object2).toString());
        }
        object = this.mLock;
        // MONITORENTER : object
        this.mFlags = bl ? (this.mFlags &= -2) : (this.mFlags |= 1);
        object2 = this.mMainSession;
        // MONITOREXIT : object
        if (object2 == null) return;
        ((MainContentCaptureSession)object2).setDisabled(bl ^ true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void updateWindowAttributes(WindowManager.LayoutParams object) {
        Object object2;
        if (ContentCaptureHelper.sDebug) {
            object2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateWindowAttributes(): window flags=");
            stringBuilder.append(((WindowManager.LayoutParams)object).flags);
            Log.d((String)object2, stringBuilder.toString());
        }
        boolean bl = (((WindowManager.LayoutParams)object).flags & 8192) != 0;
        object = this.mLock;
        // MONITORENTER : object
        this.mFlags = bl ? (this.mFlags |= 2) : (this.mFlags &= -3);
        object2 = this.mMainSession;
        // MONITOREXIT : object
        if (object2 == null) return;
        ((MainContentCaptureSession)object2).setDisabled(bl);
    }

    public static interface ContentCaptureClient {
        public ComponentName contentCaptureClientGetComponentName();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LoggingLevel {
    }

    private static interface MyRunnable {
        public void run(SyncResultReceiver var1) throws RemoteException;
    }

}

