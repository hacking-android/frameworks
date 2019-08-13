/*
 * Decompiled with CFR 0.145.
 */
package android.app.role;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;

public interface IRoleController
extends IInterface {
    public void grantDefaultRoles(RemoteCallback var1) throws RemoteException;

    public void isApplicationQualifiedForRole(String var1, String var2, RemoteCallback var3) throws RemoteException;

    public void isRoleVisible(String var1, RemoteCallback var2) throws RemoteException;

    public void onAddRoleHolder(String var1, String var2, int var3, RemoteCallback var4) throws RemoteException;

    public void onClearRoleHolders(String var1, int var2, RemoteCallback var3) throws RemoteException;

    public void onRemoveRoleHolder(String var1, String var2, int var3, RemoteCallback var4) throws RemoteException;

    public static class Default
    implements IRoleController {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void grantDefaultRoles(RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void isApplicationQualifiedForRole(String string2, String string3, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void isRoleVisible(String string2, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void onAddRoleHolder(String string2, String string3, int n, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void onClearRoleHolders(String string2, int n, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void onRemoveRoleHolder(String string2, String string3, int n, RemoteCallback remoteCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRoleController {
        private static final String DESCRIPTOR = "android.app.role.IRoleController";
        static final int TRANSACTION_grantDefaultRoles = 1;
        static final int TRANSACTION_isApplicationQualifiedForRole = 5;
        static final int TRANSACTION_isRoleVisible = 6;
        static final int TRANSACTION_onAddRoleHolder = 2;
        static final int TRANSACTION_onClearRoleHolders = 4;
        static final int TRANSACTION_onRemoveRoleHolder = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRoleController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRoleController) {
                return (IRoleController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRoleController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "isRoleVisible";
                }
                case 5: {
                    return "isApplicationQualifiedForRole";
                }
                case 4: {
                    return "onClearRoleHolders";
                }
                case 3: {
                    return "onRemoveRoleHolder";
                }
                case 2: {
                    return "onAddRoleHolder";
                }
                case 1: 
            }
            return "grantDefaultRoles";
        }

        public static boolean setDefaultImpl(IRoleController iRoleController) {
            if (Proxy.sDefaultImpl == null && iRoleController != null) {
                Proxy.sDefaultImpl = iRoleController;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.isRoleVisible((String)object2, (RemoteCallback)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.isApplicationQualifiedForRole(string2, (String)object2, (RemoteCallback)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onClearRoleHolders((String)object2, n, (RemoteCallback)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        String string3 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onRemoveRoleHolder((String)object2, string3, n, (RemoteCallback)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onAddRoleHolder(string4, (String)object2, n, (RemoteCallback)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                this.grantDefaultRoles((RemoteCallback)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IRoleController {
            public static IRoleController sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void grantDefaultRoles(RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantDefaultRoles(remoteCallback);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void isApplicationQualifiedForRole(String string2, String string3, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().isApplicationQualifiedForRole(string2, string3, remoteCallback);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void isRoleVisible(String string2, RemoteCallback remoteCallback) throws RemoteException {
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
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().isRoleVisible(string2, remoteCallback);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAddRoleHolder(String string2, String string3, int n, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAddRoleHolder(string2, string3, n, remoteCallback);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onClearRoleHolders(String string2, int n, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onClearRoleHolders(string2, n, remoteCallback);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRemoveRoleHolder(String string2, String string3, int n, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRemoveRoleHolder(string2, string3, n, remoteCallback);
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

