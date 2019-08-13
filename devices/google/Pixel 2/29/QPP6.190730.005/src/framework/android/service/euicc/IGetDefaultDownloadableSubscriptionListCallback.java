/*
 * Decompiled with CFR 0.145.
 */
package android.service.euicc;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.euicc.GetDefaultDownloadableSubscriptionListResult;

public interface IGetDefaultDownloadableSubscriptionListCallback
extends IInterface {
    @UnsupportedAppUsage
    public void onComplete(GetDefaultDownloadableSubscriptionListResult var1) throws RemoteException;

    public static class Default
    implements IGetDefaultDownloadableSubscriptionListCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onComplete(GetDefaultDownloadableSubscriptionListResult getDefaultDownloadableSubscriptionListResult) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGetDefaultDownloadableSubscriptionListCallback {
        private static final String DESCRIPTOR = "android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback";
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGetDefaultDownloadableSubscriptionListCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGetDefaultDownloadableSubscriptionListCallback) {
                return (IGetDefaultDownloadableSubscriptionListCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGetDefaultDownloadableSubscriptionListCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onComplete";
        }

        public static boolean setDefaultImpl(IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) {
            if (Proxy.sDefaultImpl == null && iGetDefaultDownloadableSubscriptionListCallback != null) {
                Proxy.sDefaultImpl = iGetDefaultDownloadableSubscriptionListCallback;
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
            object = ((Parcel)object).readInt() != 0 ? GetDefaultDownloadableSubscriptionListResult.CREATOR.createFromParcel((Parcel)object) : null;
            this.onComplete((GetDefaultDownloadableSubscriptionListResult)object);
            return true;
        }

        private static class Proxy
        implements IGetDefaultDownloadableSubscriptionListCallback {
            public static IGetDefaultDownloadableSubscriptionListCallback sDefaultImpl;
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
            public void onComplete(GetDefaultDownloadableSubscriptionListResult getDefaultDownloadableSubscriptionListResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (getDefaultDownloadableSubscriptionListResult != null) {
                        parcel.writeInt(1);
                        getDefaultDownloadableSubscriptionListResult.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onComplete(getDefaultDownloadableSubscriptionListResult);
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

