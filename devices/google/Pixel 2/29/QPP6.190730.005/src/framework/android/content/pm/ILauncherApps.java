/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IOnAppsChangedListener;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInstaller;
import android.content.pm.ParceledListSlice;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.ArrayList;
import java.util.List;

public interface ILauncherApps
extends IInterface {
    public void addOnAppsChangedListener(String var1, IOnAppsChangedListener var2) throws RemoteException;

    public ParceledListSlice getAllSessions(String var1) throws RemoteException;

    public LauncherApps.AppUsageLimit getAppUsageLimit(String var1, String var2, UserHandle var3) throws RemoteException;

    public ApplicationInfo getApplicationInfo(String var1, String var2, int var3, UserHandle var4) throws RemoteException;

    public ParceledListSlice getLauncherActivities(String var1, String var2, UserHandle var3) throws RemoteException;

    public ParceledListSlice getShortcutConfigActivities(String var1, String var2, UserHandle var3) throws RemoteException;

    public IntentSender getShortcutConfigActivityIntent(String var1, ComponentName var2, UserHandle var3) throws RemoteException;

    public ParcelFileDescriptor getShortcutIconFd(String var1, String var2, String var3, int var4) throws RemoteException;

    public int getShortcutIconResId(String var1, String var2, String var3, int var4) throws RemoteException;

    public ParceledListSlice getShortcuts(String var1, long var2, String var4, List var5, ComponentName var6, int var7, UserHandle var8) throws RemoteException;

    public Bundle getSuspendedPackageLauncherExtras(String var1, UserHandle var2) throws RemoteException;

    public boolean hasShortcutHostPermission(String var1) throws RemoteException;

    public boolean isActivityEnabled(String var1, ComponentName var2, UserHandle var3) throws RemoteException;

    public boolean isPackageEnabled(String var1, String var2, UserHandle var3) throws RemoteException;

    public void pinShortcuts(String var1, String var2, List<String> var3, UserHandle var4) throws RemoteException;

    public void registerPackageInstallerCallback(String var1, IPackageInstallerCallback var2) throws RemoteException;

    public void removeOnAppsChangedListener(IOnAppsChangedListener var1) throws RemoteException;

    public ActivityInfo resolveActivity(String var1, ComponentName var2, UserHandle var3) throws RemoteException;

    public boolean shouldHideFromSuggestions(String var1, UserHandle var2) throws RemoteException;

    public void showAppDetailsAsUser(IApplicationThread var1, String var2, ComponentName var3, Rect var4, Bundle var5, UserHandle var6) throws RemoteException;

    public void startActivityAsUser(IApplicationThread var1, String var2, ComponentName var3, Rect var4, Bundle var5, UserHandle var6) throws RemoteException;

    public void startSessionDetailsActivityAsUser(IApplicationThread var1, String var2, PackageInstaller.SessionInfo var3, Rect var4, Bundle var5, UserHandle var6) throws RemoteException;

    public boolean startShortcut(String var1, String var2, String var3, Rect var4, Bundle var5, int var6) throws RemoteException;

    public static class Default
    implements ILauncherApps {
        @Override
        public void addOnAppsChangedListener(String string2, IOnAppsChangedListener iOnAppsChangedListener) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public ParceledListSlice getAllSessions(String string2) throws RemoteException {
            return null;
        }

        @Override
        public LauncherApps.AppUsageLimit getAppUsageLimit(String string2, String string3, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override
        public ApplicationInfo getApplicationInfo(String string2, String string3, int n, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getLauncherActivities(String string2, String string3, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getShortcutConfigActivities(String string2, String string3, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override
        public IntentSender getShortcutConfigActivityIntent(String string2, ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override
        public ParcelFileDescriptor getShortcutIconFd(String string2, String string3, String string4, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getShortcutIconResId(String string2, String string3, String string4, int n) throws RemoteException {
            return 0;
        }

        @Override
        public ParceledListSlice getShortcuts(String string2, long l, String string3, List list, ComponentName componentName, int n, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override
        public Bundle getSuspendedPackageLauncherExtras(String string2, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override
        public boolean hasShortcutHostPermission(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isActivityEnabled(String string2, ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override
        public boolean isPackageEnabled(String string2, String string3, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override
        public void pinShortcuts(String string2, String string3, List<String> list, UserHandle userHandle) throws RemoteException {
        }

        @Override
        public void registerPackageInstallerCallback(String string2, IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException {
        }

        @Override
        public void removeOnAppsChangedListener(IOnAppsChangedListener iOnAppsChangedListener) throws RemoteException {
        }

        @Override
        public ActivityInfo resolveActivity(String string2, ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override
        public boolean shouldHideFromSuggestions(String string2, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override
        public void showAppDetailsAsUser(IApplicationThread iApplicationThread, String string2, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException {
        }

        @Override
        public void startActivityAsUser(IApplicationThread iApplicationThread, String string2, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException {
        }

        @Override
        public void startSessionDetailsActivityAsUser(IApplicationThread iApplicationThread, String string2, PackageInstaller.SessionInfo sessionInfo, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException {
        }

        @Override
        public boolean startShortcut(String string2, String string3, String string4, Rect rect, Bundle bundle, int n) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILauncherApps {
        private static final String DESCRIPTOR = "android.content.pm.ILauncherApps";
        static final int TRANSACTION_addOnAppsChangedListener = 1;
        static final int TRANSACTION_getAllSessions = 23;
        static final int TRANSACTION_getAppUsageLimit = 12;
        static final int TRANSACTION_getApplicationInfo = 11;
        static final int TRANSACTION_getLauncherActivities = 3;
        static final int TRANSACTION_getShortcutConfigActivities = 20;
        static final int TRANSACTION_getShortcutConfigActivityIntent = 21;
        static final int TRANSACTION_getShortcutIconFd = 17;
        static final int TRANSACTION_getShortcutIconResId = 16;
        static final int TRANSACTION_getShortcuts = 13;
        static final int TRANSACTION_getSuspendedPackageLauncherExtras = 9;
        static final int TRANSACTION_hasShortcutHostPermission = 18;
        static final int TRANSACTION_isActivityEnabled = 10;
        static final int TRANSACTION_isPackageEnabled = 8;
        static final int TRANSACTION_pinShortcuts = 14;
        static final int TRANSACTION_registerPackageInstallerCallback = 22;
        static final int TRANSACTION_removeOnAppsChangedListener = 2;
        static final int TRANSACTION_resolveActivity = 4;
        static final int TRANSACTION_shouldHideFromSuggestions = 19;
        static final int TRANSACTION_showAppDetailsAsUser = 7;
        static final int TRANSACTION_startActivityAsUser = 6;
        static final int TRANSACTION_startSessionDetailsActivityAsUser = 5;
        static final int TRANSACTION_startShortcut = 15;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILauncherApps asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILauncherApps) {
                return (ILauncherApps)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILauncherApps getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 23: {
                    return "getAllSessions";
                }
                case 22: {
                    return "registerPackageInstallerCallback";
                }
                case 21: {
                    return "getShortcutConfigActivityIntent";
                }
                case 20: {
                    return "getShortcutConfigActivities";
                }
                case 19: {
                    return "shouldHideFromSuggestions";
                }
                case 18: {
                    return "hasShortcutHostPermission";
                }
                case 17: {
                    return "getShortcutIconFd";
                }
                case 16: {
                    return "getShortcutIconResId";
                }
                case 15: {
                    return "startShortcut";
                }
                case 14: {
                    return "pinShortcuts";
                }
                case 13: {
                    return "getShortcuts";
                }
                case 12: {
                    return "getAppUsageLimit";
                }
                case 11: {
                    return "getApplicationInfo";
                }
                case 10: {
                    return "isActivityEnabled";
                }
                case 9: {
                    return "getSuspendedPackageLauncherExtras";
                }
                case 8: {
                    return "isPackageEnabled";
                }
                case 7: {
                    return "showAppDetailsAsUser";
                }
                case 6: {
                    return "startActivityAsUser";
                }
                case 5: {
                    return "startSessionDetailsActivityAsUser";
                }
                case 4: {
                    return "resolveActivity";
                }
                case 3: {
                    return "getLauncherActivities";
                }
                case 2: {
                    return "removeOnAppsChangedListener";
                }
                case 1: 
            }
            return "addOnAppsChangedListener";
        }

        public static boolean setDefaultImpl(ILauncherApps iLauncherApps) {
            if (Proxy.sDefaultImpl == null && iLauncherApps != null) {
                Proxy.sDefaultImpl = iLauncherApps;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllSessions(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerPackageInstallerCallback(((Parcel)object).readString(), IPackageInstallerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getShortcutConfigActivityIntent(string2, componentName, (UserHandle)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((IntentSender)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        String string4 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getShortcutConfigActivities(string3, string4, (UserHandle)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.shouldHideFromSuggestions(string5, (UserHandle)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasShortcutHostPermission(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getShortcutIconFd(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getShortcutIconResId(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        String string7 = ((Parcel)object).readString();
                        String string8 = ((Parcel)object).readString();
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startShortcut(string6, string7, string8, rect, bundle, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string9 = ((Parcel)object).readString();
                        String string10 = ((Parcel)object).readString();
                        ArrayList<String> arrayList = ((Parcel)object).createStringArrayList();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.pinShortcuts(string9, string10, arrayList, (UserHandle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string11 = ((Parcel)object).readString();
                        long l = ((Parcel)object).readLong();
                        String string12 = ((Parcel)object).readString();
                        ArrayList arrayList = ((Parcel)object).readArrayList(this.getClass().getClassLoader());
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getShortcuts(string11, l, string12, arrayList, componentName, n, (UserHandle)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string13 = ((Parcel)object).readString();
                        String string14 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getAppUsageLimit(string13, string14, (UserHandle)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((LauncherApps.AppUsageLimit)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string15 = ((Parcel)object).readString();
                        String string16 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getApplicationInfo(string15, string16, n, (UserHandle)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ApplicationInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string17 = ((Parcel)object).readString();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isActivityEnabled(string17, componentName, (UserHandle)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string18 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getSuspendedPackageLauncherExtras(string18, (UserHandle)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string19 = ((Parcel)object).readString();
                        String string20 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isPackageEnabled(string19, string20, (UserHandle)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string21 = ((Parcel)object).readString();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.showAppDetailsAsUser(iApplicationThread, string21, componentName, rect, bundle, (UserHandle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string22 = ((Parcel)object).readString();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startActivityAsUser(iApplicationThread, string22, componentName, rect, bundle, (UserHandle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(((Parcel)object).readStrongBinder());
                        String string23 = ((Parcel)object).readString();
                        PackageInstaller.SessionInfo sessionInfo = ((Parcel)object).readInt() != 0 ? PackageInstaller.SessionInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startSessionDetailsActivityAsUser(iApplicationThread, string23, sessionInfo, rect, bundle, (UserHandle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string24 = ((Parcel)object).readString();
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.resolveActivity(string24, componentName, (UserHandle)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ActivityInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string25 = ((Parcel)object).readString();
                        String string26 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getLauncherActivities(string25, string26, (UserHandle)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeOnAppsChangedListener(IOnAppsChangedListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.addOnAppsChangedListener(((Parcel)object).readString(), IOnAppsChangedListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ILauncherApps {
            public static ILauncherApps sDefaultImpl;
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
            public void addOnAppsChangedListener(String string2, IOnAppsChangedListener iOnAppsChangedListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iOnAppsChangedListener != null ? iOnAppsChangedListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addOnAppsChangedListener(string2, iOnAppsChangedListener);
                        return;
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
            public ParceledListSlice getAllSessions(String parceledListSlice) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        if (this.mRemote.transact(23, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getAllSessions((String)((Object)parceledListSlice));
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public LauncherApps.AppUsageLimit getAppUsageLimit(String object, String string2, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    parcel.writeString(string2);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getAppUsageLimit((String)object, string2, userHandle);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? LauncherApps.AppUsageLimit.CREATOR.createFromParcel(parcel2) : null;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ApplicationInfo getApplicationInfo(String object, String string2, int n, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getApplicationInfo((String)object, string2, n, userHandle);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? ApplicationInfo.CREATOR.createFromParcel(parcel2) : null;
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParceledListSlice getLauncherActivities(String parceledListSlice, String string2, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)parceledListSlice));
                    parcel.writeString(string2);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        parceledListSlice = Stub.getDefaultImpl().getLauncherActivities((String)((Object)parceledListSlice), string2, userHandle);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParceledListSlice getShortcutConfigActivities(String parceledListSlice, String string2, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)parceledListSlice));
                    parcel.writeString(string2);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        parceledListSlice = Stub.getDefaultImpl().getShortcutConfigActivities((String)((Object)parceledListSlice), string2, userHandle);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public IntentSender getShortcutConfigActivityIntent(String object, ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getShortcutConfigActivityIntent((String)object, componentName, userHandle);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? IntentSender.CREATOR.createFromParcel(parcel2) : null;
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

            @Override
            public ParcelFileDescriptor getShortcutIconFd(String object, String string2, String string3, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(17, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getShortcutIconFd((String)object, string2, string3, n);
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
                object = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public int getShortcutIconResId(String string2, String string3, String string4, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getShortcutIconResId(string2, string3, string4, n);
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
            public ParceledListSlice getShortcuts(String parceledListSlice, long l, String string2, List list, ComponentName componentName, int n, UserHandle userHandle) throws RemoteException {
                Parcel parcel2;
                Parcel parcel;
                void var1_5;
                block12 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString((String)((Object)parceledListSlice));
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeString(string2);
                        parcel.writeList(list);
                        if (componentName != null) {
                            parcel.writeInt(1);
                            componentName.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeInt(n);
                        if (userHandle != null) {
                            parcel.writeInt(1);
                            userHandle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            parceledListSlice = Stub.getDefaultImpl().getShortcuts((String)((Object)parceledListSlice), l, string2, list, componentName, n, userHandle);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Bundle getSuspendedPackageLauncherExtras(String object, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getSuspendedPackageLauncherExtras((String)object, userHandle);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel2) : null;
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

            @Override
            public boolean hasShortcutHostPermission(String string2) throws RemoteException {
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
                    bl = Stub.getDefaultImpl().hasShortcutHostPermission(string2);
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
            public boolean isActivityEnabled(String string2, ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    boolean bl = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isActivityEnabled(string2, componentName, userHandle);
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
            public boolean isPackageEnabled(String string2, String string3, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    boolean bl = true;
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isPackageEnabled(string2, string3, userHandle);
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
            public void pinShortcuts(String string2, String string3, List<String> list, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeStringList(list);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pinShortcuts(string2, string3, list, userHandle);
                        return;
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
            public void registerPackageInstallerCallback(String string2, IPackageInstallerCallback iPackageInstallerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iPackageInstallerCallback != null ? iPackageInstallerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerPackageInstallerCallback(string2, iPackageInstallerCallback);
                        return;
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
            public void removeOnAppsChangedListener(IOnAppsChangedListener iOnAppsChangedListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iOnAppsChangedListener != null ? iOnAppsChangedListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeOnAppsChangedListener(iOnAppsChangedListener);
                        return;
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
            public ActivityInfo resolveActivity(String object, ComponentName componentName, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().resolveActivity((String)object, componentName, userHandle);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? ActivityInfo.CREATOR.createFromParcel(parcel2) : null;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean shouldHideFromSuggestions(String string2, UserHandle userHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    boolean bl = true;
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().shouldHideFromSuggestions(string2, userHandle);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void showAppDetailsAsUser(IApplicationThread iApplicationThread, String string2, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_5;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                        try {
                            parcel2.writeString(string2);
                            if (componentName != null) {
                                parcel2.writeInt(1);
                                componentName.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (rect != null) {
                                parcel2.writeInt(1);
                                rect.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (bundle != null) {
                                parcel2.writeInt(1);
                                bundle.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (userHandle != null) {
                                parcel2.writeInt(1);
                                userHandle.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(7, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().showAppDetailsAsUser(iApplicationThread, string2, componentName, rect, bundle, userHandle);
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
            public void startActivityAsUser(IApplicationThread iApplicationThread, String string2, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_5;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                        try {
                            parcel2.writeString(string2);
                            if (componentName != null) {
                                parcel2.writeInt(1);
                                componentName.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (rect != null) {
                                parcel2.writeInt(1);
                                rect.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (bundle != null) {
                                parcel2.writeInt(1);
                                bundle.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (userHandle != null) {
                                parcel2.writeInt(1);
                                userHandle.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(6, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().startActivityAsUser(iApplicationThread, string2, componentName, rect, bundle, userHandle);
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
            public void startSessionDetailsActivityAsUser(IApplicationThread iApplicationThread, String string2, PackageInstaller.SessionInfo sessionInfo, Rect rect, Bundle bundle, UserHandle userHandle) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_5;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        IBinder iBinder = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                        try {
                            parcel2.writeString(string2);
                            if (sessionInfo != null) {
                                parcel2.writeInt(1);
                                sessionInfo.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (rect != null) {
                                parcel2.writeInt(1);
                                rect.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (bundle != null) {
                                parcel2.writeInt(1);
                                bundle.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (userHandle != null) {
                                parcel2.writeInt(1);
                                userHandle.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(5, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().startSessionDetailsActivityAsUser(iApplicationThread, string2, sessionInfo, rect, bundle, userHandle);
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
            public boolean startShortcut(String string2, String string3, String string4, Rect rect, Bundle bundle, int n) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block17 : {
                    boolean bl;
                    block16 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block17;
                        }
                        try {
                            parcel2.writeString(string3);
                        }
                        catch (Throwable throwable) {
                            break block17;
                        }
                        try {
                            parcel2.writeString(string4);
                            bl = true;
                            if (rect != null) {
                                parcel2.writeInt(1);
                                rect.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (bundle != null) {
                                parcel2.writeInt(1);
                                bundle.writeToParcel(parcel2, 0);
                                break block16;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeInt(n);
                        if (!this.mRemote.transact(15, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().startShortcut(string2, string3, string4, rect, bundle, n);
                            parcel.recycle();
                            parcel2.recycle();
                            return bl;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        if (n == 0) {
                            bl = false;
                        }
                        parcel.recycle();
                        parcel2.recycle();
                        return bl;
                    }
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_7;
            }
        }

    }

}

