/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.app.backup.RestoreSet;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IRestoreObserver
extends IInterface {
    public void onUpdate(int var1, String var2) throws RemoteException;

    public void restoreFinished(int var1) throws RemoteException;

    public void restoreSetsAvailable(RestoreSet[] var1) throws RemoteException;

    public void restoreStarting(int var1) throws RemoteException;

    public static class Default
    implements IRestoreObserver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onUpdate(int n, String string2) throws RemoteException {
        }

        @Override
        public void restoreFinished(int n) throws RemoteException {
        }

        @Override
        public void restoreSetsAvailable(RestoreSet[] arrrestoreSet) throws RemoteException {
        }

        @Override
        public void restoreStarting(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRestoreObserver {
        private static final String DESCRIPTOR = "android.app.backup.IRestoreObserver";
        static final int TRANSACTION_onUpdate = 3;
        static final int TRANSACTION_restoreFinished = 4;
        static final int TRANSACTION_restoreSetsAvailable = 1;
        static final int TRANSACTION_restoreStarting = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRestoreObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRestoreObserver) {
                return (IRestoreObserver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRestoreObserver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "restoreFinished";
                    }
                    return "onUpdate";
                }
                return "restoreStarting";
            }
            return "restoreSetsAvailable";
        }

        public static boolean setDefaultImpl(IRestoreObserver iRestoreObserver) {
            if (Proxy.sDefaultImpl == null && iRestoreObserver != null) {
                Proxy.sDefaultImpl = iRestoreObserver;
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
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, parcel, parcel2, n2);
                            }
                            parcel2.writeString(DESCRIPTOR);
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.restoreFinished(parcel.readInt());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onUpdate(parcel.readInt(), parcel.readString());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.restoreStarting(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.restoreSetsAvailable(parcel.createTypedArray(RestoreSet.CREATOR));
            return true;
        }

        private static class Proxy
        implements IRestoreObserver {
            public static IRestoreObserver sDefaultImpl;
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
            public void onUpdate(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUpdate(n, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void restoreFinished(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreFinished(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void restoreSetsAvailable(RestoreSet[] arrrestoreSet) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrrestoreSet, 0);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreSetsAvailable(arrrestoreSet);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void restoreStarting(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreStarting(n);
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

