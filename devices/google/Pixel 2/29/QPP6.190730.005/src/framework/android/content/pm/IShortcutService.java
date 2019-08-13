/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ParceledListSlice;
import android.content.pm.ShortcutInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public interface IShortcutService
extends IInterface {
    public boolean addDynamicShortcuts(String var1, ParceledListSlice var2, int var3) throws RemoteException;

    public void applyRestore(byte[] var1, int var2) throws RemoteException;

    public Intent createShortcutResultIntent(String var1, ShortcutInfo var2, int var3) throws RemoteException;

    public void disableShortcuts(String var1, List var2, CharSequence var3, int var4, int var5) throws RemoteException;

    public void enableShortcuts(String var1, List var2, int var3) throws RemoteException;

    public byte[] getBackupPayload(int var1) throws RemoteException;

    public ParceledListSlice getDynamicShortcuts(String var1, int var2) throws RemoteException;

    public int getIconMaxDimensions(String var1, int var2) throws RemoteException;

    public ParceledListSlice getManifestShortcuts(String var1, int var2) throws RemoteException;

    public int getMaxShortcutCountPerActivity(String var1, int var2) throws RemoteException;

    public ParceledListSlice getPinnedShortcuts(String var1, int var2) throws RemoteException;

    public long getRateLimitResetTime(String var1, int var2) throws RemoteException;

    public int getRemainingCallCount(String var1, int var2) throws RemoteException;

    public ParceledListSlice getShareTargets(String var1, IntentFilter var2, int var3) throws RemoteException;

    public boolean hasShareTargets(String var1, String var2, int var3) throws RemoteException;

    public boolean isRequestPinItemSupported(int var1, int var2) throws RemoteException;

    public void onApplicationActive(String var1, int var2) throws RemoteException;

    public void removeAllDynamicShortcuts(String var1, int var2) throws RemoteException;

    public void removeDynamicShortcuts(String var1, List var2, int var3) throws RemoteException;

    public void reportShortcutUsed(String var1, String var2, int var3) throws RemoteException;

    public boolean requestPinShortcut(String var1, ShortcutInfo var2, IntentSender var3, int var4) throws RemoteException;

    public void resetThrottling() throws RemoteException;

    public boolean setDynamicShortcuts(String var1, ParceledListSlice var2, int var3) throws RemoteException;

    public boolean updateShortcuts(String var1, ParceledListSlice var2, int var3) throws RemoteException;

    public static class Default
    implements IShortcutService {
        @Override
        public boolean addDynamicShortcuts(String string2, ParceledListSlice parceledListSlice, int n) throws RemoteException {
            return false;
        }

        @Override
        public void applyRestore(byte[] arrby, int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public Intent createShortcutResultIntent(String string2, ShortcutInfo shortcutInfo, int n) throws RemoteException {
            return null;
        }

        @Override
        public void disableShortcuts(String string2, List list, CharSequence charSequence, int n, int n2) throws RemoteException {
        }

        @Override
        public void enableShortcuts(String string2, List list, int n) throws RemoteException {
        }

        @Override
        public byte[] getBackupPayload(int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getDynamicShortcuts(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getIconMaxDimensions(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public ParceledListSlice getManifestShortcuts(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getMaxShortcutCountPerActivity(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public ParceledListSlice getPinnedShortcuts(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public long getRateLimitResetTime(String string2, int n) throws RemoteException {
            return 0L;
        }

        @Override
        public int getRemainingCallCount(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public ParceledListSlice getShareTargets(String string2, IntentFilter intentFilter, int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean hasShareTargets(String string2, String string3, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isRequestPinItemSupported(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void onApplicationActive(String string2, int n) throws RemoteException {
        }

        @Override
        public void removeAllDynamicShortcuts(String string2, int n) throws RemoteException {
        }

        @Override
        public void removeDynamicShortcuts(String string2, List list, int n) throws RemoteException {
        }

        @Override
        public void reportShortcutUsed(String string2, String string3, int n) throws RemoteException {
        }

        @Override
        public boolean requestPinShortcut(String string2, ShortcutInfo shortcutInfo, IntentSender intentSender, int n) throws RemoteException {
            return false;
        }

        @Override
        public void resetThrottling() throws RemoteException {
        }

        @Override
        public boolean setDynamicShortcuts(String string2, ParceledListSlice parceledListSlice, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean updateShortcuts(String string2, ParceledListSlice parceledListSlice, int n) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IShortcutService {
        private static final String DESCRIPTOR = "android.content.pm.IShortcutService";
        static final int TRANSACTION_addDynamicShortcuts = 4;
        static final int TRANSACTION_applyRestore = 21;
        static final int TRANSACTION_createShortcutResultIntent = 10;
        static final int TRANSACTION_disableShortcuts = 11;
        static final int TRANSACTION_enableShortcuts = 12;
        static final int TRANSACTION_getBackupPayload = 20;
        static final int TRANSACTION_getDynamicShortcuts = 2;
        static final int TRANSACTION_getIconMaxDimensions = 16;
        static final int TRANSACTION_getManifestShortcuts = 3;
        static final int TRANSACTION_getMaxShortcutCountPerActivity = 13;
        static final int TRANSACTION_getPinnedShortcuts = 7;
        static final int TRANSACTION_getRateLimitResetTime = 15;
        static final int TRANSACTION_getRemainingCallCount = 14;
        static final int TRANSACTION_getShareTargets = 23;
        static final int TRANSACTION_hasShareTargets = 24;
        static final int TRANSACTION_isRequestPinItemSupported = 22;
        static final int TRANSACTION_onApplicationActive = 19;
        static final int TRANSACTION_removeAllDynamicShortcuts = 6;
        static final int TRANSACTION_removeDynamicShortcuts = 5;
        static final int TRANSACTION_reportShortcutUsed = 17;
        static final int TRANSACTION_requestPinShortcut = 9;
        static final int TRANSACTION_resetThrottling = 18;
        static final int TRANSACTION_setDynamicShortcuts = 1;
        static final int TRANSACTION_updateShortcuts = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IShortcutService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IShortcutService) {
                return (IShortcutService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IShortcutService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 24: {
                    return "hasShareTargets";
                }
                case 23: {
                    return "getShareTargets";
                }
                case 22: {
                    return "isRequestPinItemSupported";
                }
                case 21: {
                    return "applyRestore";
                }
                case 20: {
                    return "getBackupPayload";
                }
                case 19: {
                    return "onApplicationActive";
                }
                case 18: {
                    return "resetThrottling";
                }
                case 17: {
                    return "reportShortcutUsed";
                }
                case 16: {
                    return "getIconMaxDimensions";
                }
                case 15: {
                    return "getRateLimitResetTime";
                }
                case 14: {
                    return "getRemainingCallCount";
                }
                case 13: {
                    return "getMaxShortcutCountPerActivity";
                }
                case 12: {
                    return "enableShortcuts";
                }
                case 11: {
                    return "disableShortcuts";
                }
                case 10: {
                    return "createShortcutResultIntent";
                }
                case 9: {
                    return "requestPinShortcut";
                }
                case 8: {
                    return "updateShortcuts";
                }
                case 7: {
                    return "getPinnedShortcuts";
                }
                case 6: {
                    return "removeAllDynamicShortcuts";
                }
                case 5: {
                    return "removeDynamicShortcuts";
                }
                case 4: {
                    return "addDynamicShortcuts";
                }
                case 3: {
                    return "getManifestShortcuts";
                }
                case 2: {
                    return "getDynamicShortcuts";
                }
                case 1: 
            }
            return "setDynamicShortcuts";
        }

        public static boolean setDefaultImpl(IShortcutService iShortcutService) {
            if (Proxy.sDefaultImpl == null && iShortcutService != null) {
                Proxy.sDefaultImpl = iShortcutService;
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
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasShareTargets(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        IntentFilter intentFilter = ((Parcel)object).readInt() != 0 ? IntentFilter.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getShareTargets(string2, intentFilter, ((Parcel)object).readInt());
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
                        n = this.isRequestPinItemSupported(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.applyRestore(((Parcel)object).createByteArray(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getBackupPayload(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onApplicationActive(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resetThrottling();
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportShortcutUsed(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getIconMaxDimensions(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getRateLimitResetTime(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRemainingCallCount(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getMaxShortcutCountPerActivity(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enableShortcuts(((Parcel)object).readString(), ((Parcel)object).readArrayList(this.getClass().getClassLoader()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        ArrayList arrayList = ((Parcel)object).readArrayList(this.getClass().getClassLoader());
                        CharSequence charSequence = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.disableShortcuts(string3, arrayList, charSequence, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        ShortcutInfo shortcutInfo = ((Parcel)object).readInt() != 0 ? ShortcutInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.createShortcutResultIntent(string4, shortcutInfo, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Intent)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        ShortcutInfo shortcutInfo = ((Parcel)object).readInt() != 0 ? ShortcutInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        IntentSender intentSender = ((Parcel)object).readInt() != 0 ? IntentSender.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.requestPinShortcut(string5, shortcutInfo, intentSender, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        ParceledListSlice parceledListSlice = ((Parcel)object).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.updateShortcuts(string6, parceledListSlice, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPinnedShortcuts(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeAllDynamicShortcuts(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeDynamicShortcuts(((Parcel)object).readString(), ((Parcel)object).readArrayList(this.getClass().getClassLoader()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string7 = ((Parcel)object).readString();
                        ParceledListSlice parceledListSlice = ((Parcel)object).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addDynamicShortcuts(string7, parceledListSlice, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getManifestShortcuts(((Parcel)object).readString(), ((Parcel)object).readInt());
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
                        object = this.getDynamicShortcuts(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                String string8 = ((Parcel)object).readString();
                ParceledListSlice parceledListSlice = ((Parcel)object).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)object) : null;
                n = this.setDynamicShortcuts(string8, parceledListSlice, ((Parcel)object).readInt()) ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IShortcutService {
            public static IShortcutService sDefaultImpl;
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
            public boolean addDynamicShortcuts(String string2, ParceledListSlice parceledListSlice, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    boolean bl = true;
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().addDynamicShortcuts(string2, parceledListSlice, n);
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
            public void applyRestore(byte[] arrby, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Intent createShortcutResultIntent(String object, ShortcutInfo shortcutInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (shortcutInfo != null) {
                        parcel.writeInt(1);
                        shortcutInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().createShortcutResultIntent((String)object, shortcutInfo, n);
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
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void disableShortcuts(String string2, List list, CharSequence charSequence, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeList(list);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableShortcuts(string2, list, charSequence, n, n2);
                        return;
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
            public void enableShortcuts(String string2, List list, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeList(list);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableShortcuts(string2, list, n);
                        return;
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
            public byte[] getBackupPayload(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public ParceledListSlice getDynamicShortcuts(String parceledListSlice, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)((Object)parceledListSlice));
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getDynamicShortcuts((String)((Object)parceledListSlice), n);
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
            public int getIconMaxDimensions(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getIconMaxDimensions(string2, n);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public ParceledListSlice getManifestShortcuts(String parceledListSlice, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)((Object)parceledListSlice));
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getManifestShortcuts((String)((Object)parceledListSlice), n);
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
            public int getMaxShortcutCountPerActivity(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getMaxShortcutCountPerActivity(string2, n);
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
            public ParceledListSlice getPinnedShortcuts(String parceledListSlice, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)((Object)parceledListSlice));
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(7, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getPinnedShortcuts((String)((Object)parceledListSlice), n);
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
            public long getRateLimitResetTime(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getRateLimitResetTime(string2, n);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getRemainingCallCount(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getRemainingCallCount(string2, n);
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
            public ParceledListSlice getShareTargets(String parceledListSlice, IntentFilter intentFilter, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)parceledListSlice));
                    if (intentFilter != null) {
                        parcel.writeInt(1);
                        intentFilter.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        parceledListSlice = Stub.getDefaultImpl().getShareTargets((String)((Object)parceledListSlice), intentFilter, n);
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
            public boolean hasShareTargets(String string2, String string3, int n) throws RemoteException {
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
                    if (iBinder.transact(24, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasShareTargets(string2, string3, n);
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
            public boolean isRequestPinItemSupported(int n, int n2) throws RemoteException {
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
                    if (iBinder.transact(22, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRequestPinItemSupported(n, n2);
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
            public void onApplicationActive(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onApplicationActive(string2, n);
                        return;
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
            public void removeAllDynamicShortcuts(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeAllDynamicShortcuts(string2, n);
                        return;
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
            public void removeDynamicShortcuts(String string2, List list, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeList(list);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeDynamicShortcuts(string2, list, n);
                        return;
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
            public void reportShortcutUsed(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportShortcutUsed(string2, string3, n);
                        return;
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
            public boolean requestPinShortcut(String string2, ShortcutInfo shortcutInfo, IntentSender intentSender, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    boolean bl = true;
                    if (shortcutInfo != null) {
                        parcel.writeInt(1);
                        shortcutInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (intentSender != null) {
                        parcel.writeInt(1);
                        intentSender.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().requestPinShortcut(string2, shortcutInfo, intentSender, n);
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
            public void resetThrottling() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetThrottling();
                        return;
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
            public boolean setDynamicShortcuts(String string2, ParceledListSlice parceledListSlice, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    boolean bl = true;
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setDynamicShortcuts(string2, parceledListSlice, n);
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
            public boolean updateShortcuts(String string2, ParceledListSlice parceledListSlice, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    boolean bl = true;
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().updateShortcuts(string2, parceledListSlice, n);
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
        }

    }

}

