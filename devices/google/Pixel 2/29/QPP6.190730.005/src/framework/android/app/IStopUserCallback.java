/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IStopUserCallback
extends IInterface {
    public void userStopAborted(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void userStopped(int var1) throws RemoteException;

    public static class Default
    implements IStopUserCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void userStopAborted(int n) throws RemoteException {
        }

        @Override
        public void userStopped(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IStopUserCallback {
        private static final String DESCRIPTOR = "android.app.IStopUserCallback";
        static final int TRANSACTION_userStopAborted = 2;
        static final int TRANSACTION_userStopped = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IStopUserCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IStopUserCallback) {
                return (IStopUserCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IStopUserCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "userStopAborted";
            }
            return "userStopped";
        }

        public static boolean setDefaultImpl(IStopUserCallback iStopUserCallback) {
            if (Proxy.sDefaultImpl == null && iStopUserCallback != null) {
                Proxy.sDefaultImpl = iStopUserCallback;
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
                this.userStopAborted(parcel.readInt());
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.userStopped(parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IStopUserCallback {
            public static IStopUserCallback sDefaultImpl;
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
            public void userStopAborted(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().userStopAborted(n);
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
            public void userStopped(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().userStopped(n);
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

