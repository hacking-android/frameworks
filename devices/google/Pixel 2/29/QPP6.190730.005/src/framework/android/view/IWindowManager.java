/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.app.IAssistDataReceiver;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.IAppTransitionAnimationSpecsFuture;
import android.view.IDisplayFoldListener;
import android.view.IDockedStackListener;
import android.view.IOnKeyguardExitResult;
import android.view.IPinnedStackListener;
import android.view.IRotationWatcher;
import android.view.ISystemGestureExclusionListener;
import android.view.IWallpaperVisibilityListener;
import android.view.IWindowSession;
import android.view.IWindowSessionCallback;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.RemoteAnimationAdapter;
import android.view.WindowContentFrameStats;
import com.android.internal.os.IResultReceiver;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IShortcutService;

public interface IWindowManager
extends IInterface {
    public void addWindowToken(IBinder var1, int var2, int var3) throws RemoteException;

    public void clearForcedDisplayDensityForUser(int var1, int var2) throws RemoteException;

    public void clearForcedDisplaySize(int var1) throws RemoteException;

    public boolean clearWindowContentFrameStats(IBinder var1) throws RemoteException;

    public void closeSystemDialogs(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public void createInputConsumer(IBinder var1, String var2, int var3, InputChannel var4) throws RemoteException;

    @UnsupportedAppUsage
    public boolean destroyInputConsumer(String var1, int var2) throws RemoteException;

    public void disableKeyguard(IBinder var1, String var2, int var3) throws RemoteException;

    public void dismissKeyguard(IKeyguardDismissCallback var1, CharSequence var2) throws RemoteException;

    public void dontOverrideDisplayInfo(int var1) throws RemoteException;

    public void enableScreenIfNeeded() throws RemoteException;

    @UnsupportedAppUsage
    public void endProlongedAnimations() throws RemoteException;

    @UnsupportedAppUsage
    public void executeAppTransition() throws RemoteException;

    public void exitKeyguardSecurely(IOnKeyguardExitResult var1) throws RemoteException;

    public void freezeDisplayRotation(int var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void freezeRotation(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public float getAnimationScale(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public float[] getAnimationScales() throws RemoteException;

    public int getBaseDisplayDensity(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void getBaseDisplaySize(int var1, Point var2) throws RemoteException;

    public float getCurrentAnimatorScale() throws RemoteException;

    public Region getCurrentImeTouchRegion() throws RemoteException;

    public int getDefaultDisplayRotation() throws RemoteException;

    @UnsupportedAppUsage
    public int getDockedStackSide() throws RemoteException;

    @UnsupportedAppUsage
    public int getInitialDisplayDensity(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void getInitialDisplaySize(int var1, Point var2) throws RemoteException;

    public int getNavBarPosition(int var1) throws RemoteException;

    public int getPreferredOptionsPanelGravity(int var1) throws RemoteException;

    public int getRemoveContentMode(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void getStableInsets(int var1, Rect var2) throws RemoteException;

    public WindowContentFrameStats getWindowContentFrameStats(IBinder var1) throws RemoteException;

    public int getWindowingMode(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean hasNavigationBar(int var1) throws RemoteException;

    public boolean injectInputAfterTransactionsApplied(InputEvent var1, int var2) throws RemoteException;

    public boolean isDisplayRotationFrozen(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean isKeyguardLocked() throws RemoteException;

    @UnsupportedAppUsage
    public boolean isKeyguardSecure(int var1) throws RemoteException;

    public boolean isRotationFrozen() throws RemoteException;

    @UnsupportedAppUsage
    public boolean isSafeModeEnabled() throws RemoteException;

    public boolean isViewServerRunning() throws RemoteException;

    public boolean isWindowTraceEnabled() throws RemoteException;

    @UnsupportedAppUsage
    public void lockNow(Bundle var1) throws RemoteException;

    public IWindowSession openSession(IWindowSessionCallback var1) throws RemoteException;

    @UnsupportedAppUsage
    public void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture var1, IRemoteCallback var2, boolean var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public void overridePendingAppTransitionRemote(RemoteAnimationAdapter var1, int var2) throws RemoteException;

    public void prepareAppTransition(int var1, boolean var2) throws RemoteException;

    public void reenableKeyguard(IBinder var1, int var2) throws RemoteException;

    public void refreshScreenCaptureDisabled(int var1) throws RemoteException;

    public void registerDisplayFoldListener(IDisplayFoldListener var1) throws RemoteException;

    @UnsupportedAppUsage
    public void registerDockedStackListener(IDockedStackListener var1) throws RemoteException;

    public void registerPinnedStackListener(int var1, IPinnedStackListener var2) throws RemoteException;

    public void registerShortcutKey(long var1, IShortcutService var3) throws RemoteException;

    public void registerSystemGestureExclusionListener(ISystemGestureExclusionListener var1, int var2) throws RemoteException;

    public boolean registerWallpaperVisibilityListener(IWallpaperVisibilityListener var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void removeRotationWatcher(IRotationWatcher var1) throws RemoteException;

    public void removeWindowToken(IBinder var1, int var2) throws RemoteException;

    public void requestAppKeyboardShortcuts(IResultReceiver var1, int var2) throws RemoteException;

    public boolean requestAssistScreenshot(IAssistDataReceiver var1) throws RemoteException;

    public void requestUserActivityNotification() throws RemoteException;

    public Bitmap screenshotWallpaper() throws RemoteException;

    @UnsupportedAppUsage
    public void setAnimationScale(int var1, float var2) throws RemoteException;

    @UnsupportedAppUsage
    public void setAnimationScales(float[] var1) throws RemoteException;

    public void setDockedStackDividerTouchRegion(Rect var1) throws RemoteException;

    public void setEventDispatching(boolean var1) throws RemoteException;

    public void setForceShowSystemBars(boolean var1) throws RemoteException;

    public void setForcedDisplayDensityForUser(int var1, int var2, int var3) throws RemoteException;

    public void setForcedDisplayScalingMode(int var1, int var2) throws RemoteException;

    public void setForcedDisplaySize(int var1, int var2, int var3) throws RemoteException;

    public void setForwardedInsets(int var1, Insets var2) throws RemoteException;

    public void setInTouchMode(boolean var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean var1) throws RemoteException;

    public void setOverscan(int var1, int var2, int var3, int var4, int var5) throws RemoteException;

    public void setPipVisibility(boolean var1) throws RemoteException;

    public void setRecentsVisibility(boolean var1) throws RemoteException;

    public void setRemoveContentMode(int var1, int var2) throws RemoteException;

    public void setResizeDimLayer(boolean var1, int var2, float var3) throws RemoteException;

    @UnsupportedAppUsage
    public void setShelfHeight(boolean var1, int var2) throws RemoteException;

    public void setShouldShowIme(int var1, boolean var2) throws RemoteException;

    public void setShouldShowSystemDecors(int var1, boolean var2) throws RemoteException;

    public void setShouldShowWithInsecureKeyguard(int var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public void setStrictModeVisualIndicatorPreference(String var1) throws RemoteException;

    public void setSwitchingUser(boolean var1) throws RemoteException;

    public void setWindowingMode(int var1, int var2) throws RemoteException;

    public boolean shouldShowIme(int var1) throws RemoteException;

    public boolean shouldShowSystemDecors(int var1) throws RemoteException;

    public boolean shouldShowWithInsecureKeyguard(int var1) throws RemoteException;

    public void showStrictModeViolation(boolean var1) throws RemoteException;

    public void startFreezingScreen(int var1, int var2) throws RemoteException;

    public boolean startViewServer(int var1) throws RemoteException;

    public void startWindowTrace() throws RemoteException;

    public void statusBarVisibilityChanged(int var1, int var2) throws RemoteException;

    public void stopFreezingScreen() throws RemoteException;

    public boolean stopViewServer() throws RemoteException;

    public void stopWindowTrace() throws RemoteException;

    public void syncInputTransactions() throws RemoteException;

    public void thawDisplayRotation(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void thawRotation() throws RemoteException;

    public void unregisterDisplayFoldListener(IDisplayFoldListener var1) throws RemoteException;

    public void unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener var1, int var2) throws RemoteException;

    public void unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener var1, int var2) throws RemoteException;

    public void updateRotation(boolean var1, boolean var2) throws RemoteException;

    public int watchRotation(IRotationWatcher var1, int var2) throws RemoteException;

    public static class Default
    implements IWindowManager {
        @Override
        public void addWindowToken(IBinder iBinder, int n, int n2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearForcedDisplayDensityForUser(int n, int n2) throws RemoteException {
        }

        @Override
        public void clearForcedDisplaySize(int n) throws RemoteException {
        }

        @Override
        public boolean clearWindowContentFrameStats(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override
        public void closeSystemDialogs(String string2) throws RemoteException {
        }

        @Override
        public void createInputConsumer(IBinder iBinder, String string2, int n, InputChannel inputChannel) throws RemoteException {
        }

        @Override
        public boolean destroyInputConsumer(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public void disableKeyguard(IBinder iBinder, String string2, int n) throws RemoteException {
        }

        @Override
        public void dismissKeyguard(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void dontOverrideDisplayInfo(int n) throws RemoteException {
        }

        @Override
        public void enableScreenIfNeeded() throws RemoteException {
        }

        @Override
        public void endProlongedAnimations() throws RemoteException {
        }

        @Override
        public void executeAppTransition() throws RemoteException {
        }

        @Override
        public void exitKeyguardSecurely(IOnKeyguardExitResult iOnKeyguardExitResult) throws RemoteException {
        }

        @Override
        public void freezeDisplayRotation(int n, int n2) throws RemoteException {
        }

        @Override
        public void freezeRotation(int n) throws RemoteException {
        }

        @Override
        public float getAnimationScale(int n) throws RemoteException {
            return 0.0f;
        }

        @Override
        public float[] getAnimationScales() throws RemoteException {
            return null;
        }

        @Override
        public int getBaseDisplayDensity(int n) throws RemoteException {
            return 0;
        }

        @Override
        public void getBaseDisplaySize(int n, Point point) throws RemoteException {
        }

        @Override
        public float getCurrentAnimatorScale() throws RemoteException {
            return 0.0f;
        }

        @Override
        public Region getCurrentImeTouchRegion() throws RemoteException {
            return null;
        }

        @Override
        public int getDefaultDisplayRotation() throws RemoteException {
            return 0;
        }

        @Override
        public int getDockedStackSide() throws RemoteException {
            return 0;
        }

        @Override
        public int getInitialDisplayDensity(int n) throws RemoteException {
            return 0;
        }

        @Override
        public void getInitialDisplaySize(int n, Point point) throws RemoteException {
        }

        @Override
        public int getNavBarPosition(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getPreferredOptionsPanelGravity(int n) throws RemoteException {
            return 0;
        }

        @Override
        public int getRemoveContentMode(int n) throws RemoteException {
            return 0;
        }

        @Override
        public void getStableInsets(int n, Rect rect) throws RemoteException {
        }

        @Override
        public WindowContentFrameStats getWindowContentFrameStats(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public int getWindowingMode(int n) throws RemoteException {
            return 0;
        }

        @Override
        public boolean hasNavigationBar(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean injectInputAfterTransactionsApplied(InputEvent inputEvent, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isDisplayRotationFrozen(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isKeyguardLocked() throws RemoteException {
            return false;
        }

        @Override
        public boolean isKeyguardSecure(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isRotationFrozen() throws RemoteException {
            return false;
        }

        @Override
        public boolean isSafeModeEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean isViewServerRunning() throws RemoteException {
            return false;
        }

        @Override
        public boolean isWindowTraceEnabled() throws RemoteException {
            return false;
        }

        @Override
        public void lockNow(Bundle bundle) throws RemoteException {
        }

        @Override
        public IWindowSession openSession(IWindowSessionCallback iWindowSessionCallback) throws RemoteException {
            return null;
        }

        @Override
        public void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, IRemoteCallback iRemoteCallback, boolean bl, int n) throws RemoteException {
        }

        @Override
        public void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, int n) throws RemoteException {
        }

        @Override
        public void prepareAppTransition(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void reenableKeyguard(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void refreshScreenCaptureDisabled(int n) throws RemoteException {
        }

        @Override
        public void registerDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException {
        }

        @Override
        public void registerDockedStackListener(IDockedStackListener iDockedStackListener) throws RemoteException {
        }

        @Override
        public void registerPinnedStackListener(int n, IPinnedStackListener iPinnedStackListener) throws RemoteException {
        }

        @Override
        public void registerShortcutKey(long l, IShortcutService iShortcutService) throws RemoteException {
        }

        @Override
        public void registerSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int n) throws RemoteException {
        }

        @Override
        public boolean registerWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int n) throws RemoteException {
            return false;
        }

        @Override
        public void removeRotationWatcher(IRotationWatcher iRotationWatcher) throws RemoteException {
        }

        @Override
        public void removeWindowToken(IBinder iBinder, int n) throws RemoteException {
        }

        @Override
        public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int n) throws RemoteException {
        }

        @Override
        public boolean requestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver) throws RemoteException {
            return false;
        }

        @Override
        public void requestUserActivityNotification() throws RemoteException {
        }

        @Override
        public Bitmap screenshotWallpaper() throws RemoteException {
            return null;
        }

        @Override
        public void setAnimationScale(int n, float f) throws RemoteException {
        }

        @Override
        public void setAnimationScales(float[] arrf) throws RemoteException {
        }

        @Override
        public void setDockedStackDividerTouchRegion(Rect rect) throws RemoteException {
        }

        @Override
        public void setEventDispatching(boolean bl) throws RemoteException {
        }

        @Override
        public void setForceShowSystemBars(boolean bl) throws RemoteException {
        }

        @Override
        public void setForcedDisplayDensityForUser(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void setForcedDisplayScalingMode(int n, int n2) throws RemoteException {
        }

        @Override
        public void setForcedDisplaySize(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void setForwardedInsets(int n, Insets insets) throws RemoteException {
        }

        @Override
        public void setInTouchMode(boolean bl) throws RemoteException {
        }

        @Override
        public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean bl) throws RemoteException {
        }

        @Override
        public void setOverscan(int n, int n2, int n3, int n4, int n5) throws RemoteException {
        }

        @Override
        public void setPipVisibility(boolean bl) throws RemoteException {
        }

        @Override
        public void setRecentsVisibility(boolean bl) throws RemoteException {
        }

        @Override
        public void setRemoveContentMode(int n, int n2) throws RemoteException {
        }

        @Override
        public void setResizeDimLayer(boolean bl, int n, float f) throws RemoteException {
        }

        @Override
        public void setShelfHeight(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void setShouldShowIme(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setShouldShowSystemDecors(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setShouldShowWithInsecureKeyguard(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setStrictModeVisualIndicatorPreference(String string2) throws RemoteException {
        }

        @Override
        public void setSwitchingUser(boolean bl) throws RemoteException {
        }

        @Override
        public void setWindowingMode(int n, int n2) throws RemoteException {
        }

        @Override
        public boolean shouldShowIme(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean shouldShowSystemDecors(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean shouldShowWithInsecureKeyguard(int n) throws RemoteException {
            return false;
        }

        @Override
        public void showStrictModeViolation(boolean bl) throws RemoteException {
        }

        @Override
        public void startFreezingScreen(int n, int n2) throws RemoteException {
        }

        @Override
        public boolean startViewServer(int n) throws RemoteException {
            return false;
        }

        @Override
        public void startWindowTrace() throws RemoteException {
        }

        @Override
        public void statusBarVisibilityChanged(int n, int n2) throws RemoteException {
        }

        @Override
        public void stopFreezingScreen() throws RemoteException {
        }

        @Override
        public boolean stopViewServer() throws RemoteException {
            return false;
        }

        @Override
        public void stopWindowTrace() throws RemoteException {
        }

        @Override
        public void syncInputTransactions() throws RemoteException {
        }

        @Override
        public void thawDisplayRotation(int n) throws RemoteException {
        }

        @Override
        public void thawRotation() throws RemoteException {
        }

        @Override
        public void unregisterDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException {
        }

        @Override
        public void unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int n) throws RemoteException {
        }

        @Override
        public void unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int n) throws RemoteException {
        }

        @Override
        public void updateRotation(boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public int watchRotation(IRotationWatcher iRotationWatcher, int n) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWindowManager {
        private static final String DESCRIPTOR = "android.view.IWindowManager";
        static final int TRANSACTION_addWindowToken = 16;
        static final int TRANSACTION_clearForcedDisplayDensityForUser = 12;
        static final int TRANSACTION_clearForcedDisplaySize = 8;
        static final int TRANSACTION_clearWindowContentFrameStats = 70;
        static final int TRANSACTION_closeSystemDialogs = 32;
        static final int TRANSACTION_createInputConsumer = 81;
        static final int TRANSACTION_destroyInputConsumer = 82;
        static final int TRANSACTION_disableKeyguard = 25;
        static final int TRANSACTION_dismissKeyguard = 30;
        static final int TRANSACTION_dontOverrideDisplayInfo = 90;
        static final int TRANSACTION_enableScreenIfNeeded = 69;
        static final int TRANSACTION_endProlongedAnimations = 22;
        static final int TRANSACTION_executeAppTransition = 21;
        static final int TRANSACTION_exitKeyguardSecurely = 27;
        static final int TRANSACTION_freezeDisplayRotation = 50;
        static final int TRANSACTION_freezeRotation = 47;
        static final int TRANSACTION_getAnimationScale = 33;
        static final int TRANSACTION_getAnimationScales = 34;
        static final int TRANSACTION_getBaseDisplayDensity = 10;
        static final int TRANSACTION_getBaseDisplaySize = 6;
        static final int TRANSACTION_getCurrentAnimatorScale = 37;
        static final int TRANSACTION_getCurrentImeTouchRegion = 83;
        static final int TRANSACTION_getDefaultDisplayRotation = 43;
        static final int TRANSACTION_getDockedStackSide = 72;
        static final int TRANSACTION_getInitialDisplayDensity = 9;
        static final int TRANSACTION_getInitialDisplaySize = 5;
        static final int TRANSACTION_getNavBarPosition = 66;
        static final int TRANSACTION_getPreferredOptionsPanelGravity = 46;
        static final int TRANSACTION_getRemoveContentMode = 93;
        static final int TRANSACTION_getStableInsets = 78;
        static final int TRANSACTION_getWindowContentFrameStats = 71;
        static final int TRANSACTION_getWindowingMode = 91;
        static final int TRANSACTION_hasNavigationBar = 65;
        static final int TRANSACTION_injectInputAfterTransactionsApplied = 101;
        static final int TRANSACTION_isDisplayRotationFrozen = 52;
        static final int TRANSACTION_isKeyguardLocked = 28;
        static final int TRANSACTION_isKeyguardSecure = 29;
        static final int TRANSACTION_isRotationFrozen = 49;
        static final int TRANSACTION_isSafeModeEnabled = 68;
        static final int TRANSACTION_isViewServerRunning = 3;
        static final int TRANSACTION_isWindowTraceEnabled = 88;
        static final int TRANSACTION_lockNow = 67;
        static final int TRANSACTION_openSession = 4;
        static final int TRANSACTION_overridePendingAppTransitionMultiThumbFuture = 19;
        static final int TRANSACTION_overridePendingAppTransitionRemote = 20;
        static final int TRANSACTION_prepareAppTransition = 18;
        static final int TRANSACTION_reenableKeyguard = 26;
        static final int TRANSACTION_refreshScreenCaptureDisabled = 41;
        static final int TRANSACTION_registerDisplayFoldListener = 84;
        static final int TRANSACTION_registerDockedStackListener = 74;
        static final int TRANSACTION_registerPinnedStackListener = 75;
        static final int TRANSACTION_registerShortcutKey = 80;
        static final int TRANSACTION_registerSystemGestureExclusionListener = 56;
        static final int TRANSACTION_registerWallpaperVisibilityListener = 54;
        static final int TRANSACTION_removeRotationWatcher = 45;
        static final int TRANSACTION_removeWindowToken = 17;
        static final int TRANSACTION_requestAppKeyboardShortcuts = 77;
        static final int TRANSACTION_requestAssistScreenshot = 58;
        static final int TRANSACTION_requestUserActivityNotification = 89;
        static final int TRANSACTION_screenshotWallpaper = 53;
        static final int TRANSACTION_setAnimationScale = 35;
        static final int TRANSACTION_setAnimationScales = 36;
        static final int TRANSACTION_setDockedStackDividerTouchRegion = 73;
        static final int TRANSACTION_setEventDispatching = 15;
        static final int TRANSACTION_setForceShowSystemBars = 60;
        static final int TRANSACTION_setForcedDisplayDensityForUser = 11;
        static final int TRANSACTION_setForcedDisplayScalingMode = 13;
        static final int TRANSACTION_setForcedDisplaySize = 7;
        static final int TRANSACTION_setForwardedInsets = 79;
        static final int TRANSACTION_setInTouchMode = 38;
        static final int TRANSACTION_setNavBarVirtualKeyHapticFeedbackEnabled = 64;
        static final int TRANSACTION_setOverscan = 14;
        static final int TRANSACTION_setPipVisibility = 62;
        static final int TRANSACTION_setRecentsVisibility = 61;
        static final int TRANSACTION_setRemoveContentMode = 94;
        static final int TRANSACTION_setResizeDimLayer = 76;
        static final int TRANSACTION_setShelfHeight = 63;
        static final int TRANSACTION_setShouldShowIme = 100;
        static final int TRANSACTION_setShouldShowSystemDecors = 98;
        static final int TRANSACTION_setShouldShowWithInsecureKeyguard = 96;
        static final int TRANSACTION_setStrictModeVisualIndicatorPreference = 40;
        static final int TRANSACTION_setSwitchingUser = 31;
        static final int TRANSACTION_setWindowingMode = 92;
        static final int TRANSACTION_shouldShowIme = 99;
        static final int TRANSACTION_shouldShowSystemDecors = 97;
        static final int TRANSACTION_shouldShowWithInsecureKeyguard = 95;
        static final int TRANSACTION_showStrictModeViolation = 39;
        static final int TRANSACTION_startFreezingScreen = 23;
        static final int TRANSACTION_startViewServer = 1;
        static final int TRANSACTION_startWindowTrace = 86;
        static final int TRANSACTION_statusBarVisibilityChanged = 59;
        static final int TRANSACTION_stopFreezingScreen = 24;
        static final int TRANSACTION_stopViewServer = 2;
        static final int TRANSACTION_stopWindowTrace = 87;
        static final int TRANSACTION_syncInputTransactions = 102;
        static final int TRANSACTION_thawDisplayRotation = 51;
        static final int TRANSACTION_thawRotation = 48;
        static final int TRANSACTION_unregisterDisplayFoldListener = 85;
        static final int TRANSACTION_unregisterSystemGestureExclusionListener = 57;
        static final int TRANSACTION_unregisterWallpaperVisibilityListener = 55;
        static final int TRANSACTION_updateRotation = 42;
        static final int TRANSACTION_watchRotation = 44;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWindowManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWindowManager) {
                return (IWindowManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWindowManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 102: {
                    return "syncInputTransactions";
                }
                case 101: {
                    return "injectInputAfterTransactionsApplied";
                }
                case 100: {
                    return "setShouldShowIme";
                }
                case 99: {
                    return "shouldShowIme";
                }
                case 98: {
                    return "setShouldShowSystemDecors";
                }
                case 97: {
                    return "shouldShowSystemDecors";
                }
                case 96: {
                    return "setShouldShowWithInsecureKeyguard";
                }
                case 95: {
                    return "shouldShowWithInsecureKeyguard";
                }
                case 94: {
                    return "setRemoveContentMode";
                }
                case 93: {
                    return "getRemoveContentMode";
                }
                case 92: {
                    return "setWindowingMode";
                }
                case 91: {
                    return "getWindowingMode";
                }
                case 90: {
                    return "dontOverrideDisplayInfo";
                }
                case 89: {
                    return "requestUserActivityNotification";
                }
                case 88: {
                    return "isWindowTraceEnabled";
                }
                case 87: {
                    return "stopWindowTrace";
                }
                case 86: {
                    return "startWindowTrace";
                }
                case 85: {
                    return "unregisterDisplayFoldListener";
                }
                case 84: {
                    return "registerDisplayFoldListener";
                }
                case 83: {
                    return "getCurrentImeTouchRegion";
                }
                case 82: {
                    return "destroyInputConsumer";
                }
                case 81: {
                    return "createInputConsumer";
                }
                case 80: {
                    return "registerShortcutKey";
                }
                case 79: {
                    return "setForwardedInsets";
                }
                case 78: {
                    return "getStableInsets";
                }
                case 77: {
                    return "requestAppKeyboardShortcuts";
                }
                case 76: {
                    return "setResizeDimLayer";
                }
                case 75: {
                    return "registerPinnedStackListener";
                }
                case 74: {
                    return "registerDockedStackListener";
                }
                case 73: {
                    return "setDockedStackDividerTouchRegion";
                }
                case 72: {
                    return "getDockedStackSide";
                }
                case 71: {
                    return "getWindowContentFrameStats";
                }
                case 70: {
                    return "clearWindowContentFrameStats";
                }
                case 69: {
                    return "enableScreenIfNeeded";
                }
                case 68: {
                    return "isSafeModeEnabled";
                }
                case 67: {
                    return "lockNow";
                }
                case 66: {
                    return "getNavBarPosition";
                }
                case 65: {
                    return "hasNavigationBar";
                }
                case 64: {
                    return "setNavBarVirtualKeyHapticFeedbackEnabled";
                }
                case 63: {
                    return "setShelfHeight";
                }
                case 62: {
                    return "setPipVisibility";
                }
                case 61: {
                    return "setRecentsVisibility";
                }
                case 60: {
                    return "setForceShowSystemBars";
                }
                case 59: {
                    return "statusBarVisibilityChanged";
                }
                case 58: {
                    return "requestAssistScreenshot";
                }
                case 57: {
                    return "unregisterSystemGestureExclusionListener";
                }
                case 56: {
                    return "registerSystemGestureExclusionListener";
                }
                case 55: {
                    return "unregisterWallpaperVisibilityListener";
                }
                case 54: {
                    return "registerWallpaperVisibilityListener";
                }
                case 53: {
                    return "screenshotWallpaper";
                }
                case 52: {
                    return "isDisplayRotationFrozen";
                }
                case 51: {
                    return "thawDisplayRotation";
                }
                case 50: {
                    return "freezeDisplayRotation";
                }
                case 49: {
                    return "isRotationFrozen";
                }
                case 48: {
                    return "thawRotation";
                }
                case 47: {
                    return "freezeRotation";
                }
                case 46: {
                    return "getPreferredOptionsPanelGravity";
                }
                case 45: {
                    return "removeRotationWatcher";
                }
                case 44: {
                    return "watchRotation";
                }
                case 43: {
                    return "getDefaultDisplayRotation";
                }
                case 42: {
                    return "updateRotation";
                }
                case 41: {
                    return "refreshScreenCaptureDisabled";
                }
                case 40: {
                    return "setStrictModeVisualIndicatorPreference";
                }
                case 39: {
                    return "showStrictModeViolation";
                }
                case 38: {
                    return "setInTouchMode";
                }
                case 37: {
                    return "getCurrentAnimatorScale";
                }
                case 36: {
                    return "setAnimationScales";
                }
                case 35: {
                    return "setAnimationScale";
                }
                case 34: {
                    return "getAnimationScales";
                }
                case 33: {
                    return "getAnimationScale";
                }
                case 32: {
                    return "closeSystemDialogs";
                }
                case 31: {
                    return "setSwitchingUser";
                }
                case 30: {
                    return "dismissKeyguard";
                }
                case 29: {
                    return "isKeyguardSecure";
                }
                case 28: {
                    return "isKeyguardLocked";
                }
                case 27: {
                    return "exitKeyguardSecurely";
                }
                case 26: {
                    return "reenableKeyguard";
                }
                case 25: {
                    return "disableKeyguard";
                }
                case 24: {
                    return "stopFreezingScreen";
                }
                case 23: {
                    return "startFreezingScreen";
                }
                case 22: {
                    return "endProlongedAnimations";
                }
                case 21: {
                    return "executeAppTransition";
                }
                case 20: {
                    return "overridePendingAppTransitionRemote";
                }
                case 19: {
                    return "overridePendingAppTransitionMultiThumbFuture";
                }
                case 18: {
                    return "prepareAppTransition";
                }
                case 17: {
                    return "removeWindowToken";
                }
                case 16: {
                    return "addWindowToken";
                }
                case 15: {
                    return "setEventDispatching";
                }
                case 14: {
                    return "setOverscan";
                }
                case 13: {
                    return "setForcedDisplayScalingMode";
                }
                case 12: {
                    return "clearForcedDisplayDensityForUser";
                }
                case 11: {
                    return "setForcedDisplayDensityForUser";
                }
                case 10: {
                    return "getBaseDisplayDensity";
                }
                case 9: {
                    return "getInitialDisplayDensity";
                }
                case 8: {
                    return "clearForcedDisplaySize";
                }
                case 7: {
                    return "setForcedDisplaySize";
                }
                case 6: {
                    return "getBaseDisplaySize";
                }
                case 5: {
                    return "getInitialDisplaySize";
                }
                case 4: {
                    return "openSession";
                }
                case 3: {
                    return "isViewServerRunning";
                }
                case 2: {
                    return "stopViewServer";
                }
                case 1: 
            }
            return "startViewServer";
        }

        public static boolean setDefaultImpl(IWindowManager iWindowManager) {
            if (Proxy.sDefaultImpl == null && iWindowManager != null) {
                Proxy.sDefaultImpl = iWindowManager;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 102: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.syncInputTransactions();
                        parcel.writeNoException();
                        return true;
                    }
                    case 101: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        InputEvent inputEvent = ((Parcel)object).readInt() != 0 ? InputEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.injectInputAfterTransactionsApplied(inputEvent, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 100: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.setShouldShowIme(n, bl16);
                        parcel.writeNoException();
                        return true;
                    }
                    case 99: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.shouldShowIme(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 98: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl16 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.setShouldShowSystemDecors(n, bl16);
                        parcel.writeNoException();
                        return true;
                    }
                    case 97: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.shouldShowSystemDecors(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 96: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl16 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.setShouldShowWithInsecureKeyguard(n, bl16);
                        parcel.writeNoException();
                        return true;
                    }
                    case 95: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.shouldShowWithInsecureKeyguard(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 94: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRemoveContentMode(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 93: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRemoveContentMode(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 92: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setWindowingMode(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 91: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getWindowingMode(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 90: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dontOverrideDisplayInfo(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 89: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestUserActivityNotification();
                        parcel.writeNoException();
                        return true;
                    }
                    case 88: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isWindowTraceEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 87: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopWindowTrace();
                        parcel.writeNoException();
                        return true;
                    }
                    case 86: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startWindowTrace();
                        parcel.writeNoException();
                        return true;
                    }
                    case 85: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterDisplayFoldListener(IDisplayFoldListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 84: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerDisplayFoldListener(IDisplayFoldListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 83: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCurrentImeTouchRegion();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Region)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 82: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.destroyInputConsumer(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 81: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = new InputChannel();
                        this.createInputConsumer(iBinder, string2, n, (InputChannel)object);
                        parcel.writeNoException();
                        parcel.writeInt(1);
                        ((InputChannel)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    case 80: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerShortcutKey(((Parcel)object).readLong(), IShortcutService.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 79: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Insets.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setForwardedInsets(n, (Insets)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 78: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = new Rect();
                        this.getStableInsets(n, (Rect)object);
                        parcel.writeNoException();
                        parcel.writeInt(1);
                        ((Rect)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    case 77: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestAppKeyboardShortcuts(IResultReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 76: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl16 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.setResizeDimLayer(bl16, ((Parcel)object).readInt(), ((Parcel)object).readFloat());
                        parcel.writeNoException();
                        return true;
                    }
                    case 75: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerPinnedStackListener(((Parcel)object).readInt(), IPinnedStackListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerDockedStackListener(IDockedStackListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDockedStackDividerTouchRegion((Rect)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDockedStackSide();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWindowContentFrameStats(((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((WindowContentFrameStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.clearWindowContentFrameStats(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enableScreenIfNeeded();
                        parcel.writeNoException();
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSafeModeEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.lockNow((Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getNavBarPosition(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasNavigationBar(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl16 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.setNavBarVirtualKeyHapticFeedbackEnabled(bl16);
                        parcel.writeNoException();
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl16 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.setShelfHeight(bl16, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl16 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.setPipVisibility(bl16);
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl16 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.setRecentsVisibility(bl16);
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl16 = bl8;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.setForceShowSystemBars(bl16);
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.statusBarVisibilityChanged(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.requestAssistScreenshot(IAssistDataReceiver.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerSystemGestureExclusionListener(ISystemGestureExclusionListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.registerWallpaperVisibilityListener(IWallpaperVisibilityListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.screenshotWallpaper();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bitmap)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDisplayRotationFrozen(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.thawDisplayRotation(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.freezeDisplayRotation(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isRotationFrozen() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.thawRotation();
                        parcel.writeNoException();
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.freezeRotation(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPreferredOptionsPanelGravity(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeRotationWatcher(IRotationWatcher.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.watchRotation(IRotationWatcher.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDefaultDisplayRotation();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl16 = ((Parcel)object).readInt() != 0;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.updateRotation(bl16, bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.refreshScreenCaptureDisabled(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setStrictModeVisualIndicatorPreference(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl16 = bl10;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.showStrictModeViolation(bl16);
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl16 = bl11;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.setInTouchMode(bl16);
                        parcel.writeNoException();
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        float f = this.getCurrentAnimatorScale();
                        parcel.writeNoException();
                        parcel.writeFloat(f);
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setAnimationScales(((Parcel)object).createFloatArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setAnimationScale(((Parcel)object).readInt(), ((Parcel)object).readFloat());
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAnimationScales();
                        parcel.writeNoException();
                        parcel.writeFloatArray((float[])object);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        float f = this.getAnimationScale(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeFloat(f);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.closeSystemDialogs(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl16 = bl12;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.setSwitchingUser(bl16);
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IKeyguardDismissCallback iKeyguardDismissCallback = IKeyguardDismissCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.dismissKeyguard(iKeyguardDismissCallback, (CharSequence)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isKeyguardSecure(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isKeyguardLocked() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.exitKeyguardSecurely(IOnKeyguardExitResult.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reenableKeyguard(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableKeyguard(((Parcel)object).readStrongBinder(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopFreezingScreen();
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startFreezingScreen(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.endProlongedAnimations();
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.executeAppTransition();
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        RemoteAnimationAdapter remoteAnimationAdapter = ((Parcel)object).readInt() != 0 ? RemoteAnimationAdapter.CREATOR.createFromParcel((Parcel)object) : null;
                        this.overridePendingAppTransitionRemote(remoteAnimationAdapter, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture = IAppTransitionAnimationSpecsFuture.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IRemoteCallback iRemoteCallback = IRemoteCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        bl16 = bl13;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.overridePendingAppTransitionMultiThumbFuture(iAppTransitionAnimationSpecsFuture, iRemoteCallback, bl16, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl16 = bl14;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.prepareAppTransition(n, bl16);
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeWindowToken(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addWindowToken(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl16 = bl15;
                        if (((Parcel)object).readInt() != 0) {
                            bl16 = true;
                        }
                        this.setEventDispatching(bl16);
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setOverscan(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setForcedDisplayScalingMode(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearForcedDisplayDensityForUser(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setForcedDisplayDensityForUser(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getBaseDisplayDensity(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getInitialDisplayDensity(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearForcedDisplaySize(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setForcedDisplaySize(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = new Point();
                        this.getBaseDisplaySize(n, (Point)object);
                        parcel.writeNoException();
                        parcel.writeInt(1);
                        ((Point)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = new Point();
                        this.getInitialDisplaySize(n, (Point)object);
                        parcel.writeNoException();
                        parcel.writeInt(1);
                        ((Point)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.openSession(IWindowSessionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        object = object != null ? object.asBinder() : null;
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isViewServerRunning() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.stopViewServer() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.startViewServer(((Parcel)object).readInt()) ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IWindowManager {
            public static IWindowManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addWindowToken(IBinder iBinder, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addWindowToken(iBinder, n, n2);
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
            public void clearForcedDisplayDensityForUser(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearForcedDisplayDensityForUser(n, n2);
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
            public void clearForcedDisplaySize(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearForcedDisplaySize(n);
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
            public boolean clearWindowContentFrameStats(IBinder iBinder) throws RemoteException {
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
                    if (iBinder2.transact(70, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().clearWindowContentFrameStats(iBinder);
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
            public void closeSystemDialogs(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public void createInputConsumer(IBinder iBinder, String string2, int n, InputChannel inputChannel) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(81, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createInputConsumer(iBinder, string2, n, inputChannel);
                        return;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        inputChannel.readFromParcel(parcel2);
                    }
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean destroyInputConsumer(String string2, int n) throws RemoteException {
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
                    if (iBinder.transact(82, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().destroyInputConsumer(string2, n);
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
            public void disableKeyguard(IBinder iBinder, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableKeyguard(iBinder, string2, n);
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
            public void dismissKeyguard(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iKeyguardDismissCallback != null ? iKeyguardDismissCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dismissKeyguard(iKeyguardDismissCallback, charSequence);
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
            public void dontOverrideDisplayInfo(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(90, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dontOverrideDisplayInfo(n);
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
            public void enableScreenIfNeeded() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(69, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableScreenIfNeeded();
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
            public void endProlongedAnimations() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().endProlongedAnimations();
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
            public void executeAppTransition() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().executeAppTransition();
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
            public void exitKeyguardSecurely(IOnKeyguardExitResult iOnKeyguardExitResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iOnKeyguardExitResult != null ? iOnKeyguardExitResult.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().exitKeyguardSecurely(iOnKeyguardExitResult);
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
            public void freezeDisplayRotation(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().freezeDisplayRotation(n, n2);
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
            public void freezeRotation(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().freezeRotation(n);
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
            public float getAnimationScale(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        float f = Stub.getDefaultImpl().getAnimationScale(n);
                        return f;
                    }
                    parcel2.readException();
                    float f = parcel2.readFloat();
                    return f;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public float[] getAnimationScales() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        float[] arrf = Stub.getDefaultImpl().getAnimationScales();
                        return arrf;
                    }
                    parcel2.readException();
                    float[] arrf = parcel2.createFloatArray();
                    return arrf;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getBaseDisplayDensity(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getBaseDisplayDensity(n);
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
            public void getBaseDisplaySize(int n, Point point) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getBaseDisplaySize(n, point);
                        return;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        point.readFromParcel(parcel2);
                    }
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public float getCurrentAnimatorScale() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        float f = Stub.getDefaultImpl().getCurrentAnimatorScale();
                        return f;
                    }
                    parcel2.readException();
                    float f = parcel2.readFloat();
                    return f;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Region getCurrentImeTouchRegion() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(83, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Region region = Stub.getDefaultImpl().getCurrentImeTouchRegion();
                        parcel.recycle();
                        parcel2.recycle();
                        return region;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Region region = parcel.readInt() != 0 ? Region.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return region;
            }

            @Override
            public int getDefaultDisplayRotation() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getDefaultDisplayRotation();
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
            public int getDockedStackSide() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(72, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getDockedStackSide();
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
            public int getInitialDisplayDensity(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getInitialDisplayDensity(n);
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
            public void getInitialDisplaySize(int n, Point point) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getInitialDisplaySize(n, point);
                        return;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        point.readFromParcel(parcel2);
                    }
                    return;
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
            public int getNavBarPosition(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(66, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getNavBarPosition(n);
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
            public int getPreferredOptionsPanelGravity(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPreferredOptionsPanelGravity(n);
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
            public int getRemoveContentMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(93, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getRemoveContentMode(n);
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
            public void getStableInsets(int n, Rect rect) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(78, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getStableInsets(n, rect);
                        return;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        rect.readFromParcel(parcel2);
                    }
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public WindowContentFrameStats getWindowContentFrameStats(IBinder object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeStrongBinder((IBinder)object);
                        if (this.mRemote.transact(71, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getWindowContentFrameStats((IBinder)object);
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
                object = parcel2.readInt() != 0 ? WindowContentFrameStats.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public int getWindowingMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(91, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getWindowingMode(n);
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
            public boolean hasNavigationBar(int n) throws RemoteException {
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
                    if (iBinder.transact(65, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasNavigationBar(n);
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
            public boolean injectInputAfterTransactionsApplied(InputEvent inputEvent, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (inputEvent != null) {
                        parcel.writeInt(1);
                        inputEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(101, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().injectInputAfterTransactionsApplied(inputEvent, n);
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
            public boolean isDisplayRotationFrozen(int n) throws RemoteException {
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
                    if (iBinder.transact(52, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDisplayRotationFrozen(n);
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
            public boolean isKeyguardLocked() throws RemoteException {
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
                    if (iBinder.transact(28, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isKeyguardLocked();
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
            public boolean isKeyguardSecure(int n) throws RemoteException {
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
                    if (iBinder.transact(29, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isKeyguardSecure(n);
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
            public boolean isRotationFrozen() throws RemoteException {
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
                    if (iBinder.transact(49, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRotationFrozen();
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
            public boolean isSafeModeEnabled() throws RemoteException {
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
                    if (iBinder.transact(68, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSafeModeEnabled();
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
            public boolean isViewServerRunning() throws RemoteException {
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
                    if (iBinder.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isViewServerRunning();
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
            public boolean isWindowTraceEnabled() throws RemoteException {
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
                    if (iBinder.transact(88, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isWindowTraceEnabled();
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
            public void lockNow(Bundle bundle) throws RemoteException {
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
                    if (!this.mRemote.transact(67, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().lockNow(bundle);
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
            public IWindowSession openSession(IWindowSessionCallback iInterface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInterface != null ? iInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IWindowSession iWindowSession = Stub.getDefaultImpl().openSession((IWindowSessionCallback)iInterface);
                        return iWindowSession;
                    }
                    parcel2.readException();
                    IWindowSession iWindowSession = IWindowSession.Stub.asInterface(parcel2.readStrongBinder());
                    return iWindowSession;
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
            public void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, IRemoteCallback iRemoteCallback, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    Object var7_8 = null;
                    IBinder iBinder = iAppTransitionAnimationSpecsFuture != null ? iAppTransitionAnimationSpecsFuture.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var7_8;
                    if (iRemoteCallback != null) {
                        iBinder = iRemoteCallback.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().overridePendingAppTransitionMultiThumbFuture(iAppTransitionAnimationSpecsFuture, iRemoteCallback, bl, n);
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
            public void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (remoteAnimationAdapter != null) {
                        parcel.writeInt(1);
                        remoteAnimationAdapter.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().overridePendingAppTransitionRemote(remoteAnimationAdapter, n);
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
            public void prepareAppTransition(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().prepareAppTransition(n, bl);
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
            public void reenableKeyguard(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reenableKeyguard(iBinder, n);
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
            public void refreshScreenCaptureDisabled(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().refreshScreenCaptureDisabled(n);
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
            public void registerDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iDisplayFoldListener != null ? iDisplayFoldListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(84, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerDisplayFoldListener(iDisplayFoldListener);
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
            public void registerDockedStackListener(IDockedStackListener iDockedStackListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iDockedStackListener != null ? iDockedStackListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(74, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerDockedStackListener(iDockedStackListener);
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
            public void registerPinnedStackListener(int n, IPinnedStackListener iPinnedStackListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iPinnedStackListener != null ? iPinnedStackListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(75, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerPinnedStackListener(n, iPinnedStackListener);
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
            public void registerShortcutKey(long l, IShortcutService iShortcutService) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    IBinder iBinder = iShortcutService != null ? iShortcutService.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(80, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerShortcutKey(l, iShortcutService);
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
            public void registerSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSystemGestureExclusionListener != null ? iSystemGestureExclusionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerSystemGestureExclusionListener(iSystemGestureExclusionListener, n);
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
            public boolean registerWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int n) throws RemoteException {
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
                                if (iWallpaperVisibilityListener == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iWallpaperVisibilityListener.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    parcel2.writeInt(n);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(54, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().registerWallpaperVisibilityListener(iWallpaperVisibilityListener, n);
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
            public void removeRotationWatcher(IRotationWatcher iRotationWatcher) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRotationWatcher != null ? iRotationWatcher.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeRotationWatcher(iRotationWatcher);
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
            public void removeWindowToken(IBinder iBinder, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeWindowToken(iBinder, n);
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
            public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iResultReceiver != null ? iResultReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(77, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestAppKeyboardShortcuts(iResultReceiver, n);
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
            public boolean requestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver) throws RemoteException {
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
                                if (iAssistDataReceiver == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iAssistDataReceiver.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(58, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().requestAssistScreenshot(iAssistDataReceiver);
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
            public void requestUserActivityNotification() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(89, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestUserActivityNotification();
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
            public Bitmap screenshotWallpaper() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(53, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Bitmap bitmap = Stub.getDefaultImpl().screenshotWallpaper();
                        parcel.recycle();
                        parcel2.recycle();
                        return bitmap;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Bitmap bitmap = parcel.readInt() != 0 ? Bitmap.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return bitmap;
            }

            @Override
            public void setAnimationScale(int n, float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAnimationScale(n, f);
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
            public void setAnimationScales(float[] arrf) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeFloatArray(arrf);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAnimationScales(arrf);
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
            public void setDockedStackDividerTouchRegion(Rect rect) throws RemoteException {
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
                    if (!this.mRemote.transact(73, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDockedStackDividerTouchRegion(rect);
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
            public void setEventDispatching(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setEventDispatching(bl);
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
            public void setForceShowSystemBars(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(60, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setForceShowSystemBars(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setForcedDisplayDensityForUser(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setForcedDisplayDensityForUser(n, n2, n3);
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
            public void setForcedDisplayScalingMode(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setForcedDisplayScalingMode(n, n2);
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
            public void setForcedDisplaySize(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setForcedDisplaySize(n, n2, n3);
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
            public void setForwardedInsets(int n, Insets insets) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (insets != null) {
                        parcel.writeInt(1);
                        insets.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(79, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setForwardedInsets(n, insets);
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
            public void setInTouchMode(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInTouchMode(bl);
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
            public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(64, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNavBarVirtualKeyHapticFeedbackEnabled(bl);
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
            public void setOverscan(int n, int n2, int n3, int n4, int n5) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    parcel.writeInt(n5);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOverscan(n, n2, n3, n4, n5);
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
            public void setPipVisibility(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(62, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPipVisibility(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setRecentsVisibility(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(61, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRecentsVisibility(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setRemoveContentMode(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(94, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRemoveContentMode(n, n2);
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
            public void setResizeDimLayer(boolean bl, int n, float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(76, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setResizeDimLayer(bl, n, f);
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
            public void setShelfHeight(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setShelfHeight(bl, n);
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
            public void setShouldShowIme(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(100, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setShouldShowIme(n, bl);
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
            public void setShouldShowSystemDecors(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(98, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setShouldShowSystemDecors(n, bl);
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
            public void setShouldShowWithInsecureKeyguard(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(96, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setShouldShowWithInsecureKeyguard(n, bl);
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
            public void setStrictModeVisualIndicatorPreference(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStrictModeVisualIndicatorPreference(string2);
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
            public void setSwitchingUser(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSwitchingUser(bl);
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
            public void setWindowingMode(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(92, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setWindowingMode(n, n2);
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
            public boolean shouldShowIme(int n) throws RemoteException {
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
                    bl = Stub.getDefaultImpl().shouldShowIme(n);
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
            public boolean shouldShowSystemDecors(int n) throws RemoteException {
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
                    if (iBinder.transact(97, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().shouldShowSystemDecors(n);
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
            public boolean shouldShowWithInsecureKeyguard(int n) throws RemoteException {
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
                    if (iBinder.transact(95, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().shouldShowWithInsecureKeyguard(n);
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
            public void showStrictModeViolation(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showStrictModeViolation(bl);
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
            public void startFreezingScreen(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startFreezingScreen(n, n2);
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
            public boolean startViewServer(int n) throws RemoteException {
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
                    if (iBinder.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().startViewServer(n);
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
            public void startWindowTrace() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(86, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startWindowTrace();
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
            public void statusBarVisibilityChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(59, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().statusBarVisibilityChanged(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stopFreezingScreen() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopFreezingScreen();
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
            public boolean stopViewServer() throws RemoteException {
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
                    if (iBinder.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().stopViewServer();
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
            public void stopWindowTrace() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(87, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopWindowTrace();
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
            public void syncInputTransactions() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(102, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().syncInputTransactions();
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
            public void thawDisplayRotation(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().thawDisplayRotation(n);
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
            public void thawRotation() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().thawRotation();
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
            public void unregisterDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iDisplayFoldListener != null ? iDisplayFoldListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(85, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterDisplayFoldListener(iDisplayFoldListener);
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
            public void unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSystemGestureExclusionListener != null ? iSystemGestureExclusionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(57, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterSystemGestureExclusionListener(iSystemGestureExclusionListener, n);
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
            public void unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iWallpaperVisibilityListener != null ? iWallpaperVisibilityListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterWallpaperVisibilityListener(iWallpaperVisibilityListener, n);
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
            public void updateRotation(boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = 1;
                int n2 = bl ? 1 : 0;
                parcel.writeInt(n2);
                n2 = bl2 ? n : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateRotation(bl, bl2);
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
            public int watchRotation(IRotationWatcher iRotationWatcher, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRotationWatcher != null ? iRotationWatcher.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().watchRotation(iRotationWatcher, n);
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
        }

    }

}

