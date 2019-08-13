/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.security.keystore.KeystoreResponse;

public interface IKeystoreResponseCallback
extends IInterface {
    public void onFinished(KeystoreResponse var1) throws RemoteException;

    public static class Default
    implements IKeystoreResponseCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onFinished(KeystoreResponse keystoreResponse) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IKeystoreResponseCallback {
        private static final String DESCRIPTOR = "android.security.keystore.IKeystoreResponseCallback";
        static final int TRANSACTION_onFinished = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IKeystoreResponseCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IKeystoreResponseCallback) {
                return (IKeystoreResponseCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IKeystoreResponseCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onFinished";
        }

        public static boolean setDefaultImpl(IKeystoreResponseCallback iKeystoreResponseCallback) {
            if (Proxy.sDefaultImpl == null && iKeystoreResponseCallback != null) {
                Proxy.sDefaultImpl = iKeystoreResponseCallback;
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
            object = ((Parcel)object).readInt() != 0 ? KeystoreResponse.CREATOR.createFromParcel((Parcel)object) : null;
            this.onFinished((KeystoreResponse)object);
            return true;
        }

        private static class Proxy
        implements IKeystoreResponseCallback {
            public static IKeystoreResponseCallback sDefaultImpl;
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
            public void onFinished(KeystoreResponse keystoreResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (keystoreResponse != null) {
                        parcel.writeInt(1);
                        keystoreResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFinished(keystoreResponse);
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

