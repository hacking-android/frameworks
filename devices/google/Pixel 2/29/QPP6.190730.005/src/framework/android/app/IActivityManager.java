/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ApplicationErrorReport;
import android.app.ContentProviderHolder;
import android.app.IActivityController;
import android.app.IApplicationThread;
import android.app.IAssistDataReceiver;
import android.app.IInstrumentationWatcher;
import android.app.IProcessObserver;
import android.app.IServiceConnection;
import android.app.IStopUserCallback;
import android.app.ITaskStackListener;
import android.app.IUiAutomationConnection;
import android.app.IUidObserver;
import android.app.IUserSwitchObserver;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProfilerInfo;
import android.content.ComponentName;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.ParceledListSlice;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IProgressListener;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.WorkSource;
import android.text.TextUtils;
import android.view.IRecentsAnimationRunner;
import com.android.internal.os.IResultReceiver;
import java.util.ArrayList;
import java.util.List;

public interface IActivityManager
extends IInterface {
    public void addInstrumentationResults(IApplicationThread var1, Bundle var2) throws RemoteException;

    public void addPackageDependency(String var1) throws RemoteException;

    public void appNotRespondingViaProvider(IBinder var1) throws RemoteException;

    public void attachApplication(IApplicationThread var1, long var2) throws RemoteException;

    public void backgroundWhitelistUid(int var1) throws RemoteException;

    public void backupAgentCreated(String var1, IBinder var2, int var3) throws RemoteException;

    public boolean bindBackupAgent(String var1, int var2, int var3) throws RemoteException;

    public int bindIsolatedService(IApplicationThread var1, IBinder var2, Intent var3, String var4, IServiceConnection var5, int var6, String var7, String var8, int var9) throws RemoteException;

    @UnsupportedAppUsage
    public int bindService(IApplicationThread var1, IBinder var2, Intent var3, String var4, IServiceConnection var5, int var6, String var7, int var8) throws RemoteException;

    public void bootAnimationComplete() throws RemoteException;

    @UnsupportedAppUsage
    public int broadcastIntent(IApplicationThread var1, Intent var2, String var3, IIntentReceiver var4, int var5, String var6, Bundle var7, String[] var8, int var9, Bundle var10, boolean var11, boolean var12, int var13) throws RemoteException;

    public void cancelIntentSender(IIntentSender var1) throws RemoteException;

    @UnsupportedAppUsage
    public void cancelRecentsAnimation(boolean var1) throws RemoteException;

    @UnsupportedAppUsage
    public void cancelTaskWindowTransition(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public int checkPermission(String var1, int var2, int var3) throws RemoteException;

    public int checkPermissionWithToken(String var1, int var2, int var3, IBinder var4) throws RemoteException;

    public int checkUriPermission(Uri var1, int var2, int var3, int var4, int var5, IBinder var6) throws RemoteException;

    public boolean clearApplicationUserData(String var1, boolean var2, IPackageDataObserver var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public void closeSystemDialogs(String var1) throws RemoteException;

    public void crashApplication(int var1, int var2, String var3, int var4, String var5) throws RemoteException;

    public boolean dumpHeap(String var1, int var2, boolean var3, boolean var4, boolean var5, String var6, ParcelFileDescriptor var7, RemoteCallback var8) throws RemoteException;

    public void dumpHeapFinished(String var1) throws RemoteException;

    public void enterSafeMode() throws RemoteException;

    @UnsupportedAppUsage
    public boolean finishActivity(IBinder var1, int var2, Intent var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public void finishHeavyWeightApp() throws RemoteException;

    public void finishInstrumentation(IApplicationThread var1, int var2, Bundle var3) throws RemoteException;

    public void finishReceiver(IBinder var1, int var2, String var3, Bundle var4, boolean var5, int var6) throws RemoteException;

    @UnsupportedAppUsage
    public void forceStopPackage(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException;

    @UnsupportedAppUsage
    public Configuration getConfiguration() throws RemoteException;

    public ContentProviderHolder getContentProvider(IApplicationThread var1, String var2, String var3, int var4, boolean var5) throws RemoteException;

    public ContentProviderHolder getContentProviderExternal(String var1, int var2, IBinder var3, String var4) throws RemoteException;

    @UnsupportedAppUsage
    public UserInfo getCurrentUser() throws RemoteException;

    @UnsupportedAppUsage
    public List<ActivityManager.RunningTaskInfo> getFilteredTasks(int var1, int var2, int var3) throws RemoteException;

    public ActivityManager.StackInfo getFocusedStackInfo() throws RemoteException;

    public int getForegroundServiceType(ComponentName var1, IBinder var2) throws RemoteException;

    @UnsupportedAppUsage
    public Intent getIntentForIntentSender(IIntentSender var1) throws RemoteException;

    @UnsupportedAppUsage
    public IIntentSender getIntentSender(int var1, String var2, IBinder var3, String var4, int var5, Intent[] var6, String[] var7, int var8, Bundle var9, int var10) throws RemoteException;

    @UnsupportedAppUsage
    public String getLaunchedFromPackage(IBinder var1) throws RemoteException;

    @UnsupportedAppUsage
    public int getLaunchedFromUid(IBinder var1) throws RemoteException;

    public ParcelFileDescriptor getLifeMonitor() throws RemoteException;

    @UnsupportedAppUsage
    public int getLockTaskModeState() throws RemoteException;

    @UnsupportedAppUsage
    public void getMemoryInfo(ActivityManager.MemoryInfo var1) throws RemoteException;

    public int getMemoryTrimLevel() throws RemoteException;

    public void getMyMemoryState(ActivityManager.RunningAppProcessInfo var1) throws RemoteException;

    public String getPackageForIntentSender(IIntentSender var1) throws RemoteException;

    @UnsupportedAppUsage
    public int getPackageProcessState(String var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public int getProcessLimit() throws RemoteException;

    @UnsupportedAppUsage
    public Debug.MemoryInfo[] getProcessMemoryInfo(int[] var1) throws RemoteException;

    @UnsupportedAppUsage
    public long[] getProcessPss(int[] var1) throws RemoteException;

    public List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException;

    @UnsupportedAppUsage
    public String getProviderMimeType(Uri var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public ParceledListSlice getRecentTasks(int var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException;

    public List<ApplicationInfo> getRunningExternalApplications() throws RemoteException;

    public PendingIntent getRunningServiceControlPanel(ComponentName var1) throws RemoteException;

    public int[] getRunningUserIds() throws RemoteException;

    @UnsupportedAppUsage
    public List<ActivityManager.RunningServiceInfo> getServices(int var1, int var2) throws RemoteException;

    public String getTagForIntentSender(IIntentSender var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public Rect getTaskBounds(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public int getTaskForActivity(IBinder var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public ActivityManager.TaskSnapshot getTaskSnapshot(int var1, boolean var2) throws RemoteException;

    public List<ActivityManager.RunningTaskInfo> getTasks(int var1) throws RemoteException;

    public int getUidForIntentSender(IIntentSender var1) throws RemoteException;

    public int getUidProcessState(int var1, String var2) throws RemoteException;

    public void grantUriPermission(IApplicationThread var1, String var2, Uri var3, int var4, int var5) throws RemoteException;

    public void handleApplicationCrash(IBinder var1, ApplicationErrorReport.ParcelableCrashInfo var2) throws RemoteException;

    @UnsupportedAppUsage
    public void handleApplicationStrictModeViolation(IBinder var1, int var2, StrictMode.ViolationInfo var3) throws RemoteException;

    public boolean handleApplicationWtf(IBinder var1, String var2, boolean var3, ApplicationErrorReport.ParcelableCrashInfo var4) throws RemoteException;

    public int handleIncomingUser(int var1, int var2, int var3, boolean var4, boolean var5, String var6, String var7) throws RemoteException;

    @UnsupportedAppUsage
    public void hang(IBinder var1, boolean var2) throws RemoteException;

    public boolean isAppStartModeDisabled(int var1, String var2) throws RemoteException;

    public boolean isBackgroundRestricted(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean isInLockTaskMode() throws RemoteException;

    public boolean isIntentSenderABroadcast(IIntentSender var1) throws RemoteException;

    public boolean isIntentSenderAForegroundService(IIntentSender var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean isIntentSenderAnActivity(IIntentSender var1) throws RemoteException;

    public boolean isIntentSenderTargetedToPackage(IIntentSender var1) throws RemoteException;

    public boolean isTopActivityImmersive() throws RemoteException;

    @UnsupportedAppUsage
    public boolean isTopOfTask(IBinder var1) throws RemoteException;

    public boolean isUidActive(int var1, String var2) throws RemoteException;

    public boolean isUserAMonkey() throws RemoteException;

    @UnsupportedAppUsage
    public boolean isUserRunning(int var1, int var2) throws RemoteException;

    public boolean isVrModePackageEnabled(ComponentName var1) throws RemoteException;

    @UnsupportedAppUsage
    public void killAllBackgroundProcesses() throws RemoteException;

    public void killApplication(String var1, int var2, int var3, String var4) throws RemoteException;

    public void killApplicationProcess(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void killBackgroundProcesses(String var1, int var2) throws RemoteException;

    public void killPackageDependents(String var1, int var2) throws RemoteException;

    public boolean killPids(int[] var1, String var2, boolean var3) throws RemoteException;

    public boolean killProcessesBelowForeground(String var1) throws RemoteException;

    public void killUid(int var1, int var2, String var3) throws RemoteException;

    public void makePackageIdle(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean moveActivityTaskToBack(IBinder var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public void moveTaskToFront(IApplicationThread var1, String var2, int var3, int var4, Bundle var5) throws RemoteException;

    @UnsupportedAppUsage
    public void moveTaskToStack(int var1, int var2, boolean var3) throws RemoteException;

    @UnsupportedAppUsage
    public boolean moveTopActivityToPinnedStack(int var1, Rect var2) throws RemoteException;

    public void noteAlarmFinish(IIntentSender var1, WorkSource var2, int var3, String var4) throws RemoteException;

    public void noteAlarmStart(IIntentSender var1, WorkSource var2, int var3, String var4) throws RemoteException;

    public void noteWakeupAlarm(IIntentSender var1, WorkSource var2, int var3, String var4, String var5) throws RemoteException;

    public void notifyCleartextNetwork(int var1, byte[] var2) throws RemoteException;

    public void notifyLockedProfile(int var1) throws RemoteException;

    public ParcelFileDescriptor openContentUri(String var1) throws RemoteException;

    public IBinder peekService(Intent var1, String var2, String var3) throws RemoteException;

    public void performIdleMaintenance() throws RemoteException;

    @UnsupportedAppUsage
    public void positionTaskInStack(int var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public boolean profileControl(String var1, int var2, boolean var3, ProfilerInfo var4, int var5) throws RemoteException;

    @UnsupportedAppUsage
    public void publishContentProviders(IApplicationThread var1, List<ContentProviderHolder> var2) throws RemoteException;

    public void publishService(IBinder var1, Intent var2, IBinder var3) throws RemoteException;

    public boolean refContentProvider(IBinder var1, int var2, int var3) throws RemoteException;

    public void registerIntentSenderCancelListener(IIntentSender var1, IResultReceiver var2) throws RemoteException;

    @UnsupportedAppUsage
    public void registerProcessObserver(IProcessObserver var1) throws RemoteException;

    @UnsupportedAppUsage
    public Intent registerReceiver(IApplicationThread var1, String var2, IIntentReceiver var3, IntentFilter var4, String var5, int var6, int var7) throws RemoteException;

    @UnsupportedAppUsage
    public void registerTaskStackListener(ITaskStackListener var1) throws RemoteException;

    public void registerUidObserver(IUidObserver var1, int var2, int var3, String var4) throws RemoteException;

    @UnsupportedAppUsage
    public void registerUserSwitchObserver(IUserSwitchObserver var1, String var2) throws RemoteException;

    public void removeContentProvider(IBinder var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public void removeContentProviderExternal(String var1, IBinder var2) throws RemoteException;

    public void removeContentProviderExternalAsUser(String var1, IBinder var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public void removeStack(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean removeTask(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void requestBugReport(int var1) throws RemoteException;

    public void requestSystemServerHeapDump() throws RemoteException;

    public void requestTelephonyBugReport(String var1, String var2) throws RemoteException;

    public void requestWifiBugReport(String var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public void resizeDockedStack(Rect var1, Rect var2, Rect var3, Rect var4, Rect var5) throws RemoteException;

    @UnsupportedAppUsage
    public void resizeStack(int var1, Rect var2, boolean var3, boolean var4, boolean var5, int var6) throws RemoteException;

    @UnsupportedAppUsage
    public void resizeTask(int var1, Rect var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public void restart() throws RemoteException;

    public int restartUserInBackground(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void resumeAppSwitches() throws RemoteException;

    public void revokeUriPermission(IApplicationThread var1, String var2, Uri var3, int var4, int var5) throws RemoteException;

    public void scheduleApplicationInfoChanged(List<String> var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void sendIdleJobTrigger() throws RemoteException;

    public int sendIntentSender(IIntentSender var1, IBinder var2, int var3, Intent var4, String var5, IIntentReceiver var6, String var7, Bundle var8) throws RemoteException;

    public void serviceDoneExecuting(IBinder var1, int var2, int var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public void setActivityController(IActivityController var1, boolean var2) throws RemoteException;

    public void setAgentApp(String var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public void setAlwaysFinish(boolean var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setDebugApp(String var1, boolean var2, boolean var3) throws RemoteException;

    @UnsupportedAppUsage
    public void setDumpHeapDebugLimit(String var1, int var2, long var3, String var5) throws RemoteException;

    public void setFocusedStack(int var1) throws RemoteException;

    public void setHasTopUi(boolean var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setPackageScreenCompatMode(String var1, int var2) throws RemoteException;

    public void setPersistentVrThread(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setProcessImportant(IBinder var1, int var2, boolean var3, String var4) throws RemoteException;

    @UnsupportedAppUsage
    public void setProcessLimit(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean setProcessMemoryTrimLevel(String var1, int var2, int var3) throws RemoteException;

    public void setRenderThread(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setRequestedOrientation(IBinder var1, int var2) throws RemoteException;

    public void setServiceForeground(ComponentName var1, IBinder var2, int var3, Notification var4, int var5, int var6) throws RemoteException;

    @UnsupportedAppUsage
    public void setTaskResizeable(int var1, int var2) throws RemoteException;

    public void setUserIsMonkey(boolean var1) throws RemoteException;

    public void showBootMessage(CharSequence var1, boolean var2) throws RemoteException;

    public void showWaitingForDebugger(IApplicationThread var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean shutdown(int var1) throws RemoteException;

    public void signalPersistentProcesses(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public int startActivity(IApplicationThread var1, String var2, Intent var3, String var4, IBinder var5, String var6, int var7, int var8, ProfilerInfo var9, Bundle var10) throws RemoteException;

    @UnsupportedAppUsage
    public int startActivityAsUser(IApplicationThread var1, String var2, Intent var3, String var4, IBinder var5, String var6, int var7, int var8, ProfilerInfo var9, Bundle var10, int var11) throws RemoteException;

    @UnsupportedAppUsage
    public int startActivityFromRecents(int var1, Bundle var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean startBinderTracking() throws RemoteException;

    public void startConfirmDeviceCredentialIntent(Intent var1, Bundle var2) throws RemoteException;

    public void startDelegateShellPermissionIdentity(int var1, String[] var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean startInstrumentation(ComponentName var1, String var2, int var3, Bundle var4, IInstrumentationWatcher var5, IUiAutomationConnection var6, int var7, String var8) throws RemoteException;

    @UnsupportedAppUsage
    public void startRecentsActivity(Intent var1, IAssistDataReceiver var2, IRecentsAnimationRunner var3) throws RemoteException;

    public ComponentName startService(IApplicationThread var1, Intent var2, String var3, boolean var4, String var5, int var6) throws RemoteException;

    @UnsupportedAppUsage
    public void startSystemLockTaskMode(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean startUserInBackground(int var1) throws RemoteException;

    public boolean startUserInBackgroundWithListener(int var1, IProgressListener var2) throws RemoteException;

    public boolean startUserInForegroundWithListener(int var1, IProgressListener var2) throws RemoteException;

    @UnsupportedAppUsage
    public void stopAppSwitches() throws RemoteException;

    @UnsupportedAppUsage
    public boolean stopBinderTrackingAndDump(ParcelFileDescriptor var1) throws RemoteException;

    public void stopDelegateShellPermissionIdentity() throws RemoteException;

    @UnsupportedAppUsage
    public int stopService(IApplicationThread var1, Intent var2, String var3, int var4) throws RemoteException;

    public boolean stopServiceToken(ComponentName var1, IBinder var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public int stopUser(int var1, boolean var2, IStopUserCallback var3) throws RemoteException;

    @UnsupportedAppUsage
    public void suppressResizeConfigChanges(boolean var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean switchUser(int var1) throws RemoteException;

    public void unbindBackupAgent(ApplicationInfo var1) throws RemoteException;

    public void unbindFinished(IBinder var1, Intent var2, boolean var3) throws RemoteException;

    @UnsupportedAppUsage
    public boolean unbindService(IServiceConnection var1) throws RemoteException;

    public void unbroadcastIntent(IApplicationThread var1, Intent var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public void unhandledBack() throws RemoteException;

    @UnsupportedAppUsage
    public boolean unlockUser(int var1, byte[] var2, byte[] var3, IProgressListener var4) throws RemoteException;

    public void unregisterIntentSenderCancelListener(IIntentSender var1, IResultReceiver var2) throws RemoteException;

    @UnsupportedAppUsage
    public void unregisterProcessObserver(IProcessObserver var1) throws RemoteException;

    @UnsupportedAppUsage
    public void unregisterReceiver(IIntentReceiver var1) throws RemoteException;

    public void unregisterTaskStackListener(ITaskStackListener var1) throws RemoteException;

    public void unregisterUidObserver(IUidObserver var1) throws RemoteException;

    public void unregisterUserSwitchObserver(IUserSwitchObserver var1) throws RemoteException;

    @UnsupportedAppUsage
    public void unstableProviderDied(IBinder var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean updateConfiguration(Configuration var1) throws RemoteException;

    public void updateDeviceOwner(String var1) throws RemoteException;

    public void updateLockTaskPackages(int var1, String[] var2) throws RemoteException;

    @UnsupportedAppUsage
    public void updatePersistentConfiguration(Configuration var1) throws RemoteException;

    public void updateServiceGroup(IServiceConnection var1, int var2, int var3) throws RemoteException;

    public void waitForNetworkStateUpdate(long var1) throws RemoteException;

    public static class Default
    implements IActivityManager {
        @Override
        public void addInstrumentationResults(IApplicationThread iApplicationThread, Bundle bundle) throws RemoteException {
        }

        @Override
        public void addPackageDependency(String string2) throws RemoteException {
        }

        @Override
        public void appNotRespondingViaProvider(IBinder iBinder) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void attachApplication(IApplicationThread iApplicationThread, long l) throws RemoteException {
        }

        @Override
        public void backgroundWhitelistUid(int n) throws RemoteException {
        }

        @Override
        public void backupAgentCreated(String string2, IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public boolean bindBackupAgent(String string2, int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public int bindIsolatedService(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String string2, IServiceConnection iServiceConnection, int n, String string3, String string4, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public int bindService(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String string2, IServiceConnection iServiceConnection, int n, String string3, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public void bootAnimationComplete() throws RemoteException {
        }

        @Override
        public int broadcastIntent(IApplicationThread iApplicationThread, Intent intent, String string2, IIntentReceiver iIntentReceiver, int n, String string3, Bundle bundle, String[] arrstring, int n2, Bundle bundle2, boolean bl, boolean bl2, int n3) throws RemoteException {
            return 0;
        }

        @Override
        public void cancelIntentSender(IIntentSender iIntentSender) throws RemoteException {
        }

        @Override
        public void cancelRecentsAnimation(boolean bl) throws RemoteException {
        }

        @Override
        public void cancelTaskWindowTransition(int n) throws RemoteException {
        }

        @Override
        public int checkPermission(String string2, int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public int checkPermissionWithToken(String string2, int n, int n2, IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public int checkUriPermission(Uri uri, int n, int n2, int n3, int n4, IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public boolean clearApplicationUserData(String string2, boolean bl, IPackageDataObserver iPackageDataObserver, int n) throws RemoteException {
            return false;
        }

        @Override
        public void closeSystemDialogs(String string2) throws RemoteException {
        }

        @Override
        public void crashApplication(int n, int n2, String string2, int n3, String string3) throws RemoteException {
        }

        @Override
        public boolean dumpHeap(String string2, int n, boolean bl, boolean bl2, boolean bl3, String string3, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
            return false;
        }

        @Override
        public void dumpHeapFinished(String string2) throws RemoteException {
        }

        @Override
        public void enterSafeMode() throws RemoteException {
        }

        @Override
        public boolean finishActivity(IBinder iBinder, int n, Intent intent, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void finishHeavyWeightApp() throws RemoteException {
        }

        @Override
        public void finishInstrumentation(IApplicationThread iApplicationThread, int n, Bundle bundle) throws RemoteException {
        }

        @Override
        public void finishReceiver(IBinder iBinder, int n, String string2, Bundle bundle, boolean bl, int n2) throws RemoteException {
        }

        @Override
        public void forceStopPackage(String string2, int n) throws RemoteException {
        }

        @Override
        public List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException {
            return null;
        }

        @Override
        public Configuration getConfiguration() throws RemoteException {
            return null;
        }

        @Override
        public ContentProviderHolder getContentProvider(IApplicationThread iApplicationThread, String string2, String string3, int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public ContentProviderHolder getContentProviderExternal(String string2, int n, IBinder iBinder, String string3) throws RemoteException {
            return null;
        }

        @Override
        public UserInfo getCurrentUser() throws RemoteException {
            return null;
        }

        @Override
        public List<ActivityManager.RunningTaskInfo> getFilteredTasks(int n, int n2, int n3) throws RemoteException {
            return null;
        }

        @Override
        public ActivityManager.StackInfo getFocusedStackInfo() throws RemoteException {
            return null;
        }

        @Override
        public int getForegroundServiceType(ComponentName componentName, IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public Intent getIntentForIntentSender(IIntentSender iIntentSender) throws RemoteException {
            return null;
        }

        @Override
        public IIntentSender getIntentSender(int n, String string2, IBinder iBinder, String string3, int n2, Intent[] arrintent, String[] arrstring, int n3, Bundle bundle, int n4) throws RemoteException {
            return null;
        }

        @Override
        public String getLaunchedFromPackage(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public int getLaunchedFromUid(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public ParcelFileDescriptor getLifeMonitor() throws RemoteException {
            return null;
        }

        @Override
        public int getLockTaskModeState() throws RemoteException {
            return 0;
        }

        @Override
        public void getMemoryInfo(ActivityManager.MemoryInfo memoryInfo) throws RemoteException {
        }

        @Override
        public int getMemoryTrimLevel() throws RemoteException {
            return 0;
        }

        @Override
        public void getMyMemoryState(ActivityManager.RunningAppProcessInfo runningAppProcessInfo) throws RemoteException {
        }

        @Override
        public String getPackageForIntentSender(IIntentSender iIntentSender) throws RemoteException {
            return null;
        }

        @Override
        public int getPackageProcessState(String string2, String string3) throws RemoteException {
            return 0;
        }

        @Override
        public int getProcessLimit() throws RemoteException {
            return 0;
        }

        @Override
        public Debug.MemoryInfo[] getProcessMemoryInfo(int[] arrn) throws RemoteException {
            return null;
        }

        @Override
        public long[] getProcessPss(int[] arrn) throws RemoteException {
            return null;
        }

        @Override
        public List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException {
            return null;
        }

        @Override
        public String getProviderMimeType(Uri uri, int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getRecentTasks(int n, int n2, int n3) throws RemoteException {
            return null;
        }

        @Override
        public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException {
            return null;
        }

        @Override
        public List<ApplicationInfo> getRunningExternalApplications() throws RemoteException {
            return null;
        }

        @Override
        public PendingIntent getRunningServiceControlPanel(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public int[] getRunningUserIds() throws RemoteException {
            return null;
        }

        @Override
        public List<ActivityManager.RunningServiceInfo> getServices(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public String getTagForIntentSender(IIntentSender iIntentSender, String string2) throws RemoteException {
            return null;
        }

        @Override
        public Rect getTaskBounds(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getTaskForActivity(IBinder iBinder, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public ActivityManager.TaskSnapshot getTaskSnapshot(int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public List<ActivityManager.RunningTaskInfo> getTasks(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getUidForIntentSender(IIntentSender iIntentSender) throws RemoteException {
            return 0;
        }

        @Override
        public int getUidProcessState(int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void grantUriPermission(IApplicationThread iApplicationThread, String string2, Uri uri, int n, int n2) throws RemoteException {
        }

        @Override
        public void handleApplicationCrash(IBinder iBinder, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws RemoteException {
        }

        @Override
        public void handleApplicationStrictModeViolation(IBinder iBinder, int n, StrictMode.ViolationInfo violationInfo) throws RemoteException {
        }

        @Override
        public boolean handleApplicationWtf(IBinder iBinder, String string2, boolean bl, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws RemoteException {
            return false;
        }

        @Override
        public int handleIncomingUser(int n, int n2, int n3, boolean bl, boolean bl2, String string2, String string3) throws RemoteException {
            return 0;
        }

        @Override
        public void hang(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public boolean isAppStartModeDisabled(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isBackgroundRestricted(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isInLockTaskMode() throws RemoteException {
            return false;
        }

        @Override
        public boolean isIntentSenderABroadcast(IIntentSender iIntentSender) throws RemoteException {
            return false;
        }

        @Override
        public boolean isIntentSenderAForegroundService(IIntentSender iIntentSender) throws RemoteException {
            return false;
        }

        @Override
        public boolean isIntentSenderAnActivity(IIntentSender iIntentSender) throws RemoteException {
            return false;
        }

        @Override
        public boolean isIntentSenderTargetedToPackage(IIntentSender iIntentSender) throws RemoteException {
            return false;
        }

        @Override
        public boolean isTopActivityImmersive() throws RemoteException {
            return false;
        }

        @Override
        public boolean isTopOfTask(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override
        public boolean isUidActive(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isUserAMonkey() throws RemoteException {
            return false;
        }

        @Override
        public boolean isUserRunning(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isVrModePackageEnabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public void killAllBackgroundProcesses() throws RemoteException {
        }

        @Override
        public void killApplication(String string2, int n, int n2, String string3) throws RemoteException {
        }

        @Override
        public void killApplicationProcess(String string2, int n) throws RemoteException {
        }

        @Override
        public void killBackgroundProcesses(String string2, int n) throws RemoteException {
        }

        @Override
        public void killPackageDependents(String string2, int n) throws RemoteException {
        }

        @Override
        public boolean killPids(int[] arrn, String string2, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean killProcessesBelowForeground(String string2) throws RemoteException {
            return false;
        }

        @Override
        public void killUid(int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public void makePackageIdle(String string2, int n) throws RemoteException {
        }

        @Override
        public boolean moveActivityTaskToBack(IBinder iBinder, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void moveTaskToFront(IApplicationThread iApplicationThread, String string2, int n, int n2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void moveTaskToStack(int n, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public boolean moveTopActivityToPinnedStack(int n, Rect rect) throws RemoteException {
            return false;
        }

        @Override
        public void noteAlarmFinish(IIntentSender iIntentSender, WorkSource workSource, int n, String string2) throws RemoteException {
        }

        @Override
        public void noteAlarmStart(IIntentSender iIntentSender, WorkSource workSource, int n, String string2) throws RemoteException {
        }

        @Override
        public void noteWakeupAlarm(IIntentSender iIntentSender, WorkSource workSource, int n, String string2, String string3) throws RemoteException {
        }

        @Override
        public void notifyCleartextNetwork(int n, byte[] arrby) throws RemoteException {
        }

        @Override
        public void notifyLockedProfile(int n) throws RemoteException {
        }

        @Override
        public ParcelFileDescriptor openContentUri(String string2) throws RemoteException {
            return null;
        }

        @Override
        public IBinder peekService(Intent intent, String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public void performIdleMaintenance() throws RemoteException {
        }

        @Override
        public void positionTaskInStack(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public boolean profileControl(String string2, int n, boolean bl, ProfilerInfo profilerInfo, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void publishContentProviders(IApplicationThread iApplicationThread, List<ContentProviderHolder> list) throws RemoteException {
        }

        @Override
        public void publishService(IBinder iBinder, Intent intent, IBinder iBinder2) throws RemoteException {
        }

        @Override
        public boolean refContentProvider(IBinder iBinder, int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void registerIntentSenderCancelListener(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void registerProcessObserver(IProcessObserver iProcessObserver) throws RemoteException {
        }

        @Override
        public Intent registerReceiver(IApplicationThread iApplicationThread, String string2, IIntentReceiver iIntentReceiver, IntentFilter intentFilter, String string3, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
        }

        @Override
        public void registerUidObserver(IUidObserver iUidObserver, int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public void registerUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver, String string2) throws RemoteException {
        }

        @Override
        public void removeContentProvider(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public void removeContentProviderExternal(String string2, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void removeContentProviderExternalAsUser(String string2, IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void removeStack(int n) throws RemoteException {
        }

        @Override
        public boolean removeTask(int n) throws RemoteException {
            return false;
        }

        @Override
        public void requestBugReport(int n) throws RemoteException {
        }

        @Override
        public void requestSystemServerHeapDump() throws RemoteException {
        }

        @Override
        public void requestTelephonyBugReport(String string2, String string3) throws RemoteException {
        }

        @Override
        public void requestWifiBugReport(String string2, String string3) throws RemoteException {
        }

        @Override
        public void resizeDockedStack(Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5) throws RemoteException {
        }

        @Override
        public void resizeStack(int n, Rect rect, boolean bl, boolean bl2, boolean bl3, int n2) throws RemoteException {
        }

        @Override
        public void resizeTask(int n, Rect rect, int n2) throws RemoteException {
        }

        @Override
        public void restart() throws RemoteException {
        }

        @Override
        public int restartUserInBackground(int n) throws RemoteException {
            return 0;
        }

        @Override
        public void resumeAppSwitches() throws RemoteException {
        }

        @Override
        public void revokeUriPermission(IApplicationThread iApplicationThread, String string2, Uri uri, int n, int n2) throws RemoteException {
        }

        @Override
        public void scheduleApplicationInfoChanged(List<String> list, int n) throws RemoteException {
        }

        @Override
        public void sendIdleJobTrigger() throws RemoteException {
        }

        @Override
        public int sendIntentSender(IIntentSender iIntentSender, IBinder iBinder, int n, Intent intent, String string2, IIntentReceiver iIntentReceiver, String string3, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override
        public void serviceDoneExecuting(IBinder iBinder, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void setActivityController(IActivityController iActivityController, boolean bl) throws RemoteException {
        }

        @Override
        public void setAgentApp(String string2, String string3) throws RemoteException {
        }

        @Override
        public void setAlwaysFinish(boolean bl) throws RemoteException {
        }

        @Override
        public void setDebugApp(String string2, boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void setDumpHeapDebugLimit(String string2, int n, long l, String string3) throws RemoteException {
        }

        @Override
        public void setFocusedStack(int n) throws RemoteException {
        }

        @Override
        public void setHasTopUi(boolean bl) throws RemoteException {
        }

        @Override
        public void setPackageScreenCompatMode(String string2, int n) throws RemoteException {
        }

        @Override
        public void setPersistentVrThread(int n) throws RemoteException {
        }

        @Override
        public void setProcessImportant(IBinder iBinder, int n, boolean bl, String string2) throws RemoteException {
        }

        @Override
        public void setProcessLimit(int n) throws RemoteException {
        }

        @Override
        public boolean setProcessMemoryTrimLevel(String string2, int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void setRenderThread(int n) throws RemoteException {
        }

        @Override
        public void setRequestedOrientation(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void setServiceForeground(ComponentName componentName, IBinder iBinder, int n, Notification notification, int n2, int n3) throws RemoteException {
        }

        @Override
        public void setTaskResizeable(int n, int n2) throws RemoteException {
        }

        @Override
        public void setUserIsMonkey(boolean bl) throws RemoteException {
        }

        @Override
        public void showBootMessage(CharSequence charSequence, boolean bl) throws RemoteException {
        }

        @Override
        public void showWaitingForDebugger(IApplicationThread iApplicationThread, boolean bl) throws RemoteException {
        }

        @Override
        public boolean shutdown(int n) throws RemoteException {
            return false;
        }

        @Override
        public void signalPersistentProcesses(int n) throws RemoteException {
        }

        @Override
        public int startActivity(IApplicationThread iApplicationThread, String string2, Intent intent, String string3, IBinder iBinder, String string4, int n, int n2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override
        public int startActivityAsUser(IApplicationThread iApplicationThread, String string2, Intent intent, String string3, IBinder iBinder, String string4, int n, int n2, ProfilerInfo profilerInfo, Bundle bundle, int n3) throws RemoteException {
            return 0;
        }

        @Override
        public int startActivityFromRecents(int n, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override
        public boolean startBinderTracking() throws RemoteException {
            return false;
        }

        @Override
        public void startConfirmDeviceCredentialIntent(Intent intent, Bundle bundle) throws RemoteException {
        }

        @Override
        public void startDelegateShellPermissionIdentity(int n, String[] arrstring) throws RemoteException {
        }

        @Override
        public boolean startInstrumentation(ComponentName componentName, String string2, int n, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int n2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public void startRecentsActivity(Intent intent, IAssistDataReceiver iAssistDataReceiver, IRecentsAnimationRunner iRecentsAnimationRunner) throws RemoteException {
        }

        @Override
        public ComponentName startService(IApplicationThread iApplicationThread, Intent intent, String string2, boolean bl, String string3, int n) throws RemoteException {
            return null;
        }

        @Override
        public void startSystemLockTaskMode(int n) throws RemoteException {
        }

        @Override
        public boolean startUserInBackground(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean startUserInBackgroundWithListener(int n, IProgressListener iProgressListener) throws RemoteException {
            return false;
        }

        @Override
        public boolean startUserInForegroundWithListener(int n, IProgressListener iProgressListener) throws RemoteException {
            return false;
        }

        @Override
        public void stopAppSwitches() throws RemoteException {
        }

        @Override
        public boolean stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return false;
        }

        @Override
        public void stopDelegateShellPermissionIdentity() throws RemoteException {
        }

        @Override
        public int stopService(IApplicationThread iApplicationThread, Intent intent, String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public boolean stopServiceToken(ComponentName componentName, IBinder iBinder, int n) throws RemoteException {
            return false;
        }

        @Override
        public int stopUser(int n, boolean bl, IStopUserCallback iStopUserCallback) throws RemoteException {
            return 0;
        }

        @Override
        public void suppressResizeConfigChanges(boolean bl) throws RemoteException {
        }

        @Override
        public boolean switchUser(int n) throws RemoteException {
            return false;
        }

        @Override
        public void unbindBackupAgent(ApplicationInfo applicationInfo) throws RemoteException {
        }

        @Override
        public void unbindFinished(IBinder iBinder, Intent intent, boolean bl) throws RemoteException {
        }

        @Override
        public boolean unbindService(IServiceConnection iServiceConnection) throws RemoteException {
            return false;
        }

        @Override
        public void unbroadcastIntent(IApplicationThread iApplicationThread, Intent intent, int n) throws RemoteException {
        }

        @Override
        public void unhandledBack() throws RemoteException {
        }

        @Override
        public boolean unlockUser(int n, byte[] arrby, byte[] arrby2, IProgressListener iProgressListener) throws RemoteException {
            return false;
        }

        @Override
        public void unregisterIntentSenderCancelListener(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override
        public void unregisterProcessObserver(IProcessObserver iProcessObserver) throws RemoteException {
        }

        @Override
        public void unregisterReceiver(IIntentReceiver iIntentReceiver) throws RemoteException {
        }

        @Override
        public void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
        }

        @Override
        public void unregisterUidObserver(IUidObserver iUidObserver) throws RemoteException {
        }

        @Override
        public void unregisterUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver) throws RemoteException {
        }

        @Override
        public void unstableProviderDied(IBinder iBinder) throws RemoteException {
        }

        @Override
        public boolean updateConfiguration(Configuration configuration) throws RemoteException {
            return false;
        }

        @Override
        public void updateDeviceOwner(String string2) throws RemoteException {
        }

        @Override
        public void updateLockTaskPackages(int n, String[] arrstring) throws RemoteException {
        }

        @Override
        public void updatePersistentConfiguration(Configuration configuration) throws RemoteException {
        }

        @Override
        public void updateServiceGroup(IServiceConnection iServiceConnection, int n, int n2) throws RemoteException {
        }

        @Override
        public void waitForNetworkStateUpdate(long l) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IActivityManager {
        private static final String DESCRIPTOR = "android.app.IActivityManager";
        static final int TRANSACTION_addInstrumentationResults = 35;
        static final int TRANSACTION_addPackageDependency = 82;
        static final int TRANSACTION_appNotRespondingViaProvider = 140;
        static final int TRANSACTION_attachApplication = 15;
        static final int TRANSACTION_backgroundWhitelistUid = 192;
        static final int TRANSACTION_backupAgentCreated = 78;
        static final int TRANSACTION_bindBackupAgent = 77;
        static final int TRANSACTION_bindIsolatedService = 27;
        static final int TRANSACTION_bindService = 26;
        static final int TRANSACTION_bootAnimationComplete = 151;
        static final int TRANSACTION_broadcastIntent = 12;
        static final int TRANSACTION_cancelIntentSender = 52;
        static final int TRANSACTION_cancelRecentsAnimation = 147;
        static final int TRANSACTION_cancelTaskWindowTransition = 187;
        static final int TRANSACTION_checkPermission = 42;
        static final int TRANSACTION_checkPermissionWithToken = 152;
        static final int TRANSACTION_checkUriPermission = 43;
        static final int TRANSACTION_clearApplicationUserData = 67;
        static final int TRANSACTION_closeSystemDialogs = 84;
        static final int TRANSACTION_crashApplication = 94;
        static final int TRANSACTION_dumpHeap = 96;
        static final int TRANSACTION_dumpHeapFinished = 160;
        static final int TRANSACTION_enterSafeMode = 56;
        static final int TRANSACTION_finishActivity = 9;
        static final int TRANSACTION_finishHeavyWeightApp = 91;
        static final int TRANSACTION_finishInstrumentation = 36;
        static final int TRANSACTION_finishReceiver = 14;
        static final int TRANSACTION_forceStopPackage = 68;
        static final int TRANSACTION_getAllStackInfos = 133;
        static final int TRANSACTION_getConfiguration = 37;
        static final int TRANSACTION_getContentProvider = 20;
        static final int TRANSACTION_getContentProviderExternal = 108;
        static final int TRANSACTION_getCurrentUser = 113;
        static final int TRANSACTION_getFilteredTasks = 17;
        static final int TRANSACTION_getFocusedStackInfo = 137;
        static final int TRANSACTION_getForegroundServiceType = 63;
        static final int TRANSACTION_getIntentForIntentSender = 128;
        static final int TRANSACTION_getIntentSender = 51;
        static final int TRANSACTION_getLaunchedFromPackage = 129;
        static final int TRANSACTION_getLaunchedFromUid = 114;
        static final int TRANSACTION_getLifeMonitor = 196;
        static final int TRANSACTION_getLockTaskModeState = 158;
        static final int TRANSACTION_getMemoryInfo = 65;
        static final int TRANSACTION_getMemoryTrimLevel = 177;
        static final int TRANSACTION_getMyMemoryState = 111;
        static final int TRANSACTION_getPackageForIntentSender = 53;
        static final int TRANSACTION_getPackageProcessState = 164;
        static final int TRANSACTION_getProcessLimit = 41;
        static final int TRANSACTION_getProcessMemoryInfo = 85;
        static final int TRANSACTION_getProcessPss = 105;
        static final int TRANSACTION_getProcessesInErrorState = 66;
        static final int TRANSACTION_getProviderMimeType = 95;
        static final int TRANSACTION_getRecentTasks = 49;
        static final int TRANSACTION_getRunningAppProcesses = 71;
        static final int TRANSACTION_getRunningExternalApplications = 90;
        static final int TRANSACTION_getRunningServiceControlPanel = 23;
        static final int TRANSACTION_getRunningUserIds = 123;
        static final int TRANSACTION_getServices = 70;
        static final int TRANSACTION_getTagForIntentSender = 143;
        static final int TRANSACTION_getTaskBounds = 141;
        static final int TRANSACTION_getTaskForActivity = 19;
        static final int TRANSACTION_getTaskSnapshot = 188;
        static final int TRANSACTION_getTasks = 16;
        static final int TRANSACTION_getUidForIntentSender = 80;
        static final int TRANSACTION_getUidProcessState = 5;
        static final int TRANSACTION_grantUriPermission = 44;
        static final int TRANSACTION_handleApplicationCrash = 6;
        static final int TRANSACTION_handleApplicationStrictModeViolation = 92;
        static final int TRANSACTION_handleApplicationWtf = 87;
        static final int TRANSACTION_handleIncomingUser = 81;
        static final int TRANSACTION_hang = 132;
        static final int TRANSACTION_isAppStartModeDisabled = 171;
        static final int TRANSACTION_isBackgroundRestricted = 183;
        static final int TRANSACTION_isInLockTaskMode = 145;
        static final int TRANSACTION_isIntentSenderABroadcast = 118;
        static final int TRANSACTION_isIntentSenderAForegroundService = 117;
        static final int TRANSACTION_isIntentSenderAnActivity = 116;
        static final int TRANSACTION_isIntentSenderTargetedToPackage = 103;
        static final int TRANSACTION_isTopActivityImmersive = 93;
        static final int TRANSACTION_isTopOfTask = 150;
        static final int TRANSACTION_isUidActive = 4;
        static final int TRANSACTION_isUserAMonkey = 89;
        static final int TRANSACTION_isUserRunning = 97;
        static final int TRANSACTION_isVrModePackageEnabled = 178;
        static final int TRANSACTION_killAllBackgroundProcesses = 107;
        static final int TRANSACTION_killApplication = 83;
        static final int TRANSACTION_killApplicationProcess = 86;
        static final int TRANSACTION_killBackgroundProcesses = 88;
        static final int TRANSACTION_killPackageDependents = 173;
        static final int TRANSACTION_killPids = 69;
        static final int TRANSACTION_killProcessesBelowForeground = 112;
        static final int TRANSACTION_killUid = 130;
        static final int TRANSACTION_makePackageIdle = 176;
        static final int TRANSACTION_moveActivityTaskToBack = 64;
        static final int TRANSACTION_moveTaskToFront = 18;
        static final int TRANSACTION_moveTaskToStack = 134;
        static final int TRANSACTION_moveTopActivityToPinnedStack = 170;
        static final int TRANSACTION_noteAlarmFinish = 163;
        static final int TRANSACTION_noteAlarmStart = 162;
        static final int TRANSACTION_noteWakeupAlarm = 57;
        static final int TRANSACTION_notifyCleartextNetwork = 155;
        static final int TRANSACTION_notifyLockedProfile = 179;
        static final int TRANSACTION_openContentUri = 1;
        static final int TRANSACTION_peekService = 72;
        static final int TRANSACTION_performIdleMaintenance = 139;
        static final int TRANSACTION_positionTaskInStack = 168;
        static final int TRANSACTION_profileControl = 73;
        static final int TRANSACTION_publishContentProviders = 21;
        static final int TRANSACTION_publishService = 30;
        static final int TRANSACTION_refContentProvider = 22;
        static final int TRANSACTION_registerIntentSenderCancelListener = 54;
        static final int TRANSACTION_registerProcessObserver = 101;
        static final int TRANSACTION_registerReceiver = 10;
        static final int TRANSACTION_registerTaskStackListener = 153;
        static final int TRANSACTION_registerUidObserver = 2;
        static final int TRANSACTION_registerUserSwitchObserver = 121;
        static final int TRANSACTION_removeContentProvider = 58;
        static final int TRANSACTION_removeContentProviderExternal = 109;
        static final int TRANSACTION_removeContentProviderExternalAsUser = 110;
        static final int TRANSACTION_removeStack = 175;
        static final int TRANSACTION_removeTask = 100;
        static final int TRANSACTION_requestBugReport = 125;
        static final int TRANSACTION_requestSystemServerHeapDump = 124;
        static final int TRANSACTION_requestTelephonyBugReport = 126;
        static final int TRANSACTION_requestWifiBugReport = 127;
        static final int TRANSACTION_resizeDockedStack = 174;
        static final int TRANSACTION_resizeStack = 135;
        static final int TRANSACTION_resizeTask = 157;
        static final int TRANSACTION_restart = 138;
        static final int TRANSACTION_restartUserInBackground = 186;
        static final int TRANSACTION_resumeAppSwitches = 76;
        static final int TRANSACTION_revokeUriPermission = 45;
        static final int TRANSACTION_scheduleApplicationInfoChanged = 189;
        static final int TRANSACTION_sendIdleJobTrigger = 181;
        static final int TRANSACTION_sendIntentSender = 182;
        static final int TRANSACTION_serviceDoneExecuting = 50;
        static final int TRANSACTION_setActivityController = 46;
        static final int TRANSACTION_setAgentApp = 32;
        static final int TRANSACTION_setAlwaysFinish = 33;
        static final int TRANSACTION_setDebugApp = 31;
        static final int TRANSACTION_setDumpHeapDebugLimit = 159;
        static final int TRANSACTION_setFocusedStack = 136;
        static final int TRANSACTION_setHasTopUi = 185;
        static final int TRANSACTION_setPackageScreenCompatMode = 98;
        static final int TRANSACTION_setPersistentVrThread = 190;
        static final int TRANSACTION_setProcessImportant = 61;
        static final int TRANSACTION_setProcessLimit = 40;
        static final int TRANSACTION_setProcessMemoryTrimLevel = 142;
        static final int TRANSACTION_setRenderThread = 184;
        static final int TRANSACTION_setRequestedOrientation = 59;
        static final int TRANSACTION_setServiceForeground = 62;
        static final int TRANSACTION_setTaskResizeable = 156;
        static final int TRANSACTION_setUserIsMonkey = 131;
        static final int TRANSACTION_showBootMessage = 106;
        static final int TRANSACTION_showWaitingForDebugger = 47;
        static final int TRANSACTION_shutdown = 74;
        static final int TRANSACTION_signalPersistentProcesses = 48;
        static final int TRANSACTION_startActivity = 7;
        static final int TRANSACTION_startActivityAsUser = 119;
        static final int TRANSACTION_startActivityFromRecents = 148;
        static final int TRANSACTION_startBinderTracking = 166;
        static final int TRANSACTION_startConfirmDeviceCredentialIntent = 180;
        static final int TRANSACTION_startDelegateShellPermissionIdentity = 194;
        static final int TRANSACTION_startInstrumentation = 34;
        static final int TRANSACTION_startRecentsActivity = 146;
        static final int TRANSACTION_startService = 24;
        static final int TRANSACTION_startSystemLockTaskMode = 149;
        static final int TRANSACTION_startUserInBackground = 144;
        static final int TRANSACTION_startUserInBackgroundWithListener = 193;
        static final int TRANSACTION_startUserInForegroundWithListener = 197;
        static final int TRANSACTION_stopAppSwitches = 75;
        static final int TRANSACTION_stopBinderTrackingAndDump = 167;
        static final int TRANSACTION_stopDelegateShellPermissionIdentity = 195;
        static final int TRANSACTION_stopService = 25;
        static final int TRANSACTION_stopServiceToken = 39;
        static final int TRANSACTION_stopUser = 120;
        static final int TRANSACTION_suppressResizeConfigChanges = 169;
        static final int TRANSACTION_switchUser = 99;
        static final int TRANSACTION_unbindBackupAgent = 79;
        static final int TRANSACTION_unbindFinished = 60;
        static final int TRANSACTION_unbindService = 29;
        static final int TRANSACTION_unbroadcastIntent = 13;
        static final int TRANSACTION_unhandledBack = 8;
        static final int TRANSACTION_unlockUser = 172;
        static final int TRANSACTION_unregisterIntentSenderCancelListener = 55;
        static final int TRANSACTION_unregisterProcessObserver = 102;
        static final int TRANSACTION_unregisterReceiver = 11;
        static final int TRANSACTION_unregisterTaskStackListener = 154;
        static final int TRANSACTION_unregisterUidObserver = 3;
        static final int TRANSACTION_unregisterUserSwitchObserver = 122;
        static final int TRANSACTION_unstableProviderDied = 115;
        static final int TRANSACTION_updateConfiguration = 38;
        static final int TRANSACTION_updateDeviceOwner = 165;
        static final int TRANSACTION_updateLockTaskPackages = 161;
        static final int TRANSACTION_updatePersistentConfiguration = 104;
        static final int TRANSACTION_updateServiceGroup = 28;
        static final int TRANSACTION_waitForNetworkStateUpdate = 191;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IActivityManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IActivityManager) {
                return (IActivityManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IActivityManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 197: {
                    return "startUserInForegroundWithListener";
                }
                case 196: {
                    return "getLifeMonitor";
                }
                case 195: {
                    return "stopDelegateShellPermissionIdentity";
                }
                case 194: {
                    return "startDelegateShellPermissionIdentity";
                }
                case 193: {
                    return "startUserInBackgroundWithListener";
                }
                case 192: {
                    return "backgroundWhitelistUid";
                }
                case 191: {
                    return "waitForNetworkStateUpdate";
                }
                case 190: {
                    return "setPersistentVrThread";
                }
                case 189: {
                    return "scheduleApplicationInfoChanged";
                }
                case 188: {
                    return "getTaskSnapshot";
                }
                case 187: {
                    return "cancelTaskWindowTransition";
                }
                case 186: {
                    return "restartUserInBackground";
                }
                case 185: {
                    return "setHasTopUi";
                }
                case 184: {
                    return "setRenderThread";
                }
                case 183: {
                    return "isBackgroundRestricted";
                }
                case 182: {
                    return "sendIntentSender";
                }
                case 181: {
                    return "sendIdleJobTrigger";
                }
                case 180: {
                    return "startConfirmDeviceCredentialIntent";
                }
                case 179: {
                    return "notifyLockedProfile";
                }
                case 178: {
                    return "isVrModePackageEnabled";
                }
                case 177: {
                    return "getMemoryTrimLevel";
                }
                case 176: {
                    return "makePackageIdle";
                }
                case 175: {
                    return "removeStack";
                }
                case 174: {
                    return "resizeDockedStack";
                }
                case 173: {
                    return "killPackageDependents";
                }
                case 172: {
                    return "unlockUser";
                }
                case 171: {
                    return "isAppStartModeDisabled";
                }
                case 170: {
                    return "moveTopActivityToPinnedStack";
                }
                case 169: {
                    return "suppressResizeConfigChanges";
                }
                case 168: {
                    return "positionTaskInStack";
                }
                case 167: {
                    return "stopBinderTrackingAndDump";
                }
                case 166: {
                    return "startBinderTracking";
                }
                case 165: {
                    return "updateDeviceOwner";
                }
                case 164: {
                    return "getPackageProcessState";
                }
                case 163: {
                    return "noteAlarmFinish";
                }
                case 162: {
                    return "noteAlarmStart";
                }
                case 161: {
                    return "updateLockTaskPackages";
                }
                case 160: {
                    return "dumpHeapFinished";
                }
                case 159: {
                    return "setDumpHeapDebugLimit";
                }
                case 158: {
                    return "getLockTaskModeState";
                }
                case 157: {
                    return "resizeTask";
                }
                case 156: {
                    return "setTaskResizeable";
                }
                case 155: {
                    return "notifyCleartextNetwork";
                }
                case 154: {
                    return "unregisterTaskStackListener";
                }
                case 153: {
                    return "registerTaskStackListener";
                }
                case 152: {
                    return "checkPermissionWithToken";
                }
                case 151: {
                    return "bootAnimationComplete";
                }
                case 150: {
                    return "isTopOfTask";
                }
                case 149: {
                    return "startSystemLockTaskMode";
                }
                case 148: {
                    return "startActivityFromRecents";
                }
                case 147: {
                    return "cancelRecentsAnimation";
                }
                case 146: {
                    return "startRecentsActivity";
                }
                case 145: {
                    return "isInLockTaskMode";
                }
                case 144: {
                    return "startUserInBackground";
                }
                case 143: {
                    return "getTagForIntentSender";
                }
                case 142: {
                    return "setProcessMemoryTrimLevel";
                }
                case 141: {
                    return "getTaskBounds";
                }
                case 140: {
                    return "appNotRespondingViaProvider";
                }
                case 139: {
                    return "performIdleMaintenance";
                }
                case 138: {
                    return "restart";
                }
                case 137: {
                    return "getFocusedStackInfo";
                }
                case 136: {
                    return "setFocusedStack";
                }
                case 135: {
                    return "resizeStack";
                }
                case 134: {
                    return "moveTaskToStack";
                }
                case 133: {
                    return "getAllStackInfos";
                }
                case 132: {
                    return "hang";
                }
                case 131: {
                    return "setUserIsMonkey";
                }
                case 130: {
                    return "killUid";
                }
                case 129: {
                    return "getLaunchedFromPackage";
                }
                case 128: {
                    return "getIntentForIntentSender";
                }
                case 127: {
                    return "requestWifiBugReport";
                }
                case 126: {
                    return "requestTelephonyBugReport";
                }
                case 125: {
                    return "requestBugReport";
                }
                case 124: {
                    return "requestSystemServerHeapDump";
                }
                case 123: {
                    return "getRunningUserIds";
                }
                case 122: {
                    return "unregisterUserSwitchObserver";
                }
                case 121: {
                    return "registerUserSwitchObserver";
                }
                case 120: {
                    return "stopUser";
                }
                case 119: {
                    return "startActivityAsUser";
                }
                case 118: {
                    return "isIntentSenderABroadcast";
                }
                case 117: {
                    return "isIntentSenderAForegroundService";
                }
                case 116: {
                    return "isIntentSenderAnActivity";
                }
                case 115: {
                    return "unstableProviderDied";
                }
                case 114: {
                    return "getLaunchedFromUid";
                }
                case 113: {
                    return "getCurrentUser";
                }
                case 112: {
                    return "killProcessesBelowForeground";
                }
                case 111: {
                    return "getMyMemoryState";
                }
                case 110: {
                    return "removeContentProviderExternalAsUser";
                }
                case 109: {
                    return "removeContentProviderExternal";
                }
                case 108: {
                    return "getContentProviderExternal";
                }
                case 107: {
                    return "killAllBackgroundProcesses";
                }
                case 106: {
                    return "showBootMessage";
                }
                case 105: {
                    return "getProcessPss";
                }
                case 104: {
                    return "updatePersistentConfiguration";
                }
                case 103: {
                    return "isIntentSenderTargetedToPackage";
                }
                case 102: {
                    return "unregisterProcessObserver";
                }
                case 101: {
                    return "registerProcessObserver";
                }
                case 100: {
                    return "removeTask";
                }
                case 99: {
                    return "switchUser";
                }
                case 98: {
                    return "setPackageScreenCompatMode";
                }
                case 97: {
                    return "isUserRunning";
                }
                case 96: {
                    return "dumpHeap";
                }
                case 95: {
                    return "getProviderMimeType";
                }
                case 94: {
                    return "crashApplication";
                }
                case 93: {
                    return "isTopActivityImmersive";
                }
                case 92: {
                    return "handleApplicationStrictModeViolation";
                }
                case 91: {
                    return "finishHeavyWeightApp";
                }
                case 90: {
                    return "getRunningExternalApplications";
                }
                case 89: {
                    return "isUserAMonkey";
                }
                case 88: {
                    return "killBackgroundProcesses";
                }
                case 87: {
                    return "handleApplicationWtf";
                }
                case 86: {
                    return "killApplicationProcess";
                }
                case 85: {
                    return "getProcessMemoryInfo";
                }
                case 84: {
                    return "closeSystemDialogs";
                }
                case 83: {
                    return "killApplication";
                }
                case 82: {
                    return "addPackageDependency";
                }
                case 81: {
                    return "handleIncomingUser";
                }
                case 80: {
                    return "getUidForIntentSender";
                }
                case 79: {
                    return "unbindBackupAgent";
                }
                case 78: {
                    return "backupAgentCreated";
                }
                case 77: {
                    return "bindBackupAgent";
                }
                case 76: {
                    return "resumeAppSwitches";
                }
                case 75: {
                    return "stopAppSwitches";
                }
                case 74: {
                    return "shutdown";
                }
                case 73: {
                    return "profileControl";
                }
                case 72: {
                    return "peekService";
                }
                case 71: {
                    return "getRunningAppProcesses";
                }
                case 70: {
                    return "getServices";
                }
                case 69: {
                    return "killPids";
                }
                case 68: {
                    return "forceStopPackage";
                }
                case 67: {
                    return "clearApplicationUserData";
                }
                case 66: {
                    return "getProcessesInErrorState";
                }
                case 65: {
                    return "getMemoryInfo";
                }
                case 64: {
                    return "moveActivityTaskToBack";
                }
                case 63: {
                    return "getForegroundServiceType";
                }
                case 62: {
                    return "setServiceForeground";
                }
                case 61: {
                    return "setProcessImportant";
                }
                case 60: {
                    return "unbindFinished";
                }
                case 59: {
                    return "setRequestedOrientation";
                }
                case 58: {
                    return "removeContentProvider";
                }
                case 57: {
                    return "noteWakeupAlarm";
                }
                case 56: {
                    return "enterSafeMode";
                }
                case 55: {
                    return "unregisterIntentSenderCancelListener";
                }
                case 54: {
                    return "registerIntentSenderCancelListener";
                }
                case 53: {
                    return "getPackageForIntentSender";
                }
                case 52: {
                    return "cancelIntentSender";
                }
                case 51: {
                    return "getIntentSender";
                }
                case 50: {
                    return "serviceDoneExecuting";
                }
                case 49: {
                    return "getRecentTasks";
                }
                case 48: {
                    return "signalPersistentProcesses";
                }
                case 47: {
                    return "showWaitingForDebugger";
                }
                case 46: {
                    return "setActivityController";
                }
                case 45: {
                    return "revokeUriPermission";
                }
                case 44: {
                    return "grantUriPermission";
                }
                case 43: {
                    return "checkUriPermission";
                }
                case 42: {
                    return "checkPermission";
                }
                case 41: {
                    return "getProcessLimit";
                }
                case 40: {
                    return "setProcessLimit";
                }
                case 39: {
                    return "stopServiceToken";
                }
                case 38: {
                    return "updateConfiguration";
                }
                case 37: {
                    return "getConfiguration";
                }
                case 36: {
                    return "finishInstrumentation";
                }
                case 35: {
                    return "addInstrumentationResults";
                }
                case 34: {
                    return "startInstrumentation";
                }
                case 33: {
                    return "setAlwaysFinish";
                }
                case 32: {
                    return "setAgentApp";
                }
                case 31: {
                    return "setDebugApp";
                }
                case 30: {
                    return "publishService";
                }
                case 29: {
                    return "unbindService";
                }
                case 28: {
                    return "updateServiceGroup";
                }
                case 27: {
                    return "bindIsolatedService";
                }
                case 26: {
                    return "bindService";
                }
                case 25: {
                    return "stopService";
                }
                case 24: {
                    return "startService";
                }
                case 23: {
                    return "getRunningServiceControlPanel";
                }
                case 22: {
                    return "refContentProvider";
                }
                case 21: {
                    return "publishContentProviders";
                }
                case 20: {
                    return "getContentProvider";
                }
                case 19: {
                    return "getTaskForActivity";
                }
                case 18: {
                    return "moveTaskToFront";
                }
                case 17: {
                    return "getFilteredTasks";
                }
                case 16: {
                    return "getTasks";
                }
                case 15: {
                    return "attachApplication";
                }
                case 14: {
                    return "finishReceiver";
                }
                case 13: {
                    return "unbroadcastIntent";
                }
                case 12: {
                    return "broadcastIntent";
                }
                case 11: {
                    return "unregisterReceiver";
                }
                case 10: {
                    return "registerReceiver";
                }
                case 9: {
                    return "finishActivity";
                }
                case 8: {
                    return "unhandledBack";
                }
                case 7: {
                    return "startActivity";
                }
                case 6: {
                    return "handleApplicationCrash";
                }
                case 5: {
                    return "getUidProcessState";
                }
                case 4: {
                    return "isUidActive";
                }
                case 3: {
                    return "unregisterUidObserver";
                }
                case 2: {
                    return "registerUidObserver";
                }
                case 1: 
            }
            return "openContentUri";
        }

        public static boolean setDefaultImpl(IActivityManager iActivityManager) {
            if (Proxy.sDefaultImpl == null && iActivityManager != null) {
                Proxy.sDefaultImpl = iActivityManager;
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
                boolean bl15 = false;
                boolean bl16 = false;
                boolean bl17 = false;
                boolean bl18 = false;
                boolean bl19 = false;
                boolean bl20 = false;
                boolean bl21 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 197: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.startUserInForegroundWithListener(((Parcel)object).readInt(), IProgressListener.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 196: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLifeMonitor();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParcelFileDescriptor)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 195: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopDelegateShellPermissionIdentity();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 194: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startDelegateShellPermissionIdentity(((Parcel)object).readInt(), ((Parcel)object).createStringArray());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 193: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.startUserInBackgroundWithListener(((Parcel)object).readInt(), IProgressListener.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 192: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.backgroundWhitelistUid(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 191: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.waitForNetworkStateUpdate(((Parcel)object).readLong());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 190: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setPersistentVrThread(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 189: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.scheduleApplicationInfoChanged(((Parcel)object).createStringArrayList(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 188: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl21 = ((Parcel)object).readInt() != 0;
                        object = this.getTaskSnapshot(n, bl21);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ActivityManager.TaskSnapshot)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 187: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelTaskWindowTransition(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 186: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.restartUserInBackground(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 185: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.setHasTopUi(bl21);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 184: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRenderThread(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 183: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isBackgroundRestricted(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 182: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IIntentSender iIntentSender = IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        n = ((Parcel)object).readInt();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string2 = ((Parcel)object).readString();
                        IIntentReceiver iIntentReceiver = IIntentReceiver.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string3 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.sendIntentSender(iIntentSender, iBinder, n, intent, string2, iIntentReceiver, string3, (Bundle)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 181: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.sendIdleJobTrigger();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 180: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startConfirmDeviceCredentialIntent(intent, (Bundle)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 179: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyLockedProfile(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 178: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isVrModePackageEnabled((ComponentName)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 177: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getMemoryTrimLevel();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 176: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.makePackageIdle(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 175: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeStack(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 174: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect2 = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect3 = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect4 = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.resizeDockedStack(rect, rect2, rect3, rect4, (Rect)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 173: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.killPackageDependents(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 172: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.unlockUser(((Parcel)object).readInt(), ((Parcel)object).createByteArray(), ((Parcel)object).createByteArray(), IProgressListener.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 171: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isAppStartModeDisabled(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 170: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.moveTopActivityToPinnedStack(n, (Rect)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 169: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl21 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.suppressResizeConfigChanges(bl21);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 168: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.positionTaskInStack(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 167: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.stopBinderTrackingAndDump((ParcelFileDescriptor)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 166: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.startBinderTracking() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 165: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateDeviceOwner(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 164: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPackageProcessState(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 163: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IIntentSender iIntentSender = IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder());
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteAlarmFinish(iIntentSender, workSource, ((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 162: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IIntentSender iIntentSender = IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder());
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteAlarmStart(iIntentSender, workSource, ((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 161: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateLockTaskPackages(((Parcel)object).readInt(), ((Parcel)object).createStringArray());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 160: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dumpHeapFinished(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 159: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDumpHeapDebugLimit(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 158: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getLockTaskModeState();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 157: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.resizeTask(n, rect, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 156: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setTaskResizeable(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 155: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyCleartextNetwork(((Parcel)object).readInt(), ((Parcel)object).createByteArray());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 154: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterTaskStackListener(ITaskStackListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 153: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerTaskStackListener(ITaskStackListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 152: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkPermissionWithToken(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 151: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.bootAnimationComplete();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 150: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isTopOfTask(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 149: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startSystemLockTaskMode(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 148: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startActivityFromRecents(n, (Bundle)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 147: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl21 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.cancelRecentsAnimation(bl21);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 146: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startRecentsActivity(intent, IAssistDataReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()), IRecentsAnimationRunner.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 145: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInLockTaskMode() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 144: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.startUserInBackground(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 143: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTagForIntentSender(IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 142: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setProcessMemoryTrimLevel(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 141: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTaskBounds(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((Rect)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 140: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.appNotRespondingViaProvider(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 139: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.performIdleMaintenance();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 138: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.restart();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 137: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getFocusedStackInfo();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ActivityManager.StackInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 136: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFocusedStack(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 135: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        bl21 = ((Parcel)object).readInt() != 0;
                        bl18 = ((Parcel)object).readInt() != 0;
                        bl20 = ((Parcel)object).readInt() != 0;
                        this.resizeStack(n, rect, bl21, bl18, bl20, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 134: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        bl21 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.moveTaskToStack(n2, n, bl21);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 133: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllStackInfos();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 132: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl21 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.hang(iBinder, bl21);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 131: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl21 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.setUserIsMonkey(bl21);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 130: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.killUid(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 129: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLaunchedFromPackage(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 128: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getIntentForIntentSender(IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((Intent)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 127: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestWifiBugReport(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 126: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestTelephonyBugReport(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 125: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestBugReport(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 124: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestSystemServerHeapDump();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 123: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRunningUserIds();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeIntArray((int[])object);
                        return true;
                    }
                    case 122: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterUserSwitchObserver(IUserSwitchObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 121: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerUserSwitchObserver(IUserSwitchObserver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 120: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl21 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        n = this.stopUser(n, bl21, IStopUserCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 119: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string4 = ((Parcel)object).readString();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string5 = ((Parcel)object).readString();
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string6 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        ProfilerInfo profilerInfo = ((Parcel)object).readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startActivityAsUser(iApplicationThread, string4, intent, string5, iBinder, string6, n, n2, profilerInfo, bundle, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 118: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isIntentSenderABroadcast(IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 117: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isIntentSenderAForegroundService(IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 116: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isIntentSenderAnActivity(IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 115: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unstableProviderDied(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 114: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getLaunchedFromUid(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 113: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCurrentUser();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((UserInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 112: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.killProcessesBelowForeground(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 111: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = new ActivityManager.RunningAppProcessInfo();
                        this.getMyMemoryState((ActivityManager.RunningAppProcessInfo)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(1);
                        ((ActivityManager.RunningAppProcessInfo)object).writeToParcel((Parcel)object2, 1);
                        return true;
                    }
                    case 110: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeContentProviderExternalAsUser(((Parcel)object).readString(), ((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 109: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeContentProviderExternal(((Parcel)object).readString(), ((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 108: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getContentProviderExternal(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ContentProviderHolder)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 107: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.killAllBackgroundProcesses();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 106: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        CharSequence charSequence = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        bl21 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.showBootMessage(charSequence, bl21);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 105: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getProcessPss(((Parcel)object).createIntArray());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeLongArray((long[])object);
                        return true;
                    }
                    case 104: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Configuration.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updatePersistentConfiguration((Configuration)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 103: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isIntentSenderTargetedToPackage(IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 102: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterProcessObserver(IProcessObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 101: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerProcessObserver(IProcessObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 100: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeTask(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 99: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.switchUser(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 98: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setPackageScreenCompatMode(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 97: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUserRunning(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 96: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string7 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl21 = ((Parcel)object).readInt() != 0;
                        bl18 = ((Parcel)object).readInt() != 0;
                        bl20 = ((Parcel)object).readInt() != 0;
                        String string8 = ((Parcel)object).readString();
                        ParcelFileDescriptor parcelFileDescriptor = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.dumpHeap(string7, n, bl21, bl18, bl20, string8, parcelFileDescriptor, (RemoteCallback)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 95: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getProviderMimeType(uri, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 94: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.crashApplication(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 93: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isTopActivityImmersive() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 92: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? StrictMode.ViolationInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.handleApplicationStrictModeViolation(iBinder, n, (StrictMode.ViolationInfo)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 91: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishHeavyWeightApp();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 90: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRunningExternalApplications();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 89: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUserAMonkey() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 88: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.killBackgroundProcesses(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 87: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string9 = ((Parcel)object).readString();
                        bl21 = bl8;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        object = ((Parcel)object).readInt() != 0 ? ApplicationErrorReport.ParcelableCrashInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.handleApplicationWtf(iBinder, string9, bl21, (ApplicationErrorReport.ParcelableCrashInfo)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 86: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.killApplicationProcess(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 85: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getProcessMemoryInfo(((Parcel)object).createIntArray());
                        ((Parcel)object2).writeNoException();
                        object2.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 84: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.closeSystemDialogs(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 83: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.killApplication(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 82: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addPackageDependency(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 81: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        int n3 = ((Parcel)object).readInt();
                        bl21 = ((Parcel)object).readInt() != 0;
                        bl18 = ((Parcel)object).readInt() != 0;
                        n = this.handleIncomingUser(n, n2, n3, bl21, bl18, ((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 80: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getUidForIntentSender(IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 79: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ApplicationInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.unbindBackupAgent((ApplicationInfo)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 78: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.backupAgentCreated(((Parcel)object).readString(), ((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 77: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.bindBackupAgent(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 76: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resumeAppSwitches();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 75: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopAppSwitches();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.shutdown(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string10 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl21 = ((Parcel)object).readInt() != 0;
                        ProfilerInfo profilerInfo = ((Parcel)object).readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.profileControl(string10, n, bl21, profilerInfo, ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.peekService(intent, ((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRunningAppProcesses();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getServices(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        int[] arrn = ((Parcel)object).createIntArray();
                        String string11 = ((Parcel)object).readString();
                        bl21 = bl9;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        n = this.killPids(arrn, string11, bl21) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.forceStopPackage(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string12 = ((Parcel)object).readString();
                        bl21 = bl10;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        n = this.clearApplicationUserData(string12, bl21, IPackageDataObserver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getProcessesInErrorState();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = new ActivityManager.MemoryInfo();
                        this.getMemoryInfo((ActivityManager.MemoryInfo)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(1);
                        ((ActivityManager.MemoryInfo)object).writeToParcel((Parcel)object2, 1);
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl21 = bl11;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        n = this.moveActivityTaskToBack(iBinder, bl21) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getForegroundServiceType(componentName, ((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        n = ((Parcel)object).readInt();
                        Notification notification = ((Parcel)object).readInt() != 0 ? Notification.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setServiceForeground(componentName, iBinder, n, notification, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        n = ((Parcel)object).readInt();
                        bl21 = bl12;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.setProcessImportant(iBinder, n, bl21, ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        bl21 = bl13;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.unbindFinished(iBinder, intent, bl21);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRequestedOrientation(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readStrongBinder();
                        bl21 = bl14;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.removeContentProvider((IBinder)object2, bl21);
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IIntentSender iIntentSender = IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder());
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteWakeupAlarm(iIntentSender, workSource, ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enterSafeMode();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterIntentSenderCancelListener(IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder()), IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerIntentSenderCancelListener(IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder()), IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPackageForIntentSender(IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelIntentSender(IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string13 = ((Parcel)object).readString();
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string14 = ((Parcel)object).readString();
                        int n4 = ((Parcel)object).readInt();
                        Intent[] arrintent = ((Parcel)object).createTypedArray(Intent.CREATOR);
                        String[] arrstring = ((Parcel)object).createStringArray();
                        n2 = ((Parcel)object).readInt();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getIntentSender(n, string13, iBinder, string14, n4, arrintent, arrstring, n2, bundle, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        object = object != null ? object.asBinder() : null;
                        ((Parcel)object2).writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.serviceDoneExecuting(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRecentTasks(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.signalPersistentProcesses(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        bl21 = bl15;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.showWaitingForDebugger(iApplicationThread, bl21);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IActivityController iActivityController = IActivityController.Stub.asInterface(((Parcel)object).readStrongBinder());
                        bl21 = bl16;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.setActivityController(iActivityController, bl21);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string15 = ((Parcel)object).readString();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.revokeUriPermission(iApplicationThread, string15, uri, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string16 = ((Parcel)object).readString();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.grantUriPermission(iApplicationThread, string16, uri, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.checkUriPermission(uri, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkPermission(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getProcessLimit();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setProcessLimit(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.stopServiceToken(componentName, ((Parcel)object).readStrongBinder(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Configuration.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.updateConfiguration((Configuration)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getConfiguration();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((Configuration)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.finishInstrumentation(iApplicationThread, n, (Bundle)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addInstrumentationResults(iApplicationThread, (Bundle)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string17 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startInstrumentation(componentName, string17, n, bundle, IInstrumentationWatcher.Stub.asInterface(((Parcel)object).readStrongBinder()), IUiAutomationConnection.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl21 = bl17;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        this.setAlwaysFinish(bl21);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setAgentApp(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string18 = ((Parcel)object).readString();
                        bl21 = ((Parcel)object).readInt() != 0;
                        if (((Parcel)object).readInt() != 0) {
                            bl18 = true;
                        }
                        this.setDebugApp(string18, bl21, bl18);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.publishService(iBinder, intent, ((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.unbindService(IServiceConnection.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateServiceGroup(IServiceConnection.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.bindIsolatedService(iApplicationThread, iBinder, intent, ((Parcel)object).readString(), IServiceConnection.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.bindService(iApplicationThread, iBinder, intent, ((Parcel)object).readString(), IServiceConnection.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.stopService(iApplicationThread, intent, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string19 = ((Parcel)object).readString();
                        bl21 = ((Parcel)object).readInt() != 0;
                        object = this.startService(iApplicationThread, intent, string19, bl21, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ComponentName)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getRunningServiceControlPanel((ComponentName)object);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((PendingIntent)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.refContentProvider(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.publishContentProviders(IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).createTypedArrayList(ContentProviderHolder.CREATOR));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string20 = ((Parcel)object).readString();
                        String string21 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl21 = ((Parcel)object).readInt() != 0;
                        object = this.getContentProvider(iApplicationThread, string20, string21, n, bl21);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ContentProviderHolder)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl21 = bl19;
                        if (((Parcel)object).readInt() != 0) {
                            bl21 = true;
                        }
                        n = this.getTaskForActivity(iBinder, bl21);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string22 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.moveTaskToFront(iApplicationThread, string22, n, n2, (Bundle)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getFilteredTasks(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTasks(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.attachApplication(IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readLong());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        n = ((Parcel)object).readInt();
                        String string23 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        bl21 = ((Parcel)object).readInt() != 0;
                        this.finishReceiver(iBinder, n, string23, (Bundle)object2, bl21, ((Parcel)object).readInt());
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.unbroadcastIntent(iApplicationThread, intent, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string24 = ((Parcel)object).readString();
                        IIntentReceiver iIntentReceiver = IIntentReceiver.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        String string25 = ((Parcel)object).readString();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        String[] arrstring = ((Parcel)object).createStringArray();
                        n2 = ((Parcel)object).readInt();
                        Bundle bundle2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        bl21 = ((Parcel)object).readInt() != 0;
                        bl18 = bl20;
                        if (((Parcel)object).readInt() != 0) {
                            bl18 = true;
                        }
                        n = this.broadcastIntent(iApplicationThread, intent, string24, iIntentReceiver, n, string25, bundle, arrstring, n2, bundle2, bl21, bl18, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterReceiver(IIntentReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string26 = ((Parcel)object).readString();
                        IIntentReceiver iIntentReceiver = IIntentReceiver.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IntentFilter intentFilter = ((Parcel)object).readInt() != 0 ? IntentFilter.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.registerReceiver(iApplicationThread, string26, iIntentReceiver, intentFilter, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((Intent)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        n = ((Parcel)object).readInt();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.finishActivity(iBinder, n, intent, ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unhandledBack();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string27 = ((Parcel)object).readString();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string28 = ((Parcel)object).readString();
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string29 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        ProfilerInfo profilerInfo = ((Parcel)object).readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startActivity(iApplicationThread, string27, intent, string28, iBinder, string29, n, n2, profilerInfo, (Bundle)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object = ((Parcel)object).readInt() != 0 ? ApplicationErrorReport.ParcelableCrashInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.handleApplicationCrash(iBinder, (ApplicationErrorReport.ParcelableCrashInfo)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getUidProcessState(((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUidActive(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterUidObserver(IUidObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerUidObserver(IUidObserver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.openContentUri(((Parcel)object).readString());
                ((Parcel)object2).writeNoException();
                if (object != null) {
                    ((Parcel)object2).writeInt(1);
                    ((ParcelFileDescriptor)object).writeToParcel((Parcel)object2, 1);
                } else {
                    ((Parcel)object2).writeInt(0);
                }
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IActivityManager {
            public static IActivityManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addInstrumentationResults(IApplicationThread iApplicationThread, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addInstrumentationResults(iApplicationThread, bundle);
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
            public void addPackageDependency(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(82, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPackageDependency(string2);
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
            public void appNotRespondingViaProvider(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(140, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().appNotRespondingViaProvider(iBinder);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void attachApplication(IApplicationThread iApplicationThread, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().attachApplication(iApplicationThread, l);
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
            public void backgroundWhitelistUid(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(192, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().backgroundWhitelistUid(n);
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
            public void backupAgentCreated(String string2, IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(78, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().backupAgentCreated(string2, iBinder, n);
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
            public boolean bindBackupAgent(String string2, int n, int n2) throws RemoteException {
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
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(77, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().bindBackupAgent(string2, n, n2);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int bindIsolatedService(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String string2, IServiceConnection iServiceConnection, int n, String string3, String string4, int n2) throws RemoteException {
                Parcel parcel2;
                void var1_5;
                Parcel parcel;
                block11 : {
                    IBinder iBinder2;
                    Object var12_16;
                    block10 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        var12_16 = null;
                        iBinder2 = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder2);
                        try {
                            parcel2.writeStrongBinder(iBinder);
                            if (intent != null) {
                                parcel2.writeInt(1);
                                intent.writeToParcel(parcel2, 0);
                                break block10;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeString(string2);
                        iBinder2 = var12_16;
                        if (iServiceConnection != null) {
                            iBinder2 = iServiceConnection.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder2);
                        parcel2.writeInt(n);
                        parcel2.writeString(string3);
                        parcel2.writeString(string4);
                        parcel2.writeInt(n2);
                        if (!this.mRemote.transact(27, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().bindIsolatedService(iApplicationThread, iBinder, intent, string2, iServiceConnection, n, string3, string4, n2);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block11;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_5;
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
            public int bindService(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String string2, IServiceConnection iServiceConnection, int n, String string3, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_6;
                block13 : {
                    Object var11_16;
                    IBinder iBinder2;
                    block12 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        var11_16 = null;
                        iBinder2 = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder2);
                        try {
                            parcel2.writeStrongBinder(iBinder);
                            if (intent != null) {
                                parcel2.writeInt(1);
                                intent.writeToParcel(parcel2, 0);
                                break block12;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeString(string2);
                        iBinder2 = var11_16;
                        if (iServiceConnection != null) {
                            iBinder2 = iServiceConnection.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder2);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel2.writeInt(n);
                        parcel2.writeString(string3);
                        parcel2.writeInt(n2);
                        if (!this.mRemote.transact(26, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().bindService(iApplicationThread, iBinder, intent, string2, iServiceConnection, n, string3, n2);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_6;
            }

            @Override
            public void bootAnimationComplete() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(151, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().bootAnimationComplete();
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
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int broadcastIntent(IApplicationThread var1_1, Intent var2_6, String var3_7, IIntentReceiver var4_8, int var5_9, String var6_10, Bundle var7_11, String[] var8_12, int var9_13, Bundle var10_14, boolean var11_15, boolean var12_16, int var13_17) throws RemoteException {
                block22 : {
                    block23 : {
                        block21 : {
                            block20 : {
                                block19 : {
                                    block18 : {
                                        block17 : {
                                            block16 : {
                                                var14_18 = Parcel.obtain();
                                                var15_19 = Parcel.obtain();
                                                var14_18.writeInterfaceToken("android.app.IActivityManager");
                                                var16_20 = null;
                                                if (var1_1 == null) break block16;
                                                try {
                                                    var17_21 = var1_1.asBinder();
                                                    break block17;
                                                }
                                                catch (Throwable var1_2) {
                                                    break block22;
                                                }
                                            }
                                            var17_21 = null;
                                        }
                                        var14_18.writeStrongBinder((IBinder)var17_21);
                                        var18_22 = 1;
                                        if (var2_6 == null) break block18;
                                        var14_18.writeInt(1);
                                        var2_6.writeToParcel(var14_18, 0);
                                        ** GOTO lbl26
                                    }
                                    var14_18.writeInt(0);
lbl26: // 2 sources:
                                    var14_18.writeString(var3_7);
                                    var17_21 = var16_20;
                                    if (var4_8 == null) break block19;
                                    var17_21 = var4_8.asBinder();
                                }
                                var14_18.writeStrongBinder((IBinder)var17_21);
                                var14_18.writeInt(var5_9);
                                var14_18.writeString(var6_10);
                                if (var7_11 == null) break block20;
                                var14_18.writeInt(1);
                                var7_11.writeToParcel(var14_18, 0);
                                ** GOTO lbl44
                            }
                            var14_18.writeInt(0);
lbl44: // 2 sources:
                            var14_18.writeStringArray(var8_12);
                            var14_18.writeInt(var9_13);
                            if (var10_14 == null) break block21;
                            var14_18.writeInt(1);
                            var10_14.writeToParcel(var14_18, 0);
                            ** GOTO lbl54
                        }
                        var14_18.writeInt(0);
lbl54: // 2 sources:
                        var19_23 = var11_15 != false ? 1 : 0;
                        var14_18.writeInt(var19_23);
                        var19_23 = var12_16 != false ? var18_22 : 0;
                        var14_18.writeInt(var19_23);
                        var14_18.writeInt(var13_17);
                        if (this.mRemote.transact(12, var14_18, var15_19, 0) || Stub.getDefaultImpl() == null) break block23;
                        var17_21 = Stub.getDefaultImpl();
                        try {
                            var5_9 = var17_21.broadcastIntent((IApplicationThread)var1_1, var2_6, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12, var9_13, var10_14, var11_15, var12_16, var13_17);
                            var15_19.recycle();
                            var14_18.recycle();
                            return var5_9;
                        }
                        catch (Throwable var1_3) {}
                    }
                    var1_1 = var15_19;
                    var1_1.readException();
                    var5_9 = var1_1.readInt();
                    var1_1.recycle();
                    var14_18.recycle();
                    return var5_9;
                    break block22;
                    catch (Throwable var1_4) {
                        // empty catch block
                    }
                }
                var15_19.recycle();
                var14_18.recycle();
                throw var1_5;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void cancelIntentSender(IIntentSender iIntentSender) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iIntentSender != null ? iIntentSender.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(52, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelIntentSender(iIntentSender);
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
            public void cancelRecentsAnimation(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(147, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelRecentsAnimation(bl);
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
            public void cancelTaskWindowTransition(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(187, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelTaskWindowTransition(n);
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
            public int checkPermission(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().checkPermission(string2, n, n2);
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
            public int checkPermissionWithToken(String string2, int n, int n2, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(152, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().checkPermissionWithToken(string2, n, n2, iBinder);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int checkUriPermission(Uri uri, int n, int n2, int n3, int n4, IBinder iBinder) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (uri != null) {
                            parcel2.writeInt(1);
                            uri.writeToParcel(parcel2, 0);
                            break block15;
                        }
                        parcel2.writeInt(0);
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n4);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeStrongBinder(iBinder);
                        if (!this.mRemote.transact(43, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().checkUriPermission(uri, n, n2, n3, n4, iBinder);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
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

            @Override
            public boolean clearApplicationUserData(String string2, boolean bl, IPackageDataObserver iPackageDataObserver, int n) throws RemoteException {
                boolean bl2;
                Parcel parcel;
                Parcel parcel2;
                block8 : {
                    IBinder iBinder;
                    block7 : {
                        block6 : {
                            int n2;
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                parcel2.writeString(string2);
                                bl2 = true;
                                n2 = bl ? 1 : 0;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            parcel2.writeInt(n2);
                            if (iPackageDataObserver == null) break block6;
                            iBinder = iPackageDataObserver.asBinder();
                            break block7;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    parcel2.writeInt(n);
                    if (this.mRemote.transact(67, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block8;
                    bl = Stub.getDefaultImpl().clearApplicationUserData(string2, bl, iPackageDataObserver, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void closeSystemDialogs(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(84, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeSystemDialogs(string2);
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
            public void crashApplication(int n, int n2, String string2, int n3, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeInt(n3);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(94, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().crashApplication(n, n2, string2, n3, string3);
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
            public boolean dumpHeap(String string2, int n, boolean bl, boolean bl2, boolean bl3, String string3, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_5;
                block12 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel.writeInt(n);
                        boolean bl4 = true;
                        int n2 = bl ? 1 : 0;
                        parcel.writeInt(n2);
                        n2 = bl2 ? 1 : 0;
                        parcel.writeInt(n2);
                        n2 = bl3 ? 1 : 0;
                        parcel.writeInt(n2);
                        parcel.writeString(string3);
                        if (parcelFileDescriptor != null) {
                            parcel.writeInt(1);
                            parcelFileDescriptor.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (remoteCallback != null) {
                            parcel.writeInt(1);
                            remoteCallback.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!this.mRemote.transact(96, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().dumpHeap(string2, n, bl, bl2, bl3, string3, parcelFileDescriptor, remoteCallback);
                            parcel2.recycle();
                            parcel.recycle();
                            return bl;
                        }
                        parcel2.readException();
                        n = parcel2.readInt();
                        bl = n != 0 ? bl4 : false;
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_5;
            }

            @Override
            public void dumpHeapFinished(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(160, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpHeapFinished(string2);
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
            public void enterSafeMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enterSafeMode();
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
            public boolean finishActivity(IBinder iBinder, int n, Intent intent, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    boolean bl = true;
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().finishActivity(iBinder, n, intent, n2);
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
            public void finishHeavyWeightApp() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(91, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishHeavyWeightApp();
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
            public void finishInstrumentation(IApplicationThread iApplicationThread, int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishInstrumentation(iApplicationThread, n, bundle);
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
            public void finishReceiver(IBinder iBinder, int n, String string2, Bundle bundle, boolean bl, int n2) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block16 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel.writeString(string2);
                        int n3 = 0;
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (bl) {
                            n3 = 1;
                        }
                        parcel.writeInt(n3);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().finishReceiver(iBinder, n, string2, bundle, bl, n2);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
            }

            @Override
            public void forceStopPackage(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(68, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forceStopPackage(string2, n);
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
            public List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(133, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ActivityManager.StackInfo> list = Stub.getDefaultImpl().getAllStackInfos();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ActivityManager.StackInfo> arrayList = parcel2.createTypedArrayList(ActivityManager.StackInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Configuration getConfiguration() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(37, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Configuration configuration = Stub.getDefaultImpl().getConfiguration();
                        parcel.recycle();
                        parcel2.recycle();
                        return configuration;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Configuration configuration = parcel.readInt() != 0 ? Configuration.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return configuration;
            }

            @Override
            public ContentProviderHolder getContentProvider(IApplicationThread object, String string2, String string3, int n, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block8 : {
                    IBinder iBinder;
                    block7 : {
                        block6 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (object == null) break block6;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = object.asBinder();
                            break block7;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    parcel2.writeString(string2);
                    parcel2.writeString(string3);
                    parcel2.writeInt(n);
                    int n2 = bl ? 1 : 0;
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(20, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block8;
                    object = Stub.getDefaultImpl().getContentProvider((IApplicationThread)object, string2, string3, n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return object;
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? ContentProviderHolder.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public ContentProviderHolder getContentProviderExternal(String object, int n, IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        parcel2.writeStrongBinder(iBinder);
                        parcel2.writeString(string2);
                        if (this.mRemote.transact(108, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getContentProviderExternal((String)object, n, iBinder, string2);
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
                object = parcel.readInt() != 0 ? ContentProviderHolder.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public UserInfo getCurrentUser() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(113, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        UserInfo userInfo = Stub.getDefaultImpl().getCurrentUser();
                        parcel.recycle();
                        parcel2.recycle();
                        return userInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                UserInfo userInfo = parcel.readInt() != 0 ? UserInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return userInfo;
            }

            @Override
            public List<ActivityManager.RunningTaskInfo> getFilteredTasks(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ActivityManager.RunningTaskInfo> list = Stub.getDefaultImpl().getFilteredTasks(n, n2, n3);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ActivityManager.RunningTaskInfo> arrayList = parcel2.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ActivityManager.StackInfo getFocusedStackInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(137, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ActivityManager.StackInfo stackInfo = Stub.getDefaultImpl().getFocusedStackInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return stackInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ActivityManager.StackInfo stackInfo = parcel.readInt() != 0 ? ActivityManager.StackInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return stackInfo;
            }

            @Override
            public int getForegroundServiceType(ComponentName componentName, IBinder iBinder) throws RemoteException {
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
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getForegroundServiceType(componentName, iBinder);
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
            public Intent getIntentForIntentSender(IIntentSender object) throws RemoteException {
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
                                if (object == null) break block5;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = object.asBinder();
                            break block6;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(128, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block7;
                    object = Stub.getDefaultImpl().getIntentForIntentSender((IIntentSender)object);
                    parcel2.recycle();
                    parcel.recycle();
                    return object;
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
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
            public IIntentSender getIntentSender(int n, String object, IBinder iBinder, String string2, int n2, Intent[] arrintent, String[] arrstring, int n3, Bundle bundle, int n4) throws RemoteException {
                void var2_5;
                Parcel parcel2;
                Parcel parcel;
                block8 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        parcel2.writeStrongBinder(iBinder);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n2);
                        parcel2.writeTypedArray((Parcelable[])arrintent, 0);
                        parcel2.writeStringArray(arrstring);
                        parcel2.writeInt(n3);
                        if (bundle != null) {
                            parcel2.writeInt(1);
                            bundle.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        parcel2.writeInt(n4);
                        if (!this.mRemote.transact(51, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().getIntentSender(n, (String)object, iBinder, string2, n2, arrintent, arrstring, n3, bundle, n4);
                            parcel.recycle();
                            parcel2.recycle();
                            return object;
                        }
                        parcel.readException();
                        object = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block8;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_5;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public String getLaunchedFromPackage(IBinder object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder((IBinder)object);
                    if (!this.mRemote.transact(129, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getLaunchedFromPackage((IBinder)object);
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
            public int getLaunchedFromUid(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(114, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getLaunchedFromUid(iBinder);
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
            public ParcelFileDescriptor getLifeMonitor() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(196, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParcelFileDescriptor parcelFileDescriptor = Stub.getDefaultImpl().getLifeMonitor();
                        parcel.recycle();
                        parcel2.recycle();
                        return parcelFileDescriptor;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ParcelFileDescriptor parcelFileDescriptor = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parcelFileDescriptor;
            }

            @Override
            public int getLockTaskModeState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(158, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getLockTaskModeState();
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
            public void getMemoryInfo(ActivityManager.MemoryInfo memoryInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(65, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getMemoryInfo(memoryInfo);
                        return;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        memoryInfo.readFromParcel(parcel2);
                    }
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getMemoryTrimLevel() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(177, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getMemoryTrimLevel();
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
            public void getMyMemoryState(ActivityManager.RunningAppProcessInfo runningAppProcessInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(111, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getMyMemoryState(runningAppProcessInfo);
                        return;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        runningAppProcessInfo.readFromParcel(parcel2);
                    }
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
            public String getPackageForIntentSender(IIntentSender object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = object != null ? object.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getPackageForIntentSender((IIntentSender)object);
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
            public int getPackageProcessState(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(164, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPackageProcessState(string2, string3);
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
            public int getProcessLimit() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getProcessLimit();
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
            public Debug.MemoryInfo[] getProcessMemoryInfo(int[] arrobject) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray((int[])arrobject);
                    if (!this.mRemote.transact(85, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrobject = Stub.getDefaultImpl().getProcessMemoryInfo((int[])arrobject);
                        return arrobject;
                    }
                    parcel2.readException();
                    arrobject = parcel2.createTypedArray(Debug.MemoryInfo.CREATOR);
                    return arrobject;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long[] getProcessPss(int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(105, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrn = Stub.getDefaultImpl().getProcessPss(arrn);
                        return arrn;
                    }
                    parcel2.readException();
                    arrn = parcel2.createLongArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(66, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ActivityManager.ProcessErrorStateInfo> list = Stub.getDefaultImpl().getProcessesInErrorState();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ActivityManager.ProcessErrorStateInfo> arrayList = parcel2.createTypedArrayList(ActivityManager.ProcessErrorStateInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getProviderMimeType(Uri object, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(95, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getProviderMimeType((Uri)object, n);
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
            public ParceledListSlice getRecentTasks(int n, int n2, int n3) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        if (this.mRemote.transact(49, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getRecentTasks(n, n2, n3);
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
                ParceledListSlice parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(71, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ActivityManager.RunningAppProcessInfo> list = Stub.getDefaultImpl().getRunningAppProcesses();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ActivityManager.RunningAppProcessInfo> arrayList = parcel2.createTypedArrayList(ActivityManager.RunningAppProcessInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<ApplicationInfo> getRunningExternalApplications() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(90, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ApplicationInfo> list = Stub.getDefaultImpl().getRunningExternalApplications();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ApplicationInfo> arrayList = parcel2.createTypedArrayList(ApplicationInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public PendingIntent getRunningServiceControlPanel(ComponentName parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        PendingIntent pendingIntent = Stub.getDefaultImpl().getRunningServiceControlPanel((ComponentName)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return pendingIntent;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        PendingIntent pendingIntent = PendingIntent.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public int[] getRunningUserIds() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(123, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getRunningUserIds();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<ActivityManager.RunningServiceInfo> getServices(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(70, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ActivityManager.RunningServiceInfo> list = Stub.getDefaultImpl().getServices(n, n2);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ActivityManager.RunningServiceInfo> arrayList = parcel2.createTypedArrayList(ActivityManager.RunningServiceInfo.CREATOR);
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
            public String getTagForIntentSender(IIntentSender object, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = object != null ? object.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(143, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getTagForIntentSender((IIntentSender)object, string2);
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
            public Rect getTaskBounds(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(141, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        Rect rect = Stub.getDefaultImpl().getTaskBounds(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return rect;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                Rect rect = parcel2.readInt() != 0 ? Rect.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return rect;
            }

            @Override
            public int getTaskForActivity(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getTaskForActivity(iBinder, bl);
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
            public ActivityManager.TaskSnapshot getTaskSnapshot(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(188, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    ActivityManager.TaskSnapshot taskSnapshot = Stub.getDefaultImpl().getTaskSnapshot(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return taskSnapshot;
                }
                parcel.readException();
                ActivityManager.TaskSnapshot taskSnapshot = parcel.readInt() != 0 ? ActivityManager.TaskSnapshot.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return taskSnapshot;
            }

            @Override
            public List<ActivityManager.RunningTaskInfo> getTasks(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ActivityManager.RunningTaskInfo> list = Stub.getDefaultImpl().getTasks(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ActivityManager.RunningTaskInfo> arrayList = parcel2.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
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
            public int getUidForIntentSender(IIntentSender iIntentSender) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iIntentSender != null ? iIntentSender.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(80, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getUidForIntentSender(iIntentSender);
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
            public int getUidProcessState(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getUidProcessState(n, string2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void grantUriPermission(IApplicationThread iApplicationThread, String string2, Uri uri, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantUriPermission(iApplicationThread, string2, uri, n, n2);
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
            public void handleApplicationCrash(IBinder iBinder, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (parcelableCrashInfo != null) {
                        parcel.writeInt(1);
                        parcelableCrashInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleApplicationCrash(iBinder, parcelableCrashInfo);
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
            public void handleApplicationStrictModeViolation(IBinder iBinder, int n, StrictMode.ViolationInfo violationInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (violationInfo != null) {
                        parcel.writeInt(1);
                        violationInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(92, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleApplicationStrictModeViolation(iBinder, n, violationInfo);
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
            public boolean handleApplicationWtf(IBinder iBinder, String string2, boolean bl, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    boolean bl2 = true;
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    if (parcelableCrashInfo != null) {
                        parcel.writeInt(1);
                        parcelableCrashInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(87, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().handleApplicationWtf(iBinder, string2, bl, parcelableCrashInfo);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    bl = n != 0 ? bl2 : false;
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int handleIncomingUser(int n, int n2, int n3, boolean bl, boolean bl2, String string2, String string3) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var6_13;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n3);
                        int n4 = 1;
                        int n5 = bl ? 1 : 0;
                        parcel.writeInt(n5);
                        n5 = bl2 ? n4 : 0;
                        parcel.writeInt(n5);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeString(string3);
                        if (!this.mRemote.transact(81, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().handleIncomingUser(n, n2, n3, bl, bl2, string2, string3);
                            parcel2.recycle();
                            parcel.recycle();
                            return n;
                        }
                        parcel2.readException();
                        n = parcel2.readInt();
                        parcel2.recycle();
                        parcel.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var6_13;
            }

            @Override
            public void hang(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(132, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hang(iBinder, bl);
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
            public boolean isAppStartModeDisabled(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(171, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isAppStartModeDisabled(n, string2);
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
            public boolean isBackgroundRestricted(String string2) throws RemoteException {
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
                    if (iBinder.transact(183, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isBackgroundRestricted(string2);
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
            public boolean isInLockTaskMode() throws RemoteException {
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
                    if (iBinder.transact(145, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isInLockTaskMode();
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
            public boolean isIntentSenderABroadcast(IIntentSender iIntentSender) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iIntentSender == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iIntentSender.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(118, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().isIntentSenderABroadcast(iIntentSender);
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
            public boolean isIntentSenderAForegroundService(IIntentSender iIntentSender) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iIntentSender == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iIntentSender.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(117, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().isIntentSenderAForegroundService(iIntentSender);
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
            public boolean isIntentSenderAnActivity(IIntentSender iIntentSender) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iIntentSender == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iIntentSender.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(116, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().isIntentSenderAnActivity(iIntentSender);
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
            public boolean isIntentSenderTargetedToPackage(IIntentSender iIntentSender) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iIntentSender == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iIntentSender.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(103, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().isIntentSenderTargetedToPackage(iIntentSender);
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
            public boolean isTopActivityImmersive() throws RemoteException {
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
                    if (iBinder.transact(93, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isTopActivityImmersive();
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
            public boolean isTopOfTask(IBinder iBinder) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeStrongBinder(iBinder);
                        iBinder2 = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder2.transact(150, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isTopOfTask(iBinder);
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
            public boolean isUidActive(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUidActive(n, string2);
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
            public boolean isUserAMonkey() throws RemoteException {
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
                    if (iBinder.transact(89, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUserAMonkey();
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
            public boolean isUserRunning(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(97, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUserRunning(n, n2);
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
            public boolean isVrModePackageEnabled(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(178, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isVrModePackageEnabled(componentName);
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
            public void killAllBackgroundProcesses() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(107, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().killAllBackgroundProcesses();
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
            public void killApplication(String string2, int n, int n2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(83, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().killApplication(string2, n, n2, string3);
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
            public void killApplicationProcess(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(86, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().killApplicationProcess(string2, n);
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
            public void killBackgroundProcesses(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(88, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().killBackgroundProcesses(string2, n);
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
            public void killPackageDependents(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(173, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().killPackageDependents(string2, n);
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
            public boolean killPids(int[] arrn, String string2, boolean bl) throws RemoteException {
                Parcel parcel;
                int n;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeIntArray(arrn);
                        parcel.writeString(string2);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(69, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().killPids(arrn, string2, bl);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean killProcessesBelowForeground(String string2) throws RemoteException {
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
                    if (iBinder.transact(112, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().killProcessesBelowForeground(string2);
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
            public void killUid(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(130, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().killUid(n, n2, string2);
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
            public void makePackageIdle(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(176, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().makePackageIdle(string2, n);
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
            public boolean moveActivityTaskToBack(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                int n;
                block4 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeStrongBinder(iBinder);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n);
                    if (this.mRemote.transact(64, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().moveActivityTaskToBack(iBinder, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
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
            public void moveTaskToFront(IApplicationThread iApplicationThread, String string2, int n, int n2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().moveTaskToFront(iApplicationThread, string2, n, n2, bundle);
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
            public void moveTaskToStack(int n, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(134, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().moveTaskToStack(n, n2, bl);
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
            public boolean moveTopActivityToPinnedStack(int n, Rect rect) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    boolean bl = true;
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(170, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().moveTopActivityToPinnedStack(n, rect);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void noteAlarmFinish(IIntentSender iIntentSender, WorkSource workSource, int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iIntentSender != null ? iIntentSender.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(163, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteAlarmFinish(iIntentSender, workSource, n, string2);
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
            public void noteAlarmStart(IIntentSender iIntentSender, WorkSource workSource, int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iIntentSender != null ? iIntentSender.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(162, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteAlarmStart(iIntentSender, workSource, n, string2);
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
            public void noteWakeupAlarm(IIntentSender iIntentSender, WorkSource workSource, int n, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iIntentSender != null ? iIntentSender.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(57, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWakeupAlarm(iIntentSender, workSource, n, string2, string3);
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
            public void notifyCleartextNetwork(int n, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(155, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCleartextNetwork(n, arrby);
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
            public void notifyLockedProfile(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(179, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyLockedProfile(n);
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
            public ParcelFileDescriptor openContentUri(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().openContentUri((String)object);
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
                object = parcel2.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public IBinder peekService(Intent object, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((Intent)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(72, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().peekService((Intent)object, string2, string3);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readStrongBinder();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void performIdleMaintenance() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(139, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performIdleMaintenance();
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
            public void positionTaskInStack(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(168, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().positionTaskInStack(n, n2, n3);
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
            public boolean profileControl(String string2, int n, boolean bl, ProfilerInfo profilerInfo, int n2) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block14 : {
                    boolean bl2;
                    block13 : {
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel.writeInt(n);
                            bl2 = true;
                            int n3 = bl ? 1 : 0;
                            parcel.writeInt(n3);
                            if (profilerInfo != null) {
                                parcel.writeInt(1);
                                profilerInfo.writeToParcel(parcel, 0);
                                break block13;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        if (!this.mRemote.transact(73, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().profileControl(string2, n, bl, profilerInfo, n2);
                            parcel2.recycle();
                            parcel.recycle();
                            return bl;
                        }
                        parcel2.readException();
                        n = parcel2.readInt();
                        bl = n != 0 ? bl2 : false;
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_7;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void publishContentProviders(IApplicationThread iApplicationThread, List<ContentProviderHolder> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().publishContentProviders(iApplicationThread, list);
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
            public void publishService(IBinder iBinder, Intent intent, IBinder iBinder2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder(iBinder2);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().publishService(iBinder, intent, iBinder2);
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
            public boolean refContentProvider(IBinder iBinder, int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeStrongBinder(iBinder);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        iBinder2 = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder2.transact(22, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().refContentProvider(iBinder, n, n2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerIntentSenderCancelListener(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    Object var5_6 = null;
                    IBinder iBinder = iIntentSender != null ? iIntentSender.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var5_6;
                    if (iResultReceiver != null) {
                        iBinder = iResultReceiver.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerIntentSenderCancelListener(iIntentSender, iResultReceiver);
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
            public void registerProcessObserver(IProcessObserver iProcessObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iProcessObserver != null ? iProcessObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(101, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerProcessObserver(iProcessObserver);
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
            public Intent registerReceiver(IApplicationThread object, String string2, IIntentReceiver iIntentReceiver, IntentFilter intentFilter, String string3, int n, int n2) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        Object var10_16 = null;
                        IBinder iBinder = object != null ? object.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        try {
                            parcel.writeString(string2);
                            iBinder = var10_16;
                            if (iIntentReceiver != null) {
                                iBinder = iIntentReceiver.asBinder();
                            }
                            parcel.writeStrongBinder(iBinder);
                            if (intentFilter != null) {
                                parcel.writeInt(1);
                                intentFilter.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n2);
                        if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().registerReceiver((IApplicationThread)object, string2, iIntentReceiver, intentFilter, string3, n, n2);
                            parcel2.recycle();
                            parcel.recycle();
                            return object;
                        }
                        parcel2.readException();
                        object = parcel2.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel2) : null;
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_7;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTaskStackListener != null ? iTaskStackListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(153, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerTaskStackListener(iTaskStackListener);
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
            public void registerUidObserver(IUidObserver iUidObserver, int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iUidObserver != null ? iUidObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerUidObserver(iUidObserver, n, n2, string2);
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
            public void registerUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iUserSwitchObserver != null ? iUserSwitchObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(121, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerUserSwitchObserver(iUserSwitchObserver, string2);
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
            public void removeContentProvider(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(58, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeContentProvider(iBinder, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeContentProviderExternal(String string2, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(109, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeContentProviderExternal(string2, iBinder);
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
            public void removeContentProviderExternalAsUser(String string2, IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(110, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeContentProviderExternalAsUser(string2, iBinder, n);
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
            public void removeStack(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(175, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeStack(n);
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
            public boolean removeTask(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(100, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeTask(n);
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
            public void requestBugReport(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(125, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestBugReport(n);
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
            public void requestSystemServerHeapDump() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(124, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestSystemServerHeapDump();
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
            public void requestTelephonyBugReport(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(126, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestTelephonyBugReport(string2, string3);
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
            public void requestWifiBugReport(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(127, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestWifiBugReport(string2, string3);
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
            public void resizeDockedStack(Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (rect2 != null) {
                        parcel.writeInt(1);
                        rect2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (rect3 != null) {
                        parcel.writeInt(1);
                        rect3.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (rect4 != null) {
                        parcel.writeInt(1);
                        rect4.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (rect5 != null) {
                        parcel.writeInt(1);
                        rect5.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(174, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resizeDockedStack(rect, rect2, rect3, rect4, rect5);
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
            public void resizeStack(int n, Rect rect, boolean bl, boolean bl2, boolean bl3, int n2) throws RemoteException {
                Parcel parcel;
                void var2_7;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                        int n3 = 1;
                        if (rect != null) {
                            parcel2.writeInt(1);
                            rect.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        int n4 = bl ? 1 : 0;
                        parcel2.writeInt(n4);
                        n4 = bl2 ? 1 : 0;
                        parcel2.writeInt(n4);
                        n4 = bl3 ? n3 : 0;
                        parcel2.writeInt(n4);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        if (!this.mRemote.transact(135, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().resizeStack(n, rect, bl, bl2, bl3, n2);
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
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_7;
            }

            @Override
            public void resizeTask(int n, Rect rect, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(157, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resizeTask(n, rect, n2);
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
            public void restart() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(138, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restart();
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
            public int restartUserInBackground(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(186, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().restartUserInBackground(n);
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
            public void resumeAppSwitches() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(76, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resumeAppSwitches();
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
            public void revokeUriPermission(IApplicationThread iApplicationThread, String string2, Uri uri, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().revokeUriPermission(iApplicationThread, string2, uri, n, n2);
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
            public void scheduleApplicationInfoChanged(List<String> list, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(189, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleApplicationInfoChanged(list, n);
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
            public void sendIdleJobTrigger() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(181, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendIdleJobTrigger();
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
            public int sendIntentSender(IIntentSender iIntentSender, IBinder iBinder, int n, Intent intent, String string2, IIntentReceiver iIntentReceiver, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_5;
                block13 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    Object var11_15 = null;
                    IBinder iBinder2 = iIntentSender != null ? iIntentSender.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    try {
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                    try {
                        parcel.writeInt(n);
                        if (intent != null) {
                            parcel.writeInt(1);
                            intent.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeString(string2);
                        iBinder2 = var11_15;
                        if (iIntentReceiver != null) {
                            iBinder2 = iIntentReceiver.asBinder();
                        }
                        parcel.writeStrongBinder(iBinder2);
                        parcel.writeString(string3);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!this.mRemote.transact(182, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().sendIntentSender(iIntentSender, iBinder, n, intent, string2, iIntentReceiver, string3, bundle);
                            parcel2.recycle();
                            parcel.recycle();
                            return n;
                        }
                        parcel2.readException();
                        n = parcel2.readInt();
                        parcel2.recycle();
                        parcel.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_5;
            }

            @Override
            public void serviceDoneExecuting(IBinder iBinder, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(50, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serviceDoneExecuting(iBinder, n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setActivityController(IActivityController iActivityController, boolean bl) throws RemoteException {
                Parcel parcel;
                IBinder iBinder;
                Parcel parcel2;
                block8 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iActivityController == null) break block8;
                    iBinder = iActivityController.asBinder();
                }
                iBinder = null;
                parcel2.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel2.writeInt(n);
                    if (!this.mRemote.transact(46, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setActivityController(iActivityController, bl);
                        return;
                    }
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setAgentApp(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAgentApp(string2, string3);
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
            public void setAlwaysFinish(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAlwaysFinish(bl);
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
            public void setDebugApp(String string2, boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = 1;
                int n2 = bl ? 1 : 0;
                parcel.writeInt(n2);
                n2 = bl2 ? n : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDebugApp(string2, bl, bl2);
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
            public void setDumpHeapDebugLimit(String string2, int n, long l, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(159, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDumpHeapDebugLimit(string2, n, l, string3);
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
            public void setFocusedStack(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(136, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFocusedStack(n);
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
            public void setHasTopUi(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(185, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setHasTopUi(bl);
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
            public void setPackageScreenCompatMode(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(98, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPackageScreenCompatMode(string2, n);
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
            public void setPersistentVrThread(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(190, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPersistentVrThread(n);
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
            public void setProcessImportant(IBinder iBinder, int n, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(61, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProcessImportant(iBinder, n, bl, string2);
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
            public void setProcessLimit(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProcessLimit(n);
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
            public boolean setProcessMemoryTrimLevel(String string2, int n, int n2) throws RemoteException {
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
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(142, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setProcessMemoryTrimLevel(string2, n, n2);
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
            public void setRenderThread(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(184, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRenderThread(n);
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
            public void setRequestedOrientation(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(59, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRequestedOrientation(iBinder, n);
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
            public void setServiceForeground(ComponentName componentName, IBinder iBinder, int n, Notification notification, int n2, int n3) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        block14 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                            if (componentName != null) {
                                parcel2.writeInt(1);
                                componentName.writeToParcel(parcel2, 0);
                                break block14;
                            }
                            parcel2.writeInt(0);
                        }
                        try {
                            parcel2.writeStrongBinder(iBinder);
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
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n3);
                        if (!this.mRemote.transact(62, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().setServiceForeground(componentName, iBinder, n, notification, n2, n3);
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
                throw var1_7;
            }

            @Override
            public void setTaskResizeable(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(156, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTaskResizeable(n, n2);
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
            public void setUserIsMonkey(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(131, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserIsMonkey(bl);
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
            public void showBootMessage(CharSequence charSequence, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(106, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showBootMessage(charSequence, bl);
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
            public void showWaitingForDebugger(IApplicationThread iApplicationThread, boolean bl) throws RemoteException {
                Parcel parcel;
                IBinder iBinder;
                Parcel parcel2;
                block8 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iApplicationThread == null) break block8;
                    iBinder = iApplicationThread.asBinder();
                }
                iBinder = null;
                parcel2.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel2.writeInt(n);
                    if (!this.mRemote.transact(47, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showWaitingForDebugger(iApplicationThread, bl);
                        return;
                    }
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public boolean shutdown(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(74, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().shutdown(n);
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
            public void signalPersistentProcesses(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().signalPersistentProcesses(n);
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
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int startActivity(IApplicationThread var1_1, String var2_6, Intent var3_7, String var4_8, IBinder var5_9, String var6_10, int var7_11, int var8_12, ProfilerInfo var9_13, Bundle var10_14) throws RemoteException {
                block19 : {
                    block20 : {
                        block21 : {
                            block18 : {
                                block17 : {
                                    block16 : {
                                        block15 : {
                                            block14 : {
                                                var11_15 = Parcel.obtain();
                                                var12_16 = Parcel.obtain();
                                                var11_15.writeInterfaceToken("android.app.IActivityManager");
                                                if (var1_1 == null) break block14;
                                                try {
                                                    var13_17 = var1_1.asBinder();
                                                    break block15;
                                                }
                                                catch (Throwable var1_2) {
                                                    break block19;
                                                }
                                            }
                                            var13_17 = null;
                                        }
                                        var11_15.writeStrongBinder((IBinder)var13_17);
                                        var11_15.writeString(var2_6);
                                        if (var3_7 == null) break block16;
                                        var11_15.writeInt(1);
                                        var3_7.writeToParcel(var11_15, 0);
                                        ** GOTO lbl25
                                    }
                                    var11_15.writeInt(0);
lbl25: // 2 sources:
                                    var11_15.writeString(var4_8);
                                    var11_15.writeStrongBinder(var5_9);
                                    var11_15.writeString(var6_10);
                                    var11_15.writeInt(var7_11);
                                    var11_15.writeInt(var8_12);
                                    if (var9_13 == null) break block17;
                                    var11_15.writeInt(1);
                                    var9_13.writeToParcel(var11_15, 0);
                                    break block18;
                                }
                                var11_15.writeInt(0);
                            }
                            if (var10_14 == null) break block21;
                            var11_15.writeInt(1);
                            var10_14.writeToParcel(var11_15, 0);
                            ** GOTO lbl47
                        }
                        var11_15.writeInt(0);
lbl47: // 2 sources:
                        if (this.mRemote.transact(7, var11_15, var12_16, 0) || Stub.getDefaultImpl() == null) break block20;
                        var13_17 = Stub.getDefaultImpl();
                        try {
                            var7_11 = var13_17.startActivity((IApplicationThread)var1_1, var2_6, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12, var9_13, var10_14);
                            var12_16.recycle();
                            var11_15.recycle();
                            return var7_11;
                        }
                        catch (Throwable var1_3) {}
                    }
                    var1_1 = var12_16;
                    var1_1.readException();
                    var7_11 = var1_1.readInt();
                    var1_1.recycle();
                    var11_15.recycle();
                    return var7_11;
                    break block19;
                    catch (Throwable var1_4) {
                        // empty catch block
                    }
                }
                var12_16.recycle();
                var11_15.recycle();
                throw var1_5;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int startActivityAsUser(IApplicationThread var1_1, String var2_6, Intent var3_7, String var4_8, IBinder var5_9, String var6_10, int var7_11, int var8_12, ProfilerInfo var9_13, Bundle var10_14, int var11_15) throws RemoteException {
                block19 : {
                    block20 : {
                        block21 : {
                            block18 : {
                                block17 : {
                                    block16 : {
                                        block15 : {
                                            block14 : {
                                                var12_16 = Parcel.obtain();
                                                var13_17 = Parcel.obtain();
                                                var12_16.writeInterfaceToken("android.app.IActivityManager");
                                                if (var1_1 == null) break block14;
                                                try {
                                                    var14_18 = var1_1.asBinder();
                                                    break block15;
                                                }
                                                catch (Throwable var1_2) {
                                                    break block19;
                                                }
                                            }
                                            var14_18 = null;
                                        }
                                        var12_16.writeStrongBinder((IBinder)var14_18);
                                        var12_16.writeString(var2_6);
                                        if (var3_7 == null) break block16;
                                        var12_16.writeInt(1);
                                        var3_7.writeToParcel(var12_16, 0);
                                        ** GOTO lbl25
                                    }
                                    var12_16.writeInt(0);
lbl25: // 2 sources:
                                    var12_16.writeString(var4_8);
                                    var12_16.writeStrongBinder(var5_9);
                                    var12_16.writeString(var6_10);
                                    var12_16.writeInt(var7_11);
                                    var12_16.writeInt(var8_12);
                                    if (var9_13 == null) break block17;
                                    var12_16.writeInt(1);
                                    var9_13.writeToParcel(var12_16, 0);
                                    break block18;
                                }
                                var12_16.writeInt(0);
                            }
                            if (var10_14 == null) break block21;
                            var12_16.writeInt(1);
                            var10_14.writeToParcel(var12_16, 0);
                            ** GOTO lbl47
                        }
                        var12_16.writeInt(0);
lbl47: // 2 sources:
                        var12_16.writeInt(var11_15);
                        if (this.mRemote.transact(119, var12_16, var13_17, 0) || Stub.getDefaultImpl() == null) break block20;
                        var14_18 = Stub.getDefaultImpl();
                        try {
                            var7_11 = var14_18.startActivityAsUser((IApplicationThread)var1_1, var2_6, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12, var9_13, var10_14, var11_15);
                            var13_17.recycle();
                            var12_16.recycle();
                            return var7_11;
                        }
                        catch (Throwable var1_3) {}
                    }
                    var1_1 = var13_17;
                    var1_1.readException();
                    var7_11 = var1_1.readInt();
                    var1_1.recycle();
                    var12_16.recycle();
                    return var7_11;
                    break block19;
                    catch (Throwable var1_4) {
                        // empty catch block
                    }
                }
                var13_17.recycle();
                var12_16.recycle();
                throw var1_5;
            }

            @Override
            public int startActivityFromRecents(int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(148, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().startActivityFromRecents(n, bundle);
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
            public boolean startBinderTracking() throws RemoteException {
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
                    if (iBinder.transact(166, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().startBinderTracking();
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
            public void startConfirmDeviceCredentialIntent(Intent intent, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(180, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startConfirmDeviceCredentialIntent(intent, bundle);
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
            public void startDelegateShellPermissionIdentity(int n, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(194, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startDelegateShellPermissionIdentity(n, arrstring);
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
            public boolean startInstrumentation(ComponentName componentName, String string2, int n, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int n2, String string3) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_5;
                block14 : {
                    boolean bl;
                    block13 : {
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl = true;
                        if (componentName != null) {
                            parcel.writeInt(1);
                            componentName.writeToParcel(parcel, 0);
                            break block13;
                        }
                        parcel.writeInt(0);
                    }
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        Object var12_16 = null;
                        IBinder iBinder = iInstrumentationWatcher != null ? iInstrumentationWatcher.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        iBinder = var12_16;
                        if (iUiAutomationConnection != null) {
                            iBinder = iUiAutomationConnection.asBinder();
                        }
                        parcel.writeStrongBinder(iBinder);
                        parcel.writeInt(n2);
                        parcel.writeString(string3);
                        if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().startInstrumentation(componentName, string2, n, bundle, iInstrumentationWatcher, iUiAutomationConnection, n2, string3);
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
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_5;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void startRecentsActivity(Intent intent, IAssistDataReceiver iAssistDataReceiver, IRecentsAnimationRunner iRecentsAnimationRunner) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    Object var6_7 = null;
                    IBinder iBinder = iAssistDataReceiver != null ? iAssistDataReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var6_7;
                    if (iRecentsAnimationRunner != null) {
                        iBinder = iRecentsAnimationRunner.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(146, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startRecentsActivity(intent, iAssistDataReceiver, iRecentsAnimationRunner);
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
            public ComponentName startService(IApplicationThread object, Intent intent, String string2, boolean bl, String string3, int n) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block15 : {
                    int n2;
                    block14 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        IBinder iBinder = object != null ? object.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                        n2 = 1;
                        if (intent != null) {
                            parcel2.writeInt(1);
                            intent.writeToParcel(parcel2, 0);
                            break block14;
                        }
                        parcel2.writeInt(0);
                    }
                    try {
                        parcel2.writeString(string2);
                        if (!bl) {
                            n2 = 0;
                        }
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        if (!this.mRemote.transact(24, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().startService((IApplicationThread)object, intent, string2, bl, string3, n);
                            parcel.recycle();
                            parcel2.recycle();
                            return object;
                        }
                        parcel.readException();
                        object = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_7;
            }

            @Override
            public void startSystemLockTaskMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(149, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startSystemLockTaskMode(n);
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
            public boolean startUserInBackground(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(144, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().startUserInBackground(n);
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
            public boolean startUserInBackgroundWithListener(int n, IProgressListener iProgressListener) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                parcel2.writeInt(n);
                                if (iProgressListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iProgressListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(193, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().startUserInBackgroundWithListener(n, iProgressListener);
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
            public boolean startUserInForegroundWithListener(int n, IProgressListener iProgressListener) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                parcel2.writeInt(n);
                                if (iProgressListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iProgressListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(197, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().startUserInForegroundWithListener(n, iProgressListener);
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
            public void stopAppSwitches() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(75, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopAppSwitches();
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
            public boolean stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(167, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().stopBinderTrackingAndDump(parcelFileDescriptor);
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
            public void stopDelegateShellPermissionIdentity() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(195, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopDelegateShellPermissionIdentity();
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
            public int stopService(IApplicationThread iApplicationThread, Intent intent, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().stopService(iApplicationThread, intent, string2, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean stopServiceToken(ComponentName componentName, IBinder iBinder, int n) throws RemoteException {
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
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().stopServiceToken(componentName, iBinder, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int stopUser(int n, boolean bl, IStopUserCallback iStopUserCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    IBinder iBinder = iStopUserCallback != null ? iStopUserCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(120, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().stopUser(n, bl, iStopUserCallback);
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
            public void suppressResizeConfigChanges(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(169, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().suppressResizeConfigChanges(bl);
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
            public boolean switchUser(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(99, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().switchUser(n);
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
            public void unbindBackupAgent(ApplicationInfo applicationInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (applicationInfo != null) {
                        parcel.writeInt(1);
                        applicationInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(79, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unbindBackupAgent(applicationInfo);
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
            public void unbindFinished(IBinder iBinder, Intent intent, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    int n = 1;
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unbindFinished(iBinder, intent, bl);
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
            public boolean unbindService(IServiceConnection iServiceConnection) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                if (iServiceConnection == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iServiceConnection.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(29, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().unbindService(iServiceConnection);
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
            public void unbroadcastIntent(IApplicationThread iApplicationThread, Intent intent, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unbroadcastIntent(iApplicationThread, intent, n);
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
            public void unhandledBack() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unhandledBack();
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
            public boolean unlockUser(int n, byte[] arrby, byte[] arrby2, IProgressListener iProgressListener) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                parcel2.writeInt(n);
                                parcel2.writeByteArray(arrby);
                                parcel2.writeByteArray(arrby2);
                                if (iProgressListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iProgressListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(172, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().unlockUser(n, arrby, arrby2, iProgressListener);
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
            public void unregisterIntentSenderCancelListener(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    Object var5_6 = null;
                    IBinder iBinder = iIntentSender != null ? iIntentSender.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var5_6;
                    if (iResultReceiver != null) {
                        iBinder = iResultReceiver.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterIntentSenderCancelListener(iIntentSender, iResultReceiver);
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
            public void unregisterProcessObserver(IProcessObserver iProcessObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iProcessObserver != null ? iProcessObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(102, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterProcessObserver(iProcessObserver);
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
            public void unregisterReceiver(IIntentReceiver iIntentReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iIntentReceiver != null ? iIntentReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterReceiver(iIntentReceiver);
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
            public void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTaskStackListener != null ? iTaskStackListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(154, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterTaskStackListener(iTaskStackListener);
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
            public void unregisterUidObserver(IUidObserver iUidObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iUidObserver != null ? iUidObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterUidObserver(iUidObserver);
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
            public void unregisterUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iUserSwitchObserver != null ? iUserSwitchObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(122, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterUserSwitchObserver(iUserSwitchObserver);
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
            public void unstableProviderDied(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(115, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unstableProviderDied(iBinder);
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
            public boolean updateConfiguration(Configuration configuration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (configuration != null) {
                        parcel.writeInt(1);
                        configuration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().updateConfiguration(configuration);
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
            public void updateDeviceOwner(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(165, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateDeviceOwner(string2);
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
            public void updateLockTaskPackages(int n, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(161, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateLockTaskPackages(n, arrstring);
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
            public void updatePersistentConfiguration(Configuration configuration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (configuration != null) {
                        parcel.writeInt(1);
                        configuration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(104, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updatePersistentConfiguration(configuration);
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
            public void updateServiceGroup(IServiceConnection iServiceConnection, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iServiceConnection != null ? iServiceConnection.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateServiceGroup(iServiceConnection, n, n2);
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
            public void waitForNetworkStateUpdate(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(191, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().waitForNetworkStateUpdate(l);
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

