/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.ActivityManager;
import android.app.IActivityController;
import android.app.IApplicationThread;
import android.app.IAssistDataReceiver;
import android.app.IRequestFinishCallback;
import android.app.ITaskStackListener;
import android.app.PictureInPictureParams;
import android.app.ProfilerInfo;
import android.app.WaitResult;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.ComponentName;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.service.voice.IVoiceInteractionSession;
import android.text.TextUtils;
import android.view.IRecentsAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationDefinition;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.policy.IKeyguardDismissCallback;
import java.util.ArrayList;
import java.util.List;

public interface IActivityTaskManager
extends IInterface {
    public void activityDestroyed(IBinder var1) throws RemoteException;

    public void activityIdle(IBinder var1, Configuration var2, boolean var3) throws RemoteException;

    public void activityPaused(IBinder var1) throws RemoteException;

    public void activityRelaunched(IBinder var1) throws RemoteException;

    public void activityResumed(IBinder var1) throws RemoteException;

    public void activitySlept(IBinder var1) throws RemoteException;

    public void activityStopped(IBinder var1, Bundle var2, PersistableBundle var3, CharSequence var4) throws RemoteException;

    public void activityTopResumedStateLost() throws RemoteException;

    public int addAppTask(IBinder var1, Intent var2, ActivityManager.TaskDescription var3, Bitmap var4) throws RemoteException;

    public void alwaysShowUnsupportedCompileSdkWarning(ComponentName var1) throws RemoteException;

    public void cancelRecentsAnimation(boolean var1) throws RemoteException;

    public void cancelTaskWindowTransition(int var1) throws RemoteException;

    public void clearLaunchParamsForPackages(List<String> var1) throws RemoteException;

    public boolean convertFromTranslucent(IBinder var1) throws RemoteException;

    public boolean convertToTranslucent(IBinder var1, Bundle var2) throws RemoteException;

    public void dismissKeyguard(IBinder var1, IKeyguardDismissCallback var2, CharSequence var3) throws RemoteException;

    public void dismissPip(boolean var1, int var2) throws RemoteException;

    public void dismissSplitScreenMode(boolean var1) throws RemoteException;

    public boolean enterPictureInPictureMode(IBinder var1, PictureInPictureParams var2) throws RemoteException;

    public boolean finishActivity(IBinder var1, int var2, Intent var3, int var4) throws RemoteException;

    public boolean finishActivityAffinity(IBinder var1) throws RemoteException;

    public void finishSubActivity(IBinder var1, String var2, int var3) throws RemoteException;

    public void finishVoiceTask(IVoiceInteractionSession var1) throws RemoteException;

    public ComponentName getActivityClassForToken(IBinder var1) throws RemoteException;

    public int getActivityDisplayId(IBinder var1) throws RemoteException;

    public Bundle getActivityOptions(IBinder var1) throws RemoteException;

    public List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException;

    public Point getAppTaskThumbnailSize() throws RemoteException;

    public List<IBinder> getAppTasks(String var1) throws RemoteException;

    public Bundle getAssistContextExtras(int var1) throws RemoteException;

    public ComponentName getCallingActivity(IBinder var1) throws RemoteException;

    public String getCallingPackage(IBinder var1) throws RemoteException;

    public ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException;

    public List<ActivityManager.RunningTaskInfo> getFilteredTasks(int var1, int var2, int var3) throws RemoteException;

    public ActivityManager.StackInfo getFocusedStackInfo() throws RemoteException;

    public int getFrontActivityScreenCompatMode() throws RemoteException;

    public int getLastResumedActivityUserId() throws RemoteException;

    public String getLaunchedFromPackage(IBinder var1) throws RemoteException;

    public int getLaunchedFromUid(IBinder var1) throws RemoteException;

    public int getLockTaskModeState() throws RemoteException;

    public int getMaxNumPictureInPictureActions(IBinder var1) throws RemoteException;

    public boolean getPackageAskScreenCompat(String var1) throws RemoteException;

    public String getPackageForToken(IBinder var1) throws RemoteException;

    public int getPackageScreenCompatMode(String var1) throws RemoteException;

    public ParceledListSlice getRecentTasks(int var1, int var2, int var3) throws RemoteException;

    public int getRequestedOrientation(IBinder var1) throws RemoteException;

    public ActivityManager.StackInfo getStackInfo(int var1, int var2) throws RemoteException;

    public Rect getTaskBounds(int var1) throws RemoteException;

    public ActivityManager.TaskDescription getTaskDescription(int var1) throws RemoteException;

    public Bitmap getTaskDescriptionIcon(String var1, int var2) throws RemoteException;

    public int getTaskForActivity(IBinder var1, boolean var2) throws RemoteException;

    public ActivityManager.TaskSnapshot getTaskSnapshot(int var1, boolean var2) throws RemoteException;

    public List<ActivityManager.RunningTaskInfo> getTasks(int var1) throws RemoteException;

    public IBinder getUriPermissionOwnerForActivity(IBinder var1) throws RemoteException;

    public boolean isActivityStartAllowedOnDisplay(int var1, Intent var2, String var3, int var4) throws RemoteException;

    public boolean isAssistDataAllowedOnCurrentActivity() throws RemoteException;

    public boolean isImmersive(IBinder var1) throws RemoteException;

    public boolean isInLockTaskMode() throws RemoteException;

    public boolean isInMultiWindowMode(IBinder var1) throws RemoteException;

    public boolean isInPictureInPictureMode(IBinder var1) throws RemoteException;

    public boolean isRootVoiceInteraction(IBinder var1) throws RemoteException;

    public boolean isTopActivityImmersive() throws RemoteException;

    public boolean isTopOfTask(IBinder var1) throws RemoteException;

    public void keyguardGoingAway(int var1) throws RemoteException;

    public boolean launchAssistIntent(Intent var1, int var2, String var3, int var4, Bundle var5) throws RemoteException;

    public boolean moveActivityTaskToBack(IBinder var1, boolean var2) throws RemoteException;

    public void moveStackToDisplay(int var1, int var2) throws RemoteException;

    public void moveTaskToFront(IApplicationThread var1, String var2, int var3, int var4, Bundle var5) throws RemoteException;

    public void moveTaskToStack(int var1, int var2, boolean var3) throws RemoteException;

    public void moveTasksToFullscreenStack(int var1, boolean var2) throws RemoteException;

    public boolean moveTopActivityToPinnedStack(int var1, Rect var2) throws RemoteException;

    public boolean navigateUpTo(IBinder var1, Intent var2, int var3, Intent var4) throws RemoteException;

    public void notifyActivityDrawn(IBinder var1) throws RemoteException;

    public void notifyEnterAnimationComplete(IBinder var1) throws RemoteException;

    public void notifyLaunchTaskBehindComplete(IBinder var1) throws RemoteException;

    public void notifyPinnedStackAnimationEnded() throws RemoteException;

    public void notifyPinnedStackAnimationStarted() throws RemoteException;

    public void offsetPinnedStackBounds(int var1, Rect var2, int var3, int var4, int var5) throws RemoteException;

    public void onBackPressedOnTaskRoot(IBinder var1, IRequestFinishCallback var2) throws RemoteException;

    public void overridePendingTransition(IBinder var1, String var2, int var3, int var4) throws RemoteException;

    public void positionTaskInStack(int var1, int var2, int var3) throws RemoteException;

    public void registerRemoteAnimationForNextActivityStart(String var1, RemoteAnimationAdapter var2) throws RemoteException;

    public void registerRemoteAnimations(IBinder var1, RemoteAnimationDefinition var2) throws RemoteException;

    public void registerRemoteAnimationsForDisplay(int var1, RemoteAnimationDefinition var2) throws RemoteException;

    public void registerTaskStackListener(ITaskStackListener var1) throws RemoteException;

    public boolean releaseActivityInstance(IBinder var1) throws RemoteException;

    public void releaseSomeActivities(IApplicationThread var1) throws RemoteException;

    public void removeAllVisibleRecentTasks() throws RemoteException;

    public void removeStack(int var1) throws RemoteException;

    public void removeStacksInWindowingModes(int[] var1) throws RemoteException;

    public void removeStacksWithActivityTypes(int[] var1) throws RemoteException;

    public boolean removeTask(int var1) throws RemoteException;

    public void reportActivityFullyDrawn(IBinder var1, boolean var2) throws RemoteException;

    public void reportAssistContextExtras(IBinder var1, Bundle var2, AssistStructure var3, AssistContent var4, Uri var5) throws RemoteException;

    public void reportSizeConfigurations(IBinder var1, int[] var2, int[] var3, int[] var4) throws RemoteException;

    public boolean requestAssistContextExtras(int var1, IAssistDataReceiver var2, Bundle var3, IBinder var4, boolean var5, boolean var6) throws RemoteException;

    public boolean requestAutofillData(IAssistDataReceiver var1, Bundle var2, IBinder var3, int var4) throws RemoteException;

    public IBinder requestStartActivityPermissionToken(IBinder var1) throws RemoteException;

    public void resizeDockedStack(Rect var1, Rect var2, Rect var3, Rect var4, Rect var5) throws RemoteException;

    public void resizePinnedStack(Rect var1, Rect var2) throws RemoteException;

    public void resizeStack(int var1, Rect var2, boolean var3, boolean var4, boolean var5, int var6) throws RemoteException;

    public void resizeTask(int var1, Rect var2, int var3) throws RemoteException;

    public void restartActivityProcessIfVisible(IBinder var1) throws RemoteException;

    public void resumeAppSwitches() throws RemoteException;

    public void setActivityController(IActivityController var1, boolean var2) throws RemoteException;

    public void setDisablePreviewScreenshots(IBinder var1, boolean var2) throws RemoteException;

    public void setDisplayToSingleTaskInstance(int var1) throws RemoteException;

    public void setFocusedStack(int var1) throws RemoteException;

    public void setFocusedTask(int var1) throws RemoteException;

    public void setFrontActivityScreenCompatMode(int var1) throws RemoteException;

    public void setImmersive(IBinder var1, boolean var2) throws RemoteException;

    public void setInheritShowWhenLocked(IBinder var1, boolean var2) throws RemoteException;

    public void setLockScreenShown(boolean var1, boolean var2) throws RemoteException;

    public void setPackageAskScreenCompat(String var1, boolean var2) throws RemoteException;

    public void setPackageScreenCompatMode(String var1, int var2) throws RemoteException;

    public void setPersistentVrThread(int var1) throws RemoteException;

    public void setPictureInPictureParams(IBinder var1, PictureInPictureParams var2) throws RemoteException;

    public void setRequestedOrientation(IBinder var1, int var2) throws RemoteException;

    public void setShowWhenLocked(IBinder var1, boolean var2) throws RemoteException;

    public void setSplitScreenResizing(boolean var1) throws RemoteException;

    public void setTaskDescription(IBinder var1, ActivityManager.TaskDescription var2) throws RemoteException;

    public void setTaskResizeable(int var1, int var2) throws RemoteException;

    public void setTaskWindowingMode(int var1, int var2, boolean var3) throws RemoteException;

    public boolean setTaskWindowingModeSplitScreenPrimary(int var1, int var2, boolean var3, boolean var4, Rect var5, boolean var6) throws RemoteException;

    public void setTurnScreenOn(IBinder var1, boolean var2) throws RemoteException;

    public void setVoiceKeepAwake(IVoiceInteractionSession var1, boolean var2) throws RemoteException;

    public int setVrMode(IBinder var1, boolean var2, ComponentName var3) throws RemoteException;

    public void setVrThread(int var1) throws RemoteException;

    public boolean shouldUpRecreateTask(IBinder var1, String var2) throws RemoteException;

    public boolean showAssistFromActivity(IBinder var1, Bundle var2) throws RemoteException;

    public void showLockTaskEscapeMessage(IBinder var1) throws RemoteException;

    public int startActivities(IApplicationThread var1, String var2, Intent[] var3, String[] var4, IBinder var5, Bundle var6, int var7) throws RemoteException;

    public int startActivity(IApplicationThread var1, String var2, Intent var3, String var4, IBinder var5, String var6, int var7, int var8, ProfilerInfo var9, Bundle var10) throws RemoteException;

    public WaitResult startActivityAndWait(IApplicationThread var1, String var2, Intent var3, String var4, IBinder var5, String var6, int var7, int var8, ProfilerInfo var9, Bundle var10, int var11) throws RemoteException;

    public int startActivityAsCaller(IApplicationThread var1, String var2, Intent var3, String var4, IBinder var5, String var6, int var7, int var8, ProfilerInfo var9, Bundle var10, IBinder var11, boolean var12, int var13) throws RemoteException;

    public int startActivityAsUser(IApplicationThread var1, String var2, Intent var3, String var4, IBinder var5, String var6, int var7, int var8, ProfilerInfo var9, Bundle var10, int var11) throws RemoteException;

    public int startActivityFromRecents(int var1, Bundle var2) throws RemoteException;

    public int startActivityIntentSender(IApplicationThread var1, IIntentSender var2, IBinder var3, Intent var4, String var5, IBinder var6, String var7, int var8, int var9, int var10, Bundle var11) throws RemoteException;

    public int startActivityWithConfig(IApplicationThread var1, String var2, Intent var3, String var4, IBinder var5, String var6, int var7, int var8, Configuration var9, Bundle var10, int var11) throws RemoteException;

    public int startAssistantActivity(String var1, int var2, int var3, Intent var4, String var5, Bundle var6, int var7) throws RemoteException;

    public void startInPlaceAnimationOnFrontMostApplication(Bundle var1) throws RemoteException;

    public void startLocalVoiceInteraction(IBinder var1, Bundle var2) throws RemoteException;

    public void startLockTaskModeByToken(IBinder var1) throws RemoteException;

    public boolean startNextMatchingActivity(IBinder var1, Intent var2, Bundle var3) throws RemoteException;

    public void startRecentsActivity(Intent var1, IAssistDataReceiver var2, IRecentsAnimationRunner var3) throws RemoteException;

    public void startSystemLockTaskMode(int var1) throws RemoteException;

    public int startVoiceActivity(String var1, int var2, int var3, Intent var4, String var5, IVoiceInteractionSession var6, IVoiceInteractor var7, int var8, ProfilerInfo var9, Bundle var10, int var11) throws RemoteException;

    public void stopAppSwitches() throws RemoteException;

    public void stopLocalVoiceInteraction(IBinder var1) throws RemoteException;

    public void stopLockTaskModeByToken(IBinder var1) throws RemoteException;

    public void stopSystemLockTaskMode() throws RemoteException;

    public boolean supportsLocalVoiceInteraction() throws RemoteException;

    public void suppressResizeConfigChanges(boolean var1) throws RemoteException;

    public void toggleFreeformWindowingMode(IBinder var1) throws RemoteException;

    public void unhandledBack() throws RemoteException;

    public void unregisterTaskStackListener(ITaskStackListener var1) throws RemoteException;

    public boolean updateConfiguration(Configuration var1) throws RemoteException;

    public boolean updateDisplayOverrideConfiguration(Configuration var1, int var2) throws RemoteException;

    public void updateLockTaskFeatures(int var1, int var2) throws RemoteException;

    public void updateLockTaskPackages(int var1, String[] var2) throws RemoteException;

    public boolean willActivityBeVisible(IBinder var1) throws RemoteException;

    public static class Default
    implements IActivityTaskManager {
        @Override
        public void activityDestroyed(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void activityIdle(IBinder iBinder, Configuration configuration, boolean bl) throws RemoteException {
        }

        @Override
        public void activityPaused(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void activityRelaunched(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void activityResumed(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void activitySlept(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void activityStopped(IBinder iBinder, Bundle bundle, PersistableBundle persistableBundle, CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void activityTopResumedStateLost() throws RemoteException {
        }

        @Override
        public int addAppTask(IBinder iBinder, Intent intent, ActivityManager.TaskDescription taskDescription, Bitmap bitmap) throws RemoteException {
            return 0;
        }

        @Override
        public void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelRecentsAnimation(boolean bl) throws RemoteException {
        }

        @Override
        public void cancelTaskWindowTransition(int n) throws RemoteException {
        }

        @Override
        public void clearLaunchParamsForPackages(List<String> list) throws RemoteException {
        }

        @Override
        public boolean convertFromTranslucent(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override
        public boolean convertToTranslucent(IBinder iBinder, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override
        public void dismissKeyguard(IBinder iBinder, IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void dismissPip(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void dismissSplitScreenMode(boolean bl) throws RemoteException {
        }

        @Override
        public boolean enterPictureInPictureMode(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException {
            return false;
        }

        @Override
        public boolean finishActivity(IBinder iBinder, int n, Intent intent, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean finishActivityAffinity(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override
        public void finishSubActivity(IBinder iBinder, String string2, int n) throws RemoteException {
        }

        @Override
        public void finishVoiceTask(IVoiceInteractionSession iVoiceInteractionSession) throws RemoteException {
        }

        @Override
        public ComponentName getActivityClassForToken(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public int getActivityDisplayId(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public Bundle getActivityOptions(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException {
            return null;
        }

        @Override
        public Point getAppTaskThumbnailSize() throws RemoteException {
            return null;
        }

        @Override
        public List<IBinder> getAppTasks(String string2) throws RemoteException {
            return null;
        }

        @Override
        public Bundle getAssistContextExtras(int n) throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getCallingActivity(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public String getCallingPackage(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException {
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
        public int getFrontActivityScreenCompatMode() throws RemoteException {
            return 0;
        }

        @Override
        public int getLastResumedActivityUserId() throws RemoteException {
            return 0;
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
        public int getLockTaskModeState() throws RemoteException {
            return 0;
        }

        @Override
        public int getMaxNumPictureInPictureActions(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public boolean getPackageAskScreenCompat(String string2) throws RemoteException {
            return false;
        }

        @Override
        public String getPackageForToken(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public int getPackageScreenCompatMode(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public ParceledListSlice getRecentTasks(int n, int n2, int n3) throws RemoteException {
            return null;
        }

        @Override
        public int getRequestedOrientation(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public ActivityManager.StackInfo getStackInfo(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public Rect getTaskBounds(int n) throws RemoteException {
            return null;
        }

        @Override
        public ActivityManager.TaskDescription getTaskDescription(int n) throws RemoteException {
            return null;
        }

        @Override
        public Bitmap getTaskDescriptionIcon(String string2, int n) throws RemoteException {
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
        public IBinder getUriPermissionOwnerForActivity(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public boolean isActivityStartAllowedOnDisplay(int n, Intent intent, String string2, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isAssistDataAllowedOnCurrentActivity() throws RemoteException {
            return false;
        }

        @Override
        public boolean isImmersive(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override
        public boolean isInLockTaskMode() throws RemoteException {
            return false;
        }

        @Override
        public boolean isInMultiWindowMode(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override
        public boolean isInPictureInPictureMode(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override
        public boolean isRootVoiceInteraction(IBinder iBinder) throws RemoteException {
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
        public void keyguardGoingAway(int n) throws RemoteException {
        }

        @Override
        public boolean launchAssistIntent(Intent intent, int n, String string2, int n2, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override
        public boolean moveActivityTaskToBack(IBinder iBinder, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void moveStackToDisplay(int n, int n2) throws RemoteException {
        }

        @Override
        public void moveTaskToFront(IApplicationThread iApplicationThread, String string2, int n, int n2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void moveTaskToStack(int n, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public void moveTasksToFullscreenStack(int n, boolean bl) throws RemoteException {
        }

        @Override
        public boolean moveTopActivityToPinnedStack(int n, Rect rect) throws RemoteException {
            return false;
        }

        @Override
        public boolean navigateUpTo(IBinder iBinder, Intent intent, int n, Intent intent2) throws RemoteException {
            return false;
        }

        @Override
        public void notifyActivityDrawn(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void notifyEnterAnimationComplete(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void notifyLaunchTaskBehindComplete(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void notifyPinnedStackAnimationEnded() throws RemoteException {
        }

        @Override
        public void notifyPinnedStackAnimationStarted() throws RemoteException {
        }

        @Override
        public void offsetPinnedStackBounds(int n, Rect rect, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void onBackPressedOnTaskRoot(IBinder iBinder, IRequestFinishCallback iRequestFinishCallback) throws RemoteException {
        }

        @Override
        public void overridePendingTransition(IBinder iBinder, String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public void positionTaskInStack(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void registerRemoteAnimationForNextActivityStart(String string2, RemoteAnimationAdapter remoteAnimationAdapter) throws RemoteException {
        }

        @Override
        public void registerRemoteAnimations(IBinder iBinder, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
        }

        @Override
        public void registerRemoteAnimationsForDisplay(int n, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
        }

        @Override
        public void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
        }

        @Override
        public boolean releaseActivityInstance(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override
        public void releaseSomeActivities(IApplicationThread iApplicationThread) throws RemoteException {
        }

        @Override
        public void removeAllVisibleRecentTasks() throws RemoteException {
        }

        @Override
        public void removeStack(int n) throws RemoteException {
        }

        @Override
        public void removeStacksInWindowingModes(int[] arrn) throws RemoteException {
        }

        @Override
        public void removeStacksWithActivityTypes(int[] arrn) throws RemoteException {
        }

        @Override
        public boolean removeTask(int n) throws RemoteException {
            return false;
        }

        @Override
        public void reportActivityFullyDrawn(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public void reportAssistContextExtras(IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, Uri uri) throws RemoteException {
        }

        @Override
        public void reportSizeConfigurations(IBinder iBinder, int[] arrn, int[] arrn2, int[] arrn3) throws RemoteException {
        }

        @Override
        public boolean requestAssistContextExtras(int n, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean bl, boolean bl2) throws RemoteException {
            return false;
        }

        @Override
        public boolean requestAutofillData(IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, int n) throws RemoteException {
            return false;
        }

        @Override
        public IBinder requestStartActivityPermissionToken(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public void resizeDockedStack(Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5) throws RemoteException {
        }

        @Override
        public void resizePinnedStack(Rect rect, Rect rect2) throws RemoteException {
        }

        @Override
        public void resizeStack(int n, Rect rect, boolean bl, boolean bl2, boolean bl3, int n2) throws RemoteException {
        }

        @Override
        public void resizeTask(int n, Rect rect, int n2) throws RemoteException {
        }

        @Override
        public void restartActivityProcessIfVisible(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void resumeAppSwitches() throws RemoteException {
        }

        @Override
        public void setActivityController(IActivityController iActivityController, boolean bl) throws RemoteException {
        }

        @Override
        public void setDisablePreviewScreenshots(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public void setDisplayToSingleTaskInstance(int n) throws RemoteException {
        }

        @Override
        public void setFocusedStack(int n) throws RemoteException {
        }

        @Override
        public void setFocusedTask(int n) throws RemoteException {
        }

        @Override
        public void setFrontActivityScreenCompatMode(int n) throws RemoteException {
        }

        @Override
        public void setImmersive(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public void setInheritShowWhenLocked(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public void setLockScreenShown(boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void setPackageAskScreenCompat(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void setPackageScreenCompatMode(String string2, int n) throws RemoteException {
        }

        @Override
        public void setPersistentVrThread(int n) throws RemoteException {
        }

        @Override
        public void setPictureInPictureParams(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException {
        }

        @Override
        public void setRequestedOrientation(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void setShowWhenLocked(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public void setSplitScreenResizing(boolean bl) throws RemoteException {
        }

        @Override
        public void setTaskDescription(IBinder iBinder, ActivityManager.TaskDescription taskDescription) throws RemoteException {
        }

        @Override
        public void setTaskResizeable(int n, int n2) throws RemoteException {
        }

        @Override
        public void setTaskWindowingMode(int n, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public boolean setTaskWindowingModeSplitScreenPrimary(int n, int n2, boolean bl, boolean bl2, Rect rect, boolean bl3) throws RemoteException {
            return false;
        }

        @Override
        public void setTurnScreenOn(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public void setVoiceKeepAwake(IVoiceInteractionSession iVoiceInteractionSession, boolean bl) throws RemoteException {
        }

        @Override
        public int setVrMode(IBinder iBinder, boolean bl, ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override
        public void setVrThread(int n) throws RemoteException {
        }

        @Override
        public boolean shouldUpRecreateTask(IBinder iBinder, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean showAssistFromActivity(IBinder iBinder, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override
        public void showLockTaskEscapeMessage(IBinder iBinder) throws RemoteException {
        }

        @Override
        public int startActivities(IApplicationThread iApplicationThread, String string2, Intent[] arrintent, String[] arrstring, IBinder iBinder, Bundle bundle, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int startActivity(IApplicationThread iApplicationThread, String string2, Intent intent, String string3, IBinder iBinder, String string4, int n, int n2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override
        public WaitResult startActivityAndWait(IApplicationThread iApplicationThread, String string2, Intent intent, String string3, IBinder iBinder, String string4, int n, int n2, ProfilerInfo profilerInfo, Bundle bundle, int n3) throws RemoteException {
            return null;
        }

        @Override
        public int startActivityAsCaller(IApplicationThread iApplicationThread, String string2, Intent intent, String string3, IBinder iBinder, String string4, int n, int n2, ProfilerInfo profilerInfo, Bundle bundle, IBinder iBinder2, boolean bl, int n3) throws RemoteException {
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
        public int startActivityIntentSender(IApplicationThread iApplicationThread, IIntentSender iIntentSender, IBinder iBinder, Intent intent, String string2, IBinder iBinder2, String string3, int n, int n2, int n3, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override
        public int startActivityWithConfig(IApplicationThread iApplicationThread, String string2, Intent intent, String string3, IBinder iBinder, String string4, int n, int n2, Configuration configuration, Bundle bundle, int n3) throws RemoteException {
            return 0;
        }

        @Override
        public int startAssistantActivity(String string2, int n, int n2, Intent intent, String string3, Bundle bundle, int n3) throws RemoteException {
            return 0;
        }

        @Override
        public void startInPlaceAnimationOnFrontMostApplication(Bundle bundle) throws RemoteException {
        }

        @Override
        public void startLocalVoiceInteraction(IBinder iBinder, Bundle bundle) throws RemoteException {
        }

        @Override
        public void startLockTaskModeByToken(IBinder iBinder) throws RemoteException {
        }

        @Override
        public boolean startNextMatchingActivity(IBinder iBinder, Intent intent, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override
        public void startRecentsActivity(Intent intent, IAssistDataReceiver iAssistDataReceiver, IRecentsAnimationRunner iRecentsAnimationRunner) throws RemoteException {
        }

        @Override
        public void startSystemLockTaskMode(int n) throws RemoteException {
        }

        @Override
        public int startVoiceActivity(String string2, int n, int n2, Intent intent, String string3, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor, int n3, ProfilerInfo profilerInfo, Bundle bundle, int n4) throws RemoteException {
            return 0;
        }

        @Override
        public void stopAppSwitches() throws RemoteException {
        }

        @Override
        public void stopLocalVoiceInteraction(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void stopLockTaskModeByToken(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void stopSystemLockTaskMode() throws RemoteException {
        }

        @Override
        public boolean supportsLocalVoiceInteraction() throws RemoteException {
            return false;
        }

        @Override
        public void suppressResizeConfigChanges(boolean bl) throws RemoteException {
        }

        @Override
        public void toggleFreeformWindowingMode(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void unhandledBack() throws RemoteException {
        }

        @Override
        public void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
        }

        @Override
        public boolean updateConfiguration(Configuration configuration) throws RemoteException {
            return false;
        }

        @Override
        public boolean updateDisplayOverrideConfiguration(Configuration configuration, int n) throws RemoteException {
            return false;
        }

        @Override
        public void updateLockTaskFeatures(int n, int n2) throws RemoteException {
        }

        @Override
        public void updateLockTaskPackages(int n, String[] arrstring) throws RemoteException {
        }

        @Override
        public boolean willActivityBeVisible(IBinder iBinder) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IActivityTaskManager {
        private static final String DESCRIPTOR = "android.app.IActivityTaskManager";
        static final int TRANSACTION_activityDestroyed = 22;
        static final int TRANSACTION_activityIdle = 17;
        static final int TRANSACTION_activityPaused = 20;
        static final int TRANSACTION_activityRelaunched = 23;
        static final int TRANSACTION_activityResumed = 18;
        static final int TRANSACTION_activitySlept = 24;
        static final int TRANSACTION_activityStopped = 21;
        static final int TRANSACTION_activityTopResumedStateLost = 19;
        static final int TRANSACTION_addAppTask = 75;
        static final int TRANSACTION_alwaysShowUnsupportedCompileSdkWarning = 147;
        static final int TRANSACTION_cancelRecentsAnimation = 60;
        static final int TRANSACTION_cancelTaskWindowTransition = 135;
        static final int TRANSACTION_clearLaunchParamsForPackages = 158;
        static final int TRANSACTION_convertFromTranslucent = 43;
        static final int TRANSACTION_convertToTranslucent = 44;
        static final int TRANSACTION_dismissKeyguard = 134;
        static final int TRANSACTION_dismissPip = 113;
        static final int TRANSACTION_dismissSplitScreenMode = 112;
        static final int TRANSACTION_enterPictureInPictureMode = 119;
        static final int TRANSACTION_finishActivity = 15;
        static final int TRANSACTION_finishActivityAffinity = 16;
        static final int TRANSACTION_finishSubActivity = 38;
        static final int TRANSACTION_finishVoiceTask = 71;
        static final int TRANSACTION_getActivityClassForToken = 108;
        static final int TRANSACTION_getActivityDisplayId = 47;
        static final int TRANSACTION_getActivityOptions = 67;
        static final int TRANSACTION_getAllStackInfos = 96;
        static final int TRANSACTION_getAppTaskThumbnailSize = 76;
        static final int TRANSACTION_getAppTasks = 68;
        static final int TRANSACTION_getAssistContextExtras = 99;
        static final int TRANSACTION_getCallingActivity = 28;
        static final int TRANSACTION_getCallingPackage = 27;
        static final int TRANSACTION_getDeviceConfigurationInfo = 131;
        static final int TRANSACTION_getFilteredTasks = 33;
        static final int TRANSACTION_getFocusedStackInfo = 58;
        static final int TRANSACTION_getFrontActivityScreenCompatMode = 25;
        static final int TRANSACTION_getLastResumedActivityUserId = 138;
        static final int TRANSACTION_getLaunchedFromPackage = 55;
        static final int TRANSACTION_getLaunchedFromUid = 54;
        static final int TRANSACTION_getLockTaskModeState = 65;
        static final int TRANSACTION_getMaxNumPictureInPictureActions = 121;
        static final int TRANSACTION_getPackageAskScreenCompat = 156;
        static final int TRANSACTION_getPackageForToken = 109;
        static final int TRANSACTION_getPackageScreenCompatMode = 154;
        static final int TRANSACTION_getRecentTasks = 39;
        static final int TRANSACTION_getRequestedOrientation = 42;
        static final int TRANSACTION_getStackInfo = 97;
        static final int TRANSACTION_getTaskBounds = 59;
        static final int TRANSACTION_getTaskDescription = 52;
        static final int TRANSACTION_getTaskDescriptionIcon = 80;
        static final int TRANSACTION_getTaskForActivity = 37;
        static final int TRANSACTION_getTaskSnapshot = 136;
        static final int TRANSACTION_getTasks = 32;
        static final int TRANSACTION_getUriPermissionOwnerForActivity = 122;
        static final int TRANSACTION_isActivityStartAllowedOnDisplay = 13;
        static final int TRANSACTION_isAssistDataAllowedOnCurrentActivity = 103;
        static final int TRANSACTION_isImmersive = 48;
        static final int TRANSACTION_isInLockTaskMode = 64;
        static final int TRANSACTION_isInMultiWindowMode = 117;
        static final int TRANSACTION_isInPictureInPictureMode = 118;
        static final int TRANSACTION_isRootVoiceInteraction = 105;
        static final int TRANSACTION_isTopActivityImmersive = 50;
        static final int TRANSACTION_isTopOfTask = 72;
        static final int TRANSACTION_keyguardGoingAway = 107;
        static final int TRANSACTION_launchAssistIntent = 100;
        static final int TRANSACTION_moveActivityTaskToBack = 51;
        static final int TRANSACTION_moveStackToDisplay = 87;
        static final int TRANSACTION_moveTaskToFront = 36;
        static final int TRANSACTION_moveTaskToStack = 90;
        static final int TRANSACTION_moveTasksToFullscreenStack = 115;
        static final int TRANSACTION_moveTopActivityToPinnedStack = 116;
        static final int TRANSACTION_navigateUpTo = 35;
        static final int TRANSACTION_notifyActivityDrawn = 45;
        static final int TRANSACTION_notifyEnterAnimationComplete = 74;
        static final int TRANSACTION_notifyLaunchTaskBehindComplete = 73;
        static final int TRANSACTION_notifyPinnedStackAnimationEnded = 130;
        static final int TRANSACTION_notifyPinnedStackAnimationStarted = 129;
        static final int TRANSACTION_offsetPinnedStackBounds = 93;
        static final int TRANSACTION_onBackPressedOnTaskRoot = 161;
        static final int TRANSACTION_overridePendingTransition = 53;
        static final int TRANSACTION_positionTaskInStack = 110;
        static final int TRANSACTION_registerRemoteAnimationForNextActivityStart = 145;
        static final int TRANSACTION_registerRemoteAnimations = 144;
        static final int TRANSACTION_registerRemoteAnimationsForDisplay = 146;
        static final int TRANSACTION_registerTaskStackListener = 82;
        static final int TRANSACTION_releaseActivityInstance = 77;
        static final int TRANSACTION_releaseSomeActivities = 79;
        static final int TRANSACTION_removeAllVisibleRecentTasks = 31;
        static final int TRANSACTION_removeStack = 88;
        static final int TRANSACTION_removeStacksInWindowingModes = 94;
        static final int TRANSACTION_removeStacksWithActivityTypes = 95;
        static final int TRANSACTION_removeTask = 30;
        static final int TRANSACTION_reportActivityFullyDrawn = 46;
        static final int TRANSACTION_reportAssistContextExtras = 56;
        static final int TRANSACTION_reportSizeConfigurations = 111;
        static final int TRANSACTION_requestAssistContextExtras = 101;
        static final int TRANSACTION_requestAutofillData = 102;
        static final int TRANSACTION_requestStartActivityPermissionToken = 78;
        static final int TRANSACTION_resizeDockedStack = 123;
        static final int TRANSACTION_resizePinnedStack = 132;
        static final int TRANSACTION_resizeStack = 91;
        static final int TRANSACTION_resizeTask = 86;
        static final int TRANSACTION_restartActivityProcessIfVisible = 160;
        static final int TRANSACTION_resumeAppSwitches = 151;
        static final int TRANSACTION_setActivityController = 152;
        static final int TRANSACTION_setDisablePreviewScreenshots = 137;
        static final int TRANSACTION_setDisplayToSingleTaskInstance = 159;
        static final int TRANSACTION_setFocusedStack = 57;
        static final int TRANSACTION_setFocusedTask = 29;
        static final int TRANSACTION_setFrontActivityScreenCompatMode = 26;
        static final int TRANSACTION_setImmersive = 49;
        static final int TRANSACTION_setInheritShowWhenLocked = 142;
        static final int TRANSACTION_setLockScreenShown = 98;
        static final int TRANSACTION_setPackageAskScreenCompat = 157;
        static final int TRANSACTION_setPackageScreenCompatMode = 155;
        static final int TRANSACTION_setPersistentVrThread = 149;
        static final int TRANSACTION_setPictureInPictureParams = 120;
        static final int TRANSACTION_setRequestedOrientation = 41;
        static final int TRANSACTION_setShowWhenLocked = 141;
        static final int TRANSACTION_setSplitScreenResizing = 124;
        static final int TRANSACTION_setTaskDescription = 66;
        static final int TRANSACTION_setTaskResizeable = 84;
        static final int TRANSACTION_setTaskWindowingMode = 89;
        static final int TRANSACTION_setTaskWindowingModeSplitScreenPrimary = 92;
        static final int TRANSACTION_setTurnScreenOn = 143;
        static final int TRANSACTION_setVoiceKeepAwake = 153;
        static final int TRANSACTION_setVrMode = 125;
        static final int TRANSACTION_setVrThread = 148;
        static final int TRANSACTION_shouldUpRecreateTask = 34;
        static final int TRANSACTION_showAssistFromActivity = 104;
        static final int TRANSACTION_showLockTaskEscapeMessage = 106;
        static final int TRANSACTION_startActivities = 2;
        static final int TRANSACTION_startActivity = 1;
        static final int TRANSACTION_startActivityAndWait = 6;
        static final int TRANSACTION_startActivityAsCaller = 12;
        static final int TRANSACTION_startActivityAsUser = 3;
        static final int TRANSACTION_startActivityFromRecents = 11;
        static final int TRANSACTION_startActivityIntentSender = 5;
        static final int TRANSACTION_startActivityWithConfig = 7;
        static final int TRANSACTION_startAssistantActivity = 9;
        static final int TRANSACTION_startInPlaceAnimationOnFrontMostApplication = 81;
        static final int TRANSACTION_startLocalVoiceInteraction = 126;
        static final int TRANSACTION_startLockTaskModeByToken = 61;
        static final int TRANSACTION_startNextMatchingActivity = 4;
        static final int TRANSACTION_startRecentsActivity = 10;
        static final int TRANSACTION_startSystemLockTaskMode = 69;
        static final int TRANSACTION_startVoiceActivity = 8;
        static final int TRANSACTION_stopAppSwitches = 150;
        static final int TRANSACTION_stopLocalVoiceInteraction = 127;
        static final int TRANSACTION_stopLockTaskModeByToken = 62;
        static final int TRANSACTION_stopSystemLockTaskMode = 70;
        static final int TRANSACTION_supportsLocalVoiceInteraction = 128;
        static final int TRANSACTION_suppressResizeConfigChanges = 114;
        static final int TRANSACTION_toggleFreeformWindowingMode = 85;
        static final int TRANSACTION_unhandledBack = 14;
        static final int TRANSACTION_unregisterTaskStackListener = 83;
        static final int TRANSACTION_updateConfiguration = 139;
        static final int TRANSACTION_updateDisplayOverrideConfiguration = 133;
        static final int TRANSACTION_updateLockTaskFeatures = 140;
        static final int TRANSACTION_updateLockTaskPackages = 63;
        static final int TRANSACTION_willActivityBeVisible = 40;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IActivityTaskManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IActivityTaskManager) {
                return (IActivityTaskManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IActivityTaskManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 161: {
                    return "onBackPressedOnTaskRoot";
                }
                case 160: {
                    return "restartActivityProcessIfVisible";
                }
                case 159: {
                    return "setDisplayToSingleTaskInstance";
                }
                case 158: {
                    return "clearLaunchParamsForPackages";
                }
                case 157: {
                    return "setPackageAskScreenCompat";
                }
                case 156: {
                    return "getPackageAskScreenCompat";
                }
                case 155: {
                    return "setPackageScreenCompatMode";
                }
                case 154: {
                    return "getPackageScreenCompatMode";
                }
                case 153: {
                    return "setVoiceKeepAwake";
                }
                case 152: {
                    return "setActivityController";
                }
                case 151: {
                    return "resumeAppSwitches";
                }
                case 150: {
                    return "stopAppSwitches";
                }
                case 149: {
                    return "setPersistentVrThread";
                }
                case 148: {
                    return "setVrThread";
                }
                case 147: {
                    return "alwaysShowUnsupportedCompileSdkWarning";
                }
                case 146: {
                    return "registerRemoteAnimationsForDisplay";
                }
                case 145: {
                    return "registerRemoteAnimationForNextActivityStart";
                }
                case 144: {
                    return "registerRemoteAnimations";
                }
                case 143: {
                    return "setTurnScreenOn";
                }
                case 142: {
                    return "setInheritShowWhenLocked";
                }
                case 141: {
                    return "setShowWhenLocked";
                }
                case 140: {
                    return "updateLockTaskFeatures";
                }
                case 139: {
                    return "updateConfiguration";
                }
                case 138: {
                    return "getLastResumedActivityUserId";
                }
                case 137: {
                    return "setDisablePreviewScreenshots";
                }
                case 136: {
                    return "getTaskSnapshot";
                }
                case 135: {
                    return "cancelTaskWindowTransition";
                }
                case 134: {
                    return "dismissKeyguard";
                }
                case 133: {
                    return "updateDisplayOverrideConfiguration";
                }
                case 132: {
                    return "resizePinnedStack";
                }
                case 131: {
                    return "getDeviceConfigurationInfo";
                }
                case 130: {
                    return "notifyPinnedStackAnimationEnded";
                }
                case 129: {
                    return "notifyPinnedStackAnimationStarted";
                }
                case 128: {
                    return "supportsLocalVoiceInteraction";
                }
                case 127: {
                    return "stopLocalVoiceInteraction";
                }
                case 126: {
                    return "startLocalVoiceInteraction";
                }
                case 125: {
                    return "setVrMode";
                }
                case 124: {
                    return "setSplitScreenResizing";
                }
                case 123: {
                    return "resizeDockedStack";
                }
                case 122: {
                    return "getUriPermissionOwnerForActivity";
                }
                case 121: {
                    return "getMaxNumPictureInPictureActions";
                }
                case 120: {
                    return "setPictureInPictureParams";
                }
                case 119: {
                    return "enterPictureInPictureMode";
                }
                case 118: {
                    return "isInPictureInPictureMode";
                }
                case 117: {
                    return "isInMultiWindowMode";
                }
                case 116: {
                    return "moveTopActivityToPinnedStack";
                }
                case 115: {
                    return "moveTasksToFullscreenStack";
                }
                case 114: {
                    return "suppressResizeConfigChanges";
                }
                case 113: {
                    return "dismissPip";
                }
                case 112: {
                    return "dismissSplitScreenMode";
                }
                case 111: {
                    return "reportSizeConfigurations";
                }
                case 110: {
                    return "positionTaskInStack";
                }
                case 109: {
                    return "getPackageForToken";
                }
                case 108: {
                    return "getActivityClassForToken";
                }
                case 107: {
                    return "keyguardGoingAway";
                }
                case 106: {
                    return "showLockTaskEscapeMessage";
                }
                case 105: {
                    return "isRootVoiceInteraction";
                }
                case 104: {
                    return "showAssistFromActivity";
                }
                case 103: {
                    return "isAssistDataAllowedOnCurrentActivity";
                }
                case 102: {
                    return "requestAutofillData";
                }
                case 101: {
                    return "requestAssistContextExtras";
                }
                case 100: {
                    return "launchAssistIntent";
                }
                case 99: {
                    return "getAssistContextExtras";
                }
                case 98: {
                    return "setLockScreenShown";
                }
                case 97: {
                    return "getStackInfo";
                }
                case 96: {
                    return "getAllStackInfos";
                }
                case 95: {
                    return "removeStacksWithActivityTypes";
                }
                case 94: {
                    return "removeStacksInWindowingModes";
                }
                case 93: {
                    return "offsetPinnedStackBounds";
                }
                case 92: {
                    return "setTaskWindowingModeSplitScreenPrimary";
                }
                case 91: {
                    return "resizeStack";
                }
                case 90: {
                    return "moveTaskToStack";
                }
                case 89: {
                    return "setTaskWindowingMode";
                }
                case 88: {
                    return "removeStack";
                }
                case 87: {
                    return "moveStackToDisplay";
                }
                case 86: {
                    return "resizeTask";
                }
                case 85: {
                    return "toggleFreeformWindowingMode";
                }
                case 84: {
                    return "setTaskResizeable";
                }
                case 83: {
                    return "unregisterTaskStackListener";
                }
                case 82: {
                    return "registerTaskStackListener";
                }
                case 81: {
                    return "startInPlaceAnimationOnFrontMostApplication";
                }
                case 80: {
                    return "getTaskDescriptionIcon";
                }
                case 79: {
                    return "releaseSomeActivities";
                }
                case 78: {
                    return "requestStartActivityPermissionToken";
                }
                case 77: {
                    return "releaseActivityInstance";
                }
                case 76: {
                    return "getAppTaskThumbnailSize";
                }
                case 75: {
                    return "addAppTask";
                }
                case 74: {
                    return "notifyEnterAnimationComplete";
                }
                case 73: {
                    return "notifyLaunchTaskBehindComplete";
                }
                case 72: {
                    return "isTopOfTask";
                }
                case 71: {
                    return "finishVoiceTask";
                }
                case 70: {
                    return "stopSystemLockTaskMode";
                }
                case 69: {
                    return "startSystemLockTaskMode";
                }
                case 68: {
                    return "getAppTasks";
                }
                case 67: {
                    return "getActivityOptions";
                }
                case 66: {
                    return "setTaskDescription";
                }
                case 65: {
                    return "getLockTaskModeState";
                }
                case 64: {
                    return "isInLockTaskMode";
                }
                case 63: {
                    return "updateLockTaskPackages";
                }
                case 62: {
                    return "stopLockTaskModeByToken";
                }
                case 61: {
                    return "startLockTaskModeByToken";
                }
                case 60: {
                    return "cancelRecentsAnimation";
                }
                case 59: {
                    return "getTaskBounds";
                }
                case 58: {
                    return "getFocusedStackInfo";
                }
                case 57: {
                    return "setFocusedStack";
                }
                case 56: {
                    return "reportAssistContextExtras";
                }
                case 55: {
                    return "getLaunchedFromPackage";
                }
                case 54: {
                    return "getLaunchedFromUid";
                }
                case 53: {
                    return "overridePendingTransition";
                }
                case 52: {
                    return "getTaskDescription";
                }
                case 51: {
                    return "moveActivityTaskToBack";
                }
                case 50: {
                    return "isTopActivityImmersive";
                }
                case 49: {
                    return "setImmersive";
                }
                case 48: {
                    return "isImmersive";
                }
                case 47: {
                    return "getActivityDisplayId";
                }
                case 46: {
                    return "reportActivityFullyDrawn";
                }
                case 45: {
                    return "notifyActivityDrawn";
                }
                case 44: {
                    return "convertToTranslucent";
                }
                case 43: {
                    return "convertFromTranslucent";
                }
                case 42: {
                    return "getRequestedOrientation";
                }
                case 41: {
                    return "setRequestedOrientation";
                }
                case 40: {
                    return "willActivityBeVisible";
                }
                case 39: {
                    return "getRecentTasks";
                }
                case 38: {
                    return "finishSubActivity";
                }
                case 37: {
                    return "getTaskForActivity";
                }
                case 36: {
                    return "moveTaskToFront";
                }
                case 35: {
                    return "navigateUpTo";
                }
                case 34: {
                    return "shouldUpRecreateTask";
                }
                case 33: {
                    return "getFilteredTasks";
                }
                case 32: {
                    return "getTasks";
                }
                case 31: {
                    return "removeAllVisibleRecentTasks";
                }
                case 30: {
                    return "removeTask";
                }
                case 29: {
                    return "setFocusedTask";
                }
                case 28: {
                    return "getCallingActivity";
                }
                case 27: {
                    return "getCallingPackage";
                }
                case 26: {
                    return "setFrontActivityScreenCompatMode";
                }
                case 25: {
                    return "getFrontActivityScreenCompatMode";
                }
                case 24: {
                    return "activitySlept";
                }
                case 23: {
                    return "activityRelaunched";
                }
                case 22: {
                    return "activityDestroyed";
                }
                case 21: {
                    return "activityStopped";
                }
                case 20: {
                    return "activityPaused";
                }
                case 19: {
                    return "activityTopResumedStateLost";
                }
                case 18: {
                    return "activityResumed";
                }
                case 17: {
                    return "activityIdle";
                }
                case 16: {
                    return "finishActivityAffinity";
                }
                case 15: {
                    return "finishActivity";
                }
                case 14: {
                    return "unhandledBack";
                }
                case 13: {
                    return "isActivityStartAllowedOnDisplay";
                }
                case 12: {
                    return "startActivityAsCaller";
                }
                case 11: {
                    return "startActivityFromRecents";
                }
                case 10: {
                    return "startRecentsActivity";
                }
                case 9: {
                    return "startAssistantActivity";
                }
                case 8: {
                    return "startVoiceActivity";
                }
                case 7: {
                    return "startActivityWithConfig";
                }
                case 6: {
                    return "startActivityAndWait";
                }
                case 5: {
                    return "startActivityIntentSender";
                }
                case 4: {
                    return "startNextMatchingActivity";
                }
                case 3: {
                    return "startActivityAsUser";
                }
                case 2: {
                    return "startActivities";
                }
                case 1: 
            }
            return "startActivity";
        }

        public static boolean setDefaultImpl(IActivityTaskManager iActivityTaskManager) {
            if (Proxy.sDefaultImpl == null && iActivityTaskManager != null) {
                Proxy.sDefaultImpl = iActivityTaskManager;
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
                boolean bl22 = false;
                boolean bl23 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 161: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onBackPressedOnTaskRoot(((Parcel)object).readStrongBinder(), IRequestFinishCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 160: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.restartActivityProcessIfVisible(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 159: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDisplayToSingleTaskInstance(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 158: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearLaunchParamsForPackages(((Parcel)object).createStringArrayList());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 157: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.setPackageAskScreenCompat(string2, bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 156: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPackageAskScreenCompat(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 155: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setPackageScreenCompatMode(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 154: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPackageScreenCompatMode(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 153: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IVoiceInteractionSession iVoiceInteractionSession = IVoiceInteractionSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        bl23 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.setVoiceKeepAwake(iVoiceInteractionSession, bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 152: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IActivityController iActivityController = IActivityController.Stub.asInterface(((Parcel)object).readStrongBinder());
                        bl23 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.setActivityController(iActivityController, bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 151: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resumeAppSwitches();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 150: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopAppSwitches();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 149: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setPersistentVrThread(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 148: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setVrThread(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 147: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.alwaysShowUnsupportedCompileSdkWarning((ComponentName)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 146: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RemoteAnimationDefinition.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registerRemoteAnimationsForDisplay(n, (RemoteAnimationDefinition)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 145: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? RemoteAnimationAdapter.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registerRemoteAnimationForNextActivityStart(string3, (RemoteAnimationAdapter)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 144: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object = ((Parcel)object).readInt() != 0 ? RemoteAnimationDefinition.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registerRemoteAnimations(iBinder, (RemoteAnimationDefinition)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 143: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl23 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.setTurnScreenOn(iBinder, bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 142: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl23 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.setInheritShowWhenLocked(iBinder, bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 141: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl23 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.setShowWhenLocked(iBinder, bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 140: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateLockTaskFeatures(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 139: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Configuration.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.updateConfiguration((Configuration)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 138: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getLastResumedActivityUserId();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 137: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl23 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.setDisablePreviewScreenshots(iBinder, bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 136: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl23 = ((Parcel)object).readInt() != 0;
                        object = this.getTaskSnapshot(n, bl23);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ActivityManager.TaskSnapshot)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 135: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelTaskWindowTransition(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 134: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        IKeyguardDismissCallback iKeyguardDismissCallback = IKeyguardDismissCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.dismissKeyguard(iBinder, iKeyguardDismissCallback, (CharSequence)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 133: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Configuration configuration = ((Parcel)object).readInt() != 0 ? Configuration.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.updateDisplayOverrideConfiguration(configuration, ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 132: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.resizePinnedStack(rect, (Rect)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 131: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDeviceConfigurationInfo();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ConfigurationInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 130: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyPinnedStackAnimationEnded();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 129: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyPinnedStackAnimationStarted();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 128: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.supportsLocalVoiceInteraction() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 127: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopLocalVoiceInteraction(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 126: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startLocalVoiceInteraction(iBinder, (Bundle)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 125: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl23 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setVrMode(iBinder, bl23, (ComponentName)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 124: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl23 = bl8;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.setSplitScreenResizing(bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 123: {
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
                    case 122: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUriPermissionOwnerForActivity(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 121: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getMaxNumPictureInPictureActions(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 120: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object = ((Parcel)object).readInt() != 0 ? PictureInPictureParams.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setPictureInPictureParams(iBinder, (PictureInPictureParams)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 119: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object = ((Parcel)object).readInt() != 0 ? PictureInPictureParams.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.enterPictureInPictureMode(iBinder, (PictureInPictureParams)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 118: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInPictureInPictureMode(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 117: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInMultiWindowMode(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 116: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.moveTopActivityToPinnedStack(n, (Rect)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 115: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl23 = bl9;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.moveTasksToFullscreenStack(n, bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 114: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl23 = bl10;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.suppressResizeConfigChanges(bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 113: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl23 = bl11;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.dismissPip(bl23, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 112: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl23 = bl12;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.dismissSplitScreenMode(bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 111: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportSizeConfigurations(((Parcel)object).readStrongBinder(), ((Parcel)object).createIntArray(), ((Parcel)object).createIntArray(), ((Parcel)object).createIntArray());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 110: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.positionTaskInStack(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 109: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPackageForToken(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 108: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActivityClassForToken(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ComponentName)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 107: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.keyguardGoingAway(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 106: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.showLockTaskEscapeMessage(((Parcel)object).readStrongBinder());
                        return true;
                    }
                    case 105: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isRootVoiceInteraction(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 104: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.showAssistFromActivity(iBinder, (Bundle)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 103: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isAssistDataAllowedOnCurrentActivity() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 102: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAssistDataReceiver iAssistDataReceiver = IAssistDataReceiver.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.requestAutofillData(iAssistDataReceiver, bundle, ((Parcel)object).readStrongBinder(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 101: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        IAssistDataReceiver iAssistDataReceiver = IAssistDataReceiver.Stub.asInterface(((Parcel)object).readStrongBinder());
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl23 = ((Parcel)object).readInt() != 0;
                        bl13 = ((Parcel)object).readInt() != 0;
                        n = this.requestAssistContextExtras(n, iAssistDataReceiver, bundle, iBinder, bl23, bl13) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 100: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        String string4 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.launchAssistIntent(intent, n, string4, n2, (Bundle)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 99: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAssistContextExtras(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((Bundle)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 98: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl23 = ((Parcel)object).readInt() != 0;
                        if (((Parcel)object).readInt() != 0) {
                            bl13 = true;
                        }
                        this.setLockScreenShown(bl23, bl13);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 97: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getStackInfo(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ActivityManager.StackInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 96: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllStackInfos();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 95: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeStacksWithActivityTypes(((Parcel)object).createIntArray());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 94: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeStacksInWindowingModes(((Parcel)object).createIntArray());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 93: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.offsetPinnedStackBounds(n, rect, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 92: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl23 = ((Parcel)object).readInt() != 0;
                        bl13 = ((Parcel)object).readInt() != 0;
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        bl = ((Parcel)object).readInt() != 0;
                        n = this.setTaskWindowingModeSplitScreenPrimary(n, n2, bl23, bl13, rect, bl) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 91: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        bl23 = ((Parcel)object).readInt() != 0;
                        bl13 = ((Parcel)object).readInt() != 0;
                        bl = ((Parcel)object).readInt() != 0;
                        this.resizeStack(n, rect, bl23, bl13, bl, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 90: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl23 = bl14;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.moveTaskToStack(n, n2, bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 89: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl23 = bl15;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.setTaskWindowingMode(n, n2, bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 88: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeStack(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 87: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.moveStackToDisplay(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 86: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.resizeTask(n, rect, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 85: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.toggleFreeformWindowingMode(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 84: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setTaskResizeable(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 83: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterTaskStackListener(ITaskStackListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 82: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerTaskStackListener(ITaskStackListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 81: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startInPlaceAnimationOnFrontMostApplication((Bundle)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 80: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTaskDescriptionIcon(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((Bitmap)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 79: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.releaseSomeActivities(IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 78: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.requestStartActivityPermissionToken(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 77: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.releaseActivityInstance(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 76: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAppTaskThumbnailSize();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((Point)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 75: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        ActivityManager.TaskDescription taskDescription = ((Parcel)object).readInt() != 0 ? ActivityManager.TaskDescription.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bitmap.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addAppTask(iBinder, intent, taskDescription, (Bitmap)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyEnterAnimationComplete(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyLaunchTaskBehindComplete(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isTopOfTask(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishVoiceTask(IVoiceInteractionSession.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopSystemLockTaskMode();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startSystemLockTaskMode(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAppTasks(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeBinderList((List<IBinder>)object);
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActivityOptions(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((Bundle)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object = ((Parcel)object).readInt() != 0 ? ActivityManager.TaskDescription.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setTaskDescription(iBinder, (ActivityManager.TaskDescription)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getLockTaskModeState();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInLockTaskMode() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateLockTaskPackages(((Parcel)object).readInt(), ((Parcel)object).createStringArray());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopLockTaskModeByToken(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startLockTaskModeByToken(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl23 = bl16;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.cancelRecentsAnimation(bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 59: {
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
                    case 58: {
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
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFocusedStack(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        AssistStructure assistStructure = ((Parcel)object).readInt() != 0 ? AssistStructure.CREATOR.createFromParcel((Parcel)object) : null;
                        AssistContent assistContent = ((Parcel)object).readInt() != 0 ? AssistContent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.reportAssistContextExtras(iBinder, bundle, assistStructure, assistContent, (Uri)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLaunchedFromPackage(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getLaunchedFromUid(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.overridePendingTransition(((Parcel)object).readStrongBinder(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTaskDescription(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ActivityManager.TaskDescription)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl23 = bl17;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        n = this.moveActivityTaskToBack(iBinder, bl23) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isTopActivityImmersive() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl23 = bl18;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.setImmersive(iBinder, bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isImmersive(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getActivityDisplayId(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl23 = bl19;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.reportActivityFullyDrawn(iBinder, bl23);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyActivityDrawn(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.convertToTranslucent(iBinder, (Bundle)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.convertFromTranslucent(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRequestedOrientation(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRequestedOrientation(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.willActivityBeVisible(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 39: {
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
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishSubActivity(((Parcel)object).readStrongBinder(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        bl23 = bl20;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        n = this.getTaskForActivity(iBinder, bl23);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string5 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.moveTaskToFront(iApplicationThread, string5, n2, n, (Bundle)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.navigateUpTo(iBinder, intent, n, (Intent)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.shouldUpRecreateTask(((Parcel)object).readStrongBinder(), ((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getFilteredTasks(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTasks(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeAllVisibleRecentTasks();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeTask(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFocusedTask(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCallingActivity(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ComponentName)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCallingPackage(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFrontActivityScreenCompatMode(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getFrontActivityScreenCompatMode();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.activitySlept(((Parcel)object).readStrongBinder());
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.activityRelaunched(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.activityDestroyed(((Parcel)object).readStrongBinder());
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        PersistableBundle persistableBundle = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.activityStopped(iBinder, bundle, persistableBundle, (CharSequence)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.activityPaused(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.activityTopResumedStateLost();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.activityResumed(((Parcel)object).readStrongBinder());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object2 = ((Parcel)object).readInt() != 0 ? Configuration.CREATOR.createFromParcel((Parcel)object) : null;
                        bl23 = bl21;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        this.activityIdle(iBinder, (Configuration)object2, bl23);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.finishActivityAffinity(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        n = ((Parcel)object).readInt();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.finishActivity(iBinder, n, intent, ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unhandledBack();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isActivityStartAllowedOnDisplay(n, intent, ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string6 = ((Parcel)object).readString();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string7 = ((Parcel)object).readString();
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string8 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        ProfilerInfo profilerInfo = ((Parcel)object).readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        IBinder iBinder2 = ((Parcel)object).readStrongBinder();
                        bl23 = bl22;
                        if (((Parcel)object).readInt() != 0) {
                            bl23 = true;
                        }
                        n = this.startActivityAsCaller(iApplicationThread, string6, intent, string7, iBinder, string8, n, n2, profilerInfo, bundle, iBinder2, bl23, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startActivityFromRecents(n, (Bundle)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startRecentsActivity(intent, IAssistDataReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()), IRecentsAnimationRunner.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string9 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string10 = ((Parcel)object).readString();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startAssistantActivity(string9, n2, n, intent, string10, bundle, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string11 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        int n3 = ((Parcel)object).readInt();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string12 = ((Parcel)object).readString();
                        IVoiceInteractionSession iVoiceInteractionSession = IVoiceInteractionSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IVoiceInteractor iVoiceInteractor = IVoiceInteractor.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n2 = ((Parcel)object).readInt();
                        ProfilerInfo profilerInfo = ((Parcel)object).readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startVoiceActivity(string11, n, n3, intent, string12, iVoiceInteractionSession, iVoiceInteractor, n2, profilerInfo, bundle, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string13 = ((Parcel)object).readString();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string14 = ((Parcel)object).readString();
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string15 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        Configuration configuration = ((Parcel)object).readInt() != 0 ? Configuration.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startActivityWithConfig(iApplicationThread, string13, intent, string14, iBinder, string15, n, n2, configuration, bundle, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string16 = ((Parcel)object).readString();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string17 = ((Parcel)object).readString();
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string18 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        ProfilerInfo profilerInfo = ((Parcel)object).readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.startActivityAndWait(iApplicationThread, string16, intent, string17, iBinder, string18, n, n2, profilerInfo, bundle, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((WaitResult)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IIntentSender iIntentSender = IIntentSender.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string19 = ((Parcel)object).readString();
                        IBinder iBinder3 = ((Parcel)object).readStrongBinder();
                        String string20 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        int n4 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startActivityIntentSender(iApplicationThread, iIntentSender, iBinder, intent, string19, iBinder3, string20, n2, n4, n, (Bundle)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startNextMatchingActivity(iBinder, intent, (Bundle)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string21 = ((Parcel)object).readString();
                        Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string22 = ((Parcel)object).readString();
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string23 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        ProfilerInfo profilerInfo = ((Parcel)object).readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startActivityAsUser(iApplicationThread, string21, intent, string22, iBinder, string23, n, n2, profilerInfo, bundle, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string24 = ((Parcel)object).readString();
                        Intent[] arrintent = ((Parcel)object).createTypedArray(Intent.CREATOR);
                        String[] arrstring = ((Parcel)object).createStringArray();
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startActivities(iApplicationThread, string24, arrintent, arrstring, iBinder, bundle, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                String string25 = ((Parcel)object).readString();
                Intent intent = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                String string26 = ((Parcel)object).readString();
                IBinder iBinder = ((Parcel)object).readStrongBinder();
                String string27 = ((Parcel)object).readString();
                n = ((Parcel)object).readInt();
                n2 = ((Parcel)object).readInt();
                ProfilerInfo profilerInfo = ((Parcel)object).readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                n = this.startActivity(iApplicationThread, string25, intent, string26, iBinder, string27, n, n2, profilerInfo, (Bundle)object);
                ((Parcel)object2).writeNoException();
                ((Parcel)object2).writeInt(n);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IActivityTaskManager {
            public static IActivityTaskManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void activityDestroyed(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(22, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().activityDestroyed(iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void activityIdle(IBinder iBinder, Configuration configuration, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    int n = 0;
                    if (configuration != null) {
                        parcel.writeInt(1);
                        configuration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(17, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().activityIdle(iBinder, configuration, bl);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void activityPaused(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().activityPaused(iBinder);
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
            public void activityRelaunched(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().activityRelaunched(iBinder);
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
            public void activityResumed(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().activityResumed(iBinder);
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
            public void activitySlept(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(24, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().activitySlept(iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void activityStopped(IBinder iBinder, Bundle bundle, PersistableBundle persistableBundle, CharSequence charSequence) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (persistableBundle != null) {
                        parcel.writeInt(1);
                        persistableBundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().activityStopped(iBinder, bundle, persistableBundle, charSequence);
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
            public void activityTopResumedStateLost() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().activityTopResumedStateLost();
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
            public int addAppTask(IBinder iBinder, Intent intent, ActivityManager.TaskDescription taskDescription, Bitmap bitmap) throws RemoteException {
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
                    if (taskDescription != null) {
                        parcel.writeInt(1);
                        taskDescription.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bitmap != null) {
                        parcel.writeInt(1);
                        bitmap.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(75, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().addAppTask(iBinder, intent, taskDescription, bitmap);
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
            public void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(147, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().alwaysShowUnsupportedCompileSdkWarning(componentName);
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

            @Override
            public void cancelRecentsAnimation(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(135, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public void clearLaunchParamsForPackages(List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(158, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearLaunchParamsForPackages(list);
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
            public boolean convertFromTranslucent(IBinder iBinder) throws RemoteException {
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
                    if (iBinder2.transact(43, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().convertFromTranslucent(iBinder);
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
            public boolean convertToTranslucent(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    boolean bl = true;
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().convertToTranslucent(iBinder, bundle);
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
            public void dismissKeyguard(IBinder iBinder, IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iKeyguardDismissCallback != null ? iKeyguardDismissCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(134, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dismissKeyguard(iBinder, iKeyguardDismissCallback, charSequence);
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
            public void dismissPip(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(113, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dismissPip(bl, n);
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
            public void dismissSplitScreenMode(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(112, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dismissSplitScreenMode(bl);
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
            public boolean enterPictureInPictureMode(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    boolean bl = true;
                    if (pictureInPictureParams != null) {
                        parcel.writeInt(1);
                        pictureInPictureParams.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(119, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().enterPictureInPictureMode(iBinder, pictureInPictureParams);
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
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public boolean finishActivityAffinity(IBinder iBinder) throws RemoteException {
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
                    if (iBinder2.transact(16, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().finishActivityAffinity(iBinder);
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
            public void finishSubActivity(IBinder iBinder, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishSubActivity(iBinder, string2, n);
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
            public void finishVoiceTask(IVoiceInteractionSession iVoiceInteractionSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iVoiceInteractionSession != null ? iVoiceInteractionSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(71, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishVoiceTask(iVoiceInteractionSession);
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
            public ComponentName getActivityClassForToken(IBinder object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeStrongBinder((IBinder)object);
                        if (this.mRemote.transact(108, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getActivityClassForToken((IBinder)object);
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
                object = parcel2.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public int getActivityDisplayId(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getActivityDisplayId(iBinder);
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
            public Bundle getActivityOptions(IBinder object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeStrongBinder((IBinder)object);
                        if (this.mRemote.transact(67, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getActivityOptions((IBinder)object);
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
                object = parcel2.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public List<ActivityManager.StackInfo> getAllStackInfos() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(96, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public Point getAppTaskThumbnailSize() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(76, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Point point = Stub.getDefaultImpl().getAppTaskThumbnailSize();
                        parcel.recycle();
                        parcel2.recycle();
                        return point;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Point point = parcel.readInt() != 0 ? Point.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return point;
            }

            @Override
            public List<IBinder> getAppTasks(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(68, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getAppTasks((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createBinderArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Bundle getAssistContextExtras(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(99, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        Bundle bundle = Stub.getDefaultImpl().getAssistContextExtras(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return bundle;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                Bundle bundle = parcel2.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return bundle;
            }

            @Override
            public ComponentName getCallingActivity(IBinder object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeStrongBinder((IBinder)object);
                        if (this.mRemote.transact(28, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getCallingActivity((IBinder)object);
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
                object = parcel2.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public String getCallingPackage(IBinder object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder((IBinder)object);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getCallingPackage((IBinder)object);
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
            public ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(131, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ConfigurationInfo configurationInfo = Stub.getDefaultImpl().getDeviceConfigurationInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return configurationInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ConfigurationInfo configurationInfo = parcel.readInt() != 0 ? ConfigurationInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return configurationInfo;
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
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
                        if (this.mRemote.transact(58, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
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
            public int getFrontActivityScreenCompatMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getFrontActivityScreenCompatMode();
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int getLastResumedActivityUserId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(138, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getLastResumedActivityUserId();
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
            public String getLaunchedFromPackage(IBinder object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder((IBinder)object);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public int getLockTaskModeState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(65, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public int getMaxNumPictureInPictureActions(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(121, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getMaxNumPictureInPictureActions(iBinder);
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
            public boolean getPackageAskScreenCompat(String string2) throws RemoteException {
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
                    if (iBinder.transact(156, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getPackageAskScreenCompat(string2);
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
            public String getPackageForToken(IBinder object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder((IBinder)object);
                    if (!this.mRemote.transact(109, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getPackageForToken((IBinder)object);
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
            public int getPackageScreenCompatMode(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(154, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPackageScreenCompatMode(string2);
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
                        if (this.mRemote.transact(39, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
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
            public int getRequestedOrientation(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getRequestedOrientation(iBinder);
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
            public ActivityManager.StackInfo getStackInfo(int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        if (this.mRemote.transact(97, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ActivityManager.StackInfo stackInfo = Stub.getDefaultImpl().getStackInfo(n, n2);
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
            public Rect getTaskBounds(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(59, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
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
            public ActivityManager.TaskDescription getTaskDescription(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(52, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ActivityManager.TaskDescription taskDescription = Stub.getDefaultImpl().getTaskDescription(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return taskDescription;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ActivityManager.TaskDescription taskDescription = parcel2.readInt() != 0 ? ActivityManager.TaskDescription.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return taskDescription;
            }

            @Override
            public Bitmap getTaskDescriptionIcon(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(80, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getTaskDescriptionIcon((String)object, n);
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
                object = parcel.readInt() != 0 ? Bitmap.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
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
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
                    if (this.mRemote.transact(136, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
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
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            @Override
            public IBinder getUriPermissionOwnerForActivity(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(122, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        iBinder = Stub.getDefaultImpl().getUriPermissionOwnerForActivity(iBinder);
                        return iBinder;
                    }
                    parcel2.readException();
                    iBinder = parcel2.readStrongBinder();
                    return iBinder;
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
            public boolean isActivityStartAllowedOnDisplay(int n, Intent intent, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    boolean bl = true;
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isActivityStartAllowedOnDisplay(n, intent, string2, n2);
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
            public boolean isAssistDataAllowedOnCurrentActivity() throws RemoteException {
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
                    if (iBinder.transact(103, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isAssistDataAllowedOnCurrentActivity();
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
            public boolean isImmersive(IBinder iBinder) throws RemoteException {
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
                    if (iBinder2.transact(48, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isImmersive(iBinder);
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
                    if (iBinder.transact(64, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
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
            public boolean isInMultiWindowMode(IBinder iBinder) throws RemoteException {
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
                    if (iBinder2.transact(117, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isInMultiWindowMode(iBinder);
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
            public boolean isInPictureInPictureMode(IBinder iBinder) throws RemoteException {
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
                    if (iBinder2.transact(118, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isInPictureInPictureMode(iBinder);
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
            public boolean isRootVoiceInteraction(IBinder iBinder) throws RemoteException {
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
                    if (iBinder2.transact(105, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRootVoiceInteraction(iBinder);
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
                    if (iBinder.transact(50, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
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
                    if (iBinder2.transact(72, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
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
            public void keyguardGoingAway(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(107, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().keyguardGoingAway(n);
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
            public boolean launchAssistIntent(Intent intent, int n, String string2, int n2, Bundle bundle) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block17 : {
                    boolean bl;
                    block16 : {
                        block15 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                            bl = true;
                            if (intent != null) {
                                parcel.writeInt(1);
                                intent.writeToParcel(parcel, 0);
                                break block15;
                            }
                            parcel.writeInt(0);
                        }
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block17;
                        }
                        try {
                            parcel.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block17;
                        }
                        try {
                            parcel.writeInt(n2);
                            if (bundle != null) {
                                parcel.writeInt(1);
                                bundle.writeToParcel(parcel, 0);
                                break block16;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(100, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().launchAssistIntent(intent, n, string2, n2, bundle);
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
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_7;
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
                    if (this.mRemote.transact(51, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
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

            @Override
            public void moveStackToDisplay(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(87, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().moveStackToDisplay(n, n2);
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
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(90, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            @Override
            public void moveTasksToFullscreenStack(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(115, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().moveTasksToFullscreenStack(n, bl);
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
                    if (!this.mRemote.transact(116, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public boolean navigateUpTo(IBinder iBinder, Intent intent, int n, Intent intent2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    boolean bl = true;
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (intent2 != null) {
                        parcel.writeInt(1);
                        intent2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().navigateUpTo(iBinder, intent, n, intent2);
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
            public void notifyActivityDrawn(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyActivityDrawn(iBinder);
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
            public void notifyEnterAnimationComplete(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(74, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyEnterAnimationComplete(iBinder);
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
            public void notifyLaunchTaskBehindComplete(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(73, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyLaunchTaskBehindComplete(iBinder);
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
            public void notifyPinnedStackAnimationEnded() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(130, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyPinnedStackAnimationEnded();
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
            public void notifyPinnedStackAnimationStarted() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(129, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyPinnedStackAnimationStarted();
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
            public void offsetPinnedStackBounds(int n, Rect rect, int n2, int n3, int n4) throws RemoteException {
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
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(93, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().offsetPinnedStackBounds(n, rect, n2, n3, n4);
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
            public void onBackPressedOnTaskRoot(IBinder iBinder, IRequestFinishCallback iRequestFinishCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iRequestFinishCallback != null ? iRequestFinishCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (!this.mRemote.transact(161, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBackPressedOnTaskRoot(iBinder, iRequestFinishCallback);
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
            public void overridePendingTransition(IBinder iBinder, String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().overridePendingTransition(iBinder, string2, n, n2);
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
                    if (!this.mRemote.transact(110, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            @Override
            public void registerRemoteAnimationForNextActivityStart(String string2, RemoteAnimationAdapter remoteAnimationAdapter) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (remoteAnimationAdapter != null) {
                        parcel.writeInt(1);
                        remoteAnimationAdapter.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(145, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerRemoteAnimationForNextActivityStart(string2, remoteAnimationAdapter);
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
            public void registerRemoteAnimations(IBinder iBinder, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (remoteAnimationDefinition != null) {
                        parcel.writeInt(1);
                        remoteAnimationDefinition.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(144, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerRemoteAnimations(iBinder, remoteAnimationDefinition);
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
            public void registerRemoteAnimationsForDisplay(int n, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (remoteAnimationDefinition != null) {
                        parcel.writeInt(1);
                        remoteAnimationDefinition.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(146, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerRemoteAnimationsForDisplay(n, remoteAnimationDefinition);
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
            public void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTaskStackListener != null ? iTaskStackListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(82, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            @Override
            public boolean releaseActivityInstance(IBinder iBinder) throws RemoteException {
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
                    if (iBinder2.transact(77, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().releaseActivityInstance(iBinder);
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
            public void releaseSomeActivities(IApplicationThread iApplicationThread) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(79, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseSomeActivities(iApplicationThread);
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
            public void removeAllVisibleRecentTasks() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeAllVisibleRecentTasks();
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
                    if (!this.mRemote.transact(88, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public void removeStacksInWindowingModes(int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(94, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeStacksInWindowingModes(arrn);
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
            public void removeStacksWithActivityTypes(int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(95, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeStacksWithActivityTypes(arrn);
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
                    if (iBinder.transact(30, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
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
            public void reportActivityFullyDrawn(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportActivityFullyDrawn(iBinder, bl);
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
            public void reportAssistContextExtras(IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (assistStructure != null) {
                        parcel.writeInt(1);
                        assistStructure.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (assistContent != null) {
                        parcel.writeInt(1);
                        assistContent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportAssistContextExtras(iBinder, bundle, assistStructure, assistContent, uri);
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
            public void reportSizeConfigurations(IBinder iBinder, int[] arrn, int[] arrn2, int[] arrn3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeIntArray(arrn);
                    parcel.writeIntArray(arrn2);
                    parcel.writeIntArray(arrn3);
                    if (!this.mRemote.transact(111, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportSizeConfigurations(iBinder, arrn, arrn2, arrn3);
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
            public boolean requestAssistContextExtras(int n, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel;
                void var2_7;
                Parcel parcel2;
                block12 : {
                    boolean bl3;
                    block11 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeInt(n);
                            IBinder iBinder2 = iAssistDataReceiver != null ? iAssistDataReceiver.asBinder() : null;
                            parcel2.writeStrongBinder(iBinder2);
                            bl3 = true;
                            if (bundle != null) {
                                parcel2.writeInt(1);
                                bundle.writeToParcel(parcel2, 0);
                                break block11;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeStrongBinder(iBinder);
                        int n2 = bl ? 1 : 0;
                        parcel2.writeInt(n2);
                        n2 = bl2 ? 1 : 0;
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(101, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().requestAssistContextExtras(n, iAssistDataReceiver, bundle, iBinder, bl, bl2);
                            parcel.recycle();
                            parcel2.recycle();
                            return bl;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        bl = n != 0 ? bl3 : false;
                        parcel.recycle();
                        parcel2.recycle();
                        return bl;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean requestAutofillData(IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder2 = iAssistDataReceiver != null ? iAssistDataReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    boolean bl = true;
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(102, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().requestAutofillData(iAssistDataReceiver, bundle, iBinder, n);
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
            public IBinder requestStartActivityPermissionToken(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(78, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        iBinder = Stub.getDefaultImpl().requestStartActivityPermissionToken(iBinder);
                        return iBinder;
                    }
                    parcel2.readException();
                    iBinder = parcel2.readStrongBinder();
                    return iBinder;
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
                    if (!this.mRemote.transact(123, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            @Override
            public void resizePinnedStack(Rect rect, Rect rect2) throws RemoteException {
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
                    if (!this.mRemote.transact(132, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resizePinnedStack(rect, rect2);
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
                        if (!this.mRemote.transact(91, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(86, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public void restartActivityProcessIfVisible(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(160, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restartActivityProcessIfVisible(iBinder);
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
            public void resumeAppSwitches() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(151, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(152, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
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
            public void setDisablePreviewScreenshots(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(137, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDisablePreviewScreenshots(iBinder, bl);
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
            public void setDisplayToSingleTaskInstance(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(159, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDisplayToSingleTaskInstance(n);
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
                    if (!this.mRemote.transact(57, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public void setFocusedTask(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFocusedTask(n);
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
            public void setFrontActivityScreenCompatMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFrontActivityScreenCompatMode(n);
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
            public void setImmersive(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(49, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setImmersive(iBinder, bl);
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
            public void setInheritShowWhenLocked(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(142, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInheritShowWhenLocked(iBinder, bl);
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
            public void setLockScreenShown(boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = 1;
                int n2 = bl ? 1 : 0;
                parcel.writeInt(n2);
                n2 = bl2 ? n : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(98, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setLockScreenShown(bl, bl2);
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
            public void setPackageAskScreenCompat(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(157, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPackageAskScreenCompat(string2, bl);
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
                    if (!this.mRemote.transact(155, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(149, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public void setPictureInPictureParams(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (pictureInPictureParams != null) {
                        parcel.writeInt(1);
                        pictureInPictureParams.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(120, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPictureInPictureParams(iBinder, pictureInPictureParams);
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
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            @Override
            public void setShowWhenLocked(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(141, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setShowWhenLocked(iBinder, bl);
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
            public void setSplitScreenResizing(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(124, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSplitScreenResizing(bl);
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
            public void setTaskDescription(IBinder iBinder, ActivityManager.TaskDescription taskDescription) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (taskDescription != null) {
                        parcel.writeInt(1);
                        taskDescription.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(66, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTaskDescription(iBinder, taskDescription);
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
            public void setTaskResizeable(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(84, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public void setTaskWindowingMode(int n, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(89, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTaskWindowingMode(n, n2, bl);
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
            public boolean setTaskWindowingModeSplitScreenPrimary(int n, int n2, boolean bl, boolean bl2, Rect rect, boolean bl3) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var5_10;
                block12 : {
                    boolean bl4;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n2);
                        bl4 = true;
                        int n3 = bl ? 1 : 0;
                        parcel2.writeInt(n3);
                        n3 = bl2 ? 1 : 0;
                        parcel2.writeInt(n3);
                        if (rect != null) {
                            parcel2.writeInt(1);
                            rect.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        n3 = bl3 ? 1 : 0;
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(92, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().setTaskWindowingModeSplitScreenPrimary(n, n2, bl, bl2, rect, bl3);
                            parcel.recycle();
                            parcel2.recycle();
                            return bl;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        bl = n != 0 ? bl4 : false;
                        parcel.recycle();
                        parcel2.recycle();
                        return bl;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var5_10;
            }

            @Override
            public void setTurnScreenOn(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(143, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTurnScreenOn(iBinder, bl);
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
            public void setVoiceKeepAwake(IVoiceInteractionSession iVoiceInteractionSession, boolean bl) throws RemoteException {
                Parcel parcel;
                IBinder iBinder;
                Parcel parcel2;
                block8 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iVoiceInteractionSession == null) break block8;
                    iBinder = iVoiceInteractionSession.asBinder();
                }
                iBinder = null;
                parcel2.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel2.writeInt(n);
                    if (!this.mRemote.transact(153, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVoiceKeepAwake(iVoiceInteractionSession, bl);
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
            public int setVrMode(IBinder iBinder, boolean bl, ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(125, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setVrMode(iBinder, bl, componentName);
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
            public void setVrThread(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(148, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVrThread(n);
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
            public boolean shouldUpRecreateTask(IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeStrongBinder(iBinder);
                        parcel2.writeString(string2);
                        iBinder2 = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder2.transact(34, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().shouldUpRecreateTask(iBinder, string2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean showAssistFromActivity(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    boolean bl = true;
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(104, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().showAssistFromActivity(iBinder, bundle);
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
            public void showLockTaskEscapeMessage(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(106, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showLockTaskEscapeMessage(iBinder);
                        return;
                    }
                    return;
                }
                finally {
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
            public int startActivities(IApplicationThread iApplicationThread, String string2, Intent[] arrintent, String[] arrstring, IBinder iBinder, Bundle bundle, int n) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder2 = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeTypedArray((Parcelable[])arrintent, 0);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeStringArray(arrstring);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeStrongBinder(iBinder);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeInt(n);
                        if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().startActivities(iApplicationThread, string2, arrintent, arrstring, iBinder, bundle, n);
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
                throw var1_7;
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
                                                var11_15.writeInterfaceToken("android.app.IActivityTaskManager");
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
                        if (this.mRemote.transact(1, var11_15, var12_16, 0) || Stub.getDefaultImpl() == null) break block20;
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
            public WaitResult startActivityAndWait(IApplicationThread var1_1, String var2_9, Intent var3_10, String var4_11, IBinder var5_12, String var6_13, int var7_14, int var8_15, ProfilerInfo var9_16, Bundle var10_17, int var11_18) throws RemoteException {
                block24 : {
                    block27 : {
                        block26 : {
                            block25 : {
                                block28 : {
                                    block23 : {
                                        block22 : {
                                            block21 : {
                                                block20 : {
                                                    block19 : {
                                                        var12_19 = Parcel.obtain();
                                                        var13_20 = Parcel.obtain();
                                                        var12_19.writeInterfaceToken("android.app.IActivityTaskManager");
                                                        if (var1_1 == null) break block19;
                                                        try {
                                                            var14_21 = var1_1.asBinder();
                                                            break block20;
                                                        }
                                                        catch (Throwable var1_2) {
                                                            break block24;
                                                        }
                                                    }
                                                    var14_21 = null;
                                                }
                                                var12_19.writeStrongBinder((IBinder)var14_21);
                                                var12_19.writeString(var2_9);
                                                if (var3_10 == null) break block21;
                                                var12_19.writeInt(1);
                                                var3_10.writeToParcel(var12_19, 0);
                                                ** GOTO lbl25
                                            }
                                            var12_19.writeInt(0);
lbl25: // 2 sources:
                                            var12_19.writeString(var4_11);
                                            var12_19.writeStrongBinder(var5_12);
                                            var12_19.writeString(var6_13);
                                            var12_19.writeInt(var7_14);
                                            var12_19.writeInt(var8_15);
                                            if (var9_16 == null) break block22;
                                            var12_19.writeInt(1);
                                            var9_16.writeToParcel(var12_19, 0);
                                            break block23;
                                        }
                                        var12_19.writeInt(0);
                                    }
                                    if (var10_17 == null) break block28;
                                    var12_19.writeInt(1);
                                    var10_17.writeToParcel(var12_19, 0);
                                    ** GOTO lbl47
                                }
                                var12_19.writeInt(0);
lbl47: // 2 sources:
                                var12_19.writeInt(var11_18);
                                var15_22 = this.mRemote.transact(6, var12_19, var13_20, 0);
                                if (var15_22) break block25;
                                try {
                                    if (Stub.getDefaultImpl() == null) break block25;
                                    var14_21 = Stub.getDefaultImpl();
                                }
                                catch (Throwable var1_4) {
                                    break block24;
                                }
                                try {
                                    var1_1 = var14_21.startActivityAndWait((IApplicationThread)var1_1, var2_9, var3_10, var4_11, var5_12, var6_13, var7_14, var8_15, var9_16, var10_17, var11_18);
                                    var13_20.recycle();
                                    var12_19.recycle();
                                    return var1_1;
                                }
                                catch (Throwable var1_3) {}
                                break block24;
                            }
                            try {
                                var13_20.readException();
                                if (var13_20.readInt() == 0) break block26;
                                var1_1 = WaitResult.CREATOR;
                            }
                            catch (Throwable var1_6) {}
                            try {
                                var1_1 = var1_1.createFromParcel(var13_20);
                                break block27;
                            }
                            catch (Throwable var1_5) {
                                break block24;
                            }
                        }
                        var1_1 = null;
                    }
                    var13_20.recycle();
                    var12_19.recycle();
                    return var1_1;
                    break block24;
                    catch (Throwable var1_7) {
                        // empty catch block
                    }
                }
                var13_20.recycle();
                var12_19.recycle();
                throw var1_8;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int startActivityAsCaller(IApplicationThread var1_1, String var2_6, Intent var3_7, String var4_8, IBinder var5_9, String var6_10, int var7_11, int var8_12, ProfilerInfo var9_13, Bundle var10_14, IBinder var11_15, boolean var12_16, int var13_17) throws RemoteException {
                block20 : {
                    block21 : {
                        block22 : {
                            block19 : {
                                block18 : {
                                    block17 : {
                                        block16 : {
                                            block15 : {
                                                var14_18 = Parcel.obtain();
                                                var15_19 = Parcel.obtain();
                                                var14_18.writeInterfaceToken("android.app.IActivityTaskManager");
                                                if (var1_1 == null) break block15;
                                                try {
                                                    var16_20 = var1_1.asBinder();
                                                    break block16;
                                                }
                                                catch (Throwable var1_2) {
                                                    break block20;
                                                }
                                            }
                                            var16_20 = null;
                                        }
                                        var14_18.writeStrongBinder((IBinder)var16_20);
                                        var14_18.writeString(var2_6);
                                        var17_21 = 1;
                                        if (var3_7 == null) break block17;
                                        var14_18.writeInt(1);
                                        var3_7.writeToParcel(var14_18, 0);
                                        ** GOTO lbl26
                                    }
                                    var14_18.writeInt(0);
lbl26: // 2 sources:
                                    var14_18.writeString(var4_8);
                                    var14_18.writeStrongBinder(var5_9);
                                    var14_18.writeString(var6_10);
                                    var14_18.writeInt(var7_11);
                                    var14_18.writeInt(var8_12);
                                    if (var9_13 == null) break block18;
                                    var14_18.writeInt(1);
                                    var9_13.writeToParcel(var14_18, 0);
                                    break block19;
                                }
                                var14_18.writeInt(0);
                            }
                            if (var10_14 == null) break block22;
                            var14_18.writeInt(1);
                            var10_14.writeToParcel(var14_18, 0);
                            ** GOTO lbl48
                        }
                        var14_18.writeInt(0);
lbl48: // 2 sources:
                        var14_18.writeStrongBinder(var11_15);
                        if (!var12_16) {
                            var17_21 = 0;
                        }
                        var14_18.writeInt(var17_21);
                        var14_18.writeInt(var13_17);
                        if (this.mRemote.transact(12, var14_18, var15_19, 0) || Stub.getDefaultImpl() == null) break block21;
                        var16_20 = Stub.getDefaultImpl();
                        try {
                            var7_11 = var16_20.startActivityAsCaller((IApplicationThread)var1_1, var2_6, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12, var9_13, var10_14, var11_15, var12_16, var13_17);
                            var15_19.recycle();
                            var14_18.recycle();
                            return var7_11;
                        }
                        catch (Throwable var1_3) {}
                    }
                    var1_1 = var15_19;
                    var1_1.readException();
                    var7_11 = var1_1.readInt();
                    var1_1.recycle();
                    var14_18.recycle();
                    return var7_11;
                    break block20;
                    catch (Throwable var1_4) {
                        // empty catch block
                    }
                }
                var15_19.recycle();
                var14_18.recycle();
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
                                                var12_16.writeInterfaceToken("android.app.IActivityTaskManager");
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
                        if (this.mRemote.transact(3, var12_16, var13_17, 0) || Stub.getDefaultImpl() == null) break block20;
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
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int startActivityIntentSender(IApplicationThread var1_1, IIntentSender var2_6, IBinder var3_7, Intent var4_8, String var5_9, IBinder var6_10, String var7_11, int var8_12, int var9_13, int var10_14, Bundle var11_15) throws RemoteException {
                block19 : {
                    block20 : {
                        block18 : {
                            block17 : {
                                block16 : {
                                    block15 : {
                                        block14 : {
                                            var12_16 = Parcel.obtain();
                                            var13_17 = Parcel.obtain();
                                            var12_16.writeInterfaceToken("android.app.IActivityTaskManager");
                                            var14_18 = null;
                                            if (var1_1 == null) break block14;
                                            try {
                                                var15_19 = var1_1.asBinder();
                                                break block15;
                                            }
                                            catch (Throwable var1_2) {
                                                break block19;
                                            }
                                        }
                                        var15_19 = null;
                                    }
                                    var12_16.writeStrongBinder((IBinder)var15_19);
                                    var15_19 = var14_18;
                                    if (var2_6 == null) break block16;
                                    var15_19 = var2_6.asBinder();
                                }
                                var12_16.writeStrongBinder((IBinder)var15_19);
                                var12_16.writeStrongBinder(var3_7);
                                if (var4_8 == null) break block17;
                                var12_16.writeInt(1);
                                var4_8.writeToParcel(var12_16, 0);
                                ** GOTO lbl33
                            }
                            var12_16.writeInt(0);
lbl33: // 2 sources:
                            var12_16.writeString(var5_9);
                            var12_16.writeStrongBinder(var6_10);
                            var12_16.writeString(var7_11);
                            var12_16.writeInt(var8_12);
                            var12_16.writeInt(var9_13);
                            var12_16.writeInt(var10_14);
                            if (var11_15 == null) break block18;
                            var12_16.writeInt(1);
                            var11_15.writeToParcel(var12_16, 0);
                            ** GOTO lbl47
                        }
                        var12_16.writeInt(0);
lbl47: // 2 sources:
                        if (this.mRemote.transact(5, var12_16, var13_17, 0) || Stub.getDefaultImpl() == null) break block20;
                        var15_19 = Stub.getDefaultImpl();
                        try {
                            var8_12 = var15_19.startActivityIntentSender((IApplicationThread)var1_1, var2_6, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12, var9_13, var10_14, var11_15);
                            var13_17.recycle();
                            var12_16.recycle();
                            return var8_12;
                        }
                        catch (Throwable var1_3) {}
                    }
                    var1_1 = var13_17;
                    var1_1.readException();
                    var8_12 = var1_1.readInt();
                    var1_1.recycle();
                    var12_16.recycle();
                    return var8_12;
                    break block19;
                    catch (Throwable var1_4) {
                        // empty catch block
                    }
                }
                var13_17.recycle();
                var12_16.recycle();
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
            public int startActivityWithConfig(IApplicationThread var1_1, String var2_6, Intent var3_7, String var4_8, IBinder var5_9, String var6_10, int var7_11, int var8_12, Configuration var9_13, Bundle var10_14, int var11_15) throws RemoteException {
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
                                                var12_16.writeInterfaceToken("android.app.IActivityTaskManager");
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
                        if (this.mRemote.transact(7, var12_16, var13_17, 0) || Stub.getDefaultImpl() == null) break block20;
                        var14_18 = Stub.getDefaultImpl();
                        try {
                            var7_11 = var14_18.startActivityWithConfig((IApplicationThread)var1_1, var2_6, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12, var9_13, var10_14, var11_15);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int startAssistantActivity(String string2, int n, int n2, Intent intent, String string3, Bundle bundle, int n3) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_6;
                block14 : {
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
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n2);
                        if (intent != null) {
                            parcel.writeInt(1);
                            intent.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeString(string3);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeInt(n3);
                        if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().startAssistantActivity(string2, n, n2, intent, string3, bundle, n3);
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
                throw var1_6;
            }

            @Override
            public void startInPlaceAnimationOnFrontMostApplication(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(81, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startInPlaceAnimationOnFrontMostApplication(bundle);
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
            public void startLocalVoiceInteraction(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(126, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startLocalVoiceInteraction(iBinder, bundle);
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
            public void startLockTaskModeByToken(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(61, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startLockTaskModeByToken(iBinder);
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
            public boolean startNextMatchingActivity(IBinder iBinder, Intent intent, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    boolean bl = true;
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
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().startNextMatchingActivity(iBinder, intent, bundle);
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
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            @Override
            public void startSystemLockTaskMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(69, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public int startVoiceActivity(String var1_1, int var2_6, int var3_7, Intent var4_8, String var5_9, IVoiceInteractionSession var6_10, IVoiceInteractor var7_11, int var8_12, ProfilerInfo var9_13, Bundle var10_14, int var11_15) throws RemoteException {
                block22 : {
                    block23 : {
                        block24 : {
                            block21 : {
                                block20 : {
                                    block19 : {
                                        block18 : {
                                            block17 : {
                                                block16 : {
                                                    var12_16 = Parcel.obtain();
                                                    var13_17 = Parcel.obtain();
                                                    var12_16.writeInterfaceToken("android.app.IActivityTaskManager");
                                                    var12_16.writeString((String)var1_1);
                                                    var12_16.writeInt(var2_6);
                                                    var12_16.writeInt(var3_7);
                                                    if (var4_8 == null) break block16;
                                                    try {
                                                        var12_16.writeInt(1);
                                                        var4_8.writeToParcel(var12_16, 0);
                                                        ** GOTO lbl18
                                                    }
                                                    catch (Throwable var1_2) {
                                                        break block22;
                                                    }
                                                }
                                                var12_16.writeInt(0);
lbl18: // 2 sources:
                                                var12_16.writeString(var5_9);
                                                var14_18 = null;
                                                if (var6_10 == null) break block17;
                                                var15_19 = var6_10.asBinder();
                                                break block18;
                                            }
                                            var15_19 = null;
                                        }
                                        var12_16.writeStrongBinder((IBinder)var15_19);
                                        var15_19 = var14_18;
                                        if (var7_11 == null) break block19;
                                        var15_19 = var7_11.asBinder();
                                    }
                                    var12_16.writeStrongBinder((IBinder)var15_19);
                                    var12_16.writeInt(var8_12);
                                    if (var9_13 == null) break block20;
                                    var12_16.writeInt(1);
                                    var9_13.writeToParcel(var12_16, 0);
                                    break block21;
                                }
                                var12_16.writeInt(0);
                            }
                            if (var10_14 == null) break block24;
                            var12_16.writeInt(1);
                            var10_14.writeToParcel(var12_16, 0);
                            ** GOTO lbl54
                        }
                        var12_16.writeInt(0);
lbl54: // 2 sources:
                        var12_16.writeInt(var11_15);
                        if (this.mRemote.transact(8, var12_16, var13_17, 0) || Stub.getDefaultImpl() == null) break block23;
                        var15_19 = Stub.getDefaultImpl();
                        try {
                            var2_6 = var15_19.startVoiceActivity((String)var1_1, var2_6, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12, var9_13, var10_14, var11_15);
                            var13_17.recycle();
                            var12_16.recycle();
                            return var2_6;
                        }
                        catch (Throwable var1_3) {}
                    }
                    var1_1 = var13_17;
                    var1_1.readException();
                    var2_6 = var1_1.readInt();
                    var1_1.recycle();
                    var12_16.recycle();
                    return var2_6;
                    break block22;
                    catch (Throwable var1_4) {
                        // empty catch block
                    }
                }
                var13_17.recycle();
                var12_16.recycle();
                throw var1_5;
            }

            @Override
            public void stopAppSwitches() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(150, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            @Override
            public void stopLocalVoiceInteraction(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(127, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopLocalVoiceInteraction(iBinder);
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
            public void stopLockTaskModeByToken(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(62, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopLockTaskModeByToken(iBinder);
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
            public void stopSystemLockTaskMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(70, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopSystemLockTaskMode();
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
            public boolean supportsLocalVoiceInteraction() throws RemoteException {
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
                    if (iBinder.transact(128, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().supportsLocalVoiceInteraction();
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
            public void suppressResizeConfigChanges(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(114, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public void toggleFreeformWindowingMode(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(85, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().toggleFreeformWindowingMode(iBinder);
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
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(83, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(139, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean updateDisplayOverrideConfiguration(Configuration configuration, int n) throws RemoteException {
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
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(133, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().updateDisplayOverrideConfiguration(configuration, n);
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
            public void updateLockTaskFeatures(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(140, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateLockTaskFeatures(n, n2);
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
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public boolean willActivityBeVisible(IBinder iBinder) throws RemoteException {
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
                    if (iBinder2.transact(40, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().willActivityBeVisible(iBinder);
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
        }

    }

}

