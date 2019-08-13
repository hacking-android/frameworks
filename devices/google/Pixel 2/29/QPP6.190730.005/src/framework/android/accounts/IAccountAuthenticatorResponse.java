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

public interface IAccountAuthenticatorResponse
extends IInterface {
    @UnsupportedAppUsage
    public void onError(int var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public void onRequestContinued() throws RemoteException;

    @UnsupportedAppUsage
    public void onResult(Bundle var1) throws RemoteException;

    public static class Default
    implements IAccountAuthenticatorResponse {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onError(int n, String string2) throws RemoteException {
        }

        @Override
        public void onRequestContinued() throws RemoteException {
        }

        @Override
        public void onResult(Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAccountAuthenticatorResponse {
        private static final String DESCRIPTOR = "android.accounts.IAccountAuthenticatorResponse";
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onRequestContinued = 2;
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAccountAuthenticatorResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAccountAuthenticatorResponse) {
                return (IAccountAuthenticatorResponse)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAccountAuthenticatorResponse getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onError";
                }
                return "onRequestContinued";
            }
            return "onResult";
        }

        public static boolean setDefaultImpl(IAccountAuthenticatorResponse iAccountAuthenticatorResponse) {
            if (Proxy.sDefaultImpl == null && iAccountAuthenticatorResponse != null) {
                Proxy.sDefaultImpl = iAccountAuthenticatorResponse;
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
                    if (n != 3) {
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
                this.onRequestContinued();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.onResult((Bundle)object);
            return true;
        }

        private static class Proxy
        implements IAccountAuthenticatorResponse {
            public static IAccountAuthenticatorResponse sDefaultImpl;
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
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
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
            public void onRequestContinued() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRequestContinued();
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

