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
import android.service.euicc.GetEuiccProfileInfoListResult;

public interface IGetEuiccProfileInfoListCallback
extends IInterface {
    @UnsupportedAppUsage
    public void onComplete(GetEuiccProfileInfoListResult var1) throws RemoteException;

    public static class Default
    implements IGetEuiccProfileInfoListCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onComplete(GetEuiccProfileInfoListResult getEuiccProfileInfoListResult) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGetEuiccProfileInfoListCallback {
        private static final String DESCRIPTOR = "android.service.euicc.IGetEuiccProfileInfoListCallback";
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGetEuiccProfileInfoListCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGetEuiccProfileInfoListCallback) {
                return (IGetEuiccProfileInfoListCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGetEuiccProfileInfoListCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onComplete";
        }

        public static boolean setDefaultImpl(IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) {
            if (Proxy.sDefaultImpl == null && iGetEuiccProfileInfoListCallback != null) {
                Proxy.sDefaultImpl = iGetEuiccProfileInfoListCallback;
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
            object = ((Parcel)object).readInt() != 0 ? GetEuiccProfileInfoListResult.CREATOR.createFromParcel((Parcel)object) : null;
            this.onComplete((GetEuiccProfileInfoListResult)object);
            return true;
        }

        private static class Proxy
        implements IGetEuiccProfileInfoListCallback {
            public static IGetEuiccProfileInfoListCallback sDefaultImpl;
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
            public void onComplete(GetEuiccProfileInfoListResult getEuiccProfileInfoListResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (getEuiccProfileInfoListResult != null) {
                        parcel.writeInt(1);
                        getEuiccProfileInfoListResult.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onComplete(getEuiccProfileInfoListResult);
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

