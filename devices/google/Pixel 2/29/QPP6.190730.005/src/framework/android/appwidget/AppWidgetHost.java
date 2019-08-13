/*
 * Decompiled with CFR 0.145.
 */
package android.appwidget;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.appwidget.PendingHostUpdate;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.widget.RemoteViews;
import com.android.internal.appwidget.IAppWidgetHost;
import com.android.internal.appwidget.IAppWidgetService;
import java.lang.ref.WeakReference;
import java.util.List;

public class AppWidgetHost {
    static final int HANDLE_PROVIDERS_CHANGED = 3;
    static final int HANDLE_PROVIDER_CHANGED = 2;
    static final int HANDLE_UPDATE = 1;
    @UnsupportedAppUsage
    static final int HANDLE_VIEW_DATA_CHANGED = 4;
    @UnsupportedAppUsage
    static IAppWidgetService sService;
    static boolean sServiceInitialized;
    static final Object sServiceLock;
    private final Callbacks mCallbacks;
    private String mContextOpPackageName;
    private DisplayMetrics mDisplayMetrics;
    @UnsupportedAppUsage
    private final Handler mHandler;
    private final int mHostId;
    private RemoteViews.OnClickHandler mOnClickHandler;
    private final SparseArray<AppWidgetHostView> mViews = new SparseArray();

    static {
        sServiceLock = new Object();
        sServiceInitialized = false;
    }

    public AppWidgetHost(Context context, int n) {
        this(context, n, null, context.getMainLooper());
    }

    @UnsupportedAppUsage
    public AppWidgetHost(Context context, int n, RemoteViews.OnClickHandler onClickHandler, Looper looper) {
        this.mContextOpPackageName = context.getOpPackageName();
        this.mHostId = n;
        this.mOnClickHandler = onClickHandler;
        this.mHandler = new UpdateHandler(looper);
        this.mCallbacks = new Callbacks(this.mHandler);
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        AppWidgetHost.bindService(context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void bindService(Context context) {
        Object object = sServiceLock;
        synchronized (object) {
            if (sServiceInitialized) {
                return;
            }
            sServiceInitialized = true;
            if (!context.getPackageManager().hasSystemFeature("android.software.app_widgets") && !context.getResources().getBoolean(17891431)) {
                return;
            }
            sService = IAppWidgetService.Stub.asInterface(ServiceManager.getService("appwidget"));
            return;
        }
    }

    public static void deleteAllHosts() {
        IAppWidgetService iAppWidgetService = sService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.deleteAllHosts();
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("system server dead?", remoteException);
        }
    }

    public int allocateAppWidgetId() {
        IAppWidgetService iAppWidgetService = sService;
        if (iAppWidgetService == null) {
            return -1;
        }
        try {
            int n = iAppWidgetService.allocateAppWidgetId(this.mContextOpPackageName, this.mHostId);
            return n;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("system server dead?", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void clearViews() {
        SparseArray<AppWidgetHostView> sparseArray = this.mViews;
        synchronized (sparseArray) {
            this.mViews.clear();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final AppWidgetHostView createView(Context object, int n, AppWidgetProviderInfo object2) {
        if (sService == null) {
            return null;
        }
        object = this.onCreateView((Context)object, n, (AppWidgetProviderInfo)object2);
        ((AppWidgetHostView)object).setOnClickHandler(this.mOnClickHandler);
        ((AppWidgetHostView)object).setAppWidget(n, (AppWidgetProviderInfo)object2);
        object2 = this.mViews;
        synchronized (object2) {
            this.mViews.put(n, (AppWidgetHostView)object);
        }
        try {
            object2 = sService.getAppWidgetViews(this.mContextOpPackageName, n);
            ((AppWidgetHostView)object).updateAppWidget((RemoteViews)object2);
            return object;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("system server dead?", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void deleteAppWidgetId(int n) {
        if (sService == null) {
            return;
        }
        SparseArray<AppWidgetHostView> sparseArray = this.mViews;
        synchronized (sparseArray) {
            this.mViews.remove(n);
            try {
                sService.deleteAppWidgetId(this.mContextOpPackageName, n);
                return;
            }
            catch (RemoteException remoteException) {
                RuntimeException runtimeException = new RuntimeException("system server dead?", remoteException);
                throw runtimeException;
            }
        }
    }

    public void deleteHost() {
        IAppWidgetService iAppWidgetService = sService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.deleteHost(this.mContextOpPackageName, this.mHostId);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("system server dead?", remoteException);
        }
    }

    public int[] getAppWidgetIds() {
        int[] arrn = sService;
        if (arrn == null) {
            return new int[0];
        }
        try {
            arrn = arrn.getAppWidgetIdsForHost(this.mContextOpPackageName, this.mHostId);
            return arrn;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("system server dead?", remoteException);
        }
    }

    protected AppWidgetHostView onCreateView(Context context, int n, AppWidgetProviderInfo appWidgetProviderInfo) {
        return new AppWidgetHostView(context, this.mOnClickHandler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected void onProviderChanged(int n, AppWidgetProviderInfo appWidgetProviderInfo) {
        appWidgetProviderInfo.updateDimensions(this.mDisplayMetrics);
        SparseArray<AppWidgetHostView> sparseArray = this.mViews;
        // MONITORENTER : sparseArray
        AppWidgetHostView appWidgetHostView = this.mViews.get(n);
        // MONITOREXIT : sparseArray
        if (appWidgetHostView == null) return;
        appWidgetHostView.resetAppWidget(appWidgetProviderInfo);
    }

    protected void onProvidersChanged() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void startAppWidgetConfigureActivityForResult(Activity object, int n, int n2, int n3, Bundle bundle) {
        Object object2 = sService;
        if (object2 == null) {
            return;
        }
        try {
            object2 = object2.createAppWidgetConfigIntentSender(this.mContextOpPackageName, n, n2);
            if (object2 != null) {
                ((Activity)object).startIntentSenderForResult((IntentSender)object2, n3, null, 0, 0, 0, bundle);
                return;
            }
            object = new ActivityNotFoundException();
            throw object;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("system server dead?", remoteException);
        }
        catch (IntentSender.SendIntentException sendIntentException) {
            throw new ActivityNotFoundException();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startListening() {
        int n;
        int n2;
        Object object;
        if (sService == null) {
            return;
        }
        Object object2 = this.mViews;
        synchronized (object2) {
            n = this.mViews.size();
            object = new int[n];
            for (n2 = 0; n2 < n; ++n2) {
                object[n2] = this.mViews.keyAt(n2);
            }
        }
        try {
            object = sService.startListening(this.mCallbacks, this.mContextOpPackageName, this.mHostId, (int[])object).getList();
            n = object.size();
            n2 = 0;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("system server dead?", remoteException);
        }
        while (n2 < n) {
            object2 = (PendingHostUpdate)object.get(n2);
            int n3 = ((PendingHostUpdate)object2).type;
            if (n3 != 0) {
                if (n3 != 1) {
                    if (n3 == 2) {
                        this.viewDataChanged(((PendingHostUpdate)object2).appWidgetId, ((PendingHostUpdate)object2).viewId);
                    }
                } else {
                    this.onProviderChanged(((PendingHostUpdate)object2).appWidgetId, ((PendingHostUpdate)object2).widgetInfo);
                }
            } else {
                this.updateAppWidgetView(((PendingHostUpdate)object2).appWidgetId, ((PendingHostUpdate)object2).views);
            }
            ++n2;
        }
        return;
    }

    public void stopListening() {
        IAppWidgetService iAppWidgetService = sService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.stopListening(this.mContextOpPackageName, this.mHostId);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("system server dead?", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void updateAppWidgetView(int n, RemoteViews remoteViews) {
        SparseArray<AppWidgetHostView> sparseArray = this.mViews;
        // MONITORENTER : sparseArray
        AppWidgetHostView appWidgetHostView = this.mViews.get(n);
        // MONITOREXIT : sparseArray
        if (appWidgetHostView == null) return;
        appWidgetHostView.updateAppWidget(remoteViews);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void viewDataChanged(int n, int n2) {
        SparseArray<AppWidgetHostView> sparseArray = this.mViews;
        // MONITORENTER : sparseArray
        AppWidgetHostView appWidgetHostView = this.mViews.get(n);
        // MONITOREXIT : sparseArray
        if (appWidgetHostView == null) return;
        appWidgetHostView.viewDataChanged(n2);
    }

    static class Callbacks
    extends IAppWidgetHost.Stub {
        private final WeakReference<Handler> mWeakHandler;

        public Callbacks(Handler handler) {
            this.mWeakHandler = new WeakReference<Handler>(handler);
        }

        private static boolean isLocalBinder() {
            boolean bl = Process.myPid() == Binder.getCallingPid();
            return bl;
        }

        @Override
        public void providerChanged(int n, AppWidgetProviderInfo object) {
            AppWidgetProviderInfo appWidgetProviderInfo = object;
            if (Callbacks.isLocalBinder()) {
                appWidgetProviderInfo = object;
                if (object != null) {
                    appWidgetProviderInfo = ((AppWidgetProviderInfo)object).clone();
                }
            }
            if ((object = (Handler)this.mWeakHandler.get()) == null) {
                return;
            }
            ((Handler)object).obtainMessage(2, n, 0, appWidgetProviderInfo).sendToTarget();
        }

        @Override
        public void providersChanged() {
            Handler handler = (Handler)this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(3).sendToTarget();
        }

        @Override
        public void updateAppWidget(int n, RemoteViews object) {
            RemoteViews remoteViews = object;
            if (Callbacks.isLocalBinder()) {
                remoteViews = object;
                if (object != null) {
                    remoteViews = ((RemoteViews)object).clone();
                }
            }
            if ((object = (Handler)this.mWeakHandler.get()) == null) {
                return;
            }
            ((Handler)object).obtainMessage(1, n, 0, remoteViews).sendToTarget();
        }

        @Override
        public void viewDataChanged(int n, int n2) {
            Handler handler = (Handler)this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(4, n, n2).sendToTarget();
        }
    }

    class UpdateHandler
    extends Handler {
        public UpdateHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            AppWidgetHost.this.viewDataChanged(message.arg1, message.arg2);
                        }
                    } else {
                        AppWidgetHost.this.onProvidersChanged();
                    }
                } else {
                    AppWidgetHost.this.onProviderChanged(message.arg1, (AppWidgetProviderInfo)message.obj);
                }
            } else {
                AppWidgetHost.this.updateAppWidgetView(message.arg1, (RemoteViews)message.obj);
            }
        }
    }

}

