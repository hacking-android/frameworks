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
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keystore.KeystoreResponse;

public interface IKeystoreCertificateChainCallback
extends IInterface {
    public void onFinished(KeystoreResponse var1, KeymasterCertificateChain var2) throws RemoteException;

    public static class Default
    implements IKeystoreCertificateChainCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onFinished(KeystoreResponse keystoreResponse, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IKeystoreCertificateChainCallback {
        private static final String DESCRIPTOR = "android.security.keystore.IKeystoreCertificateChainCallback";
        static final int TRANSACTION_onFinished = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IKeystoreCertificateChainCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IKeystoreCertificateChainCallback) {
                return (IKeystoreCertificateChainCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IKeystoreCertificateChainCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onFinished";
        }

        public static boolean setDefaultImpl(IKeystoreCertificateChainCallback iKeystoreCertificateChainCallback) {
            if (Proxy.sDefaultImpl == null && iKeystoreCertificateChainCallback != null) {
                Proxy.sDefaultImpl = iKeystoreCertificateChainCallback;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                }
                ((Parcel)object2).writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = ((Parcel)object).readInt() != 0 ? KeystoreResponse.CREATOR.createFromParcel((Parcel)object) : null;
            object = ((Parcel)object).readInt() != 0 ? KeymasterCertificateChain.CREATOR.createFromParcel((Parcel)object) : null;
            this.onFinished((KeystoreResponse)object2, (KeymasterCertificateChain)object);
            return true;
        }

        private static class Proxy
        implements IKeystoreCertificateChainCallback {
            public static IKeystoreCertificateChainCallback sDefaultImpl;
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
            public void onFinished(KeystoreResponse keystoreResponse, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (keystoreResponse != null) {
                        parcel.writeInt(1);
                        keystoreResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (keymasterCertificateChain != null) {
                        parcel.writeInt(1);
                        keymasterCertificateChain.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFinished(keystoreResponse, keymasterCertificateChain);
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

