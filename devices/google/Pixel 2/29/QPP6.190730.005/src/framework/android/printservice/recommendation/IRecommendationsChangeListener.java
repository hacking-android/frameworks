/*
 * Decompiled with CFR 0.145.
 */
package android.printservice.recommendation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRecommendationsChangeListener
extends IInterface {
    public void onRecommendationsChanged() throws RemoteException;

    public static class Default
    implements IRecommendationsChangeListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onRecommendationsChanged() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRecommendationsChangeListener {
        private static final String DESCRIPTOR = "android.printservice.recommendation.IRecommendationsChangeListener";
        static final int TRANSACTION_onRecommendationsChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRecommendationsChangeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRecommendationsChangeListener) {
                return (IRecommendationsChangeListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRecommendationsChangeListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onRecommendationsChanged";
        }

        public static boolean setDefaultImpl(IRecommendationsChangeListener iRecommendationsChangeListener) {
            if (Proxy.sDefaultImpl == null && iRecommendationsChangeListener != null) {
                Proxy.sDefaultImpl = iRecommendationsChangeListener;
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
            this.onRecommendationsChanged();
            return true;
        }

        private static class Proxy
        implements IRecommendationsChangeListener {
            public static IRecommendationsChangeListener sDefaultImpl;
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
            public void onRecommendationsChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRecommendationsChanged();
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

