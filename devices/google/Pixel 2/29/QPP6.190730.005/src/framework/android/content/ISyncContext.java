/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.SyncResult;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISyncContext
extends IInterface {
    public void onFinished(SyncResult var1) throws RemoteException;

    public void sendHeartbeat() throws RemoteException;

    public static class Default
    implements ISyncContext {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onFinished(SyncResult syncResult) throws RemoteException {
        }

        @Override
        public void sendHeartbeat() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISyncContext {
        private static final String DESCRIPTOR = "android.content.ISyncContext";
        static final int TRANSACTION_onFinished = 2;
        static final int TRANSACTION_sendHeartbeat = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISyncContext asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISyncContext) {
                return (ISyncContext)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISyncContext getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onFinished";
            }
            return "sendHeartbeat";
        }

        public static boolean setDefaultImpl(ISyncContext iSyncContext) {
            if (Proxy.sDefaultImpl == null && iSyncContext != null) {
                Proxy.sDefaultImpl = iSyncContext;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? SyncResult.CREATOR.createFromParcel((Parcel)object) : null;
                this.onFinished((SyncResult)object);
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.sendHeartbeat();
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements ISyncContext {
            public static ISyncContext sDefaultImpl;
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
            public void onFinished(SyncResult syncResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (syncResult != null) {
                        parcel.writeInt(1);
                        syncResult.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFinished(syncResult);
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
            public void sendHeartbeat() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendHeartbeat();
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

