/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.ArrayList;
import java.util.List;

public interface IPermissionController
extends IInterface {
    public void countPermissionApps(List<String> var1, int var2, RemoteCallback var3) throws RemoteException;

    public void getAppPermissions(String var1, RemoteCallback var2) throws RemoteException;

    public void getPermissionUsages(boolean var1, long var2, RemoteCallback var4) throws RemoteException;

    public void getRuntimePermissionBackup(UserHandle var1, ParcelFileDescriptor var2) throws RemoteException;

    public void grantOrUpgradeDefaultRuntimePermissions(RemoteCallback var1) throws RemoteException;

    public void restoreDelayedRuntimePermissionBackup(String var1, UserHandle var2, RemoteCallback var3) throws RemoteException;

    public void restoreRuntimePermissionBackup(UserHandle var1, ParcelFileDescriptor var2) throws RemoteException;

    public void revokeRuntimePermission(String var1, String var2) throws RemoteException;

    public void revokeRuntimePermissions(Bundle var1, boolean var2, int var3, String var4, RemoteCallback var5) throws RemoteException;

    public void setRuntimePermissionGrantStateByDeviceAdmin(String var1, String var2, String var3, int var4, RemoteCallback var5) throws RemoteException;

    public static class Default
    implements IPermissionController {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void countPermissionApps(List<String> list, int n, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void getAppPermissions(String string2, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void getPermissionUsages(boolean bl, long l, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void getRuntimePermissionBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override
        public void grantOrUpgradeDefaultRuntimePermissions(RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void restoreDelayedRuntimePermissionBackup(String string2, UserHandle userHandle, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void restoreRuntimePermissionBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override
        public void revokeRuntimePermission(String string2, String string3) throws RemoteException {
        }

        @Override
        public void revokeRuntimePermissions(Bundle bundle, boolean bl, int n, String string2, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void setRuntimePermissionGrantStateByDeviceAdmin(String string2, String string3, String string4, int n, RemoteCallback remoteCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPermissionController {
        private static final String DESCRIPTOR = "android.permission.IPermissionController";
        static final int TRANSACTION_countPermissionApps = 7;
        static final int TRANSACTION_getAppPermissions = 5;
        static final int TRANSACTION_getPermissionUsages = 8;
        static final int TRANSACTION_getRuntimePermissionBackup = 2;
        static final int TRANSACTION_grantOrUpgradeDefaultRuntimePermissions = 10;
        static final int TRANSACTION_restoreDelayedRuntimePermissionBackup = 4;
        static final int TRANSACTION_restoreRuntimePermissionBackup = 3;
        static final int TRANSACTION_revokeRuntimePermission = 6;
        static final int TRANSACTION_revokeRuntimePermissions = 1;
        static final int TRANSACTION_setRuntimePermissionGrantStateByDeviceAdmin = 9;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPermissionController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPermissionController) {
                return (IPermissionController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPermissionController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "grantOrUpgradeDefaultRuntimePermissions";
                }
                case 9: {
                    return "setRuntimePermissionGrantStateByDeviceAdmin";
                }
                case 8: {
                    return "getPermissionUsages";
                }
                case 7: {
                    return "countPermissionApps";
                }
                case 6: {
                    return "revokeRuntimePermission";
                }
                case 5: {
                    return "getAppPermissions";
                }
                case 4: {
                    return "restoreDelayedRuntimePermissionBackup";
                }
                case 3: {
                    return "restoreRuntimePermissionBackup";
                }
                case 2: {
                    return "getRuntimePermissionBackup";
                }
                case 1: 
            }
            return "revokeRuntimePermissions";
        }

        public static boolean setDefaultImpl(IPermissionController iPermissionController) {
            if (Proxy.sDefaultImpl == null && iPermissionController != null) {
                Proxy.sDefaultImpl = iPermissionController;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.grantOrUpgradeDefaultRuntimePermissions((RemoteCallback)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        String string3 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setRuntimePermissionGrantStateByDeviceAdmin(string2, string3, (String)object2, n, (RemoteCallback)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        long l = ((Parcel)object).readLong();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getPermissionUsages(bl, l, (RemoteCallback)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).createStringArrayList();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.countPermissionApps((List<String>)object2, n, (RemoteCallback)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.revokeRuntimePermission(((Parcel)object).readString(), ((Parcel)object).readString());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getAppPermissions((String)object2, (RemoteCallback)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.restoreDelayedRuntimePermissionBackup(string4, (UserHandle)object2, (RemoteCallback)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        this.restoreRuntimePermissionBackup((UserHandle)object2, (ParcelFileDescriptor)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getRuntimePermissionBackup((UserHandle)object2, (ParcelFileDescriptor)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                bl = ((Parcel)object).readInt() != 0;
                n = ((Parcel)object).readInt();
                String string5 = ((Parcel)object).readString();
                object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                this.revokeRuntimePermissions((Bundle)object2, bl, n, string5, (RemoteCallback)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPermissionController {
            public static IPermissionController sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void countPermissionApps(List<String> list, int n, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    parcel.writeInt(n);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().countPermissionApps(list, n, remoteCallback);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void getAppPermissions(String string2, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getAppPermissions(string2, remoteCallback);
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
            public void getPermissionUsages(boolean bl, long l, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getPermissionUsages(bl, l, remoteCallback);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void getRuntimePermissionBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getRuntimePermissionBackup(userHandle, parcelFileDescriptor);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void grantOrUpgradeDefaultRuntimePermissions(RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantOrUpgradeDefaultRuntimePermissions(remoteCallback);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void restoreDelayedRuntimePermissionBackup(String string2, UserHandle userHandle, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreDelayedRuntimePermissionBackup(string2, userHandle, remoteCallback);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void restoreRuntimePermissionBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreRuntimePermissionBackup(userHandle, parcelFileDescriptor);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void revokeRuntimePermission(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().revokeRuntimePermission(string2, string3);
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
            public void revokeRuntimePermissions(Bundle bundle, boolean bl, int n, String string2, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().revokeRuntimePermissions(bundle, bl, n, string2, remoteCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setRuntimePermissionGrantStateByDeviceAdmin(String string2, String string3, String string4, int n, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    parcel.writeInt(n);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRuntimePermissionGrantStateByDeviceAdmin(string2, string3, string4, n, remoteCallback);
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

