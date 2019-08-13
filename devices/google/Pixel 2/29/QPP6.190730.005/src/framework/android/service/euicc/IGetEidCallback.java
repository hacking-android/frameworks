/*
 * Decompiled with CFR 0.145.
 */
package android.service.euicc;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGetEidCallback
extends IInterface {
    @UnsupportedAppUsage
    public void onSuccess(String var1) throws RemoteException;

    public static class Default
    implements IGetEidCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onSuccess(String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGetEidCallback {
        private static final String DESCRIPTOR = "android.service.euicc.IGetEidCallback";
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGetEidCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGetEidCallback) {
                return (IGetEidCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGetEidCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onSuccess";
        }

        public static boolean setDefaultImpl(IGetEidCallback iGetEidCallback) {
            if (Proxy.sDefaultImpl == null && iGetEidCallback != null) {
                Proxy.sDefaultImpl = iGetEidCallback;
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
            this.onSuccess(parcel.readString());
            return true;
        }

        private static class Proxy
        implements IGetEidCallback {
            public static IGetEidCallback sDefaultImpl;
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
            public void onSuccess(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSuccess(string2);
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

