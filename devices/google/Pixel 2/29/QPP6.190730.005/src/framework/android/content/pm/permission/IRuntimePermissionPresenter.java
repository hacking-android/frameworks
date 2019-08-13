/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm.permission;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;

public interface IRuntimePermissionPresenter
extends IInterface {
    public void getAppPermissions(String var1, RemoteCallback var2) throws RemoteException;

    public static class Default
    implements IRuntimePermissionPresenter {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void getAppPermissions(String string2, RemoteCallback remoteCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRuntimePermissionPresenter {
        private static final String DESCRIPTOR = "android.content.pm.permission.IRuntimePermissionPresenter";
        static final int TRANSACTION_getAppPermissions = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRuntimePermissionPresenter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRuntimePermissionPresenter) {
                return (IRuntimePermissionPresenter)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRuntimePermissionPresenter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "getAppPermissions";
        }

        public static boolean setDefaultImpl(IRuntimePermissionPresenter iRuntimePermissionPresenter) {
            if (Proxy.sDefaultImpl == null && iRuntimePermissionPresenter != null) {
                Proxy.sDefaultImpl = iRuntimePermissionPresenter;
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
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                }
                ((Parcel)object2).writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = ((Parcel)object).readString();
            object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
            this.getAppPermissions((String)object2, (RemoteCallback)object);
            return true;
        }

        private static class Proxy
        implements IRuntimePermissionPresenter {
            public static IRuntimePermissionPresenter sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
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
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
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
        }

    }

}

