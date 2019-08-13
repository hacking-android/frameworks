/*
 * Decompiled with CFR 0.145.
 */
package android.app.role;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IOnRoleHoldersChangedListener
extends IInterface {
    public void onRoleHoldersChanged(String var1, int var2) throws RemoteException;

    public static class Default
    implements IOnRoleHoldersChangedListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onRoleHoldersChanged(String string2, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IOnRoleHoldersChangedListener {
        private static final String DESCRIPTOR = "android.app.role.IOnRoleHoldersChangedListener";
        static final int TRANSACTION_onRoleHoldersChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IOnRoleHoldersChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IOnRoleHoldersChangedListener) {
                return (IOnRoleHoldersChangedListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IOnRoleHoldersChangedListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onRoleHoldersChanged";
        }

        public static boolean setDefaultImpl(IOnRoleHoldersChangedListener iOnRoleHoldersChangedListener) {
            if (Proxy.sDefaultImpl == null && iOnRoleHoldersChangedListener != null) {
                Proxy.sDefaultImpl = iOnRoleHoldersChangedListener;
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
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onRoleHoldersChanged(parcel.readString(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements IOnRoleHoldersChangedListener {
            public static IOnRoleHoldersChangedListener sDefaultImpl;
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
            public void onRoleHoldersChanged(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRoleHoldersChanged(string2, n);
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

