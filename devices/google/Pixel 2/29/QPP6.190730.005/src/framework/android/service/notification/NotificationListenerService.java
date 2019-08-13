/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.Person;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.notification.INotificationListener;
import android.service.notification.IStatusBarNotificationHolder;
import android.service.notification.NotificationRankingUpdate;
import android.service.notification.NotificationStats;
import android.service.notification.SnoozeCriterion;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.RemoteViews;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.SomeArgs;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class NotificationListenerService
extends Service {
    public static final int HINT_HOST_DISABLE_CALL_EFFECTS = 4;
    public static final int HINT_HOST_DISABLE_EFFECTS = 1;
    public static final int HINT_HOST_DISABLE_NOTIFICATION_EFFECTS = 2;
    public static final int INTERRUPTION_FILTER_ALARMS = 4;
    public static final int INTERRUPTION_FILTER_ALL = 1;
    public static final int INTERRUPTION_FILTER_NONE = 3;
    public static final int INTERRUPTION_FILTER_PRIORITY = 2;
    public static final int INTERRUPTION_FILTER_UNKNOWN = 0;
    public static final int NOTIFICATION_CHANNEL_OR_GROUP_ADDED = 1;
    public static final int NOTIFICATION_CHANNEL_OR_GROUP_DELETED = 3;
    public static final int NOTIFICATION_CHANNEL_OR_GROUP_UPDATED = 2;
    public static final int REASON_APP_CANCEL = 8;
    public static final int REASON_APP_CANCEL_ALL = 9;
    public static final int REASON_CANCEL = 2;
    public static final int REASON_CANCEL_ALL = 3;
    public static final int REASON_CHANNEL_BANNED = 17;
    public static final int REASON_CLICK = 1;
    public static final int REASON_ERROR = 4;
    public static final int REASON_GROUP_OPTIMIZATION = 13;
    public static final int REASON_GROUP_SUMMARY_CANCELED = 12;
    public static final int REASON_LISTENER_CANCEL = 10;
    public static final int REASON_LISTENER_CANCEL_ALL = 11;
    public static final int REASON_PACKAGE_BANNED = 7;
    public static final int REASON_PACKAGE_CHANGED = 5;
    public static final int REASON_PACKAGE_SUSPENDED = 14;
    public static final int REASON_PROFILE_TURNED_OFF = 15;
    public static final int REASON_SNOOZED = 18;
    public static final int REASON_TIMEOUT = 19;
    public static final int REASON_UNAUTOBUNDLED = 16;
    public static final int REASON_USER_STOPPED = 6;
    public static final String SERVICE_INTERFACE = "android.service.notification.NotificationListenerService";
    @Deprecated
    public static final int SUPPRESSED_EFFECT_SCREEN_OFF = 1;
    @Deprecated
    public static final int SUPPRESSED_EFFECT_SCREEN_ON = 2;
    @SystemApi
    public static final int TRIM_FULL = 0;
    @SystemApi
    public static final int TRIM_LIGHT = 1;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final String TAG = this.getClass().getSimpleName();
    private boolean isConnected = false;
    protected int mCurrentUser;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private Handler mHandler;
    private final Object mLock = new Object();
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    protected INotificationManager mNoMan;
    @GuardedBy(value={"mLock"})
    private RankingMap mRankingMap;
    protected Context mSystemContext;
    @UnsupportedAppUsage
    protected NotificationListenerWrapper mWrapper = null;

    private StatusBarNotification[] cleanUpNotificationList(ParceledListSlice<StatusBarNotification> object) {
        if (object != null && ((ParceledListSlice)object).getList() != null) {
            List list = ((ParceledListSlice)object).getList();
            Object object2 = null;
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                StatusBarNotification statusBarNotification = (StatusBarNotification)list.get(i);
                object = statusBarNotification.getNotification();
                try {
                    this.createLegacyIconExtras((Notification)object);
                    this.maybePopulateRemoteViews((Notification)object);
                    this.maybePopulatePeople((Notification)object);
                    object = object2;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    object = object2;
                    if (object2 == null) {
                        object = new ArrayList(n);
                    }
                    ((ArrayList)object).add((StatusBarNotification)statusBarNotification);
                    object2 = this.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("get(Active/Snoozed)Notifications: can't rebuild notification from ");
                    stringBuilder.append(statusBarNotification.getPackageName());
                    Log.w((String)object2, stringBuilder.toString());
                }
                object2 = object;
            }
            if (object2 != null) {
                list.removeAll((Collection<?>)object2);
            }
            return list.toArray(new StatusBarNotification[list.size()]);
        }
        return new StatusBarNotification[0];
    }

    private void maybePopulatePeople(Notification notification) {
        ArrayList arrayList;
        if (this.getContext().getApplicationInfo().targetSdkVersion < 28 && (arrayList = notification.extras.getParcelableArrayList("android.people.list")) != null && arrayList.isEmpty()) {
            int n = arrayList.size();
            String[] arrstring = new String[n];
            for (int i = 0; i < n; ++i) {
                arrstring[i] = ((Person)arrayList.get(i)).resolveToLegacyUri();
            }
            notification.extras.putStringArray("android.people", arrstring);
        }
    }

    private void maybePopulateRemoteViews(Notification notification) {
        if (this.getContext().getApplicationInfo().targetSdkVersion < 24) {
            Object object = Notification.Builder.recoverBuilder(this.getContext(), notification);
            RemoteViews remoteViews = ((Notification.Builder)object).createContentView();
            RemoteViews remoteViews2 = ((Notification.Builder)object).createBigContentView();
            object = ((Notification.Builder)object).createHeadsUpContentView();
            notification.contentView = remoteViews;
            notification.bigContentView = remoteViews2;
            notification.headsUpContentView = object;
        }
    }

    public static void requestRebind(ComponentName componentName) {
        INotificationManager iNotificationManager = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        try {
            iNotificationManager.requestBindListener(componentName);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @GuardedBy(value={"mLock"})
    public final void applyUpdateLocked(NotificationRankingUpdate notificationRankingUpdate) {
        this.mRankingMap = notificationRankingUpdate.getRankingMap();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new MyHandler(this.getMainLooper());
    }

    public final void cancelAllNotifications() {
        this.cancelNotifications(null);
    }

    public final void cancelNotification(String string2) {
        if (!this.isBound()) {
            return;
        }
        try {
            this.getNotificationInterface().cancelNotificationsFromListener(this.mWrapper, new String[]{string2});
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
        }
    }

    @Deprecated
    public final void cancelNotification(String string2, String string3, int n) {
        if (!this.isBound()) {
            return;
        }
        try {
            this.getNotificationInterface().cancelNotificationFromListener(this.mWrapper, string2, string3, n);
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
        }
    }

    public final void cancelNotifications(String[] arrstring) {
        if (!this.isBound()) {
            return;
        }
        try {
            this.getNotificationInterface().cancelNotificationsFromListener(this.mWrapper, arrstring);
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
        }
    }

    public final void clearRequestedListenerHints() {
        if (!this.isBound()) {
            return;
        }
        try {
            this.getNotificationInterface().clearRequestedListenerHints(this.mWrapper);
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
        }
    }

    public final void createLegacyIconExtras(Notification notification) {
        if (this.getContext().getApplicationInfo().targetSdkVersion < 23) {
            Object object = notification.getSmallIcon();
            Icon icon = notification.getLargeIcon();
            if (object != null && ((Icon)object).getType() == 2) {
                notification.extras.putInt("android.icon", ((Icon)object).getResId());
                notification.icon = ((Icon)object).getResId();
            }
            if (icon != null && (object = icon.loadDrawable(this.getContext())) != null && object instanceof BitmapDrawable) {
                object = ((BitmapDrawable)object).getBitmap();
                notification.extras.putParcelable("android.largeIcon", (Parcelable)object);
                notification.largeIcon = object;
            }
        }
    }

    public StatusBarNotification[] getActiveNotifications() {
        StatusBarNotification[] arrstatusBarNotification = this.getActiveNotifications(null, 0);
        if (arrstatusBarNotification == null) {
            arrstatusBarNotification = new StatusBarNotification[]{};
        }
        return arrstatusBarNotification;
    }

    @SystemApi
    public StatusBarNotification[] getActiveNotifications(int n) {
        StatusBarNotification[] arrstatusBarNotification = this.getActiveNotifications(null, n);
        if (arrstatusBarNotification == null) {
            arrstatusBarNotification = new StatusBarNotification[]{};
        }
        return arrstatusBarNotification;
    }

    public StatusBarNotification[] getActiveNotifications(String[] arrobject) {
        if ((arrobject = this.getActiveNotifications((String[])arrobject, 0)) == null) {
            arrobject = new StatusBarNotification[]{};
        }
        return arrobject;
    }

    @SystemApi
    public StatusBarNotification[] getActiveNotifications(String[] arrobject, int n) {
        if (!this.isBound()) {
            return null;
        }
        try {
            arrobject = this.cleanUpNotificationList(this.getNotificationInterface().getActiveNotificationsFromListener(this.mWrapper, (String[])arrobject, n));
            return arrobject;
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
            return null;
        }
    }

    protected Context getContext() {
        Context context = this.mSystemContext;
        if (context != null) {
            return context;
        }
        return this;
    }

    public final int getCurrentInterruptionFilter() {
        if (!this.isBound()) {
            return 0;
        }
        try {
            int n = this.getNotificationInterface().getInterruptionFilterFromListener(this.mWrapper);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
            return 0;
        }
    }

    public final int getCurrentListenerHints() {
        if (!this.isBound()) {
            return 0;
        }
        try {
            int n = this.getNotificationInterface().getHintsFromListener(this.mWrapper);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public RankingMap getCurrentRanking() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mRankingMap;
        }
    }

    public final List<NotificationChannelGroup> getNotificationChannelGroups(String object, UserHandle userHandle) {
        if (!this.isBound()) {
            return null;
        }
        try {
            object = this.getNotificationInterface().getNotificationChannelGroupsFromPrivilegedListener(this.mWrapper, (String)object, userHandle).getList();
            return object;
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public final List<NotificationChannel> getNotificationChannels(String object, UserHandle userHandle) {
        if (!this.isBound()) {
            return null;
        }
        try {
            object = this.getNotificationInterface().getNotificationChannelsFromPrivilegedListener(this.mWrapper, (String)object, userHandle).getList();
            return object;
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    protected final INotificationManager getNotificationInterface() {
        if (this.mNoMan == null) {
            this.mNoMan = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        }
        return this.mNoMan;
    }

    public final StatusBarNotification[] getSnoozedNotifications() {
        try {
            StatusBarNotification[] arrstatusBarNotification = this.cleanUpNotificationList(this.getNotificationInterface().getSnoozedNotificationsFromListener(this.mWrapper, 0));
            return arrstatusBarNotification;
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
            return null;
        }
    }

    @UnsupportedAppUsage
    protected boolean isBound() {
        if (this.mWrapper == null) {
            Log.w(this.TAG, "Notification listener service not yet bound.");
            return false;
        }
        return true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (this.mWrapper == null) {
            this.mWrapper = new NotificationListenerWrapper();
        }
        return this.mWrapper;
    }

    @Override
    public void onDestroy() {
        this.onListenerDisconnected();
        super.onDestroy();
    }

    public void onInterruptionFilterChanged(int n) {
    }

    public void onListenerConnected() {
    }

    public void onListenerDisconnected() {
    }

    public void onListenerHintsChanged(int n) {
    }

    public void onNotificationChannelGroupModified(String string2, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int n) {
    }

    public void onNotificationChannelModified(String string2, UserHandle userHandle, NotificationChannel notificationChannel, int n) {
    }

    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
    }

    public void onNotificationPosted(StatusBarNotification statusBarNotification, RankingMap rankingMap) {
        this.onNotificationPosted(statusBarNotification);
    }

    public void onNotificationRankingUpdate(RankingMap rankingMap) {
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification, RankingMap rankingMap) {
        this.onNotificationRemoved(statusBarNotification);
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification, RankingMap rankingMap, int n) {
        this.onNotificationRemoved(statusBarNotification, rankingMap);
    }

    @SystemApi
    public void onNotificationRemoved(StatusBarNotification statusBarNotification, RankingMap rankingMap, NotificationStats notificationStats, int n) {
        this.onNotificationRemoved(statusBarNotification, rankingMap, n);
    }

    public void onSilentStatusBarIconsVisibilityChanged(boolean bl) {
    }

    @SystemApi
    public void registerAsSystemService(Context context, ComponentName componentName, int n) throws RemoteException {
        if (this.mWrapper == null) {
            this.mWrapper = new NotificationListenerWrapper();
        }
        this.mSystemContext = context;
        INotificationManager iNotificationManager = this.getNotificationInterface();
        this.mHandler = new MyHandler(context.getMainLooper());
        this.mCurrentUser = n;
        iNotificationManager.registerListener(this.mWrapper, componentName, n);
    }

    public final void requestInterruptionFilter(int n) {
        if (!this.isBound()) {
            return;
        }
        try {
            this.getNotificationInterface().requestInterruptionFilterFromListener(this.mWrapper, n);
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
        }
    }

    public final void requestListenerHints(int n) {
        if (!this.isBound()) {
            return;
        }
        try {
            this.getNotificationInterface().requestHintsFromListener(this.mWrapper, n);
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
        }
    }

    public final void requestUnbind() {
        if (this.mWrapper != null) {
            INotificationManager iNotificationManager = this.getNotificationInterface();
            try {
                iNotificationManager.requestUnbindListener(this.mWrapper);
                this.isConnected = false;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public final void setNotificationsShown(String[] arrstring) {
        if (!this.isBound()) {
            return;
        }
        try {
            this.getNotificationInterface().setNotificationsShownFromListener(this.mWrapper, arrstring);
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
        }
    }

    @SystemApi
    public final void setOnNotificationPostedTrim(int n) {
        if (!this.isBound()) {
            return;
        }
        try {
            this.getNotificationInterface().setOnNotificationPostedTrimFromListener(this.mWrapper, n);
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
        }
    }

    public final void snoozeNotification(String string2, long l) {
        if (!this.isBound()) {
            return;
        }
        try {
            this.getNotificationInterface().snoozeNotificationUntilFromListener(this.mWrapper, string2, l);
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
        }
    }

    @SystemApi
    public final void snoozeNotification(String string2, String string3) {
        if (!this.isBound()) {
            return;
        }
        try {
            this.getNotificationInterface().snoozeNotificationUntilContextFromListener(this.mWrapper, string2, string3);
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
        }
    }

    @SystemApi
    public void unregisterAsSystemService() throws RemoteException {
        if (this.mWrapper != null) {
            this.getNotificationInterface().unregisterListener(this.mWrapper, this.mCurrentUser);
        }
    }

    public final void updateNotificationChannel(String string2, UserHandle userHandle, NotificationChannel notificationChannel) {
        if (!this.isBound()) {
            return;
        }
        try {
            this.getNotificationInterface().updateNotificationChannelFromPrivilegedListener(this.mWrapper, string2, userHandle, notificationChannel);
            return;
        }
        catch (RemoteException remoteException) {
            Log.v(this.TAG, "Unable to contact notification manager", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ChannelOrGroupModificationTypes {
    }

    private final class MyHandler
    extends Handler {
        public static final int MSG_ON_INTERRUPTION_FILTER_CHANGED = 6;
        public static final int MSG_ON_LISTENER_CONNECTED = 3;
        public static final int MSG_ON_LISTENER_HINTS_CHANGED = 5;
        public static final int MSG_ON_NOTIFICATION_CHANNEL_GROUP_MODIFIED = 8;
        public static final int MSG_ON_NOTIFICATION_CHANNEL_MODIFIED = 7;
        public static final int MSG_ON_NOTIFICATION_POSTED = 1;
        public static final int MSG_ON_NOTIFICATION_RANKING_UPDATE = 4;
        public static final int MSG_ON_NOTIFICATION_REMOVED = 2;
        public static final int MSG_ON_STATUS_BAR_ICON_BEHAVIOR_CHANGED = 9;

        public MyHandler(Looper looper) {
            super(looper, null, false);
        }

        @Override
        public void handleMessage(Message object) {
            if (!NotificationListenerService.this.isConnected) {
                return;
            }
            switch (((Message)object).what) {
                default: {
                    break;
                }
                case 9: {
                    NotificationListenerService.this.onSilentStatusBarIconsVisibilityChanged((Boolean)((Message)object).obj);
                    break;
                }
                case 8: {
                    object = (SomeArgs)((Message)object).obj;
                    String string2 = (String)((SomeArgs)object).arg1;
                    UserHandle userHandle = (UserHandle)((SomeArgs)object).arg2;
                    NotificationChannelGroup notificationChannelGroup = (NotificationChannelGroup)((SomeArgs)object).arg3;
                    int n = (Integer)((SomeArgs)object).arg4;
                    NotificationListenerService.this.onNotificationChannelGroupModified(string2, userHandle, notificationChannelGroup, n);
                    break;
                }
                case 7: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    object = (String)someArgs.arg1;
                    UserHandle userHandle = (UserHandle)someArgs.arg2;
                    NotificationChannel notificationChannel = (NotificationChannel)someArgs.arg3;
                    int n = (Integer)someArgs.arg4;
                    NotificationListenerService.this.onNotificationChannelModified((String)object, userHandle, notificationChannel, n);
                    break;
                }
                case 6: {
                    int n = ((Message)object).arg1;
                    NotificationListenerService.this.onInterruptionFilterChanged(n);
                    break;
                }
                case 5: {
                    int n = ((Message)object).arg1;
                    NotificationListenerService.this.onListenerHintsChanged(n);
                    break;
                }
                case 4: {
                    object = (RankingMap)((Message)object).obj;
                    NotificationListenerService.this.onNotificationRankingUpdate((RankingMap)object);
                    break;
                }
                case 3: {
                    NotificationListenerService.this.onListenerConnected();
                    break;
                }
                case 2: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    object = (StatusBarNotification)someArgs.arg1;
                    RankingMap rankingMap = (RankingMap)someArgs.arg2;
                    int n = (Integer)someArgs.arg3;
                    NotificationStats notificationStats = (NotificationStats)someArgs.arg4;
                    someArgs.recycle();
                    NotificationListenerService.this.onNotificationRemoved((StatusBarNotification)object, rankingMap, notificationStats, n);
                    break;
                }
                case 1: {
                    object = (SomeArgs)((Message)object).obj;
                    StatusBarNotification statusBarNotification = (StatusBarNotification)((SomeArgs)object).arg1;
                    RankingMap rankingMap = (RankingMap)((SomeArgs)object).arg2;
                    ((SomeArgs)object).recycle();
                    NotificationListenerService.this.onNotificationPosted(statusBarNotification, rankingMap);
                }
            }
        }
    }

    protected class NotificationListenerWrapper
    extends INotificationListener.Stub {
        protected NotificationListenerWrapper() {
        }

        @Override
        public void onActionClicked(String string2, Notification.Action action, int n) {
        }

        @Override
        public void onAllowedAdjustmentsChanged() {
        }

        @Override
        public void onInterruptionFilterChanged(int n) throws RemoteException {
            NotificationListenerService.this.mHandler.obtainMessage(6, n, 0).sendToTarget();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onListenerConnected(NotificationRankingUpdate notificationRankingUpdate) {
            Object object = NotificationListenerService.this.mLock;
            synchronized (object) {
                NotificationListenerService.this.applyUpdateLocked(notificationRankingUpdate);
            }
            NotificationListenerService.this.isConnected = true;
            NotificationListenerService.this.mHandler.obtainMessage(3).sendToTarget();
        }

        @Override
        public void onListenerHintsChanged(int n) throws RemoteException {
            NotificationListenerService.this.mHandler.obtainMessage(5, n, 0).sendToTarget();
        }

        @Override
        public void onNotificationChannelGroupModification(String string2, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int n) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = string2;
            someArgs.arg2 = userHandle;
            someArgs.arg3 = notificationChannelGroup;
            someArgs.arg4 = n;
            NotificationListenerService.this.mHandler.obtainMessage(8, someArgs).sendToTarget();
        }

        @Override
        public void onNotificationChannelModification(String string2, UserHandle userHandle, NotificationChannel notificationChannel, int n) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = string2;
            someArgs.arg2 = userHandle;
            someArgs.arg3 = notificationChannel;
            someArgs.arg4 = n;
            NotificationListenerService.this.mHandler.obtainMessage(7, someArgs).sendToTarget();
        }

        @Override
        public void onNotificationDirectReply(String string2) {
        }

        @Override
        public void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationChannel notificationChannel) throws RemoteException {
        }

        @Override
        public void onNotificationExpansionChanged(String string2, boolean bl, boolean bl2) {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onNotificationPosted(IStatusBarNotificationHolder object, NotificationRankingUpdate object2) {
            Object object3;
            try {
                object = object.get();
            }
            catch (RemoteException remoteException) {
                Log.w(NotificationListenerService.this.TAG, "onNotificationPosted: Error receiving StatusBarNotification", remoteException);
                return;
            }
            try {
                NotificationListenerService.this.createLegacyIconExtras(((StatusBarNotification)object).getNotification());
                NotificationListenerService.this.maybePopulateRemoteViews(((StatusBarNotification)object).getNotification());
                NotificationListenerService.this.maybePopulatePeople(((StatusBarNotification)object).getNotification());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                String string2 = NotificationListenerService.this.TAG;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("onNotificationPosted: can't rebuild notification from ");
                ((StringBuilder)object3).append(((StatusBarNotification)object).getPackageName());
                Log.w(string2, ((StringBuilder)object3).toString());
                object = null;
            }
            object3 = NotificationListenerService.this.mLock;
            synchronized (object3) {
                NotificationListenerService.this.applyUpdateLocked((NotificationRankingUpdate)object2);
                if (object != null) {
                    object2 = SomeArgs.obtain();
                    ((SomeArgs)object2).arg1 = object;
                    ((SomeArgs)object2).arg2 = NotificationListenerService.this.mRankingMap;
                    NotificationListenerService.this.mHandler.obtainMessage(1, object2).sendToTarget();
                } else {
                    NotificationListenerService.this.mHandler.obtainMessage(4, NotificationListenerService.this.mRankingMap).sendToTarget();
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
        public void onNotificationRankingUpdate(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
            Object object = NotificationListenerService.this.mLock;
            synchronized (object) {
                NotificationListenerService.this.applyUpdateLocked(notificationRankingUpdate);
                NotificationListenerService.this.mHandler.obtainMessage(4, NotificationListenerService.this.mRankingMap).sendToTarget();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onNotificationRemoved(IStatusBarNotificationHolder object, NotificationRankingUpdate object2, NotificationStats notificationStats, int n) {
            StatusBarNotification statusBarNotification;
            try {
                statusBarNotification = object.get();
            }
            catch (RemoteException remoteException) {
                Log.w(NotificationListenerService.this.TAG, "onNotificationRemoved: Error receiving StatusBarNotification", remoteException);
                return;
            }
            object = NotificationListenerService.this.mLock;
            synchronized (object) {
                NotificationListenerService.this.applyUpdateLocked((NotificationRankingUpdate)object2);
                object2 = SomeArgs.obtain();
                ((SomeArgs)object2).arg1 = statusBarNotification;
                ((SomeArgs)object2).arg2 = NotificationListenerService.this.mRankingMap;
                ((SomeArgs)object2).arg3 = n;
                ((SomeArgs)object2).arg4 = notificationStats;
                NotificationListenerService.this.mHandler.obtainMessage(2, object2).sendToTarget();
                return;
            }
        }

        @Override
        public void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder iStatusBarNotificationHolder, String string2) throws RemoteException {
        }

        @Override
        public void onNotificationsSeen(List<String> list) throws RemoteException {
        }

        @Override
        public void onStatusBarIconsBehaviorChanged(boolean bl) {
            NotificationListenerService.this.mHandler.obtainMessage(9, bl).sendToTarget();
        }

        @Override
        public void onSuggestedReplySent(String string2, CharSequence charSequence, int n) {
        }
    }

    public static class Ranking {
        private static final int PARCEL_VERSION = 2;
        public static final int USER_SENTIMENT_NEGATIVE = -1;
        public static final int USER_SENTIMENT_NEUTRAL = 0;
        public static final int USER_SENTIMENT_POSITIVE = 1;
        public static final int VISIBILITY_NO_OVERRIDE = -1000;
        private boolean mCanBubble;
        private NotificationChannel mChannel;
        private boolean mHidden;
        private int mImportance;
        private CharSequence mImportanceExplanation;
        private boolean mIsAmbient;
        private String mKey;
        private long mLastAudiblyAlertedMs;
        private boolean mMatchesInterruptionFilter;
        private boolean mNoisy;
        private String mOverrideGroupKey;
        private ArrayList<String> mOverridePeople;
        private int mRank = -1;
        private boolean mShowBadge;
        private ArrayList<Notification.Action> mSmartActions;
        private ArrayList<CharSequence> mSmartReplies;
        private ArrayList<SnoozeCriterion> mSnoozeCriteria;
        private int mSuppressedVisualEffects;
        private int mUserSentiment = 0;
        private int mVisibilityOverride;

        public Ranking() {
        }

        @VisibleForTesting
        public Ranking(Parcel parcel) {
            Object object = this.getClass().getClassLoader();
            int n = parcel.readInt();
            if (n == 2) {
                this.mKey = parcel.readString();
                this.mRank = parcel.readInt();
                this.mIsAmbient = parcel.readBoolean();
                this.mMatchesInterruptionFilter = parcel.readBoolean();
                this.mVisibilityOverride = parcel.readInt();
                this.mSuppressedVisualEffects = parcel.readInt();
                this.mImportance = parcel.readInt();
                this.mImportanceExplanation = parcel.readCharSequence();
                this.mOverrideGroupKey = parcel.readString();
                this.mChannel = (NotificationChannel)parcel.readParcelable((ClassLoader)object);
                this.mOverridePeople = parcel.createStringArrayList();
                this.mSnoozeCriteria = parcel.createTypedArrayList(SnoozeCriterion.CREATOR);
                this.mShowBadge = parcel.readBoolean();
                this.mUserSentiment = parcel.readInt();
                this.mHidden = parcel.readBoolean();
                this.mLastAudiblyAlertedMs = parcel.readLong();
                this.mNoisy = parcel.readBoolean();
                this.mSmartActions = parcel.createTypedArrayList(Notification.Action.CREATOR);
                this.mSmartReplies = parcel.readCharSequenceList();
                this.mCanBubble = parcel.readBoolean();
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("malformed Ranking parcel: ");
            ((StringBuilder)object).append(parcel);
            ((StringBuilder)object).append(" version ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", expected ");
            ((StringBuilder)object).append(2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public static String importanceToString(int n) {
            if (n != -1000) {
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                if (n != 4 && n != 5) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("UNKNOWN(");
                                    stringBuilder.append(String.valueOf(n));
                                    stringBuilder.append(")");
                                    return stringBuilder.toString();
                                }
                                return "HIGH";
                            }
                            return "DEFAULT";
                        }
                        return "LOW";
                    }
                    return "MIN";
                }
                return "NONE";
            }
            return "UNSPECIFIED";
        }

        public boolean canBubble() {
            return this.mCanBubble;
        }

        public boolean canShowBadge() {
            return this.mShowBadge;
        }

        public boolean equals(Object object) {
            block4 : {
                boolean bl;
                block6 : {
                    block5 : {
                        bl = true;
                        if (this == object) {
                            return true;
                        }
                        if (object == null || this.getClass() != object.getClass()) break block4;
                        object = (Ranking)object;
                        if (!Objects.equals(this.mKey, ((Ranking)object).mKey) || !Objects.equals(this.mRank, ((Ranking)object).mRank) || !Objects.equals(this.mMatchesInterruptionFilter, ((Ranking)object).mMatchesInterruptionFilter) || !Objects.equals(this.mVisibilityOverride, ((Ranking)object).mVisibilityOverride) || !Objects.equals(this.mSuppressedVisualEffects, ((Ranking)object).mSuppressedVisualEffects) || !Objects.equals(this.mImportance, ((Ranking)object).mImportance) || !Objects.equals(this.mImportanceExplanation, ((Ranking)object).mImportanceExplanation) || !Objects.equals(this.mOverrideGroupKey, ((Ranking)object).mOverrideGroupKey) || !Objects.equals(this.mChannel, ((Ranking)object).mChannel) || !Objects.equals(this.mOverridePeople, ((Ranking)object).mOverridePeople) || !Objects.equals(this.mSnoozeCriteria, ((Ranking)object).mSnoozeCriteria) || !Objects.equals(this.mShowBadge, ((Ranking)object).mShowBadge) || !Objects.equals(this.mUserSentiment, ((Ranking)object).mUserSentiment) || !Objects.equals(this.mHidden, ((Ranking)object).mHidden) || !Objects.equals(this.mLastAudiblyAlertedMs, ((Ranking)object).mLastAudiblyAlertedMs) || !Objects.equals(this.mNoisy, ((Ranking)object).mNoisy)) break block5;
                        ArrayList<Notification.Action> arrayList = this.mSmartActions;
                        int n = arrayList == null ? 0 : arrayList.size();
                        arrayList = ((Ranking)object).mSmartActions;
                        int n2 = arrayList == null ? 0 : arrayList.size();
                        if (n == n2 && Objects.equals(this.mSmartReplies, ((Ranking)object).mSmartReplies) && Objects.equals(this.mCanBubble, ((Ranking)object).mCanBubble)) break block6;
                    }
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        @SystemApi
        public List<String> getAdditionalPeople() {
            return this.mOverridePeople;
        }

        public NotificationChannel getChannel() {
            return this.mChannel;
        }

        public int getImportance() {
            return this.mImportance;
        }

        public CharSequence getImportanceExplanation() {
            return this.mImportanceExplanation;
        }

        public String getKey() {
            return this.mKey;
        }

        public long getLastAudiblyAlertedMillis() {
            return this.mLastAudiblyAlertedMs;
        }

        public String getOverrideGroupKey() {
            return this.mOverrideGroupKey;
        }

        public int getRank() {
            return this.mRank;
        }

        public List<Notification.Action> getSmartActions() {
            return this.mSmartActions;
        }

        public List<CharSequence> getSmartReplies() {
            return this.mSmartReplies;
        }

        @SystemApi
        public List<SnoozeCriterion> getSnoozeCriteria() {
            return this.mSnoozeCriteria;
        }

        public int getSuppressedVisualEffects() {
            return this.mSuppressedVisualEffects;
        }

        public int getUserSentiment() {
            return this.mUserSentiment;
        }

        @UnsupportedAppUsage
        public int getVisibilityOverride() {
            return this.mVisibilityOverride;
        }

        public boolean isAmbient() {
            return this.mIsAmbient;
        }

        public boolean isNoisy() {
            return this.mNoisy;
        }

        public boolean isSuspended() {
            return this.mHidden;
        }

        public boolean matchesInterruptionFilter() {
            return this.mMatchesInterruptionFilter;
        }

        public void populate(Ranking ranking) {
            this.populate(ranking.mKey, ranking.mRank, ranking.mMatchesInterruptionFilter, ranking.mVisibilityOverride, ranking.mSuppressedVisualEffects, ranking.mImportance, ranking.mImportanceExplanation, ranking.mOverrideGroupKey, ranking.mChannel, ranking.mOverridePeople, ranking.mSnoozeCriteria, ranking.mShowBadge, ranking.mUserSentiment, ranking.mHidden, ranking.mLastAudiblyAlertedMs, ranking.mNoisy, ranking.mSmartActions, ranking.mSmartReplies, ranking.mCanBubble);
        }

        @VisibleForTesting
        public void populate(String string2, int n, boolean bl, int n2, int n3, int n4, CharSequence charSequence, String string3, NotificationChannel notificationChannel, ArrayList<String> arrayList, ArrayList<SnoozeCriterion> arrayList2, boolean bl2, int n5, boolean bl3, long l, boolean bl4, ArrayList<Notification.Action> arrayList3, ArrayList<CharSequence> arrayList4, boolean bl5) {
            this.mKey = string2;
            this.mRank = n;
            boolean bl6 = n4 < 2;
            this.mIsAmbient = bl6;
            this.mMatchesInterruptionFilter = bl;
            this.mVisibilityOverride = n2;
            this.mSuppressedVisualEffects = n3;
            this.mImportance = n4;
            this.mImportanceExplanation = charSequence;
            this.mOverrideGroupKey = string3;
            this.mChannel = notificationChannel;
            this.mOverridePeople = arrayList;
            this.mSnoozeCriteria = arrayList2;
            this.mShowBadge = bl2;
            this.mUserSentiment = n5;
            this.mHidden = bl3;
            this.mLastAudiblyAlertedMs = l;
            this.mNoisy = bl4;
            this.mSmartActions = arrayList3;
            this.mSmartReplies = arrayList4;
            this.mCanBubble = bl5;
        }

        @VisibleForTesting
        public void writeToParcel(Parcel parcel, int n) {
            long l = parcel.dataPosition();
            parcel.writeInt(2);
            parcel.writeString(this.mKey);
            parcel.writeInt(this.mRank);
            parcel.writeBoolean(this.mIsAmbient);
            parcel.writeBoolean(this.mMatchesInterruptionFilter);
            parcel.writeInt(this.mVisibilityOverride);
            parcel.writeInt(this.mSuppressedVisualEffects);
            parcel.writeInt(this.mImportance);
            parcel.writeCharSequence(this.mImportanceExplanation);
            parcel.writeString(this.mOverrideGroupKey);
            parcel.writeParcelable(this.mChannel, n);
            parcel.writeStringList(this.mOverridePeople);
            parcel.writeTypedList(this.mSnoozeCriteria, n);
            parcel.writeBoolean(this.mShowBadge);
            parcel.writeInt(this.mUserSentiment);
            parcel.writeBoolean(this.mHidden);
            parcel.writeLong(this.mLastAudiblyAlertedMs);
            parcel.writeBoolean(this.mNoisy);
            parcel.writeTypedList(this.mSmartActions, n);
            parcel.writeCharSequenceList(this.mSmartReplies);
            parcel.writeBoolean(this.mCanBubble);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface UserSentiment {
        }

    }

    public static class RankingMap
    implements Parcelable {
        public static final Parcelable.Creator<RankingMap> CREATOR = new Parcelable.Creator<RankingMap>(){

            @Override
            public RankingMap createFromParcel(Parcel parcel) {
                return new RankingMap(parcel);
            }

            public RankingMap[] newArray(int n) {
                return new RankingMap[n];
            }
        };
        private ArrayList<String> mOrderedKeys = new ArrayList();
        private ArrayMap<String, Ranking> mRankings = new ArrayMap();

        private RankingMap(Parcel parcel) {
            this.getClass().getClassLoader();
            int n = parcel.readInt();
            this.mOrderedKeys.ensureCapacity(n);
            this.mRankings.ensureCapacity(n);
            for (int i = 0; i < n; ++i) {
                Ranking ranking = new Ranking(parcel);
                String string2 = ranking.getKey();
                this.mOrderedKeys.add(string2);
                this.mRankings.put(string2, ranking);
            }
        }

        public RankingMap(Ranking[] arrranking) {
            for (int i = 0; i < arrranking.length; ++i) {
                String string2 = arrranking[i].getKey();
                this.mOrderedKeys.add(string2);
                this.mRankings.put(string2, arrranking[i]);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (RankingMap)object;
                if (!this.mOrderedKeys.equals(((RankingMap)object).mOrderedKeys) || !this.mRankings.equals(((RankingMap)object).mRankings)) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public String[] getOrderedKeys() {
            return this.mOrderedKeys.toArray(new String[0]);
        }

        public boolean getRanking(String string2, Ranking ranking) {
            if (this.mRankings.containsKey(string2)) {
                ranking.populate(this.mRankings.get(string2));
                return true;
            }
            return false;
        }

        @VisibleForTesting
        public Ranking getRawRankingObject(String string2) {
            return this.mRankings.get(string2);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            int n2 = this.mOrderedKeys.size();
            parcel.writeInt(n2);
            for (int i = 0; i < n2; ++i) {
                this.mRankings.get(this.mOrderedKeys.get(i)).writeToParcel(parcel, n);
            }
        }

    }

}

