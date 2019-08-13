/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.NetworkKey;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface INetworkRecommendationProvider
extends IInterface {
    public void requestScores(NetworkKey[] var1) throws RemoteException;

    public static class Default
    implements INetworkRecommendationProvider {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void requestScores(NetworkKey[] arrnetworkKey) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkRecommendationProvider {
        private static final String DESCRIPTOR = "android.net.INetworkRecommendationProvider";
        static final int TRANSACTION_requestScores = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkRecommendationProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkRecommendationProvider) {
                return (INetworkRecommendationProvider)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkRecommendationProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "requestScores";
        }

        public static boolean setDefaultImpl(INetworkRecommendationProvider iNetworkRecommendationProvider) {
            if (Proxy.sDefaultImpl == null && iNetworkRecommendationProvider != null) {
                Proxy.sDefaultImpl = iNetworkRecommendationProvider;
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
            this.requestScores(parcel.createTypedArray(NetworkKey.CREATOR));
            return true;
        }

        private static class Proxy
        implements INetworkRecommendationProvider {
            public static INetworkRecommendationProvider sDefaultImpl;
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
            public void requestScores(NetworkKey[] arrnetworkKey) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrnetworkKey, 0);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestScores(arrnetworkKey);
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

