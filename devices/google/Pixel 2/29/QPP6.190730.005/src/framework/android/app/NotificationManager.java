/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.AutomaticZenRule;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.UserHandle;
import android.service.notification.Condition;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenPolicy;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NotificationManager {
    public static final String ACTION_APP_BLOCK_STATE_CHANGED = "android.app.action.APP_BLOCK_STATE_CHANGED";
    public static final String ACTION_AUTOMATIC_ZEN_RULE = "android.app.action.AUTOMATIC_ZEN_RULE";
    public static final String ACTION_EFFECTS_SUPPRESSOR_CHANGED = "android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED";
    public static final String ACTION_INTERRUPTION_FILTER_CHANGED = "android.app.action.INTERRUPTION_FILTER_CHANGED";
    public static final String ACTION_INTERRUPTION_FILTER_CHANGED_INTERNAL = "android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL";
    public static final String ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED = "android.app.action.NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED";
    public static final String ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED = "android.app.action.NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED";
    public static final String ACTION_NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED = "android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED";
    public static final String ACTION_NOTIFICATION_POLICY_CHANGED = "android.app.action.NOTIFICATION_POLICY_CHANGED";
    public static final String EXTRA_AUTOMATIC_RULE_ID = "android.app.extra.AUTOMATIC_RULE_ID";
    public static final String EXTRA_BLOCKED_STATE = "android.app.extra.BLOCKED_STATE";
    public static final String EXTRA_NOTIFICATION_CHANNEL_GROUP_ID = "android.app.extra.NOTIFICATION_CHANNEL_GROUP_ID";
    public static final String EXTRA_NOTIFICATION_CHANNEL_ID = "android.app.extra.NOTIFICATION_CHANNEL_ID";
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    public static final int INTERRUPTION_FILTER_ALARMS = 4;
    public static final int INTERRUPTION_FILTER_ALL = 1;
    public static final int INTERRUPTION_FILTER_NONE = 3;
    public static final int INTERRUPTION_FILTER_PRIORITY = 2;
    public static final int INTERRUPTION_FILTER_UNKNOWN = 0;
    public static final String META_DATA_AUTOMATIC_RULE_TYPE = "android.service.zen.automatic.ruleType";
    public static final String META_DATA_RULE_INSTANCE_LIMIT = "android.service.zen.automatic.ruleInstanceLimit";
    private static String TAG = "NotificationManager";
    public static final int VISIBILITY_NO_OVERRIDE = -1000;
    private static boolean localLOGV = false;
    @UnsupportedAppUsage
    private static INotificationManager sService;
    private Context mContext;

    @UnsupportedAppUsage
    NotificationManager(Context context, Handler handler) {
        this.mContext = context;
    }

    private static void checkRequired(String string2, Object object) {
        if (object != null) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" is required");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private void fixLegacySmallIcon(Notification notification, String string2) {
        if (notification.getSmallIcon() == null && notification.icon != 0) {
            notification.setSmallIcon(Icon.createWithResource(string2, notification.icon));
        }
    }

    private Notification fixNotification(Notification notification) {
        CharSequence charSequence = this.mContext.getPackageName();
        Notification.addFieldsFromContext(this.mContext, notification);
        if (notification.sound != null) {
            notification.sound = notification.sound.getCanonicalUri();
            if (StrictMode.vmFileUriExposureEnabled()) {
                notification.sound.checkFileUriExposed("Notification.sound");
            }
        }
        this.fixLegacySmallIcon(notification, (String)charSequence);
        if (this.mContext.getApplicationInfo().targetSdkVersion > 22 && notification.getSmallIcon() == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid notification (no valid small icon): ");
            ((StringBuilder)charSequence).append(notification);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        notification.reduceImageSizes(this.mContext);
        return Notification.Builder.maybeCloneStrippedForDelivery(notification, ((ActivityManager)this.mContext.getSystemService("activity")).isLowRamDevice(), this.mContext);
    }

    @UnsupportedAppUsage
    public static NotificationManager from(Context context) {
        return (NotificationManager)context.getSystemService("notification");
    }

    @UnsupportedAppUsage
    public static INotificationManager getService() {
        INotificationManager iNotificationManager = sService;
        if (iNotificationManager != null) {
            return iNotificationManager;
        }
        sService = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        return sService;
    }

    public static int zenModeFromInterruptionFilter(int n, int n2) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return n2;
                    }
                    return 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public static int zenModeToInterruptionFilter(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return 0;
                    }
                    return 4;
                }
                return 3;
            }
            return 2;
        }
        return 1;
    }

    public String addAutomaticZenRule(AutomaticZenRule object) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            object = iNotificationManager.addAutomaticZenRule((AutomaticZenRule)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void allowAssistantAdjustment(String string2) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            iNotificationManager.allowAssistantAdjustment(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean areBubblesAllowed() {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.areBubblesAllowed(this.mContext.getPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean areNotificationsEnabled() {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.areNotificationsEnabled(this.mContext.getPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean areNotificationsPaused() {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.isPackagePaused(this.mContext.getPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean canNotifyAsPackage(String string2) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.canNotifyAsPackage(this.mContext.getPackageName(), string2, this.mContext.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void cancel(int n) {
        this.cancel(null, n);
    }

    public void cancel(String string2, int n) {
        this.cancelAsUser(string2, n, this.mContext.getUser());
    }

    public void cancelAll() {
        INotificationManager iNotificationManager = NotificationManager.getService();
        String string2 = this.mContext.getPackageName();
        if (localLOGV) {
            String string3 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(": cancelAll()");
            Log.v(string3, stringBuilder.toString());
        }
        try {
            iNotificationManager.cancelAllNotifications(string2, this.mContext.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void cancelAsUser(String string2, int n, UserHandle userHandle) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        String string3 = this.mContext.getPackageName();
        if (localLOGV) {
            String string4 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string3);
            stringBuilder.append(": cancel(");
            stringBuilder.append(n);
            stringBuilder.append(")");
            Log.v(string4, stringBuilder.toString());
        }
        try {
            iNotificationManager.cancelNotificationWithTag(string3, string2, n, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void createNotificationChannel(NotificationChannel notificationChannel) {
        this.createNotificationChannels(Arrays.asList(notificationChannel));
    }

    public void createNotificationChannelGroup(NotificationChannelGroup notificationChannelGroup) {
        this.createNotificationChannelGroups(Arrays.asList(notificationChannelGroup));
    }

    public void createNotificationChannelGroups(List<NotificationChannelGroup> list) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            String string2 = this.mContext.getPackageName();
            ParceledListSlice<NotificationChannelGroup> parceledListSlice = new ParceledListSlice<NotificationChannelGroup>(list);
            iNotificationManager.createNotificationChannelGroups(string2, parceledListSlice);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void createNotificationChannels(List<NotificationChannel> list) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            String string2 = this.mContext.getPackageName();
            ParceledListSlice<NotificationChannel> parceledListSlice = new ParceledListSlice<NotificationChannel>(list);
            iNotificationManager.createNotificationChannels(string2, parceledListSlice);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void deleteNotificationChannel(String string2) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            iNotificationManager.deleteNotificationChannel(this.mContext.getPackageName(), string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void deleteNotificationChannelGroup(String string2) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            iNotificationManager.deleteNotificationChannelGroup(this.mContext.getPackageName(), string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void disallowAssistantAdjustment(String string2) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            iNotificationManager.disallowAssistantAdjustment(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public StatusBarNotification[] getActiveNotifications() {
        block3 : {
            StatusBarNotification[] arrstatusBarNotification = NotificationManager.getService();
            String string2 = this.mContext.getPackageName();
            try {
                arrstatusBarNotification = arrstatusBarNotification.getAppActiveNotifications(string2, this.mContext.getUserId());
                if (arrstatusBarNotification == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            arrstatusBarNotification = arrstatusBarNotification.getList();
            arrstatusBarNotification = arrstatusBarNotification.toArray(new StatusBarNotification[arrstatusBarNotification.size()]);
            return arrstatusBarNotification;
        }
        return new StatusBarNotification[0];
    }

    @SystemApi
    public List<String> getAllowedAssistantAdjustments() {
        Object object = NotificationManager.getService();
        try {
            object = object.getAllowedAssistantAdjustments(this.mContext.getOpPackageName());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public ComponentName getAllowedNotificationAssistant() {
        Object object = NotificationManager.getService();
        try {
            object = object.getAllowedNotificationAssistant();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public AutomaticZenRule getAutomaticZenRule(String object) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            object = iNotificationManager.getAutomaticZenRule((String)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * WARNING - void declaration
     */
    public Map<String, AutomaticZenRule> getAutomaticZenRules() {
        void var1_4;
        block19 : {
            Iterator<ZenModeConfig.ZenRule> iterator;
            INotificationManager iNotificationManager;
            INotificationManager iNotificationManager2 = iNotificationManager = NotificationManager.getService();
            List<ZenModeConfig.ZenRule> list = iNotificationManager.getZenRules();
            iNotificationManager2 = iNotificationManager;
            iNotificationManager2 = iNotificationManager;
            HashMap<String, AutomaticZenRule> hashMap = new HashMap<String, AutomaticZenRule>();
            iNotificationManager2 = iNotificationManager;
            try {
                iterator = list.iterator();
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            do {
                iNotificationManager2 = iNotificationManager;
                if (!iterator.hasNext()) break;
                iNotificationManager2 = iNotificationManager;
                ZenModeConfig.ZenRule zenRule = iterator.next();
                iNotificationManager2 = iNotificationManager;
                String string2 = zenRule.id;
                iNotificationManager2 = iNotificationManager;
                iNotificationManager2 = iNotificationManager;
                String string3 = zenRule.name;
                iNotificationManager2 = iNotificationManager;
                ComponentName componentName = zenRule.component;
                iNotificationManager2 = iNotificationManager;
                ComponentName componentName2 = zenRule.configurationActivity;
                iNotificationManager2 = iNotificationManager;
                Uri uri = zenRule.conditionId;
                iNotificationManager2 = iNotificationManager;
                ZenPolicy zenPolicy = zenRule.zenPolicy;
                iNotificationManager2 = iNotificationManager;
                int n = NotificationManager.zenModeToInterruptionFilter(zenRule.zenMode);
                iNotificationManager2 = iNotificationManager;
                boolean bl = zenRule.enabled;
                try {
                    AutomaticZenRule automaticZenRule = new AutomaticZenRule(string3, componentName, componentName2, uri, zenPolicy, n, bl, zenRule.creationTime);
                    hashMap.put(string2, automaticZenRule);
                }
                catch (RemoteException remoteException) {
                    break block19;
                }
            } while (true);
            return hashMap;
        }
        throw var1_4.rethrowFromSystemServer();
    }

    public Policy getConsolidatedNotificationPolicy() {
        Object object = NotificationManager.getService();
        try {
            object = object.getConsolidatedNotificationPolicy();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public final int getCurrentInterruptionFilter() {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            int n = NotificationManager.zenModeToInterruptionFilter(iNotificationManager.getZenMode());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ComponentName getEffectsSuppressor() {
        Object object = NotificationManager.getService();
        try {
            object = object.getEffectsSuppressor();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<String> getEnabledNotificationListenerPackages() {
        Object object = NotificationManager.getService();
        try {
            object = object.getEnabledNotificationListenerPackages();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<ComponentName> getEnabledNotificationListeners(int n) {
        Object object = NotificationManager.getService();
        try {
            object = object.getEnabledNotificationListeners(n);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getImportance() {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            int n = iNotificationManager.getPackageImportance(this.mContext.getPackageName());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public NotificationChannel getNotificationChannel(String object) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            object = iNotificationManager.getNotificationChannel(this.mContext.getOpPackageName(), this.mContext.getUserId(), this.mContext.getPackageName(), (String)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public NotificationChannelGroup getNotificationChannelGroup(String object) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            object = iNotificationManager.getNotificationChannelGroup(this.mContext.getPackageName(), (String)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<NotificationChannelGroup> getNotificationChannelGroups() {
        block3 : {
            Object object = NotificationManager.getService();
            try {
                object = object.getNotificationChannelGroups(this.mContext.getPackageName());
                if (object == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            object = ((ParceledListSlice)object).getList();
            return object;
        }
        return new ArrayList<NotificationChannelGroup>();
    }

    public List<NotificationChannel> getNotificationChannels() {
        Object object = NotificationManager.getService();
        try {
            object = object.getNotificationChannels(this.mContext.getOpPackageName(), this.mContext.getPackageName(), this.mContext.getUserId()).getList();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String getNotificationDelegate() {
        INotificationManager iNotificationManager = NotificationManager.getService();
        String string2 = this.mContext.getPackageName();
        try {
            string2 = iNotificationManager.getNotificationDelegate(string2);
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Policy getNotificationPolicy() {
        Object object = NotificationManager.getService();
        try {
            object = object.getNotificationPolicy(this.mContext.getOpPackageName());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getRuleInstanceCount(ComponentName componentName) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            int n = iNotificationManager.getRuleInstanceCount(componentName);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getZenMode() {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            int n = iNotificationManager.getZenMode();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public ZenModeConfig getZenModeConfig() {
        Object object = NotificationManager.getService();
        try {
            object = object.getZenModeConfig();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isNotificationAssistantAccessGranted(ComponentName componentName) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.isNotificationAssistantAccessGranted(componentName);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationListenerAccessGranted(ComponentName componentName) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.isNotificationListenerAccessGranted(componentName);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationPolicyAccessGranted() {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.isNotificationPolicyAccessGranted(this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationPolicyAccessGrantedForPackage(String string2) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.isNotificationPolicyAccessGrantedForPackage(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isSystemConditionProviderEnabled(String string2) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.isSystemConditionProviderEnabled(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean matchesCallFilter(Bundle bundle) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.matchesCallFilter(bundle);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void notify(int n, Notification notification) {
        this.notify(null, n, notification);
    }

    public void notify(String string2, int n, Notification notification) {
        this.notifyAsUser(string2, n, notification, this.mContext.getUser());
    }

    public void notifyAsPackage(String string2, String string3, int n, Notification notification) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        String string4 = this.mContext.getPackageName();
        try {
            if (localLOGV) {
                String string5 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string4);
                stringBuilder.append(": notify(");
                stringBuilder.append(n);
                stringBuilder.append(", ");
                stringBuilder.append(notification);
                stringBuilder.append(")");
                Log.v(string5, stringBuilder.toString());
            }
            iNotificationManager.enqueueNotificationWithTag(string2, string4, string3, n, this.fixNotification(notification), this.mContext.getUser().getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void notifyAsUser(String string2, int n, Notification notification, UserHandle userHandle) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        String string3 = this.mContext.getPackageName();
        try {
            if (localLOGV) {
                String string4 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string3);
                stringBuilder.append(": notify(");
                stringBuilder.append(n);
                stringBuilder.append(", ");
                stringBuilder.append(notification);
                stringBuilder.append(")");
                Log.v(string4, stringBuilder.toString());
            }
            iNotificationManager.enqueueNotificationWithTag(string3, this.mContext.getOpPackageName(), string2, n, this.fixNotification(notification), userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean removeAutomaticZenRule(String string2) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.removeAutomaticZenRule(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean removeAutomaticZenRules(String string2) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.removeAutomaticZenRules(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setAutomaticZenRuleState(String string2, Condition condition) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            iNotificationManager.setAutomaticZenRuleState(string2, condition);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public final void setInterruptionFilter(int n) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            iNotificationManager.setInterruptionFilter(this.mContext.getOpPackageName(), n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setNotificationAssistantAccessGranted(ComponentName componentName, boolean bl) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            iNotificationManager.setNotificationAssistantAccessGranted(componentName, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setNotificationDelegate(String string2) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        String string3 = this.mContext.getPackageName();
        if (localLOGV) {
            String string4 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string3);
            stringBuilder.append(": cancelAll()");
            Log.v(string4, stringBuilder.toString());
        }
        try {
            iNotificationManager.setNotificationDelegate(string3, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setNotificationListenerAccessGranted(ComponentName componentName, boolean bl) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            iNotificationManager.setNotificationListenerAccessGranted(componentName, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setNotificationListenerAccessGrantedForUser(ComponentName componentName, int n, boolean bl) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            iNotificationManager.setNotificationListenerAccessGrantedForUser(componentName, n, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setNotificationPolicy(Policy policy) {
        NotificationManager.checkRequired("policy", policy);
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            iNotificationManager.setNotificationPolicy(this.mContext.getOpPackageName(), policy);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setNotificationPolicyAccessGranted(String string2, boolean bl) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            iNotificationManager.setNotificationPolicyAccessGranted(string2, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void setZenMode(int n, Uri uri, String string2) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            iNotificationManager.setZenMode(n, uri, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean shouldHideSilentStatusBarIcons() {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.shouldHideSilentStatusIcons(this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean updateAutomaticZenRule(String string2, AutomaticZenRule automaticZenRule) {
        INotificationManager iNotificationManager = NotificationManager.getService();
        try {
            boolean bl = iNotificationManager.updateAutomaticZenRule(string2, automaticZenRule);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Importance {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InterruptionFilter {
    }

    public static class Policy
    implements Parcelable {
        public static final int[] ALL_PRIORITY_CATEGORIES = new int[]{32, 64, 128, 1, 2, 4, 8, 16};
        private static final int[] ALL_SUPPRESSED_EFFECTS = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256};
        public static final Parcelable.Creator<Policy> CREATOR;
        public static final int PRIORITY_CATEGORY_ALARMS = 32;
        public static final int PRIORITY_CATEGORY_CALLS = 8;
        public static final int PRIORITY_CATEGORY_EVENTS = 2;
        public static final int PRIORITY_CATEGORY_MEDIA = 64;
        public static final int PRIORITY_CATEGORY_MESSAGES = 4;
        public static final int PRIORITY_CATEGORY_REMINDERS = 1;
        public static final int PRIORITY_CATEGORY_REPEAT_CALLERS = 16;
        public static final int PRIORITY_CATEGORY_SYSTEM = 128;
        public static final int PRIORITY_SENDERS_ANY = 0;
        public static final int PRIORITY_SENDERS_CONTACTS = 1;
        public static final int PRIORITY_SENDERS_STARRED = 2;
        private static final int[] SCREEN_OFF_SUPPRESSED_EFFECTS;
        private static final int[] SCREEN_ON_SUPPRESSED_EFFECTS;
        public static final int STATE_CHANNELS_BYPASSING_DND = 1;
        public static final int STATE_UNSET = -1;
        public static final int SUPPRESSED_EFFECTS_UNSET = -1;
        public static final int SUPPRESSED_EFFECT_AMBIENT = 128;
        public static final int SUPPRESSED_EFFECT_BADGE = 64;
        public static final int SUPPRESSED_EFFECT_FULL_SCREEN_INTENT = 4;
        public static final int SUPPRESSED_EFFECT_LIGHTS = 8;
        public static final int SUPPRESSED_EFFECT_NOTIFICATION_LIST = 256;
        public static final int SUPPRESSED_EFFECT_PEEK = 16;
        @Deprecated
        public static final int SUPPRESSED_EFFECT_SCREEN_OFF = 1;
        @Deprecated
        public static final int SUPPRESSED_EFFECT_SCREEN_ON = 2;
        public static final int SUPPRESSED_EFFECT_STATUS_BAR = 32;
        public final int priorityCallSenders;
        public final int priorityCategories;
        public final int priorityMessageSenders;
        public final int state;
        public final int suppressedVisualEffects;

        static {
            SCREEN_OFF_SUPPRESSED_EFFECTS = new int[]{1, 4, 8, 128};
            SCREEN_ON_SUPPRESSED_EFFECTS = new int[]{2, 16, 32, 64, 256};
            CREATOR = new Parcelable.Creator<Policy>(){

                @Override
                public Policy createFromParcel(Parcel parcel) {
                    return new Policy(parcel);
                }

                public Policy[] newArray(int n) {
                    return new Policy[n];
                }
            };
        }

        public Policy(int n, int n2, int n3) {
            this(n, n2, n3, -1, -1);
        }

        public Policy(int n, int n2, int n3, int n4) {
            this.priorityCategories = n;
            this.priorityCallSenders = n2;
            this.priorityMessageSenders = n3;
            this.suppressedVisualEffects = n4;
            this.state = -1;
        }

        public Policy(int n, int n2, int n3, int n4, int n5) {
            this.priorityCategories = n;
            this.priorityCallSenders = n2;
            this.priorityMessageSenders = n3;
            this.suppressedVisualEffects = n4;
            this.state = n5;
        }

        public Policy(Parcel parcel) {
            this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public static boolean areAllVisualEffectsSuppressed(int n) {
            int[] arrn;
            for (int i = 0; i < (arrn = ALL_SUPPRESSED_EFFECTS).length; ++i) {
                if ((n & arrn[i]) != 0) continue;
                return false;
            }
            return true;
        }

        private static void bitwiseToProtoEnum(ProtoOutputStream protoOutputStream, long l, int n) {
            int n2 = 1;
            while (n > 0) {
                if ((n & 1) == 1) {
                    protoOutputStream.write(l, n2);
                }
                ++n2;
                n >>>= 1;
            }
        }

        private static String effectToString(int n) {
            if (n != -1) {
                if (n != 4) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 32) {
                                if (n != 64) {
                                    if (n != 128) {
                                        if (n != 256) {
                                            if (n != 1) {
                                                if (n != 2) {
                                                    StringBuilder stringBuilder = new StringBuilder();
                                                    stringBuilder.append("UNKNOWN_");
                                                    stringBuilder.append(n);
                                                    return stringBuilder.toString();
                                                }
                                                return "SUPPRESSED_EFFECT_SCREEN_ON";
                                            }
                                            return "SUPPRESSED_EFFECT_SCREEN_OFF";
                                        }
                                        return "SUPPRESSED_EFFECT_NOTIFICATION_LIST";
                                    }
                                    return "SUPPRESSED_EFFECT_AMBIENT";
                                }
                                return "SUPPRESSED_EFFECT_BADGE";
                            }
                            return "SUPPRESSED_EFFECT_STATUS_BAR";
                        }
                        return "SUPPRESSED_EFFECT_PEEK";
                    }
                    return "SUPPRESSED_EFFECT_LIGHTS";
                }
                return "SUPPRESSED_EFFECT_FULL_SCREEN_INTENT";
            }
            return "SUPPRESSED_EFFECTS_UNSET";
        }

        public static int getAllSuppressedVisualEffects() {
            int[] arrn;
            int n = 0;
            for (int i = 0; i < (arrn = ALL_SUPPRESSED_EFFECTS).length; ++i) {
                n |= arrn[i];
            }
            return n;
        }

        public static String priorityCategoriesToString(int n) {
            int[] arrn;
            if (n == 0) {
                return "";
            }
            StringBuilder stringBuilder = new StringBuilder();
            int n2 = 0;
            int n3 = n;
            for (n = n2; n < (arrn = ALL_PRIORITY_CATEGORIES).length; ++n) {
                n2 = arrn[n];
                if ((n3 & n2) != 0) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(',');
                    }
                    stringBuilder.append(Policy.priorityCategoryToString(n2));
                }
                n3 &= n2;
            }
            if (n3 != 0) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append("PRIORITY_CATEGORY_UNKNOWN_");
                stringBuilder.append(n3);
            }
            return stringBuilder.toString();
        }

        private static String priorityCategoryToString(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 4) {
                        if (n != 8) {
                            if (n != 16) {
                                if (n != 32) {
                                    if (n != 64) {
                                        if (n != 128) {
                                            StringBuilder stringBuilder = new StringBuilder();
                                            stringBuilder.append("PRIORITY_CATEGORY_UNKNOWN_");
                                            stringBuilder.append(n);
                                            return stringBuilder.toString();
                                        }
                                        return "PRIORITY_CATEGORY_SYSTEM";
                                    }
                                    return "PRIORITY_CATEGORY_MEDIA";
                                }
                                return "PRIORITY_CATEGORY_ALARMS";
                            }
                            return "PRIORITY_CATEGORY_REPEAT_CALLERS";
                        }
                        return "PRIORITY_CATEGORY_CALLS";
                    }
                    return "PRIORITY_CATEGORY_MESSAGES";
                }
                return "PRIORITY_CATEGORY_EVENTS";
            }
            return "PRIORITY_CATEGORY_REMINDERS";
        }

        public static String prioritySendersToString(int n) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("PRIORITY_SENDERS_UNKNOWN_");
                        stringBuilder.append(n);
                        return stringBuilder.toString();
                    }
                    return "PRIORITY_SENDERS_STARRED";
                }
                return "PRIORITY_SENDERS_CONTACTS";
            }
            return "PRIORITY_SENDERS_ANY";
        }

        public static String suppressedEffectsToString(int n) {
            int[] arrn;
            if (n <= 0) {
                return "";
            }
            StringBuilder stringBuilder = new StringBuilder();
            int n2 = 0;
            int n3 = n;
            for (n = n2; n < (arrn = ALL_SUPPRESSED_EFFECTS).length; ++n) {
                n2 = arrn[n];
                if ((n3 & n2) != 0) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(',');
                    }
                    stringBuilder.append(Policy.effectToString(n2));
                }
                n3 &= n2;
            }
            if (n3 != 0) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append("UNKNOWN_");
                stringBuilder.append(n3);
            }
            return stringBuilder.toString();
        }

        private boolean suppressedVisualEffectsEqual(int n, int n2) {
            boolean bl = true;
            if (n == n2) {
                return true;
            }
            int n3 = n;
            if ((n & 2) != 0) {
                n3 = n | 16;
            }
            n = n3;
            if ((n3 & 1) != 0) {
                n = n3 | 4 | 8 | 128;
            }
            n3 = n2;
            if ((n2 & 2) != 0) {
                n3 = n2 | 16;
            }
            n2 = n3;
            if ((n3 & 1) != 0) {
                n2 = n3 | 4 | 8 | 128;
            }
            if ((n & 2) != (n2 & 2) && ((n3 = (n & 2) != 0 ? n2 : n) & 16) == 0) {
                return false;
            }
            if ((n & 1) != (n2 & 1) && (((n3 = (n & 1) != 0 ? n2 : n) & 4) == 0 || (n3 & 8) == 0 || (n3 & 128) == 0)) {
                return false;
            }
            if ((n & -3 & -2) != (n2 & -3 & -2)) {
                bl = false;
            }
            return bl;
        }

        private static int toggleEffects(int n, int[] arrn, boolean bl) {
            for (int i = 0; i < arrn.length; ++i) {
                int n2 = arrn[i];
                if (bl) {
                    n |= n2;
                    continue;
                }
                n &= n2;
            }
            return n;
        }

        public boolean allowAlarms() {
            boolean bl = (this.priorityCategories & 32) != 0;
            return bl;
        }

        public boolean allowCalls() {
            boolean bl = (this.priorityCategories & 8) != 0;
            return bl;
        }

        public int allowCallsFrom() {
            return this.priorityCallSenders;
        }

        public boolean allowEvents() {
            boolean bl = (this.priorityCategories & 2) != 0;
            return bl;
        }

        public boolean allowMedia() {
            boolean bl = (this.priorityCategories & 64) != 0;
            return bl;
        }

        public boolean allowMessages() {
            boolean bl = (this.priorityCategories & 4) != 0;
            return bl;
        }

        public int allowMessagesFrom() {
            return this.priorityMessageSenders;
        }

        public boolean allowReminders() {
            int n = this.priorityCategories;
            boolean bl = true;
            if ((n & 1) == 0) {
                bl = false;
            }
            return bl;
        }

        public boolean allowRepeatCallers() {
            boolean bl = (this.priorityCategories & 16) != 0;
            return bl;
        }

        public boolean allowSystem() {
            boolean bl = (this.priorityCategories & 128) != 0;
            return bl;
        }

        public Policy copy() {
            Parcel parcel = Parcel.obtain();
            try {
                this.writeToParcel(parcel, 0);
                parcel.setDataPosition(0);
                Policy policy = new Policy(parcel);
                return policy;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (!(object instanceof Policy)) {
                return false;
            }
            boolean bl = true;
            if (object == this) {
                return true;
            }
            object = (Policy)object;
            if (((Policy)object).priorityCategories != this.priorityCategories || ((Policy)object).priorityCallSenders != this.priorityCallSenders || ((Policy)object).priorityMessageSenders != this.priorityMessageSenders || !this.suppressedVisualEffectsEqual(this.suppressedVisualEffects, ((Policy)object).suppressedVisualEffects)) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            return Objects.hash(this.priorityCategories, this.priorityCallSenders, this.priorityMessageSenders, this.suppressedVisualEffects);
        }

        public boolean showAmbient() {
            boolean bl = (this.suppressedVisualEffects & 128) == 0;
            return bl;
        }

        public boolean showBadges() {
            boolean bl = (this.suppressedVisualEffects & 64) == 0;
            return bl;
        }

        public boolean showFullScreenIntents() {
            boolean bl = (this.suppressedVisualEffects & 4) == 0;
            return bl;
        }

        public boolean showInNotificationList() {
            boolean bl = (this.suppressedVisualEffects & 256) == 0;
            return bl;
        }

        public boolean showLights() {
            boolean bl = (this.suppressedVisualEffects & 8) == 0;
            return bl;
        }

        public boolean showPeeking() {
            boolean bl = (this.suppressedVisualEffects & 16) == 0;
            return bl;
        }

        public boolean showStatusBarIcons() {
            boolean bl = (this.suppressedVisualEffects & 32) == 0;
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NotificationManager.Policy[priorityCategories=");
            stringBuilder.append(Policy.priorityCategoriesToString(this.priorityCategories));
            stringBuilder.append(",priorityCallSenders=");
            stringBuilder.append(Policy.prioritySendersToString(this.priorityCallSenders));
            stringBuilder.append(",priorityMessageSenders=");
            stringBuilder.append(Policy.prioritySendersToString(this.priorityMessageSenders));
            stringBuilder.append(",suppressedVisualEffects=");
            stringBuilder.append(Policy.suppressedEffectsToString(this.suppressedVisualEffects));
            stringBuilder.append(",areChannelsBypassingDnd=");
            String string2 = (this.state & 1) != 0 ? "true" : "false";
            stringBuilder.append(string2);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.priorityCategories);
            parcel.writeInt(this.priorityCallSenders);
            parcel.writeInt(this.priorityMessageSenders);
            parcel.writeInt(this.suppressedVisualEffects);
            parcel.writeInt(this.state);
        }

        public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
            l = protoOutputStream.start(l);
            Policy.bitwiseToProtoEnum(protoOutputStream, 2259152797697L, this.priorityCategories);
            protoOutputStream.write(1159641169922L, this.priorityCallSenders);
            protoOutputStream.write(1159641169923L, this.priorityMessageSenders);
            Policy.bitwiseToProtoEnum(protoOutputStream, 2259152797700L, this.suppressedVisualEffects);
            protoOutputStream.end(l);
        }

    }

}

