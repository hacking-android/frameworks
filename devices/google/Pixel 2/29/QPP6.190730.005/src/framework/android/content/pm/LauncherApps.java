/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.IApplicationThread;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ILauncherApps;
import android.content.pm.IOnAppsChangedListener;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.IPinItemRequest;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.DisplayMetrics;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

public class LauncherApps {
    public static final String ACTION_CONFIRM_PIN_APPWIDGET = "android.content.pm.action.CONFIRM_PIN_APPWIDGET";
    public static final String ACTION_CONFIRM_PIN_SHORTCUT = "android.content.pm.action.CONFIRM_PIN_SHORTCUT";
    static final boolean DEBUG = false;
    public static final String EXTRA_PIN_ITEM_REQUEST = "android.content.pm.extra.PIN_ITEM_REQUEST";
    static final String TAG = "LauncherApps";
    private IOnAppsChangedListener.Stub mAppsChangedListener = new IOnAppsChangedListener.Stub(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onPackageAdded(UserHandle userHandle, String string2) throws RemoteException {
            LauncherApps launcherApps = LauncherApps.this;
            synchronized (launcherApps) {
                Iterator iterator = LauncherApps.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((CallbackMessageHandler)iterator.next()).postOnPackageAdded(string2, userHandle);
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
        public void onPackageChanged(UserHandle userHandle, String string2) throws RemoteException {
            LauncherApps launcherApps = LauncherApps.this;
            synchronized (launcherApps) {
                Iterator iterator = LauncherApps.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((CallbackMessageHandler)iterator.next()).postOnPackageChanged(string2, userHandle);
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
        public void onPackageRemoved(UserHandle userHandle, String string2) throws RemoteException {
            LauncherApps launcherApps = LauncherApps.this;
            synchronized (launcherApps) {
                Iterator iterator = LauncherApps.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((CallbackMessageHandler)iterator.next()).postOnPackageRemoved(string2, userHandle);
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
        public void onPackagesAvailable(UserHandle userHandle, String[] arrstring, boolean bl) throws RemoteException {
            LauncherApps launcherApps = LauncherApps.this;
            synchronized (launcherApps) {
                Iterator iterator = LauncherApps.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((CallbackMessageHandler)iterator.next()).postOnPackagesAvailable(arrstring, userHandle, bl);
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
        public void onPackagesSuspended(UserHandle userHandle, String[] arrstring, Bundle bundle) throws RemoteException {
            LauncherApps launcherApps = LauncherApps.this;
            synchronized (launcherApps) {
                Iterator iterator = LauncherApps.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((CallbackMessageHandler)iterator.next()).postOnPackagesSuspended(arrstring, bundle, userHandle);
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
        public void onPackagesUnavailable(UserHandle userHandle, String[] arrstring, boolean bl) throws RemoteException {
            LauncherApps launcherApps = LauncherApps.this;
            synchronized (launcherApps) {
                Iterator iterator = LauncherApps.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((CallbackMessageHandler)iterator.next()).postOnPackagesUnavailable(arrstring, userHandle, bl);
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
        public void onPackagesUnsuspended(UserHandle userHandle, String[] arrstring) throws RemoteException {
            LauncherApps launcherApps = LauncherApps.this;
            synchronized (launcherApps) {
                Iterator iterator = LauncherApps.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((CallbackMessageHandler)iterator.next()).postOnPackagesUnsuspended(arrstring, userHandle);
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
        public void onShortcutChanged(UserHandle userHandle, String string2, ParceledListSlice object) {
            List list = ((ParceledListSlice)object).getList();
            object = LauncherApps.this;
            synchronized (object) {
                Iterator iterator = LauncherApps.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((CallbackMessageHandler)iterator.next()).postOnShortcutChanged(string2, userHandle, list);
                }
                return;
            }
        }
    };
    private final List<CallbackMessageHandler> mCallbacks = new ArrayList<CallbackMessageHandler>();
    private final Context mContext;
    private final List<PackageInstaller.SessionCallbackDelegate> mDelegates = new ArrayList<PackageInstaller.SessionCallbackDelegate>();
    @UnsupportedAppUsage
    private final PackageManager mPm;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final ILauncherApps mService;
    private final UserManager mUserManager;

    public LauncherApps(Context context) {
        this(context, ILauncherApps.Stub.asInterface(ServiceManager.getService("launcherapps")));
    }

    public LauncherApps(Context context, ILauncherApps iLauncherApps) {
        this.mContext = context;
        this.mService = iLauncherApps;
        this.mPm = context.getPackageManager();
        this.mUserManager = context.getSystemService(UserManager.class);
    }

    private void addCallbackLocked(Callback object, Handler handler) {
        this.removeCallbackLocked((Callback)object);
        Handler handler2 = handler;
        if (handler == null) {
            handler2 = new Handler();
        }
        object = new CallbackMessageHandler(handler2.getLooper(), (Callback)object);
        this.mCallbacks.add((CallbackMessageHandler)object);
    }

    private List<LauncherActivityInfo> convertToActivityList(ParceledListSlice<ResolveInfo> object, UserHandle userHandle) {
        if (object == null) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<LauncherActivityInfo> arrayList = new ArrayList<LauncherActivityInfo>();
        for (ResolveInfo resolveInfo : ((ParceledListSlice)object).getList()) {
            arrayList.add(new LauncherActivityInfo(this.mContext, resolveInfo.activityInfo, userHandle));
        }
        return arrayList;
    }

    private int findCallbackLocked(Callback callback) {
        if (callback != null) {
            int n = this.mCallbacks.size();
            for (int i = 0; i < n; ++i) {
                if (this.mCallbacks.get(i).mCallback != callback) continue;
                return i;
            }
            return -1;
        }
        throw new IllegalArgumentException("Callback cannot be null");
    }

    private ParcelFileDescriptor getShortcutIconFd(String object, String string2, int n) {
        try {
            object = this.mService.getShortcutIconFd(this.mContext.getPackageName(), (String)object, string2, n);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private Drawable loadDrawableResourceFromPackage(String object, int n, UserHandle userHandle, int n2) {
        if (n == 0) {
            return null;
        }
        try {
            object = this.getApplicationInfo((String)object, 0, userHandle);
            object = this.mContext.getPackageManager().getResourcesForApplication((ApplicationInfo)object).getDrawableForDensity(n, n2);
            return object;
        }
        catch (PackageManager.NameNotFoundException | Resources.NotFoundException exception) {
            return null;
        }
    }

    private void logErrorForInvalidProfileAccess(UserHandle userHandle) {
        if (UserHandle.myUserId() != userHandle.getIdentifier() && this.mUserManager.isManagedProfile()) {
            Log.w(TAG, "Accessing other profiles/users from managed profile is no longer allowed.");
        }
    }

    private List<ShortcutInfo> maybeUpdateDisabledMessage(List<ShortcutInfo> list) {
        if (list == null) {
            return null;
        }
        for (int i = list.size() - 1; i >= 0; --i) {
            ShortcutInfo shortcutInfo = list.get(i);
            String string2 = ShortcutInfo.getDisabledReasonForRestoreIssue(this.mContext, shortcutInfo.getDisabledReason());
            if (string2 == null) continue;
            shortcutInfo.setDisabledMessage(string2);
        }
        return list;
    }

    private void removeCallbackLocked(Callback callback) {
        int n = this.findCallbackLocked(callback);
        if (n >= 0) {
            this.mCallbacks.remove(n);
        }
    }

    @UnsupportedAppUsage
    private void startShortcut(String object, String string2, Rect rect, Bundle bundle, int n) {
        try {
            if (this.mService.startShortcut(this.mContext.getPackageName(), (String)object, string2, rect, bundle, n)) {
                return;
            }
            object = new ActivityNotFoundException("Shortcut could not be started");
            throw object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<LauncherActivityInfo> getActivityList(String object, UserHandle userHandle) {
        this.logErrorForInvalidProfileAccess(userHandle);
        try {
            object = this.convertToActivityList(this.mService.getLauncherActivities(this.mContext.getPackageName(), (String)object, userHandle), userHandle);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<PackageInstaller.SessionInfo> getAllPackageInstallerSessions() {
        try {
            List list = this.mService.getAllSessions(this.mContext.getPackageName()).getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public AppUsageLimit getAppUsageLimit(String object, UserHandle userHandle) {
        try {
            object = this.mService.getAppUsageLimit(this.mContext.getPackageName(), (String)object, userHandle);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ApplicationInfo getApplicationInfo(String string2, int n, UserHandle userHandle) throws PackageManager.NameNotFoundException {
        Object object;
        block3 : {
            Preconditions.checkNotNull(string2, "packageName");
            Preconditions.checkNotNull(userHandle, "user");
            this.logErrorForInvalidProfileAccess(userHandle);
            try {
                object = this.mService.getApplicationInfo(this.mContext.getPackageName(), string2, n, userHandle);
                if (object == null) break block3;
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Package ");
        stringBuilder.append(string2);
        stringBuilder.append(" not found for user ");
        stringBuilder.append(userHandle.getIdentifier());
        object = new PackageManager.NameNotFoundException(stringBuilder.toString());
        throw object;
    }

    public PinItemRequest getPinItemRequest(Intent intent) {
        return (PinItemRequest)intent.getParcelableExtra(EXTRA_PIN_ITEM_REQUEST);
    }

    public List<UserHandle> getProfiles() {
        if (this.mUserManager.isManagedProfile()) {
            ArrayList<UserHandle> arrayList = new ArrayList<UserHandle>(1);
            arrayList.add(Process.myUserHandle());
            return arrayList;
        }
        return this.mUserManager.getUserProfiles();
    }

    public Drawable getShortcutBadgedIconDrawable(ShortcutInfo object, int n) {
        Drawable drawable2 = this.getShortcutIconDrawable((ShortcutInfo)object, n);
        object = drawable2 == null ? null : this.mContext.getPackageManager().getUserBadgedIcon(drawable2, ((ShortcutInfo)object).getUserHandle());
        return object;
    }

    public IntentSender getShortcutConfigActivityIntent(LauncherActivityInfo object) {
        try {
            object = this.mService.getShortcutConfigActivityIntent(this.mContext.getPackageName(), ((LauncherActivityInfo)object).getComponentName(), ((LauncherActivityInfo)object).getUser());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<LauncherActivityInfo> getShortcutConfigActivityList(String object, UserHandle userHandle) {
        this.logErrorForInvalidProfileAccess(userHandle);
        try {
            object = this.convertToActivityList(this.mService.getShortcutConfigActivities(this.mContext.getPackageName(), (String)object, userHandle), userHandle);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Drawable getShortcutIconDrawable(ShortcutInfo object, int n) {
        if (((ShortcutInfo)object).hasIconFile()) {
            ParcelFileDescriptor parcelFileDescriptor;
            block15 : {
                parcelFileDescriptor = this.getShortcutIconFd((ShortcutInfo)object);
                if (parcelFileDescriptor == null) {
                    return null;
                }
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
                if (bitmap == null) break block15;
                BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), bitmap);
                if (!((ShortcutInfo)object).hasAdaptiveBitmap()) return bitmapDrawable;
                object = new AdaptiveIconDrawable(null, bitmapDrawable);
                return object;
            }
            try {
                parcelFileDescriptor.close();
                return null;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return null;
            finally {
                try {
                    parcelFileDescriptor.close();
                }
                catch (IOException iOException) {}
            }
        }
        if (((ShortcutInfo)object).hasIconResource()) {
            return this.loadDrawableResourceFromPackage(((ShortcutInfo)object).getPackage(), ((ShortcutInfo)object).getIconResourceId(), ((ShortcutInfo)object).getUserHandle(), n);
        }
        if (((ShortcutInfo)object).getIcon() == null) return null;
        Icon icon = ((ShortcutInfo)object).getIcon();
        int n2 = icon.getType();
        if (n2 == 1) return icon.loadDrawable(this.mContext);
        if (n2 == 2) return this.loadDrawableResourceFromPackage(((ShortcutInfo)object).getPackage(), icon.getResId(), ((ShortcutInfo)object).getUserHandle(), n);
        if (n2 == 5) return icon.loadDrawable(this.mContext);
        return null;
    }

    public ParcelFileDescriptor getShortcutIconFd(ShortcutInfo shortcutInfo) {
        return this.getShortcutIconFd(shortcutInfo.getPackage(), shortcutInfo.getId(), shortcutInfo.getUserId());
    }

    public ParcelFileDescriptor getShortcutIconFd(String string2, String string3, UserHandle userHandle) {
        return this.getShortcutIconFd(string2, string3, userHandle.getIdentifier());
    }

    @Deprecated
    public int getShortcutIconResId(ShortcutInfo shortcutInfo) {
        return shortcutInfo.getIconResourceId();
    }

    @Deprecated
    public int getShortcutIconResId(String object, String string2, UserHandle userHandle) {
        ShortcutQuery shortcutQuery = new ShortcutQuery();
        shortcutQuery.setPackage((String)object);
        int n = 0;
        shortcutQuery.setShortcutIds(Arrays.asList(string2));
        shortcutQuery.setQueryFlags(11);
        object = this.getShortcuts(shortcutQuery, userHandle);
        if (object.size() > 0) {
            n = ((ShortcutInfo)object.get(0)).getIconResourceId();
        }
        return n;
    }

    @Deprecated
    public List<ShortcutInfo> getShortcutInfo(String string2, List<String> list, UserHandle userHandle) {
        ShortcutQuery shortcutQuery = new ShortcutQuery();
        shortcutQuery.setPackage(string2);
        shortcutQuery.setShortcutIds(list);
        shortcutQuery.setQueryFlags(11);
        return this.getShortcuts(shortcutQuery, userHandle);
    }

    public List<ShortcutInfo> getShortcuts(ShortcutQuery object, UserHandle userHandle) {
        this.logErrorForInvalidProfileAccess(userHandle);
        try {
            object = this.maybeUpdateDisabledMessage(this.mService.getShortcuts(this.mContext.getPackageName(), ((ShortcutQuery)object).mChangedSince, ((ShortcutQuery)object).mPackage, ((ShortcutQuery)object).mShortcutIds, ((ShortcutQuery)object).mActivity, ((ShortcutQuery)object).mQueryFlags, userHandle).getList());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Bundle getSuspendedPackageLauncherExtras(String object, UserHandle userHandle) {
        this.logErrorForInvalidProfileAccess(userHandle);
        try {
            object = this.mService.getSuspendedPackageLauncherExtras((String)object, userHandle);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean hasShortcutHostPermission() {
        try {
            boolean bl = this.mService.hasShortcutHostPermission(this.mContext.getPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isActivityEnabled(ComponentName componentName, UserHandle userHandle) {
        this.logErrorForInvalidProfileAccess(userHandle);
        try {
            boolean bl = this.mService.isActivityEnabled(this.mContext.getPackageName(), componentName, userHandle);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isPackageEnabled(String string2, UserHandle userHandle) {
        this.logErrorForInvalidProfileAccess(userHandle);
        try {
            boolean bl = this.mService.isPackageEnabled(this.mContext.getPackageName(), string2, userHandle);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void pinShortcuts(String string2, List<String> list, UserHandle userHandle) {
        this.logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.pinShortcuts(this.mContext.getPackageName(), string2, list, userHandle);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void registerCallback(Callback callback) {
        this.registerCallback(callback, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerCallback(Callback callback, Handler handler) {
        synchronized (this) {
            if (callback != null && this.findCallbackLocked(callback) < 0) {
                boolean bl = this.mCallbacks.size() == 0;
                this.addCallbackLocked(callback, handler);
                if (bl) {
                    try {
                        this.mService.addOnAppsChangedListener(this.mContext.getPackageName(), this.mAppsChangedListener);
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerPackageInstallerSessionCallback(Executor executor, PackageInstaller.SessionCallback sessionCallback) {
        if (executor == null) {
            throw new NullPointerException("Executor must not be null");
        }
        List<PackageInstaller.SessionCallbackDelegate> list = this.mDelegates;
        synchronized (list) {
            PackageInstaller.SessionCallbackDelegate sessionCallbackDelegate = new PackageInstaller.SessionCallbackDelegate(sessionCallback, executor);
            try {
                this.mService.registerPackageInstallerCallback(this.mContext.getPackageName(), sessionCallbackDelegate);
                this.mDelegates.add(sessionCallbackDelegate);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            return;
        }
    }

    public LauncherActivityInfo resolveActivity(Intent object, UserHandle userHandle) {
        block3 : {
            this.logErrorForInvalidProfileAccess(userHandle);
            try {
                object = this.mService.resolveActivity(this.mContext.getPackageName(), ((Intent)object).getComponent(), userHandle);
                if (object == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            object = new LauncherActivityInfo(this.mContext, (ActivityInfo)object, userHandle);
            return object;
        }
        return null;
    }

    public boolean shouldHideFromSuggestions(String string2, UserHandle userHandle) {
        Preconditions.checkNotNull(string2, "packageName");
        Preconditions.checkNotNull(userHandle, "user");
        try {
            boolean bl = this.mService.shouldHideFromSuggestions(string2, userHandle);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void startAppDetailsActivity(ComponentName componentName, UserHandle userHandle, Rect rect, Bundle bundle) {
        this.logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.showAppDetailsAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), componentName, rect, bundle, userHandle);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void startMainActivity(ComponentName componentName, UserHandle userHandle, Rect rect, Bundle bundle) {
        this.logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), componentName, rect, bundle, userHandle);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void startPackageInstallerSessionDetailsActivity(PackageInstaller.SessionInfo sessionInfo, Rect rect, Bundle bundle) {
        try {
            this.mService.startSessionDetailsActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), sessionInfo, rect, bundle, sessionInfo.getUser());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void startShortcut(ShortcutInfo shortcutInfo, Rect rect, Bundle bundle) {
        this.startShortcut(shortcutInfo.getPackage(), shortcutInfo.getId(), rect, bundle, shortcutInfo.getUserId());
    }

    public void startShortcut(String string2, String string3, Rect rect, Bundle bundle, UserHandle userHandle) {
        this.logErrorForInvalidProfileAccess(userHandle);
        this.startShortcut(string2, string3, rect, bundle, userHandle.getIdentifier());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterCallback(Callback callback) {
        synchronized (this) {
            this.removeCallbackLocked(callback);
            int n = this.mCallbacks.size();
            if (n == 0) {
                try {
                    this.mService.removeOnAppsChangedListener(this.mAppsChangedListener);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterPackageInstallerSessionCallback(PackageInstaller.SessionCallback sessionCallback) {
        List<PackageInstaller.SessionCallbackDelegate> list = this.mDelegates;
        synchronized (list) {
            Iterator<PackageInstaller.SessionCallbackDelegate> iterator = this.mDelegates.iterator();
            while (iterator.hasNext()) {
                PackageInstaller.SessionCallbackDelegate sessionCallbackDelegate = iterator.next();
                if (sessionCallbackDelegate.mCallback != sessionCallback) continue;
                this.mPm.getPackageInstaller().unregisterSessionCallback(sessionCallbackDelegate.mCallback);
                iterator.remove();
            }
            return;
        }
    }

    @SystemApi
    public static final class AppUsageLimit
    implements Parcelable {
        public static final Parcelable.Creator<AppUsageLimit> CREATOR = new Parcelable.Creator<AppUsageLimit>(){

            @Override
            public AppUsageLimit createFromParcel(Parcel parcel) {
                return new AppUsageLimit(parcel);
            }

            public AppUsageLimit[] newArray(int n) {
                return new AppUsageLimit[n];
            }
        };
        private final long mTotalUsageLimit;
        private final long mUsageRemaining;

        public AppUsageLimit(long l, long l2) {
            this.mTotalUsageLimit = l;
            this.mUsageRemaining = l2;
        }

        private AppUsageLimit(Parcel parcel) {
            this.mTotalUsageLimit = parcel.readLong();
            this.mUsageRemaining = parcel.readLong();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public long getTotalUsageLimit() {
            return this.mTotalUsageLimit;
        }

        public long getUsageRemaining() {
            return this.mUsageRemaining;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeLong(this.mTotalUsageLimit);
            parcel.writeLong(this.mUsageRemaining);
        }

    }

    public static abstract class Callback {
        public abstract void onPackageAdded(String var1, UserHandle var2);

        public abstract void onPackageChanged(String var1, UserHandle var2);

        public abstract void onPackageRemoved(String var1, UserHandle var2);

        public abstract void onPackagesAvailable(String[] var1, UserHandle var2, boolean var3);

        public void onPackagesSuspended(String[] arrstring, UserHandle userHandle) {
        }

        public void onPackagesSuspended(String[] arrstring, UserHandle userHandle, Bundle bundle) {
            this.onPackagesSuspended(arrstring, userHandle);
        }

        public abstract void onPackagesUnavailable(String[] var1, UserHandle var2, boolean var3);

        public void onPackagesUnsuspended(String[] arrstring, UserHandle userHandle) {
        }

        public void onShortcutsChanged(String string2, List<ShortcutInfo> list, UserHandle userHandle) {
        }
    }

    private static class CallbackMessageHandler
    extends Handler {
        private static final int MSG_ADDED = 1;
        private static final int MSG_AVAILABLE = 4;
        private static final int MSG_CHANGED = 3;
        private static final int MSG_REMOVED = 2;
        private static final int MSG_SHORTCUT_CHANGED = 8;
        private static final int MSG_SUSPENDED = 6;
        private static final int MSG_UNAVAILABLE = 5;
        private static final int MSG_UNSUSPENDED = 7;
        private Callback mCallback;

        public CallbackMessageHandler(Looper looper, Callback callback) {
            super(looper, null, true);
            this.mCallback = callback;
        }

        @Override
        public void handleMessage(Message message) {
            if (this.mCallback != null && message.obj instanceof CallbackInfo) {
                CallbackInfo callbackInfo = (CallbackInfo)message.obj;
                switch (message.what) {
                    default: {
                        break;
                    }
                    case 8: {
                        this.mCallback.onShortcutsChanged(callbackInfo.packageName, callbackInfo.shortcuts, callbackInfo.user);
                        break;
                    }
                    case 7: {
                        this.mCallback.onPackagesUnsuspended(callbackInfo.packageNames, callbackInfo.user);
                        break;
                    }
                    case 6: {
                        this.mCallback.onPackagesSuspended(callbackInfo.packageNames, callbackInfo.user, callbackInfo.launcherExtras);
                        break;
                    }
                    case 5: {
                        this.mCallback.onPackagesUnavailable(callbackInfo.packageNames, callbackInfo.user, callbackInfo.replacing);
                        break;
                    }
                    case 4: {
                        this.mCallback.onPackagesAvailable(callbackInfo.packageNames, callbackInfo.user, callbackInfo.replacing);
                        break;
                    }
                    case 3: {
                        this.mCallback.onPackageChanged(callbackInfo.packageName, callbackInfo.user);
                        break;
                    }
                    case 2: {
                        this.mCallback.onPackageRemoved(callbackInfo.packageName, callbackInfo.user);
                        break;
                    }
                    case 1: {
                        this.mCallback.onPackageAdded(callbackInfo.packageName, callbackInfo.user);
                    }
                }
                return;
            }
        }

        public void postOnPackageAdded(String string2, UserHandle userHandle) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageName = string2;
            callbackInfo.user = userHandle;
            this.obtainMessage(1, callbackInfo).sendToTarget();
        }

        public void postOnPackageChanged(String string2, UserHandle userHandle) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageName = string2;
            callbackInfo.user = userHandle;
            this.obtainMessage(3, callbackInfo).sendToTarget();
        }

        public void postOnPackageRemoved(String string2, UserHandle userHandle) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageName = string2;
            callbackInfo.user = userHandle;
            this.obtainMessage(2, callbackInfo).sendToTarget();
        }

        public void postOnPackagesAvailable(String[] arrstring, UserHandle userHandle, boolean bl) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageNames = arrstring;
            callbackInfo.replacing = bl;
            callbackInfo.user = userHandle;
            this.obtainMessage(4, callbackInfo).sendToTarget();
        }

        public void postOnPackagesSuspended(String[] arrstring, Bundle bundle, UserHandle userHandle) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageNames = arrstring;
            callbackInfo.user = userHandle;
            callbackInfo.launcherExtras = bundle;
            this.obtainMessage(6, callbackInfo).sendToTarget();
        }

        public void postOnPackagesUnavailable(String[] arrstring, UserHandle userHandle, boolean bl) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageNames = arrstring;
            callbackInfo.replacing = bl;
            callbackInfo.user = userHandle;
            this.obtainMessage(5, callbackInfo).sendToTarget();
        }

        public void postOnPackagesUnsuspended(String[] arrstring, UserHandle userHandle) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageNames = arrstring;
            callbackInfo.user = userHandle;
            this.obtainMessage(7, callbackInfo).sendToTarget();
        }

        public void postOnShortcutChanged(String string2, UserHandle userHandle, List<ShortcutInfo> list) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageName = string2;
            callbackInfo.user = userHandle;
            callbackInfo.shortcuts = list;
            this.obtainMessage(8, callbackInfo).sendToTarget();
        }

        private static class CallbackInfo {
            Bundle launcherExtras;
            String packageName;
            String[] packageNames;
            boolean replacing;
            List<ShortcutInfo> shortcuts;
            UserHandle user;

            private CallbackInfo() {
            }
        }

    }

    public static final class PinItemRequest
    implements Parcelable {
        public static final Parcelable.Creator<PinItemRequest> CREATOR = new Parcelable.Creator<PinItemRequest>(){

            @Override
            public PinItemRequest createFromParcel(Parcel parcel) {
                return new PinItemRequest(parcel);
            }

            public PinItemRequest[] newArray(int n) {
                return new PinItemRequest[n];
            }
        };
        public static final int REQUEST_TYPE_APPWIDGET = 2;
        public static final int REQUEST_TYPE_SHORTCUT = 1;
        private final IPinItemRequest mInner;
        private final int mRequestType;

        public PinItemRequest(IPinItemRequest iPinItemRequest, int n) {
            this.mInner = iPinItemRequest;
            this.mRequestType = n;
        }

        private PinItemRequest(Parcel parcel) {
            this.getClass().getClassLoader();
            this.mRequestType = parcel.readInt();
            this.mInner = IPinItemRequest.Stub.asInterface(parcel.readStrongBinder());
        }

        public boolean accept() {
            return this.accept(null);
        }

        public boolean accept(Bundle bundle) {
            try {
                boolean bl = this.mInner.accept(bundle);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public AppWidgetProviderInfo getAppWidgetProviderInfo(Context context) {
            AppWidgetProviderInfo appWidgetProviderInfo;
            block3 : {
                try {
                    appWidgetProviderInfo = this.mInner.getAppWidgetProviderInfo();
                    if (appWidgetProviderInfo != null) break block3;
                    return null;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            appWidgetProviderInfo.updateDimensions(context.getResources().getDisplayMetrics());
            return appWidgetProviderInfo;
        }

        public Bundle getExtras() {
            try {
                Bundle bundle = this.mInner.getExtras();
                return bundle;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowAsRuntimeException();
            }
        }

        public int getRequestType() {
            return this.mRequestType;
        }

        public ShortcutInfo getShortcutInfo() {
            try {
                ShortcutInfo shortcutInfo = this.mInner.getShortcutInfo();
                return shortcutInfo;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowAsRuntimeException();
            }
        }

        public boolean isValid() {
            try {
                boolean bl = this.mInner.isValid();
                return bl;
            }
            catch (RemoteException remoteException) {
                return false;
            }
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mRequestType);
            parcel.writeStrongBinder(this.mInner.asBinder());
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface RequestType {
        }

    }

    public static class ShortcutQuery {
        @Deprecated
        public static final int FLAG_GET_ALL_KINDS = 11;
        @Deprecated
        public static final int FLAG_GET_DYNAMIC = 1;
        public static final int FLAG_GET_KEY_FIELDS_ONLY = 4;
        @Deprecated
        public static final int FLAG_GET_MANIFEST = 8;
        @Deprecated
        public static final int FLAG_GET_PINNED = 2;
        public static final int FLAG_MATCH_ALL_KINDS = 11;
        public static final int FLAG_MATCH_ALL_KINDS_WITH_ALL_PINNED = 1035;
        public static final int FLAG_MATCH_DYNAMIC = 1;
        public static final int FLAG_MATCH_MANIFEST = 8;
        public static final int FLAG_MATCH_PINNED = 2;
        public static final int FLAG_MATCH_PINNED_BY_ANY_LAUNCHER = 1024;
        ComponentName mActivity;
        long mChangedSince;
        String mPackage;
        int mQueryFlags;
        List<String> mShortcutIds;

        public ShortcutQuery setActivity(ComponentName componentName) {
            this.mActivity = componentName;
            return this;
        }

        public ShortcutQuery setChangedSince(long l) {
            this.mChangedSince = l;
            return this;
        }

        public ShortcutQuery setPackage(String string2) {
            this.mPackage = string2;
            return this;
        }

        public ShortcutQuery setQueryFlags(int n) {
            this.mQueryFlags = n;
            return this;
        }

        public ShortcutQuery setShortcutIds(List<String> list) {
            this.mShortcutIds = list;
            return this;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface QueryFlags {
        }

    }

}

