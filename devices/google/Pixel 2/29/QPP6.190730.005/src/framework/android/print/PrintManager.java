/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.print;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.print.ILayoutResultCallback;
import android.print.IPrintDocumentAdapter;
import android.print.IPrintDocumentAdapterObserver;
import android.print.IPrintJobStateChangeListener;
import android.print.IPrintManager;
import android.print.IPrintServicesChangeListener;
import android.print.IWriteResultCallback;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintJob;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrinterDiscoverySession;
import android.print.PrinterId;
import android.print._$$Lambda$KZ41E_yXUNYMY9k_Xeus1UG_cS8;
import android.print._$$Lambda$c2Elb5E1w2yc6lr236iX_RUAL5Q;
import android.printservice.PrintServiceInfo;
import android.printservice.recommendation.IRecommendationsChangeListener;
import android.printservice.recommendation.RecommendationInfo;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import libcore.io.IoUtils;

public final class PrintManager {
    public static final String ACTION_PRINT_DIALOG = "android.print.PRINT_DIALOG";
    public static final int ALL_SERVICES = 3;
    public static final int APP_ID_ANY = -2;
    private static final boolean DEBUG = false;
    public static final int DISABLED_SERVICES = 2;
    @SystemApi
    public static final int ENABLED_SERVICES = 1;
    public static final String EXTRA_PRINT_DIALOG_INTENT = "android.print.intent.extra.EXTRA_PRINT_DIALOG_INTENT";
    public static final String EXTRA_PRINT_DOCUMENT_ADAPTER = "android.print.intent.extra.EXTRA_PRINT_DOCUMENT_ADAPTER";
    public static final String EXTRA_PRINT_JOB = "android.print.intent.extra.EXTRA_PRINT_JOB";
    private static final String LOG_TAG = "PrintManager";
    private static final int MSG_NOTIFY_PRINT_JOB_STATE_CHANGED = 1;
    public static final String PRINT_SPOOLER_PACKAGE_NAME = "com.android.printspooler";
    private final int mAppId;
    private final Context mContext;
    private final Handler mHandler;
    private Map<PrintJobStateChangeListener, PrintJobStateChangeListenerWrapper> mPrintJobStateChangeListeners;
    private Map<PrintServiceRecommendationsChangeListener, PrintServiceRecommendationsChangeListenerWrapper> mPrintServiceRecommendationsChangeListeners;
    private Map<PrintServicesChangeListener, PrintServicesChangeListenerWrapper> mPrintServicesChangeListeners;
    private final IPrintManager mService;
    private final int mUserId;

    public PrintManager(Context context, IPrintManager iPrintManager, int n, int n2) {
        this.mContext = context;
        this.mService = iPrintManager;
        this.mUserId = n;
        this.mAppId = n2;
        this.mHandler = new Handler(context.getMainLooper(), null, false){

            @Override
            public void handleMessage(Message object) {
                if (((Message)object).what == 1) {
                    object = (SomeArgs)((Message)object).obj;
                    PrintJobStateChangeListener printJobStateChangeListener = ((PrintJobStateChangeListenerWrapper)((SomeArgs)object).arg1).getListener();
                    if (printJobStateChangeListener != null) {
                        printJobStateChangeListener.onPrintJobStateChanged((PrintJobId)((SomeArgs)object).arg2);
                    }
                    ((SomeArgs)object).recycle();
                }
            }
        };
    }

    @UnsupportedAppUsage
    public void addPrintJobStateChangeListener(PrintJobStateChangeListener printJobStateChangeListener) {
        if (this.mService == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        if (this.mPrintJobStateChangeListeners == null) {
            this.mPrintJobStateChangeListeners = new ArrayMap<PrintJobStateChangeListener, PrintJobStateChangeListenerWrapper>();
        }
        PrintJobStateChangeListenerWrapper printJobStateChangeListenerWrapper = new PrintJobStateChangeListenerWrapper(printJobStateChangeListener, this.mHandler);
        try {
            this.mService.addPrintJobStateChangeListener(printJobStateChangeListenerWrapper, this.mAppId, this.mUserId);
            this.mPrintJobStateChangeListeners.put(printJobStateChangeListener, printJobStateChangeListenerWrapper);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void addPrintServiceRecommendationsChangeListener(PrintServiceRecommendationsChangeListener printServiceRecommendationsChangeListener, Handler object) {
        Preconditions.checkNotNull(printServiceRecommendationsChangeListener);
        Handler handler = object;
        if (object == null) {
            handler = this.mHandler;
        }
        if (this.mService == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        if (this.mPrintServiceRecommendationsChangeListeners == null) {
            this.mPrintServiceRecommendationsChangeListeners = new ArrayMap<PrintServiceRecommendationsChangeListener, PrintServiceRecommendationsChangeListenerWrapper>();
        }
        object = new PrintServiceRecommendationsChangeListenerWrapper(printServiceRecommendationsChangeListener, handler);
        try {
            this.mService.addPrintServiceRecommendationsChangeListener((IRecommendationsChangeListener)object, this.mUserId);
            this.mPrintServiceRecommendationsChangeListeners.put(printServiceRecommendationsChangeListener, (PrintServiceRecommendationsChangeListenerWrapper)object);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void addPrintServicesChangeListener(PrintServicesChangeListener printServicesChangeListener, Handler object) {
        Preconditions.checkNotNull(printServicesChangeListener);
        Handler handler = object;
        if (object == null) {
            handler = this.mHandler;
        }
        if (this.mService == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        if (this.mPrintServicesChangeListeners == null) {
            this.mPrintServicesChangeListeners = new ArrayMap<PrintServicesChangeListener, PrintServicesChangeListenerWrapper>();
        }
        object = new PrintServicesChangeListenerWrapper(printServicesChangeListener, handler);
        try {
            this.mService.addPrintServicesChangeListener((IPrintServicesChangeListener)object, this.mUserId);
            this.mPrintServicesChangeListeners.put(printServicesChangeListener, (PrintServicesChangeListenerWrapper)object);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    void cancelPrintJob(PrintJobId printJobId) {
        IPrintManager iPrintManager = this.mService;
        if (iPrintManager == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        try {
            iPrintManager.cancelPrintJob(printJobId, this.mAppId, this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public PrinterDiscoverySession createPrinterDiscoverySession() {
        IPrintManager iPrintManager = this.mService;
        if (iPrintManager == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return null;
        }
        return new PrinterDiscoverySession(iPrintManager, this.mContext, this.mUserId);
    }

    public Icon getCustomPrinterIcon(PrinterId parcelable) {
        IPrintManager iPrintManager = this.mService;
        if (iPrintManager == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return null;
        }
        try {
            parcelable = iPrintManager.getCustomPrinterIcon((PrinterId)parcelable, this.mUserId);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public PrintManager getGlobalPrintManagerForUser(int n) {
        IPrintManager iPrintManager = this.mService;
        if (iPrintManager == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return null;
        }
        return new PrintManager(this.mContext, iPrintManager, n, -2);
    }

    public PrintJob getPrintJob(PrintJobId object) {
        block4 : {
            IPrintManager iPrintManager = this.mService;
            if (iPrintManager == null) {
                Log.w(LOG_TAG, "Feature android.software.print not available");
                return null;
            }
            try {
                object = iPrintManager.getPrintJobInfo((PrintJobId)object, this.mAppId, this.mUserId);
                if (object == null) break block4;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            object = new PrintJob((PrintJobInfo)object, this);
            return object;
        }
        return null;
    }

    PrintJobInfo getPrintJobInfo(PrintJobId parcelable) {
        try {
            parcelable = this.mService.getPrintJobInfo((PrintJobId)parcelable, this.mAppId, this.mUserId);
            return parcelable;
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
    public List<PrintJob> getPrintJobs() {
        Object object = this.mService;
        if (object == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return Collections.emptyList();
        }
        try {
            List<PrintJobInfo> list = object.getPrintJobInfos(this.mAppId, this.mUserId);
            if (list == null) {
                return Collections.emptyList();
            }
            int n = list.size();
            object = new Object(n);
            int n2 = 0;
            do {
                if (n2 >= n) {
                    return object;
                }
                PrintJob printJob = new PrintJob(list.get(n2), this);
                object.add((PrintJob)printJob);
                ++n2;
            } while (true);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<RecommendationInfo> getPrintServiceRecommendations() {
        block2 : {
            try {
                List<RecommendationInfo> list = this.mService.getPrintServiceRecommendations(this.mUserId);
                if (list == null) break block2;
                return list;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return Collections.emptyList();
    }

    @SystemApi
    public List<PrintServiceInfo> getPrintServices(int n) {
        block2 : {
            Preconditions.checkFlagsArgument(n, 3);
            try {
                List<PrintServiceInfo> list = this.mService.getPrintServices(n, this.mUserId);
                if (list == null) break block2;
                return list;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return Collections.emptyList();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public PrintJob print(String object, PrintDocumentAdapter object2, PrintAttributes printAttributes) {
        if (this.mService == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return null;
        }
        if (!(this.mContext instanceof Activity)) throw new IllegalStateException("Can print only from an activity");
        if (TextUtils.isEmpty((CharSequence)object)) throw new IllegalArgumentException("printJobName cannot be empty");
        if (object2 == null) throw new IllegalArgumentException("documentAdapter cannot be null");
        object2 = new PrintDocumentAdapterDelegate((Activity)this.mContext, (PrintDocumentAdapter)object2);
        try {
            object2 = this.mService.print((String)object, (IPrintDocumentAdapter)object2, printAttributes, this.mContext.getPackageName(), this.mAppId, this.mUserId);
            if (object2 == null) return null;
            object = (PrintJobInfo)((Bundle)object2).getParcelable(EXTRA_PRINT_JOB);
            object2 = (IntentSender)((Bundle)object2).getParcelable(EXTRA_PRINT_DIALOG_INTENT);
            if (object == null) return null;
            if (object2 == null) {
                return null;
            }
            try {
                this.mContext.startIntentSender((IntentSender)object2, null, 0, 0, 0);
                return new PrintJob((PrintJobInfo)object, this);
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                Log.e(LOG_TAG, "Couldn't start print job config activity.", sendIntentException);
                return null;
            }
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removePrintJobStateChangeListener(PrintJobStateChangeListener object) {
        if (this.mService == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        Map<PrintJobStateChangeListener, PrintJobStateChangeListenerWrapper> map = this.mPrintJobStateChangeListeners;
        if (map == null) {
            return;
        }
        if ((object = map.remove(object)) == null) {
            return;
        }
        if (this.mPrintJobStateChangeListeners.isEmpty()) {
            this.mPrintJobStateChangeListeners = null;
        }
        ((PrintJobStateChangeListenerWrapper)object).destroy();
        try {
            this.mService.removePrintJobStateChangeListener((IPrintJobStateChangeListener)object, this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void removePrintServiceRecommendationsChangeListener(PrintServiceRecommendationsChangeListener object) {
        Preconditions.checkNotNull(object);
        if (this.mService == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        Map<PrintServiceRecommendationsChangeListener, PrintServiceRecommendationsChangeListenerWrapper> map = this.mPrintServiceRecommendationsChangeListeners;
        if (map == null) {
            return;
        }
        if ((object = map.remove(object)) == null) {
            return;
        }
        if (this.mPrintServiceRecommendationsChangeListeners.isEmpty()) {
            this.mPrintServiceRecommendationsChangeListeners = null;
        }
        ((PrintServiceRecommendationsChangeListenerWrapper)object).destroy();
        try {
            this.mService.removePrintServiceRecommendationsChangeListener((IRecommendationsChangeListener)object, this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void removePrintServicesChangeListener(PrintServicesChangeListener object) {
        Preconditions.checkNotNull(object);
        if (this.mService == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        Map<PrintServicesChangeListener, PrintServicesChangeListenerWrapper> map = this.mPrintServicesChangeListeners;
        if (map == null) {
            return;
        }
        if ((object = map.remove(object)) == null) {
            return;
        }
        if (this.mPrintServicesChangeListeners.isEmpty()) {
            this.mPrintServicesChangeListeners = null;
        }
        ((PrintServicesChangeListenerWrapper)object).destroy();
        try {
            this.mService.removePrintServicesChangeListener((IPrintServicesChangeListener)object, this.mUserId);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error removing print services change listener", remoteException);
        }
    }

    void restartPrintJob(PrintJobId printJobId) {
        IPrintManager iPrintManager = this.mService;
        if (iPrintManager == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        try {
            iPrintManager.restartPrintJob(printJobId, this.mAppId, this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setPrintServiceEnabled(ComponentName componentName, boolean bl) {
        Object object = this.mService;
        if (object == null) {
            Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        try {
            object.setPrintServiceEnabled(componentName, bl, this.mUserId);
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Error enabling or disabling ");
            ((StringBuilder)object).append(componentName);
            Log.e(LOG_TAG, ((StringBuilder)object).toString(), remoteException);
        }
    }

    public static final class PrintDocumentAdapterDelegate
    extends IPrintDocumentAdapter.Stub
    implements Application.ActivityLifecycleCallbacks {
        private Activity mActivity;
        private PrintDocumentAdapter mDocumentAdapter;
        private Handler mHandler;
        private final Object mLock = new Object();
        private IPrintDocumentAdapterObserver mObserver;
        private DestroyableCallback mPendingCallback;

        public PrintDocumentAdapterDelegate(Activity activity, PrintDocumentAdapter printDocumentAdapter) {
            if (!activity.isFinishing()) {
                this.mActivity = activity;
                this.mDocumentAdapter = printDocumentAdapter;
                this.mHandler = new MyHandler(this.mActivity.getMainLooper());
                this.mActivity.getApplication().registerActivityLifecycleCallbacks(this);
                return;
            }
            throw new IllegalStateException("Cannot start printing for finishing activity");
        }

        private void destroyLocked() {
            this.mActivity.getApplication().unregisterActivityLifecycleCallbacks(this);
            this.mActivity = null;
            this.mDocumentAdapter = null;
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mHandler.removeMessages(4);
            this.mHandler = null;
            this.mObserver = null;
            DestroyableCallback destroyableCallback = this.mPendingCallback;
            if (destroyableCallback != null) {
                destroyableCallback.destroy();
                this.mPendingCallback = null;
            }
        }

        private boolean isDestroyedLocked() {
            boolean bl = this.mActivity == null;
            return bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void finish() {
            Object object = this.mLock;
            synchronized (object) {
                if (!this.isDestroyedLocked()) {
                    this.mHandler.obtainMessage(4, this.mDocumentAdapter).sendToTarget();
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
        public void kill(String string2) {
            Object object = this.mLock;
            synchronized (object) {
                if (!this.isDestroyedLocked()) {
                    this.mHandler.obtainMessage(5, string2).sendToTarget();
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
        public void layout(PrintAttributes object, PrintAttributes printAttributes, ILayoutResultCallback iLayoutResultCallback, Bundle bundle, int n) {
            Object object2;
            Object object3 = CancellationSignal.createTransport();
            try {
                iLayoutResultCallback.onLayoutStarted((ICancellationSignal)object3, n);
                object2 = this.mLock;
            }
            catch (RemoteException remoteException) {
                Log.e(PrintManager.LOG_TAG, "Error notifying for layout start", remoteException);
                return;
            }
            synchronized (object2) {
                if (this.isDestroyedLocked()) {
                    return;
                }
                CancellationSignal cancellationSignal = CancellationSignal.fromTransport((ICancellationSignal)object3);
                object3 = SomeArgs.obtain();
                ((SomeArgs)object3).arg1 = this.mDocumentAdapter;
                ((SomeArgs)object3).arg2 = object;
                ((SomeArgs)object3).arg3 = printAttributes;
                ((SomeArgs)object3).arg4 = cancellationSignal;
                ((SomeArgs)object3).arg5 = object = new MyLayoutResultCallback(iLayoutResultCallback, n);
                ((SomeArgs)object3).arg6 = bundle;
                this.mHandler.obtainMessage(2, object3).sendToTarget();
                return;
            }
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onActivityDestroyed(Activity activity) {
            IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver = null;
            Object object = this.mLock;
            synchronized (object) {
                if (activity == this.mActivity) {
                    iPrintDocumentAdapterObserver = this.mObserver;
                    this.destroyLocked();
                }
            }
            if (iPrintDocumentAdapterObserver == null) return;
            try {
                iPrintDocumentAdapterObserver.onDestroy();
                return;
            }
            catch (RemoteException remoteException) {
                Log.e(PrintManager.LOG_TAG, "Error announcing destroyed state", remoteException);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setObserver(IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver) {
            boolean bl;
            Object object = this.mLock;
            synchronized (object) {
                this.mObserver = iPrintDocumentAdapterObserver;
                bl = this.isDestroyedLocked();
            }
            if (!bl) return;
            if (iPrintDocumentAdapterObserver == null) return;
            try {
                iPrintDocumentAdapterObserver.onDestroy();
                return;
            }
            catch (RemoteException remoteException) {
                Log.e(PrintManager.LOG_TAG, "Error announcing destroyed state", remoteException);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void start() {
            Object object = this.mLock;
            synchronized (object) {
                if (!this.isDestroyedLocked()) {
                    this.mHandler.obtainMessage(1, this.mDocumentAdapter).sendToTarget();
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
        public void write(PageRange[] object, ParcelFileDescriptor parcelFileDescriptor, IWriteResultCallback iWriteResultCallback, int n) {
            Object object2;
            Object object3 = CancellationSignal.createTransport();
            try {
                iWriteResultCallback.onWriteStarted((ICancellationSignal)object3, n);
                object2 = this.mLock;
            }
            catch (RemoteException remoteException) {
                Log.e(PrintManager.LOG_TAG, "Error notifying for write start", remoteException);
                return;
            }
            synchronized (object2) {
                if (this.isDestroyedLocked()) {
                    return;
                }
                CancellationSignal cancellationSignal = CancellationSignal.fromTransport((ICancellationSignal)object3);
                object3 = SomeArgs.obtain();
                ((SomeArgs)object3).arg1 = this.mDocumentAdapter;
                ((SomeArgs)object3).arg2 = object;
                ((SomeArgs)object3).arg3 = parcelFileDescriptor;
                ((SomeArgs)object3).arg4 = cancellationSignal;
                ((SomeArgs)object3).arg5 = object = new MyWriteResultCallback(iWriteResultCallback, parcelFileDescriptor, n);
                this.mHandler.obtainMessage(3, object3).sendToTarget();
                return;
            }
        }

        private static interface DestroyableCallback {
            public void destroy();
        }

        private final class MyHandler
        extends Handler {
            public static final int MSG_ON_FINISH = 4;
            public static final int MSG_ON_KILL = 5;
            public static final int MSG_ON_LAYOUT = 2;
            public static final int MSG_ON_START = 1;
            public static final int MSG_ON_WRITE = 3;

            public MyHandler(Looper looper) {
                super(looper, null, true);
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void handleMessage(Message object) {
                int n = ((Message)object).what;
                if (n == 1) {
                    ((PrintDocumentAdapter)((Message)object).obj).onStart();
                    return;
                }
                if (n == 2) {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    PrintDocumentAdapter printDocumentAdapter = (PrintDocumentAdapter)someArgs.arg1;
                    PrintAttributes printAttributes = (PrintAttributes)someArgs.arg2;
                    PrintAttributes printAttributes2 = (PrintAttributes)someArgs.arg3;
                    CancellationSignal cancellationSignal = (CancellationSignal)someArgs.arg4;
                    object = (PrintDocumentAdapter.LayoutResultCallback)someArgs.arg5;
                    Bundle bundle = (Bundle)someArgs.arg6;
                    someArgs.recycle();
                    printDocumentAdapter.onLayout(printAttributes, printAttributes2, cancellationSignal, (PrintDocumentAdapter.LayoutResultCallback)object, bundle);
                    return;
                }
                if (n == 3) {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    PrintDocumentAdapter printDocumentAdapter = (PrintDocumentAdapter)someArgs.arg1;
                    PageRange[] arrpageRange = (PageRange[])someArgs.arg2;
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor)someArgs.arg3;
                    CancellationSignal cancellationSignal = (CancellationSignal)someArgs.arg4;
                    object = (PrintDocumentAdapter.WriteResultCallback)someArgs.arg5;
                    someArgs.recycle();
                    printDocumentAdapter.onWrite(arrpageRange, parcelFileDescriptor, cancellationSignal, (PrintDocumentAdapter.WriteResultCallback)object);
                    return;
                }
                if (n != 4) {
                    if (n == 5) throw new RuntimeException((String)((Message)object).obj);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown message: ");
                    stringBuilder.append(((Message)object).what);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                ((PrintDocumentAdapter)((Message)object).obj).onFinish();
                object = PrintDocumentAdapterDelegate.this.mLock;
                synchronized (object) {
                    PrintDocumentAdapterDelegate.this.destroyLocked();
                    return;
                }
            }
        }

        private final class MyLayoutResultCallback
        extends PrintDocumentAdapter.LayoutResultCallback
        implements DestroyableCallback {
            private ILayoutResultCallback mCallback;
            private final int mSequence;

            public MyLayoutResultCallback(ILayoutResultCallback iLayoutResultCallback, int n) {
                this.mCallback = iLayoutResultCallback;
                this.mSequence = n;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void destroy() {
                Object object = PrintDocumentAdapterDelegate.this.mLock;
                synchronized (object) {
                    this.mCallback = null;
                    PrintDocumentAdapterDelegate.this.mPendingCallback = null;
                    return;
                }
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void onLayoutCancelled() {
                var1_1 = PrintDocumentAdapterDelegate.access$000(PrintDocumentAdapterDelegate.this);
                // MONITORENTER : var1_1
                var2_4 = this.mCallback;
                // MONITOREXIT : var1_1
                if (var2_4 == null) {
                    Log.e("PrintManager", "PrintDocumentAdapter is destroyed. Did you finish the printing activity before print completion or did you invoke a callback after finish?");
                    return;
                }
                var2_4.onLayoutCanceled(this.mSequence);
                this.destroy();
                return;
                {
                    catch (RemoteException var1_3) {}
                    {
                        Log.e("PrintManager", "Error calling onLayoutFailed", var1_3);
                    }
                }
                ** finally { 
lbl17: // 1 sources:
                this.destroy();
                throw var1_2;
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void onLayoutFailed(CharSequence var1_1) {
                var2_4 = PrintDocumentAdapterDelegate.access$000(PrintDocumentAdapterDelegate.this);
                // MONITORENTER : var2_4
                var3_5 = this.mCallback;
                // MONITOREXIT : var2_4
                if (var3_5 == null) {
                    Log.e("PrintManager", "PrintDocumentAdapter is destroyed. Did you finish the printing activity before print completion or did you invoke a callback after finish?");
                    return;
                }
                var3_5.onLayoutFailed(var1_1, this.mSequence);
                this.destroy();
                return;
                {
                    catch (RemoteException var1_3) {}
                    {
                        Log.e("PrintManager", "Error calling onLayoutFailed", var1_3);
                    }
                }
                ** finally { 
lbl17: // 1 sources:
                this.destroy();
                throw var1_2;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void onLayoutFinished(PrintDocumentInfo var1_1, boolean var2_4) {
                var3_5 = PrintDocumentAdapterDelegate.access$000(PrintDocumentAdapterDelegate.this);
                // MONITORENTER : var3_5
                var4_6 = this.mCallback;
                // MONITOREXIT : var3_5
                if (var4_6 == null) {
                    Log.e("PrintManager", "PrintDocumentAdapter is destroyed. Did you finish the printing activity before print completion or did you invoke a callback after finish?");
                    return;
                }
                if (var1_1 == null) ** GOTO lbl19
                try {
                    try {
                        var4_6.onLayoutFinished((PrintDocumentInfo)var1_1, var2_4, this.mSequence);
                    }
                    catch (RemoteException var1_3) {
                        Log.e("PrintManager", "Error calling onLayoutFinished", var1_3);
                    }
                    this.destroy();
                    return;
lbl19: // 1 sources:
                    var1_1 = new NullPointerException("document info cannot be null");
                    throw var1_1;
                }
                catch (Throwable var1_2) {}
                this.destroy();
                throw var1_2;
            }
        }

        private final class MyWriteResultCallback
        extends PrintDocumentAdapter.WriteResultCallback
        implements DestroyableCallback {
            private IWriteResultCallback mCallback;
            private ParcelFileDescriptor mFd;
            private final int mSequence;

            public MyWriteResultCallback(IWriteResultCallback iWriteResultCallback, ParcelFileDescriptor parcelFileDescriptor, int n) {
                this.mFd = parcelFileDescriptor;
                this.mSequence = n;
                this.mCallback = iWriteResultCallback;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void destroy() {
                Object object = PrintDocumentAdapterDelegate.this.mLock;
                synchronized (object) {
                    IoUtils.closeQuietly((AutoCloseable)this.mFd);
                    this.mCallback = null;
                    this.mFd = null;
                    PrintDocumentAdapterDelegate.this.mPendingCallback = null;
                    return;
                }
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void onWriteCancelled() {
                var1_1 = PrintDocumentAdapterDelegate.access$000(PrintDocumentAdapterDelegate.this);
                // MONITORENTER : var1_1
                var2_4 = this.mCallback;
                // MONITOREXIT : var1_1
                if (var2_4 == null) {
                    Log.e("PrintManager", "PrintDocumentAdapter is destroyed. Did you finish the printing activity before print completion or did you invoke a callback after finish?");
                    return;
                }
                var2_4.onWriteCanceled(this.mSequence);
                this.destroy();
                return;
                {
                    catch (RemoteException var1_3) {}
                    {
                        Log.e("PrintManager", "Error calling onWriteCanceled", var1_3);
                    }
                }
                ** finally { 
lbl17: // 1 sources:
                this.destroy();
                throw var1_2;
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void onWriteFailed(CharSequence var1_1) {
                var2_4 = PrintDocumentAdapterDelegate.access$000(PrintDocumentAdapterDelegate.this);
                // MONITORENTER : var2_4
                var3_5 = this.mCallback;
                // MONITOREXIT : var2_4
                if (var3_5 == null) {
                    Log.e("PrintManager", "PrintDocumentAdapter is destroyed. Did you finish the printing activity before print completion or did you invoke a callback after finish?");
                    return;
                }
                var3_5.onWriteFailed(var1_1, this.mSequence);
                this.destroy();
                return;
                {
                    catch (RemoteException var1_3) {}
                    {
                        Log.e("PrintManager", "Error calling onWriteFailed", var1_3);
                    }
                }
                ** finally { 
lbl17: // 1 sources:
                this.destroy();
                throw var1_2;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void onWriteFinished(PageRange[] object) {
                Throwable throwable2;
                Object object2 = PrintDocumentAdapterDelegate.this.mLock;
                // MONITORENTER : object2
                IWriteResultCallback iWriteResultCallback = this.mCallback;
                // MONITOREXIT : object2
                if (iWriteResultCallback == null) {
                    Log.e(PrintManager.LOG_TAG, "PrintDocumentAdapter is destroyed. Did you finish the printing activity before print completion or did you invoke a callback after finish?");
                    return;
                }
                if (object != null) {
                    try {
                        int n = ((PageRange[])object).length;
                        if (n != 0) {
                            try {
                                iWriteResultCallback.onWriteFinished((PageRange[])object, this.mSequence);
                            }
                            catch (RemoteException remoteException) {
                                Log.e(PrintManager.LOG_TAG, "Error calling onWriteFinished", remoteException);
                            }
                            this.destroy();
                            return;
                        }
                        object = new IllegalArgumentException("pages cannot be empty");
                        throw object;
                    }
                    catch (Throwable throwable2) {}
                } else {
                    object = new IllegalArgumentException("pages cannot be null");
                    throw object;
                }
                this.destroy();
                throw throwable2;
            }
        }

    }

    public static interface PrintJobStateChangeListener {
        public void onPrintJobStateChanged(PrintJobId var1);
    }

    public static final class PrintJobStateChangeListenerWrapper
    extends IPrintJobStateChangeListener.Stub {
        private final WeakReference<Handler> mWeakHandler;
        private final WeakReference<PrintJobStateChangeListener> mWeakListener;

        public PrintJobStateChangeListenerWrapper(PrintJobStateChangeListener printJobStateChangeListener, Handler handler) {
            this.mWeakListener = new WeakReference<PrintJobStateChangeListener>(printJobStateChangeListener);
            this.mWeakHandler = new WeakReference<Handler>(handler);
        }

        public void destroy() {
            this.mWeakListener.clear();
        }

        public PrintJobStateChangeListener getListener() {
            return (PrintJobStateChangeListener)this.mWeakListener.get();
        }

        @Override
        public void onPrintJobStateChanged(PrintJobId printJobId) {
            Handler handler = (Handler)this.mWeakHandler.get();
            Object object = (PrintJobStateChangeListener)this.mWeakListener.get();
            if (handler != null && object != null) {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = this;
                ((SomeArgs)object).arg2 = printJobId;
                handler.obtainMessage(1, object).sendToTarget();
            }
        }
    }

    @SystemApi
    public static interface PrintServiceRecommendationsChangeListener {
        public void onPrintServiceRecommendationsChanged();
    }

    public static final class PrintServiceRecommendationsChangeListenerWrapper
    extends IRecommendationsChangeListener.Stub {
        private final WeakReference<Handler> mWeakHandler;
        private final WeakReference<PrintServiceRecommendationsChangeListener> mWeakListener;

        public PrintServiceRecommendationsChangeListenerWrapper(PrintServiceRecommendationsChangeListener printServiceRecommendationsChangeListener, Handler handler) {
            this.mWeakListener = new WeakReference<PrintServiceRecommendationsChangeListener>(printServiceRecommendationsChangeListener);
            this.mWeakHandler = new WeakReference<Handler>(handler);
        }

        public void destroy() {
            this.mWeakListener.clear();
        }

        @Override
        public void onRecommendationsChanged() {
            Handler handler = (Handler)this.mWeakHandler.get();
            PrintServiceRecommendationsChangeListener printServiceRecommendationsChangeListener = (PrintServiceRecommendationsChangeListener)this.mWeakListener.get();
            if (handler != null && printServiceRecommendationsChangeListener != null) {
                Objects.requireNonNull(printServiceRecommendationsChangeListener);
                handler.post(new _$$Lambda$KZ41E_yXUNYMY9k_Xeus1UG_cS8(printServiceRecommendationsChangeListener));
            }
        }
    }

    @SystemApi
    public static interface PrintServicesChangeListener {
        public void onPrintServicesChanged();
    }

    public static final class PrintServicesChangeListenerWrapper
    extends IPrintServicesChangeListener.Stub {
        private final WeakReference<Handler> mWeakHandler;
        private final WeakReference<PrintServicesChangeListener> mWeakListener;

        public PrintServicesChangeListenerWrapper(PrintServicesChangeListener printServicesChangeListener, Handler handler) {
            this.mWeakListener = new WeakReference<PrintServicesChangeListener>(printServicesChangeListener);
            this.mWeakHandler = new WeakReference<Handler>(handler);
        }

        public void destroy() {
            this.mWeakListener.clear();
        }

        @Override
        public void onPrintServicesChanged() {
            Handler handler = (Handler)this.mWeakHandler.get();
            PrintServicesChangeListener printServicesChangeListener = (PrintServicesChangeListener)this.mWeakListener.get();
            if (handler != null && printServicesChangeListener != null) {
                Objects.requireNonNull(printServicesChangeListener);
                handler.post(new _$$Lambda$c2Elb5E1w2yc6lr236iX_RUAL5Q(printServicesChangeListener));
            }
        }
    }

}

