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
import android.security.keymaster.KeyCharacteristics;
import android.security.keystore.KeystoreResponse;

public interface IKeystoreKeyCharacteristicsCallback
extends IInterface {
    public void onFinished(KeystoreResponse var1, KeyCharacteristics var2) throws RemoteException;

    public static class Default
    implements IKeystoreKeyCharacteristicsCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onFinished(KeystoreResponse keystoreResponse, KeyCharacteristics keyCharacteristics) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IKeystoreKeyCharacteristicsCallback {
        private static final String DESCRIPTOR = "android.security.keystore.IKeystoreKeyCharacteristicsCallback";
        static final int TRANSACTION_onFinished = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IKeystoreKeyCharacteristicsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IKeystoreKeyCharacteristicsCallback) {
                return (IKeystoreKeyCharacteristicsCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IKeystoreKeyCharacteristicsCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onFinished";
        }

        public static boolean setDefaultImpl(IKeystoreKeyCharacteristicsCallback iKeystoreKeyCharacteristicsCallback) {
            if (Proxy.sDefaultImpl == null && iKeystoreKeyCharacteristicsCallback != null) {
                Proxy.sDefaultImpl = iKeystoreKeyCharacteristicsCallback;
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
            object = ((Parcel)object).readInt() != 0 ? KeyCharacteristics.CREATOR.createFromParcel((Parcel)object) : null;
            this.onFinished((KeystoreResponse)object2, (KeyCharacteristics)object);
            return true;
        }

        private static class Proxy
        implements IKeystoreKeyCharacteristicsCallback {
            public static IKeystoreKeyCharacteristicsCallback sDefaultImpl;
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
            public void onFinished(KeystoreResponse keystoreResponse, KeyCharacteristics keyCharacteristics) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (keystoreResponse != null) {
                        parcel.writeInt(1);
                        keystoreResponse.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (keyCharacteristics != null) {
                        parcel.writeInt(1);
                        keyCharacteristics.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFinished(keystoreResponse, keyCharacteristics);
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

