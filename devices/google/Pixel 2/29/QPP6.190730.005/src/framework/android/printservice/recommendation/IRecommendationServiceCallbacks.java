/*
 * Decompiled with CFR 0.145.
 */
package android.printservice.recommendation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.printservice.recommendation.RecommendationInfo;
import java.util.ArrayList;
import java.util.List;

public interface IRecommendationServiceCallbacks
extends IInterface {
    public void onRecommendationsUpdated(List<RecommendationInfo> var1) throws RemoteException;

    public static class Default
    implements IRecommendationServiceCallbacks {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onRecommendationsUpdated(List<RecommendationInfo> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRecommendationServiceCallbacks {
        private static final String DESCRIPTOR = "android.printservice.recommendation.IRecommendationServiceCallbacks";
        static final int TRANSACTION_onRecommendationsUpdated = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRecommendationServiceCallbacks asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRecommendationServiceCallbacks) {
                return (IRecommendationServiceCallbacks)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRecommendationServiceCallbacks getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onRecommendationsUpdated";
        }

        public static boolean setDefaultImpl(IRecommendationServiceCallbacks iRecommendationServiceCallbacks) {
            if (Proxy.sDefaultImpl == null && iRecommendationServiceCallbacks != null) {
                Proxy.sDefaultImpl = iRecommendationServiceCallbacks;
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
            this.onRecommendationsUpdated(parcel.createTypedArrayList(RecommendationInfo.CREATOR));
            return true;
        }

        private static class Proxy
        implements IRecommendationServiceCallbacks {
            public static IRecommendationServiceCallbacks sDefaultImpl;
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
            public void onRecommendationsUpdated(List<RecommendationInfo> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRecommendationsUpdated(list);
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

