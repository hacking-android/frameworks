/*
 * Decompiled with CFR 0.145.
 */
package android.service.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.euicc.DownloadSubscriptionResult;

public interface IDownloadSubscriptionCallback
extends IInterface {
    public void onComplete(DownloadSubscriptionResult var1) throws RemoteException;

    public static class Default
    implements IDownloadSubscriptionCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onComplete(DownloadSubscriptionResult downloadSubscriptionResult) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDownloadSubscriptionCallback {
        private static final String DESCRIPTOR = "android.service.euicc.IDownloadSubscriptionCallback";
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDownloadSubscriptionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDownloadSubscriptionCallback) {
                return (IDownloadSubscriptionCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDownloadSubscriptionCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onComplete";
        }

        public static boolean setDefaultImpl(IDownloadSubscriptionCallback iDownloadSubscriptionCallback) {
            if (Proxy.sDefaultImpl == null && iDownloadSubscriptionCallback != null) {
                Proxy.sDefaultImpl = iDownloadSubscriptionCallback;
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
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? DownloadSubscriptionResult.CREATOR.createFromParcel((Parcel)object) : null;
            this.onComplete((DownloadSubscriptionResult)object);
            return true;
        }

        private static class Proxy
        implements IDownloadSubscriptionCallback {
            public static IDownloadSubscriptionCallback sDefaultImpl;
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
            public void onComplete(DownloadSubscriptionResult downloadSubscriptionResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (downloadSubscriptionResult != null) {
                        parcel.writeInt(1);
                        downloadSubscriptionResult.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onComplete(downloadSubscriptionResult);
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

