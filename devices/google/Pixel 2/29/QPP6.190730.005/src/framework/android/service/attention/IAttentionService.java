/*
 * Decompiled with CFR 0.145.
 */
package android.service.attention;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.attention.IAttentionCallback;

public interface IAttentionService
extends IInterface {
    public void cancelAttentionCheck(IAttentionCallback var1) throws RemoteException;

    public void checkAttention(IAttentionCallback var1) throws RemoteException;

    public static class Default
    implements IAttentionService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelAttentionCheck(IAttentionCallback iAttentionCallback) throws RemoteException {
        }

        @Override
        public void checkAttention(IAttentionCallback iAttentionCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAttentionService {
        private static final String DESCRIPTOR = "android.service.attention.IAttentionService";
        static final int TRANSACTION_cancelAttentionCheck = 2;
        static final int TRANSACTION_checkAttention = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAttentionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAttentionService) {
                return (IAttentionService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAttentionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "cancelAttentionCheck";
            }
            return "checkAttention";
        }

        public static boolean setDefaultImpl(IAttentionService iAttentionService) {
            if (Proxy.sDefaultImpl == null && iAttentionService != null) {
                Proxy.sDefaultImpl = iAttentionService;
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
                this.cancelAttentionCheck(IAttentionCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.checkAttention(IAttentionCallback.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IAttentionService {
            public static IAttentionService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void cancelAttentionCheck(IAttentionCallback iAttentionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAttentionCallback != null ? iAttentionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().cancelAttentionCheck(iAttentionCallback);
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
            public void checkAttention(IAttentionCallback iAttentionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAttentionCallback != null ? iAttentionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().checkAttention(iAttentionCallback);
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

