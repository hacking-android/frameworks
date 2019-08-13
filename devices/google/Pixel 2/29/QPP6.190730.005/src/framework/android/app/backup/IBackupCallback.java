/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBackupCallback
extends IInterface {
    public void operationComplete(long var1) throws RemoteException;

    public static class Default
    implements IBackupCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void operationComplete(long l) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBackupCallback {
        private static final String DESCRIPTOR = "android.app.backup.IBackupCallback";
        static final int TRANSACTION_operationComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBackupCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBackupCallback) {
                return (IBackupCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBackupCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "operationComplete";
        }

        public static boolean setDefaultImpl(IBackupCallback iBackupCallback) {
            if (Proxy.sDefaultImpl == null && iBackupCallback != null) {
                Proxy.sDefaultImpl = iBackupCallback;
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
            this.operationComplete(parcel.readLong());
            return true;
        }

        private static class Proxy
        implements IBackupCallback {
            public static IBackupCallback sDefaultImpl;
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
            public void operationComplete(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().operationComplete(l);
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

