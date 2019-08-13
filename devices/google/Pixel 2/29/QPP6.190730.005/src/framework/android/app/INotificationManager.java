/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.AutomaticZenRule;
import android.app.ITransientNotification;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.Adjustment;
import android.service.notification.Condition;
import android.service.notification.IConditionProvider;
import android.service.notification.INotificationListener;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenModeConfig;
import java.util.ArrayList;
import java.util.List;

public interface INotificationManager
extends IInterface {
    public String addAutomaticZenRule(AutomaticZenRule var1) throws RemoteException;

    public void allowAssistantAdjustment(String var1) throws RemoteException;

    public void applyAdjustmentFromAssistant(INotificationListener var1, Adjustment var2) throws RemoteException;

    public void applyAdjustmentsFromAssistant(INotificationListener var1, List<Adjustment> var2) throws RemoteException;

    public void applyEnqueuedAdjustmentFromAssistant(INotificationListener var1, Adjustment var2) throws RemoteException;

    public void applyRestore(byte[] var1, int var2) throws RemoteException;

    public boolean areBubblesAllowed(String var1) throws RemoteException;

    public boolean areBubblesAllowedForPackage(String var1, int var2) throws RemoteException;

    public boolean areChannelsBypassingDnd() throws RemoteException;

    public boolean areNotificationsEnabled(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean areNotificationsEnabledForPackage(String var1, int var2) throws RemoteException;

    public boolean canNotifyAsPackage(String var1, String var2, int var3) throws RemoteException;

    public boolean canShowBadge(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void cancelAllNotifications(String var1, int var2) throws RemoteException;

    public void cancelNotificationFromListener(INotificationListener var1, String var2, String var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public void cancelNotificationWithTag(String var1, String var2, int var3, int var4) throws RemoteException;

    public void cancelNotificationsFromListener(INotificationListener var1, String[] var2) throws RemoteException;

    @UnsupportedAppUsage
    public void cancelToast(String var1, ITransientNotification var2) throws RemoteException;

    public void clearData(String var1, int var2, boolean var3) throws RemoteException;

    public void clearRequestedListenerHints(INotificationListener var1) throws RemoteException;

    public void createNotificationChannelGroups(String var1, ParceledListSlice var2) throws RemoteException;

    public void createNotificationChannels(String var1, ParceledListSlice var2) throws RemoteException;

    public void createNotificationChannelsForPackage(String var1, int var2, ParceledListSlice var3) throws RemoteException;

    public void deleteNotificationChannel(String var1, String var2) throws RemoteException;

    public void deleteNotificationChannelGroup(String var1, String var2) throws RemoteException;

    public void disallowAssistantAdjustment(String var1) throws RemoteException;

    public void enqueueNotificationWithTag(String var1, String var2, String var3, int var4, Notification var5, int var6) throws RemoteException;

    @UnsupportedAppUsage
    public void enqueueToast(String var1, ITransientNotification var2, int var3, int var4) throws RemoteException;

    public void finishToken(String var1, ITransientNotification var2) throws RemoteException;

    @UnsupportedAppUsage
    public StatusBarNotification[] getActiveNotifications(String var1) throws RemoteException;

    public ParceledListSlice getActiveNotificationsFromListener(INotificationListener var1, String[] var2, int var3) throws RemoteException;

    public List<String> getAllowedAssistantAdjustments(String var1) throws RemoteException;

    public ComponentName getAllowedNotificationAssistant() throws RemoteException;

    public ComponentName getAllowedNotificationAssistantForUser(int var1) throws RemoteException;

    public ParceledListSlice getAppActiveNotifications(String var1, int var2) throws RemoteException;

    public int getAppsBypassingDndCount(int var1) throws RemoteException;

    public AutomaticZenRule getAutomaticZenRule(String var1) throws RemoteException;

    public byte[] getBackupPayload(int var1) throws RemoteException;

    public int getBlockedAppCount(int var1) throws RemoteException;

    public int getBlockedChannelCount(String var1, int var2) throws RemoteException;

    public NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException;

    public int getDeletedChannelCount(String var1, int var2) throws RemoteException;

    public ComponentName getEffectsSuppressor() throws RemoteException;

    public List<String> getEnabledNotificationListenerPackages() throws RemoteException;

    public List<ComponentName> getEnabledNotificationListeners(int var1) throws RemoteException;

    public int getHintsFromListener(INotificationListener var1) throws RemoteException;

    @UnsupportedAppUsage
    public StatusBarNotification[] getHistoricalNotifications(String var1, int var2) throws RemoteException;

    public int getInterruptionFilterFromListener(INotificationListener var1) throws RemoteException;

    public NotificationChannel getNotificationChannel(String var1, int var2, String var3, String var4) throws RemoteException;

    public NotificationChannel getNotificationChannelForPackage(String var1, int var2, String var3, boolean var4) throws RemoteException;

    public NotificationChannelGroup getNotificationChannelGroup(String var1, String var2) throws RemoteException;

    public NotificationChannelGroup getNotificationChannelGroupForPackage(String var1, String var2, int var3) throws RemoteException;

    public ParceledListSlice getNotificationChannelGroups(String var1) throws RemoteException;

    public ParceledListSlice getNotificationChannelGroupsForPackage(String var1, int var2, boolean var3) throws RemoteException;

    public ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(INotificationListener var1, String var2, UserHandle var3) throws RemoteException;

    public ParceledListSlice getNotificationChannels(String var1, String var2, int var3) throws RemoteException;

    public ParceledListSlice getNotificationChannelsBypassingDnd(String var1, int var2) throws RemoteException;

    public ParceledListSlice getNotificationChannelsForPackage(String var1, int var2, boolean var3) throws RemoteException;

    public ParceledListSlice getNotificationChannelsFromPrivilegedListener(INotificationListener var1, String var2, UserHandle var3) throws RemoteException;

    public String getNotificationDelegate(String var1) throws RemoteException;

    public NotificationManager.Policy getNotificationPolicy(String var1) throws RemoteException;

    public int getNumNotificationChannelsForPackage(String var1, int var2, boolean var3) throws RemoteException;

    public int getPackageImportance(String var1) throws RemoteException;

    public NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(String var1, int var2, String var3, boolean var4) throws RemoteException;

    public boolean getPrivateNotificationsAllowed() throws RemoteException;

    public int getRuleInstanceCount(ComponentName var1) throws RemoteException;

    public ParceledListSlice getSnoozedNotificationsFromListener(INotificationListener var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public int getZenMode() throws RemoteException;

    @UnsupportedAppUsage
    public ZenModeConfig getZenModeConfig() throws RemoteException;

    public List<ZenModeConfig.ZenRule> getZenRules() throws RemoteException;

    public boolean hasUserApprovedBubblesForPackage(String var1, int var2) throws RemoteException;

    public boolean isNotificationAssistantAccessGranted(ComponentName var1) throws RemoteException;

    public boolean isNotificationListenerAccessGranted(ComponentName var1) throws RemoteException;

    public boolean isNotificationListenerAccessGrantedForUser(ComponentName var1, int var2) throws RemoteException;

    public boolean isNotificationPolicyAccessGranted(String var1) throws RemoteException;

    public boolean isNotificationPolicyAccessGrantedForPackage(String var1) throws RemoteException;

    public boolean isPackagePaused(String var1) throws RemoteException;

    public boolean isSystemConditionProviderEnabled(String var1) throws RemoteException;

    public boolean matchesCallFilter(Bundle var1) throws RemoteException;

    public void notifyConditions(String var1, IConditionProvider var2, Condition[] var3) throws RemoteException;

    public boolean onlyHasDefaultChannel(String var1, int var2) throws RemoteException;

    public void registerListener(INotificationListener var1, ComponentName var2, int var3) throws RemoteException;

    public boolean removeAutomaticZenRule(String var1) throws RemoteException;

    public boolean removeAutomaticZenRules(String var1) throws RemoteException;

    public void requestBindListener(ComponentName var1) throws RemoteException;

    public void requestBindProvider(ComponentName var1) throws RemoteException;

    public void requestHintsFromListener(INotificationListener var1, int var2) throws RemoteException;

    public void requestInterruptionFilterFromListener(INotificationListener var1, int var2) throws RemoteException;

    public void requestUnbindListener(INotificationListener var1) throws RemoteException;

    public void requestUnbindProvider(IConditionProvider var1) throws RemoteException;

    public void setAutomaticZenRuleState(String var1, Condition var2) throws RemoteException;

    public void setBubblesAllowed(String var1, int var2, boolean var3) throws RemoteException;

    public void setHideSilentStatusIcons(boolean var1) throws RemoteException;

    public void setInterruptionFilter(String var1, int var2) throws RemoteException;

    public void setNotificationAssistantAccessGranted(ComponentName var1, boolean var2) throws RemoteException;

    public void setNotificationAssistantAccessGrantedForUser(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setNotificationDelegate(String var1, String var2) throws RemoteException;

    public void setNotificationListenerAccessGranted(ComponentName var1, boolean var2) throws RemoteException;

    public void setNotificationListenerAccessGrantedForUser(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setNotificationPolicy(String var1, NotificationManager.Policy var2) throws RemoteException;

    public void setNotificationPolicyAccessGranted(String var1, boolean var2) throws RemoteException;

    public void setNotificationPolicyAccessGrantedForUser(String var1, int var2, boolean var3) throws RemoteException;

    public void setNotificationsEnabledForPackage(String var1, int var2, boolean var3) throws RemoteException;

    public void setNotificationsEnabledWithImportanceLockForPackage(String var1, int var2, boolean var3) throws RemoteException;

    public void setNotificationsShownFromListener(INotificationListener var1, String[] var2) throws RemoteException;

    public void setOnNotificationPostedTrimFromListener(INotificationListener var1, int var2) throws RemoteException;

    public void setPrivateNotificationsAllowed(boolean var1) throws RemoteException;

    public void setShowBadge(String var1, int var2, boolean var3) throws RemoteException;

    public void setZenMode(int var1, Uri var2, String var3) throws RemoteException;

    public boolean shouldHideSilentStatusIcons(String var1) throws RemoteException;

    public void snoozeNotificationUntilContextFromListener(INotificationListener var1, String var2, String var3) throws RemoteException;

    public void snoozeNotificationUntilFromListener(INotificationListener var1, String var2, long var3) throws RemoteException;

    public void unregisterListener(INotificationListener var1, int var2) throws RemoteException;

    public void unsnoozeNotificationFromAssistant(INotificationListener var1, String var2) throws RemoteException;

    public boolean updateAutomaticZenRule(String var1, AutomaticZenRule var2) throws RemoteException;

    public void updateNotificationChannelForPackage(String var1, int var2, NotificationChannel var3) throws RemoteException;

    public void updateNotificationChannelFromPrivilegedListener(INotificationListener var1, String var2, UserHandle var3, NotificationChannel var4) throws RemoteException;

    public void updateNotificationChannelGroupForPackage(String var1, int var2, NotificationChannelGroup var3) throws RemoteException;

    public void updateNotificationChannelGroupFromPrivilegedListener(INotificationListener var1, String var2, UserHandle var3, NotificationChannelGroup var4) throws RemoteException;

    public static class Default
    implements INotificationManager {
        @Override
        public String addAutomaticZenRule(AutomaticZenRule automaticZenRule) throws RemoteException {
            return null;
        }

        @Override
        public void allowAssistantAdjustment(String string2) throws RemoteException {
        }

        @Override
        public void applyAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException {
        }

        @Override
        public void applyAdjustmentsFromAssistant(INotificationListener iNotificationListener, List<Adjustment> list) throws RemoteException {
        }

        @Override
        public void applyEnqueuedAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException {
        }

        @Override
        public void applyRestore(byte[] arrby, int n) throws RemoteException {
        }

        @Override
        public boolean areBubblesAllowed(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean areBubblesAllowedForPackage(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean areChannelsBypassingDnd() throws RemoteException {
            return false;
        }

        @Override
        public boolean areNotificationsEnabled(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean areNotificationsEnabledForPackage(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean canNotifyAsPackage(String string2, String string3, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean canShowBadge(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public void cancelAllNotifications(String string2, int n) throws RemoteException {
        }

        @Override
        public void cancelNotificationFromListener(INotificationListener iNotificationListener, String string2, String string3, int n) throws RemoteException {
        }

        @Override
        public void cancelNotificationWithTag(String string2, String string3, int n, int n2) throws RemoteException {
        }

        @Override
        public void cancelNotificationsFromListener(INotificationListener iNotificationListener, String[] arrstring) throws RemoteException {
        }

        @Override
        public void cancelToast(String string2, ITransientNotification iTransientNotification) throws RemoteException {
        }

        @Override
        public void clearData(String string2, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void clearRequestedListenerHints(INotificationListener iNotificationListener) throws RemoteException {
        }

        @Override
        public void createNotificationChannelGroups(String string2, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override
        public void createNotificationChannels(String string2, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override
        public void createNotificationChannelsForPackage(String string2, int n, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override
        public void deleteNotificationChannel(String string2, String string3) throws RemoteException {
        }

        @Override
        public void deleteNotificationChannelGroup(String string2, String string3) throws RemoteException {
        }

        @Override
        public void disallowAssistantAdjustment(String string2) throws RemoteException {
        }

        @Override
        public void enqueueNotificationWithTag(String string2, String string3, String string4, int n, Notification notification, int n2) throws RemoteException {
        }

        @Override
        public void enqueueToast(String string2, ITransientNotification iTransientNotification, int n, int n2) throws RemoteException {
        }

        @Override
        public void finishToken(String string2, ITransientNotification iTransientNotification) throws RemoteException {
        }

        @Override
        public StatusBarNotification[] getActiveNotifications(String string2) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getActiveNotificationsFromListener(INotificationListener iNotificationListener, String[] arrstring, int n) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getAllowedAssistantAdjustments(String string2) throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getAllowedNotificationAssistant() throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getAllowedNotificationAssistantForUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getAppActiveNotifications(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getAppsBypassingDndCount(int n) throws RemoteException {
            return 0;
        }

        @Override
        public AutomaticZenRule getAutomaticZenRule(String string2) throws RemoteException {
            return null;
        }

        @Override
        public byte[] getBackupPayload(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getBlockedAppCount(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getBlockedChannelCount(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException {
            return null;
        }

        @Override
        public int getDeletedChannelCount(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public ComponentName getEffectsSuppressor() throws RemoteException {
            return null;
        }

        @Override
        public List<String> getEnabledNotificationListenerPackages() throws RemoteException {
            return null;
        }

        @Override
        public List<ComponentName> getEnabledNotificationListeners(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getHintsFromListener(INotificationListener iNotificationListener) throws RemoteException {
            return 0;
        }

        @Override
        public StatusBarNotification[] getHistoricalNotifications(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getInterruptionFilterFromListener(INotificationListener iNotificationListener) throws RemoteException {
            return 0;
        }

        @Override
        public NotificationChannel getNotificationChannel(String string2, int n, String string3, String string4) throws RemoteException {
            return null;
        }

        @Override
        public NotificationChannel getNotificationChannelForPackage(String string2, int n, String string3, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public NotificationChannelGroup getNotificationChannelGroup(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public NotificationChannelGroup getNotificationChannelGroupForPackage(String string2, String string3, int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getNotificationChannelGroups(String string2) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getNotificationChannelGroupsForPackage(String string2, int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(INotificationListener iNotificationListener, String string2, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getNotificationChannels(String string2, String string3, int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getNotificationChannelsBypassingDnd(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getNotificationChannelsForPackage(String string2, int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getNotificationChannelsFromPrivilegedListener(INotificationListener iNotificationListener, String string2, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override
        public String getNotificationDelegate(String string2) throws RemoteException {
            return null;
        }

        @Override
        public NotificationManager.Policy getNotificationPolicy(String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getNumNotificationChannelsForPackage(String string2, int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public int getPackageImportance(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(String string2, int n, String string3, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public boolean getPrivateNotificationsAllowed() throws RemoteException {
            return false;
        }

        @Override
        public int getRuleInstanceCount(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override
        public ParceledListSlice getSnoozedNotificationsFromListener(INotificationListener iNotificationListener, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getZenMode() throws RemoteException {
            return 0;
        }

        @Override
        public ZenModeConfig getZenModeConfig() throws RemoteException {
            return null;
        }

        @Override
        public List<ZenModeConfig.ZenRule> getZenRules() throws RemoteException {
            return null;
        }

        @Override
        public boolean hasUserApprovedBubblesForPackage(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isNotificationAssistantAccessGranted(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isNotificationListenerAccessGranted(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isNotificationListenerAccessGrantedForUser(ComponentName componentName, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isNotificationPolicyAccessGranted(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isNotificationPolicyAccessGrantedForPackage(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isPackagePaused(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isSystemConditionProviderEnabled(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean matchesCallFilter(Bundle bundle) throws RemoteException {
            return false;
        }

        @Override
        public void notifyConditions(String string2, IConditionProvider iConditionProvider, Condition[] arrcondition) throws RemoteException {
        }

        @Override
        public boolean onlyHasDefaultChannel(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public void registerListener(INotificationListener iNotificationListener, ComponentName componentName, int n) throws RemoteException {
        }

        @Override
        public boolean removeAutomaticZenRule(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean removeAutomaticZenRules(String string2) throws RemoteException {
            return false;
        }

        @Override
        public void requestBindListener(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void requestBindProvider(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void requestHintsFromListener(INotificationListener iNotificationListener, int n) throws RemoteException {
        }

        @Override
        public void requestInterruptionFilterFromListener(INotificationListener iNotificationListener, int n) throws RemoteException {
        }

        @Override
        public void requestUnbindListener(INotificationListener iNotificationListener) throws RemoteException {
        }

        @Override
        public void requestUnbindProvider(IConditionProvider iConditionProvider) throws RemoteException {
        }

        @Override
        public void setAutomaticZenRuleState(String string2, Condition condition) throws RemoteException {
        }

        @Override
        public void setBubblesAllowed(String string2, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setHideSilentStatusIcons(boolean bl) throws RemoteException {
        }

        @Override
        public void setInterruptionFilter(String string2, int n) throws RemoteException {
        }

        @Override
        public void setNotificationAssistantAccessGranted(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void setNotificationAssistantAccessGrantedForUser(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setNotificationDelegate(String string2, String string3) throws RemoteException {
        }

        @Override
        public void setNotificationListenerAccessGranted(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void setNotificationListenerAccessGrantedForUser(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setNotificationPolicy(String string2, NotificationManager.Policy policy) throws RemoteException {
        }

        @Override
        public void setNotificationPolicyAccessGranted(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void setNotificationPolicyAccessGrantedForUser(String string2, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setNotificationsEnabledForPackage(String string2, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setNotificationsEnabledWithImportanceLockForPackage(String string2, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setNotificationsShownFromListener(INotificationListener iNotificationListener, String[] arrstring) throws RemoteException {
        }

        @Override
        public void setOnNotificationPostedTrimFromListener(INotificationListener iNotificationListener, int n) throws RemoteException {
        }

        @Override
        public void setPrivateNotificationsAllowed(boolean bl) throws RemoteException {
        }

        @Override
        public void setShowBadge(String string2, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setZenMode(int n, Uri uri, String string2) throws RemoteException {
        }

        @Override
        public boolean shouldHideSilentStatusIcons(String string2) throws RemoteException {
            return false;
        }

        @Override
        public void snoozeNotificationUntilContextFromListener(INotificationListener iNotificationListener, String string2, String string3) throws RemoteException {
        }

        @Override
        public void snoozeNotificationUntilFromListener(INotificationListener iNotificationListener, String string2, long l) throws RemoteException {
        }

        @Override
        public void unregisterListener(INotificationListener iNotificationListener, int n) throws RemoteException {
        }

        @Override
        public void unsnoozeNotificationFromAssistant(INotificationListener iNotificationListener, String string2) throws RemoteException {
        }

        @Override
        public boolean updateAutomaticZenRule(String string2, AutomaticZenRule automaticZenRule) throws RemoteException {
            return false;
        }

        @Override
        public void updateNotificationChannelForPackage(String string2, int n, NotificationChannel notificationChannel) throws RemoteException {
        }

        @Override
        public void updateNotificationChannelFromPrivilegedListener(INotificationListener iNotificationListener, String string2, UserHandle userHandle, NotificationChannel notificationChannel) throws RemoteException {
        }

        @Override
        public void updateNotificationChannelGroupForPackage(String string2, int n, NotificationChannelGroup notificationChannelGroup) throws RemoteException {
        }

        @Override
        public void updateNotificationChannelGroupFromPrivilegedListener(INotificationListener iNotificationListener, String string2, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INotificationManager {
        private static final String DESCRIPTOR = "android.app.INotificationManager";
        static final int TRANSACTION_addAutomaticZenRule = 106;
        static final int TRANSACTION_allowAssistantAdjustment = 16;
        static final int TRANSACTION_applyAdjustmentFromAssistant = 76;
        static final int TRANSACTION_applyAdjustmentsFromAssistant = 77;
        static final int TRANSACTION_applyEnqueuedAdjustmentFromAssistant = 75;
        static final int TRANSACTION_applyRestore = 113;
        static final int TRANSACTION_areBubblesAllowed = 21;
        static final int TRANSACTION_areBubblesAllowedForPackage = 22;
        static final int TRANSACTION_areChannelsBypassingDnd = 45;
        static final int TRANSACTION_areNotificationsEnabled = 13;
        static final int TRANSACTION_areNotificationsEnabledForPackage = 12;
        static final int TRANSACTION_canNotifyAsPackage = 117;
        static final int TRANSACTION_canShowBadge = 9;
        static final int TRANSACTION_cancelAllNotifications = 1;
        static final int TRANSACTION_cancelNotificationFromListener = 53;
        static final int TRANSACTION_cancelNotificationWithTag = 7;
        static final int TRANSACTION_cancelNotificationsFromListener = 54;
        static final int TRANSACTION_cancelToast = 4;
        static final int TRANSACTION_clearData = 2;
        static final int TRANSACTION_clearRequestedListenerHints = 64;
        static final int TRANSACTION_createNotificationChannelGroups = 24;
        static final int TRANSACTION_createNotificationChannels = 25;
        static final int TRANSACTION_createNotificationChannelsForPackage = 26;
        static final int TRANSACTION_deleteNotificationChannel = 34;
        static final int TRANSACTION_deleteNotificationChannelGroup = 40;
        static final int TRANSACTION_disallowAssistantAdjustment = 17;
        static final int TRANSACTION_enqueueNotificationWithTag = 6;
        static final int TRANSACTION_enqueueToast = 3;
        static final int TRANSACTION_finishToken = 5;
        static final int TRANSACTION_getActiveNotifications = 49;
        static final int TRANSACTION_getActiveNotificationsFromListener = 62;
        static final int TRANSACTION_getAllowedAssistantAdjustments = 15;
        static final int TRANSACTION_getAllowedNotificationAssistant = 92;
        static final int TRANSACTION_getAllowedNotificationAssistantForUser = 91;
        static final int TRANSACTION_getAppActiveNotifications = 114;
        static final int TRANSACTION_getAppsBypassingDndCount = 46;
        static final int TRANSACTION_getAutomaticZenRule = 104;
        static final int TRANSACTION_getBackupPayload = 112;
        static final int TRANSACTION_getBlockedAppCount = 44;
        static final int TRANSACTION_getBlockedChannelCount = 39;
        static final int TRANSACTION_getConsolidatedNotificationPolicy = 95;
        static final int TRANSACTION_getDeletedChannelCount = 38;
        static final int TRANSACTION_getEffectsSuppressor = 79;
        static final int TRANSACTION_getEnabledNotificationListenerPackages = 89;
        static final int TRANSACTION_getEnabledNotificationListeners = 90;
        static final int TRANSACTION_getHintsFromListener = 66;
        static final int TRANSACTION_getHistoricalNotifications = 50;
        static final int TRANSACTION_getInterruptionFilterFromListener = 68;
        static final int TRANSACTION_getNotificationChannel = 32;
        static final int TRANSACTION_getNotificationChannelForPackage = 33;
        static final int TRANSACTION_getNotificationChannelGroup = 41;
        static final int TRANSACTION_getNotificationChannelGroupForPackage = 28;
        static final int TRANSACTION_getNotificationChannelGroups = 42;
        static final int TRANSACTION_getNotificationChannelGroupsForPackage = 27;
        static final int TRANSACTION_getNotificationChannelGroupsFromPrivilegedListener = 74;
        static final int TRANSACTION_getNotificationChannels = 35;
        static final int TRANSACTION_getNotificationChannelsBypassingDnd = 47;
        static final int TRANSACTION_getNotificationChannelsForPackage = 36;
        static final int TRANSACTION_getNotificationChannelsFromPrivilegedListener = 73;
        static final int TRANSACTION_getNotificationDelegate = 116;
        static final int TRANSACTION_getNotificationPolicy = 99;
        static final int TRANSACTION_getNumNotificationChannelsForPackage = 37;
        static final int TRANSACTION_getPackageImportance = 14;
        static final int TRANSACTION_getPopulatedNotificationChannelGroupForPackage = 29;
        static final int TRANSACTION_getPrivateNotificationsAllowed = 119;
        static final int TRANSACTION_getRuleInstanceCount = 110;
        static final int TRANSACTION_getSnoozedNotificationsFromListener = 63;
        static final int TRANSACTION_getZenMode = 93;
        static final int TRANSACTION_getZenModeConfig = 94;
        static final int TRANSACTION_getZenRules = 105;
        static final int TRANSACTION_hasUserApprovedBubblesForPackage = 23;
        static final int TRANSACTION_isNotificationAssistantAccessGranted = 84;
        static final int TRANSACTION_isNotificationListenerAccessGranted = 82;
        static final int TRANSACTION_isNotificationListenerAccessGrantedForUser = 83;
        static final int TRANSACTION_isNotificationPolicyAccessGranted = 98;
        static final int TRANSACTION_isNotificationPolicyAccessGrantedForPackage = 101;
        static final int TRANSACTION_isPackagePaused = 48;
        static final int TRANSACTION_isSystemConditionProviderEnabled = 81;
        static final int TRANSACTION_matchesCallFilter = 80;
        static final int TRANSACTION_notifyConditions = 97;
        static final int TRANSACTION_onlyHasDefaultChannel = 43;
        static final int TRANSACTION_registerListener = 51;
        static final int TRANSACTION_removeAutomaticZenRule = 108;
        static final int TRANSACTION_removeAutomaticZenRules = 109;
        static final int TRANSACTION_requestBindListener = 57;
        static final int TRANSACTION_requestBindProvider = 59;
        static final int TRANSACTION_requestHintsFromListener = 65;
        static final int TRANSACTION_requestInterruptionFilterFromListener = 67;
        static final int TRANSACTION_requestUnbindListener = 58;
        static final int TRANSACTION_requestUnbindProvider = 60;
        static final int TRANSACTION_setAutomaticZenRuleState = 111;
        static final int TRANSACTION_setBubblesAllowed = 20;
        static final int TRANSACTION_setHideSilentStatusIcons = 19;
        static final int TRANSACTION_setInterruptionFilter = 70;
        static final int TRANSACTION_setNotificationAssistantAccessGranted = 86;
        static final int TRANSACTION_setNotificationAssistantAccessGrantedForUser = 88;
        static final int TRANSACTION_setNotificationDelegate = 115;
        static final int TRANSACTION_setNotificationListenerAccessGranted = 85;
        static final int TRANSACTION_setNotificationListenerAccessGrantedForUser = 87;
        static final int TRANSACTION_setNotificationPolicy = 100;
        static final int TRANSACTION_setNotificationPolicyAccessGranted = 102;
        static final int TRANSACTION_setNotificationPolicyAccessGrantedForUser = 103;
        static final int TRANSACTION_setNotificationsEnabledForPackage = 10;
        static final int TRANSACTION_setNotificationsEnabledWithImportanceLockForPackage = 11;
        static final int TRANSACTION_setNotificationsShownFromListener = 61;
        static final int TRANSACTION_setOnNotificationPostedTrimFromListener = 69;
        static final int TRANSACTION_setPrivateNotificationsAllowed = 118;
        static final int TRANSACTION_setShowBadge = 8;
        static final int TRANSACTION_setZenMode = 96;
        static final int TRANSACTION_shouldHideSilentStatusIcons = 18;
        static final int TRANSACTION_snoozeNotificationUntilContextFromListener = 55;
        static final int TRANSACTION_snoozeNotificationUntilFromListener = 56;
        static final int TRANSACTION_unregisterListener = 52;
        static final int TRANSACTION_unsnoozeNotificationFromAssistant = 78;
        static final int TRANSACTION_updateAutomaticZenRule = 107;
        static final int TRANSACTION_updateNotificationChannelForPackage = 31;
        static final int TRANSACTION_updateNotificationChannelFromPrivilegedListener = 72;
        static final int TRANSACTION_updateNotificationChannelGroupForPackage = 30;
        static final int TRANSACTION_updateNotificationChannelGroupFromPrivilegedListener = 71;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INotificationManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INotificationManager) {
                return (INotificationManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INotificationManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 119: {
                    return "getPrivateNotificationsAllowed";
                }
                case 118: {
                    return "setPrivateNotificationsAllowed";
                }
                case 117: {
                    return "canNotifyAsPackage";
                }
                case 116: {
                    return "getNotificationDelegate";
                }
                case 115: {
                    return "setNotificationDelegate";
                }
                case 114: {
                    return "getAppActiveNotifications";
                }
                case 113: {
                    return "applyRestore";
                }
                case 112: {
                    return "getBackupPayload";
                }
                case 111: {
                    return "setAutomaticZenRuleState";
                }
                case 110: {
                    return "getRuleInstanceCount";
                }
                case 109: {
                    return "removeAutomaticZenRules";
                }
                case 108: {
                    return "removeAutomaticZenRule";
                }
                case 107: {
                    return "updateAutomaticZenRule";
                }
                case 106: {
                    return "addAutomaticZenRule";
                }
                case 105: {
                    return "getZenRules";
                }
                case 104: {
                    return "getAutomaticZenRule";
                }
                case 103: {
                    return "setNotificationPolicyAccessGrantedForUser";
                }
                case 102: {
                    return "setNotificationPolicyAccessGranted";
                }
                case 101: {
                    return "isNotificationPolicyAccessGrantedForPackage";
                }
                case 100: {
                    return "setNotificationPolicy";
                }
                case 99: {
                    return "getNotificationPolicy";
                }
                case 98: {
                    return "isNotificationPolicyAccessGranted";
                }
                case 97: {
                    return "notifyConditions";
                }
                case 96: {
                    return "setZenMode";
                }
                case 95: {
                    return "getConsolidatedNotificationPolicy";
                }
                case 94: {
                    return "getZenModeConfig";
                }
                case 93: {
                    return "getZenMode";
                }
                case 92: {
                    return "getAllowedNotificationAssistant";
                }
                case 91: {
                    return "getAllowedNotificationAssistantForUser";
                }
                case 90: {
                    return "getEnabledNotificationListeners";
                }
                case 89: {
                    return "getEnabledNotificationListenerPackages";
                }
                case 88: {
                    return "setNotificationAssistantAccessGrantedForUser";
                }
                case 87: {
                    return "setNotificationListenerAccessGrantedForUser";
                }
                case 86: {
                    return "setNotificationAssistantAccessGranted";
                }
                case 85: {
                    return "setNotificationListenerAccessGranted";
                }
                case 84: {
                    return "isNotificationAssistantAccessGranted";
                }
                case 83: {
                    return "isNotificationListenerAccessGrantedForUser";
                }
                case 82: {
                    return "isNotificationListenerAccessGranted";
                }
                case 81: {
                    return "isSystemConditionProviderEnabled";
                }
                case 80: {
                    return "matchesCallFilter";
                }
                case 79: {
                    return "getEffectsSuppressor";
                }
                case 78: {
                    return "unsnoozeNotificationFromAssistant";
                }
                case 77: {
                    return "applyAdjustmentsFromAssistant";
                }
                case 76: {
                    return "applyAdjustmentFromAssistant";
                }
                case 75: {
                    return "applyEnqueuedAdjustmentFromAssistant";
                }
                case 74: {
                    return "getNotificationChannelGroupsFromPrivilegedListener";
                }
                case 73: {
                    return "getNotificationChannelsFromPrivilegedListener";
                }
                case 72: {
                    return "updateNotificationChannelFromPrivilegedListener";
                }
                case 71: {
                    return "updateNotificationChannelGroupFromPrivilegedListener";
                }
                case 70: {
                    return "setInterruptionFilter";
                }
                case 69: {
                    return "setOnNotificationPostedTrimFromListener";
                }
                case 68: {
                    return "getInterruptionFilterFromListener";
                }
                case 67: {
                    return "requestInterruptionFilterFromListener";
                }
                case 66: {
                    return "getHintsFromListener";
                }
                case 65: {
                    return "requestHintsFromListener";
                }
                case 64: {
                    return "clearRequestedListenerHints";
                }
                case 63: {
                    return "getSnoozedNotificationsFromListener";
                }
                case 62: {
                    return "getActiveNotificationsFromListener";
                }
                case 61: {
                    return "setNotificationsShownFromListener";
                }
                case 60: {
                    return "requestUnbindProvider";
                }
                case 59: {
                    return "requestBindProvider";
                }
                case 58: {
                    return "requestUnbindListener";
                }
                case 57: {
                    return "requestBindListener";
                }
                case 56: {
                    return "snoozeNotificationUntilFromListener";
                }
                case 55: {
                    return "snoozeNotificationUntilContextFromListener";
                }
                case 54: {
                    return "cancelNotificationsFromListener";
                }
                case 53: {
                    return "cancelNotificationFromListener";
                }
                case 52: {
                    return "unregisterListener";
                }
                case 51: {
                    return "registerListener";
                }
                case 50: {
                    return "getHistoricalNotifications";
                }
                case 49: {
                    return "getActiveNotifications";
                }
                case 48: {
                    return "isPackagePaused";
                }
                case 47: {
                    return "getNotificationChannelsBypassingDnd";
                }
                case 46: {
                    return "getAppsBypassingDndCount";
                }
                case 45: {
                    return "areChannelsBypassingDnd";
                }
                case 44: {
                    return "getBlockedAppCount";
                }
                case 43: {
                    return "onlyHasDefaultChannel";
                }
                case 42: {
                    return "getNotificationChannelGroups";
                }
                case 41: {
                    return "getNotificationChannelGroup";
                }
                case 40: {
                    return "deleteNotificationChannelGroup";
                }
                case 39: {
                    return "getBlockedChannelCount";
                }
                case 38: {
                    return "getDeletedChannelCount";
                }
                case 37: {
                    return "getNumNotificationChannelsForPackage";
                }
                case 36: {
                    return "getNotificationChannelsForPackage";
                }
                case 35: {
                    return "getNotificationChannels";
                }
                case 34: {
                    return "deleteNotificationChannel";
                }
                case 33: {
                    return "getNotificationChannelForPackage";
                }
                case 32: {
                    return "getNotificationChannel";
                }
                case 31: {
                    return "updateNotificationChannelForPackage";
                }
                case 30: {
                    return "updateNotificationChannelGroupForPackage";
                }
                case 29: {
                    return "getPopulatedNotificationChannelGroupForPackage";
                }
                case 28: {
                    return "getNotificationChannelGroupForPackage";
                }
                case 27: {
                    return "getNotificationChannelGroupsForPackage";
                }
                case 26: {
                    return "createNotificationChannelsForPackage";
                }
                case 25: {
                    return "createNotificationChannels";
                }
                case 24: {
                    return "createNotificationChannelGroups";
                }
                case 23: {
                    return "hasUserApprovedBubblesForPackage";
                }
                case 22: {
                    return "areBubblesAllowedForPackage";
                }
                case 21: {
                    return "areBubblesAllowed";
                }
                case 20: {
                    return "setBubblesAllowed";
                }
                case 19: {
                    return "setHideSilentStatusIcons";
                }
                case 18: {
                    return "shouldHideSilentStatusIcons";
                }
                case 17: {
                    return "disallowAssistantAdjustment";
                }
                case 16: {
                    return "allowAssistantAdjustment";
                }
                case 15: {
                    return "getAllowedAssistantAdjustments";
                }
                case 14: {
                    return "getPackageImportance";
                }
                case 13: {
                    return "areNotificationsEnabled";
                }
                case 12: {
                    return "areNotificationsEnabledForPackage";
                }
                case 11: {
                    return "setNotificationsEnabledWithImportanceLockForPackage";
                }
                case 10: {
                    return "setNotificationsEnabledForPackage";
                }
                case 9: {
                    return "canShowBadge";
                }
                case 8: {
                    return "setShowBadge";
                }
                case 7: {
                    return "cancelNotificationWithTag";
                }
                case 6: {
                    return "enqueueNotificationWithTag";
                }
                case 5: {
                    return "finishToken";
                }
                case 4: {
                    return "cancelToast";
                }
                case 3: {
                    return "enqueueToast";
                }
                case 2: {
                    return "clearData";
                }
                case 1: 
            }
            return "cancelAllNotifications";
        }

        public static boolean setDefaultImpl(INotificationManager iNotificationManager) {
            if (Proxy.sDefaultImpl == null && iNotificationManager != null) {
                Proxy.sDefaultImpl = iNotificationManager;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                boolean bl6 = false;
                boolean bl7 = false;
                boolean bl8 = false;
                boolean bl9 = false;
                boolean bl10 = false;
                boolean bl11 = false;
                boolean bl12 = false;
                boolean bl13 = false;
                boolean bl14 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 119: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPrivateNotificationsAllowed() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 118: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.setPrivateNotificationsAllowed(bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 117: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.canNotifyAsPackage(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 116: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNotificationDelegate(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 115: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setNotificationDelegate(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 114: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAppActiveNotifications(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 113: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.applyRestore(((Parcel)object).createByteArray(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 112: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getBackupPayload(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeByteArray((byte[])object);
                        return true;
                    }
                    case 111: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Condition.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setAutomaticZenRuleState(string2, (Condition)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 110: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getRuleInstanceCount((ComponentName)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 109: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeAutomaticZenRules(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 108: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeAutomaticZenRule(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 107: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? AutomaticZenRule.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.updateAutomaticZenRule(string3, (AutomaticZenRule)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 106: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? AutomaticZenRule.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.addAutomaticZenRule((AutomaticZenRule)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 105: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getZenRules();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 104: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAutomaticZenRule(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((AutomaticZenRule)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 103: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl14 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.setNotificationPolicyAccessGrantedForUser(string4, n, bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 102: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        bl14 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.setNotificationPolicyAccessGranted(string5, bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 101: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isNotificationPolicyAccessGrantedForPackage(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 100: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? NotificationManager.Policy.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setNotificationPolicy(string6, (NotificationManager.Policy)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 99: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNotificationPolicy(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((NotificationManager.Policy)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 98: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isNotificationPolicyAccessGranted(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 97: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyConditions(((Parcel)object).readString(), IConditionProvider.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).createTypedArray(Condition.CREATOR));
                        return true;
                    }
                    case 96: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setZenMode(n, (Uri)object2, ((Parcel)object).readString());
                        return true;
                    }
                    case 95: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getConsolidatedNotificationPolicy();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((NotificationManager.Policy)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 94: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getZenModeConfig();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ZenModeConfig)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 93: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getZenMode();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 92: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllowedNotificationAssistant();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ComponentName)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 91: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllowedNotificationAssistantForUser(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ComponentName)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 90: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getEnabledNotificationListeners(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 89: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getEnabledNotificationListenerPackages();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringList((List<String>)object);
                        return true;
                    }
                    case 88: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl14 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.setNotificationAssistantAccessGrantedForUser(componentName, n, bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 87: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl14 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.setNotificationListenerAccessGrantedForUser(componentName, n, bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 86: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl14 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.setNotificationAssistantAccessGranted(componentName, bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 85: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl14 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.setNotificationListenerAccessGranted(componentName, bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 84: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isNotificationAssistantAccessGranted((ComponentName)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 83: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isNotificationListenerAccessGrantedForUser(componentName, ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 82: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isNotificationListenerAccessGranted((ComponentName)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 81: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSystemConditionProviderEnabled(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 80: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.matchesCallFilter((Bundle)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 79: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getEffectsSuppressor();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ComponentName)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 78: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unsnoozeNotificationFromAssistant(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 77: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.applyAdjustmentsFromAssistant(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).createTypedArrayList(Adjustment.CREATOR));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 76: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        INotificationListener iNotificationListener = INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Adjustment.CREATOR.createFromParcel((Parcel)object) : null;
                        this.applyAdjustmentFromAssistant(iNotificationListener, (Adjustment)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 75: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        INotificationListener iNotificationListener = INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Adjustment.CREATOR.createFromParcel((Parcel)object) : null;
                        this.applyEnqueuedAdjustmentFromAssistant(iNotificationListener, (Adjustment)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        INotificationListener iNotificationListener = INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string7 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getNotificationChannelGroupsFromPrivilegedListener(iNotificationListener, string7, (UserHandle)object);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        INotificationListener iNotificationListener = INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string8 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getNotificationChannelsFromPrivilegedListener(iNotificationListener, string8, (UserHandle)object);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        INotificationListener iNotificationListener = INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string9 = ((Parcel)object).readString();
                        UserHandle userHandle = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? NotificationChannel.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateNotificationChannelFromPrivilegedListener(iNotificationListener, string9, userHandle, (NotificationChannel)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        INotificationListener iNotificationListener = INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string10 = ((Parcel)object).readString();
                        UserHandle userHandle = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? NotificationChannelGroup.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateNotificationChannelGroupFromPrivilegedListener(iNotificationListener, string10, userHandle, (NotificationChannelGroup)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setInterruptionFilter(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setOnNotificationPostedTrimFromListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getInterruptionFilterFromListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestInterruptionFilterFromListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getHintsFromListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestHintsFromListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearRequestedListenerHints(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSnoozedNotificationsFromListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveNotificationsFromListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).createStringArray(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setNotificationsShownFromListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).createStringArray());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestUnbindProvider(IConditionProvider.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestBindProvider((ComponentName)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestUnbindListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestBindListener((ComponentName)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.snoozeNotificationUntilFromListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).readLong());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.snoozeNotificationUntilContextFromListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelNotificationsFromListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).createStringArray());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelNotificationFromListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterListener(INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        INotificationListener iNotificationListener = INotificationListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registerListener(iNotificationListener, componentName, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getHistoricalNotifications(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        object2.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveNotifications(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        object2.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isPackagePaused(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNotificationChannelsBypassingDnd(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getAppsBypassingDndCount(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.areChannelsBypassingDnd() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getBlockedAppCount(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.onlyHasDefaultChannel(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNotificationChannelGroups(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNotificationChannelGroup(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((NotificationChannelGroup)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteNotificationChannelGroup(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getBlockedChannelCount(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDeletedChannelCount(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string11 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl14 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        n = this.getNumNotificationChannelsForPackage(string11, n, bl14);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string12 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl14 = ((Parcel)object).readInt() != 0;
                        object = this.getNotificationChannelsForPackage(string12, n, bl14);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNotificationChannels(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteNotificationChannel(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string13 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        String string14 = ((Parcel)object).readString();
                        bl14 = ((Parcel)object).readInt() != 0;
                        object = this.getNotificationChannelForPackage(string13, n, string14, bl14);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((NotificationChannel)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNotificationChannel(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((NotificationChannel)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string15 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? NotificationChannel.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateNotificationChannelForPackage(string15, n, (NotificationChannel)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string16 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? NotificationChannelGroup.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateNotificationChannelGroupForPackage(string16, n, (NotificationChannelGroup)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string17 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        String string18 = ((Parcel)object).readString();
                        bl14 = ((Parcel)object).readInt() != 0;
                        object = this.getPopulatedNotificationChannelGroupForPackage(string17, n, string18, bl14);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((NotificationChannelGroup)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNotificationChannelGroupForPackage(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((NotificationChannelGroup)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string19 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl14 = ((Parcel)object).readInt() != 0;
                        object = this.getNotificationChannelGroupsForPackage(string19, n, bl14);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string20 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.createNotificationChannelsForPackage(string20, n, (ParceledListSlice)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string21 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.createNotificationChannels(string21, (ParceledListSlice)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string22 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.createNotificationChannelGroups(string22, (ParceledListSlice)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasUserApprovedBubblesForPackage(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.areBubblesAllowedForPackage(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.areBubblesAllowed(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string23 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl14 = bl8;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.setBubblesAllowed(string23, n, bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl14 = bl9;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.setHideSilentStatusIcons(bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.shouldHideSilentStatusIcons(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disallowAssistantAdjustment(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.allowAssistantAdjustment(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllowedAssistantAdjustments(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringList((List<String>)object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPackageImportance(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.areNotificationsEnabled(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.areNotificationsEnabledForPackage(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string24 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl14 = bl10;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.setNotificationsEnabledWithImportanceLockForPackage(string24, n, bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string25 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl14 = bl11;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.setNotificationsEnabledForPackage(string25, n, bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.canShowBadge(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string26 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl14 = bl12;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.setShowBadge(string26, n, bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelNotificationWithTag(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string27 = ((Parcel)object).readString();
                        String string28 = ((Parcel)object).readString();
                        String string29 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        Notification notification = ((Parcel)object).readInt() != 0 ? Notification.CREATOR.createFromParcel((Parcel)object) : null;
                        this.enqueueNotificationWithTag(string27, string28, string29, n, notification, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishToken(((Parcel)object).readString(), ITransientNotification.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelToast(((Parcel)object).readString(), ITransientNotification.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enqueueToast(((Parcel)object).readString(), ITransientNotification.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string30 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl14 = bl13;
                        if (((Parcel)object).readInt() != 0) {
                            bl14 = true;
                        }
                        this.clearData(string30, n, bl14);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.cancelAllNotifications(((Parcel)object).readString(), ((Parcel)object).readInt());
                ((Parcel)object2).writeNoException();
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements INotificationManager {
            public static INotificationManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public String addAutomaticZenRule(AutomaticZenRule object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((AutomaticZenRule)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(106, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().addAutomaticZenRule((AutomaticZenRule)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void allowAssistantAdjustment(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().allowAssistantAdjustment(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void applyAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (adjustment != null) {
                        parcel.writeInt(1);
                        adjustment.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(76, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().applyAdjustmentFromAssistant(iNotificationListener, adjustment);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void applyAdjustmentsFromAssistant(INotificationListener iNotificationListener, List<Adjustment> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(77, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().applyAdjustmentsFromAssistant(iNotificationListener, list);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void applyEnqueuedAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (adjustment != null) {
                        parcel.writeInt(1);
                        adjustment.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(75, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().applyEnqueuedAdjustmentFromAssistant(iNotificationListener, adjustment);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void applyRestore(byte[] arrby, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(113, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().applyRestore(arrby, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean areBubblesAllowed(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(21, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().areBubblesAllowed(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean areBubblesAllowedForPackage(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(22, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().areBubblesAllowedForPackage(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean areChannelsBypassingDnd() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(45, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().areChannelsBypassingDnd();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean areNotificationsEnabled(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(13, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().areNotificationsEnabled(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean areNotificationsEnabledForPackage(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(12, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().areNotificationsEnabledForPackage(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public boolean canNotifyAsPackage(String string2, String string3, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(117, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().canNotifyAsPackage(string2, string3, n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean canShowBadge(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().canShowBadge(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void cancelAllNotifications(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelAllNotifications(string2, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void cancelNotificationFromListener(INotificationListener iNotificationListener, String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelNotificationFromListener(iNotificationListener, string2, string3, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void cancelNotificationWithTag(String string2, String string3, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelNotificationWithTag(string2, string3, n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void cancelNotificationsFromListener(INotificationListener iNotificationListener, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelNotificationsFromListener(iNotificationListener, arrstring);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void cancelToast(String string2, ITransientNotification iTransientNotification) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iTransientNotification != null ? iTransientNotification.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelToast(string2, iTransientNotification);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void clearData(String string2, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearData(string2, n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void clearRequestedListenerHints(INotificationListener iNotificationListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(64, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearRequestedListenerHints(iNotificationListener);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void createNotificationChannelGroups(String string2, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createNotificationChannelGroups(string2, parceledListSlice);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void createNotificationChannels(String string2, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createNotificationChannels(string2, parceledListSlice);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void createNotificationChannelsForPackage(String string2, int n, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createNotificationChannelsForPackage(string2, n, parceledListSlice);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void deleteNotificationChannel(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteNotificationChannel(string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void deleteNotificationChannelGroup(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteNotificationChannelGroup(string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void disallowAssistantAdjustment(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disallowAssistantAdjustment(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
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
            @Override
            public void enqueueNotificationWithTag(String string2, String string3, String string4, int n, Notification notification, int n2) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        try {
                            parcel2.writeString(string3);
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        try {
                            parcel2.writeString(string4);
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        try {
                            parcel2.writeInt(n);
                            if (notification != null) {
                                parcel2.writeInt(1);
                                notification.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeInt(n2);
                        if (!this.mRemote.transact(6, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().enqueueNotificationWithTag(string2, string3, string4, n, notification, n2);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_8;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void enqueueToast(String string2, ITransientNotification iTransientNotification, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iTransientNotification != null ? iTransientNotification.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enqueueToast(string2, iTransientNotification, n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void finishToken(String string2, ITransientNotification iTransientNotification) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iTransientNotification != null ? iTransientNotification.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishToken(string2, iTransientNotification);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public StatusBarNotification[] getActiveNotifications(String arrstatusBarNotification) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrstatusBarNotification);
                    if (!this.mRemote.transact(49, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstatusBarNotification = Stub.getDefaultImpl().getActiveNotifications((String)arrstatusBarNotification);
                        return arrstatusBarNotification;
                    }
                    parcel2.readException();
                    arrstatusBarNotification = parcel2.createTypedArray(StatusBarNotification.CREATOR);
                    return arrstatusBarNotification;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice getActiveNotificationsFromListener(INotificationListener parceledListSlice, String[] arrstring, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block7 : {
                    IBinder iBinder;
                    block6 : {
                        block5 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (parceledListSlice == null) break block5;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = parceledListSlice.asBinder();
                            break block6;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeStringArray(arrstring);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(62, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block7;
                    parceledListSlice = Stub.getDefaultImpl().getActiveNotificationsFromListener((INotificationListener)((Object)parceledListSlice), arrstring, n);
                    parcel2.recycle();
                    parcel.recycle();
                    return parceledListSlice;
                }
                parcel2.readException();
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public List<String> getAllowedAssistantAdjustments(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getAllowedAssistantAdjustments((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ComponentName getAllowedNotificationAssistant() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(92, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getAllowedNotificationAssistant();
                        parcel.recycle();
                        parcel2.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ComponentName componentName = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return componentName;
            }

            @Override
            public ComponentName getAllowedNotificationAssistantForUser(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(91, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getAllowedNotificationAssistantForUser(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ComponentName componentName = parcel2.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return componentName;
            }

            @Override
            public ParceledListSlice getAppActiveNotifications(String parceledListSlice, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)((Object)parceledListSlice));
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(114, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getAppActiveNotifications((String)((Object)parceledListSlice), n);
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            @Override
            public int getAppsBypassingDndCount(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getAppsBypassingDndCount(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public AutomaticZenRule getAutomaticZenRule(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(104, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getAutomaticZenRule((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? AutomaticZenRule.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public byte[] getBackupPayload(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(112, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().getBackupPayload(n);
                        return arrby;
                    }
                    parcel2.readException();
                    byte[] arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getBlockedAppCount(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getBlockedAppCount(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getBlockedChannelCount(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getBlockedChannelCount(string2, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(95, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        NotificationManager.Policy policy = Stub.getDefaultImpl().getConsolidatedNotificationPolicy();
                        parcel.recycle();
                        parcel2.recycle();
                        return policy;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                NotificationManager.Policy policy = parcel.readInt() != 0 ? NotificationManager.Policy.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return policy;
            }

            @Override
            public int getDeletedChannelCount(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getDeletedChannelCount(string2, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ComponentName getEffectsSuppressor() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(79, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getEffectsSuppressor();
                        parcel.recycle();
                        parcel2.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ComponentName componentName = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return componentName;
            }

            @Override
            public List<String> getEnabledNotificationListenerPackages() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(89, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<String> list = Stub.getDefaultImpl().getEnabledNotificationListenerPackages();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<String> arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<ComponentName> getEnabledNotificationListeners(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(90, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ComponentName> list = Stub.getDefaultImpl().getEnabledNotificationListeners(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ComponentName> arrayList = parcel2.createTypedArrayList(ComponentName.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getHintsFromListener(INotificationListener iNotificationListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(66, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getHintsFromListener(iNotificationListener);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public StatusBarNotification[] getHistoricalNotifications(String arrstatusBarNotification, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrstatusBarNotification);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstatusBarNotification = Stub.getDefaultImpl().getHistoricalNotifications((String)arrstatusBarNotification, n);
                        return arrstatusBarNotification;
                    }
                    parcel2.readException();
                    arrstatusBarNotification = parcel2.createTypedArray(StatusBarNotification.CREATOR);
                    return arrstatusBarNotification;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getInterruptionFilterFromListener(INotificationListener iNotificationListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(68, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getInterruptionFilterFromListener(iNotificationListener);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public NotificationChannel getNotificationChannel(String object, int n, String string2, String string3) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        if (this.mRemote.transact(32, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getNotificationChannel((String)object, n, string2, string3);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? NotificationChannel.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public NotificationChannel getNotificationChannelForPackage(String object, int n, String string2, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(33, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    object = Stub.getDefaultImpl().getNotificationChannelForPackage((String)object, n, string2, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return object;
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? NotificationChannel.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public NotificationChannelGroup getNotificationChannelGroup(String object, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeString(string2);
                        if (this.mRemote.transact(41, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getNotificationChannelGroup((String)object, string2);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? NotificationChannelGroup.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public NotificationChannelGroup getNotificationChannelGroupForPackage(String object, String string2, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        parcel.writeString(string2);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(28, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getNotificationChannelGroupForPackage((String)object, string2, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? NotificationChannelGroup.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public ParceledListSlice getNotificationChannelGroups(String parceledListSlice) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        if (this.mRemote.transact(42, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getNotificationChannelGroups((String)((Object)parceledListSlice));
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public ParceledListSlice getNotificationChannelGroupsForPackage(String parceledListSlice, int n, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        parcel.writeInt(n);
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n2);
                    if (this.mRemote.transact(27, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    parceledListSlice = Stub.getDefaultImpl().getNotificationChannelGroupsForPackage((String)((Object)parceledListSlice), n, bl);
                    parcel2.recycle();
                    parcel.recycle();
                    return parceledListSlice;
                }
                parcel2.readException();
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(INotificationListener parceledListSlice, String string2, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = parceledListSlice != null ? parceledListSlice.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(74, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        parceledListSlice = Stub.getDefaultImpl().getNotificationChannelGroupsFromPrivilegedListener((INotificationListener)((Object)parceledListSlice), string2, userHandle);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    parcel2.readException();
                    parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                    parcel2.recycle();
                    parcel.recycle();
                    return parceledListSlice;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public ParceledListSlice getNotificationChannels(String parceledListSlice, String string2, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        parcel.writeString(string2);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(35, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getNotificationChannels((String)((Object)parceledListSlice), string2, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public ParceledListSlice getNotificationChannelsBypassingDnd(String parceledListSlice, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)((Object)parceledListSlice));
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(47, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getNotificationChannelsBypassingDnd((String)((Object)parceledListSlice), n);
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            @Override
            public ParceledListSlice getNotificationChannelsForPackage(String parceledListSlice, int n, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        parcel.writeInt(n);
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n2);
                    if (this.mRemote.transact(36, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    parceledListSlice = Stub.getDefaultImpl().getNotificationChannelsForPackage((String)((Object)parceledListSlice), n, bl);
                    parcel2.recycle();
                    parcel.recycle();
                    return parceledListSlice;
                }
                parcel2.readException();
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParceledListSlice getNotificationChannelsFromPrivilegedListener(INotificationListener parceledListSlice, String string2, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = parceledListSlice != null ? parceledListSlice.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(73, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        parceledListSlice = Stub.getDefaultImpl().getNotificationChannelsFromPrivilegedListener((INotificationListener)((Object)parceledListSlice), string2, userHandle);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    parcel2.readException();
                    parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                    parcel2.recycle();
                    parcel.recycle();
                    return parceledListSlice;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public String getNotificationDelegate(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(116, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getNotificationDelegate(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public NotificationManager.Policy getNotificationPolicy(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(99, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getNotificationPolicy((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? NotificationManager.Policy.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public int getNumNotificationChannelsForPackage(String string2, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getNumNotificationChannelsForPackage(string2, n, bl);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getPackageImportance(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPackageImportance(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(String object, int n, String string2, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(29, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    object = Stub.getDefaultImpl().getPopulatedNotificationChannelGroupForPackage((String)object, n, string2, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return object;
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? NotificationChannelGroup.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public boolean getPrivateNotificationsAllowed() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(119, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getPrivateNotificationsAllowed();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public int getRuleInstanceCount(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(110, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getRuleInstanceCount(componentName);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice getSnoozedNotificationsFromListener(INotificationListener parceledListSlice, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block7 : {
                    IBinder iBinder;
                    block6 : {
                        block5 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (parceledListSlice == null) break block5;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = parceledListSlice.asBinder();
                            break block6;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    parcel2.writeInt(n);
                    if (this.mRemote.transact(63, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block7;
                    parceledListSlice = Stub.getDefaultImpl().getSnoozedNotificationsFromListener((INotificationListener)((Object)parceledListSlice), n);
                    parcel.recycle();
                    parcel2.recycle();
                    return parceledListSlice;
                }
                parcel.readException();
                parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            @Override
            public int getZenMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(93, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getZenMode();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ZenModeConfig getZenModeConfig() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(94, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ZenModeConfig zenModeConfig = Stub.getDefaultImpl().getZenModeConfig();
                        parcel.recycle();
                        parcel2.recycle();
                        return zenModeConfig;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ZenModeConfig zenModeConfig = parcel.readInt() != 0 ? ZenModeConfig.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return zenModeConfig;
            }

            @Override
            public List<ZenModeConfig.ZenRule> getZenRules() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(105, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ZenModeConfig.ZenRule> list = Stub.getDefaultImpl().getZenRules();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ZenModeConfig.ZenRule> arrayList = parcel2.createTypedArrayList(ZenModeConfig.ZenRule.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean hasUserApprovedBubblesForPackage(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(23, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasUserApprovedBubblesForPackage(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isNotificationAssistantAccessGranted(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(84, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isNotificationAssistantAccessGranted(componentName);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isNotificationListenerAccessGranted(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(82, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isNotificationListenerAccessGranted(componentName);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isNotificationListenerAccessGrantedForUser(ComponentName componentName, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(83, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isNotificationListenerAccessGrantedForUser(componentName, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean isNotificationPolicyAccessGranted(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(98, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isNotificationPolicyAccessGranted(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isNotificationPolicyAccessGrantedForPackage(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(101, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isNotificationPolicyAccessGrantedForPackage(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isPackagePaused(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(48, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isPackagePaused(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isSystemConditionProviderEnabled(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(81, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSystemConditionProviderEnabled(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean matchesCallFilter(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(80, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().matchesCallFilter(bundle);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void notifyConditions(String string2, IConditionProvider iConditionProvider, Condition[] arrcondition) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iConditionProvider != null ? iConditionProvider.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeTypedArray((Parcelable[])arrcondition, 0);
                    if (this.mRemote.transact(97, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().notifyConditions(string2, iConditionProvider, arrcondition);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public boolean onlyHasDefaultChannel(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(43, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().onlyHasDefaultChannel(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerListener(INotificationListener iNotificationListener, ComponentName componentName, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerListener(iNotificationListener, componentName, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean removeAutomaticZenRule(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(108, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeAutomaticZenRule(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean removeAutomaticZenRules(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(109, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeAutomaticZenRules(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void requestBindListener(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(57, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestBindListener(componentName);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void requestBindProvider(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(59, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestBindProvider(componentName);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestHintsFromListener(INotificationListener iNotificationListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(65, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestHintsFromListener(iNotificationListener, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestInterruptionFilterFromListener(INotificationListener iNotificationListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(67, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestInterruptionFilterFromListener(iNotificationListener, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestUnbindListener(INotificationListener iNotificationListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(58, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestUnbindListener(iNotificationListener);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestUnbindProvider(IConditionProvider iConditionProvider) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iConditionProvider != null ? iConditionProvider.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestUnbindProvider(iConditionProvider);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setAutomaticZenRuleState(String string2, Condition condition) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (condition != null) {
                        parcel.writeInt(1);
                        condition.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(111, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAutomaticZenRuleState(string2, condition);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setBubblesAllowed(String string2, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBubblesAllowed(string2, n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setHideSilentStatusIcons(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setHideSilentStatusIcons(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setInterruptionFilter(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(70, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterruptionFilter(string2, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setNotificationAssistantAccessGranted(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(86, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNotificationAssistantAccessGranted(componentName, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setNotificationAssistantAccessGrantedForUser(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(88, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNotificationAssistantAccessGrantedForUser(componentName, n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setNotificationDelegate(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(115, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNotificationDelegate(string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setNotificationListenerAccessGranted(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(85, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNotificationListenerAccessGranted(componentName, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setNotificationListenerAccessGrantedForUser(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(87, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNotificationListenerAccessGrantedForUser(componentName, n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setNotificationPolicy(String string2, NotificationManager.Policy policy) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (policy != null) {
                        parcel.writeInt(1);
                        policy.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(100, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNotificationPolicy(string2, policy);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setNotificationPolicyAccessGranted(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(102, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNotificationPolicyAccessGranted(string2, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setNotificationPolicyAccessGrantedForUser(String string2, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(103, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNotificationPolicyAccessGrantedForUser(string2, n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setNotificationsEnabledForPackage(String string2, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNotificationsEnabledForPackage(string2, n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setNotificationsEnabledWithImportanceLockForPackage(String string2, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNotificationsEnabledWithImportanceLockForPackage(string2, n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setNotificationsShownFromListener(INotificationListener iNotificationListener, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(61, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNotificationsShownFromListener(iNotificationListener, arrstring);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setOnNotificationPostedTrimFromListener(INotificationListener iNotificationListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(69, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOnNotificationPostedTrimFromListener(iNotificationListener, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setPrivateNotificationsAllowed(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(118, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPrivateNotificationsAllowed(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setShowBadge(String string2, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setShowBadge(string2, n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setZenMode(int n, Uri uri, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(96, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setZenMode(n, uri, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public boolean shouldHideSilentStatusIcons(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(18, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().shouldHideSilentStatusIcons(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void snoozeNotificationUntilContextFromListener(INotificationListener iNotificationListener, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().snoozeNotificationUntilContextFromListener(iNotificationListener, string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void snoozeNotificationUntilFromListener(INotificationListener iNotificationListener, String string2, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().snoozeNotificationUntilFromListener(iNotificationListener, string2, l);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterListener(INotificationListener iNotificationListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(52, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterListener(iNotificationListener, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unsnoozeNotificationFromAssistant(INotificationListener iNotificationListener, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(78, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unsnoozeNotificationFromAssistant(iNotificationListener, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean updateAutomaticZenRule(String string2, AutomaticZenRule automaticZenRule) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    boolean bl = true;
                    if (automaticZenRule != null) {
                        parcel.writeInt(1);
                        automaticZenRule.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(107, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().updateAutomaticZenRule(string2, automaticZenRule);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void updateNotificationChannelForPackage(String string2, int n, NotificationChannel notificationChannel) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (notificationChannel != null) {
                        parcel.writeInt(1);
                        notificationChannel.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateNotificationChannelForPackage(string2, n, notificationChannel);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void updateNotificationChannelFromPrivilegedListener(INotificationListener iNotificationListener, String string2, UserHandle userHandle, NotificationChannel notificationChannel) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (notificationChannel != null) {
                        parcel.writeInt(1);
                        notificationChannel.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(72, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateNotificationChannelFromPrivilegedListener(iNotificationListener, string2, userHandle, notificationChannel);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void updateNotificationChannelGroupForPackage(String string2, int n, NotificationChannelGroup notificationChannelGroup) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (notificationChannelGroup != null) {
                        parcel.writeInt(1);
                        notificationChannelGroup.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateNotificationChannelGroupForPackage(string2, n, notificationChannelGroup);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void updateNotificationChannelGroupFromPrivilegedListener(INotificationListener iNotificationListener, String string2, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNotificationListener != null ? iNotificationListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (notificationChannelGroup != null) {
                        parcel.writeInt(1);
                        notificationChannelGroup.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(71, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateNotificationChannelGroupFromPrivilegedListener(iNotificationListener, string2, userHandle, notificationChannelGroup);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

