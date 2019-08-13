/*
 * Decompiled with CFR 0.145.
 */
package android.appwidget;

import android.annotation.UnsupportedAppUsage;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.PendingIntent;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ParceledListSlice;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.DisplayMetrics;
import android.widget.RemoteViews;
import com.android.internal.appwidget.IAppWidgetService;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AppWidgetManager {
    public static final String ACTION_APPWIDGET_BIND = "android.appwidget.action.APPWIDGET_BIND";
    public static final String ACTION_APPWIDGET_CONFIGURE = "android.appwidget.action.APPWIDGET_CONFIGURE";
    public static final String ACTION_APPWIDGET_DELETED = "android.appwidget.action.APPWIDGET_DELETED";
    public static final String ACTION_APPWIDGET_DISABLED = "android.appwidget.action.APPWIDGET_DISABLED";
    public static final String ACTION_APPWIDGET_ENABLED = "android.appwidget.action.APPWIDGET_ENABLED";
    public static final String ACTION_APPWIDGET_HOST_RESTORED = "android.appwidget.action.APPWIDGET_HOST_RESTORED";
    public static final String ACTION_APPWIDGET_OPTIONS_CHANGED = "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS";
    public static final String ACTION_APPWIDGET_PICK = "android.appwidget.action.APPWIDGET_PICK";
    public static final String ACTION_APPWIDGET_RESTORED = "android.appwidget.action.APPWIDGET_RESTORED";
    public static final String ACTION_APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String ACTION_KEYGUARD_APPWIDGET_PICK = "android.appwidget.action.KEYGUARD_APPWIDGET_PICK";
    public static final String EXTRA_APPWIDGET_ID = "appWidgetId";
    public static final String EXTRA_APPWIDGET_IDS = "appWidgetIds";
    public static final String EXTRA_APPWIDGET_OLD_IDS = "appWidgetOldIds";
    public static final String EXTRA_APPWIDGET_OPTIONS = "appWidgetOptions";
    public static final String EXTRA_APPWIDGET_PREVIEW = "appWidgetPreview";
    public static final String EXTRA_APPWIDGET_PROVIDER = "appWidgetProvider";
    public static final String EXTRA_APPWIDGET_PROVIDER_PROFILE = "appWidgetProviderProfile";
    public static final String EXTRA_CATEGORY_FILTER = "categoryFilter";
    public static final String EXTRA_CUSTOM_EXTRAS = "customExtras";
    public static final String EXTRA_CUSTOM_INFO = "customInfo";
    public static final String EXTRA_CUSTOM_SORT = "customSort";
    public static final String EXTRA_HOST_ID = "hostId";
    public static final int INVALID_APPWIDGET_ID = 0;
    public static final String META_DATA_APPWIDGET_PROVIDER = "android.appwidget.provider";
    public static final String OPTION_APPWIDGET_HOST_CATEGORY = "appWidgetCategory";
    public static final String OPTION_APPWIDGET_MAX_HEIGHT = "appWidgetMaxHeight";
    public static final String OPTION_APPWIDGET_MAX_WIDTH = "appWidgetMaxWidth";
    public static final String OPTION_APPWIDGET_MIN_HEIGHT = "appWidgetMinHeight";
    public static final String OPTION_APPWIDGET_MIN_WIDTH = "appWidgetMinWidth";
    private final Context mContext;
    private final DisplayMetrics mDisplayMetrics;
    private final String mPackageName;
    @UnsupportedAppUsage
    private final IAppWidgetService mService;

    public AppWidgetManager(Context context, IAppWidgetService iAppWidgetService) {
        this.mContext = context;
        this.mPackageName = context.getOpPackageName();
        this.mService = iAppWidgetService;
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
    }

    @UnsupportedAppUsage
    private boolean bindAppWidgetIdIfAllowed(int n, int n2, ComponentName componentName, Bundle bundle) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            boolean bl = iAppWidgetService.bindAppWidgetId(this.mPackageName, n, n2, componentName, bundle);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static AppWidgetManager getInstance(Context context) {
        return (AppWidgetManager)context.getSystemService("appwidget");
    }

    @UnsupportedAppUsage
    public void bindAppWidgetId(int n, ComponentName componentName) {
        if (this.mService == null) {
            return;
        }
        this.bindAppWidgetId(n, componentName, null);
    }

    @UnsupportedAppUsage
    public void bindAppWidgetId(int n, ComponentName componentName, Bundle bundle) {
        if (this.mService == null) {
            return;
        }
        this.bindAppWidgetIdIfAllowed(n, this.mContext.getUser(), componentName, bundle);
    }

    public boolean bindAppWidgetIdIfAllowed(int n, ComponentName componentName) {
        if (this.mService == null) {
            return false;
        }
        return this.bindAppWidgetIdIfAllowed(n, this.mContext.getUserId(), componentName, null);
    }

    public boolean bindAppWidgetIdIfAllowed(int n, ComponentName componentName, Bundle bundle) {
        if (this.mService == null) {
            return false;
        }
        return this.bindAppWidgetIdIfAllowed(n, this.mContext.getUserId(), componentName, bundle);
    }

    public boolean bindAppWidgetIdIfAllowed(int n, UserHandle userHandle, ComponentName componentName, Bundle bundle) {
        if (this.mService == null) {
            return false;
        }
        return this.bindAppWidgetIdIfAllowed(n, userHandle.getIdentifier(), componentName, bundle);
    }

    @UnsupportedAppUsage
    public boolean bindRemoteViewsService(Context context, int n, Intent intent, IServiceConnection iServiceConnection, int n2) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            boolean bl = iAppWidgetService.bindRemoteViewsService(context.getOpPackageName(), n, intent, context.getIApplicationThread(), context.getActivityToken(), iServiceConnection, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int[] getAppWidgetIds(ComponentName arrn) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return new int[0];
        }
        try {
            arrn = iAppWidgetService.getAppWidgetIds((ComponentName)arrn);
            return arrn;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public AppWidgetProviderInfo getAppWidgetInfo(int n) {
        Object object;
        block4 : {
            object = this.mService;
            if (object == null) {
                return null;
            }
            try {
                object = object.getAppWidgetInfo(this.mPackageName, n);
                if (object == null) break block4;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            ((AppWidgetProviderInfo)object).updateDimensions(this.mDisplayMetrics);
        }
        return object;
    }

    public Bundle getAppWidgetOptions(int n) {
        Object object = this.mService;
        if (object == null) {
            return Bundle.EMPTY;
        }
        try {
            object = object.getAppWidgetOptions(this.mPackageName, n);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<AppWidgetProviderInfo> getInstalledProviders() {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return this.getInstalledProvidersForProfile(1, null, null);
    }

    @UnsupportedAppUsage
    public List<AppWidgetProviderInfo> getInstalledProviders(int n) {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return this.getInstalledProvidersForProfile(n, null, null);
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForPackage(String string2, UserHandle userHandle) {
        if (string2 != null) {
            if (this.mService == null) {
                return Collections.emptyList();
            }
            return this.getInstalledProvidersForProfile(1, userHandle, string2);
        }
        throw new NullPointerException("A non-null package must be passed to this method. If you want all widgets regardless of package, see getInstalledProvidersForProfile(UserHandle)");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public List<AppWidgetProviderInfo> getInstalledProvidersForProfile(int n, UserHandle object, String object2) {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        Iterator iterator = object;
        if (object == null) {
            iterator = this.mContext.getUser();
        }
        try {
            object2 = this.mService.getInstalledProvidersForProfile(n, ((UserHandle)((Object)iterator)).getIdentifier(), (String)object2);
            if (object2 == null) {
                return Collections.emptyList();
            }
            object = ((ParceledListSlice)object2).getList().iterator();
            do {
                if (!object.hasNext()) {
                    return ((ParceledListSlice)object2).getList();
                }
                ((AppWidgetProviderInfo)object.next()).updateDimensions(this.mDisplayMetrics);
            } while (true);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<AppWidgetProviderInfo> getInstalledProvidersForProfile(UserHandle userHandle) {
        if (this.mService == null) {
            return Collections.emptyList();
        }
        return this.getInstalledProvidersForProfile(1, userHandle, null);
    }

    public boolean hasBindAppWidgetPermission(String string2) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            boolean bl = iAppWidgetService.hasBindAppWidgetPermission(string2, this.mContext.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean hasBindAppWidgetPermission(String string2, int n) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            boolean bl = iAppWidgetService.hasBindAppWidgetPermission(string2, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isBoundWidgetPackage(String string2, int n) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return false;
        }
        try {
            boolean bl = iAppWidgetService.isBoundWidgetPackage(string2, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isRequestPinAppWidgetSupported() {
        try {
            boolean bl = this.mService.isRequestPinAppWidgetSupported();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void notifyAppWidgetViewDataChanged(int n, int n2) {
        if (this.mService == null) {
            return;
        }
        this.notifyAppWidgetViewDataChanged(new int[]{n}, n2);
    }

    public void notifyAppWidgetViewDataChanged(int[] arrn, int n) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.notifyAppWidgetViewDataChanged(this.mPackageName, arrn, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void partiallyUpdateAppWidget(int n, RemoteViews remoteViews) {
        if (this.mService == null) {
            return;
        }
        this.partiallyUpdateAppWidget(new int[]{n}, remoteViews);
    }

    public void partiallyUpdateAppWidget(int[] arrn, RemoteViews remoteViews) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.partiallyUpdateAppWidgetIds(this.mPackageName, arrn, remoteViews);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean requestPinAppWidget(ComponentName componentName, PendingIntent pendingIntent) {
        return this.requestPinAppWidget(componentName, null, pendingIntent);
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean requestPinAppWidget(ComponentName componentName, Bundle bundle, PendingIntent parcelable) {
        IAppWidgetService iAppWidgetService;
        void var3_7;
        String string2;
        block3 : {
            try {
                iAppWidgetService = this.mService;
                string2 = this.mPackageName;
                if (parcelable != null) break block3;
                Object var3_5 = null;
                return iAppWidgetService.requestPinAppWidget(string2, componentName, bundle, (IntentSender)var3_7);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        IntentSender intentSender = ((PendingIntent)parcelable).getIntentSender();
        return iAppWidgetService.requestPinAppWidget(string2, componentName, bundle, (IntentSender)var3_7);
    }

    public void setBindAppWidgetPermission(String string2, int n, boolean bl) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.setBindAppWidgetPermission(string2, n, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setBindAppWidgetPermission(String string2, boolean bl) {
        if (this.mService == null) {
            return;
        }
        this.setBindAppWidgetPermission(string2, this.mContext.getUserId(), bl);
    }

    public void updateAppWidget(int n, RemoteViews remoteViews) {
        if (this.mService == null) {
            return;
        }
        this.updateAppWidget(new int[]{n}, remoteViews);
    }

    public void updateAppWidget(ComponentName componentName, RemoteViews remoteViews) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.updateAppWidgetProvider(componentName, remoteViews);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void updateAppWidget(int[] arrn, RemoteViews remoteViews) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.updateAppWidgetIds(this.mPackageName, arrn, remoteViews);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void updateAppWidgetOptions(int n, Bundle bundle) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.updateAppWidgetOptions(this.mPackageName, n, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void updateAppWidgetProviderInfo(ComponentName componentName, String string2) {
        IAppWidgetService iAppWidgetService = this.mService;
        if (iAppWidgetService == null) {
            return;
        }
        try {
            iAppWidgetService.updateAppWidgetProviderInfo(componentName, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }
}

