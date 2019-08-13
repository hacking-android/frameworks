/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IAccountManagerResponse
extends IInterface {
    @UnsupportedAppUsage
    public void onError(int var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public void onResult(Bundle var1) throws RemoteException;

    public static class Default
    implements IAccountManagerResponse {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onError(int n, String string2) throws RemoteException {
        }

        @Override
        public void onResult(Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAccountManagerResponse {
        private static final String DESCRIPTOR = "android.accounts.IAccountManagerResponse";
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAccountManagerResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAccountManagerResponse) {
                return (IAccountManagerResponse)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAccountManagerResponse getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "onError";
            }
            return "onResult";
        }

        public static boolean setDefaultImpl(IAccountManagerResponse iAccountManagerResponse) {
            if (Proxy.sDefaultImpl == null && iAccountManagerResponse != null) {
                Proxy.sDefaultImpl = iAccountManagerResponse;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onError(((Parcel)object).readInt(), ((Parcel)object).readString());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.onResult((Bundle)object);
            return true;
        }

        private static class Proxy
        implements IAccountManagerResponse {
            public static IAccountManagerResponse sDefaultImpl;
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
            public void onError(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(n, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onResult(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onResult(bundle);
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

