/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.IProcessObserver;
import android.app.ProcessMemoryState;
import android.app.ProfilerInfo;
import android.content.ComponentName;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ActivityPresentationInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.TransactionTooLargeException;
import java.util.ArrayList;
import java.util.List;

public abstract class ActivityManagerInternal {
    public static final int ALLOW_FULL_ONLY = 2;
    public static final int ALLOW_NON_FULL = 0;
    public static final int ALLOW_NON_FULL_IN_PROFILE = 1;

    public abstract void broadcastCloseSystemDialogs(String var1);

    public abstract void broadcastGlobalConfigurationChanged(int var1, boolean var2);

    public abstract int broadcastIntentInPackage(String var1, int var2, int var3, int var4, Intent var5, String var6, IIntentReceiver var7, int var8, String var9, Bundle var10, String var11, Bundle var12, boolean var13, boolean var14, int var15, boolean var16);

    public abstract boolean canStartMoreUsers();

    public abstract String checkContentProviderAccess(String var1, int var2);

    public abstract void cleanUpServices(int var1, ComponentName var2, Intent var3);

    public abstract void clearPendingBackup(int var1);

    public abstract void clearPendingIntentAllowBgActivityStarts(IIntentSender var1, IBinder var2);

    public abstract void disconnectActivityFromServices(Object var1, Object var2);

    public abstract void enforceCallingPermission(String var1, String var2);

    public abstract void ensureBootCompleted();

    public abstract void ensureNotSpecialUser(int var1);

    public abstract void finishBooting();

    public abstract void finishUserSwitch(Object var1);

    public abstract ActivityInfo getActivityInfoForUser(ActivityInfo var1, int var2);

    public abstract ActivityPresentationInfo getActivityPresentationInfo(IBinder var1);

    public abstract int[] getCurrentProfileIds();

    public abstract UserInfo getCurrentUser();

    public abstract int getCurrentUserId();

    public abstract int getMaxRunningUsers();

    public abstract List<ProcessMemoryState> getMemoryStateForProcesses();

    public abstract int getStorageMountMode(int var1, int var2);

    public abstract int getTaskIdForActivity(IBinder var1, boolean var2);

    public abstract int getUidProcessState(int var1);

    public abstract int handleIncomingUser(int var1, int var2, int var3, boolean var4, int var5, String var6, String var7);

    public abstract boolean hasRunningActivity(int var1, String var2);

    public abstract boolean hasRunningForegroundService(int var1, int var2);

    public abstract boolean hasStartedUserState(int var1);

    public abstract long inputDispatchingTimedOut(int var1, boolean var2, String var3);

    public abstract boolean inputDispatchingTimedOut(Object var1, String var2, ApplicationInfo var3, String var4, Object var5, boolean var6, String var7);

    public abstract boolean isActivityStartsLoggingEnabled();

    public abstract boolean isAppBad(ApplicationInfo var1);

    public abstract boolean isAppForeground(int var1);

    public abstract boolean isBackgroundActivityStartsEnabled();

    public abstract boolean isBooted();

    public abstract boolean isBooting();

    public abstract boolean isCurrentProfile(int var1);

    public abstract boolean isRuntimeRestarted();

    public abstract boolean isSystemReady();

    public abstract boolean isUidActive(int var1);

    public abstract boolean isUserRunning(int var1, int var2);

    public abstract void killAllBackgroundProcessesExcept(int var1, int var2);

    public abstract void killForegroundAppsForUser(int var1);

    public abstract void killProcess(String var1, int var2, String var3);

    public abstract void killProcessesForRemovedTask(ArrayList<Object> var1);

    public abstract void notifyNetworkPolicyRulesUpdated(int var1, long var2);

    public abstract void onWakefulnessChanged(int var1);

    public abstract void prepareForPossibleShutdown();

    public abstract void registerProcessObserver(IProcessObserver var1);

    public abstract void reportCurKeyguardUsageEvent(boolean var1);

    public abstract void scheduleAppGcs();

    public abstract void sendForegroundProfileChanged(int var1);

    public abstract void setBooted(boolean var1);

    public abstract void setBooting(boolean var1);

    public abstract void setDebugFlagsForStartingActivity(ActivityInfo var1, int var2, ProfilerInfo var3, Object var4);

    public abstract void setDeviceIdleWhitelist(int[] var1, int[] var2);

    public abstract void setHasOverlayUi(int var1, boolean var2);

    public abstract void setPendingIntentAllowBgActivityStarts(IIntentSender var1, IBinder var2, int var3);

    public abstract void setPendingIntentWhitelistDuration(IIntentSender var1, IBinder var2, long var3);

    public abstract void setRunningRemoteAnimation(int var1, boolean var2);

    public abstract void setSwitchingFromSystemUserMessage(String var1);

    public abstract void setSwitchingToSystemUserMessage(String var1);

    public abstract boolean shouldConfirmCredentials(int var1);

    public abstract boolean startIsolatedProcess(String var1, String[] var2, String var3, String var4, int var5, Runnable var6);

    public abstract void startProcess(String var1, ApplicationInfo var2, boolean var3, String var4, ComponentName var5);

    public abstract ComponentName startServiceInPackage(int var1, Intent var2, String var3, boolean var4, String var5, int var6, boolean var7) throws TransactionTooLargeException;

    public abstract void tempWhitelistForPendingIntent(int var1, int var2, int var3, long var4, String var6);

    public abstract void trimApplications();

    public abstract void unregisterProcessObserver(IProcessObserver var1);

    public abstract void updateActivityUsageStats(ComponentName var1, int var2, int var3, IBinder var4, ComponentName var5);

    public abstract void updateBatteryStats(ComponentName var1, int var2, int var3, boolean var4);

    public abstract void updateCpuStats();

    public abstract void updateDeviceIdleTempWhitelist(int[] var1, int var2, boolean var3);

    public abstract void updateForegroundTimeIfOnBattery(String var1, int var2, long var3);

    public abstract void updateOomAdj();

    public abstract void updateOomLevelsForDisplay(int var1);
}

