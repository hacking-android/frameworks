/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IQualifiedNetworksServiceCallback
extends IInterface {
    public void onQualifiedNetworkTypesChanged(int var1, int[] var2) throws RemoteException;

    public static class Default
    implements IQualifiedNetworksServiceCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onQualifiedNetworkTypesChanged(int n, int[] arrn) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IQualifiedNetworksServiceCallback {
        private static final String DESCRIPTOR = "android.telephony.data.IQualifiedNetworksServiceCallback";
        static final int TRANSACTION_onQualifiedNetworkTypesChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IQualifiedNetworksServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IQualifiedNetworksServiceCallback) {
                return (IQualifiedNetworksServiceCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IQualifiedNetworksServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onQualifiedNetworkTypesChanged";
        }

        public static boolean setDefaultImpl(IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) {
            if (Proxy.sDefaultImpl == null && iQualifiedNetworksServiceCallback != null) {
                Proxy.sDefaultImpl = iQualifiedNetworksServiceCallback;
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
            this.onQualifiedNetworkTypesChanged(parcel.readInt(), parcel.createIntArray());
            return true;
        }

        private static class Proxy
        implements IQualifiedNetworksServiceCallback {
            public static IQualifiedNetworksServiceCallback sDefaultImpl;
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
            public void onQualifiedNetworkTypesChanged(int n, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onQualifiedNetworkTypesChanged(n, arrn);
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

