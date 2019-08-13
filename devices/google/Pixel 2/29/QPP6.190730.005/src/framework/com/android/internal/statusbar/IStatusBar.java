/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.statusbar;

import android.content.ComponentName;
import android.graphics.Rect;
import android.hardware.biometrics.IBiometricServiceReceiverInternal;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.statusbar.StatusBarIcon;

public interface IStatusBar
extends IInterface {
    public void addQsTile(ComponentName var1) throws RemoteException;

    public void animateCollapsePanels() throws RemoteException;

    public void animateExpandNotificationsPanel() throws RemoteException;

    public void animateExpandSettingsPanel(String var1) throws RemoteException;

    public void appTransitionCancelled(int var1) throws RemoteException;

    public void appTransitionFinished(int var1) throws RemoteException;

    public void appTransitionPending(int var1) throws RemoteException;

    public void appTransitionStarting(int var1, long var2, long var4) throws RemoteException;

    public void cancelPreloadRecentApps() throws RemoteException;

    public void clickQsTile(ComponentName var1) throws RemoteException;

    public void disable(int var1, int var2, int var3) throws RemoteException;

    public void dismissKeyboardShortcutsMenu() throws RemoteException;

    public void handleSystemKey(int var1) throws RemoteException;

    public void hideBiometricDialog() throws RemoteException;

    public void hideRecentApps(boolean var1, boolean var2) throws RemoteException;

    public void onBiometricAuthenticated(boolean var1, String var2) throws RemoteException;

    public void onBiometricError(String var1) throws RemoteException;

    public void onBiometricHelp(String var1) throws RemoteException;

    public void onCameraLaunchGestureDetected(int var1) throws RemoteException;

    public void onDisplayReady(int var1) throws RemoteException;

    public void onProposedRotationChanged(int var1, boolean var2) throws RemoteException;

    public void onRecentsAnimationStateChanged(boolean var1) throws RemoteException;

    public void preloadRecentApps() throws RemoteException;

    public void remQsTile(ComponentName var1) throws RemoteException;

    public void removeIcon(String var1) throws RemoteException;

    public void setIcon(String var1, StatusBarIcon var2) throws RemoteException;

    public void setImeWindowStatus(int var1, IBinder var2, int var3, int var4, boolean var5) throws RemoteException;

    public void setSystemUiVisibility(int var1, int var2, int var3, int var4, int var5, Rect var6, Rect var7, boolean var8) throws RemoteException;

    public void setTopAppHidesStatusBar(boolean var1) throws RemoteException;

    public void setWindowState(int var1, int var2, int var3) throws RemoteException;

    public void showAssistDisclosure() throws RemoteException;

    public void showBiometricDialog(Bundle var1, IBiometricServiceReceiverInternal var2, int var3, boolean var4, int var5) throws RemoteException;

    public void showGlobalActionsMenu() throws RemoteException;

    public void showPictureInPictureMenu() throws RemoteException;

    public void showPinningEnterExitToast(boolean var1) throws RemoteException;

    public void showPinningEscapeToast() throws RemoteException;

    public void showRecentApps(boolean var1) throws RemoteException;

    public void showScreenPinningRequest(int var1) throws RemoteException;

    public void showShutdownUi(boolean var1, String var2) throws RemoteException;

    public void showWirelessChargingAnimation(int var1) throws RemoteException;

    public void startAssist(Bundle var1) throws RemoteException;

    public void toggleKeyboardShortcutsMenu(int var1) throws RemoteException;

    public void togglePanel() throws RemoteException;

    public void toggleRecentApps() throws RemoteException;

    public void toggleSplitScreen() throws RemoteException;

    public void topAppWindowChanged(int var1, boolean var2) throws RemoteException;

    public static class Default
    implements IStatusBar {
        @Override
        public void addQsTile(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void animateCollapsePanels() throws RemoteException {
        }

        @Override
        public void animateExpandNotificationsPanel() throws RemoteException {
        }

        @Override
        public void animateExpandSettingsPanel(String string2) throws RemoteException {
        }

        @Override
        public void appTransitionCancelled(int n) throws RemoteException {
        }

        @Override
        public void appTransitionFinished(int n) throws RemoteException {
        }

        @Override
        public void appTransitionPending(int n) throws RemoteException {
        }

        @Override
        public void appTransitionStarting(int n, long l, long l2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelPreloadRecentApps() throws RemoteException {
        }

        @Override
        public void clickQsTile(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void disable(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void dismissKeyboardShortcutsMenu() throws RemoteException {
        }

        @Override
        public void handleSystemKey(int n) throws RemoteException {
        }

        @Override
        public void hideBiometricDialog() throws RemoteException {
        }

        @Override
        public void hideRecentApps(boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void onBiometricAuthenticated(boolean bl, String string2) throws RemoteException {
        }

        @Override
        public void onBiometricError(String string2) throws RemoteException {
        }

        @Override
        public void onBiometricHelp(String string2) throws RemoteException {
        }

        @Override
        public void onCameraLaunchGestureDetected(int n) throws RemoteException {
        }

        @Override
        public void onDisplayReady(int n) throws RemoteException {
        }

        @Override
        public void onProposedRotationChanged(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void onRecentsAnimationStateChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void preloadRecentApps() throws RemoteException {
        }

        @Override
        public void remQsTile(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void removeIcon(String string2) throws RemoteException {
        }

        @Override
        public void setIcon(String string2, StatusBarIcon statusBarIcon) throws RemoteException {
        }

        @Override
        public void setImeWindowStatus(int n, IBinder iBinder, int n2, int n3, boolean bl) throws RemoteException {
        }

        @Override
        public void setSystemUiVisibility(int n, int n2, int n3, int n4, int n5, Rect rect, Rect rect2, boolean bl) throws RemoteException {
        }

        @Override
        public void setTopAppHidesStatusBar(boolean bl) throws RemoteException {
        }

        @Override
        public void setWindowState(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void showAssistDisclosure() throws RemoteException {
        }

        @Override
        public void showBiometricDialog(Bundle bundle, IBiometricServiceReceiverInternal iBiometricServiceReceiverInternal, int n, boolean bl, int n2) throws RemoteException {
        }

        @Override
        public void showGlobalActionsMenu() throws RemoteException {
        }

        @Override
        public void showPictureInPictureMenu() throws RemoteException {
        }

        @Override
        public void showPinningEnterExitToast(boolean bl) throws RemoteException {
        }

        @Override
        public void showPinningEscapeToast() throws RemoteException {
        }

        @Override
        public void showRecentApps(boolean bl) throws RemoteException {
        }

        @Override
        public void showScreenPinningRequest(int n) throws RemoteException {
        }

        @Override
        public void showShutdownUi(boolean bl, String string2) throws RemoteException {
        }

        @Override
        public void showWirelessChargingAnimation(int n) throws RemoteException {
        }

        @Override
        public void startAssist(Bundle bundle) throws RemoteException {
        }

        @Override
        public void toggleKeyboardShortcutsMenu(int n) throws RemoteException {
        }

        @Override
        public void togglePanel() throws RemoteException {
        }

        @Override
        public void toggleRecentApps() throws RemoteException {
        }

        @Override
        public void toggleSplitScreen() throws RemoteException {
        }

        @Override
        public void topAppWindowChanged(int n, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IStatusBar {
        private static final String DESCRIPTOR = "com.android.internal.statusbar.IStatusBar";
        static final int TRANSACTION_addQsTile = 33;
        static final int TRANSACTION_animateCollapsePanels = 6;
        static final int TRANSACTION_animateExpandNotificationsPanel = 4;
        static final int TRANSACTION_animateExpandSettingsPanel = 5;
        static final int TRANSACTION_appTransitionCancelled = 23;
        static final int TRANSACTION_appTransitionFinished = 25;
        static final int TRANSACTION_appTransitionPending = 22;
        static final int TRANSACTION_appTransitionStarting = 24;
        static final int TRANSACTION_cancelPreloadRecentApps = 18;
        static final int TRANSACTION_clickQsTile = 35;
        static final int TRANSACTION_disable = 3;
        static final int TRANSACTION_dismissKeyboardShortcutsMenu = 20;
        static final int TRANSACTION_handleSystemKey = 36;
        static final int TRANSACTION_hideBiometricDialog = 44;
        static final int TRANSACTION_hideRecentApps = 14;
        static final int TRANSACTION_onBiometricAuthenticated = 41;
        static final int TRANSACTION_onBiometricError = 43;
        static final int TRANSACTION_onBiometricHelp = 42;
        static final int TRANSACTION_onCameraLaunchGestureDetected = 28;
        static final int TRANSACTION_onDisplayReady = 45;
        static final int TRANSACTION_onProposedRotationChanged = 31;
        static final int TRANSACTION_onRecentsAnimationStateChanged = 46;
        static final int TRANSACTION_preloadRecentApps = 17;
        static final int TRANSACTION_remQsTile = 34;
        static final int TRANSACTION_removeIcon = 2;
        static final int TRANSACTION_setIcon = 1;
        static final int TRANSACTION_setImeWindowStatus = 11;
        static final int TRANSACTION_setSystemUiVisibility = 9;
        static final int TRANSACTION_setTopAppHidesStatusBar = 32;
        static final int TRANSACTION_setWindowState = 12;
        static final int TRANSACTION_showAssistDisclosure = 26;
        static final int TRANSACTION_showBiometricDialog = 40;
        static final int TRANSACTION_showGlobalActionsMenu = 30;
        static final int TRANSACTION_showPictureInPictureMenu = 29;
        static final int TRANSACTION_showPinningEnterExitToast = 37;
        static final int TRANSACTION_showPinningEscapeToast = 38;
        static final int TRANSACTION_showRecentApps = 13;
        static final int TRANSACTION_showScreenPinningRequest = 19;
        static final int TRANSACTION_showShutdownUi = 39;
        static final int TRANSACTION_showWirelessChargingAnimation = 8;
        static final int TRANSACTION_startAssist = 27;
        static final int TRANSACTION_toggleKeyboardShortcutsMenu = 21;
        static final int TRANSACTION_togglePanel = 7;
        static final int TRANSACTION_toggleRecentApps = 15;
        static final int TRANSACTION_toggleSplitScreen = 16;
        static final int TRANSACTION_topAppWindowChanged = 10;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IStatusBar asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IStatusBar) {
                return (IStatusBar)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IStatusBar getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 46: {
                    return "onRecentsAnimationStateChanged";
                }
                case 45: {
                    return "onDisplayReady";
                }
                case 44: {
                    return "hideBiometricDialog";
                }
                case 43: {
                    return "onBiometricError";
                }
                case 42: {
                    return "onBiometricHelp";
                }
                case 41: {
                    return "onBiometricAuthenticated";
                }
                case 40: {
                    return "showBiometricDialog";
                }
                case 39: {
                    return "showShutdownUi";
                }
                case 38: {
                    return "showPinningEscapeToast";
                }
                case 37: {
                    return "showPinningEnterExitToast";
                }
                case 36: {
                    return "handleSystemKey";
                }
                case 35: {
                    return "clickQsTile";
                }
                case 34: {
                    return "remQsTile";
                }
                case 33: {
                    return "addQsTile";
                }
                case 32: {
                    return "setTopAppHidesStatusBar";
                }
                case 31: {
                    return "onProposedRotationChanged";
                }
                case 30: {
                    return "showGlobalActionsMenu";
                }
                case 29: {
                    return "showPictureInPictureMenu";
                }
                case 28: {
                    return "onCameraLaunchGestureDetected";
                }
                case 27: {
                    return "startAssist";
                }
                case 26: {
                    return "showAssistDisclosure";
                }
                case 25: {
                    return "appTransitionFinished";
                }
                case 24: {
                    return "appTransitionStarting";
                }
                case 23: {
                    return "appTransitionCancelled";
                }
                case 22: {
                    return "appTransitionPending";
                }
                case 21: {
                    return "toggleKeyboardShortcutsMenu";
                }
                case 20: {
                    return "dismissKeyboardShortcutsMenu";
                }
                case 19: {
                    return "showScreenPinningRequest";
                }
                case 18: {
                    return "cancelPreloadRecentApps";
                }
                case 17: {
                    return "preloadRecentApps";
                }
                case 16: {
                    return "toggleSplitScreen";
                }
                case 15: {
                    return "toggleRecentApps";
                }
                case 14: {
                    return "hideRecentApps";
                }
                case 13: {
                    return "showRecentApps";
                }
                case 12: {
                    return "setWindowState";
                }
                case 11: {
                    return "setImeWindowStatus";
                }
                case 10: {
                    return "topAppWindowChanged";
                }
                case 9: {
                    return "setSystemUiVisibility";
                }
                case 8: {
                    return "showWirelessChargingAnimation";
                }
                case 7: {
                    return "togglePanel";
                }
                case 6: {
                    return "animateCollapsePanels";
                }
                case 5: {
                    return "animateExpandSettingsPanel";
                }
                case 4: {
                    return "animateExpandNotificationsPanel";
                }
                case 3: {
                    return "disable";
                }
                case 2: {
                    return "removeIcon";
                }
                case 1: 
            }
            return "setIcon";
        }

        public static boolean setDefaultImpl(IStatusBar iStatusBar) {
            if (Proxy.sDefaultImpl == null && iStatusBar != null) {
                Proxy.sDefaultImpl = iStatusBar;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.onRecentsAnimationStateChanged(bl9);
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onDisplayReady(((Parcel)object).readInt());
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.hideBiometricDialog();
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onBiometricError(((Parcel)object).readString());
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onBiometricHelp(((Parcel)object).readString());
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl9 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.onBiometricAuthenticated(bl9, ((Parcel)object).readString());
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        IBiometricServiceReceiverInternal iBiometricServiceReceiverInternal = IBiometricServiceReceiverInternal.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        bl9 = ((Parcel)object).readInt() != 0;
                        this.showBiometricDialog((Bundle)object2, iBiometricServiceReceiverInternal, n, bl9, ((Parcel)object).readInt());
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl9 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.showShutdownUi(bl9, ((Parcel)object).readString());
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.showPinningEscapeToast();
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl9 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.showPinningEnterExitToast(bl9);
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.handleSystemKey(((Parcel)object).readInt());
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.clickQsTile((ComponentName)object);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.remQsTile((ComponentName)object);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addQsTile((ComponentName)object);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl9 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.setTopAppHidesStatusBar(bl9);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl9 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.onProposedRotationChanged(n, bl9);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.showGlobalActionsMenu();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.showPictureInPictureMenu();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onCameraLaunchGestureDetected(((Parcel)object).readInt());
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startAssist((Bundle)object);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.showAssistDisclosure();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.appTransitionFinished(((Parcel)object).readInt());
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.appTransitionStarting(((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readLong());
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.appTransitionCancelled(((Parcel)object).readInt());
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.appTransitionPending(((Parcel)object).readInt());
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.toggleKeyboardShortcutsMenu(((Parcel)object).readInt());
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dismissKeyboardShortcutsMenu();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.showScreenPinningRequest(((Parcel)object).readInt());
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelPreloadRecentApps();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.preloadRecentApps();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.toggleSplitScreen();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.toggleRecentApps();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl9 = ((Parcel)object).readInt() != 0;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.hideRecentApps(bl9, bl6);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl9 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.showRecentApps(bl9);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setWindowState(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readStrongBinder();
                        int n3 = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl9 = ((Parcel)object).readInt() != 0;
                        this.setImeWindowStatus(n, (IBinder)object2, n3, n2, bl9);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl9 = bl8;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.topAppWindowChanged(n, bl9);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        int n4 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        int n5 = ((Parcel)object).readInt();
                        int n6 = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        bl9 = ((Parcel)object).readInt() != 0;
                        this.setSystemUiVisibility(n4, n, n5, n6, n2, (Rect)object2, rect, bl9);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.showWirelessChargingAnimation(((Parcel)object).readInt());
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.togglePanel();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.animateCollapsePanels();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.animateExpandSettingsPanel(((Parcel)object).readString());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.animateExpandNotificationsPanel();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disable(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeIcon(((Parcel)object).readString());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readString();
                object = ((Parcel)object).readInt() != 0 ? StatusBarIcon.CREATOR.createFromParcel((Parcel)object) : null;
                this.setIcon((String)object2, (StatusBarIcon)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IStatusBar {
            public static IStatusBar sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addQsTile(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(33, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addQsTile(componentName);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void animateCollapsePanels() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().animateCollapsePanels();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void animateExpandNotificationsPanel() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().animateExpandNotificationsPanel();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void animateExpandSettingsPanel(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().animateExpandSettingsPanel(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void appTransitionCancelled(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(23, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().appTransitionCancelled(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void appTransitionFinished(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(25, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().appTransitionFinished(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void appTransitionPending(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(22, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().appTransitionPending(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void appTransitionStarting(int n, long l, long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    parcel.writeLong(l2);
                    if (!this.mRemote.transact(24, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().appTransitionStarting(n, l, l2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancelPreloadRecentApps() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelPreloadRecentApps();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void clickQsTile(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(35, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clickQsTile(componentName);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void disable(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disable(n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dismissKeyboardShortcutsMenu() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dismissKeyboardShortcutsMenu();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void handleSystemKey(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(36, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleSystemKey(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void hideBiometricDialog() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(44, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hideBiometricDialog();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void hideRecentApps(boolean bl, boolean bl2) throws RemoteException {
                int n;
                Parcel parcel;
                block6 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 0;
                    n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    n = n2;
                    if (!bl2) break block6;
                    n = 1;
                }
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hideRecentApps(bl, bl2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onBiometricAuthenticated(boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(41, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBiometricAuthenticated(bl, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onBiometricError(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(43, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBiometricError(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onBiometricHelp(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(42, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBiometricHelp(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCameraLaunchGestureDetected(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(28, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCameraLaunchGestureDetected(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDisplayReady(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(45, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDisplayReady(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onProposedRotationChanged(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(31, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProposedRotationChanged(n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRecentsAnimationStateChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(46, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRecentsAnimationStateChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void preloadRecentApps() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().preloadRecentApps();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void remQsTile(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(34, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().remQsTile(componentName);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeIcon(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeIcon(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setIcon(String string2, StatusBarIcon statusBarIcon) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (statusBarIcon != null) {
                        parcel.writeInt(1);
                        statusBarIcon.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setIcon(string2, statusBarIcon);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setImeWindowStatus(int n, IBinder iBinder, int n2, int n3, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeStrongBinder(iBinder);
                parcel.writeInt(n2);
                parcel.writeInt(n3);
                int n4 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setImeWindowStatus(n, iBinder, n2, n3, bl);
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
            public void setSystemUiVisibility(int n, int n2, int n3, int n4, int n5, Rect rect, Rect rect2, boolean bl) throws RemoteException {
                void var6_11;
                Parcel parcel;
                block14 : {
                    parcel = Parcel.obtain();
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
                        parcel.writeInt(n4);
                        parcel.writeInt(n5);
                        int n6 = 0;
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
                        if (bl) {
                            n6 = 1;
                        }
                        parcel.writeInt(n6);
                        if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().setSystemUiVisibility(n, n2, n3, n4, n5, rect, rect2, bl);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var6_11;
            }

            @Override
            public void setTopAppHidesStatusBar(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(32, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTopAppHidesStatusBar(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setWindowState(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setWindowState(n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void showAssistDisclosure() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showAssistDisclosure();
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
            public void showBiometricDialog(Bundle bundle, IBiometricServiceReceiverInternal iBiometricServiceReceiverInternal, int n, boolean bl, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n3 = 0;
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iBiometricServiceReceiverInternal != null ? iBiometricServiceReceiverInternal.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (bl) {
                        n3 = 1;
                    }
                    parcel.writeInt(n3);
                    parcel.writeInt(n2);
                    if (this.mRemote.transact(40, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().showBiometricDialog(bundle, iBiometricServiceReceiverInternal, n, bl, n2);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void showGlobalActionsMenu() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(30, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showGlobalActionsMenu();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void showPictureInPictureMenu() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showPictureInPictureMenu();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void showPinningEnterExitToast(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(37, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showPinningEnterExitToast(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void showPinningEscapeToast() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(38, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showPinningEscapeToast();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void showRecentApps(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showRecentApps(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void showScreenPinningRequest(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showScreenPinningRequest(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void showShutdownUi(boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(39, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showShutdownUi(bl, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void showWirelessChargingAnimation(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showWirelessChargingAnimation(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void startAssist(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(27, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startAssist(bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void toggleKeyboardShortcutsMenu(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(21, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().toggleKeyboardShortcutsMenu(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void togglePanel() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().togglePanel();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void toggleRecentApps() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().toggleRecentApps();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void toggleSplitScreen() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().toggleSplitScreen();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void topAppWindowChanged(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().topAppWindowChanged(n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

