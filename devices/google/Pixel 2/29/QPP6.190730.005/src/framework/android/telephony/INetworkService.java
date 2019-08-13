/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.INetworkServiceCallback;

public interface INetworkService
extends IInterface {
    public void createNetworkServiceProvider(int var1) throws RemoteException;

    public void registerForNetworkRegistrationInfoChanged(int var1, INetworkServiceCallback var2) throws RemoteException;

    public void removeNetworkServiceProvider(int var1) throws RemoteException;

    public void requestNetworkRegistrationInfo(int var1, int var2, INetworkServiceCallback var3) throws RemoteException;

    public void unregisterForNetworkRegistrationInfoChanged(int var1, INetworkServiceCallback var2) throws RemoteException;

    public static class Default
    implements INetworkService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void createNetworkServiceProvider(int n) throws RemoteException {
        }

        @Override
        public void registerForNetworkRegistrationInfoChanged(int n, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException {
        }

        @Override
        public void removeNetworkServiceProvider(int n) throws RemoteException {
        }

        @Override
        public void requestNetworkRegistrationInfo(int n, int n2, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException {
        }

        @Override
        public void unregisterForNetworkRegistrationInfoChanged(int n, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkService {
        private static final String DESCRIPTOR = "android.telephony.INetworkService";
        static final int TRANSACTION_createNetworkServiceProvider = 1;
        static final int TRANSACTION_registerForNetworkRegistrationInfoChanged = 4;
        static final int TRANSACTION_removeNetworkServiceProvider = 2;
        static final int TRANSACTION_requestNetworkRegistrationInfo = 3;
        static final int TRANSACTION_unregisterForNetworkRegistrationInfoChanged = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkService) {
                return (INetworkService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "unregisterForNetworkRegistrationInfoChanged";
                        }
                        return "registerForNetworkRegistrationInfoChanged";
                    }
                    return "requestNetworkRegistrationInfo";
                }
                return "removeNetworkServiceProvider";
            }
            return "createNetworkServiceProvider";
        }

        public static boolean setDefaultImpl(INetworkService iNetworkService) {
            if (Proxy.sDefaultImpl == null && iNetworkService != null) {
                Proxy.sDefaultImpl = iNetworkService;
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
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, parcel, parcel2, n2);
                                }
                                parcel2.writeString(DESCRIPTOR);
                                return true;
                            }
                            parcel.enforceInterface(DESCRIPTOR);
                            this.unregisterForNetworkRegistrationInfoChanged(parcel.readInt(), INetworkServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.registerForNetworkRegistrationInfoChanged(parcel.readInt(), INetworkServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.requestNetworkRegistrationInfo(parcel.readInt(), parcel.readInt(), INetworkServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.removeNetworkServiceProvider(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.createNetworkServiceProvider(parcel.readInt());
            return true;
        }

        private static class Proxy
        implements INetworkService {
            public static INetworkService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void createNetworkServiceProvider(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createNetworkServiceProvider(n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerForNetworkRegistrationInfoChanged(int n, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iNetworkServiceCallback != null ? iNetworkServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().registerForNetworkRegistrationInfoChanged(n, iNetworkServiceCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeNetworkServiceProvider(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeNetworkServiceProvider(n);
                        return;
                    }
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
            public void requestNetworkRegistrationInfo(int n, int n2, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iNetworkServiceCallback != null ? iNetworkServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().requestNetworkRegistrationInfo(n, n2, iNetworkServiceCallback);
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
            public void unregisterForNetworkRegistrationInfoChanged(int n, INetworkServiceCallback iNetworkServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iNetworkServiceCallback != null ? iNetworkServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().unregisterForNetworkRegistrationInfoChanged(n, iNetworkServiceCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

