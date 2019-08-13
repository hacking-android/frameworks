/*
 * Decompiled with CFR 0.145.
 */
package android.app.prediction;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IPredictionCallback
extends IInterface {
    public void onResult(ParceledListSlice var1) throws RemoteException;

    public static class Default
    implements IPredictionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onResult(ParceledListSlice parceledListSlice) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPredictionCallback {
        private static final String DESCRIPTOR = "android.app.prediction.IPredictionCallback";
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPredictionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPredictionCallback) {
                return (IPredictionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPredictionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onResult";
        }

        public static boolean setDefaultImpl(IPredictionCallback iPredictionCallback) {
            if (Proxy.sDefaultImpl == null && iPredictionCallback != null) {
                Proxy.sDefaultImpl = iPredictionCallback;
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
        public boolean onTransact(int n, Parcel parceledListSlice, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)((Object)parceledListSlice), parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)((Object)parceledListSlice)).enforceInterface(DESCRIPTOR);
            parceledListSlice = ((Parcel)((Object)parceledListSlice)).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)((Object)parceledListSlice)) : null;
            this.onResult(parceledListSlice);
            return true;
        }

        private static class Proxy
        implements IPredictionCallback {
            public static IPredictionCallback sDefaultImpl;
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
            public void onResult(ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onResult(parceledListSlice);
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

