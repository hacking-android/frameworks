/*
 * Decompiled with CFR 0.145.
 */
package android.service.attention;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAttentionCallback
extends IInterface {
    public void onFailure(int var1) throws RemoteException;

    public void onSuccess(int var1, long var2) throws RemoteException;

    public static class Default
    implements IAttentionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onFailure(int n) throws RemoteException {
        }

        @Override
        public void onSuccess(int n, long l) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAttentionCallback {
        private static final String DESCRIPTOR = "android.service.attention.IAttentionCallback";
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAttentionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAttentionCallback) {
                return (IAttentionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAttentionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onFailure";
            }
            return "onSuccess";
        }

        public static boolean setDefaultImpl(IAttentionCallback iAttentionCallback) {
            if (Proxy.sDefaultImpl == null && iAttentionCallback != null) {
                Proxy.sDefaultImpl = iAttentionCallback;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onFailure(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onSuccess(parcel.readInt(), parcel.readLong());
            return true;
        }

        private static class Proxy
        implements IAttentionCallback {
            public static IAttentionCallback sDefaultImpl;
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
            public void onFailure(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFailure(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSuccess(int n, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuccess(n, l);
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

