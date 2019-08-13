/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.statusbar;

import android.annotation.UnsupportedAppUsage;
import android.app.Notification;
import android.content.ComponentName;
import android.hardware.biometrics.IBiometricServiceReceiverInternal;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.statusbar.RegisterStatusBarResult;

public interface IStatusBarService
extends IInterface {
    public void addTile(ComponentName var1) throws RemoteException;

    public void clearNotificationEffects() throws RemoteException;

    public void clickTile(ComponentName var1) throws RemoteException;

    @UnsupportedAppUsage
    public void collapsePanels() throws RemoteException;

    @UnsupportedAppUsage
    public void disable(int var1, IBinder var2, String var3) throws RemoteException;

    public void disable2(int var1, IBinder var2, String var3) throws RemoteException;

    public void disable2ForUser(int var1, IBinder var2, String var3, int var4) throws RemoteException;

    public void disableForUser(int var1, IBinder var2, String var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public void expandNotificationsPanel() throws RemoteException;

    public void expandSettingsPanel(String var1) throws RemoteException;

    public int[] getDisableFlags(IBinder var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void handleSystemKey(int var1) throws RemoteException;

    public void hideBiometricDialog() throws RemoteException;

    public void onBiometricAuthenticated(boolean var1, String var2) throws RemoteException;

    public void onBiometricError(String var1) throws RemoteException;

    public void onBiometricHelp(String var1) throws RemoteException;

    public void onClearAllNotifications(int var1) throws RemoteException;

    public void onGlobalActionsHidden() throws RemoteException;

    public void onGlobalActionsShown() throws RemoteException;

    public void onNotificationActionClick(String var1, int var2, Notification.Action var3, NotificationVisibility var4, boolean var5) throws RemoteException;

    public void onNotificationBubbleChanged(String var1, boolean var2) throws RemoteException;

    public void onNotificationClear(String var1, String var2, int var3, int var4, String var5, int var6, int var7, NotificationVisibility var8) throws RemoteException;

    public void onNotificationClick(String var1, NotificationVisibility var2) throws RemoteException;

    public void onNotificationDirectReplied(String var1) throws RemoteException;

    public void onNotificationError(String var1, String var2, int var3, int var4, int var5, String var6, int var7) throws RemoteException;

    public void onNotificationExpansionChanged(String var1, boolean var2, boolean var3, int var4) throws RemoteException;

    public void onNotificationSettingsViewed(String var1) throws RemoteException;

    public void onNotificationSmartReplySent(String var1, int var2, CharSequence var3, int var4, boolean var5) throws RemoteException;

    public void onNotificationSmartSuggestionsAdded(String var1, int var2, int var3, boolean var4, boolean var5) throws RemoteException;

    public void onNotificationVisibilityChanged(NotificationVisibility[] var1, NotificationVisibility[] var2) throws RemoteException;

    public void onPanelHidden() throws RemoteException;

    public void onPanelRevealed(boolean var1, int var2) throws RemoteException;

    public void reboot(boolean var1) throws RemoteException;

    public RegisterStatusBarResult registerStatusBar(IStatusBar var1) throws RemoteException;

    public void remTile(ComponentName var1) throws RemoteException;

    @UnsupportedAppUsage
    public void removeIcon(String var1) throws RemoteException;

    public void setIcon(String var1, String var2, int var3, int var4, String var5) throws RemoteException;

    @UnsupportedAppUsage
    public void setIconVisibility(String var1, boolean var2) throws RemoteException;

    public void setImeWindowStatus(int var1, IBinder var2, int var3, int var4, boolean var5) throws RemoteException;

    public void setSystemUiVisibility(int var1, int var2, int var3, String var4) throws RemoteException;

    public void showBiometricDialog(Bundle var1, IBiometricServiceReceiverInternal var2, int var3, boolean var4, int var5) throws RemoteException;

    public void showPinningEnterExitToast(boolean var1) throws RemoteException;

    public void showPinningEscapeToast() throws RemoteException;

    public void shutdown() throws RemoteException;

    public void togglePanel() throws RemoteException;

    public static class Default
    implements IStatusBarService {
        @Override
        public void addTile(ComponentName componentName) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearNotificationEffects() throws RemoteException {
        }

        @Override
        public void clickTile(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void collapsePanels() throws RemoteException {
        }

        @Override
        public void disable(int n, IBinder iBinder, String string2) throws RemoteException {
        }

        @Override
        public void disable2(int n, IBinder iBinder, String string2) throws RemoteException {
        }

        @Override
        public void disable2ForUser(int n, IBinder iBinder, String string2, int n2) throws RemoteException {
        }

        @Override
        public void disableForUser(int n, IBinder iBinder, String string2, int n2) throws RemoteException {
        }

        @Override
        public void expandNotificationsPanel() throws RemoteException {
        }

        @Override
        public void expandSettingsPanel(String string2) throws RemoteException {
        }

        @Override
        public int[] getDisableFlags(IBinder iBinder, int n) throws RemoteException {
            return null;
        }

        @Override
        public void handleSystemKey(int n) throws RemoteException {
        }

        @Override
        public void hideBiometricDialog() throws RemoteException {
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
        public void onClearAllNotifications(int n) throws RemoteException {
        }

        @Override
        public void onGlobalActionsHidden() throws RemoteException {
        }

        @Override
        public void onGlobalActionsShown() throws RemoteException {
        }

        @Override
        public void onNotificationActionClick(String string2, int n, Notification.Action action, NotificationVisibility notificationVisibility, boolean bl) throws RemoteException {
        }

        @Override
        public void onNotificationBubbleChanged(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void onNotificationClear(String string2, String string3, int n, int n2, String string4, int n3, int n4, NotificationVisibility notificationVisibility) throws RemoteException {
        }

        @Override
        public void onNotificationClick(String string2, NotificationVisibility notificationVisibility) throws RemoteException {
        }

        @Override
        public void onNotificationDirectReplied(String string2) throws RemoteException {
        }

        @Override
        public void onNotificationError(String string2, String string3, int n, int n2, int n3, String string4, int n4) throws RemoteException {
        }

        @Override
        public void onNotificationExpansionChanged(String string2, boolean bl, boolean bl2, int n) throws RemoteException {
        }

        @Override
        public void onNotificationSettingsViewed(String string2) throws RemoteException {
        }

        @Override
        public void onNotificationSmartReplySent(String string2, int n, CharSequence charSequence, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public void onNotificationSmartSuggestionsAdded(String string2, int n, int n2, boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void onNotificationVisibilityChanged(NotificationVisibility[] arrnotificationVisibility, NotificationVisibility[] arrnotificationVisibility2) throws RemoteException {
        }

        @Override
        public void onPanelHidden() throws RemoteException {
        }

        @Override
        public void onPanelRevealed(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void reboot(boolean bl) throws RemoteException {
        }

        @Override
        public RegisterStatusBarResult registerStatusBar(IStatusBar iStatusBar) throws RemoteException {
            return null;
        }

        @Override
        public void remTile(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void removeIcon(String string2) throws RemoteException {
        }

        @Override
        public void setIcon(String string2, String string3, int n, int n2, String string4) throws RemoteException {
        }

        @Override
        public void setIconVisibility(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void setImeWindowStatus(int n, IBinder iBinder, int n2, int n3, boolean bl) throws RemoteException {
        }

        @Override
        public void setSystemUiVisibility(int n, int n2, int n3, String string2) throws RemoteException {
        }

        @Override
        public void showBiometricDialog(Bundle bundle, IBiometricServiceReceiverInternal iBiometricServiceReceiverInternal, int n, boolean bl, int n2) throws RemoteException {
        }

        @Override
        public void showPinningEnterExitToast(boolean bl) throws RemoteException {
        }

        @Override
        public void showPinningEscapeToast() throws RemoteException {
        }

        @Override
        public void shutdown() throws RemoteException {
        }

        @Override
        public void togglePanel() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IStatusBarService {
        private static final String DESCRIPTOR = "com.android.internal.statusbar.IStatusBarService";
        static final int TRANSACTION_addTile = 35;
        static final int TRANSACTION_clearNotificationEffects = 17;
        static final int TRANSACTION_clickTile = 37;
        static final int TRANSACTION_collapsePanels = 2;
        static final int TRANSACTION_disable = 4;
        static final int TRANSACTION_disable2 = 6;
        static final int TRANSACTION_disable2ForUser = 7;
        static final int TRANSACTION_disableForUser = 5;
        static final int TRANSACTION_expandNotificationsPanel = 1;
        static final int TRANSACTION_expandSettingsPanel = 13;
        static final int TRANSACTION_getDisableFlags = 8;
        static final int TRANSACTION_handleSystemKey = 38;
        static final int TRANSACTION_hideBiometricDialog = 45;
        static final int TRANSACTION_onBiometricAuthenticated = 42;
        static final int TRANSACTION_onBiometricError = 44;
        static final int TRANSACTION_onBiometricHelp = 43;
        static final int TRANSACTION_onClearAllNotifications = 21;
        static final int TRANSACTION_onGlobalActionsHidden = 32;
        static final int TRANSACTION_onGlobalActionsShown = 31;
        static final int TRANSACTION_onNotificationActionClick = 19;
        static final int TRANSACTION_onNotificationBubbleChanged = 30;
        static final int TRANSACTION_onNotificationClear = 22;
        static final int TRANSACTION_onNotificationClick = 18;
        static final int TRANSACTION_onNotificationDirectReplied = 25;
        static final int TRANSACTION_onNotificationError = 20;
        static final int TRANSACTION_onNotificationExpansionChanged = 24;
        static final int TRANSACTION_onNotificationSettingsViewed = 28;
        static final int TRANSACTION_onNotificationSmartReplySent = 27;
        static final int TRANSACTION_onNotificationSmartSuggestionsAdded = 26;
        static final int TRANSACTION_onNotificationVisibilityChanged = 23;
        static final int TRANSACTION_onPanelHidden = 16;
        static final int TRANSACTION_onPanelRevealed = 15;
        static final int TRANSACTION_reboot = 34;
        static final int TRANSACTION_registerStatusBar = 14;
        static final int TRANSACTION_remTile = 36;
        static final int TRANSACTION_removeIcon = 11;
        static final int TRANSACTION_setIcon = 9;
        static final int TRANSACTION_setIconVisibility = 10;
        static final int TRANSACTION_setImeWindowStatus = 12;
        static final int TRANSACTION_setSystemUiVisibility = 29;
        static final int TRANSACTION_showBiometricDialog = 41;
        static final int TRANSACTION_showPinningEnterExitToast = 39;
        static final int TRANSACTION_showPinningEscapeToast = 40;
        static final int TRANSACTION_shutdown = 33;
        static final int TRANSACTION_togglePanel = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IStatusBarService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IStatusBarService) {
                return (IStatusBarService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IStatusBarService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 45: {
                    return "hideBiometricDialog";
                }
                case 44: {
                    return "onBiometricError";
                }
                case 43: {
                    return "onBiometricHelp";
                }
                case 42: {
                    return "onBiometricAuthenticated";
                }
                case 41: {
                    return "showBiometricDialog";
                }
                case 40: {
                    return "showPinningEscapeToast";
                }
                case 39: {
                    return "showPinningEnterExitToast";
                }
                case 38: {
                    return "handleSystemKey";
                }
                case 37: {
                    return "clickTile";
                }
                case 36: {
                    return "remTile";
                }
                case 35: {
                    return "addTile";
                }
                case 34: {
                    return "reboot";
                }
                case 33: {
                    return "shutdown";
                }
                case 32: {
                    return "onGlobalActionsHidden";
                }
                case 31: {
                    return "onGlobalActionsShown";
                }
                case 30: {
                    return "onNotificationBubbleChanged";
                }
                case 29: {
                    return "setSystemUiVisibility";
                }
                case 28: {
                    return "onNotificationSettingsViewed";
                }
                case 27: {
                    return "onNotificationSmartReplySent";
                }
                case 26: {
                    return "onNotificationSmartSuggestionsAdded";
                }
                case 25: {
                    return "onNotificationDirectReplied";
                }
                case 24: {
                    return "onNotificationExpansionChanged";
                }
                case 23: {
                    return "onNotificationVisibilityChanged";
                }
                case 22: {
                    return "onNotificationClear";
                }
                case 21: {
                    return "onClearAllNotifications";
                }
                case 20: {
                    return "onNotificationError";
                }
                case 19: {
                    return "onNotificationActionClick";
                }
                case 18: {
                    return "onNotificationClick";
                }
                case 17: {
                    return "clearNotificationEffects";
                }
                case 16: {
                    return "onPanelHidden";
                }
                case 15: {
                    return "onPanelRevealed";
                }
                case 14: {
                    return "registerStatusBar";
                }
                case 13: {
                    return "expandSettingsPanel";
                }
                case 12: {
                    return "setImeWindowStatus";
                }
                case 11: {
                    return "removeIcon";
                }
                case 10: {
                    return "setIconVisibility";
                }
                case 9: {
                    return "setIcon";
                }
                case 8: {
                    return "getDisableFlags";
                }
                case 7: {
                    return "disable2ForUser";
                }
                case 6: {
                    return "disable2";
                }
                case 5: {
                    return "disableForUser";
                }
                case 4: {
                    return "disable";
                }
                case 3: {
                    return "togglePanel";
                }
                case 2: {
                    return "collapsePanels";
                }
                case 1: 
            }
            return "expandNotificationsPanel";
        }

        public static boolean setDefaultImpl(IStatusBarService iStatusBarService) {
            if (Proxy.sDefaultImpl == null && iStatusBarService != null) {
                Proxy.sDefaultImpl = iStatusBarService;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.hideBiometricDialog();
                        parcel.writeNoException();
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onBiometricError(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onBiometricHelp(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl7 = true;
                        }
                        this.onBiometricAuthenticated(bl7, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        IBiometricServiceReceiverInternal iBiometricServiceReceiverInternal = IBiometricServiceReceiverInternal.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        bl7 = ((Parcel)object).readInt() != 0;
                        this.showBiometricDialog(bundle, iBiometricServiceReceiverInternal, n, bl7, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.showPinningEscapeToast();
                        parcel.writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl7 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl7 = true;
                        }
                        this.showPinningEnterExitToast(bl7);
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.handleSystemKey(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.clickTile((ComponentName)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.remTile((ComponentName)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addTile((ComponentName)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl7 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl7 = true;
                        }
                        this.reboot(bl7);
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.shutdown();
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onGlobalActionsHidden();
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onGlobalActionsShown();
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        bl7 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl7 = true;
                        }
                        this.onNotificationBubbleChanged(string2, bl7);
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setSystemUiVisibility(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNotificationSettingsViewed(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        CharSequence charSequence = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl7 = ((Parcel)object).readInt() != 0;
                        this.onNotificationSmartReplySent(string3, n2, charSequence, n, bl7);
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl7 = ((Parcel)object).readInt() != 0;
                        bl4 = ((Parcel)object).readInt() != 0;
                        this.onNotificationSmartSuggestionsAdded(string4, n, n2, bl7, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNotificationDirectReplied(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        bl7 = ((Parcel)object).readInt() != 0;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.onNotificationExpansionChanged(string5, bl7, bl4, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNotificationVisibilityChanged(((Parcel)object).createTypedArray(NotificationVisibility.CREATOR), ((Parcel)object).createTypedArray(NotificationVisibility.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        String string7 = ((Parcel)object).readString();
                        int n3 = ((Parcel)object).readInt();
                        int n4 = ((Parcel)object).readInt();
                        String string8 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? NotificationVisibility.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onNotificationClear(string6, string7, n3, n4, string8, n, n2, (NotificationVisibility)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onClearAllNotifications(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onNotificationError(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string9 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        Notification.Action action = ((Parcel)object).readInt() != 0 ? Notification.Action.CREATOR.createFromParcel((Parcel)object) : null;
                        NotificationVisibility notificationVisibility = ((Parcel)object).readInt() != 0 ? NotificationVisibility.CREATOR.createFromParcel((Parcel)object) : null;
                        bl7 = ((Parcel)object).readInt() != 0;
                        this.onNotificationActionClick(string9, n, action, notificationVisibility, bl7);
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string10 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? NotificationVisibility.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onNotificationClick(string10, (NotificationVisibility)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearNotificationEffects();
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onPanelHidden();
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl7 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl7 = true;
                        }
                        this.onPanelRevealed(bl7, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.registerStatusBar(IStatusBar.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((RegisterStatusBarResult)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.expandSettingsPanel(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        int n5 = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl7 = ((Parcel)object).readInt() != 0;
                        this.setImeWindowStatus(n, iBinder, n5, n2, bl7);
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeIcon(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string11 = ((Parcel)object).readString();
                        bl7 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl7 = true;
                        }
                        this.setIconVisibility(string11, bl7);
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setIcon(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDisableFlags(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disable2ForUser(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disable2(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableForUser(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disable(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.togglePanel();
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.collapsePanels();
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.expandNotificationsPanel();
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IStatusBarService {
            public static IStatusBarService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addTile(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addTile(componentName);
                        return;
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
            public void clearNotificationEffects() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearNotificationEffects();
                        return;
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
            public void clickTile(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clickTile(componentName);
                        return;
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
            public void collapsePanels() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().collapsePanels();
                        return;
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
            public void disable(int n, IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disable(n, iBinder, string2);
                        return;
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
            public void disable2(int n, IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disable2(n, iBinder, string2);
                        return;
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
            public void disable2ForUser(int n, IBinder iBinder, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disable2ForUser(n, iBinder, string2, n2);
                        return;
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
            public void disableForUser(int n, IBinder iBinder, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableForUser(n, iBinder, string2, n2);
                        return;
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
            public void expandNotificationsPanel() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().expandNotificationsPanel();
                        return;
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
            public void expandSettingsPanel(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().expandSettingsPanel(string2);
                        return;
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
            public int[] getDisableFlags(IBinder arrn, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder((IBinder)arrn);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrn = Stub.getDefaultImpl().getDisableFlags((IBinder)arrn, n);
                        return arrn;
                    }
                    parcel2.readException();
                    arrn = parcel2.createIntArray();
                    return arrn;
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
            public void handleSystemKey(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleSystemKey(n);
                        return;
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
            public void hideBiometricDialog() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hideBiometricDialog();
                        return;
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
            public void onBiometricAuthenticated(boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBiometricAuthenticated(bl, string2);
                        return;
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
            public void onBiometricError(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBiometricError(string2);
                        return;
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
            public void onBiometricHelp(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onBiometricHelp(string2);
                        return;
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
            public void onClearAllNotifications(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onClearAllNotifications(n);
                        return;
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
            public void onGlobalActionsHidden() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGlobalActionsHidden();
                        return;
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
            public void onGlobalActionsShown() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGlobalActionsShown();
                        return;
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
            public void onNotificationActionClick(String string2, int n, Notification.Action action, NotificationVisibility notificationVisibility, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    int n2 = 1;
                    if (action != null) {
                        parcel.writeInt(1);
                        action.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (notificationVisibility != null) {
                        parcel.writeInt(1);
                        notificationVisibility.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationActionClick(string2, n, action, notificationVisibility, bl);
                        return;
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
            public void onNotificationBubbleChanged(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationBubbleChanged(string2, bl);
                        return;
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
            public void onNotificationClear(String string2, String string3, int n, int n2, String string4, int n3, int n4, NotificationVisibility notificationVisibility) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_6;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        parcel2.writeString(string4);
                        parcel2.writeInt(n3);
                        parcel2.writeInt(n4);
                        if (notificationVisibility != null) {
                            parcel2.writeInt(1);
                            notificationVisibility.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (!this.mRemote.transact(22, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onNotificationClear(string2, string3, n, n2, string4, n3, n4, notificationVisibility);
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
                throw var1_6;
            }

            @Override
            public void onNotificationClick(String string2, NotificationVisibility notificationVisibility) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (notificationVisibility != null) {
                        parcel.writeInt(1);
                        notificationVisibility.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationClick(string2, notificationVisibility);
                        return;
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
            public void onNotificationDirectReplied(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationDirectReplied(string2);
                        return;
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
            public void onNotificationError(String string2, String string3, int n, int n2, int n3, String string4, int n4) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_8;
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
                        parcel.writeString(string3);
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
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n3);
                        parcel.writeString(string4);
                        parcel.writeInt(n4);
                        if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onNotificationError(string2, string3, n, n2, n3, string4, n4);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_8;
            }

            @Override
            public void onNotificationExpansionChanged(String string2, boolean bl, boolean bl2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n2 = 1;
                int n3 = bl ? 1 : 0;
                parcel.writeInt(n3);
                n3 = bl2 ? n2 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationExpansionChanged(string2, bl, bl2, n);
                        return;
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
            public void onNotificationSettingsViewed(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationSettingsViewed(string2);
                        return;
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
            public void onNotificationSmartReplySent(String string2, int n, CharSequence charSequence, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    int n3 = 1;
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    if (!bl) {
                        n3 = 0;
                    }
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationSmartReplySent(string2, n, charSequence, n2, bl);
                        return;
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
            public void onNotificationSmartSuggestionsAdded(String string2, int n, int n2, boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = 1;
                int n4 = bl ? 1 : 0;
                parcel.writeInt(n4);
                n4 = bl2 ? n3 : 0;
                try {
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationSmartSuggestionsAdded(string2, n, n2, bl, bl2);
                        return;
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
            public void onNotificationVisibilityChanged(NotificationVisibility[] arrnotificationVisibility, NotificationVisibility[] arrnotificationVisibility2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrnotificationVisibility, 0);
                    parcel.writeTypedArray((Parcelable[])arrnotificationVisibility2, 0);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNotificationVisibilityChanged(arrnotificationVisibility, arrnotificationVisibility2);
                        return;
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
            public void onPanelHidden() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPanelHidden();
                        return;
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
            public void onPanelRevealed(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPanelRevealed(bl, n);
                        return;
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
            public void reboot(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reboot(bl);
                        return;
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
            public RegisterStatusBarResult registerStatusBar(IStatusBar object) throws RemoteException {
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
                    if (this.mRemote.transact(14, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block7;
                    object = Stub.getDefaultImpl().registerStatusBar((IStatusBar)object);
                    parcel2.recycle();
                    parcel.recycle();
                    return object;
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? RegisterStatusBarResult.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public void remTile(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().remTile(componentName);
                        return;
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
            public void removeIcon(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeIcon(string2);
                        return;
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
            public void setIcon(String string2, String string3, int n, int n2, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setIcon(string2, string3, n, n2, string4);
                        return;
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
            public void setIconVisibility(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setIconVisibility(string2, bl);
                        return;
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
            public void setImeWindowStatus(int n, IBinder iBinder, int n2, int n3, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeStrongBinder(iBinder);
                parcel.writeInt(n2);
                parcel.writeInt(n3);
                int n4 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setImeWindowStatus(n, iBinder, n2, n3, bl);
                        return;
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
            public void setSystemUiVisibility(int n, int n2, int n3, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSystemUiVisibility(n, n2, n3, string2);
                        return;
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
            public void showBiometricDialog(Bundle bundle, IBiometricServiceReceiverInternal iBiometricServiceReceiverInternal, int n, boolean bl, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n3 = 1;
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iBiometricServiceReceiverInternal != null ? iBiometricServiceReceiverInternal.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!bl) {
                        n3 = 0;
                    }
                    parcel.writeInt(n3);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showBiometricDialog(bundle, iBiometricServiceReceiverInternal, n, bl, n2);
                        return;
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
            public void showPinningEnterExitToast(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showPinningEnterExitToast(bl);
                        return;
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
            public void showPinningEscapeToast() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showPinningEscapeToast();
                        return;
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
            public void shutdown() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().shutdown();
                        return;
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
            public void togglePanel() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().togglePanel();
                        return;
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

