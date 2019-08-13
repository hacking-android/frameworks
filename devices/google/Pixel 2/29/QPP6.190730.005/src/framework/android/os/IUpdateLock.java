/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUpdateLock
extends IInterface {
    public void acquireUpdateLock(IBinder var1, String var2) throws RemoteException;

    public void releaseUpdateLock(IBinder var1) throws RemoteException;

    public static class Default
    implements IUpdateLock {
        @Override
        public void acquireUpdateLock(IBinder iBinder, String string2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void releaseUpdateLock(IBinder iBinder) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IUpdateLock {
        private static final String DESCRIPTOR = "android.os.IUpdateLock";
        static final int TRANSACTION_acquireUpdateLock = 1;
        static final int TRANSACTION_releaseUpdateLock = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IUpdateLock asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IUpdateLock) {
                return (IUpdateLock)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IUpdateLock getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "releaseUpdateLock";
            }
            return "acquireUpdateLock";
        }

        public static boolean setDefaultImpl(IUpdateLock iUpdateLock) {
            if (Proxy.sDefaultImpl == null && iUpdateLock != null) {
                Proxy.sDefaultImpl = iUpdateLock;
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
                this.releaseUpdateLock(parcel.readStrongBinder());
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.acquireUpdateLock(parcel.readStrongBinder(), parcel.readString());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IUpdateLock {
            public static IUpdateLock sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void acquireUpdateLock(IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acquireUpdateLock(iBinder, string2);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void releaseUpdateLock(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseUpdateLock(iBinder);
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

