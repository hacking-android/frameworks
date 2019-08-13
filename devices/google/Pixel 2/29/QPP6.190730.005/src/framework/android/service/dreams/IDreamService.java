/*
 * Decompiled with CFR 0.145.
 */
package android.service.dreams;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDreamService
extends IInterface {
    public void attach(IBinder var1, boolean var2, IRemoteCallback var3) throws RemoteException;

    public void detach() throws RemoteException;

    public void wakeUp() throws RemoteException;

    public static class Default
    implements IDreamService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void attach(IBinder iBinder, boolean bl, IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override
        public void detach() throws RemoteException {
        }

        @Override
        public void wakeUp() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDreamService {
        private static final String DESCRIPTOR = "android.service.dreams.IDreamService";
        static final int TRANSACTION_attach = 1;
        static final int TRANSACTION_detach = 2;
        static final int TRANSACTION_wakeUp = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDreamService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDreamService) {
                return (IDreamService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDreamService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "wakeUp";
                }
                return "detach";
            }
            return "attach";
        }

        public static boolean setDefaultImpl(IDreamService iDreamService) {
            if (Proxy.sDefaultImpl == null && iDreamService != null) {
                Proxy.sDefaultImpl = iDreamService;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, (Parcel)object, n2);
                        }
                        ((Parcel)object).writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.wakeUp();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.detach();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.readStrongBinder();
            boolean bl = parcel.readInt() != 0;
            this.attach((IBinder)object, bl, IRemoteCallback.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IDreamService {
            public static IDreamService sDefaultImpl;
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
            public void attach(IBinder iBinder, boolean bl, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    IBinder iBinder2 = iRemoteCallback != null ? iRemoteCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().attach(iBinder, bl, iRemoteCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void detach() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().detach();
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

            @Override
            public void wakeUp() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().wakeUp();
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

