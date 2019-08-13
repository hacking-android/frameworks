/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.NetworkRegistrationInfo;

public interface INetworkServiceCallback
extends IInterface {
    public void onNetworkStateChanged() throws RemoteException;

    public void onRequestNetworkRegistrationInfoComplete(int var1, NetworkRegistrationInfo var2) throws RemoteException;

    public static class Default
    implements INetworkServiceCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onNetworkStateChanged() throws RemoteException {
        }

        @Override
        public void onRequestNetworkRegistrationInfoComplete(int n, NetworkRegistrationInfo networkRegistrationInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkServiceCallback {
        private static final String DESCRIPTOR = "android.telephony.INetworkServiceCallback";
        static final int TRANSACTION_onNetworkStateChanged = 2;
        static final int TRANSACTION_onRequestNetworkRegistrationInfoComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkServiceCallback) {
                return (INetworkServiceCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onNetworkStateChanged";
            }
            return "onRequestNetworkRegistrationInfoComplete";
        }

        public static boolean setDefaultImpl(INetworkServiceCallback iNetworkServiceCallback) {
            if (Proxy.sDefaultImpl == null && iNetworkServiceCallback != null) {
                Proxy.sDefaultImpl = iNetworkServiceCallback;
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
                this.onNetworkStateChanged();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? NetworkRegistrationInfo.CREATOR.createFromParcel((Parcel)object) : null;
            this.onRequestNetworkRegistrationInfoComplete(n, (NetworkRegistrationInfo)object);
            return true;
        }

        private static class Proxy
        implements INetworkServiceCallback {
            public static INetworkServiceCallback sDefaultImpl;
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
            public void onNetworkStateChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNetworkStateChanged();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRequestNetworkRegistrationInfoComplete(int n, NetworkRegistrationInfo networkRegistrationInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (networkRegistrationInfo != null) {
                        parcel.writeInt(1);
                        networkRegistrationInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRequestNetworkRegistrationInfoComplete(n, networkRegistrationInfo);
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

