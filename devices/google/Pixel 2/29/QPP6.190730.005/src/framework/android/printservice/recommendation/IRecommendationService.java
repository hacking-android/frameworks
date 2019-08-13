/*
 * Decompiled with CFR 0.145.
 */
package android.printservice.recommendation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.printservice.recommendation.IRecommendationServiceCallbacks;

public interface IRecommendationService
extends IInterface {
    public void registerCallbacks(IRecommendationServiceCallbacks var1) throws RemoteException;

    public static class Default
    implements IRecommendationService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void registerCallbacks(IRecommendationServiceCallbacks iRecommendationServiceCallbacks) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRecommendationService {
        private static final String DESCRIPTOR = "android.printservice.recommendation.IRecommendationService";
        static final int TRANSACTION_registerCallbacks = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRecommendationService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRecommendationService) {
                return (IRecommendationService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRecommendationService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "registerCallbacks";
        }

        public static boolean setDefaultImpl(IRecommendationService iRecommendationService) {
            if (Proxy.sDefaultImpl == null && iRecommendationService != null) {
                Proxy.sDefaultImpl = iRecommendationService;
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
            this.registerCallbacks(IRecommendationServiceCallbacks.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IRecommendationService {
            public static IRecommendationService sDefaultImpl;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerCallbacks(IRecommendationServiceCallbacks iRecommendationServiceCallbacks) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRecommendationServiceCallbacks != null ? iRecommendationServiceCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().registerCallbacks(iRecommendationServiceCallbacks);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

