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
import android.telephony.euicc.EuiccInfo;

public interface IGetEuiccInfoCallback
extends IInterface {
    @UnsupportedAppUsage
    public void onSuccess(EuiccInfo var1) throws RemoteException;

    public static class Default
    implements IGetEuiccInfoCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onSuccess(EuiccInfo euiccInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGetEuiccInfoCallback {
        private static final String DESCRIPTOR = "android.service.euicc.IGetEuiccInfoCallback";
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGetEuiccInfoCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGetEuiccInfoCallback) {
                return (IGetEuiccInfoCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGetEuiccInfoCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onSuccess";
        }

        public static boolean setDefaultImpl(IGetEuiccInfoCallback iGetEuiccInfoCallback) {
            if (Proxy.sDefaultImpl == null && iGetEuiccInfoCallback != null) {
                Proxy.sDefaultImpl = iGetEuiccInfoCallback;
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
            object = ((Parcel)object).readInt() != 0 ? EuiccInfo.CREATOR.createFromParcel((Parcel)object) : null;
            this.onSuccess((EuiccInfo)object);
            return true;
        }

        private static class Proxy
        implements IGetEuiccInfoCallback {
            public static IGetEuiccInfoCallback sDefaultImpl;
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
            public void onSuccess(EuiccInfo euiccInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (euiccInfo != null) {
                        parcel.writeInt(1);
                        euiccInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuccess(euiccInfo);
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

