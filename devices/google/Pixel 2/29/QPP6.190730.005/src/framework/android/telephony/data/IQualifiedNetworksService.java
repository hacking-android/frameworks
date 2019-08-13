/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.data.IQualifiedNetworksServiceCallback;

public interface IQualifiedNetworksService
extends IInterface {
    public void createNetworkAvailabilityProvider(int var1, IQualifiedNetworksServiceCallback var2) throws RemoteException;

    public void removeNetworkAvailabilityProvider(int var1) throws RemoteException;

    public static class Default
    implements IQualifiedNetworksService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void createNetworkAvailabilityProvider(int n, IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) throws RemoteException {
        }

        @Override
        public void removeNetworkAvailabilityProvider(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IQualifiedNetworksService {
        private static final String DESCRIPTOR = "android.telephony.data.IQualifiedNetworksService";
        static final int TRANSACTION_createNetworkAvailabilityProvider = 1;
        static final int TRANSACTION_removeNetworkAvailabilityProvider = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IQualifiedNetworksService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IQualifiedNetworksService) {
                return (IQualifiedNetworksService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IQualifiedNetworksService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "removeNetworkAvailabilityProvider";
            }
            return "createNetworkAvailabilityProvider";
        }

        public static boolean setDefaultImpl(IQualifiedNetworksService iQualifiedNetworksService) {
            if (Proxy.sDefaultImpl == null && iQualifiedNetworksService != null) {
                Proxy.sDefaultImpl = iQualifiedNetworksService;
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
                this.removeNetworkAvailabilityProvider(parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.createNetworkAvailabilityProvider(parcel.readInt(), IQualifiedNetworksServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IQualifiedNetworksService {
            public static IQualifiedNetworksService sDefaultImpl;
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
            public void createNetworkAvailabilityProvider(int n, IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iQualifiedNetworksServiceCallback != null ? iQualifiedNetworksServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().createNetworkAvailabilityProvider(n, iQualifiedNetworksServiceCallback);
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
            public void removeNetworkAvailabilityProvider(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeNetworkAvailabilityProvider(n);
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

